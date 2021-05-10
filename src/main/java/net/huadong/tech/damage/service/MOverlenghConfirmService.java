package net.huadong.tech.damage.service;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.damage.entity.MDeliveryRecord;
import net.huadong.tech.damage.entity.MOverlenghConfirm;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;

/**
 * @author 
 */
public interface MOverlenghConfirmService {
	HdEzuiDatagridData find(HdQuery hdQuery);
	HdMessageCode save(HdEzuiSaveDatagridData<MOverlenghConfirm> gridData);
	void removeAll(String confirmids);
	MOverlenghConfirm findone(String confirmid);
	HdMessageCode saveone(MOverlenghConfirm mOverlenghConfirm);
}
