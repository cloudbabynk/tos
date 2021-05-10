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
@Table(name="SHIP_IN_OUT_CHECK")
@NamedQuery(name="ShipInOutCheck.findAll", query="SELECT c FROM ShipInOutCheck c")
public class ShipInOutCheck extends AuditEntityBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="CHECK_ID")
	private String checkId;
	
	@Column(name="SHIP_NO")
	private String shipNo;
	
	@Column(name="VC_VIN_NO")
	private String vcVinNo;
	
	@Column(name="VC_PORT")
	private String vcPort;
	
	@Column(name="VC_GARAGE")
	private String vcGarage;
	
	@Column(name="WORK_TYP")
	private String workTyp;
	
	@Column(name="CY_AREA_NO")
	private String cyAreaNo;
	
	@Column(name="CY_ROW_NO")
	private String cyRowNo;
	
	public String getCyAreaNo() {
		return cyAreaNo;
	}

	public void setCyAreaNo(String cyAreaNo) {
		this.cyAreaNo = cyAreaNo;
	}

	public String getCyRowNo() {
		return cyRowNo;
	}

	public void setCyRowNo(String cyRowNo) {
		this.cyRowNo = cyRowNo;
	}

	@Transient
	private String vcPortNam;

	public ShipInOutCheck() {
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

	public String getCheckId() {
		return checkId;
	}

	public void setCheckId(String checkId) {
		this.checkId = checkId;
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

	public String getVcGarage() {
		return vcGarage;
	}

	public void setVcGarage(String vcGarage) {
		this.vcGarage = vcGarage;
	}

	public String getWorkTyp() {
		return workTyp;
	}

	public void setWorkTyp(String workTyp) {
		this.workTyp = workTyp;
	}


}