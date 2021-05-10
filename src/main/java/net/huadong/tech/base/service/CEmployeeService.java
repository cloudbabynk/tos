package net.huadong.tech.base.service;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.entity.CEmployee;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiQueryParams;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;

/**
 * @author 
 */
public interface CEmployeeService {
	HdEzuiDatagridData find(HdQuery hdQuery);
	HdEzuiDatagridData findSj(HdQuery hdQuery);
	HdMessageCode save(HdEzuiSaveDatagridData<CEmployee> gridData);
	void removeAll(String empNos);
	CEmployee findone(String empNo);
	public CEmployee findCemployee(String sysUserNam);
	HdMessageCode saveone(CEmployee cEmployee);
	HdMessageCode findCEmployee(String empNo);
}
