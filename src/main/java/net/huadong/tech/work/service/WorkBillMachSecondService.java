package net.huadong.tech.work.service;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.work.entity.WorkBillDriverSecond;
import net.huadong.tech.work.entity.WorkBillMachSecond;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiQueryParams;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;

/**
 * @author 
 */
public interface WorkBillMachSecondService {
	HdEzuiDatagridData find(HdQuery hdQuery);
	HdEzuiDatagridData findMach(HdQuery hdQuery);
	HdMessageCode save(HdEzuiSaveDatagridData<WorkBillMachSecond> gridData,String workbillNo,String machTypCod);
	void removeAll(String classCods,String workbillNo);
	WorkBillMachSecond findone(String damCod);
	HdMessageCode saveone(WorkBillMachSecond workBillMachSecond);
	HdMessageCode removeone(WorkBillMachSecond workBillMachSecond);
}
