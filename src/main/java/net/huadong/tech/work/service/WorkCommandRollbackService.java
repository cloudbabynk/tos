package net.huadong.tech.work.service;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.work.entity.CargoInfo;

public interface WorkCommandRollbackService {
	HdEzuiDatagridData find(HdQuery hdQuery);
	void unloadBack(String portCarNo);
	void loadBack(String portCarNo);
	void loadBackCz(String portCarNo);
	void jgBack(String portCarNo);
	void sgBack(String portCarNo);
	void tzBack(String portCarNo);
	HdMessageCode cargoBack(CargoInfo cargoInfo,String type);
}
