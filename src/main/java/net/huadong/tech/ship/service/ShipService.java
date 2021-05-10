package net.huadong.tech.ship.service;

import java.util.List;
import java.util.Map;import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.EzTreeBean;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.ship.entity.HdShipPicBerthPlanShipVisit;
import net.huadong.tech.ship.entity.Ship;
import net.huadong.tech.ship.entity.ShipFee;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;

/**
 * @yl
 */
public interface ShipService {
	HdEzuiDatagridData find(HdQuery hdQuery);
	HdEzuiDatagridData findShipJt(HdQuery hdQuery);
	HdEzuiDatagridData findShipJtNew(HdQuery hdQuery);
	HdMessageCode save(HdEzuiSaveDatagridData<Ship> gridData);
	HdMessageCode savezong(HdEzuiSaveDatagridData<Ship> gridData,String shipNo);
	HdMessageCode saveNewZong(HdEzuiSaveDatagridData<Ship> gridData,String shipNo);
	void removeAll(String shipNos);
	String uploadAll(String shipNos);
	void importBilling(String shipNo, String ieId, String dockCod, String feeTon);
	Ship findone(String shipNo);
	String findDashShip();
	List<Ship> findDashShipTable();
	HdShipPicBerthPlanShipVisit findBerth(String shipNo);
	HdMessageCode saveone(Ship ship);
	HdMessageCode saveSbjf(ShipFee shipFee);
	public List<EzTreeBean> findTree();
	String findAxis(String width,String height,String daySum,String startdate);
	String getBerthTim(String ptim,String startdate,String enddate,String height);
	String showBerths(String startdate,String enddate,String width,String height,String daySum);
	String showDetail(String type,String width,String height,String berth1, String cable1, String berth2, String cable2,String tim1, String tim2,String date1,String date2);
    //泊位计划方法
	HdMessageCode findMsg(String start, String end, String interval);
	//泊位计划修改嵌套方法
	HdMessageCode findExpandMsg(String start, String end, String interval);
	HdMessageCode saveBerth(HdShipPicBerthPlanShipVisit hdShipPicBerthPlanShipVisit);
	HdMessageCode saveBerthAndShip(HdShipPicBerthPlanShipVisit hdShipPicBerthPlanShipVisit);
	void removeShipPlan(HdShipPicBerthPlanShipVisit hdShipPicBerthPlanShipVisit);
	 HdMessageCode getBerthPlanShip( Map map);
}
