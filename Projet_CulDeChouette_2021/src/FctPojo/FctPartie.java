package FctPojo;

//import POJO
//import Pojo.Partie;

//import TYPE
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
        
        int nb = reqInsertParam.executeUpdate();
        System.out.println("Nombre de ligne ajoutée: " + nb);
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
        return numPartie;
    }

        //Nombre de joueur total
     public int getNb_Joueur_Tot() throws SQLException{
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
        return nbJoueur;
    }

        //Moyenne de joueur par partie
    public float getMoy_Joueur() throws SQLException{
        float nbPartie = (float)getNb_Partie_Tot();
        float nbJoueur = (float)getNb_Joueur_Tot();
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
                moy += (float)FctScore.getValeur(BigInteger.valueOf(res.getInt(1)), BigInteger.valueOf(res.getInt(i)), "SCORE");
            }
        }
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
        return nbJoueur;
    }
    
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
