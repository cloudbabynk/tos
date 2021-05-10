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
@Table(name = "AUTH_PRIVILEGE_RESOURCE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AuthPrivilegeResource.findAll", query = "SELECT a FROM AuthPrivilegeResource a"),
    @NamedQuery(name = "AuthPrivilegeResource.findByPrivilegeId", query = "SELECT a FROM AuthPrivilegeResource a WHERE a.authPrivilegeResourcePK.privilegeId = :privilegeId"),
    @NamedQuery(name = "AuthPrivilegeResource.findByResourceId", query = "SELECT a FROM AuthPrivilegeResource a WHERE a.authPrivilegeResourcePK.resourceId = :resourceId"),
    @NamedQuery(name = "AuthPrivilegeResource.findByInsAccount", query = "SELECT a FROM AuthPrivilegeResource a WHERE a.recNam = :recNam"),
    @NamedQuery(name = "AuthPrivilegeResource.findByInsTimestamp", query = "SELECT a FROM AuthPrivilegeResource a WHERE a.recTim = :recTim"),
    @NamedQuery(name = "AuthPrivilegeResource.findByUpdAccount", query = "SELECT a FROM AuthPrivilegeResource a WHERE a.updNam = :updNam"),
    @NamedQuery(name = "AuthPrivilegeResource.findByUpdTimestamp", query = "SELECT a FROM AuthPrivilegeResource a WHERE a.updTim = :updTim")})
public class AuthPrivilegeResource extends AuditEntityBean implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected AuthPrivilegeResourcePK authPrivilegeResourcePK;

    public AuthPrivilegeResource() {
    }

    public AuthPrivilegeResource(AuthPrivilegeResourcePK authPrivilegeResourcePK) {
        this.authPrivilegeResourcePK = authPrivilegeResourcePK;
    }

   
    public AuthPrivilegeResource(String privilegeId, String resourceId) {
        this.authPrivilegeResourcePK = new AuthPrivilegeResourcePK(privilegeId, resourceId);
    }

    public AuthPrivilegeResourcePK getAuthPrivilegeResourcePK() {
        return authPrivilegeResourcePK;
    }

    public void setAuthPrivilegeResourcePK(AuthPrivilegeResourcePK authPrivilegeResourcePK) {
        this.authPrivilegeResourcePK = authPrivilegeResourcePK;
    }



	@Override
    public int hashCode() {
        int hash = 0;
        hash += (authPrivilegeResourcePK != null ? authPrivilegeResourcePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AuthPrivilegeResource)) {
            return false;
        }
        AuthPrivilegeResource other = (AuthPrivilegeResource) object;
        if ((this.authPrivilegeResourcePK == null && other.authPrivilegeResourcePK != null) || (this.authPrivilegeResourcePK != null && !this.authPrivilegeResourcePK.equals(other.authPrivilegeResourcePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "net.huadong.tech.privilege.entity.AuthPrivilegeResource[ authPrivilegeResourcePK=" + authPrivilegeResourcePK + " ]";
    }
    
}
