package net.huadong.tech.ship.service.impl;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.persistence.config.QueryHints;
import org.eclipse.persistence.config.ResultType;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.tech.ship.entity.ShipExecution;
import net.huadong.tech.ship.service.ShipExecutionService;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;
import net.huadong.tech.util.HdUtils;

@Component
public class ShipExecutionServiceImpl implements ShipExecutionService {
	public HdEzuiDatagridData find(HdQuery hdQuery) {
		// TODO Auto-generated method stub
		String jpql = "select a from ShipExecution a where 1=1 ";
		String shipNo = hdQuery.getStr("shipNo");
		QueryParamLs paramLs = new QueryParamLs();
		if (HdUtils.strIsNull(shipNo)) {
			jpql += "and a.shipNo =:shipNo ";
			paramLs.addParam("shipNo", "#");
		}
		if (HdUtils.strNotNull(shipNo)) {
			jpql += "and a.shipNo =:shipNo ";
			paramLs.addParam("shipNo", shipNo);
		}
		HdEzuiDatagridData result=JpaUtils.findByEz(jpql, paramLs , hdQuery);
		List<ShipExecution> shipExecutionList=result.getRows();
		if(shipExecutionList.size()>0){
			for(ShipExecution shipExecution :shipExecutionList){
				if(HdUtils.strNotNull(shipExecution.getiEId())){
					shipExecution.setIeNam(HdUtils.getSysCodeName("I_E_ID",shipExecution.getiEId() ));
				}
			}
		}
		return result;
	}

	public HdMessageCode save(HdEzuiSaveDatagridData<ShipExecution> hdEzuiSaveDatagridData) {
		return JpaUtils.save(hdEzuiSaveDatagridData);
	}

	@Transactional
	public void removeAll(String shipExecutionIds) {
		// TODO Auto-generated method stub
		List<String> shipExecutionIdList = HdUtils.paraseStrs(shipExecutionIds);
		for (String shipExecutionId : shipExecutionIdList) {
			JpaUtils.remove(ShipExecution.class, shipExecutionId);
		}
	}

	public ShipExecution findone(String shipExecutionId) {
		ShipExecution shipExecution = JpaUtils.findById(ShipExecution.class, shipExecutionId);
		return shipExecution;

	}

	public HdMessageCode saveone(@RequestBody ShipExecution shipExecution) {
		if (HdUtils.strNotNull(shipExecution.getShipExecutionId())) {
			JpaUtils.update(shipExecution);
		} else {
			shipExecution.setShipExecutionId(HdUtils.genUuid());
			shipExecution.setShipNo(shipExecution.getShipNo());
			JpaUtils.save(shipExecution);
		}
		return HdUtils.genMsg();
	}

	@Override
	public HdEzuiDatagridData genExecutionByship(Map map) {
		String shipNo=map.get("shipNo")+"";
		String sql="select t.*,T1.BRAND_NAM  from (  select count(PORT_CAR_NO) CAR_NUM,                                                         "+                                                                              
				" case WORK_TYP when 'SI' then 'I'  when 'SO' then 'E' else WORK_TYP  end I_E_ID  ,BRAND_COD "+
				" from work_command where ship_no='"+shipNo+"' and WORK_TYP in ('SI','SO')               "+
				" group by BRAND_COD, WORK_TYP ) t,c_brand t1 where t.BRAND_COD=t1.BRAND_COD(+)";
		
		String sql2="select t.*,T1.BRAND_NAM  from ("
				+ " select sum(CAR_NUM) CAR_NUM, I_E_ID,BRAND_COD  from ship_bill where ship_no='"+shipNo+"' group by BRAND_COD, I_E_ID"
				+ " ) t,c_brand t1 where t.BRAND_COD=t1.BRAND_COD(+)";
		
		List<Map> lstCommand=JpaUtils.getEntityManager().createNativeQuery(sql).setHint(QueryHints.RESULT_TYPE, ResultType.Map).getResultList(); 
		List<Map> lstBill=JpaUtils.getEntityManager().createNativeQuery(sql2).setHint(QueryHints.RESULT_TYPE, ResultType.Map).getResultList();
		Map<String,BigDecimal> mapBill=new HashMap();
		
		for (Map map2 : lstBill) {
			mapBill.put(map2.get("BRAND_COD")+"-"+map2.get("I_E_ID"),new BigDecimal(map2.get("CAR_NUM")+""));
		}
 		List<ShipExecution> lst=new ArrayList<ShipExecution>();
		for (Map mapItem : lstCommand) {
			ShipExecution shipExecution=new ShipExecution();
			shipExecution.setShipExecutionId(HdUtils.generateUUID());
			shipExecution.setCargoKind(mapItem.get("BRAND_NAM")+"");
			shipExecution.setIeNam(HdUtils.getSysCodeName("I_E_ID",mapItem.get("I_E_ID")+""));
			shipExecution.setiEId(mapItem.get("I_E_ID")+"");
			shipExecution.setWorkTon(new BigDecimal(mapItem.get("CAR_NUM")+""));
			shipExecution.setShipNo(shipNo);
			if(mapBill.containsKey(mapItem.get("BRAND_COD")+"-"+mapItem.get("I_E_ID"))) {
				shipExecution.setPlanTon(mapBill.get(mapItem.get("BRAND_COD")+"-"+mapItem.get("I_E_ID")));
			}else {
				shipExecution.setPlanTon(new BigDecimal(0));
			}
			lst.add(shipExecution);
		} 
		HdEzuiDatagridData ezuiDatagridData=new HdEzuiDatagridData();
		ezuiDatagridData.setRows(lst);
		return ezuiDatagridData;
	}
}
