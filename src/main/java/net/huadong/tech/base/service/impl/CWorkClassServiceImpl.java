package net.huadong.tech.base.service.impl;
import com.alibaba.dubbo.config.annotation.Service;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.bean.HdRunTimeException;
import net.huadong.tech.base.entity.CCarKind;
import net.huadong.tech.base.entity.CWorkClass;
import net.huadong.tech.base.service.CWorkClassService;
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
public class CWorkClassServiceImpl implements CWorkClassService {
	@Override
	public HdEzuiDatagridData find(HdQuery hdQuery) {
		String jpql="select a from CWorkClass a where 1=1 ";
		String classCod = hdQuery.getStr("classCod");
		String classNam = hdQuery.getStr("classNam");
		QueryParamLs paramLs = new QueryParamLs();
		if(HdUtils.strNotNull(classCod)){
			jpql += "and a.classCod =:classCod ";
			paramLs.addParam("damCod", classCod);
		}
		if(HdUtils.strNotNull(classNam)){
			jpql += "and a.classNam =:classNam ";
			paramLs.addParam("classNam", classNam);
		}
		jpql += "order by a.recTim desc";
		return JpaUtils.findByEz(jpql, paramLs , hdQuery);
	}

	@Override
	public HdMessageCode save(HdEzuiSaveDatagridData<CWorkClass> hdEzuiSaveDatagridData) {
		// TODO Auto-generated method stub
		return JpaUtils.save(hdEzuiSaveDatagridData);
	}

	@Transactional
	public void removeAll(String classCods) {
		List<String> classCodList = HdUtils.paraseStrs(classCods);
		for (String classCod : classCodList) {
			JpaUtils.remove(CWorkClass.class, classCod);
		}
	}
	
	@Override
	public CWorkClass findone(String classCod) {
		CWorkClass cWorkClass = JpaUtils.findById(CWorkClass.class, classCod);
		return cWorkClass;

	}
	
	@Override
	public HdMessageCode saveone(@RequestBody CWorkClass cWorkClass) {
		String classCod = cWorkClass.getClassCod();
		CWorkClass cworkclass = JpaUtils.findById(CWorkClass.class, classCod);
		if(cworkclass != null){
			JpaUtils.update(cWorkClass);
		}else{
			JpaUtils.save(cWorkClass);
		}
		return HdUtils.genMsg();
	}
	
	@Override
	public HdMessageCode findCWorkClass(String classCod) {
		if(HdUtils.strNotNull(classCod)){
			CWorkClass cWorkClass = JpaUtils.findById(CWorkClass.class, classCod);
			if(cWorkClass != null){
				throw new HdRunTimeException("该代码已存在，请重新输入！");// 主键已存在
			}
		}
		return HdUtils.genMsg();
	}
}

