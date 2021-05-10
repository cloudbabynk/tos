/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.huadong.tech.privilege.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author liangjh
 * @date 2017年9月15日上午9:39:04
 * @description TODO
 */
@Embeddable
public class AuthRoleResourcePK implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 36)
    @Column(name = "ROLE_ID")
    private String roleId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 36)
    @Column(name = "RESOURCE_ID")
    private String resourceId;
    @Size(max = 36)
    @Column(name = "PRIVILEGE_ID")
    private String privilegeId;

    public AuthRoleResourcePK() {
    }

    public AuthRoleResourcePK(String roleId, String resourceId, String privilegeId) {
        this.roleId = roleId;
        this.resourceId = resourceId;
        this.privilegeId = privilegeId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

       public String getPrivilegeId() {
        return privilegeId;
    }

    public void setPrivilegeId(String privilegeId) {
        this.privilegeId = privilegeId;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 13 * hash + Objects.hashCode(this.roleId);
        hash = 13 * hash + Objects.hashCode(this.resourceId);
        hash = 13 * hash + Objects.hashCode(this.privilegeId);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AuthRoleResourcePK other = (AuthRoleResourcePK) obj;
        if (!Objects.equals(this.roleId, other.roleId)) {
            return false;
        }
        if (!Objects.equals(this.resourceId, other.resourceId)) {
            return false;
        }
        if (!Objects.equals(this.privilegeId, other.privilegeId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "AuthRoleResourcePK{" + "roleId=" + roleId + ", resourceId=" + resourceId + ", privilegeId=" + privilegeId + '}';
    }
    
}
