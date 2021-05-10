package net.huadong.tech.shipbill.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="SHIP_LOAD_BILL")
public class ShipLoadBill {
	
	@Id
	@Column(name="ID")
	private String id;
	
	@Column(name="BILL_NO")
	private String billNo;
	@Column(name="I_E_ID")
	private String iEId;
	
	@Column(name="SHIP_NO")
	private String shipNo;
	
	@Column(name="REC_NAM")
	private String recNam;

	@Temporal(TemporalType.DATE)
	@Column(name="REC_TIM")
	private Date recTim;
	
	@Column(name="BILL_NUM")
	private int billNum; 

	@Column(name="JQ_ID")
	private String jqId;

	@Column(name="FORCE_ID")
	private  String forceId ;

	public String getForceId() {
		return forceId;
	}

	public void setForceId(String forceId) {
		this.forceId = forceId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public String getiEId() {
		return iEId;
	}

	public void setiEId(String iEId) {
		this.iEId = iEId;
	}

	public String getShipNo() {
		return shipNo;
	}

	public void setShipNo(String shipNo) {
		this.shipNo = shipNo;
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

	public int getBillNum() {
		return billNum;
	}

	public void setBillNum(int billNum) {
		this.billNum = billNum;
	}
	
	public String getJqId() {
		return jqId;
	}
	
	public void setJqId(String jqId) {
		this.jqId = jqId;
	}
	
	
}
