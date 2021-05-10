package net.huadong.tech.base.entity;

import java.io.Serializable;
import javax.persistence.*;

import net.huadong.tech.base.bean.AuditEntityBean;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the C_SPEC_PLAC database table.
 * 
 */
@Entity
@Table(name="C_SPEC_PLAC")
@NamedQuery(name="CSpecPlac.findAll", query="SELECT c FROM CSpecPlac c")
public class CSpecPlac extends AuditEntityBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="SPEC_PLAC")
	private String specPlac;

	@Column(name="CAR_NUM")
	private BigDecimal carNum;

	@Column(name="CY_TYP")
	private String cyTyp;

	@Column(name="DOCK_COD")
	private String dockCod;

	@Column(name="L_D_X")
	private BigDecimal lDX;

	@Column(name="L_D_Y")
	private BigDecimal lDY;

	@Column(name="L_U_X")
	private BigDecimal lUX;

	@Column(name="L_U_Y")
	private BigDecimal lUY;

	@Column(name="R_D_X")
	private BigDecimal rDX;

	@Column(name="R_D_Y")
	private BigDecimal rDY;

	@Column(name="R_U_X")
	private BigDecimal rUX;

	@Column(name="R_U_Y")
	private BigDecimal rUY;

	private String remarks;

	@Column(name="SPEC_PLAC_NAM")
	private String specPlacNam;

	public CSpecPlac() {
	}

	public String getSpecPlac() {
		return this.specPlac;
	}

	public void setSpecPlac(String specPlac) {
		this.specPlac = specPlac;
	}

	public BigDecimal getCarNum() {
		return carNum;
	}

	public void setCarNum(BigDecimal carNum) {
		this.carNum = carNum;
	}

	public String getCyTyp() {
		return this.cyTyp;
	}

	public void setCyTyp(String cyTyp) {
		this.cyTyp = cyTyp;
	}

	public String getDockCod() {
		return this.dockCod;
	}

	public void setDockCod(String dockCod) {
		this.dockCod = dockCod;
	}

	public BigDecimal getlDX() {
		return this.lDX;
	}

	public void setlDX(BigDecimal lDX) {
		this.lDX = lDX;
	}

	public BigDecimal getlDY() {
		return this.lDY;
	}

	public void setlDY(BigDecimal lDY) {
		this.lDY = lDY;
	}

	public BigDecimal getlUX() {
		return this.lUX;
	}

	public void setlUX(BigDecimal lUX) {
		this.lUX = lUX;
	}

	public BigDecimal getlUY() {
		return this.lUY;
	}

	public void setlUY(BigDecimal lUY) {
		this.lUY = lUY;
	}

	public BigDecimal getrDX() {
		return this.rDX;
	}

	public void setrDX(BigDecimal rDX) {
		this.rDX = rDX;
	}

	public BigDecimal getrDY() {
		return this.rDY;
	}

	public void setrDY(BigDecimal rDY) {
		this.rDY = rDY;
	}

	public BigDecimal getrUX() {
		return this.rUX;
	}

	public void setrUX(BigDecimal rUX) {
		this.rUX = rUX;
	}

	public BigDecimal getrUY() {
		return this.rUY;
	}

	public void setrUY(BigDecimal rUY) {
		this.rUY = rUY;
	}


	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getSpecPlacNam() {
		return this.specPlacNam;
	}

	public void setSpecPlacNam(String specPlacNam) {
		this.specPlacNam = specPlacNam;
	}


}