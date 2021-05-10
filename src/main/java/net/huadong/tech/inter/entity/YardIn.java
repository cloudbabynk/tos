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
@Table(name="YARD_IN")
@NamedQuery(name="yardIn.findAll", query="SELECT c FROM YardIn c")
public class YardIn extends AuditEntityBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="IN_ID")
	private String inId;
	
	@Column(name="SHIP_NO")
	private String shipNo;
	
	@Column(name="VC_VIN_NO")
	private String vcVinNo;
	
	@Column(name="VC_SITE")
	private String vcSite;
	
	@Column(name="D_YARD_IN_TIME")
	private String dYardInTime;
	
	@Transient
	private String vcPortNam;

	public YardIn() {
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

	public String getInId() {
		return inId;
	}

	public void setInId(String inId) {
		this.inId = inId;
	}

	public String getVcSite() {
		return vcSite;
	}

	public void setVcSite(String vcSite) {
		this.vcSite = vcSite;
	}

	public String getdYardInTime() {
		return dYardInTime;
	}

	public void setdYardInTime(String dYardInTime) {
		this.dYardInTime = dYardInTime;
	}


}