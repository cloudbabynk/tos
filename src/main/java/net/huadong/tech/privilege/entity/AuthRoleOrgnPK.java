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
 * @author Administrator
 */
@Embeddable
public class AuthRoleOrgnPK implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 36)
    @Column(name = "ROLE_ID")
    private String roleId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 36)
    @Column(name = "ORGN_ID")
    private String orgnId;
    @Size(max = 36)
    @Column(name = "PRIVILEGE_ID")
    private String privilegeId;

    public AuthRoleOrgnPK() {
    }

    public AuthRoleOrgnPK(String roleId, String orgnId) {
        this.roleId = roleId;
        this.orgnId = orgnId;
    }

    public AuthRoleOrgnPK(String roleId, String privilegeId, String orgnId) {
    	 this.roleId = roleId;
         this.orgnId = orgnId;
         this.privilegeId = privilegeId;
	}

	public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getOrgnId() {
        return orgnId;
    }

    public void setOrgnId(String orgnId) {
        this.orgnId = orgnId;
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
        hash = 13 * hash + Objects.hashCode(this.orgnId);
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
        final AuthRoleOrgnPK other = (AuthRoleOrgnPK) obj;
        if (!Objects.equals(this.roleId, other.roleId)) {
            return false;
        }
        if (!Objects.equals(this.orgnId, other.orgnId)) {
            return false;
        }
        if (!Objects.equals(this.privilegeId, other.privilegeId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "AuthRoleOrgnPK{" + "roleId=" + roleId + ", orgnId=" + orgnId + ", privilegeId=" + privilegeId + '}';
    }
    
}
