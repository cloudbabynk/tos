package net.huadong.tech.base.service;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.entity.CCountry;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiQueryParams;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;

/**
 * @author 
 */
public interface CCountryService {
	HdEzuiDatagridData find(HdQuery hdQuery);
	HdMessageCode save(HdEzuiSaveDatagridData<CCountry> gridData);
	void removeAll(String countryCods);
	CCountry findone(String countryCod);
	HdMessageCode saveone(CCountry cCountry);
	HdMessageCode findCCountry(String countryCod);
}
