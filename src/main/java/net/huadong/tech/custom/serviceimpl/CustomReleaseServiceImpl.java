package net.huadong.tech.custom.serviceimpl;


import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.custom.entity.CustomRelease;
import net.huadong.tech.custom.service.CustomReleaseService;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;
import net.huadong.tech.util.HdUtils;
@Component
public class CustomReleaseServiceImpl implements CustomReleaseService {
	public HdEzuiDatagridData find(HdQuery hdQuery) {
		// TODO Auto-generated method stub
		String jpql="select a from BillCar a where 1=1 ";
		String billNo = hdQuery.getStr("billNo");
		String shipNo= hdQuery.getStr("shipNo");
		QueryParamLs paramLs = new QueryParamLs();
		if(HdUtils.strNotNull(billNo)){
			jpql += "and a.billNo =:billNo ";
			paramLs.addParam("billNo", billNo);
		}
		/*else{
			jpql += "and 1=2 ";
		}*/
		jpql += "order by a.billNo desc";
		return JpaUtils.findByEz(jpql, paramLs , hdQuery);
	}

	public HdMessageCode save(HdEzuiSaveDatagridData<CustomRelease> hdEzuiSaveDatagridData) {
		// TODO Auto-generated method stub
	return JpaUtils.save(hdEzuiSaveDatagridData);
	}


	public HdMessageCode saveone(@RequestBody CustomRelease customRelease) {
		// TODO Auto-generated method stub
		customRelease.setCustomid(HdUtils.genUuid());
	    JpaUtils.save(customRelease);
	    CustomRelease custom = JpaUtils.findById(CustomRelease.class, customRelease.getCustomid());
		String jpql="update PortCar a set a.customId='1' where a.shipNo=:shipNo and a.billNo=:billNo and a.iEId=:iEId";
		QueryParamLs paramLs = new QueryParamLs();
		paramLs.addParam("shipNo",custom.getShipNo());
		paramLs.addParam("billNo",custom.getBillNo());
		paramLs.addParam("iEId",custom.getiEId());
		JpaUtils.execUpdate(jpql, paramLs);
		return HdUtils.genMsg();
	}

}
