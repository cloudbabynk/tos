package net.huadong.tech.ship.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;

import net.huadong.tech.base.bean.AuditEntityBean;

import java.util.Date;


/**
 * The persistent class for the SHIP_REPORT database table.
 * 
 */
@Entity
@Table(name="SHIP_REPORT")
@NamedQuery(name="ShipReport.findAll", query="SELECT s FROM ShipReport s")
public class ShipReport extends AuditEntityBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="SHIP_RPT_ID")
	private String shipRptId;

	@Column(name="ADDRESS_TXT")
	private String addressTxt;


	@Column(name="E_VOYAGE")
	private String eVoyage;

	@Column(name="ETA_TIM")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
	private Timestamp etaTim;

	@Column(name="I_VOYAGE")
	private String iVoyage;

	@Column(name="RELATION_NAM")
	private String relationNam;

	@Column(name="REMARK_TXT")
	private String remarkTxt;

	@Column(name="SHIP_AGENT_COD")
	private String shipAgentCod;

	@Column(name="SHIP_COD_ID")
	private String shipCodId;

	@Column(name="SHIP_CORP_COD")
	private String shipCorpCod;

	@Column(name="SHIP_NAM")
	private String shipNam;

	private String telephon;

	@Column(name="TRADE_ID")
	private String tradeId;

	@Column(name="CARGO_NAM")
	private String cargoNam;
	
	@Column(name="CARGO_WGT")
	private BigDecimal cargoWgt;
	
	@Column(name="E_SHIP_NAM")
	private String eShipNam;
	
	@Column(name="REMARKS")
	private String remarks;
	
	@Column(name="TEN_DAYS")
	private String tenDays;
	
	@Column(name="I_E_ID")
	private String iEId;
	
	@Column(name="SHIP_LENGTH")
	private String shipLength;
	
	@Column(name="LINE_COD")
	private String lineCod;
	
	@Column(name="SHIP_COUNTRY")
	private String shipCountry;
	
	@Column(name="SHIP_WIDTH")
	private BigDecimal shipWidth;
	
	@Column(name="SHIP_WGT")
	private BigDecimal shipWgt;
	

	public ShipReport() {
	}

	public String getShipCountry() {
		return shipCountry;
	}

	public void setShipCountry(String shipCountry) {
		this.shipCountry = shipCountry;
	}

	public BigDecimal getShipWidth() {
		return shipWidth;
	}

	public void setShipWidth(BigDecimal shipWidth) {
		this.shipWidth = shipWidth;
	}

	public BigDecimal getShipWgt() {
		return shipWgt;
	}

	public void setShipWgt(BigDecimal shipWgt) {
		this.shipWgt = shipWgt;
	}

	public String getTenDays() {
		return tenDays;
	}

	public void setTenDays(String tenDays) {
		this.tenDays = tenDays;
	}

	public String geteVoyage() {
		return eVoyage;
	}

	public void seteVoyage(String eVoyage) {
		this.eVoyage = eVoyage;
	}

	public Timestamp getEtaTim() {
		return etaTim;
	}

	public void setEtaTim(Timestamp etaTim) {
		this.etaTim = etaTim;
	}

	public String getiVoyage() {
		return iVoyage;
	}

	public void setiVoyage(String iVoyage) {
		this.iVoyage = iVoyage;
	}

	public String getShipAgentCod() {
		return shipAgentCod;
	}

	public void setShipAgentCod(String shipAgentCod) {
		this.shipAgentCod = shipAgentCod;
	}

	public String getLineCod() {
		return lineCod;
	}

	public void setLineCod(String lineCod) {
		this.lineCod = lineCod;
	}

	public String getCargoNam() {
		return cargoNam;
	}

	public void setCargoNam(String cargoNam) {
		this.cargoNam = cargoNam;
	}

	public BigDecimal getCargoWgt() {
		return cargoWgt;
	}

	public void setCargoWgt(BigDecimal cargoWgt) {
		this.cargoWgt = cargoWgt;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getiEId() {
		return iEId;
	}

	public void setiEId(String iEId) {
		this.iEId = iEId;
	}

	public String getShipLength() {
		return shipLength;
	}

	public void setShipLength(String shipLength) {
		this.shipLength = shipLength;
	}

	public String getShipRptId() {
		return this.shipRptId;
	}

	public void setShipRptId(String shipRptId) {
		this.shipRptId = shipRptId;
	}

	public String geteShipNam() {
		return eShipNam;
	}

	public void seteShipNam(String eShipNam) {
		this.eShipNam = eShipNam;
	}

	public String getAddressTxt() {
		return this.addressTxt;
	}

	public void setAddressTxt(String addressTxt) {
		this.addressTxt = addressTxt;
	}


	public String getEVoyage() {
		return this.eVoyage;
	}

	public void setEVoyage(String eVoyage) {
		this.eVoyage = eVoyage;
	}


	public String getIVoyage() {
		return this.iVoyage;
	}

	public void setIVoyage(String iVoyage) {
		this.iVoyage = iVoyage;
	}

	public String getRelationNam() {
		return this.relationNam;
	}

	public void setRelationNam(String relationNam) {
		this.relationNam = relationNam;
	}

	public String getRemarkTxt() {
		return this.remarkTxt;
	}

	public void setRemarkTxt(String remarkTxt) {
		this.remarkTxt = remarkTxt;
	}


	public String getShipCodId() {
		return this.shipCodId;
	}

	public void setShipCodId(String shipCodId) {
		this.shipCodId = shipCodId;
	}

	public String getShipCorpCod() {
		return this.shipCorpCod;
	}

	public void setShipCorpCod(String shipCorpCod) {
		this.shipCorpCod = shipCorpCod;
	}

	public String getShipNam() {
		return this.shipNam;
	}

	public void setShipNam(String shipNam) {
		this.shipNam = shipNam;
	}

	public String getTelephon() {
		return this.telephon;
	}

	public void setTelephon(String telephon) {
		this.telephon = telephon;
	}

	public String getTradeId() {
		return this.tradeId;
	}

	public void setTradeId(String tradeId) {
		this.tradeId = tradeId;
	}
	

}