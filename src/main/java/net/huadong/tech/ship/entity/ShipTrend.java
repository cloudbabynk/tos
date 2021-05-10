package net.huadong.tech.ship.entity;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;

import net.huadong.tech.base.bean.AuditEntityBean;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;


/**
 * The persistent class for the SHIP_TRENDS database table.
 * 
 */
@Entity
@Table(name="SHIP_TRENDS")
@NamedQuery(name="ShipTrend.findAll", query="SELECT s FROM ShipTrend s")
public class ShipTrend extends AuditEntityBean implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final String SQ = "01"; //动态状态为申请
	public static final String JH = "02"; //动态状态为计划
	public static final String BG = "03"; //动态状态为变更
	public static final String SJ = "04"; //动态状态为实际
	public static final String SQJH = "05"; //动态状态为实际
	
	public static final String B = "B"; //发送状态为变
	public static final String F = "F"; //发送状态为发
	public static final String S = "Y"; //发送状态为收
	
	public static final String J = "1"; //动向：进
	public static final String JZK = "11"; //动向：进左靠
	public static final String JYK = "12"; //动向：进右靠
	public static final String K = "3"; //动向：开
	public static final String Y = "2"; //动向：移
	public static final String YZK = "21"; //动向：移左靠
	public static final String YYK = "22"; //动向：移右靠
	
	@Id
	@Column(name="SHIP_TRENDS_ID")
	private String shipTrendsId;

	@Column(name="CHANGE_CONFIRM_FLAG")
	private String changeConfirmFlag;

	@Column(name="CHANGE_SEND_FLAG")
	private String changeSendFlag;

	@Column(name="CONFIRM_FLAG")
	private String confirmFlag;

	private String remarks;

	@Column(name="SEND_FLAG")
	private String sendFlag;

	@Column(name="SEQ_NO")
	private BigDecimal seqNo;

	@Column(name="SHIP_DRAFT")
	private BigDecimal shipDraft;

	@Column(name="SHIP_NO")
	private String shipNo;

	@Column(name="SHIP_TRENDS_COD")
	private String shipTrendsCod;

	@Column(name="TRENDS_BEG_AREA")
	private String trendsBegArea;

	@Column(name="TRENDS_BEG_TIM")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm") 
	private Timestamp trendsBegTim;

	@Column(name="TRENDS_CHANGE_REASON")
	private String trendsChangeReason;

	@Column(name="TRENDS_CHANGE_TIM")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm") 
	private Timestamp trendsChangeTim;

	@Column(name="TRENDS_END_AREA")
	private String trendsEndArea;

	@Column(name="TRENDS_PLAN_TIM")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm") 
	private Timestamp trendsPlanTim;

	@Column(name="TRENDS_TERMINI")
	private String trendsTermini;

	@Column(name="TRENDS_TYP")
	private String trendsTyp;

	@Column(name="USE_PILOT")
	private String usePilot;

	@Column(name="USE_TAG")
	private String useTag;
	
	@Column(name="GROUP_SHIP_NO")
	private String groupShipNo;
	
	@Column(name="SHIP_TO_CABLE")
	private String shipToCable;
	
	@Column(name="I_DAN_CARGO")
	private String iDanCargo;
	
	@Column(name="E_DAN_CARGO")
	private String eDanCargo;
	
	@Column(name="TRENDS_CHANGE_TXT")
	private String trendsChangeTxt;
	
	@Column(name="BEG_CABLE_NO")
	private String begCableNo;
	
	@Column(name="END_CABLE_NO")
	private String endCableNo;
	
	@Transient
	private String begCableNoNam;
	
	@Transient
	private String endCableNoNam;
	
	@Transient
	private String shipVoyage;
	
	@Transient
	private String trendsTypNam;
	
	@Transient
	private String trendsBegAreaNam;
	
	@Transient
	private String trendsEndAreaNam;
	
	
	public String getBegCableNoNam() {
		return begCableNoNam;
	}

	public void setBegCableNoNam(String begCableNoNam) {
		this.begCableNoNam = begCableNoNam;
	}

	public String getEndCableNoNam() {
		return endCableNoNam;
	}

	public void setEndCableNoNam(String endCableNoNam) {
		this.endCableNoNam = endCableNoNam;
	}

	public String getTrendsBegAreaNam() {
		return trendsBegAreaNam;
	}

	public void setTrendsBegAreaNam(String trendsBegAreaNam) {
		this.trendsBegAreaNam = trendsBegAreaNam;
	}

	public String getTrendsEndAreaNam() {
		return trendsEndAreaNam;
	}

	public void setTrendsEndAreaNam(String trendsEndAreaNam) {
		this.trendsEndAreaNam = trendsEndAreaNam;
	}

	public String getTrendsTypNam() {
		return trendsTypNam;
	}

	public void setTrendsTypNam(String trendsTypNam) {
		this.trendsTypNam = trendsTypNam;
	}

	public String getShipVoyage() {
		return shipVoyage;
	}

	public void setShipVoyage(String shipVoyage) {
		this.shipVoyage = shipVoyage;
	}

	public String getBegCableNo() {
		return begCableNo;
	}

	public void setBegCableNo(String begCableNo) {
		this.begCableNo = begCableNo;
	}

	public String getEndCableNo() {
		return endCableNo;
	}

	public void setEndCableNo(String endCableNo) {
		this.endCableNo = endCableNo;
	}

	public String getShipToCable() {
		return shipToCable;
	}

	public void setShipToCable(String shipToCable) {
		this.shipToCable = shipToCable;
	}


	
	public String getiDanCargo() {
		return iDanCargo;
	}

	public void setiDanCargo(String iDanCargo) {
		this.iDanCargo = iDanCargo;
	}

	public String geteDanCargo() {
		return eDanCargo;
	}

	public void seteDanCargo(String eDanCargo) {
		this.eDanCargo = eDanCargo;
	}

	public String getTrendsChangeTxt() {
		return trendsChangeTxt;
	}

	public void setTrendsChangeTxt(String trendsChangeTxt) {
		this.trendsChangeTxt = trendsChangeTxt;
	}

	public String getGroupShipNo() {
		return groupShipNo;
	}

	public void setGroupShipNo(String groupShipNo) {
		this.groupShipNo = groupShipNo;
	}

	public ShipTrend() {
	}

	public String getShipTrendsId() {
		return this.shipTrendsId;
	}

	public void setShipTrendsId(String shipTrendsId) {
		this.shipTrendsId = shipTrendsId;
	}

	public String getChangeConfirmFlag() {
		return this.changeConfirmFlag;
	}

	public void setChangeConfirmFlag(String changeConfirmFlag) {
		this.changeConfirmFlag = changeConfirmFlag;
	}

	public String getChangeSendFlag() {
		return this.changeSendFlag;
	}

	public void setChangeSendFlag(String changeSendFlag) {
		this.changeSendFlag = changeSendFlag;
	}

	public String getConfirmFlag() {
		return this.confirmFlag;
	}

	public void setConfirmFlag(String confirmFlag) {
		this.confirmFlag = confirmFlag;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getSendFlag() {
		return this.sendFlag;
	}

	public void setSendFlag(String sendFlag) {
		this.sendFlag = sendFlag;
	}

	public BigDecimal getSeqNo() {
		return this.seqNo;
	}

	public void setSeqNo(BigDecimal seqNo) {
		this.seqNo = seqNo;
	}

	public BigDecimal getShipDraft() {
		return this.shipDraft;
	}

	public void setShipDraft(BigDecimal shipDraft) {
		this.shipDraft = shipDraft;
	}

	public String getShipNo() {
		return this.shipNo;
	}

	public void setShipNo(String shipNo) {
		this.shipNo = shipNo;
	}

	public String getShipTrendsCod() {
		return this.shipTrendsCod;
	}

	public void setShipTrendsCod(String shipTrendsCod) {
		this.shipTrendsCod = shipTrendsCod;
	}

	public String getTrendsBegArea() {
		return this.trendsBegArea;
	}

	public void setTrendsBegArea(String trendsBegArea) {
		this.trendsBegArea = trendsBegArea;
	}


	public String getTrendsChangeReason() {
		return this.trendsChangeReason;
	}

	public void setTrendsChangeReason(String trendsChangeReason) {
		this.trendsChangeReason = trendsChangeReason;
	}

	public String getTrendsEndArea() {
		return this.trendsEndArea;
	}

	public void setTrendsEndArea(String trendsEndArea) {
		this.trendsEndArea = trendsEndArea;
	}

	public String getTrendsTermini() {
		return this.trendsTermini;
	}

	public void setTrendsTermini(String trendsTermini) {
		this.trendsTermini = trendsTermini;
	}

	public String getTrendsTyp() {
		return this.trendsTyp;
	}

	public void setTrendsTyp(String trendsTyp) {
		this.trendsTyp = trendsTyp;
	}

	public String getUsePilot() {
		return this.usePilot;
	}

	public void setUsePilot(String usePilot) {
		this.usePilot = usePilot;
	}

	public String getUseTag() {
		return this.useTag;
	}

	public void setUseTag(String useTag) {
		this.useTag = useTag;
	}

	public Timestamp getTrendsBegTim() {
		return trendsBegTim;
	}

	public void setTrendsBegTim(Timestamp trendsBegTim) {
		this.trendsBegTim = trendsBegTim;
	}

	public Timestamp getTrendsChangeTim() {
		return trendsChangeTim;
	}

	public void setTrendsChangeTim(Timestamp trendsChangeTim) {
		this.trendsChangeTim = trendsChangeTim;
	}

	public Timestamp getTrendsPlanTim() {
		return trendsPlanTim;
	}

	public void setTrendsPlanTim(Timestamp trendsPlanTim) {
		this.trendsPlanTim = trendsPlanTim;
	}

}