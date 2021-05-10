package net.huadong.tech.base.service.impl;
import com.alibaba.dubbo.config.annotation.Service;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.bean.HdRunTimeException;
import net.huadong.tech.base.entity.CCarKind;
import net.huadong.tech.base.entity.CSpecPlac;
import net.huadong.tech.base.service.CSpecPlacService;
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
public class CSpecPlacServiceImpl implements CSpecPlacService {
	@Override
	public HdEzuiDatagridData find(HdQuery hdQuery) {
		String jpql="select a from CSpecPlac a where 1=1 ";
		String specPlac = hdQuery.getStr("specPlac");
		String specPlacNam = hdQuery.getStr("specPlacNam");
		QueryParamLs paramLs = new QueryParamLs();
		if(HdUtils.strNotNull(specPlac)){
			jpql += "and a.specPlac =:specPlac ";
			paramLs.addParam("specPlac", specPlac);
		}
		if(HdUtils.strNotNull(specPlacNam)){
			jpql += "and a.specPlacNam =:specPlacNam ";
			paramLs.addParam("specPlacNam", specPlacNam);
		}
		jpql += "order by a.recTim desc";
		return JpaUtils.findByEz(jpql, paramLs , hdQuery);
	}

	@Override
	public HdMessageCode save(HdEzuiSaveDatagridData<CSpecPlac> hdEzuiSaveDatagridData) {
		// TODO Auto-generated method stub
		return JpaUtils.save(hdEzuiSaveDatagridData);
	}

	@Transactional
	public void removeAll(String specPlacs) {
		List<String> specPlacList = HdUtils.paraseStrs(specPlacs);
		for (String specPlac : specPlacList) {
			JpaUtils.remove(CSpecPlac.class, specPlac);
		}
	}
	
	@Override
	public CSpecPlac findone(String specPlac) {
		CSpecPlac cSpecPlac = JpaUtils.findById(CSpecPlac.class, specPlac);
		return cSpecPlac;

	}
	
	@Override
	public HdMessageCode saveone(@RequestBody CSpecPlac cSpecPlac) {
		String specPlac = cSpecPlac.getSpecPlac();
		CSpecPlac cspacplac = JpaUtils.findById(CSpecPlac.class, specPlac);
		if(cspacplac != null){
			JpaUtils.update(cSpecPlac);
		}else{
			JpaUtils.save(cSpecPlac);
		}
		return HdUtils.genMsg();
	}
	
	@Override
	public HdMessageCode findCSpecPlac(String specPlac) {
		if(HdUtils.strNotNull(specPlac)){
			CSpecPlac cSpecPlac = JpaUtils.findById(CSpecPlac.class, specPlac);
			if(cSpecPlac != null){
				throw new HdRunTimeException("该代码已存在，请重新输入！");// 主键已存在
			}
		}
		return HdUtils.genMsg();
	}
}

