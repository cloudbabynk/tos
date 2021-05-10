package net.huadong.tech.ship.service.impl;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.entity.CPort;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.tech.inter.entity.CPortFt;
import net.huadong.tech.ship.entity.CShipDataFt;
import net.huadong.tech.ship.service.CPortFtService;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.util.HdUtils;

@Component
public class CPortFtServiceImpl implements CPortFtService {

	@Override
	public HdEzuiDatagridData find(HdQuery hdQuery) {
		// TODO Auto-generated method stub
		String cPortFtJpql = "SELECT c FROM CPortFt c where 1=1 ";
		String vcPortName = hdQuery.getStr("vcPortName");
		QueryParamLs cPortFtParams = new QueryParamLs();
		if(HdUtils.strNotNull(vcPortName)) {
			cPortFtJpql += " and c.vcPortName like :vcPortName";
			cPortFtParams.addParam("vcPortName", "%" + vcPortName + "%");
		}
		cPortFtJpql += " order by c.recTim desc";
		HdEzuiDatagridData result = JpaUtils.findByEz(cPortFtJpql, cPortFtParams, hdQuery);
		List<CPortFt> cPortFtList = result.getRows();
		for(CPortFt cPortFt : cPortFtList) {
			cPortFt.setcPortNam(JpaUtils.findById(CPort.class, cPortFt.getPortId()).getcPortNam());
		}
		return result;
	}

	@Override
	public HdMessageCode saveone(@RequestBody CPortFt cPortFt) {
		// TODO Auto-generated method stub
		if(HdUtils.strIsNull(cPortFt.getPortFtId())) {
			cPortFt.setPortFtId(HdUtils.genUuid());
			cPortFt.setRecNam(HdUtils.getCurUser().getAccount());
			cPortFt.setRecTim(HdUtils.getDateTime());
			JpaUtils.save(cPortFt);
		} else {
			cPortFt.setUpdNam(HdUtils.getCurUser().getAccount());
			cPortFt.setUpdTim(HdUtils.getDateTime());
			JpaUtils.update(cPortFt);
		}
		return HdUtils.genMsg();
	}

	@Transactional
	public void removeAll(String portFtId) {
		// TODO Auto-generated method stub
		List<String> cPortFtList = HdUtils.paraseStrs(portFtId);
		for (String cPortDataFt : cPortFtList) {
			JpaUtils.remove(CPortFt.class, cPortDataFt);
		}
	}

}
