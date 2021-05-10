package net.huadong.tech.Interface.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the V_GROUP_CORP_FLOW database table.
 * 
 */
@Entity
@Table(name="V_GROUP_CORP_FLOW")
@NamedQuery(name="VGroupCorpFlow.findAll", query="SELECT v FROM VGroupCorpFlow v")
public class VGroupCorpFlow implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="DATA_FLAG")
	private String dataFlag;

	@Column(name="FLOW_FLAG")
	private String flowFlag;

	@Id
	@Column(name="ORIGIN_CODE")
	private String originCode;

	@Column(name="ORIGIN_NAME")
	private String originName;

	@Column(name="PRODUCT_FLAG")
	private String productFlag;

	public VGroupCorpFlow() {
	}

	public String getDataFlag() {
		return this.dataFlag;
	}

	public void setDataFlag(String dataFlag) {
		this.dataFlag = dataFlag;
	}

	public String getFlowFlag() {
		return this.flowFlag;
	}

	public void setFlowFlag(String flowFlag) {
		this.flowFlag = flowFlag;
	}

	public String getOriginCode() {
		return this.originCode;
	}

	public void setOriginCode(String originCode) {
		this.originCode = originCode;
	}

	public String getOriginName() {
		return this.originName;
	}

	public void setOriginName(String originName) {
		this.originName = originName;
	}

	public String getProductFlag() {
		return this.productFlag;
	}

	public void setProductFlag(String productFlag) {
		this.productFlag = productFlag;
	}

}