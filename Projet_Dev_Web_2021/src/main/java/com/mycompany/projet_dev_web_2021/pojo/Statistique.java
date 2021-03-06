/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.projet_dev_web_2021.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author pauline
 */
@Entity
@Table(name = "STATISTIQUE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Statistique.findAll", query = "SELECT s FROM Statistique s"),
    @NamedQuery(name = "Statistique.findByCodeJoueur", query = "SELECT s FROM Statistique s WHERE s.codeJoueur = :codeJoueur"),
    @NamedQuery(name = "Statistique.findByNbPartie", query = "SELECT s FROM Statistique s WHERE s.nbPartie = :nbPartie"),
    @NamedQuery(name = "Statistique.findByNbVictoire", query = "SELECT s FROM Statistique s WHERE s.nbVictoire = :nbVictoire"),
    @NamedQuery(name = "Statistique.findByNbVictoireMoyenne", query = "SELECT s FROM Statistique s WHERE s.nbVictoireMoyenne = :nbVictoireMoyenne"),
    @NamedQuery(name = "Statistique.findByNbPtsTot", query = "SELECT s FROM Statistique s WHERE s.nbPtsTot = :nbPtsTot"),
    @NamedQuery(name = "Statistique.findByScoreMoyen", query = "SELECT s FROM Statistique s WHERE s.scoreMoyen = :scoreMoyen"),
    @NamedQuery(name = "Statistique.findByNbSuite", query = "SELECT s FROM Statistique s WHERE s.nbSuite = :nbSuite"),
    @NamedQuery(name = "Statistique.findBySuiteMoyenG", query = "SELECT s FROM Statistique s WHERE s.suiteMoyenG = :suiteMoyenG"),
    @NamedQuery(name = "Statistique.findByNbChouvel", query = "SELECT s FROM Statistique s WHERE s.nbChouvel = :nbChouvel"),
    @NamedQuery(name = "Statistique.findByChouvelMoyenP", query = "SELECT s FROM Statistique s WHERE s.chouvelMoyenP = :chouvelMoyenP")})
public class Statistique implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "CODE_JOUEUR")
    private BigDecimal codeJoueur;
    @Column(name = "NB_PARTIE")
    private BigInteger nbPartie;
    @Column(name = "NB_VICTOIRE")
    private BigInteger nbVictoire;
    @Column(name = "NB_VICTOIRE_MOYENNE")
    private BigDecimal nbVictoireMoyenne;
    @Column(name = "NB_PTS_TOT")
    private BigInteger nbPtsTot;
    @Column(name = "SCORE_MOYEN")
    private BigDecimal scoreMoyen;
    @Column(name = "NB_SUITE")
    private BigInteger nbSuite;
    @Column(name = "SUITE_MOYEN_G")
    private BigDecimal suiteMoyenG;
    @Column(name = "NB_CHOUVEL")
    private BigInteger nbChouvel;
    @Column(name = "CHOUVEL_MOYEN_P")
    private BigDecimal chouvelMoyenP;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "statistique")
    private Joueur joueur;

    public Statistique() {
    }

    public Statistique(BigDecimal codeJoueur) {
        this.codeJoueur = codeJoueur;
    }

    public BigDecimal getCodeJoueur() {
        return codeJoueur;
    }

    public void setCodeJoueur(BigDecimal codeJoueur) {
        this.codeJoueur = codeJoueur;
    }

    public BigInteger getNbPartie() {
        return nbPartie;
    }

    public void setNbPartie(BigInteger nbPartie) {
        this.nbPartie = nbPartie;
    }

    public BigInteger getNbVictoire() {
        return nbVictoire;
    }

    public void setNbVictoire(BigInteger nbVictoire) {
        this.nbVictoire = nbVictoire;
    }

    public BigDecimal getNbVictoireMoyenne() {
        return nbVictoireMoyenne;
    }

    public void setNbVictoireMoyenne(BigDecimal nbVictoireMoyenne) {
        this.nbVictoireMoyenne = nbVictoireMoyenne;
    }

    public BigInteger getNbPtsTot() {
        return nbPtsTot;
    }

    public void setNbPtsTot(BigInteger nbPtsTot) {
        this.nbPtsTot = nbPtsTot;
    }

    public BigDecimal getScoreMoyen() {
        return scoreMoyen;
    }

    public void setScoreMoyen(BigDecimal scoreMoyen) {
        this.scoreMoyen = scoreMoyen;
    }

    public BigInteger getNbSuite() {
        return nbSuite;
    }

    public void setNbSuite(BigInteger nbSuite) {
        this.nbSuite = nbSuite;
    }

    public BigDecimal getSuiteMoyenG() {
        return suiteMoyenG;
    }

    public void setSuiteMoyenG(BigDecimal suiteMoyenG) {
        this.suiteMoyenG = suiteMoyenG;
    }

    public BigInteger getNbChouvel() {
        return nbChouvel;
    }

    public void setNbChouvel(BigInteger nbChouvel) {
        this.nbChouvel = nbChouvel;
    }

    public BigDecimal getChouvelMoyenP() {
        return chouvelMoyenP;
    }

    public void setChouvelMoyenP(BigDecimal chouvelMoyenP) {
        this.chouvelMoyenP = chouvelMoyenP;
    }

    public Joueur getJoueur() {
        return joueur;
    }

    public void setJoueur(Joueur joueur) {
        this.joueur = joueur;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codeJoueur != null ? codeJoueur.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Statistique)) {
            return false;
        }
        Statistique other = (Statistique) object;
        if ((this.codeJoueur == null && other.codeJoueur != null) || (this.codeJoueur != null && !this.codeJoueur.equals(other.codeJoueur))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.projet_dev_web_2021.pojo.Statistique[ codeJoueur=" + codeJoueur + " ]";
    }
    
}
