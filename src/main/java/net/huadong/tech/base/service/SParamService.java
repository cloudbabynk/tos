package net.huadong.tech.base.service;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.entity.SParam;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;

/**
 * @author 
 */
public interface SParamService {
	HdEzuiDatagridData find(HdQuery hdQuery);
	
	HdMessageCode save(HdEzuiSaveDatagridData<SParam> gridData);
	
	void removeAll(String paramId);
	SParam  findone(String paramId);
	HdMessageCode saveone(SParam sParam);
	public void removeById(String paramId);
}
