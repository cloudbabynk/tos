package net.huadong.tech.base.service;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.entity.CBrandDetail;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;

public interface CBrandDetailService {
	HdEzuiDatagridData find(HdQuery hdQuery);
	HdMessageCode ezuiSaveSysCode(HdEzuiSaveDatagridData<CBrandDetail> hdEzuiSaveDatagridData);
	HdMessageCode saveone(CBrandDetail cBrandDetail);
	void removeAll(String ids);
}
