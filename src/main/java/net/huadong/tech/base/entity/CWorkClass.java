package net.huadong.tech.base.entity;

import java.io.Serializable;
import javax.persistence.*;

import net.huadong.tech.base.bean.AuditEntityBean;

import java.util.Date;


/**
 * The persistent class for the C_WORK_CLASS database table.
 * 
 */
@Entity
@Table(name="C_WORK_CLASS")
@NamedQuery(name="CWorkClass.findAll", query="SELECT c FROM CWorkClass c")
public class CWorkClass extends AuditEntityBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CLASS_COD")
	private String classCod;

	@Column(name="CLASS_NAM")
	private String classNam;

	public CWorkClass() {
	}

	public String getClassCod() {
		return this.classCod;
	}

	public void setClassCod(String classCod) {
		this.classCod = classCod;
	}

	public String getClassNam() {
		return this.classNam;
	}

	public void setClassNam(String classNam) {
		this.classNam = classNam;
	}

}