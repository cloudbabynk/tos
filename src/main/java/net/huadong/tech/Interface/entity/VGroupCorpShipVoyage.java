package net.huadong.tech.Interface.entity;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;


/**
 * The persistent class for the V_GROUP_CORP_SHIP_VOYAGE database table.
 * 
 */
@Entity
@Table(name="V_GROUP_CORP_SHIP_VOYAGE")
@NamedQuery(name="VGroupCorpShipVoyage.findAll", query="SELECT v FROM VGroupCorpShipVoyage v")
public class VGroupCorpShipVoyage implements Serializable {
	private static final long serialVersionUID = 1L;

	private BigDecimal aad;

	private BigDecimal afd;

	private String description;

	@Column(name="DEST_PORT_CODE")
	private String destPortCode;

	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm") 
	private Timestamp eta;

	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
	private Timestamp etd;

	@Column(name="EXP_VOYAGE")
	private String expVoyage;

	@Column(name="FROM_PORT_CODE")
	private String fromPortCode;

	@Column(name="IMP_VOYAGE")
	private String impVoyage;

	@Column(name="LAST_PORT_CODE")
	private String lastPortCode;

	@Column(name="NEXT_PORT_CODE")
	private String nextPortCode;

	@Column(name="PLAN_BERTH_CODE")
	private String planBerthCode;

	@Column(name="PLAN_TEAM_ID")
	private String planTeamId;

	@Column(name="SDATA_ID")
	private String sdataId;

	@Column(name="SHIP_NAME")
	private String shipName;

	@Column(name="SHIP_STAT_FLAG")
	private String shipStatFlag;

	@Id
	@Column(name="SVOYAGE_ID")
	private String svoyageId;

	@Column(name="WORK_WAY")
	private String workWay;
	
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
	private Timestamp rta;
	
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
	private Timestamp rtd;
	
	
	
	

	public VGroupCorpShipVoyage() {
	}

	public BigDecimal getAad() {
		return this.aad;
	}

	public void setAad(BigDecimal aad) {
		this.aad = aad;
	}

	public BigDecimal getAfd() {
		return this.afd;
	}

	public void setAfd(BigDecimal afd) {
		this.afd = afd;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDestPortCode() {
		return this.destPortCode;
	}

	public void setDestPortCode(String destPortCode) {
		this.destPortCode = destPortCode;
	}

	public String getExpVoyage() {
		return this.expVoyage;
	}

	public void setExpVoyage(String expVoyage) {
		this.expVoyage = expVoyage;
	}

	public String getFromPortCode() {
		return this.fromPortCode;
	}

	public void setFromPortCode(String fromPortCode) {
		this.fromPortCode = fromPortCode;
	}

	public String getImpVoyage() {
		return this.impVoyage;
	}

	public void setImpVoyage(String impVoyage) {
		this.impVoyage = impVoyage;
	}

	public String getLastPortCode() {
		return this.lastPortCode;
	}

	public void setLastPortCode(String lastPortCode) {
		this.lastPortCode = lastPortCode;
	}

	public String getNextPortCode() {
		return this.nextPortCode;
	}

	public void setNextPortCode(String nextPortCode) {
		this.nextPortCode = nextPortCode;
	}

	public String getPlanBerthCode() {
		return this.planBerthCode;
	}

	public void setPlanBerthCode(String planBerthCode) {
		this.planBerthCode = planBerthCode;
	}

	public String getPlanTeamId() {
		return this.planTeamId;
	}

	public void setPlanTeamId(String planTeamId) {
		this.planTeamId = planTeamId;
	}

	public String getSdataId() {
		return this.sdataId;
	}

	public void setSdataId(String sdataId) {
		this.sdataId = sdataId;
	}

	public String getShipName() {
		return this.shipName;
	}

	public void setShipName(String shipName) {
		this.shipName = shipName;
	}

	public String getShipStatFlag() {
		return this.shipStatFlag;
	}

	public void setShipStatFlag(String shipStatFlag) {
		this.shipStatFlag = shipStatFlag;
	}

	public String getSvoyageId() {
		return this.svoyageId;
	}

	public void setSvoyageId(String svoyageId) {
		this.svoyageId = svoyageId;
	}

	public String getWorkWay() {
		return this.workWay;
	}

	public void setWorkWay(String workWay) {
		this.workWay = workWay;
	}

	public Timestamp getEta() {
		return eta;
	}

	public void setEta(Timestamp eta) {
		this.eta = eta;
	}

	public Timestamp getEtd() {
		return etd;
	}

	public void setEtd(Timestamp etd) {
		this.etd = etd;
	}

	public Timestamp getRta() {
		return rta;
	}

	public void setRta(Timestamp rta) {
		this.rta = rta;
	}

	public Timestamp getRtd() {
		return rtd;
	}

	public void setRtd(Timestamp rtd) {
		this.rtd = rtd;
	}

	
}