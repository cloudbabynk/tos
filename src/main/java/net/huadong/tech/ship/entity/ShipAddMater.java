package net.huadong.tech.ship.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the SHIP_ADD_MATER database table.
 * 
 */
@Entity
@Table(name="SHIP_ADD_MATER")
@NamedQuery(name="ShipAddMater.findAll", query="SELECT s FROM ShipAddMater s")
public class ShipAddMater implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ADD_MATER_ID")
	private String addMaterId;

	@Column(name="ADD_MATER_TYP")
	private String addMaterTyp;

	private String director;

	@Column(name="REC_NAM")
	private String recNam;

	@Temporal(TemporalType.DATE)
	@Column(name="REC_TIM")
	private Date recTim;

	@Column(name="REMARK_TXT")
	private String remarkTxt;

	@Column(name="SHIP_ADD_NUM")
	private BigDecimal shipAddNum;

	@Column(name="SHIP_ADD_UNIT")
	private String shipAddUnit;

	@Column(name="SHIP_NO")
	private String shipNo;

	@Column(name="UPD_NAM")
	private String updNam;

	@Temporal(TemporalType.DATE)
	@Column(name="UPD_TIM")
	private Date updTim;

	public ShipAddMater() {
	}

	public String getAddMaterId() {
		return this.addMaterId;
	}

	public void setAddMaterId(String addMaterId) {
		this.addMaterId = addMaterId;
	}

	public String getAddMaterTyp() {
		return this.addMaterTyp;
	}

	public void setAddMaterTyp(String addMaterTyp) {
		this.addMaterTyp = addMaterTyp;
	}

	public String getDirector() {
		return this.director;
	}

	public void setDirector(String director) {
		this.director = director;
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

	public String getRemarkTxt() {
		return this.remarkTxt;
	}

	public void setRemarkTxt(String remarkTxt) {
		this.remarkTxt = remarkTxt;
	}

	public BigDecimal getShipAddNum() {
		return this.shipAddNum;
	}

	public void setShipAddNum(BigDecimal shipAddNum) {
		this.shipAddNum = shipAddNum;
	}

	public String getShipAddUnit() {
		return this.shipAddUnit;
	}

	public void setShipAddUnit(String shipAddUnit) {
		this.shipAddUnit = shipAddUnit;
	}

	public String getShipNo() {
		return this.shipNo;
	}

	public void setShipNo(String shipNo) {
		this.shipNo = shipNo;
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

}