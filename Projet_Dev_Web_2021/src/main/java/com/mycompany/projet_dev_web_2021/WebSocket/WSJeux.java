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
import org.json.JSONObject;

@ServerEndpoint("/Joueur")
public class WSJeux {
    ObjectSocket OS;
    // la liste des websockets : en static pour être partagée
    private static ArrayList<ObjectSocket> listeWS = new ArrayList<>();
    
    @OnMessage
    public void message(String message, Session session) throws IOException {
        
        /*System.out.println("Message : " + message);
        // on parcourt toutes les WS pour leur envoyer une à une le message
        for(Basic ws : WSJeux.listeWS) {
            ws.sendText(message);
        }*/
        
        System.out.println("Message : " + message);
        JSONObject jsonObject = new JSONObject(message);
        
        if(jsonObject.getString("Type").equals("Chat")){
            for(ObjectSocket os : WSJeux.listeWS) {
                os.getWS().sendText(jsonObject.toString());
                System.out.println("Message envoyé à : OS.Id : " + os.getId());
            }
        }else if(jsonObject.getString("Type").equals("Identification")){
            int i = 0;
            boolean done = false;
            while(!done){
                if(WSJeux.listeWS.get(i).getId().compareTo(session.getId()) == 0){
                    WSJeux.listeWS.get(i).majPseudo(jsonObject.getString("Pseudo"));
                    done = true;
                    System.out.println("WSChat.listeOS.get(i):");
                    System.out.println(WSJeux.listeWS.get(i).getPseudo());
                    System.out.println(WSJeux.listeWS.get(i).getId());
                    System.out.println("ListeOS length : " + WSJeux.listeWS.size());
                }else{
                    i++;
                }
            }
            
            System.out.println("Nouvelle liste de joueur à envoyer");
            
            String ListePS = "{\"Type\":\"ListePS\",\"Pseudos\":[";
            i = 1;
            for(ObjectSocket os : WSJeux.listeWS) {
                ListePS = ListePS + "\"" +os.getPseudo() + "\"";
                if(i < WSJeux.listeWS.size()){
                    ListePS = ListePS + ",";
                }else{
                    ListePS = ListePS + "]}";
                }
                i++;
            }
            System.out.println("ListePS : " + ListePS);
            for(ObjectSocket os : WSJeux.listeWS) {
                os.getWS().sendText(ListePS);
            }
        }
    }
    
    @OnOpen
    public void open(Session session) {
        // à l'ouverture d'une connexion, on rajoute la WS dans la liste
        /*WSJeux.listeWS.add(session.getBasicRemote());
        
        System.out.println("ID: " + session.getId());*/
        OS = new ObjectSocket(session.getBasicRemote(), session.getId());
        WSJeux.listeWS.add(OS);
    }
    
    @OnClose
        public void onClose(CloseReason reason, Session session) throws IOException {
        System.out.println("Fermeture de la WS : " + session.getId());
        int i = 0;
        boolean done = false;
        while(!done){
            if(WSJeux.listeWS.get(i).getId().compareTo(session.getId()) == 0){
                WSJeux.listeWS.remove(i);
                done = true;
                System.out.println("ListeOS length : " + WSJeux.listeWS.size());
            }else{
                i++;
            }
        }
        
        String ListePS = "{\"Type\":\"ListePS\",\"Pseudos\":[";
        i = 1;
        for(ObjectSocket os : WSJeux.listeWS) {
            ListePS = ListePS + "\"" +os.getPseudo() + "\"";
            if(i < WSJeux.listeWS.size()){
                ListePS = ListePS + ",";
            }else{
                ListePS = ListePS + "]}";
            }
            i++;
        }
        System.out.println("ListePS : " + ListePS);
        for(ObjectSocket os : WSJeux.listeWS) {
            os.getWS().sendText(ListePS);
        }
    }
        
    @OnError
        public void error(Throwable t) {
        System.err.println("Erreur WS : "+t);
    }
}