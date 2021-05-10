package net.huadong.tech.map.util;

import java.awt.geom.Point2D;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * gis的通用类
 * @author liudy
 *
 */
public class GisUtil {
	/**
	 * 去掉map的相同列
	 * @param 传入map
	 * @return 传出map
	 */
	public static Map RemoveDupMap(Map map) {
		Map map2 = new HashMap();
		Map map3 = new HashMap();
		// TreeMap:对map按key值排序
		TreeMap treemap = new TreeMap<String, String>(map);
		Iterator<String> it = treemap.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next();
			Object value = treemap.get(key);
			if (map2.containsKey(value)) {
				continue;
			} else {
				map2.put(value, value);
				map3.put(key, value);
			}
		}
		return map3;
	}

	/**
	 * 整理openlayers3 中 点最后一个点的处理 及其结合排序
	 * @param posArr
	 * 所有点排序后 pt1,pt2,pt3,pt4
	 *  3___4
	 *  |   |
	 *  |   |
	 * 1|___|2
	 * @return
	 */
	public static Map<String, Point2D.Double> ListArrPt(Map<String, Point2D.Double> map) {
		Map<String, Point2D.Double> arrMap = new HashMap<String, Point2D.Double>();
		List<Double> lsArr = new ArrayList<Double>();
		for (String key : map.keySet()) {
			lsArr.add(map.get(key).getX() + map.get(key).getY());
		}

		// 冒泡排序

		for (int i = 0; i < lsArr.size(); i++) {
			for (int j = 0; j < lsArr.size() - i - 1; j++) {
				// 这里-i主要是每遍历一次都把最大的i个数沉到最底下去了，没有必要再替换了
				if (lsArr.get(j) > lsArr.get(j + 1)) {
					double temp = lsArr.get(j);
					lsArr.set(j, lsArr.get(j + 1));
					lsArr.set(j + 1, temp);
				}
			}
		}

		for (int i = 0; i < lsArr.size(); i++) {
			double douPos = lsArr.get(i);
			for (String key : map.keySet()) {
				if (douPos == map.get(key).getX() + map.get(key).getY()) {
					arrMap.put(i + "", map.get(key));
				}
			}
		}
		Map<String, Point2D.Double>  rMap=new HashMap<String, Point2D.Double>();
		
		if(arrMap.get("1").getX()<arrMap.get("2").getX())
		{
			rMap.put("0", arrMap.get("0"));
			rMap.put("1", arrMap.get("2"));
			rMap.put("2", arrMap.get("1"));
			rMap.put("3", arrMap.get("3"));
		}else
		{
			rMap.putAll(arrMap);
		}
		
		return rMap;
	}
	
	/**
	 * 
	 * @param long1
	 * @param lat1
	 * @param long2
	 * @param lat2
	 * @return
	 */
	public static double Distance(double long1, double lat1, double long2,   double lat2) {  
	    double a, b, R;  
	    R = 6378137; // 地球半径  
	    lat1 = lat1 * Math.PI / 180.0;  
	    lat2 = lat2 * Math.PI / 180.0;  
	    a = lat1 - lat2;  
	    b = (long1 - long2) * Math.PI / 180.0;  
	    double d;  
	    double sa2, sb2;  
	    sa2 = Math.sin(a / 2.0);  
	    sb2 = Math.sin(b / 2.0);  
	    d = 2   * R    * Math.asin(Math.sqrt(sa2 * sa2 + Math.cos(lat1)   * Math.cos(lat2) * sb2 * sb2));  
	    return d;  
	}
	
	// 根据长度比例 和线段上2点的坐标 获取比例上的点
	public static Point2D.Double getBlPoint(double lenBlx,double lenBly,  Point2D.Double pt1,Point2D.Double pt2) {
		double ptx = pt1.getX() + ((pt2.getX() - pt1.getX()) * lenBlx);
		double pty = pt1.getY() + ((pt2.getY() - pt1.getY()) * lenBly);
		return new Point2D.Double(ptx, pty);
	}

	// 根据长度比例 和线段上2点的坐标 获取比例上的点
	public static Point2D.Double getBlPoint(BigDecimal lenBlx,BigDecimal lenBly,  Point2D.Double pt1,Point2D.Double pt2) {
		double ptx = pt1.getX() + ((pt2.getX() - pt1.getX()) * lenBlx.doubleValue());
		double pty = pt1.getY() + ((pt2.getY() - pt1.getY()) * lenBly.doubleValue());
		return new Point2D.Double(ptx, pty);
	}
	/**
	 * 整理openlayers3 中 点最后一个点的处理 及其结合排序
	 * @param posArr
	 * 所有点排序后 pt1,pt2,pt3,pt4
	 *  3___4
	 *  |   |
	 *  |   |
	 * 1|___|2
	 * @return
	 */
	public static Map<String, Point2D.Double> modifyOpenLayersPos(String[] posArr)
	{
		double xpos, ypos;
		Point2D.Double pt;
		Map<String, Point2D.Double> mapP = new HashMap<String, Point2D.Double>();
		String[] posxy;

		for (int i = 0; i < posArr.length; i++) {
			if(i==posArr.length-1) continue;
			
			posxy = posArr[i].split(",");
			xpos = Double.parseDouble(posxy[0]);
			ypos = Double.parseDouble(posxy[1]);
			pt = new Point2D.Double(new BigDecimal(xpos).setScale(6,BigDecimal.ROUND_HALF_UP).doubleValue(), new BigDecimal(ypos).setScale(6,BigDecimal.ROUND_HALF_UP).doubleValue());
			mapP.put(i + "", pt);
		}
		return RemoveDupMap(mapP);
	}
	
	public static String splitAreaPos(String pos) {
		String[] posArray=pos.split(",");
		StringBuffer t=new StringBuffer();
		for (int i = 0; i < posArray.length; i++) {
		
			if(i==0) {
				t.append(posArray[i]);
			}else if(i%2==0) {
				t.append(";"+posArray[i]);
			}else {
				t.append(","+posArray[i]);
			}
		}
		return t.toString();
	}
	

}
