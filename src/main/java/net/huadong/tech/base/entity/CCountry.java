package net.huadong.tech.base.entity;

import java.io.Serializable;
import javax.persistence.*;

import net.huadong.tech.base.bean.AuditEntityBean;

import java.util.Date;


/**
 * The persistent class for the C_COUNTRY database table.
 * 
 */
@Entity
@Table(name="C_COUNTRY")
@NamedQuery(name="CCountry.findAll", query="SELECT c FROM CCountry c")
public class CCountry extends AuditEntityBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="COUNTRY_COD")
	private String countryCod;

	@Column(name="AREA_COD")
	private String areaCod;

	@Column(name="C_COUNTRY_NAM")
	private String cCountryNam;

	@Column(name="COUNTRY_SHORT")
	private String countryShort;

	@Column(name="E_COUNTRY_NAM")
	private String eCountryNam;

	public CCountry() {
	}

	public String getCountryCod() {
		return this.countryCod;
	}

	public void setCountryCod(String countryCod) {
		this.countryCod = countryCod;
	}

	public String getAreaCod() {
		return this.areaCod;
	}

	public void setAreaCod(String areaCod) {
		this.areaCod = areaCod;
	}

	public String getcCountryNam() {
		return this.cCountryNam;
	}

	public void setcCountryNam(String cCountryNam) {
		this.cCountryNam = cCountryNam;
	}

	public String getCountryShort() {
		return this.countryShort;
	}

	public void setCountryShort(String countryShort) {
		this.countryShort = countryShort;
	}

	public String geteCountryNam() {
		return this.eCountryNam;
	}

	public void seteCountryNam(String eCountryNam) {
		this.eCountryNam = eCountryNam;
	}

}