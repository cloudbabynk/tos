package net.huadong.tech.damage.entity;

import java.io.Serializable;
import javax.persistence.*;

import net.huadong.tech.base.bean.AuditEntityBean;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the M_OVER_SHORT_BACK database table.
 * 
 */
@Entity
@Table(name="M_OVER_SHORT_BACK")
@NamedQuery(name="MOverShortBack.findAll", query="SELECT m FROM MOverShortBack m")
public class MOverShortBack  extends AuditEntityBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="OVERSHORT_ID")
	private String overshortId;

	@Column(name="BILL_NO")
	private String billNo;

	@Column(name="BRAND_COD")
	private String brandCod;

	@Column(name="I_E_ID")
	private String iEId;

	@Column(name="MISS_ID")
	private String missId;

	@Column(name="PORT_CAR_NO")
	private BigDecimal portCarNo;

	private String remarks;

	@Column(name="RFID_CARD_NO")
	private String rfidCardNo;

	@Column(name="SHIP_NO")
	private String shipNo;

	@Column(name="VIN_NO")
	private String vinNo;

	public MOverShortBack() {
	}
	public String getiEId() {
		return iEId;
	}

	public void setiEId(String iEId) {
		this.iEId = iEId;
	}
	public String getOvershortId() {
		return this.overshortId;
	}

	public void setOvershortId(String overshortId) {
		this.overshortId = overshortId;
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


	public String getMissId() {
		return this.missId;
	}

	public void setMissId(String missId) {
		this.missId = missId;
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

	public String getVinNo() {
		return this.vinNo;
	}

	public void setVinNo(String vinNo) {
		this.vinNo = vinNo;
	}

}