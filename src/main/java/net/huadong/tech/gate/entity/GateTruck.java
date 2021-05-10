package net.huadong.tech.gate.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the GATE_TRUCK database table.
 * 
 */
@Entity
@Table(name="GATE_TRUCK")
@NamedQuery(name="GateTruck.findAll", query="SELECT g FROM GateTruck g")
public class GateTruck implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_INGATE_ID", sequenceName = "SEQ_INGATE_ID",allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "SEQ_INGATE_ID")
	@Column(name="INGATE_ID")
	private String ingateId;

	@Column(name="DOCK_COD")
	private String dockCod;

	@Column(name="FINISHED_ID")
	private String finishedId;

	@Column(name="FINISHED_OPER")
	private String finishedOper;

	@Column(name="FINISHED_TIM")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm") 
	private Timestamp finishedTim;

	@Column(name="IN_GAT_NO")
	private String inGatNo;

	@Column(name="IN_GAT_TIM")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm") 
	private Timestamp inGatTim;

	@Column(name="IN_REC_NAM")
	private String inRecNam;

	@Column(name="OUT_GAT_NAM")
	private String outGatNam;

	@Column(name="OUT_GAT_NO")
	private String outGatNo;

	@Column(name="OUT_GAT_TIM")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
	private Timestamp outGatTim;

	@Column(name="PLAT_NO")
	private String platNo;

	@Column(name="SINGLE_ID")
	private String singleId;

	@Column(name="TRUCK_NO")
	private String truckNo;

	//bi-directional many-to-one association to GateTruckContract
	@OneToMany(mappedBy="gateTruck")
	private List<GateTruckContract> gateTruckContracts;

	public GateTruck() {
	}

	public String getIngateId() {
		return this.ingateId;
	}

	public void setIngateId(String ingateId) {
		this.ingateId = ingateId;
	}

	public String getDockCod() {
		return this.dockCod;
	}

	public void setDockCod(String dockCod) {
		this.dockCod = dockCod;
	}

	public String getFinishedId() {
		return this.finishedId;
	}

	public void setFinishedId(String finishedId) {
		this.finishedId = finishedId;
	}

	public String getFinishedOper() {
		return this.finishedOper;
	}

	public void setFinishedOper(String finishedOper) {
		this.finishedOper = finishedOper;
	}


	public String getInGatNo() {
		return this.inGatNo;
	}

	public void setInGatNo(String inGatNo) {
		this.inGatNo = inGatNo;
	}


	public String getInRecNam() {
		return this.inRecNam;
	}

	public void setInRecNam(String inRecNam) {
		this.inRecNam = inRecNam;
	}

	public String getOutGatNam() {
		return this.outGatNam;
	}

	public void setOutGatNam(String outGatNam) {
		this.outGatNam = outGatNam;
	}

	public String getOutGatNo() {
		return this.outGatNo;
	}

	public void setOutGatNo(String outGatNo) {
		this.outGatNo = outGatNo;
	}

	

	public Timestamp getFinishedTim() {
		return finishedTim;
	}

	public void setFinishedTim(Timestamp finishedTim) {
		this.finishedTim = finishedTim;
	}

	public Timestamp getInGatTim() {
		return inGatTim;
	}

	public void setInGatTim(Timestamp inGatTim) {
		this.inGatTim = inGatTim;
	}

	public Timestamp getOutGatTim() {
		return outGatTim;
	}

	public void setOutGatTim(Timestamp outGatTim) {
		this.outGatTim = outGatTim;
	}

	public String getPlatNo() {
		return this.platNo;
	}

	public void setPlatNo(String platNo) {
		this.platNo = platNo;
	}

	public String getSingleId() {
		return this.singleId;
	}

	public void setSingleId(String singleId) {
		this.singleId = singleId;
	}

	public String getTruckNo() {
		return this.truckNo;
	}

	public void setTruckNo(String truckNo) {
		this.truckNo = truckNo;
	}

	public List<GateTruckContract> getGateTruckContracts() {
		return this.gateTruckContracts;
	}

	public void setGateTruckContracts(List<GateTruckContract> gateTruckContracts) {
		this.gateTruckContracts = gateTruckContracts;
	}

	public GateTruckContract addGateTruckContract(GateTruckContract gateTruckContract) {
		getGateTruckContracts().add(gateTruckContract);
		gateTruckContract.setGateTruck(this);

		return gateTruckContract;
	}

	public GateTruckContract removeGateTruckContract(GateTruckContract gateTruckContract) {
		getGateTruckContracts().remove(gateTruckContract);
		gateTruckContract.setGateTruck(null);

		return gateTruckContract;
	}

}