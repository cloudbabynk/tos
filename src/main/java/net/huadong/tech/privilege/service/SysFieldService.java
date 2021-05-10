package net.huadong.tech.privilege.service;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.privilege.entity.SysField;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;

/**
 * Created by tianqw on 1/16/17.
 */
public interface SysFieldService {



	public HdEzuiDatagridData find(HdQuery query);
	
	HdMessageCode remove(String idLs);

	HdMessageCode save(HdEzuiSaveDatagridData<SysField> menu);
}
