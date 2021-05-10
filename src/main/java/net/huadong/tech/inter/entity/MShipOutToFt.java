package net.huadong.tech.inter.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;

import net.huadong.tech.base.bean.AuditEntityBean;

import java.util.Date;


/**
 * The persistent class for the C_BRAND database table.
 * 
 */
@Entity
@Table(name="M_SHIP_OUT_TO_FT")
@NamedQuery(name="MShipOutToFt.findAll", query="SELECT m FROM MShipOutToFt m")
public class MShipOutToFt implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="OUT_ID")
	private String outId;
	
	public String getOutId() {
		return outId;
	}

	public void setOutId(String outId) {
		this.outId = outId;
	}

	@Column(name="VC_VIN_NO")
	private String vcVinNo;
	
	@Column(name="VC_SITE")
	private String vcSite;
	
	@Column(name="D_CREATE_DATE")
	private String vcCreateDate;
	
	@Column(name="VC_EXCEPTION")
	private String vcException;
	
	@Column(name="VC_SHIP_ID")
	private String vcShipId;
	
	@Transient
	private String vcPortNam;

	public MShipOutToFt() {
	}

	public String getVcSite() {
		return vcSite;
	}

	public void setVcSite(String vcSite) {
		this.vcSite = vcSite;
	}

	public String getVcException() {
		return vcException;
	}

	public void setVcException(String vcException) {
		this.vcException = vcException;
	}

	public String getVcPortNam() {
		return vcPortNam;
	}

	public void setVcPortNam(String vcPortNam) {
		this.vcPortNam = vcPortNam;
	}


	public String getVcVinNo() {
		return vcVinNo;
	}

	public void setVcVinNo(String vcVinNo) {
		this.vcVinNo = vcVinNo;
	}


	public String getVcShipId() {
		return vcShipId;
	}

	public void setVcShipId(String vcShipId) {
		this.vcShipId = vcShipId;
	}

	public String getVcCreateDate() {
		return vcCreateDate;
	}

	public void setVcCreateDate(String vcCreateDate) {
		this.vcCreateDate = vcCreateDate;
	}


}