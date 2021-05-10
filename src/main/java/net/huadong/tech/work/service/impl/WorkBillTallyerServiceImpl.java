package net.huadong.tech.work.service.impl;
import com.alibaba.dubbo.config.annotation.Service;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.bean.HdRunTimeException;
import net.huadong.tech.base.entity.CWorkClass;
import net.huadong.tech.work.entity.WorkBillDriverClass;
import net.huadong.tech.work.entity.WorkBillTallyer;
import net.huadong.tech.work.service.WorkBillTallyerService;
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
public class WorkBillTallyerServiceImpl implements WorkBillTallyerService {
	@Override
	public HdEzuiDatagridData find(HdQuery hdQuery) {
		String jpql="select a from WorkBillTallyerClass a where 1=1 ";
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
		List<WorkBillTallyer> workBillTallyerList = result.getRows();
		for(WorkBillTallyer workBillTallyer : workBillTallyerList){
			if(HdUtils.strNotNull(workBillTallyer.getClassCod())){
				CWorkClass cWorkClass = JpaUtils.findById(CWorkClass.class, workBillTallyer.getClassCod());
				if(cWorkClass != null){
					workBillTallyer.setClassCodNam(cWorkClass.getClassNam());
				}
			}
		}
		return result;
	}

	@Override
	public HdMessageCode save(HdEzuiSaveDatagridData<WorkBillTallyer> hdEzuiSaveDatagridData,String workbillNo) {
		// TODO Auto-generated method stub
		List<WorkBillTallyer> WorkBillTallyerList = hdEzuiSaveDatagridData.getInsertedRows();
		for(WorkBillTallyer workBillDriver : WorkBillTallyerList){
			workBillDriver.setWorkbillNo(workbillNo);
			if(HdUtils.strNotNull(workBillDriver.getClassCodNam())){
				workBillDriver.setClassCod(workBillDriver.getClassCodNam());
			}
		}
		return JpaUtils.save(hdEzuiSaveDatagridData);
	}

	@Transactional
	public void removeAll(String classCods,String workbillNo) {
		List<String> classCodList = HdUtils.paraseStrs(classCods);
		for (String classCod : classCodList) {
			String jpql = "select a from WorkBillTallyer a where a.workbillNo=:workbillNo and a.classCod=:classCod";
			QueryParamLs paramLs = new QueryParamLs();
			paramLs.addParam("workbillNo", workbillNo);
			paramLs.addParam("classCod", classCod);
			List<WorkBillTallyer> WorkBillTallyerList = JpaUtils.findAll(jpql, paramLs);
			JpaUtils.removeAll(WorkBillTallyerList);
			
		}
	}
	
	@Override
	public WorkBillTallyer findone(String damCod) {
		WorkBillTallyer workBillTallyer = JpaUtils.findById(WorkBillTallyer.class, damCod);
		return workBillTallyer;

	}
	
	@Override
	public HdMessageCode saveone(@RequestBody WorkBillTallyer workBillTallyer) {
		if(HdUtils.strNotNull(workBillTallyer.getWorkbillNo())){
			JpaUtils.update(workBillTallyer);
		}else{
			String datetime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());  
			String id = datetime.substring(0,8) + workBillTallyer.getClassCod();
			String jpql = "select MAX(a.workbillNo) from WorkBill a where a.workbillNo like :workbillNo";
			QueryParamLs paramLs = new QueryParamLs();
			paramLs.addParam("workbillNo", id+"%");
			String workbillNo = JpaUtils.single(jpql, paramLs);
			if(HdUtils.strNotNull(workbillNo)){
				workBillTallyer.setWorkbillNo(workbillNo+1);
			}else{
				workBillTallyer.setWorkbillNo(id+"001");
			}
			JpaUtils.save(workBillTallyer);
		}
		return HdUtils.genMsg();
	}
	
}

