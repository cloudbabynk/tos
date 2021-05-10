package net.huadong.tech.base.service.impl;
import com.alibaba.dubbo.config.annotation.Service;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.bean.HdRunTimeException;
import net.huadong.tech.base.entity.CDamgLevel;
import net.huadong.tech.base.service.CDamgLevelService;
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
public class CDamgLevelServiceImpl implements CDamgLevelService {
	@Override
	public HdEzuiDatagridData find(HdQuery hdQuery) {
		String jpql="select a from CDamgLevel a where 1=1 ";
		String damgLevelCod = hdQuery.getStr("damgLevelCod");
		String damgLevel = hdQuery.getStr("damgLevel");
		QueryParamLs paramLs = new QueryParamLs();
		if(HdUtils.strNotNull(damgLevelCod)){
			jpql += "and a.damgLevelCod =:damgLevelCod ";
			paramLs.addParam("damgLevelCod", damgLevelCod);
		}
		if(HdUtils.strNotNull(damgLevel)){
			jpql += "and a.damgLevel =:damgLevel ";
			paramLs.addParam("damgLevel", damgLevel);
		}
		jpql += "order by a.recTim desc";
		return JpaUtils.findByEz(jpql, paramLs , hdQuery);
	}

	@Override
	public HdMessageCode save(HdEzuiSaveDatagridData<CDamgLevel> hdEzuiSaveDatagridData) {
		// TODO Auto-generated method stub
		return JpaUtils.save(hdEzuiSaveDatagridData);
	}

	@Transactional
	public void removeAll(String damgLevelCods) {
		List<String> damgLevelCodList = HdUtils.paraseStrs(damgLevelCods);
		for (String damgLevelCod : damgLevelCodList) {
			JpaUtils.remove(CDamgLevel.class, damgLevelCod);
		}
	}
	
	@Override
	public CDamgLevel findone(String damgLevelCod) {
		CDamgLevel cDamgLevel = JpaUtils.findById(CDamgLevel.class, damgLevelCod);
		return cDamgLevel;

	}
	
	@Override
	public HdMessageCode saveone(@RequestBody CDamgLevel cDamgLevel) {
		String damgLevelCod = cDamgLevel.getDamgLevelCod();
		CDamgLevel cdamgLevel = JpaUtils.findById(CDamgLevel.class, damgLevelCod);
		if(cdamgLevel != null){
			JpaUtils.update(cDamgLevel);
		}else{
			JpaUtils.save(cDamgLevel);
		}
		return HdUtils.genMsg();
	}
	


@Override
	public HdMessageCode findCDamgLevel(String damCod) {
		if(HdUtils.strNotNull(damCod)){
			CDamgLevel cDamgLevel = JpaUtils.findById(CDamgLevel.class, damCod);
			if(cDamgLevel != null){
				throw new HdRunTimeException("该代码已存在，请重新输入！");// 主键已存在
			}
		}
		return HdUtils.genMsg();
	}
}

