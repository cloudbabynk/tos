package net.huadong.tech.Interface.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the V_GROUP_CORP_SHIP database table.
 * 
 */
@Entity
@Table(name="V_GROUP_CORP_SHIP")
@NamedQuery(name="VGroupCorpShip.findAll", query="SELECT v FROM VGroupCorpShip v")
public class VGroupCorpShip implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="CAR_NUM")
	private BigDecimal carNum;

	@Column(name="CARGO_CODE")
	private String cargoCode;

	@Column(name="CARGO_NUM")
	private BigDecimal cargoNum;

	@Column(name="CARGO_WGT")
	private BigDecimal cargoWgt;

	@Column(name="CNTR_NUM")
	private BigDecimal cntrNum;

	@Column(name="CONSIGN_CODE")
	private String consignCode;

	@Column(name="FORWARDER_CODE")
	private String forwarderCode;

	@Column(name="IE_FLAG")
	private String ieFlag;

	@Column(name="PILOT_FLAG")
	private String pilotFlag;

	@Column(name="SHIP_AGENT_CODE")
	private String shipAgentCode;

	@Id
	@Column(name="SHIP_ID")
	private String shipId;

	@Column(name="SHIP_LINE_CODE")
	private String shipLineCode;

	@Column(name="SHIP_NAME")
	private String shipName;

	@Column(name="STDCAR_NUM")
	private BigDecimal stdcarNum;

	@Column(name="SVOYAGE_ID")
	private String svoyageId;

	@Column(name="TEU_NUM")
	private BigDecimal teuNum;

	@Column(name="TRADE_FLAG")
	private String tradeFlag;

	@Column(name="TUG_NUM")
	private BigDecimal tugNum;

	@Column(name="VISIT_NUM")
	private BigDecimal visitNum;

	private String voyage;

	public VGroupCorpShip() {
	}

	public BigDecimal getCarNum() {
		return this.carNum;
	}

	public void setCarNum(BigDecimal carNum) {
		this.carNum = carNum;
	}

	public String getCargoCode() {
		return this.cargoCode;
	}

	public void setCargoCode(String cargoCode) {
		this.cargoCode = cargoCode;
	}

	public BigDecimal getCargoNum() {
		return this.cargoNum;
	}

	public void setCargoNum(BigDecimal cargoNum) {
		this.cargoNum = cargoNum;
	}

	public BigDecimal getCargoWgt() {
		return this.cargoWgt;
	}

	public void setCargoWgt(BigDecimal cargoWgt) {
		this.cargoWgt = cargoWgt;
	}

	public BigDecimal getCntrNum() {
		return this.cntrNum;
	}

	public void setCntrNum(BigDecimal cntrNum) {
		this.cntrNum = cntrNum;
	}

	public String getConsignCode() {
		return this.consignCode;
	}

	public void setConsignCode(String consignCode) {
		this.consignCode = consignCode;
	}

	public String getForwarderCode() {
		return this.forwarderCode;
	}

	public void setForwarderCode(String forwarderCode) {
		this.forwarderCode = forwarderCode;
	}

	public String getIeFlag() {
		return this.ieFlag;
	}

	public void setIeFlag(String ieFlag) {
		this.ieFlag = ieFlag;
	}

	public String getPilotFlag() {
		return this.pilotFlag;
	}

	public void setPilotFlag(String pilotFlag) {
		this.pilotFlag = pilotFlag;
	}

	public String getShipAgentCode() {
		return this.shipAgentCode;
	}

	public void setShipAgentCode(String shipAgentCode) {
		this.shipAgentCode = shipAgentCode;
	}

	public String getShipId() {
		return this.shipId;
	}

	public void setShipId(String shipId) {
		this.shipId = shipId;
	}

	public String getShipLineCode() {
		return this.shipLineCode;
	}

	public void setShipLineCode(String shipLineCode) {
		this.shipLineCode = shipLineCode;
	}

	public String getShipName() {
		return this.shipName;
	}

	public void setShipName(String shipName) {
		this.shipName = shipName;
	}

	public BigDecimal getStdcarNum() {
		return this.stdcarNum;
	}

	public void setStdcarNum(BigDecimal stdcarNum) {
		this.stdcarNum = stdcarNum;
	}

	public String getSvoyageId() {
		return this.svoyageId;
	}

	public void setSvoyageId(String svoyageId) {
		this.svoyageId = svoyageId;
	}

	public BigDecimal getTeuNum() {
		return this.teuNum;
	}

	public void setTeuNum(BigDecimal teuNum) {
		this.teuNum = teuNum;
	}

	public String getTradeFlag() {
		return this.tradeFlag;
	}

	public void setTradeFlag(String tradeFlag) {
		this.tradeFlag = tradeFlag;
	}

	public BigDecimal getTugNum() {
		return this.tugNum;
	}

	public void setTugNum(BigDecimal tugNum) {
		this.tugNum = tugNum;
	}

	public BigDecimal getVisitNum() {
		return this.visitNum;
	}

	public void setVisitNum(BigDecimal visitNum) {
		this.visitNum = visitNum;
	}

	public String getVoyage() {
		return this.voyage;
	}

	public void setVoyage(String voyage) {
		this.voyage = voyage;
	}

}