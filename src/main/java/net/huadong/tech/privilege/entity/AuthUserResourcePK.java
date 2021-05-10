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
 *
 * @author 徐希龙
 */
@Embeddable
public class AuthUserResourcePK implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 36)
    @Column(name = "RESOURCE_ID")
    private String resourceId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 36)
    @Column(name = "USER_ID")
    private String userId;
    @Size(max = 36)
    @Column(name = "PRIVILEGE_ID")
    private String privilegeId;
    public AuthUserResourcePK() {
    }

    public AuthUserResourcePK(String resourceId, String userId) {
        this.resourceId = resourceId;
        this.userId = userId;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

        public String getPrivilegeId() {
        return privilegeId;
    }

    public void setPrivilegeId(String privilegeId) {
        this.privilegeId = privilegeId;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 19 * hash + Objects.hashCode(this.resourceId);
        hash = 19 * hash + Objects.hashCode(this.userId);
        hash = 19 * hash + Objects.hashCode(this.privilegeId);
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
        final AuthUserResourcePK other = (AuthUserResourcePK) obj;
        if (!Objects.equals(this.resourceId, other.resourceId)) {
            return false;
        }
        if (!Objects.equals(this.userId, other.userId)) {
            return false;
        }
        if (!Objects.equals(this.privilegeId, other.privilegeId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "AuthUserResourcePK{" + "resourceId=" + resourceId + ", userId=" + userId + ", privilegeId=" + privilegeId + '}';
    }
    
}
