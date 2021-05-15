/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.projet_dev_web_2021.WebSocket;

import javax.websocket.RemoteEndpoint;

/**
 *
 * @author pauline
 */
public class ObjectSocket {
    private RemoteEndpoint.Basic WS;
    private String Id;
    private String Pseudo;
    private int CodePartie;
    
    ObjectSocket(RemoteEndpoint.Basic ws, String id){
        WS = ws;
        Id = id;
    }
    
    public void majPseudo(String pseudo){
        Pseudo = pseudo;
    }
    
    public void majCodePartie(int codepartie){
        CodePartie = codepartie;
    }

    public String getPseudo(){
        return Pseudo;
    }
    
    public int getCodePartie(){
        return CodePartie;
    }
    
    public RemoteEndpoint.Basic getWS(){
        return WS;
    }
    
    public String getId(){
        return Id;
    }
    
}
