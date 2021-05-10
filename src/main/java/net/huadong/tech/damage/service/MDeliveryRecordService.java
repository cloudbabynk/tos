package net.huadong.tech.damage.service;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.damage.entity.MDeliveryRecord;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;

/**
 * @author 
 */
public interface MDeliveryRecordService {
	HdEzuiDatagridData find(HdQuery hdQuery);
	HdMessageCode save(HdEzuiSaveDatagridData<MDeliveryRecord> gridData);
	void removeAll(String deliveryids);
	MDeliveryRecord findone(String deliveryid);
	HdMessageCode saveone(MDeliveryRecord mDeliveryRecord);
}
