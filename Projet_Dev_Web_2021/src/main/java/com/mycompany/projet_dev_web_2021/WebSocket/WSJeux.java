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
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mycompany.projet_dev_web_2021.fctPojo.FctJoueur;
import com.mycompany.projet_dev_web_2021.fctPojo.FctPartie;
import com.mycompany.projet_dev_web_2021.fctPojo.FctResumePartie;
import com.mycompany.projet_dev_web_2021.fctPojo.FctScorePartie;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.SQLException;

@ServerEndpoint("/CulDeChouette")
public class WSJeux {
     ObjectSocket OS;
    // la liste des websockets : en static pour être partagée
    private static ArrayList<ObjectSocket> listeOS = new ArrayList<>();
    
    // variables fctPojo
    private boolean init = false;
    FctJoueur fctJ;
    FctPartie fctP;
    FctResumePartie fctRP;
    FctScorePartie fctSP;
    
    @OnMessage
    public void message(String message, Session session) throws IOException, SQLException {
        // on parcourt toutes les OS pour leur envoyer une à une le message
        System.out.println("Message : " + message);
        JSONObject jsonObject = new JSONObject(message);
        
        if(!init){
            init = true;
            fctJ = new FctJoueur();
            fctP = new FctPartie();
            fctRP = new FctResumePartie();
            fctSP = new FctScorePartie();
        }
        
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
                || jsonObject.getString("Type").equals("ComplementPartie") || jsonObject.getString("Type").equals("PassageMain")
                || jsonObject.getString("Type").equals("ChouetteVelute") || jsonObject.getString("Type").equals("Suite")
                || jsonObject.getString("Type").equals("VictoirePartie")){
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
        
        if(jsonObject.getString("Type").equals("Chouette")){
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
            
            int[] des = {jsonObject.getInt("Des1"), jsonObject.getInt("Des2")};
            
            i = 0;
            done = false;
            while(!done){
                if(WSJeux.listeOS.get(i).getPseudo().compareTo(jsonObject.getString("Pseudo")) == 0){
                    fctP.initLance(new BigDecimal(WSJeux.listeOS.get(i).getCodePartie()), des);
                    done = true;
                }else{
                    i++;
                }
            }
        }
        
        if(jsonObject.getString("Type").equals("Cu")){
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
            
            int[] des = {jsonObject.getInt("Des3")};
            
            i = 0;
            done = false;
            while(!done){
                if(WSJeux.listeOS.get(i).getPseudo().compareTo(jsonObject.getString("Pseudo")) == 0){
                    fctRP.majDes_Cu(new BigInteger(String.valueOf(WSJeux.listeOS.get(i).getCodePartie())), des);
                    done = true;
                }else{
                    i++;
                }
            }
        }
        
        if(jsonObject.getString("Type").equals("ChouetteVeluteGagne")){
            int i = 0;
            boolean done = false;
            while(!done){
                if(WSJeux.listeOS.get(i).getPseudo().compareTo(jsonObject.getString("Destinataire")) == 0){
                    WSJeux.listeOS.get(i).getWS().sendText(jsonObject.toString());
                    BigInteger CodePartie = new BigInteger(String.valueOf(WSJeux.listeOS.get(i).getCodePartie()));
                    fctP.incChouVel_P(CodePartie, jsonObject.getString("Destinataire"));
                    done = true;
                }else{
                    i++;
                }
            }
        }
        
        if(jsonObject.getString("Type").equals("SuiteGagne")){
            JSONArray jsonArray = jsonObject.getJSONArray("Destinataires");
            String msgScore = "{\"Type\":\"SuiteGagne\"}";
            jsonArray.forEach(item -> {
                JSONObject itm = new JSONObject(item.toString());
                int i = 0;
                boolean done = false;
                while(!done){
                    if(itm.getString("Pseudo").compareTo(jsonObject.getString("Pseudo")) == 0){
                        done = true;
                    }else if(WSJeux.listeOS.get(i).getPseudo().compareTo(itm.getString("Pseudo")) == 0){
                        try {
                            WSJeux.listeOS.get(i).getWS().sendText(msgScore);
                        } catch (IOException ex) {
                            Logger.getLogger(WSJeux.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        done = true;
                    }else{
                        i++;
                    }
                }
            });
            
            int i = 0;
            boolean done = false;
            while(!done){
                if(WSJeux.listeOS.get(i).getPseudo().compareTo(jsonObject.getString("PSeudo")) == 0){
                    BigInteger CodePartie = new BigInteger(String.valueOf(WSJeux.listeOS.get(i).getCodePartie()));
                    fctP.incSuite_G(CodePartie, jsonObject.getString("Pseudo"));
                    done = true;
                }else{
                    i++;
                }
            }
        }
        
        if(jsonObject.getString("Type").equals("MajScore")){
            JSONArray jsonArray = jsonObject.getJSONArray("Destinataires");
            String msgScore = "{\"Pseudo\":\"" + jsonObject.getString("Pseudo") + "\",\"Type\":\"MajScore\",\"Score\":" + jsonObject.getInt("Score") + "}";
            jsonArray.forEach(item -> {
                JSONObject itm = new JSONObject(item.toString());
                int i = 0;
                boolean done = false;
                while(!done){
                    if(itm.getString("Pseudo").compareTo(jsonObject.getString("Pseudo")) == 0){
                        done = true;
                    }else if(WSJeux.listeOS.get(i).getPseudo().compareTo(itm.getString("Pseudo")) == 0){
                        try {
                            WSJeux.listeOS.get(i).getWS().sendText(msgScore);
                        } catch (IOException ex) {
                            Logger.getLogger(WSJeux.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        done = true;
                    }else{
                        i++;
                    }
                }
            });
            
            int i = 0;
            boolean done = false;
            while(!done){
                if(WSJeux.listeOS.get(i).getPseudo().compareTo(jsonObject.getString("Pseudo")) == 0){
                    BigInteger CodePartie = new BigInteger(String.valueOf(WSJeux.listeOS.get(i).getCodePartie()));
                    int Score = jsonObject.getInt("Score");
                    fctP.majScore(CodePartie, jsonObject.getString("Pseudo"), Score);
                    done = true;
                }else{
                    i++;
                }
            }
            
        }
        
        if(jsonObject.getString("Type").equals("LancerPartie")){
            JSONArray jsonArray = jsonObject.getJSONArray("Pseudos");
            ArrayList<Integer> CodeJoueur = new ArrayList<Integer>();
            String PartiePS = "{\"Type\":\"LancerPartie\",\"Pseudos\":" + jsonArray.toString() + ",\"Seuil\":" + jsonObject.getString("Seuil") + "}";
            jsonArray.forEach(item -> {
                JSONObject itm = new JSONObject(item.toString());
                int i = 0;
                boolean done = false;
                while(!done){
                    if(WSJeux.listeOS.get(i).getPseudo().compareTo(itm.getString("Pseudo")) == 0){
                        CodeJoueur.add(fctJ.getCode_Joueur(itm.getString("Pseudo")).intValue());
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
            
            int[] CodeJoueurs = new int[CodeJoueur.size()];
            for(int j = 0; j < CodeJoueur.size(); j++){
                CodeJoueurs[j] = CodeJoueur.get(j);
            }
            int CodePartie = fctP.initPartie(CodeJoueurs);
            
            jsonArray.forEach(item -> {
                JSONObject itm = new JSONObject(item.toString());
                int i = 0;
                boolean done = false;
                while(!done){
                    if(WSJeux.listeOS.get(i).getPseudo().compareTo(itm.getString("Pseudo")) == 0){
                        WSJeux.listeOS.get(i).majCodePartie(CodePartie);
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