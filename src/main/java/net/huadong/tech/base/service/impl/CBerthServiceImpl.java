package net.huadong.tech.base.service.impl;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.bean.HdRunTimeException;
import net.huadong.tech.base.entity.CBerth;
import net.huadong.tech.base.entity.CCable;
import net.huadong.tech.base.entity.CDock;
import net.huadong.tech.base.service.CBerthService;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.idev.hdmessagecode.HdMessageCode;

import java.util.List;

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
public class CBerthServiceImpl implements CBerthService {
	@Override
	public HdEzuiDatagridData find(HdQuery hdQuery) {
		String jpql="select a from CBerth a where 1=1 ";
		String berthCod = hdQuery.getStr("berthCod");
		String berthNam = hdQuery.getStr("berthNam");
		QueryParamLs paramLs = new QueryParamLs();
		if(HdUtils.strNotNull(berthCod)){
			jpql += "and a.berthCod like :berthCod ";
			paramLs.addParam("berthCod", "%"+berthCod+"%");
		}
		if(HdUtils.strNotNull(berthNam)){
			jpql += "and a.berthNam like :berthNam ";
			paramLs.addParam("berthNam", "%"+berthNam+"%");
		}
		HdEzuiDatagridData result = JpaUtils.findByEz(jpql, paramLs, hdQuery);
		List<CBerth> cBerthList = result.getRows();
		for(CBerth cBerth : cBerthList){
			if(HdUtils.strNotNull(cBerth.getDockCod())){
				CDock cDock = JpaUtils.findById(CDock.class, cBerth.getDockCod());
				if(cDock != null){
					cBerth.setDockCodNam(cDock.getDockNam());
				}
			}
		}
		return result;
	}
	
	//公司泊位下拉
	@Override
	public HdEzuiDatagridData findGs(HdQuery hdQuery) {
		String jpql="select a from CBerth a where a.berthTyp = '01' ";
		String berthCod = hdQuery.getStr("berthCod");
		String berthNam = hdQuery.getStr("berthNam");
		QueryParamLs paramLs = new QueryParamLs();
		if(HdUtils.strNotNull(berthCod)){
			jpql += "and a.berthCod like :berthCod ";
			paramLs.addParam("berthCod", "%"+berthCod+"%");
		}
		if(HdUtils.strNotNull(berthNam)){
			jpql += "and a.berthNam like :berthNam ";
			paramLs.addParam("berthNam", "%"+berthNam+"%");
		}
		HdEzuiDatagridData result = JpaUtils.findByEz(jpql, paramLs, hdQuery);
		List<CBerth> cBerthList = result.getRows();
		for(CBerth cBerth : cBerthList){
			if(HdUtils.strNotNull(cBerth.getDockCod())){
				CDock cDock = JpaUtils.findById(CDock.class, cBerth.getDockCod());
				if(cDock != null){
					cBerth.setDockCodNam(cDock.getDockNam());
				}
			}
		}
		return result;
	}
	
	//公司泊位下拉
		@Override
		public HdEzuiDatagridData findShipstat(HdQuery hdQuery) {
			String jpql="select a from CBerth a where a.dockCod in ('03406500','03409000') ";
			String berthCod = hdQuery.getStr("berthCod");
			String berthNam = hdQuery.getStr("berthNam");
			QueryParamLs paramLs = new QueryParamLs();
			if(HdUtils.strNotNull(berthCod)){
				jpql += "and a.berthCod like :berthCod ";
				paramLs.addParam("berthCod", "%"+berthCod+"%");
			}
			if(HdUtils.strNotNull(berthNam)){
				jpql += "and a.berthNam like :berthNam ";
				paramLs.addParam("berthNam", "%"+berthNam+"%");
			}
			HdEzuiDatagridData result = JpaUtils.findByEz(jpql, paramLs, hdQuery);
			List<CBerth> cBerthList = result.getRows();
			for(CBerth cBerth : cBerthList){
				if(HdUtils.strNotNull(cBerth.getDockCod())){
					CDock cDock = JpaUtils.findById(CDock.class, cBerth.getDockCod());
					if(cDock != null){
						cBerth.setDockCodNam(cDock.getDockNam());
					}
				}
			}
			return result;
		}

	@Override
	public HdMessageCode save(HdEzuiSaveDatagridData<CBerth> hdEzuiSaveDatagridData) {
		// TODO Auto-generated method stub
		return JpaUtils.save(hdEzuiSaveDatagridData);
	}

	@Transactional
	public void removeAll(String berthCods) {
		List<String> berthCodList = HdUtils.paraseStrs(berthCods);
		for (String berthCod : berthCodList) {
			JpaUtils.remove(CBerth.class, berthCod);
		}
	}
	
	@Override
	public CBerth findone(String berthCod) {
		CBerth cBerth = JpaUtils.findById(CBerth.class, berthCod);
		return cBerth;

	}
	
	@Override
	public HdMessageCode saveone(@RequestBody CBerth cBerth) {
		String berthCod = cBerth.getBerthCod();
		CBerth cberth = JpaUtils.findById(CBerth.class, berthCod);
		if(cberth != null){
			JpaUtils.update(cBerth);
		}else{
			JpaUtils.save(cBerth);
		}
		return HdUtils.genMsg();
	}
	@Override
	public HdMessageCode findCBerth(String berthCod) {
		if(HdUtils.strNotNull(berthCod)){
			CBerth cBerth = JpaUtils.findById(CBerth.class, berthCod);
			if(cBerth != null){
				throw new HdRunTimeException("该代码已存在，请重新输入！");// 主键已存在
			}
		}
		return HdUtils.genMsg();
	}
}

