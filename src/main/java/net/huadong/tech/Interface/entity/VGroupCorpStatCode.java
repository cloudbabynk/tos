package net.huadong.tech.Interface.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the V_GROUP_CORP_STAT_CODE database table.
 * 
 */
@Entity
@Table(name="V_GROUP_CORP_STAT_CODE")
@NamedQuery(name="VGroupCorpStatCode.findAll", query="SELECT v FROM VGroupCorpStatCode v")
public class VGroupCorpStatCode implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String code;

	private String name;

	public VGroupCorpStatCode() {
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