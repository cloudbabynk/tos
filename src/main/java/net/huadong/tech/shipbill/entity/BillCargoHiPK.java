package net.huadong.tech.shipbill.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the BILL_CARGO_HIS database table.
 * 
 */
@Embeddable
public class BillCargoHiPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="SHIP_NO")
	private String shipNo;

	@Column(name="BILL_NO")
	private String billNo;

	@Column(name="I_E_ID")
	private String iEId;

	@Column(name="CARGO_COD")
	private String cargoCod;

	public BillCargoHiPK() {
	}
	public String getShipNo() {
		return this.shipNo;
	}
	public void setShipNo(String shipNo) {
		this.shipNo = shipNo;
	}
	public String getBillNo() {
		return this.billNo;
	}
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}
	public String getIEId() {
		return this.iEId;
	}
	public void setIEId(String iEId) {
		this.iEId = iEId;
	}
	public String getCargoCod() {
		return this.cargoCod;
	}
	public void setCargoCod(String cargoCod) {
		this.cargoCod = cargoCod;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof BillCargoHiPK)) {
			return false;
		}
		BillCargoHiPK castOther = (BillCargoHiPK)other;
		return 
			this.shipNo.equals(castOther.shipNo)
			&& this.billNo.equals(castOther.billNo)
			&& this.iEId.equals(castOther.iEId)
			&& this.cargoCod.equals(castOther.cargoCod);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.shipNo.hashCode();
		hash = hash * prime + this.billNo.hashCode();
		hash = hash * prime + this.iEId.hashCode();
		hash = hash * prime + this.cargoCod.hashCode();
		
		return hash;
	}
}