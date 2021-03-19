package FctPojo;

import java.sql.Connection;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class FctScorePartie {
    
    Connection connection;
    
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("Projet_CulDeChouette_2021PU");
    EntityManager em = emf.createEntityManager();
    
    //INITIALISATION
    
        //Initialisation de tous les champs lié au Code_Partie et Code_Joueur (les
        //2 passés en paramètre) à 0
    
    //AFFICHAGE
    
        /*-----------------------POUR TOUT LES JOUEURS------------------------*/
    
        //Score totale d'une partie
    
        //Moyenne de suite gagnée
    
        //Moyenne de Chouette Velute perdue
    
        /*--------------------------POUR UN JOUEUR----------------------------*/
    
        //Score, Nb_Suite_G et Nb_ChouVel_P du Code_Joueur de Code_Partie
    
        //Une valeur précise du Code_Joueur de Code_Partie (à définir en paramètre)
    
    //MISE A JOUR
    
        //Incrémentation Score
    
        //Incrémentation Nb_Suite_G
    
        //Incrémentation Nb_ChouVel_P   
}
