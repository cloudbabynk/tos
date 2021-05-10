package net.huadong.tech.plan.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.bean.HdRunTimeException;
import net.huadong.tech.base.entity.CCarTyp;
import net.huadong.tech.base.entity.CClientCod;
import net.huadong.tech.base.entity.SParam;
import net.huadong.tech.plan.entity.ConstructionPlan;
import net.huadong.tech.plan.service.ConstructionPlanService;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;
import net.huadong.tech.util.HdUtils;

@Component
public class ConstructionPlanServiceImpl implements ConstructionPlanService {
	@Override
	public HdEzuiDatagridData find(HdQuery hdQuery) {
		// TODO Auto-generated method stub
		String jpql="select a from ConstructionPlan a where 1=1 ";
		String days = hdQuery.getStr("days");
		QueryParamLs paramLs = new QueryParamLs();
		
		if (HdUtils.strNotNull(days)) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = null;
			try {
				date = (Date) sdf.parse(days);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			jpql += "and a.days =:date ";
			paramLs.addParam("date", date);
		}
		jpql += "order by a.recTim desc";
		HdEzuiDatagridData result = JpaUtils.findByEz(jpql, paramLs, hdQuery);
		List<ConstructionPlan> constructionPlanList = result.getRows();
		for(ConstructionPlan constructionPlan:constructionPlanList){
		if(HdUtils.strNotNull(constructionPlan.getConsCorpCod())){
			CClientCod ccd=JpaUtils.findById(CClientCod.class,constructionPlan.getConsCorpCod());
			constructionPlan.setConsCorpNam(ccd==null?"":ccd.getcClientNam());
		}	
		if(HdUtils.strNotNull(constructionPlan.getDanWorkItems())){
			constructionPlan.setDanWorkItemsName(HdUtils.getSysCodeName("DAN_WORK_ITEMS",constructionPlan.getDanWorkItems()));	
		}
		}
		return result;
	}

	@Override
	public HdMessageCode save(HdEzuiSaveDatagridData<ConstructionPlan> hdEzuiSaveDatagridData) {
		// TODO Auto-generated method stub
	return JpaUtils.save(hdEzuiSaveDatagridData);
	}
	@Transactional
	public void removeAll(String planIds) {
		// TODO Auto-generated method stub
		List<String> planIdList = HdUtils.paraseStrs(planIds);
		for (String planId : planIdList) {
			JpaUtils.remove(ConstructionPlan.class, planId);
		}	
	}

	@Override
	public ConstructionPlan findone(String planId) {
		// TODO Auto-generated method stub
		ConstructionPlan constructionPlan = JpaUtils.findById(ConstructionPlan.class, planId);
		return constructionPlan;

	}

	@Override
	public HdMessageCode saveone(@RequestBody ConstructionPlan constructionPlan) {
		if(HdUtils.strNotNull(constructionPlan.getPlanId())){
			JpaUtils.update(constructionPlan);
		}else{
			String uuid=HdUtils.genUuid();
		    ConstructionPlan cp = JpaUtils.findById(ConstructionPlan.class, uuid);
			if(cp != null){
				throw new HdRunTimeException("该代码已存在，请重新输入！");// 主键已存在
			}else {
			constructionPlan.setPlanId(uuid);
			String jpql="select a from ConstructionPlan  a where a.days=:days and a.workItem=:workItem ";
			QueryParamLs paramLs=new QueryParamLs();
			paramLs.addParam("days",constructionPlan.getDays());
			paramLs.addParam("workItem",constructionPlan.getWorkItem());
			List<ConstructionPlan> constructionList=JpaUtils.findAll(jpql, paramLs);
			if(constructionList.size()>0){
				throw new HdRunTimeException("该天改作业项目已存在,请重新输入!");// 主键已存在	
			}
			JpaUtils.save(constructionPlan);}
		}
		return HdUtils.genMsg();
	}
	
}

