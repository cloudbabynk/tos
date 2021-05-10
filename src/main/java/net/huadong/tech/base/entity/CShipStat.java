package net.huadong.tech.base.entity;

import java.io.Serializable;
import javax.persistence.*;

import net.huadong.tech.base.bean.AuditEntityBean;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the C_SHIP_STAT database table.
 * 
 */
@Entity
@Table(name="C_SHIP_STAT")
@NamedQuery(name="CShipStat.findAll", query="SELECT c FROM CShipStat c")
public class CShipStat extends AuditEntityBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="SHIP_STAT_COD")
	private String shipStatCod;

	@Column(name="SHIP_STAT_NAM")
	private String shipStatNam;

	@Column(name="SHIP_STAT_SEQ")
	private BigDecimal shipStatSeq;

	@Column(name="SHIP_STAT_TYP")
	private String shipStatTyp;
	
	@Column(name="SHIP_STAT_SHORT")
	private String shipStatShort;

	public CShipStat() {
	}

	public String getShipStatShort() {
		return shipStatShort;
	}

	public void setShipStatShort(String shipStatShort) {
		this.shipStatShort = shipStatShort;
	}

	public String getShipStatCod() {
		return this.shipStatCod;
	}

	public void setShipStatCod(String shipStatCod) {
		this.shipStatCod = shipStatCod;
	}

	public String getShipStatNam() {
		return this.shipStatNam;
	}

	public void setShipStatNam(String shipStatNam) {
		this.shipStatNam = shipStatNam;
	}

	public BigDecimal getShipStatSeq() {
		return this.shipStatSeq;
	}

	public void setShipStatSeq(BigDecimal shipStatSeq) {
		this.shipStatSeq = shipStatSeq;
	}

	public String getShipStatTyp() {
		return this.shipStatTyp;
	}

	public void setShipStatTyp(String shipStatTyp) {
		this.shipStatTyp = shipStatTyp;
	}

}