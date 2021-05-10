package net.huadong.tech.privilege.entity;

import java.io.Serializable;
import javax.persistence.*;
import net.huadong.tech.base.bean.AuditEntityBean;


/**
 * The persistent class for the SEL_PAGE database table.
 * 
 */
@Entity(name = "SelPage")
@Table(name="SEL_PAGE")
@NamedQuery(name="SelPage.findAll", query="SELECT s FROM SelPage s")
public class SelPage   extends AuditEntityBean  implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="PAGEID")
	private String pageid;

	@Column(name="LOCATION")
	private int location;
	
	@Column(name="PRIVILEGE_ID")
	private String privilegeId;
    
	@Column(name="URL")
	private String url;
	
	@Column(name="USER")
	private String user;
	
	@Column(name="WIDTH")
	private String width;
	
	@Column(name="HEIGHT")
	private String height;
	
	@Column(name="TITLE")
	private String title;

	public SelPage() {
	}

	public String getPageid() {
		return pageid;
	}

	public void setPageid(String pageid) {
		this.pageid = pageid;
	}

	public int getLocation() {
		return location;
	}

	public void setLocation(int location) {
		this.location = location;
	}

	public String getPrivilegeId() {
		return privilegeId;
	}

	public void setPrivilegeId(String privilegeId) {
		this.privilegeId = privilegeId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	

}