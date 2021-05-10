package net.huadong.tech.privilege.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.eclipse.persistence.annotations.UuidGenerator;

import net.huadong.tech.base.bean.AuditEntityBean;


/**
 * The persistent class for the SYS_FIELD database table.
 * 
 */
@Entity
@Table(name="SYS_FIELD")
@NamedQuery(name="SysField.findAll", query="SELECT s FROM SysField s")
public class SysField extends AuditEntityBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(generator = "UUID")
    @UuidGenerator(name = "UUID")
	@Column(name="FIELD_ID")
	private String fieldId;

	@Column(name="FIELD_COD")
	private String fieldCod;

	@Column(name="FIELD_NAME")
	private String fieldName;

	@Column(name="SORTER")
	private String sorter;

	public SysField() {
	}

	public String getFieldId() {
		return this.fieldId;
	}

	public void setFieldId(String fieldId) {
		this.fieldId = fieldId;
	}

	public String getFieldCod() {
		return this.fieldCod;
	}

	public void setFieldCod(String fieldCod) {
		this.fieldCod = fieldCod;
	}

	public String getFieldName() {
		return this.fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getSorter() {
		return sorter;
	}

	public void setSorter(String sorter) {
		this.sorter = sorter;
	}


}