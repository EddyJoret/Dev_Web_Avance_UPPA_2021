package FctPojo;

//import POJO
import Pojo.Scorepartie;


//import MATH
import java.math.BigDecimal;
import java.math.BigInteger;

//import SQL
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

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
    
    //INITIALISATION
    
        //Initialisation de tous les champs lié au Code_Partie et Code_Joueur (les
        //2 passés en paramètre) à 0
    public void InitScorePartie(BigDecimal Code_Partie, BigDecimal Code_Joueur) throws SQLException{
        connection = DriverManager.getConnection("jdbc:oracle:thin:@//scinfe098.univ-pau.fr:1521/etud.univ-pau.fr", "pcazalis", "pcazalis");
        PreparedStatement reqParam = connection.prepareStatement("INSERT INTO STATISTIQUE VALUES (?, ?, 0, 0, 0)");
        reqParam.setBigDecimal(1, Code_Partie);
        reqParam.setBigDecimal(2, Code_Joueur);
        
        int nb = reqParam.executeUpdate();
        System.out.println("Nombre de ligne ajoutée: " + nb);
    }
    
    //AFFICHAGE
    
        /*-----------------------POUR TOUT LES JOUEURS------------------------*/
    
        //Score totale d'une partie
    public BigInteger ScoreTotal(BigDecimal Code_Partie) throws SQLException{
        Statement req = connection.createStatement();
        
        PreparedStatement reqParam = connection.prepareStatement("SELECT SUM(SCORE) FROM SCOREPARTIE WHERE CODE_PARTIE = ?");   
        reqParam.setBigDecimal(1, Code_Partie);
        ResultSet res = reqParam.executeQuery();
        
        BigInteger scoreTot = new BigInteger("0");
        
        while(res.next()){
            Scorepartie score = em.find(Scorepartie.class, res.getBigDecimal("Code_Partie"));
            scoreTot.add(score.getScore());
        }
        
        return scoreTot;
    }
    
        //Moyenne de suite gagnée
    
    public BigDecimal MoySuitG(BigDecimal Code_Partie) throws SQLException{
        Statement req = connection.createStatement();
        
        PreparedStatement reqParam = connection.prepareStatement("SELECT NB_SUITE_G FROM SCOREPARTIE WHERE CODE_PARTIE = ?");   
        reqParam.setBigDecimal(1, Code_Partie);
        ResultSet res = reqParam.executeQuery();
        
        int nbSuiteG = 0;
        BigDecimal moy = new BigDecimal(0.0);
        
        while(res.next()){
            Scorepartie score = em.find(Scorepartie.class, res.getBigDecimal("Code_Joueur"));
            moy.add(new BigDecimal(score.getNbSuiteG()));
            nbSuiteG = res.getRow();
        }
        
        return (moy.divide(new BigDecimal(nbSuiteG)));
    }
    
        //Moyenne de Chouette Velute perdue
    
    public BigDecimal MoyChouVelPerdue(BigDecimal Code_Partie) throws SQLException{
        Statement req = connection.createStatement();
        
        PreparedStatement reqParam = connection.prepareStatement("SELECT NB_CHOUVEL_P FROM SCOREPARTIE WHERE CODE_PARTIE = ?");   
        reqParam.setBigDecimal(1, Code_Partie);
        ResultSet res = reqParam.executeQuery();
        
        int nbSuiteP = 0;
        BigDecimal moy = new BigDecimal(0.0);
        
        while(res.next()){
            Scorepartie score = em.find(Scorepartie.class, res.getBigDecimal("Code_Joueur"));
            moy.add(new BigDecimal(score.getNbChouvelP()));
            nbSuiteP = res.getRow();
        }
        
        return (moy.divide(new BigDecimal(nbSuiteP)));
    }
    
        /*--------------------------POUR UN JOUEUR----------------------------*/
    
        //Score, Nb_Suite_G et Nb_ChouVel_P du Code_Joueur de Code_Partie
    public String getScores(BigDecimal Code_Joueur) throws SQLException{
        Scorepartie score = em.find(Scorepartie.class, Code_Joueur);
        String scoreP;
        scoreP = "{\"Score\":"+score.getScorepartiePK()
                +", \"Nb_Suite_G\":"+score.getNbSuiteG()
                +", \"Nb_ChouVel_P\":"+score.getNbChouvelP()+"}";
        
        return scoreP;
    }
    
        //Une valeur précise du Code_Joueur de Code_Partie (à définir en paramètre)
    public String getScore(BigDecimal Code_Joueur, String Colonne) throws SQLException{
        Scorepartie score = em.find(Scorepartie.class, Code_Joueur);
        String scoreP = "{\""+Colonne+"\":";
        
        switch(Colonne){
            case "Score":
                scoreP += score.getScore()+"}";
                break;
            case "Nb_Suite_G":
                scoreP += score.getNbSuiteG()+"}";
                break;
            case "Nb_ChouVel_P":
                scoreP += score.getNbChouvelP()+"}";
                break;
        }
        
        return scoreP;
    }
    
    //MISE A JOUR
    
        //Incrémentation Score
    private void inc_Score(BigDecimal Code_Joueur){
        Scorepartie score = em.find(Scorepartie.class, Code_Joueur);
        
    }
    
        //Incrémentation Nb_Suite_G
    
        //Incrémentation Nb_ChouVel_P
    
    
}
