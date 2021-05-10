package net.huadong.tech.base.entity;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;


import net.huadong.tech.base.bean.AuditEntityBean;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the C_CLIENT_COD database table.
 * 
 */
@Entity
@Table(name="C_CLIENT_COD")
@NamedQuery(name="CClientCod.findAll", query="SELECT c FROM CClientCod c")
public class CClientCod extends AuditEntityBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Basic(optional = false)
	@NotNull
	@Column(name="CLIENT_COD")
	private String clientCod;

	@Column(name="ACO_WAY")
	private String acoWay;

	@Column(name="ADDRES_TXT")
	private String addresTxt;

	@Column(name="C_CLIENT_NAM")
	private String cClientNam;

	@Column(name="C_CLIENT_SHORT")
	private String cClientShort;

	@Column(name="E_CLIENT_SHORT")
	private String eClientShort;
	
	@Column(name="CLIENT_ABSTRACT")
	private String clientAbstract;

	@Column(name="CONSIGN_ID")
	private String consignId;

	private BigDecimal credit;

	@Column(name="CRG_AGENT_ID")
	private String crgAgentId;

	@Column(name="E_CLIENT_NAM")
	private String eClientNam;

	private String email;

	private String fax;

	@Column(name="FEE_WAY")
	private String feeWay;

	@Column(name="FREE_DAYS")
	private BigDecimal freeDays;

	@Column(name="PAY_UNIT_ID")
	private String payUnitId;

	private String remarks;

	@Column(name="SHIP_AGENT_ID")
	private String shipAgentId;
	
	@Column(name="SHIP_CONSIGN_ID")
	private String shipConsignId;

	@Column(name="SHIP_CORP_ID")
	private String shipCorpId;
	
	@Column(name="SHIP_OWNER_ID")
	private String shipOwnerId;

	@Column(name="TELEPHON")
	private String telephon;

	@Column(name="TELEX")
	private String telex;

	@Column(name="TRUCK_UNIT_ID")
	private String truckUnitId;

	@Temporal(TemporalType.DATE)
	@Column(name="VALID_DTE")
	private Date validDte;

	@Column(name="ZIP")
	private String zip;
	
	@Column(name="CONSTRUCTION_ID")
	private String constructionId;
	
	@Column(name="GROUP_CLIENT_COD")
	private String groupClientCod;

	public CClientCod() {
	}

	public String getClientCod() {
		return this.clientCod;
	}

	public void setClientCod(String clientCod) {
		this.clientCod = clientCod;
	}

	public String getShipConsignId() {
		return shipConsignId;
	}

	public void setShipConsignId(String shipConsignId) {
		this.shipConsignId = shipConsignId;
	}

	public String getConstructionId() {
		return constructionId;
	}

	public void setConstructionId(String constructionId) {
		this.constructionId = constructionId;
	}

	public String getAcoWay() {
		return this.acoWay;
	}

	public void setAcoWay(String acoWay) {
		this.acoWay = acoWay;
	}

	public String getAddresTxt() {
		return this.addresTxt;
	}

	public void setAddresTxt(String addresTxt) {
		this.addresTxt = addresTxt;
	}

	public String geteClientShort() {
		return eClientShort;
	}

	public void seteClientShort(String eClientShort) {
		this.eClientShort = eClientShort;
	}

	public String getClientAbstract() {
		return clientAbstract;
	}

	public void setClientAbstract(String clientAbstract) {
		this.clientAbstract = clientAbstract;
	}

	public String getShipOwnerId() {
		return shipOwnerId;
	}

	public void setShipOwnerId(String shipOwnerId) {
		this.shipOwnerId = shipOwnerId;
	}

	public String getConsignId() {
		return this.consignId;
	}

	public void setConsignId(String consignId) {
		this.consignId = consignId;
	}

	public BigDecimal getCredit() {
		return this.credit;
	}

	public void setCredit(BigDecimal credit) {
		this.credit = credit;
	}

	public String getCrgAgentId() {
		return this.crgAgentId;
	}

	public void setCrgAgentId(String crgAgentId) {
		this.crgAgentId = crgAgentId;
	}

	
	public String getcClientNam() {
		return cClientNam;
	}

	public void setcClientNam(String cClientNam) {
		this.cClientNam = cClientNam;
	}

	public String getcClientShort() {
		return cClientShort;
	}

	public void setcClientShort(String cClientShort) {
		this.cClientShort = cClientShort;
	}

	public String geteClientNam() {
		return eClientNam;
	}

	public void seteClientNam(String eClientNam) {
		this.eClientNam = eClientNam;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFax() {
		return this.fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getFeeWay() {
		return this.feeWay;
	}

	public void setFeeWay(String feeWay) {
		this.feeWay = feeWay;
	}

	public BigDecimal getFreeDays() {
		return this.freeDays;
	}

	public void setFreeDays(BigDecimal freeDays) {
		this.freeDays = freeDays;
	}

	public String getPayUnitId() {
		return this.payUnitId;
	}

	public void setPayUnitId(String payUnitId) {
		this.payUnitId = payUnitId;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getShipAgentId() {
		return this.shipAgentId;
	}

	public void setShipAgentId(String shipAgentId) {
		this.shipAgentId = shipAgentId;
	}

	public String getShipCorpId() {
		return this.shipCorpId;
	}

	public void setShipCorpId(String shipCorpId) {
		this.shipCorpId = shipCorpId;
	}

	public String getTelephon() {
		return this.telephon;
	}

	public void setTelephon(String telephon) {
		this.telephon = telephon;
	}

	public String getTelex() {
		return this.telex;
	}

	public void setTelex(String telex) {
		this.telex = telex;
	}

	public String getTruckUnitId() {
		return this.truckUnitId;
	}

	public void setTruckUnitId(String truckUnitId) {
		this.truckUnitId = truckUnitId;
	}

	public Date getValidDte() {
		return this.validDte;
	}

	public void setValidDte(Date validDte) {
		this.validDte = validDte;
	}

	public String getZip() {
		return this.zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getGroupClientCod() {
		return groupClientCod;
	}

	public void setGroupClientCod(String groupClientCod) {
		this.groupClientCod = groupClientCod;
	}

}