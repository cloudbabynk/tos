package net.huadong.tech.plan.service.impl;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.bean.HdRunTimeException;
import net.huadong.tech.base.entity.CCyArea;
import net.huadong.tech.base.entity.CCyBay;
import net.huadong.tech.base.entity.CCyRow;
import net.huadong.tech.base.entity.CDock;
import net.huadong.tech.base.entity.CEmployee;
import net.huadong.tech.cargo.service.TruckWorkService;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.tech.plan.entity.PlanGroup;
import net.huadong.tech.plan.entity.PlanPlacVin;
import net.huadong.tech.plan.entity.PlanRange;
import net.huadong.tech.plan.service.PlanGroupService;
import net.huadong.tech.shipbill.entity.BillCar;
import net.huadong.tech.shipbill.entity.PortCar;
import net.huadong.tech.shipbill.entity.ShipBill;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;
import net.huadong.tech.util.HdUtils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.eclipse.persistence.config.QueryHints;
import org.eclipse.persistence.config.ResultType;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

@Component
public class PlanGroupServiceImpl implements PlanGroupService {
	public HdEzuiDatagridData find(HdQuery hdQuery) {
		// TODO Auto-generated method stub
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String jpql = "select a from PlanGroup a where a.validatDte>=:beginDat and a.validatDte<=:endDat and a.toyotoId='0' ";
		// String planGroupNo = hdQuery.getStr("planGroupNo");
		String contractNo = hdQuery.getStr("contractNo");
		String billNo = hdQuery.getStr("billNo");
		QueryParamLs paramLs = new QueryParamLs();
		try {
			paramLs.addParam("beginDat", sdf.parse(hdQuery.getStr("beginDat")));
			paramLs.addParam("endDat", sdf.parse(hdQuery.getStr("endDat")));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		if (HdUtils.strNotNull(contractNo)) {
			jpql += "and a.contractNo =:contractNo ";
			paramLs.addParam("contractNo", contractNo);
		}
		if (HdUtils.strNotNull(billNo)) {
			jpql += "and a.billNo =:billNo ";
			paramLs.addParam("billNo", billNo);
		}
		jpql += " order by a.recTim desc";
		return JpaUtils.findByEz(jpql, paramLs, hdQuery);
	}

	public HdEzuiDatagridData findft(HdQuery hdQuery) {
		// TODO Auto-generated method stub
		String jpql = "select a from PlanGroup a where 1=1 and a.toyotoId='1'";
		// String planGroupNo = hdQuery.getStr("planGroupNo");
		String contractNo = hdQuery.getStr("contractNo");
		String billNo = hdQuery.getStr("billNo");
		QueryParamLs paramLs = new QueryParamLs();
		if (HdUtils.strNotNull(contractNo)) {
			jpql += "and a.contractNo =:contractNo ";
			paramLs.addParam("contractNo", contractNo);
		}
		if (HdUtils.strNotNull(billNo)) {
			jpql += "and a.billNo =:billNo ";
			paramLs.addParam("billNo", billNo);
		}
		jpql += "order by a.recTim desc";
		return JpaUtils.findByEz(jpql, paramLs, hdQuery);
	}

	public HdMessageCode save(HdEzuiSaveDatagridData<PlanGroup> hdEzuiSaveDatagridData) {
		// TODO Auto-generated method stub
		return JpaUtils.save(hdEzuiSaveDatagridData);
	}

	@Override
	public HdMessageCode saveone(@RequestBody PlanGroup planGroup) {

		JpaUtils.save(planGroup);
		return HdUtils.genMsg();
	}

	/**
	 * 丰田场地策划具体到车道
	 */
	@Override
	public HdMessageCode ftsaveone(@RequestBody PlanGroup planGroup) {

		JpaUtils.save(planGroup);
		long planGroupNo = planGroup.getPlanGroupNo();
		String billNo = planGroup.getBillNo();
		String jpql = "select a from BillCar a where 1=1 and a.billNo=:billNo ";
		QueryParamLs paramLs = new QueryParamLs();
		paramLs.addParam("billNo", billNo);
		List<BillCar> billCarList = JpaUtils.findAll(jpql, paramLs);
		for (int i = 0; i < billCarList.size(); i++) {
			if (billCarList.get(i).getBillcarId() != null) {
				String jpqlx = "select max(a.planGroupNo) from PlanPlacVin a where a.planGroupNo=:planGroupNo";
				QueryParamLs paramLsx = new QueryParamLs();
				paramLsx.addParam("planGroupNo", planGroupNo);
				List<Long> vinNoList = JpaUtils.findAll(jpqlx, paramLsx);
				if (vinNoList.size() > 0) {
					long maxNo = vinNoList.get(0) + 1;
					String vinNo = billCarList.get(i).getVinNo();
					PlanPlacVin planPlacVin = new PlanPlacVin();
					planPlacVin.setPlanVinNo(maxNo);
					planPlacVin.setVinNo(vinNo);
					planPlacVin.setPlanGroupNo(planGroupNo);
					planPlacVin.setCyAreaNo("");
					planPlacVin.setCyRowNo("");
					planPlacVin.setRecNam(HdUtils.getCurUser().getAccount());
					planPlacVin.setRecTim(new Date(HdUtils.getDateTime().getTime()));
					JpaUtils.save(planPlacVin);
				} else {
					String vinNo = billCarList.get(i).getVinNo();
					PlanPlacVin planPlacVin = new PlanPlacVin();
					planPlacVin.setPlanVinNo(0);
					planPlacVin.setVinNo(vinNo);
					planPlacVin.setPlanGroupNo(planGroupNo);
					planPlacVin.setCyAreaNo("");
					planPlacVin.setCyRowNo("");
					planPlacVin.setRecNam(HdUtils.getCurUser().getAccount());
					planPlacVin.setRecTim(new Date(HdUtils.getDateTime().getTime()));
					JpaUtils.save(planPlacVin);
				}
			}
		}
		return HdUtils.genMsg();
	}

	@Override
	public HdEzuiDatagridData findCCyArea(HdQuery hdQuery) {
		// TODO Auto-generated method stub
		String jpq = "select distinct a from CCyArea a ,CCyRow b,CCyBay c where 1=1 "
				+ "and a.cyAreaNo = b.cyAreaNo and b.cyRowNo = c.cyRowNo " + "and c.lockId='0'";
		String cyAreaNo = hdQuery.getStr("cyAreaNo");
		String rowNum = hdQuery.getStr("rowNum");
		QueryParamLs paramL = new QueryParamLs();
		if (HdUtils.strNotNull(cyAreaNo)) {
			jpq += "and a.cyAreaNo =:cyAreaNo ";
			paramL.addParam("cyAreaNo", cyAreaNo);
		}
		if (HdUtils.strNotNull(rowNum)) {
			jpq += "and a.rowNum =:rowNum ";
			paramL.addParam("rowNum", rowNum);
		}
		jpq += "order by a.cyAreaNo desc";
		HdEzuiDatagridData result = JpaUtils.findByEz(jpq, paramL, hdQuery);
		List<CCyArea> ccyAreaList = result.getRows();
		if (ccyAreaList.size() > 0) {
			for (CCyArea ccy : ccyAreaList) {
				CDock cd = JpaUtils.findById(CDock.class, ccy.getDockCod());
				Long unlockedNum = (long) 0;
				ccy.setDockNam(cd.getDockNam());
				String jpql2 = "select a.cyRowNo from CCyRow a where a.cyAreaNo=:cyareano";
				QueryParamLs paramLs2 = new QueryParamLs();
				paramLs2.addParam("cyareano", ccy.getCyAreaNo());
				List<String> cyRowNoList = JpaUtils.findAll(jpql2, paramLs2);
				if (cyRowNoList.size() > 0) {
					for (int i = 0; i < cyRowNoList.size(); i++) {
						String jpql3 = "select count(a.cyBayNo) from CCyBay a where a.cyAreaNo=:cyareano and a.cyRowNo=:cyrowno ";
						QueryParamLs paramLs3 = new QueryParamLs();
						paramLs3.addParam("cyareano", ccy.getCyAreaNo());
						paramLs3.addParam("cyrowno", cyRowNoList.get(i));
						List<Long> unlokcedNumList = JpaUtils.findAll(jpql3, paramLs3);
						if (unlokcedNumList.size() > 0) {
							unlockedNum += unlokcedNumList.get(0);
						}
					}
				}
				ccy.setUnlockNum(unlockedNum);
			}
		}
		return result;
	}

	@Transactional
	public void removeAll(String planGroupNo) {
		List<String> planGroupList = HdUtils.paraseStrs(planGroupNo);

		for (String plangroupno : planGroupList) {
			if (checkExist(plangroupno)) {
				throw new HdRunTimeException("改计划有相关数据不能删除！");
			}
			long plangroupNo = Long.valueOf(planGroupNo);
			JpaUtils.remove(PlanGroup.class, plangroupNo);
		}
	}

	public boolean checkExist(String plangroupno) {
		String jpql1 = "select a from PlanRange a where a.planGroupNo =:plangroupno ";
		QueryParamLs paramLs1 = new QueryParamLs();
		long plangroupNo = Long.valueOf(plangroupno);
		paramLs1.addParam("plangroupno", plangroupNo);
		List<PlanRange> planRangeList = JpaUtils.findAll(jpql1, paramLs1);
		if (planRangeList.size() > 0) {
			return true;
		}
		String jpql2 = "select a from PlanPlacVin a where a.planGroupNo =:plangroupno ";
		QueryParamLs paramLs2 = new QueryParamLs();
		// long planGroupNo=Long.valueOf(plangroupno);
		paramLs2.addParam("plangroupno", plangroupNo);
		List<PlanPlacVin> planPlacVinList = JpaUtils.findAll(jpql2, paramLs2);
		if (planPlacVinList.size() > 0) {
			return true;
		}
		return false;

	}

	@Override
	public List getUnlockedBay(String cyAreaNo) {
		BigDecimal totalNum = new BigDecimal(0);
		BigDecimal ActivedBay = new BigDecimal(0);
		BigDecimal unActivedBay = new BigDecimal(0);
		String jpql = "select a from CCyRow a where a.cyAreaNo=:cyAreaNo";
		QueryParamLs paramLs = new QueryParamLs();
		paramLs.addParam("cyAreaNo", cyAreaNo);
		List<CCyRow> crnList = JpaUtils.findAll(jpql, paramLs);
		if (crnList.size() > 0) {
			totalNum = crnList.get(0).getMaxBayNum().multiply(new BigDecimal(crnList.size()));
		}
		for (CCyRow ccr : crnList) {
			String jpql2 = "select a from CCyBay a where a.cyAreaNo=:cyAreaNo and a.cyRowNo=:cyRowNo and a.lockId='1'";
			QueryParamLs paramLs2 = new QueryParamLs();
			paramLs2.addParam("cyAreaNo", cyAreaNo);
			paramLs2.addParam("cyRowNo", ccr.getCyRowNo());
			List<CCyBay> ccbList = JpaUtils.findAll(jpql2, paramLs2);
			if (ccbList.size() > 0) {
				ActivedBay = new BigDecimal(ccbList.size());
			}
		}

		unActivedBay = totalNum.subtract(ActivedBay);
		List unActiveList = new ArrayList();
		unActiveList.add(0, unActivedBay);
		return unActiveList;
	}

	@Override
	public List getRest(String planGroupNo) {
		String jpql = "select a from PlanRange a where a.planGroupNo=:planGroupNo";
		Long restNum = (long) 0;
		QueryParamLs paramLs = new QueryParamLs();
		BigDecimal plangroupno = new BigDecimal(planGroupNo);
		paramLs.addParam("planGroupNo", plangroupno);
		List<PlanRange> planRangeList = JpaUtils.findAll(jpql, paramLs);
		if (planRangeList.size() > 0) {
			for (PlanRange planrange : planRangeList) {
				restNum = restNum + planrange.getPlanNum().longValue();
			}
		}
		List arrayList = new ArrayList();
		arrayList.add(0, restNum);	
		return arrayList;
	}

	@Override
	public HdEzuiDatagridData findBill(HdQuery hdQuery) {
		String jpql = "select a from ShipBill a  where 1=1";
		String shipNo = hdQuery.getStr("shipNo");
		QueryParamLs paramLs = new QueryParamLs();
		if(HdUtils.strNotNull(shipNo)) {
			jpql += " and a.shipNo =:shipNo ";
			paramLs.addParam("shipNo", shipNo);
		}else{
			jpql += " and a.shipNo =:shipNo ";
			paramLs.addParam("shipNo", "#123#");	
		}
	
		HdEzuiDatagridData result = JpaUtils.findByEz(jpql, paramLs, hdQuery);
		return result;
	}

	@Transactional
	@Override
	public HdMessageCode impWrokPlan(Map map) {
		String user=HdUtils.getCurUser().getAccount();
		String sql=" INSERT INTO PLAN_GROUP (  PLAN_GROUP_NO, PLAN_TYP, SHIP_NO,  SHIP_NAM, VOYAGE, I_E_ID,  TOTAL_NUM,  VALIDAT_DTE, TOYOTO_ID, REC_NAM,  REC_TIM, UPD_NAM, UPD_TIM)   " + 
				"  SELECT SEQ_PLAN_GROUP_NO.NEXTVAL  PLAN_GROUP_NO,'SI' PLAN_TYP,T1.SHIP_NO,T2.C_SHIP_NAM SHIP_NAM,T2.IVOYAGE VOYAGE,'I' I_E_ID ,T.IN_TOTAL_NUM TOTAL_NUM  " + 
				" ,T.DAYS VALIDAT_DTE,'0' TOYOTO_ID,'"+user+"' REC_NAM,SYSDATE REC_TIM,'"+user+"' UPD_NAM,SYSDATE UPD_TIM  " + 
				" FROM DAY_NIGHT_TRENDS T ,SHIP_TRENDS T1,SHIP T2  " + 
				" WHERE T.SHIP_TRENDS_ID=T1.SHIP_TRENDS_ID AND T1.SHIP_NO=T2.SHIP_NO AND   TO_CHAR(DAYS,'YYYY-MM-DD')=TO_CHAR(SYSDATE,'YYYY-MM-DD')  " + 
				" AND NOT EXISTS (SELECT 1 FROM PLAN_GROUP S WHERE S.SHIP_NO=T1.SHIP_NO AND  TO_CHAR(S.VALIDAT_DTE,'YYYY-MM-DD')=TO_CHAR(SYSDATE,'YYYY-MM-DD') ) ";	
		JpaUtils.getEntityManager().createNativeQuery(sql).executeUpdate();
		HdMessageCode messageCode=HdUtils.genMsg();
		return messageCode;
	}

}
