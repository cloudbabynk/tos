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
@Table(name = "AUTH_ROLE_RESOURCE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AuthRoleResource.findAll", query = "SELECT a FROM AuthRoleResource a"),
    @NamedQuery(name = "AuthRoleResource.findByRoleId", query = "SELECT a FROM AuthRoleResource a WHERE a.authRoleResourcePK.roleId = :roleId"),
    @NamedQuery(name = "AuthRoleResource.findByResourceId", query = "SELECT a FROM AuthRoleResource a WHERE a.authRoleResourcePK.resourceId = :resourceId"),
    @NamedQuery(name = "AuthRoleResource.findByPrivilegeId", query = "SELECT a FROM AuthRoleResource a WHERE a.authRoleResourcePK.privilegeId = :privilegeId")})
public class AuthRoleResource extends AuditEntityBean implements Serializable {
    @JoinColumn(name = "ROLE_ID", referencedColumnName = "ROLE_ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private AuthRole authRole;
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected AuthRoleResourcePK authRoleResourcePK;
    
    public AuthRoleResource() {
    }

    public AuthRoleResource(AuthRoleResourcePK authRoleResourcePK) {
        this.authRoleResourcePK = authRoleResourcePK;
    }

    public AuthRoleResource(String roleId, String resourceId, String privilegeId) {
        this.authRoleResourcePK = new AuthRoleResourcePK(roleId, resourceId, privilegeId);
    }

    public AuthRoleResourcePK getAuthRoleResourcePK() {
        return authRoleResourcePK;
    }

    public void setAuthRoleResourcePK(AuthRoleResourcePK authRoleResourcePK) {
        this.authRoleResourcePK = authRoleResourcePK;
    }


	@Override
    public int hashCode() {
        int hash = 0;
        hash += (authRoleResourcePK != null ? authRoleResourcePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AuthRoleResource)) {
            return false;
        }
        AuthRoleResource other = (AuthRoleResource) object;
        if ((this.authRoleResourcePK == null && other.authRoleResourcePK != null) || (this.authRoleResourcePK != null && !this.authRoleResourcePK.equals(other.authRoleResourcePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "net.huadong.idev.tpl.privilege.entity.AuthRoleResource[ authRoleResourcePK=" + authRoleResourcePK + " ]";
    }

    public AuthRole getAuthRole() {
        return authRole;
    }

    public void setAuthRole(AuthRole authRole) {
        this.authRole = authRole;
    }
    
}
