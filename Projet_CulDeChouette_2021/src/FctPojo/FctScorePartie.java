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
    public void InitScorePartie(BigInteger Code_Partie, BigInteger Code_Joueur) throws SQLException{
        connection = DriverManager.getConnection("jdbc:oracle:thin:@//scinfe098.univ-pau.fr:1521/etud.univ-pau.fr", "pcazalis", "pcazalis");
        PreparedStatement reqParam = connection.prepareStatement("INSERT INTO SCOREPARTIE VALUES (?, ?, 0, 0, 0)");
        reqParam.setInt(1, Code_Partie.intValue());
        reqParam.setInt(2, Code_Joueur.intValue());
        
        int nb = reqParam.executeUpdate();
        System.out.println("Nombre de ligne ajoutée: " + nb);
    }
    
    //AFFICHAGE
    
        /*-----------------------POUR TOUT LES JOUEURS------------------------*/
    
        //Score totale d'une partie
    public BigInteger ScoreTotal(BigInteger Code_Partie) throws SQLException{
        PreparedStatement reqParam = connection.prepareStatement("SELECT SUM(SCORE) FROM SCOREPARTIE WHERE CODE_PARTIE = ?");   
        reqParam.setInt(1, Code_Partie.intValue());
        ResultSet res = reqParam.executeQuery();
        BigInteger scoreTot = new BigInteger("0");
        while(res.next()){
            scoreTot.valueOf(res.getInt(1));
        }
        return scoreTot;
    }
    
        //Moyenne de suite gagnée
    public BigDecimal MoySuitG(BigInteger Code_Partie) throws SQLException{
        PreparedStatement reqParam = connection.prepareStatement("SELECT AVG(NB_SUITE_G) FROM SCOREPARTIE WHERE CODE_PARTIE = ?");   
        reqParam.setInt(1, Code_Partie.intValue());
        ResultSet res = reqParam.executeQuery();
        BigDecimal moy = new BigDecimal(0.0);
        while(res.next()){
            moy.valueOf(res.getDouble(1));
        }
        return moy;
    }
    
        //Moyenne de Chouette Velute perdue
    public BigDecimal MoyChouVelPerdue(BigInteger Code_Partie) throws SQLException{
        PreparedStatement reqParam = connection.prepareStatement("SELECT AVG(NB_CHOUVEL_P) FROM SCOREPARTIE WHERE CODE_PARTIE = ?");   
        reqParam.setInt(1, Code_Partie.intValue());
        ResultSet res = reqParam.executeQuery();
        BigDecimal moy = new BigDecimal(0.0);
        while(res.next()){
            moy.valueOf(res.getDouble(1));
        }
        return moy;
    }
    
        /*--------------------------POUR UN JOUEUR----------------------------*/
    
        //Score, Nb_Suite_G et Nb_ChouVel_P du Code_Joueur de Code_Partie
    public String getScores(BigInteger Code_Partie, BigInteger Code_Joueur) throws SQLException{
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
        return scoreP;
    }
    
        //Une valeur précise du Code_Joueur de Code_Partie (à définir en paramètre)
    public String getScore(BigInteger Code_Partie, BigInteger Code_Joueur, String Colonne) throws SQLException{
        PreparedStatement reqParam = connection.prepareStatement("SELECT ? FROM SCOREPARTIE WHERE CODE_PARTIE = ? AND CODE_JOUEUR = ?");
        reqParam.setInt(2, Code_Partie.intValue());
        reqParam.setInt(3, Code_Joueur.intValue());
        reqParam.setString(1, Colonne);
        ResultSet res = reqParam.executeQuery();
        String scoreP = "{\""+Colonne+"\":";
        while(res.next()){
            scoreP += res.getInt(1);
        }      
        return scoreP;
    }
    
    //MISE A JOUR
    
        //Incrémentation Score
    private void inc_Score(BigInteger Code_Partie, BigInteger Code_Joueur, int Score) throws SQLException{
        PreparedStatement reqSelectParam = connection.prepareStatement("SELECT SCORE FROM SCOREPARTIE WHERE CODE_PARTIE = ? AND CODE_JOUEUR = ?");
        reqSelectParam.setInt(1, Code_Partie.intValue());
        reqSelectParam.setInt(2, Code_Joueur.intValue());
        ResultSet resSelect = reqSelectParam.executeQuery();
        PreparedStatement reqUpdateParam;
        int nb = 0;
        while(resSelect.next()){
            int newScore = resSelect.getInt(1) + Score;
            reqUpdateParam = connection.prepareStatement("UPDATE SCOREPARTIE SET SCORE = ? WHERE CODE_PARTIE = ? AND CODE_JOUEUR = ?");
            reqUpdateParam.setInt(2, Code_Partie.intValue());
            reqUpdateParam.setInt(3, Code_Joueur.intValue());
            reqUpdateParam.setInt(1, newScore);
            nb = reqUpdateParam.executeUpdate();
        }
        System.out.println(nb + " ligne ont été update");
    }
    
        //Incrémentation Nb_Suite_G
    private void inc_Nb_Suite_G(BigInteger Code_Partie, BigInteger Code_Joueur, int Suite) throws SQLException{
        PreparedStatement reqSelectParam = connection.prepareStatement("SELECT NB_SUITE_G FROM SCOREPARTIE WHERE CODE_PARTIE = ? AND CODE_JOUEUR = ?");
        reqSelectParam.setInt(1, Code_Partie.intValue());
        reqSelectParam.setInt(2, Code_Joueur.intValue());
        ResultSet resSelect = reqSelectParam.executeQuery();
        PreparedStatement reqUpdateParam;
        int nb = 0;
        while(resSelect.next()){
            int newSuite = resSelect.getInt(1) + Suite;
            reqUpdateParam = connection.prepareStatement("UPDATE SCOREPARTIE SET NB_SUITE_G = ? WHERE CODE_PARTIE = ? AND CODE_JOUEUR = ?");
            reqUpdateParam.setInt(2, Code_Partie.intValue());
            reqUpdateParam.setInt(3, Code_Joueur.intValue());
            reqUpdateParam.setInt(1, newSuite);
            nb = reqUpdateParam.executeUpdate();
        }
        System.out.println(nb + " ligne ont été update");
    }
    
        //Incrémentation Nb_ChouVel_P
    private void inc_Nb_ChouVel_P(BigInteger Code_Partie, BigInteger Code_Joueur, int ChouVel) throws SQLException{
        PreparedStatement reqSelectParam = connection.prepareStatement("SELECT NB_CHOUVEL_P FROM SCOREPARTIE WHERE CODE_PARTIE = ? AND CODE_JOUEUR = ?");
        reqSelectParam.setInt(1, Code_Partie.intValue());
        reqSelectParam.setInt(2, Code_Joueur.intValue());
        ResultSet resSelect = reqSelectParam.executeQuery();
        PreparedStatement reqUpdateParam;
        int nb = 0;
        while(resSelect.next()){
            int newChouVel = resSelect.getInt(1) + ChouVel;
            reqUpdateParam = connection.prepareStatement("UPDATE SCOREPARTIE SET NB_CHOUVEL_P = ? WHERE CODE_PARTIE = ? AND CODE_JOUEUR = ?");
            reqUpdateParam.setInt(2, Code_Partie.intValue());
            reqUpdateParam.setInt(3, Code_Joueur.intValue());
            reqUpdateParam.setInt(1, newChouVel);
            nb = reqUpdateParam.executeUpdate();
        }
        System.out.println(nb + " ligne ont été update");
    }
    
    
}
