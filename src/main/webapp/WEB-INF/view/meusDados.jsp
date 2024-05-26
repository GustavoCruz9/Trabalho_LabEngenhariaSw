<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<title>Eclipse Lounge - Tela Inicial</title>
<link rel="stylesheet" type="text/css"
	href='<c:url value = "./resources/css/styleMeusDados.css"/>'>
</head>

<body>

	<header>
		<a href="./index"><img alt="Logo"
			src="./resources/images/LogoIconSemBackground.png"></a>

		<div class="loginIcon">
			<a href="login"> <img alt="Logo"
				src="./resources/images/loginIcon.png" class="userIcon">
				<h3>Bem Vindo</h3>
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
			<a href="meusDados">Meus Dados</a> <a href="cadastrarEvento">Cadastrar Evento</a> <a
				href="cadastrarArtista">Cadastrar Artista</a> 
			<form action="login" method="Post">
				<input type="submit" value="Deslogar" id="botao" name="botao">
			</form>
		</nav>
	</c:if>

	<main>

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

		<h1>Meus Dados:</h1>
		<c:if test="${acesso.permissao eq 1}">
			<form action="meusDados" method="post" class="meusDados">
				<table>
					<tr>
						<td colspan="1">
							<div class="content">
								<label for="cpf">Cpf:</label> <input type="text" name="cpf"
									id="cpf" value="${cliente.cpf}" readonly>
							</div>
						</td>
						<td colspan="1">
							<div class="content">
								<label for="dataNascimento">Data de Nascimento:</label> <input
									type="date" name="dataNascimento" id="dataNascimento"
									value="${cliente.dataNascimento}" readonly>
							</div>
						</td>
					</tr>
					<tr>
						<td colspan="">
							<div class="content">
								<label for="nome">Nome:</label> <input type="text" name="nome"
									id="nome" value="${cliente.nome}"> <img
									src="./resources/images/lapisEditar.png" alt="editavel">
							</div>
						</td>
					</tr>
					<tr>
						<td colspan="1">
							<div class="content">
								<label for="email">Email</label> <input type="email"
									name="email" id="email" value="${cliente.email}"> <img
									src="./resources/images/lapisEditar.png" alt="editavel">
							</div>
						</td>
					</tr>
					<tr>
						<td colspan="1">
							<div class="content">
								<label for="endereco">Endereço:</label> <input type="text"
									name="endereco" id="endereco" value="${cliente.endereco}">
								<img src="./resources/images/lapisEditar.png" alt="editavel">
							</div>
						</td>
					</tr>
				</table>
				<input type="submit" value="Atualizar" id="botao" name="botao"
					class="atualizar">
			</form>
		</c:if>

		<c:if test="${acesso.permissao eq 2}">
			<form action="meusDados" method="post" class="meusDados">
				<table>
					<tr>
						<td colspan="1">
							<div class="content">
								<label for="registro">Registro:</label> <input type="text"
									name="registro" id="registro" value="${funcionario.registro}"
									readonly>
							</div>
						</td>
					</tr>
					<tr>
						<td colspan="">
							<div class="content">
								<label for="nome">Nome:</label> <input type="text" name="nome"
									id="nome" value="${funcionario.nome}" readonly>
							</div>
						</td>
					</tr>
					<tr>
						<td colspan="1">
							<div class="content">
								<label for="cargo">Cargo</label> <input type="text" name="cargo"
									id="cargo" value="${funcionario.cargo}" readonly>
							</div>
						</td>
					</tr>
				</table>
			</form>
		</c:if>

	</main>

</body>

</html>