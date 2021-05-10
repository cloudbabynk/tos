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
@Table(name="SHIP_IN")
@NamedQuery(name="ShipIn.findAll", query="SELECT c FROM ShipIn c")
public class ShipIn extends AuditEntityBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="IN_ID")
	private String inId;
	
	@Column(name="SHIP_NO")
	private String shipNo;
	
	@Column(name="VC_VIN_NO")
	private String vcVinNo;
	
	@Column(name="VC_SHIP_ID")
	private String vcShipId;
	
	@Column(name="D_CREATE_DATE")
	private String vcCreateDate;
	
	@Column(name="VC_PORT")
	private String vcPort;
	
	@Column(name="VC_START_SITE")
	private String vcStartSite;
	
	@Transient
	private String vcPortNam;

	public ShipIn() {
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

	public String getVcPort() {
		return vcPort;
	}

	public void setVcPort(String vcPort) {
		this.vcPort = vcPort;
	}

	public String getInId() {
		return inId;
	}

	public void setInId(String inId) {
		this.inId = inId;
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

	public String getVcStartSite() {
		return vcStartSite;
	}

	public void setVcStartSite(String vcStartSite) {
		this.vcStartSite = vcStartSite;
	}

}