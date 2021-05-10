package net.huadong.tech.work.service;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.work.entity.WorkBillMach;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiQueryParams;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;

/**
 * @author 
 */
public interface WorkBillMachService {
	HdEzuiDatagridData find(HdQuery hdQuery);
	HdMessageCode save(HdEzuiSaveDatagridData<WorkBillMach> gridData,String workbillNo);
	void removeAll(String machTypCods,String workbillNo);
	WorkBillMach findone(String damCod);
	HdMessageCode saveone(WorkBillMach workBillMach);
}
