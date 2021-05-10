package net.huadong.tech.ship.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

import net.huadong.tech.base.bean.AuditEntityBean;
import net.huadong.tech.util.HdUtils;


/**
 * The persistent class for the SHIP_CAR_MEASURE database table.
 * 
 */
@Entity
@Table(name="V_SHIP_CAR_MEASURE")
@NamedQuery(name="VShipCarMeasure.findAll", query="SELECT s FROM VShipCarMeasure s")
public class VShipCarMeasure extends AuditEntityBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID")
	private String id;

	@Column(name="I_E_ID")
	private String iEId;
	
	@Column(name="IE_NAM")
	private String ieNam;

	@Column(name="NEW_CAR_HIGHTH")
	private BigDecimal newCarHighth;

	@Column(name="NEW_CAR_LENGTH")
	private BigDecimal newCarLength;

	@Column(name="NEW_CAR_WIDTH")
	private BigDecimal newCarWidth;

	@Column(name="OLD_CAR_HIGHTH")
	private BigDecimal oldCarHighth;

	@Column(name="OLD_CAR_LENGTH")
	private BigDecimal oldCarLength;

	@Column(name="OLD_CAR_WIDTH")
	private BigDecimal oldCarWidth;


	@Column(name="REMARK_TXT")
	private String remarkTxt;

	@Column(name="SHIP_NAM")
	private String shipNam;

	@Column(name="SHIP_NO")
	private String shipNo;
	
	@Column(name="VOYAGE")
	private String voyage;
	
	@Column(name="BILL_NO")
	private String billNo;
	
	@Column(name="CAR_TYP")
	private String carTyp;
	
	@Column(name="CAR_VOL")
	private String carVol;
	
	@Column(name="OLD_CAR_VOL")
	private String oldCarVol;
	
	@Column(name="CAR_TYP_NAM")
	private String carTypNam;
	
	@Column(name="CAR_VIN")
	private String carVin;
	
	@Column(name="CAR_WEIGHT")
	private BigDecimal carWeight;
	
	@Column(name="CAR_NUM")
	private String carNum;
	
	@Column(name="CAR_SIZE")
	private String carSize;
	
	@Column(name="CONSIGN_COD")
	private String consignCod;
	
	@Column(name="OLD_CAR_SIZE")
	private String oldCarSize;
	
	@Column(name="CAR_TYP_VERSION")
	private String carTypVersion;
	
//	@Column(name="CONSIGN_NAM")
//	private String consignNam;
	
	@Column(name="QUEUE_ID")
	private String queueId;
	
	public VShipCarMeasure() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getQueueId() {
		return queueId;
	}

	public void setQueueId(String queueId) {
		this.queueId = queueId;
	}

	public String getConsignCod() {
		return consignCod;
	}

	public void setConsignCod(String consignCod) {
		this.consignCod = consignCod;
	}

	public String getOldCarVol() {
		return oldCarVol;
	}

	public void setOldCarVol(String oldCarVol) {
		this.oldCarVol = oldCarVol;
	}

	public String getOldCarSize() {
		return oldCarSize;
	}

	public void setOldCarSize(String oldCarSize) {
		this.oldCarSize = oldCarSize;
	}

	public String getCarTypVersion() {
		return carTypVersion;
	}

	public void setCarTypVersion(String carTypVersion) {
		this.carTypVersion = carTypVersion;
	}

//	public String getConsignNam() {
//		return consignNam;
//	}
//
//	public void setConsignNam(String consignNam) {
//		this.consignNam = consignNam;
//	}

	public String getCarNum() {
		return carNum;
	}

	public void setCarNum(String carNum) {
		this.carNum = carNum;
	}

	public String getCarSize() {
		return carSize;
	}

	public void setCarSize(String carSize) {
		this.carSize = carSize;
	}

	public String getCarVin() {
		return carVin;
	}

	public void setCarVin(String carVin) {
		this.carVin = carVin;
	}

	public BigDecimal getCarWeight() {
		return carWeight;
	}

	public void setCarWeight(BigDecimal carWeight) {
		this.carWeight = carWeight;
	}

	public String getiEId() {
		return this.iEId;
	}

	public void setiEId(String iEId) {
		this.iEId = iEId;
	}

	public BigDecimal getNewCarHighth() {
		return this.newCarHighth;
	}

	public void setNewCarHighth(BigDecimal newCarHighth) {
		this.newCarHighth = newCarHighth;
	}

	public BigDecimal getNewCarLength() {
		return this.newCarLength;
	}

	public void setNewCarLength(BigDecimal newCarLength) {
		this.newCarLength = newCarLength;
	}

	public BigDecimal getNewCarWidth() {
		return this.newCarWidth;
	}

	public void setNewCarWidth(BigDecimal newCarWidth) {
		this.newCarWidth = newCarWidth;
	}

	public BigDecimal getOldCarHighth() {
		return this.oldCarHighth;
	}

	public void setOldCarHighth(BigDecimal oldCarHighth) {
		this.oldCarHighth = oldCarHighth;
	}

	public BigDecimal getOldCarLength() {
		return this.oldCarLength;
	}

	public void setOldCarLength(BigDecimal oldCarLength) {
		this.oldCarLength = oldCarLength;
	}

	public BigDecimal getOldCarWidth() {
		return this.oldCarWidth;
	}

	public void setOldCarWidth(BigDecimal oldCarWidth) {
		this.oldCarWidth = oldCarWidth;
	}	

	public String getRemarkTxt() {
		return this.remarkTxt;
	}

	public void setRemarkTxt(String remarkTxt) {
		this.remarkTxt = remarkTxt;
	}

	public String getShipNam() {
		return this.shipNam;
	}

	public void setShipNam(String shipNam) {
		this.shipNam = shipNam;
	}

	public String getShipNo() {
		return this.shipNo;
	}

	public void setShipNo(String shipNo) {
		this.shipNo = shipNo;
	}

	public String getVoyage() {
		return this.voyage;
	}

	public void setVoyage(String voyage) {
		this.voyage = voyage;
	}

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public String getCarTyp() {
		return carTyp;
	}

	public void setCarTyp(String carTyp) {
		this.carTyp = carTyp;
	}

	public String getIeNam() {
		return ieNam;
	}

	public void setIeNam(String ieNam) {
		this.ieNam = ieNam;
	}

	public String getCarTypNam() {
		return carTypNam;
	}

	public void setCarTypNam(String carTypNam) {
		this.carTypNam = carTypNam;
	}

	public String getCarVol() {
		return carVol;
	}

	public void setCarVol(String carVol) {
		this.carVol = carVol;
	}



}