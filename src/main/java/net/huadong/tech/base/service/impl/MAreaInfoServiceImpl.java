package net.huadong.tech.base.service.impl;
import com.alibaba.dubbo.config.annotation.Service;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.bean.HdRunTimeException;
import net.huadong.tech.base.entity.MAreaInfo;
import net.huadong.tech.base.service.MAreaInfoService;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.idev.hdmessagecode.HdMessageCode;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;
import net.huadong.tech.util.HdUtils;
import net.huadong.tech.util.Util;

/**
 * @author 
 */
@Component
public class MAreaInfoServiceImpl implements MAreaInfoService {
	@Override
	public HdEzuiDatagridData find(HdQuery hdQuery) {
		String jpql="select a from MAreaInfo a where 1=1 ";
		String workDte = hdQuery.getStr("workDte");
		QueryParamLs paramLs = new QueryParamLs();

		if (HdUtils.strNotNull(workDte)){
			jpql += "and a.workDte =:workDte";
			paramLs.addParam("workDte", HdUtils.strToDate(workDte));

		}
		jpql += " order by a.brandNam, a.areaNum, a.areaInfo asc ";
		return JpaUtils.findByEz(jpql, paramLs , hdQuery);
	}

	@Override
	public HdMessageCode save(HdEzuiSaveDatagridData<MAreaInfo> hdEzuiSaveDatagridData) {
		// TODO Auto-generated method stub
		return JpaUtils.save(hdEzuiSaveDatagridData);
	}

	@Transactional
	public void removeAll(String ids) {
		List<String> idList = HdUtils.paraseStrs(ids);
		for (String id : idList) {
			JpaUtils.remove(MAreaInfo.class, id);
		}
	}
	
	@Override
	public MAreaInfo findone(String id) {
		MAreaInfo mAreaInfo = JpaUtils.findById(MAreaInfo.class, id);
		return mAreaInfo;
	}
	
	@Override
	public HdMessageCode saveone(@RequestBody MAreaInfo mAreaInfo) {
		JpaUtils.save(mAreaInfo);
		return HdUtils.genMsg();
	}
	
}

