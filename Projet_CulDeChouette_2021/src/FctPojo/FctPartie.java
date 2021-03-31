package FctPojo;

//import POJO
import Pojo.Partie;

//import FctPojo
import FctPojo.FctScorePartie;

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
import java.sql.Types;

public class FctPartie {
    
    Connection connection;
    
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("Projet_CulDeChouette_2021PU");
    EntityManager em = emf.createEntityManager();
    
    FctScorePartie FctScore;
    
    //INITIALISATION
    
        //Initialisation avec les Code_Joueur en paramètres 
        //+ création Code_Partie
        //+ création des lignes correspondantes de SCOREPARTIE
    public void initPartie(int[] Codes_Joueur) throws SQLException{
        connection = DriverManager.getConnection("jdbc:oracle:thin:@//scinfe098.univ-pau.fr:1521/etud.univ-pau.fr", "pcazalis", "pcazalis");
        
        int numPartie = getNb_Partie_Tot();
        numPartie += 1;
        
        PreparedStatement reqInsertParam = connection.prepareStatement("INSERT INTO RESUMEPARTIE VALUES (?, ?, ?, ?, ?, ?, ?, \'F\')");
        reqInsertParam.setInt(1, numPartie);
        for(int i = 0; i < 6; i++){
            if(i < Codes_Joueur.length){
                reqInsertParam.setInt(i+2, Codes_Joueur[i]);
                FctScore.InitScorePartie(BigInteger.valueOf(numPartie), BigInteger.valueOf(Codes_Joueur[i]));
            }else{
                reqInsertParam.setNull(i+2, Types.INTEGER);
            }
        }
    }
    
    //AFFICHAGE
    
        /*----------------------POUR TOUTES LES PARTIES-----------------------*/

        //Nombre de partie total
    public int getNb_Partie_Tot() throws SQLException{
        int numPartie = 0;
        Statement req = connection.createStatement();
        ResultSet res = req.executeQuery("SELECT * FROM PARTIE");
        while(res.next()){
            numPartie = res.getRow();
        }
        return numPartie;
    }
    
        //Nombre de partie total en cours

        //Nombre de joueur total 

        //Moyenne de joueur par partie

        //Score moyen
    
        /*--------------------------POUR UNE PARTIE---------------------------*/
    
        //Nombre de joueur
    
        //Liste des joueurs
    
        //Partie terminée?
    
        //Statistiques des Code_Joueur
    
        //Statistiques d'un Code_Joueur

    //MISE A JOUR
    
        //Score du Code_Joueur d'un Code_Partie
    
        //Nb_Suite_G du Code_Joueur d'un Code_Partie
    
        //Nb_ChouVel_P du Code_Joueur d'un Code_Partie
    
        //Enregistrement du dernier lancé de dès effectué de Code_Partie
}
