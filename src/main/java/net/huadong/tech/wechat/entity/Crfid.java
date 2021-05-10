package net.huadong.tech.wechat.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the SHIP_BERTH database table.
 * 
 */
@Entity
@Table(name="C_RFID")

public class Crfid implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="RFID_COD")
	private String rfidCod;

	@Column(name="RFID_NO")
	private String rfidNo;
	
	@Column(name="REC_NAM")
	private String recNam;

	@Temporal(TemporalType.DATE)
	@Column(name="REC_TIM")
	private Date recTim;

	public String getRfidCod() {
		return rfidCod;
	}

	public void setRfidCod(String rfidCod) {
		this.rfidCod = rfidCod;
	}

	public String getRfidNo() {
		return rfidNo;
	}

	public void setRfidNo(String rfidNo) {
		this.rfidNo = rfidNo;
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
	
	
}
