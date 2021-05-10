package net.huadong.tech.work.service.impl;
import com.alibaba.dubbo.config.annotation.Service;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.bean.HdRunTimeException;
import net.huadong.tech.base.entity.CEmployee;
import net.huadong.tech.com.entity.AuthUserExtra;
import net.huadong.tech.work.entity.WorkBillDriverClass;
import net.huadong.tech.work.entity.WorkBillDriverSecond;
import net.huadong.tech.work.entity.WorkBillTallyerSecond;
import net.huadong.tech.work.service.WorkBillTallyerSecondService;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
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
public class WorkBillTallyerSecondServiceImpl implements WorkBillTallyerSecondService {
	@Override
	public HdEzuiDatagridData find(HdQuery hdQuery) {
		String jpql="select a from WorkBillTallyerSecond a where 1=1 ";
		String workbillNo = hdQuery.getStr("workbillNo");
		QueryParamLs paramLs = new QueryParamLs();
		String classCod = hdQuery.getStr("classCod");
		if(HdUtils.strNotNull(workbillNo)){
			jpql += "and a.workbillNo =:workbillNo ";
			paramLs.addParam("workbillNo", workbillNo);
		}
		if(HdUtils.strNotNull(classCod)){
			jpql += "and a.classCod =:classCod ";
			paramLs.addParam("classCod", classCod);
		}
		jpql += "order by a.recTim desc";
		return JpaUtils.findByEz(jpql, paramLs , hdQuery);
	}
	
	public HdEzuiDatagridData findTallyer(HdQuery hdQuery) {
		String jpql = "select a from  CEmployee a  where a.onDutyId =:onDutyId and a.empTypCod =:empTypCod";
		QueryParamLs paramLs = new QueryParamLs();
		paramLs.addParam("onDutyId", "1");// 是否在职标志
		String classCod = hdQuery.getStr("classCod");
		if(HdUtils.strIsNull(classCod)){
			paramLs.addParam("empTypCod", "1234567890#");
		}else{
			paramLs.addParam("empTypCod", "05");//05代表理货员
		}
		HdEzuiDatagridData result = JpaUtils.findByEz(jpql, paramLs, hdQuery);
		this.initName(result.getRows(), hdQuery);
		return result;
	}
	
	private void initName(List<CEmployee> relLs, HdQuery hdQuery) {
		for (CEmployee rel : relLs) {
			String workbillNo = hdQuery.getStr("workbillNo");
			String classCod = hdQuery.getStr("classCod");
			String jpql = "select a from WorkBillTallyerSecond a where a.workbillNo =:workbillNo and a.classCod=:classCod ";
			QueryParamLs paramLs = new QueryParamLs();
			paramLs.addParam("workbillNo", workbillNo);
			paramLs.addParam("classCod", classCod);
			List<WorkBillTallyerSecond> workBillTallyerSecondList = JpaUtils.findAll(jpql, paramLs);
			for (WorkBillTallyerSecond workBillTallyerSecond : workBillTallyerSecondList) {
				if (workBillTallyerSecond.getTallyCod().equals(rel.getEmpNo())) {
					rel.setChecked(true);
					break;
				}
			}
		}
	}
	
	@Override
	public HdMessageCode removeone(@RequestBody WorkBillTallyerSecond workBill) {
		String jpql = "select a from WorkBillTallyerSecond a where a.workbillNo =:workbillNo and a.classCod =:classCod and a.tallyCod =:tallyCod";
		QueryParamLs paramLs = new QueryParamLs();
		paramLs.addParam("workbillNo", workBill.getWorkbillNo());
		paramLs.addParam("classCod", workBill.getClassCod());
		paramLs.addParam("tallyCod", workBill.getTallyCod());
		List<WorkBillTallyerSecond> workBillTallyerSecondList = JpaUtils.findAll(jpql, paramLs);
		if(workBillTallyerSecondList.size()>0){
			JpaUtils.removeAll(workBillTallyerSecondList);
		}
		return HdUtils.genMsg();
	}


	@Override
	public HdMessageCode save(HdEzuiSaveDatagridData<WorkBillTallyerSecond> hdEzuiSaveDatagridData,String workbillNo,String classCod) {
		// TODO Auto-generated method stub
		List<WorkBillTallyerSecond> WorkBillTallyerSecondList = hdEzuiSaveDatagridData.getInsertedRows();
		for(WorkBillTallyerSecond workBillTallyerSecond : WorkBillTallyerSecondList){
			workBillTallyerSecond.setWorkbillNo(workbillNo);
			workBillTallyerSecond.setClassCod(classCod);
		}
		return JpaUtils.save(hdEzuiSaveDatagridData);
	}

	@Transactional
	public void removeAll(String classCods,String workbillNo) {
		List<String> classCodList = HdUtils.paraseStrs(classCods);
		for (String classCod : classCodList) {
			String jpql = "select a from WorkBillDriverClass a where a.workbillNo=:workbillNo and a.classCod=:classCod";
			String jpql1 = "select a from WorkBillDriverSecond a where a.workbillNo=:workbillNo and a.classCod=:classCod";
			QueryParamLs paramLs = new QueryParamLs();
			paramLs.addParam("workbillNo", workbillNo);
			paramLs.addParam("classCod", classCod);
			List<WorkBillDriverClass> WorkBillDriverClassList = JpaUtils.findAll(jpql, paramLs);
			List<WorkBillTallyerSecond> workBillDriverSecondList = JpaUtils.findAll(jpql1, paramLs);
			JpaUtils.removeAll(workBillDriverSecondList);
			JpaUtils.removeAll(WorkBillDriverClassList);
			
		}
	}
	
	@Override
	public WorkBillTallyerSecond findone(String damCod) {
		WorkBillTallyerSecond cDamage = JpaUtils.findById(WorkBillTallyerSecond.class, damCod);
		return cDamage;

	}
	
	@Override
	public HdMessageCode saveone(@RequestBody WorkBillTallyerSecond workBill) {
		String jpql = "select a from WorkBillTallyerSecond a where a.workbillNo =:workbillNo and a.classCod =:classCod and a.tallyCod =:tallyCod";
		QueryParamLs paramLs = new QueryParamLs();
		paramLs.addParam("workbillNo", workBill.getWorkbillNo());
		paramLs.addParam("classCod", workBill.getClassCod());
		paramLs.addParam("tallyCod", workBill.getTallyCod());
		List<WorkBillTallyerSecond> workBillTallyerSecondList = JpaUtils.findAll(jpql, paramLs);
		if(workBillTallyerSecondList.size()>0){
			throw new HdRunTimeException("已存在");// 进口航次重复
		}else{
			JpaUtils.save(workBill);
		}
		
		return HdUtils.genMsg();
	}
	
}

