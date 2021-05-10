package net.huadong.tech.ship.entity;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;

import net.huadong.tech.base.bean.AuditEntityBean;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;


/**
 * The persistent class for the SHIP_TIM_WORK database table.
 * 
 */
@Entity
@Table(name="SHIP_TIM_WORK")
@NamedQuery(name="ShipTimWork.findAll", query="SELECT s FROM ShipTimWork s")
public class ShipTimWork extends AuditEntityBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="SHIP_TIMWORK_ID")
	private String shipTimworkId;
	
    @Transient 
    private String shipTimTypNam;
    
	private String description;

	private BigDecimal lengths;

	private String remarks;

	@Column(name="SHIP_NO")
	private String shipNo;

	@Column(name="SHIP_TIM_TYP")
	private String shipTimTyp;

	private BigDecimal tons;


	@Column(name="USE_DEVICES")
	private String useDevices;

	@Column(name="\"VOLUMES\"")
	private BigDecimal volumes;

	@Column(name="WAIT_TIM")
	private BigDecimal waitTim;

	@Column(name="WORK_BEG_TIM")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
	private Timestamp workBegTim;
	
	@Column(name="WORK_END_TIM")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
	private Timestamp workEndTim;

	public ShipTimWork() {
	}

	public String getShipTimworkId() {
		return this.shipTimworkId;
	}

	public void setShipTimworkId(String shipTimworkId) {
		this.shipTimworkId = shipTimworkId;
	}

	public String getShipTimTypNam() {
		return shipTimTypNam;
	}

	public void setShipTimTypNam(String shipTimTypNam) {
		this.shipTimTypNam = shipTimTypNam;
	}

	
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getLengths() {
		return this.lengths;
	}

	public void setLengths(BigDecimal lengths) {
		this.lengths = lengths;
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

	public String getShipTimTyp() {
		return this.shipTimTyp;
	}

	public void setShipTimTyp(String shipTimTyp) {
		this.shipTimTyp = shipTimTyp;
	}

	public BigDecimal getTons() {
		return this.tons;
	}

	public void setTons(BigDecimal tons) {
		this.tons = tons;
	}

	public String getUseDevices() {
		return this.useDevices;
	}

	public void setUseDevices(String useDevices) {
		this.useDevices = useDevices;
	}

	public BigDecimal getVolumes() {
		return this.volumes;
	}

	public void setVolumes(BigDecimal volumes) {
		this.volumes = volumes;
	}

	public BigDecimal getWaitTim() {
		return this.waitTim;
	}

	public void setWaitTim(BigDecimal waitTim) {
		this.waitTim = waitTim;
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