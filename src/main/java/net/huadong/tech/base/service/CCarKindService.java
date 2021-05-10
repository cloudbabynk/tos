package net.huadong.tech.base.service;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.entity.CCarKind;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiQueryParams;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;

/**
 * @author 
 */
public interface CCarKindService {
	HdEzuiDatagridData find(HdQuery hdQuery);
	HdMessageCode save(HdEzuiSaveDatagridData<CCarKind> gridData);
	void removeAll(String carKinds);
	void checkAll(String carKinds);
	CCarKind findone(String carKind);
	HdMessageCode saveone(CCarKind cCarKind);
	HdMessageCode findCCarKind(String carKind);
}
