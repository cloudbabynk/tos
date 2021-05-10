package net.huadong.tech.ship.service;

import java.util.List;
import java.util.Map;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.ship.entity.ShipCarMeasure;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;

/**
 *
 * @author 于如河
 * @date 2019-07-08
 */
public interface ShipCarMeasureService {
	HdEzuiDatagridData find(HdQuery hdQuery);
	
	HdEzuiDatagridData findFcd(HdQuery hdQuery);
	
	public ShipCarMeasure findone(String id);

	public HdMessageCode saveone(ShipCarMeasure entity);
	
	public HdMessageCode savepl(ShipCarMeasure entity, String ids);
	
	HdMessageCode updateAll(String queueIds);

	HdMessageCode save(HdEzuiSaveDatagridData<ShipCarMeasure> gridData);

	HdEzuiDatagridData exportExcel(HdQuery params, String ids);//新增加的进口复尺单excel导出的方法

	HdEzuiDatagridData exportExcelCk(HdQuery params, String ids);//新增加的出口复尺单excel导出的方法

	HdEzuiDatagridData findFcdck(HdQuery hdQuery);//新增加的出口复尺单的批量列表的查询方法
}