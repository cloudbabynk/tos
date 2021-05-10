package net.huadong.tech.his.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the TRUCK_WORK_HIS database table.
 * 
 */
@Entity
@Table(name="TRUCK_WORK_HIS")
@IdClass(TruckWorkHiPK.class)
@NamedQuery(name="TruckWorkHis.findAll", query="SELECT t FROM TruckWorkHis t")
public class TruckWorkHis implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CONTRACT_NO")
	private String contractNo;

	@Id
	@Column(name="PORT_CAR_NO")
	private BigDecimal portCarNo;
	
	@Column(name="INGATE_ID")
	private String ingateId;

	@Column(name="BILL_NO")
	private String billNo;

	@Column(name="BRAND_COD")
	private String brandCod;

	@Column(name="CAR_KIND")
	private String carKind;

	@Column(name="CAR_TYP")
	private String carTyp;

	@Column(name="CARRY_ID")
	private String carryId;

	@Column(name="CARRY_WAY")
	private String carryWay;

	@Column(name="CONSIGN_COD")
	private String consignCod;

	@Column(name="CRG_AGENT")
	private String crgAgent;

	@Column(name="CY_PLAC")
	private String cyPlac;

	@Column(name="DAM_AREA")
	private String damArea;

	@Column(name="DAM_COD")
	private String damCod;

	@Column(name="DIRECT_ID")
	private String directId;

	@Column(name="DISC_PORT_COD")
	private String discPortCod;

	@Column(name="I_E_ID")
	private String iEId;

	@Temporal(TemporalType.DATE)
	@Column(name="IN_GAT_TIM")
	private Date inGatTim;

	@Column(name="IN_GATE_NO")
	private String inGateNo;

	@Column(name="IN_REC_NAM")
	private String inRecNam;

	private String marks;

	@Column(name="NEW_VIN_NO")
	private String newVinNo;

	@Column(name="OUT_GAT_NO")
	private String outGatNo;

	@Temporal(TemporalType.DATE)
	@Column(name="OUT_GAT_TIM")
	private Date outGatTim;

	@Column(name="OUT_REC_NAM")
	private String outRecNam;

	@Column(name="REMARK_TXT")
	private String remarkTxt;

	@Column(name="RFID_CARD_NO")
	private String rfidCardNo;

	@Column(name="SHIP_COD")
	private String shipCod;

	@Column(name="SHIP_NO")
	private String shipNo;

	@Column(name="SPEC_ID")
	private String specId;

	@Column(name="TRADE_ID")
	private String tradeId;

	@Column(name="TRAN_PORT_COD")
	private String tranPortCod;

	@Column(name="TRUCK_NO")
	private String truckNo;

	@Column(name="UPD_NAM")
	private String updNam;

	@Temporal(TemporalType.DATE)
	@Column(name="UPD_TIM")
	private Date updTim;

	@Column(name="VIN_NO")
	private String vinNo;

	private String voyage;

	@Column(name="WORK_NAM")
	private String workNam;

	@Temporal(TemporalType.DATE)
	@Column(name="WORK_TIM")
	private Date workTim;

	//bi-directional many-to-one association to GateTruckHi
	@ManyToOne
	@JoinColumn(name="INGATE_ID", insertable=false, updatable=false)
	private GateTruckHis gateTruckHi;

	public TruckWorkHis() {
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public BigDecimal getPortCarNo() {
		return portCarNo;
	}

	public void setPortCarNo(BigDecimal portCarNo) {
		this.portCarNo = portCarNo;
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

	public String getCarKind() {
		return this.carKind;
	}

	public void setCarKind(String carKind) {
		this.carKind = carKind;
	}

	public String getCarTyp() {
		return this.carTyp;
	}

	public void setCarTyp(String carTyp) {
		this.carTyp = carTyp;
	}

	public String getCarryId() {
		return this.carryId;
	}

	public void setCarryId(String carryId) {
		this.carryId = carryId;
	}

	public String getCarryWay() {
		return this.carryWay;
	}

	public void setCarryWay(String carryWay) {
		this.carryWay = carryWay;
	}

	public String getConsignCod() {
		return this.consignCod;
	}

	public void setConsignCod(String consignCod) {
		this.consignCod = consignCod;
	}

	public String getCrgAgent() {
		return this.crgAgent;
	}

	public void setCrgAgent(String crgAgent) {
		this.crgAgent = crgAgent;
	}

	public String getCyPlac() {
		return this.cyPlac;
	}

	public void setCyPlac(String cyPlac) {
		this.cyPlac = cyPlac;
	}

	public String getDamArea() {
		return this.damArea;
	}

	public void setDamArea(String damArea) {
		this.damArea = damArea;
	}

	public String getDamCod() {
		return this.damCod;
	}

	public void setDamCod(String damCod) {
		this.damCod = damCod;
	}

	public String getDirectId() {
		return this.directId;
	}

	public void setDirectId(String directId) {
		this.directId = directId;
	}

	public String getDiscPortCod() {
		return this.discPortCod;
	}

	public void setDiscPortCod(String discPortCod) {
		this.discPortCod = discPortCod;
	}

	public String getIEId() {
		return this.iEId;
	}

	public void setIEId(String iEId) {
		this.iEId = iEId;
	}

	public Date getInGatTim() {
		return this.inGatTim;
	}

	public void setInGatTim(Date inGatTim) {
		this.inGatTim = inGatTim;
	}

	public String getInGateNo() {
		return this.inGateNo;
	}

	public void setInGateNo(String inGateNo) {
		this.inGateNo = inGateNo;
	}

	public String getInRecNam() {
		return this.inRecNam;
	}

	public void setInRecNam(String inRecNam) {
		this.inRecNam = inRecNam;
	}

	public String getMarks() {
		return this.marks;
	}

	public void setMarks(String marks) {
		this.marks = marks;
	}

	public String getNewVinNo() {
		return this.newVinNo;
	}

	public void setNewVinNo(String newVinNo) {
		this.newVinNo = newVinNo;
	}

	public String getOutGatNo() {
		return this.outGatNo;
	}

	public void setOutGatNo(String outGatNo) {
		this.outGatNo = outGatNo;
	}

	public Date getOutGatTim() {
		return this.outGatTim;
	}

	public void setOutGatTim(Date outGatTim) {
		this.outGatTim = outGatTim;
	}

	public String getOutRecNam() {
		return this.outRecNam;
	}

	public void setOutRecNam(String outRecNam) {
		this.outRecNam = outRecNam;
	}

	public String getRemarkTxt() {
		return this.remarkTxt;
	}

	public void setRemarkTxt(String remarkTxt) {
		this.remarkTxt = remarkTxt;
	}

	public String getRfidCardNo() {
		return this.rfidCardNo;
	}

	public void setRfidCardNo(String rfidCardNo) {
		this.rfidCardNo = rfidCardNo;
	}

	public String getShipCod() {
		return this.shipCod;
	}

	public void setShipCod(String shipCod) {
		this.shipCod = shipCod;
	}

	public String getShipNo() {
		return this.shipNo;
	}

	public void setShipNo(String shipNo) {
		this.shipNo = shipNo;
	}

	public String getSpecId() {
		return this.specId;
	}

	public void setSpecId(String specId) {
		this.specId = specId;
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

	public String getTruckNo() {
		return this.truckNo;
	}

	public void setTruckNo(String truckNo) {
		this.truckNo = truckNo;
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

	public String getVinNo() {
		return this.vinNo;
	}

	public void setVinNo(String vinNo) {
		this.vinNo = vinNo;
	}

	public String getVoyage() {
		return this.voyage;
	}

	public void setVoyage(String voyage) {
		this.voyage = voyage;
	}

	public String getWorkNam() {
		return this.workNam;
	}

	public void setWorkNam(String workNam) {
		this.workNam = workNam;
	}

	public Date getWorkTim() {
		return this.workTim;
	}

	public void setWorkTim(Date workTim) {
		this.workTim = workTim;
	}

	public GateTruckHis getGateTruckHi() {
		return this.gateTruckHi;
	}

	public void setGateTruckHi(GateTruckHis gateTruckHi) {
		this.gateTruckHi = gateTruckHi;
	}
	
	public String getIngateId() {
		return ingateId;
	}


	public void setIngateId(String ingateId) {
		this.ingateId = ingateId;
	}

}