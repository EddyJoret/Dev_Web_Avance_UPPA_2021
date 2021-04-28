package com.mycompany.projet_dev_web_2021.fctPojo;

//import POJO
//import Pojo.Resumepartie;

//import TYPE
//import java.math.BigDecimal;
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

public class FctResumePartie {
    
    Connection connection;
    
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("Projet_CulDeChouette_2021PU");
    EntityManager em = emf.createEntityManager();
    
    //INITIALISATION
    
        //Initialisation de tous les champs lié au Code_Partie (passé en
        //paramètre) et de Num_Lance_Des (calculé) à 0
    public void initTourResumePartie(BigInteger Code_Partie) throws SQLException{
        connection = DriverManager.getConnection("jdbc:oracle:thin:@//scinfe098.univ-pau.fr:1521/etud.univ-pau.fr", "pcazalis", "pcazalis");
        
        int numLance = 0;
        PreparedStatement reqSelectParam = connection.prepareStatement("SELECT COUNT(NUM_LANCE_DES) FROM RESUMEPARTIE WHERE CODE_PARTIE = ?");
        reqSelectParam.setInt(1, Code_Partie.intValue());
        ResultSet res = reqSelectParam.executeQuery();
        while(res.next()){
            numLance = res.getInt(1);
        }
        
        PreparedStatement reqInsertParam = connection.prepareStatement("INSERT INTO RESUMEPARTIE VALUES (?, ?, 0, 0, 0)");
        reqInsertParam.setInt(1, Code_Partie.intValue());
        reqInsertParam.setInt(2, numLance+1);
        
        int nb = reqInsertParam.executeUpdate();
        System.out.println("Nombre de ligne ajoutée: " + nb);
    }
    
    //AFFICHAGE
    
        /*----------------------POUR TOUTES LES PARTIES-----------------------*/
    
        //Nombre total de lancer de dès
    public int getTotal_Lance() throws SQLException{
        Statement req = connection.createStatement();
        ResultSet res = req.executeQuery("SELECT COUNT(*) FROM RESUMEPARTIE");
        
        int nbLance = 0;
        while(res.next()){
            nbLance = res.getInt(1);
        }
        return nbLance;
    }
    
        //Nombre total de fois qu'une valeur de dès précise est tombée (à définir en paramètre)
    public int getTotal_Nb_Lance_De(int valeur) throws SQLException{
        Statement req = connection.createStatement();
        ResultSet res = req.executeQuery("SELECT * FROM RESUMEPARTIE");
        
        int nbLance = 0;
        while(res.next()){
            if(res.getInt("Des_1") == valeur){
                nbLance += 1;
            }
            if(res.getInt("Des_2") == valeur){
                nbLance += 1;
            }
            if(res.getInt("Des_3") == valeur){
                nbLance += 1;
            }
        }
        return nbLance;
    }
        
        //Moyenne totale d'une valeur de dès (à définir en paramètre)
    public double getTotal_Moyenne_Lance_De(int valeur) throws SQLException{
        double total = new Double(getTotal_Lance());
        double nbLance = new Double(getTotal_Nb_Lance_De(valeur));
        return (nbLance/total);
    }
    
        /*--------------------------POUR UNE PARTIE---------------------------*/
    
        //Nombre de lancé de dès
    public int getNb_Lance(BigInteger Code_Partie) throws SQLException{
        PreparedStatement reqSelectParam = connection.prepareStatement("SELECT COUNT(*) FROM RESUMEPARTIE WHERE CODE_PARTIE = ?");
        reqSelectParam.setInt(1, Code_Partie.intValue());
        ResultSet res = reqSelectParam.executeQuery();
        int nbLance = 0;
        while(res.next()){
            nbLance = res.getInt(1);
        }
        return nbLance;
    }
    
        //Nombre de fois qu'une valeur de dès précise est tombée (à définir en paramètre)
    public int getNb_Lance_De(BigInteger Code_Partie, int valeur) throws SQLException{
        PreparedStatement reqSelectParam = connection.prepareStatement("SELECT * FROM RESUMEPARTIE WHERE CODE_PARTIE = ?");
        reqSelectParam.setInt(1, Code_Partie.intValue());
        ResultSet res = reqSelectParam.executeQuery();
        
        int nbLance = 0;
        while(res.next()){
            if(res.getInt("DES_1") == valeur){
                nbLance += 1;
            }
            if(res.getInt("DES_2") == valeur){
                nbLance += 1;
            }
            if(res.getInt("DES_3") == valeur){
                nbLance += 1;
            }
        }
        return nbLance;
    }
    
        //Moyenne d'une valeur de dès (à définir en paramètre)
    public double getMoyenne_Lance_De(BigInteger Code_Partie, int valeur) throws SQLException{
        double total = new Double(getNb_Lance(Code_Partie));
        double nbLance = new Double(getNb_Lance_De(Code_Partie, valeur));
        return (nbLance/total);
    }
    
        //3 valeurs du dernier lancé de dès
    public int[] getDerniers_Des(BigInteger Code_Partie) throws SQLException{
        PreparedStatement reqSelectParam = connection.prepareStatement("SELECT * FROM RESUMEPARTIE WHERE CODE_PARTIE = ?");
        reqSelectParam.setInt(1, Code_Partie.intValue());
        ResultSet res = reqSelectParam.executeQuery();
        res.last();
        int [] des = {res.getInt("DES_1"),res.getInt("DES_2"),res.getInt("DES_3")};
        return des;
    }
    
        //3 valeurs lancé de dès choisit (à définir en paramètre)
    public int[] getDes(BigInteger Code_Partie, int numLance) throws SQLException{
        PreparedStatement reqSelectParam = connection.prepareStatement("SELECT * FROM RESUMEPARTIE WHERE CODE_PARTIE = ? and NUM_LANCE_DES = ?");
        reqSelectParam.setInt(1, Code_Partie.intValue());
        reqSelectParam.setInt(2, numLance);
        ResultSet res = reqSelectParam.executeQuery();
        res.last();
        int [] des = {res.getInt("DES_1"),res.getInt("DES_2"),res.getInt("DES_3")};
        return des;
    }
    
        //Score des 3 derniers dès
    public int getScore_Des(BigInteger Code_Partie) throws SQLException{
        int[] derDes = getDerniers_Des(Code_Partie);
        int nbLance = getNb_Lance(Code_Partie);
        
        if(nbLance > 1){
            int[] des = getDes(Code_Partie, nbLance-1);
            if(suite(des, derDes)){ //SUITE
                //besoin thread pour savoir le perdant
                //return -10;
            }
        }else if(derDes[0] == derDes[1] && (derDes[0] + derDes[1] == derDes[2])){ //CHOUETTE VELUTE
            //besoin thread pour savoir le gagnant
            //return velute(derDes[2]);
        }else if(derDes[0] == derDes[1] && derDes[1] == derDes[2]){ //CUL DE CHOUETTE
            return culDeChouette(derDes[0]);
        }else if(derDes[0] == derDes[1]){ //CHOUETTE
            return derDes[0] * derDes[1];
        }else if(derDes[0] + derDes[1] == derDes[2]){ //VELUTE
            return derDes[2] * derDes[2];
        }
        return 0;
    }
    
    //MISE A JOUR
    
        //Maj de Des_1, Des_2 et Des_3 lors de la fin du tout d'un joueur
    public void majDes(BigInteger Code_Partie, int [] des) throws SQLException{
        int nbLance = getNb_Lance(Code_Partie);
        int nb;
        PreparedStatement reqUpdateParam = connection.prepareStatement("UPDATE RESUMEPARTIE SET DES_1 = ?, DES_2 = ?, DES_3 = ? WHERE CODE_PARTIE = ? AND NUM_LANCE_DES = ?");
        reqUpdateParam.setInt(1,des[0]);
        reqUpdateParam.setInt(2,des[1]);
        reqUpdateParam.setInt(3,des[2]);
        reqUpdateParam.setInt(4, Code_Partie.intValue());
        reqUpdateParam.setInt(5, nbLance);
        nb = reqUpdateParam.executeUpdate();
        System.out.println(nb + " ligne ont été update");
    }
    
    //FCT FALCULTATIVE
    
        //Savoir si il y a une suite
    public boolean suite(int[] des, int [] derDes){
        return suite1(des, derDes) || suite2(des, derDes);
    }
    
        //Savoir si il y a une suite 1-2-3 => 3-4-5
    public boolean suite1(int [] des, int[] derDes){
        boolean un = false;
        boolean deux = false;
        boolean trois = false;
        boolean derTrois = false;
        boolean derQuatre = false;
        boolean derCinq = false;
        
        for(int i: des){
            if(i == 1){
                un = true;
            }
            if(i == 2){
                deux = true;
            }
            if(i == 3){
                trois = true;
            }
        }
        
        for(int i : derDes){
            if(i == 3){
                derTrois = true;
            }
            if(i == 4){
                derQuatre = true;
            }
            if(i == 5){
                derCinq = true;
            }
        }
        
        return (un && deux && trois) && (derTrois && derQuatre && derCinq);
    }
    
        //Savoir si il y a une suite 2-3-4 => 4-5-6
    public boolean suite2(int [] des, int[] derDes){
        boolean deux = false;
        boolean trois = false;
        boolean quatre = false;
        boolean derQuatre = false;
        boolean derCinq = false;
        boolean derSix = false;
        
        for(int i: des){
            if(i == 2){
                deux = true;
            }
            if(i == 3){
                trois = true;
            }
            if(i == 4){
                quatre = true;
            }
        }
        
        for(int i : derDes){
            if(i == 4){
                derQuatre = true;
            }
            if(i == 5){
                derCinq = true;
            }
            if(i == 6){
                derSix = true;
            }
        }
        
        return (deux && trois && quatre) && (derQuatre && derCinq && derSix);
    }
    
        //Calcul point d'un Cul de Chouette
    public int culDeChouette(int des){
        switch(des){
            case 1:
                return 50;
            case 2:
                return 60;
            case 3:
                return 70;
            case 4:
                return 80;
            case 5:
                return 90;
            default:
                return 100;
        }
    }
}
