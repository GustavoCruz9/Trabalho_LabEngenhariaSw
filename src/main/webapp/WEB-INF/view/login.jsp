<!-- <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="ISO-8859-1"%> -->
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>Eclipse Lounge - Tela de Login</title>
    <!-- <link rel="stylesheet" type="text/css"
        href='<c:url value = "./resources/css/styleTelaLogin.css"/>'> -->
    <link rel="stylesheet" href="./resources/css/styleTelaLogin.css">
</head>
<body>
	
	
    <main>
    	
		
        <div class="left">
            <img src="./resources/images/LogoSemBackground.png" alt="">
        </div>
        <div class="right">
        	
            <form method="post" action="login"> <!--Lembrar de colocar o action devido-->
                <div class="cardLogin">
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
		
                    <label for="">Usuário</label>
                    <input type="text" name="usuario" id="usuario" value="${acesso.usuario }">
    
                    <label>Senha</label>
                    <input type="password" name="senha" id="senha"  value="${acesso.senha }">
    
                    <input type="submit" value="Entrar" id="botao" name="botao">
    
                    <a href="cadastrarCliente">Não possui login? Cadastre-se</a>
                </div>
            </form>
		</div>

        
    </main>

</body>
</html>   