package net.huadong.tech.base.service.impl;
import com.alibaba.dubbo.config.annotation.Service;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.bean.HdRunTimeException;
import net.huadong.tech.base.entity.CMachine;
import net.huadong.tech.base.service.CMachineService;
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
public class CMachineServiceImpl implements CMachineService {
	@Override
	public HdEzuiDatagridData find(HdQuery hdQuery) {
		String jpql="select a from CMachine a where 1=1 ";
		String machNo = hdQuery.getStr("machNo");
		String machNam = hdQuery.getStr("machNam");
		String machTyp = hdQuery.getStr("machTyp");
		QueryParamLs paramLs = new QueryParamLs();
		if(HdUtils.strNotNull(machNo)){
			jpql += "and a.machNo =:machNo ";
			paramLs.addParam("machNo", machNo);
		}
		if(HdUtils.strNotNull(machNam)){
			jpql += "and a.machNam =:machNam ";
			paramLs.addParam("machNam", machNam);
		}
		if(HdUtils.strNotNull(machTyp)){
			jpql += "and a.machTyp =:machTyp ";
			paramLs.addParam("machTyp", machTyp);
		}
		jpql += "order by a.machNo asc";
		return JpaUtils.findByEz(jpql, paramLs , hdQuery);
	}

	@Override
	public HdMessageCode save(HdEzuiSaveDatagridData<CMachine> hdEzuiSaveDatagridData) {
		// TODO Auto-generated method stub
		return JpaUtils.save(hdEzuiSaveDatagridData);
	}

	@Transactional
	public void removeAll(String machNos) {
		List<String> machNoList = HdUtils.paraseStrs(machNos);
		for (String machNo : machNoList) {
			JpaUtils.remove(CMachine.class, machNo);
		}
	}
	
	@Override
	public CMachine findone(String machNo) {
		CMachine cMachine = JpaUtils.findById(CMachine.class, machNo);
		return cMachine;

	}
	
	@Override
	public HdMessageCode saveone(@RequestBody CMachine cMachine) {
		String machNo = cMachine.getMachNo();
		CMachine cmachine = JpaUtils.findById(CMachine.class, machNo);
		if(cmachine != null){
			JpaUtils.update(cMachine);
		}else{
			JpaUtils.save(cMachine);
		}
		return HdUtils.genMsg();
	}

	@Override
	public HdMessageCode findCMachine(String machNo) {
		// TODO Auto-generated method stub
		if(HdUtils.strNotNull(machNo)){
			CMachine cMachine = JpaUtils.findById(CMachine.class, machNo);
			if(cMachine != null){
				throw new HdRunTimeException("该代码已存在，请重新输入！");// 主键已存在
			}
		}
		return HdUtils.genMsg();
	}
}

