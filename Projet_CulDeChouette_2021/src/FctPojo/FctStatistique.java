package FctPojo;

//import POJO
import Pojo.Statistique;

//import MATH
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Array;

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

public class FctStatistique{
    
    Connection connection;
    
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("Projet_CulDeChouette_2021PU");
    EntityManager em = emf.createEntityManager();
    
    //INITIALISATION
    
        //Initialisation de tous les champs lié au Code_Joueur (passé en
        //paramètre) à 0
    public void InitStat(BigDecimal Code_Joueur) throws SQLException{
        connection = DriverManager.getConnection("jdbc:oracle:thin:@//scinfe098.univ-pau.fr:1521/etud.univ-pau.fr", "pcazalis", "pcazalis");
        PreparedStatement reqParam = connection.prepareStatement("INSERT INTO STATISTIQUE VALUES (?, 0, 0, 0, 0, 0, 0, 0, 0, 0)");
        reqParam.setBigDecimal(1, Code_Joueur);
        
        int nb = reqParam.executeUpdate();
        System.out.println("Nombre de ligne ajoutée: " + nb);
    }
    
    //AFFICHAGE
    
        /*-----------------------POUR TOUT LES JOUEURS------------------------*/

        //Moyenne de partie jouée calculée à partir de Nb_Partie
    public BigInteger getMoyPartieTot() throws SQLException{
        Statement req = connection.createStatement();
        ResultSet res = req.executeQuery("SELECT * FROM STATISTIQUE");
        
        BigInteger nbPartie = new BigInteger("0");
        
        while(res.next()){
            Statistique stat = em.find(Statistique.class, res.getBigDecimal("Code_Joueur"));
            nbPartie.add(stat.getNbPartie());
        }
        
        return nbPartie;
    }

        //Moyenne de partie gagnée calculée à partir de Nb_Victoire_Moyen
    public double getMoyPartieGTot() throws SQLException{
        Statement req = connection.createStatement();
        ResultSet res = req.executeQuery("SELECT * FROM STATISTIQUE");
        
        int nbPartie = 0;
        double moy = 0.0;
        
        while(res.next()){
            Statistique stat = em.find(Statistique.class, res.getBigDecimal("Code_Joueur"));
            moy += stat.getNbVictoireMoyen();
            nbPartie = res.getRow();
        }
        
        return (moy / nbPartie);
    }
    
        //Nombre de points totals gagnés calculé à partir de Nb_Pts_Tot
    public BigInteger getNbPtsTot() throws SQLException{
        Statement req = connection.createStatement();
        ResultSet res = req.executeQuery("SELECT * FROM STATISTIQUE");
        
        BigInteger nbPts = new BigInteger("0");
        
        while(res.next()){
            Statistique stat = em.find(Statistique.class, res.getBigDecimal("Code_Joueur"));
            nbPts.add(stat.getNbPtsTot());
        }
        
        return nbPts;
    }

        //Moyenne de points calculée à partir de Sc_Moyen
    public double getScoreMoyTot() throws SQLException{
        Statement req = connection.createStatement();
        ResultSet res = req.executeQuery("SELECT * FROM STATISTIQUE");
        
        int nbPartie = 0;
        double moy = 0.0;
        
        while(res.next()){
            Statistique stat = em.find(Statistique.class, res.getBigDecimal("Code_Joueur"));
            moy += stat.getScoreMoyen();
            nbPartie = res.getRow();
        }
        
        return (moy / nbPartie);
    }

        //Moyenne de suite gagnée calculée à partir de Su_Moyen_G
    public double getSuiteMoyGTot() throws SQLException{
        Statement req = connection.createStatement();
        ResultSet res = req.executeQuery("SELECT * FROM STATISTIQUE");
        
        int nbPartie = 0;
        double moy = 0.0;
        
        while(res.next()){
            Statistique stat = em.find(Statistique.class, res.getBigDecimal("Code_Joueur"));
            moy += stat.getSuiteMoyenG();
            nbPartie = res.getRow();
        }
        
        return (moy / nbPartie);
    }

        //Moyenne de chouette velute perdue calculée à partir de CV_Moyen_G
    public double getChouVelPTot() throws SQLException{
        Statement req = connection.createStatement();
        ResultSet res = req.executeQuery("SELECT * FROM STATISTIQUE");
        
        int nbPartie = 0;
        double moy = 0.0;
        
        while(res.next()){
            Statistique stat = em.find(Statistique.class, res.getBigDecimal("Code_Joueur"));
            moy += stat.getChouvelMoyenP();
            nbPartie = res.getRow();
        }
        
        return (moy / nbPartie);
    }

        /*--------------------------POUR UN JOUEUR----------------------------*/

        //Stats du Code_Joueur
    public String getStats(BigDecimal Code_Joueur) throws SQLException{
        Statistique stat = em.find(Statistique.class, Code_Joueur);
        String stats;
        stats = "{\"Nb_Partie\":"+stat.getNbPartie()+", \"Nb_Victoire\":"+stat.getNbVictoire()
                +", \"Nb_Victoire_Moyen\":"+stat.getNbVictoireMoyen()
                +", \"Nb_Pts_Tot\":"+stat.getNbPtsTot()
                +", \"Score_Moyen\":"+stat.getScoreMoyen()
                +", \"Nb_Suite\":"+stat.getNbSuite()
                +", \"Suite_Moyen_G\":"+stat.getSuiteMoyenG()
                +", \"Nb_ChouVel\":"+stat.getNbChouvel()
                +", \"ChouVel_Moyen_P\":"+stat.getChouvelMoyenP()+"}";
        
        return stats;
    }

        //Une stat du Code_Joueur (à définir en paramètre)
    public String getStat(BigDecimal Code_Joueur, String Colonne) throws SQLException{
        Statistique stat = em.find(Statistique.class, Code_Joueur);
        String stats = "{\""+Colonne+"\":";
        
        switch(Colonne){
            case "Nb_Partie":
                stats += stat.getNbPartie()+"}";
                break;
        }
        
        return stats;
    }

    //MISE A JOUR 
    
        //Incrémentation Nb_Partie
    
        //Incrémentation Nb_Victoire
        
        //Incrémentation Nb_Victoire_Moyen
    
        //Incrémentation Nb_Pts_Tot
    
        //Incrémentation Score_Moyen
    
        //Incrémentation Nb_Suite
    
        //Incrémentation Suite_Moyen_G
    
        //Incrémentation Nb_ChouVel
    
        //Incrémentation ChouVel_Moyen_P
}
