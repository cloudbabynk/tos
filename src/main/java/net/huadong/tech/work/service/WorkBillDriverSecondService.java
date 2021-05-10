package net.huadong.tech.work.service;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.work.entity.WorkBillDriverSecond;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiQueryParams;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;

/**
 * @author 
 */
public interface WorkBillDriverSecondService {
	HdEzuiDatagridData find(HdQuery hdQuery);
	HdEzuiDatagridData findDriver(HdQuery hdQuery);
	HdMessageCode save(HdEzuiSaveDatagridData<WorkBillDriverSecond> gridData,String workbillNo,String classCod);
	void removeAll(String driverCods,String workbillNo,String classCod);
	WorkBillDriverSecond findone(String damCod);
	HdMessageCode saveone(WorkBillDriverSecond workBillDriverSecond);
	HdMessageCode removeone(WorkBillDriverSecond workBillDriverSecond);
}
