package net.huadong.tech.map.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class GeoObjectFactory {
	private static final String SHIP_NO = "SHIP_NO";
	private static final String SHIPXY = "SHIPXY";
	public static final FeatureCollection buildCurWorkPostionCollection(List<Map<String, Object>> ships) {
		FeatureCollection fc = (FeatureCollection) create(FeatureCollection.class);

		return fc;
	}
	
	public static final Feature createFeature(String id,String loc){
		Feature f=(Feature)create(Feature.class);
		Point p=(Point) create(Point.class);
		p.setCoordinate(loc);
		f.getProps().put("t", new Date());
		f.getProps().put("id", id);
		f.setGeometry(p);
		return f;
	}
	

	
	public static final FeatureCollection buildGraph(List<Map<String,Object>> datas, FeatureCreater creater){
		FeatureCollection fc = (FeatureCollection) create(FeatureCollection.class);
		if (fc != null && datas != null && !datas.isEmpty()) {
			for (int i=0;i<datas.size();i++) {
				creater.create(datas.get(i),fc,i);
			}
		}
		return fc;
	}
	
	public static final FeatureCollection buildBargoFeatureCollection(List<Map<String, Object>> ships){
		FeatureCollection fc = (FeatureCollection) create(FeatureCollection.class);
		if(ships!=null&&!ships.isEmpty()){
			for(Map m:ships){
				fc.addFeature(bargeFeature(m));
			}
		}
		return fc;
	}
	private static Feature bargeFeature(Map<String,Object> b){
		Feature f=(Feature) create(Feature.class);
		f.setProps(b);
		String ship_xy=(String)b.get("SHIP_XY");
		String x="";
		String y="";
		String lb;
		if(ship_xy!=null&&!"".equals(ship_xy)){
			String[] poss=ship_xy.split(";");
			if(poss.length==2){
				x=poss[1].substring(0,poss[1].indexOf(","));
				y=poss[1].substring(poss[1].indexOf(",")+1);
			}
			lb=poss[0];
		}
		Point p =new Point();
		p.setCoordinate("["+x+","+y+"]");
		f.setGeometry(p);
		return f;
	}

	public static boolean isNotEmpty(String res) {
		return res != null && !"".equals(res);
	}
	public static GeoObject create(Class target) {
		if (GeoObject.class.isAssignableFrom(target)) {
			try {
				return (GeoObject) target.newInstance();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	private String getCenterPoint(String cords){
		String temp=cords.replace("[", "").replace("]","");
		String[] points=temp.split(",");
		BigDecimal bx=new BigDecimal(0),by=new BigDecimal(0);
		BigDecimal bt;
		int length=points.length/2;
		BigDecimal divd=new BigDecimal(length);
		for(int i=0;i<length;i++){
			bt=new BigDecimal(points[i*2]);
			bx=bx.add(bt);
			bt=new BigDecimal(points[i*2+1]);
			by=by.add(bt);
		}
		bx=bx.divide(divd);
		by=by.divide(divd);
		return "["+bx.toString()+","+by.toString()+"]";
	}
	
	private static String getCenterXMaxyPoint(String cords){
		String temp=cords.replace("[", "").replace("]","");
		String[] points=temp.split(",");
		BigDecimal bx=new BigDecimal(0),bmx=new BigDecimal(0),by=new BigDecimal(0) ,bmy=new BigDecimal(0);
		BigDecimal bt;
		int length=points.length/2;
		BigDecimal divd=new BigDecimal(length),tow=new BigDecimal(2);
		for(int i=0;i<length;i++){
			bt=new BigDecimal(points[i*2]);
			if(bmx.doubleValue()==0)
				bmx=bt;
			if(bt.doubleValue()<bmx.doubleValue())
				bmx=bt;
			bx=bx.add(bt);
			bt=new BigDecimal(points[i*2+1]);
			
			if(bt.doubleValue()>by.doubleValue()){
				by=bt;		
		    }
			if(bmy.intValue()==0)
				bmy=bt;
			else{
				if(bt.doubleValue()<bmy.doubleValue())
					bmy=bt;
			}
		}
		bx=bx.divide(divd,5).add(bmx).divide(tow);
		
		by=by.add(bmy).divide(tow,5).add(by).divide(tow);
		return bx.toString()+","+by.toString();
	}
	
	public interface FeatureCreater{
		Feature create(Map<String,Object> data,FeatureCollection fc,double index);
	}
}
