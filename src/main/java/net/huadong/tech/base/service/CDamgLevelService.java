package net.huadong.tech.base.service;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.entity.CDamgLevel;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiQueryParams;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;

/**
 * @author 
 */
public interface CDamgLevelService {
	HdEzuiDatagridData find(HdQuery hdQuery);
	HdMessageCode save(HdEzuiSaveDatagridData<CDamgLevel> gridData);
	void removeAll(String damgLevelCods);
	CDamgLevel findone(String damgLevelCod);
	HdMessageCode saveone(CDamgLevel cDamgLevel);
	HdMessageCode findCDamgLevel(String damgLevelCod);
}
