package net.huadong.tech.base.entity;

import java.io.Serializable;
import javax.persistence.*;

import net.huadong.tech.base.bean.AuditEntityBean;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the C_CONVERT_STAND database table.
 * 
 */
@Entity
@Table(name="C_CONVERT_STAND")
@NamedQuery(name="CConvertStand.findAll", query="SELECT c FROM CConvertStand c")
public class CConvertStand extends AuditEntityBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="STAND_ID")
	private String standId;

	@Column(name="\"LENGTH\"")
	private BigDecimal length;

	@Column(name="REMARK_TXT")
	private String remarkTxt;

	@Column(name="STAND_COD")
	private BigDecimal standCod;

	@Column(name="STAND_VALUE")
	private BigDecimal standValue;


	public CConvertStand() {
	}

	public String getStandId() {
		return this.standId;
	}

	public void setStandId(String standId) {
		this.standId = standId;
	}

	public BigDecimal getLength() {
		return this.length;
	}

	public void setLength(BigDecimal length) {
		this.length = length;
	}

	public String getRemarkTxt() {
		return this.remarkTxt;
	}

	public void setRemarkTxt(String remarkTxt) {
		this.remarkTxt = remarkTxt;
	}

	public BigDecimal getStandCod() {
		return this.standCod;
	}

	public void setStandCod(BigDecimal standCod) {
		this.standCod = standCod;
	}

	public BigDecimal getStandValue() {
		return this.standValue;
	}

	public void setStandValue(BigDecimal standValue) {
		this.standValue = standValue;
	}

}