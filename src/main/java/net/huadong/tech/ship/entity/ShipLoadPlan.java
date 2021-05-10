package net.huadong.tech.ship.entity;

import java.io.Serializable;
import javax.persistence.*;

import net.huadong.tech.base.bean.AuditEntityBean;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the SHIP_LOAD_PLAN database table.
 * 
 */
@Entity
@Table(name="SHIP_LOAD_PLAN")
@NamedQuery(name="ShipLoadPlan.findAll", query="SELECT s FROM ShipLoadPlan s")
public class ShipLoadPlan extends AuditEntityBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_PLAN_NO", sequenceName = "SEQ_PLAN_NO",allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "SEQ_PLAN_NO")
	@Column(name="PLAN_NO")
	private String planNo;

	@Column(name="BILL_NO")
	private String billNo;

	@Column(name="BRAND_COD")
	private String brandCod;

	@Column(name="CAR_KIND")
	private String carKind;

	@Column(name="CAR_NUM")
	private BigDecimal carNum;

	@Column(name="CAR_TYP")
	private String carTyp;

	@Column(name="CONSIGN_COD")
	private String consignCod;

	@Column(name="CONSIGN_NAM")
	private String consignNam;

	@Column(name="DISC_PORT_COD")
	private String discPortCod;

	@Column(name="DOCK_COD")
	private String dockCod;

	private BigDecimal height;

	@Column(name="I_E_ID")
	private String iEId;

	private BigDecimal length;

	@Column(name="LOAD_PORT_COD")
	private String loadPortCod;

	private String plac;

	private String remarks;

	@Column(name="SHIP_NAM")
	private String shipNam;

	@Column(name="SHIP_NO")
	private String shipNo;

	@Column(name="TRADE_ID")
	private String tradeId;

	@Column(name="TRAN_PORT_COD")
	private String tranPortCod;

	
	@Column(name="\"VOLUMES\"")
	private BigDecimal volumes;

	private String voyage;

	private BigDecimal weights;

	private BigDecimal width;
	
	@Column(name="WORK_SEQ_NO")
	private BigDecimal workSeqNo;
	
	@Column(name="TELE")
	private String tele;
	
	@Transient
	private String cClientNam;
	
	@Transient 
	private String carKindNam;

	@Transient 
	private String carTypNam;
	
	@Transient 
	private String loadPortNam;
	
	@Transient 
	private String tranPortNam;
	
	@Transient 
	private String discPortNam;
	
	@Transient 
	private String dockNam;
	
	@Transient
	private String cyAreaNam;
	public String getCyAreaNam() {
		return cyAreaNam;
	}

	public void setCyAreaNam(String cyAreaNam) {
		this.cyAreaNam = cyAreaNam;
	}

	public ShipLoadPlan() {
	}

	public String getPlanNo() {
		return this.planNo;
	}

	public void setPlanNo(String planNo) {
		this.planNo = planNo;
	}

	public String getiEId() {
		return iEId;
	}

	public void setiEId(String iEId) {
		this.iEId = iEId;
	}
	public String getBillNo() {
		return this.billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public String getBrandCod() {
		return this.brandCod;
	}

	public void setBrandCod(String brandCod) {
		this.brandCod = brandCod;
	}

	public String getCarKind() {
		return this.carKind;
	}

	public void setCarKind(String carKind) {
		this.carKind = carKind;
	}

	public BigDecimal getCarNum() {
		return this.carNum;
	}

	public void setCarNum(BigDecimal carNum) {
		this.carNum = carNum;
	}

	public String getCarTyp() {
		return this.carTyp;
	}

	public void setCarTyp(String carTyp) {
		this.carTyp = carTyp;
	}

	public String getConsignCod() {
		return this.consignCod;
	}

	public void setConsignCod(String consignCod) {
		this.consignCod = consignCod;
	}

	public String getConsignNam() {
		return this.consignNam;
	}

	public void setConsignNam(String consignNam) {
		this.consignNam = consignNam;
	}

	public String getDiscPortCod() {
		return this.discPortCod;
	}

	public void setDiscPortCod(String discPortCod) {
		this.discPortCod = discPortCod;
	}

	public String getDockCod() {
		return this.dockCod;
	}

	public void setDockCod(String dockCod) {
		this.dockCod = dockCod;
	}

	public BigDecimal getHeight() {
		return this.height;
	}

	public void setHeight(BigDecimal height) {
		this.height = height;
	}

	public BigDecimal getLength() {
		return this.length;
	}

	public void setLength(BigDecimal length) {
		this.length= length;
	}

	public String getLoadPortCod() {
		return this.loadPortCod;
	}

	public void setLoadPortCod(String loadPortCod) {
		this.loadPortCod = loadPortCod;
	}

	public String getPlac() {
		return this.plac;
	}

	public void setPlac(String plac) {
		this.plac = plac;
	}


	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getShipNam() {
		return this.shipNam;
	}

	public void setShipNam(String shipNam) {
		this.shipNam = shipNam;
	}

	public String getShipNo() {
		return this.shipNo;
	}

	public void setShipNo(String shipNo) {
		this.shipNo = shipNo;
	}

	public String getTradeId() {
		return this.tradeId;
	}

	public void setTradeId(String tradeId) {
		this.tradeId = tradeId;
	}

	public String getTranPortCod() {
		return this.tranPortCod;
	}

	public void setTranPortCod(String tranPortCod) {
		this.tranPortCod = tranPortCod;
	}

	public BigDecimal getVolumes() {
		return this.volumes;
	}

	public void setVolumes(BigDecimal volumes) {
		this.volumes = volumes;
	}

	public String getVoyage() {
		return this.voyage;
	}

	public void setVoyage(String voyage) {
		this.voyage = voyage;
	}

	public BigDecimal getWeights() {
		return this.weights;
	}

	public void setWeights(BigDecimal weights) {
		this.weights = weights;
	}

	public BigDecimal getWidth() {
		return this.width;
	}

	public void setWidth(BigDecimal width) {
		this.width = width;
	}

	public BigDecimal getWorkSeqNo() {
		return this.workSeqNo;
	}

	public void setWorkSeqNo(BigDecimal workSeqNo) {
		this.workSeqNo = workSeqNo;
	}

	
	public String getcClientNam() {
		return cClientNam;
	}

	public void setcClientNam(String cClientNam) {
		this.cClientNam = cClientNam;
	}

	public String getCarKindNam() {
		return carKindNam;
	}

	public void setCarKindNam(String carKindNam) {
		this.carKindNam = carKindNam;
	}

	public String getCarTypNam() {
		return carTypNam;
	}

	public void setCarTypNam(String carTypNam) {
		this.carTypNam = carTypNam;
	}

	public String getLoadPortNam() {
		return loadPortNam;
	}

	public void setLoadPortNam(String loadPortNam) {
		this.loadPortNam = loadPortNam;
	}

	public String getTranPortNam() {
		return tranPortNam;
	}

	public void setTranPortNam(String tranPortNam) {
		this.tranPortNam = tranPortNam;
	}

	public String getDiscPortNam() {
		return discPortNam;
	}

	public void setDiscPortNam(String discPortNam) {
		this.discPortNam = discPortNam;
	}

	public String getDockNam() {
		return dockNam;
	}

	public void setDockNam(String dockNam) {
		this.dockNam = dockNam;
	}

	public String getTele() {
		return tele;
	}

	public void setTele(String tele) {
		this.tele = tele;
	}

}