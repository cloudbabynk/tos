package net.huadong.tech.util;

import java.util.Comparator;

import net.huadong.tech.ship.entity.ShipStatGroup;

public class ComparatorU implements Comparator {
	public int compare(Object obj0, Object obj1) {
		ShipStatGroup shipStatGroup0=(ShipStatGroup)obj0;
		ShipStatGroup shipStatGroup1=(ShipStatGroup)obj1;

		   //首先比较年龄，如果年龄相同，则比较名字

		  int flag=shipStatGroup0.getQssj().compareTo(shipStatGroup1.getQssj());
		  if(flag==0){
		   return shipStatGroup0.getJssj().compareTo(shipStatGroup1.getJssj());
		  }else{
		   return flag;
		  }  
		 }
}
