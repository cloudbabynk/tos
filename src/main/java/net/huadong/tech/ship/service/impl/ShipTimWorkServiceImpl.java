package net.huadong.tech.ship.service.impl;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.bean.HdRunTimeException;
import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.entity.CArea;
import net.huadong.tech.base.service.CAreaService;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.tech.ship.entity.ShipTimWork;
import net.huadong.tech.ship.service.ShipTimWorkService;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;
import net.huadong.tech.util.HdUtils;

/**
 * @author 
 */
@Component
public class ShipTimWorkServiceImpl implements ShipTimWorkService {
	@Override
	public HdEzuiDatagridData find(HdQuery hdQuery) {
		// TODO Auto-generated method stub
		String jpql="select a from ShipTimWork a where 1=1 ";
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
		jpql += "order by a.recTim desc";
		HdEzuiDatagridData result=JpaUtils.findByEz(jpql, paramLs , hdQuery);
		List<ShipTimWork> shipTimWorkList=result.getRows();
		if(shipTimWorkList.size()>0){
		for(ShipTimWork shiptimwork:shipTimWorkList){
			shiptimwork.setShipTimTypNam(HdUtils.getSysCodeName("SHIP_TIM_TYP",shiptimwork.getShipTimTyp()));
		}	
		}
		return result;
	}

	@Override
	public HdMessageCode save(HdEzuiSaveDatagridData<ShipTimWork> hdEzuiSaveDatagridData) {
		// TODO Auto-generated method stub
	return JpaUtils.save(hdEzuiSaveDatagridData);
	}
	@Transactional
	public void removeAll(String shipTimworkIds) {
		// TODO Auto-generated method stub
		List<String> shipTimWorkList = HdUtils.paraseStrs(shipTimworkIds);
		for (String shipTimWork : shipTimWorkList) {
			JpaUtils.remove(ShipTimWork.class, shipTimWork);
		}	
	}

	@Override
	public HdMessageCode saveone(@RequestBody ShipTimWork shipTimWork) {
		// TODO Auto-generated method stub
		if(HdUtils.strNotNull(shipTimWork.getShipTimworkId())){
			JpaUtils.update(shipTimWork);
		}else{
			shipTimWork.setShipTimworkId(HdUtils.genUuid());
			shipTimWork.setShipNo(shipTimWork.getShipNo());
			JpaUtils.save(shipTimWork);
		}
		return HdUtils.genMsg();
	}
	@Override
	public ShipTimWork findone(String shipTimworkId) {
		// TODO Auto-generated method stub
		ShipTimWork shipTimWork = JpaUtils.findById(ShipTimWork.class, shipTimworkId);
		return shipTimWork;

	}
	
}

