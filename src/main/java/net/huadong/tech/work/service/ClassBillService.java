package net.huadong.tech.work.service;

import java.util.Date;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.work.entity.ClassBill;
import net.huadong.tech.work.entity.WorkBill;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiQueryParams;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;

/**
 * @author 
 */
public interface ClassBillService {
	HdEzuiDatagridData find(HdQuery hdQuery);
	HdMessageCode findZx(String shipNo,String workDte,String workRunCod);
	HdMessageCode save(HdEzuiSaveDatagridData<ClassBill> gridData);
	void removeAll(String classbillNos);
	ClassBill findone(String classbillNo);
	HdMessageCode saveone(ClassBill classBill);
}
