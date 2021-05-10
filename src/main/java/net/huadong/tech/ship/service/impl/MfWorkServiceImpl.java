package net.huadong.tech.ship.service.impl;
import com.alibaba.dubbo.config.annotation.Service;
import net.huadong.tech.base.bean.EzTreeBean;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.bean.HdRunTimeException;
import net.huadong.tech.base.entity.CBerth;
import net.huadong.tech.base.entity.CClientCod;
import net.huadong.tech.base.entity.CDamage;
import net.huadong.tech.base.entity.CPort;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.tech.ship.entity.CShipData;
import net.huadong.tech.ship.entity.Ship;
import net.huadong.tech.base.entity.MfWork;
import net.huadong.tech.ship.entity.ShipLoadPlan;
import net.huadong.tech.ship.service.MfWorkService;
import net.huadong.tech.shipbill.entity.ShipBill;
import net.huadong.idev.hdmessagecode.HdMessageCode;
import java.util.ArrayList;
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
public class MfWorkServiceImpl implements MfWorkService {
	@Override
	public HdEzuiDatagridData find(HdQuery hdQuery) {
		String jpql = "select a from MfWork a where 1=1 ";
		QueryParamLs paramLs = new QueryParamLs();
		String workDate = hdQuery.getStr("workDate");
		String shipNo = hdQuery.getStr("shipNo");
		String workTyp = hdQuery.getStr("workTyp");
		if(HdUtils.strNotNull(workDate)){
			jpql += "and a.workDate =:workDate ";
			paramLs.addParam("workDate", HdUtils.strToDate(workDate));
		}
		if(HdUtils.strNotNull(shipNo)){
			jpql += "and a.shipNo =:shipNo ";
			paramLs.addParam("shipNo", shipNo);
		}
		if(HdUtils.strNotNull(workTyp)){
			jpql += "and a.workTyp =:workTyp ";
			paramLs.addParam("workTyp", workTyp);
		}
		jpql += "order by a.recTim desc";
		HdEzuiDatagridData result = JpaUtils.findByEz(jpql, paramLs, hdQuery);
		List<MfWork> shipList = result.getRows();
		for (MfWork ship : shipList) {
		}
		return result;
	}
	@Override
	public HdMessageCode save(HdEzuiSaveDatagridData<MfWork> hdEzuiSaveDatagridData,String shipNo) {
		List<MfWork> insertList = hdEzuiSaveDatagridData.getInsertedRows();
		for(MfWork mfWork : insertList){
			mfWork.setId(HdUtils.genUuid());
			mfWork.setShipNo(shipNo);
			Ship bean = JpaUtils.findById(Ship.class, shipNo);
			if (bean != null){
				mfWork.setcShipNam(bean.getcShipNam());
			}
		}
		return JpaUtils.save(hdEzuiSaveDatagridData);
	}
	@Transactional
	public void removeAll(String ids) {
		List<String> idList = HdUtils.paraseStrs(ids);
		for (String id : idList) {
			JpaUtils.remove(MfWork.class, id);
		}
	}
	@Override
	public MfWork findone(String id) {
		MfWork bean = JpaUtils.findById(MfWork.class, id);
		return bean;
	}
	@Override
	@Transactional
	public HdMessageCode saveone(@RequestBody MfWork bean) {
		if (HdUtils.strNotNull(bean.getId())) {
			JpaUtils.update(bean);
		} else {
			bean.setId(HdUtils.genUuid());
			JpaUtils.save(bean);
		}
		return HdUtils.genMsg();
	}
}
