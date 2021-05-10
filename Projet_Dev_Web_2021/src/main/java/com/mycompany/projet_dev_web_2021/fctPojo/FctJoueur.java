package com.mycompany.projet_dev_web_2021.fctPojo;

//import POJO
import com.mycompany.projet_dev_web_2021.pojo.Joueur;

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

//import PERSISTENCE
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

//import EXCEPTION
import java.sql.SQLException;


public class FctJoueur {
    
    Connection connection;
    
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("Projet_CulDeChouette_2021PU");
    EntityManager em = emf.createEntityManager();
    
    FctStatistique FctStat = new FctStatistique();
    
    public FctJoueur() throws SQLException{
       
    }
    //INITIALISATION
    
        //Initialisation des champs récupérés lors de l'enregistrement 
        //+ création Code_Joueur correspondant 
        //+ création STATISTIQUE correspondant
    public void InitJoueur(String Pseudo, String Mdp, int Age, String Sexe, String Ville) throws SQLException{
        connection = DriverManager.getConnection("jdbc:oracle:thin:@//scinfe098.univ-pau.fr:1521/etud.univ-pau.fr", "pcazalis", "pcazalis");
        int numJoueur = 0;
        
        Statement reqSelectParam = connection.createStatement();
        ResultSet res = reqSelectParam.executeQuery("SELECT COUNT(*) FROM JOUEUR ");
        while(res.next()){
            numJoueur = res.getInt(1)+1;
        }
        PreparedStatement reqInsertParam = connection.prepareStatement("INSERT INTO JOUEUR VALUES (?, ?, ?, ?, ?, ?)");
        reqInsertParam.setInt(1, numJoueur);
        reqInsertParam.setString(2, Pseudo);
        reqInsertParam.setString(3, Mdp);
        reqInsertParam.setInt(4, Age);
        reqInsertParam.setString(5, Sexe);
        reqInsertParam.setString(6, Ville);
        FctStat.InitStat(new BigDecimal(numJoueur));
        int nb = reqInsertParam.executeUpdate();
        System.out.println("Nombre de ligne ajoutée dans Joueur: " + nb);
        System.out.println(connection);
    }
    
    public void getConnexion(){
        System.out.println(connection);
    }
    
    //AFFICHAGE
    
        /*-----------------------POUR TOUT LES JOUEURS------------------------*/

        //Nombre total de joueur
    public int getNb_Tot_Joueur() throws SQLException{
        Statement req = connection.createStatement();
        ResultSet res = req.executeQuery("SELECT COUNT(*) FROM JOUEUR");
        
        int nbJoueurTot = 0;
        
        while(res.next()){
            nbJoueurTot = res.getInt(1);
        }
        return nbJoueurTot;
    }

        //Liste des pseudos
    public ArrayList<String> getListe_Pseudo() throws SQLException{
        Statement req = connection.createStatement();
        ResultSet res = req.executeQuery("SELECT PSEUDO FROM JOUEUR");
        ArrayList<String> listP = new ArrayList<String>();
        while(res.next()){
            listP.add(res.getString(1));
        }
        return listP;
    }

        //Moyenne d'âge
    public float getMoy_Age() throws SQLException{
        Statement req = connection.createStatement();
        ResultSet res = req.executeQuery("SELECT AVG(AGE) FROM JOUEUR");
        
        float nbMoyAge = 0;
        
        while(res.next()){
            nbMoyAge = res.getFloat(1);
        }
        
        return nbMoyAge;
    }

        //Moyenne d'homme et de femmes
    public float getMoy_Sexe() throws SQLException{
        Statement req = connection.createStatement();
        ResultSet res = req.executeQuery("SELECT COUNT(SEXE) FROM JOUEUR WHERE SEXE = H");
        
        float nbMoySexe = 0;
        
        while(res.next()){
            nbMoySexe = res.getFloat(1);
        }
        
        return nbMoySexe/(float)getNb_Tot_Joueur();
    }

        //Liste des villes
    public ArrayList<String> getListe_Villes() throws SQLException{
        Statement req = connection.createStatement();
        ResultSet res = req.executeQuery("SELECT DISTINCT VILLE FROM JOUEUR");
        ArrayList<String> liste = new ArrayList<String>();
        while(res.next()){
            liste.add(res.getString(1));
        }
        return liste;
    }

        /*--------------------------POUR UN JOUEUR----------------------------*/
      
        //Pseudo
    public String getPseudo(BigDecimal Code_Joueur){
          Joueur jou = em.find(Joueur.class, Code_Joueur);
          return jou.getPseudo();
    }
    
        //Code_Joueur
    public BigDecimal getCode_Joueur(String Pseudo){
          Joueur jou = em.find(Joueur.class, Pseudo);
          return jou.getCodeJoueur();
    }

        //Informations du Pseudo
    public String getInfos_Pseudo(String Pseudo) throws SQLException{
          Joueur info = em.find(Joueur.class, Pseudo);
          String stat;
          stat = "{\"Age\":"+info.getAge()
                +", \"Sexe\":"+info.getSexe()
                +", \"Ville\":"+info.getVille()+"}";
          return stat;
    }
      
        //Stats du pseudo
    public String getStats_Pseudo(String Pseudo)throws SQLException{
          PreparedStatement req = connection.prepareStatement("SELECT CODE_JOUEUR FROM JOUEUR WHERE PSEUDO = ?");
          req.setString(1, Pseudo);
          ResultSet res = req.executeQuery();
          String resultat = "";
          while(res.next()){
              resultat = FctStat.getStats(res.getBigDecimal(1));
          }
          return resultat;
    }
    
        //Une information du Pseudo (à définir en paramètre)
    public String getInfo_Pseudo(String Pseudo, String Colonne )throws SQLException{
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
      
      //Une stat du pseudo (à définir en paramètre)
    public String getStat_Pseudo(String Pseudo, String Colonne)throws SQLException{
          PreparedStatement req = connection.prepareStatement("SELECT CODE_JOUEUR FROM JOUEUR WHERE PSEUDO = ?");
          req.setString(1, Pseudo);
          ResultSet res = req.executeQuery();
          String resultat = "";
          while(res.next()){
              resultat = FctStat.getStat(res.getBigDecimal(1), Colonne);
          }
          return resultat;
    }
    
    //MISE A JOUR
    
        //Changement MDP
    public void majMdp(String Pseudo, String Mdp)throws SQLException{
          Joueur jou = em.find(Joueur.class, Pseudo);
          jou.setMdp(Mdp);
    }
    
        //Changement Age
    public void majAge(String Pseudo, BigInteger Age)throws SQLException{
          Joueur jou = em.find(Joueur.class, Pseudo);
          jou.setAge(Age);
    }
    
        //Changement Sexe
    public void majSexe(String Pseudo, Character Sexe)throws SQLException{
          Joueur jou = em.find(Joueur.class, Pseudo);
          jou.setSexe(Sexe);
    }
    
        //Changement Ville
    public void majVille(String Pseudo, String Ville)throws SQLException{
          Joueur jou = em.find(Joueur.class, Pseudo);
          jou.setVille(Ville);
    }
}
