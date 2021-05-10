package net.huadong.tech.ship.entity;

import java.io.Serializable;
import javax.persistence.*;

import net.huadong.tech.base.bean.AuditEntityBean;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the SHIP_EXECUTION database table.
 * 
 */
@Entity
@Table(name="SHIP_EXECUTION")
@NamedQuery(name="ShipExecution.findAll", query="SELECT s FROM ShipExecution s")
public class ShipExecution extends AuditEntityBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="SHIP_EXECUTION_ID")
	private String shipExecutionId;

	@Column(name="I_E_ID")
	private String iEId;

	@Column(name="PLAN_TON")
	private BigDecimal planTon;

	private String remarks;

	@Column(name="SHIP_NO")
	private String shipNo;

	@Column(name="WOKR_NUM")
	private BigDecimal wokrNum;

	@Column(name="WORK_TON")
	private BigDecimal workTon;

	@Column(name="WORK_VOLUME")
	private BigDecimal workVolume;

	@Column(name="CARGO_KIND")
	private String cargoKind;
	
	@Transient
	private String ieNam;
	
	public ShipExecution() {
	}

	public String getShipExecutionId() {
		return this.shipExecutionId;
	}

	public void setShipExecutionId(String shipExecutionId) {
		this.shipExecutionId = shipExecutionId;
	}

	public BigDecimal getPlanTon() {
		return this.planTon;
	}

	public String getiEId() {
		return iEId;
	}

	public void setiEId(String iEId) {
		this.iEId = iEId;
	}

	public String getCargoKind() {
		return cargoKind;
	}

	public void setCargoKind(String cargoKind) {
		this.cargoKind = cargoKind;
	}

	public void setPlanTon(BigDecimal planTon) {
		this.planTon = planTon;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getShipNo() {
		return this.shipNo;
	}

	public void setShipNo(String shipNo) {
		this.shipNo = shipNo;
	}

	public BigDecimal getWokrNum() {
		return this.wokrNum;
	}

	public void setWokrNum(BigDecimal wokrNum) {
		this.wokrNum = wokrNum;
	}

	public BigDecimal getWorkTon() {
		return this.workTon;
	}

	public void setWorkTon(BigDecimal workTon) {
		this.workTon = workTon;
	}

	public BigDecimal getWorkVolume() {
		return this.workVolume;
	}

	public void setWorkVolume(BigDecimal workVolume) {
		this.workVolume = workVolume;
	}

	public String getIeNam() {
		return ieNam;
	}

	public void setIeNam(String ieNam) {
		this.ieNam = ieNam;
	}
	
	

}