/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author pauline
 */
@Entity
@Table(name = "JOUEUR")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Joueur.findAll", query = "SELECT j FROM Joueur j"),
    @NamedQuery(name = "Joueur.findByCodeJoueur", query = "SELECT j FROM Joueur j WHERE j.codeJoueur = :codeJoueur"),
    @NamedQuery(name = "Joueur.findByPseudo", query = "SELECT j FROM Joueur j WHERE j.pseudo = :pseudo"),
    @NamedQuery(name = "Joueur.findByMdp", query = "SELECT j FROM Joueur j WHERE j.mdp = :mdp"),
    @NamedQuery(name = "Joueur.findByAge", query = "SELECT j FROM Joueur j WHERE j.age = :age"),
    @NamedQuery(name = "Joueur.findBySexe", query = "SELECT j FROM Joueur j WHERE j.sexe = :sexe"),
    @NamedQuery(name = "Joueur.findByVille", query = "SELECT j FROM Joueur j WHERE j.ville = :ville")})
public class Joueur implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "CODE_JOUEUR")
    private BigDecimal codeJoueur;
    @Column(name = "PSEUDO")
    private String pseudo;
    @Column(name = "MDP")
    private String mdp;
    @Column(name = "AGE")
    private BigInteger age;
    @Column(name = "SEXE")
    private Character sexe;
    @Column(name = "VILLE")
    private String ville;
    @OneToMany(mappedBy = "codeJoueur1")
    private Collection<Partie> partieCollection;
    @OneToMany(mappedBy = "codeJoueur2")
    private Collection<Partie> partieCollection1;
    @OneToMany(mappedBy = "codeJoueur6")
    private Collection<Partie> partieCollection2;
    @OneToMany(mappedBy = "codeJoueur4")
    private Collection<Partie> partieCollection3;
    @OneToMany(mappedBy = "codeJoueur5")
    private Collection<Partie> partieCollection4;
    @OneToMany(mappedBy = "codeJoueur3")
    private Collection<Partie> partieCollection5;
    @JoinColumn(name = "CODE_JOUEUR", referencedColumnName = "CODE_JOUEUR", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Statistique statistique;

    public Joueur() {
    }

    public Joueur(BigDecimal codeJoueur) {
        this.codeJoueur = codeJoueur;
    }

    public BigDecimal getCodeJoueur() {
        return codeJoueur;
    }

    public void setCodeJoueur(BigDecimal codeJoueur) {
        this.codeJoueur = codeJoueur;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public BigInteger getAge() {
        return age;
    }

    public void setAge(BigInteger age) {
        this.age = age;
    }

    public Character getSexe() {
        return sexe;
    }

    public void setSexe(Character sexe) {
        this.sexe = sexe;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    @XmlTransient
    public Collection<Partie> getPartieCollection() {
        return partieCollection;
    }

    public void setPartieCollection(Collection<Partie> partieCollection) {
        this.partieCollection = partieCollection;
    }

    @XmlTransient
    public Collection<Partie> getPartieCollection1() {
        return partieCollection1;
    }

    public void setPartieCollection1(Collection<Partie> partieCollection1) {
        this.partieCollection1 = partieCollection1;
    }

    @XmlTransient
    public Collection<Partie> getPartieCollection2() {
        return partieCollection2;
    }

    public void setPartieCollection2(Collection<Partie> partieCollection2) {
        this.partieCollection2 = partieCollection2;
    }

    @XmlTransient
    public Collection<Partie> getPartieCollection3() {
        return partieCollection3;
    }

    public void setPartieCollection3(Collection<Partie> partieCollection3) {
        this.partieCollection3 = partieCollection3;
    }

    @XmlTransient
    public Collection<Partie> getPartieCollection4() {
        return partieCollection4;
    }

    public void setPartieCollection4(Collection<Partie> partieCollection4) {
        this.partieCollection4 = partieCollection4;
    }

    @XmlTransient
    public Collection<Partie> getPartieCollection5() {
        return partieCollection5;
    }

    public void setPartieCollection5(Collection<Partie> partieCollection5) {
        this.partieCollection5 = partieCollection5;
    }

    public Statistique getStatistique() {
        return statistique;
    }

    public void setStatistique(Statistique statistique) {
        this.statistique = statistique;
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
        if (!(object instanceof Joueur)) {
            return false;
        }
        Joueur other = (Joueur) object;
        if ((this.codeJoueur == null && other.codeJoueur != null) || (this.codeJoueur != null && !this.codeJoueur.equals(other.codeJoueur))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Pojo.Joueur[ codeJoueur=" + codeJoueur + " ]";
    }
    
}
