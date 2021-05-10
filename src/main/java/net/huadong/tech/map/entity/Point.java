package net.huadong.tech.map.entity;

import java.util.Map;

public class Point  extends Geometry{

	private static final long serialVersionUID = 1L;
	private String coordinate;
	public Point() {
		super("Point");
		// TODO Auto-generated constructor stub
	}
	
	public String getCoordinate() {
		return coordinate;
	}

	public void setCoordinate(String coordinate) {
		this.coordinate = coordinate;
	}

	@Override
	public Map<String, Object> toMap() {
		Map<String,Object> m= super.toMap();
		m.put("coordinate", coordinate);
		return m;
	}

	@Override
	public String toGson() {
		 return "{\"type\":\""+this.getType()+"\",\"coordinates\":"+this.coordinate+"}";
	}

}
