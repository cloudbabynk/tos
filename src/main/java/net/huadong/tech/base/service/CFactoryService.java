package net.huadong.tech.base.service;

import net.huadong.idev.hdmessagecode.HdMessageCode;


import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.entity.CFactory;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiQueryParams;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;

/**
 * @author 
 */
public interface CFactoryService {
	HdEzuiDatagridData find(HdQuery hdQuery);
	HdMessageCode save(HdEzuiSaveDatagridData<CFactory> hdEzuiSaveDatagridData);
	void removeAll(String factoryCods);
	CFactory findone(String factoryCod);
	HdMessageCode saveone(CFactory cFactory);
    HdMessageCode findCFactory(String factoryCod);
}

