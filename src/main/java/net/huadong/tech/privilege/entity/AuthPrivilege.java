/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.huadong.tech.privilege.entity;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.eclipse.persistence.annotations.UuidGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

import net.huadong.tech.base.bean.AuditEntityBean;

/**
 * 权限管理实体
 *
 * @author ws
 */
@Entity
@Table(name = "AUTH_PRIVILEGE")
public class AuthPrivilege extends AuditEntityBean implements Serializable {
	public static final String TYPE_OPEN="open";
	public static final String TYPE_CLOSED="closed";
	public static final String PRIVILEGE_TYPE_SYS="0";//菜单权限,系统用

	public static final String ROOT_TOP="-1";//顶级菜单
	public static final String ROOT_SYS="0";//系统内菜单
	private static final long serialVersionUID = 1L;
	@Id
	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 36)
	@Column(name = "PRIVILEGE_ID")
	private String privilegeId;
	
	@Column(name = "PRIVILEGE_TYPE")
	private String privilegeType;
	
	@Size(max = 36)
	@Column(name = "PARENT_ID")
	private String parentId;
	@Size(max = 20)
	@Column(name = "TEXT")
	private String text;
	@Column(name = "EN_TEXT")
	private String enText;
	
	@Size(max = 255)
	@Column(name = "URL")
	private String url;
	@Column(name = "SORTER")
	private BigInteger sorter;
	@Size(max = 20)
	@Column(name = "STATE")
	private String state;
	@Size(max = 20)
	@Column(name = "ICON_CLS")
	private String iconCls;
	
	@Size(max = 1)
	@Column(name = "OPEN_TYPE")
	private String openType;
	@Size(max = 255)
	@Column(name = "DESCRIPTION")
	private String description;
	
	@Size(max = 100)
	@Column(name = "IMG_ADDRESS")
	private String imgAddress;

	@Transient
	private boolean checked;
	@Transient
	private String id;

	@ManyToOne(fetch = FetchType.LAZY,cascade=CascadeType.DETACH)
	@JoinColumn(name = "PARENT_ID", insertable = false, updatable = false)
	private  transient AuthPrivilege parentPrivilege;

	@OneToMany(mappedBy = "parentPrivilege", fetch = FetchType.LAZY,cascade=CascadeType.DETACH)
	private  transient List<AuthPrivilege> children = new ArrayList<>();

	@JsonIgnore
	public List<AuthPrivilege> getChildren() {
		return children;
	}

	public void setChildren(List<AuthPrivilege> children) {
		this.children = children;
	}

	@JsonIgnore
	public AuthPrivilege getParentPrivilege() {
		return parentPrivilege;
	}

	public void setParentPrivilege(AuthPrivilege parentPrivilege) {
		this.parentPrivilege = parentPrivilege;
	}

	public String getId() {
		return privilegeId;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getImgAddress() {
		return imgAddress;
	}

	public void setImgAddress(String imgAddress) {
		this.imgAddress = imgAddress;
	}

	public String getPrivilegeId() {
		return privilegeId;
	}

	public void setPrivilegeId(String privilegeId) {
		this.privilegeId = privilegeId;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public BigInteger getSorter() {
		return sorter;
	}

	public void setSorter(BigInteger sorter) {
		this.sorter = sorter;
	}
	

	public String getOpenType() {
		return openType;
	}

	public void setOpenType(String openType) {
		this.openType = openType;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}


	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "net.huadong.idev.tpl.privilege.entity.AuthPrivilege[ privilegeId=" + privilegeId + " ]";
	}

	/**
	 * @return the children
	 */

	/**
	 * @return the checked
	 */
	public boolean isChecked() {
		return checked;
	}

	/**
	 * @param checked
	 *            the checked to set
	 */
	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public String getPrivilegeType() {
		return privilegeType;
	}

	public void setPrivilegeType(String privilegeType) {
		this.privilegeType = privilegeType;
	}

	public String getEnText() {
		return enText;
	}

	public void setEnText(String enText) {
		this.enText = enText;
	}
	
}
