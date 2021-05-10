package net.huadong.tech.base.entity;

import java.io.Serializable;
import javax.persistence.*;

import net.huadong.tech.base.bean.AuditEntityBean;

import java.util.Date;


/**
 * The persistent class for the C_AREA database table.
 * 
 */
@Entity
@Table(name="C_AREA")
@NamedQuery(name="CArea.findAll", query="SELECT c FROM CArea c")
public class CArea extends AuditEntityBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="AREA_COD")
	private String areaCod;

	@Column(name="AREA_NAM")
	private String areaNam;


	private String remarks;

	
	public CArea() {
	}

	public String getAreaCod() {
		return this.areaCod;
	}

	public void setAreaCod(String areaCod) {
		this.areaCod = areaCod;
	}

	public String getAreaNam() {
		return this.areaNam;
	}

	public void setAreaNam(String areaNam) {
		this.areaNam = areaNam;
	}
	
	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}


}