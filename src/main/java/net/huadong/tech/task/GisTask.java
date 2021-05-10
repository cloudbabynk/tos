package net.huadong.tech.task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.persistence.config.QueryHints;
import org.eclipse.persistence.config.ResultType;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import net.huadong.tech.base.entity.CCyArea;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;

@Component
@Configurable
@EnableScheduling
public class GisTask {

	//在场车辆信息
	private static List<Map> gzPortCarlst=null;
	private static List<Map> hqPortCarLst=null;
	
	private static Map<String,Map> gzMapPortCar=null;
	private static Map<String,Map> hqMapPortCar=null;
	
	//每3分钟执行一次
    @Scheduled(cron = "0 */2 *  * * * ")
    public void reportCurrentByCron(){	
    	getPortCarList("03406500");
    	getPortCarList("03409000");
    }
    
    public static void getPortCarList(String dockCod) {
    	String tabName="";
    	 if(dockCod.equals("03406500")) {
    		 tabName="GZ_CONTAINER";
		 }else {
			 tabName="CONTAINER";
		 }
    	System.out.println("dockCod:"+dockCod+" port info loading...");
		String sql=" SELECT /*+ USE_HASH(T1,T) ORDERED */ "+
				" t1.port_car_no, t1.vin_no, t1.bill_no, "+
				" (SELECT car_typ_nam FROM c_car_typ m WHERE m.car_typ = t1.car_typ) car_typ_nam, "+
				" (SELECT brand_nam FROM c_brand m WHERE m.brand_cod = t1.brand_cod) brand_cod, "+
				" t.blockno,  t.rowno,  t.bayno, t.companycod,  h.c_ship_nam, "+
				" s.name i_e_nam, t1.i_e_id, t1.trade_id, c.name trade_nam, to_char(t1.IN_CY_TIM,'yyyy-MM-dd HH:mm') IN_CY_TIM, "+
				" t.pos, t1.TRADE_ID || t1.I_E_ID  colour_set"+
				" FROM "+tabName+" t, port_car t1,ship h "+
				" , (SELECT code, name FROM sys_code WHERE field_cod = 'I_E_ID') s, "+
				" (SELECT code, name FROM sys_code WHERE field_cod = 'TRADE_ID') c "+
				" WHERE t.blockno = t1.cy_area_no(+) "+
				" AND t.rowno = t1.cy_row_no "+
				" AND t.bayno = t1.cy_bay_no "+
				" AND t1.ship_no = h.ship_no "+
				" AND t1.i_e_id = s.code(+)  and t1.CURRENT_STAT='2' AND t1.trade_id = c.code（+） "+
				" AND t.companycod = '"+dockCod+"' "+
				"ORDER BY t.blockno, t.rowno, t.bayno ";
		 List<Map> portCarlst=JpaUtils.getEntityManager().createNativeQuery(sql).setHint(QueryHints.RESULT_TYPE, ResultType.Map).getResultList();
		 //滚装:dockCod="03406500";
		 //环球:dockCod="03409000";
		 if(dockCod.equals("03406500")) {
			 gzMapPortCar=new HashMap<String,Map>();
			 for (Map map : portCarlst) {
				 gzMapPortCar.put(map.get("PORT_CAR_NO")+"", map);
			 }
		 }else {
			 hqMapPortCar=new HashMap<String,Map>();
			 for (Map map : portCarlst) {
				 hqMapPortCar.put(map.get("PORT_CAR_NO")+"", map);
			}
		 }
		 
    }

	public static List<Map> getGzPortCarlst() {
		hqPortCarLst=new ArrayList<Map>(); 
		for (Map.Entry<String, Map> m : gzMapPortCar.entrySet()) {
			hqPortCarLst.add(m.getValue()); 
		}
		return hqPortCarLst;
	}

	public static void setGzPortCarlst(List<Map> gzPortCarlst) {
		GisTask.gzPortCarlst = gzPortCarlst;
	}

	public static List<Map> getHqPortCarLst() {
		gzPortCarlst=new ArrayList<Map>(); 
		for (Map.Entry<String, Map> m : hqMapPortCar.entrySet()) {
			gzPortCarlst.add(m.getValue()); 
		}
		return gzPortCarlst;
	}

	public static void setHqPortCarLst(List<Map> hqPortCarLst) {
		GisTask.hqPortCarLst = hqPortCarLst;
	}

	public static Map<String, Map> getGzMapPortCar() {
		if(gzMapPortCar==null) gzMapPortCar=new HashMap<String,Map>(); 
		return gzMapPortCar;
	}

	public static void setGzMapPortCar(Map<String, Map> gzMapPortCar) {
		GisTask.gzMapPortCar = gzMapPortCar;
	}

	public static Map<String, Map> getHqMapPortCar() {
		if(hqMapPortCar==null) hqMapPortCar=new HashMap<String,Map>(); 
		return hqMapPortCar;
	}

	public static void setHqMapPortCar(Map<String, Map> hqMapPortCar) {
		GisTask.hqMapPortCar = hqMapPortCar;
	}
    




}
