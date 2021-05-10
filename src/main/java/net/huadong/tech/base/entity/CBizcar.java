package net.huadong.tech.base.entity;

import java.io.Serializable;
import javax.persistence.*;

import net.huadong.tech.base.bean.AuditEntityBean;

import java.util.Date;


/**
 * The persistent class for the C_BIZCAR database table.
 * 
 */
@Entity
@Table(name="C_BIZCAR")
@NamedQuery(name="CBizcar.findAll", query="SELECT c FROM CBizcar c")
public class CBizcar extends AuditEntityBean  implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="BIZCAR_NO")
	private String bizcarNo;

	@Column(name="CAR_COLOR")
	private String carColor;

	@Column(name="CAR_PROP")
	private String carProp;

	@Column(name="CAR_TYP")
	private String carTyp;

	@Column(name="CORP_NAM")
	private String corpNam;

	@Column(name="DRIVER_NAM")
	private String driverNam;

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

	public CBizcar() {
	}

	public String getBizcarNo() {
		return this.bizcarNo;
	}

	public void setBizcarNo(String bizcarNo) {
		this.bizcarNo = bizcarNo;
	}

	public String getCarColor() {
		return this.carColor;
	}

	public void setCarColor(String carColor) {
		this.carColor = carColor;
	}

	public String getCarProp() {
		return this.carProp;
	}

	public void setCarProp(String carProp) {
		this.carProp = carProp;
	}

	public String getCarTyp() {
		return this.carTyp;
	}

	public void setCarTyp(String carTyp) {
		this.carTyp = carTyp;
	}

	public String getCorpNam() {
		return this.corpNam;
	}

	public void setCorpNam(String corpNam) {
		this.corpNam = corpNam;
	}

	public String getDriverNam() {
		return this.driverNam;
	}

	public void setDriverNam(String driverNam) {
		this.driverNam = driverNam;
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


}