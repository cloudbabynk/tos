package net.huadong.tech.base.entity;

import java.io.Serializable;
import javax.persistence.*;

import net.huadong.tech.base.bean.AuditEntityBean;

import java.math.BigDecimal;


/**
 * The persistent class for the S_CODE database table.
 * 
 */
@Entity
@Table(name="S_CODE")
@NamedQuery(name="SCode.findAll", query="SELECT s FROM SCode s")
public class SCode implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="SCODE_ID")
	private String scodeId;

	@Column(name="CODE")
	private String code;

	@Column(name="DEF_VAL")
	private String defVal;

	@Column(name="FLD_CHI")
	private String fldChi;

	@Column(name="FLD_ENG")
	private String fldEng;

	@Column(name="FLD_ID")
	private String fldId;

	@Column(name="NAME")
	private String name;

	@Column(name="REMARKS")
	private String remarks;

	@Column(name="SEQ_NO")
	private BigDecimal seqNo;

	public SCode() {
	}

	public String getScodeId() {
		return this.scodeId;
	}

	public void setScodeId(String scodeId) {
		this.scodeId = scodeId;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDefVal() {
		return this.defVal;
	}

	public void setDefVal(String defVal) {
		this.defVal = defVal;
	}

	public String getFldChi() {
		return this.fldChi;
	}

	public void setFldChi(String fldChi) {
		this.fldChi = fldChi;
	}

	public String getFldEng() {
		return this.fldEng;
	}

	public void setFldEng(String fldEng) {
		this.fldEng = fldEng;
	}

	public String getFldId() {
		return this.fldId;
	}

	public void setFldId(String fldId) {
		this.fldId = fldId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public BigDecimal getSeqNo() {
		return this.seqNo;
	}

	public void setSeqNo(BigDecimal seqNo) {
		this.seqNo = seqNo;
	}

}