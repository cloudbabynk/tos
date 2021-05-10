package net.huadong.tech.base.entity;

import java.io.Serializable;
import javax.persistence.*;

import net.huadong.tech.base.bean.AuditEntityBean;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the C_PLACE database table.
 * 
 */
@Entity
@Table(name="C_PLACE")
@NamedQuery(name="CPlace.findAll", query="SELECT c FROM CPlace c")
public class CPlace extends AuditEntityBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="PLAC_COD")
	private String placCod;

	@Column(name="AREA_COD")
	private String areaCod;
	
	@Column(name="AREA_NAM")
	private String areaNam;


	private BigDecimal distince;

	@Column(name="PLAC_NAM")
	private String placNam;

	@Column(name="PLAC_SHORT")
	private String placShort;

	@Column(name="REMARK_TXT")
	private String remarkTxt;
	public CPlace() {
	}

	public String getPlacCod() {
		return this.placCod;
	}

	public void setPlacCod(String placCod) {
		this.placCod = placCod;
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

	public BigDecimal getDistince() {
		return this.distince;
	}

	public void setDistince(BigDecimal distince) {
		this.distince = distince;
	}

	public String getPlacNam() {
		return this.placNam;
	}

	public void setPlacNam(String placNam) {
		this.placNam = placNam;
	}

	public String getPlacShort() {
		return this.placShort;
	}

	public void setPlacShort(String placShort) {
		this.placShort = placShort;
	}

	public String getRemarkTxt() {
		return this.remarkTxt;
	}

	public void setRemarkTxt(String remarkTxt) {
		this.remarkTxt = remarkTxt;
	}

}