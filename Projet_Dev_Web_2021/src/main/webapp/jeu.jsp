

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%--<jsp:useBean class="com.mycompany.projet_dev_web_2021.servlet.CulDeChouette" id="essai" scope="session"/>--%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Profil de <%=request.getAttribute("pseudo")%></title>
        <script type="text/javascript" src="./js/joueur.js"></script>
    </head>
    <body style="background-image: url('./img/parchemin2.jpg'); background-repeat: no-repeat; background-size: cover; font-family: 'Fredericka the Great', cursive;">
        
            <h1 style="text-align: center" id="pseudo"><%=request.getAttribute("pseudo")%></h1>
            <button onclick="initialisation('<%=(String)request.getAttribute("pseudo")%>')" id="jouerPartie">Jouer</button>
            <button style="display: none" onclick="quitterPartie()" id="quittePartie">Quitter la partie</button>
            <button style="display: none" onclick="quitterPartieHote()" id="quittePartieHote">Annuler la partie</button>
            <button style="display: none" onclick="lancerPartie()" id="lancerPartie">Lancer la partie</button>
            <p id="listePseudo"></p>
        <!--<p style="font-size: 25px">Joueurs en ligne</p>
        <p id="listejoueur"></p>-->
        
    </body>
</html>
