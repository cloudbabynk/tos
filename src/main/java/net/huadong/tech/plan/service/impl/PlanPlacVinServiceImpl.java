package net.huadong.tech.plan.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.bean.HdRunTimeException;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.tech.plan.entity.PlanGroup;
import net.huadong.tech.plan.entity.PlanPlacVin;
import net.huadong.tech.plan.entity.PlanRange;
import net.huadong.tech.plan.service.PlanPlacVinService;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;
import net.huadong.tech.util.HdUtils;

@Component
public class PlanPlacVinServiceImpl  implements PlanPlacVinService{
	public HdEzuiDatagridData find(HdQuery hdQuery) {
		// TODO Auto-generated method stub
		String jpql="select a from PlanPlacVin a where 1=1 ";
		String planGroupNo = hdQuery.getStr("planGroupNo");
		QueryParamLs paramLs = new QueryParamLs();
		if(HdUtils.strNotNull(planGroupNo)){
			jpql += "and a.planGroupNo =:planGroupNo ";
			paramLs.addParam("planGroupNo", planGroupNo);
		}
		
		jpql += "order by a.planGroupNo desc";
		return JpaUtils.findByEz(jpql, paramLs , hdQuery);
	}

	public HdMessageCode save(HdEzuiSaveDatagridData<PlanPlacVin> hdEzuiSaveDatagridData) {
		// TODO Auto-generated method stub
	return JpaUtils.save(hdEzuiSaveDatagridData);
	}

	@Override
	public HdMessageCode saveone(PlanPlacVin planPlacVin) {
		String jpql="select max(a.planVinNo) from PlanPlacVin a where a.planGroupNo=:planGroupNo";
		Long maxVinNo = null;
		QueryParamLs paramLs = new QueryParamLs();
		paramLs.addParam("planGroupNo",planPlacVin.getPlanGroupNo());
		List<Long> vinnoList=(List) JpaUtils.findAll(jpql, paramLs);
		if(vinnoList.size()>0){
			maxVinNo=vinnoList.get(0)+1;
		}
		planPlacVin.setPlanVinNo(maxVinNo);
		planPlacVin.setRecNam(HdUtils.getCurUser().getAccount());
		Date date=new Date(); 
		SimpleDateFormat df=new SimpleDateFormat("yyyy/MM/dd hh:mm:ss"); 
		String str=df.format(HdUtils.getDateTime().getTime());
		try {
			date=df.parse(str);
			planPlacVin.setRecTim(date);
			JpaUtils.save(planPlacVin);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return HdUtils.genMsg();
	}
	@Transactional
	public void removeAll(String vinNo) {
		String jpql="select a from PlanPlacVin a where 1=1 and a.vinNo=:vinNo ";
		QueryParamLs paramLs= new QueryParamLs();
		paramLs.addParam("vinNo",vinNo);
		List<PlanPlacVin> planPlacVinList=JpaUtils.findAll(jpql, paramLs);
		for(int i=0;i<planPlacVinList.size();i++){
			JpaUtils.remove(planPlacVinList.get(i));
		}
	}
}
