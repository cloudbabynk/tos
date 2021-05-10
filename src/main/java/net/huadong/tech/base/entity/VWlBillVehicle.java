package net.huadong.tech.base.entity;

import java.io.Serializable;

import java.io.Serializable;
import javax.persistence.*;

/**
 * 
 * @ClassName 
 * @Description 外理视图查询
 * @author yl
 * @date 2018.7.2
 */
@Entity
@Table(name="V_WL_BILL_VEHICLE")
public class VWlBillVehicle implements Serializable {
	private static final long serialVersionUID = 1L;
	
	
	private String imo;
	
	private String voyage;
	
	@Column(name="BILL_NO")
	private String billNo;
	
	@Id
	private String vin;

	public String getImo() {
		return imo;
	}

	public void setImo(String imo) {
		this.imo = imo;
	}

	public String getVoyage() {
		return voyage;
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

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}



}