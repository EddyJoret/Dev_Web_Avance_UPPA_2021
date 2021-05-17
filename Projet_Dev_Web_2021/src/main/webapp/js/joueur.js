//Variable WebSocket
var websocket;

//Variable Joueur
var Pseudo;
var Position;
var Score;

//Variables partie
var Seuil = 343;
var attenteReponse = [];
var partieJoueur = [];
var nbInvit = 5;
var boolPartie = false;
var boolHote = false;
var boolPartieFini = false;
var CHV = false;
var Suite;

//Variables dés
var dice1;
var dice2;
var dice3;
var desAvt = [];
var desMnt = [];

function ouvrirConnexion() {
    websocket = new WebSocket("ws://localhost:8080/Projet_Dev_Web_2021/CulDeChouette");
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
        "Pseudo" : Pseudo,
        "Type" : "Identification"
    };
    // envoie le message au serveur avec le pseudo
    websocket.send(JSON.stringify(msg));

}

// appelée quand le serveur envoie un message : rajoute la donnée de
// l'événement à la fin du paragraphe qui contient la liste des messages
function onMessage(evt) {
    var msg = JSON.parse(evt.data);
    console.log("Message reçu: ");
    console.log(msg);
    
    if(msg.Type === "ListePS"){
        majListePS(msg.Pseudos);
    }
    
    if(msg.Type === "Invitation"){
        if(!boolPartie){
            Swal.fire({
                title: 'Invitation à jouer de ' + msg.Pseudo,
                showDenyButton: true,
                confirmButtonText: `Rejoindre`,
                denyButtonText: `Ne pas rejoindre`
            }).then((result) => {
                if(result.isConfirmed){
                    confirmationPartie(msg.Pseudo);
                }else if(result.isDenied){
                   refusPartie(msg.Pseudo); 
                }
            });
        }else{
            refusPartie(msg.Pseudo);
        }
    }
    
    if(msg.Type === "Reponse"){
        reponse(msg.Pseudo, msg.Contenu);
    }
    
    if(msg.Type === "Quitte"){
        quitte(msg.Pseudo);
    }
    
    if(msg.Type === "QuitteHote"){
        document.getElementById("quittePartie").style.display = "none";
        partieJoueur.splice(0,partieJoueur.length);
        boolPartie = false;
    }
    
    if(msg.Type === "ComplementPartie"){
        var obj = {
            "Pseudo" : msg.Pseudo,
            "Position" : 0
        };
        partieJoueur.push(obj);
    }
    
    if(msg.Type === "LancerPartie"){
        dice1 = document.getElementById("die-1");
        dice2 = document.getElementById("die-2");
        dice3 = document.getElementById("die-3");
        if(!boolHote){
            for(var i = 0; i < msg.Pseudos.length; i++){
                var j = 0;
                var existe = false;
                while(j < partieJoueur.length && !existe){
                    if(partieJoueur[j].Pseudo === msg.Pseudos[i].Pseudo){
                        partieJoueur[j].Position = msg.Pseudos[i].Position;
                        existe = true;
                    }
                    j++;
                }
                if(!existe && msg.Pseudos[i].Pseudo === Pseudo){
                    Position = msg.Pseudos[i].Position;
                    Score = 0;
                    if(parseInt(msg.Seuil) !== 0){
                        Seuil = parseInt(msg.Seuil);
                    }
                    var obj = {
                        "Pseudo" : Pseudo,
                        "Position" : Position
                    };
                    partieJoueur.push(obj);
                }
            }
            document.getElementById("quittePartie").style.display = "none";
            document.getElementById("divListePseudo").style.display = "none";
            document.getElementById("textJoueurCo").style.display = "none";
        }else{
            document.getElementById("roll-button").style.display = "block";
            document.getElementById("dice").style.display = "grid";
            document.getElementById("divButtonStat").style.top = "50%";
            
        }
        document.getElementById("position-joueur").style.display = "inline-block";
        affichageListeJoueur();
    }
    
    if(msg.Type === "Chouette"){
        document.getElementById("dice").style.display = "grid";
        document.getElementById("die-3").style.display = "none";
        rollDiceSpec();
        diceNumber(msg.Des1, msg.Des2);
        desAvt.push(parseInt(msg.Des1));
        desAvt.push(parseInt(msg.Des2));
    }
    
    if(msg.Type === "Cu"){
        document.getElementById("die-3").style.display = "grid";
        rollDice3Spec();
        dice3Number(msg.Des3);
        desAvt.push(parseInt(msg.Des3));
    }
    
    if(msg.Type === "PassageMain"){
        if(msg.Num !== Position){
            document.getElementById("dice").style.display = "none";
            desAvt.splice(0,desAvt.length);
        }else{
            document.getElementById("roll-button").style.display = "block";
            document.getElementById("divButtonStat").style.top = "50%";
        }
    }
    
    if(msg.Type === "MajScore"){
        majScore(msg.Pseudo, msg.Score);
    }
    
    if(msg.Type === "ChouetteVelute"){
        CHV = true;
        Swal.fire({
            title: 'CHOUETTE VELUTE !!!',
            confirmButtonText: `Pas mou le caillou!`
        }).then((result) => {
            if(result.isConfirmed){
                if(CHV){
                    var msgCHV = {
                        "Pseudo" : Pseudo,
                        "Type" : "ChouetteVeluteGagne",
                        "Destinataire" : ""
                    };
                    for(var i = 0; i < partieJoueur.length; i++){
                        if(partieJoueur[i].Pseudo !== Pseudo){
                            msgCHV.Destinataire = partieJoueur[i].Pseudo;
                            websocket.send(JSON.stringify(msgCHV));
                        }
                    }
                    CHV = false;
                    envoieScore(parseInt(msg.Points));
                    Score = Score + parseInt(msg.Points);
                    majScore(Pseudo, msg.Points);
                }else{
                    chouetteVelutePerdu();
                }
            }
        });
    }
    
    if(msg.Type === "ChouetteVeluteGagne"){
        CHV = false;
    }
    
    if(msg.Type === "Suite"){
        Suite = 0;
        Swal.fire({
            title: 'SUITE !!!',
            confirmButtonText: `Grelotte ça picotte!`
        }).then((result) => {
            if(result.isConfirmed){
                if(Suite >= (partieJoueur.length -1)){
                    var msgCHV = {
                        "Pseudo" : Pseudo,
                        "Type" : "SuiteGagne",
                        "Destinataires" : partieJoueur
                    };
                    websocket.send(JSON.stringify(msgCHV));
                    Suite = 0;
                }else{
                    SuitePerdu();
                    Suite = 0;
                }
            }
        });
    }
    
    if(msg.Type === "SuiteGagne"){
        Suite ++;
    }
    
    if(msg.Type === "VictoirePartie"){
        Swal.fire({
            title: 'Partie terminée',
            text: 'Le gagnant est' + msg.Pseudo,
            confirmButtonText: `Good Game`
        }).then((result) => {
            Position = 0;
            Score = 0;

            Seuil = 343;
            partieJoueur.splice(0, partieJoueur.length);
            nbInvit = 5;
            boolPartie = false;
            boolHote = false;
            boolPartieFini = false;
            CHV = false;

            desAvt.splice(0, desAvt.length);
            desMnt.splice(0, desMnt.length);
            
            document.getElementById("divListePseudo").style.display = "block";
            document.getElementById("textJoueurCo").style.display = "block";
            document.getElementById("dice").style.display = "none";
            document.getElementById("essai").innerHTML = "";
            document.getElementById("position-joueur").style.display = "none";
            
        });
    }
}

// appelée quand il y a une erreur
function onError(evt) {
    alert("Erreur : " + evt.data);
}

// initialisation : récupère le pseudo puis ouvre la connexion
function initialisation(pseudo) {
    // ouvre la connexion avec la partie serveur
    Pseudo = pseudo;
    ouvrirConnexion();
    document.getElementById("jouerPartie").style.display = "none";
    document.getElementById("divButtonStat").style.display = "block";
}

function verifInvit(pseudo){
    if(!boolPartie || boolHote){
        boolHote = true;
        boolPartie = true;
        invitationJoueur(pseudo); 
    }else{
        alert("Vous êtes déjà dans une partie\nPour inviter des gens, veuillez quitter la partie.");
    }
}

function invitationJoueur(pseudo){
    var i = 0;
    var existe = false;
    document.getElementById("quittePartieHote").style.display = "block";
    document.getElementById("lancerPartie").style.display = "block";
    document.getElementById("divInputScore").style.display = "block";
    
    while(i < attenteReponse.length && !existe){
        if(attenteReponse[i].Pseudo === pseudo){
            existe = true;
        }
        i++;
    }
    
    i=0;
    while(i < partieJoueur.length && !existe){
        if(partieJoueur[i].Pseudo === pseudo){
            existe = true;
        }
        i++;
    }
    
    if(!existe){
        if(nbInvit > 0){
            nbInvit--;
            var msg = {
                "Pseudo" : Pseudo,
                "Type" : "Invitation",
                "Destinataire" : pseudo
            };
            var objet = {
                "Pseudo" : pseudo,
                "Position" : 6-nbInvit
            };
            attenteReponse.push(objet);
            websocket.send(JSON.stringify(msg));
        }else{
            alert("Nombre maximum d'invitations envoyées atteint");
        } 
    }else{
        alert("Invitation déjà envoyée pour ce joueur");
    }    
}

function reponse(pseudo, contenu){
    var i = 0;
    var existe = false;
    while(i < attenteReponse.length && !existe){
        if(attenteReponse[i].Pseudo === pseudo){
            existe = true;
        }else{
          i++;  
        }
    }

    if(contenu === "Non"){
        nbInvit++;
        attenteReponse.splice(i,1);
        if(attenteReponse.length === 0 && partieJoueur.length === 0){
            document.getElementById("quittePartieHote").style.display = "none";
            document.getElementById("lancerPartie").style.display = "none";
            document.getElementById("divInputScore").style.display = "none";
            boolPartie = false;
            boolHote = false;
        }
    }else if(existe){
        partieJoueur.push(attenteReponse.splice(i,1)[0]);
        for(i = 0; i < partieJoueur.length; i++){
            if(pseudo !== partieJoueur[i].Pseudo){
                var msg1 = {
                    "Pseudo" : pseudo,
                    "Type" : "ComplementPartie",
                    "Destinataire" : partieJoueur[i].Pseudo
                };
                var msg2 = {
                    "Pseudo" : partieJoueur[i].Pseudo,
                    "Type" : "ComplementPartie",
                    "Destinataire" : pseudo
                };
                
                websocket.send(JSON.stringify(msg1));
                websocket.send(JSON.stringify(msg2));
            }
        }
    }
}

function quitte(pseudo){
    var i = 0;
    var existe = false;
    var joueur;

    while(i < partieJoueur.length && !existe){
        if(partieJoueur[i].Pseudo === pseudo){
            existe = true;
            joueur = partieJoueur.splice(i,1);
            if(boolHote){
               nbInvit++; 
            }
        }else{
          i++;  
        }
    }
    if(boolHote){
        if(partieJoueur.length === 0 && attenteReponse.length === 0){
            boolHote = false;
            boolPartie = false;
            document.getElementById("quittePartieHote").style.display = "none";
            document.getElementById("lancerPartie").style.display = "none";
            document.getElementById("divInputScore").style.display = "none";
        }
        if(partieJoueur.length !== 0){
            for(let i = 0; i < partieJoueur.length; i++){
                if(partieJoueur[i].Position > joueur[0].Position){
                    partieJoueur[i].Position = partieJoueur[i].Position - 1;
                } 
            }
        }
    }
}

function ajoutPartieJoueur(pseudo){
    var objet = {
        "Pseudo" : pseudo,
        "Position" : 0
    };
    partieJoueur.push(objet);
}

function refusPartie(destinataire){
    var rep = {
        "Pseudo" : Pseudo,
        "Type" : "Reponse",
        "Destinataire" : destinataire,
        "Contenu" : "Non"
    };
    websocket.send(JSON.stringify(rep));
}

function confirmationPartie(pseudo){
    var rep = {
        "Pseudo" : Pseudo,
        "Type" : "Reponse",
        "Destinataire" : pseudo,
        "Contenu" : "Oui"
    };
    boolPartie = true;
    ajoutPartieJoueur(pseudo);
    document.getElementById("quittePartie").style.display = "block";
    websocket.send(JSON.stringify(rep));
}

function majListePS(Pseudos){
    liste = document.getElementById("listePseudo");
    var listestat = document.getElementById("listePseudostat");
    liste.innerHTML = "";
    listestat.innerHTML = "";
    for(let i = 0; i < Pseudos.length; i++){
        listestat.innerHTML = listestat.innerHTML + "<br/>"+ "<h3>" + Pseudos[i] + " : "+ "</h3>" + "Nombre de Partie joués: " + "<span id=\"nbPartieJouer\">0</span>" + "<br/>"
                + "Nombre de victoires: " + "<span id=\"nbVict\">0</span>" + "<br/>" + "Nombre moyen de victoires: " + "<span id=\"nbVictMoy\">0</span>" + "<br/>" 
                + "Score moyen: " + "<span id=\"scoreMoyJoueur\">0</span>" + "<br/>" + "Moyenne suites gagnées: " + "<span id=\moySuiteG\">0</span>" + "<br/>" + "Moyenne chouettes velutes perdues: " + "<span id=\"moyChouettePerd\">0</span>" + "<br/>";
        if(Pseudos[i] !== Pseudo){
            liste.innerHTML = liste.innerHTML + "<br />"+ "<a id=\"nomJoueur\" href=\"#\" onclick=\"verifInvit('" +Pseudos[i]+ "')\">" + Pseudos[i] + "</a>" + "<br />"; 
        }
    }
}

function quitterPartie(){
    var message = {
        "Pseudo" : Pseudo,
        "Type" : "Quitte",
        "Destinataire" : ""
    };
    document.getElementById("quittePartie").style.display = "none";
    for(let i = 0; i < partieJoueur.length; i++){
        message.Destinataire = partieJoueur[i].Pseudo;
        websocket.send(JSON.stringify(message));
    }
    partieJoueur.splice(0, partieJoueur.length);
    boolPartie = false;
}

function quitterPartieHote(){
    if(attenteReponse.length === 0){
        var message = {
            "Pseudo" : Pseudo,
            "Type" : "QuitteHote",
            "Destinataire" : ""
        };
        document.getElementById("quittePartieHote").style.display = "none";
        document.getElementById("lancerPartie").style.display = "none";
        document.getElementById("divInputScore").style.display = "none";
        for(let i = 0; i < partieJoueur.length; i++){
            message.Destinataire = partieJoueur[i].Pseudo;
            websocket.send(JSON.stringify(message));
        }
        partieJoueur.splice(0, partieJoueur.length);
        boolPartie = false;
        boolHote = false;
        nbInvit = 5; 
    }else{
        alert("Il reste au moins un joueur qui n'a pas répondu à votre invitation");
    }
    
}

function lancerPartie(){
    if(attenteReponse.length === 0){
        Position = 1;
        Score = 0;
        if(document.getElementById("scoremax").value !== 0 && document.getElementById("scoremax").value !== null){
            Seuil = document.getElementById("scoremax").value;
        }
        var joueurs = partieJoueur;
        var obj = {
            "Pseudo" : Pseudo,
            "Position" : Position
        };
        joueurs.push(obj);
        var message = {
            "Pseudo" : Pseudo,
            "Type" : "LancerPartie",
            "Pseudos" : joueurs,
            "Seuil": document.getElementById("scoremax").value
        };

        document.getElementById("quittePartieHote").style.display = "none";
        document.getElementById("lancerPartie").style.display = "none";
        document.getElementById("divInputScore").style.display = "none";
        document.getElementById("divListePseudo").style.display = "none";
        document.getElementById("textJoueurCo").style.display = "none";
        
        websocket.send(JSON.stringify(message));
    }else{
        alert("Impossible de lancer.\nIl reste au moins un joueur qui n'a pas répondu à votre invitation");
    }
}

function affichageListeJoueur(){
    var pos = document.getElementById("essai");
    for(let i = 0; i < partieJoueur.length; i++){
        pos.innerHTML = pos.innerHTML + "<td id=\"anchor-pos\">"+"<a href=\"#\" id=\"listepos\">" + partieJoueur[i].Pseudo + "<br/>" + "Joueur " + partieJoueur[i].Position + "<br/>" + "Score: " + "<span id='" + i + "'>0</span>" + "</a>" + "</td>" + "<br/>";
    }
}


/*---------------------Fonctions dés---------------------*/
//Faire rouler les deux premiers dés pour le joueur
function rollDice() {
    const dice = [...document.querySelectorAll(".die-list")];
    dice.forEach(die => {
        toggleClasses(die);
    });
    document.getElementById("roll-button").style.display = "none";
    document.getElementById("roll-button-die3").style.display = "block";
    diceRandom();
}

//Faire rouler le 3ème dé pour le joueur
function rollDice3() {
    const dice = [...document.querySelectorAll(".die-list-3")];
    dice.forEach(die => {
        toggleClasses(die);
    });
    document.getElementById("roll-button-die3").style.display = "none";
    dice3Random();
    incScore();
    setTimeout(function(){
        if(!boolPartieFini){
            document.getElementById("dice").style.display = "none";
            passageMain();  
        }
    },3000);
}

//Faire rouler les deux premiers dés pour le spectateur
function rollDiceSpec() {
    const dice = [...document.querySelectorAll(".die-list")];
    dice.forEach(die => {
        toggleClasses(die);
    });
}

//Faire rouler le 3ème dé pour le spectateur
function rollDice3Spec() {
    const dice = [...document.querySelectorAll(".die-list-3")];
    dice.forEach(die => {
        toggleClasses(die);
    });
}

function toggleClasses(die) {
    die.classList.toggle("odd-roll");
    die.classList.toggle("even-roll");
}

function getRandomNumber(min, max) {
    min = Math.ceil(min);
    max = Math.floor(max);
    return Math.floor(Math.random() * (max - min + 1)) + min;
}

//Mettre un nombre random pour les 2 premiers dès
function diceRandom(){
    dice1.dataset.roll = getRandomNumber(1, 6);
    dice2.dataset.roll = getRandomNumber(1, 6);
    desMnt.push(parseInt(dice1.dataset.roll));
    desMnt.push(parseInt(dice2.dataset.roll));
    var msg = {
      "Pseudo" : Pseudo,
      "Type" : "Chouette",
      "Destinataire" : "",
      "Des1" : dice1.dataset.roll,
      "Des2" : dice2.dataset.roll
    };

    for(var i = 0; i < partieJoueur.length; i++){
      if(partieJoueur[i].Pseudo !== Pseudo){
          msg.Destinataire = partieJoueur[i].Pseudo;
          websocket.send(JSON.stringify(msg));
      }
    }
}

//Mettre un nombre random pour le 3me dès
function dice3Random(){
    dice3.dataset.roll = getRandomNumber(1, 6);
    desMnt.push(parseInt(dice3.dataset.roll));
    var msg = {
      "Pseudo" : Pseudo,
      "Type" : "Cu",
      "Destinataire" : "",
      "Des3" : dice3.dataset.roll
    };
  
    for(var i = 0; i < partieJoueur.length; i++){
      if(partieJoueur[i].Pseudo !== Pseudo){
          msg.Destinataire = partieJoueur[i].Pseudo;
          websocket.send(JSON.stringify(msg));
      }
    }
}

//Mettre un nombre défini pour les 2 premiers dès
function diceNumber(des1, des2){
    dice1.dataset.roll = des1;
    dice2.dataset.roll = des2;
}

//Mettre un nombre défini pour le 3me dès
function dice3Number(des3){
    dice3.dataset.roll = des3;
}

function incScore(){
    console.log("   desAvt:");
    console.log(desAvt);
    console.log("   desMnt:");
    console.log(desMnt);
    
    var pts = getScore();
    
    if(pts === -1){
        pts = 0;
        chouetteVelute();
    }else if(pts === -2){
        pts = 0;
        suite();
    }else{
        Score = Score + pts;
        var i = 0;
        var existe = false;
        while(i < partieJoueur.length && !existe){
            if(partieJoueur[i].Pseudo === Pseudo){
                existe = true;
                document.getElementById(i.toString()).innerHTML = Score;
            }else{
              i++;  
            }
        }
    }
    
    envoieScore(pts);

    desAvt.splice(0, desAvt.length);
    desMnt.splice(0, desMnt.length);
    
    if(Score >= Seuil){
        var msgG = {
            "Pseudo" : Pseudo,
            "Type" : "VictoirePartie",
            "Destinataires" : partieJoueur
        };
        websocket.send(JSON.stringify(msgG));
        boolPartieFini = true;
    }
}

function envoieScore(pts){
    var msgScore = {
        "Pseudo" : Pseudo,
        "Type" : "MajScore",
        "Destinataires" : partieJoueur,
        "Score" : pts
    };
    
    websocket.send(JSON.stringify(msgScore));
}

function majScore(pseudo, score){
    var i = 0;
    var existe = false;
    while(i < partieJoueur.length && !existe){
        if(partieJoueur[i].Pseudo === pseudo){
            existe = true;
            document.getElementById(i.toString()).innerHTML = parseInt(document.getElementById(i.toString()).innerHTML) + score;
        }else{
            i++;
        }
    }
}

function getScore(){
    if(desMnt[0] === desMnt[1] && ((desMnt[0] + desMnt[1]) === desMnt[2])){
        console.log("CHOUETTE VELUTE");
        return -1;
    }else if(desMnt[0] === desMnt[1] && desMnt[1] === desMnt[2]){
        console.log("CUL DE CHOUETTE");
        return culDeChouette();
    }else if(desMnt[0] === desMnt[1]){
        console.log("CHOUETTE");
        return Math.pow(desMnt[0],2);
    }else if((desMnt[0] + desMnt[1]) === desMnt[2]){
        console.log("VELUTE");
        return Math.pow(desMnt[2],2);
    }else if(desAvt.length !== 0){
        if(getSuite()){
            console.log("SUITE");
            return -2; 
        }else{
            console.log("RIEN");
            return 0;
        }
    }else{
        return 0;
    }
}

function culDeChouette(){
    if(desMnt[0] === 1){
        return 50;
    }else if(desMnt[0] === 2){
        return 60;
    }else if(desMnt[0] === 3){
        return 70;
    }else if(desMnt[0] === 4){
        return 80;
    }else if(desMnt[0] === 5){
        return 90;
    }else if(desMnt[0] === 6){
        return 100;
    }
}

function chouetteVelute(){
    var msg = {
        "Type" : "ChouetteVelute",
        "Destinataire" : "",
        "Points" : Math.pow(desMnt[2],2)
    };
    
    for(var i = 0; i < partieJoueur.length; i++){
        msg.Destinataire = partieJoueur[i].Pseudo;
        websocket.send(JSON.stringify(msg));
    }
}

function chouetteVelutePerdu(){
    Swal.fire({
        title: 'Dommage Perdu',
        confirmButtonText: `Compris...`
    });
}

function suite(){
    var msg = {
        "Type" : "Suite",
        "Destinataire" : ""
    };
    
    for(var i = 0; i < partieJoueur.length; i++){
        msg.Destinataire = partieJoueur[i].Pseudo;
        websocket.send(JSON.stringify(msg));
    }
}

function SuitePerdu(){
    Swal.fire({
        title: 'Dommage Perdu',
        confirmButtonText: `Compris...`
    });
    envoieScore(-10);
    Score = Score - 10;
    majScore(Pseudo, -10);
}

function getSuite(){
    return suite1() || suite2();
}

function suite1(){
    var un = false;
    var deux = false;
    var trois = false;
    var derTrois = false;
    var derQuatre = false;
    var derCinq = false;

    for(var i = 0; i < desAvt.length; i++){
        if(desAvt[i] === 1){
            un = true;
        }
        if(desAvt[i] === 2){
            deux = true;
        }
        if(desAvt[i] === 3){
            trois = true;
        }
    }

    for(var i = 0; i < desMnt.length; i++){
        if(desMnt[i] === 3){
            derTrois = true;
        }
        if(desMnt[i] === 4){
            derQuatre = true;
        }
        if(desMnt[i] === 5){
            derCinq = true;
        }
    }

    return (un && deux && trois) && (derTrois && derQuatre && derCinq);
}

function suite2(){
    var deux = false;
    var trois = false;
    var quatre = false;
    var derQuatre = false;
    var derCinq = false;
    var derSix = false;

    for(var i = 0; i < desAvt.length; i++){
        if(desAvt[i] === 2){
            deux = true;
        }
        if(desAvt[i] === 3){
            trois = true;
        }
        if(desAvt[i] === 4){
            quatre = true;
        }
    }

    for(var i = 0; i < desMnt.length; i++){
        if(desMnt[i] === 4){
            derQuatre = true;
        }
        if(desMnt[i] === 5){
            derCinq = true;
        }
        if(desMnt[i] === 6){
            derSix = true;
        }
    }

    return (deux && trois && quatre) && (derQuatre && derCinq && derSix);
}

function passageMain(){
    var newPos = Position + 1;
    var msg = {
         "Type" : "PassageMain",
         "Destinataire" : "",
         "Num" : 0
    };
    if(newPos <= partieJoueur.length){
        msg.Num = newPos;
    }else{
        msg.Num = newPos % partieJoueur.length;
    }
    
    for(var i = 0; i < partieJoueur.length; i++){
      if(partieJoueur[i].Pseudo !== Pseudo){
          msg.Destinataire = partieJoueur[i].Pseudo;
          websocket.send(JSON.stringify(msg));
      }
    }
}