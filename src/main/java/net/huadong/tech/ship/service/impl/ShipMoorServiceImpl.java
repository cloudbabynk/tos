package net.huadong.tech.ship.service.impl;
import com.alibaba.dubbo.config.annotation.Service;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.bean.HdRunTimeException;
import net.huadong.tech.base.entity.CBerth;
import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.tech.ship.entity.ShipMoor;
import net.huadong.tech.ship.service.ShipMoorService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiQueryParams;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;
import net.huadong.tech.util.HdUtils;

/**
 * @author 
 */
@Component
public class ShipMoorServiceImpl implements ShipMoorService {
	@Override
	public HdEzuiDatagridData find(HdQuery hdQuery) {
		// TODO Auto-generated method stub
		String jpql="select a from ShipMoor a where 1=1 ";
		String shipNo = hdQuery.getStr("shipNo");
		QueryParamLs paramLs = new QueryParamLs();
		if(HdUtils.strIsNull(shipNo)){
			jpql += "and a.shipNo =:shipNo ";
			paramLs.addParam("shipNo", "#");	
		}
		if(HdUtils.strNotNull(shipNo)){
			jpql += "and a.shipNo =:shipNo ";
			paramLs.addParam("shipNo", shipNo);
		}
		jpql += "order by a.workBegTim asc";
		HdEzuiDatagridData result=JpaUtils.findByEz(jpql, paramLs , hdQuery);
		List<ShipMoor> shipMoorList=result.getRows();
		if(shipMoorList.size()>0){
			for(ShipMoor shipmoor :shipMoorList){
				if(HdUtils.strNotNull(shipmoor.getBerthCod())){
				CBerth cberth=JpaUtils.findById(CBerth.class, shipmoor.getBerthCod());
				shipmoor.setBerthNam(cberth.getBerthNam());
			}
				}
		}
		return result;
	}

	@Override
	public HdMessageCode save(HdEzuiSaveDatagridData<ShipMoor> hdEzuiSaveDatagridData) {
		// TODO Auto-generated method stub
	return JpaUtils.save(hdEzuiSaveDatagridData);
	}
	@Transactional
	public void removeAll(String shipMoorIds) {
		// TODO Auto-generated method stub
		List<String> shipMoorIdList = HdUtils.paraseStrs(shipMoorIds);
		for (String shipMoorId : shipMoorIdList) {
			JpaUtils.remove(ShipMoor.class, shipMoorId);
		}	
	}

	@Override
	public ShipMoor findone(String shipMoorId) {
		// TODO Auto-generated method stub
		ShipMoor shipMoor = JpaUtils.findById(ShipMoor.class, shipMoorId);
		return shipMoor;

	}

	@Override
	public HdMessageCode saveone(@RequestBody ShipMoor shipMoor) {
		// TODO Auto-generated method stub
		if(HdUtils.strNotNull(shipMoor.getShipMoorId())){
			JpaUtils.update(shipMoor);
		}else{
			shipMoor.setShipMoorId(HdUtils.genUuid());
			shipMoor.setShipNo(shipMoor.getShipNo());
			JpaUtils.save(shipMoor);
		}
		return HdUtils.genMsg();
	}
}

