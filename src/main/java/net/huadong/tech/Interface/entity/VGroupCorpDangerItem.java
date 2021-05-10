package net.huadong.tech.Interface.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the V_GROUP_CORP_DANGER_ITEM database table.
 * 
 */
@Entity
@Table(name="V_GROUP_CORP_DANGER_ITEM")
@NamedQuery(name="VGroupCorpDangerItem.findAll", query="SELECT v FROM VGroupCorpDangerItem v")
public class VGroupCorpDangerItem implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String code;

	private String name;

	public VGroupCorpDangerItem() {
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