package net.huadong.tech.gate.service.impl;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.tech.gate.entity.GateTruck;
import net.huadong.tech.gate.entity.GateTruckContract;
import net.huadong.tech.gate.service.GateTruckService;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;
import net.huadong.tech.util.HdUtils;

/**
 * @author
 */
@Component
public class GateTruckServiceImpl implements GateTruckService {
	@Override
	public HdEzuiDatagridData find(HdQuery hdQuery) {
		String jpql = "select distinct(a) from GateTruck a,GateTruckContract b,ContractIeDoc c where a.ingateId = b.ingateId and b.carryId =:carryId and b.contractNo = c.contractNo and c.tradeId =:tradeId ";
		QueryParamLs paramLs = new QueryParamLs();
		String type = hdQuery.getStr("Type");
		String contractNo = hdQuery.getStr("contractNo");
		if ("WMPLJG".equals(type) ) {
			paramLs.addParam("carryId", "A");
			paramLs.addParam("tradeId", "2");
		} else if ("NMPLJG".equals(type)){
			paramLs.addParam("carryId", "A");
			paramLs.addParam("tradeId", "1");
		} else if ("WMPLSG".equals(type)) {
			paramLs.addParam("carryId", "T");
			paramLs.addParam("tradeId", "2");
		} else if ("NMPLSG".equals(type)){
			paramLs.addParam("carryId", "T");
		    paramLs.addParam("tradeId", "1");
		}

		if (HdUtils.strNotNull(contractNo)){
			jpql += "and b.contractNo like :contractNo ";
			paramLs.addParam("contractNo", "%" + contractNo + "%");
		}
		jpql += "order by a.inGatTim desc";
		return JpaUtils.findByEz(jpql, paramLs, hdQuery);
	}

	@Override
	public HdEzuiDatagridData findCar(HdQuery hdQuery) {
		String jpql = "select a from GateTruck a where 1=1";
		String finishedId = hdQuery.getStr("finishedId");
		QueryParamLs paramLs = new QueryParamLs();
		if (HdUtils.strNotNull(finishedId)) {
			jpql += " and a.finishedId =:finishedId";
			paramLs.addParam("finishedId", finishedId);
		}
		jpql += " order by a.inGatTim desc";
		return JpaUtils.findByEz(jpql, paramLs, hdQuery);
	}

	@Override
	public HdMessageCode save(HdEzuiSaveDatagridData<GateTruck> hdEzuiSaveDatagridData, String contractNo,
			String singleId, String truckNo, String planNum, String inGatTim, String gateNo) {
		return JpaUtils.save(hdEzuiSaveDatagridData);
	}

	@Transactional
	public void removeAll(String ids) {
		List<String> idList = HdUtils.paraseStrs(ids);
		for (String id : idList) {
			JpaUtils.remove(GateTruckContract.class, id);
		}
	}

	@Override
	public GateTruck findone(String id) {
		GateTruck gateTruck = JpaUtils.findById(GateTruck.class, id);
		return gateTruck;

	}

	@Override
	public HdMessageCode saveone(@RequestBody GateTruck gateTruck) {
		if (HdUtils.strNotNull(gateTruck.getIngateId())) {
			JpaUtils.update(gateTruck);
		} else {
			JpaUtils.save(gateTruck);
		}
		return HdUtils.genMsg();
	}
}
