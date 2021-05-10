package net.huadong.tech.work.service.impl;
import com.alibaba.dubbo.config.annotation.Service;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.bean.HdRunTimeException;
import net.huadong.tech.base.entity.CBerth;
import net.huadong.tech.base.entity.CMachTyp;
import net.huadong.tech.work.entity.WorkBillDriverClass;
import net.huadong.tech.work.entity.WorkBillMach;
import net.huadong.tech.work.service.WorkBillMachService;
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
public class WorkBillMachServiceImpl implements WorkBillMachService {
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
		List<WorkBillMach> workbillmachList = result.getRows();
		for (WorkBillMach workBillMach : workbillmachList) {
			if(HdUtils.strNotNull(workBillMach.getMachTypCod())){
				CMachTyp cMachTyp = JpaUtils.findById(CMachTyp.class, workBillMach.getMachTypCod());
				if(cMachTyp != null){
					workBillMach.setMachTypCodNam(cMachTyp.getMachTyp());
				}
			}
		}
		return result;
	}

	@Override
	public HdMessageCode save(HdEzuiSaveDatagridData<WorkBillMach> hdEzuiSaveDatagridData,String workbillNo) {
		// TODO Auto-generated method stub
		List<WorkBillMach> WorkBillTallyerClassList = hdEzuiSaveDatagridData.getInsertedRows();
		for(WorkBillMach workBillDriverClass : WorkBillTallyerClassList){
			workBillDriverClass.setWorkbillNo(workbillNo);
		}
		return JpaUtils.save(hdEzuiSaveDatagridData);
	}

	@Transactional
	public void removeAll(String classCods,String workbillNo) {
		List<String> classCodList = HdUtils.paraseStrs(classCods);
		for (String classCod : classCodList) {
			String jpql = "select a from WorkBillTallyerClass a where a.workbillNo=:workbillNo and a.classCod=:classCod";
			String jpql1 = "select a from WorkBillTallyerSecond a where a.workbillNo=:workbillNo and a.classCod=:classCod";
			QueryParamLs paramLs = new QueryParamLs();
			paramLs.addParam("workbillNo", workbillNo);
			paramLs.addParam("classCod", classCod);
			List<WorkBillDriverClass> WorkBillDriverClassList = JpaUtils.findAll(jpql, paramLs);
			List<WorkBillMach> workBillDriverSecondList = JpaUtils.findAll(jpql1, paramLs);
			JpaUtils.removeAll(workBillDriverSecondList);
			JpaUtils.removeAll(WorkBillDriverClassList);
			
		}
	}
	
	@Override
	public WorkBillMach findone(String damCod) {
		WorkBillMach workBillMach = JpaUtils.findById(WorkBillMach.class, damCod);
		return workBillMach;

	}
	
	@Override
	public HdMessageCode saveone(@RequestBody WorkBillMach workBillMach) {
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

