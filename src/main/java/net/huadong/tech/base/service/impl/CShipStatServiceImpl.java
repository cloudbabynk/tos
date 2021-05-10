package net.huadong.tech.base.service.impl;
import com.alibaba.dubbo.config.annotation.Service;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.bean.HdRunTimeException;
import net.huadong.tech.base.entity.CCarKind;
import net.huadong.tech.base.entity.CShipStat;
import net.huadong.tech.base.service.CShipStatService;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.idev.hdmessagecode.HdMessageCode;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
public class CShipStatServiceImpl implements CShipStatService {
	@Override
	public HdEzuiDatagridData find(HdQuery hdQuery) {
		String jpql="select a from CShipStat a where 1=1 ";
		String shipStatCod = hdQuery.getStr("shipStatCod");
		String shipStatNam = hdQuery.getStr("shipStatNam");
		QueryParamLs paramLs = new QueryParamLs();
		if(HdUtils.strNotNull(shipStatCod)){
			jpql += "and a.shipStatCod = :shipStatCod ";
			paramLs.addParam("shipStatCod", shipStatCod);
		}
		if(HdUtils.strNotNull(shipStatNam)){
			jpql += "and a.shipStatNam like :shipStatNam ";
			paramLs.addParam("shipStatNam", "%"+shipStatNam+"%");
		}
		return JpaUtils.findByEz(jpql, paramLs , hdQuery);
	}

	@Override
	public HdMessageCode save(HdEzuiSaveDatagridData<CShipStat> hdEzuiSaveDatagridData) {
		// TODO Auto-generated method stub
		return JpaUtils.save(hdEzuiSaveDatagridData);
	}

	@Transactional
	public void removeAll(String shipStatCods) {
		List<String> shipStatCodList = HdUtils.paraseStrs(shipStatCods);
		for (String shipStatCod : shipStatCodList) {
			JpaUtils.remove(CShipStat.class, shipStatCod);
		}
	}
	
	@Override
	public CShipStat findone(String shipStatCod) {
		CShipStat cShipStat = JpaUtils.findById(CShipStat.class, shipStatCod);
		return cShipStat;

	}
	
	@Override
	public HdMessageCode saveone(@RequestBody CShipStat cShipStat) {
		String shipStatCod = cShipStat.getShipStatCod();
		CShipStat cshipstat = JpaUtils.findById(CShipStat.class, shipStatCod);
		if(cshipstat != null){
			JpaUtils.update(cShipStat);
		}else{
			JpaUtils.save(cShipStat);
		}
		return HdUtils.genMsg();
	}
	
	@Override
	public HdMessageCode findCShipStat(String shipStatCod) {
		if(HdUtils.strNotNull(shipStatCod)){
			CShipStat cShipStat = JpaUtils.findById(CShipStat.class, shipStatCod);
			if(cShipStat != null){
				throw new HdRunTimeException("该代码已存在，请重新输入！");// 主键已存在
			}
		}
		return HdUtils.genMsg();
	}
}

