package net.huadong.tech.shipbill.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

import net.huadong.tech.base.bean.AuditEntityBean;

/**
 * The persistent class for the SHIP_BILL database table.
 * 
 */
@Entity
@Table(name = "SHIP_BILL")
@NamedQuery(name = "ShipBill.findAll", query = "SELECT s FROM ShipBill s")
public class ShipBill extends AuditEntityBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Basic(optional = false)

	@NotNull
	@Column(name = "SHIPBILL_ID")
	private String shipbillId;

	@Column(name = "BILL_NO")
	private String billNo;

	@Column(name = "BRAND_COD")
	private String brandCod;

	@Column(name = "FORCE_ID")
	private String forceId;

	@Column(name = "CAR_NUM")
	private BigDecimal carNum;

	@Column(name = "CAR_TYP")
	private String carTyp;

	@Column(name = "CONSIGN_COD")
	private String consignCod;

	@Column(name = "CONSIGN_NAM")
	private String consignNam;

	@Column(name = "CONTACT_PERSON")
	private String contactPerson;

	@Column(name = "CONTACT_PHONE")
	private String contactPhone;

	@Column(name = "CUSTOM_BILL_NO")
	private String customBillNo;

	@Column(name = "DISC_PORT_COD")
	private String discPortCod;

	private BigDecimal fittings;

	@Column(name = "I_E_ID")
	private String iEId;

	@Column(name = "LOAD_PORT_COD")
	private String loadPortCod;

	private String marks;

	private BigDecimal pieces;

	@Column(name = "RECEIVE_COD")
	private String receiveCod;

	@Column(name = "RECEIVE_NAM")
	private String receiveNam;
	@Column(name = "REMARKS")
	private String remarks;

	@Column(name = "JQ_ID")
	private String jqId;
	@Column(name = "SEND_FLAG")
	private String sendFlag;
	
	@Column(name="RELEASE_TIM")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss") 
	private Timestamp releaseTim;
	
	@Column(name="CHECK_TIM")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss") 
	private Timestamp checkTim;
	
	@Column(name="YD_TIM")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss") 
	private Timestamp ydTim;
	
	@Column(name = "WORK_TYP")
	private String workTyp;
	
	@Column(name = "SPLIT_BILL_NO")
	private String splitBillNo;

	@Column(name = "TRADE_ID")
	private String tradeId;

	@Column(name = "TRAN_PORT_COD")
	private String tranPortCod;
	
	@Column(name = "CONFIRM_ID")
	private String confirmId;
	
	@Column(name = "DOCK_COD")
	private String dockCod;
	
	@Column(name = "EXIT_CUSTOM_ID")
	private String exitCustomId;
	@Column(name = "YD_ID")
	private String ydId;
	@Column(name = "YD_RECEIVE_ID")
	private String ydRecId;
	
	@Column(name = "REC_NAM")
	private String recNam;	
	
	public void setRecNam(String recNam) {
		this.recNam = recNam;
	}	
	
	@Column(name="REC_2TIM")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss") 
	private Timestamp Rec2Tim;
	
	public void setRec2Tim(Timestamp Rec2Tim) {
		this.Rec2Tim = Rec2Tim;
	}	
	
	private BigDecimal valumes;

	
	@Column(name = "WEIGHTS")
	private BigDecimal weights;
	
	@Transient
	private String dockNam;

	public String getJqId() {
		return jqId;
	}

	public void setJqId(String jqId) {
		this.jqId = jqId;
	}

	public String getForceId() {
		return forceId;
	}

	public void setForceId(String forceId) {
		this.forceId = forceId;
	}

	public String getExitCustomId() {
		return exitCustomId;
	}

	public void setExitCustomId(String exitCustomId) {
		this.exitCustomId = exitCustomId;
	}

	public String getSendFlag() {
		return sendFlag;
	}

	public Timestamp getYdTim() {
		return ydTim;
	}

	public void setYdTim(Timestamp ydTim) {
		this.ydTim = ydTim;
	}

	public String getYdRecId() {
		return ydRecId;
	}

	public void setYdRecId(String ydRecId) {
		this.ydRecId = ydRecId;
	}

	public String getYdId() {
		return ydId;
	}

	public void setYdId(String ydId) {
		this.ydId = ydId;
	}

	public void setSendFlag(String sendFlag) {
		this.sendFlag = sendFlag;
	}

	public Timestamp getReleaseTim() {
		return releaseTim;
	}

	public void setReleaseTim(Timestamp releaseTim) {
		this.releaseTim = releaseTim;
	}

	public String getWorkTyp() {
		return workTyp;
	}

	public Timestamp getCheckTim() {
		return checkTim;
	}

	public void setCheckTim(Timestamp checkTim) {
		this.checkTim = checkTim;
	}

	public void setWorkTyp(String workTyp) {
		this.workTyp = workTyp;
	}

	@Column(name = "SHIP_NO")
	private String shipNo;
	
	@Column(name = "CARGO_NAM")
	private String cargoNam;
	
	@Transient
	private String cyAreaNo;

	@Transient
	public String cShipNam;

	@Transient
	public String voyage;

	@Transient
	private String cyArea;

	@Transient
	private String carKind;

	@Transient
	private String brandNam;
	@Transient
	private String loadPortNam;
	@Transient
	private String discPortNam;
	@Transient
	private String tranPortNam;
	@Transient
	private String carTypNam;
	public String getCarTypNam() {
		return carTypNam;
	}

	public void setCarTypNam(String carTypNam) {
		this.carTypNam = carTypNam;
	}

	public String getLoadPortNam() {
		return loadPortNam;
	}

	public void setLoadPortNam(String loadPortNam) {
		this.loadPortNam = loadPortNam;
	}

	public String getDiscPortNam() {
		return discPortNam;
	}

	public void setDiscPortNam(String discPortNam) {
		this.discPortNam = discPortNam;
	}

	public String getTranPortNam() {
		return tranPortNam;
	}

	public void setTranPortNam(String tranPortNam) {
		this.tranPortNam = tranPortNam;
	}

	public String getBrandNam() {
		return brandNam;
	}

	public void setBrandNam(String brandNam) {
		this.brandNam = brandNam;
	}
	


	public String getVoyage() {
		return voyage;
	}

	public void setVoyage(String voyage) {
		this.voyage = voyage;
	}

	public String getcShipNam() {
		return cShipNam;
	}

	public void setcShipNam(String cShipNam) {
		this.cShipNam = cShipNam;
	}

	public String getCyArea() {
		return cyArea;
	}

	public void setCyArea(String cyArea) {
		this.cyArea = cyArea;
	}

	public String getCarKind() {
		return carKind;
	}

	public void setCarKind(String carKind) {
		this.carKind = carKind;
	}

	public String getCyAreaNo() {
		return cyAreaNo;
	}

	public void setCyAreaNo(String cyAreaNo) {
		this.cyAreaNo = cyAreaNo;
	}

	public ShipBill() {
	}

	public String getShipbillId() {
		return this.shipbillId;
	}

	public void setShipbillId(String shipbillId) {
		this.shipbillId = shipbillId;
	}

	public String getBillNo() {
		return this.billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public String getBrandCod() {
		return this.brandCod;
	}

	public void setBrandCod(String brandCod) {
		this.brandCod = brandCod;
	}

	public BigDecimal getCarNum() {
		return this.carNum;
	}

	public void setCarNum(BigDecimal carNum) {
		this.carNum = carNum;
	}

	public String getCarTyp() {
		return this.carTyp;
	}

	public void setCarTyp(String carTyp) {
		this.carTyp = carTyp;
	}

	public String getConsignCod() {
		return this.consignCod;
	}

	public void setConsignCod(String consignCod) {
		this.consignCod = consignCod;
	}

	public String getConsignNam() {
		return this.consignNam;
	}

	public void setConsignNam(String consignNam) {
		this.consignNam = consignNam;
	}

	public String getContactPerson() {
		return this.contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	public String getContactPhone() {
		return this.contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getCustomBillNo() {
		return this.customBillNo;
	}

	public void setCustomBillNo(String customBillNo) {
		this.customBillNo = customBillNo;
	}

	public String getDiscPortCod() {
		return this.discPortCod;
	}

	public void setDiscPortCod(String discPortCod) {
		this.discPortCod = discPortCod;
	}

	public BigDecimal getFittings() {
		return this.fittings;
	}

	public void setFittings(BigDecimal fittings) {
		this.fittings = fittings;
	}

	public String getiEId() {
		return iEId;
	}

	public void setiEId(String iEId) {
		this.iEId = iEId;
	}

	/*
	 * public String getIEId() { return this.iEId; }
	 * 
	 * public void setIEId(String iEId) { this.iEId = iEId; }
	 */

	public String getLoadPortCod() {
		return this.loadPortCod;
	}

	public void setLoadPortCod(String loadPortCod) {
		this.loadPortCod = loadPortCod;
	}

	public String getMarks() {
		return this.marks;
	}

	public void setMarks(String marks) {
		this.marks = marks;
	}

	public BigDecimal getPieces() {
		return this.pieces;
	}

	public void setPieces(BigDecimal pieces) {
		this.pieces = pieces;
	}

	public String getReceiveCod() {
		return this.receiveCod;
	}

	public void setReceiveCod(String receiveCod) {
		this.receiveCod = receiveCod;
	}

	public String getReceiveNam() {
		return this.receiveNam;
	}

	public void setReceiveNam(String receiveNam) {
		this.receiveNam = receiveNam;
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

	public String getSplitBillNo() {
		return this.splitBillNo;
	}

	public void setSplitBillNo(String splitBillNo) {
		this.splitBillNo = splitBillNo;
	}

	public String getTradeId() {
		return this.tradeId;
	}

	public void setTradeId(String tradeId) {
		this.tradeId = tradeId;
	}

	public String getTranPortCod() {
		return this.tranPortCod;
	}

	public void setTranPortCod(String tranPortCod) {
		this.tranPortCod = tranPortCod;
	}

	public BigDecimal getValumes() {
		return this.valumes;
	}

	public void setValumes(BigDecimal valumes) {
		this.valumes = valumes;
	}

	public BigDecimal getWeights() {
		return this.weights;
	}

	public void setWeights(BigDecimal weights) {
		this.weights = weights;
	}
	public String getCargoNam() {
		return cargoNam;
	}

	public void setCargoNam(String cargoNam) {
		this.cargoNam = cargoNam;
	}

	public String getConfirmId() {
		return confirmId;
	}

	public void setConfirmId(String confirmId) {
		this.confirmId = confirmId;
	}

	public String getDockCod() {
		return dockCod;
	}

	public void setDockCod(String dockCod) {
		this.dockCod = dockCod;
	}

	public String getDockNam() {
		return dockNam;
	}

	public void setDockNam(String dockNam) {
		this.dockNam = dockNam;
	}
	
	
	
}