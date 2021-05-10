package net.huadong.tech.base.entity;

import java.io.Serializable;
import javax.persistence.*;

import net.huadong.tech.base.bean.AuditEntityBean;

import java.util.Date;


/**
 * The persistent class for the C_EMPLOYEE database table.
 * 
 */
@Entity
@Table(name="C_EMPLOYEE")
@NamedQuery(name="CEmployee.findAll", query="SELECT c FROM CEmployee c")
public class CEmployee extends AuditEntityBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static final String TYP_SJ = "08";
	
	public static final String TYP_GZ_01 = "10"; //滚装一班
	
	public static final String TYP_GZ_02 = "11"; //滚装二班
	
	public static final String TYP_GZ_03 = "12"; //滚装三班
	
	public static final String TYP_GZ_04 = "13"; //滚装四班
	
	public static final String TYP_GZ_05 = "15"; //滚装白班

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="EMP_NO")
	private String empNo;

	@Column(name="CLASS_NO")
	private String classNo;

	@Column(name="EMP_NAM")
	private String empNam;

	@Column(name="EMP_TYP_COD")
	private String empTypCod;

	@Column(name="ON_DUTY_ID")
	private String onDutyId;

	@Column(name="REMARKS")
	private String remarks;

	@Column(name="SEX")
	private String sex;
	
	@Column(name="SYS_USER_NAM")
	private String sysUserNam;
	
	@Transient
	private String empTypCodNam;
	
	@Transient
	private String classNoNam;
	
	@Transient
	private boolean checked;
	
	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public String getSysUserNam() {
		return sysUserNam;
	}

	public void setSysUserNam(String sysUserNam) {
		this.sysUserNam = sysUserNam;
	}

	public String getClassNoNam() {
		return classNoNam;
	}

	public void setClassNoNam(String classNoNam) {
		this.classNoNam = classNoNam;
	}

	public CEmployee() {
	}

	public String getEmpTypCodNam() {
		return empTypCodNam;
	}



	public void setEmpTypCodNam(String empTypCodNam) {
		this.empTypCodNam = empTypCodNam;
	}



	public String getEmpNo() {
		return empNo;
	}


	public void setEmpNo(String empNo) {
		this.empNo = empNo;
	}


	public String getClassNo() {
		return classNo;
	}


	public void setClassNo(String classNo) {
		this.classNo = classNo;
	}


	public String getEmpNam() {
		return empNam;
	}


	public void setEmpNam(String empNam) {
		this.empNam = empNam;
	}


	public String getEmpTypCod() {
		return empTypCod;
	}


	public void setEmpTypCod(String empTypCod) {
		this.empTypCod = empTypCod;
	}


	public String getOnDutyId() {
		return onDutyId;
	}


	public void setOnDutyId(String onDutyId) {
		this.onDutyId = onDutyId;
	}


	public String getRemarks() {
		return remarks;
	}


	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}


	public String getSex() {
		return sex;
	}


	public void setSex(String sex) {
		this.sex = sex;
	}


}