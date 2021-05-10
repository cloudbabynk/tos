package net.huadong.tech.map.entity;

public class LineString extends Geometry {
	protected  String coordinates;

	public LineString() {
		super("LineString");
	}

	
	
	public LineString( String coordinates) {
		super("LineString");
		this.coordinates = coordinates;
	}



	@Override
	public String toGson() {
		 return "{\"type\":\""+this.getType()+"\",\"coordinates\":"+this.coordinates+"}";
	}

	public String getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(String coordinates) {
		this.coordinates = coordinates;
	}
	public void orgnCoordinates(String coordinates) {
		if(coordinates.indexOf(";")>0){
			String[] cs=coordinates.split(";");
			StringBuffer sb=new StringBuffer("[");
			for(int i=0;i<cs.length;i++){
				if(i>0){
					sb.append(",");
				}
				sb.append("["+cs[i]+"]");
			}
			sb.append("]");
			coordinates=sb.toString();
		}
		this.coordinates = coordinates;
	}

}
