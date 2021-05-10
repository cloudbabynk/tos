package net.huadong.tech.ship.service;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.inter.entity.CPortFt;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;

public interface CPortFtService {
	HdEzuiDatagridData find(HdQuery hdQuery);
	HdMessageCode saveone(CPortFt cPortFt);
	void removeAll(String portFtId);
}
