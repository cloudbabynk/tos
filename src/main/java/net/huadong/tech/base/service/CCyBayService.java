package net.huadong.tech.base.service;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.entity.CCyBay;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;

/**
 * @author 
 */
public interface CCyBayService {
	HdEzuiDatagridData find(HdQuery hdQuery);
	String findLocked(String cyAreaNo);
	HdMessageCode save(HdEzuiSaveDatagridData<CCyBay> gridData);
}
