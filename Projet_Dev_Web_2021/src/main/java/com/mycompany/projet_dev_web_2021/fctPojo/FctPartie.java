package com.mycompany.projet_dev_web_2021.fctPojo;

//import POJO
//import Pojo.Partie;

//import TYPE
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;

//import SQL
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;

//import PERSISTENCE
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

//import EXCEPTION
import java.sql.SQLException;

public class FctPartie {
    
    Connection connection;
    
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("Projet_CulDeChouette_2021PU");
    EntityManager em = emf.createEntityManager();
    
    FctResumePartie FctResume = new FctResumePartie();
    FctScorePartie FctScore = new FctScorePartie();
    FctJoueur FctJoueur = new FctJoueur();
    
    public FctPartie() throws SQLException{
       connection = DriverManager.getConnection("jdbc:oracle:thin:@//scinfe098.univ-pau.fr:1521/etud.univ-pau.fr", "pcazalis", "pcazalis");
    }
    
    //INITIALISATION
    
        //Initialisation avec les Code_Joueur en paramètres 
        //+ création Code_Partie
        //+ création des lignes correspondantes de SCOREPARTIE
    public int initPartie(int[] Codes_Joueur) throws SQLException{
        int numPartie = getNb_Partie_Tot();
        numPartie += 1;
        
        PreparedStatement reqInsertParam = connection.prepareStatement("INSERT INTO PARTIE VALUES (?, ?, ?, ?, ?, ?, ?, \'F\')");
        reqInsertParam.setInt(1, numPartie);
        for(int i = 0; i < 6; i++){
            if(i < Codes_Joueur.length){
                reqInsertParam.setInt(i+2, Codes_Joueur[i]);
            }else{
                reqInsertParam.setNull(i+2, Types.INTEGER);
            }
        }
        
        int nb = reqInsertParam.executeUpdate();
        System.out.println("Nombre de ligne ajoutée: " + nb);
        
        for(int i = 0; i < Codes_Joueur.length; i++){
            FctScore.InitScorePartie(BigInteger.valueOf(numPartie), BigInteger.valueOf(Codes_Joueur[i]));
        }
        
        return numPartie;
    }
    
    //AFFICHAGE
    
        /*----------------------POUR TOUTES LES PARTIES-----------------------*/

        //Nombre de partie total
    public int getNb_Partie_Tot() throws SQLException{
        int numPartie = 0;
        Statement req = connection.createStatement();
        ResultSet res = req.executeQuery("SELECT COUNT(*) FROM PARTIE");
        while(res.next()){
            numPartie = res.getInt(1);
        }
        res.close();
        return numPartie;
    }
    
        //Nombre de partie total en cours
     public int getNb_Partie_Tot_En_Cours() throws SQLException{
        int numPartie = 0;
        Statement req = connection.createStatement();
        ResultSet res = req.executeQuery("SELECT COUNT(*) FROM PARTIE WHERE TERMINE = \'F\'");
        while(res.next()){
            numPartie = res.getInt(1);
        }
        res.close();
        return numPartie;
    }

        //Nombre de joueur total
     public int getNb_Participation_Tot() throws SQLException{
        int nbJoueur = 0;
        Statement req = connection.createStatement();
        ResultSet res = req.executeQuery("SELECT * FROM PARTIE");
        while(res.next()){
            int i = 2;
            while (res.getBigDecimal(i) != null && i < 8){
                nbJoueur ++;
                i++;
            }
        }
        res.close();
        return nbJoueur;
    }

        //Moyenne de joueur par partie
    public float getMoy_Joueur() throws SQLException{
        float nbPartie = (float)getNb_Partie_Tot();
        float nbJoueur = (float)getNb_Participation_Tot();
        return nbJoueur/nbPartie;
    }

        //Score moyen des parties terminées
    public float getMoy_Score_Partie_F() throws SQLException{
        float nbPartie = 0;
        float moy = 0;
        Statement req = connection.createStatement();
        ResultSet res = req.executeQuery("SELECT * FROM PARTIE WHERE TERMINE = \'F\'");
        while(res.next()){
            nbPartie ++;
            int i = 2;
            while(i < 8 && res.getBigDecimal(i) != null){
                moy += (float)FctScore.getScore(BigInteger.valueOf(res.getInt(1)), BigInteger.valueOf(res.getInt(i)));
            }
        }
        res.close();
        return moy/nbPartie;
    }
    
        /*--------------------------POUR UNE PARTIE---------------------------*/
    
        //Nombre de joueur
    public int getNb_Joueur(BigDecimal Code_Partie) throws SQLException{
        int nbJoueur = 0;
        PreparedStatement reqSelectParam = connection.prepareStatement("SELECT * FROM PARTIE WHERE Code_PARTIE = ?");
        reqSelectParam.setBigDecimal(1, Code_Partie);
        ResultSet res = reqSelectParam.executeQuery();
        while(res.next()){
            int i = 2;
            while (res.getBigDecimal(i) != null && i < 8){
                nbJoueur ++;
                i++;
            }
        }
        res.close();
        return nbJoueur;
    }
    
        //Liste des joueurs
    public ArrayList<String> getJoueur(BigDecimal Code_Partie) throws SQLException{
        PreparedStatement reqSelectParam = connection.prepareStatement("SELECT * FROM PARTIE WHERE Code_PARTIE = ?");
        reqSelectParam.setBigDecimal(1, Code_Partie);
        ResultSet res = reqSelectParam.executeQuery();
        ArrayList<String> liste = new ArrayList<String>();
        while(res.next()){
            int i = 2;
            while (res.getBigDecimal(i) != null && i < 8){
                liste.add(FctJoueur.getPseudo(res.getBigDecimal(i)));
                i++;
            }
        }
        res.close();
        return liste;
    }
    
        //Partie terminée?
    public boolean getFinish(BigDecimal Code_Partie) throws SQLException{
        PreparedStatement reqSelectParam = connection.prepareStatement("SELECT TERMINE FROM PARTIE WHERE Code_PARTIE = ?");
        reqSelectParam.setBigDecimal(1, Code_Partie);
        ResultSet res = reqSelectParam.executeQuery();
        boolean termine = false;
        while(res.next()){
            termine = res.getString(1).equals("T");
        }
        res.close();
        return termine;
    }
    
        //Statistiques des Code_Joueur
    public ArrayList<String> getStats_Joueurs(BigDecimal Code_Partie) throws SQLException{
        PreparedStatement reqSelectParam = connection.prepareStatement("SELECT * FROM PARTIE WHERE Code_PARTIE = ?");
        reqSelectParam.setBigDecimal(1, Code_Partie);
        ResultSet res = reqSelectParam.executeQuery();
        String pseudo = "";
        ArrayList<String> stats = new ArrayList<String>();
        while(res.next()){
            int i = 2;
            while (res.getBigDecimal(i) != null && i < 8){
                pseudo = FctJoueur.getPseudo(res.getBigDecimal(i));
                stats.add("{Pseudo:"+pseudo+FctJoueur.getStats_Pseudo(pseudo)+"}");
                i++;
            }
        }
        res.close();
        return stats;
    }
    
        //Statistiques d'un Code_Joueur
    public String getStats_Joueur(String Pseudo) throws SQLException{
        String stats = "";
        stats = FctJoueur.getStats_Pseudo(Pseudo);
        return stats;
    }
    
        //Statiqtique défini d'un Code_Joueur (à définir en paramètre)
    public String getStat_Joueur(String Pseudo, String Colonne) throws SQLException{
        String stats = "";
        stats = FctJoueur.getStat_Pseudo(Pseudo,Colonne);
        return stats;
    }
    
    //MISE A JOUR
    
        //maj du score du Code_Joueur d'un Code_Partie
    public void majScore(BigInteger Code_Partie, String Pseudo, int Score) throws SQLException{
        BigInteger code_joueur = new BigInteger(FctJoueur.getCode_Joueur(Pseudo).toString());
        FctScore.incScore(Code_Partie, code_joueur, Score);
    }
    
        //incrémentation Nb_Suite_G du Code_Joueur d'un Code_Partie
    public void incSuite_G(BigInteger Code_Partie, String Pseudo) throws SQLException{
        BigInteger code_joueur = new BigInteger(FctJoueur.getCode_Joueur(Pseudo).toString());
        FctScore.incNb_Suite_G(Code_Partie, code_joueur);
    }
    
        //incrémentation Nb_ChouVel_P du Code_Joueur d'un Code_Partie
    public void incChouVel_P(BigInteger Code_Partie, String Pseudo) throws SQLException{
        BigInteger code_joueur = new BigInteger(FctJoueur.getCode_Joueur(Pseudo).toString());
        FctScore.incNb_ChouVel_P(Code_Partie, code_joueur);
    }
    
        //Enregistrement du dernier lancé de dès effectué de Code_Partie
    public void initLance(BigDecimal Code_Partie, int[] des) throws SQLException{
        FctResume.initTourResumePartie(new BigInteger(Code_Partie.toString()));
        FctResume.majDes_Chouette(new BigInteger(Code_Partie.toString()), des);
    }
}
