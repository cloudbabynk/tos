package net.huadong.tech.ship.entity;

public class Shipcount {
    private String berthId;
    private String cableId;
	private double length;
	private String name;
	private double seqNo;
	private double rate;
	private String fullNam;
	private double zbx;
	
	
	public double getZbx() {
		return zbx;
	}
	public void setZbx(double zbx) {
		this.zbx = zbx;
	}
	public String getFullNam() {
		return fullNam;
	}
	public void setFullNam(String fullNam) {
		this.fullNam = fullNam;
	}
	public String getCableId() {
		return cableId;
	}
	public void setCableId(String cableId) {
		this.cableId = cableId;
	}
	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}
	public String getBerthId() {
		return berthId;
	}
	public void setBerthId(String berthId) {
		this.berthId = berthId;
	}
	public double getSeqNo() {
		return seqNo;
	}
	public void setSeqNo(double seqNo) {
		this.seqNo = seqNo;
	}
	public double getLength() {
		return length;
	}
	public void setLength(double length) {
		this.length = length;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
