package net.huadong.tech.work.service.impl;
import com.alibaba.dubbo.config.annotation.Service;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.bean.HdRunTimeException;
import net.huadong.tech.base.entity.CEmployee;
import net.huadong.tech.base.entity.CMachTyp;
import net.huadong.tech.base.entity.CMachine;
import net.huadong.tech.work.entity.WorkBillDriverClass;
import net.huadong.tech.work.entity.WorkBillDriverSecond;
import net.huadong.tech.work.entity.WorkBillMachSecond;
import net.huadong.tech.work.service.WorkBillMachSecondService;
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
public class WorkBillMachSecondServiceImpl implements WorkBillMachSecondService {
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

	@Override
	public HdMessageCode save(HdEzuiSaveDatagridData<WorkBillMachSecond> hdEzuiSaveDatagridData,String workbillNo,String classCod) {
		// TODO Auto-generated method stub
		List<WorkBillMachSecond> WorkBillTallyerSecondList = hdEzuiSaveDatagridData.getInsertedRows();
		for(WorkBillMachSecond workBillTallyerSecond : WorkBillTallyerSecondList){
			workBillTallyerSecond.setWorkbillNo(workbillNo);
		}
		return JpaUtils.save(hdEzuiSaveDatagridData);
	}

	public HdEzuiDatagridData findMach(HdQuery hdQuery) {
		String jpql = "select a from  CMachine a  where 1=1";
		QueryParamLs paramLs = new QueryParamLs();
		String machTypCod = hdQuery.getStr("machTypCod");
		if(HdUtils.strIsNull(machTypCod)){
			jpql += " and a.machTyp =:machTyp";
			paramLs.addParam("machTyp", "1234567890#");
		}else{
			jpql += " and a.machTyp =:machTyp";
			paramLs.addParam("machTyp", machTypCod);
		}
		HdEzuiDatagridData result = JpaUtils.findByEz(jpql, paramLs, hdQuery);
		this.initName(result.getRows(), hdQuery);
		return result;
	}
	
	private void initName(List<CMachine> relLs, HdQuery hdQuery) {
		for (CMachine rel : relLs) {
			String workbillNo = hdQuery.getStr("workbillNo");
			String machTypCod = hdQuery.getStr("machTypCod");
			String jpql = "select a from WorkBillMachSecond a where a.workbillNo =:workbillNo and a.machTypCod=:machTypCod ";
			QueryParamLs paramLs = new QueryParamLs();
			paramLs.addParam("workbillNo", workbillNo);
			paramLs.addParam("machTypCod", machTypCod);
			List<WorkBillMachSecond> workBillMachSecondList = JpaUtils.findAll(jpql, paramLs);
			for (WorkBillMachSecond workBillMachSecond : workBillMachSecondList) {
				if (workBillMachSecond.getMachNo().equals(rel.getMachNo())) {
					rel.setChecked(true);
					if(HdUtils.strNotNull(workBillMachSecond.getDriverCod())){
						CEmployee cEmployee = JpaUtils.findById(CEmployee.class, workBillMachSecond.getDriverCod());
						if(cEmployee != null){
							rel.setDriverCod(cEmployee.getEmpNam());
						}
					}
					break;
				}
			}
		}
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
			List<WorkBillMachSecond> workBillDriverSecondList = JpaUtils.findAll(jpql1, paramLs);
			JpaUtils.removeAll(workBillDriverSecondList);
			JpaUtils.removeAll(WorkBillDriverClassList);
			
		}
	}
	
	@Override
	public WorkBillMachSecond findone(String damCod) {
		WorkBillMachSecond cDamage = JpaUtils.findById(WorkBillMachSecond.class, damCod);
		return cDamage;

	}
	
	@Override
	public HdMessageCode saveone(@RequestBody WorkBillMachSecond workBillMachSecond) {
		String jpql = "select a from WorkBillMachSecond a where a.workbillNo =:workbillNo and a.machTypCod =:machTypCod and a.machNo =:machNo and a.driverCod =:driverCod";
		QueryParamLs paramLs = new QueryParamLs();
		paramLs.addParam("workbillNo", workBillMachSecond.getWorkbillNo());
		paramLs.addParam("machTypCod", workBillMachSecond.getMachTypCod());
		paramLs.addParam("machNo", workBillMachSecond.getMachNo());
		paramLs.addParam("driverCod", workBillMachSecond.getDriverCod());
		List<WorkBillMachSecond> workBillMachSecondList = JpaUtils.findAll(jpql, paramLs);
		if(workBillMachSecondList.size()>0){
			throw new HdRunTimeException("已存在");// 存在
		}else{
			JpaUtils.save(workBillMachSecond);
		}
		
		return HdUtils.genMsg();
	}
	
	@Override
	public HdMessageCode removeone(@RequestBody WorkBillMachSecond workBill) {
		String jpql = "select a from WorkBillMachSecond a where a.workbillNo =:workbillNo and a.machTypCod =:machTypCod and a.machNo =:machNo";
		QueryParamLs paramLs = new QueryParamLs();
		paramLs.addParam("workbillNo", workBill.getWorkbillNo());
		paramLs.addParam("machTypCod", workBill.getMachTypCod());
		paramLs.addParam("machNo", workBill.getMachNo());
		List<WorkBillMachSecond> workBillMachSecondList = JpaUtils.findAll(jpql, paramLs);
		if(workBillMachSecondList.size()>0){
			JpaUtils.removeAll(workBillMachSecondList);
		}
		return HdUtils.genMsg();
	}

}

