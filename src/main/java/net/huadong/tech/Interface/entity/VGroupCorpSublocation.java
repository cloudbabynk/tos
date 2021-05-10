package net.huadong.tech.Interface.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the V_GROUP_CORP_SUBLOCATION database table.
 * 
 */
@Entity
@Table(name="V_GROUP_CORP_SUBLOCATION")
@NamedQuery(name="VGroupCorpSublocation.findAll", query="SELECT v FROM VGroupCorpSublocation v")
public class VGroupCorpSublocation implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="LOCATION_CODE")
	private String locationCode;

	@Id
	@Column(name="SUBLOCATION_CODE")
	private String sublocationCode;

	@Column(name="YARD_CODE")
	private String yardCode;

	public VGroupCorpSublocation() {
	}

	public String getLocationCode() {
		return this.locationCode;
	}

	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}

	public String getSublocationCode() {
		return this.sublocationCode;
	}

	public void setSublocationCode(String sublocationCode) {
		this.sublocationCode = sublocationCode;
	}

	public String getYardCode() {
		return this.yardCode;
	}

	public void setYardCode(String yardCode) {
		this.yardCode = yardCode;
	}

}