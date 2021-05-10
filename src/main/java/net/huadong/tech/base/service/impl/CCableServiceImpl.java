package net.huadong.tech.base.service.impl;
import com.alibaba.dubbo.config.annotation.Service;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.bean.HdRunTimeException;
import net.huadong.tech.base.entity.CBerth;
import net.huadong.tech.base.entity.CCable;
import net.huadong.tech.base.entity.CCarKind;
import net.huadong.tech.base.service.CCableService;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.tech.ship.entity.Ship;
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
public class CCableServiceImpl implements CCableService {
	@Override
	public HdEzuiDatagridData find(HdQuery hdQuery) {
		String jpql="select a from CCable a where 1=1 ";
		String cableCod = hdQuery.getStr("cableCod");
		QueryParamLs paramLs = new QueryParamLs();
		if(HdUtils.strNotNull(cableCod)){
			jpql += "and a.cableCod like :cableCod ";
			paramLs.addParam("cableCod", "%"+cableCod+"%");
		}
		HdEzuiDatagridData result = JpaUtils.findByEz(jpql, paramLs, hdQuery);
		List<CCable> cCableList = result.getRows();
		for(CCable cCable : cCableList){
			if(HdUtils.strNotNull(cCable.getBerthCod())){
				CBerth cBerth = JpaUtils.findById(CBerth.class, cCable.getBerthCod());
				cCable.setBerthCodNam(cBerth.getBerthNam());
			}
		}
		return result;
	}

	@Override
	public HdMessageCode save(HdEzuiSaveDatagridData<CCable> hdEzuiSaveDatagridData) {
		// TODO Auto-generated method stub
		return JpaUtils.save(hdEzuiSaveDatagridData);
	}

	@Transactional
	public void removeAll(String cableCods) {
		List<String> cableCodList = HdUtils.paraseStrs(cableCods);
		for (String cableCod : cableCodList) {
			JpaUtils.remove(CCable.class, cableCod);
		}
	}
	
	@Override
	public CCable findone(String cableCod) {
		CCable cCable = JpaUtils.findById(CCable.class, cableCod);
		return cCable;

	}
	
	@Override
	public HdMessageCode saveone(@RequestBody CCable cCable) {
		if(HdUtils.strNotNull(cCable.getCableCod())){
			JpaUtils.update(cCable);
		}else{
			JpaUtils.save(cCable);
		}
		return HdUtils.genMsg();
	}
	
	@Override
	public HdMessageCode findCCable(String cableCod) {
		if(HdUtils.strNotNull(cableCod)){
			CCable cCable = JpaUtils.findById(CCable.class, cableCod);
			if(cCable != null){
				throw new HdRunTimeException("该代码已存在，请重新输入！");// 主键已存在
			}
		}
		return HdUtils.genMsg();
	}
}

