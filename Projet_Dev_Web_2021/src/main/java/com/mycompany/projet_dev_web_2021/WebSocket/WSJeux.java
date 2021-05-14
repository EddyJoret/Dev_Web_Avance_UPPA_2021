package com.mycompany.projet_dev_web_2021.WebSocket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.RemoteEndpoint.Basic;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import org.json.JSONArray;
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
            for(ObjectSocket os : WSJeux.listeOS) {
                os.getWS().sendText(ListePS);
            }
        }
        
        //Cas pour Type = Invitation ou Reponse ou Quitte ou QuitteHote ou ComplementPartie ou Chouette ou Cu
        if(jsonObject.getString("Type").equals("Invitation") || jsonObject.getString("Type").equals("Reponse") 
                || jsonObject.getString("Type").equals("Quitte") || jsonObject.getString("Type").equals("QuitteHote")
                || jsonObject.getString("Type").equals("ComplementPartie") || jsonObject.getString("Type").equals("Chouette")
                || jsonObject.getString("Type").equals("Cu") || jsonObject.getString("Type").equals("PassageMain")
                || jsonObject.getString("Type").equals("MajScore") || jsonObject.getString("Type").equals("ChouetteVelute")){
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
        
        if(jsonObject.getString("Type").equals("LancerPartie")){
            JSONArray jsonArray = jsonObject.getJSONArray("Pseudos");
            String PartiePS = "{\"Type\":\"LancerPartie\",\"Pseudos\":" + jsonArray.toString() + "}";
            jsonArray.forEach(item -> {
                JSONObject itm = new JSONObject(item.toString());
                int i = 0;
                boolean done = false;
                while(!done){
                    if(WSJeux.listeOS.get(i).getPseudo().compareTo(itm.getString("Pseudo")) == 0){
                        try {
                            WSJeux.listeOS.get(i).getWS().sendText(PartiePS);
                        } catch (IOException ex) {
                            Logger.getLogger(WSJeux.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        done = true;
                    }else{
                        i++;
                    }
                }
            });
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
        int i = 0;
        boolean done = false;
        while(!done){
            if(WSJeux.listeOS.get(i).getId().compareTo(session.getId()) == 0){
                WSJeux.listeOS.remove(i);
                done = true;
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
        for(ObjectSocket os : WSJeux.listeOS) {
            os.getWS().sendText(ListePS);
        }
        
    }
        
    @OnError
        public void error(Throwable t) {
        System.err.println("Erreur WS : "+t);
    }
}