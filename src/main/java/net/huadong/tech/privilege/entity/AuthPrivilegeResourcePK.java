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
public class AuthPrivilegeResourcePK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 36)
    @Column(name = "PRIVILEGE_ID")
    private String privilegeId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 36)
    @Column(name = "RESOURCE_ID")
    private String resourceId;

    public AuthPrivilegeResourcePK() {
    }

    public AuthPrivilegeResourcePK(String privilegeId, String resourceId) {
        this.privilegeId = privilegeId;
        this.resourceId = resourceId;
    }

    public String getPrivilegeId() {
        return privilegeId;
    }

    public void setPrivilegeId(String privilegeId) {
        this.privilegeId = privilegeId;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (privilegeId != null ? privilegeId.hashCode() : 0);
        hash += (resourceId != null ? resourceId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AuthPrivilegeResourcePK)) {
            return false;
        }
        AuthPrivilegeResourcePK other = (AuthPrivilegeResourcePK) object;
        if ((this.privilegeId == null && other.privilegeId != null) || (this.privilegeId != null && !this.privilegeId.equals(other.privilegeId))) {
            return false;
        }
        if ((this.resourceId == null && other.resourceId != null) || (this.resourceId != null && !this.resourceId.equals(other.resourceId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "net.huadong.tech.privilege.entity.AuthPrivilegeResourcePK[ privilegeId=" + privilegeId + ", resourceId=" + resourceId + " ]";
    }
    
}
