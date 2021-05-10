package net.huadong.tech.Interface.entity;

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
@Table(name="SHIP_PLAN_SEND")
@NamedQuery(name="ShipPlanSend.findAll", query="SELECT c FROM ShipPlanSend c")
public class ShipPlanSend extends AuditEntityBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="SPSEND_ID")
	private String spsendId;
	
	@Column(name="TEAM_ORGN_ID")
	private String teamOrgnId;
	
	@Column(name="WORK_DATE")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd") 
	private Timestamp workDate;
	
	@Column(name="SHIFT_CODE")
	private String shiftCode;
	
	@Column(name="SHIP_ID")
	private String shipId;
	
	@Column(name="UNLOAD_FLAG")
	private String unloadFlag;
	
	@Column(name="CABIN_NUM")
	private BigDecimal cabinNum;
	
	@Column(name="CABIN_NO")
	private String cabinNo;
	
	@Column(name="PLAN_WGT")
	private BigDecimal planWgt;
	
	@Column(name="SURP_WGT")
	private BigDecimal surpWgt;
	
	@Column(name="YARD_WGT")
	private BigDecimal yardWgt;
	
	@Column(name="WORK_WGT")
	private BigDecimal workWgt;
	
	@Column(name="PLAN_CNTR_NUM")
	private BigDecimal planCntrNum;
	
	@Column(name="SURP_CNTR_NUM")
	private BigDecimal surpCntrNum;
	
	@Column(name="YARD_CNTR_NUM")
	private BigDecimal yardCntrNum;

	@Column(name="WORK_CNTR_NUM")
	private BigDecimal workCntrNum;

	@Column(name="PLAN_CAR_NUM")
	private BigDecimal planCarNum;

	@Column(name="SURP_CAR_NUM")
	private BigDecimal surpCarNum;
	
	@Column(name="YARD_CAR_NUM")
	private BigDecimal yardCarNum;
	
	@Column(name="WORK_CAR_NUM")
	private BigDecimal workCarNum;
	
	@Column(name="DESCRIPTION")
	private String description;
	
	@Column(name="SUBMIT_FLAG")
	private String submitFlag;
	
	@Column(name="SUBMIT_NAME")
	private String submitName;
	
	@Column(name="SUBMIT_TIME")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss") 
	private String submitTime;
	
	@Column(name="CHECK_FLAG")
	private String checkFlag;
	
	@Column(name="CHECK_NAME")
	private String checkName;
	
	@Column(name="CHECK_TIME")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm") 
	private Timestamp checkTime;
	
	@Column(name="SHIP_NAME")
	private String shipName;
	
	@Column(name="SVOYAGE_ID")
	private String svoyageId;
	
	@Column(name="SHIP_NO")
	private String shipNo;


	public ShipPlanSend() {
	}

	public String getShipNo() {
		return shipNo;
	}


	public void setShipNo(String shipNo) {
		this.shipNo = shipNo;
	}


	public String getSpsendId() {
		return spsendId;
	}


	public void setSpsendId(String spsendId) {
		this.spsendId = spsendId;
	}


	public String getTeamOrgnId() {
		return teamOrgnId;
	}


	public void setTeamOrgnId(String teamOrgnId) {
		this.teamOrgnId = teamOrgnId;
	}


	public Timestamp getWorkDate() {
		return workDate;
	}


	public void setWorkDate(Timestamp workDate) {
		this.workDate = workDate;
	}


	public String getShiftCode() {
		return shiftCode;
	}


	public void setShiftCode(String shiftCode) {
		this.shiftCode = shiftCode;
	}


	public String getShipId() {
		return shipId;
	}


	public void setShipId(String shipId) {
		this.shipId = shipId;
	}


	public String getUnloadFlag() {
		return unloadFlag;
	}


	public void setUnloadFlag(String unloadFlag) {
		this.unloadFlag = unloadFlag;
	}


	public BigDecimal getCabinNum() {
		return cabinNum;
	}


	public void setCabinNum(BigDecimal cabinNum) {
		this.cabinNum = cabinNum;
	}


	public String getCabinNo() {
		return cabinNo;
	}


	public void setCabinNo(String cabinNo) {
		this.cabinNo = cabinNo;
	}


	public BigDecimal getPlanWgt() {
		return planWgt;
	}


	public void setPlanWgt(BigDecimal planWgt) {
		this.planWgt = planWgt;
	}


	public BigDecimal getSurpWgt() {
		return surpWgt;
	}


	public void setSurpWgt(BigDecimal surpWgt) {
		this.surpWgt = surpWgt;
	}


	public BigDecimal getYardWgt() {
		return yardWgt;
	}


	public void setYardWgt(BigDecimal yardWgt) {
		this.yardWgt = yardWgt;
	}


	public BigDecimal getWorkWgt() {
		return workWgt;
	}


	public void setWorkWgt(BigDecimal workWgt) {
		this.workWgt = workWgt;
	}


	public BigDecimal getPlanCntrNum() {
		return planCntrNum;
	}


	public void setPlanCntrNum(BigDecimal planCntrNum) {
		this.planCntrNum = planCntrNum;
	}


	public BigDecimal getSurpCntrNum() {
		return surpCntrNum;
	}


	public void setSurpCntrNum(BigDecimal surpCntrNum) {
		this.surpCntrNum = surpCntrNum;
	}


	public BigDecimal getYardCntrNum() {
		return yardCntrNum;
	}


	public void setYardCntrNum(BigDecimal yardCntrNum) {
		this.yardCntrNum = yardCntrNum;
	}


	public BigDecimal getWorkCntrNum() {
		return workCntrNum;
	}


	public void setWorkCntrNum(BigDecimal workCntrNum) {
		this.workCntrNum = workCntrNum;
	}


	public BigDecimal getPlanCarNum() {
		return planCarNum;
	}


	public void setPlanCarNum(BigDecimal planCarNum) {
		this.planCarNum = planCarNum;
	}


	public BigDecimal getSurpCarNum() {
		return surpCarNum;
	}


	public void setSurpCarNum(BigDecimal surpCarNum) {
		this.surpCarNum = surpCarNum;
	}


	public BigDecimal getYardCarNum() {
		return yardCarNum;
	}


	public void setYardCarNum(BigDecimal yardCarNum) {
		this.yardCarNum = yardCarNum;
	}


	public BigDecimal getWorkCarNum() {
		return workCarNum;
	}


	public void setWorkCarNum(BigDecimal workCarNum) {
		this.workCarNum = workCarNum;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getSubmitFlag() {
		return submitFlag;
	}


	public void setSubmitFlag(String submitFlag) {
		this.submitFlag = submitFlag;
	}


	public String getSubmitName() {
		return submitName;
	}


	public void setSubmitName(String submitName) {
		this.submitName = submitName;
	}


	public String getSubmitTime() {
		return submitTime;
	}


	public void setSubmitTime(String submitTime) {
		this.submitTime = submitTime;
	}


	public String getCheckFlag() {
		return checkFlag;
	}


	public void setCheckFlag(String checkFlag) {
		this.checkFlag = checkFlag;
	}


	public String getCheckName() {
		return checkName;
	}


	public void setCheckName(String checkName) {
		this.checkName = checkName;
	}


	public Timestamp getCheckTime() {
		return checkTime;
	}


	public void setCheckTime(Timestamp checkTime) {
		this.checkTime = checkTime;
	}


	public String getShipName() {
		return shipName;
	}


	public void setShipName(String shipName) {
		this.shipName = shipName;
	}


	public String getSvoyageId() {
		return svoyageId;
	}


	public void setSvoyageId(String svoyageId) {
		this.svoyageId = svoyageId;
	}
	


}