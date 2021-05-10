package net.huadong.tech.Interface.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the V_GROUP_CORP_YARD database table.
 * 
 */
@Entity
@Table(name="V_GROUP_CORP_YARD")
@NamedQuery(name="VGroupCorpYard.findAll", query="SELECT v FROM VGroupCorpYard v")
public class VGroupCorpYard implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="TEAM_ORGN_ID")
	private String teamOrgnId;

	@Id
	@Column(name="YARD_CODE")
	private String yardCode;

	@Column(name="YARD_NAME")
	private String yardName;

	public VGroupCorpYard() {
	}

	public String getTeamOrgnId() {
		return this.teamOrgnId;
	}

	public void setTeamOrgnId(String teamOrgnId) {
		this.teamOrgnId = teamOrgnId;
	}

	public String getYardCode() {
		return this.yardCode;
	}

	public void setYardCode(String yardCode) {
		this.yardCode = yardCode;
	}

	public String getYardName() {
		return this.yardName;
	}

	public void setYardName(String yardName) {
		this.yardName = yardName;
	}

}