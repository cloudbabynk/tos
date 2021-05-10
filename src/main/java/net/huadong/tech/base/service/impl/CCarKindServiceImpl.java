package net.huadong.tech.base.service.impl;
import com.alibaba.dubbo.config.annotation.Service;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.bean.HdRunTimeException;
import net.huadong.tech.base.entity.CBrand;
import net.huadong.tech.base.entity.CCarKind;
import net.huadong.tech.base.service.CCarKindService;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.idev.hdmessagecode.HdMessageCode;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
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
public class CCarKindServiceImpl implements CCarKindService {
	@Override
	public HdEzuiDatagridData find(HdQuery hdQuery) {
		String jpql="select a from CCarKind a where 1=1 ";
		String carKind = hdQuery.getStr("carKind");
		String carKindNam = hdQuery.getStr("carKindNam");
		String checkFlag = hdQuery.getStr("checkFlag");
		QueryParamLs paramLs = new QueryParamLs();
		if(HdUtils.strNotNull(carKind)){
			jpql += "and a.carKind =:carKind ";
			paramLs.addParam("carKind", carKind);
		}
		if(HdUtils.strNotNull(carKindNam)){
			jpql += "and a.carKindNam =:carKindNam ";
			paramLs.addParam("carKindNam", carKindNam);
		}
		if(HdUtils.strNotNull(checkFlag)){
			jpql += "and a.checkFlag =:checkFlag ";
			paramLs.addParam("checkFlag", checkFlag);
		}
		jpql += "order by a.recTim desc";
		return JpaUtils.findByEz(jpql, paramLs , hdQuery);
	}

	@Override
	public HdMessageCode save(HdEzuiSaveDatagridData<CCarKind> hdEzuiSaveDatagridData) {
		// TODO Auto-generated method stub
		return JpaUtils.save(hdEzuiSaveDatagridData);
	}

	@Transactional
	public void removeAll(String carKinds) {
		List<String> carKindList = HdUtils.paraseStrs(carKinds);
		for (String carKind : carKindList) {
			JpaUtils.remove(CCarKind.class, carKind);
		}
	}
	
	@Transactional
	public void checkAll(String carKinds) {
		List<String> carKindList = HdUtils.paraseStrs(carKinds);
		for (String carKind : carKindList) {
			CCarKind cCarKind = JpaUtils.findById(CCarKind.class, carKind);
			if(CBrand.N.equals(cCarKind.getCheckFlag())){
				cCarKind.setCheckFlag(CBrand.Y);
			}
			JpaUtils.update(cCarKind);
		}
	}
	
	@Override
	public CCarKind findone(String carKind) {
		CCarKind cCarKind = JpaUtils.findById(CCarKind.class, carKind);
		return cCarKind;

	}
	
	@Override
	public HdMessageCode saveone(@RequestBody CCarKind cCarKind) {
		if(HdUtils.strIsNull(cCarKind.getCarKind())){
			String datetime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()); 
			cCarKind.setCarKind(datetime.substring(4));
			JpaUtils.save(cCarKind);
		}else{
			JpaUtils.update(cCarKind);
		}
		return HdUtils.genMsg();
	}
	
	@Override
	public HdMessageCode findCCarKind(String carKind) {
		if(HdUtils.strNotNull(carKind)){
			CCarKind cCarKind = JpaUtils.findById(CCarKind.class, carKind);
			if(cCarKind != null){
				throw new HdRunTimeException("该代码已存在，请重新输入！");// 主键已存在
			}
		}
		return HdUtils.genMsg();
	}
}

