package net.huadong.tech.base.service.impl;
import com.alibaba.dubbo.config.annotation.Service;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.bean.HdRunTimeException;
import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.entity.CArea;
import net.huadong.tech.base.service.CAreaService;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiQueryParams;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;
import net.huadong.tech.util.HdUtils;

/**
 * @author 
 */
@Component
public class CAreaServiceImpl implements CAreaService {
	@Override
	public HdEzuiDatagridData find(HdQuery hdQuery) {
		// TODO Auto-generated method stub
		String jpql="select a from CArea a where 1=1 ";
		String areaCod = hdQuery.getStr("areaCod");
		String areaNam = hdQuery.getStr("areaNam");
		QueryParamLs paramLs = new QueryParamLs();
		if(HdUtils.strNotNull(areaCod)){
			jpql += "and a.areaCod =:areaCod ";
			paramLs.addParam("areaCod", areaCod);
		}
		if(HdUtils.strNotNull(areaNam)){
			jpql += "and a.areaNam =:areaNam ";
			paramLs.addParam("areaNam", areaNam);
		}
		jpql += "order by a.recTim desc";
		return JpaUtils.findByEz(jpql, paramLs , hdQuery);
	}

	@Override
	public HdMessageCode save(HdEzuiSaveDatagridData<CArea> hdEzuiSaveDatagridData) {
		// TODO Auto-generated method stub
	return JpaUtils.save(hdEzuiSaveDatagridData);
	}
	@Transactional
	public void removeAll(String areaCods) {
		// TODO Auto-generated method stub
		List<String> areaCodList = HdUtils.paraseStrs(areaCods);
		for (String areaCod : areaCodList) {
			JpaUtils.remove(CArea.class, areaCod);
		}	
	}

	@Override
	public CArea findone(String areaCod) {
		// TODO Auto-generated method stub
		CArea CArea = JpaUtils.findById(CArea.class, areaCod);
		return CArea;

	}

	@Override
	public HdMessageCode saveone(@RequestBody CArea cArea) {
		// TODO Auto-generated method stub
		String areaCod = cArea.getAreaCod();
		CArea carea = JpaUtils.findById(CArea.class, areaCod);
		if(carea != null){
			JpaUtils.update(cArea);
		}else{
			JpaUtils.save(cArea);
		}
		return HdUtils.genMsg();
	}
	@Override
	public HdMessageCode findCArea(String areaCod) {
		if(HdUtils.strNotNull(areaCod)){
			CArea cArea = JpaUtils.findById(CArea.class, areaCod);
			if(cArea != null){
				throw new HdRunTimeException("该代码已存在，请重新输入！");// 主键已存在
			}
		}
		return HdUtils.genMsg();
	}
}

