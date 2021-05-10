package net.huadong.tech.base.entity;

import java.io.Serializable;
import javax.persistence.*;

import net.huadong.tech.base.bean.AuditEntityBean;

import java.util.Date;


/**
 * The persistent class for the M_WORK_MEET database table.
 * 
 */
@Entity
@Table(name="M_WORK_MEET")
@NamedQuery(name="MWorkMeet.findAll", query="SELECT c FROM MWorkMeet c")
public class MWorkMeet extends AuditEntityBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID")
	private String id;

	@Column(name="SHIP_NO")
	private String shipNo;

	@Column(name="SHIP_TREND")
	private String shipTrend;

	@Column(name="CARGO_INFO")
	private String cargoInfo;
	
	@Column(name="WORK_PLAN")
	private String workPlan;
	
	@Column(name="SAFE_NOTE")
	private String safeNote;
	
	@Column(name="TELEPHONE_ALL")
	private String telephoneAll;

	public MWorkMeet() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getShipNo() {
		return shipNo;
	}

	public void setShipNo(String shipNo) {
		this.shipNo = shipNo;
	}

	public String getShipTrend() {
		return shipTrend;
	}

	public void setShipTrend(String shipTrend) {
		this.shipTrend = shipTrend;
	}

	public String getCargoInfo() {
		return cargoInfo;
	}

	public void setCargoInfo(String cargoInfo) {
		this.cargoInfo = cargoInfo;
	}

	public String getWorkPlan() {
		return workPlan;
	}

	public void setWorkPlan(String workPlan) {
		this.workPlan = workPlan;
	}

	public String getSafeNote() {
		return safeNote;
	}

	public void setSafeNote(String safeNote) {
		this.safeNote = safeNote;
	}

	public String getTelephoneAll() {
		return telephoneAll;
	}

	public void setTelephoneAll(String telephoneAll) {
		this.telephoneAll = telephoneAll;
	}


}