<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Eclipse Lounge - Cadastro de Evento</title>
<link rel="stylesheet" type="text/css" href='<c:url value = "./resources/css/styleCadastrarEvento.css"/>'> 
</head>
<body>
	<header>
		<a href="./index"><img alt="Logo"
			src="./resources/images/LogoIconSemBackground.png"></a>

		<div class="search-bar">
			<input type="text" class="search-input"
				placeholder="Digite sua pesquisa..."> <input type="submit"
				class="custom-submit" value="Pesquisar">
		</div>

		<div class="loginIcon">
			<a href="telaLogin.html"> <img alt="Logo"
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
		<a href="meusDados">Meus Dados</a> <a href="cadastrarEvento">Cadastrar Evento</a> <a
			href="cadastrarArtista">Cadastrar Artista</a>
		<form action="login" method="Post" class="deslogar">
			<input type="submit" value="Deslogar" id="botao" name="botao">
		</form>
	</nav>
	</c:if>

	<main>
		<h1>Cadastro de Evento:</h1>
		lembrete: Como faremos os artistas?
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
				<h3>
					<b> <c:out value="${saida }" />
					</b>
				</h3>
			</c:if>
		</div>
		<form action="cadastrarEvento" method="post" class="meusDados">
			<table>
				<tr>
					<td colspan="1"><input type="number" name="codigo" id="codigo"
						placeholder="Codigo"></td>
					<td colspan="1"><input type="submit" name="botao" id="botao"
						value="Buscar"></td>
				</tr>
				<tr>
					<td colspan="2"><input type="text" name="titulo" id="titulo"
						placeholder="Titulo"></td>
				</tr>
				<tr>
					<td colspan="2"><input type="text" name="link" id="link"
						placeholder="Link do Cartaz"></td>
				</tr>
				<tr>
					<td colspan="2"><input type="text" name="genero" id="genero"
						placeholder="Genero"></td>
				</tr>
				<tr>
					<td colspan="1"><label for="horaInicio">Hora de
							Inicio: </label> <input type="time" name="horaInicio" id="horaInicio">
					</td>
					<td colspan="1"><label for="horaInicio">Hora de Fim: </label>
						<input type="time" name="horaFim" id="horaFim"></td>
				</tr>
				<tr>
					<td colspan="1"><label for="data">Data: </label> <input
						type="date" id="data" name="data"></td>
					<td colspan="1"><input type="number" id="valorBase"
						name="valorBase" min="0" step="0.01" placeholder="Valor Base">
					</td>
				</tr>
				<tr>
					<td colspan="1"><input type="submit" value="Atualizar"
						id="botao" name="botao"></td>
					<td colspan="1"><input type="submit" value="Cadastrar"
						id="botao" name="botao"></td>
				</tr>
			</table>
		</form>
	</main>
</body>

</html>