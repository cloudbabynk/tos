/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.huadong.tech.privilege.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Ryze
 */
@Embeddable
public class AuthRolePrivilegePK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 36)
    @Column(name = "ROLE_ID")
    private String roleId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 36)
    @Column(name = "PRIVILEGE_ID")
    private String privilegeId;

    public AuthRolePrivilegePK() {
    }

    public AuthRolePrivilegePK(String roleId, String privilegeId) {
        this.roleId = roleId;
        this.privilegeId = privilegeId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getPrivilegeId() {
        return privilegeId;
    }

    public void setPrivilegeId(String privilegeId) {
        this.privilegeId = privilegeId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (roleId != null ? roleId.hashCode() : 0);
        hash += (privilegeId != null ? privilegeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AuthRolePrivilegePK)) {
            return false;
        }
        AuthRolePrivilegePK other = (AuthRolePrivilegePK) object;
        if ((this.roleId == null && other.roleId != null) || (this.roleId != null && !this.roleId.equals(other.roleId))) {
            return false;
        }
        if ((this.privilegeId == null && other.privilegeId != null) || (this.privilegeId != null && !this.privilegeId.equals(other.privilegeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "net.huadong.tech.privilege.entity.AuthRolePrivilegePK[ roleId=" + roleId + ", privilegeId=" + privilegeId + " ]";
    }
    
}
