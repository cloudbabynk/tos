package net.huadong.tech.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

import net.huadong.tech.ship.entity.ShipGroup;
import net.huadong.tech.ship.entity.ShipStatGroup;

public class ComparatorX implements Comparator {
	public int compare(Object obj0, Object obj1) {
		ShipGroup shipGroup0 = (ShipGroup) obj0;
		ShipGroup shipGroup1 = (ShipGroup) obj1;
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 首先比较年龄，如果年龄相同，则比较名字
		Date a = new Date();
		Date b = new Date();
		try {
			a = sf.parse(shipGroup0.getDkrq());
			b = sf.parse(shipGroup1.getDkrq());
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		int flag = b.compareTo(a);
		return flag;

	}
}
