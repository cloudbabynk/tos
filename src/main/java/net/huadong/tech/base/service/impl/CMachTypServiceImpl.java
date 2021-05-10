package net.huadong.tech.base.service.impl;
import com.alibaba.dubbo.config.annotation.Service;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.bean.HdRunTimeException;
import net.huadong.tech.base.entity.CMachTyp;
import net.huadong.tech.base.service.CMachTypService;
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
public class CMachTypServiceImpl implements CMachTypService {
	@Override
	public HdEzuiDatagridData find(HdQuery hdQuery) {
		String jpql="select a from CMachTyp a where 1=1 ";
		String machTypCod = hdQuery.getStr("machTypCod");
		String machTyp = hdQuery.getStr("machTyp");
		QueryParamLs paramLs = new QueryParamLs();
		if(HdUtils.strNotNull(machTypCod)){
			jpql += "and a.machTypCod =:machTypCod ";
			paramLs.addParam("machTypCod", machTypCod);
		}
		if(HdUtils.strNotNull(machTyp)){
			jpql += "and a.machTyp =:machTyp ";
			paramLs.addParam("machTyp", machTyp);
		}
		jpql += " order by a.recTim desc";
		return JpaUtils.findByEz(jpql, paramLs , hdQuery);
	}

	@Override
	public HdMessageCode save(HdEzuiSaveDatagridData<CMachTyp> hdEzuiSaveDatagridData) {
		// TODO Auto-generated method stub
		return JpaUtils.save(hdEzuiSaveDatagridData);
	}

	@Transactional
	public void removeAll(String machTypCods) {
		List<String> machTypCodList = HdUtils.paraseStrs(machTypCods);
		for (String machTypCod : machTypCodList) {
			JpaUtils.remove(CMachTyp.class, machTypCod);
		}
	}
	
	@Override
	public CMachTyp findone(String machTypCod) {
		CMachTyp cMachTyp = JpaUtils.findById(CMachTyp.class, machTypCod);
		return cMachTyp;

	}
	
	@Override
	public HdMessageCode saveone(@RequestBody CMachTyp cMachTyp) {
		String machTypCod = cMachTyp.getMachTypCod();
		CMachTyp cmachtyp = JpaUtils.findById(CMachTyp.class, machTypCod);
		if(cmachtyp != null){
			JpaUtils.update(cMachTyp);
		}else{
			JpaUtils.save(cMachTyp);
		}
		return HdUtils.genMsg();
	}

	@Override
	public HdMessageCode findCMachTyp(String machTypCod) {
		// TODO Auto-generated method stub
		if(HdUtils.strNotNull(machTypCod)){
			CMachTyp cMachTyp = JpaUtils.findById(CMachTyp.class, machTypCod);
			if(cMachTyp != null){
				throw new HdRunTimeException("该代码已存在，请重新输入！");// 主键已存在
			}
		}
		return HdUtils.genMsg();
	}
}

