package net.huadong.tech.cargo.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="SHIP_MASTER")
@NamedQuery(name="ShipMaster.findAll", query="SELECT s FROM ShipMaster s")
public class ShipMaster implements Serializable {
	private static final long serialVersionUID = 1L;
	
	
	@Id
	@Column(name="ID")
	private String id;

	@Column(name="VC_SHIP_ID")
	private String vcShipID;
	
	@Column(name="VC_SHIP_NAME")
	private String vcShipName;
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getVcShipID() {
		return vcShipID;
	}

	public void setVcShipID(String vcShipID) {
		this.vcShipID = vcShipID;
	}

	public String getVcShipName() {
		return vcShipName;
	}

	public void setVcShipName(String vcShipName) {
		this.vcShipName = vcShipName;
	}

}
