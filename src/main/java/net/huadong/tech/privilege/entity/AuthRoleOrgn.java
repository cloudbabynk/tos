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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
 * @author Administrator
 */
@Entity
@Table(name = "AUTH_ROLE_ORGN")
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "AuthRoleOrgn.findAll", query = "SELECT a FROM AuthRoleOrgn a"),
		@NamedQuery(name = "AuthRoleOrgn.findByRoleId", query = "SELECT a FROM AuthRoleOrgn a WHERE a.authRoleOrgnPK.roleId = :roleId"),
		@NamedQuery(name = "AuthRoleOrgn.findByOrgnId", query = "SELECT a FROM AuthRoleOrgn a WHERE a.authRoleOrgnPK.orgnId = :orgnId"),
		@NamedQuery(name = "AuthRoleOrgn.findByPrivilegeId", query = "SELECT a FROM AuthRoleOrgn a WHERE a.authRoleOrgnPK.privilegeId = :privilegeId") })
public class AuthRoleOrgn extends AuditEntityBean implements Serializable {
	@JoinColumn(name = "ROLE_ID", referencedColumnName = "ROLE_ID", insertable = false, updatable = false)
	@ManyToOne(optional = false)
	private AuthRole authRole;
	private static final long serialVersionUID = 1L;
	@JoinColumn(name = "ORGN_ID", referencedColumnName = "ORGN_ID", insertable = false, updatable = false)
	@ManyToOne(optional = false)
	private AuthOrgn authOrgn;

	@EmbeddedId
	protected AuthRoleOrgnPK authRoleOrgnPK;

	public AuthRoleOrgn() {
	}

	public AuthRoleOrgn(AuthRoleOrgnPK authRoleOrgnPK) {
		this.authRoleOrgnPK = authRoleOrgnPK;
	}

	public AuthRoleOrgn(String roleId, String orgnId) {
		this.authRoleOrgnPK = new AuthRoleOrgnPK(roleId, orgnId);
	}

	public AuthRoleOrgnPK getAuthRoleOrgnPK() {
		return authRoleOrgnPK;
	}

	public void setAuthRoleOrgnPK(AuthRoleOrgnPK authRoleOrgnPK) {
		this.authRoleOrgnPK = authRoleOrgnPK;
	}

	public AuthOrgn getAuthOrgn() {
		return authOrgn;
	}

	public void setAuthOrgn(AuthOrgn authOrgn) {
		this.authOrgn = authOrgn;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (authRoleOrgnPK != null ? authRoleOrgnPK.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof AuthRoleOrgn)) {
			return false;
		}
		AuthRoleOrgn other = (AuthRoleOrgn) object;
		if ((this.authRoleOrgnPK == null && other.authRoleOrgnPK != null)
				|| (this.authRoleOrgnPK != null && !this.authRoleOrgnPK.equals(other.authRoleOrgnPK))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "net.huadong.idev.tpl.privilege.entity.AuthRoleOrgn[ authRoleOrgnPK=" + authRoleOrgnPK + " ]";
	}

	public AuthRole getAuthRole() {
		return authRole;
	}

	public void setAuthRole(AuthRole authRole) {
		this.authRole = authRole;
	}

}
