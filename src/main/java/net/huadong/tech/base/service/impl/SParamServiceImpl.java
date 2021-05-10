package net.huadong.tech.base.service.impl;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.bean.HdRunTimeException;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.entity.SParam;
import net.huadong.tech.base.service.SParamService;

import java.util.List;
import java.util.UUID;

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
public class SParamServiceImpl implements SParamService {
	@Override
	public HdEzuiDatagridData find(HdQuery hdQuery) {
		String jpql="select a from SParam a where 1=1";
		String paramCod = hdQuery.getStr("paramCod");
		String paramNam = hdQuery.getStr("paramNam");
		QueryParamLs paramLs = new QueryParamLs();
		if(HdUtils.strNotNull(paramCod)){
			jpql += " and a.paramCod =:paramCod ";
			paramLs.addParam("paramCod", paramCod);
		}
		if(HdUtils.strNotNull(paramNam)){
			jpql += " and a.paramNam =:paramNam ";
			paramLs.addParam("paramNam", paramNam);
		}
		jpql += " order by a.recTim desc";
	
		return JpaUtils.findByEz(jpql, paramLs , hdQuery);
	}

	@Override
	public HdMessageCode save(HdEzuiSaveDatagridData<SParam> hdEzuiSaveDatagridData) {
		// TODO Auto-generated method stub
		return JpaUtils.save(hdEzuiSaveDatagridData);
	}
	@Transactional
	public void removeAll(String paramId) {
		List<String> paramCodList = HdUtils.paraseStrs(paramId);
		for (String paramCod : paramCodList) {
			JpaUtils.remove(SParam.class, paramCod);
		}
	}
	@Transactional
	public void removeById(String paramId) {
			JpaUtils.remove(SParam.class, paramId);
	}
	@Override
	public SParam findone(String paramId) {
		SParam sParam = JpaUtils.findById(SParam.class, paramId);
		return sParam;

	}
	

	@Override
	public HdMessageCode saveone(@RequestBody SParam sParam) {
		if(HdUtils.strNotNull(sParam.getParamId())){
			JpaUtils.update(sParam);
		}else{
			String uuid=UUID.randomUUID().toString();
			SParam sp = JpaUtils.findById(SParam.class, uuid);
			if(sp != null){
				throw new HdRunTimeException("该代码已存在，请重新输入！");// 主键已存在
			}else {
			sParam.setParamId(uuid);
			JpaUtils.save(sParam);}
		}
		return HdUtils.genMsg();
	}
}

