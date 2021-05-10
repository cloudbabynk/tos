package net.huadong.tech.base.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the C_SHIP_SWITCH database table.
 * 
 */
@Entity
@Table(name="C_SHIP_SWITCH")
@NamedQuery(name="CShipSwitch.findAll", query="SELECT c FROM CShipSwitch c")
public class CShipSwitch implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="SHIP_ID")
	private String shipId;

	@Column(name="GROUP_SHIP_COD")
	private String groupShipCod;

	@Column(name="REC_NAM")
	private String recNam;

	@Temporal(TemporalType.DATE)
	@Column(name="REC_TIM")
	private Date recTim;

	@Column(name="SHIP_COD")
	private String shipCod;

	@Column(name="UPD_NAM")
	private String updNam;

	@Temporal(TemporalType.DATE)
	@Column(name="UPD_TIM")
	private Date updTim;

	public CShipSwitch() {
	}

	public String getShipId() {
		return this.shipId;
	}

	public void setShipId(String shipId) {
		this.shipId = shipId;
	}

	public String getGroupShipCod() {
		return this.groupShipCod;
	}

	public void setGroupShipCod(String groupShipCod) {
		this.groupShipCod = groupShipCod;
	}

	public String getRecNam() {
		return this.recNam;
	}

	public void setRecNam(String recNam) {
		this.recNam = recNam;
	}

	public Date getRecTim() {
		return this.recTim;
	}

	public void setRecTim(Date recTim) {
		this.recTim = recTim;
	}

	public String getShipCod() {
		return this.shipCod;
	}

	public void setShipCod(String shipCod) {
		this.shipCod = shipCod;
	}

	public String getUpdNam() {
		return this.updNam;
	}

	public void setUpdNam(String updNam) {
		this.updNam = updNam;
	}

	public Date getUpdTim() {
		return this.updTim;
	}

	public void setUpdTim(Date updTim) {
		this.updTim = updTim;
	}

}