package net.huadong.tech.damage.service;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.damage.entity.LockCarDoc;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;

/**
 * @author 
 */
public interface LockCarDocService {
	HdEzuiDatagridData find(HdQuery hdQuery);
	HdMessageCode save(HdEzuiSaveDatagridData<LockCarDoc> gridData);
	void removeAll(String lockcarIds);
	LockCarDoc findone(String lockcarId);
	HdMessageCode saveone(LockCarDoc lockCarDoc);
}
