package net.huadong.tech.ship.service;

import java.util.List;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.EzTreeBean;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.entity.MfWork;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiQueryParams;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;

/**
 * @yl
 */
public interface MfWorkService {
	HdEzuiDatagridData find(HdQuery hdQuery);
	HdMessageCode save(HdEzuiSaveDatagridData<MfWork> gridData,String shipNo);
	void removeAll(String ids);
	MfWork findone(String id);
	HdMessageCode saveone(MfWork ship);
}
