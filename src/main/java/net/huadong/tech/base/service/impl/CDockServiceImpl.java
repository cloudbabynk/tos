package net.huadong.tech.base.service.impl;
import com.alibaba.dubbo.config.annotation.Service;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.bean.HdRunTimeException;
import net.huadong.tech.base.entity.CCarKind;
import net.huadong.tech.base.entity.CDock;
import net.huadong.tech.base.service.CDockService;
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
public class CDockServiceImpl implements CDockService {
	@Override
	public HdEzuiDatagridData find(HdQuery hdQuery) {
		String jpql="select a from CDock a where 1=1 ";
		String dockCod = hdQuery.getStr("dockCod");
		String dockNam = hdQuery.getStr("dockNam");
		QueryParamLs paramLs = new QueryParamLs();
		if(HdUtils.strNotNull(dockCod)){
			jpql += "and a.dockCod =:dockCod ";
			paramLs.addParam("dockCod", dockCod);
		}
		if(HdUtils.strNotNull(dockNam)){
			jpql += "and a.dockNam =:dockNam ";
			paramLs.addParam("dockNam", dockNam);
		}
		jpql += "order by a.recTim desc";
		return JpaUtils.findByEz(jpql, paramLs , hdQuery);
	}

	@Override
	public HdMessageCode save(HdEzuiSaveDatagridData<CDock> hdEzuiSaveDatagridData) {
		// TODO Auto-generated method stub
		return JpaUtils.save(hdEzuiSaveDatagridData);
	}

	@Transactional
	public void removeAll(String dockCods) {
		List<String> dockCodList = HdUtils.paraseStrs(dockCods);
		for (String dockCod : dockCodList) {
			JpaUtils.remove(CDock.class, dockCod);
		}
	}
	
	@Override
	public CDock findone(String dockCod) {
		CDock cDock = JpaUtils.findById(CDock.class, dockCod);
		return cDock;

	}
	
	@Override
	public HdMessageCode saveone(@RequestBody CDock cDock) {
		String dockCod = cDock.getDockCod();
		CDock cdock = JpaUtils.findById(CDock.class, dockCod);
		if(cdock != null){
			JpaUtils.update(cDock);
		}else{
			JpaUtils.save(cDock);
		}
		return HdUtils.genMsg();
	}
	
	@Override
	public HdMessageCode findCDock(String dockCod) {
		if(HdUtils.strNotNull(dockCod)){
			CDock cDock = JpaUtils.findById(CDock.class, dockCod);
			if(cDock != null){
				throw new HdRunTimeException("该代码已存在，请重新输入！");// 主键已存在
			}
		}
		return HdUtils.genMsg();
	}
}

