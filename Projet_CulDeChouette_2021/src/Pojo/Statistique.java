/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pojo;

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
    @NamedQuery(name = "Statistique.findByNbParti", query = "SELECT s FROM Statistique s WHERE s.nbParti = :nbParti"),
    @NamedQuery(name = "Statistique.findByNbVictoire", query = "SELECT s FROM Statistique s WHERE s.nbVictoire = :nbVictoire"),
    @NamedQuery(name = "Statistique.findByNbPtsTot", query = "SELECT s FROM Statistique s WHERE s.nbPtsTot = :nbPtsTot"),
    @NamedQuery(name = "Statistique.findByScMoyen", query = "SELECT s FROM Statistique s WHERE s.scMoyen = :scMoyen"),
    @NamedQuery(name = "Statistique.findBySuMoyenG", query = "SELECT s FROM Statistique s WHERE s.suMoyenG = :suMoyenG"),
    @NamedQuery(name = "Statistique.findByCvMoyenP", query = "SELECT s FROM Statistique s WHERE s.cvMoyenP = :cvMoyenP")})
public class Statistique implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "CODE_JOUEUR")
    private BigDecimal codeJoueur;
    @Column(name = "NB_PARTI")
    private BigInteger nbParti;
    @Column(name = "NB_VICTOIRE")
    private BigInteger nbVictoire;
    @Column(name = "NB_PTS_TOT")
    private BigInteger nbPtsTot;
    @Column(name = "SC_MOYEN")
    private BigInteger scMoyen;
    @Column(name = "SU_MOYEN_G")
    private BigInteger suMoyenG;
    @Column(name = "CV_MOYEN_P")
    private BigInteger cvMoyenP;
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

    public BigInteger getNbParti() {
        return nbParti;
    }

    public void setNbParti(BigInteger nbParti) {
        this.nbParti = nbParti;
    }

    public BigInteger getNbVictoire() {
        return nbVictoire;
    }

    public void setNbVictoire(BigInteger nbVictoire) {
        this.nbVictoire = nbVictoire;
    }

    public BigInteger getNbPtsTot() {
        return nbPtsTot;
    }

    public void setNbPtsTot(BigInteger nbPtsTot) {
        this.nbPtsTot = nbPtsTot;
    }

    public BigInteger getScMoyen() {
        return scMoyen;
    }

    public void setScMoyen(BigInteger scMoyen) {
        this.scMoyen = scMoyen;
    }

    public BigInteger getSuMoyenG() {
        return suMoyenG;
    }

    public void setSuMoyenG(BigInteger suMoyenG) {
        this.suMoyenG = suMoyenG;
    }

    public BigInteger getCvMoyenP() {
        return cvMoyenP;
    }

    public void setCvMoyenP(BigInteger cvMoyenP) {
        this.cvMoyenP = cvMoyenP;
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
        return "Pojo.Statistique[ codeJoueur=" + codeJoueur + " ]";
    }
    
}
