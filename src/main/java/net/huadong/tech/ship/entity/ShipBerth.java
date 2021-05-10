package net.huadong.tech.ship.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the SHIP_BERTH database table.
 * 
 */
@Entity
@Table(name="SHIP_BERTH")
@NamedQuery(name="ShipBerth.findAll", query="SELECT s FROM ShipBerth s")
public class ShipBerth implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="SHIP_BERTH_ID")
	private String shipBerthId;

	@Column(name="BEG_CAB_SEQ")
	private BigDecimal begCabSeq;

	@Temporal(TemporalType.DATE)
	@Column(name="BERTH_BEG_TIM")
	private Date berthBegTim;

	@Column(name="BERTH_COD")
	private String berthCod;

	@Temporal(TemporalType.DATE)
	@Column(name="BERTH_END_TIM")
	private Date berthEndTim;

	@Column(name="END_CAB_SEQ")
	private BigDecimal endCabSeq;

	@Column(name="ORI_BERTH_COD")
	private String oriBerthCod;

	@Column(name="REC_NAM")
	private String recNam;

	@Temporal(TemporalType.DATE)
	@Column(name="REC_TIM")
	private Date recTim;

	@Column(name="SEQ_NO")
	private BigDecimal seqNo;

	@Column(name="SHIP_NO")
	private String shipNo;

	@Column(name="UPD_NAM")
	private String updNam;

	@Temporal(TemporalType.DATE)
	@Column(name="UPD_TIM")
	private Date updTim;

	public ShipBerth() {
	}

	public String getShipBerthId() {
		return this.shipBerthId;
	}

	public void setShipBerthId(String shipBerthId) {
		this.shipBerthId = shipBerthId;
	}

	public BigDecimal getBegCabSeq() {
		return this.begCabSeq;
	}

	public void setBegCabSeq(BigDecimal begCabSeq) {
		this.begCabSeq = begCabSeq;
	}

	public Date getBerthBegTim() {
		return this.berthBegTim;
	}

	public void setBerthBegTim(Date berthBegTim) {
		this.berthBegTim = berthBegTim;
	}

	public String getBerthCod() {
		return this.berthCod;
	}

	public void setBerthCod(String berthCod) {
		this.berthCod = berthCod;
	}

	public Date getBerthEndTim() {
		return this.berthEndTim;
	}

	public void setBerthEndTim(Date berthEndTim) {
		this.berthEndTim = berthEndTim;
	}

	public BigDecimal getEndCabSeq() {
		return this.endCabSeq;
	}

	public void setEndCabSeq(BigDecimal endCabSeq) {
		this.endCabSeq = endCabSeq;
	}

	public String getOriBerthCod() {
		return this.oriBerthCod;
	}

	public void setOriBerthCod(String oriBerthCod) {
		this.oriBerthCod = oriBerthCod;
	}

	public String getRecNam() {
		return this.recNam;
	}

	public void setRecNam(String recNam) {
		this.recNam = recNam;
	}

	public Date getRecTim() {
		return this.recTim;
	}

	public void setRecTim(Date recTim) {
		this.recTim = recTim;
	}

	public BigDecimal getSeqNo() {
		return this.seqNo;
	}

	public void setSeqNo(BigDecimal seqNo) {
		this.seqNo = seqNo;
	}

	public String getShipNo() {
		return this.shipNo;
	}

	public void setShipNo(String shipNo) {
		this.shipNo = shipNo;
	}

	public String getUpdNam() {
		return this.updNam;
	}

	public void setUpdNam(String updNam) {
		this.updNam = updNam;
	}

	public Date getUpdTim() {
		return this.updTim;
	}

	public void setUpdTim(Date updTim) {
		this.updTim = updTim;
	}

}