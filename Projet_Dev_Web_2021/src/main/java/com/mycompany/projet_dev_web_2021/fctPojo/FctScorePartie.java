package com.mycompany.projet_dev_web_2021.fctPojo;

//import POJO
//import Pojo.Scorepartie;

//import TYPE
import java.math.BigDecimal;
import java.math.BigInteger;

//import SQL
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
//import java.sql.Statement;

//import PERSISTENCE
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

//import EXCEPTION
import java.sql.SQLException;


public class FctScorePartie {
    
    Connection connection;
    
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("Projet_CulDeChouette_2021PU");
    EntityManager em = emf.createEntityManager();
    
    public FctScorePartie() throws SQLException{
       connection = DriverManager.getConnection("jdbc:oracle:thin:@//scinfe098.univ-pau.fr:1521/etud.univ-pau.fr", "pcazalis", "pcazalis");
    }
    
    //INITIALISATION
    
        //Initialisation de tous les champs lié au Code_Partie et Code_Joueur (les
        //2 passés en paramètre) à 0
    public void InitScorePartie(BigInteger Code_Partie, BigInteger Code_Joueur) throws SQLException{
        PreparedStatement reqParam = connection.prepareStatement("INSERT INTO SCOREPARTIE VALUES (?, ?, 0, 0, 0)");
        reqParam.setInt(1, Code_Partie.intValue());
        reqParam.setInt(2, Code_Joueur.intValue());
        
        int nb = reqParam.executeUpdate();
        System.out.println("Nombre de ligne ajoutée: " + nb);
    }
    
    //AFFICHAGE
    
        /*-----------------------POUR TOUT LES JOUEURS------------------------*/
    
        //Score totale d'une partie
    public BigInteger getScore_Total(BigInteger Code_Partie) throws SQLException{
        PreparedStatement reqParam = connection.prepareStatement("SELECT SUM(SCORE) FROM SCOREPARTIE WHERE CODE_PARTIE = ?");   
        reqParam.setInt(1, Code_Partie.intValue());
        ResultSet res = reqParam.executeQuery();
        BigInteger scoreTot = new BigInteger("0");
        while(res.next()){
            scoreTot.valueOf(res.getInt(1));
        }
        res.close();
        return scoreTot;
    }
    
        //Moyenne de suite gagnée
    public BigDecimal getMoy_Suite_G(BigInteger Code_Partie) throws SQLException{
        PreparedStatement reqParam = connection.prepareStatement("SELECT AVG(NB_SUITE_G) FROM SCOREPARTIE WHERE CODE_PARTIE = ?");   
        reqParam.setInt(1, Code_Partie.intValue());
        ResultSet res = reqParam.executeQuery();
        BigDecimal moy = new BigDecimal(0.0);
        while(res.next()){
            moy.valueOf(res.getDouble(1));
        }
        res.close();
        return moy;
    }
    
        //Moyenne de Chouette Velute perdue
    public BigDecimal getMoy_ChouVel_P(BigInteger Code_Partie) throws SQLException{
        PreparedStatement reqParam = connection.prepareStatement("SELECT AVG(NB_CHOUVEL_P) FROM SCOREPARTIE WHERE CODE_PARTIE = ?");   
        reqParam.setInt(1, Code_Partie.intValue());
        ResultSet res = reqParam.executeQuery();
        BigDecimal moy = new BigDecimal(0.0);
        while(res.next()){
            moy.valueOf(res.getDouble(1));
        }
        res.close();
        return moy;
    }
    
        /*--------------------------POUR UN JOUEUR----------------------------*/
    
        //Score, Nb_Suite_G et Nb_ChouVel_P du Code_Joueur de Code_Partie
    public String getValeurs(BigInteger Code_Partie, BigInteger Code_Joueur) throws SQLException{
        PreparedStatement reqParam = connection.prepareStatement("SELECT * FROM SCOREPARTIE WHERE CODE_PARTIE = ? AND CODE_JOUEUR = ?");
        reqParam.setInt(1, Code_Partie.intValue());
        reqParam.setInt(2, Code_Joueur.intValue());
        ResultSet res = reqParam.executeQuery();
        String scoreP = "";
        while(res.next()){
            scoreP = "{\"Score\":"+res.getInt("Score")
                +", \"Nb_Suite_G\":"+res.getInt("Nb_Suite_G")
                +", \"Nb_ChouVel_P\":"+res.getInt("Nb_ChouVel_P")+"}";
        }
        res.close();
        return scoreP;
    }
    
        //Une valeur précise du Code_Joueur de Code_Partie (à définir en paramètre)
    public int getValeur(BigInteger Code_Partie, BigInteger Code_Joueur, String Colonne) throws SQLException{
        PreparedStatement reqParam = connection.prepareStatement("SELECT ? FROM SCOREPARTIE WHERE CODE_PARTIE = ? AND CODE_JOUEUR = ?");
        reqParam.setInt(2, Code_Partie.intValue());
        reqParam.setInt(3, Code_Joueur.intValue());
        reqParam.setString(1, Colonne);
        ResultSet res = reqParam.executeQuery();
        int result = 0;
        while(res.next()){
            result = res.getInt(1);
        }
        res.close();
        return result;
    }
    
    //MISE A JOUR
    
        //Incrémentation Score
    public void incScore(BigInteger Code_Partie, BigInteger Code_Joueur, int Score) throws SQLException{
        System.out.println("            Début incScore");
        int nb = 0;
        int newScore = getValeur(Code_Partie, Code_Joueur, "SCORE") + Score;
        System.out.println("            newScore = " + newScore);
        PreparedStatement reqUpdateParam = connection.prepareStatement("UPDATE SCOREPARTIE SET SCORE = ? WHERE CODE_PARTIE = ? AND CODE_JOUEUR = ?");
        reqUpdateParam.setInt(1, newScore);
        reqUpdateParam.setInt(2, Code_Partie.intValue());
        reqUpdateParam.setInt(3, Code_Joueur.intValue());
        nb = reqUpdateParam.executeUpdate();
        System.out.println(nb + " ligne ont été update");
    }
    
        //Incrémentation Nb_Suite_G
    public void incNb_Suite_G(BigInteger Code_Partie, BigInteger Code_Joueur, int Suite) throws SQLException{
        int nb = 0;
        int newSuite = getValeur(Code_Partie, Code_Joueur, "NB_SUITE_G") + Suite;
        PreparedStatement reqUpdateParam = connection.prepareStatement("UPDATE SCOREPARTIE SET NB_SUITE_G = ? WHERE CODE_PARTIE = ? AND CODE_JOUEUR = ?");
        reqUpdateParam.setInt(2, Code_Partie.intValue());
        reqUpdateParam.setInt(3, Code_Joueur.intValue());
        reqUpdateParam.setInt(1, newSuite);
        nb = reqUpdateParam.executeUpdate();
        System.out.println(nb + " ligne ont été update");
    }
    
        //Incrémentation Nb_ChouVel_P
    public void incNb_ChouVel_P(BigInteger Code_Partie, BigInteger Code_Joueur, int ChouVel) throws SQLException{
        int nb = 0;
        int newChouVel = getValeur(Code_Partie, Code_Joueur, "NB_CHOUVEL_P") + ChouVel;
        PreparedStatement reqUpdateParam = connection.prepareStatement("UPDATE SCOREPARTIE SET NB_CHOUVEL_P = ? WHERE CODE_PARTIE = ? AND CODE_JOUEUR = ?");
        reqUpdateParam.setInt(2, Code_Partie.intValue());
        reqUpdateParam.setInt(3, Code_Joueur.intValue());
        reqUpdateParam.setInt(1, newChouVel);
        nb = reqUpdateParam.executeUpdate();
        System.out.println(nb + " ligne ont été update");
    }
    
    
}
