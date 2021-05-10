package net.huadong.tech.base.service;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.entity.CCarKind;
import net.huadong.tech.base.entity.CCarTypFeeName;
import net.huadong.tech.base.entity.ReportClient;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;

/**
 * @author sunlu
 * @date 2021/5/6 13:32
 */
public interface CCarTypFeeNameService {
    HdEzuiDatagridData find(HdQuery hdQuery);
   // HdMessageCode save(HdEzuiSaveDatagridData<CCarKind> gridData);
    void remove(String id);
    HdMessageCode saveone(CCarTypFeeName carTypFeeName);
    HdMessageCode saveAll(HdEzuiSaveDatagridData<CCarTypFeeName> carTypFeeNameLs);
    void removeAll(String ids);
}
