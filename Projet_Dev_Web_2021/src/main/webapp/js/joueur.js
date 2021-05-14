//Variable WebSocket
var websocket;

//Variable Joueur
var Pseudo;

//Variables partie
var attenteReponse = [];
var partieJoueur = [];
var nbInvit = 5;
var boolPartie = false;
var boolHote = false;

//Variables dés
var cptDice = 0;
var ptsJoueur = 0;
var dice1;
var dice2;
var dice3;

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
            if(confirm(msg.Pseudo + " t'invites ")){
                confirmationPartie(msg.Pseudo);
            } else {
                refusPartie(msg.Pseudo);
            }
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
        console.log(partieJoueur);
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
                    var obj = {
                        "Pseudo" : Pseudo,
                        "Position" : msg.Pseudos[i].Position
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
            
        }
        document.getElementById("position-joueur").style.display = "inline-block";
        affichageListeJoueur();
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
    liste.innerHTML = "";
    for(let i = 0; i < Pseudos.length; i++){
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
        var joueurs = partieJoueur;
        var obj = {
            "Pseudo" : Pseudo,
            "Position" : 1
        };
        joueurs.push(obj);
        var message = {
            "Pseudo" : Pseudo,
            "Type" : "LancerPartie",
            "Pseudos" : joueurs
        };
        console.log(message);

        document.getElementById("quittePartieHote").style.display = "none";
        document.getElementById("lancerPartie").style.display = "none";
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
  console.log(dice1.dataset.roll);
  console.log(dice2.dataset.roll);
}

//Mettre un nombre random pour le 3me dès
function dice3Random(){
    dice3.dataset.roll = getRandomNumber(1, 6);
    console.log(dice3.dataset.roll);
}

//Mettre un nombre défini pour les 2 premiers dès
function diceNumber(des1, des2){
  dice1.dataset.roll = des1;
  dice2.dataset.roll = des2;
  console.log(dice1.dataset.roll);
  console.log(dice2.dataset.roll);
}

//Mettre un nombre défini pour le 3me dès
function dice3Number(des3){
    dice3.dataset.roll = des3;
    console.log(dice3.dataset.roll);
}

function velute(){
    console.log("la velute");
    cptDice = parseInt(dice1.dataset.roll) + parseInt(dice2.dataset.roll);
    ptsJoueur = ptsJoueur + Math.pow(cptDice,2);
    console.log(cptDice);
    console.log(ptsJoueur)
    
}

function chouette(){
    console.log("la chouette");
    cptDice = parseInt(dice1.dataset.roll);
    ptsJoueur = ptsJoueur + Math.pow(cptDice,2);
    console.log(cptDice);
    console.log(ptsJoueur)
}

function culdechouette(){
    console.log("cul de chouette");
    if(dice1.dataset.roll === "1"){
        ptsJoueur = ptsJoueur + 50;
    }else if(dice1.dataset.roll === "2"){
        ptsJoueur = ptsJoueur + 60;
    }else if(dice1.dataset.roll === "3"){
        ptsJoueur = ptsJoueur + 70;
    }else if(dice1.dataset.roll === "4"){
        ptsJoueur = ptsJoueur + 80;
    }else if(dice1.dataset.roll === "5"){
        ptsJoueur = ptsJoueur + 90;
    }else if(dice.dataset.roll === "6"){
        ptsJoueur = ptsJoueur + 100;
    }
}

function suite(){
    console.log("grelotte ça picote !");
}