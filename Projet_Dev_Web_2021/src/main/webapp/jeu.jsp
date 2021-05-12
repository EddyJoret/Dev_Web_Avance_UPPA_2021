

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%--<jsp:useBean class="com.mycompany.projet_dev_web_2021.servlet.CulDeChouette" id="essai" scope="session"/>--%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <script type="text/javascript" src="./js/joueur.js"></script>
    </head>
    <body style="background-image: url('./img/parchemin2.jpg'); background-repeat: no-repeat; background-size: cover; font-family: 'Fredericka the Great', cursive;">
        <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <c:forEach items="${pseudo}" var="sp">
            <h1 style="text-align: center" id="pseudo">${sp}</h1>
        </c:forEach>
            <button onclick="initialisation('<%=(String)request.getAttribute("pseudo")%>')">Jouer</button>
            <button onclick="">Essai</button>
            <p id="listePseudo"></p>
        <!--<p style="font-size: 25px">Joueurs en ligne</p>
        <p id="listejoueur"></p>-->
        
    </body>
</html>
