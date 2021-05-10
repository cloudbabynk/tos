package net.huadong.tech.privilege.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.*;

import org.eclipse.persistence.annotations.UuidGenerator;

import java.util.Date;


/**
 * The persistent class for the SYS_NO_SEQ database table.
 * wxl 序列号生成
 */
@Entity
@Table(name="SYS_NO_SEQ")
@NamedQuery(name="SysNoSeq.findAll", query="SELECT s FROM SysNoSeq s")
public class SysNoSeq implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
    @UuidGenerator(name = "UUID")
    @GeneratedValue(generator = "UUID")
	@Column(name="SEQ_ID")
	private String seqId;

	@Column(name="ENTITY_NAME")
	private String entityName;

	@Column(name="NOW_NO")
	private int nowNo;

	
	@Column(name="SEQ_DATE")//如果有相同日期,则nowNo+1,没有则新增
	private Timestamp seqDate;

	public SysNoSeq() {
	}

	public String getSeqId() {
		return this.seqId;
	}

	public void setSeqId(String seqId) {
		this.seqId = seqId;
	}

	public String getEntityName() {
		return this.entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public int getNowNo() {
		return this.nowNo;
	}

	public void setNowNo(int nowNo) {
		this.nowNo = nowNo;
	}

	public Timestamp getSeqDate() {
		return seqDate;
	}

	public void setSeqDate(Timestamp seqDate) {
		this.seqDate = seqDate;
	}

	

}