var websocket;

var Pseudo;
var Position = 0;

var attenteReponse = [];
var partieJoueur = [];
var nbInvit = 5;
var boolPartie = false;
var boolHote = false;

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
    if(msg.Type === "ListePS"){
        majListePS(msg.Pseudos);
    }
    if(msg.Type === "Invitation"){
        if(!boolPartie){
            if ( confirm(msg.Pseudo + " t'invites ") ) {
                confirmationPartie(msg.Pseudo);
            } else {
                refusPartie(msg.Pseudo);
            }
        }else{
            refusPartie(msg.Pseudo);
        }
    }
    
    if(msg.Type === "Reponse"){
        var i = 0;
        var existe = false;
        while(i < attenteReponse.length && !existe){
            if(attenteReponse[i].Pseudo === msg.Pseudo){
                existe = true;
            }else{
              i++;  
            }
        }
        
        if(msg.Contenu === "Non"){
            nbInvit++;
        }else{
            partieJoueur.push(attenteReponse.splice(i,1)[0]);
        }
    }
    
    if(msg.Type === "Quitte"){
        console.log(msg.Pseudo + "quitte la partie");
        var i = 0;
        var existe = false;
        while(i < partieJoueur.length && !existe){
            if(partieJoueur[i].Pseudo === msg.Pseudo){
                existe = true;
                partieJoueur.splice(i,1);
            }else{
              i++;  
            }
        }
        if(partieJoueur.length === 0){
            boolHote = false;
            boolPartie = false;
        }
        console.log(partieJoueur);
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

function envoyerMessage() {
    // récupère le contenu de la zone de texte
    message = document.getElementById("message").value;

    //var msg = "{Pseudo:"+pseudo + ",Type:chat,Contenu:"+message+"}";
    var msg = {
        "Pseudo" : Pseudo,
        "Type" : "Chat",
        "Contenu" : message
    };

    console.log("envoie message :");
    console.log(msg);
    // envoie le message au serveur avec le pseudo
    websocket.send(JSON.stringify(msg));
    // efface le contenu de la zone de texte
    document.getElementById("message").value = "";
}

function invitationJoueur(pseudo){
    var i = 0;
    var existe = false;
    document.getElementById("quittePartieHote").style.display = "block";
    while(i < attenteReponse.length && !existe){
        if(attenteReponse[i].Pseudo === pseudo){
            existe = true;
        }
        i++;
    }
    
    i=0;
    while(i < partieJoueur.length && !existe){
        if(partieJoueur[i] === pseudo){
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
            }
            attenteReponse.push(objet);
            console.log("demande -> attenteReponse: " + attenteReponse);
            websocket.send(JSON.stringify(msg));
        }else{
            alert("Nombre maximum d'invitations envoyées atteint");
        } 
    }else{
        alert("Invitation déjà envoyée pour ce joueur");
    }
    
    console.log("nbInvit:" + nbInvit);
    
}

function ajoutPartieJoueur(pseudo){
    var objet = {
        "Pseudo" : pseudo,
        "Position" : 0
    }
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
    console.log(partieJoueur);
    document.getElementById("quittePartie").style.display = "block";
    websocket.send(JSON.stringify(rep));
}

function majListePS(Pseudos){
    liste = document.getElementById("listePseudo");
    liste.innerHTML = "";
    for(let i = 0; i < Pseudos.length; i++){
        if(Pseudos[i] !== Pseudo){
            liste.innerHTML = liste.innerHTML + "<br />"+ "<a href=\"#\" id=\""+i+"\">" + Pseudos[i] + "</a>";
            document.getElementById(i).onclick = function(){
                if(!boolPartie || boolHote){
                    boolHote = true;
                    boolPartie = true;
                   invitationJoueur(this.innerText); 
                }else{
                    alert("Vous êtes déjà dans une partie\nPour inviter des gens, veuillez quitter la partie.");
                }
            }
        }

    }
}

function quitterPartie(){
    var message = {
        "Pseudo" : Pseudo,
        "Type" : "Quitte",
        "Destinataire" : ""
    }
    console.log("quitte partie");
    document.getElementById("quittePartie").style.display = "none";
    for(let i = 0; i < partieJoueur.length; i++){
        message.Destinataire = partieJoueur[i].Pseudo;
        websocket.send(JSON.stringify(message));
    }
    partieJoueur.splice(0, partieJoueur.length);
    boolPartie = false;
}

function quitterPartieHote(){
    var message = {
        "Pseudo" : Pseudo,
        "Type" : "QuitteHote",
        "Destinataire" : ""
    }
    console.log("quitte partie hote");
    document.getElementById("quittePartie").style.display = "none";
    for(let i = 0; i < partieJoueur.length; i++){
        message.Destinataire = partieJoueur[i].Pseudo;
        websocket.send(JSON.stringify(message));
    }
    partieJoueur.splice(0, partieJoueur.length);
    boolPartie = false;
}





