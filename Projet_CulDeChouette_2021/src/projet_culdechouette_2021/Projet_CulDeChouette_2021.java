package projet_culdechouette_2021;

//import FctPojo
import FctPojo.FctJoueur;
import FctPojo.FctPartie;
import FctPojo.FctResumePartie;
import FctPojo.FctScorePartie;
import FctPojo.FctStatistique;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Projet_CulDeChouette_2021 {
    FctJoueur Joueur;
    FctPartie Partie;
    FctResumePartie Resume;
    FctScorePartie Score;
    FctStatistique Stat;
    
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Projet_CulDeChouette_2021PU");
        EntityManager em = emf.createEntityManager();
    }  
}
