package net.huadong.tech.Interface.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the V_GROUP_CORP_MAHINE_TYPE database table.
 * 
 */
@Entity
@Table(name="V_GROUP_CORP_MAHINE_TYPE")
@NamedQuery(name="VGroupCorpMahineType.findAll", query="SELECT v FROM VGroupCorpMahineType v")
public class VGroupCorpMahineType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="TYPE_CODE")
	private String typeCode;

	@Column(name="TYPE_NAME")
	private String typeName;

	public VGroupCorpMahineType() {
	}

	public String getTypeCode() {
		return this.typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public String getTypeName() {
		return this.typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

}