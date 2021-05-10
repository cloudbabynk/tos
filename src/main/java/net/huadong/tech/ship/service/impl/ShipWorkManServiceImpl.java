package net.huadong.tech.ship.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.persistence.config.QueryHints;
import org.eclipse.persistence.config.ResultType;
import org.springframework.stereotype.Component;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.entity.CWorkRun;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.tech.ship.entity.ShipExecution;
import net.huadong.tech.ship.entity.ShipWorkman;
import net.huadong.tech.ship.service.ShipWorkManService;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;
import net.huadong.tech.util.HdUtils;
import net.huadong.tech.util.Util;
@Component
public class ShipWorkManServiceImpl implements ShipWorkManService {

	@Override
	public HdEzuiDatagridData find(HdQuery hdQuery) {
		String jpql="select a from ShipWorkman a where 1=1 ";
		String shipNo = hdQuery.getStr("shipNo");
		QueryParamLs paramLs = new QueryParamLs();
		if (HdUtils.strIsNull(shipNo)) {
			jpql += "and a.shipNo =:shipNo ";
			paramLs.addParam("shipNo", "#");
		}
		if(HdUtils.strNotNull(shipNo)){
			jpql += "and a.shipNo =:shipNo ";
			paramLs.addParam("shipNo", shipNo);
		}
		jpql += "order by a.recTim desc";
		HdEzuiDatagridData result =JpaUtils.findByEz(jpql, paramLs , hdQuery);
		List<ShipWorkman> shipWorkmanList=result.getRows();
		if(shipWorkmanList.size()>0){
		for(ShipWorkman shipworkman:shipWorkmanList){
		CWorkRun cWorkRun=JpaUtils.findById(CWorkRun.class, shipworkman.getWokrRunCod());
		shipworkman.setWorkRunNam(cWorkRun.getWorkRunNam());
		}	
		}
		return result;
	}

	@Override
	public HdMessageCode save(HdEzuiSaveDatagridData<ShipWorkman> gridData) {
		return JpaUtils.save(gridData);
	}

	@Override
	public void removeAll(String shipWorkmanIds) {
		List<String> shipWorkmanList = HdUtils.paraseStrs(shipWorkmanIds);
		for (String shipWorkmanId : shipWorkmanList) {
			JpaUtils.remove(ShipWorkman.class, shipWorkmanId);
		}		
	}

	@Override
	public ShipWorkman findone(String shipWorkmanId) {
		ShipWorkman shipWorkman = JpaUtils.findById(ShipWorkman.class, shipWorkmanId);
		return shipWorkman;
	}

	@Override
	public HdMessageCode saveone(ShipWorkman shipWorkman) {
		if(HdUtils.strNotNull(shipWorkman.getShipWorkmanId())){
			JpaUtils.update(shipWorkman);
		}else{
			shipWorkman.setShipWorkmanId(HdUtils.generateUUID());;
			shipWorkman.setShipNo(shipWorkman.getShipNo());
			JpaUtils.save(shipWorkman);
		}
		return HdUtils.genMsg();
	}

	@Override
	public HdEzuiDatagridData genExecutionByship(Map map) {
		String shipNo=map.get("shipNo")+"";
		String sql="SELECT t.WORK_DTE,t.WORK_RUN_COD,w.WORK_RUN_NAM,t.WORK_TYP,c.name WORK_TYP_NAM,t.F_DIRECTOR "
				+ "  FROM WORK_BILL t,sys_code c,C_WORK_RUN w  " + 
				" WHERE T.work_typ = c.code and T.WORK_RUN_COD=w.WORK_RUN and c.FIELD_COD='WORK_TYP' "
				+" and t.SHIP_NO = '"+shipNo+"'";
		
		List<Map> lstShipWorkman=JpaUtils.getEntityManager().createNativeQuery(sql).setHint(QueryHints.RESULT_TYPE, ResultType.Map).getResultList(); 
		
 		List<ShipWorkman> lst=new ArrayList<ShipWorkman>();
		for (Map mapItem : lstShipWorkman) {
			ShipWorkman shipWorkman=new ShipWorkman();
			shipWorkman.setShipWorkmanId(HdUtils.generateUUID());
			shipWorkman.setShipNo(shipNo);
			shipWorkman.setWorkDay(Util.strFormateDate(mapItem.get("WORK_DTE")+"", "yyyy-MM-dd"));
			shipWorkman.setWokrRunCod(mapItem.get("WORK_RUN_COD")+"");
			shipWorkman.setWorkRunNam(mapItem.get("WORK_RUN_NAM")+"");
			shipWorkman.setWorkTyp(mapItem.get("WORK_TYP_NAM")+"");
			shipWorkman.setWorkContent(mapItem.get("F_DIRECTOR")+"");
			lst.add(shipWorkman);
		} 
		HdEzuiDatagridData ezuiDatagridData=new HdEzuiDatagridData();
		ezuiDatagridData.setRows(lst);
		return ezuiDatagridData;
	}

}
