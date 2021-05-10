package net.huadong.tech.ship.entity;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;

import net.huadong.tech.base.bean.AuditEntityBean;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;


/**
 * The persistent class for the SHIP_MOOR database table.
 * 
 */
@Entity
@Table(name="SHIP_MOOR")
@NamedQuery(name="ShipMoor.findAll", query="SELECT s FROM ShipMoor s")
public class ShipMoor  extends AuditEntityBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="SHIP_MOOR_ID")
	private String shipMoorId;

	@Transient 
	private String berthNam;
	
	@Column(name="BERTH_COD")
	private String berthCod;

	private String director;

	@Column(name="DRAFT_A")
	private BigDecimal draftA;

	@Column(name="DRAFT_F")
	private BigDecimal draftF;

	@Column(name="MOOR_ID")
	private String moorId;

	private String remarks;

	@Column(name="SHIP_NO")
	private String shipNo;

	@Column(name="WORK_BEG_TIM")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm") 
	private Timestamp workBegTim;

	@Column(name="WORK_END_TIM")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm") 
	private Timestamp workEndTim;

	public ShipMoor() {
	}

	public String getShipMoorId() {
		return this.shipMoorId;
	}

	public void setShipMoorId(String shipMoorId) {
		this.shipMoorId = shipMoorId;
	}

	public String getBerthCod() {
		return this.berthCod;
	}

	public void setBerthCod(String berthCod) {
		this.berthCod = berthCod;
	}

	public String getBerthNam() {
		return berthNam;
	}

	public void setBerthNam(String berthNam) {
		this.berthNam = berthNam;
	}

	public String getDirector() {
		return this.director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public BigDecimal getDraftA() {
		return this.draftA;
	}

	public void setDraftA(BigDecimal draftA) {
		this.draftA = draftA;
	}

	public BigDecimal getDraftF() {
		return this.draftF;
	}

	public void setDraftF(BigDecimal draftF) {
		this.draftF = draftF;
	}

	public String getMoorId() {
		return this.moorId;
	}

	public void setMoorId(String moorId) {
		this.moorId = moorId;
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

	public Timestamp getWorkBegTim() {
		return workBegTim;
	}

	public void setWorkBegTim(Timestamp workBegTim) {
		this.workBegTim = workBegTim;
	}

	public Timestamp getWorkEndTim() {
		return workEndTim;
	}

	public void setWorkEndTim(Timestamp workEndTim) {
		this.workEndTim = workEndTim;
	}



}