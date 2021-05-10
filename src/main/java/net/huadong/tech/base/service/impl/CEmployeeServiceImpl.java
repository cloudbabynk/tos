package net.huadong.tech.base.service.impl;
import com.alibaba.dubbo.config.annotation.Service;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.bean.HdRunTimeException;
import net.huadong.tech.base.entity.CEmpTyp;
import net.huadong.tech.base.entity.CEmployee;
import net.huadong.tech.base.entity.CWorkClass;
import net.huadong.tech.base.service.CEmployeeService;
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
public class CEmployeeServiceImpl implements CEmployeeService {
	@Override
	public HdEzuiDatagridData find(HdQuery hdQuery) {
		String jpql="select a from CEmployee a where 1=1 ";
		String empNo = hdQuery.getStr("empNo");
		String empNam = hdQuery.getStr("empNam");
		String empTypCod = hdQuery.getStr("empTypCod");
		String classNo = hdQuery.getStr("classNo");
		String sex = hdQuery.getStr("sex");
		QueryParamLs paramLs = new QueryParamLs();
		if(HdUtils.strNotNull(empNo)){
			jpql += "and a.empNo =:empNo ";
			paramLs.addParam("empNo", empNo);
		}
		if(HdUtils.strNotNull(empNam)){
			jpql += "and a.empNam like :empNam ";
			paramLs.addParam("empNam", "%" + empNam + "%");
		}
		if(HdUtils.strNotNull(empTypCod)){
			jpql += "and a.empTypCod =:empTypCod ";
			paramLs.addParam("empTypCod", empTypCod);
		}
		if(HdUtils.strNotNull(classNo)){
			jpql += "and a.classNo =:classNo ";
			paramLs.addParam("classNo", classNo);
		}
		if(HdUtils.strNotNull(sex)){
			jpql += "and a.sex =:sex ";
			paramLs.addParam("sex", sex);
		}
		jpql += "order by a.empNo asc";
		HdEzuiDatagridData result = JpaUtils.findByEz(jpql, paramLs , hdQuery);
		List<CEmployee> cEmployeeList = result.getRows();
		for(CEmployee cEmployee : cEmployeeList){
			cEmployee.setEmpTypCodNam(JpaUtils.findById(CEmpTyp.class, cEmployee.getEmpTypCod()).getEmpTypNam());
			if(HdUtils.strNotNull(cEmployee.getClassNo())){
				cEmployee.setClassNoNam(JpaUtils.findById(CWorkClass.class, cEmployee.getClassNo()).getClassNam());
			}
		}
		return result;
	}
	
	public HdEzuiDatagridData findSj(HdQuery hdQuery) {
		String jpql="select a from CEmployee a where a.onDutyId = '1' and a.empTypCod =:empTypCod ";
		String empNo = hdQuery.getStr("empNo");
		QueryParamLs paramLs = new QueryParamLs();
		paramLs.addParam("empTypCod", CEmployee.TYP_SJ);
		if(HdUtils.strNotNull(empNo)){
			jpql += "and a.empNo =:empNo ";
			paramLs.addParam("empNo", empNo);
		}
		jpql += "order by a.recTim desc";
		HdEzuiDatagridData result = JpaUtils.findByEz(jpql, paramLs , hdQuery);
		return result;
	}

	@Override
	public HdMessageCode save(HdEzuiSaveDatagridData<CEmployee> hdEzuiSaveDatagridData) {
		// TODO Auto-generated method stub
		return JpaUtils.save(hdEzuiSaveDatagridData);
	}

	@Transactional
	public void removeAll(String empNos) {
		List<String> damCodList = HdUtils.paraseStrs(empNos);
		for (String empNo : damCodList) {
			JpaUtils.remove(CEmployee.class, empNo);
		}
	}
	
	@Override
	public CEmployee findone(String empNo) {
		CEmployee cEmployee = JpaUtils.findById(CEmployee.class, empNo);
		return cEmployee;

	}
	
	@Override
	public CEmployee findCemployee(String sysUserNam) {
		CEmployee cEmployee = new CEmployee();
		if(HdUtils.strNotNull(sysUserNam)){
			String jpql = "select a from CEmployee a, AuthUserExtra b where a.sysUserNam = b.userId and b.account =:account";
			QueryParamLs paramLs = new QueryParamLs();
			paramLs.addParam("account", sysUserNam);
			List<CEmployee> cEmployeeList = JpaUtils.findAll(jpql, paramLs);
			if(cEmployeeList.size()>0){
				cEmployee = cEmployeeList.get(0);
			}
		}
		return cEmployee;

	}
	
	@Override
	public HdMessageCode saveone(@RequestBody CEmployee cEmployee) {
		String empNo = cEmployee.getEmpNo();
		CEmployee cemployee = JpaUtils.findById(CEmployee.class, empNo);
		if(cemployee != null){
			JpaUtils.update(cEmployee);
		}else{
			JpaUtils.save(cEmployee);
		}
		return HdUtils.genMsg();
	}
	
	@Override
	public HdMessageCode findCEmployee(String empNo) {
		if(HdUtils.strNotNull(empNo)){
			CEmployee cEmployee = JpaUtils.findById(CEmployee.class, empNo);
			if(cEmployee != null){
				throw new HdRunTimeException("该代码已存在，请重新输入！");// 主键已存在
			}
		}
		return HdUtils.genMsg();
	}
}

