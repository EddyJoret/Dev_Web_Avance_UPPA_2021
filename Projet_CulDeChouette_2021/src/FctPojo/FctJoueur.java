package FctPojo;

import Pojo.Joueur;
import FctPojo.FctStatistique;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class FctJoueur {
    
    Connection connection;
    
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("Projet_CulDeChouette_2021PU");
    EntityManager em = emf.createEntityManager();
    
    FctStatistique fctstat;
    
    
    //INITIALISATION
    
        //Initialisation des champs récupérer lors de l'enregistrement 
        //+ création Code_Joueur correspondant 
        //+ création STATISTIQUE
     public void InitJoueur(String Pseudo, String Mdp, BigInteger Age, Character Sexe, String Ville) throws SQLException{
        connection = DriverManager.getConnection("jdbc:oracle:thin:@//scinfe098.univ-pau.fr:1521/etud.univ-pau.fr", "pcazalis", "pcazalis");
        int numJoueur = 0;
        int codeJoueur = 1;
        
        PreparedStatement reqSelectParam = connection.prepareStatement("SELECT * FROM JOUEUR WHERE CODE_JOUEUR = ?");
        ResultSet res = reqSelectParam.executeQuery();
        while(res.next()){
            numJoueur = res.getRow();
        }
        PreparedStatement reqParam = connection.prepareStatement("INSERT INTO JOUEUR VALUES (?, 0, 0, 0, 0, 0)");
        reqParam.setInt(1, codeJoueur+1);
        
    }
    
    //AFFICHAGE
    
        /*-----------------------POUR TOUT LES JOUEURS------------------------*/

        //Nombre total de joueur
     public int getNbTotJoueur() throws SQLException{
        Statement req = connection.createStatement();
        ResultSet res = req.executeQuery("SELECT COUNT(*) FROM JOUEUR");
        
        int nbJoueurTot = 0;
        
        while(res.next()){
            nbJoueurTot = res.getInt(1);
        }
        return nbJoueurTot;
     }

        //Liste des pseudos
     public ArrayList<String> listePseudo() throws SQLException{
        Statement req = connection.createStatement();
        ResultSet res = req.executeQuery("SELECT PSEUDO FROM JOUEUR");
        ArrayList<String> listP = new ArrayList<String>();
        while(res.next()){
            listP.add(res.getString(1));
        }
        return listP;
     }

        //Moyenne d'âge calculée à partir de Age
     public float moyAge() throws SQLException{
        Statement req = connection.createStatement();
        ResultSet res = req.executeQuery("SELECT AVG(AGE) FROM JOUEUR");
        
        float nbMoyAge = 0;
        
        while(res.next()){
            nbMoyAge = res.getFloat(1);
        }
        
        return nbMoyAge;
     }

        //Moyenne d'homme et de femmes calculée à partir de Sexe
      public float moySexe() throws SQLException{
        Statement req = connection.createStatement();
        ResultSet res = req.executeQuery("SELECT COUNT(SEXE) FROM JOUEUR WHERE SEXE = H");
        
        float nbMoySexe = 0;
        
        while(res.next()){
            nbMoySexe = res.getFloat(1);
        }
        
        return nbMoySexe/(float)getNbTotJoueur();
     }

        //Liste des villes à partir de Ville (sans répétitions si possible)
      public ArrayList<String> listVilles() throws SQLException{
        Statement req = connection.createStatement();
        ResultSet res = req.executeQuery("SELECT DISTINCT VILLE FROM JOUEUR");
        ArrayList<String> liste = new ArrayList<String>();
        while(res.next()){
            liste.add(res.getString(1));
        }
        return liste;
      }

        /*--------------------------POUR UN JOUEUR----------------------------*/

        //Informations du Pseudo
      public String infosPseudo(String Pseudo) throws SQLException{
          Joueur info = em.find(Joueur.class, Pseudo);
          String stat;
          stat = "{\"Age\":"+info.getAge()
                +", \"Sexe\":"+info.getSexe()
                +", \"Ville\":"+info.getVille()+"}";
          return stat;
      }
      
      //Stats du pseudo
      public String statsPseudo(String Pseudo)throws SQLException{
          PreparedStatement req = connection.prepareStatement("SELECT CODE_JOUEUR FROM JOUEUR WHERE PSEUDO = ?");
          req.setString(1, Pseudo);
          ResultSet res = req.executeQuery();
          String resultat = "";
          while(res.next()){
              resultat = fctstat.getStats(res.getBigDecimal(1));
          }
          return resultat;
      }
    
        //Une information du Pseudo (à définir en paramètre)
      public String infoPseudo(String Pseudo, String Colonne )throws SQLException{
          Joueur info = em.find(Joueur.class, Pseudo);
        String stats = "";
        
        switch(Colonne){
            case "Age":
                stats = info.getAge().toString();
                break;
            case "Sexe":
                stats = info.getSexe().toString();
                break;
            case "Ville":
                stats = info.getVille().toString();
                break;
        }
        return stats;
      }
      
      //Une stat du pseudo
      public String statPseudo(String Pseudo, String Colonne)throws SQLException{
          PreparedStatement req = connection.prepareStatement("SELECT CODE_JOUEUR FROM JOUEUR WHERE PSEUDO = ?");
          req.setString(1, Pseudo);
          ResultSet res = req.executeQuery();
          String resultat = "";
          while(res.next()){
              resultat = fctstat.getStat(res.getBigDecimal(1), Colonne);
          }
          return resultat;
      }
    
    //MISE A JOUR
    
        //Changement MDP
      public void changeMdp(String Pseudo, String Mdp)throws SQLException{
          Joueur jou = em.find(Joueur.class, Pseudo);
          jou.setMdp(Mdp);
      }
    
        //Changement Age
      public void changeAge(String Pseudo, BigInteger Age)throws SQLException{
          Joueur jou = em.find(Joueur.class, Pseudo);
          jou.setAge(Age);
      }
    
        //Changement Sexe
      public void changeSexe(String Pseudo, Character Sexe)throws SQLException{
          Joueur jou = em.find(Joueur.class, Pseudo);
          jou.setSexe(Sexe);
      }
    
        //Changement Ville
      public void changeVille(String Pseudo, String Ville)throws SQLException{
          Joueur jou = em.find(Joueur.class, Pseudo);
          jou.setVille(Ville);
      }
}
