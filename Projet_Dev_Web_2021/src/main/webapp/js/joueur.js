var websocket;
var Pseudo;

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

    console.log("onOpen :");
    console.log(msg);
    // envoie le message au serveur avec le pseudo
    websocket.send(JSON.stringify(msg));

}

// appelée quand le serveur envoie un message : rajoute la donnée de
// l'événement à la fin du paragraphe qui contient la liste des messages
function onMessage(evt) {
    var msg = JSON.parse(evt.data)
    console.log(msg);
    console.log("Pseudo: " + Pseudo);
    if(msg.Type == "ListePS"){
        liste = document.getElementById("listePseudo");
        liste.innerHTML = "";
        for(let i = 0; i < msg.Pseudos.length; i++){
            if(msg.Pseudos[i] != Pseudo){
                liste.innerHTML = liste.innerHTML + "<br />"+ "<a href=\"#\" onclick=\"invitationJoueur()\">" + msg.Pseudos[i] + "</a>";
            }
            
        }
    }
    if(msg.Type == "Invitation"){
        //liste.innerHTML = liste.innerHTML + "<br />"+ "Msg Prive : " + msg.Pseudo + " : " + msg.Contenu; 
        if ( confirm(msg.Pseudo + " te dis " + msg.Contenu) ) {
            var rep = {
                "Pseudo" : pseudo,
                "Type" : "Reponse",
                "Destinataire" : msg.Pseudo,
                "Contenu" : "Oui"
            }
            websocket.send(JSON.stringify(rep));
        } else {
            var rep = {
                "Pseudo" : pseudo,
                "Type" : "Reponse",
                "Destinataire" : msg.Pseudo,
                "Contenu" : "Non"
            }
            websocket.send(JSON.stringify(rep));
        }
    }
    if(msg.Type == "Reponse"){
        //liste.innerHTML = liste.innerHTML + "<br />"+ "Msg Prive : " + msg.Pseudo + " : " + msg.Contenu; 
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

function envoyerMessagePrive() {
    // récupère le contenu de la zone de texte
    var message = document.getElementById("messageprive").value;
    var destinataire = document.getElementById("destinataire").value;

    //var msg = "{Pseudo:"+pseudo + ",Type:chat,Contenu:"+message+"}";
    var msg = {
        "Pseudo" : Pseudo,
        "Type" : "Invitation",
        "Destinataire" : destinataire,
        "Contenu" : message
    };

    console.log("envoie message :");
    console.log(msg);
    // envoie le message au serveur avec le pseudo
    websocket.send(JSON.stringify(msg));
    // efface le contenu de la zone de texte
    document.getElementById("messageprive").value = "";
    document.getElementById("destinataire").value = "";
}
function invitationJoueur(){
    console.log("Coucou toi");
}



