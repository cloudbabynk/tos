package net.huadong.tech.base.entity;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import net.huadong.tech.base.bean.AuditEntityBean;

import java.math.BigDecimal;


/**
 * The persistent class for the C_CABLE database table.
 * 
 */
@Entity
@Table(name="C_CABLE")
@NamedQuery(name="CCable.findAll", query="SELECT c FROM CCable c")
public class CCable implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CABLE_COD")
	private String cableCod;
	
	@Column(name="CABLE_SEQ")
	private long cableSeq;

	@Column(name="CABLE_NO")
	private String cableNo;

	@Column(name="DISTANCE")
	private BigDecimal distance;

	@Column(name="REMARKS")
	private String remarks;

	@Column(name="X")
	private String x;

	@Column(name="Y")
	private String y;
	
	@Column(name="BERTH_COD")
	private String berthCod;
	
	@Column(name="DOCK_COD")
	private String dockCod;
	
	@Transient
	private String berthCodNam;

	//bi-directional many-to-one association to CBerth
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="BERTH_COD", insertable = false, updatable = false)
	private CBerth CBerth;

	public CCable() {
	}

	@JsonIgnore
	public CBerth getCBerth() {
		return CBerth;
	}

	public void setCBerth(CBerth cBerth) {
		CBerth = cBerth;
	}

	public long getCableSeq() {
		return this.cableSeq;
	}

	public void setCableSeq(long cableSeq) {
		this.cableSeq = cableSeq;
	}

	public String getBerthCodNam() {
		return berthCodNam;
	}

	public void setBerthCodNam(String berthCodNam) {
		this.berthCodNam = berthCodNam;
	}

	public String getCableNo() {
		return this.cableNo;
	}

	public void setCableNo(String cableNo) {
		this.cableNo = cableNo;
	}

	public BigDecimal getDistance() {
		return this.distance;
	}

	public void setDistance(BigDecimal distance) {
		this.distance = distance;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}


	
	public String getX() {
		return x;
	}

	public void setX(String x) {
		this.x = x;
	}

	public String getY() {
		return y;
	}

	public void setY(String y) {
		this.y = y;
	}

	public String getBerthCod() {
		return this.berthCod;
	}

	public void setBerthCod(String berthCod) {
		this.berthCod = berthCod;
	}

	public String getCableCod() {
		return cableCod;
	}

	public void setCableCod(String cableCod) {
		this.cableCod = cableCod;
	}

	public String getDockCod() {
		return dockCod;
	}

	public void setDockCod(String dockCod) {
		this.dockCod = dockCod;
	}


}