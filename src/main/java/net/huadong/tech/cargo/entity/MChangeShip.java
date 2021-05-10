package net.huadong.tech.cargo.entity;

import java.io.Serializable;
import javax.persistence.*;

import net.huadong.tech.base.bean.AuditEntityBean;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the M_CHANGE_SHIP database table.
 * 
 */
@Entity
@Table(name="M_CHANGE_SHIP")
@NamedQuery(name="MChangeShip.findAll", query="SELECT m FROM MChangeShip m")
public class MChangeShip implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String changeid;

	@Column(name="BRAND_COD")
	private String brandCod;

	@Column(name="CY_PLAC")
	private String cyPlac;

	@Column(name="N_I_E_ID")
	private String nIEId;

	@Column(name="NEW_BILL_NO")
	private String newBillNo;

	@Column(name="NEW_DISC_PORT_COD")
	private String newDiscPortCod;

	@Column(name="NEW_SHIP_NO")
	private String newShipNo;

	@Column(name="NEW_TRAN_PORT_COD")
	private String newTranPortCod;

	@Column(name="O_I_E_ID")
	private String oIEId;

	@Column(name="OLD_BILL_NO")
	private String oldBillNo;

	@Column(name="OLD_DISC_PORT_COD")
	private String oldDiscPortCod;

	@Column(name="OLD_SHIP_NO")
	private String oldShipNo;

	@Column(name="OLD_TRAN_PORT_COD")
	private String oldTranPortCod;

	@Column(name="PORT_CAR_NO")
	private BigDecimal portCarNo;

	@Column(name="RFID_CARD_NO")
	private String rfidCardNo;

	@Column(name="VIN_NO")
	private String vinNo;
	
	@Column(name="REC_NAM")
	private String recNam;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="REC_TIM")
	private Date recTim;

	@Column(name="FLAG")
	private String flag;

	@Transient
	private String oldNamVoyage;
	
	@Transient
	private String newNamVoyage;

	@Transient
	private String brandNam;
	@Transient
	private String newTranPortNam;
	
	@Transient
	private String newDiscPortNam;
	
	@Transient
	private String countNum;
	
	public MChangeShip() {
	}
	public String getBrandNam() {
		return brandNam;
	}

	public void setBrandNam(String brandNam) {
		this.brandNam = brandNam;
	}

	public String getNewTranPortNam() {
		return newTranPortNam;
	}
	public void setNewTranPortNam(String newTranPortNam) {
		this.newTranPortNam = newTranPortNam;
	}
	public String getNewDiscPortNam() {
		return newDiscPortNam;
	}
	public void setNewDiscPortNam(String newDiscPortNam) {
		this.newDiscPortNam = newDiscPortNam;
	}
	public String getnIEId() {
		return nIEId;
	}

	public String getOldNamVoyage() {
		return oldNamVoyage;
	}

	public void setOldNamVoyage(String oldNamVoyage) {
		this.oldNamVoyage = oldNamVoyage;
	}

	public String getNewNamVoyage() {
		return newNamVoyage;
	}

	public void setNewNamVoyage(String newNamVoyage) {
		this.newNamVoyage = newNamVoyage;
	}

	public void setnIEId(String nIEId) {
		this.nIEId = nIEId;
	}

	public String getoIEId() {
		return oIEId;
	}

	public void setoIEId(String oIEId) {
		this.oIEId = oIEId;
	}

	public String getRecNam() {
		return recNam;
	}

	public void setRecNam(String recNam) {
		this.recNam = recNam;
	}

	public Date getRecTim() {
		return recTim;
	}

	public void setRecTim(Date recTim) {
		this.recTim = recTim;
	}

	public String getChangeid() {
		return this.changeid;
	}

	public void setChangeid(String changeid) {
		this.changeid = changeid;
	}

	public String getBrandCod() {
		return this.brandCod;
	}

	public void setBrandCod(String brandCod) {
		this.brandCod = brandCod;
	}

	public String getCyPlac() {
		return this.cyPlac;
	}

	public void setCyPlac(String cyPlac) {
		this.cyPlac = cyPlac;
	}

	public String getNIEId() {
		return this.nIEId;
	}

	public void setNIEId(String nIEId) {
		this.nIEId = nIEId;
	}

	public String getNewBillNo() {
		return this.newBillNo;
	}

	public void setNewBillNo(String newBillNo) {
		this.newBillNo = newBillNo;
	}

	public String getNewDiscPortCod() {
		return this.newDiscPortCod;
	}

	public void setNewDiscPortCod(String newDiscPortCod) {
		this.newDiscPortCod = newDiscPortCod;
	}

	public String getNewShipNo() {
		return this.newShipNo;
	}

	public void setNewShipNo(String newShipNo) {
		this.newShipNo = newShipNo;
	}

	public String getNewTranPortCod() {
		return this.newTranPortCod;
	}

	public void setNewTranPortCod(String newTranPortCod) {
		this.newTranPortCod = newTranPortCod;
	}

	public String getOIEId() {
		return this.oIEId;
	}

	public void setOIEId(String oIEId) {
		this.oIEId = oIEId;
	}

	public String getOldBillNo() {
		return this.oldBillNo;
	}

	public void setOldBillNo(String oldBillNo) {
		this.oldBillNo = oldBillNo;
	}

	public String getOldDiscPortCod() {
		return this.oldDiscPortCod;
	}

	public void setOldDiscPortCod(String oldDiscPortCod) {
		this.oldDiscPortCod = oldDiscPortCod;
	}

	public String getOldShipNo() {
		return this.oldShipNo;
	}

	public void setOldShipNo(String oldShipNo) {
		this.oldShipNo = oldShipNo;
	}

	public String getOldTranPortCod() {
		return this.oldTranPortCod;
	}

	public void setOldTranPortCod(String oldTranPortCod) {
		this.oldTranPortCod = oldTranPortCod;
	}

	public BigDecimal getPortCarNo() {
		return this.portCarNo;
	}

	public void setPortCarNo(BigDecimal portCarNo) {
		this.portCarNo = portCarNo;
	}


	public String getRfidCardNo() {
		return this.rfidCardNo;
	}

	public void setRfidCardNo(String rfidCardNo) {
		this.rfidCardNo = rfidCardNo;
	}

	public String getVinNo() {
		return this.vinNo;
	}

	public void setVinNo(String vinNo) {
		this.vinNo = vinNo;
	}
	public String getCountNum() {
		return countNum;
	}
	public void setCountNum(String countNum) {
		this.countNum = countNum;
	}
	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
}