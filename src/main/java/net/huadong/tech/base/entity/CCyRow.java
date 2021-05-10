package net.huadong.tech.base.entity;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import net.huadong.tech.base.bean.AuditEntityBean;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the C_CY_ROW database table.
 * 
 */
@Entity
@Table(name="C_CY_ROW")
@IdClass(CCyRowPK.class)
@NamedQuery(name="CCyRow.findAll", query="SELECT c FROM CCyRow c")
public class CCyRow extends AuditEntityBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CY_AREA_NO")
	private String cyAreaNo;
    
	@Transient
	private Long unlockNum;
	
	@Id
	@Column(name="CY_ROW_NO")
	private String cyRowNo;
	
	@Column(name="\"LENGTH\"")
	private BigDecimal length;

	@Column(name="MAX_BAY_NUM")
	private BigDecimal maxBayNum;

	private String remarks;
	

	private BigDecimal width;


	//bi-directional many-to-one association to CCyArea
	@ManyToOne
	@JoinColumn(name="CY_AREA_NO", insertable=false, updatable=false)
	private CCyArea CCyArea;

	public CCyRow() {
	}

	public BigDecimal getLength() {
		return this.length;
	}

	public void setLength(BigDecimal length) {
		this.length = length;
	}

	public String getCyAreaNo() {
		return cyAreaNo;
	}

	public void setCyAreaNo(String cyAreaNo) {
		this.cyAreaNo = cyAreaNo;
	}
		

	public String getCyRowNo() {
		return cyRowNo;
	}

	public void setCyRowNo(String cyRowNo) {
		this.cyRowNo = cyRowNo;
	}

	public BigDecimal getMaxBayNum() {
		return this.maxBayNum;
	}

	public void setMaxBayNum(BigDecimal maxBayNum) {
		this.maxBayNum = maxBayNum;
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

	public Long getUnlockNum() {
		return unlockNum;
	}

	public void setUnlockNum(Long unlockNum) {
		this.unlockNum = unlockNum;
	}
	

	@JsonIgnore
	public CCyArea getCCyArea() {
		return this.CCyArea;
	}

	public void setCCyArea(CCyArea CCyArea) {
		this.CCyArea = CCyArea;
	}

}