package net.huadong.tech.Interface.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the V_GROUP_CORP_CARGO database table.
 * 
 */
@Entity
@Table(name="V_GROUP_CORP_CARGO")
@NamedQuery(name="VGroupCorpCargo.findAll", query="SELECT v FROM VGroupCorpCargo v")
public class VGroupCorpCargo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CARGO_CODE")
	private String cargoCode;

	@Column(name="CARGO_NAME")
	private String cargoName;
	
	@Column(name="TYPE_FLAG")
	private String typeFlag;

	public String getTypeFlag() {
		return typeFlag;
	}

	public void setTypeFlag(String typeFlag) {
		this.typeFlag = typeFlag;
	}

	public VGroupCorpCargo() {
	}

	public String getCargoCode() {
		return this.cargoCode;
	}

	public void setCargoCode(String cargoCode) {
		this.cargoCode = cargoCode;
	}

	public String getCargoName() {
		return this.cargoName;
	}

	public void setCargoName(String cargoName) {
		this.cargoName = cargoName;
	}

}