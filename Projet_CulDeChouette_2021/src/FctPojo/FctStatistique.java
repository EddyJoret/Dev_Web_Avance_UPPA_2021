package FctPojo;

//import POJO
import Pojo.Statistique;

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

        //Moyenne de partie gagnée calculée à partir de Nb_Victoire_Moyenne
    public BigDecimal getMoyPartieGTot() throws SQLException{
        Statement req = connection.createStatement();
        ResultSet res = req.executeQuery("SELECT * FROM STATISTIQUE");
        
        int nbPartie = 0;
        BigDecimal moy = new BigDecimal(0.0);
        
        while(res.next()){
            Statistique stat = em.find(Statistique.class, res.getBigDecimal("Code_Joueur"));
            moy.add(stat.getNbVictoireMoyenne());
            nbPartie = res.getRow();
        }
        
        return (moy.divide(new BigDecimal(nbPartie)));
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
    public BigDecimal getScoreMoyTot() throws SQLException{
        Statement req = connection.createStatement();
        ResultSet res = req.executeQuery("SELECT * FROM STATISTIQUE");
        
        int nbPartie = 0;
        BigDecimal moy = new BigDecimal(0.0);
        
        while(res.next()){
            Statistique stat = em.find(Statistique.class, res.getBigDecimal("Code_Joueur"));
            moy.add(stat.getScoreMoyen());
            nbPartie = res.getRow();
        }
        
        return (moy.divide(new BigDecimal(nbPartie)));
    }

        //Moyenne de suite gagnée calculée à partir de Su_Moyen_G
    public BigDecimal getSuiteMoyGTot() throws SQLException{
        Statement req = connection.createStatement();
        ResultSet res = req.executeQuery("SELECT * FROM STATISTIQUE");
        
        int nbPartie = 0;
        BigDecimal moy = new BigDecimal(0.0);
        
        while(res.next()){
            Statistique stat = em.find(Statistique.class, res.getBigDecimal("Code_Joueur"));
            moy.add(stat.getSuiteMoyenG());
            nbPartie = res.getRow();
        }
        
        return (moy.divide(new BigDecimal(nbPartie)));
    }

        //Moyenne de chouette velute perdue calculée à partir de CV_Moyen_G
    public BigDecimal getChouVelPTot() throws SQLException{
        Statement req = connection.createStatement();
        ResultSet res = req.executeQuery("SELECT * FROM STATISTIQUE");
        
        int nbPartie = 0;
        BigDecimal moy = new BigDecimal(0.0);
        
        while(res.next()){
            Statistique stat = em.find(Statistique.class, res.getBigDecimal("Code_Joueur"));
            moy.add(stat.getChouvelMoyenP());
            nbPartie = res.getRow();
        }
        
        return (moy.divide(new BigDecimal(nbPartie)));
    }

        /*--------------------------POUR UN JOUEUR----------------------------*/

        //Stats du Code_Joueur
    public String getStats(BigDecimal Code_Joueur) throws SQLException{
        Statistique stat = em.find(Statistique.class, Code_Joueur);
        String stats;
        stats = "{\"Nb_Partie\":"+stat.getNbPartie()+", \"Nb_Victoire\":"+stat.getNbVictoire()
                +", \"Nb_Victoire_Moyen\":"+stat.getNbVictoireMoyenne()
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
            case "Nb_Victoire":
                stats += stat.getNbVictoire()+"}";
                break;
            case "Nb_Victoire_Moyenne":
                stats += stat.getNbVictoireMoyenne()+"}";
                break;
            case "Nb_Pts_Tot":
                stats += stat.getNbPtsTot()+"}";
                break;
            case "Score_Moyen":
                stats += stat.getScoreMoyen()+"}";
                break;
            case "Suite_Moyen_G":
                stats += stat.getSuiteMoyenG()+"}";
                break;
            case "Chouvel_Moyen_P":
                stats += stat.getChouvelMoyenP()+"}";
                break;
        }
        
        return stats;
    }

    //MISE A JOUR 
    
        //Incrémentation Nb_Partie
    private void inc_Partie(BigDecimal Code_Joueur){
        Statistique stat = em.find(Statistique.class, Code_Joueur);
        stat.setNbPartie(stat.getNbPartie().add(new BigInteger("1")));
    }
    
        //Incrémentation Nb_Victoire
    private void inc_Victoire(BigDecimal Code_Joueur){
        Statistique stat = em.find(Statistique.class, Code_Joueur);
        stat.setNbVictoire(stat.getNbVictoire().add(new BigInteger("1")));
    }
        
        //Maj Nb_Victoire_Moyenne
    private void maj_Victoire_Moyenne(BigDecimal Code_Joueur){
        Statistique stat = em.find(Statistique.class, Code_Joueur);
        stat.setNbVictoireMoyenne(new BigDecimal(stat.getNbVictoire()).divide(new BigDecimal(stat.getNbPartie())));
    }
    
        //Incrémentation Nb_Pts_Tot
    private void inc_Pts(BigDecimal Code_Joueur, BigInteger points){
        Statistique stat = em.find(Statistique.class, Code_Joueur);
        stat.setNbPtsTot(stat.getNbPtsTot().add(points));
    }
    
        //Maj Score_Moyen
    private void maj_Score_Moyen(BigDecimal Code_Joueur){
        Statistique stat = em.find(Statistique.class, Code_Joueur);
        stat.setScoreMoyen(new BigDecimal(stat.getNbPtsTot()).divide(new BigDecimal(stat.getNbPartie())));
    }
    
        //Incrémentation Nb_Suite
    private void inc_Suite(BigDecimal Code_Joueur, BigInteger suite){
        Statistique stat = em.find(Statistique.class, Code_Joueur);
        stat.setNbSuite(stat.getNbSuite().add(suite));
    }
    
        //Maj Suite_Moyen_G + appel inc_Suite
    private void maj_Suite_Moyen_G(BigDecimal Code_Joueur, BigInteger suite, BigInteger suiteG){
        Statistique stat = em.find(Statistique.class, Code_Joueur);
        BigDecimal moyG = new BigDecimal(stat.getNbSuite()).multiply(stat.getSuiteMoyenG());
        inc_Suite(Code_Joueur, suite);
        moyG.add(new BigDecimal(suiteG));
        stat.setSuiteMoyenG(moyG.divide(new BigDecimal(stat.getNbSuite())));
    }
    
        //Incrémentation Nb_ChouVel
    private void inc_ChouVel(BigDecimal Code_Joueur, BigInteger chouvel){
        Statistique stat = em.find(Statistique.class, Code_Joueur);
        stat.setNbChouvel(stat.getNbChouvel().add(chouvel));
    }
    
        //Maj ChouVel_Moyen_P + appel inc_ChouVel
    private void maj_ChouVel_Moyen_p(BigDecimal Code_Joueur, BigInteger chouvel, BigInteger chouvelP){
        Statistique stat = em.find(Statistique.class, Code_Joueur);
        BigDecimal moyP = new BigDecimal(stat.getNbChouvel()).multiply(stat.getChouvelMoyenP());
        inc_ChouVel(Code_Joueur, chouvel);
        moyP.add(new BigDecimal(chouvelP));
        stat.setChouvelMoyenP(moyP.divide(new BigDecimal(stat.getNbChouvel())));
    }
}
