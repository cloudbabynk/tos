package net.huadong.tech.ship.entity;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

import org.eclipse.persistence.annotations.UuidGenerator;

import com.fasterxml.jackson.annotation.JsonFormat;

import net.huadong.tech.base.bean.AuditEntityBean;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;


/**
 * The persistent class for the SHIP_STAT database table.
 * 
 */
@Entity
@Table(name="SHIP_STAT")
@NamedQuery(name="ShipStat.findAll", query="SELECT s FROM ShipStat s")
public class ShipStat extends AuditEntityBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static final String KAOBO = "1001";
	public static final String KAICHUAN = "1002";
	public static final String LIBO = "1003";
	public static final String YIBO = "1008";


	@Id
	@Column(name="SHIP_STAT_ID")
	private String shipStatId;

	private String remarks;

	@Column(name="SEQ_NO")
	private BigDecimal seqNo;

	@Column(name="SHIP_NO")
	private String shipNo;

	@Column(name="SHIP_STAT_COD")
	private String shipStatCod;

	@Column(name="STAT_BEG_TIM")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm") 
	private Timestamp statBegTim;

	@Column(name="STAT_END_TIM")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm") 
	private Timestamp statEndTim;

	@Column(name="SEND_FLAG")
	private String sendFlag;
	
	@Column(name="STAT_TIM")
	private BigDecimal statTim	;
	
	@Column(name="I_E_ID")
	private String iEId;
	
	@Column(name="GROUP_CARGO_TYP")
	private String groupCargoTyp;
	
	@Column(name="BERTH_COD")
	private String berthCod;
	
	@Transient
	private String shipStatCodNam;
	
	@Transient
	private String berthNam;
	
	@Transient
	private String bgTim;
	
	@Transient
	private String edTim;
	
	@Transient
	private String iEIdNam;
	
	@Transient
	private String groupCargoTypNam;
	
	@Transient
	private String sendFlagNam;
	
	
	public String getSendFlagNam() {
		return sendFlagNam;
	}

	public void setSendFlagNam(String sendFlagNam) {
		this.sendFlagNam = sendFlagNam;
	}

	public String getSendFlag() {
		return sendFlag;
	}

	public void setSendFlag(String sendFlag) {
		this.sendFlag = sendFlag;
	}

	public String getiEIdNam() {
		return iEIdNam;
	}

	public void setiEIdNam(String iEIdNam) {
		this.iEIdNam = iEIdNam;
	}

	public String getGroupCargoTypNam() {
		return groupCargoTypNam;
	}

	public void setGroupCargoTypNam(String groupCargoTypNam) {
		this.groupCargoTypNam = groupCargoTypNam;
	}

	public String getGroupCargoTyp() {
		return groupCargoTyp;
	}

	public void setGroupCargoTyp(String groupCargoTyp) {
		this.groupCargoTyp = groupCargoTyp;
	}

	public String getiEId() {
		return iEId;
	}

	public void setiEId(String iEId) {
		this.iEId = iEId;
	}

	public BigDecimal getStatTim() {
		return statTim;
	}

	public String getBgTim() {
		return bgTim;
	}

	public void setBgTim(String bgTim) {
		this.bgTim = bgTim;
	}

	public String getEdTim() {
		return edTim;
	}

	public void setEdTim(String edTim) {
		this.edTim = edTim;
	}

	public void setStatTim(BigDecimal statTim) {
		this.statTim = statTim;
	}

	public String getBerthNam() {
		return berthNam;
	}

	public void setBerthNam(String berthNam) {
		this.berthNam = berthNam;
	}

	public ShipStat() {
	}

	public String getShipStatCodNam() {
		return shipStatCodNam;
	}

	public void setShipStatCodNam(String shipStatCodNam) {
		this.shipStatCodNam = shipStatCodNam;
	}

	public String getShipStatId() {
		return this.shipStatId;
	}

	public void setShipStatId(String shipStatId) {
		this.shipStatId = shipStatId;
	}


	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public BigDecimal getSeqNo() {
		return this.seqNo;
	}

	public void setSeqNo(BigDecimal seqNo) {
		this.seqNo = seqNo;
	}

	public String getShipNo() {
		return this.shipNo;
	}

	public void setShipNo(String shipNo) {
		this.shipNo = shipNo;
	}

	public String getShipStatCod() {
		return this.shipStatCod;
	}

	public void setShipStatCod(String shipStatCod) {
		this.shipStatCod = shipStatCod;
	}

	public Timestamp getStatBegTim() {
		return statBegTim;
	}

	public void setStatBegTim(Timestamp statBegTim) {
		this.statBegTim = statBegTim;
	}

	public Timestamp getStatEndTim() {
		return statEndTim;
	}

	public void setStatEndTim(Timestamp statEndTim) {
		this.statEndTim = statEndTim;
	}


	public String getBerthCod() {
		return berthCod;
	}

	public void setBerthCod(String berthCod) {
		this.berthCod = berthCod;
	}

	


}