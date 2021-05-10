package net.huadong.tech.base.entity;

import java.io.Serializable;
import javax.persistence.*;

import net.huadong.tech.base.bean.AuditEntityBean;

import java.util.Date;


/**
 * The persistent class for the C_BRAND database table.
 * 
 */
@Entity
@Table(name="C_BRAND")
@NamedQuery(name="CBrand.findAll", query="SELECT c FROM CBrand c")
public class CBrand extends AuditEntityBean implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final String Y = "1"; //审核状态是
	public static final String N = "0"; //审核状态否
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="BRAND_COD")
	private String brandCod;

	@Column(name="BRAND_NAM")
	private String brandNam;
	
	@Column(name="BRAND_ENAME")
	private String brandEname;

	@Column(name="FACTORY_COD")
	private String factoryCod;

	@Column(name="REMARKS")
	private String remarks;
	
	@Column(name="CHECK_FLAG")
	private String checkFlag;
	
	@Column(name="BRAND_SHORT")
	private String brandShort;
	
	@Column(name="COLOUR_SET")
	private String colourSet;
	
	@Transient
	private String factoryCodNam;


	public CBrand() {
	}
	
	public String getColourSet() {
		return colourSet;
	}

	public void setColourSet(String colourSet) {
		this.colourSet = colourSet;
	}
	
	public String getFactoryCodNam() {
		return factoryCodNam;
	}

	public void setFactoryCodNam(String factoryCodNam) {
		this.factoryCodNam = factoryCodNam;
	}

	public String getBrandShort() {
		return brandShort;
	}

	public void setBrandShort(String brandShort) {
		this.brandShort = brandShort;
	}

	public String getBrandCod() {
		return this.brandCod;
	}

	public void setBrandCod(String brandCod) {
		this.brandCod = brandCod;
	}

	public String getBrandNam() {
		return this.brandNam;
	}

	public void setBrandNam(String brandNam) {
		this.brandNam = brandNam;
	}

	public String getFactoryCod() {
		return this.factoryCod;
	}

	public void setFactoryCod(String factoryCod) {
		this.factoryCod = factoryCod;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getCheckFlag() {
		return checkFlag;
	}

	public void setCheckFlag(String checkFlag) {
		this.checkFlag = checkFlag;
	}

	public String getBrandEname() {
		return brandEname;
	}

	public void setBrandEname(String brandEname) {
		this.brandEname = brandEname;
	}


}