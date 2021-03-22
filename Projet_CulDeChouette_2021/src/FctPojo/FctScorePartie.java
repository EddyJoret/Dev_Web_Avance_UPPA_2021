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
        
        /*PreparedStatement reqParam = connection.prepareStatement("SELECT COUNT(SCORE) FROM SCOREPARTIE WHERE CODE_PARTIE = ?");   
        reqParam.setBigDecimal(1, Code_Partie);
        ResultSet res = reqParam.executeQuery();*/
        
        PreparedStatement reqParam = connection.prepareStatement("SELECT * FROM SCOREPARTIE WHERE SCOERPARTIE.CODE_PARTIE = ?");   
        reqParam.setBigDecimal(1, Code_Partie);
        ResultSet res = reqParam.executeQuery();
        
        BigInteger nbPts = new BigInteger("0");
        
        while(res.next()){
            Scorepartie stat = em.find(Scorepartie.class, Code_Partie);
            nbPts.add(stat.getScore());
        }
        
        return nbPts;
    }
    
        //Moyenne de suite gagnée
    
        //Moyenne de Chouette Velute perdue
    
        /*--------------------------POUR UN JOUEUR----------------------------*/
    
        //Score, Nb_Suite_G et Nb_ChouVel_P du Code_Joueur de Code_Partie
    
        //Une valeur précise du Code_Joueur de Code_Partie (à définir en paramètre)
    
    //MISE A JOUR
    
        //Incrémentation Score
    
        //Incrémentation Nb_Suite_G
    
        //Incrémentation Nb_ChouVel_P   
}
