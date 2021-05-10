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

import net.huadong.tech.base.bean.AuditEntityBean;

/**
 *
 * @author Ryze
 */
@Entity
@Table(name = "AUTH_USER_ROLE")
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "AuthUserRole.findAll", query = "SELECT a FROM AuthUserRole a"),
		@NamedQuery(name = "AuthUserRole.findByUserId", query = "SELECT a FROM AuthUserRole a WHERE a.authUserRolePK.userId = :userId"),
		@NamedQuery(name = "AuthUserRole.findByRoleId", query = "SELECT a FROM AuthUserRole a WHERE a.authUserRolePK.roleId = :roleId") })
public class AuthUserRole extends AuditEntityBean implements Serializable {
	private static final long serialVersionUID = 1L;
	@EmbeddedId
	protected AuthUserRolePK authUserRolePK;

	public AuthUserRole() {
	}

	public AuthUserRolePK getAuthUserRolePK() {
		return authUserRolePK;
	}

	public void setAuthUserRolePK(AuthUserRolePK authUserRolePK) {
		this.authUserRolePK = authUserRolePK;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (authUserRolePK != null ? authUserRolePK.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof AuthUserRole)) {
			return false;
		}
		AuthUserRole other = (AuthUserRole) object;
		if ((this.authUserRolePK == null && other.authUserRolePK != null)
				|| (this.authUserRolePK != null && !this.authUserRolePK.equals(other.authUserRolePK))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "net.huadong.tech.privilege.entity.AuthUserRole[ authUserRolePK=" + authUserRolePK + " ]";
	}

}
