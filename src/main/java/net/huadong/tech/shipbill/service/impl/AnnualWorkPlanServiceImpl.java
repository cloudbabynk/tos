package net.huadong.tech.shipbill.service.impl;
import com.alibaba.dubbo.config.annotation.Service;
import net.huadong.tech.base.bean.EzTreeBean;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.bean.HdRunTimeException;
import net.huadong.tech.base.entity.CBerth;
import net.huadong.tech.base.entity.CClientCod;
import net.huadong.tech.base.entity.CDamage;
import net.huadong.tech.base.entity.CPort;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.tech.ship.entity.CShipData;
import net.huadong.tech.shipbill.entity.AnnualWorkPlan;
import net.huadong.tech.ship.entity.ShipLoadPlan;
import net.huadong.tech.shipbill.service.AnnualWorkPlanService;
import net.huadong.tech.shipbill.entity.ShipBill;
import net.huadong.idev.hdmessagecode.HdMessageCode;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiQueryParams;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;
import net.huadong.tech.util.HdUtils;
/**
 * @author
 */
@Component
public class AnnualWorkPlanServiceImpl implements AnnualWorkPlanService {
	@Override
	public HdEzuiDatagridData find(HdQuery hdQuery) {
		String jpql = "select a from AnnualWorkPlan a where 1=1 ";
		QueryParamLs paramLs = new QueryParamLs();
		String dispatchDte = hdQuery.getStr("dispatchDte");
		jpql += "order by a.recTim desc";
		HdEzuiDatagridData result = JpaUtils.findByEz(jpql, paramLs, hdQuery);
		return result;
	}
	@Override
	public HdMessageCode save(HdEzuiSaveDatagridData<AnnualWorkPlan> hdEzuiSaveDatagridData) {
		List<AnnualWorkPlan> insertList = hdEzuiSaveDatagridData.getInsertedRows();
		for(AnnualWorkPlan bean : insertList){
			bean.setPlanId(HdUtils.genUuid());
		}
		return JpaUtils.save(hdEzuiSaveDatagridData);
	}
	@Transactional
	public void removeAll(String planIds) {
		List<String> planIdList = HdUtils.paraseStrs(planIds);
		for (String planId : planIdList) {
			JpaUtils.remove(AnnualWorkPlan.class, planId);
		}
	}
	@Override
	public AnnualWorkPlan findone(String planId) {
		AnnualWorkPlan annualWorkPlan = JpaUtils.findById(AnnualWorkPlan.class, planId);
		return annualWorkPlan;
	}
	@Override
	@Transactional
	public HdMessageCode saveone(@RequestBody AnnualWorkPlan annualWorkPlan) {
		if (HdUtils.strNotNull(annualWorkPlan.getPlanId())) {
			JpaUtils.update(annualWorkPlan);
		} else {
			annualWorkPlan.setPlanId(HdUtils.genUuid());
			JpaUtils.save(annualWorkPlan);
		}
		return HdUtils.genMsg();
	}
}
