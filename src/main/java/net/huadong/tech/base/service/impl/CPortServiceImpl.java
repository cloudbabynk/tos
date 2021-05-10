package net.huadong.tech.base.service.impl;
import com.alibaba.dubbo.config.annotation.Service;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.bean.HdRunTimeException;
import net.huadong.tech.base.entity.CCarKind;
import net.huadong.tech.base.entity.CPort;
import net.huadong.tech.base.service.CPortService;
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
import net.huadong.tech.util.CommonUtil;
import net.huadong.tech.util.HdUtils;

/**
 * @author 
 */
@Component
public class CPortServiceImpl implements CPortService {
	@Override
	public HdEzuiDatagridData find(HdQuery hdQuery) {
		String jpql="select a from CPort a where 1=1 ";
		String portCod = hdQuery.getStr("portCod");
		String portShort = hdQuery.getStr("portShort");
		String cPortNam = hdQuery.getStr("cPortNam");
		String portClass = hdQuery.getStr("portClass");
		QueryParamLs paramLs = new QueryParamLs();
		if(HdUtils.strNotNull(portCod)){
			jpql += "and a.portCod =:portCod ";
			paramLs.addParam("portCod", portCod);
		}
		if(HdUtils.strNotNull(portShort)){
			jpql += "and a.portShort =:portShort ";
			paramLs.addParam("portShort", portShort);
		}
		if(HdUtils.strNotNull(cPortNam)){
			jpql += "and a.cPortNam like :cPortNam ";
			paramLs.addParam("cPortNam", "%"+cPortNam+"%");
		}
		if(HdUtils.strNotNull(portClass)){
			jpql += "and a.portClass =:portClass ";
			paramLs.addParam("portClass", portClass);
		}
		jpql += "order by a.recTim desc";
		return JpaUtils.findByEz(jpql, paramLs, hdQuery);
	}

	@Override
	public HdMessageCode save(HdEzuiSaveDatagridData<CPort> hdEzuiSaveDatagridData) {
		// TODO Auto-generated method stub
		return JpaUtils.save(hdEzuiSaveDatagridData);
	}

	@Transactional
	public void removeAll(String portIds) {
		List<String> portIdList = HdUtils.paraseStrs(portIds);
		for (String portId : portIdList) {
			JpaUtils.remove(CPort.class, portId);
		}
	}
	
	@Override
	public CPort findone(String portId) {
		CPort cPort = JpaUtils.findById(CPort.class, portId);
		return cPort;

	}
	
	@Override
	public HdMessageCode saveone(@RequestBody CPort cPort) {
		if(HdUtils.strNotNull(cPort.getPortId())){
			JpaUtils.update(cPort);
		}else{
			cPort.setPortId(HdUtils.genUuid());
			JpaUtils.save(cPort);
		}
		return HdUtils.genMsg();
	}

	@Override
	public CPort findByCode(String portCode) {
		String jpql="select a from CPort a where a.portCod=:portCod";
		QueryParamLs paramLs = new QueryParamLs();
			paramLs.addParam("portCod", portCode);
		List<CPort> mList=JpaUtils.findAll(jpql, paramLs);
		if(mList.size()>0) {
			return mList.get(0);
		}
		return null;
	}
	
}

