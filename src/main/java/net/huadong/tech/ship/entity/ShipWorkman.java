package net.huadong.tech.ship.entity;

import java.io.Serializable;
import javax.persistence.*;

import net.huadong.tech.base.bean.AuditEntityBean;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the SHIP_WORKMAN database table.
 * 
 */
@Entity
@Table(name="SHIP_WORKMAN")
@NamedQuery(name="ShipWorkman.findAll", query="SELECT s FROM ShipWorkman s")
public class ShipWorkman extends AuditEntityBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="SHIP_WORKMAN_ID")
	private String shipWorkmanId;

	private String remarks;

	@Column(name="SHIP_NO")
	private String shipNo;

	@Column(name="WOKR_NUM")
	private BigDecimal wokrNum;

	@Column(name="WOKR_RUN_COD")
	private String wokrRunCod;

	@Column(name="WORK_CONTENT")
	private String workContent;

	@Temporal(TemporalType.DATE)
	@Column(name="WORK_DAY")
	private Date workDay;

	@Column(name="WORK_TYP")
	private String workTyp;
    //班次透明字段
	@Transient 
	private String workRunNam;
	
	public String getWorkRunNam() {
		return workRunNam;
	}

	public void setWorkRunNam(String workRunNam) {
		this.workRunNam = workRunNam;
	}

	public ShipWorkman() {
	}

	public String getShipWorkmanId() {
		return this.shipWorkmanId;
	}

	public void setShipWorkmanId(String shipWorkmanId) {
		this.shipWorkmanId = shipWorkmanId;
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

	public String getWokrRunCod() {
		return this.wokrRunCod;
	}

	public void setWokrRunCod(String wokrRunCod) {
		this.wokrRunCod = wokrRunCod;
	}

	public String getWorkContent() {
		return this.workContent;
	}

	public void setWorkContent(String workContent) {
		this.workContent = workContent;
	}

	public Date getWorkDay() {
		return this.workDay;
	}

	public void setWorkDay(Date workDay) {
		this.workDay = workDay;
	}

	public String getWorkTyp() {
		return this.workTyp;
	}

	public void setWorkTyp(String workTyp) {
		this.workTyp = workTyp;
	}

}