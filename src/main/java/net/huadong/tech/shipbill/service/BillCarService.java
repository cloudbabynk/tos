package net.huadong.tech.shipbill.service;

import java.util.List;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.shipbill.entity.BillCar;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiQueryParams;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;
import net.huadong.tech.base.bean.EzTreeBean;
/**
 * @author 
 */
public interface BillCarService {
	HdEzuiDatagridData findShipVoyage(HdQuery hdQuery);
	HdEzuiDatagridData findShipBillCar(HdQuery hdQuery);
	HdEzuiDatagridData findXclh(HdQuery hdQuery);
	HdEzuiDatagridData find(HdQuery hdQuery);
	HdMessageCode saveone(BillCar billCar);
	void removeAll(String billcarId);
/*	List<EzTreeBean> getShipStatusTree();*/
	/*
	HdMessageCode save(HdEzuiSaveDatagridData<BillCar> gridData);
	void removeAll(String BillCarIds);
	BillCar findone(String BillCarId);
	*/
	HdMessageCode save(HdEzuiSaveDatagridData<BillCar> gridData);
}
