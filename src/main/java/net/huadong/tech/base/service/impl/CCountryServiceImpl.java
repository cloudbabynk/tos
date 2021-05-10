package net.huadong.tech.base.service.impl;
import com.alibaba.dubbo.config.annotation.Service;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.bean.HdRunTimeException;
import net.huadong.tech.base.entity.CCarKind;
import net.huadong.tech.base.entity.CCountry;
import net.huadong.tech.base.service.CCountryService;
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
public class CCountryServiceImpl implements CCountryService {
	@Override
	public HdEzuiDatagridData find(HdQuery hdQuery) {
		String jpql="select a from CCountry a where 1=1 ";
		String countryCod = hdQuery.getStr("countryCod");
		String cCountryNam = hdQuery.getStr("cCountryNam");
		QueryParamLs paramLs = new QueryParamLs();
		if(HdUtils.strNotNull(countryCod)){
			jpql += "and a.countryCod =:countryCod ";
			paramLs.addParam("countryCod", countryCod);
		}
		if(HdUtils.strNotNull(cCountryNam)){
			jpql += "and a.cCountryNam =:cCountryNam ";
			paramLs.addParam("cCountryNam", cCountryNam);
		}
		jpql += "order by a.recTim desc";
		return JpaUtils.findByEz(jpql, paramLs , hdQuery);
	}

	@Override
	public HdMessageCode save(HdEzuiSaveDatagridData<CCountry> hdEzuiSaveDatagridData) {
		// TODO Auto-generated method stub
		return JpaUtils.save(hdEzuiSaveDatagridData);
	}

	@Transactional
	public void removeAll(String countryCods) {
		List<String> countryCodList = HdUtils.paraseStrs(countryCods);
		for (String countryCod : countryCodList) {
			JpaUtils.remove(CCountry.class, countryCod);
		}
	}
	
	@Override
	public CCountry findone(String countryCod) {
		CCountry cCountry = JpaUtils.findById(CCountry.class, countryCod);
		return cCountry;

	}
	
	@Override
	public HdMessageCode saveone(@RequestBody CCountry cCountry) {
		String counrtyCod = cCountry.getCountryCod();
		CCountry ccountry = JpaUtils.findById(CCountry.class, counrtyCod);
		if(ccountry != null){
			JpaUtils.update(cCountry);
		}else{
			JpaUtils.save(cCountry);
		}
		return HdUtils.genMsg();
	}
	
	@Override
	public HdMessageCode findCCountry(String counrtyCod) {
		if(HdUtils.strNotNull(counrtyCod)){
			CCountry cCountry = JpaUtils.findById(CCountry.class, counrtyCod);
			if(cCountry != null){
				throw new HdRunTimeException("该代码已存在，请重新输入！");// 主键已存在
			}
		}
		return HdUtils.genMsg();
	}
}

