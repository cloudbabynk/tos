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
@Table(name="SHIP_OUT")
@NamedQuery(name="ShipOut.findAll", query="SELECT c FROM ShipOut c")
public class ShipOut extends AuditEntityBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="OUT_ID")
	private String outId;
	
	@Column(name="SHIP_NO")
	private String shipNo;
	
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

	public ShipOut() {
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

	public String getShipNo() {
		return shipNo;
	}

	public void setShipNo(String shipNo) {
		this.shipNo = shipNo;
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