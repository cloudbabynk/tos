package net.huadong.tech.base.service;
import net.huadong.tech.base.entity.CBizcar;
import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;

/**
 * @author 
 */
public interface CBizcarService {
	HdEzuiDatagridData find(HdQuery hdQuery);
	HdMessageCode save(HdEzuiSaveDatagridData<CBizcar> gridData);
	void removeAll(String bizcarNos);
	CBizcar findone(String bizcarNo);
	HdMessageCode saveone(CBizcar cBizcar);
	HdMessageCode findCBizcar(String bizcarNo);
}
