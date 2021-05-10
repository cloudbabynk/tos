package net.huadong.tech.base.entity;

import java.io.Serializable;
import javax.persistence.*;

import net.huadong.tech.base.bean.AuditEntityBean;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the C_CY_AREA database table.
 * 
 */
@Entity
@Table(name="C_CY_AREA")
@NamedQuery(name="CCyArea.findAll", query="SELECT c FROM CCyArea c")
public class CCyArea extends AuditEntityBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CY_AREA_NO")
	private String cyAreaNo;
	
	@Transient
	private String dockNam;
	
	@Transient
	private Long unlockNum;
	
	@Column(name="CY_AREA_NAM")
	private String cyAreaNam;

	@Column(name="BAY_NUM")
	private BigDecimal bayNum;

	@Column(name="CY_TYP")
	private String cyTyp;

	@Column(name="DOCK_COD")
	private String dockCod;

	private BigDecimal height;

	private String remarks;

	@Column(name="ROW_NUM")
	private BigDecimal rowNum;
	
	@Column(name="LOCK_ID")
	private String lockId;
	
	@Column(name="POS")
	private String pos;

	private String scheme;

	private BigDecimal width;

	private BigDecimal x0;

	private BigDecimal x1;

	private BigDecimal x2;

	private BigDecimal x3;

	private BigDecimal y0;

	private BigDecimal y1;

	private BigDecimal y2;

	private BigDecimal y3;
	
	@Transient
	private String jcs;
	
	@Transient
	private String sbs;
	
	@Transient
	private String zdcc;
	
	//（昨8点至今8点）出库数
	@Transient
	private String otNum;
	//今8点）在库数
	@Transient
	private String inNum;
	
	
	@Transient
	private BigDecimal zyl;

	//bi-directional many-to-one association to CCyRow

	public CCyArea() {
	}

	public String getCyAreaNo() {
		return this.cyAreaNo;
	}

	public void setCyAreaNo(String cyAreaNo) {
		this.cyAreaNo = cyAreaNo;
	}

	public BigDecimal getBayNum() {
		return this.bayNum;
	}

	public void setBayNum(BigDecimal bayNum) {
		this.bayNum = bayNum;
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

	public String getCyAreaNam() {
		return cyAreaNam;
	}

	public void setCyAreaNam(String cyAreaNam) {
		this.cyAreaNam = cyAreaNam;
	}

	public BigDecimal getHeight() {
		return this.height;
	}

	public void setHeight(BigDecimal height) {
		this.height = height;
	}


	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public BigDecimal getRowNum() {
		return this.rowNum;
	}

	public void setRowNum(BigDecimal rowNum) {
		this.rowNum = rowNum;
	}

	public String getScheme() {
		return this.scheme;
	}

	public void setScheme(String scheme) {
		this.scheme = scheme;
	}

	public BigDecimal getWidth() {
		return this.width;
	}

	public void setWidth(BigDecimal width) {
		this.width = width;
	}

	public BigDecimal getX0() {
		return this.x0;
	}

	public void setX0(BigDecimal x0) {
		this.x0 = x0;
	}

	public BigDecimal getX1() {
		return this.x1;
	}

	public void setX1(BigDecimal x1) {
		this.x1 = x1;
	}

	public BigDecimal getX2() {
		return this.x2;
	}

	public void setX2(BigDecimal x2) {
		this.x2 = x2;
	}

	public BigDecimal getX3() {
		return this.x3;
	}

	public void setX3(BigDecimal x3) {
		this.x3 = x3;
	}

	public BigDecimal getY0() {
		return this.y0;
	}

	public void setY0(BigDecimal y0) {
		this.y0 = y0;
	}

	public BigDecimal getY1() {
		return this.y1;
	}

	public void setY1(BigDecimal y1) {
		this.y1 = y1;
	}

	public BigDecimal getY2() {
		return this.y2;
	}

	public void setY2(BigDecimal y2) {
		this.y2 = y2;
	}

	public BigDecimal getY3() {
		return this.y3;
	}

	public void setY3(BigDecimal y3) {
		this.y3 = y3;
	}

	public String getDockNam() {
		return dockNam;
	}

	public void setDockNam(String dockNam) {
		this.dockNam = dockNam;
	}

	public Long getUnlockNum() {
		return unlockNum;
	}

	public void setUnlockNum(Long unlockNum) {
		this.unlockNum = unlockNum;
	}

	public String getJcs() {
		return jcs;
	}

	public void setJcs(String jcs) {
		this.jcs = jcs;
	}

	public String getSbs() {
		return sbs;
	}

	public void setSbs(String sbs) {
		this.sbs = sbs;
	}

	public String getZdcc() {
		return zdcc;
	}

	public void setZdcc(String zdcc) {
		this.zdcc = zdcc;
	}

	public BigDecimal getZyl() {
		return zyl;
	}

	public void setZyl(BigDecimal zyl) {
		this.zyl = zyl;
	}

	public String getPos() {
		return pos;
	}

	public void setPos(String pos) {
		this.pos = pos;
	}

	public String getLockId() {
		return lockId;
	}

	public void setLockId(String lockId) {
		this.lockId = lockId;
	}

	public String getOtNum() {
		return otNum;
	}

	public void setOtNum(String otNum) {
		this.otNum = otNum;
	}

	public String getInNum() {
		return inNum;
	}

	public void setInNum(String inNum) {
		this.inNum = inNum;
	}

	

}