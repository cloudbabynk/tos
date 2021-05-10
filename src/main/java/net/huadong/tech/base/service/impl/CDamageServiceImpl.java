package net.huadong.tech.base.service.impl;
import com.alibaba.dubbo.config.annotation.Service;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.bean.HdRunTimeException;
import net.huadong.tech.base.entity.CDamage;
import net.huadong.tech.base.service.CDamageService;
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
public class CDamageServiceImpl implements CDamageService {
	@Override
	public HdEzuiDatagridData find(HdQuery hdQuery) {
		String jpql="select a from CDamage a where 1=1 ";
		String damCod = hdQuery.getStr("damCod");
		String damNam = hdQuery.getStr("damNam");
		QueryParamLs paramLs = new QueryParamLs();
		if(HdUtils.strNotNull(damCod)){
			jpql += "and a.damCod =:damCod ";
			paramLs.addParam("damCod", damCod);
		}
		if(HdUtils.strNotNull(damNam)){
			jpql += "and a.damNam =:damNam ";
			paramLs.addParam("damNam", damNam);
		}
		jpql += "order by a.recTim desc";
		return JpaUtils.findByEz(jpql, paramLs , hdQuery);
	}

	@Override
	public HdMessageCode save(HdEzuiSaveDatagridData<CDamage> hdEzuiSaveDatagridData) {
		// TODO Auto-generated method stub
		return JpaUtils.save(hdEzuiSaveDatagridData);
	}

	@Transactional
	public void removeAll(String damCods) {
		List<String> damCodList = HdUtils.paraseStrs(damCods);
		for (String damCod : damCodList) {
			JpaUtils.remove(CDamage.class, damCod);
		}
	}
	
	@Override
	public CDamage findone(String damCod) {
		CDamage cDamage = JpaUtils.findById(CDamage.class, damCod);
		return cDamage;

	}
	
	@Override
	public HdMessageCode saveone(@RequestBody CDamage cDamage) {
		String damCod = cDamage.getDamCod();
		CDamage cdamage = JpaUtils.findById(CDamage.class, damCod);
		if(cdamage != null){
			JpaUtils.update(cDamage);
		}else{
			JpaUtils.save(cDamage);
		}
		return HdUtils.genMsg();
	}
	
	@Override
	public HdMessageCode findCDamage(String damCod) {
		if(HdUtils.strNotNull(damCod)){
			CDamage cDamage = JpaUtils.findById(CDamage.class, damCod);
			if(cDamage != null){
				throw new HdRunTimeException("该代码已存在，请重新输入！");// 主键已存在
			}
		}
		return HdUtils.genMsg();
	}
}

