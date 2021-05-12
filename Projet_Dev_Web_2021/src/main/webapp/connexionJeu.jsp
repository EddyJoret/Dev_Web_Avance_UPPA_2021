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
                           <input id="inputSubmitCo" type="submit" value="Connexion"/>
                       </form>
                    </div>
		</div>
            </div>
	</div>
    </body>
</html>
