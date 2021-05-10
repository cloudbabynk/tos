package net.huadong.tech.base.service.impl;
import com.alibaba.dubbo.config.annotation.Service;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.bean.HdRunTimeException;
import net.huadong.tech.base.entity.CClientCod;
import net.huadong.tech.base.service.CClientCodService;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.tech.shipbill.entity.ContractIeDoc;
import net.huadong.idev.hdmessagecode.HdMessageCode;

import java.util.ArrayList;
import java.util.Collections;
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
public class CClientCodServiceImpl implements CClientCodService {
	@Override
	public HdEzuiDatagridData find(HdQuery hdQuery) {
		String jpql="select a from CClientCod a where 1=1 ";
		String cClientShort = hdQuery.getStr("cClientShort");
		String cClientNam = hdQuery.getStr("cClientNam");
		String shipCorpId = hdQuery.getStr("shipCorpId");
		String shipOwnerId = hdQuery.getStr("shipOwnerId");
		String shipAgentId = hdQuery.getStr("shipAgentId");
		String crgAgentId = hdQuery.getStr("crgAgentId");
		String consignId = hdQuery.getStr("consignId");
		String shipConsignId = hdQuery.getStr("shipConsignId");
		String truckUnitId = hdQuery.getStr("truckUnitId");
		String payUnitId = hdQuery.getStr("payUnitId");
		String constructionId = hdQuery.getStr("constructionId");
		QueryParamLs paramLs = new QueryParamLs();
		if(HdUtils.strNotNull(cClientShort)){
			jpql += "and a.cClientShort =:cClientShort ";
			paramLs.addParam("cClientShort", cClientShort);
		}
		if(HdUtils.strNotNull(cClientNam)){
			jpql += "and a.cClientNam like :cClientNam ";
			paramLs.addParam("cClientNam", "%"+cClientNam+"%");
		}
		if(HdUtils.strNotNull(shipCorpId)){
			jpql += "and a.shipCorpId =:shipCorpId ";
			paramLs.addParam("shipCorpId", shipCorpId);
		}
		if(HdUtils.strNotNull(shipConsignId)){
			jpql += "and a.shipConsignId =:shipConsignId ";
			paramLs.addParam("shipConsignId", shipConsignId);
		}
		if(HdUtils.strNotNull(shipOwnerId)){
			jpql += "and a.shipOwnerId =:shipOwnerId ";
			paramLs.addParam("shipOwnerId", shipOwnerId);
		}
		if(HdUtils.strNotNull(shipAgentId)){
			jpql += "and a.shipAgentId =:shipAgentId ";
			paramLs.addParam("shipAgentId", shipAgentId);
		}
		if(HdUtils.strNotNull(crgAgentId)){
			jpql += "and a.crgAgentId =:crgAgentId ";
			paramLs.addParam("crgAgentId", crgAgentId);
		}
		if(HdUtils.strNotNull(consignId)){
			jpql += "and a.consignId =:consignId ";
			paramLs.addParam("consignId", consignId);
		}
		if(HdUtils.strNotNull(truckUnitId)){
			jpql += "and a.truckUnitId =:truckUnitId ";
			paramLs.addParam("truckUnitId", truckUnitId);
		}
		if(HdUtils.strNotNull(payUnitId)){
			jpql += "and a.payUnitId =:payUnitId ";
			paramLs.addParam("payUnitId", payUnitId);
		}
		if(HdUtils.strNotNull(constructionId)){
			jpql += "and a.constructionId =:constructionId ";
			paramLs.addParam("constructionId", constructionId);
		}
		return JpaUtils.findByEz(jpql, paramLs , hdQuery);
	}

	@Override
	public HdMessageCode save(HdEzuiSaveDatagridData<CClientCod> hdEzuiSaveDatagridData) {
		// TODO Auto-generated method stub
		return JpaUtils.save(hdEzuiSaveDatagridData);
	}

	@Transactional
	public void removeAll(String clientCods) {
		List<String> clientCodList = HdUtils.paraseStrs(clientCods);
		for (String clientCod : clientCodList) {
			JpaUtils.remove(CClientCod.class, clientCod);
		}
	}
	
	@Override
	public CClientCod findone(String clientCod) {
		CClientCod cClientCod = JpaUtils.findById(CClientCod.class, clientCod);
		return cClientCod;

	}
	
	@Override
	public HdMessageCode saveone(@RequestBody CClientCod cClientCod) {
		//校验中文名称和简称
		String checkJpql = "SELECT c FROM CClientCod c where (c.cClientNam like ?1 or c.cClientShort like ?2)  ";
		QueryParamLs checkParams = new QueryParamLs();
		if(HdUtils.strNotNull(cClientCod.getClientCod())) {
			checkJpql += " and c.clientCod <> ?3";
			checkParams.addParam(3, cClientCod.getClientCod());
		}
		checkParams.addParam(1, "%"+cClientCod.getcClientNam()+"%");
		checkParams.addParam(2, "%"+cClientCod.getcClientShort()+"%");
		
		List<CClientCod> cClientCodList = JpaUtils.findAll(checkJpql, checkParams);
		
		CClientCod bean = JpaUtils.findById(CClientCod.class, cClientCod.getClientCod());
		if(cClientCodList.size() > 0 && bean == null) {
			throw new HdRunTimeException("该客户已存在！");
		}
		
		if(cClientCod.getShipCorpId() == null){
			cClientCod.setShipCorpId("0");
		}
		if(cClientCod.getShipAgentId() == null){
			cClientCod.setShipAgentId("0");
		}
		if(cClientCod.getShipOwnerId() == null){
			cClientCod.setShipOwnerId("0");
		}
		if(cClientCod.getCrgAgentId() == null){
			cClientCod.setCrgAgentId("0");
		}
		if(cClientCod.getConsignId() == null){
			cClientCod.setConsignId("0");
		}
		if(cClientCod.getTruckUnitId() == null){
			cClientCod.setTruckUnitId("0");
		}
		if(cClientCod.getPayUnitId() == null){
			cClientCod.setPayUnitId("0");
		}
		if(cClientCod.getConstructionId() == null){
			cClientCod.setConstructionId("0");
		}
		if(HdUtils.strNotNull(cClientCod.getClientCod())){
			JpaUtils.update(cClientCod);
		}else{
			String jpql="select max(a.clientCod) from CClientCod a where 1=1";
			List<String> cclientcodList=JpaUtils.findAll(jpql,null);
			Integer c=Integer.valueOf(cclientcodList.get(0).toString());
			Integer maxclientcod = c + 1;
			String maxN = addZeroForNum(maxclientcod.toString(), 8);
			cClientCod.setClientCod(maxN);
			JpaUtils.save(cClientCod);
		}
		return HdUtils.genMsg();
	}
	public String addZeroForNum(String str, int strLength) {
		int strLen = str.length();
		if (strLen < strLength) {
			while (strLen < strLength) {
				StringBuffer sb = new StringBuffer();
				sb.append("0").append(str);// 左补0
				// sb.append(str).append("0");//右补0
				str = sb.toString();
				strLen = str.length();
			}
		}

		return str;
	}

	@Override
	public HdMessageCode findCClientCod(String clientCod) {
		if(HdUtils.strNotNull(clientCod)){
			CClientCod cClientCod = JpaUtils.findById(CClientCod.class, clientCod);
			if(cClientCod != null){
				throw new HdRunTimeException("该代码已存在，请重新输入！");// 主键已存在
			}
		}
		return HdUtils.genMsg();
	}
}

