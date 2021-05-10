package net.huadong.tech.base.service;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.entity.CMachTyp;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiQueryParams;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;

/**
 * @author 
 */
public interface CMachTypService {
	HdEzuiDatagridData find(HdQuery hdQuery);
	HdMessageCode save(HdEzuiSaveDatagridData<CMachTyp> gridData);
	void removeAll(String machTypCods);
	CMachTyp findone(String machTypCod);
	HdMessageCode saveone(CMachTyp cMachTyp);
	HdMessageCode findCMachTyp(String machTypCod);
}
