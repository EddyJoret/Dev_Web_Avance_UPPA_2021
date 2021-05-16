
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%--<jsp:useBean class="com.mycompany.projet_dev_web_2021.servlet.CulDeChouette" id="essai" scope="session"/>--%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Profil de <%=request.getAttribute("pseudo")%></title>
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@10"></script>
        <script type="text/javascript" src="./js/joueur.js"></script>
        <link rel="stylesheet" href="./css/jeu.css">
        <link href="https://fonts.googleapis.com/css2?family=Fredericka+the+Great&display=swap" rel="stylesheet">
        <link href="https://kit-pro.fontawesome.com/releases/v5.15.1/css/pro.min.css" rel="stylesheet">
    </head>
    <body id="bodyjeu" style="background-image: url('./img/parchemin2.jpg'); background-repeat: no-repeat; background-size: cover; font-family: 'Fredericka the Great', cursive; overflow-x: hidden">
        
        <h1 style="text-align: center" id="pseudo">Bienvenue <%=request.getAttribute("pseudo")%></h1>
        
        <div style="text-align: center;" id="divButtonJouer">
            <button onclick="initialisation('<%=(String)request.getAttribute("pseudo")%>')" id="jouerPartie">Jouer</button>
        </div>
        <div style="text-align: center;" id="divButtonStat">
            <button id="statJoueur" onclick="afficheStat()">Statistiques</button>
        </div>
        
        <div id="div-affiche-stat">
            <h2 style="text-align: center">Statistiques</h2>
            <i class="far fa-times" id="close-stat" onclick="closeStat()"style="position: absolute; left: 90%; top: 4%; font-size: 31px"></i>
            <p id="listePseudostat"></p>
        </div>
        
        <div style="text-align: center;" id="divButtonQuitte">
            <button style="display: none" onclick="quitterPartie()" id="quittePartie">Quitter la partie</button>
        </div>
        <div style="text-align: center;" id="divButtonQuitteHote">
            <button style="display: none" onclick="quitterPartieHote()" id="quittePartieHote">Annuler la partie</button>
        </div>
        <div style="text-align: center;" id="divButtonLancerPartie">
            <button style="display: none" onclick="lancerPartie()" id="lancerPartie">Lancer la partie</button>
        </div>
        <div style="text-align: center; display: none;" id="divInputScore">
            <label for="Score" style="font-size: 18px">Score maximum: </label>
            <input type="number" id="scoremax" name="Score" min="1" value="343">
        </div>
        <p id="textJoueurCo">Joueurs connectés : </p>
        <div id="divListePseudo">
            <p id="listePseudo"></p>
        </div>
        
        <div class="dice" id="dice">
            <ol class="die-list even-roll" data-roll="1" id="die-1">
                <li class="die-item" data-side="1">
                  <span class="dot"></span>
                </li>
                <li class="die-item" data-side="2">
                  <span class="dot"></span>
                  <span class="dot"></span>
                </li>
                <li class="die-item" data-side="3">
                  <span class="dot"></span>
                  <span class="dot"></span>
                  <span class="dot"></span>
                </li>
                <li class="die-item" data-side="4">
                  <span class="dot"></span>
                  <span class="dot"></span>
                  <span class="dot"></span>
                  <span class="dot"></span>
                </li>
                <li class="die-item" data-side="5">
                  <span class="dot"></span>
                  <span class="dot"></span>
                  <span class="dot"></span>
                  <span class="dot"></span>
                  <span class="dot"></span>
                </li>
                <li class="die-item" data-side="6">
                  <span class="dot"></span>
                  <span class="dot"></span>
                  <span class="dot"></span>
                  <span class="dot"></span>
                  <span class="dot"></span>
                  <span class="dot"></span>
                </li>
            </ol>
            
            <ol class="die-list odd-roll" data-roll="1" id="die-2">
                <li class="die-item" data-side="1">
                  <span class="dot"></span>
                </li>
                <li class="die-item" data-side="2">
                  <span class="dot"></span>
                  <span class="dot"></span>
                </li>
                <li class="die-item" data-side="3">
                  <span class="dot"></span>
                  <span class="dot"></span>
                  <span class="dot"></span>
                </li>
                <li class="die-item" data-side="4">
                  <span class="dot"></span>
                  <span class="dot"></span>
                  <span class="dot"></span>
                  <span class="dot"></span>
                </li>
                <li class="die-item" data-side="5">
                  <span class="dot"></span>
                  <span class="dot"></span>
                  <span class="dot"></span>
                  <span class="dot"></span>
                  <span class="dot"></span>
                </li>
                <li class="die-item" data-side="6">
                  <span class="dot"></span>
                  <span class="dot"></span>
                  <span class="dot"></span>
                  <span class="dot"></span>
                  <span class="dot"></span>
                  <span class="dot"></span>
                </li>
            </ol>
            
            <ol class="die-list-3 odd-roll" data-roll="1" id="die-3">
                <li class="die-item" data-side="1">
                  <span class="dot"></span>
                </li>
                <li class="die-item" data-side="2">
                  <span class="dot"></span>
                  <span class="dot"></span>
                </li>
                <li class="die-item" data-side="3">
                  <span class="dot"></span>
                  <span class="dot"></span>
                  <span class="dot"></span>
                </li>
                <li class="die-item" data-side="4">
                  <span class="dot"></span>
                  <span class="dot"></span>
                  <span class="dot"></span>
                  <span class="dot"></span>
                </li>
                <li class="die-item" data-side="5">
                  <span class="dot"></span>
                  <span class="dot"></span>
                  <span class="dot"></span>
                  <span class="dot"></span>
                  <span class="dot"></span>
                </li>
                <li class="die-item" data-side="6">
                  <span class="dot"></span>
                  <span class="dot"></span>
                  <span class="dot"></span>
                  <span class="dot"></span>
                  <span class="dot"></span>
                  <span class="dot"></span>
                </li>
            </ol>
        </div>
        <div style="text-align: center;" id="div-button-lancer">
            <button type="button" id="roll-button" onclick="rollDice()">Lancer la chouette</button>
            <button type="button" id="roll-button-die3" onclick="rollDice3()">Lancer le cul</button>
        </div>
        
        <div id="position-joueur">
            <p style="font-size: 18px">Joueurs : </p>
            <table style="width: 90%">
                <tbody>
                    <tr id="essai">
                    </tr>
                </tbody>
            </table>
            <!--<div id="div-pos-joueur">
                <p id="liste-position-joueur"></p>
            </div>-->
        </div>
    </body>
    <script>
        function afficheStat(){
            document.getElementById('div-affiche-stat').style.display = "block";
            document.getElementById("bodyjeu").style.background = "linear-gradient(rgba(0,0,0,0.6), rgba(0,0,0,0.6)), url('./img/parchemin2.jpg') no-repeat";
            document.getElementById("bodyjeu").style.backgroundSize = "cover";
        }
        
        function closeStat(){
            document.getElementById('div-affiche-stat').style.display = "none";
            document.getElementById("bodyjeu").style.background = "url('./img/parchemin2.jpg') no-repeat";
            document.getElementById("bodyjeu").style.backgroundSize = "cover";
        }
    </script>
</html>
