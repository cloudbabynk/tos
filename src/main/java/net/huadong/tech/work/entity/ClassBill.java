package net.huadong.tech.work.entity;

import java.io.Serializable;
import javax.persistence.*;

import net.huadong.tech.base.bean.AuditEntityBean;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the CLASS_BILL database table.
 * 
 */
@Entity
@Table(name="CLASS_BILL")
@NamedQuery(name="ClassBill.findAll", query="SELECT c FROM ClassBill c")
public class ClassBill extends AuditEntityBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CLASSBILL_NO")
	private String classbillNo;

	@Column(name="BILL_NO")
	private String billNo;

	@Column(name="BILL_TYP")
	private String billTyp;

	private String brand;

	@Column(name="CAR_KIND")
	private String carKind;

	@Column(name="CAR_LEVEL")
	private String carLevel;

	@Column(name="CAR_TYP")
	private String carTyp;

	@Column(name="CY_AREAS")
	private String cyAreas;

	@Column(name="DRIVER_CLASS")
	private String driverClass;

	@Column(name="I_E_ID")
	private String iEId;

	@Column(name="SHIP_NO")
	private String shipNo;

	@Column(name="TALLIER_NAM")
	private String tallierNam;


	@Temporal(TemporalType.DATE)
	@Column(name="WORK_DTE")
	private Date workDte;

	@Column(name="WORK_NUM")
	private BigDecimal workNum;

	@Column(name="WORK_RUN_COD")
	private String workRunCod;
	
	@Column(name="MAFI_NUM")
	private BigDecimal mafiNum;
	
	@Transient
	private String iEIdNam;
	
	@Transient
	private String billTypNam;
	
	@Transient
	private String brandNam;
	
	@Transient
	private String workRunCodNam;
	
	@Transient
	private String carKindNam;
	
	@Transient
	private String carTypNam;
	
	@Transient
	private String driverClassNam;

	public ClassBill() {
	}

	public String getClassbillNo() {
		return this.classbillNo;
	}

	public void setClassbillNo(String classbillNo) {
		this.classbillNo = classbillNo;
	}

	public String getBillNo() {
		return this.billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public String getBillTyp() {
		return this.billTyp;
	}

	public void setBillTyp(String billTyp) {
		this.billTyp = billTyp;
	}

	public String getBrand() {
		return this.brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getCarKind() {
		return this.carKind;
	}

	public void setCarKind(String carKind) {
		this.carKind = carKind;
	}

	public String getCarLevel() {
		return this.carLevel;
	}

	public void setCarLevel(String carLevel) {
		this.carLevel = carLevel;
	}

	public String getCarTyp() {
		return this.carTyp;
	}

	public void setCarTyp(String carTyp) {
		this.carTyp = carTyp;
	}

	public String getCyAreas() {
		return this.cyAreas;
	}

	public void setCyAreas(String cyAreas) {
		this.cyAreas = cyAreas;
	}

	public String getDriverClass() {
		return this.driverClass;
	}

	public void setDriverClass(String driverClass) {
		this.driverClass = driverClass;
	}

	public String getiEId() {
		return iEId;
	}

	public void setiEId(String iEId) {
		this.iEId = iEId;
	}

	public String getShipNo() {
		return this.shipNo;
	}

	public void setShipNo(String shipNo) {
		this.shipNo = shipNo;
	}

	public String getTallierNam() {
		return this.tallierNam;
	}

	public void setTallierNam(String tallierNam) {
		this.tallierNam = tallierNam;
	}

	public Date getWorkDte() {
		return this.workDte;
	}

	public void setWorkDte(Date workDte) {
		this.workDte = workDte;
	}

	public BigDecimal getWorkNum() {
		return this.workNum;
	}

	public void setWorkNum(BigDecimal workNum) {
		this.workNum = workNum;
	}

	public String getWorkRunCod() {
		return this.workRunCod;
	}

	public void setWorkRunCod(String workRunCod) {
		this.workRunCod = workRunCod;
	}

	public String getWorkRunCodNam() {
		return workRunCodNam;
	}

	public void setWorkRunCodNam(String workRunCodNam) {
		this.workRunCodNam = workRunCodNam;
	}

	public String getiEIdNam() {
		return iEIdNam;
	}

	public void setiEIdNam(String iEIdNam) {
		this.iEIdNam = iEIdNam;
	}

	public String getBillTypNam() {
		return billTypNam;
	}

	public void setBillTypNam(String billTypNam) {
		this.billTypNam = billTypNam;
	}

	public String getBrandNam() {
		return brandNam;
	}

	public void setBrandNam(String brandNam) {
		this.brandNam = brandNam;
	}

	public String getCarKindNam() {
		return carKindNam;
	}

	public void setCarKindNam(String carKindNam) {
		this.carKindNam = carKindNam;
	}

	public String getCarTypNam() {
		return carTypNam;
	}

	public void setCarTypNam(String carTypNam) {
		this.carTypNam = carTypNam;
	}

	public String getDriverClassNam() {
		return driverClassNam;
	}

	public void setDriverClassNam(String driverClassNam) {
		this.driverClassNam = driverClassNam;
	}

	public BigDecimal getMafiNum() {
		return mafiNum;
	}

	public void setMafiNum(BigDecimal mafiNum) {
		this.mafiNum = mafiNum;
	}

	
}