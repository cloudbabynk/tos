package net.huadong.tech.base.service.impl;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.bean.HdRunTimeException;
import net.huadong.tech.base.entity.CArea;
import net.huadong.tech.base.entity.CMachTyp;
import net.huadong.tech.base.entity.CPlace;
import net.huadong.tech.base.service.CPlaceService;
import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.tech.ship.entity.Ship;
import net.huadong.tech.shipbill.entity.ShipBill;

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
public class CPlaceServiceImpl implements CPlaceService {
	@Override
	public HdEzuiDatagridData find(HdQuery hdQuery) {
		// TODO Auto-generated method stub
		String jpql="select a from CPlace a where 1=1 ";
		String placCod = hdQuery.getStr("placCod");
		String placShort = hdQuery.getStr("placShort");
		String placNam = hdQuery.getStr("placNam");
		String areaNam = hdQuery.getStr("areaNam");
		QueryParamLs paramLs = new QueryParamLs();
		if(HdUtils.strNotNull(placCod)){
			jpql += "and a.placCod =:placCod ";
			paramLs.addParam("placCod", placCod);
		}
		if(HdUtils.strNotNull(placShort)){
			jpql += "and a.placShort =:placShort ";
			paramLs.addParam("placShort", placShort);
		}
		if(HdUtils.strNotNull(placNam)){
			jpql += "and a.placNam =:placNam ";
			paramLs.addParam("placNam", placNam);
		}
		if(HdUtils.strNotNull(areaNam)){
			jpql += "and a.areaCod =:areaNam ";
			paramLs.addParam("areaNam", areaNam);
		}
		jpql += "order by a.recTim desc";
		HdEzuiDatagridData result = JpaUtils.findByEz(jpql, paramLs, hdQuery);
		return result;
	}

	@Override
	public HdMessageCode save(HdEzuiSaveDatagridData<CPlace> hdEzuiSaveDatagridData) {
		// TODO Auto-generated method stub
	return JpaUtils.save(hdEzuiSaveDatagridData);
	}
	@Transactional
	public void removeAll(String placCods) {
		// TODO Auto-generated method stub
		List<String> placCodList = HdUtils.paraseStrs(placCods);
		for (String placCod : placCodList) {
			JpaUtils.remove(CPlace.class, placCod);
		}	
	}

	@Override
	public CPlace findone(String placCod) {
		// TODO Auto-generated method stub
		CPlace cPlace = JpaUtils.findById(CPlace.class, placCod);
		return  cPlace;

	}

	@Override
	public HdMessageCode saveone(@RequestBody CPlace cPlace) {
		// TODO Auto-generated method stub
		String placCod = cPlace.getPlacCod();
		CPlace cplace = JpaUtils.findById(CPlace.class, placCod);
		String areaCod=cPlace.getAreaCod();
		CArea cArea = JpaUtils.findById(CArea.class, areaCod);
		cPlace.setAreaNam(cArea.getAreaNam());
		if(cplace != null){
			JpaUtils.update(cPlace);
		}else{
			JpaUtils.save(cPlace);
		}
		return HdUtils.genMsg();
	}
	@Override
	public HdMessageCode findCPlace(String placCod) {
		// TODO Auto-generated method stub
		if(HdUtils.strNotNull(placCod)){
			CPlace cPlace = JpaUtils.findById(CPlace.class, placCod);
			if(cPlace != null){
				throw new HdRunTimeException("该代码已存在，请重新输入！");// 主键已存在
			}
		}
		return HdUtils.genMsg();
	}
}

