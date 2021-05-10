package net.huadong.tech.base.service.impl;

import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Component;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.entity.CBrandDetail;
import net.huadong.tech.base.service.CBrandDetailService;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;
import net.huadong.tech.util.HdUtils;

@Component
public class CBrandDetailServiceImpl implements CBrandDetailService {

	@Override
	public HdEzuiDatagridData find(HdQuery hdQuery) {
		// TODO Auto-generated method stub
		String jpql = "SELECT c FROM CBrandDetail c where 1=1 ";
		String brandCod = hdQuery.getStr("brandCod");
		QueryParamLs params = new QueryParamLs();
		if(HdUtils.strNotNull(brandCod)) {
			jpql += "   and c.brandCod=:brandCod ";
			params.addParam("brandCod", brandCod);
			jpql += "order by c.recTim desc";
			return JpaUtils.findByEz(jpql, params , hdQuery);
		} else {
			return null;
		}
	}

	@Override
	public HdMessageCode ezuiSaveSysCode(HdEzuiSaveDatagridData<CBrandDetail> hdEzuiSaveDatagridData) {
		Iterator arg2 = hdEzuiSaveDatagridData.getInsertedRows().iterator();
		while (arg2.hasNext()) {
			CBrandDetail cBrandDetail = (CBrandDetail) arg2.next();
			cBrandDetail.setId(HdUtils.genUuid());
			cBrandDetail.setRecNam(HdUtils.getCurUser().getAccount());
			cBrandDetail.setRecTim(HdUtils.getDateTime());
			
		}
		return JpaUtils.save(hdEzuiSaveDatagridData);
	}

	@Override
	public HdMessageCode saveone(CBrandDetail cBrandDetail) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeAll(String ids) {
		List<String> idList = HdUtils.paraseStrs(ids);
		for (String id : idList) {
			JpaUtils.remove(CBrandDetail.class, id);
		}
	}

}
