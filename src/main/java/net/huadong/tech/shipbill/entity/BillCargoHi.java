package net.huadong.tech.shipbill.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the BILL_CARGO_HIS database table.
 * 
 */
@Entity
@Table(name="BILL_CARGO_HIS")
@NamedQuery(name="BillCargoHi.findAll", query="SELECT b FROM BillCargoHi b")
public class BillCargoHi implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private BillCargoHiPK id;

	@Column(name="CARGO_NAM")
	private String cargoNam;

	@Column(name="CARGO_PIECES")
	private BigDecimal cargoPieces;

	private BigDecimal height;

	@Column(name="\"LENGTH\"")
	private BigDecimal length;

	private String marks;

	@Column(name="REC_NAM")
	private String recNam;

	@Temporal(TemporalType.DATE)
	@Column(name="REC_TIM")
	private Date recTim;

	private String remarks;

	@Column(name="SHIPBILL_ID")
	private String shipbillId;

	@Column(name="TRADE_ID")
	private String tradeId;

	@Column(name="UPD_NAM")
	private String updNam;

	@Temporal(TemporalType.DATE)
	@Column(name="UPD_TIM")
	private Date updTim;

	@Column(name="\"VOLUMES\"")
	private BigDecimal volumes;

	private BigDecimal weights;

	private BigDecimal width;

	public BillCargoHi() {
	}

	public BillCargoHiPK getId() {
		return this.id;
	}

	public void setId(BillCargoHiPK id) {
		this.id = id;
	}

	public String getCargoNam() {
		return this.cargoNam;
	}

	public void setCargoNam(String cargoNam) {
		this.cargoNam = cargoNam;
	}

	public BigDecimal getCargoPieces() {
		return this.cargoPieces;
	}

	public void setCargoPieces(BigDecimal cargoPieces) {
		this.cargoPieces = cargoPieces;
	}

	public BigDecimal getHeight() {
		return this.height;
	}

	public void setHeight(BigDecimal height) {
		this.height = height;
	}

	public BigDecimal getLength() {
		return this.length;
	}

	public void setLength(BigDecimal length) {
		this.length = length;
	}

	public String getMarks() {
		return this.marks;
	}

	public void setMarks(String marks) {
		this.marks = marks;
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

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getShipbillId() {
		return this.shipbillId;
	}

	public void setShipbillId(String shipbillId) {
		this.shipbillId = shipbillId;
	}

	public String getTradeId() {
		return this.tradeId;
	}

	public void setTradeId(String tradeId) {
		this.tradeId = tradeId;
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

	public BigDecimal getVolumes() {
		return this.volumes;
	}

	public void setVolumes(BigDecimal volumes) {
		this.volumes = volumes;
	}

	public BigDecimal getWeights() {
		return this.weights;
	}

	public void setWeights(BigDecimal weights) {
		this.weights = weights;
	}

	public BigDecimal getWidth() {
		return this.width;
	}

	public void setWidth(BigDecimal width) {
		this.width = width;
	}

}