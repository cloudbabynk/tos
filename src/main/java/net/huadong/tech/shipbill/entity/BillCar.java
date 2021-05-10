package net.huadong.tech.shipbill.entity;

import java.io.Serializable;
import javax.persistence.*;

import net.huadong.tech.base.bean.AuditEntityBean;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the BILL_CAR database table.
 * 
 */
@Entity
@Table(name="BILL_CAR")
@NamedQuery(name="BillCar.findAll", query="SELECT b FROM BillCar b")
public class BillCar extends AuditEntityBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="BILLCAR_ID")
	private String billcarId;

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

	@Column(name="CONFIRM_ID")
	private String confirmId;

	private BigDecimal height;

	@Column(name="I_E_ID")
	private String iEId;


	@Column(name="\"LENGTH\"")
	private BigDecimal length;

	@Column(name="LENGTH_OVER_ID")
	private String lengthOverId;

	private String marks;

	@Column(name="MISS_ID")
	private String missId;

	@Column(name="OVER_ID")
	private String overId;

	@SequenceGenerator(name = "generator", sequenceName = "SEQ_PORT_CAR_NO",allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "generator")
	@Column(name="PORT_CAR_NO")
	private BigDecimal portCarNo;
	
	@Column(name="REMARKS")
	private String remarks;

	@Column(name="RFID_CARD_NO")
	private String rfidCardNo;

	@Column(name="SHIP_NO")
	private String shipNo;

	@Column(name="SHIPBILL_ID")
	private String shipbillId;

	@Column(name="TRADE_ID")
	private String tradeId;

	@Column(name="VIN_NO")
	private String vinNo;

	@Column(name="\"VOLUMES\"")
	private BigDecimal volumes;

	private BigDecimal weights;

	private BigDecimal width;

	@Column(name="WIDTH_OVER_ID")
	private String widthOverId;
	
	@Column(name="LH_FLAG")
	private String lhFlag;
	
	@Transient
	private String carTypNam;
	
	@Transient
	private String carKindNam;
	
	@Transient
	private String brandNam;
	
	@Transient
	private String receiveNam;
	
	@Transient
	private BigDecimal planNum;
	
	@Transient
	private String dockCod;
	
	@Transient
	private BigDecimal unTallyNum;

	@Transient
	private BigDecimal tallyNum;

	public BigDecimal getTallyNum() {
		return tallyNum;
	}

	public void setTallyNum(BigDecimal tallyNum) {
		this.tallyNum = tallyNum;
	}


	
	public BigDecimal getUnTallyNum() {
		return unTallyNum;
	}
	public void setUnTallyNum(BigDecimal unTallyNum) {
		this.unTallyNum = unTallyNum;
	}
	public String getReceiveNam() {
		return receiveNam;
	}
	public void setReceiveNam(String receiveNam) {
		this.receiveNam = receiveNam;
	}
	public BigDecimal getPlanNum() {
		return planNum;
	}
	public void setPlanNum(BigDecimal planNum) {
		this.planNum = planNum;
	}
	public String getBrandNam() {
		return brandNam;
	}
	public void setBrandNam(String brandNam) {
		this.brandNam = brandNam;
	}
	public String getCarTypNam() {
		return carTypNam;
	}
	public void setCarTypNam(String carTypNam) {
		this.carTypNam = carTypNam;
	}
	public String getCarKindNam() {
		return carKindNam;
	}
	public void setCarKindNam(String carKindNam) {
		this.carKindNam = carKindNam;
	}
	public BillCar() {
	}
	public String getiEId() {
		return iEId;
	}

	public void setiEId(String iEId) {
		this.iEId = iEId;
	}

	public String getBillcarId() {
		return this.billcarId;
	}

	public void setBillcarId(String billcarId) {
		this.billcarId = billcarId;
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

	public String getConfirmId() {
		return this.confirmId;
	}

	public void setConfirmId(String confirmId) {
		this.confirmId = confirmId;
	}

	public BigDecimal getHeight() {
		return this.height;
	}

	public void setHeight(BigDecimal height) {
		this.height = height;
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

	public String getMarks() {
		return this.marks;
	}

	public void setMarks(String marks) {
		this.marks = marks;
	}

	public String getMissId() {
		return this.missId;
	}

	public void setMissId(String missId) {
		this.missId = missId;
	}

	public String getOverId() {
		return this.overId;
	}

	public void setOverId(String overId) {
		this.overId = overId;
	}

	public BigDecimal getPortCarNo() {
		return this.portCarNo;
	}

	public void setPortCarNo(BigDecimal portCarNo) {
		this.portCarNo = portCarNo;
	}

	
	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getRfidCardNo() {
		return this.rfidCardNo;
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

	public String getShipbillId() {
		return this.shipbillId;
	}

	public void setShipbillId(String shipbillId) {
		this.shipbillId = shipbillId;
	}

	public String getTradeId() {
		return this.tradeId;
	}

	public void setTradeId(String tradeId) {
		this.tradeId = tradeId;
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
	public String getLhFlag() {
		return lhFlag;
	}
	public void setLhFlag(String lhFlag) {
		this.lhFlag = lhFlag;
	}
	public String getDockCod() {
		return dockCod;
	}
	public void setDockCod(String dockCod) {
		this.dockCod = dockCod;
	}
	
	

}