package net.huadong.tech.tool.service;

import java.math.BigDecimal;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.com.entity.ComMsgRec;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiQueryParams;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;

/**
 * @author 
 */
public interface ComMsgRecService {
	HdEzuiDatagridData find(HdQuery hdQuery);
	void removeAll(String recIds);
	ComMsgRec findNum();
	void update();
}
