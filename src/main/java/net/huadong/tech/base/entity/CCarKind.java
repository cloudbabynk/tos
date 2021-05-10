package net.huadong.tech.base.entity;

import java.io.Serializable;
import javax.persistence.*;

import net.huadong.tech.base.bean.AuditEntityBean;

import java.util.Date;


/**
 * The persistent class for the C_CAR_KIND database table.
 * 
 */
@Entity
@Table(name="C_CAR_KIND")
@NamedQuery(name="CCarKind.findAll", query="SELECT c FROM CCarKind c")
public class CCarKind extends AuditEntityBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CAR_KIND")
	private String carKind;

	@Column(name="CAR_KIND_NAM")
	private String carKindNam;

	@Column(name="REMARKS")
	private String remarks;
	
	@Column(name="CHECK_FLAG")
	private String checkFlag;
	
	@Column(name="GROUP_CAR_KIND")
	private String groupCarKind;


	public CCarKind() {
	}

	public String getCarKind() {
		return this.carKind;
	}

	public void setCarKind(String carKind) {
		this.carKind = carKind;
	}

	public String getCarKindNam() {
		return this.carKindNam;
	}

	public void setCarKindNam(String carKindNam) {
		this.carKindNam = carKindNam;
	}

	public String getGroupCarKind() {
		return groupCarKind;
	}

	public void setGroupCarKind(String groupCarKind) {
		this.groupCarKind = groupCarKind;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getCheckFlag() {
		return checkFlag;
	}

	public void setCheckFlag(String checkFlag) {
		this.checkFlag = checkFlag;
	}

	
}