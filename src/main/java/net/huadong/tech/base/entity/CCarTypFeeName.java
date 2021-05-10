package net.huadong.tech.base.entity;

import java.io.Serializable;
import javax.persistence.*;

import net.huadong.tech.base.bean.AuditEntityBean;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the C_CAR_TYP database table.
 * 
 */
@Entity
@Table(name="C_CAR_TYP_FEE_NAME")
@NamedQuery(name="CCarTypFeeName.findAll", query="SELECT c FROM CCarTypFeeName c")
public class CCarTypFeeName extends AuditEntityBean implements Serializable {
	
	@Id
	@Column(name="ID")
	private String id;
    @Column(name="NAME")
	private String carTypName;

	@Column(name="REMARKS")
	private String remarks;
	
	@Column(name="CAR_FEE_TYP")
	private String carFeeTyp;

	@Column(name="CAR_LEVEL")
	private String carLevel;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCarTypName() {
		return carTypName;
	}

	public void setCarTypName(String carTypName) {
		this.carTypName = carTypName;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getCarFeeTyp() {
		return carFeeTyp;
	}

	public void setCarFeeTyp(String carFeeTyp) {
		this.carFeeTyp = carFeeTyp;
	}

	public String getCarLevel() {
		return carLevel;
	}

	public void setCarLevel(String carLevel) {
		this.carLevel = carLevel;
	}


	
	
}