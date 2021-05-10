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
public class CoorUtile {
	// 点到直线的最短距离的判断 点（x0,y0） 到由两点组成的线段（x1,y1） ,( x2,y2 )
		public static double pointToLine(double x1, double y1, double x2, double y2, double x0, double y0) {  
	         double space = 0;  
	         double a, b, c;  
	         a = lineSpace(x1, y1, x2, y2);// 线段的长度  
	         b = lineSpace(x1, y1, x0, y0);// (x1,y1)到点的距离  
	         c = lineSpace(x2, y2, x0, y0);// (x2,y2)到点的距离  
	         if (c+b == a) {//点在线段上  
	             space = 0;  
	             return space;  
	         }  
	         if (a <= 0.000001) {//不是线段，是一个点  
	             space = b;  
	             return space;  
	         }  
	         if (c * c >= a * a + b * b) { //组成直角三角形或钝角三角形，(x1,y1)为直角或钝角  
	        	 //System.out.println("组成直角三角形或钝角三角形，(x1,y1)为直角或钝角  ");
	             space = b;  
	             return space;  
	         }  
	         if (b * b >= a * a + c * c) {//组成直角三角形或钝角三角形，(x2,y2)为直角或钝角  
	        	 //System.out.println("组成直角三角形或钝角三角形，(x2,y2)为直角或钝角 ");
	             space = c;  
	             return space;  
	         }  
	         //组成锐角三角形，则求三角形的高  
	         //System.out.println("组成锐角三角形，则求三角形的高  ");
	         double p = (a + b + c) / 2;// 半周长  
	         double s = Math.sqrt(p * (p - a) * (p - b) * (p - c));// 海伦公式求面积  
	         space = 2 * s / a;// 返回点到线的距离（利用三角形面积公式求高）  
	         return space;  
	     }  
	 
	     //计算两点之间的距离  
		 public static double lineSpace(double x1, double y1, double x2, double y2) {  
	         double lineLength = 0;  
	         lineLength = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));  
	         return lineLength;  
	     } 

	
	
}
