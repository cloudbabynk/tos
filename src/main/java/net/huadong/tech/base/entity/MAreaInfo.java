package net.huadong.tech.base.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.*;

import net.huadong.tech.base.bean.AuditEntityBean;

import java.util.Date;


/**
 * The persistent class for the C_DAMAGE database table.
 * 
 */
@Entity
@Table(name="M_AREA_INFO")
@NamedQuery(name="MAreaInfo.findAll", query="SELECT c FROM MAreaInfo c")
public class MAreaInfo extends AuditEntityBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID")
	private String id;

	@Column(name="AREA_INFO")
	private String areaInfo;
	
	@Column(name="AREA_NUM")
	private String areaNum;
	
	@Temporal(TemporalType.DATE)
	@Column(name="WORK_DTE")
	private Date workDte;
	
	@Column(name="BRAND_NAM")
	private String brandNam;

	@Column(name="PIECE_NUM")
	private BigDecimal pieceNum;


	public MAreaInfo() {
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public Date getWorkDte() {
		return workDte;
	}


	public void setWorkDte(Date workDte) {
		this.workDte = workDte;
	}


	public String getAreaInfo() {
		return areaInfo;
	}


	public void setAreaInfo(String areaInfo) {
		this.areaInfo = areaInfo;
	}


	public BigDecimal getPieceNum() {
		return pieceNum;
	}


	public void setPieceNum(BigDecimal pieceNum) {
		this.pieceNum = pieceNum;
	}


	public String getAreaNum() {
		return areaNum;
	}


	public void setAreaNum(String areaNum) {
		this.areaNum = areaNum;
	}


	public String getBrandNam() {
		return brandNam;
	}


	public void setBrandNam(String brandNam) {
		this.brandNam = brandNam;
	}


}