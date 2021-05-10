package net.huadong.tech.Interface.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the V_GROUP_CORP_PORT database table.
 * 
 */
@Entity
@Table(name="V_GROUP_CORP_PORT")
@NamedQuery(name="VGroupCorpPort.findAll", query="SELECT v FROM VGroupCorpPort v")
public class VGroupCorpPort implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="PORT_CODE")
	private String portCode;

	@Column(name="PORT_NAME")
	private String portName;

	public VGroupCorpPort() {
	}

	public String getPortCode() {
		return this.portCode;
	}

	public void setPortCode(String portCode) {
		this.portCode = portCode;
	}

	public String getPortName() {
		return this.portName;
	}

	public void setPortName(String portName) {
		this.portName = portName;
	}

}