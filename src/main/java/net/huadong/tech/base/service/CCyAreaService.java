package net.huadong.tech.base.service;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.bean.HdQueryParam;
import net.huadong.tech.base.entity.CCyArea;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiQueryParams;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;

/**
 * @author 
 */
public interface CCyAreaService {
	HdEzuiDatagridData find(HdEzuiQueryParams hdQuery);
	HdEzuiDatagridData findCdxx(HdQuery hdQuery);
	HdEzuiDatagridData findDccl(HdQuery hdQuery);
	HdEzuiDatagridData findCzzc(HdQuery hdQuery);
	HdEzuiDatagridData findDcclhz(HdQuery hdQuery);
	HdEzuiDatagridData findSglh(HdQuery hdQuery);
	HdMessageCode save(HdEzuiSaveDatagridData<CCyArea> gridData);
	HdMessageCode saveone(CCyArea cCyArea);
	HdEzuiDatagridData findcdch(HdQuery hdQuery);
	CCyArea findone(String cyAreaNo);
	CCyArea findCCyArea(String cyAreaNo);
	void removeAll(String damCods);
	void sendDataJT();
	String findAreaNam(String cyAreaNo);
}
