package net.huadong.tech.base.service.impl;
import com.alibaba.dubbo.config.annotation.Service;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.bean.HdRunTimeException;
import net.huadong.tech.base.entity.CEmpTyp;
import net.huadong.tech.base.service.CEmpTypService;
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
public class CEmpTypServiceImpl implements CEmpTypService {
	@Override
	public HdEzuiDatagridData find(HdQuery hdQuery) {
		String jpql="select a from CEmpTyp a where 1=1 ";
		String empTypCod = hdQuery.getStr("empTypCod");
		String empTypNam = hdQuery.getStr("empTypNam");
		QueryParamLs paramLs = new QueryParamLs();
		if(HdUtils.strNotNull(empTypCod)){
			jpql += "and a.empTypCod =:empTypCod ";
			paramLs.addParam("empTypCod", empTypCod);
		}
		if(HdUtils.strNotNull(empTypNam)){
			jpql += "and a.empTypNam =:empTypNam ";
			paramLs.addParam("empTypNam", empTypNam);
		}
		jpql += "order by a.recTim desc";
		return JpaUtils.findByEz(jpql, paramLs , hdQuery);
	}

	@Override
	public HdMessageCode save(HdEzuiSaveDatagridData<CEmpTyp> hdEzuiSaveDatagridData) {
		// TODO Auto-generated method stub
		return JpaUtils.save(hdEzuiSaveDatagridData);
	}

	@Transactional
	public void removeAll(String empTypCods) {
		List<String> empTypCodList = HdUtils.paraseStrs(empTypCods);
		for (String empTypCod : empTypCodList) {
			JpaUtils.remove(CEmpTyp.class, empTypCod);
		}
	}
	
	@Override
	public CEmpTyp findone(String empTypCod) {
		CEmpTyp cEmpTyp = JpaUtils.findById(CEmpTyp.class, empTypCod);
		return cEmpTyp;

	}
	
	@Override
	public HdMessageCode saveone(@RequestBody CEmpTyp cEmpTyp) {
		String empTypCod = cEmpTyp.getEmpTypCod();
		CEmpTyp cempTyp = JpaUtils.findById(CEmpTyp.class, empTypCod);
		if(cempTyp != null){
			JpaUtils.update(cEmpTyp);
		}else{
			JpaUtils.save(cEmpTyp);
		}
		return HdUtils.genMsg();
	}
	
	@Override
	public HdMessageCode findCEmpTyp(String empTypCod) {
		if(HdUtils.strNotNull(empTypCod)){
			CEmpTyp cEmpTyp = JpaUtils.findById(CEmpTyp.class, empTypCod);
			if(cEmpTyp != null){
				throw new HdRunTimeException("该代码已存在，请重新输入！");// 主键已存在
			}
		}
		return HdUtils.genMsg();
	}
}

