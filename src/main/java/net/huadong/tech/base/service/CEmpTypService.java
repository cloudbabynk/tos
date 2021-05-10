package net.huadong.tech.base.service;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.entity.CEmpTyp;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiQueryParams;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;

/**
 * @author 
 */
public interface CEmpTypService {
	HdEzuiDatagridData find(HdQuery hdQuery);
	HdMessageCode save(HdEzuiSaveDatagridData<CEmpTyp> gridData);
	void removeAll(String empTypCods);
	CEmpTyp findone(String empTypCod);
	HdMessageCode saveone(CEmpTyp cEmpTyp);
	HdMessageCode findCEmpTyp(String empTypCod);
}
