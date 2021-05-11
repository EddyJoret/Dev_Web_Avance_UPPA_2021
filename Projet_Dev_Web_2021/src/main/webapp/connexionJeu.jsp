<%-- 
    Document   : connexionJeu
    Created on : 3 mai 2021, 18:34:59
    Author     : pauline
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="./css/style.css">
        <script language="javascript" type="text/javascript">
            var websocket;

            function ouvrirConnexion() {
                websocket = new WebSocket("ws://localhost:8080/Projet_Dev_Web_2021/Joueur");
                websocket.onopen = function(evt) {
                    onOpen(evt);
                };
                websocket.onmessage = function(evt) {
                    onMessage(evt);
                };
                websocket.onerror = function(evt) {
                    onError(evt);
                };
            }
            
            // appelée quand la connexion est ouverte
            function onOpen(evt) {
                alert("Connexion établie");
                var msg = {
                    "Pseudo" : pseudo,
                    "Type" : "Identification"
                };

                console.log("onOpen :");
                console.log(msg);
                // envoie le message au serveur avec le pseudo
                websocket.send(JSON.stringify(msg));
                
            }
            
            // appelée quand le serveur envoie un message : rajoute la donnée de
            // l'événement à la fin du paragraphe qui contient la liste des messages
            function onMessage(evt) {
                liste = document.getElementById("listeMessages");
                console.log('ok message');
                var msg = JSON.parse(evt.data)
                console.log(msg);
                liste.innerHTML = liste.innerHTML + "<br />" + msg.Pseudo;
            }
            
            // appelée quand il y a une erreur
            function onError(evt) {
                alert("Erreur : " + evt.data);
            }

            // initialisation : récupère le pseudo puis ouvre la connexion
            function initialisation() {
                // récupère le pseudo de l'utilisateur
                pseudo = document.getElementById("pseudoco").value;
                console.log(pseudo);
                // ouvre la connexion avec la partie serveur
                ouvrirConnexion();
            }
            
            function envoyerMessage() {
                // récupère le contenu de la zone de texte
                //message = document.getElementById("message").value;
                
                //var msg = "{Pseudo:"+pseudo + ",Type:chat,Contenu:"+message+"}";
                var msg = {
                    "Pseudo" : pseudo,
                    "Type" : "Chat",
                    //"Contenu" : message
                };

                console.log("envoie message :");
                console.log(msg);
                // envoie le message au serveur avec le pseudo
                websocket.send(JSON.stringify(msg));
                // efface le contenu de la zone de texte
                //document.getElementById("message").value = "";
            }
        </script>
    </head>
    <body style="background-image: url('./img/parchemin2.jpg'); background-repeat: no-repeat; background-size: cover; font-family: 'Fredericka the Great', cursive;">
        <div class="container" id="container">
            <div class="form-container log-in-container">
                <h1 style="margin-top: 40px; text-align: center; font-size: 30px">Inscription</h1>
		<form name="registerForm" method="post" action="Joueur?operation=inscription">
                    <input type="text" name="Pseudo" required="true" placeholder="pseudo"/>
                    <input type="text" name="Age" required="true" placeholder="age"/>
                    <input type="text" name="Sexe" required="true" placeholder="sexe"/>
                    <input type="text" name="ville" required="true" placeholder="ville"/>
                    <input type="password" name="mot de passe" required="true" placeholder="mot de passe"/>
                    <input id="inputSubmit" type="submit" value="Inscription" />
                </form>
            </div>
            <div class="overlay-container">
		<div class="overlay">
                    <div class="overlay-panel overlay-right">
                       <h1 style="text-align: center; font-size: 30px">Connexion</h1>
                       <form name="loginForm" method="post" action="Joueur?operation=connexion" id="loginform">
                           <input type="text" name="usernameco" placeholder="pseudo" id="pseudoco"/>
                           <input type="password" name="mdpco" placeholder="mot de passe"/>
                           <input id="inputSubmitCo" type="submit" value="Connexion" onclick="initialisation()"/>
                       </form>
                    </div>
		</div>
            </div>
	</div>
    </body>
</html>
