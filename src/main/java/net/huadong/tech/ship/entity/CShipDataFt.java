package net.huadong.tech.ship.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.*;

import net.huadong.tech.base.bean.AuditEntityBean;

import java.util.Date;


/**
 * The persistent class for the C_SHIP_DATA_FT database table.
 * 
 */
@Entity
@Table(name="C_SHIP_DATA_FT")
@NamedQuery(name="CShipDataFt.findAll", query="SELECT c FROM CShipDataFt c")
public class CShipDataFt extends AuditEntityBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
//	@GeneratedValue(strategy=GenerationType.TABLE)
	@Column(name="SHIP_DATA_FT_ID")
	private String shipDataFtId;

//	@Column(name="REC_NAM")
//	private String recNam;
//
//	@Column(name="REC_TIM")
//	private Timestamp recTim;

	@Column(name="SHIP_COD_ID")
	private String shipCodId;

//	@Column(name="UPD_NAM")
//	private String updNam;
//
//	@Column(name="UPD_TIM")
//	private Timestamp updTim;

	public String getShipCodId() {
		return shipCodId;
	}

	@Column(name="VC_SHIP_ID")
	private String vcShipId;

	@Column(name="VC_SHIP_NAME")
	private String vcShipName;
	
	@Transient
	private String shipDataName;

	public String getShipDataName() {
		return shipDataName;
	}

	public void setShipDataName(String shipDataName) {
		this.shipDataName = shipDataName;
	}

	public CShipDataFt() {
	}

	public String getShipDataFtId() {
		return this.shipDataFtId;
	}

	public void setShipDataFtId(String shipDataFtId) {
		this.shipDataFtId = shipDataFtId;
	}

//	public Timestamp getUpdTim() {
//		return updTim;
//	}
//
//	public void setUpdTim(Timestamp updTim) {
//		this.updTim = updTim;
//	}

	public void setShipCodId(String shipCodId) {
		this.shipCodId = shipCodId;
	}

	public String getVcShipId() {
		return this.vcShipId;
	}

	public void setVcShipId(String vcShipId) {
		this.vcShipId = vcShipId;
	}

	public String getVcShipName() {
		return this.vcShipName;
	}

	public void setVcShipName(String vcShipName) {
		this.vcShipName = vcShipName;
	}

}