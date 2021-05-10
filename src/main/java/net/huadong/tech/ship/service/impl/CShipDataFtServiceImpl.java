package net.huadong.tech.ship.service.impl;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.tech.ship.entity.CShipData;
import net.huadong.tech.ship.entity.CShipDataFt;
import net.huadong.tech.ship.service.CShipDataFtService;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.util.HdUtils;

@Component
public class CShipDataFtServiceImpl implements CShipDataFtService {

	@Override
	public HdEzuiDatagridData find(HdQuery hdQuery) {
		// TODO Auto-generated method stub
		String cShipDataFtJpql = "SELECT c FROM CShipDataFt c where 1=1";
		String vcShipName = hdQuery.getStr("vcShipName");
		QueryParamLs cShipDataFtParams = new QueryParamLs();
		if(HdUtils.strNotNull(vcShipName)) {
			cShipDataFtJpql += " and c.vcShipName like :vcShipName";
			cShipDataFtParams.addParam("vcShipName","%" + vcShipName + "%");
		}
		cShipDataFtJpql += "  order by c.recTim desc";
		HdEzuiDatagridData result = JpaUtils.findByEz(cShipDataFtJpql, cShipDataFtParams , hdQuery);
		List<CShipDataFt> cShipDataFtList = result.getRows();
		for(CShipDataFt cShipDataFt : cShipDataFtList) {
			cShipDataFt.setShipDataName(JpaUtils.findById(CShipData.class, cShipDataFt.getShipCodId()).getcShipNam());
		}
		
		return result;
	}

	@Override
	public HdMessageCode saveone(@RequestBody CShipDataFt cShipDataFt) {
		if(HdUtils.strIsNull(cShipDataFt.getShipDataFtId())){
			cShipDataFt.setShipDataFtId(HdUtils.genUuid());
			cShipDataFt.setRecNam(HdUtils.getCurUser().getAccount());
			cShipDataFt.setRecTim(HdUtils.getDateTime());
			cShipDataFt.setVcShipId(cShipDataFt.getVcShipId());
			cShipDataFt.setVcShipName(cShipDataFt.getVcShipName());
			cShipDataFt.setShipCodId(cShipDataFt.getShipCodId());
			JpaUtils.save(cShipDataFt);
		}else{
			cShipDataFt.setUpdNam(HdUtils.getCurUser().getAccount());
			cShipDataFt.setUpdTim(HdUtils.getDateTime());
			JpaUtils.update(cShipDataFt);
		}
		return HdUtils.genMsg();
	}

	@Transactional
	public void removeAll(String shipDataFtId) {
		List<String> cShipDataFtList = HdUtils.paraseStrs(shipDataFtId);
		for (String cShipDataFt : cShipDataFtList) {
			JpaUtils.remove(CShipDataFt.class, cShipDataFt);
		}
		
	}

}
