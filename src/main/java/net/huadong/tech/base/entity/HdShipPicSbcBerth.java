package net.huadong.tech.base.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the C_BERTH database table.
 * 
 */
@Entity
@Table(name="C_BERTH")
@NamedQuery(name="CBerth.findAll", query="SELECT c FROM CBerth c")
public class HdShipPicSbcBerth implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="BERTH_COD")
	private String berthCode;

	@Column(name="BEG_METER")
	private String begMeter;

	@Column(name="BEGIN_X")
	private BigDecimal beginX;
	
	@Column(name="BEGIN_Y")
	private BigDecimal beginY;

	@Column(name="BERTH_DEPTH")
	private BigDecimal berthDepth;

	@Column(name="BERTH_HIGH")
	private BigDecimal berthHigh;

	@Column(name="BERTH_LONG")
	private BigDecimal berthLong;

	@Column(name="BERTH_NAM")
	private String berthName;

	@Column(name="BERTH_TYP")
	private String berthTyp;

	@Column(name="CABLE_NUM")
	private BigDecimal cableNum;

	@Column(name="DISP_SEQ")
	private BigDecimal dispSeq;

	@Column(name="DOCK_COD")
	private String dockCod;

	@Column(name="END_METER")
	private String endMeter;

	@Column(name="END_X")
	private BigDecimal endX;

	@Column(name="END_Y")
	private BigDecimal endY;

	private String remarks;
	
	@Transient
	private String dockCodNam;

	//bi-directional many-to-one association to CCable
//	@OneToMany(mappedBy="CBerth")
//	private List<CCable> CCables;

	public HdShipPicSbcBerth() {
	}


	public String getBerthCode() {
		return berthCode;
	}


	public void setBerthCode(String berthCode) {
		this.berthCode = berthCode;
	}


	public String getBerthName() {
		return berthName;
	}


	public void setBerthName(String berthName) {
		this.berthName = berthName;
	}


	public String getDockCodNam() {
		return dockCodNam;
	}

	public void setDockCodNam(String dockCodNam) {
		this.dockCodNam = dockCodNam;
	}

	public String getBegMeter() {
		return this.begMeter;
	}

	public void setBegMeter(String begMeter) {
		this.begMeter = begMeter;
	}

	public BigDecimal getBeginX() {
		return this.beginX;
	}

	public void setBeginX(BigDecimal beginX) {
		this.beginX = beginX;
	}

	public BigDecimal getBeginY() {
		return this.beginY;
	}

	public void setBeginY(BigDecimal beginY) {
		this.beginY = beginY;
	}

	public BigDecimal getBerthDepth() {
		return this.berthDepth;
	}

	public void setBerthDepth(BigDecimal berthDepth) {
		this.berthDepth = berthDepth;
	}

	public BigDecimal getBerthHigh() {
		return this.berthHigh;
	}

	public void setBerthHigh(BigDecimal berthHigh) {
		this.berthHigh = berthHigh;
	}

	public BigDecimal getBerthLong() {
		return this.berthLong;
	}

	public void setBerthLong(BigDecimal berthLong) {
		this.berthLong = berthLong;
	}


	public String getBerthTyp() {
		return this.berthTyp;
	}

	public void setBerthTyp(String berthTyp) {
		this.berthTyp = berthTyp;
	}

	public BigDecimal getCableNum() {
		return this.cableNum;
	}

	public void setCableNum(BigDecimal cableNum) {
		this.cableNum = cableNum;
	}

	public BigDecimal getDispSeq() {
		return this.dispSeq;
	}

	public void setDispSeq(BigDecimal dispSeq) {
		this.dispSeq = dispSeq;
	}

	public String getDockCod() {
		return this.dockCod;
	}

	public void setDockCod(String dockCod) {
		this.dockCod = dockCod;
	}

	public String getEndMeter() {
		return this.endMeter;
	}

	public void setEndMeter(String endMeter) {
		this.endMeter = endMeter;
	}

	public BigDecimal getEndX() {
		return this.endX;
	}

	public void setEndX(BigDecimal endX) {
		this.endX = endX;
	}

	public BigDecimal getEndY() {
		return this.endY;
	}

	public void setEndY(BigDecimal endY) {
		this.endY = endY;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

//	public List<CCable> getCCables() {
//		return this.CCables;
//	}
//
//	public void setCCables(List<CCable> CCables) {
//		this.CCables = CCables;
//	}

//	public CCable addCCable(CCable CCable) {
//		getCCables().add(CCable);
//		CCable.setCBerth(this);
//
//		return CCable;
//	}
//
//	public CCable removeCCable(CCable CCable) {
//		getCCables().remove(CCable);
//		CCable.setCBerth(null);
//
//		return CCable;
//	}

}