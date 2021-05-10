package net.huadong.tech.work.service.impl;
import com.alibaba.dubbo.config.annotation.Service;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.bean.HdRunTimeException;
import net.huadong.tech.base.entity.CBerth;
import net.huadong.tech.base.entity.CMachTyp;
import net.huadong.tech.work.entity.WorkBillDriverClass;
import net.huadong.tech.work.entity.WorkBillMachine;
import net.huadong.tech.work.service.WorkBillMachineService;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.tech.ship.entity.Ship;
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
public class WorkBillMachineServiceImpl implements WorkBillMachineService {
	@Override
	public HdEzuiDatagridData find(HdQuery hdQuery) {
		String jpql="select a from WorkBillMach a where 1=1 ";
		String workbillNo = hdQuery.getStr("workbillNo");
		QueryParamLs paramLs = new QueryParamLs();
		if(HdUtils.strNotNull(workbillNo)){
			jpql += "and a.workbillNo =:workbillNo ";
			paramLs.addParam("workbillNo", workbillNo);
		}else{
			jpql += "and a.workbillNo =:workbillNo ";
			paramLs.addParam("workbillNo", "1234567890#");
		}
		jpql += "order by a.recTim desc";
		HdEzuiDatagridData result = JpaUtils.findByEz(jpql, paramLs, hdQuery);
		List<WorkBillMachine> workbillmachList = result.getRows();
		for (WorkBillMachine workBillMachine : workbillmachList) {
			if(HdUtils.strNotNull(workBillMachine.getMachTypCod())){
				CMachTyp cMachTyp = JpaUtils.findById(CMachTyp.class, workBillMachine.getMachTypCod());
				if(cMachTyp != null){
					workBillMachine.setMachTypCodNam(cMachTyp.getMachTyp());
				}
			}
		}
		return result;
	}

	@Override
	public HdMessageCode save(HdEzuiSaveDatagridData<WorkBillMachine> hdEzuiSaveDatagridData,String workbillNo) {
		// TODO Auto-generated method stub
		List<WorkBillMachine> WorkBillTallyerClassList = hdEzuiSaveDatagridData.getInsertedRows();
		for(WorkBillMachine workBillDriverClass : WorkBillTallyerClassList){
			workBillDriverClass.setWorkbillNo(workbillNo);
		}
		return JpaUtils.save(hdEzuiSaveDatagridData);
	}

	@Transactional
	public void removeAll(String classCods,String workbillNo) {
		List<String> classCodList = HdUtils.paraseStrs(classCods);
		for (String classCod : classCodList) {
			String jpql = "select a from WorkBillMachine a where a.workbillNo=:workbillNo and a.classCod=:classCod";
			QueryParamLs paramLs = new QueryParamLs();
			paramLs.addParam("workbillNo", workbillNo);
			paramLs.addParam("classCod", classCod);
			List<WorkBillMachine> workBillDriverSecondList = JpaUtils.findAll(jpql, paramLs);
			JpaUtils.removeAll(workBillDriverSecondList);
			
		}
	}
	
	@Override
	public WorkBillMachine findone(String damCod) {
		WorkBillMachine workBillMach = JpaUtils.findById(WorkBillMachine.class, damCod);
		return workBillMach;

	}
	
	@Override
	public HdMessageCode saveone(@RequestBody WorkBillMachine workBillMach) {
		if(HdUtils.strNotNull(workBillMach.getWorkbillNo())){
			JpaUtils.update(workBillMach);
		}else{
			String datetime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());  
			String id = datetime.substring(0,8) + workBillMach.getMachTypCod();
			String jpql = "select MAX(a.workbillNo) from WorkBill a where a.workbillNo like :workbillNo";
			QueryParamLs paramLs = new QueryParamLs();
			paramLs.addParam("workbillNo", id+"%");
			String workbillNo = JpaUtils.single(jpql, paramLs);
			if(HdUtils.strNotNull(workbillNo)){
				workBillMach.setWorkbillNo(workbillNo+1);
			}else{
				workBillMach.setWorkbillNo(id+"001");
			}
			JpaUtils.save(workBillMach);
		}
		return HdUtils.genMsg();
	}
	
}

