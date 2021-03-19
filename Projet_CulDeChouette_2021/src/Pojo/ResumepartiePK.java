/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pojo;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author pauline
 */
@Embeddable
public class ResumepartiePK implements Serializable {

    @Basic(optional = false)
    @Column(name = "CODE_PARTIE")
    private BigInteger codePartie;
    @Basic(optional = false)
    @Column(name = "NUM_LANCE_DES")
    private BigInteger numLanceDes;

    public ResumepartiePK() {
    }

    public ResumepartiePK(BigInteger codePartie, BigInteger numLanceDes) {
        this.codePartie = codePartie;
        this.numLanceDes = numLanceDes;
    }

    public BigInteger getCodePartie() {
        return codePartie;
    }

    public void setCodePartie(BigInteger codePartie) {
        this.codePartie = codePartie;
    }

    public BigInteger getNumLanceDes() {
        return numLanceDes;
    }

    public void setNumLanceDes(BigInteger numLanceDes) {
        this.numLanceDes = numLanceDes;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codePartie != null ? codePartie.hashCode() : 0);
        hash += (numLanceDes != null ? numLanceDes.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ResumepartiePK)) {
            return false;
        }
        ResumepartiePK other = (ResumepartiePK) object;
        if ((this.codePartie == null && other.codePartie != null) || (this.codePartie != null && !this.codePartie.equals(other.codePartie))) {
            return false;
        }
        if ((this.numLanceDes == null && other.numLanceDes != null) || (this.numLanceDes != null && !this.numLanceDes.equals(other.numLanceDes))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Pojo.ResumepartiePK[ codePartie=" + codePartie + ", numLanceDes=" + numLanceDes + " ]";
    }
    
}
