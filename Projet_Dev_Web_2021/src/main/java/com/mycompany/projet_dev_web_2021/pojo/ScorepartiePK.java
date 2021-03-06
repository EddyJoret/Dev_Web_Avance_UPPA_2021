/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.projet_dev_web_2021.pojo;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author pauline
 */
@Embeddable
public class ScorepartiePK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "CODE_PARTIE")
    private BigInteger codePartie;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CODE_JOUEUR")
    private BigInteger codeJoueur;

    public ScorepartiePK() {
    }

    public ScorepartiePK(BigInteger codePartie, BigInteger codeJoueur) {
        this.codePartie = codePartie;
        this.codeJoueur = codeJoueur;
    }

    public BigInteger getCodePartie() {
        return codePartie;
    }

    public void setCodePartie(BigInteger codePartie) {
        this.codePartie = codePartie;
    }

    public BigInteger getCodeJoueur() {
        return codeJoueur;
    }

    public void setCodeJoueur(BigInteger codeJoueur) {
        this.codeJoueur = codeJoueur;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codePartie != null ? codePartie.hashCode() : 0);
        hash += (codeJoueur != null ? codeJoueur.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ScorepartiePK)) {
            return false;
        }
        ScorepartiePK other = (ScorepartiePK) object;
        if ((this.codePartie == null && other.codePartie != null) || (this.codePartie != null && !this.codePartie.equals(other.codePartie))) {
            return false;
        }
        if ((this.codeJoueur == null && other.codeJoueur != null) || (this.codeJoueur != null && !this.codeJoueur.equals(other.codeJoueur))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.projet_dev_web_2021.pojo.ScorepartiePK[ codePartie=" + codePartie + ", codeJoueur=" + codeJoueur + " ]";
    }
    
}
