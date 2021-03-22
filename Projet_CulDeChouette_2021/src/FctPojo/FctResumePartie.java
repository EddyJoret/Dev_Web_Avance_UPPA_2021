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
    public int total_Lance_De(int valeur) throws SQLException{
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
    public double moyenne_Lance_De(int valeur) throws SQLException{
        double total = new Double(total_Lance());
        double nbLance = new Double(total_Lance_De(valeur));
        return (nbLance/total);
    }
    
        /*--------------------------POUR UNE PARTIE---------------------------*/
    
        //Nombre de lancé de dès
    public int lance(BigInteger Code_Partie) throws SQLException{
        int numLance = 0;
        PreparedStatement reqSelectParam = connection.prepareStatement("SELECT * FROM RESUMEPARTIE WHERE CODE_PARTIE = ?");
        reqSelectParam.setInt(1, Code_Partie.intValue());
        ResultSet res = reqSelectParam.executeQuery();
        while(res.next()){
            numLance = res.getRow();
        }
        return numLance;
    }
    
        //Valeurs des dès tombées
    
        //Moyenne des valeurs des dès 
    
        //Nombre de fois qu'une valeur de dès précise est tombée
    
        //Moyenne d'une valeur de dès
    
        //3 valeurs du dernier lancé de dès
    
    //MISE A JOUR
    
        //Maj de Des_1, Des_2 et Des_3 lors de la fin du tout d'un joueur
}
