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
public class AuthUserPrivilegePK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 36)
    @Column(name = "PRIVILEGE_ID")
    private String privilegeId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 36)
    @Column(name = "USER_ID")
    private String userId;

    public AuthUserPrivilegePK() {
    }

    public AuthUserPrivilegePK(String privilegeId, String userId) {
        this.privilegeId = privilegeId;
        this.userId = userId;
    }

    public String getPrivilegeId() {
        return privilegeId;
    }

    public void setPrivilegeId(String privilegeId) {
        this.privilegeId = privilegeId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (privilegeId != null ? privilegeId.hashCode() : 0);
        hash += (userId != null ? userId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AuthUserPrivilegePK)) {
            return false;
        }
        AuthUserPrivilegePK other = (AuthUserPrivilegePK) object;
        if ((this.privilegeId == null && other.privilegeId != null) || (this.privilegeId != null && !this.privilegeId.equals(other.privilegeId))) {
            return false;
        }
        if ((this.userId == null && other.userId != null) || (this.userId != null && !this.userId.equals(other.userId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "net.huadong.tech.privilege.entity.AuthUserPrivilegePK[ privilegeId=" + privilegeId + ", userId=" + userId + " ]";
    }
    
}
