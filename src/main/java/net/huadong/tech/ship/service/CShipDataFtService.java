package net.huadong.tech.ship.service;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.ship.entity.CShipDataFt;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;

public interface CShipDataFtService {
	HdEzuiDatagridData find(HdQuery hdQuery);
	HdMessageCode saveone(CShipDataFt cShipDataFt);
	void removeAll(String shipDataFtId);
}
