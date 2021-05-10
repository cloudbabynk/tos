package net.huadong.tech.base.service.impl;
import com.alibaba.dubbo.config.annotation.Service;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.bean.HdRunTimeException;
import net.huadong.tech.base.entity.CCarKind;
import net.huadong.tech.base.entity.CWorkRun;
import net.huadong.tech.base.service.CWorkRunService;
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
public class CWorkRunServiceImpl implements CWorkRunService {
	@Override
	public HdEzuiDatagridData find(HdQuery hdQuery) {
		String jpql="select a from CWorkRun a where 1=1 ";
		String workRun = hdQuery.getStr("workRun");
		String workRunNam = hdQuery.getStr("workRunNam");
		QueryParamLs paramLs = new QueryParamLs();
		if(HdUtils.strNotNull(workRun)){
			jpql += "and a.workRun =:workRun ";
			paramLs.addParam("workRun", workRun);
		}
		if(HdUtils.strNotNull(workRunNam)){
			jpql += "and a.workRunNam =:workRunNam ";
			paramLs.addParam("workRunNam", workRunNam);
		}
		jpql += "order by a.recTim desc";
		return JpaUtils.findByEz(jpql, paramLs , hdQuery);
	}

	@Override
	public HdMessageCode save(HdEzuiSaveDatagridData<CWorkRun> hdEzuiSaveDatagridData) {
		// TODO Auto-generated method stub
		return JpaUtils.save(hdEzuiSaveDatagridData);
	}

	@Transactional
	public void removeAll(String workRuns) {
		List<String> workRunList = HdUtils.paraseStrs(workRuns);
		for (String workRun : workRunList) {
			JpaUtils.remove(CWorkRun.class, workRun);
		}
	}
	
	@Override
	public CWorkRun findone(String workRun) {
		CWorkRun cWorkRun = JpaUtils.findById(CWorkRun.class, workRun);
		return cWorkRun;

	}
	
	@Override
	public HdMessageCode saveone(@RequestBody CWorkRun cWorkRun) {
		String workRun = cWorkRun.getWorkRun();
		CWorkRun cworkrun = JpaUtils.findById(CWorkRun.class, workRun);
		if(cworkrun != null){
			JpaUtils.update(cWorkRun);
		}else{
			JpaUtils.save(cWorkRun);
		}
		return HdUtils.genMsg();
	}
	
	@Override
	public HdMessageCode findCWorkRun(String workRun) {
		if(HdUtils.strNotNull(workRun)){
			CWorkRun cWorkRun = JpaUtils.findById(CWorkRun.class, workRun);
			if(cWorkRun != null){
				throw new HdRunTimeException("该代码已存在，请重新输入！");// 主键已存在
			}
		}
		return HdUtils.genMsg();
	}
}

