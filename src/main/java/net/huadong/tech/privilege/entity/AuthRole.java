/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.huadong.tech.privilege.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import org.eclipse.persistence.annotations.Customizer;
import org.eclipse.persistence.annotations.UuidGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

import net.huadong.tech.base.bean.AuditEntityBean;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "AUTH_ROLE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AuthRole.find", query = "SELECT a FROM AuthRole a"),
    @NamedQuery(name = "AuthRole.findByParentId", query = "SELECT a FROM AuthRole a WHERE a.parentId = :parentId"),
})
public class AuthRole extends AuditEntityBean implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 36)
    @Column(name = "ROLE_ID")
    private String roleId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "NAME")
    private String name;
    @Size(max = 36)
    @Column(name = "PARENT_ID")
    private String parentId;
    @Column(name = "ORGN_ID")
    private String orgnId;
    @Size(max = 1)
    @Column(name = "IS_ADMIN")
    private String isAdmin;//1是true
    @Transient
    private boolean admin;
    public boolean isAdmin()
    {
        return (isAdmin != null && isAdmin.equals("1"));
    }
    public String getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(String isAdmin) {
        this.isAdmin = isAdmin;
    }
    @Size(max = 255)
    @Column(name = "DESCRIPTION")
    private String description;


    @Transient
    private String text;
//    @OneToMany(cascade = {CascadeType.ALL})
//    @JoinColumn(name = "PARENT_ID", referencedColumnName = "ID", insertable = false, updatable = false)
//    private Collection<AuthRole> children;
    @Transient
    private boolean checked;
    @Transient
    private String tenantName;

    public AuthRole() {
    }

    public String getOrgnId() {
		return orgnId;
	}
	public void setOrgnId(String orgnId) {
		this.orgnId = orgnId;
	}
	public AuthRole(String roleId) {
        this.roleId = roleId;
    }

    public AuthRole(String roleId, String name) {
        this.roleId = roleId;
        this.name = name;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
	@Override
    public int hashCode() {
        int hash = 0;
        hash += (roleId != null ? roleId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AuthRole)) {
            return false;
        }
        AuthRole other = (AuthRole) object;
        if ((this.roleId == null && other.roleId != null) || (this.roleId != null && !this.roleId.equals(other.roleId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "net.huadong.idev.tpl.privilege.entity.AuthRole[ roleId=" + roleId + " ]";
    }

    /**
     * @return the text
     */
    public String getText() {
        return text;
    }

//    /**
//     * @return the children
//     */
//    @JsonIgnore
//    public Collection<AuthRole> getChildren() {
//        return children;
//    }

    /**
     * @return the checked
     */
    public boolean isChecked() {
        return checked;
    }

    /**
     * @param checked the checked to set
     */
    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
