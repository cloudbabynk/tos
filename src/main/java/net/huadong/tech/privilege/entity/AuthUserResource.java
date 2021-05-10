/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.huadong.tech.privilege.entity;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import net.huadong.tech.base.bean.AuditEntityBean;

/**
 *
 * @author 徐希龙
 */
@Entity
@Table(name = "AUTH_USER_RESOURCE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AuthUserResource.findAll", query = "SELECT a FROM AuthUserResource a"),
    @NamedQuery(name = "AuthUserResource.findByResourceId", query = "SELECT a FROM AuthUserResource a WHERE a.authUserResourcePK.resourceId = :resourceId"),
    @NamedQuery(name = "AuthUserResource.findByUserId", query = "SELECT a FROM AuthUserResource a WHERE a.authUserResourcePK.userId = :userId"),
    @NamedQuery(name = "AuthUserResource.findByPrivilegeId", query = "SELECT a FROM AuthUserResource a WHERE a.authUserResourcePK.privilegeId = :privilegeId")})
public class AuthUserResource  extends AuditEntityBean implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected AuthUserResourcePK authUserResourcePK;

    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private AuthUserNoPwd authUserNoPwd;
    @JoinColumn(name = "RESOURCE_ID", referencedColumnName = "RESOURCE_ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private AuthResource authResource;

    public AuthUserResource() {
    }

    public AuthUserResource(AuthUserResourcePK authUserResourcePK) {
        this.authUserResourcePK = authUserResourcePK;
    }

    public AuthUserResource(String resourceId, String userId) {
        this.authUserResourcePK = new AuthUserResourcePK(resourceId, userId);
    }

    public AuthUserResourcePK getAuthUserResourcePK() {
        return authUserResourcePK;
    }

    public void setAuthUserResourcePK(AuthUserResourcePK authUserResourcePK) {
        this.authUserResourcePK = authUserResourcePK;
    }



    public AuthUserNoPwd getAuthUserNoPwd() {
        return authUserNoPwd;
    }

    public void setAuthUserNoPwd(AuthUserNoPwd authUserNoPwd) {
        this.authUserNoPwd = authUserNoPwd;
    }

    public AuthResource getAuthResource() {
        return authResource;
    }

    public void setAuthResource(AuthResource authResource) {
        this.authResource = authResource;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (authUserResourcePK != null ? authUserResourcePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AuthUserResource)) {
            return false;
        }
        AuthUserResource other = (AuthUserResource) object;
        if ((this.authUserResourcePK == null && other.authUserResourcePK != null) || (this.authUserResourcePK != null && !this.authUserResourcePK.equals(other.authUserResourcePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "net.huadong.idev.tpl.privilege.entity.AuthUserResource[ authUserResourcePK=" + authUserResourcePK + " ]";
    }
    
}
