package com.mycompany.projet_dev_web_2021.entityManager;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManager {
    public EntityManagerFactory emf;
    public javax.persistence.EntityManager em;
    
    public void EntityManager(){
        emf = Persistence.createEntityManagerFactory("Projet_CulDeChouette_2021PU");
        em = emf.createEntityManager();
    }
}
