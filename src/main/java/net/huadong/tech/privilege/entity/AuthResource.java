/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.huadong.tech.privilege.entity;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import org.eclipse.persistence.annotations.UuidGenerator;

import net.huadong.tech.base.bean.AuditEntityBean;

/**
 * 资源管理实体类
 *
 * @author ws
 */
@Entity
@Table(name = "AUTH_RESOURCE")
@XmlRootElement
public class AuthResource extends AuditEntityBean implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@UuidGenerator(name = "UUID")
	@GeneratedValue(generator = "UUID")
	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 36)
	@Column(name = "RESOURCE_ID")
	private String resourceId;
	@Basic(optional = false)
	@NotNull
	@Column(name = "DTYPE")
	private BigInteger dtype;
	@Size(max = 1024)
	@Column(name = "NAME")
	private String name;
	@Size(max = 1024)
	@Column(name = "RESOURCE_TYP")
	private String resource;
	@Size(max = 1024)
	@Column(name = "DESCRIPTION")
	private String description;
	@Size(max = 1024)
	@Column(name = "PERMISSIONS")
	private String permissions;

	public AuthResource() {
	}

	public AuthResource(String resourceId) {
		this.resourceId = resourceId;
	}

	public AuthResource(String resourceId, BigInteger dtype) {
		this.resourceId = resourceId;
		this.dtype = dtype;
	}

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	public BigInteger getDtype() {
		return dtype;
	}

	public void setDtype(BigInteger dtype) {
		this.dtype = dtype;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPermissions() {
		return permissions;
	}

	public void setPermissions(String permissions) {
		this.permissions = permissions;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (resourceId != null ? resourceId.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof AuthResource)) {
			return false;
		}
		AuthResource other = (AuthResource) object;
		if ((this.resourceId == null && other.resourceId != null)
				|| (this.resourceId != null && !this.resourceId.equals(other.resourceId))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "net.huadong.idev.tpl.privilege.entity.AuthResource[ resourceId=" + resourceId + " ]";
	}

}
