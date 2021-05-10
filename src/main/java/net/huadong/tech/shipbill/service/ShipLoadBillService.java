package net.huadong.tech.shipbill.service;

import java.util.Map;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.shipbill.entity.ShipLoadBill;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;

/**
 * @author 
 */
public interface ShipLoadBillService {
	HdEzuiDatagridData find(HdQuery hdQuery);
	HdMessageCode saveone(ShipLoadBill shipLoadBill);
	HdMessageCode save(HdEzuiSaveDatagridData<ShipLoadBill> gridData);
	void removeAll(String id);
	HdMessageCode savePortCarBillNo(Map mapPam);
	String jiqi(Map mapPam);
	String deljiqi(Map mapPam);
	String shipjiqcheck(Map mapPam);

}
