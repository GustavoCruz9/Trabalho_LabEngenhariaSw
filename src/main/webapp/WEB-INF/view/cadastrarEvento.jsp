<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<title>Eclipse Lounge - Cadastro de Evento</title>
<link rel="stylesheet" type="text/css"
	href='<c:url value = "./resources/css/styleCadastrarEvento.css"/>'>
</head>

<body>

	<header>
		<a href="index"><img alt="Logo"
			src="./resources/images/LogoIconSemBackground.png"></a>

		<div class="loginIcon">
			<a href="login"> <img alt="Logo"
				src="./resources/images/loginIcon.png" class="userIcon"> <c:if
					test="${acesso.permissao eq 1 or acesso.permissao eq 2}">
					<h3>Bem Vindo</h3>
				</c:if> <c:if test="${acesso.permissao ne 1 and acesso.permissao ne 2}">
					<h3>Login</h3>
				</c:if>
			</a>
		</div>
	</header>

	<c:if test="${acesso.permissao eq 1}">
		<nav>
			<a href="meusDados">Meus Dados</a> <a href="visualizarIngressos">Visualizar
				Ingressos</a>
			<form action="login" method="Post">
				<input type="submit" value="Deslogar" id="botao" name="botao">
			</form>
		</nav>
	</c:if>

	<c:if test="${acesso.permissao eq 2}">
		<nav>
			<a href="meusDados">Meus Dados</a> <a href="cadastrarEvento">Cadastrar
				Evento</a> <a href="cadastrarArtista">Cadastrar Artista</a>
			<form action="login" method="Post" class="deslogar">
				<input type="submit" value="Deslogar" id="botao" name="botao">
			</form>
		</nav>
	</c:if>

	<main>
		<h1>Cadastro de Evento</h1>
		<div align="center">
			<c:if test="${not empty erro }">
				<h2>
					<b> <c:out value="${erro }" />
					</b>
				</h2>
			</c:if>
		</div>
		<div align="center">
			<c:if test="${not empty saida }">
				<h2>
					<b> <c:out value="${saida }" />
					</b>
				</h2>
			</c:if>
		</div>
		<form action="cadastrarEvento" method="post" class="meusDados">
			<table>
				<tr>
					<td colspan="2">&nbsp&nbsp&nbsp&nbsp<input type="number" name="codigo" id="codigo"
						placeholder="Codigo"
						value='<c:out value="${evento.codigo}"></c:out>'></td>
					<td colspan="1"><input type="submit" name="botao" id="botao"
						value="Buscar"></td>	
				</tr>
				<tr>
					<td colspan="3"><input type="text" name="titulo" id="titulo"
						placeholder="Titulo"
						value='<c:out value="${evento.titulo}"></c:out>'></td>
				</tr>
				<tr>
					<td colspan="3"><input type="text" name="linkImagem"
						id="linkImagem" placeholder="Link do Cartaz"
						value='<c:out value="${evento.linkImagem}"></c:out>'></td>
				</tr>
				<tr>
					<td colspan="2">
						<select name="statusEvento" id="statusEvento">
						    <c:choose>
						        <c:when test="${empty evento.statusEvento}">
						            <option value='<c:out value="${evento.statusEvento}"></c:out>' disabled selected>Selecione o status do evento</option>
						            <option value="Ativo">Ativo</option>
						            <option value="Inativo">Inativo</option>
						        </c:when>
						        <c:otherwise>
						            <option value='<c:out value="${evento.statusEvento}"></c:out>' disabled>Selecione o status do evento</option>
						            <option value="Ativo" <c:if test="${evento.statusEvento == 'Ativo'}">selected</c:if>>Ativo</option>
						            <option value="Inativo" <c:if test="${evento.statusEvento == 'Inativo'}">selected</c:if>>Inativo</option>
						        </c:otherwise>
						    </c:choose>
						</select>
					</td>
					<td colspan="1"><input class="valorBase" type="number" id="valorBase"
						name="valorBase" min="0" step="0.01" placeholder="Valor Base"
						value='<c:out value="${evento.valor}"></c:out>'></td>
				</tr>
				<tr>
					<td colspan="1"><label for="horaInicio">Hora de
							Inicio: </label> <input type="time" name="horaInicio" id="horaInicio"
						value='<c:out value="${evento.horaInicio}"></c:out>'></td>
					<td colspan="1"><label for="horaInicio">Hora de Fim: </label>
						<input type="time" name="horaFim" id="horaFim"
						value='<c:out value="${evento.horaFim}"></c:out>'></td>
					<td colspan="1"><label for="data">Data: </label> <input
						type="date" id="data" name="data"
						value='<c:out value="${evento.data}"></c:out>'></td>
				</tr>
				<tr>
				</tr>
			</table>
		
			<div class="tabelaArtistas">
				<div class="table-container">
					<div class="esquerda">
						<table>
							<thead>
								<tr>
									<th colspan="4">Lista de Artistas no evento</th>
								</tr>
								<tr>
									<th>Código</th>
									<th>Nome</th>
									<th>Genero</th>
									<th></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="ae" items="${artistasEvento}">
									<tr>
										<td><c:out value="${ae.codigo}"></c:out></td>
										<td><c:out value="${ae.nome}"></c:out></td>
										<td><c:out value="${ae.genero}"></c:out></td>
										<td>
												<form action="cadastrarEvento" method="post" class="meusDados">
												<input type="hidden" id="codigoArtista" name="codigoArtista" value="${ae.codigo}" /> 
												<input type="hidden" id="nomeArtista" name="nomeArtista" value="${ae.nome}" /> 
												<input type="hidden" id="generoArtista" name="generoArtista" value="${ae.genero}" /> 
												<input type="hidden" id="codigo" name="codigo" value="${evento.codigo}" /> 
												<input type="hidden" id="titulo" name="titulo" value="${evento.titulo}" /> 
												<input type="hidden" id="linkImagem" name="linkImagem" value="${evento.linkImagem}" /> 
												<input type="hidden" id="genero" name="genero" value="${evento.genero}" /> 
												<input type="hidden" id="valorBase" name="valorBase" value="${evento.valor}" /> 
												<input type="hidden" id="horaInicio" name="horaInicio" value="${evento.horaInicio}" /> 
												<input type="hidden" id="horaFim" name="horaFim" value="${evento.horaFim}" /> 
												<input type="hidden" id="data" name="data" value="${evento.data}" /> 
												<input type="hidden" id="statusEvento" name="statusEvento" value="${evento.statusEvento}" /> 
												<input type="submit" value="Remover" id="botao" name="botao" />
												</form>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>

					<div class="esquerda">
						<input type="text" name="pesquisarArtista" id="pesquisarArtista"
							placeholder="Pesquisar Artista"> <input type="submit"
							value="Pesquisar" id="botao" name="botao" class="btn">
						<table>
							<thead>
								<tr>
									<th colspan="4">Artistas Disponíveis</th>
								</tr>
								<tr>
									<th>Código</th>
									<th>Nome</th>
									<th>Genero</th>
									<th></th>
								</tr>
							</thead>
							<tbody>
							
								<c:forEach var="a" items="${artistas}">
									<tr>
										<td><c:out value="${a.codigo}"></c:out></td>
										<td><c:out value="${a.nome}"></c:out></td>
										<td><c:out value="${a.genero}"></c:out></td>
										<td>
											<form action="cadastrarEvento" method="post" class="meusDados">
											<input type="hidden" id="codigoArtista" name="codigoArtista" value="${a.codigo}" /> 
											<input type="hidden" id="nomeArtista" name="nomeArtista" value="${a.nome}" /> 
											<input type="hidden" id="generoArtista" name="generoArtista" value="${a.genero}" /> 
											<input type="hidden" id="codigo" name="codigo" value="${evento.codigo}" /> 
											<input type="hidden" id="titulo" name="titulo" value="${evento.titulo}" /> 
											<input type="hidden" id="linkImagem" name="linkImagem" value="${evento.linkImagem}" /> 
											<input type="hidden" id="genero" name="genero" value="${evento.genero}" /> 
											<input type="hidden" id="valorBase" name="valorBase" value="${evento.valor}" /> 
											<input type="hidden" id="horaInicio" name="horaInicio" value="${evento.horaInicio}" /> 
											<input type="hidden" id="horaFim" name="horaFim" value="${evento.horaFim}" /> 
											<input type="hidden" id="data" name="data" value="${evento.data}" />
											<input type="hidden" id="statusEvento" name="statusEvento" value="${evento.statusEvento}" /> 
											<input type="submit" value="Adicionar"id="botao" name="botao" />
											</form>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
			</div>
			<div class="botoesBaixo">
				<input type="submit" value="Cadastrar" id="botao" name="botao" />
				<input type="submit" value="Atualizar" id="botao" name="botao" />
			</div>
	</main>
	</form>
</body>
</html>