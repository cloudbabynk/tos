package net.huadong.tech.work.service.impl;

import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.bean.HdRunTimeException;
import net.huadong.tech.base.entity.CBrand;
import net.huadong.tech.base.entity.CCarTyp;
import net.huadong.tech.base.entity.CGate;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.tech.gate.entity.GateTruck;
import net.huadong.tech.ship.entity.Ship;
import net.huadong.tech.ship.entity.ShipLoadPlan;
import net.huadong.tech.shipbill.entity.BillCar;
import net.huadong.tech.shipbill.entity.PortCar;
import net.huadong.tech.shipbill.entity.ShipBill;
import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.work.entity.WorkQueue;
import net.huadong.tech.work.service.WorkQueueService;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import org.eclipse.persistence.config.QueryHints;
import org.eclipse.persistence.config.ResultType;
import org.springframework.stereotype.Component;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;
import net.huadong.tech.util.CommonUtil;
import net.huadong.tech.util.HdUtils;

/**
 * @author
 */
@Component
public class WorkQueueServiceImpl implements WorkQueueService {
	@Override
	public HdEzuiDatagridData find(HdQuery hdQuery) {
		String jpql = "select b.billNo  from WorkQueue a,BillCar b where b.lhFlag = '0' and a.shipNo = b.shipNo ";
		QueryParamLs paramLs = new QueryParamLs();
		String shipNo = hdQuery.getStr("shipNo");
		String type = hdQuery.getStr("type");
		if (HdUtils.strNotNull(shipNo)) {
			jpql += "and a.shipNo=:shipNo ";
			paramLs.addParam("shipNo", shipNo);
		}else{
			jpql += "and a.shipNo=:shipNo ";
			paramLs.addParam("shipNo", "123456789##");
		}
		if ("XC".equals(type) || "WMPLXC".equals(type) || "NMPLXC".equals(type)) {
			jpql += "and b.iEId ='I' and a.workTyp=:workTyp ";
			paramLs.addParam("workTyp", "SI");
		}
		jpql += "group by b.billNo order by b.billNo asc";
		List<String> billNolist = JpaUtils.findAll(jpql, paramLs);
		List<BillCar> billCarList = new ArrayList();
		for (String str : billNolist) {
			String jpql1 = "select a from BillCar a where a.billNo =:billNo";
			QueryParamLs paramLs1 = new QueryParamLs();
			paramLs1.addParam("billNo", str);
			List<BillCar> billCarList1 = JpaUtils.findAll(jpql1, paramLs1);
			if (billCarList1.size() > 0) {
				billCarList.add(billCarList1.get(0));
			}
		}
		for (BillCar bc : billCarList) {
			BigDecimal pic=null;
			String jpql2 = "select a from ShipBill a where a.billNo =:billNo";
			QueryParamLs paramLs2 = new QueryParamLs();
			paramLs2.addParam("billNo", bc.getBillNo());
			List<ShipBill> shipBillList = JpaUtils.findAll(jpql2, paramLs2);
			for (ShipBill shipBill : shipBillList) {
				if (bc.getPlanNum() == null) {
					bc.setPlanNum(shipBill.getPieces());
					pic=shipBill.getPieces();
				}
				if (HdUtils.strNotNull(shipBill.getDockCod())){
					bc.setDockCod(shipBill.getDockCod());
				}
				if (HdUtils.strNotNull(shipBill.getRemarks())){
					bc.setRemarks(shipBill.getRemarks());
				}
			}
			String jpql3 = "select count(a) from BillCar a where a.billNo =:billNo and a.lhFlag = '0'";
			List<Long> countList = JpaUtils.findAll(jpql3, paramLs2);
			if (countList.size() > 0) {
				bc.setUnTallyNum(new BigDecimal(countList.get(0)));
				bc.setTallyNum(pic.subtract(new BigDecimal(countList.get(0))));
			}
			if (HdUtils.strNotNull(bc.getBrandCod())) {
				CBrand cbrand = JpaUtils.findById(CBrand.class, bc.getBrandCod());
				if (cbrand != null) {
					bc.setBrandNam(cbrand.getBrandNam());
				}
			}
			if (HdUtils.strNotNull(bc.getCarTyp())){
				CCarTyp cCarTyp = JpaUtils.findById(CCarTyp.class, bc.getCarTyp());
				if(cCarTyp != null){
					bc.setCarTypNam(cCarTyp.getCarTypNam());
				}
			}

		}
		HdEzuiDatagridData result = new HdEzuiDatagridData();
		result.setRows(billCarList);
		return result;
	}
	
	@Override
	public HdEzuiDatagridData findZcnmlh(HdQuery hdQuery) {
		String jpql = "select a from ShipLoadPlan a where 1=1 ";
		QueryParamLs paramLs = new QueryParamLs();
		String shipNo = hdQuery.getStr("shipNo");
		if(HdUtils.strNotNull(shipNo)){
			jpql += "and a.shipNo =:shipNo";
			paramLs.addParam("shipNo", shipNo);
		}else{
			jpql += "and a.shipNo =:shipNo";
			paramLs.addParam("shipNo", "12345678###");//单纯的为了让页面不刷出数据
		}
		List<ShipLoadPlan> shipLoadPlanList = JpaUtils.findAll(jpql, paramLs);
		for(ShipLoadPlan bean : shipLoadPlanList){
			if (HdUtils.strNotNull(bean.getCarTyp())) {
				CCarTyp ccartyp = JpaUtils.findById(CCarTyp.class, bean.getCarTyp());
				if (ccartyp != null)
					bean.setCarTypNam(ccartyp.getCarTypNam());
			}
		}
		HdEzuiDatagridData result = new HdEzuiDatagridData();
		result.setRows(shipLoadPlanList);
		return result;
	}

	@Override
	public HdEzuiDatagridData findZclh(HdQuery hdQuery) {
		String jpql = "select b.billNo  from WorkQueue a,PortCar b where b.currentStat = '2' and a.shipNo = b.shipNo ";
		QueryParamLs paramLs = new QueryParamLs();
		BigDecimal pic=null;
		String shipNo = hdQuery.getStr("shipNo");
		String type = hdQuery.getStr("type");
		if (HdUtils.strNotNull(shipNo)) {
			jpql += "and a.shipNo=:shipNo ";
			paramLs.addParam("shipNo", shipNo);
		}else{
			jpql += "and a.shipNo=:shipNo ";
			paramLs.addParam("shipNo", "123456789##");
		}
		if ("ZC".equals(type) || "NMPLZC".equals(type) || "WMPLZC".equals(type)) {
			jpql += "and b.isTiComplete = '1' and b.iEId ='E' and a.workTyp=:workTyp ";
			paramLs.addParam("workTyp", "SO");
		}else if("PLTZ".equals(type)){
			jpql += "and b.iEId ='I' and a.workTyp =:workTyp ";
			paramLs.addParam("workTyp", "TZ");
		}
		jpql += "group by b.billNo order by b.billNo asc";
		List<String> billNolist = JpaUtils.findAll(jpql, paramLs);
		List<PortCar> portCarList = new ArrayList();
		for (String str : billNolist) {
			String jpql1 = "select a from PortCar a where a.billNo =:billNo and a.shipNo =:shipNo and a.currentStat = '2'";
			QueryParamLs paramLs1 = new QueryParamLs();
			paramLs1.addParam("billNo", str);
			paramLs1.addParam("shipNo", shipNo);
			List<PortCar> list = JpaUtils.findAll(jpql1, paramLs1);
			if (list.size() > 0) {
				portCarList.add(list.get(0));
			}
		}
		for (PortCar bc : portCarList) {
			String jpql2 = "select a from ShipBill a where a.billNo =:billNo and a.shipNo =:shipNo";
			QueryParamLs paramLs2 = new QueryParamLs();
			paramLs2.addParam("billNo", bc.getBillNo());
			paramLs2.addParam("shipNo", shipNo);
			List<ShipBill> shipBillList = JpaUtils.findAll(jpql2, paramLs2);
			for (ShipBill shipBill : shipBillList) {
				if (bc.getPlanNum() == null) {
					bc.setPlanNum(shipBill.getPieces());
					pic=shipBill.getPieces();
				}else{
					bc.setPlanNum(bc.getPlanNum().add(shipBill.getPieces()));
				}
				if (shipBill.getReleaseTim() != null){
					bc.setSffx("是");
				} else {
					bc.setSffx("否");
				}
			}
			
			List<Long> countList = new ArrayList<>();
			if("PLTZ".equals(type)){
				//2020年11月24日当为PLTZ时执行以下函数。
				String sql = "select f_get_zz_worknum('"+shipNo+"', '"+bc.getBillNo()+"') num  from dual";
				List<Map<String,String>> res =
						JpaUtils.getEntityManager().createNativeQuery(sql).setHint(QueryHints.RESULT_TYPE, ResultType.Map).getResultList();
				if(null!=res&&res.size()>0){
					countList.add(Long.valueOf(res.get(0).get("NUM")));
				}else{
					countList.add(0L);
				}
			}else{
				String jpql3 = "select count(a) from PortCar a where a.billNo =:billNo and a.shipNo =:shipNo and a.currentStat = '2'";
				 countList = JpaUtils.findAll(jpql3, paramLs2);
			}
			
			if (countList.size() > 0) {
				bc.setUnTallyNum(new BigDecimal(countList.get(0)));
				bc.setTallyNum(pic.subtract(new BigDecimal(countList.get(0))));
			}
			if (HdUtils.strNotNull(bc.getBrandCod())) {
				CBrand cbrand = JpaUtils.findById(CBrand.class, bc.getBrandCod());
				if (cbrand != null) {
					bc.setBrandNam(cbrand.getBrandNam());
				}
			}
			if (HdUtils.strNotNull(bc.getCarTyp())){
				CCarTyp cCarTyp = JpaUtils.findById(CCarTyp.class, bc.getCarTyp());
				if(cCarTyp != null){
					bc.setCarTypNam(cCarTyp.getCarTypNam());
				}
			}
		}
		HdEzuiDatagridData result = new HdEzuiDatagridData();
		result.setRows(portCarList);
		return result;
	}

	@Override
	public HdEzuiDatagridData findPl(HdQuery hdQuery) {
		String jpql = "select a from WorkQueue a where 1=1 ";
		QueryParamLs paramLs = new QueryParamLs();
		String shipNo = hdQuery.getStr("shipNo");
		String type = hdQuery.getStr("type");
		if (HdUtils.strNotNull(shipNo)) {
			jpql += "and a.shipNo=:shipNo ";
			paramLs.addParam("shipNo", shipNo);
		}
		if ("XC".equals(type)) {
			jpql += "and a.workTyp=:workTyp ";
			paramLs.addParam("workTyp", "SI");
		} else if ("ZC".equals(type)) {
			jpql += "and a.workTyp=:workTyp ";
			paramLs.addParam("workTyp", "SO");
		}
		HdEzuiDatagridData result = JpaUtils.findByEz(jpql, paramLs, hdQuery);
		List<WorkQueue> workQueueList = result.getRows();
		for (WorkQueue bc : workQueueList) {
			String jpql1 = "select count(a) from BillCar a where a.LhFlag = '0' and a.billNo =:billNo";
			QueryParamLs paramLs1 = new QueryParamLs();
			paramLs1.addParam("billNo", bc.getContractNo());
			List<BigDecimal> billCarList = JpaUtils.findAll(jpql1, paramLs1);
			if (billCarList.size() > 0) {
				bc.setPlanNum(billCarList.get(0));
			}
		}
		return result;
	}

	@Override
	public HdEzuiDatagridData findWorkPlan(HdQuery hdQuery) {
		String jpql ="Select new net.huadong.tech.work.entity.WorkQueue(a.workQueueNo,a.contractNo,a.recNam,a.recTim,a.remarks,a.shipNo,a.truckNo,a.workTyp,bb.planNum,bb.workNum,dd.cClientShort,doc.validatDte,doc.contractDte)  "
				+ " from WorkQueue a,GateTruckContract bb,GateTruck cc,ContractIeDoc doc,CClientCod dd"
				+ " Where a.contractNo= bb.contractNo and  a.truckNo = cc.truckNo and a.contractNo=doc.contractNo and doc.consignCod = dd.clientCod"
				+ " and bb.ingateId=cc.ingateId ";

		QueryParamLs paramLs = new QueryParamLs();
		String contractNo = hdQuery.getStr("contractNo");
		String workTyp = hdQuery.getStr("workTyp");
		String unit = hdQuery.getStr("unit");
		String consignCod = hdQuery.getStr("consignCod");
		String validatDte = hdQuery.getStr("validatDte");
		String validatDte1 = hdQuery.getStr("validatDte1");
		
		if (HdUtils.strNotNull(contractNo)) {
			jpql += " and a.contractNo=:contractNo ";
			paramLs.addParam("contractNo", contractNo);
		}
		if (HdUtils.strNotNull(workTyp)) {
			jpql += " and a.workTyp=:workTyp ";
			paramLs.addParam("workTyp", workTyp);
		}
		if (HdUtils.strNotNull(unit)) {
			jpql += " and doc.dockCod=:dockCod ";
			paramLs.addParam("dockCod", unit);
		}
		if (HdUtils.strNotNull(consignCod)) {
			jpql += " and doc.consignCod=:consignCod ";
			paramLs.addParam("consignCod", consignCod);
		}
	/*	if (HdUtils.strNotNull(validatDte)) {	
			jpql += "and doc.validatDte >=:begDte and doc.contractDte <=:endDte ";
			paramLs.addParam("begDte", HdUtils.strToDate(validatDte));
			paramLs.addParam("endDte", HdUtils.addDay( HdUtils.strToDate(validatDte), -1));
		}*/
		if (HdUtils.strNotNull(validatDte)&&HdUtils.strNotNull(validatDte1)) {	
			jpql += "and doc.validatDte >=:begDte and doc.contractDte <=:endDte ";
			paramLs.addParam("begDte", HdUtils.strToDate(validatDte));
			paramLs.addParam("endDte", HdUtils.strToDate(validatDte1));
		}

		jpql += "order by a.recTim desc";
		HdEzuiDatagridData results = JpaUtils.findByEz(jpql, paramLs, hdQuery);
		return results;
	}

	@Override
	public HdMessageCode save(HdEzuiSaveDatagridData<WorkQueue> hdEzuiSaveDatagridData) {
		// TODO Auto-generated method stub
		return JpaUtils.save(hdEzuiSaveDatagridData);
	}

	@Override
	public void generatewq(WorkQueue workQueue) {
		String shipNo = workQueue.getShipNo();
		Ship ship=JpaUtils.findById(Ship.class, shipNo);
		ship.setDiscBegTim(HdUtils.getDateTime());
		JpaUtils.update(ship);
		String jpql = "select a from WorkQueue a where a.shipNo=:shipNo and a.workTyp='SI'";
		QueryParamLs paramLs = new QueryParamLs();
		paramLs.addParam("shipNo", shipNo);
		List<WorkQueue> workQueueList = JpaUtils.findAll(jpql, paramLs);
		if (workQueueList.size() > 0) {
			throw new HdRunTimeException("已生成作业队列！");
		} else {
			workQueue.setRecNam(HdUtils.getCurUser().getAccount());
			workQueue.setRecTim(HdUtils.getDateTime());
			JpaUtils.save(workQueue);
		}
	}

	@Override
	public void generatewq2(WorkQueue workQueue) {
		String shipNo = workQueue.getShipNo();
		Ship ship=JpaUtils.findById(Ship.class, shipNo);
		ship.setLoadBegTim(HdUtils.getDateTime());
		JpaUtils.update(ship);
		String jpql = "select a from WorkQueue a where a.shipNo=:shipNo and a.workTyp='SO'";
		QueryParamLs paramLs = new QueryParamLs();
		paramLs.addParam("shipNo", shipNo);
		List<WorkQueue> workQueueList = JpaUtils.findAll(jpql, paramLs);
		if (workQueueList.size() > 0) {
			throw new HdRunTimeException("已生成作业队列！");
		} else {
			workQueue.setRecNam(HdUtils.getCurUser().getAccount());
			workQueue.setRecTim(HdUtils.getDateTime());
			JpaUtils.save(workQueue);
		}
	}
	@Override
	public void generatewq3(WorkQueue workQueue) {
		String shipNo = workQueue.getShipNo();
		Ship ship=JpaUtils.findById(Ship.class,shipNo);
		String jpql = "select a from WorkQueue a where a.shipNo=:shipNo and a.workTyp='TZ'";
		QueryParamLs paramLs = new QueryParamLs();
		paramLs.addParam("shipNo", shipNo);
		List<WorkQueue> workQueueList = JpaUtils.findAll(jpql, paramLs);
		if (workQueueList.size() > 0) {
			throw new HdRunTimeException("已生成作业队列！");
		} else {
			String truckNo="";
			String platNo="";
			String dockCod="";
			String gateNo="";
			if("03406500".equals(ship.getDockCod())){
				dockCod="G";
				gateNo="R1";
				}
			if("03409000".equals(ship.getDockCod())){
				dockCod="H";	
				gateNo="G5";
				}
			truckNo=CommonUtil.getId().substring(0, 8)+"Z"+dockCod;
			platNo=CommonUtil.getId().substring(0, 8)+"Z"+dockCod;
			GateTruck gateTruck = new GateTruck();
			gateTruck.setSingleId("0");
			gateTruck.setTruckNo(truckNo);
			gateTruck.setPlatNo(platNo);
			gateTruck.setInGatNo(gateNo);
			gateTruck.setInRecNam(HdUtils.getCurUser().getAccount());
			gateTruck.setInGatTim(HdUtils.getDateTime());
			CGate cGate = JpaUtils.findById(CGate.class, gateNo);
			gateTruck.setDockCod(cGate.getDockCod());
			gateTruck.setFinishedId("0");
			JpaUtils.save(gateTruck);
			workQueue.setTruckNo(truckNo);
			JpaUtils.save(workQueue);
		}	
	}

	@Override
	public HdMessageCode findWorkCharts(Map mapPam) {
		String ctType=mapPam.get("ctType")+"";
        String  validatDte=(String) mapPam.get("validatDte");//新传进来的起始时间
        String validatDte1=(String) mapPam.get("validatDte1");//新传进来的结束时间
        String beginYear="";
        String endYear="";
        if(HdUtils.strNotNull(validatDte)&&HdUtils.strNotNull(validatDte1)){
        String[]  strs=validatDte.split("-");
        beginYear=strs[0];
        String[]  strs1=validatDte1.split("-");
        endYear=strs1[0];
        }
        Calendar date = Calendar.getInstance();
        String Year = String.valueOf(date.get(Calendar.YEAR));//系统时间的年份
		String contactNo=mapPam.get("contactNo")+"";
		String dFmat="YYYY-MM-DD";
		String limtSql="";
		
		if(HdUtils.strNotNull(contactNo)&&mapPam.get("contactNo")!=null) {
			limtSql+=" and t1.CONTRACT_NO='"+contactNo+"'";
		}
		
		if(ctType.equals("day")) {
			dFmat="YYYY-MM-DD";
			limtSql+=" and  TO_CHAR (t2.in_gat_tim, 'YYYY-MM-DD') = to_char(sysdate,'YYYY-MM-DD'))  ";
		}else if(ctType.equals("mon")) {
			dFmat="YYYY-MM";
			limtSql+=" AND TO_CHAR (t2.in_gat_tim, 'YYYY-MM')>to_char(add_months(SYSDATE,-6),'yyyy-mm')) ";
		}else if(ctType.equals("year")) {
			/*dFmat="YYYY";
			limtSql+=" AND TO_CHAR (t2.in_gat_tim, 'YYYY')>to_char(add_months(SYSDATE,-24),'yyyy')) ";*/
			if(Year.equals(beginYear)&&Year.equals(endYear)){
			dFmat="YYYY";
			limtSql+=" AND TO_CHAR (t2.in_gat_tim, 'YYYY')>to_char(add_months(SYSDATE,-12),'yyyy')) ";
			}else {
				dFmat="YYYY";
				limtSql+=" AND TO_CHAR (t2.in_gat_tim, 'YYYY')>to_char(add_months(SYSDATE,-24),'yyyy')) ";
			}
		}
		
		String sql="  SELECT in_gat_data, SUM (plan_num) plan_num, SUM (work_num) work_num  " + 
				"    FROM (SELECT TO_CHAR (t2.in_gat_tim, '"+dFmat+"') in_gat_data, t1.plan_num,  " + 
				"                 t1.work_num  " + 
				"            FROM work_queue t0, gate_truck t2, gate_truck_contract t1  " + 
				"           WHERE t0.contract_no = t1.contract_no  " + 
				"                AND t0.truck_no = t2.truck_no  " + 
				"              AND t1.ingate_id = t2.ingate_id  " + limtSql+             
				"GROUP BY in_gat_data   ORDER BY in_gat_data";		
		List<Map> lstContainer=JpaUtils.getEntityManager().createNativeQuery(sql).setHint(QueryHints.RESULT_TYPE, ResultType.Map).getResultList(); 		
		HdMessageCode data=HdUtils.genMsg();
		data.setData(lstContainer);
		return data;
	}
	@Override
	public HdMessageCode findWorkCharts1(Map mapPam) {
		String ctType=mapPam.get("ctType")+"";
		String contactNo=mapPam.get("contactNo")+"";
		String dFmat="YYYY-MM-DD";
		String limtSql="";
		
		if(HdUtils.strNotNull(contactNo)&&mapPam.get("contactNo")!=null) {
			limtSql+=" and t1.CONTRACT_NO in ("+contactNo+")";
		}
		
		if(ctType.equals("day")) {
			dFmat="YYYY-MM-DD";
			limtSql+=" and  TO_CHAR (t2.in_gat_tim, 'YYYY-MM-DD') = to_char(sysdate,'YYYY-MM-DD'))  ";
		}else if(ctType.equals("mon")) {
			dFmat="YYYY-MM";
			limtSql+=" AND TO_CHAR (t2.in_gat_tim, 'YYYY-MM')>to_char(add_months(SYSDATE,-6),'yyyy-mm')) ";
		}else if(ctType.equals("year")) {
			dFmat="YYYY";
			limtSql+=" AND TO_CHAR (t2.in_gat_tim, 'YYYY')>to_char(add_months(SYSDATE,-24),'yyyy')) ";
		}
		
		String sql="  SELECT in_gat_data, SUM (plan_num) plan_num, SUM (work_num) work_num  " + 
				"    FROM (SELECT TO_CHAR (t2.in_gat_tim, '"+dFmat+"') in_gat_data, t1.plan_num,  " + 
				"                 t1.work_num  " + 
				"            FROM work_queue t0, gate_truck t2, gate_truck_contract t1  " + 
				"           WHERE t0.contract_no = t1.contract_no  " + 
				"                AND t0.truck_no = t2.truck_no  " + 
				"              AND t1.ingate_id = t2.ingate_id  " + limtSql+             
				"GROUP BY in_gat_data   ORDER BY in_gat_data";		
		List<Map> lstContainer=JpaUtils.getEntityManager().createNativeQuery(sql).setHint(QueryHints.RESULT_TYPE, ResultType.Map).getResultList(); 		
		HdMessageCode data=HdUtils.genMsg();
		data.setData(lstContainer);
		return data;
	}

}
