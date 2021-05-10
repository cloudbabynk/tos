package net.huadong.tech.base.service.impl;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.bean.HdRunTimeException;
import net.huadong.tech.base.entity.MBillVin;
import net.huadong.tech.base.entity.VWlBillVehicle;
import net.huadong.tech.base.service.MBillVinService;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.tech.ship.entity.CShipData;
import net.huadong.tech.ship.entity.Ship;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;
import net.huadong.tech.util.HdUtils;

/**
 * @author 
 */
@Component
public class MBillVinServiceImpl implements MBillVinService {
	@Override
	public HdEzuiDatagridData find(HdQuery hdQuery) {
		String jpql="select a from MBillVin a where 1=1 ";
		String shipNo = hdQuery.getStr("shipNo");
		String iEId = hdQuery.getStr("iEId");
		QueryParamLs paramLs = new QueryParamLs();
		if(HdUtils.strNotNull(shipNo)){
			jpql += "and a.shipNo =:shipNo ";
			paramLs.addParam("shipNo", shipNo);
		}
		if(HdUtils.strNotNull(iEId)){
			jpql += "and a.iEId =:iEId ";
			paramLs.addParam("iEId", iEId);
		}
		jpql += "order by a.recTim desc";
		return JpaUtils.findByEz(jpql, paramLs , hdQuery);
	}

	@Override
	public HdMessageCode save(HdEzuiSaveDatagridData<MBillVin> hdEzuiSaveDatagridData) {
		// TODO Auto-generated method stub
		List<MBillVin> mBillVinList = hdEzuiSaveDatagridData.getInsertedRows();
		for(MBillVin bean : mBillVinList){
			bean.setBillVinId(HdUtils.genUuid());
		}
		return JpaUtils.save(hdEzuiSaveDatagridData);
	}

	@Transactional
	public void removeAll(String billVinIds) {
		List<String> billVinIdList = HdUtils.paraseStrs(billVinIds);
		for (String billVinId : billVinIdList) {
			JpaUtils.remove(MBillVin.class, billVinId);
		}
	}
	
	@Transactional
	public HdMessageCode importAll(String shipNo) {
		HdMessageCode hdMessageCode =new HdMessageCode();
		Ship bean = JpaUtils.findById(Ship.class, shipNo);
		if (bean == null){
			hdMessageCode.setMessage("获取失败！");
			hdMessageCode.setCode("-1");
			return hdMessageCode;
		}
		String ivoyage = bean.getIvoyage();
		CShipData cShipData = JpaUtils.findById(CShipData.class, bean.getShipCodId());
		if (cShipData == null){
			hdMessageCode.setMessage("获取失败！");
			hdMessageCode.setCode("-1");
			return hdMessageCode;
		}
		String imo = cShipData.getShipImo();
		if (HdUtils.strIsNull(imo)){
			hdMessageCode.setMessage("船舶资料imo为空！");
			hdMessageCode.setCode("-1");
			return hdMessageCode;
		}
		String jpql = "select a from VWlBillVehicle a where a.imo =:imo and a.voyage =:voyage";
		QueryParamLs paramLs = new QueryParamLs();
		paramLs.addParam("imo", imo);
		paramLs.addParam("voyage", ivoyage);
		List<VWlBillVehicle> list = JpaUtils.findAll(jpql, paramLs);
		if (list.size() <= 0){
			hdMessageCode.setMessage("外理数据为空！");
			hdMessageCode.setCode("-1");
			return hdMessageCode;
		}
		for(VWlBillVehicle vWlBillVehicle : list){
			MBillVin mBillVin = new MBillVin();
			mBillVin.setBillVinId(HdUtils.genUuid());
			mBillVin.setShipNo(shipNo);
			mBillVin.setBillNo(vWlBillVehicle.getBillNo());
			mBillVin.setVinNo(vWlBillVehicle.getVin());
			JpaUtils.save(mBillVin);
		}
		hdMessageCode.setMessage("导入成功！");
		hdMessageCode.setCode("200");
		return  hdMessageCode;
	}
	
	@Override
	public MBillVin findone(String billVinId) {
		MBillVin mBillVin = JpaUtils.findById(MBillVin.class, billVinId);
		return mBillVin;

	}
	
	@Transactional
	@Override
	public HdMessageCode antiBill(String shipNo) {
		    HdMessageCode result1 = new HdMessageCode();   
		    String outinfo = "";
			List<Object> inParamLs = new ArrayList<Object>();
	        inParamLs.add(shipNo+"");
			List<String> result = new ArrayList<String>();//过程返回值
			JpaUtils.executeOracleProcWithResult("p_update_lh_bill", inParamLs, result, inParamLs.size()+1);
			if (result.size()>0) {
			  outinfo = result.get(0);
			}
			result1.setCode(outinfo);
			return result1;
	}	
	
	
}

