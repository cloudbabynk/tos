package net.huadong.tech.map.entity;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Feature extends GeoObject {

	private static final long serialVersionUID = 1L;
	private Geometry geometry;
	private Map<String,Object> props;
	private String style;
	
	public Map<String,Object> getProps() {
		return props;
	}

	public Object getId(){
		return this.props.get("id");
	}
	
	public void setProps(Map<String, Object> props) {
		this.props = props;
	}

	@Override
	public String toGson() {
		StringBuffer sb=new StringBuffer("{");
		sb.append("\"type\":\"").append(this.getType()).append("\",");
		sb.append("\"id\":").append("\"").append(this.getId()).append("\",");
		if(style!=null&&!"".equals(style)){
			sb.append("\"style\":").append(style).append(",");
		}
		sb.append("\"geometry\":").append(geometry.toGson());
		if(props!=null&&!props.isEmpty()){
			Object value=null;
			
			sb.append(",\"properties\":{");
			int i=0;
			for(Object key:props.keySet()){
				value=props.get(key);
				if(value!=null&&!value.equals("")&&!value.equals("NULL")&&!value.equals("null"))
				{ 
					if(value instanceof Date) value=((Date)value).getTime();
					if(i>0) 
						sb.append(",\"").append(key).append("\":\"").append(value).append("\"");	
					else
						sb.append("\"").append(key).append("\":\"").append(value).append("\"");	
					i++;
				}
				
			}
			sb.append("}");
			
		}
		sb.append("}");
		return sb.toString();
	}
	
	public Geometry getGeometry() {
		return geometry;
	}

	public void setGeometry(Geometry geometry) {
		this.geometry = geometry;
	}

	public Feature() {
		super("Feature");
		props=new HashMap<String,Object>();
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}
	
	public Map toMap(){
		Map map=super.toMap();
		map.put("id", this.getId());
		map.put("geometry", this.geometry.toMap());
		map.put("props", this.props);
		map.put("t", this.props.get("t"));
		return map;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((geometry == null) ? 0 : geometry.hashCode());
		result = prime * result + ((props == null) ? 0 : props.hashCode());
		result = prime * result + ((style == null) ? 0 : style.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Feature other = (Feature) obj;
		if (geometry == null) {
			if (other.geometry != null)
				return false;
		} else if (!geometry.equals(other.geometry))
			return false;
		if (props == null) {
			if (other.props != null)
				return false;
		} else if (!props.equals(other.props))
			return false;
		if (style == null) {
			if (other.style != null)
				return false;
		} else if (!style.equals(other.style))
			return false;
		return true;
	}
}
