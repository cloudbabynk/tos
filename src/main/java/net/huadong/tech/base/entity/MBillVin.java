package net.huadong.tech.base.entity;

import java.io.Serializable;
import javax.persistence.*;

import net.huadong.tech.base.bean.AuditEntityBean;

import java.util.Date;


/**
 * The persistent class for the C_CAR_VIN database table.
 * 
 */
@Entity
@Table(name="M_BILL_VIN")
@NamedQuery(name="MBillVin.findAll", query="SELECT c FROM MBillVin c")
public class MBillVin extends AuditEntityBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="BILL_VIN_ID")
	private String billVinId;

	@Column(name="SHIP_NO")
	private String shipNo;
	
	@Column(name="BILL_NO")
	private String billNo;

	@Column(name="VIN_NO")
	private String vinNo;

	@Column(name="RFID_NO")
	private String rfidNo;
	
	@Column(name="I_E_ID")
	private String iEId;
	
	
	public MBillVin() {
	}
	
	public String getBillVinId() {
		return billVinId;
	}

	public void setBillVinId(String billVinId) {
		this.billVinId = billVinId;
	}

	public String getShipNo() {
		return shipNo;
	}

	public void setShipNo(String shipNo) {
		this.shipNo = shipNo;
	}

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public String getVinNo() {
		return vinNo;
	}

	public void setVinNo(String vinNo) {
		this.vinNo = vinNo;
	}
	
	public String getRfidNo() {
		return rfidNo;
	}

	public void setRfidNo(String rfidNo) {
		this.rfidNo = rfidNo;
	}

	public String getiEId() {
		return iEId;
	}

	public void setiEId(String iEId) {
		this.iEId = iEId;
	}	

}