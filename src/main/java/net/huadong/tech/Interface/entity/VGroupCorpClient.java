package net.huadong.tech.Interface.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the V_GROUP_CORP_CLIENT database table.
 * 
 */
@Entity
@Table(name="V_GROUP_CORP_CLIENT")
@NamedQuery(name="VGroupCorpClient.findAll", query="SELECT v FROM VGroupCorpClient v")
public class VGroupCorpClient implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CLIENT_CODE")
	private String clientCode;

	@Column(name="CLIENT_NAME")
	private String clientName;

	@Column(name="CNEE_FLAG")
	private String cneeFlag;

	@Column(name="CNOR_FLAG")
	private String cnorFlag;

	@Column(name="CNTR_CORP_FLAG")
	private String cntrCorpFlag;

	@Column(name="CNTR_OPER_FLAG")
	private String cntrOperFlag;

	@Column(name="CONSIGN_FLAG")
	private String consignFlag;

	@Column(name="CONSIGNOR_FLAG")
	private String consignorFlag;

	@Column(name="CUSTOMS_FLAG")
	private String customsFlag;

	@Column(name="FLEET_FLAG")
	private String fleetFlag;

	@Column(name="FORWARDER_FLAG")
	private String forwarderFlag;

	@Column(name="SHIP_AGENT_FLAG")
	private String shipAgentFlag;

	@Column(name="SHIP_CORP_FLAG")
	private String shipCorpFlag;

	@Column(name="SHIP_OWNER_FLAG")
	private String shipOwnerFlag;

	@Column(name="TRANS_FLAG")
	private String transFlag;

	@Column(name="TRAVEL_AGENCY_FLAG")
	private String travelAgencyFlag;

	public VGroupCorpClient() {
	}

	public String getClientCode() {
		return this.clientCode;
	}

	public void setClientCode(String clientCode) {
		this.clientCode = clientCode;
	}

	public String getClientName() {
		return this.clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getCneeFlag() {
		return this.cneeFlag;
	}

	public void setCneeFlag(String cneeFlag) {
		this.cneeFlag = cneeFlag;
	}

	public String getCnorFlag() {
		return this.cnorFlag;
	}

	public void setCnorFlag(String cnorFlag) {
		this.cnorFlag = cnorFlag;
	}

	public String getCntrCorpFlag() {
		return this.cntrCorpFlag;
	}

	public void setCntrCorpFlag(String cntrCorpFlag) {
		this.cntrCorpFlag = cntrCorpFlag;
	}

	public String getCntrOperFlag() {
		return this.cntrOperFlag;
	}

	public void setCntrOperFlag(String cntrOperFlag) {
		this.cntrOperFlag = cntrOperFlag;
	}

	public String getConsignFlag() {
		return this.consignFlag;
	}

	public void setConsignFlag(String consignFlag) {
		this.consignFlag = consignFlag;
	}

	public String getConsignorFlag() {
		return this.consignorFlag;
	}

	public void setConsignorFlag(String consignorFlag) {
		this.consignorFlag = consignorFlag;
	}

	public String getCustomsFlag() {
		return this.customsFlag;
	}

	public void setCustomsFlag(String customsFlag) {
		this.customsFlag = customsFlag;
	}

	public String getFleetFlag() {
		return this.fleetFlag;
	}

	public void setFleetFlag(String fleetFlag) {
		this.fleetFlag = fleetFlag;
	}

	public String getForwarderFlag() {
		return this.forwarderFlag;
	}

	public void setForwarderFlag(String forwarderFlag) {
		this.forwarderFlag = forwarderFlag;
	}

	public String getShipAgentFlag() {
		return this.shipAgentFlag;
	}

	public void setShipAgentFlag(String shipAgentFlag) {
		this.shipAgentFlag = shipAgentFlag;
	}

	public String getShipCorpFlag() {
		return this.shipCorpFlag;
	}

	public void setShipCorpFlag(String shipCorpFlag) {
		this.shipCorpFlag = shipCorpFlag;
	}

	public String getShipOwnerFlag() {
		return this.shipOwnerFlag;
	}

	public void setShipOwnerFlag(String shipOwnerFlag) {
		this.shipOwnerFlag = shipOwnerFlag;
	}

	public String getTransFlag() {
		return this.transFlag;
	}

	public void setTransFlag(String transFlag) {
		this.transFlag = transFlag;
	}

	public String getTravelAgencyFlag() {
		return this.travelAgencyFlag;
	}

	public void setTravelAgencyFlag(String travelAgencyFlag) {
		this.travelAgencyFlag = travelAgencyFlag;
	}

}