package net.huadong.tech.base.service.impl;
import com.alibaba.dubbo.config.annotation.Service;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.bean.HdRunTimeException;
import net.huadong.tech.base.entity.CDamgArea;
import net.huadong.tech.base.service.CDamgAreaService;
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
public class CDamgAreaServiceImpl implements CDamgAreaService {
	@Override
	public HdEzuiDatagridData find(HdQuery hdQuery) {
		String jpql="select a from CDamgArea a where 1=1 ";
		String damgAreaCod = hdQuery.getStr("damgAreaCod");
		String damgArea = hdQuery.getStr("damgArea");
		QueryParamLs paramLs = new QueryParamLs();
		if(HdUtils.strNotNull(damgAreaCod)){
			jpql += "and a.damgAreaCod =:damgAreaCod ";
			paramLs.addParam("damgAreaCod", damgAreaCod);
		}
		if(HdUtils.strNotNull(damgArea)){
			jpql += "and a.damgArea =:damgArea ";
			paramLs.addParam("damgArea", damgArea);
		}
		jpql += "order by a.recTim desc";
		return JpaUtils.findByEz(jpql, paramLs , hdQuery);
	}

	@Override
	public HdMessageCode save(HdEzuiSaveDatagridData<CDamgArea> hdEzuiSaveDatagridData) {
		// TODO Auto-generated method stub
		return JpaUtils.save(hdEzuiSaveDatagridData);
	}

	@Transactional
	public void removeAll(String damgAreaCods) {
		List<String> damgAreaCodList = HdUtils.paraseStrs(damgAreaCods);
		for (String damgAreaCod : damgAreaCodList) {
			JpaUtils.remove(CDamgArea.class, damgAreaCod);
		}
	}
	
	@Override
	public CDamgArea findone(String damgAreaCod) {
		CDamgArea cDamgArea = JpaUtils.findById(CDamgArea.class, damgAreaCod);
		return cDamgArea;

	}
	
	@Override
	public HdMessageCode saveone(@RequestBody CDamgArea cDamgArea) {
		String damgAreaCod = cDamgArea.getDamgAreaCod();
		CDamgArea cdamgArea = JpaUtils.findById(CDamgArea.class, damgAreaCod);
		if(cdamgArea != null){
			JpaUtils.update(cDamgArea);
		}else{
			JpaUtils.save(cDamgArea);
		}
		return HdUtils.genMsg();
	}
	


@Override
	public HdMessageCode findCDamgArea(String damgAreaCod) {
		if(HdUtils.strNotNull(damgAreaCod)){
			CDamgArea cDamgArea = JpaUtils.findById(CDamgArea.class, damgAreaCod);
			if(cDamgArea != null){
				throw new HdRunTimeException("该代码已存在，请重新输入！");// 主键已存在
			}
		}
		return HdUtils.genMsg();
	}
}

