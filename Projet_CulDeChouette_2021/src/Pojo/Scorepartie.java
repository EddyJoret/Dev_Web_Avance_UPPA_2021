/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pojo;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author pauline
 */
@Entity
@Table(name = "SCOREPARTIE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Scorepartie.findAll", query = "SELECT s FROM Scorepartie s"),
    @NamedQuery(name = "Scorepartie.findByCodePartie", query = "SELECT s FROM Scorepartie s WHERE s.scorepartiePK.codePartie = :codePartie"),
    @NamedQuery(name = "Scorepartie.findByCodeJoueur", query = "SELECT s FROM Scorepartie s WHERE s.scorepartiePK.codeJoueur = :codeJoueur"),
    @NamedQuery(name = "Scorepartie.findByScore", query = "SELECT s FROM Scorepartie s WHERE s.score = :score"),
    @NamedQuery(name = "Scorepartie.findByNbSuiteG", query = "SELECT s FROM Scorepartie s WHERE s.nbSuiteG = :nbSuiteG"),
    @NamedQuery(name = "Scorepartie.findByNbChouvelP", query = "SELECT s FROM Scorepartie s WHERE s.nbChouvelP = :nbChouvelP")})
public class Scorepartie implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ScorepartiePK scorepartiePK;
    @Column(name = "SCORE")
    private BigInteger score;
    @Column(name = "NB_SUITE_G")
    private BigInteger nbSuiteG;
    @Column(name = "NB_CHOUVEL_P")
    private BigInteger nbChouvelP;
    @JoinColumn(name = "CODE_PARTIE", referencedColumnName = "CODE_PARTIE", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Partie partie;

    public Scorepartie() {
    }

    public Scorepartie(ScorepartiePK scorepartiePK) {
        this.scorepartiePK = scorepartiePK;
    }

    public Scorepartie(BigInteger codePartie, BigInteger codeJoueur) {
        this.scorepartiePK = new ScorepartiePK(codePartie, codeJoueur);
    }

    public ScorepartiePK getScorepartiePK() {
        return scorepartiePK;
    }

    public void setScorepartiePK(ScorepartiePK scorepartiePK) {
        this.scorepartiePK = scorepartiePK;
    }

    public BigInteger getScore() {
        return score;
    }

    public void setScore(BigInteger score) {
        this.score = score;
    }

    public BigInteger getNbSuiteG() {
        return nbSuiteG;
    }

    public void setNbSuiteG(BigInteger nbSuiteG) {
        this.nbSuiteG = nbSuiteG;
    }

    public BigInteger getNbChouvelP() {
        return nbChouvelP;
    }

    public void setNbChouvelP(BigInteger nbChouvelP) {
        this.nbChouvelP = nbChouvelP;
    }

    public Partie getPartie() {
        return partie;
    }

    public void setPartie(Partie partie) {
        this.partie = partie;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (scorepartiePK != null ? scorepartiePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Scorepartie)) {
            return false;
        }
        Scorepartie other = (Scorepartie) object;
        if ((this.scorepartiePK == null && other.scorepartiePK != null) || (this.scorepartiePK != null && !this.scorepartiePK.equals(other.scorepartiePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Pojo.Scorepartie[ scorepartiePK=" + scorepartiePK + " ]";
    }
    
}
