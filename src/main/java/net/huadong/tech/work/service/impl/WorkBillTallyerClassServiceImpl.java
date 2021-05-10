package net.huadong.tech.work.service.impl;
import com.alibaba.dubbo.config.annotation.Service;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.bean.HdRunTimeException;
import net.huadong.tech.base.entity.CWorkClass;
import net.huadong.tech.work.entity.WorkBillDriverClass;
import net.huadong.tech.work.entity.WorkBillTallyerClass;
import net.huadong.tech.work.service.WorkBillTallyerClassService;
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
public class WorkBillTallyerClassServiceImpl implements WorkBillTallyerClassService {
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
		List<WorkBillTallyerClass> workBillTallyerClassList = result.getRows();
		for(WorkBillTallyerClass workBillTallyerClass : workBillTallyerClassList){
			if(HdUtils.strNotNull(workBillTallyerClass.getClassCod())){
				CWorkClass cWorkClass = JpaUtils.findById(CWorkClass.class, workBillTallyerClass.getClassCod());
				if(cWorkClass != null){
					workBillTallyerClass.setClassCodNam(cWorkClass.getClassNam());
				}
			}
		}
		return result;
	}

	@Override
	public HdMessageCode save(HdEzuiSaveDatagridData<WorkBillTallyerClass> hdEzuiSaveDatagridData,String workbillNo) {
		// TODO Auto-generated method stub
		List<WorkBillTallyerClass> WorkBillTallyerClassList = hdEzuiSaveDatagridData.getInsertedRows();
		for(WorkBillTallyerClass workBillDriverClass : WorkBillTallyerClassList){
			workBillDriverClass.setWorkbillNo(workbillNo);
			if(HdUtils.strNotNull(workBillDriverClass.getClassCodNam())){
				workBillDriverClass.setClassCod(workBillDriverClass.getClassCodNam());
			}
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
			List<WorkBillTallyerClass> workBillDriverSecondList = JpaUtils.findAll(jpql1, paramLs);
			JpaUtils.removeAll(workBillDriverSecondList);
			JpaUtils.removeAll(WorkBillDriverClassList);
			
		}
	}
	
	@Override
	public WorkBillTallyerClass findone(String damCod) {
		WorkBillTallyerClass workBillTallyerClass = JpaUtils.findById(WorkBillTallyerClass.class, damCod);
		return workBillTallyerClass;

	}
	
	@Override
	public HdMessageCode saveone(@RequestBody WorkBillTallyerClass workBillTallyerClass) {
		if(HdUtils.strNotNull(workBillTallyerClass.getWorkbillNo())){
			JpaUtils.update(workBillTallyerClass);
		}else{
			String datetime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());  
			String id = datetime.substring(0,8) + workBillTallyerClass.getClassCod();
			String jpql = "select MAX(a.workbillNo) from WorkBill a where a.workbillNo like :workbillNo";
			QueryParamLs paramLs = new QueryParamLs();
			paramLs.addParam("workbillNo", id+"%");
			String workbillNo = JpaUtils.single(jpql, paramLs);
			if(HdUtils.strNotNull(workbillNo)){
				workBillTallyerClass.setWorkbillNo(workbillNo+1);
			}else{
				workBillTallyerClass.setWorkbillNo(id+"001");
			}
			JpaUtils.save(workBillTallyerClass);
		}
		return HdUtils.genMsg();
	}
	
}

