package net.huadong.tech.wechat.service;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.wechat.entity.Crfid;

public interface CrfidService {

	HdEzuiDatagridData find(HdQuery hdQuery);
	HdMessageCode saveone(Crfid cPortFt);
	void removeAll(String rfid);

}
