package net.huadong.tech.Interface.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the V_GROUP_CORP_DANGER_KIND database table.
 * 
 */
@Entity
@Table(name="V_GROUP_CORP_DANGER_KIND")
@NamedQuery(name="VGroupCorpDangerKind.findAll", query="SELECT v FROM VGroupCorpDangerKind v")
public class VGroupCorpDangerKind implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String code;

	private String name;

	public VGroupCorpDangerKind() {
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