package net.huadong.tech.shipbill.entity;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import java.sql.Timestamp;


/**
 * The persistent class for the PORT_CAR database table.
 * 
 */
@Entity
@Table(name="V_PORT_CAR_STA")

public class VPortCarSta implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="ROW_ID")
	private BigDecimal rowId;
	@Column(name="BRAND_COD")
	private String brandCod;
	
	@Column(name="CAR_KIND")
	private String carKind;
	@Column(name="CAR_TYP")
	private String carTyp;
	@Column(name="CY_AREA_NO")
	private String cyAreaNo;
	@Column(name="I_E_ID")
	private String iEId;
	@Column(name="IN_CY_TIM")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd") 
	private Timestamp inCyTim;
	@Column(name="SHIP_NO")
	private String shipNo;
	@Column(name="TRADE_ID")
	private String tradeId;
	@Column(name="TRAN_PORT_COD")
	private String tranPortCod;
	@Column(name="C_SHIP_NAM")
	private String cShipNam;
	@Column(name="VOYAGE")
	private String voyage;
	@Column(name="CAR_NUM")
	private int rksl;
	
	@Column(name="CAR_TYP_NAM")
	private String carTypNam;
	
	@Column(name="CY_AREA_NAM")
	private String cyAreaNam;
	
	@Column(name="BRAND_NAM")
	private String brandNam;
	@Column(name="CAR_KIND_NAM")
	private String carKindNam;
	
	@Column(name="PORT_NAM")
	private String tranPortNam;
	
	public BigDecimal getRowId() {
		return rowId;
	}
	public void setRowId(BigDecimal rowId) {
		this.rowId = rowId;
	}
	
	public String getCyAreaNam() {
		return cyAreaNam;
	}
	public void setCyAreaNam(String cyAreaNam) {
		this.cyAreaNam = cyAreaNam;
	}
	public String getBrandCod() {
		return brandCod;
	}
	public void setBrandCod(String brandCod) {
		this.brandCod = brandCod;
	}
	public String getCarKind() {
		return carKind;
	}
	public void setCarKind(String carKind) {
		this.carKind = carKind;
	}
	public String getCarTyp() {
		return carTyp;
	}
	public void setCarTyp(String carTyp) {
		this.carTyp = carTyp;
	}
	public String getCyAreaNo() {
		return cyAreaNo;
	}
	public void setCyAreaNo(String cyAreaNo) {
		this.cyAreaNo = cyAreaNo;
	}
	public String getiEId() {
		return iEId;
	}
	public void setiEId(String iEId) {
		this.iEId = iEId;
	}
	public Timestamp getInCyTim() {
		return inCyTim;
	}
	public void setInCyTim(Timestamp inCyTim) {
		this.inCyTim = inCyTim;
	}
	public String getShipNo() {
		return shipNo;
	}
	public void setShipNo(String shipNo) {
		this.shipNo = shipNo;
	}
	public String getTradeId() {
		return tradeId;
	}
	public void setTradeId(String tradeId) {
		this.tradeId = tradeId;
	}
	public String getTranPortCod() {
		return tranPortCod;
	}
	public void setTranPortCod(String tranPortCod) {
		this.tranPortCod = tranPortCod;
	}
	public String getcShipNam() {
		return cShipNam;
	}
	public void setcShipNam(String cShipNam) {
		this.cShipNam = cShipNam;
	}
	public String getVoyage() {
		return voyage;
	}
	public void setVoyage(String voyage) {
		this.voyage = voyage;
	}

	public String getCarTypNam() {
		return carTypNam;
	}
	public void setCarTypNam(String carTypNam) {
		this.carTypNam = carTypNam;
	}
	public String getBrandNam() {
		return brandNam;
	}
	public void setBrandNam(String brandNam) {
		this.brandNam = brandNam;
	}
	public String getCarKindNam() {
		return carKindNam;
	}
	public void setCarKindNam(String carKindNam) {
		this.carKindNam = carKindNam;
	}
	public int getRksl() {
		return rksl;
	}
	public void setRksl(int rksl) {
		this.rksl = rksl;
	}
	public String getTranPortNam() {
		return tranPortNam;
	}
	public void setTranPortNam(String tranPortNam) {
		this.tranPortNam = tranPortNam;
	}


	
	


}