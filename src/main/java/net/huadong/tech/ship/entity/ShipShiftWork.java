package net.huadong.tech.ship.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the SHIP_SHIFT_WORK database table.
 * 
 */
@Entity
@Table(name="SHIP_SHIFT_WORK")
@NamedQuery(name="ShipShiftWork.findAll", query="SELECT s FROM ShipShiftWork s")
public class ShipShiftWork implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="SHIP_SHIFT_ID")
	private String shipShiftId;

	@Column(name="REC_NAM")
	private String recNam;

	@Temporal(TemporalType.DATE)
	@Column(name="REC_TIM")
	private Date recTim;

	private String remarks;

	@Column(name="SHIP_NO")
	private String shipNo;

	@Column(name="SHIP_SHIFT_TYP")
	private String shipShiftTyp;

	@Column(name="UPD_NAM")
	private String updNam;

	@Temporal(TemporalType.DATE)
	@Column(name="UPD_TIM")
	private Date updTim;

	@Temporal(TemporalType.DATE)
	@Column(name="WORK_BEG_TIM")
	private Date workBegTim;

	@Temporal(TemporalType.DATE)
	@Column(name="WORK_END_TIM")
	private Date workEndTim;

	@Column(name="WORK_NUM")
	private BigDecimal workNum;

	public ShipShiftWork() {
	}

	public String getShipShiftId() {
		return this.shipShiftId;
	}

	public void setShipShiftId(String shipShiftId) {
		this.shipShiftId = shipShiftId;
	}

	public String getRecNam() {
		return this.recNam;
	}

	public void setRecNam(String recNam) {
		this.recNam = recNam;
	}

	public Date getRecTim() {
		return this.recTim;
	}

	public void setRecTim(Date recTim) {
		this.recTim = recTim;
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

	public String getShipShiftTyp() {
		return this.shipShiftTyp;
	}

	public void setShipShiftTyp(String shipShiftTyp) {
		this.shipShiftTyp = shipShiftTyp;
	}

	public String getUpdNam() {
		return this.updNam;
	}

	public void setUpdNam(String updNam) {
		this.updNam = updNam;
	}

	public Date getUpdTim() {
		return this.updTim;
	}

	public void setUpdTim(Date updTim) {
		this.updTim = updTim;
	}

	public Date getWorkBegTim() {
		return this.workBegTim;
	}

	public void setWorkBegTim(Date workBegTim) {
		this.workBegTim = workBegTim;
	}

	public Date getWorkEndTim() {
		return this.workEndTim;
	}

	public void setWorkEndTim(Date workEndTim) {
		this.workEndTim = workEndTim;
	}

	public BigDecimal getWorkNum() {
		return this.workNum;
	}

	public void setWorkNum(BigDecimal workNum) {
		this.workNum = workNum;
	}

}