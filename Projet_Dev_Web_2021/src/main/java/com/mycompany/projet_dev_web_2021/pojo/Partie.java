/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.projet_dev_web_2021.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author pauline
 */
@Entity
@Table(name = "PARTIE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Partie.findAll", query = "SELECT p FROM Partie p"),
    @NamedQuery(name = "Partie.findByCodePartie", query = "SELECT p FROM Partie p WHERE p.codePartie = :codePartie"),
    @NamedQuery(name = "Partie.findByTermine", query = "SELECT p FROM Partie p WHERE p.termine = :termine")})
public class Partie implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "CODE_PARTIE")
    private BigDecimal codePartie;
    @Column(name = "TERMINE")
    private Character termine;
    @JoinColumn(name = "CODE_JOUEUR1", referencedColumnName = "CODE_JOUEUR")
    @ManyToOne
    private Joueur codeJoueur1;
    @JoinColumn(name = "CODE_JOUEUR2", referencedColumnName = "CODE_JOUEUR")
    @ManyToOne
    private Joueur codeJoueur2;
    @JoinColumn(name = "CODE_JOUEUR6", referencedColumnName = "CODE_JOUEUR")
    @ManyToOne
    private Joueur codeJoueur6;
    @JoinColumn(name = "CODE_JOUEUR4", referencedColumnName = "CODE_JOUEUR")
    @ManyToOne
    private Joueur codeJoueur4;
    @JoinColumn(name = "CODE_JOUEUR5", referencedColumnName = "CODE_JOUEUR")
    @ManyToOne
    private Joueur codeJoueur5;
    @JoinColumn(name = "CODE_JOUEUR3", referencedColumnName = "CODE_JOUEUR")
    @ManyToOne
    private Joueur codeJoueur3;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "partie")
    private Collection<Resumepartie> resumepartieCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "partie")
    private Collection<Scorepartie> scorepartieCollection;

    public Partie() {
    }

    public Partie(BigDecimal codePartie) {
        this.codePartie = codePartie;
    }

    public BigDecimal getCodePartie() {
        return codePartie;
    }

    public void setCodePartie(BigDecimal codePartie) {
        this.codePartie = codePartie;
    }

    public Character getTermine() {
        return termine;
    }

    public void setTermine(Character termine) {
        this.termine = termine;
    }

    public Joueur getCodeJoueur1() {
        return codeJoueur1;
    }

    public void setCodeJoueur1(Joueur codeJoueur1) {
        this.codeJoueur1 = codeJoueur1;
    }

    public Joueur getCodeJoueur2() {
        return codeJoueur2;
    }

    public void setCodeJoueur2(Joueur codeJoueur2) {
        this.codeJoueur2 = codeJoueur2;
    }

    public Joueur getCodeJoueur6() {
        return codeJoueur6;
    }

    public void setCodeJoueur6(Joueur codeJoueur6) {
        this.codeJoueur6 = codeJoueur6;
    }

    public Joueur getCodeJoueur4() {
        return codeJoueur4;
    }

    public void setCodeJoueur4(Joueur codeJoueur4) {
        this.codeJoueur4 = codeJoueur4;
    }

    public Joueur getCodeJoueur5() {
        return codeJoueur5;
    }

    public void setCodeJoueur5(Joueur codeJoueur5) {
        this.codeJoueur5 = codeJoueur5;
    }

    public Joueur getCodeJoueur3() {
        return codeJoueur3;
    }

    public void setCodeJoueur3(Joueur codeJoueur3) {
        this.codeJoueur3 = codeJoueur3;
    }

    @XmlTransient
    public Collection<Resumepartie> getResumepartieCollection() {
        return resumepartieCollection;
    }

    public void setResumepartieCollection(Collection<Resumepartie> resumepartieCollection) {
        this.resumepartieCollection = resumepartieCollection;
    }

    @XmlTransient
    public Collection<Scorepartie> getScorepartieCollection() {
        return scorepartieCollection;
    }

    public void setScorepartieCollection(Collection<Scorepartie> scorepartieCollection) {
        this.scorepartieCollection = scorepartieCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codePartie != null ? codePartie.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Partie)) {
            return false;
        }
        Partie other = (Partie) object;
        if ((this.codePartie == null && other.codePartie != null) || (this.codePartie != null && !this.codePartie.equals(other.codePartie))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.projet_dev_web_2021.pojo.Partie[ codePartie=" + codePartie + " ]";
    }
    
}
