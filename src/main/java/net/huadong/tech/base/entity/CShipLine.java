package net.huadong.tech.base.entity;

import java.io.Serializable;
import javax.persistence.*;

import net.huadong.tech.base.bean.AuditEntityBean;

import java.util.Date;


/**
 * The persistent class for the C_SHIP_LINE database table.
 * 
 */
@Entity
@Table(name="C_SHIP_LINE")
@NamedQuery(name="CShipLine.findAll", query="SELECT c FROM CShipLine c")
public class CShipLine extends AuditEntityBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="SHIP_LINE_COD")
	private String shipLineCod;

	@Column(name="C_SHIP_LINE_NAM")
	private String cShipLineNam;

	@Column(name="E_SHIP_LINE_NAM")
	private String eShipLineNam;

	@Column(name="LINE_ID")
	private String lineId;

	@Column(name="MAIN_STEM")
	private String mainStem;

	@Column(name="OCEAN_ID")
	private String oceanId;

	
	@Column(name="SHIP_LINE_NAM")
	private String shipLineNam;

	@Column(name="TRADE_ID")
	private String tradeId;

	public CShipLine() {
	}

	public String getShipLineCod() {
		return this.shipLineCod;
	}

	public void setShipLineCod(String shipLineCod) {
		this.shipLineCod = shipLineCod;
	}

	public String getCShipLineNam() {
		return this.cShipLineNam;
	}

	public void setCShipLineNam(String cShipLineNam) {
		this.cShipLineNam = cShipLineNam;
	}

	public String getEShipLineNam() {
		return this.eShipLineNam;
	}

	public void setEShipLineNam(String eShipLineNam) {
		this.eShipLineNam = eShipLineNam;
	}

	public String getLineId() {
		return this.lineId;
	}

	public void setLineId(String lineId) {
		this.lineId = lineId;
	}

	public String getMainStem() {
		return this.mainStem;
	}

	public void setMainStem(String mainStem) {
		this.mainStem = mainStem;
	}

	public String getOceanId() {
		return this.oceanId;
	}

	public void setOceanId(String oceanId) {
		this.oceanId = oceanId;
	}

	

	public String getShipLineNam() {
		return this.shipLineNam;
	}

	public void setShipLineNam(String shipLineNam) {
		this.shipLineNam = shipLineNam;
	}

	public String getTradeId() {
		return this.tradeId;
	}

	public void setTradeId(String tradeId) {
		this.tradeId = tradeId;
	}

	

}