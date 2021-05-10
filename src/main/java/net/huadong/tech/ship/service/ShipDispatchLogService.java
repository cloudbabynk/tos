package net.huadong.tech.ship.service;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.ship.entity.ShipDispatchLog;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;

/**
 * @yl
 */
public interface ShipDispatchLogService {
    
    HdEzuiDatagridData find(HdQuery hdQuery);

    HdMessageCode save(HdEzuiSaveDatagridData<ShipDispatchLog> gridData);

    void removeAll(String dispatchIds);

    ShipDispatchLog findone(String dispatchId);

    HdMessageCode saveone(ShipDispatchLog ship);
    
    /**
     * 交班
     * @param dispatchIds
     * @return
     */
    void jiaoban(String dispatchIds);
}
