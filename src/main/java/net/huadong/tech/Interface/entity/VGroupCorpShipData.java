package net.huadong.tech.Interface.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the V_GROUP_CORP_SHIP_DATA database table.
 * 
 */
@Entity
@Table(name="V_GROUP_CORP_SHIP_DATA")
@NamedQuery(name="VGroupCorpShipData.findAll", query="SELECT v FROM VGroupCorpShipData v")
public class VGroupCorpShipData implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="BAY_NUM")
	private BigDecimal bayNum;

	@Temporal(TemporalType.DATE)
	@Column(name="BUILD_DATE")
	private Date buildDate;

	@Column(name="CABIN_SIZE")
	private String cabinSize;

	@Column(name="CALL_SIGN")
	private String callSign;

	@Column(name="COUNTRY_CODE")
	private String countryCode;

	private String description;

	private BigDecimal ead;

	private BigDecimal efd;

	@Column(name="EN_SHIP_NAME")
	private String enShipName;

	private BigDecimal fad;

	private BigDecimal ffd;

	@Column(name="HATCH_NUM")
	private BigDecimal hatchNum;

	private String imo;

	@Column(name="MAIN_POWER")
	private BigDecimal mainPower;

	private String mmsi;

	@Id
	@Column(name="SDATA_ID")
	private String sdataId;

	private BigDecimal sdw;

	private BigDecimal sgt;

	@Column(name="SHIP_AGENT_CODE")
	private String shipAgentCode;

	
	@Column(name="SHIP_CODE")
	private String shipCode;

	@Column(name="SHIP_LENGTH")
	private BigDecimal shipLength;

	@Column(name="SHIP_NAME")
	private String shipName;

	@Column(name="SHIP_SPEED")
	private BigDecimal shipSpeed;

	@Column(name="SHIP_TRADE_CODE")
	private String shipTradeCode;

	@Column(name="SHIP_TYPE_CODE")
	private String shipTypeCode;

	@Column(name="SHIP_WIDTH")
	private BigDecimal shipWidth;

	@Column(name="SHIPLINE_CODE")
	private String shiplineCode;

	private BigDecimal snt;

	@Column(name="TOTAL_TEU")
	private BigDecimal totalTeu;

	@Column(name="TYPE_DEEP")
	private BigDecimal typeDeep;

	public VGroupCorpShipData() {
	}

	public BigDecimal getBayNum() {
		return this.bayNum;
	}

	public void setBayNum(BigDecimal bayNum) {
		this.bayNum = bayNum;
	}

	public Date getBuildDate() {
		return this.buildDate;
	}

	public void setBuildDate(Date buildDate) {
		this.buildDate = buildDate;
	}

	public String getCabinSize() {
		return this.cabinSize;
	}

	public void setCabinSize(String cabinSize) {
		this.cabinSize = cabinSize;
	}

	public String getCallSign() {
		return this.callSign;
	}

	public void setCallSign(String callSign) {
		this.callSign = callSign;
	}

	public String getCountryCode() {
		return this.countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getEad() {
		return this.ead;
	}

	public void setEad(BigDecimal ead) {
		this.ead = ead;
	}

	public BigDecimal getEfd() {
		return this.efd;
	}

	public void setEfd(BigDecimal efd) {
		this.efd = efd;
	}

	public String getEnShipName() {
		return this.enShipName;
	}

	public void setEnShipName(String enShipName) {
		this.enShipName = enShipName;
	}

	public BigDecimal getFad() {
		return this.fad;
	}

	public void setFad(BigDecimal fad) {
		this.fad = fad;
	}

	public BigDecimal getFfd() {
		return this.ffd;
	}

	public void setFfd(BigDecimal ffd) {
		this.ffd = ffd;
	}

	public BigDecimal getHatchNum() {
		return this.hatchNum;
	}

	public void setHatchNum(BigDecimal hatchNum) {
		this.hatchNum = hatchNum;
	}

	public String getImo() {
		return this.imo;
	}

	public void setImo(String imo) {
		this.imo = imo;
	}

	public BigDecimal getMainPower() {
		return this.mainPower;
	}

	public void setMainPower(BigDecimal mainPower) {
		this.mainPower = mainPower;
	}

	public String getMmsi() {
		return this.mmsi;
	}

	public void setMmsi(String mmsi) {
		this.mmsi = mmsi;
	}

	public String getSdataId() {
		return this.sdataId;
	}

	public void setSdataId(String sdataId) {
		this.sdataId = sdataId;
	}

	public BigDecimal getSdw() {
		return this.sdw;
	}

	public void setSdw(BigDecimal sdw) {
		this.sdw = sdw;
	}

	public BigDecimal getSgt() {
		return this.sgt;
	}

	public void setSgt(BigDecimal sgt) {
		this.sgt = sgt;
	}

	public String getShipAgentCode() {
		return this.shipAgentCode;
	}

	public void setShipAgentCode(String shipAgentCode) {
		this.shipAgentCode = shipAgentCode;
	}

	public String getShipCode() {
		return this.shipCode;
	}

	public void setShipCode(String shipCode) {
		this.shipCode = shipCode;
	}

	public BigDecimal getShipLength() {
		return this.shipLength;
	}

	public void setShipLength(BigDecimal shipLength) {
		this.shipLength = shipLength;
	}

	public String getShipName() {
		return this.shipName;
	}

	public void setShipName(String shipName) {
		this.shipName = shipName;
	}

	public BigDecimal getShipSpeed() {
		return this.shipSpeed;
	}

	public void setShipSpeed(BigDecimal shipSpeed) {
		this.shipSpeed = shipSpeed;
	}

	public String getShipTradeCode() {
		return this.shipTradeCode;
	}

	public void setShipTradeCode(String shipTradeCode) {
		this.shipTradeCode = shipTradeCode;
	}

	public String getShipTypeCode() {
		return this.shipTypeCode;
	}

	public void setShipTypeCode(String shipTypeCode) {
		this.shipTypeCode = shipTypeCode;
	}

	public BigDecimal getShipWidth() {
		return this.shipWidth;
	}

	public void setShipWidth(BigDecimal shipWidth) {
		this.shipWidth = shipWidth;
	}

	public String getShiplineCode() {
		return this.shiplineCode;
	}

	public void setShiplineCode(String shiplineCode) {
		this.shiplineCode = shiplineCode;
	}

	public BigDecimal getSnt() {
		return this.snt;
	}

	public void setSnt(BigDecimal snt) {
		this.snt = snt;
	}

	public BigDecimal getTotalTeu() {
		return this.totalTeu;
	}

	public void setTotalTeu(BigDecimal totalTeu) {
		this.totalTeu = totalTeu;
	}

	public BigDecimal getTypeDeep() {
		return this.typeDeep;
	}

	public void setTypeDeep(BigDecimal typeDeep) {
		this.typeDeep = typeDeep;
	}

}