package net.huadong.tech.base.entity;

import java.io.Serializable;
import javax.persistence.*;

import net.huadong.tech.base.bean.AuditEntityBean;

import java.util.Date;


/**
 * The persistent class for the C_EMP_TYP database table.
 * 
 */
@Entity
@Table(name="C_EMP_TYP")
@NamedQuery(name="CEmpTyp.findAll", query="SELECT c FROM CEmpTyp c")
public class CEmpTyp extends AuditEntityBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="EMP_TYP_COD")
	private String empTypCod;

	@Column(name="EMP_TYP_NAM")
	private String empTypNam;
	
	@Column(name="REMARKS")
	private String remarks;

	public CEmpTyp() {
	}

	public String getEmpTypCod() {
		return this.empTypCod;
	}

	public void setEmpTypCod(String empTypCod) {
		this.empTypCod = empTypCod;
	}

	public String getEmpTypNam() {
		return this.empTypNam;
	}

	public void setEmpTypNam(String empTypNam) {
		this.empTypNam = empTypNam;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}



}