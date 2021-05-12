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

@ServerEndpoint("/CulDeChouette")
public class WSJeux {
     ObjectSocket OS;
    // la liste des websockets : en static pour être partagée
    private static ArrayList<ObjectSocket> listeOS = new ArrayList<>();
    
    @OnMessage
    public void message(String message, Session session) throws IOException {
        // on parcourt toutes les OS pour leur envoyer une à une le message
        System.out.println("Message : " + message);
        JSONObject jsonObject = new JSONObject(message);
        
        //Cas pour Type = Chat
        if(jsonObject.getString("Type").equals("Chat")){
            for(ObjectSocket os : WSJeux.listeOS) {
                os.getWS().sendText(jsonObject.toString());
                System.out.println("Message envoyé à : OS.Id : " + os.getId());
            }
        }
        
        //Cas pour Type = Identification
        if(jsonObject.getString("Type").equals("Identification")){
            int i = 0;
            boolean done = false;
            while(!done){
                if(WSJeux.listeOS.get(i).getId().compareTo(session.getId()) == 0){
                    WSJeux.listeOS.get(i).majPseudo(jsonObject.getString("Pseudo"));
                    done = true;
                    System.out.println("WSJeux.listeOS.get(i):");
                    System.out.println(WSJeux.listeOS.get(i).getPseudo());
                    System.out.println(WSJeux.listeOS.get(i).getId());
                    System.out.println("ListeOS length : " + WSJeux.listeOS.size());
                }else{
                    i++;
                }
            }
            
            System.out.println("Nouvelle liste de joueur à envoyer");
            
            String ListePS = "{\"Type\":\"ListePS\",\"Pseudos\":[";
            i = 1;
            for(ObjectSocket os : WSJeux.listeOS) {
                ListePS = ListePS + "\"" +os.getPseudo() + "\"";
                if(i < WSJeux.listeOS.size()){
                    ListePS = ListePS + ",";
                }else{
                    ListePS = ListePS + "]}";
                }
                i++;
            }
            System.out.println("ListePS : " + ListePS);
            for(ObjectSocket os : WSJeux.listeOS) {
                os.getWS().sendText(ListePS);
            }
        }
        
        //Cas pour Type = Invitation
        if(jsonObject.getString("Type").equals("Invitation")){
            int i = 0;
            boolean done = false;
            while(!done){
                if(WSJeux.listeOS.get(i).getPseudo().compareTo(jsonObject.getString("Destinataire")) == 0){
                    WSJeux.listeOS.get(i).getWS().sendText(jsonObject.toString());
                    done = true;
                }else{
                    i++;
                }
            }
        }
    }
    
    @OnOpen
    public void open(Session session) {
        // à l'ouverture d'une connexion, on rajoute le OS dans la liste
        OS = new ObjectSocket(session.getBasicRemote(), session.getId());
        WSJeux.listeOS.add(OS);
    }
    
    @OnClose
    public void onClose(CloseReason reason, Session session) throws IOException {
        System.out.println("Fermeture de la WS : " + session.getId());
        int i = 0;
        boolean done = false;
        while(!done){
            if(WSJeux.listeOS.get(i).getId().compareTo(session.getId()) == 0){
                WSJeux.listeOS.remove(i);
                done = true;
                System.out.println("ListeOS length : " + WSJeux.listeOS.size());
            }else{
                i++;
            }
        }
        
        String ListePS = "{\"Type\":\"ListePS\",\"Pseudos\":[";
        i = 1;
        for(ObjectSocket os : WSJeux.listeOS) {
            ListePS = ListePS + "\"" +os.getPseudo() + "\"";
            if(i < WSJeux.listeOS.size()){
                ListePS = ListePS + ",";
            }else{
                ListePS = ListePS + "]}";
            }
            i++;
        }
        System.out.println("ListePS : " + ListePS);
        for(ObjectSocket os : WSJeux.listeOS) {
            os.getWS().sendText(ListePS);
        }
        
    }
        
    @OnError
        public void error(Throwable t) {
        System.err.println("Erreur WS : "+t);
    }
}