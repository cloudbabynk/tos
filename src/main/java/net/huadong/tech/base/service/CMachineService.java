package net.huadong.tech.base.service;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.entity.CMachine;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiQueryParams;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;

/**
 * @author 
 */
public interface CMachineService {
	HdEzuiDatagridData find(HdQuery hdQuery);
	HdMessageCode save(HdEzuiSaveDatagridData<CMachine> gridData);
	void removeAll(String machNos);
	CMachine findone(String machNo);
	HdMessageCode saveone(CMachine cMachine);
	HdMessageCode findCMachine(String machNo);
}
