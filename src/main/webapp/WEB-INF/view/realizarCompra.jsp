<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<title>Eclipse Lounge - Meus Ingressos</title>
<link rel="stylesheet" type="text/css"
	href='<c:url value = "./resources/css/styleRealizarCompra.css"/>'>
</head>

<body>

	<header>
		<a href="./index"><img alt="Logo"
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

	<main>
		<div align="center">
			<c:if test="${not empty erro }">
				<h2>
					<b><c:out value="${erro}" /></b>
				</h2>
			</c:if>
		</div>
		
		<div align="center">
			<c:if test="${not empty saida }">
				<h2>
					<b><c:out value="${saida}" /></b>
				</h2>
			</c:if>
		</div>

		<div class="left">
			<img src="<c:out value="${evento.linkImagem}"></c:out>"
				alt="<c:out value="${evento.titulo}"></c:out>">
		</div>

		<div class="right">
			<form action="realizarCompra" method="get">
				<h1>${evento.titulo}</h1>
				<div class="conteudo">
					<p>
						Gênero: <span>${evento.genero}</span>
					</p>
					<p>
						Artista(s): <br>
						<c:forEach var="a" items="${evento.artistas}">
							<span>${a.nome}</span>
							<br>
						</c:forEach>
					</p>
					<p>
						Hora de Inicio: <span>${evento.horaInicio}</span>
					</p>
					<p>
						Hora de Fim: <span>${evento.horaFim}</span>
					</p>
					<p>
						Data: <span>${evento.data}</span>
					</p>
					<input type="hidden" id="codEvento" name="codEvento"
						value="${evento.codigo}" />
			</form>

			<script>
				function submitForm() {
					document.getElementById("realizarCompraForm").submit();
				}
			</script>

			<form id="realizarCompraForm" action="realizarCompra" method="post">
				<select name="tipo" id="tipo" onchange="submitForm()">
					<option disabled ${ingresso.tipo==null?'selected' : ''}>Tipo</option>
					<option value="inteira"
						${ingresso.tipo == 'inteira' ? 'selected' : ''}>Inteira</option>
					<option value="meia" ${ingresso.tipo == 'meia' ? 'selected' : ''}>Meia</option>
				</select> <select name="setor" id="setor" onchange="submitForm()">
					<option disabled ${ingresso.setor==null?'selected' : ''}>Setor</option>
					<option value="Pista"
						${ingresso.setor == 'Pista' ? 'selected' : ''}>Pista</option>
					<option value="FrontStage"
						${ingresso.setor == 'FrontStage' ? 'selected' : ''}>FrontStage</option>
					<option value="Camarote"
						${ingresso.setor == 'Camarote' ? 'selected' : ''}>Camarote</option>
				</select> <input type="hidden" id="codEvento" name="codEvento"
					value="${param.codEvento}" /> <input type="number"
					name="quantidade" id="quantidade" min="1" step="1" value="1">

				<br> <br>

				<c:if test="${not empty ingresso.preco}">
					<p>
						Valor do Ingresso:
						<c:out value="${ingresso.preco}"></c:out>
					</p>
				</c:if>
				<input type="submit" id="botao" name="botao" value="Adicionar ao carrinho" /> 
				<input type="submit" id="botao" name="botao" value="Finalizar compra" />
				<input type="submit" id="botao" name="botao" value="Limpar carrinho" />
			</form>
		</div>
		</div>
	</main>
	<div>
		<c:if test="${not empty ingressos}">
			<form action="realizarCompra" method="post">
				<table>
					<thead>
						<tr>
							<th>Ingressos</th>
						</tr>
						<th>Setor</th>
						<th>Tipo</th>
						<th>Preço</th>
						<tr>
					</thead>
					<tbody>
						<c:forEach var="i" items="${ingressos}">
							<tr>
								<td><c:out value="${i.setor}"></c:out></td>
								<td><c:out value="${i.tipo}"></c:out></td>
								<td><c:out value="${i.preco}"></c:out></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</form>
		</c:if>
	</div>
</body>

</html>