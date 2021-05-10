package net.huadong.tech.ship.entity;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;

import net.huadong.tech.base.bean.AuditEntityBean;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the SHIP_THRUPUT database table.
 * 
 */
@Entity
@Table(name="SHIP_THRUPUT")
@NamedQuery(name="ShipThruput.findAll", query="SELECT s FROM ShipThruput s")
public class ShipThruput extends AuditEntityBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="SHIP_THRUPUT_ID")
	private String shipThruputId;
	
	@Column(name="E_PRE_KNOT")
	private String ePreKnot;

	@Column(name="E_VISA_VOLUME")
	private BigDecimal eVisaVolume;

	@Column(name="E_VISA_WEIGHT")
	private BigDecimal eVisaWeight;

	@Column(name="E_WORK_VOLUME")
	private BigDecimal eWorkVolume;

	@Column(name="E_WORK_WEIGHT")
	private BigDecimal eWorkWeight;

	@Column(name="GROUP_SHIP_NO")
	private String groupShipNo;

	@Column(name="I_PRE_KNOT")
	private String iPreKnot;

	@Column(name="I_VISA_VOLUME")
	private BigDecimal iVisaVolume;

	@Column(name="I_VISA_WEIGHT")
	private BigDecimal iVisaWeight;

	@Column(name="I_WORK_VOLUME")
	private BigDecimal iWorkVolume;

	@Column(name="I_WORK_WEIGHT")
	private BigDecimal iWorkWeight;

	private String remarks;

	@Column(name="SEND_FLAG")
	private String sendFlag;
	
	@Column(name="I_CAR_NUM")
	private String iCarNum;
	
	@Column(name="E_CAR_NUM")
	private String eCarNum;
	
	@Column(name="I_STDCAR_NUM")
	private String iStdcarNum;
	
	@Column(name="E_STDCAR_NUM")
	private String eStdcarNum;
	
	@Column(name="I_QUICK_FLAG")
	private String iQuickFlag;
	
	@Column(name="E_QUICK_FLAG")
	private String eQuickFlag;

	@Column(name="SHIP_NO")
	private String shipNo;
	
	@Column(name="IN_BEG_TIM")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm") 
	private Date inBegTim;
	
	@Column(name="IN_END_TIM")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm") 
	private Date inEndTim;
	
	@Column(name="OUT_BEG_TIM")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm") 
	private Date outBegTim;
	
	@Column(name="OUT_END_TIM")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm") 
	private Date outEndTim;

	public ShipThruput() {
	}
	public String getShipThruputId() {
		return shipThruputId;
	}

	public void setShipThruputId(String shipThruputId) {
		this.shipThruputId = shipThruputId;
	}

	public Date getInBegTim() {
		return inBegTim;
	}
	public void setInBegTim(Date inBegTim) {
		this.inBegTim = inBegTim;
	}
	public Date getInEndTim() {
		return inEndTim;
	}
	public void setInEndTim(Date inEndTim) {
		this.inEndTim = inEndTim;
	}
	public Date getOutBegTim() {
		return outBegTim;
	}
	public void setOutBegTim(Date outBegTim) {
		this.outBegTim = outBegTim;
	}
	public Date getOutEndTim() {
		return outEndTim;
	}
	public void setOutEndTim(Date outEndTim) {
		this.outEndTim = outEndTim;
	}
	public String getePreKnot() {
		return ePreKnot;
	}
	public void setePreKnot(String ePreKnot) {
		this.ePreKnot = ePreKnot;
	}
	public BigDecimal geteVisaVolume() {
		return eVisaVolume;
	}
	public void seteVisaVolume(BigDecimal eVisaVolume) {
		this.eVisaVolume = eVisaVolume;
	}
	public BigDecimal geteVisaWeight() {
		return eVisaWeight;
	}
	public void seteVisaWeight(BigDecimal eVisaWeight) {
		this.eVisaWeight = eVisaWeight;
	}
	public BigDecimal geteWorkVolume() {
		return eWorkVolume;
	}
	public void seteWorkVolume(BigDecimal eWorkVolume) {
		this.eWorkVolume = eWorkVolume;
	}
	public BigDecimal geteWorkWeight() {
		return eWorkWeight;
	}
	public void seteWorkWeight(BigDecimal eWorkWeight) {
		this.eWorkWeight = eWorkWeight;
	}
	public String getiPreKnot() {
		return iPreKnot;
	}
	public void setiPreKnot(String iPreKnot) {
		this.iPreKnot = iPreKnot;
	}
	public BigDecimal getiVisaVolume() {
		return iVisaVolume;
	}
	public void setiVisaVolume(BigDecimal iVisaVolume) {
		this.iVisaVolume = iVisaVolume;
	}
	public BigDecimal getiVisaWeight() {
		return iVisaWeight;
	}
	public void setiVisaWeight(BigDecimal iVisaWeight) {
		this.iVisaWeight = iVisaWeight;
	}
	public BigDecimal getiWorkVolume() {
		return iWorkVolume;
	}
	public void setiWorkVolume(BigDecimal iWorkVolume) {
		this.iWorkVolume = iWorkVolume;
	}
	public BigDecimal getiWorkWeight() {
		return iWorkWeight;
	}
	public void setiWorkWeight(BigDecimal iWorkWeight) {
		this.iWorkWeight = iWorkWeight;
	}

	public String getGroupShipNo() {
		return groupShipNo;
	}
	public void setGroupShipNo(String groupShipNo) {
		this.groupShipNo = groupShipNo;
	}
	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getSendFlag() {
		return this.sendFlag;
	}

	public void setSendFlag(String sendFlag) {
		this.sendFlag = sendFlag;
	}

	public String getShipNo() {
		return this.shipNo;
	}

	public void setShipNo(String shipNo) {
		this.shipNo = shipNo;
	}
	public String getiCarNum() {
		return iCarNum;
	}
	public void setiCarNum(String iCarNum) {
		this.iCarNum = iCarNum;
	}
	public String geteCarNum() {
		return eCarNum;
	}
	public void seteCarNum(String eCarNum) {
		this.eCarNum = eCarNum;
	}
	public String getiStdcarNum() {
		return iStdcarNum;
	}
	public void setiStdcarNum(String iStdcarNum) {
		this.iStdcarNum = iStdcarNum;
	}
	public String geteStdcarNum() {
		return eStdcarNum;
	}
	public void seteStdcarNum(String eStdcarNum) {
		this.eStdcarNum = eStdcarNum;
	}
	public String getiQuickFlag() {
		return iQuickFlag;
	}
	public void setiQuickFlag(String iQuickFlag) {
		this.iQuickFlag = iQuickFlag;
	}
	public String geteQuickFlag() {
		return eQuickFlag;
	}
	public void seteQuickFlag(String eQuickFlag) {
		this.eQuickFlag = eQuickFlag;
	}

}