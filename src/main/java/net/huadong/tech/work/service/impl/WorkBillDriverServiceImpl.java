package net.huadong.tech.work.service.impl;
import com.alibaba.dubbo.config.annotation.Service;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.bean.HdRunTimeException;
import net.huadong.tech.base.entity.CWorkClass;
import net.huadong.tech.work.entity.WorkBillDriver;
import net.huadong.tech.work.entity.WorkBillDriverSecond;
import net.huadong.tech.work.service.WorkBillDriverService;
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
public class WorkBillDriverServiceImpl implements WorkBillDriverService {
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
		List<WorkBillDriver> workBillDriverClassList = result.getRows();
		for(WorkBillDriver workBillDriverClass : workBillDriverClassList){
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
	public HdMessageCode save(HdEzuiSaveDatagridData<WorkBillDriver> hdEzuiSaveDatagridData,String workbillNo) {
		// TODO Auto-generated method stub
		List<WorkBillDriver> WorkBillDriverList = hdEzuiSaveDatagridData.getInsertedRows();
		for(WorkBillDriver workBillDriver : WorkBillDriverList){
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
			String jpql = "select a from WorkBillDriver a where a.workbillNo=:workbillNo and a.classCod=:classCod";
			QueryParamLs paramLs = new QueryParamLs();
			paramLs.addParam("workbillNo", workbillNo);
			paramLs.addParam("classCod", classCod);
			List<WorkBillDriver> WorkBillDriverList = JpaUtils.findAll(jpql, paramLs);
			JpaUtils.removeAll(WorkBillDriverList);
			
		}
	}
	
	@Override
	public WorkBillDriver findone(String damCod) {
		WorkBillDriver cDamage = JpaUtils.findById(WorkBillDriver.class, damCod);
		return cDamage;

	}
	
	@Override
	public HdMessageCode saveone(@RequestBody WorkBillDriver workBill) {
		HdMessageCode hdMessageCode =new HdMessageCode();
		try {
			if (HdUtils.strNotNull(workBill.getWorkbillNo())) {
				JpaUtils.update(workBill);
			} else {
				String datetime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
				String id = datetime.substring(0, 8) + workBill.getClassCod();
				String jpql = "select MAX(a.workbillNo) from WorkBill a where a.workbillNo like :workbillNo";
				QueryParamLs paramLs = new QueryParamLs();
				paramLs.addParam("workbillNo", id + "%");
				String workbillNo = JpaUtils.single(jpql, paramLs);
				if (HdUtils.strNotNull(workbillNo)) {
					workBill.setWorkbillNo(workbillNo + 1);
				} else {
					workBill.setWorkbillNo(id + "001");
				}
				JpaUtils.save(workBill);
			}
			return HdUtils.genMsg();
		}catch (Exception e){
		hdMessageCode.setCode("-1");
		hdMessageCode.setMessage(e.getMessage());
		return  hdMessageCode;
	}
	}
	
}

