package net.huadong.tech.base.service.impl;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.bean.HdRunTimeException;
import net.huadong.tech.base.entity.CFactory;
import net.huadong.tech.base.service.CFactoryService;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;
import net.huadong.tech.util.HdUtils;
@Component
public class CFactoryServiceImpl  implements CFactoryService{

	@Override
	public HdEzuiDatagridData find(HdQuery hdQuery) {
		// TODO Auto-generated method stub
		String jpql="select a from CFactory a where 1=1 ";
		String factoryCod = hdQuery.getStr("factoryCod");
		String factoryNam = hdQuery.getStr("factoryNam");
		QueryParamLs paramLs = new QueryParamLs();
		if(HdUtils.strNotNull(factoryCod)){
			jpql += "and a.factoryCod =:factoryCod ";
			paramLs.addParam("factoryCod", factoryCod);
		}
		if(HdUtils.strNotNull(factoryNam)){
			jpql += "and a.factoryNam =:factoryNam ";
			paramLs.addParam("factoryNam", factoryNam);
		}
		jpql += "order by a.recTim desc";
		return JpaUtils.findByEz(jpql, paramLs , hdQuery);
	}

	@Override
	public HdMessageCode save(HdEzuiSaveDatagridData<CFactory> hdEzuiSaveDatagridData) {
		// TODO Auto-generated method stub
	return JpaUtils.save(hdEzuiSaveDatagridData);
	}
	@Transactional
	public void removeAll(String factoryCods) {
		// TODO Auto-generated method stub
		List<String> factoryCodList = HdUtils.paraseStrs(factoryCods);
		for (String factoryCod : factoryCodList) {
			JpaUtils.remove(CFactory.class, factoryCod);
		}	
	}

	@Override
	public CFactory findone(String factoryCod) {
		// TODO Auto-generated method stub
		CFactory cFactory = JpaUtils.findById(CFactory.class, factoryCod);
		return cFactory;

	}

	@Override
	public HdMessageCode saveone(@RequestBody CFactory cFactory) {
		// TODO Auto-generated method stub
		String factoryCod = cFactory.getFactoryCod();
		CFactory cfactory = JpaUtils.findById(CFactory.class, factoryCod);
		if(cfactory != null){
			JpaUtils.update(cFactory);
		}else{
			JpaUtils.save(cFactory);
		}
		return HdUtils.genMsg();
	}
	@Override
	public HdMessageCode findCFactory(String factoryCod) {
		if(HdUtils.strNotNull(factoryCod)){
			CFactory cFactory = JpaUtils.findById(CFactory.class, factoryCod);
			if(cFactory != null){
				throw new HdRunTimeException("该代码已存在，请重新输入！");// 主键已存在
			}
		}
		return HdUtils.genMsg();
	}
}
