package net.huadong.tech.Interface.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the V_GROUP_CORP_SHIP_TEAM database table.
 * 
 */
@Entity
@Table(name="V_GROUP_CORP_SHIP_TEAM")
@NamedQuery(name="VGroupCorpShipTeam.findAll", query="SELECT v FROM VGroupCorpShipTeam v")
public class VGroupCorpShipTeam implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="BERTH_CODE")
	private String berthCode;

	@Column(name="CAR_NUM")
	private BigDecimal carNum;

	@Column(name="CARGO_KIND")
	private String cargoKind;

	@Column(name="CNTR_NUM")
	private BigDecimal cntrNum;

	private BigDecimal dad;

	private String description;

	private BigDecimal dfd;

	@Column(name="END_FLAG")
	private String endFlag;

	@Temporal(TemporalType.DATE)
	@Column(name="END_TIME")
	private Date endTime;

	@Column(name="IE_FLAG")
	private String ieFlag;

	@Column(name="PLAN_HOUR")
	private BigDecimal planHour;

	@Column(name="PRE_TTL_FLAG")
	private String preTtlFlag;

	@Temporal(TemporalType.DATE)
	@Column(name="QUICK_DATE")
	private Date quickDate;

	@Column(name="QUICK_FLAG")
	private String quickFlag;

	@Column(name="SHIP_ID")
	private String shipId;

	@Column(name="SHIP_NAME")
	private String shipName;

	@Temporal(TemporalType.DATE)
	@Column(name="START_TIME")
	private Date startTime;

	@Column(name="STDCAR_NUM")
	private BigDecimal stdcarNum;

	@Id
	@Column(name="STEAM_ID")
	private String steamId;

	@Column(name="SVOYAGE_ID")
	private String svoyageId;

	@Column(name="TEAM_ORGN_ID")
	private String teamOrgnId;

	@Column(name="TEU_NUM")
	private BigDecimal teuNum;

	@Column(name="THRUPUT_FLAG")
	private String thruputFlag;

	@Column(name="THRUPUT_NAME")
	private String thruputName;

	@Temporal(TemporalType.DATE)
	@Column(name="THRUPUT_TIME")
	private Date thruputTime;

	@Column(name="TRADE_FLAG")
	private String tradeFlag;

	@Column(name="WORK_WGT")
	private BigDecimal workWgt;

	public VGroupCorpShipTeam() {
	}

	public String getBerthCode() {
		return this.berthCode;
	}

	public void setBerthCode(String berthCode) {
		this.berthCode = berthCode;
	}

	public BigDecimal getCarNum() {
		return this.carNum;
	}

	public void setCarNum(BigDecimal carNum) {
		this.carNum = carNum;
	}

	public String getCargoKind() {
		return this.cargoKind;
	}

	public void setCargoKind(String cargoKind) {
		this.cargoKind = cargoKind;
	}

	public BigDecimal getCntrNum() {
		return this.cntrNum;
	}

	public void setCntrNum(BigDecimal cntrNum) {
		this.cntrNum = cntrNum;
	}

	public BigDecimal getDad() {
		return this.dad;
	}

	public void setDad(BigDecimal dad) {
		this.dad = dad;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getDfd() {
		return this.dfd;
	}

	public void setDfd(BigDecimal dfd) {
		this.dfd = dfd;
	}

	public String getEndFlag() {
		return this.endFlag;
	}

	public void setEndFlag(String endFlag) {
		this.endFlag = endFlag;
	}

	public Date getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getIeFlag() {
		return this.ieFlag;
	}

	public void setIeFlag(String ieFlag) {
		this.ieFlag = ieFlag;
	}

	public BigDecimal getPlanHour() {
		return this.planHour;
	}

	public void setPlanHour(BigDecimal planHour) {
		this.planHour = planHour;
	}

	public String getPreTtlFlag() {
		return this.preTtlFlag;
	}

	public void setPreTtlFlag(String preTtlFlag) {
		this.preTtlFlag = preTtlFlag;
	}

	public Date getQuickDate() {
		return this.quickDate;
	}

	public void setQuickDate(Date quickDate) {
		this.quickDate = quickDate;
	}

	public String getQuickFlag() {
		return this.quickFlag;
	}

	public void setQuickFlag(String quickFlag) {
		this.quickFlag = quickFlag;
	}

	public String getShipId() {
		return this.shipId;
	}

	public void setShipId(String shipId) {
		this.shipId = shipId;
	}

	public String getShipName() {
		return this.shipName;
	}

	public void setShipName(String shipName) {
		this.shipName = shipName;
	}

	public Date getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public BigDecimal getStdcarNum() {
		return this.stdcarNum;
	}

	public void setStdcarNum(BigDecimal stdcarNum) {
		this.stdcarNum = stdcarNum;
	}

	public String getSteamId() {
		return this.steamId;
	}

	public void setSteamId(String steamId) {
		this.steamId = steamId;
	}

	public String getSvoyageId() {
		return this.svoyageId;
	}

	public void setSvoyageId(String svoyageId) {
		this.svoyageId = svoyageId;
	}

	public String getTeamOrgnId() {
		return this.teamOrgnId;
	}

	public void setTeamOrgnId(String teamOrgnId) {
		this.teamOrgnId = teamOrgnId;
	}

	public BigDecimal getTeuNum() {
		return this.teuNum;
	}

	public void setTeuNum(BigDecimal teuNum) {
		this.teuNum = teuNum;
	}

	public String getThruputFlag() {
		return this.thruputFlag;
	}

	public void setThruputFlag(String thruputFlag) {
		this.thruputFlag = thruputFlag;
	}

	public String getThruputName() {
		return this.thruputName;
	}

	public void setThruputName(String thruputName) {
		this.thruputName = thruputName;
	}

	public Date getThruputTime() {
		return this.thruputTime;
	}

	public void setThruputTime(Date thruputTime) {
		this.thruputTime = thruputTime;
	}

	public String getTradeFlag() {
		return this.tradeFlag;
	}

	public void setTradeFlag(String tradeFlag) {
		this.tradeFlag = tradeFlag;
	}

	public BigDecimal getWorkWgt() {
		return this.workWgt;
	}

	public void setWorkWgt(BigDecimal workWgt) {
		this.workWgt = workWgt;
	}

}