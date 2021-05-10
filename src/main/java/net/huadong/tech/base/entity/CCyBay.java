package net.huadong.tech.base.entity;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import net.huadong.tech.base.bean.AuditEntityBean;
import net.huadong.tech.gate.entity.GateTruckContractPK;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the C_CY_BAY database table.
 * 
 */
@Entity
@Table(name="C_CY_BAY")
@IdClass(CCyBayPK.class)
@NamedQuery(name="CCyBay.findAll", query="SELECT c FROM CCyBay c")
public class CCyBay extends AuditEntityBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CY_AREA_NO")
	private String cyAreaNo;

	@Id
	@Column(name="CY_BAY_NO")
	private String cyBayNo;

	@Id
	@Column(name="CY_ROW_NO")
	private String cyRowNo;
	
	@Column(name="CY_PLAC")
	private String cyPlac;

	@Column(name="\"LENGTH\"")
	private BigDecimal length;

	@Column(name="LOCK_ID")
	private String lockId;

	private String remarks;

	private BigDecimal width;

	//bi-directional many-to-one association to CCyRow
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name="CY_AREA_NO", referencedColumnName="CY_AREA_NO", insertable=false, updatable=false),
		@JoinColumn(name="CY_ROW_NO", referencedColumnName="CY_ROW_NO", insertable=false, updatable=false)
		})
	private CCyRow CCyRow;

	public CCyBay() {
	}


	public String getCyPlac() {
		return this.cyPlac;
	}

	public void setCyPlac(String cyPlac) {
		this.cyPlac = cyPlac;
	}

	public String getCyAreaNo() {
		return cyAreaNo;
	}


	public void setCyAreaNo(String cyAreaNo) {
		this.cyAreaNo = cyAreaNo;
	}


	public String getCyBayNo() {
		return cyBayNo;
	}


	public void setCyBayNo(String cyBayNo) {
		this.cyBayNo = cyBayNo;
	}


	public String getCyRowNo() {
		return cyRowNo;
	}


	public void setCyRowNo(String cyRowNo) {
		this.cyRowNo = cyRowNo;
	}


	public BigDecimal getLength() {
		return this.length;
	}

	public void setLength(BigDecimal length) {
		this.length = length;
	}

	public String getLockId() {
		return this.lockId;
	}

	public void setLockId(String lockId) {
		this.lockId = lockId;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public BigDecimal getWidth() {
		return this.width;
	}

	public void setWidth(BigDecimal width) {
		this.width = width;
	}

	@JsonIgnore
	public CCyRow getCCyRow() {
		return this.CCyRow;
	}

	public void setCCyRow(CCyRow CCyRow) {
		this.CCyRow = CCyRow;
	}

}