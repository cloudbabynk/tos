package net.huadong.tech.base.service;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.entity.CTruck;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiQueryParams;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;

/**
 * @author 
 */
public interface CTruckService {
	HdEzuiDatagridData find(HdQuery hdQuery);
	HdMessageCode save(HdEzuiSaveDatagridData<CTruck> gridData);
	void removeAll(String truckNos);
	CTruck findone(String truckNo);
	HdMessageCode saveone(CTruck cTruck);
	HdMessageCode findCTruck(String truckNo);
	HdMessageCode ifForbid(String truckNo);
}
