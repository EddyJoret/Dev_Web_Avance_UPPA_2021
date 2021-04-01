package FctPojo;

//import POJO
//import Pojo.Partie;

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
import java.sql.Types;

//import PERSISTENCE
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

//import EXCEPTION
import java.sql.SQLException;

public class FctPartie {
    
    Connection connection;
    
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("Projet_CulDeChouette_2021PU");
    EntityManager em = emf.createEntityManager();
    
    FctResumePartie FctResume;
    FctScorePartie FctScore;
    FctJoueur FctJoueur;
    
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
    public ArrayList<String> getJoueur(BigDecimal Code_Partie) throws SQLException{
        PreparedStatement reqSelectParam = connection.prepareStatement("SELECT * FROM PARTIE WHERE Code_PARTIE = ?");
        reqSelectParam.setBigDecimal(1, Code_Partie);
        ResultSet res = reqSelectParam.executeQuery();
        ArrayList<String> liste = new ArrayList<String>();
        while(res.next()){
            int i = 2;
            while (res.getBigDecimal(i) != null && i < 8){
                liste.add(FctJoueur.getPseudo(res.getBigDecimal(i)));
                i++;
            }
        }
        return liste;
    }
    
        //Partie terminée?
    public boolean getFinish(BigDecimal Code_Partie) throws SQLException{
        PreparedStatement reqSelectParam = connection.prepareStatement("SELECT TERMINE FROM PARTIE WHERE Code_PARTIE = ?");
        reqSelectParam.setBigDecimal(1, Code_Partie);
        ResultSet res = reqSelectParam.executeQuery();
        boolean termine = false;
        while(res.next()){
            termine = res.getString(1).equals("T");
        }
        return termine;
    }
    
        //Statistiques des Code_Joueur
    public ArrayList<String> getStats_Joueurs(BigDecimal Code_Partie) throws SQLException{
        PreparedStatement reqSelectParam = connection.prepareStatement("SELECT * FROM PARTIE WHERE Code_PARTIE = ?");
        reqSelectParam.setBigDecimal(1, Code_Partie);
        ResultSet res = reqSelectParam.executeQuery();
        String pseudo = "";
        ArrayList<String> stats = new ArrayList<String>();
        while(res.next()){
            int i = 2;
            while (res.getBigDecimal(i) != null && i < 8){
                pseudo = FctJoueur.getPseudo(res.getBigDecimal(i));
                stats.add("{Pseudo:"+pseudo+FctJoueur.getStats_Pseudo(pseudo)+"}");
                i++;
            }
        }
        return stats;
    }
    
        //Statistiques d'un Code_Joueur
    public String getStats_Joueur(String Pseudo) throws SQLException{
        String stats = "";
        stats = FctJoueur.getStats_Pseudo(Pseudo);
        return stats;
    }
    
        //Statiqtique défini d'un Code_Joueur
    public String getStat_Joueur(String Pseudo, String Colonne) throws SQLException{
        String stats = "";
        stats = FctJoueur.getStat_Pseudo(Pseudo,Colonne);
        return stats;
    }
    
    //MISE A JOUR
    
        //Score du Code_Joueur d'un Code_Partie
    public void majScore(BigDecimal Code_Partie, String Pseudo, int Score) throws SQLException{
        BigInteger code_joueur = new BigInteger(FctJoueur.getCode_Joueur(Pseudo).toString());
        FctScore.incScore(code_joueur, new BigInteger(Code_Partie.toString()), Score);
    }
    
        //Nb_Suite_G du Code_Joueur d'un Code_Partie
    public void majSuite_G(BigDecimal Code_Partie, String Pseudo, int Suite) throws SQLException{
        BigInteger code_joueur = new BigInteger(FctJoueur.getCode_Joueur(Pseudo).toString());
        FctScore.incNb_Suite_G(code_joueur, new BigInteger(Code_Partie.toString()), Suite);
    }
    
        //Nb_ChouVel_P du Code_Joueur d'un Code_Partie
    public void majChouVel_P(BigDecimal Code_Partie, String Pseudo, int ChouVel) throws SQLException{
        BigInteger code_joueur = new BigInteger(FctJoueur.getCode_Joueur(Pseudo).toString());
        FctScore.incNb_ChouVel_P(code_joueur, new BigInteger(Code_Partie.toString()), ChouVel);
    }
    
        //Enregistrement du dernier lancé de dès effectué de Code_Partie

    public void initLance(BigDecimal Code_Partie, int[] des) throws SQLException{
        FctResume.initTourResumePartie(new BigInteger(Code_Partie.toString()));
        FctResume.majDes(new BigInteger(Code_Partie.toString()), des);
    }
}
