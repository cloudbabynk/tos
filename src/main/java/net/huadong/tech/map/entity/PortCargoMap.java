package net.huadong.tech.map.entity;

import java.io.Serializable;
/**
 * 图形化库存结构
 * @author liudy
 *
 */
public class PortCargoMap implements Serializable  {

	private static final long serialVersionUID = 1L;

	private String col;
	private String areaNo;
	private String rowNo;
	private String bayNo;
	private String portCarNo;
	private String companyCod;
	private String pos;
	private String txt;
	private String ieNam;
	private String traidNam;
	private String carTyp;
	private String shipNam;
	private String billNo;
	private String vinNo;
	
	private String bandCod;
	private String carKind;
	private String inTime;
			
			
			
	
	public PortCargoMap(String areaNo, String rowNo, String bayNo, String portCarNo, String companyCod,
			String pos,String txt,String ieNam,String traidNam,String carTyp,String shipNam,String billNo, String vinNo) {
		super();
		this.col="#FF0033"; 
		this.areaNo = areaNo;
		this.rowNo = rowNo;
		this.bayNo = bayNo;
		this.portCarNo = portCarNo;
		this.companyCod = companyCod;
		this.pos = pos;
		this.txt=txt;
		this.ieNam=ieNam;
		this.traidNam=traidNam;
		this.carTyp=carTyp;
		this.shipNam=shipNam;
		this.billNo=billNo;
		this.vinNo=vinNo;
	}
	
	public PortCargoMap(String col, String areaNo, String rowNo, String bayNo, String portCarNo, String companyCod,
			String pos,String txt,String ieNam,String traidNam,String carTyp,String shipNam,String billNo, String vinNo) {
		super();
		if(col.equals("null")) this.col="#18E5F7";
		else this.col = col;
		switch (col) {
		case "1I"://内贸进口  绿色
			this.col="#00FF40";
			break;
		case "1E"://内贸出口  黄色
			this.col="#FFFF71";
			break;
		case "2I"://外贸进口  蓝色
			this.col="#0000FF";
			break;
		case "2E"://外贸出口  橙色
			this.col="#FF8000";
			break;
		default:
			this.col="#18E5F7";
			break;
		}
		this.areaNo = areaNo;
		this.rowNo = rowNo;
		this.bayNo = bayNo;
		this.portCarNo = portCarNo;
		this.companyCod = companyCod;
		this.pos = pos;
		this.txt=txt;
		this.ieNam=ieNam;
		this.traidNam=traidNam;
		this.carTyp=carTyp;
		this.shipNam=shipNam;
		this.billNo=billNo;
		this.vinNo=vinNo;
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
	public String getPortCarNo() {
		return portCarNo;
	}
	public void setPortCarNo(String portCarNo) {
		this.portCarNo = portCarNo;
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
	public String getIeNam() {
		return ieNam;
	}
	public void setIeNam(String ieNam) {
		this.ieNam = ieNam;
	}
	public String getTraidNam() {
		return traidNam;
	}
	public void setTraidNam(String traidNam) {
		this.traidNam = traidNam;
	}
	public String getCarTyp() {
		return carTyp;
	}
	public void setCarTyp(String carTyp) {
		this.carTyp = carTyp;
	}
	public String getShipNam() {
		return shipNam;
	}
	public void setShipNam(String shipNam) {
		this.shipNam = shipNam;
	}

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public String getVinNo() {
		return vinNo;
	}

	public void setVinNo(String vinNo) {
		this.vinNo = vinNo;
	}

	public String getBandCod() {
		return bandCod;
	}

	public void setBandCod(String bandCod) {
		this.bandCod = bandCod;
	}

	public String getCarKind() {
		return carKind;
	}

	public void setCarKind(String carKind) {
		this.carKind = carKind;
	}

	public String getInTime() {
		return inTime;
	}

	public void setInTime(String inTime) {
		this.inTime = inTime;
	}
	
}
