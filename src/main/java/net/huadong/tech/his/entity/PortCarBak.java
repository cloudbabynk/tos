package net.huadong.tech.his.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the PORT_CAR_BAK database table.
 * 
 */
@Entity
@Table(name="PORT_CAR_BAK")
@NamedQuery(name="PortCarBak.findAll", query="SELECT p FROM PortCarBak p")
public class PortCarBak implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="PORT_CAR_NO")
	private BigDecimal portCarNo;

	@Column(name="BILL_NO")
	private String billNo;

	@Column(name="BRAND_COD")
	private String brandCod;

	@Column(name="CAR_KIND")
	private String carKind;

	@Column(name="CAR_TYP")
	private String carTyp;

	@Column(name="COLOR_COD")
	private String colorCod;

	@Column(name="CONSIGN_COD")
	private String consignCod;

	@Column(name="CONSIGN_NAM")
	private String consignNam;

	@Column(name="CONTRACT_NO")
	private String contractNo;

	@Column(name="CURRENT_STAT")
	private String currentStat;

	@Column(name="CUSTOM_BILL_NO")
	private String customBillNo;

	@Column(name="CUSTOM_ID")
	private String customId;

	@Column(name="CY_AREA_NO")
	private String cyAreaNo;

	@Column(name="CY_BAY_NO")
	private String cyBayNo;

	@Column(name="CY_PLAC")
	private String cyPlac;

	@Column(name="CY_ROW_NO")
	private String cyRowNo;

	@Column(name="DAM_AREA")
	private String damArea;

	@Column(name="DAM_COD")
	private String damCod;

	@Column(name="DAM_ID")
	private String damId;

	@Column(name="DAM_LEVEL")
	private String damLevel;

	@Column(name="DISC_PORT_COD")
	private String discPortCod;

	@Temporal(TemporalType.DATE)
	@Column(name="DISC_SHIP_TIM")
	private Date discShipTim;

	@Column(name="DOCK_COD")
	private String dockCod;

	@Column(name="EXIT_CUSTOM_ID")
	private String exitCustomId;

	private BigDecimal height;

	@Column(name="I_E_ID")
	private String iEId;

	@Temporal(TemporalType.DATE)
	@Column(name="IN_CY_TIM")
	private Date inCyTim;

	@Column(name="IN_PORT_NO")
	private String inPortNo;

	@Column(name="IN_TOOL_NO")
	private String inToolNo;

	@Column(name="INSP_OK_ID")
	private String inspOkId;

	@Column(name="INSPECTION_ID")
	private String inspectionId;

	@Temporal(TemporalType.DATE)
	@Column(name="LEAV_PORT_TIM")
	private Date leavPortTim;

	@Column(name="\"LENGTH\"")
	private BigDecimal length;

	@Column(name="LENGTH_OVER_ID")
	private String lengthOverId;

	@Column(name="LOAD_PORT_COD")
	private String loadPortCod;

	@Temporal(TemporalType.DATE)
	@Column(name="LOAD_SHIP_TIM")
	private Date loadShipTim;

	@Column(name="LOCK_ID")
	private String lockId;

	private String marks;

	@Temporal(TemporalType.DATE)
	@Column(name="OUT_CY_TIM")
	private Date outCyTim;

	@Column(name="OUT_PORT_NO")
	private String outPortNo;

	@Column(name="OUT_TOOL_NO")
	private String outToolNo;

	@Column(name="REC_NAM")
	private String recNam;

	@Temporal(TemporalType.DATE)
	@Column(name="REC_TIM")
	private Date recTim;

	@Column(name="RECEIVE_COD")
	private String receiveCod;

	@Column(name="RECEIVE_NAM")
	private String receiveNam;

	private String remarks;

	@Column(name="RFID_CARD_NO")
	private String rfidCardNo;

	@Column(name="SHIP_NO")
	private String shipNo;

	@Column(name="SPEC_ID")
	private String specId;

	@Temporal(TemporalType.DATE)
	@Column(name="TO_PORT_TIM")
	private Date toPortTim;

	@Column(name="TRADE_ID")
	private String tradeId;

	@Column(name="TRAN_PORT_COD")
	private String tranPortCod;

	@Column(name="TRANS_ID")
	private String transId;

	@Column(name="UPD_NAM")
	private String updNam;

	@Temporal(TemporalType.DATE)
	@Column(name="UPD_TIM")
	private Date updTim;

	@Column(name="VIN_NO")
	private String vinNo;

	@Column(name="\"VOLUMES\"")
	private BigDecimal volumes;

	private BigDecimal weights;

	private BigDecimal width;

	@Column(name="WIDTH_OVER_ID")
	private String widthOverId;

	public PortCarBak() {
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

	public String getColorCod() {
		return this.colorCod;
	}

	public void setColorCod(String colorCod) {
		this.colorCod = colorCod;
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

	public String getContractNo() {
		return this.contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getCurrentStat() {
		return this.currentStat;
	}

	public void setCurrentStat(String currentStat) {
		this.currentStat = currentStat;
	}

	public String getCustomBillNo() {
		return this.customBillNo;
	}

	public void setCustomBillNo(String customBillNo) {
		this.customBillNo = customBillNo;
	}

	public String getCustomId() {
		return this.customId;
	}

	public void setCustomId(String customId) {
		this.customId = customId;
	}

	public String getCyAreaNo() {
		return this.cyAreaNo;
	}

	public void setCyAreaNo(String cyAreaNo) {
		this.cyAreaNo = cyAreaNo;
	}

	public String getCyBayNo() {
		return this.cyBayNo;
	}

	public void setCyBayNo(String cyBayNo) {
		this.cyBayNo = cyBayNo;
	}

	public String getCyPlac() {
		return this.cyPlac;
	}

	public void setCyPlac(String cyPlac) {
		this.cyPlac = cyPlac;
	}

	public String getCyRowNo() {
		return this.cyRowNo;
	}

	public void setCyRowNo(String cyRowNo) {
		this.cyRowNo = cyRowNo;
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

	public String getDamId() {
		return this.damId;
	}

	public void setDamId(String damId) {
		this.damId = damId;
	}

	public String getDamLevel() {
		return this.damLevel;
	}

	public void setDamLevel(String damLevel) {
		this.damLevel = damLevel;
	}

	public String getDiscPortCod() {
		return this.discPortCod;
	}

	public void setDiscPortCod(String discPortCod) {
		this.discPortCod = discPortCod;
	}

	public Date getDiscShipTim() {
		return this.discShipTim;
	}

	public void setDiscShipTim(Date discShipTim) {
		this.discShipTim = discShipTim;
	}

	public String getDockCod() {
		return this.dockCod;
	}

	public void setDockCod(String dockCod) {
		this.dockCod = dockCod;
	}

	public String getExitCustomId() {
		return this.exitCustomId;
	}

	public void setExitCustomId(String exitCustomId) {
		this.exitCustomId = exitCustomId;
	}

	public BigDecimal getHeight() {
		return this.height;
	}

	public void setHeight(BigDecimal height) {
		this.height = height;
	}


	public String getiEId() {
		return iEId;
	}

	public void setiEId(String iEId) {
		this.iEId = iEId;
	}

	public Date getInCyTim() {
		return this.inCyTim;
	}

	public void setInCyTim(Date inCyTim) {
		this.inCyTim = inCyTim;
	}

	public String getInPortNo() {
		return this.inPortNo;
	}

	public void setInPortNo(String inPortNo) {
		this.inPortNo = inPortNo;
	}

	public String getInToolNo() {
		return this.inToolNo;
	}

	public void setInToolNo(String inToolNo) {
		this.inToolNo = inToolNo;
	}

	public String getInspOkId() {
		return this.inspOkId;
	}

	public void setInspOkId(String inspOkId) {
		this.inspOkId = inspOkId;
	}

	public String getInspectionId() {
		return this.inspectionId;
	}

	public void setInspectionId(String inspectionId) {
		this.inspectionId = inspectionId;
	}

	public Date getLeavPortTim() {
		return this.leavPortTim;
	}

	public void setLeavPortTim(Date leavPortTim) {
		this.leavPortTim = leavPortTim;
	}

	public BigDecimal getLength() {
		return this.length;
	}

	public void setLength(BigDecimal length) {
		this.length = length;
	}

	public String getLengthOverId() {
		return this.lengthOverId;
	}

	public void setLengthOverId(String lengthOverId) {
		this.lengthOverId = lengthOverId;
	}

	public String getLoadPortCod() {
		return this.loadPortCod;
	}

	public void setLoadPortCod(String loadPortCod) {
		this.loadPortCod = loadPortCod;
	}

	public Date getLoadShipTim() {
		return this.loadShipTim;
	}

	public void setLoadShipTim(Date loadShipTim) {
		this.loadShipTim = loadShipTim;
	}

	public String getLockId() {
		return this.lockId;
	}

	public void setLockId(String lockId) {
		this.lockId = lockId;
	}

	public String getMarks() {
		return this.marks;
	}

	public void setMarks(String marks) {
		this.marks = marks;
	}

	public Date getOutCyTim() {
		return this.outCyTim;
	}

	public void setOutCyTim(Date outCyTim) {
		this.outCyTim = outCyTim;
	}

	public String getOutPortNo() {
		return this.outPortNo;
	}

	public void setOutPortNo(String outPortNo) {
		this.outPortNo = outPortNo;
	}

	public String getOutToolNo() {
		return this.outToolNo;
	}

	public void setOutToolNo(String outToolNo) {
		this.outToolNo = outToolNo;
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

	public String getRfidCardNo() {
		return rfidCardNo;
	}

	public void setRfidCardNo(String rfidCardNo) {
		this.rfidCardNo = rfidCardNo;
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

	public Date getToPortTim() {
		return this.toPortTim;
	}

	public void setToPortTim(Date toPortTim) {
		this.toPortTim = toPortTim;
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

	public String getTransId() {
		return this.transId;
	}

	public void setTransId(String transId) {
		this.transId = transId;
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

	public BigDecimal getVolumes() {
		return this.volumes;
	}

	public void setVolumes(BigDecimal volumes) {
		this.volumes = volumes;
	}

	public BigDecimal getWeights() {
		return this.weights;
	}

	public void setWeights(BigDecimal weights) {
		this.weights = weights;
	}

	public BigDecimal getWidth() {
		return this.width;
	}

	public void setWidth(BigDecimal width) {
		this.width = width;
	}

	public String getWidthOverId() {
		return this.widthOverId;
	}

	public void setWidthOverId(String widthOverId) {
		this.widthOverId = widthOverId;
	}

}