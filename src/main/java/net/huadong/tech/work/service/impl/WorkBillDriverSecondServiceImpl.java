package net.huadong.tech.work.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.bean.HdRunTimeException;
import net.huadong.tech.base.entity.CEmployee;
import net.huadong.tech.com.entity.AuthUserExtra;
import net.huadong.tech.work.entity.WorkBillDriverClass;
import net.huadong.tech.work.entity.WorkBillDriverSecond;
import net.huadong.tech.work.service.WorkBillDriverSecondService;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.tech.privilege.entity.AuthUser;
import net.huadong.idev.hdmessagecode.HdMessageCode;

import java.text.SimpleDateFormat;
import java.util.Date;
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
public class WorkBillDriverSecondServiceImpl implements WorkBillDriverSecondService {
	@Override
	public HdEzuiDatagridData find(HdQuery hdQuery) {
		String jpql = "select a from WorkBillDriverSecond a where 1=1 ";
		String workbillNo = hdQuery.getStr("workbillNo");
		QueryParamLs paramLs = new QueryParamLs();
		String classCod = hdQuery.getStr("classCod");
		if (HdUtils.strNotNull(workbillNo)) {
			jpql += "and a.workbillNo =:workbillNo ";
			paramLs.addParam("workbillNo", workbillNo);
		}
		if (HdUtils.strNotNull(classCod)) {
			jpql += "and a.classCod =:classCod ";
			paramLs.addParam("classCod", classCod);
		}
		jpql += "order by a.recTim desc";
		return JpaUtils.findByEz(jpql, paramLs, hdQuery);
	}

	@Override
	public HdMessageCode save(HdEzuiSaveDatagridData<WorkBillDriverSecond> hdEzuiSaveDatagridData, String workbillNo,
			String classCod) {
		// TODO Auto-generated method stub
		List<WorkBillDriverSecond> WorkBillDriverSecondList = hdEzuiSaveDatagridData.getInsertedRows();
		for (WorkBillDriverSecond workBillDriverSecond : WorkBillDriverSecondList) {
			workBillDriverSecond.setWorkbillNo(workbillNo);
			workBillDriverSecond.setClassCod(classCod);
		}
		return JpaUtils.save(hdEzuiSaveDatagridData);
	}

	@Transactional
	public void removeAll(String driverCods, String workbillNo, String classCod) {
		List<String> driverCodList = HdUtils.paraseStrs(driverCods);
		for (String driverCod : driverCodList) {
			String jpql = "select a from WorkBillDriverSecond a where a.workbillNo=:workbillNo and a.classCod=:classCod and a.driverCod=:driverCod";
			QueryParamLs paramLs = new QueryParamLs();
			paramLs.addParam("workbillNo", workbillNo);
			paramLs.addParam("classCod", classCod);
			paramLs.addParam("driverCod", driverCod);
			List<WorkBillDriverClass> WorkBillDriverClassList = JpaUtils.findAll(jpql, paramLs);
			JpaUtils.removeAll(WorkBillDriverClassList);
		}
	}

	@Override
	public WorkBillDriverSecond findone(String damCod) {
		WorkBillDriverSecond cDamage = JpaUtils.findById(WorkBillDriverSecond.class, damCod);
		return cDamage;
	}

	public HdEzuiDatagridData findDriver(HdQuery hdQuery) {
		String jpql = "select a from  CEmployee a  where a.onDutyId =:onDutyId and a.empTypCod =:empTypCod";
		QueryParamLs paramLs = new QueryParamLs();
		paramLs.addParam("onDutyId", "1");// 是否在职标志
		String classCod = hdQuery.getStr("classCod");
		if(HdUtils.strIsNull(classCod)){
			paramLs.addParam("empTypCod", "1234567890#");//过滤
		}else{
			paramLs.addParam("empTypCod", "08");//08代表司机
		}
		HdEzuiDatagridData result = JpaUtils.findByEz(jpql, paramLs, hdQuery);
		this.initName(result.getRows(), hdQuery);
		return result;
	}

	private void initName(List<CEmployee> relLs, HdQuery hdQuery) {
		for (CEmployee rel : relLs) {
			String workbillNo = hdQuery.getStr("workbillNo");
			String classCod = hdQuery.getStr("classCod");
			String jpql = "select a from WorkBillDriverSecond a where a.workbillNo =:workbillNo and a.classCod=:classCod ";
			QueryParamLs paramLs = new QueryParamLs();
			paramLs.addParam("workbillNo", workbillNo);
			paramLs.addParam("classCod", classCod);
			List<WorkBillDriverSecond> workBillDriverSecondList = JpaUtils.findAll(jpql, paramLs);
			for (WorkBillDriverSecond workBillDriverSecond : workBillDriverSecondList) {
				if (workBillDriverSecond.getDriverCod().equals(rel.getEmpNo())) {
					rel.setChecked(true);
					break;
				}
			}
		}
	}

	@Override
	public HdMessageCode saveone(@RequestBody WorkBillDriverSecond workBill) {
		String jpql = "select a from WorkBillDriverSecond a where a.workbillNo =:workbillNo and a.classCod =:classCod and a.driverCod =:driverCod";
		QueryParamLs paramLs = new QueryParamLs();
		paramLs.addParam("workbillNo", workBill.getWorkbillNo());
		paramLs.addParam("classCod", workBill.getClassCod());
		paramLs.addParam("driverCod", workBill.getDriverCod());
		List<WorkBillDriverSecond> workBillDriverSecondList = JpaUtils.findAll(jpql, paramLs);
		if(workBillDriverSecondList.size()>0){
			throw new HdRunTimeException("已存在");// 进口航次重复
		}else{
			JpaUtils.save(workBill);
		}
		
		return HdUtils.genMsg();
	}

	@Override
	public HdMessageCode removeone(@RequestBody WorkBillDriverSecond workBill) {
		String jpql = "select a from WorkBillDriverSecond a where a.workbillNo =:workbillNo and a.classCod =:classCod and a.driverCod =:driverCod";
		QueryParamLs paramLs = new QueryParamLs();
		paramLs.addParam("workbillNo", workBill.getWorkbillNo());
		paramLs.addParam("classCod", workBill.getClassCod());
		paramLs.addParam("driverCod", workBill.getDriverCod());
		List<WorkBillDriverSecond> workBillDriverSecondList = JpaUtils.findAll(jpql, paramLs);
		if(workBillDriverSecondList.size()>0){
			JpaUtils.removeAll(workBillDriverSecondList);
		}
		return HdUtils.genMsg();
	}

}
