<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<title>Eclipse Lounge - Tela de Login</title>
<link rel="stylesheet" type="text/css"
	href='<c:url value = "./resources/css/styleTelaLogin.css"/>'>
<link rel="stylesheet" href="./resources/css/styleCadastrarCliente.css">
</head>
<body>

	<a href="index"> <img src="./resources/images/LogoSemBackground.png"
		alt="">
	</a>

	<div align="center">
		<c:if test="${not empty erro }">
			<h2>
				<b><c:out value="${erro }" /></b>
			</h2>
		</c:if>
	</div>

	<br />

	<div align="center">
		<c:if test="${not empty saida }">
			<h3>
				<b><c:out value="${saida }" /></b>
			</h3>
		</c:if>
	</div>


	<form action="cadastrarCliente" method="post">
		<table>
			<tr>
				<td><input type="text" placeholder="Nome" id="nome" name="nome" value="${cliente.nome}">
				</td>
				<td><input type="text" placeholder="Endereço" id="endereco"
					name="endereco" value="${cliente.endereco}"></td>
			</tr>
			<tr>
				<td><input type="text" placeholder="CPF" id="cpf" name="cpf" value="${cliente.cpf}">
				</td>
				<td><input type="text" placeholder="Usuario" id="usuario"
					name="usuario" value="${cliente.acesso.usuario}"></td>
			</tr>
			<tr>
				<td><input type="email" placeholder="Email" id="email"
					name="email" value="${cliente.email}"></td>
				<td><input type="password" placeholder="Senha" id="senha"
					name="senha" value="${cliente.acesso.senha}"></td>
			</tr>
			<tr>
				<td>
					<div class="dataNasc">
						<label for="dataNasc">Data de Nascimento:</label> <input
							type="date" placeholder="Data de Nascimento" id="dataNascimento"
							name="dataNascimento" value="${cliente.dataNascimento}">
					</div>
				</td>
				<td><input type="password" placeholder="Confirmar Senha"
					id="confirmarSenha" name="confirmarSenha" value="${confirmarSenha}"></td>
			</tr>
			<tr>
				<td colspan="4"><input type="submit" value="Cadastrar"
					id="botao" name="botao"></td>
			</tr>
		</table>
	</form>
</body>
</html>
