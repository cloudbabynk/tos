package net.huadong.tech.map.entity;

import java.util.Map;

public class Polygon extends Geometry {

	private static final long serialVersionUID = 1L;
	private String coordinates;

	public Polygon() {
		super("Polygon");
	}

	public String getCoordinates() {
		return coordinates;
	}
	
	@Override
	public Map<String, Object> toMap() {
		Map<String,Object> m= super.toMap();
		m.put("coordinates", coordinates);
		return m;
	}

	public void setCoordinates(String coordinates) {
		if(coordinates.indexOf(";")>0){
			coordinates=coordinates.replace(";", ",");
			String[] cs=coordinates.split(",");
			int points=cs.length/3;
			StringBuffer sb=new StringBuffer("[[");
			for(int i=0;i<points;i++){
				if(i>0){
					sb.append(",");
				}
				sb.append("[").append(cs[i*3+1]).append(",").append(cs[i*3+2]).append("]");
			}
			sb.append("]]");
			coordinates=sb.toString();
		}
		this.coordinates = coordinates;
	}
	public void setShipCoordinates(String coordinates) {
		if(coordinates.indexOf(";")>0){
			String[] cs=coordinates.split(";");
			StringBuffer sb=new StringBuffer("[[");
			for(int i=0;i<cs.length;i++){
				if(i>0){
					sb.append(",");
				}
				sb.append("["+cs[i]+"]");
			}
			sb.append("]]");
			coordinates=sb.toString();
		}
		this.coordinates = coordinates;
	}
	@Override
	public String toGson() {
		return "{\"type\":\""+this.getType()+"\",\"coordinates\":"+this.coordinates+"}";
	}

}
