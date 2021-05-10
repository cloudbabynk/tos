package net.huadong.tech.map.service;

import java.util.Map;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.map.entity.CGisWbillCamera;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;

/**
 * @author 
 */
public interface CGisWbillCameraService{
	HdEzuiDatagridData find(HdQuery hdQuery);
	HdEzuiDatagridData findCamera(HdQuery hdQuery);
	HdEzuiDatagridData findCGWCamera(HdQuery hdQuery);
	HdMessageCode save(HdEzuiSaveDatagridData<CGisWbillCamera> gridData);
	HdMessageCode saveRightDialog(HdEzuiSaveDatagridData<Map<String,Object>> saveData);
	void removeAll(String ids);
	CGisWbillCamera findone(String id);
	HdMessageCode saveone(CGisWbillCamera cgiswbillcamera);
}
