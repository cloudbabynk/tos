package net.huadong.tech.shipbill.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the BILL_CAR_HIS database table.
 * 
 */
@Entity
@Table(name="BILL_CAR_HIS")
@NamedQuery(name="BillCarHi.findAll", query="SELECT b FROM BillCarHi b")
public class BillCarHi implements Serializable {
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

	@Column(name="PORT_CAR_NO")
	private BigDecimal portCarNo;

	@Column(name="REC_NAM")
	private String recNam;

	@Temporal(TemporalType.DATE)
	@Column(name="REC_TIM")
	private Date recTim;

	private String remarks;

	@Column(name="RFID_CARD_NO")
	private String rfidCardNo;

	@Column(name="SHIP_NO")
	private String shipNo;

	@Column(name="SHIPBILL_ID")
	private String shipbillId;

	@Column(name="TRADE_ID")
	private String tradeId;

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

	public BillCarHi() {
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

	public String getIEId() {
		return this.iEId;
	}

	public void setIEId(String iEId) {
		this.iEId = iEId;
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