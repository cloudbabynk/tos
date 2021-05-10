package net.huadong.tech.base.entity;

import java.io.Serializable;
import javax.persistence.*;

import net.huadong.tech.base.bean.AuditEntityBean;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the C_TRUCK database table.
 * 
 */
@Entity
@Table(name="C_TRUCK")
@NamedQuery(name="CTruck.findAll", query="SELECT c FROM CTruck c")
public class CTruck extends AuditEntityBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static final String ForBId="1";//今日计划

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="TRUCK_NO")
	private String truckNo;

	private BigDecimal capability;

	private String factroy;

	@Column(name="FORBID_ID")
	private String forbidId;

	@Column(name="FORBID_TXT")
	private String forbidTxt;

	@Temporal(TemporalType.DATE)
	@Column(name="IN_DATE")
	private Date inDate;

	@Column(name="MOBILE_TELE")
	private String mobileTele;

	@Column(name="PLAT_NO")
	private String platNo;


	@Column(name="RELATION_NAM")
	private String relationNam;

	private String remarks;

	private String telephon;

	@Column(name="TRAY_NO")
	private String trayNo;

	@Column(name="TRUCK_UNIT")
	private String truckUnit;

	@Column(name="TRUCK_WGT")
	private BigDecimal truckWgt;

	@Column(name="TRUCKER_NAM")
	private String truckerNam;

	@Column(name="TRUCKER_NO")
	private String truckerNo;


	public CTruck() {
	}

	public String getTruckNo() {
		return this.truckNo;
	}

	public void setTruckNo(String truckNo) {
		this.truckNo = truckNo;
	}

	public BigDecimal getCapability() {
		return this.capability;
	}

	public void setCapability(BigDecimal capability) {
		this.capability = capability;
	}

	public String getFactroy() {
		return this.factroy;
	}

	public void setFactroy(String factroy) {
		this.factroy = factroy;
	}

	public String getForbidId() {
		return this.forbidId;
	}

	public void setForbidId(String forbidId) {
		this.forbidId = forbidId;
	}

	public String getForbidTxt() {
		return this.forbidTxt;
	}

	public void setForbidTxt(String forbidTxt) {
		this.forbidTxt = forbidTxt;
	}

	public Date getInDate() {
		return this.inDate;
	}

	public void setInDate(Date inDate) {
		this.inDate = inDate;
	}

	public String getMobileTele() {
		return this.mobileTele;
	}

	public void setMobileTele(String mobileTele) {
		this.mobileTele = mobileTele;
	}

	public String getPlatNo() {
		return this.platNo;
	}

	public void setPlatNo(String platNo) {
		this.platNo = platNo;
	}

	public String getRelationNam() {
		return this.relationNam;
	}

	public void setRelationNam(String relationNam) {
		this.relationNam = relationNam;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getTelephon() {
		return this.telephon;
	}

	public void setTelephon(String telephon) {
		this.telephon = telephon;
	}

	public String getTrayNo() {
		return this.trayNo;
	}

	public void setTrayNo(String trayNo) {
		this.trayNo = trayNo;
	}

	public String getTruckUnit() {
		return this.truckUnit;
	}

	public void setTruckUnit(String truckUnit) {
		this.truckUnit = truckUnit;
	}

	public BigDecimal getTruckWgt() {
		return this.truckWgt;
	}

	public void setTruckWgt(BigDecimal truckWgt) {
		this.truckWgt = truckWgt;
	}

	public String getTruckerNam() {
		return this.truckerNam;
	}

	public void setTruckerNam(String truckerNam) {
		this.truckerNam = truckerNam;
	}

	public String getTruckerNo() {
		return this.truckerNo;
	}

	public void setTruckerNo(String truckerNo) {
		this.truckerNo = truckerNo;
	}


}