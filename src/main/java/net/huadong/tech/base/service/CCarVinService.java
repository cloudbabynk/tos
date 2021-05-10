package net.huadong.tech.base.service;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.entity.CCarVin;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;

/**
 * @author 
 */
public interface CCarVinService {
	HdEzuiDatagridData find(HdQuery hdQuery);
	HdMessageCode ezuiSaveSysCode(HdEzuiSaveDatagridData<CCarVin> arg0);
	void removeAll(String vinIds);
	CCarVin findone(String vinId);
	HdMessageCode saveone(CCarVin cCarVin);
	HdEzuiDatagridData findvinNoByCarTyp(HdQuery arg0, boolean arg1);
	HdMessageCode updatePortCarTyp(String carTyp,String vinNo);
}
