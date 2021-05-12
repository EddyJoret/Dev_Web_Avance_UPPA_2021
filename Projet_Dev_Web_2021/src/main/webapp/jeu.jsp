<%-- 
    Document   : jeu
    Created on : 3 mai 2021, 19:44:27
    Author     : pauline
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <script type="text/javascript" src="./js/joueur.js"></script>
    </head>
    <body style="background-image: url('./img/parchemin2.jpg'); background-repeat: no-repeat; background-size: cover; font-family: 'Fredericka the Great', cursive;">
        <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <c:forEach items="${requestScope.pseudo}" var="sp">
            <h1 style="text-align: center" id="pseudo">${sp}</h1>
        </c:forEach>
            <button onclick="initialisation('<%=(String)request.getAttribute("pseudo")%>')">Jouer</button>
        <!--<p style="font-size: 25px">Joueurs en ligne</p>
        <p id="listejoueur"></p>-->
    </body>
</html>
