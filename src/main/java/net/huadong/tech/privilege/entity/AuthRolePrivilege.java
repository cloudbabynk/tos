/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.huadong.tech.privilege.entity;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import net.huadong.tech.base.bean.AuditEntityBean;

/**
 *
 * @author Ryze
 */
@Entity
@Table(name = "AUTH_ROLE_PRIVILEGE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AuthRolePrivilege.findAll", query = "SELECT a FROM AuthRolePrivilege a"),
    @NamedQuery(name = "AuthRolePrivilege.findByRoleId", query = "SELECT a FROM AuthRolePrivilege a WHERE a.authRolePrivilegePK.roleId = :roleId"),
    @NamedQuery(name = "AuthRolePrivilege.findByPrivilegeId", query = "SELECT a FROM AuthRolePrivilege a WHERE a.authRolePrivilegePK.privilegeId = :privilegeId"),
    @NamedQuery(name = "AuthRolePrivilege.findByInsAccount", query = "SELECT a FROM AuthRolePrivilege a WHERE a.recNam = :recNam"),
    @NamedQuery(name = "AuthRolePrivilege.findByInsTimestamp", query = "SELECT a FROM AuthRolePrivilege a WHERE a.recTim = :recTim"),
    @NamedQuery(name = "AuthRolePrivilege.findByUpdAccount", query = "SELECT a FROM AuthRolePrivilege a WHERE a.updNam = :updNam"),
    @NamedQuery(name = "AuthRolePrivilege.findByUpdTimestamp", query = "SELECT a FROM AuthRolePrivilege a WHERE a.updTim = :updTim")})
public class AuthRolePrivilege extends AuditEntityBean implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected AuthRolePrivilegePK authRolePrivilegePK;

    
    public AuthRolePrivilege() {
    }

	public AuthRolePrivilege(AuthRolePrivilegePK authRolePrivilegePK) {
        this.authRolePrivilegePK = authRolePrivilegePK;
    }


    public AuthRolePrivilege(String roleId, String privilegeId) {
        this.authRolePrivilegePK = new AuthRolePrivilegePK(roleId, privilegeId);
    }

    public AuthRolePrivilegePK getAuthRolePrivilegePK() {
        return authRolePrivilegePK;
    }

    public void setAuthRolePrivilegePK(AuthRolePrivilegePK authRolePrivilegePK) {
        this.authRolePrivilegePK = authRolePrivilegePK;
    }


	@Override
    public int hashCode() {
        int hash = 0;
        hash += (authRolePrivilegePK != null ? authRolePrivilegePK.hashCode() : 0);
        return hash;
    }

	@Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AuthRolePrivilege)) {
            return false;
        }
        AuthRolePrivilege other = (AuthRolePrivilege) object;
        if ((this.authRolePrivilegePK == null && other.authRolePrivilegePK != null) || (this.authRolePrivilegePK != null && !this.authRolePrivilegePK.equals(other.authRolePrivilegePK))) {
            return false;
        }
        return true;
    }
    @Override
    public String toString() {
        return "net.huadong.tech.privilege.entity.AuthRolePrivilege[ authRolePrivilegePK=" + authRolePrivilegePK + " ]";
    }
    
}
