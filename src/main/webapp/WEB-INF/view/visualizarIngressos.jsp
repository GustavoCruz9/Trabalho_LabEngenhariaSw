<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>Eclipse Lounge - Meus Ingressos</title>
    <link rel="stylesheet" type="text/css"
        href='<c:url value = "./resources/css/styleVisualizarsIngressos.css"/>'>
</head>

<body>

    <header>
        <a href="index"><img alt="Logo" src="./resources/images/LogoIconSemBackground.png"></a>

        <div class="loginIcon">
            <a href="login">
                <img alt="Logo" src="./resources/images/loginIcon.png" class="userIcon">
                <c:if test="${acesso.permissao eq 1 or acesso.permissao eq 2}">
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
            <a href="meusDados">Meus Dados</a> <a href="cadastrarEvento">Cadastrar Evento</a> <a href="cadastrarArtista">Cadastrar Artista</a>
            <form action="login" method="Post" class="deslogar">
                <input type="submit" value="Deslogar" id="botao" name="botao">
            </form>
        </nav>
    </c:if>
	    <main>
	    
	    	<script>
					function submitForm() {
						document.getElementById("visualizarIngressosForm")
						.submit();
					}
			</script>
	    	
	    	<form id="visualizarIngressosForm" action="visualizarIngressos" method="post">
	        <select name="eventoCodigo" id="eventoCodigo" onchange="submitForm()">
	            <option disabled selected>Selecione um evento</option>
	            <c:forEach var="e" items="${eventos}">
					<option value="${e.codigo}">
						<c:out value="${e.titulo}" />
					</option>
				</c:forEach>
	        </select>
	        
	        <div class="right">
					<div align="center">
	                    <c:if test="${not empty erro }">
	                        <h2>
	                            <b>
	                                <c:out value="${erro}" />
	                            </b>
	                        </h2>
	                    </c:if>
	                </div>
	
	                <div align="center">
	                    <c:if test="${not empty saida }">
	                        <h2>
	                            <b>
	                                <c:out value="${saida}" />
	                            </b>
	                        </h2>
	                    </c:if>
	                </div>
	        
	        
	        <c:if test="${not empty ingressos}">
	        	<c:forEach var="i" items="${ingressos}">
		        <div class="ingresso">
		            <h2><c:out value="${i.evento.titulo}" /></h2>
		            <div class="conteudo">
			                <p>Data do Evento: <span><c:out value="${i.evento.data}" /></span></p>
			                <p>Preço: <span><c:out value="${i.preco}" /></span></p>
			                <p>Tipo: <span><c:out value="${i.tipo}" /></span></p>
			                <p>Setor: <span><c:out value="${i.setor}" /></span></p>
			            <form action="visualizarIngressos" method="post">
			               	<input type="submit" class="cancel-button" value="Cancelar" id="botao" name="botao"> 
			                <input type="hidden" id="codigoIngresso" name="codigoIngresso" value="${i.codigo}" /> 
		                </form>
		            </div>
		        </div>
		        </c:forEach>
			</c:if>
						</form>
	    </main>
</body>

</html>