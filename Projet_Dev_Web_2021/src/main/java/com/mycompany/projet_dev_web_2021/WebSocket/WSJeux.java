package com.mycompany.projet_dev_web_2021.WebSocket;

import java.io.IOException;
import java.util.ArrayList;
import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.RemoteEndpoint.Basic;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/Joueur")
public class WSJeux {
    // la liste des websockets : en static pour être partagée
    private static ArrayList<Basic> listeWS = new ArrayList<>();
    
    @OnMessage
    public void message(String message) throws IOException {
        // on parcourt toutes les WS pour leur envoyer une à une le message
        for(Basic ws : WSJeux.listeWS) {
            ws.sendText(message);
        }
    }
    
    @OnOpen
    public void open(Session session) {
        // à l'ouverture d'une connexion, on rajoute la WS dans la liste
        WSJeux.listeWS.add(session.getBasicRemote());
        
        System.out.println("ID: " + session.getId());
    }
    
    @OnClose
        public void onClose(CloseReason reason) {
        System.out.println("Fermeture de la WS");
    }
        
    @OnError
        public void error(Throwable t) {
        System.err.println("Erreur WS : "+t);
    }
}