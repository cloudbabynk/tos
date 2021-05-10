package net.huadong.tech.Interface.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the V_GROUP_CORP_LOCATION database table.
 * 
 */
@Entity
@Table(name="V_GROUP_CORP_LOCATION")
@NamedQuery(name="VGroupCorpLocation.findAll", query="SELECT v FROM VGroupCorpLocation v")
public class VGroupCorpLocation implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="LOCATION_CODE")
	private String locationCode;

	@Id
	@Column(name="TEAM_CODE")
	private String teamCode;

	@Column(name="YARD_CODE")
	private String yardCode;

	public VGroupCorpLocation() {
	}

	public String getLocationCode() {
		return this.locationCode;
	}

	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}

	public String getTeamCode() {
		return this.teamCode;
	}

	public void setTeamCode(String teamCode) {
		this.teamCode = teamCode;
	}

	public String getYardCode() {
		return this.yardCode;
	}

	public void setYardCode(String yardCode) {
		this.yardCode = yardCode;
	}

}