package FctPojo;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class FctJoueur {
    
    Connection connection;
    
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("Projet_CulDeChouette_2021PU");
    EntityManager em = emf.createEntityManager();
    
    //INITIALISATION
    
        //Initialisation des champs récupérer lors de l'enregistrement 
        //+ création Code_Joueur correspondant 
        //+ création STATISTIQUE
     public void InitJoueur() throws SQLException{
        connection = DriverManager.getConnection("jdbc:oracle:thin:@//scinfe098.univ-pau.fr:1521/etud.univ-pau.fr", "pcazalis", "pcazalis");
        int numJoueur = 0;
        PreparedStatement reqSelectParam = connection.prepareStatement("SELECT * FROM JOUEUR WHERE CODE_JOUEUR = ?");
        ResultSet res = reqSelectParam.executeQuery();
        while(res.next()){
            numJoueur = res.getRow();
        }
        PreparedStatement reqParam = connection.prepareStatement("INSERT INTO JOUEUR VALUES (?, ?, ?, ?, ?, ?)");
    }
    
    //AFFICHAGE
    
        /*-----------------------POUR TOUT LES JOUEURS------------------------*/

        //Nombre total de joueur

        //Liste des pseudos à partir de Pseudo

        //Moyenne d'âge calculée à partir de Age

        //Moyenne d'homme et de femmes calculée à partir de Sexe

        //Ratio homme / femme calculée à partir de Sexe

        //Liste des villes à partir de Ville (sans répétitions si possible)

        /*--------------------------POUR UN JOUEUR----------------------------*/

        //Informations du Pseudo
    
        //Une information du Pseudo (à définir en paramètre)
    
    //MISE A JOUR
    
        //Changement MDP
    
        //Changement Age (bien que je trouve ça con qu'on mette pas la date de naissance plutôt fin bon)
    
        //Changement Sexe
    
        //Changement Ville
}
