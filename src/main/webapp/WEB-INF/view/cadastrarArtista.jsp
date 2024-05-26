<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Eclipse Lounge - Cadastro de Artista</title>
<link rel="stylesheet" type="text/css"
	href='<c:url value = "./resources/css/styleCadastrarArtista.css"/>'>
</head>

<body>

	<header>
		<a href="./index"><img alt="Logo"
			src="./resources/images/LogoIconSemBackground.png"></a>

		<div class="loginIcon">
			<a href="login"> <img alt="Logo"
				src="./resources/images/loginIcon.png" class="userIcon">
			<c:if test="${acesso.permissao eq 1 or acesso.permissao eq 2}">
					<h3>Bem Vindo</h3>
			</c:if>
			<c:if test="${acesso.permissao ne 1 and acesso.permissao ne 2}">
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
			<a href="meusDados">Meus Dados</a> 
			<a href="cadastrarEvento">Cadastrar Evento</a> 
			<a href="cadastrarArtista">Cadastrar Artista</a>
			<form action="login" method="Post" class="deslogar">
				<input type="submit" value="Deslogar" id="botao" name="botao">
			</form>
		</nav>
	</c:if>

	<main>
		<h1>Cadastro de Artista:</h1>
		<div align="center">
			<c:if test="${not empty erro }">
				<h2>
					<b><c:out value="${erro }" /></b>
				</h2>
			</c:if>
		</div>
		<div align="center">
			<c:if test="${not empty saida }">
				<h2>
					<b><c:out value="${saida }" /></b>
				</h2>
			</c:if>
		</div>
		<form action="cadastrarArtista" method="post" class="meusDados">
			<table>
				<tr>
					<td colspan="1"><input type="number" name="codigo" id="codigo"
						placeholder="Codigo" value='<c:out value="${artista.codigo}"></c:out>'></td>
					<td colspan="1"><input type="submit" name="botao" id="botao"
						value="Buscar"></td>
				</tr>
				<tr>
					<td colspan="2"><input type="text" name="nome" id="nome"
						placeholder="Nome" value='<c:out value="${artista.nome}"></c:out>'></td>
				</tr>
				<tr>
					<td colspan="2"><input type="text" name="genero" id="genero"
						placeholder="Genero" value='<c:out value="${artista.genero}"></c:out>'></td>
				</tr>
				<tr>
					<td colspan="2"><input type="submit" value="Cadastrar"
						id="botao" name="botao"></td>
				</tr>
				<tr>
					<td colspan="1"><input type="submit" value="Atualizar"
						id="botao" name="botao"></td>
					<td colspan="1"><input type="submit" value="Listar" id="botao"
						name="botao"></td>
				</tr>
			</table>
		</form>

		<c:if test="${not empty artistas}">
			<div class="tabelaArtistas">
				<table>
					<thead>
						<th>Codigo</th>
						<th>Nome</th>
						<th>Genero</th>
					</thead>
					<tbody>
						<c:forEach var="a" items="${artistas}">
							<tr>
								<td><c:out value="${a.codigo}"></c:out></td>
								<td><c:out value="${a.nome}"></c:out></td>
								<td><c:out value="${a.genero}"></c:out></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</c:if>
	</main>
</body>

</html>