package net.huadong.tech.base.service.impl;
import com.alibaba.dubbo.config.annotation.Service;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.bean.HdRunTimeException;
import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.entity.CDock;
import net.huadong.tech.base.entity.CGate;
import net.huadong.tech.base.service.CGateService;
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
public class CGateServiceImpl implements CGateService {
	@Override
	public HdEzuiDatagridData find(HdQuery hdQuery) {
		// TODO Auto-generated method stub
		String jpql="select a from CGate a where 1=1 ";
		String gateNo = hdQuery.getStr("gateNo");
		String gateNam = hdQuery.getStr("gateNam");
		String gateTyp = hdQuery.getStr("gateTyp");

		QueryParamLs paramLs = new QueryParamLs();
		if(HdUtils.strNotNull(gateNo)){
			jpql += "and a.gateNo =:gateNo ";
			paramLs.addParam("gateNo", gateNo);
		}
		if(HdUtils.strNotNull(gateNam)){
			jpql += "and a.gateNam =:gateNam ";
			paramLs.addParam("gateNam", gateNam);
		}
		if(HdUtils.strNotNull(gateTyp)){
			jpql += "and a.gateTyp =:gateTyp ";
			paramLs.addParam("gateTyp", gateTyp);
		}
		jpql += "order by a.recTim desc";
		HdEzuiDatagridData result = JpaUtils.findByEz(jpql, paramLs, hdQuery);
		List<CGate> cGateList = result.getRows();
		for(CGate cGate : cGateList){
			if(HdUtils.strNotNull(cGate.getDockCod())){
				CDock cDock = JpaUtils.findById(CDock.class, cGate.getDockCod());
				if(cDock != null){
					cGate.setDockCodNam(cDock.getDockNam());
				}
			}
		}
		return result;
	}

	@Override
	public HdMessageCode save(HdEzuiSaveDatagridData<CGate> hdEzuiSaveDatagridData) {
		// TODO Auto-generated method stub
	return JpaUtils.save(hdEzuiSaveDatagridData);
	}
	@Transactional
	public void removeAll(String gateNos) {
		// TODO Auto-generated method stub
		List<String> gateNoList = HdUtils.paraseStrs(gateNos);
		for (String gateNo : gateNoList) {
			JpaUtils.remove(CGate.class, gateNo);
		}	
	}

	@Override
	public CGate findone(String gateNo) {
		// TODO Auto-generated method stub
		CGate cGate = JpaUtils.findById(CGate.class, gateNo);
		return cGate;

	}

	@Override
	public HdMessageCode saveone(@RequestBody CGate cGate) {
		// TODO Auto-generated method stub
		String gateNo = cGate.getGateNo();
		CGate cgate = JpaUtils.findById(CGate.class, gateNo);
		if(cgate != null){
			JpaUtils.update(cGate);
		}else{
			JpaUtils.save(cGate);
		}
		return HdUtils.genMsg();
	}
	@Override
	public HdMessageCode findCGate(String gateNo) {
		// TODO Auto-generated method stub
		if(HdUtils.strNotNull(gateNo)){
			CGate cGate = JpaUtils.findById(CGate.class, gateNo);
			if(cGate != null){
				throw new HdRunTimeException("该代码已存在，请重新输入！");// 主键已存在
			}
		}
		return HdUtils.genMsg();
	}
}

