package net.huadong.tech.base.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import net.huadong.tech.base.bean.AuditEntityBean;


/**
 * The persistent class for the S_PARAM database table.
 * 
 */
@Entity
@Table(name="S_PARAM")
@NamedQuery(name="SParam.findAll", query="SELECT s FROM SParam s")
public class SParam extends AuditEntityBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	/*@Basic(optional = false)
	@UuidGenerator(name = "UUID")
	@GeneratedValue(generator = "UUID")
	@NotNull*/
	@Column(name="PARAM_ID")
	private String paramId;

	@Column(name="PARAM_COD")
	private String paramCod;

	@Column(name="PARAM_NAM")
	private String paramNam;

	@Column(name="PARAM_VAL")
	private String paramVal;

	private String remarks;

	public SParam() {
	}

	public String getParamId() {
		return this.paramId;
	}

	public void setParamId(String paramId) {
		this.paramId = paramId;
	}

	public String getParamCod() {
		return this.paramCod;
	}

	public void setParamCod(String paramCod) {
		this.paramCod = paramCod;
	}

	public String getParamNam() {
		return this.paramNam;
	}

	public void setParamNam(String paramNam) {
		this.paramNam = paramNam;
	}

	public String getParamVal() {
		return this.paramVal;
	}

	public void setParamVal(String paramVal) {
		this.paramVal = paramVal;
	}

	

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	

}