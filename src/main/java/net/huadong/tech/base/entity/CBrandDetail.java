package net.huadong.tech.base.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="C_BRAND_DETAIL")
@NamedQuery(name="CBrandDetail.findAll", query="SELECT c FROM CBrandDetail c")
public class CBrandDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID")
	private String id;
	
	@Column(name="BRAND_COD")
	private String brandCod;
	
	@Column(name="CONSIGN_COD")
	private String consignCod;
	
	@Column(name="CONSIGN_NAM")
	private String consignNam;
	
	@Column(name="RECEIVE_COD")
	private String receiveCod;
	
	@Column(name="RECEIVE_NAM")
	private String receiveNam;
	
	@Column(name="CLIENT_COD")
	private String clientCod;

	@Column(name="CLIENT_NAM")
	private String clientNam;
	
	@Column(name="AGENT_COD")
	private String agentCod;
	
	@Column(name="AGENT_NAM")
	private String agentNam;
	
	@Column(name="I_E_ID")
	private String iEId;
	
	@Column(name="TRADE_ID")
	private String tradeId;
	
	@Column(name="REC_NAM")
	private String recNam;

	@Temporal(TemporalType.DATE)
	@Column(name="REC_TIM")
	private Date recTim;
	
	@Column(name="UPD_NAM")
	private String updNam;

	@Temporal(TemporalType.DATE)
	@Column(name="UPD_TIM")
	private Date updTim;
	
	@Column(name="ORIGIN_CODE")
	private String originCode;
	
	@Column(name="FLOW_DIR")
	private String flowDir;

	
	public String getOriginCode() {
		return originCode;
	}

	public void setOriginCode(String originCode) {
		this.originCode = originCode;
	}

	public String getFlowDir() {
		return flowDir;
	}

	public void setFlowDir(String flowDir) {
		this.flowDir = flowDir;
	}

	public String getRecNam() {
		return recNam;
	}

	public void setRecNam(String recNam) {
		this.recNam = recNam;
	}

	public Date getRecTim() {
		return recTim;
	}

	public void setRecTim(Date recTim) {
		this.recTim = recTim;
	}

	public String getUpdNam() {
		return updNam;
	}

	public void setUpdNam(String updNam) {
		this.updNam = updNam;
	}

	public Date getUpdTim() {
		return updTim;
	}

	public void setUpdTim(Date updTim) {
		this.updTim = updTim;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBrandCod() {
		return brandCod;
	}

	public void setBrandCod(String brandCod) {
		this.brandCod = brandCod;
	}

	public String getConsignCod() {
		return consignCod;
	}

	public void setConsignCod(String consignCod) {
		this.consignCod = consignCod;
	}

	public String getConsignNam() {
		return consignNam;
	}

	public void setConsignNam(String consignNam) {
		this.consignNam = consignNam;
	}

	public String getReceiveCod() {
		return receiveCod;
	}

	public void setReceiveCod(String receiveCod) {
		this.receiveCod = receiveCod;
	}

	public String getReceiveNam() {
		return receiveNam;
	}

	public void setReceiveNam(String receiveNam) {
		this.receiveNam = receiveNam;
	}

	public String getClientCod() {
		return clientCod;
	}

	public void setClientCod(String clientCod) {
		this.clientCod = clientCod;
	}

	public String getClientNam() {
		return clientNam;
	}

	public void setClientNam(String clientNam) {
		this.clientNam = clientNam;
	}

	public String getAgentCod() {
		return agentCod;
	}

	public void setAgentCod(String agentCod) {
		this.agentCod = agentCod;
	}

	public String getAgentNam() {
		return agentNam;
	}

	public void setAgentNam(String agentNam) {
		this.agentNam = agentNam;
	}

	public String getiEId() {
		return iEId;
	}

	public void setiEId(String iEId) {
		this.iEId = iEId;
	}

	public String getTradeId() {
		return tradeId;
	}

	public void setTradeId(String tradeId) {
		this.tradeId = tradeId;
	}
}
