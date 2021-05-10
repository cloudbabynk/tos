package net.huadong.tech.custom.entity;

import java.io.Serializable;
import javax.persistence.*;

import net.huadong.tech.base.bean.AuditEntityBean;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the CUSTOM_RELEASE database table.
 * 
 */
@Entity
@Table(name="CUSTOM_RELEASE")
@NamedQuery(name="CustomRelease.findAll", query="SELECT c FROM CustomRelease c")
public class CustomRelease extends AuditEntityBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CUSTOMID")
	private String customid;

	@Column(name="BILL_NO")
	private String billNo;

	@Column(name="CANCEL_ID")
	private String cancelId;

	@Column(name="CANCEL_NAM")
	private String cancelNam;

	@Temporal(TemporalType.DATE)
	@Column(name="CANCEL_TIM")
	private Date cancelTim;

	@Column(name="CAR_NUM")
	private BigDecimal carNum;

	@Column(name="CONSIGN_COD")
	private String consignCod;

	@Column(name="CONTRACT_UNIT")
	private String contractUnit;

	@Column(name="CTM_C_SHIP_NAM")
	private String ctmCShipNam;

	@Column(name="CTM_SHIP_NO")
	private String ctmShipNo;

	@Column(name="CTM_VOYAGE")
	private String ctmVoyage;

	@Column(name="CUST_NAM")
	private String custNam;

	@Temporal(TemporalType.DATE)
	@Column(name="CUST_TIM")
	private Date custTim;

	@Column(name="CUSTOM_BILL_ID")
	private String customBillId;

	@Column(name="CUSTOM_BILL_NO")
	private String customBillNo;

	@Column(name="EDI_ID")
	private String ediId;

	@Column(name="I_E_ID")
	private String iEId;

	public String getiEId() {
		return iEId;
	}

	public void setiEId(String iEId) {
		this.iEId = iEId;
	}

	private BigDecimal pieces;

	@Column(name="REMARK_TXT")
	private String remarkTxt;

	@Column(name="SHIP_NO")
	private String shipNo;
	private BigDecimal volume;

	private BigDecimal weight;

	public CustomRelease() {
	}

	public String getCustomid() {
		return this.customid;
	}

	public void setCustomid(String customid) {
		this.customid = customid;
	}

	public String getBillNo() {
		return this.billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public String getCancelId() {
		return this.cancelId;
	}

	public void setCancelId(String cancelId) {
		this.cancelId = cancelId;
	}

	public String getCancelNam() {
		return this.cancelNam;
	}

	public void setCancelNam(String cancelNam) {
		this.cancelNam = cancelNam;
	}

	public Date getCancelTim() {
		return this.cancelTim;
	}

	public void setCancelTim(Date cancelTim) {
		this.cancelTim = cancelTim;
	}

	public BigDecimal getCarNum() {
		return this.carNum;
	}

	public void setCarNum(BigDecimal carNum) {
		this.carNum = carNum;
	}

	public String getConsignCod() {
		return this.consignCod;
	}

	public void setConsignCod(String consignCod) {
		this.consignCod = consignCod;
	}

	public String getContractUnit() {
		return this.contractUnit;
	}

	public void setContractUnit(String contractUnit) {
		this.contractUnit = contractUnit;
	}

	public String getCtmCShipNam() {
		return this.ctmCShipNam;
	}

	public void setCtmCShipNam(String ctmCShipNam) {
		this.ctmCShipNam = ctmCShipNam;
	}

	public String getCtmShipNo() {
		return this.ctmShipNo;
	}

	public void setCtmShipNo(String ctmShipNo) {
		this.ctmShipNo = ctmShipNo;
	}

	public String getCtmVoyage() {
		return this.ctmVoyage;
	}

	public void setCtmVoyage(String ctmVoyage) {
		this.ctmVoyage = ctmVoyage;
	}

	public String getCustNam() {
		return this.custNam;
	}

	public void setCustNam(String custNam) {
		this.custNam = custNam;
	}

	public Date getCustTim() {
		return this.custTim;
	}

	public void setCustTim(Date custTim) {
		this.custTim = custTim;
	}

	public String getCustomBillId() {
		return this.customBillId;
	}

	public void setCustomBillId(String customBillId) {
		this.customBillId = customBillId;
	}

	public String getCustomBillNo() {
		return this.customBillNo;
	}

	public void setCustomBillNo(String customBillNo) {
		this.customBillNo = customBillNo;
	}

	public String getEdiId() {
		return this.ediId;
	}

	public void setEdiId(String ediId) {
		this.ediId = ediId;
	}

	public BigDecimal getPieces() {
		return this.pieces;
	}

	public void setPieces(BigDecimal pieces) {
		this.pieces = pieces;
	}


	public String getRemarkTxt() {
		return this.remarkTxt;
	}

	public void setRemarkTxt(String remarkTxt) {
		this.remarkTxt = remarkTxt;
	}

	public String getShipNo() {
		return this.shipNo;
	}

	public void setShipNo(String shipNo) {
		this.shipNo = shipNo;
	}


	public BigDecimal getVolume() {
		return this.volume;
	}

	public void setVolume(BigDecimal volume) {
		this.volume = volume;
	}

	public BigDecimal getWeight() {
		return this.weight;
	}

	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

}