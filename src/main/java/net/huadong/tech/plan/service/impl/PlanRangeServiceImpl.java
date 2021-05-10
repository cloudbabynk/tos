package net.huadong.tech.plan.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.entity.CArea;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.tech.plan.entity.PlanRange;
import net.huadong.tech.plan.service.PlanRangeService;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;
import net.huadong.tech.util.HdUtils;

@Component
public class PlanRangeServiceImpl  implements PlanRangeService{
	public HdEzuiDatagridData find(HdQuery hdQuery) {
		String jpql = "select a from PlanRange a where 1=1 ";
		String planGroupNo=hdQuery.getStr("planGroupNo");
		QueryParamLs paramLs = new QueryParamLs();
		if (HdUtils.strNotNull(planGroupNo)) {
			long plangroupNo=Long.parseLong(planGroupNo);
			jpql += "and a.planGroupNo =:planGroupNo ";
			paramLs.addParam("planGroupNo", plangroupNo);
			jpql += "order by a.recTim desc";
			return JpaUtils.findByEz(jpql, paramLs, hdQuery);
		}else return null;
	
	}
	public HdMessageCode save(HdEzuiSaveDatagridData<PlanRange> hdEzuiSaveDatagridData) {
		// TODO Auto-generated method stub
	return JpaUtils.save(hdEzuiSaveDatagridData);
	}

	@Override
	public HdMessageCode saveone(PlanRange planRange) {
		//String jpql="select max(a.planRangeNo) from PlanRange a where a.planGroupNo=:planGroupNo";
		planRange.setPlanRangeNo(HdUtils.generateUUID());
		planRange.setSeqNo(new BigDecimal(0));
		JpaUtils.save(planRange);
		return HdUtils.genMsg();
	}
	@Override
	public HdMessageCode update(PlanRange planRange) {
		if("0".equals(planRange.getActiveId())||planRange.getActiveId()==null){
			planRange.setActiveId("1");	
		}else if("1".equals(planRange.getActiveId())){
			planRange.setActiveId("0");	
		}
		JpaUtils.update(planRange);
		return HdUtils.genMsg();
	}
	@Transactional
	@Override
	public void removeAll(String ids) {
		List<String> areaCodList = HdUtils.paraseStrs(ids);
		for (String areaCod : areaCodList) {
			JpaUtils.remove(PlanRange.class, areaCod);
		}
	}

}
