package net.huadong.tech.work.entity;

import java.io.Serializable;
import javax.persistence.*;

import net.huadong.tech.base.bean.AuditEntityBean;


/**
 * The persistent class for the WORK_BILL_DRIVER_CLASS database table.
 * 
 */
@Entity
@Table(name="WORK_BILL_DRIVER_CLASS")
@IdClass(WorkBillDriverClassPK.class)
@NamedQuery(name="WorkBillDriverClass.findAll", query="SELECT w FROM WorkBillDriverClass w")
public class WorkBillDriverClass extends AuditEntityBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="WORKBILL_NO")
	private String workbillNo;

	@Id
	@Column(name="CLASS_COD")
	private String classCod;
	
	@Transient
	private String classCodNam;
	
	public String getClassCodNam() {
		return classCodNam;
	}

	public void setClassCodNam(String classCodNam) {
		this.classCodNam = classCodNam;
	}

	public String getWorkbillNo() {
		return workbillNo;
	}

	public void setWorkbillNo(String workbillNo) {
		this.workbillNo = workbillNo;
	}



	public String getClassCod() {
		return classCod;
	}



	public void setClassCod(String classCod) {
		this.classCod = classCod;
	}



	public WorkBillDriverClass() {
	}


}