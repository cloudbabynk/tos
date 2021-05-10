package net.huadong.tech.Interface.entity;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;


/**
 * The persistent class for the V_GROUP_CORP_DAY_STAT_PLAN database table.
 * 
 */
@Entity
@Table(name="V_GROUP_CORP_DAY_STAT_PLAN")
@NamedQuery(name="VGroupCorpDayStatPlan.findAll", query="SELECT v FROM VGroupCorpDayStatPlan v")
public class VGroupCorpDayStatPlan implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="DSPLAN_ID")
	private String dsplanId;
	
	@Column(name="ALTER_TYPE")
	private String alterType;

	@Column(name="ASK_FLAG")
	private String askFlag;

	@Column(name="BEG_CABLE")
	private String begCable;

	
	@Column(name="BERTH_CODE")
	private String berthCode;

	@Column(name="CABLE_DISTANCE")
	private BigDecimal cableDistance;

	private String description;

	private BigDecimal draft;

	@Column(name="END_CABLE")
	private String endCable;

	@Column(name="PILOT_FLAG")
	private String pilotFlag;

	@Column(name="PLAN_BERTH_CODE")
	private String planBerthCode;

	@Column(name="PORT_DISTRICT")
	private String portDistrict;

	@Column(name="SHIP_NAME")
	private String shipName;

	@Column(name="STAT_CODE")
	private String statCode;

	
	@Column(name="STAT_TIME")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
	private Timestamp statTime;

	@Column(name="STAT_TYPE")
	private String statType;

	@Column(name="SVOYAGE_ID")
	private String svoyageId;

	@Column(name="TEAM_ORGN_ID")
	private String teamOrgnId;

	@Column(name="TUG_NUM")
	private BigDecimal tugNum;

	public VGroupCorpDayStatPlan() {
	}

	public String getDsplanId() {
		return dsplanId;
	}

	public void setDsplanId(String dsplanId) {
		this.dsplanId = dsplanId;
	}

	public String getAlterType() {
		return this.alterType;
	}

	public void setAlterType(String alterType) {
		this.alterType = alterType;
	}

	public String getAskFlag() {
		return this.askFlag;
	}

	public void setAskFlag(String askFlag) {
		this.askFlag = askFlag;
	}

	public String getBegCable() {
		return this.begCable;
	}

	public void setBegCable(String begCable) {
		this.begCable = begCable;
	}

	public String getBerthCode() {
		return this.berthCode;
	}

	public void setBerthCode(String berthCode) {
		this.berthCode = berthCode;
	}

	public BigDecimal getCableDistance() {
		return this.cableDistance;
	}

	public void setCableDistance(BigDecimal cableDistance) {
		this.cableDistance = cableDistance;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getDraft() {
		return this.draft;
	}

	public void setDraft(BigDecimal draft) {
		this.draft = draft;
	}

	public String getEndCable() {
		return this.endCable;
	}

	public void setEndCable(String endCable) {
		this.endCable = endCable;
	}

	public String getPilotFlag() {
		return this.pilotFlag;
	}

	public void setPilotFlag(String pilotFlag) {
		this.pilotFlag = pilotFlag;
	}

	public String getPlanBerthCode() {
		return this.planBerthCode;
	}

	public void setPlanBerthCode(String planBerthCode) {
		this.planBerthCode = planBerthCode;
	}

	public String getPortDistrict() {
		return this.portDistrict;
	}

	public void setPortDistrict(String portDistrict) {
		this.portDistrict = portDistrict;
	}

	public String getShipName() {
		return this.shipName;
	}

	public void setShipName(String shipName) {
		this.shipName = shipName;
	}

	public String getStatCode() {
		return this.statCode;
	}

	public void setStatCode(String statCode) {
		this.statCode = statCode;
	}


	public Timestamp getStatTime() {
		return statTime;
	}

	public void setStatTime(Timestamp statTime) {
		this.statTime = statTime;
	}

	public String getStatType() {
		return this.statType;
	}

	public void setStatType(String statType) {
		this.statType = statType;
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

	public BigDecimal getTugNum() {
		return this.tugNum;
	}

	public void setTugNum(BigDecimal tugNum) {
		this.tugNum = tugNum;
	}

}