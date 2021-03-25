package FctPojo;

//import POJO
import Pojo.Resumepartie;

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

public class FctResumePartie {
    
    Connection connection;
    
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("Projet_CulDeChouette_2021PU");
    EntityManager em = emf.createEntityManager();
    
    //INITIALISATION
    
        //Initialisation de tous les champs lié au Code_Partie (passé en
        //paramètre) et de Num_Lance_Des (calculé) à 0
    public void init_Tour_Resume_Partie(BigInteger Code_Partie) throws SQLException{
        connection = DriverManager.getConnection("jdbc:oracle:thin:@//scinfe098.univ-pau.fr:1521/etud.univ-pau.fr", "pcazalis", "pcazalis");
        
        int numLance = 0;
        PreparedStatement reqSelectParam = connection.prepareStatement("SELECT * FROM RESUMEPARTIE WHERE CODE_PARTIE = ?");
        reqSelectParam.setInt(1, Code_Partie.intValue());
        ResultSet res = reqSelectParam.executeQuery();
        while(res.next()){
            numLance = res.getRow();
        }
        
        PreparedStatement reqInsertParam = connection.prepareStatement("INSERT INTO RESUMEPARTIE VALUES (?, ?, 0, 0, 0)");
        reqInsertParam.setInt(1, Code_Partie.intValue());
        reqInsertParam.setInt(2, numLance+1);
        
        int nb = reqInsertParam.executeUpdate();
        System.out.println("Nombre de ligne ajoutée: " + nb);
    }
    
    //AFFICHAGE
    
        /*----------------------POUR TOUTES LES PARTIES-----------------------*/
    
        //Nombre total de lancé de dès
    public int total_Lance() throws SQLException{
        Statement req = connection.createStatement();
        ResultSet res = req.executeQuery("SELECT * FROM RESUMEPARTIE");
        
        int nbLance = 0;
        while(res.next()){
            nbLance = res.getRow();
        }
        return nbLance;
    }
    
        //Moyenne totale des valeurs des dès
    //fct de calcul du score à faire avant
    
        //Nombre total de fois qu'une valeur de dès précise est tombée
    public int total_Nb_Lance_De(int valeur) throws SQLException{
        Statement req = connection.createStatement();
        ResultSet res = req.executeQuery("SELECT * FROM RESUMEPARTIE");
        BigInteger Val = BigInteger.valueOf(valeur);
        
        int nbLance = 0;
        while(res.next()){
            Resumepartie resume = em.find(Resumepartie.class, res.getInt("Code_Partie"));
            if(resume.getDes1() == Val){
                nbLance += 1;
            }
            if(resume.getDes2() == Val){
                nbLance += 1;
            }
            if(resume.getDes3() == Val){
                nbLance += 1;
            }
        }
        return nbLance;
    }
        
        //Moyenne totale d'une valeur de dès
    public double total_Moyenne_Lance_De(int valeur) throws SQLException{
        double total = new Double(total_Lance());
        double nbLance = new Double(total_Nb_Lance_De(valeur));
        return (nbLance/total);
    }
    
        /*--------------------------POUR UNE PARTIE---------------------------*/
    
        //Nombre de lancé de dès
    public int lance(BigInteger Code_Partie) throws SQLException{
        PreparedStatement reqSelectParam = connection.prepareStatement("SELECT * FROM RESUMEPARTIE WHERE CODE_PARTIE = ?");
        reqSelectParam.setInt(1, Code_Partie.intValue());
        ResultSet res = reqSelectParam.executeQuery();
        int nbLance = 0;
        while(res.next()){
            nbLance = res.getRow();
        }
        return nbLance;
    }
    
        //Valeurs des dès tombées
    //fct de calcul du score à faire avant
    
        //Moyenne des valeurs des dès
    //fct de calcul du score à faire avant
    
        //Nombre de fois qu'une valeur de dès précise est tombée
    public int nb_Lance_De(BigInteger Code_Partie, int valeur) throws SQLException{
        PreparedStatement reqSelectParam = connection.prepareStatement("SELECT * FROM RESUMEPARTIE WHERE CODE_PARTIE = ?");
        reqSelectParam.setInt(1, Code_Partie.intValue());
        ResultSet res = reqSelectParam.executeQuery();
        //BigInteger Val = BigInteger.valueOf(valeur);
        
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
    
        //Moyenne d'une valeur de dès
    public double moyenne_Lance_De(BigInteger Code_Partie, int valeur) throws SQLException{
        double total = new Double(lance(Code_Partie));
        double nbLance = new Double(nb_Lance_De(Code_Partie, valeur));
        return (nbLance/total);
    }
    
        //3 valeurs du dernier lancé de dès
    public int[] derniers_Des(BigInteger Code_Partie) throws SQLException{
        PreparedStatement reqSelectParam = connection.prepareStatement("SELECT * FROM RESUMEPARTIE WHERE CODE_PARTIE = ?");
        reqSelectParam.setInt(1, Code_Partie.intValue());
        ResultSet res = reqSelectParam.executeQuery();
        res.last();
        int [] des = {res.getInt("DES_1"),res.getInt("DES_2"),res.getInt("DES_3")};
        return des;
    }
    
    //MISE A JOUR
    
        //Maj de Des_1, Des_2 et Des_3 lors de la fin du tout d'un joueur
    public void maj_Des(BigInteger Code_Partie, int [] des) throws SQLException{
        int nbLance = lance(Code_Partie);
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
}
