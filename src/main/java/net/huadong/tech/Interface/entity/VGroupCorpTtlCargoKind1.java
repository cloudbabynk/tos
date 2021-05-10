package net.huadong.tech.Interface.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the V_GROUP_CORP_TTL_CARGO_KIND1 database table.
 * 
 */
@Entity
@Table(name="V_GROUP_CORP_TTL_CARGO_KIND1")
@NamedQuery(name="VGroupCorpTtlCargoKind1.findAll", query="SELECT v FROM VGroupCorpTtlCargoKind1 v")
public class VGroupCorpTtlCargoKind1 implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String code;

	private String name;

	public VGroupCorpTtlCargoKind1() {
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}