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
    </head>
    <body>
        <form name="registerForm" method="post" action="Joueur?operation=connexion">
               Pseudo: <input type="text" name="Pseudo" required="true"/> <br/>
               Age: <input type="text" name="Age" required="true"/> <br/>
               Sexe: <input type="text" name="Sexe" required="true"/> <br/>
               Ville: <input type="text" name="ville" required="true"/> <br>
               Mdp: <input type="password" name="mot de passe" required="true"/> <br>
               <input type="submit" value="Inscription" />
        </form>
        <br/>
        <br/>
        <form name="loginForm" method="post" action="servletPersonne">
               Nom: <input type="text" name="username"/> <br/>
               MDP: <input type="password" name="mot de passe"/> <br/>
               <input type="submit" value="Connexion" />
        </form>
    </body>
</html>
