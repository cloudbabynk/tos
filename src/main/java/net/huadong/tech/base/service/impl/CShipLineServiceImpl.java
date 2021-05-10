package net.huadong.tech.base.service.impl;
import com.alibaba.dubbo.config.annotation.Service;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.bean.HdRunTimeException;
import net.huadong.tech.base.entity.CShipLine;
import net.huadong.tech.base.service.CShipLineService;
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
public class CShipLineServiceImpl implements CShipLineService {
	@Override
	public HdEzuiDatagridData find(HdQuery hdQuery) {
		String jpql="select a from CShipLine a where 1=1 ";
		String shipLineCod = hdQuery.getStr("shipLineCod");
		String shipLineNam = hdQuery.getStr("shipLineNam");
		String lineId = hdQuery.getStr("lineId");
		String tradeId = hdQuery.getStr("tradeId");
		QueryParamLs paramLs = new QueryParamLs();
		if(HdUtils.strNotNull(shipLineCod)){
			jpql += "and a.shipLineCod =:shipLineCod ";
			paramLs.addParam("shipLineCod", shipLineCod);
		}
		if(HdUtils.strNotNull(shipLineNam)){
			jpql += "and a.shipLineNam =:shipLineNam ";
			paramLs.addParam("shipLineNam", shipLineNam);
		}
		if(HdUtils.strNotNull(lineId)){
			jpql += "and a.lineId =:lineId ";
			paramLs.addParam("lineId", lineId);
		}
		if(HdUtils.strNotNull(tradeId)){
			jpql += "and a.tradeId =:tradeId ";
			paramLs.addParam("tradeId", tradeId);
		}
		jpql += " order by a.recTim desc";
		return JpaUtils.findByEz(jpql, paramLs , hdQuery);
	}

	@Override
	public HdMessageCode save(HdEzuiSaveDatagridData<CShipLine> hdEzuiSaveDatagridData) {
		// TODO Auto-generated method stub
		return JpaUtils.save(hdEzuiSaveDatagridData);
	}

	@Transactional
	public void removeAll(String shipLineCods) {
		List<String> shipLineCodList = HdUtils.paraseStrs(shipLineCods);
		for (String shipLineCod : shipLineCodList) {
			JpaUtils.remove(CShipLine.class, shipLineCod);
		}
	}
	
	@Override
	public CShipLine findone(String shipLineCod) {
		CShipLine cShipLine = JpaUtils.findById(CShipLine.class, shipLineCod);
		return cShipLine;

	}
	
	@Override
	public HdMessageCode saveone(@RequestBody CShipLine cShipLine) {
		String shipLineCod = cShipLine.getShipLineCod();
		CShipLine cshipLine = JpaUtils.findById(CShipLine.class, shipLineCod);
		if(cshipLine != null){
			JpaUtils.update(cShipLine);
		}else{
			JpaUtils.save(cShipLine);
		}
		return HdUtils.genMsg();
	}
	@Override
	public HdMessageCode findCShipLine(String shipLineCod) {
		// TODO Auto-generated method stub
		if(HdUtils.strNotNull(shipLineCod)){
			CShipLine cShipLine = JpaUtils.findById(CShipLine.class, shipLineCod);
			if(cShipLine != null){
				throw new HdRunTimeException("该代码已存在，请重新输入！");// 主键已存在
			}
		}
		return HdUtils.genMsg();
	}
}

