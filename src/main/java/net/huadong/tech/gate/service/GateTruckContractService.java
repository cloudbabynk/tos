package net.huadong.tech.gate.service;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.gate.entity.GateTruckContract;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiQueryParams;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;

/**
 * @author 
 */
public interface GateTruckContractService {
	HdEzuiDatagridData find(HdQuery hdQuery);
	HdMessageCode save(HdEzuiSaveDatagridData<GateTruckContract> gridData,String singleId,String truckNo,String planNum,String inGatTim,String gateNo);
	void removeAll(String ids);
	GateTruckContract findone(String id);
	HdMessageCode saveone(GateTruckContract gateTruckContract);
}
