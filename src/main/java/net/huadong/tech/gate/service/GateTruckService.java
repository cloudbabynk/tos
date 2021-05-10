package net.huadong.tech.gate.service;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.gate.entity.GateTruck;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;

/**
 * @author 
 */
public interface GateTruckService {
	HdEzuiDatagridData find(HdQuery hdQuery);
	HdEzuiDatagridData findCar(HdQuery hdQuery);
	HdMessageCode save(HdEzuiSaveDatagridData<GateTruck> gridData,String contractNo,String singleId,String truckNo,String planNum,String inGatTim,String gateNo);
	void removeAll(String ids);
	GateTruck findone(String id);
	HdMessageCode saveone(GateTruck gateTruck);
}
