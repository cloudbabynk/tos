package net.huadong.tech.damage.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the LOCK_CAR_DOC database table.
 * 
 */
@Entity
@Table(name="LOCK_CAR_DOC")
@NamedQuery(name="LockCarDoc.findAll", query="SELECT l FROM LockCarDoc l")
public class LockCarDoc implements Serializable {
	private static final long serialVersionUID = 1L;

	@Transient
	private String brandNam;
	
	@Transient
	private String lockReasonNam;
	
	@Transient
	private String lockUnitNam;
	
	@Id
	@Column(name="LOCKCAR_ID")
	private String lockcarId;

	@Column(name="BILL_NO")
	private String billNo;

	@Column(name="BRAND_COD")
	private String brandCod;

	@Column(name="LOCK_ID")
	private String lockId;

	@Column(name="LOCK_REASON")
	private String lockReason;

	@Column(name="LOCK_REC_NAM")
	private String lockRecNam;

	@Temporal(TemporalType.DATE)
	@Column(name="LOCK_REC_TIM")
	private Date lockRecTim;

	@Column(name="LOCK_UNIT")
	private String lockUnit;

	@Column(name="PORT_CAR_NO")
	private BigDecimal portCarNo;

	@Column(name="REL_ID")
	private String relId;

	@Column(name="REL_REC_NAM")
	private String relRecNam;

	@Temporal(TemporalType.DATE)
	@Column(name="REL_REC_TIM")
	private Date relRecTim;

	@Column(name="REMARK_TXT")
	private String remarkTxt;

	@Column(name="RFID_CARD_NO")
	private String rfidCardNo;

	@Column(name="UPD_NAM")
	private String updNam;

	@Temporal(TemporalType.DATE)
	@Column(name="UPD_TIM")
	private Date updTim;

	@Column(name="VIN_NO")
	private String vinNo;

	public LockCarDoc() {
	}

	public String getBrandNam() {
		return brandNam;
	}

	public void setBrandNam(String brandNam) {
		this.brandNam = brandNam;
	}

	public String getLockReasonNam() {
		return lockReasonNam;
	}

	public void setLockReasonNam(String lockReasonNam) {
		this.lockReasonNam = lockReasonNam;
	}

	public String getLockUnitNam() {
		return lockUnitNam;
	}

	public void setLockUnitNam(String lockUnitNam) {
		this.lockUnitNam = lockUnitNam;
	}

	public String getLockcarId() {
		return this.lockcarId;
	}

	public void setLockcarId(String lockcarId) {
		this.lockcarId = lockcarId;
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

	public String getLockId() {
		return this.lockId;
	}

	public void setLockId(String lockId) {
		this.lockId = lockId;
	}

	public String getLockReason() {
		return this.lockReason;
	}

	public void setLockReason(String lockReason) {
		this.lockReason = lockReason;
	}

	public String getLockRecNam() {
		return this.lockRecNam;
	}

	public void setLockRecNam(String lockRecNam) {
		this.lockRecNam = lockRecNam;
	}

	public Date getLockRecTim() {
		return this.lockRecTim;
	}

	public void setLockRecTim(Date lockRecTim) {
		this.lockRecTim = lockRecTim;
	}

	public String getLockUnit() {
		return this.lockUnit;
	}

	public void setLockUnit(String lockUnit) {
		this.lockUnit = lockUnit;
	}

	public BigDecimal getPortCarNo() {
		return this.portCarNo;
	}

	public void setPortCarNo(BigDecimal portCarNo) {
		this.portCarNo = portCarNo;
	}

	public String getRelId() {
		return this.relId;
	}

	public void setRelId(String relId) {
		this.relId = relId;
	}

	public String getRelRecNam() {
		return this.relRecNam;
	}

	public void setRelRecNam(String relRecNam) {
		this.relRecNam = relRecNam;
	}

	public Date getRelRecTim() {
		return this.relRecTim;
	}

	public void setRelRecTim(Date relRecTim) {
		this.relRecTim = relRecTim;
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

}