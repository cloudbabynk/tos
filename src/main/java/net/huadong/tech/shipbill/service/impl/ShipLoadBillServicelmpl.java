package net.huadong.tech.shipbill.service.impl;


import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.sql.*;
import java.util.ArrayList;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.tech.shipbill.entity.PortCar;
import net.huadong.tech.shipbill.entity.ShipLoadBill;
import net.huadong.tech.shipbill.service.ShipLoadBillService;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;
import net.huadong.tech.util.HdUtils;
import net.huadong.tech.util.beanUtil;


/**
 * @author
 */
@Component
public class ShipLoadBillServicelmpl implements ShipLoadBillService {

	@Override
	public HdEzuiDatagridData find(HdQuery hdQuery) {
		String jpql = "select a from ShipLoadBill a where 1=1 ";
		String shipNo = hdQuery.getStr("shipNo");
		String iEId = hdQuery.getStr("iEId");
		String billNo = hdQuery.getStr("billNo");
		QueryParamLs paramLs = new QueryParamLs();
		if (HdUtils.strNotNull(shipNo)) {
			jpql += "and a.shipNo =:shipNo ";
			paramLs.addParam("shipNo", shipNo);
		}
		if (HdUtils.strNotNull(iEId)) {
			if("1".equals(iEId))
			{
				jpql += "and (a.iEId ='1' or a.iEId ='I') ";
			}else if("2".equals(iEId))
			{
				jpql += "and (a.iEId ='2' or a.iEId ='E') ";
			}
//			jpql += "and a.iEId =:iEId ";
//			paramLs.addParam("iEId", iEId);
		}
		if (HdUtils.strNotNull(billNo)) {
			jpql += "and a.billNo = :billNo ";
			paramLs.addParam("billNo",  billNo);
		}
//		if (HdUtils.strNotNull(shipNo)) {
//			jpql += "and a.shipNo =:shipNo ";
//			paramLs.addParam("shipNo",shipNo);
//		}
		jpql += "order by a.recTim desc";
		HdEzuiDatagridData result = JpaUtils.findByEz(jpql, paramLs, hdQuery);
		

		return result;

	}

	@Transactional
	@Override
	public HdMessageCode save(HdEzuiSaveDatagridData<ShipLoadBill> gridData) {
		return JpaUtils.save(gridData);
	}

	@Transactional
	@Override
	public void removeAll(String id) {
		List<String> billSplitList = HdUtils.paraseStrs(id);
		for (String billspid : billSplitList) {
			JpaUtils.remove(ShipLoadBill.class, billspid);

		}
	}

	@Transactional
	@Override
	public HdMessageCode saveone(ShipLoadBill shipLoadBill) {
		JpaUtils.save(shipLoadBill);
		return HdUtils.genMsg();
	}
	@Transactional
	@Override
	public HdMessageCode savePortCarBillNo(Map mapPam) {
		HdMessageCode messageCode=HdUtils.genMsg();
		String billNo=mapPam.get("billNo")+""; // --
		String shipNo=mapPam.get("shipNo")+"";
				
		List<Map> lstPortCar=(List<Map>) mapPam.get("lstPortCar");
		if(lstPortCar!=null) {
			for (Map portCar : lstPortCar) {
				PortCar portCarItem=JpaUtils.findById(PortCar.class, new BigDecimal(portCar.get("portCarNo")+""));
				portCarItem.setBillNo(billNo);
				portCarItem.setShipNo(shipNo);
				JpaUtils.save(portCarItem);
			}
		}
		//

		
		return messageCode;
	}

	@Transactional
	@Override
	public String jiqi(Map mapPam) {
        String outinfo = "";
		String billNo=mapPam.get("billNo")+"";
		String shipNo=mapPam.get("shipNo")+"";
		
		List<Object> inParamLs = new ArrayList<Object>();
		inParamLs.add(shipNo);//过程参数值
		inParamLs.add(billNo);//过程参数值
		List<String> result = new ArrayList<String>();//过程返回值
		JpaUtils.executeOracleProcWithResult("p_xhz_jq", inParamLs, result, inParamLs.size()+1);
		outinfo = result.get(0);

		return outinfo;
	}
	
	@Transactional
	@Override
	public String deljiqi(Map mapPam) {
        String outinfo = "";
		String billNo=mapPam.get("billNo")+"";
		String shipNo=mapPam.get("shipNo")+"";
		
		List<Object> inParamLs = new ArrayList<Object>();
		inParamLs.add(shipNo);//过程参数值
		inParamLs.add(billNo);//过程参数值
		List<String> result = new ArrayList<String>();//过程返回值
		JpaUtils.executeOracleProcWithResult("p_xhz_del_jq", inParamLs, result, inParamLs.size()+1);
		outinfo = result.get(0);

		return outinfo;
	}
	
	@Transactional
	@Override
	public String shipjiqcheck(Map mapPam) {
        String outinfo = "";
		String shipNo=mapPam.get("shipNo")+"";
		
		List<Object> inParamLs = new ArrayList<Object>();
		inParamLs.add(shipNo);//过程参数值
		List<String> result = new ArrayList<String>();//过程返回值
		JpaUtils.executeOracleProcWithResult("p_xhz_jq_check", inParamLs, result, inParamLs.size()+1);
		outinfo = result.get(0);

		return outinfo;
	}	
}
