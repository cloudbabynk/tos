package net.huadong.tech.Interface.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the V_GROUP_CORP_BERTH database table.
 * 
 */
@Entity
@Table(name="V_GROUP_CORP_BERTH")
@NamedQuery(name="VGroupCorpBerth.findAll", query="SELECT v FROM VGroupCorpBerth v")
public class VGroupCorpBerth implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="BERTH_CODE")
	private String berthCode;

	@Column(name="BERTH_NAME")
	private String berthName;

	@Id
	@Column(name="TEAM_ORGN_ID")
	private String teamOrgnId;

	public VGroupCorpBerth() {
	}

	public String getBerthCode() {
		return this.berthCode;
	}

	public void setBerthCode(String berthCode) {
		this.berthCode = berthCode;
	}

	public String getBerthName() {
		return this.berthName;
	}

	public void setBerthName(String berthName) {
		this.berthName = berthName;
	}

	public String getTeamOrgnId() {
		return this.teamOrgnId;
	}

	public void setTeamOrgnId(String teamOrgnId) {
		this.teamOrgnId = teamOrgnId;
	}

}