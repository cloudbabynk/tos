package net.huadong.tech.wechat.serviceimpl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.eclipse.persistence.config.QueryHints;
import org.eclipse.persistence.config.ResultType;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.entity.CPort;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.tech.inter.entity.CPortFt;
import net.huadong.tech.shipbill.entity.PortCar;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.util.HdUtils;
import net.huadong.tech.wechat.entity.Crfid;
import net.huadong.tech.wechat.service.CrfidService;
import net.huadong.tech.work.entity.MoveCarPlan;
import net.huadong.tech.work.entity.MoveCarPlanBak;
import net.huadong.tech.work.entity.WorkCommand;
import net.huadong.tech.work.entity.WorkCommandBak;
import net.huadong.tech.work.entity.WorkQueue;
import net.sf.json.JSONObject;

/**
 * 捣场
 * 
 * @author hdwork
 *
 */
@Component
public class CrfidServerImpl implements CrfidService {

	@Override
	public HdEzuiDatagridData find(HdQuery hdQuery) {
		String cPortFtJpql = "SELECT c FROM Crfid c where 1=1 ";
		String rfidCod = hdQuery.getStr("rfidCod");
		QueryParamLs cPortFtParams = new QueryParamLs();
		if(HdUtils.strNotNull(rfidCod)) {
			cPortFtJpql += " and c.rfidCod = :rfidCod";
			cPortFtParams.addParam("rfidCod", rfidCod);
		}
		HdEzuiDatagridData result = JpaUtils.findByEz(cPortFtJpql, cPortFtParams, hdQuery);
		return result;
	}

	@Override
	public HdMessageCode saveone(Crfid cPortFt) {
		HdMessageCode message=HdUtils.genMsg();
		
		String cPortFtJpql = "SELECT c FROM Crfid c where 1=1 ";
		String rfidNo = cPortFt.getRfidNo();
		QueryParamLs cPortFtParams = new QueryParamLs();
		if(HdUtils.strNotNull(rfidNo)) {
			cPortFtJpql += " and c.rfidNo = :rfidNo";
			cPortFtParams.addParam("rfidNo", rfidNo);
		}
		HdEzuiDatagridData result = JpaUtils.findByEz(cPortFtJpql, cPortFtParams, new HdQuery());
		
		if(result.getRows().size()>0) {
			message.setMessage("此卡号已经绑定！");
		}else {
			if(HdUtils.strIsNull(cPortFt.getRfidCod())) {
				cPortFt.setRecNam(HdUtils.getCurUser().getAccount());
				cPortFt.setRecTim(HdUtils.getDateTime());
				JpaUtils.save(cPortFt);
			} else {
				JpaUtils.update(cPortFt);
			}
		}
		return message;
	}

	@Override
	public void removeAll(String rfid) {
		List<String> cPortFtList = HdUtils.paraseStrs(rfid);
		for (String cPortDataFt : cPortFtList) {
			JpaUtils.remove(CPortFt.class, cPortDataFt);
		}
		
	}
	

}
