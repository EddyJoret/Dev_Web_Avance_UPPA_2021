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
@Table(name = "RESUMEPARTIE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Resumepartie.findAll", query = "SELECT r FROM Resumepartie r"),
    @NamedQuery(name = "Resumepartie.findByCodePartie", query = "SELECT r FROM Resumepartie r WHERE r.resumepartiePK.codePartie = :codePartie"),
    @NamedQuery(name = "Resumepartie.findByNumLanceDes", query = "SELECT r FROM Resumepartie r WHERE r.resumepartiePK.numLanceDes = :numLanceDes"),
    @NamedQuery(name = "Resumepartie.findByDes1", query = "SELECT r FROM Resumepartie r WHERE r.des1 = :des1"),
    @NamedQuery(name = "Resumepartie.findByDes2", query = "SELECT r FROM Resumepartie r WHERE r.des2 = :des2"),
    @NamedQuery(name = "Resumepartie.findByDes3", query = "SELECT r FROM Resumepartie r WHERE r.des3 = :des3")})
public class Resumepartie implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ResumepartiePK resumepartiePK;
    @Column(name = "DES_1")
    private BigInteger des1;
    @Column(name = "DES_2")
    private BigInteger des2;
    @Column(name = "DES_3")
    private BigInteger des3;
    @JoinColumn(name = "CODE_PARTIE", referencedColumnName = "CODE_PARTIE", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Partie partie;

    public Resumepartie() {
    }

    public Resumepartie(ResumepartiePK resumepartiePK) {
        this.resumepartiePK = resumepartiePK;
    }

    public Resumepartie(BigInteger codePartie, BigInteger numLanceDes) {
        this.resumepartiePK = new ResumepartiePK(codePartie, numLanceDes);
    }

    public ResumepartiePK getResumepartiePK() {
        return resumepartiePK;
    }

    public void setResumepartiePK(ResumepartiePK resumepartiePK) {
        this.resumepartiePK = resumepartiePK;
    }

    public BigInteger getDes1() {
        return des1;
    }

    public void setDes1(BigInteger des1) {
        this.des1 = des1;
    }

    public BigInteger getDes2() {
        return des2;
    }

    public void setDes2(BigInteger des2) {
        this.des2 = des2;
    }

    public BigInteger getDes3() {
        return des3;
    }

    public void setDes3(BigInteger des3) {
        this.des3 = des3;
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
        hash += (resumepartiePK != null ? resumepartiePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Resumepartie)) {
            return false;
        }
        Resumepartie other = (Resumepartie) object;
        if ((this.resumepartiePK == null && other.resumepartiePK != null) || (this.resumepartiePK != null && !this.resumepartiePK.equals(other.resumepartiePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Pojo.Resumepartie[ resumepartiePK=" + resumepartiePK + " ]";
    }
    
}
