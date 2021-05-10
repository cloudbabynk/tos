package net.huadong.tech.damage.entity;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;

import net.huadong.tech.base.bean.AuditEntityBean;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;


/**
 * The persistent class for the M_OVERLENGH_CONFIRM database table.
 * 
 */
@Entity
@Table(name="M_OVERLENGH_CONFIRM")
@NamedQuery(name="MOverlenghConfirm.findAll", query="SELECT m FROM MOverlenghConfirm m")
public class MOverlenghConfirm extends AuditEntityBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String confirmid;

	@Column(name="REG_DTE")
	private  Timestamp regDte;
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	public Timestamp getRegDte() {
		return regDte;
	}
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	public void setRegDte(Timestamp regDte) {
		this.regDte = regDte;
	}
	@Column(name="BRAND_COD")
	private String brandCod;
	@Column(name="CAR_TYP")
	private String carTyp;
	public String getCarTyp() {
		return carTyp;
	}
	public void setCarTyp(String carTyp) {
		this.carTyp = carTyp;
	}
	private BigDecimal height;

	private BigDecimal lengh;

	@Column(name="LENGTH_OVER_ID")
	private String lengthOverId;

	@Column(name="PORT_CAR_NO")
	private BigDecimal portCarNo;


	@Column(name="RFID_CARD_NO")
	private String rfidCardNo;

	@Column(name="VIN_NO")
	private String vinNo;

	private BigDecimal width;

	@Column(name="WIDTH_OVER_ID")
	private String widthOverId;
	
	@Column(name="SHIP_NO")
	private String shipNo;

	@Transient
	private String carTypNam;
	
	@Transient
	private String cyPlac;

	public String getCyPlac() {
		return cyPlac;
	}
	public void setCyPlac(String cyPlac) {
		this.cyPlac = cyPlac;
	}
	public MOverlenghConfirm() {
	}
	
	public String getCarTypNam() {
		return carTypNam;
	}
	public void setCarTypNam(String carTypNam) {
		this.carTypNam = carTypNam;
	}
	
	public String getConfirmid() {
		return this.confirmid;
	}

	public void setConfirmid(String confirmid) {
		this.confirmid = confirmid;
	}

	public String getBrandCod() {
		return this.brandCod;
	}

	public void setBrandCod(String brandCod) {
		this.brandCod = brandCod;
	}

	public BigDecimal getHeight() {
		return this.height;
	}

	public void setHeight(BigDecimal height) {
		this.height = height;
	}

	public BigDecimal getLengh() {
		return this.lengh;
	}

	public void setLengh(BigDecimal lengh) {
		this.lengh = lengh;
	}

	public String getLengthOverId() {
		return this.lengthOverId;
	}

	public void setLengthOverId(String lengthOverId) {
		this.lengthOverId = lengthOverId;
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
	public String getShipNo() {
		return shipNo;
	}
	public void setShipNo(String shipNo) {
		this.shipNo = shipNo;
	}

}