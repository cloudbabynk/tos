package net.huadong.tech.shipbill.service;

import net.huadong.tech.shipbill.entity.TallySplit;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;
import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.ship.entity.ShipDispatchLog;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

/**
 * @author yuliang
 * @date 2020-07-14
 */
public interface TallySplitService {
	HdEzuiDatagridData find(HdQuery hdQuery);
    TallySplit findone(String id);
    HdMessageCode  saveone(TallySplit tallysplit);
    HdMessageCode removeAll(String ids);
    HdMessageCode removeAllHY(String ids);
    HdMessageCode submitAll(String ids, String type);
    HdMessageCode billCheck(String type, String shipNo);
    HdMessageCode billCheckCurd(String type, String shipNo);
    void remove(String id);
    HdMessageCode save(HdEzuiSaveDatagridData<TallySplit> gridData);
    HdMessageCode saveHY(HdEzuiSaveDatagridData<TallySplit> gridData);
	HdMessageCode dealS(String shipNo, String workTyp);
	HdMessageCode dealT(String contractNo, String workTyp);
	HdEzuiDatagridData findExcel(HdQuery hdQuery, String ids);//新增加的根据 选择的数据进行导出
	void exportExcelJF(HdEzuiDatagridData data, HttpServletResponse response);
    
}
