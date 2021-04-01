package FctPojo;

//import POJO
import Pojo.Statistique;

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

        //Moyenne de partie jouée
    public float getMoy_Partie_Tot() throws SQLException{
        Statement req = connection.createStatement();
        ResultSet res = req.executeQuery("SELECT AVG(NB_PARTIE) FROM STATISTIQUE");
        
        float nbPartie = 0;
        
        while(res.next()){
            nbPartie = res.getFloat(1);
        }
        
        return nbPartie;
    }

        //Moyenne de partie gagnée
    public float getMoy_Partie_G_Tot() throws SQLException{
        Statement req = connection.createStatement();
        ResultSet res = req.executeQuery("SELECT AVG(NB_VICTOIRE) FROM STATISTIQUE");
        
        float moy = 0;
        
        while(res.next()){
            moy = res.getFloat(1);
        }
        
        return moy;
    }
    
        //Nombre de points totals gagnés
    public int getNb_Pts_Tot() throws SQLException{
        Statement req = connection.createStatement();
        ResultSet res = req.executeQuery("SELECT SUM(NB_PTS_TOT) FROM STATISTIQUE");
        
        int nbPts = 0;
        
        while(res.next()){
            nbPts = res.getInt(1);
        }
        
        return nbPts;
    }

        //Moyenne de points calculée à partir de Sc_Moyen
    public float getScore_Moy_Tot() throws SQLException{
        Statement req = connection.createStatement();
        ResultSet res = req.executeQuery("SELECT AVG(SCORE_MOYEN) FROM STATISTIQUE");
        
        float moy = 0;
        
        while(res.next()){
            moy = res.getFloat(1);
        }
        
        return moy;
    }

        //Moyenne de suite gagnée calculée à partir de Su_Moyen_G
    public float getSuite_Moy_G_Tot() throws SQLException{
        Statement req = connection.createStatement();
        ResultSet res = req.executeQuery("SELECT AVG(SUITE_MOYEN_G) FROM STATISTIQUE");
        
        float moy = 0;
        
        while(res.next()){
            moy = res.getFloat(1);
        }
        
        return moy;
    }

        //Moyenne de chouette velute perdue calculée à partir de CV_Moyen_G
    public float getChouVel_P_Tot() throws SQLException{
        Statement req = connection.createStatement();
        ResultSet res = req.executeQuery("SELECT AVG(CHOUVEL_MOYEN_P) FROM STATISTIQUE");
        
         float moy = 0;
        
        while(res.next()){
            moy = res.getFloat(1);
        }
        
        return moy;
    }

        /*--------------------------POUR UN JOUEUR----------------------------*/

        //Stats du Code_Joueur
    public String getStats(BigDecimal Code_Joueur) throws SQLException{
        Statistique stat = em.find(Statistique.class, Code_Joueur);
        String stats;
        stats = "{\"Nb_Partie\":"+stat.getNbPartie()
                +", \"Nb_Victoire\":"+stat.getNbVictoire()
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
        String stats = "";
        
        switch(Colonne){
            case "Nb_Partie":
                stats = stat.getNbPartie().toString();
                break;
            case "Nb_Victoire":
                stats = stat.getNbVictoire().toString();
                break;
            case "Nb_Victoire_Moyenne":
                stats = stat.getNbVictoireMoyenne().toString();
                break;
            case "Nb_Pts_Tot":
                stats = stat.getNbPtsTot().toString();
                break;
            case "Score_Moyen":
                stats = stat.getScoreMoyen().toString();
                break;
            case "Suite_Moyen_G":
                stats = stat.getSuiteMoyenG().toString();
                break;
            case "Chouvel_Moyen_P":
                stats = stat.getChouvelMoyenP().toString();
                break;
        }
        
        return stats;
    }

    //MISE A JOUR 
    
        //Maj totale des données
    public void majStats(BigDecimal Code_Joueur, boolean Victoire, int Points, int Suite, int SuiteG, int ChouVel, int ChouVelP){
        //appel inc_Partie
        incPartie(Code_Joueur);
        
        //appel maj_Victoire_Moyenne
        majVictoire_Moyenne(Code_Joueur, Victoire);
        
        //transformation int -> BigInteger de Points et appel maj_Score_Moyen
        BigInteger Pts = BigInteger.valueOf(Points);
        majScore_Moyen(Code_Joueur, Pts);
        
        //transformation int -> Biginteger de Suite + SuiteG et maj_Suite_Moyen_G
        BigInteger Ste = BigInteger.valueOf(Suite);
        BigInteger SteG = BigInteger.valueOf(SuiteG);
        majSuite_Moyen_G(Code_Joueur, Ste, SteG);
        
        //transformation int -> Biginteger de Suite + SuiteG et maj_Suite_Moyen_G
        BigInteger CV = BigInteger.valueOf(ChouVel);
        BigInteger CVP = BigInteger.valueOf(ChouVelP);
        majChouVel_Moyen_P(Code_Joueur, CV, CVP);
    }
    
        //Incrémentation Nb_Partie
    private void incPartie(BigDecimal Code_Joueur){
        Statistique stat = em.find(Statistique.class, Code_Joueur);
        stat.setNbPartie(stat.getNbPartie().add(new BigInteger("1")));
    }
    
        //Incrémentation Nb_Victoire
    private void incVictoire(BigDecimal Code_Joueur){
        Statistique stat = em.find(Statistique.class, Code_Joueur);
        stat.setNbVictoire(stat.getNbVictoire().add(new BigInteger("1")));
    }
        
        //Maj Nb_Victoire_Moyenne + appel inc_Victoire
    private void majVictoire_Moyenne(BigDecimal Code_Joueur, boolean Victoire){
        if(Victoire){
            incVictoire(Code_Joueur);
        }
        Statistique stat = em.find(Statistique.class, Code_Joueur);
        stat.setNbVictoireMoyenne(new BigDecimal(stat.getNbVictoire()).divide(new BigDecimal(stat.getNbPartie())));
    }
    
        //Incrémentation Nb_Pts_Tot
    private void incPts(BigDecimal Code_Joueur, BigInteger points){
        Statistique stat = em.find(Statistique.class, Code_Joueur);
        stat.setNbPtsTot(stat.getNbPtsTot().add(points));
    }
    
        //Maj Score_Moyen + appel inc_Pts
    private void majScore_Moyen(BigDecimal Code_Joueur, BigInteger points){
        incPts(Code_Joueur, points);
        Statistique stat = em.find(Statistique.class, Code_Joueur);
        stat.setScoreMoyen(new BigDecimal(stat.getNbPtsTot()).divide(new BigDecimal(stat.getNbPartie())));
    }
    
        //Incrémentation Nb_Suite
    private void incSuite(BigDecimal Code_Joueur, BigInteger suite){
        Statistique stat = em.find(Statistique.class, Code_Joueur);
        stat.setNbSuite(stat.getNbSuite().add(suite));
    }
    
        //Maj Suite_Moyen_G + appel inc_Suite
    private void majSuite_Moyen_G(BigDecimal Code_Joueur, BigInteger suite, BigInteger suiteG){
        Statistique stat = em.find(Statistique.class, Code_Joueur);
        BigDecimal moyG = new BigDecimal(stat.getNbSuite()).multiply(stat.getSuiteMoyenG());
        incSuite(Code_Joueur, suite);
        moyG.add(new BigDecimal(suiteG));
        stat.setSuiteMoyenG(moyG.divide(new BigDecimal(stat.getNbSuite())));
    }
    
        //Incrémentation Nb_ChouVel
    private void incChouVel(BigDecimal Code_Joueur, BigInteger chouvel){
        Statistique stat = em.find(Statistique.class, Code_Joueur);
        stat.setNbChouvel(stat.getNbChouvel().add(chouvel));
    }
    
        //Maj ChouVel_Moyen_P + appel inc_ChouVel
    private void majChouVel_Moyen_P(BigDecimal Code_Joueur, BigInteger chouvel, BigInteger chouvelP){
        Statistique stat = em.find(Statistique.class, Code_Joueur);
        BigDecimal moyP = new BigDecimal(stat.getNbChouvel()).multiply(stat.getChouvelMoyenP());
        incChouVel(Code_Joueur, chouvel);
        moyP.add(new BigDecimal(chouvelP));
        stat.setChouvelMoyenP(moyP.divide(new BigDecimal(stat.getNbChouvel())));
    }
}
