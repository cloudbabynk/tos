package net.huadong.tech.damage.service;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.damage.entity.MOverShortBack;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;

/**
 * @author 
 */
public interface MOverShortBackService {
	HdEzuiDatagridData find(HdQuery hdQuery);
	HdMessageCode save(HdEzuiSaveDatagridData<MOverShortBack> gridData);
	void removeAll(String overshortIds);
	MOverShortBack findone(String overshortId);
	HdMessageCode saveone(MOverShortBack mOverShortBack);
	HdEzuiDatagridData findBillCar(HdQuery hdQuery);
	HdMessageCode genmovershort(String shipNo, String tradeId, String billNo, String iEId, String vinNo, String missId);
}
