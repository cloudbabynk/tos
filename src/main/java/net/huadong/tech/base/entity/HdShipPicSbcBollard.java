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
public class HdShipPicSbcBollard implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CABLE_COD")
	private String bollardCode;
	
	@Column(name="CABLE_SEQ")
	private long cableSeq;

	@Column(name="CABLE_NO")
	private String bollardName;

	@Column(name="DISTANCE")
	private BigDecimal bollardDistance;

	@Column(name="REMARKS")
	private String berthName;

	@Column(name="X")
	private String x;

	@Column(name="Y")
	private String y;
	
	@Column(name="BERTH_COD")
	private String berthCode;
	
	@Column(name="DOCK_COD")
	private String dockCod;
	
	@Transient
	private String berthCodNam;


	public HdShipPicSbcBollard() {
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


	public BigDecimal getBollardDistance() {
		return bollardDistance;
	}


	public void setBollardDistance(BigDecimal bollardDistance) {
		this.bollardDistance = bollardDistance;
	}


	public String getBollardCode() {
		return bollardCode;
	}

	public void setBollardCode(String bollardCode) {
		this.bollardCode = bollardCode;
	}

	public String getBollardName() {
		return bollardName;
	}

	public void setBollardName(String bollardName) {
		this.bollardName = bollardName;
	}

	public String getBerthName() {
		return berthName;
	}

	public void setBerthName(String berthName) {
		this.berthName = berthName;
	}

	public String getBerthCode() {
		return berthCode;
	}

	public void setBerthCode(String berthCode) {
		this.berthCode = berthCode;
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


	public String getDockCod() {
		return dockCod;
	}

	public void setDockCod(String dockCod) {
		this.dockCod = dockCod;
	}


}