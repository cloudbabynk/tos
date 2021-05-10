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
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Ryze
 */
@Entity
@Table(name = "AUTH_USER_PRIVILEGE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AuthUserPrivilege.findAll", query = "SELECT a FROM AuthUserPrivilege a"),
    @NamedQuery(name = "AuthUserPrivilege.findByPrivilegeId", query = "SELECT a FROM AuthUserPrivilege a WHERE a.authUserPrivilegePK.privilegeId = :privilegeId"),
    @NamedQuery(name = "AuthUserPrivilege.findByUserId", query = "SELECT a FROM AuthUserPrivilege a WHERE a.authUserPrivilegePK.userId = :userId")})
public class AuthUserPrivilege implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected AuthUserPrivilegePK authUserPrivilegePK;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 36)
    @Column(name = "REC_NAM")
    private String recNam;
    @Basic(optional = false)
    @NotNull
    @Column(name = "REC_TIM")
    @Temporal(TemporalType.TIMESTAMP)
    private Date recTim;
    @Size(max = 36)
    @Column(name = "UPD_NAM")
    private String updNam;
    @Column(name = "UPD_TIM")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updTim;

    public AuthUserPrivilege() {
    }

    public AuthUserPrivilege(AuthUserPrivilegePK authUserPrivilegePK) {
        this.authUserPrivilegePK = authUserPrivilegePK;
    }

    public AuthUserPrivilege(AuthUserPrivilegePK authUserPrivilegePK, String recNam, Date recTim) {
        this.authUserPrivilegePK = authUserPrivilegePK;
        this.recNam = recNam;
        this.recTim = recTim;
    }

    public AuthUserPrivilege(String privilegeId, String userId) {
        this.authUserPrivilegePK = new AuthUserPrivilegePK(privilegeId, userId);
    }

    public AuthUserPrivilegePK getAuthUserPrivilegePK() {
        return authUserPrivilegePK;
    }

    public void setAuthUserPrivilegePK(AuthUserPrivilegePK authUserPrivilegePK) {
        this.authUserPrivilegePK = authUserPrivilegePK;
    }

   

    public String getRecNam() {
		return recNam;
	}

	public void setRecNam(String recNam) {
		this.recNam = recNam;
	}

	public Date getRecTim() {
		return recTim;
	}

	public void setRecTim(Date recTim) {
		this.recTim = recTim;
	}

	public String getUpdNam() {
		return updNam;
	}

	public void setUpdNam(String updNam) {
		this.updNam = updNam;
	}

	public Date getUpdTim() {
		return updTim;
	}

	public void setUpdTim(Date updTim) {
		this.updTim = updTim;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (authUserPrivilegePK != null ? authUserPrivilegePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AuthUserPrivilege)) {
            return false;
        }
        AuthUserPrivilege other = (AuthUserPrivilege) object;
        if ((this.authUserPrivilegePK == null && other.authUserPrivilegePK != null) || (this.authUserPrivilegePK != null && !this.authUserPrivilegePK.equals(other.authUserPrivilegePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "net.huadong.tech.privilege.entity.AuthUserPrivilege[ authUserPrivilegePK=" + authUserPrivilegePK + " ]";
    }
    
}
