package net.huadong.tech.Interface.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the V_GROUP_CORP_SHIP_LINE database table.
 * 
 */
@Entity
@Table(name="V_GROUP_CORP_SHIP_LINE")
@NamedQuery(name="VGroupCorpShipLine.findAll", query="SELECT v FROM VGroupCorpShipLine v")
public class VGroupCorpShipLine implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="SHIP_LINE_CODE")
	private String shipLineCode;

	@Column(name="SHIP_LINE_NAME")
	private String shipLineName;

	public VGroupCorpShipLine() {
	}

	public String getShipLineCode() {
		return this.shipLineCode;
	}

	public void setShipLineCode(String shipLineCode) {
		this.shipLineCode = shipLineCode;
	}

	public String getShipLineName() {
		return this.shipLineName;
	}

	public void setShipLineName(String shipLineName) {
		this.shipLineName = shipLineName;
	}

}