/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.huadong.tech.privilege.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import org.eclipse.persistence.annotations.UuidGenerator;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "SYS_FORM")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SysForm.findAll", query = "SELECT s FROM SysForm s"),
    @NamedQuery(name = "SysForm.findByFormId", query = "SELECT s FROM SysForm s WHERE s.formId = :formId"),
    @NamedQuery(name = "SysForm.findByFormText", query = "SELECT s FROM SysForm s WHERE s.formText = :formText"),
    @NamedQuery(name = "SysForm.findByFormTime", query = "SELECT s FROM SysForm s WHERE s.formTime = :formTime"),
    @NamedQuery(name = "SysForm.findByFormType", query = "SELECT s FROM SysForm s WHERE s.formType = :formType"),
    @NamedQuery(name = "SysForm.findByFormNumber", query = "SELECT s FROM SysForm s WHERE s.formNumber = :formNumber")})
public class SysForm implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 36)
    @UuidGenerator(name = "UUID")
    @GeneratedValue(generator = "UUID")
    @Column(name = "FORM_ID")
    private String formId;
    @Size(max = 100)
    @Column(name = "FORM_TEXT")
    private String formText;
    @Column(name = "FORM_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date formTime;
    @Size(max = 200)
    @Column(name = "FORM_TYPE")
    private String formType;
    @Size(max = 20)
    @Column(name = "FORM_NUMBER")
    private String  formNumber;

    public SysForm() {
    }

    public SysForm(String formId) {
        this.formId = formId;
    }

    public String getFormId() {
        return formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public String getFormText() {
        return formText;
    }

    public void setFormText(String formText) {
        this.formText = formText;
    }

    public Date getFormTime() {
        return formTime;
    }

    public void setFormTime(Date formTime) {
        this.formTime = formTime;
    }

    public String getFormType() {
        return formType;
    }

    public void setFormType(String formType) {
        this.formType = formType;
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (formId != null ? formId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SysForm)) {
            return false;
        }
        SysForm other = (SysForm) object;
        if ((this.formId == null && other.formId != null) || (this.formId != null && !this.formId.equals(other.formId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "net.huadong.idev.tpl.privilege.entity.SysForm[ formId=" + formId + " ]";
    }

    /**
     * @return the formNumber
     */
    public String getFormNumber() {
        return formNumber;
    }

    /**
     * @param formNumber the formNumber to set
     */
    public void setFormNumber(String formNumber) {
        this.formNumber = formNumber;
    }

   
    
}
