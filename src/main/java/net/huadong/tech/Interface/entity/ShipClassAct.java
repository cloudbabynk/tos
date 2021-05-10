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
@Table(name="SHIP_CLASS_ACT")
@NamedQuery(name="ShipClassAct.findAll", query="SELECT c FROM ShipClassAct c")
public class ShipClassAct extends AuditEntityBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="SCACT_ID")
	private String scactId;
	
	@Column(name="SCPLAN_ID")
	private String scplanId;
	
	@Column(name="SHIP_NO")
	private String shipNo;
	
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
	
	@Column(name="FACT_CNTR_NUM")
	private BigDecimal factCntrNum;
	
	@Column(name="FACT_TEU_NUM")
	private BigDecimal factTeuNum;
	
	@Column(name="FACT_CAR_NUM")
	private BigDecimal factCarNum;
	
	@Column(name="FACT_STD_CAR_NUM")
	private BigDecimal factStdCarNum;
	
	@Column(name="FACT_WGT1")
	private BigDecimal factWgt1;
	
	@Column(name="FACT_WGT2")
	private BigDecimal factWgt2;
	
	@Column(name="FACT_WGT3")
	private BigDecimal factWgt3;
	
	@Column(name="FACT_WGT4")
	private BigDecimal factWgt4;
	
	@Column(name="FACT_WGT5")
	private BigDecimal factWgt5;

	@Column(name="FACT_WGT6")
	private BigDecimal factWgt6;

	@Column(name="FACT_WGT7")
	private BigDecimal factWgt7;

	@Column(name="FACT_WGT8")
	private BigDecimal factWgt8;
	
	@Column(name="FACT_WGT9")
	private BigDecimal factWgt9;
	
	@Column(name="FACT_WGT10")
	private BigDecimal factWgt10;
	
	@Column(name="FACT_WGT99")
	private BigDecimal factWgt99;
	
	@Column(name="CAUSE_INFO")
	private String causeInfo;
	
	
	@Column(name="CLASS_FLAG")
	private String classFlag;
	
	
	@Column(name="SHIP_NAME")
	private String shipName;
	
	@Column(name="SVOYAGE_ID")
	private String svoyageId;
	
	@Column(name="PLAN_CAR_NUMS")
	private BigDecimal planCarNums;

	@Column(name="PLAN_CAR_WEIGTS")
	private BigDecimal planCarWeigts;
	
	@Column(name="CAUSE_INFO_NAM")
	private String causeInfoNam;
	


	public String getCauseInfoNam() {
		return causeInfoNam;
	}


	public void setCauseInfoNam(String causeInfoNam) {
		this.causeInfoNam = causeInfoNam;
	}


	public ShipClassAct() {
	}


	public String getShipNo() {
		return shipNo;
	}


	public void setShipNo(String shipNo) {
		this.shipNo = shipNo;
	}


	public String getScactId() {
		return scactId;
	}


	public void setScactId(String scactId) {
		this.scactId = scactId;
	}


	public String getScplanId() {
		return scplanId;
	}


	public void setScplanId(String scplanId) {
		this.scplanId = scplanId;
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


	public BigDecimal getFactCntrNum() {
		return factCntrNum;
	}


	public void setFactCntrNum(BigDecimal factCntrNum) {
		this.factCntrNum = factCntrNum;
	}


	public BigDecimal getFactTeuNum() {
		return factTeuNum;
	}


	public void setFactTeuNum(BigDecimal factTeuNum) {
		this.factTeuNum = factTeuNum;
	}


	public BigDecimal getFactCarNum() {
		return factCarNum;
	}


	public void setFactCarNum(BigDecimal factCarNum) {
		this.factCarNum = factCarNum;
	}


	public BigDecimal getFactStdCarNum() {
		return factStdCarNum;
	}


	public void setFactStdCarNum(BigDecimal factStdCarNum) {
		this.factStdCarNum = factStdCarNum;
	}


	public BigDecimal getFactWgt1() {
		return factWgt1;
	}


	public void setFactWgt1(BigDecimal factWgt1) {
		this.factWgt1 = factWgt1;
	}


	public BigDecimal getFactWgt2() {
		return factWgt2;
	}


	public void setFactWgt2(BigDecimal factWgt2) {
		this.factWgt2 = factWgt2;
	}


	public BigDecimal getFactWgt3() {
		return factWgt3;
	}


	public void setFactWgt3(BigDecimal factWgt3) {
		this.factWgt3 = factWgt3;
	}


	public BigDecimal getFactWgt4() {
		return factWgt4;
	}


	public void setFactWgt4(BigDecimal factWgt4) {
		this.factWgt4 = factWgt4;
	}


	public BigDecimal getFactWgt5() {
		return factWgt5;
	}


	public void setFactWgt5(BigDecimal factWgt5) {
		this.factWgt5 = factWgt5;
	}


	public BigDecimal getFactWgt6() {
		return factWgt6;
	}


	public void setFactWgt6(BigDecimal factWgt6) {
		this.factWgt6 = factWgt6;
	}


	public BigDecimal getFactWgt7() {
		return factWgt7;
	}


	public void setFactWgt7(BigDecimal factWgt7) {
		this.factWgt7 = factWgt7;
	}


	public BigDecimal getFactWgt8() {
		return factWgt8;
	}


	public void setFactWgt8(BigDecimal factWgt8) {
		this.factWgt8 = factWgt8;
	}


	public BigDecimal getFactWgt9() {
		return factWgt9;
	}


	public void setFactWgt9(BigDecimal factWgt9) {
		this.factWgt9 = factWgt9;
	}


	public BigDecimal getFactWgt10() {
		return factWgt10;
	}


	public void setFactWgt10(BigDecimal factWgt10) {
		this.factWgt10 = factWgt10;
	}


	public BigDecimal getFactWgt99() {
		return factWgt99;
	}


	public void setFactWgt99(BigDecimal factWgt99) {
		this.factWgt99 = factWgt99;
	}





	public String getCauseInfo() {
		return causeInfo;
	}


	public void setCauseInfo(String causeInfo) {
		this.causeInfo = causeInfo;
	}


	public String getClassFlag() {
		return classFlag;
	}


	public void setClassFlag(String classFlag) {
		this.classFlag = classFlag;
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


	public BigDecimal getPlanCarNums() {
		return planCarNums;
	}


	public void setPlanCarNums(BigDecimal planCarNums) {
		this.planCarNums = planCarNums;
	}


	public BigDecimal getPlanCarWeigts() {
		return planCarWeigts;
	}


	public void setPlanCarWeigts(BigDecimal planCarWeigts) {
		this.planCarWeigts = planCarWeigts;
	}
	
	
//	
}