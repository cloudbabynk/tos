package net.huadong.tech.map.service;


import java.util.Map;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.entity.CCyArea;

/**
 * @author 
 */
public interface MapFeatureService {
	/**
	 * 
	 * @param cyArea
	 * @return
	 */
	public HdMessageCode gensubCargo( CCyArea cyArea);
	
	//public HdMessageCode getPortCarMap( HdQuery hdQuery);
	public HdMessageCode getPortCarMap(Map cntrMap) throws Exception;
	//库存查询
	public HdMessageCode getPortCarSearchLocal(Map cntrMap) throws Exception;
	 //查询定位
	public HdMessageCode getPortCarLocal(Map cntrMap) throws Exception;
	//初始化库存库场
	public HdMessageCode initPortCarByDock(String dockCod);
	
	/**
	 * 倒运
	 * @param dockCod
	 * @return
	 */
	public HdMessageCode transPortCar(Map map);
	
	//多车辆位置加载
	public HdMessageCode mulGenPortCarPostion(Map portCarInfo);
	
}
