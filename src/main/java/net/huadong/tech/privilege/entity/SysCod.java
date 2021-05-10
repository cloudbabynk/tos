/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.huadong.tech.privilege.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import org.eclipse.persistence.annotations.UuidGenerator;



/**
 *
 * @author zx
 */
@Entity
@Table(name = "SYS_COD")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SysCod.findAll", query = "SELECT s FROM SysCod s"),
    @NamedQuery(name = "SysCod.findByCodId", query = "SELECT s FROM SysCod s WHERE s.codId = :codId"),
    @NamedQuery(name = "SysCod.findByCodValue", query = "SELECT s FROM SysCod s WHERE s.codValue = :codValue"),
    @NamedQuery(name = "SysCod.findByCodText", query = "SELECT s FROM SysCod s WHERE s.codText = :codText"),
    @NamedQuery(name = "SysCod.findByCodTyp", query = "SELECT s FROM SysCod s WHERE s.codTyp = :codTyp"),
    @NamedQuery(name = "SysCod.findByDescription", query = "SELECT s FROM SysCod s WHERE s.description = :description"),
    @NamedQuery(name = "SysCod.findByCodTypNam", query = "SELECT s FROM SysCod s WHERE s.codTypNam = :codTypNam")})
public class SysCod implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @UuidGenerator(name = "UUID")
    @GeneratedValue(generator = "UUID")
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 36)
    @Column(name = "COD_ID")
    private String codId;
    @Size(max = 50)
    @Column(name = "COD_VALUE")
    private String codValue;
    @Size(max = 500)
    @Column(name = "COD_TEXT")
    private String codText;
    @Size(max = 10)
    @Column(name = "COD_TYP")
    private String codTyp;
    @Size(max = 1024)
    @Column(name = "DESCRIPTION")
    private String description;
    @Size(max = 500)
    @Column(name = "COD_TYP_NAM")
    private String codTypNam;

    public SysCod() {
    }

    public SysCod(String codId) {
        this.codId = codId;
    }

    public String getCodId() {
        return codId;
    }

    public void setCodId(String codId) {
        this.codId = codId;
    }

    public String getCodValue() {
        return codValue;
    }

    public void setCodValue(String codValue) {
        this.codValue = codValue;
    }

    public String getCodText() {
        return codText;
    }

    public void setCodText(String codText) {
        this.codText = codText;
    }

    public String getCodTyp() {
        return codTyp;
    }

    public void setCodTyp(String codTyp) {
        this.codTyp = codTyp;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCodTypNam() {
        return codTypNam;
    }

    public void setCodTypNam(String codTypNam) {
        this.codTypNam = codTypNam;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codId != null ? codId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SysCod)) {
            return false;
        }
        SysCod other = (SysCod) object;
        if ((this.codId == null && other.codId != null) || (this.codId != null && !this.codId.equals(other.codId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "net.huadong.idev.tpl.privilege.entity.SysCod[ codId=" + codId + " ]";
    }



}
