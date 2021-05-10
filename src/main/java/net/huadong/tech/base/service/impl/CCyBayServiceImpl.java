package net.huadong.tech.base.service.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.entity.CCyBay;
import net.huadong.tech.base.service.CCyBayService;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;
import net.huadong.tech.util.HdUtils;

/**
 * @author
 */
@Component
public class CCyBayServiceImpl implements CCyBayService {
	@Override
	public HdEzuiDatagridData find(HdQuery hdQuery) {
		// TODO Auto-generated method stub
		String jpql = "select a from CCyBay a where 1=1 ";
		String cyAreaNo = hdQuery.getStr("cyAreaNo");
		String cyRowNo = hdQuery.getStr("cyRowNo");
		String lockId = hdQuery.getStr("lockId");
		QueryParamLs paramLs = new QueryParamLs();
		if (HdUtils.strNotNull(cyAreaNo)) {
			jpql += "and a.cyAreaNo =:cyAreaNo ";
			paramLs.addParam("cyAreaNo", cyAreaNo);
		}
		if (HdUtils.strNotNull(cyRowNo)) {
			jpql += "and a.cyRowNo =:cyRowNo ";
			paramLs.addParam("cyRowNo", cyRowNo);
		}
		if (HdUtils.strNotNull(lockId)) {
			jpql += "and a.lockId =:lockId ";
			paramLs.addParam("lockId", lockId);
		}
		jpql += "order by a.cyBayNo ";
		return JpaUtils.findByEz(jpql, paramLs, hdQuery);
	}

	@Override
	public HdMessageCode save(HdEzuiSaveDatagridData<CCyBay> hdEzuiSaveDatagridData) {
		// TODO Auto-generated method stub
		return JpaUtils.save(hdEzuiSaveDatagridData);
	}
	
	@Override
	public String findLocked(String cyAreaNo){
		String jpql = "select a from CCyBay a where a.lockId = '1' ";
		QueryParamLs paramLs = new QueryParamLs();
		if(HdUtils.strNotNull(cyAreaNo)){
			jpql += "and a.cyAreaNo =:cyAreaNo";
			paramLs.addParam("cyAreaNo", cyAreaNo);
		}
		List<CCyBay> list = JpaUtils.findAll(jpql, paramLs);
		JSONObject json = new JSONObject();
		json.put("list", list);
		return json.toString();
	}

}
