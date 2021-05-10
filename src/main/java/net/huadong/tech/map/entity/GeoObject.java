package net.huadong.tech.map.entity;

import java.util.HashMap;
import java.util.Map;

public abstract class GeoObject implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static String DY="'";
	private String type;
	
	protected GeoObject(String type) {
		super();
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public abstract String toGson();

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GeoObject other = (GeoObject) obj;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}
	
	public Map<String,Object> toMap(){
		 Map<String,Object> m=new HashMap<String,Object>();
		m.put("type", type);
		return m;
	}
	
	
}
