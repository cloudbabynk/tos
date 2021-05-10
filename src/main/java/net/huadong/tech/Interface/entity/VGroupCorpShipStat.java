package net.huadong.tech.Interface.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the V_GROUP_CORP_SHIP_STAT database table.
 * 
 */
@Entity
@Table(name="V_GROUP_CORP_SHIP_STAT")
@NamedQuery(name="VGroupCorpShipStat.findAll", query="SELECT v FROM VGroupCorpShipStat v")
public class VGroupCorpShipStat implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="SHIP_STAT_CODE")
	private String shipStatCode;

	@Column(name="SHIP_STAT_NAME")
	private String shipStatName;

	public VGroupCorpShipStat() {
	}

	public String getShipStatCode() {
		return this.shipStatCode;
	}

	public void setShipStatCode(String shipStatCode) {
		this.shipStatCode = shipStatCode;
	}

	public String getShipStatName() {
		return this.shipStatName;
	}

	public void setShipStatName(String shipStatName) {
		this.shipStatName = shipStatName;
	}

}