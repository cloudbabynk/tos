package net.huadong.tech.map.entity;

import java.io.Serializable;

import net.huadong.tech.util.HdUtils;

/**
 * 图形化堆场结构
 * @author liudy
 *
 */
public class ContainerMap implements Serializable  {

	private static final long serialVersionUID = 1L;

	private String portCarNo;
	private String col;
	private String areaNo;
	private String rowNo;
	private String bayNo;
	private String companyCod;
	private String pos;
	private String txt;
	private String vinNo;
	private String inCyTime;
	
	
	
	public ContainerMap(String portCarNo, String areaNo, String rowNo, String bayNo, String companyCod,	String pos,String txt,String vinNo) {
		super();
		this.portCarNo = portCarNo;
		
		this.areaNo = areaNo;
		this.rowNo = rowNo;
		this.bayNo = bayNo;
		this.companyCod = companyCod;
		this.pos = pos;
		if(HdUtils.strIsNull(portCarNo)||portCarNo.equals("null")) this.col="#18E5F7";	
		else this.col="#39EE15";
		this.txt=txt;
		this.vinNo=vinNo;
	}
	public String getPortCarNo() {
		return portCarNo;
	}
	public void setPortCarNo(String portCarNo) {
		this.portCarNo = portCarNo;
	}
	public String getCol() {
		return col;
	}
	public void setCol(String col) {
		this.col = col;
	}
	public String getAreaNo() {
		return areaNo;
	}
	public void setAreaNo(String areaNo) {
		this.areaNo = areaNo;
	}
	public String getRowNo() {
		return rowNo;
	}
	public void setRowNo(String rowNo) {
		this.rowNo = rowNo;
	}
	public String getBayNo() {
		return bayNo;
	}
	public void setBayNo(String bayNo) {
		this.bayNo = bayNo;
	}
	public String getCompanyCod() {
		return companyCod;
	}
	public void setCompanyCod(String companyCod) {
		this.companyCod = companyCod;
	}
	public String getPos() {
		return pos;
	}
	public void setPos(String pos) {
		this.pos = pos;
	}
	/**
	 * @return the txt
	 */
	public String getTxt() {
		return txt;
	}
	/**
	 * @param txt the txt to set
	 */
	public void setTxt(String txt) {
		this.txt = txt;
	}
	public String getVinNo() {
		return vinNo;
	}
	public void setVinNo(String vinNo) {
		this.vinNo = vinNo;
	}
	public String getInCyTime() {
		return inCyTime;
	}
	public void setInCyTime(String inCyTime) {
		this.inCyTime = inCyTime;
	}
	
	
}
