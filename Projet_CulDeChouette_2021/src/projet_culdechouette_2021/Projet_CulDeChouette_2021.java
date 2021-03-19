package projet_culdechouette_2021;

import java.math.BigDecimal;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Projet_CulDeChouette_2021 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Projet_CulDeChouette_2021PU");
        EntityManager em = emf.createEntityManager();
        /*int cle = 1;
        Sport sport = em.find(Sport.class, new BigDecimal(cle));
        System.out.println("Le sport " + cle + " est " + sport.getIntitule());
        sport.getDisciplineSet().forEach(disc -> {
          System.out.println(" -> " + disc.getIntitule());
        });*/
    }
    
}
