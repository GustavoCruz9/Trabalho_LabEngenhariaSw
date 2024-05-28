/*
	use master
	go
	drop database TrabalhoEngSw
*/

Create database TrabalhoEngSw
go 
use TrabalhoEngSw
Go
-- Criacao de tabelas 
Create table Acesso(
usuario varchar(255) not null Unique,
senha varchar(255) not null,
permissao int not null
Primary Key(usuario)
)
Go
Create table Cliente(
cpf char(11) not null,
nome varchar(150) not null,
dataNascimento date not null,
email varchar(170) not null,
endereco varchar(255) not null,
loginUsuario varchar(255) not null
Primary key(cpf)
Foreign key(loginUsuario) references Acesso(usuario)
)
Go
Create table Funcionario(
registro varchar(20) not null,
nome varchar(150) not null,
cargo varchar(100) not null,
loginUsuario varchar(255) not null
Primary Key(registro)
Foreign key(loginUsuario) references Acesso(usuario)
)
Go
Create table Evento(
codigo int not null,
horaInicio time(7) not null,
horaFim time(7) not null,
dataEvento date not null,
titulo varchar(150) not null,
genero varchar(150) not null,
valor decimal(7,2) not null,
linkImagem varchar(Max) not null,
funcionarioRegistro varchar(20) not null,
statusEvento varchar(10) not null default('Ativo')
Primary Key(codigo)
Foreign key(funcionarioRegistro) references Funcionario(registro)
)
Go
Create table Artista(
codigo int not null,
nome varchar(150) not null,
genero varchar(100) not null
Primary Key(codigo)
)
Go
Create table Evento_Artista(
artistaCodigo int not null,
eventoCodigo int not null
Primary Key(artistaCodigo, eventoCodigo)
)
go
Create table Compra(
notaFiscal int not null Identity(1001,1),
valorTotal decimal(7,2) not null,
quantidade int not null,
clienteCpf char(11) not null
Primary Key(notaFiscal)
Foreign Key(clienteCpf) references Cliente(cpf)
)
Go
Create table Ingresso(
codigo int not null Identity(1,1),
preco decimal(7,2) not null,
tipo varchar(15) not null,
setor varchar(20) not null,
eventoCodigo int not null,
notaFiscal int not null
Primary Key(codigo)
Foreign Key(eventoCodigo) References Evento(codigo),
Foreign Key(notaFiscal) References Compra(notaFiscal)
)

GO
--Procedure que valida data de nascimento, verifificando se é maior que 18;
CREATE PROCEDURE sp_validaIdade(@dt_nasc DATE, @status BIT OUTPUT)
AS
	DECLARE @hoje	DATE,
			@idade	INT
	set @idade = DATEDIFF(YEAR, @dt_nasc, GETDATE())
	If(@idade >= 18)
	Begin
		set @status = 1
	End
	Else
	Begin
		RaisError('Idade Invalida', 16, 1)
		Set @status = 0
	End


-- Algoritmo que valida cpf
GO
Create Procedure sp_algoritmoCPF(@cpf char(11), @saida varchar(100) output )
As
--Declarações de Variaveis
Declare @cont int,
		@valor int,
		@status int,
		@x int

-- Atribuindo Valores
set @status = 0
set @cont = 0 
set @x = 2

-- Verifica se contem 11 digitos
	If(LEN(@cpf) = 11) Begin
		-- Verificando se os 11 digitos são iguais
		While(@cont<10) begin
			If(SUBSTRING(@cpf,1,1) = SUBSTRING(@cpf,@x,1) ) begin	
				set @status = @status + 1
			end
		set @cont = @cont + 1
		set @x = @x+1
		End
		If(@status < 10) Begin
			-- Realizando calculo do primeiro digito
			Declare @valorMutiplicado int
			set @valor = 10
			set @cont = 0
			set @x = 1
			set @valorMutiplicado = 0

			While(@cont<9) Begin
				set @valorMutiplicado = CAST(SUBSTRING(@cpf,@x,1) as int) * @valor + @valorMutiplicado
				set @x = @x+1
				set @cont = @cont + 1
				set @valor = @valor - 1
			End

			Declare @valorDivido int,
					@primeiroDigito int
			set @valorDivido = @valorMutiplicado % 11

			If (@valorDivido < 2) Begin
				set @primeiroDigito = 0
			End
			Else Begin
				set @primeiroDigito = 11 - @valorDivido
			End

			-- Verifica se o digito calculado é igual o digito inserido
			If( CAST(SUBSTRING(@cpf,10,1)as int) = @primeiroDigito) Begin
				-- Calculando o segundo digito
				set @valor = 11
				set @cont = 0
				set @x = 1
				set @valorMutiplicado = 0

				While(@cont<10) Begin
				set @valorMutiplicado = CAST(SUBSTRING(@cpf,@x,1) as int) * @valor + @valorMutiplicado
				set @x = @x+1
				set @cont = @cont + 1
				set @valor = @valor - 1
				End
				
				Declare @segundoDigito int
				set @valorDivido = @valorMutiplicado % 11

				If (@valorDivido < 2) Begin
				set @segundoDigito = 0
				End
				Else Begin
				set @segundoDigito = 11 - @valorDivido
				End

				-- Verifica se o ultimo digito calculado correponde a o ultimo digito inserido
				If( CAST(SUBSTRING(@cpf,11,1)as int) = @segundoDigito) Begin
					set @saida = 'CPF Valido'
				End
				Else Begin
					RaisError('CPF Invalido', 16,1)
				End

			End
			Else Begin
				RaisError('CPF Invalido', 16,1)
			End
		End
		Else Begin
		RaisError('Todos os digitos do CPF são iguais ', 16,1)
		End
	End
	Else Begin
	RaisError('Numero de digitos do CPF invalido', 16,1)
	End

--Procedure que registra o login
Go
CREATE PROCEDURE sp_cadastraLogin(@usuario varchar(255), @senha varchar(255), @permissao int, @status bit output)
as
	Begin Try
		Insert into Acesso Values(@usuario, @senha, @permissao)
		set @status = 1
	End Try
	Begin Catch
		Declare @erro varchar(255)
		set @erro = ERROR_MESSAGE()
		set @status = 0
		If(@erro Like '%Primary%') Begin
			RaisError('Este usuario ja esta cadastrado',16,1)
		End
	End Catch

-- Procedure que cadastra e atualiza cliente
GO
CREATE PROCEDURE sp_iuCliente(@op CHAR(1), @cpf char(11), @nome VARCHAR(150), @dt_nasc Date,
	@email varchar(170), @endereco varchar(255), @loginUsuario varchar(255), @senha varchar(255), @saida VARCHAR(100) OUTPUT)
AS
	Declare @verificaCpf varchar(100)
	exec sp_algoritmoCPF @cpf, @verificaCpf output

	If(@verificaCpf = 'CPF Valido') Begin
		If(Upper(@op) = 'I' ) Begin
			Declare @verificaIdade bit
			Exec sp_validaIdade @dt_nasc, @verificaIdade output

			If(@verificaIdade = 1) Begin
				set @verificaCpf = (Select cpf from Cliente where cpf = @cpf)
				If(@verificaCpf is not null) Begin
					RaisError('Este Cpf ja esta cadastrado',16,1)
				End
				Else Begin
					declare @verificaLogin bit
					exec sp_cadastraLogin @loginUsuario, @senha, 1, @verificaLogin output
				End
				If(@verificaLogin = 1) Begin
					Begin Try
						Insert into Cliente Values(@cpf, @nome, @dt_nasc, @email, @endereco, @loginUsuario)
						set @saida = 'Cliente cadastrado com sucesso'
					End try
					Begin Catch
						Declare @erro varchar(255)
						set @erro = ERROR_MESSAGE()
					End Catch
				End
			End
		End

		If(UPPER(@op) = 'U') Begin
			Update Cliente set nome = @nome, email = @email, endereco = @endereco where cpf = @cpf
			set @saida = 'Cliente atualizado com sucesso'
		End
	End

--Procedure que Realiza login
Go
Create Procedure  sp_realizaLogin(@usuario varchar(255), @senha varchar(255), @saida varchar(100) output, @permissao int output)
As
	Declare @verificaUsuario varchar(255),
			@verificaSenha varchar(255)
			
	
	set @permissao = 0
	set @verificaUsuario = (Select usuario from Acesso where usuario = @usuario)

	If(@verificaUsuario is not null) Begin
		set @verificaSenha = (Select senha from Acesso where usuario = @usuario and senha = @senha)

		If(@verificaSenha is not null) Begin
			set @permissao = (Select  permissao from Acesso where usuario = @usuario)
			set @saida = 'Logou'
		End
		Else Begin
			set @saida = 'Senha Incorreta'
		End
	End
	Else Begin
		set @saida = 'Este usuario não existe' 
	End

-- Criando a procedure que traz a nota fiscal da compra e insere a compra
GO
create Procedure sp_compra(@valorTotal decimal(7,2), @usuario varchar(255), @quantidade int, @codigoEvento int, @notaFiscal int output)
As
    Declare @cpf char(11),
            @qtdIngressoComprado int

    set @cpf = (Select cpf from Cliente where loginUsuario = @usuario)
    --Verifica se ja existe uma compra de 4 ingresso nesse evento
    set @qtdIngressoComprado = (Select count(distinct i.codigo) from Ingresso i, compra c where c.clienteCpf = @cpf and eventoCodigo = @codigoEvento)

    If(@qtdIngressoComprado + @quantidade <= 4) Begin
        Insert into Compra Values(@valorTotal, @quantidade, @cpf)
        set @notaFiscal = (Select Top(1)notaFiscal from Compra where clienteCpf = @cpf order by notaFiscal desc)
    End
    Else Begin
        RaisError('Limite de 4 ingressos para esse cpf. Atingido!',16,1)
    End
-- Procedure que cadastra e atualiza Artista
GO
create procedure sp_iuArtista(@op char(1), @codigo int, @nome varchar(150), @genero varchar(100), @saida varchar(100) output)
as
	if(upper(@op) = 'I')begin

			begin try
				insert into Artista (codigo, nome, genero) values (@codigo, @nome, @genero)

				set @saida = 'Artista inserido com sucesso'
			end try
			begin catch
				set @saida = error_message()
				if(@saida like '%Violação da restrição PRIMARY KEY%')begin
					set @saida = 'Este codigo ja existe'
				end
			end catch
	end
	else 
			if(upper(@op) = 'U')
			begin
			
						set @codigo = (select codigo from Artista where codigo = @codigo)

						if(@codigo is not null)
						begin
							update Artista set nome = @nome,  genero = @genero where codigo = @codigo

							set @saida = 'Artista atualizado com sucesso'
						end
						else
						begin
							set @saida = 'Codigo de artista inexistente'
						end
	
			end
			else
			begin
				raiserror('Operação invalida', 16, 1)
				return
			end

go
--Drop procedure sp_verificaEvento
Create Procedure sp_verificaEvento(@horaInicio time(7),
@horaFim time(7), @data date, @codigo int, @saida bit output)
as
		Declare		
					@compHoraInicio time(7),
					@compHoraFim time(7),
					@diaSemana int,
					@tempoEvento time(7),
					@status int

			set @diaSemana = (SELECT DATEPART(WEEKDAY, @data))
			set @status = 1

			If(@diaSemana!=2 and @diaSemana !=3) Begin
				 IF ((@horaInicio >= '13:00:00') OR (@horaInicio < '03:00:00')) AND
					((@horaFim <= '03:00:00') OR (@horaFim >= '13:00:00')) Begin
		
					IF @horaFim >= @horaInicio
					BEGIN
						SET @tempoEvento = DATEADD(MINUTE, DATEDIFF(MINUTE, @horaInicio, @horaFim), '00:00:00');
					END
					ELSE
					BEGIN
						SET @tempoEvento = DATEADD(MINUTE, DATEDIFF(MINUTE, @horaInicio, '23:59:59') + 1 + DATEDIFF(MINUTE, '00:00:00', @horaFim), '00:00:00');
					END

					If(@tempoEvento>='01:30:00' and @tempoEvento <='05:00:00') Begin
						DECLARE c CURSOR FOR 
						SELECT horaInicio, horaFim FROM Evento where dataEvento = @data and codigo != @codigo
						OPEN c
						FETCH NEXT FROM c 
							INTO @compHoraInicio, @compHoraFim
						WHILE @@FETCH_STATUS = 0
						BEGIN
						  IF (@horaInicio BETWEEN @compHoraInicio AND @compHoraFim) OR
							 (@horaFim BETWEEN @compHoraInicio AND @compHoraFim) OR
							 (@compHoraInicio BETWEEN @horaInicio AND @horaFim) OR
							 (@compHoraFim BETWEEN @horaInicio AND @horaFim)
						  Begin
								RaisError('Ja existe um evento cadastrado nesse intervalo de tempo', 16, 1)
								CLOSE c
								DEALLOCATE c
								return
							End
							IF (DATEDIFF(MINUTE, @compHoraFim, @horaInicio) < 120 AND DATEDIFF(MINUTE, @compHoraFim, @horaInicio) > 0) OR
								(DATEDIFF(MINUTE, @horaFim, @compHoraInicio) < 120 AND DATEDIFF(MINUTE, @horaFim, @compHoraInicio) > 0)
           						BEGIN
								RaisError('Entre cada evento deve ter um intervalo de 2 horas', 16, 1)
								CLOSE c
								DEALLOCATE c
								return
							End

							FETCH NEXT FROM c INTO @compHoraInicio, @compHoraFim
						End
						CLOSE c
						DEALLOCATE c
						-- Insert aqui
						set @saida = 1
					End
					Else Begin
						RaisError('Eventos devem ter no minimo 1h e 30m de duração e no maximo 5h', 16, 1)
						return
					End

				End
				Else Begin
					RaisError('So pode ser cadastro eventos entre 13h a 3h, respeitando as regras da casa', 16, 1)
					return
				End	
			End
			Else Begin
				RaisError('So podemos ter eventos entre quarta e domingo, consulte o calendario', 16, 1)
				return
			End

--Procedure que cadastra e atualiza evento
-- Drop Procedure sp_iuEvento
Go
Create Procedure sp_iuEvento(@op char(1), @codigo int, @titulo varchar(150), @horaInicio time(7),
@horaFim time(7), @data date, @genero varchar(150), @valor decimal(7,2), @linkImagem varchar(MAX), @usuarioFuncionario varchar(255), @statusEvento varchar(10),
@saida varchar(100) output)
as
			Declare @registroFunc varchar(20),
					@compHoraInicio time(7),
					@compHoraFim time(7),
					@diaSemana int,
					@tempoEvento time(7),
					@status bit

			set @registroFunc = (Select registro from Funcionario where loginUsuario = @usuarioFuncionario)
			set @diaSemana = (SELECT DATEPART(WEEKDAY, @data))
			set @status = 0

 if(upper(@op) = 'I')begin
			

			exec sp_verificaEvento @horaInicio, @horaFim, @data, @codigo, @status output
			if(@status = 1) Begin
				Begin Try
						Insert into Evento(codigo, horaInicio,horaFim,dataEvento, titulo, genero ,valor, linkImagem, funcionarioRegistro) 
						Values(@codigo,@horaInicio, @horaFim, @data, @titulo, @genero, @valor, @linkImagem, @registroFunc)
						set @saida = 'Evento registrado com sucesso'
					End Try
					Begin catch
						set @saida = error_message()
						If(@saida like '%Violação da restrição PRIMARY KEY%')begin
							RaisError('Já existe um evento com este código. Se você deseja atualizar, por favor, utilize a operação apropriada.', 16, 1)
						End
					End catch
			End
End
if(upper(@op) = 'U')begin
		exec sp_verificaEvento @horaInicio, @horaFim, @data, @codigo, @status output
		if(@status = 1) Begin
				Begin Try
						Update Evento set horaInicio = @horaInicio, horaFim = @horaFim, dataEvento = @data, titulo = @titulo,
						linkImagem = @linkImagem, genero = @genero, valor = @valor, statusEvento = @statusEvento where codigo = @codigo
						set @saida = 'Evento Atualizado com sucesso'
					End Try
					Begin catch
						set @saida = error_message()
					End catch	
		End
	End
go
create Procedure sp_verificaIngresso(@qtdPista int, @qtdCamarote int, @qtdFront int,@codigoEvento int, @status int output)
As
    Declare @ingressoDisponiveisCamarote int,
            @ingressoDisponiveisPista int,
            @ingressoDisponiveisFront int

    set @ingressoDisponiveisCamarote = 1000 - (Select COUNT(codigo) from Ingresso where eventoCodigo = @codigoEvento and setor = 'Camarote')
    set @ingressoDisponiveisPista = 3000 - (Select COUNT(codigo) from Ingresso where eventoCodigo = @codigoEvento and setor = 'Pista')
    set @ingressoDisponiveisFront = 1000 - (Select COUNT(codigo) from Ingresso where eventoCodigo = @codigoEvento and setor = 'FrontStage')
    set @status = 1

    If(@qtdPista > @ingressoDisponiveisPista) Begin
        set @status = 0
        RaisError('Ingressos para pista esgostado', 16, 1)
        return
    End
    If(@qtdCamarote > @ingressoDisponiveisCamarote) Begin
        set @status = 0
        RaisError('Ingressos para camarote esgostado', 16, 1)
        return
    End
    If(@qtdFront > @ingressoDisponiveisFront) Begin
        set @status = 0
        RaisError('Ingressos para frontstage esgostado', 16, 1)
        return
    End
/*
	INSERT INTO Acesso (usuario, senha, permissao)
VALUES ('ja', 'k', 2);
go
INSERT INTO Funcionario (registro, nome, cargo, loginUsuario)
VALUES ('F123', 'João Silva', 'Organizador', 'ja')
*/
