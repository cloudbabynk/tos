package net.huadong.tech.work.service.impl;
import com.alibaba.dubbo.config.annotation.Service;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.bean.HdRunTimeException;
import net.huadong.tech.base.entity.CWorkClass;
import net.huadong.tech.work.entity.WorkBillDriverClass;
import net.huadong.tech.work.entity.WorkBillDriverSecond;
import net.huadong.tech.work.service.WorkBillDriverClassService;
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
public class WorkBillDriverClassServiceImpl implements WorkBillDriverClassService {
	@Override
	public HdEzuiDatagridData find(HdQuery hdQuery) {
		String jpql="select a from WorkBillDriverClass a where 1=1 ";
		String workbillNo = hdQuery.getStr("workbillNo");
		QueryParamLs paramLs = new QueryParamLs();
		if(HdUtils.strNotNull(workbillNo)){
			jpql += "and a.workbillNo =:workbillNo ";
			paramLs.addParam("workbillNo", workbillNo);
		}else{
			jpql += "and a.workbillNo =:workbillNo ";
			paramLs.addParam("workbillNo", "1234567890#");//过滤查询
		}
		jpql += "order by a.recTim desc";
		HdEzuiDatagridData result = JpaUtils.findByEz(jpql, paramLs, hdQuery);
		List<WorkBillDriverClass> workBillDriverClassList = result.getRows();
		for(WorkBillDriverClass workBillDriverClass : workBillDriverClassList){
			if(HdUtils.strNotNull(workBillDriverClass.getClassCod())){
				CWorkClass cWorkClass = JpaUtils.findById(CWorkClass.class, workBillDriverClass.getClassCod());
				if(cWorkClass != null){
					workBillDriverClass.setClassCodNam(cWorkClass.getClassNam());
				}
			}
		}
		return result;
	}

	@Override
	public HdMessageCode save(HdEzuiSaveDatagridData<WorkBillDriverClass> hdEzuiSaveDatagridData,String workbillNo) {
		// TODO Auto-generated method stub
		List<WorkBillDriverClass> WorkBillDriverClassList = hdEzuiSaveDatagridData.getInsertedRows();
		for(WorkBillDriverClass workBillDriverClass : WorkBillDriverClassList){
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
			String jpql = "select a from WorkBillDriverClass a where a.workbillNo=:workbillNo and a.classCod=:classCod";
			String jpql1 = "select a from WorkBillDriverSecond a where a.workbillNo=:workbillNo and a.classCod=:classCod";
			QueryParamLs paramLs = new QueryParamLs();
			paramLs.addParam("workbillNo", workbillNo);
			paramLs.addParam("classCod", classCod);
			List<WorkBillDriverClass> WorkBillDriverClassList = JpaUtils.findAll(jpql, paramLs);
			List<WorkBillDriverSecond> workBillDriverSecondList = JpaUtils.findAll(jpql1, paramLs);
			JpaUtils.removeAll(workBillDriverSecondList);
			JpaUtils.removeAll(WorkBillDriverClassList);
			
		}
	}
	
	@Override
	public WorkBillDriverClass findone(String damCod) {
		WorkBillDriverClass cDamage = JpaUtils.findById(WorkBillDriverClass.class, damCod);
		return cDamage;

	}
	
	@Override
	public HdMessageCode saveone(@RequestBody WorkBillDriverClass workBill) {
		if(HdUtils.strNotNull(workBill.getWorkbillNo())){
			JpaUtils.update(workBill);
		}else{
			String datetime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());  
			String id = datetime.substring(0,8) + workBill.getClassCod();
			String jpql = "select MAX(a.workbillNo) from WorkBill a where a.workbillNo like :workbillNo";
			QueryParamLs paramLs = new QueryParamLs();
			paramLs.addParam("workbillNo", id+"%");
			String workbillNo = JpaUtils.single(jpql, paramLs);
			if(HdUtils.strNotNull(workbillNo)){
				workBill.setWorkbillNo(workbillNo+1);
			}else{
				workBill.setWorkbillNo(id+"001");
			}
			JpaUtils.save(workBill);
		}
		return HdUtils.genMsg();
	}
	
}

