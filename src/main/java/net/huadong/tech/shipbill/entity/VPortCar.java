package net.huadong.tech.shipbill.entity;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;

import net.huadong.tech.base.bean.AuditEntityBean;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;


/**
 * The persistent class for the PORT_CAR database table.
 * 
 */
@Entity
@Table(name="V_PORT_CAR")

public class VPortCar extends AuditEntityBean implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final String CURRENT_STAT_1 = "0";// 离港
	public static final String CURRENT_STAT_2 = "1";// 预报
	public static final String CURRENT_STAT_3 = "2";// 在场
	public static final String CURRENT_STAT_4 = "3";// 卸船
	public static final String CURRENT_STAT_5 = "4";// 装船
	public static final String CURRENT_STAT_6 = "5";// 在船
	@Id
	@SequenceGenerator(name = "SEQ_PORT_CAR_NO", sequenceName = "SEQ_PORT_CAR_NO",allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "SEQ_PORT_CAR_NO")
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

	@Column(name="DISC_SHIP_TIM")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm") 
	private Timestamp discShipTim;

	@Column(name="DOCK_COD")
	private String dockCod;

	@Column(name="EXIT_CUSTOM_ID")
	private String exitCustomId;

	private BigDecimal height;

	@Column(name="I_E_ID")
	private String iEId;

	@Column(name="IN_CY_TIM")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm") 
	private Timestamp inCyTim;

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

	@Column(name="LOAD_SHIP_TIM")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm") 
	private Timestamp loadShipTim;

	@Column(name="LOCK_ID")
	private String lockId;

	private String marks;

	@Column(name="OUT_CY_TIM")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm") 
	private Timestamp outCyTim;

	@Column(name="OUT_PORT_NO")
	private String outPortNo;

	@Column(name="OUT_TOOL_NO")
	private String outToolNo;

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

	@Column(name="TO_PORT_TIM")
	private Date toPortTim;

	@Column(name="TRADE_ID")
	private String tradeId;

	@Column(name="TRAN_PORT_COD")
	private String tranPortCod;

	@Column(name="TRANS_ID")
	private String transId;

	@Column(name="VIN_NO")
	private String vinNo;

	@Column(name="\"VOLUMES\"")
	private BigDecimal volumes;

	private BigDecimal weights;

	private BigDecimal width;

	@Column(name="WIDTH_OVER_ID")
	private String widthOverId;
	
	@Column(name="CONTACT_INFO")
	private String contactInfo;
	
	@Column(name="CAR_LEVEL")
	private String carLevel;
	
	@Column(name="TRANS_CORP")
	private String transCorp;
	
	@Column(name="IS_TI_COMPLETE")
	private String isTiComplete;
	
	@Column(name="C_SHIP_NAM")
	private String cShipNam;

	@Column(name="VOYAGE")
	private String voyage;

	@Column(name="BRAND_NAM")
	private String brandNam;
	
	@Column(name="IN_PORT_NAM")
	private String inPortNam;
	@Column(name="OT_PORT_NAM")
	private String otPortNam;
	
	
	@Column(name="CURRENT_STAT_NAM")
	private String currentStatNam;
	
	@Column(name="CAR_TYP_NAM")
	private String carTypNam;
	

	

	
	public String getBrandNam() {
		return brandNam;
	}


	public void setBrandNam(String brandNam) {
		this.brandNam = brandNam;
	}


	public String getInPortNam() {
		return inPortNam;
	}


	public void setInPortNam(String inPortNam) {
		this.inPortNam = inPortNam;
	}


	public String getOtPortNam() {
		return otPortNam;
	}


	public void setOtPortNam(String otPortNam) {
		this.otPortNam = otPortNam;
	}


	public String getCurrentStatNam() {
		return currentStatNam;
	}


	public void setCurrentStatNam(String currentStatNam) {
		this.currentStatNam = currentStatNam;
	}


	public Timestamp getOutCyTim() {
		return outCyTim;
	}


	public void setOutCyTim(Timestamp outCyTim) {
		this.outCyTim = outCyTim;
	}





	public void setLoadShipTim(Timestamp loadShipTim) {
		this.loadShipTim = loadShipTim;
	}


	public String getCarLevel() {
		return carLevel;
	}


	public void setCarLevel(String carLevel) {
		this.carLevel = carLevel;
	}



	public String getIsTiComplete() {
		return isTiComplete;
	}


	public void setIsTiComplete(String isTiComplete) {
		this.isTiComplete = isTiComplete;
	}



	public BigDecimal getPortCarNo() {
		return this.portCarNo;
	}

	public void setPortCarNo(BigDecimal portCarNo) {
		this.portCarNo = portCarNo;
	}

	public String getCarTypNam() {
		return carTypNam;
	}

	public void setCarTypNam(String carTypNam) {
		this.carTypNam = carTypNam;
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


	public Timestamp getDiscShipTim() {
		return discShipTim;
	}

	public void setDiscShipTim(Timestamp discShipTim) {
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

	public Timestamp getInCyTim() {
		return inCyTim;
	}

	public void setInCyTim(Timestamp inCyTim) {
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


	public Timestamp getLoadShipTim() {
		return loadShipTim;
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
	
	public String getcShipNam() {
		return cShipNam;
	}

	public void setcShipNam(String cShipNam) {
		this.cShipNam = cShipNam;
	}

	public String getVoyage() {
		return voyage;
	}

	public void setVoyage(String voyage) {
		this.voyage = voyage;
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

	
	public String getContactInfo() {
		return contactInfo;
	}

	public void setContactInfo(String contactInfo) {
		this.contactInfo = contactInfo;
	}


	public String getTransCorp() {
		return transCorp;
	}


	public void setTransCorp(String transCorp) {
		this.transCorp = transCorp;
	}


}