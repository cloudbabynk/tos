package net.huadong.tech.Interface.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.*;

import org.apache.commons.net.ntp.TimeStamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import net.huadong.tech.base.bean.AuditEntityBean;

import java.util.Date;


/**
 * The persistent class for the C_BRAND database table.
 * 
 */
@Entity
@Table(name="SHIP_TEAM")
@NamedQuery(name="ShipTeam.findAll", query="SELECT c FROM ShipTeam c")
public class ShipTeam extends AuditEntityBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="STEAM_ID")
	private String steamId;
	
	@Column(name="SHIP_ID")
	private String shipId;
	
	@Column(name="IE_FLAG")
	private String ieFlag;
	
	@Column(name="TRADE_FLAG")
	private String tradeFlag;
	
	@Column(name="TEAM_ORGN_ID")
	private String teamOrgnId;
	
	@Column(name="RTD")
	private String rtd;
	
	@Column(name="SHIP_STAT_FLAG")
	private String shipStatFlag;
	
	@Column(name="START_TIME")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm") 
	private Timestamp startTime;
	
	@Column(name="END_TIME")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm") 
	private Timestamp endTime;
	
	@Column(name="WORK_WGT")
	private String workWgt;
	
	@Column(name="CNTR_NUM")
	private String cntrNum;
	
	@Column(name="TEU_NUM")
	private String teuNum;
	
	@Column(name="CAR_NUM")
	private String carNum;
	
	@Column(name="STDCAR_NUM")
	private String stdcarNum;
	
	@Column(name="PLAN_HOUR")
	private String planHour;
	
	@Column(name="CARGO_KIND")
	private String cargoKind;
	
	@Column(name="DESCRIPTION")
	private String description;
	
	@Column(name="PRE_TTL_FLAG")
	private String preTtlFlag;
	
	@Column(name="THRUPUT_FLAG")
	private String thruputFlag;
	
	@Column(name="THRUPUT_TIME")
	private String thruputTime;
	
	@Column(name="THRUPUT_NAME")
	private String thruputName;
	
	@Column(name="ORGN_ID")
	private String orgnId;
	
	@Column(name="END_FLAG")
	private String endFlag;
	
	@Column(name="QUICK_FLAG")
	private String quickFlag;
	
	@Column(name="QUICK_DATE")
	private String quickDate;
	
	@Column(name="DFD")
	private String dfd;
	
	@Column(name="DAD")
	private String dad;
	
	@Column(name="SUBMIT_FLAG")
	private String submitFlag;
	
	@Column(name="SUBMIT_NAME")
	private String submitName;
	
	@Column(name="SUBMIT_TIME")
	private String submitTime;
	
	@Column(name="CHECK_FLAG")
	private String checkFlag;
	
	@Column(name="CHECK_NAME")
	private String checkName;
	
	@Column(name="CHECK_TIME")
	private String checkTime;
	
	@Column(name="LOCK_FLAG")
	private String lockFlag;
	
	@Column(name="AUTO_LOCK_FLAG")
	private String autoLockFlag;
	
	@Column(name="PRE_TTL_DATE")
	private String preTtlDate;
	
	@Column(name="PRE_MISS_FLAG")
	private String preMissFlag;
	
	@Column(name="PRE_ADJUST_FLAG")
	private String preAdjustFlag;
	
	@Column(name="ADJUST_FLAG")
	private String adjustFlag;
	
	@Column(name="THRUPUT_COUNT")
	private String thruputCount;
	
	@Column(name="SHIP_NO")
	private String shipNo;
	
	@Column(name="BERTH_CODE")
	private String berthCode;
	
	@Column(name="SVOYAGE_ID")
	private String svoyageId;
	
	@Transient
	private String shipName;
	
	@Transient
	private String voyage;
	
	@Transient
	private String IeFlagNam;
	

	public ShipTeam() {
	}

	public String getShipName() {
		return shipName;
	}

	public void setShipName(String shipName) {
		this.shipName = shipName;
	}

	public String getVoyage() {
		return voyage;
	}

	public void setVoyage(String voyage) {
		this.voyage = voyage;
	}

	
	public String getIeFlagNam() {
		return IeFlagNam;
	}

	public void setIeFlagNam(String ieFlagNam) {
		IeFlagNam = ieFlagNam;
	}

	public String getShipNo() {
		return shipNo;
	}

	public void setShipNo(String shipNo) {
		this.shipNo = shipNo;
	}

	public String getTeamOrgnId() {
		return teamOrgnId;
	}

	public void setTeamOrgnId(String teamOrgnId) {
		this.teamOrgnId = teamOrgnId;
	}

	public String getSteamId() {
		return steamId;
	}


	public void setSteamId(String steamId) {
		this.steamId = steamId;
	}


	public String getIeFlag() {
		return ieFlag;
	}


	public void setIeFlag(String ieFlag) {
		this.ieFlag = ieFlag;
	}


	public String getTradeFlag() {
		return tradeFlag;
	}


	public void setTradeFlag(String tradeFlag) {
		this.tradeFlag = tradeFlag;
	}


	public String getRtd() {
		return rtd;
	}


	public void setRtd(String rtd) {
		this.rtd = rtd;
	}


	public String getShipStatFlag() {
		return shipStatFlag;
	}


	public void setShipStatFlag(String shipStatFlag) {
		this.shipStatFlag = shipStatFlag;
	}



	public Timestamp getStartTime() {
		return startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	public Timestamp getEndTime() {
		return endTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	public String getWorkWgt() {
		return workWgt;
	}


	public void setWorkWgt(String workWgt) {
		this.workWgt = workWgt;
	}


	public String getCntrNum() {
		return cntrNum;
	}


	public void setCntrNum(String cntrNum) {
		this.cntrNum = cntrNum;
	}


	public String getTeuNum() {
		return teuNum;
	}


	public void setTeuNum(String teuNum) {
		this.teuNum = teuNum;
	}


	public String getCarNum() {
		return carNum;
	}


	public void setCarNum(String carNum) {
		this.carNum = carNum;
	}


	public String getStdcarNum() {
		return stdcarNum;
	}


	public void setStdcarNum(String stdcarNum) {
		this.stdcarNum = stdcarNum;
	}


	public String getPlanHour() {
		return planHour;
	}


	public void setPlanHour(String planHour) {
		this.planHour = planHour;
	}


	public String getCargoKind() {
		return cargoKind;
	}


	public void setCargoKind(String cargoKind) {
		this.cargoKind = cargoKind;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getPreTtlFlag() {
		return preTtlFlag;
	}


	public void setPreTtlFlag(String preTtlFlag) {
		this.preTtlFlag = preTtlFlag;
	}


	public String getThruputFlag() {
		return thruputFlag;
	}


	public void setThruputFlag(String thruputFlag) {
		this.thruputFlag = thruputFlag;
	}


	public String getThruputTime() {
		return thruputTime;
	}


	public void setThruputTime(String thruputTime) {
		this.thruputTime = thruputTime;
	}


	public String getThruputName() {
		return thruputName;
	}


	public void setThruputName(String thruputName) {
		this.thruputName = thruputName;
	}


	public String getOrgnId() {
		return orgnId;
	}


	public void setOrgnId(String orgnId) {
		this.orgnId = orgnId;
	}


	public String getEndFlag() {
		return endFlag;
	}


	public void setEndFlag(String endFlag) {
		this.endFlag = endFlag;
	}


	public String getQuickFlag() {
		return quickFlag;
	}


	public void setQuickFlag(String quickFlag) {
		this.quickFlag = quickFlag;
	}


	public String getQuickDate() {
		return quickDate;
	}


	public void setQuickDate(String quickDate) {
		this.quickDate = quickDate;
	}


	public String getDfd() {
		return dfd;
	}


	public void setDfd(String dfd) {
		this.dfd = dfd;
	}


	public String getDad() {
		return dad;
	}


	public void setDad(String dad) {
		this.dad = dad;
	}


	public String getSubmitFlag() {
		return submitFlag;
	}


	public void setSubmitFlag(String submitFlag) {
		this.submitFlag = submitFlag;
	}


	public String getSubmitName() {
		return submitName;
	}


	public void setSubmitName(String submitName) {
		this.submitName = submitName;
	}


	public String getSubmitTime() {
		return submitTime;
	}


	public void setSubmitTime(String submitTime) {
		this.submitTime = submitTime;
	}


	public String getCheckFlag() {
		return checkFlag;
	}


	public void setCheckFlag(String checkFlag) {
		this.checkFlag = checkFlag;
	}


	public String getCheckName() {
		return checkName;
	}


	public void setCheckName(String checkName) {
		this.checkName = checkName;
	}


	public String getCheckTime() {
		return checkTime;
	}


	public void setCheckTime(String checkTime) {
		this.checkTime = checkTime;
	}


	public String getLockFlag() {
		return lockFlag;
	}


	public void setLockFlag(String lockFlag) {
		this.lockFlag = lockFlag;
	}


	public String getAutoLockFlag() {
		return autoLockFlag;
	}


	public void setAutoLockFlag(String autoLockFlag) {
		this.autoLockFlag = autoLockFlag;
	}


	public String getPreTtlDate() {
		return preTtlDate;
	}


	public void setPreTtlDate(String preTtlDate) {
		this.preTtlDate = preTtlDate;
	}


	public String getPreMissFlag() {
		return preMissFlag;
	}


	public void setPreMissFlag(String preMissFlag) {
		this.preMissFlag = preMissFlag;
	}


	public String getPreAdjustFlag() {
		return preAdjustFlag;
	}


	public void setPreAdjustFlag(String preAdjustFlag) {
		this.preAdjustFlag = preAdjustFlag;
	}


	public String getAdjustFlag() {
		return adjustFlag;
	}


	public void setAdjustFlag(String adjustFlag) {
		this.adjustFlag = adjustFlag;
	}


	public String getThruputCount() {
		return thruputCount;
	}


	public void setThruputCount(String thruputCount) {
		this.thruputCount = thruputCount;
	}


	public String getBerthCode() {
		return berthCode;
	}


	public void setBerthCode(String berthCode) {
		this.berthCode = berthCode;
	}


	public String getShipId() {
		return shipId;
	}

	public void setShipId(String shipId) {
		this.shipId = shipId;
	}

	public String getSvoyageId() {
		return svoyageId;
	}

	public void setSvoyageId(String svoyageId) {
		this.svoyageId = svoyageId;
	}


}