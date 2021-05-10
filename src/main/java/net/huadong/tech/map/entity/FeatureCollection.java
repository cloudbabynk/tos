package net.huadong.tech.map.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FeatureCollection extends GeoObject{

	private static final long serialVersionUID = 1L;
	private String crs="\"crs\": {    \"type\": \"name\",    \"properties\": {      \"name\": \"EPSG:4326\"    }  }";
	private List<Feature> features;
	private Map<Object,Integer> fiTable;//feature 索引表
	protected FeatureCollection() {
		super("FeatureCollection");
		features=new ArrayList<Feature>();
		fiTable=new HashMap<Object,Integer>();
	}

	public void addFeature(Feature fa){
		this.features.add(fa);
		fiTable.put(fa.getId(), this.features.size()-1);
	}
	public Feature createFeature(Map map)
	{
		return null;
	}
	
	public int getFeatureSize(){
		return this.features.size();
	}

	@Override
	public String toGson() {
		StringBuffer sb=new StringBuffer("{");
		sb.append("\"type\":").append("\"").append(this.getType()).append("\",");
		sb.append(crs);
		sb.append(",\"features\":");
		if(!this.features.isEmpty()){
			int i=0;
			sb.append("[");
			for(Feature f:this.features){
				if(i>0)
					sb.append(",");
				sb.append(f.toGson());
				i++;
			}
			sb.append("]");
			
		}
		sb.append("");
		sb.append("}");
		return sb.toString();
	}
	
	public Feature getFeatureById(String id){
		if(this.fiTable.containsKey(id))
			return this.features.get(this.fiTable.get(id));
		return null;
	}
	
	
}
