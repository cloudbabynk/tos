package net.huadong.tech.base.service.impl;
import com.alibaba.dubbo.config.annotation.Service;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.entity.CConvertStand;
import net.huadong.tech.base.service.CConvertStandService;
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
public class CConvertStandServiceImpl implements CConvertStandService {
	@Override
	public HdEzuiDatagridData find(HdQuery hdQuery) {
		String jpql="select a from CConvertStand a where 1=1 ";
		String standCod = hdQuery.getStr("standCod");
		String standValue = hdQuery.getStr("standValue");
		QueryParamLs paramLs = new QueryParamLs();
		if(HdUtils.strNotNull(standCod)){
			jpql += "and a.standCod =:standCod ";
			paramLs.addParam("standCod", standCod);
		}
		if(HdUtils.strNotNull(standValue)){
			jpql += "and a.standValue =:standValue ";
			paramLs.addParam("standValue", standValue);
		}
		jpql += "order by a.recTim desc";
		return JpaUtils.findByEz(jpql, paramLs , hdQuery);
	}

	@Override
	public HdMessageCode save(HdEzuiSaveDatagridData<CConvertStand> hdEzuiSaveDatagridData) {
		// TODO Auto-generated method stub
		return JpaUtils.save(hdEzuiSaveDatagridData);
	}

	@Transactional
	public void removeAll(String standIds) {
		List<String> standIdList = HdUtils.paraseStrs(standIds);
		for (String standId : standIdList) {
			JpaUtils.remove(CConvertStand.class, standId);
		}
	}
	
	@Override
	public CConvertStand findone(String standId) {
		CConvertStand cConvertStand = JpaUtils.findById(CConvertStand.class, standId);
		return cConvertStand;

	}
	
	@Override
	public HdMessageCode saveone(@RequestBody CConvertStand cConvertStand) {
HdMessageCode hdMessageCode =new HdMessageCode();
		try {
			String standId = cConvertStand.getStandId();
			CConvertStand cconvertstand = JpaUtils.findById(CConvertStand.class, standId);
			if (cconvertstand != null) {
				JpaUtils.update(cConvertStand);
			} else {
				cConvertStand.setStandId(HdUtils.genUuid());
				JpaUtils.save(cConvertStand);
			}
			return HdUtils.genMsg();
		}catch (Exception e){
			hdMessageCode.setCode("-1");
			hdMessageCode.setMessage(e.getMessage());
			return  hdMessageCode;
		}
	}
}

