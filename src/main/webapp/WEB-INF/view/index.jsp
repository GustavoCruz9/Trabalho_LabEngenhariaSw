<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<title>Eclipse Lounge - Tela Inicial</title>
<link rel="stylesheet" type="text/css"
	href='<c:url value = "./resources/css/styleIndex.css"/>'>
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
			<a href="#"> <img alt="Logo"
				src="./resources/images/loginIcon.png" class="userIcon">
				<h3>Login</h3>
			</a>
		</div>
	</header>

	<main>



		<form action="index" method="get">
			<c:forEach var="e" items="${eventos}">
				<a href="comprarIngresso" class="grid">
					<div class="cardEvento">
						<img src ="<c:out value="${e.linkImagem}"></c:out>"
							alt="Bruno Mars">
						<div class="card-info">
							<h2>
								<c:out value="${e.titulo}"></c:out>
							</h2>
							<p>
								<c:out value="${e.data}"></c:out>
							</p>
						</div>
					</div>
					<input type="hidden" name="codEvento" id="codEvento" value="${e.codigo}">
				</a>
			</c:forEach>
		</form>
	</main>

</body>

</html>