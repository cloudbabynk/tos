package net.huadong.tech.map.service.impl;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.tech.map.entity.CGisWbill;
import net.huadong.tech.map.service.CGisWbillService;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;
import net.huadong.tech.util.HdUtils;

/**
 * @author 
 */
@Component
public class CGisWbillServiceImpl implements CGisWbillService {
	@Override
	public HdEzuiDatagridData find(HdQuery hdQuery) {
		String jpql="select a from CGisWbill a where 1=1 ";
		QueryParamLs paramLs = new QueryParamLs();
		if(HdUtils.strNotNull(hdQuery.getStr("wbId"))){
			jpql+=" and  a.wbId=:wbId ";
			paramLs.addParam("wbId",hdQuery.getStr("wbId"));
		}
		HdEzuiDatagridData result = JpaUtils.findByEz(jpql, paramLs, hdQuery);
		return result;
	}
	

	@Override
	public HdMessageCode save(HdEzuiSaveDatagridData<CGisWbill> hdEzuiSaveDatagridData) {
		// TODO Auto-generated method stub
		
		List<CGisWbill> insertRows=hdEzuiSaveDatagridData.getInsertedRows();
		for (CGisWbill cGisWbill : insertRows) {
			cGisWbill.setWbId(HdUtils.genUuid());
			cGisWbill.setRecNam(HdUtils.getCurUser().getName());
			cGisWbill.setRecTim(HdUtils.getDateTime());
			cGisWbill.setUpdNam(HdUtils.getCurUser().getName());
			cGisWbill.setUpdTim(HdUtils.getDateTime());
		}
		List<CGisWbill> updateRows=hdEzuiSaveDatagridData.getUpdatedRows();
		for (CGisWbill cGisWbill : updateRows) {
			cGisWbill.setUpdNam(HdUtils.getCurUser().getName());
			cGisWbill.setUpdTim(HdUtils.getDateTime());
		}
	
		return JpaUtils.save(hdEzuiSaveDatagridData);
	}

	@Transactional
	public void removeAll(String ids) {
		List<String> billList = HdUtils.paraseStrs(ids);
		for (String item : billList) {
			JpaUtils.remove(CGisWbill.class, item);
		}
	}
	
	@Override
	public CGisWbill findone(String id) {
		CGisWbill cgiswbill = JpaUtils.findById(CGisWbill.class, id);
		return cgiswbill;

	}
	
	@Override
	public HdMessageCode saveone(@RequestBody CGisWbill cgiswbill) {
		String idStr = cgiswbill.getWbId();
		CGisWbill cgiswbillItem = JpaUtils.findById(CGisWbill.class, idStr);
		if(cgiswbillItem != null){
			JpaUtils.update(cgiswbillItem);
		}else{
			JpaUtils.save(cgiswbillItem);
		}
		return HdUtils.genMsg();
	}

}

