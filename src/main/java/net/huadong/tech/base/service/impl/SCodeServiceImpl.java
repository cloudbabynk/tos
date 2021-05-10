package net.huadong.tech.base.service.impl;
import com.alibaba.dubbo.config.annotation.Service;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.entity.SCode;
import net.huadong.tech.base.service.SCodeService;
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

/**
 * @author 
 */
@Component
public class SCodeServiceImpl implements SCodeService {
	@Override
	public HdEzuiDatagridData find(HdQuery hdQuery) {
		String jpql="select a from SCode a where 1=1 ";
		String code = hdQuery.getStr("code");
		String fldChi = hdQuery.getStr("fldChi");
		String fldEng = hdQuery.getStr("fldEng");
		QueryParamLs paramLs = new QueryParamLs();
		if(HdUtils.strNotNull(code)){
			jpql += "and a.code =:code ";
			paramLs.addParam("code", code);
		}
		if(HdUtils.strNotNull(fldChi)){
			jpql += "and a.fldChi =:fldChi ";
			paramLs.addParam("fldChi", fldChi);
		}
		if(HdUtils.strNotNull(fldEng)){
			jpql += "and a.fldEng =:fldEng ";
			paramLs.addParam("fldEng", fldEng);
		}
		return JpaUtils.findByEz(jpql, paramLs , hdQuery);
	}

	@Override
	public HdMessageCode save(HdEzuiSaveDatagridData<SCode> hdEzuiSaveDatagridData) {
		// TODO Auto-generated method stub
		return JpaUtils.save(hdEzuiSaveDatagridData);
	}

	@Transactional
	public void removeAll(String scodeIds) {
		List<String> scodeIdList = HdUtils.paraseStrs(scodeIds);
		for (String scodeId : scodeIdList) {
			JpaUtils.remove(SCode.class, scodeId);
		}
	}
	
	@Override
	public SCode findone(String scodeId) {
		SCode sCode = JpaUtils.findById(SCode.class, scodeId);
		return sCode;

	}
	
	@Override
	public HdMessageCode saveone(@RequestBody SCode sCode) {
		if(HdUtils.strNotNull(sCode.getScodeId())){
			JpaUtils.update(sCode);
		}else{
			sCode.setScodeId(HdUtils.genUuid());
			JpaUtils.save(sCode);
		}
		return HdUtils.genMsg();
	}
}

