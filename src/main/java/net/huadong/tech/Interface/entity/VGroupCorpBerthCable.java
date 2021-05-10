package net.huadong.tech.Interface.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the V_GROUP_CORP_BERTH_CABLE database table.
 * 
 */
@Entity
@Table(name="V_GROUP_CORP_BERTH_CABLE")
@NamedQuery(name="VGroupCorpBerthCable.findAll", query="SELECT v FROM VGroupCorpBerthCable v")
public class VGroupCorpBerthCable implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="BERTH_CODE")
	private String berthCode;

	@Column(name="CABLE_CODE")
	private String cableCode;

	public VGroupCorpBerthCable() {
	}

	public String getBerthCode() {
		return this.berthCode;
	}

	public void setBerthCode(String berthCode) {
		this.berthCode = berthCode;
	}

	public String getCableCode() {
		return this.cableCode;
	}

	public void setCableCode(String cableCode) {
		this.cableCode = cableCode;
	}

}