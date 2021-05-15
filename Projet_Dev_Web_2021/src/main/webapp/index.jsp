<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="preconnect" href="https://fonts.gstatic.com">
        <link rel="stylesheet" href="./css/style.css">
        <link href="https://fonts.googleapis.com/css2?family=Fredericka+the+Great&display=swap" rel="stylesheet">
        <link href="https://kit-pro.fontawesome.com/releases/v5.15.1/css/pro.min.css" rel="stylesheet">
    </head>
    <body style="background-image: url('./img/parchemin2.jpg'); background-repeat: no-repeat; background-size: cover; font-family: 'Fredericka the Great', cursive; overflow: hidden">
        <h1 style="text-align: center; font-size: 50px; margin-top: -20px">Le jeu du cul de chouette</h1>
        <div style="text-align: center; margin-top: 5%">
            <a href="connexionJeu.jsp" style="font-size: 30px" target="_blank" id="jouermenuprinci">Jouer</a>  
        </div>
        <div style="text-align: center; margin-top: 5%">
            <a href="#" style="font-size: 30px" id="reglejeu" onclick="ouvrirRegles()">Règles du jeu</a>
        </div>
        <div class="img-container" style="text-align: center; margin-top: 80px">
            <img src="./img/dice.png" style=""/>
        </div>
        <div id="div-regles">
            <h3 style="text-align: center">Règles du jeu</h3>
            <i class="far fa-times" id="close-regles" onclick="closeRegles()"style="position: absolute; left: 90%; top: 4%; font-size: 31px"></i>
            <div style="width: 90%; margin-left: 10px">
                <p>Les dés se lancent en 2 fois : d'abord 2 dés puis le troisième.</p>
                <p> Le jet des 2 premiers dés est appelé la chouette et le troisième dé est appelé le cul.</p>
                <p style="text-decoration: underline">Combinaisons sans interactions :</p>
                <p> - La velute : la somme des dés de la chouette donne la valeur du cul. Exemple : (1,3) avec 4. Les points marqués par le joueur sont le carré de la velute. Dans l'exemple précédent, la velute est de 4, donc 16 points sont marqués.</p>
                <p> - La chouette : les deux dés de la chouette sont identiques. Exemple : (5,5). Les points marqués sont le carré de cette valeur identique, soit 25 pour l'exemple.</p>
                <p> - Le cul de chouette : les trois dés sont identiques. 50 points sont marqués pour un cul de chouette de 1, 60 pour un de 2, 70 pour un de 3, 80 pour un de 4, 90 pour un de 5 et 100 pour un de 6.</p>
                <p style="text-decoration: underline">Combinaisons avec interaction : </p>
                <p> - La suite : toute combinaison de 3 dés donnant 3 valeurs consécutives (1,2,3), (3,4,5) ... En cas de suite, chaque joueur doit crier "Grelotte ça picote !". Le dernier joueur à le faire perd 10 points. </p>
                <p> - La chouette velute : il s'agit d'une chouette qui avec le cul donne une velute, ce qui est le cas pour les 3 combinaisons (1,1) avec 2, (2,2) avec 4 et (3,3) avec 6. Dans ce cas, chaque joueur doit crier "Pas mou le caillou !". Le premier à crier gagne les points de la velute.</p>  
            </div>
            
        </div>
    </body>
    <script>
        
        function ouvrirRegles(){
            console.log('ok');
            document.getElementById('div-regles').style.display = "block";
        }
        
        function closeRegles(){
            document.getElementById('div-regles').style.display = "none";
        }
        
    </script>
</html>
