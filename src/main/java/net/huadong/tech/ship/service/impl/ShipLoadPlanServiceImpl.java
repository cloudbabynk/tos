package net.huadong.tech.ship.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import net.huadong.tech.util.CommonUtil;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.EzTreeBean;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.bean.HdRunTimeException;
import net.huadong.tech.base.entity.CCarKind;
import net.huadong.tech.base.entity.CCarTyp;
import net.huadong.tech.base.entity.CClientCod;
import net.huadong.tech.base.entity.CCyArea;
import net.huadong.tech.base.entity.CDock;
import net.huadong.tech.base.entity.CPort;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.tech.ship.entity.CShipData;
import net.huadong.tech.ship.entity.Ship;
import net.huadong.tech.ship.entity.ShipLoadPlan;
import net.huadong.tech.ship.service.ShipLoadPlanService;
import net.huadong.tech.shipbill.entity.PortCar;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;
import net.huadong.tech.task.ShipTask;
import net.huadong.tech.util.HdUtils;
import net.huadong.tech.work.entity.WorkQueue;
import net.sf.json.JSONObject;

/**
 * @author
 */
@Component
public class ShipLoadPlanServiceImpl implements ShipLoadPlanService {
	@Override
	public HdEzuiDatagridData find(HdQuery hdQuery) {
		// TODO Auto-generated method stub
		String jpql = "select a from ShipLoadPlan a where 1=1 ";
		String evoyage = hdQuery.getStr("evoyage");
		String shipNo = hdQuery.getStr("shipNo");
		String shipNam = hdQuery.getStr("shipNam");
		QueryParamLs paramLs = new QueryParamLs();
		if (HdUtils.strNotNull(evoyage)) {
			jpql += "and a.voyage =:evoyage ";
			paramLs.addParam("evoyage", evoyage);
		}
		if (HdUtils.strNotNull(shipNam)) {
			jpql += "and a.shipNam =:shipNam ";
			paramLs.addParam("shipNam", shipNam);
		}
		if (HdUtils.strIsNull(shipNo)) {
			jpql += "and a.shipNo =:shipNo ";
			paramLs.addParam("shipNo", "10000");
		}
		if (HdUtils.strNotNull(shipNo)) {
			jpql += "and a.shipNo =:shipNo ";
			paramLs.addParam("shipNo", shipNo);
		}
		jpql += "order by a.recTim desc";
		HdEzuiDatagridData result = JpaUtils.findByEz(jpql, paramLs, hdQuery);
		List<ShipLoadPlan> shiploadplanList = result.getRows();
		if (shiploadplanList.size() > 0) {
			for (ShipLoadPlan shiploadplan : shiploadplanList) {
//				if (HdUtils.strNotNull(shiploadplan.getConsignCod())) {
//					CClientCod ccc = JpaUtils.findById(CClientCod.class, shiploadplan.getConsignCod());
//					shiploadplan.setConsignNam(ccc.getCClientShort());
//				}
				if (HdUtils.strNotNull(shiploadplan.getCarKind())) {
					CCarKind cck = JpaUtils.findById(CCarKind.class, shiploadplan.getCarKind());
					shiploadplan.setCarKindNam(cck.getCarKindNam());
				}
				if (HdUtils.strNotNull(shiploadplan.getCarTyp())) {
					CCarTyp cct = JpaUtils.findById(CCarTyp.class, shiploadplan.getCarTyp());
					shiploadplan.setCarTypNam(cct.getCarTypNam());
				}
				if (HdUtils.strNotNull(shiploadplan.getDockCod())) {
					CDock cd = JpaUtils.findById(CDock.class, shiploadplan.getDockCod());
					shiploadplan.setDockNam(cd.getcDockNam());
				}
				if (HdUtils.strNotNull(shiploadplan.getLoadPortCod())) {
					String jpql2 = "select a from CPort a where a.portCod=:loadportcod ";
					QueryParamLs paramLs2 = new QueryParamLs();
					paramLs2.addParam("loadportcod", shiploadplan.getLoadPortCod());
					List<CPort> cplist = JpaUtils.findAll(jpql2, paramLs2);
					if (cplist.size() > 0) {
						for (CPort cp : cplist) {
							shiploadplan.setLoadPortNam(cp.getcPortNam());
						}
					}
				}
				if (HdUtils.strNotNull(shiploadplan.getTranPortCod())) {

					String jpql3 = "select a from CPort a where a.portCod=:tranportcod ";
					QueryParamLs paramLs3 = new QueryParamLs();
					paramLs3.addParam("tranportcod", shiploadplan.getTranPortCod());
					List<CPort> cplist2 = JpaUtils.findAll(jpql3, paramLs3);
					if (cplist2.size() > 0) {
						for (CPort cp : cplist2) {
							shiploadplan.setTranPortNam(cp.getcPortNam());
						}
					}
				}
				if (HdUtils.strNotNull(shiploadplan.getDiscPortCod())) {
					String jpql4 = "select a from CPort a where a.portCod=:discportcod ";
					QueryParamLs paramLs4 = new QueryParamLs();
					paramLs4.addParam("discportcod", shiploadplan.getDiscPortCod());
					List<CPort> cplist3 = JpaUtils.findAll(jpql4, paramLs4);
					if (cplist3.size() > 0) {
						for (CPort cp : cplist3) {
							shiploadplan.setDiscPortNam(cp.getcPortNam());
						}
					}
				}
				if (HdUtils.strNotNull(shiploadplan.getPlac())) {
					String jpql5 = "select a from CCyArea a where a.cyAreaNo=:cyAreaNo ";
					QueryParamLs paramLs5 = new QueryParamLs();
					paramLs5.addParam("cyAreaNo", shiploadplan.getPlac());
					List<CCyArea> cplist3 = JpaUtils.findAll(jpql5, paramLs5);
					if (cplist3.size() > 0) {
						for (CCyArea cp : cplist3) {
							shiploadplan.setCyAreaNam(cp.getCyAreaNam());
						}
					}
				}
			}
		}
		return result;
	}

	@Override
	public HdEzuiDatagridData findPortCar(HdQuery hdQuery) {
		String jpql="select a.billNo,a.consignCod,a.carTyp, a.carKind,a.tranPortCod,a.discPortCod, count(a.portCarNo) pieces,a.cyAreaNo"
				+" from PortCar a where a.iEId='E' ";
					String billNo = hdQuery.getStr("billNo");
					String vinNo = hdQuery.getStr("vinNo");
					String brandCod = hdQuery.getStr("brandCod");
					String cyAreaNo = hdQuery.getStr("cyAreaNo");
					String shipNo = hdQuery.getStr("shipNo");
					QueryParamLs paramLs = new QueryParamLs();
					if (HdUtils.strNotNull(billNo)) {
						jpql += " and a.billNo like :billNo ";
						paramLs.addParam("billNo", "%" + billNo + "%");
					}
					if (HdUtils.strNotNull(vinNo)) {
						jpql += " and a.vinNo like :vinNo ";
						paramLs.addParam("vinNo", "%" + vinNo + "%");
					}
					if (HdUtils.strNotNull(brandCod)) {
						jpql += " and a.brandCod  like :brandCod ";
						paramLs.addParam("brandCod", "%" + brandCod + "%");
					}
					if (HdUtils.strNotNull(shipNo)) {
						jpql += " and a.shipNo like :shipNo ";
						paramLs.addParam("shipNo", "%" + shipNo + "%");
					}
					if (HdUtils.strNotNull(cyAreaNo)) {
						jpql += " and a.cyAreaNo like :cyAreaNo ";
						paramLs.addParam("cyAreaNo", "%" + cyAreaNo + "%");
					}
					jpql+="group by a.carKind,a.billNo,a.consignCod, a.carTyp,a.carKind,a.tranPortCod, a.discPortCod, a.cyAreaNo";
					List<Object[]> objList = JpaUtils.findAll(jpql, paramLs);
					List<PortCar> allList = new ArrayList();
					for(Object[] obj : objList){
						String jqpl1 = "select a from PortCar a where 1=1";
						QueryParamLs paramLs1 = new QueryParamLs();
						if(obj[0] != null){
							jqpl1 += " and a.billNo =:billNo";
							paramLs1.addParam("billNo", obj[0]);
						}
						if(obj[1] != null){
							jqpl1 += " and a.consignCod =:consignCod";
							paramLs1.addParam("consignCod", obj[1]);
						}
						if(obj[2] != null){
							jqpl1 += " and a.carTyp =:carTyp";
							paramLs1.addParam("carTyp", obj[2]);
						}
						List<PortCar> portCarList = JpaUtils.findAll(jqpl1, paramLs1);
						if(portCarList.size()>0){
							PortCar portCar = portCarList.get(0);
							if (HdUtils.strNotNull(portCar.getConsignCod())) {
								CClientCod cct = JpaUtils.findById(CClientCod.class, portCar.getConsignCod());
								if(cct != null){
									portCar.setConsignNam(cct.getcClientNam());
								}
							}
							if (HdUtils.strNotNull(portCar.getCarTyp())) {
								CCarTyp cc = JpaUtils.findById(CCarTyp.class, portCar.getCarTyp());
								if(cc != null){
									portCar.setCarTypNam(cc.getCarTypNam());
								}
							}
							if (HdUtils.strNotNull(portCar.getCarKind())) {
								CCarKind cck = JpaUtils.findById(CCarKind.class, portCar.getCarKind());
								if(cck != null){
									portCar.setCarKindNam(cck.getCarKindNam());
								}
							}
							portCar.setPieces(new BigDecimal(obj[6].toString()));
							allList.add(portCar);
						}
					}
					HdEzuiDatagridData result = new HdEzuiDatagridData();
					result.setRows(allList);
					return result;
	}

	@Override
	public HdEzuiDatagridData findShip(HdQuery hdQuery) {
		 
		String jpql = "select a from Ship a where 1=1 and (a.shipStat='E' or a.shipStat='Y' or a.shipStat='L') ";
		String cShipNam = hdQuery.getStr("cShipNam");
		String shipCod = hdQuery.getStr("shipCod");
		String ivoyage = hdQuery.getStr("ivoyage");
		String evoyage = hdQuery.getStr("evoyage");
		String type = hdQuery.getStr("type");
		String splitTyp = hdQuery.getStr("splitTyp");
		String tradeId = hdQuery.getStr("tradeId");
		//String iEId = hdQuery.getStr("iEId");
		
		String shipStatus = hdQuery.getStr("shipStatus");


		QueryParamLs paramLs = new QueryParamLs();
		if (HdUtils.strNotNull(cShipNam)) {
			jpql += "and a.cShipNam like :cShipNam ";
			paramLs.addParam("cShipNam", "%" + cShipNam + "%");
		}
		if (HdUtils.strNotNull(shipCod)) {
			jpql += "and a.shipCod like :shipCod ";
			paramLs.addParam("shipCod", "%" + shipCod + "%");
		}
		if (HdUtils.strNotNull(ivoyage)) {
			jpql += "and a.ivoyage like :ivoyage ";
			paramLs.addParam("ivoyage", "%" + ivoyage + "%");
		}
		if (HdUtils.strNotNull(evoyage)) {
			jpql += "or a.evoyage like :evoyage ";
			paramLs.addParam("evoyage", "%" + evoyage + "%");
		}
		if (HdUtils.strNotNull(shipStatus)) {
			jpql += "and a.shipStat = :shipStat ";
			paramLs.addParam("shipStat", shipStatus);
		}

		if(CommonUtil.strNotNull(tradeId))
		{
			jpql += "and a.tradeId = :tradeId ";
			paramLs.addParam("tradeId", tradeId);
		}

//		if(CommonUtil.strNotNull(iEId))
//		{
//			jpql += "and a.iEId = :iEId ";
//			paramLs.addParam("iEId", iEId);
//		}

		if (HdUtils.strNotNull(type)){
			if (type.startsWith("W")){
				jpql += "and a.tradeId =:tradeId ";
				paramLs.addParam("tradeId", "2");
			}else if(type.startsWith("N")){
				jpql += "and a.tradeId =:tradeId ";
				paramLs.addParam("tradeId", "1");
			}
		}
		if(HdUtils.strNotNull(splitTyp)){
			if("WI".equals(splitTyp)||"WO".equals(splitTyp)){
				jpql += "and a.tradeId =:tradeId ";
				paramLs.addParam("tradeId", "2");
			}else if("NI".equals(splitTyp)||"NO".equals(splitTyp)){
				jpql += "and a.tradeId =:tradeId ";
				paramLs.addParam("tradeId", "1");
			}
		}
		jpql += "order by a.recTim desc";
		// return JpaUtils.findByEz(jpql, paramLs , hdQuery);
		HdEzuiDatagridData result = JpaUtils.findByEz(jpql, paramLs, hdQuery);
		List<Ship> shipList = result.getRows();
		for (Ship ship : shipList) {
			ship.setShipStatNam(HdUtils.getSysCodeName("SHIP_STAT", ship.getShipStat()));
		}
		return result;
	}

//	public HdEzuiDatagridData findShipBill(HdQuery hdQuery) {
//		// TODO Auto-generated method stub
//		String jpql = "select distinct a  from ShipBill a,PortCar c,BillCar b"
//				+ " where a.shipNo=b.shipNo and a.billNo= b.billNo and" + " a.iEId =b.iEId ";
//		String billNo = hdQuery.getStr("billNo");
//		String vinNo = hdQuery.getStr("vinNo");
//		String brandCod = hdQuery.getStr("brandCod");
//		String cyArea = hdQuery.getStr("cyArea");
//		String shipNo = hdQuery.getStr("shipNo");
//		QueryParamLs paramLs = new QueryParamLs();
//		if (HdUtils.strNotNull(billNo)) {
//			jpql += " and a.billNo like :billNo ";
//			paramLs.addParam("billNo", "%" + billNo + "%");
//		}
//		if (HdUtils.strNotNull(vinNo)) {
//			jpql += " and a.vinNo like :vinNo ";
//			paramLs.addParam("vinNo", "%" + vinNo + "%");
//		}
//		if (HdUtils.strNotNull(brandCod)) {
//			jpql += " and a.brandCod  like :brandCod ";
//			paramLs.addParam("brandCod", "%" + brandCod + "%");
//		}
//		if (HdUtils.strNotNull(shipNo)) {
//			jpql += " and a.shipNo like :shipNo ";
//			paramLs.addParam("shipNo", "%" + shipNo + "%");
//		}
//		if (HdUtils.strNotNull(cyArea)) {
//			jpql += " and a.cyArea like :cyArea ";
//			paramLs.addParam("cyArea", "%" + cyArea + "%");
//		}
//		jpql += " order by a.recTim desc";
//		HdEzuiDatagridData result = JpaUtils.findByEz(jpql, paramLs, hdQuery);
//		List<ShipBill> shipbillList = result.getRows();
//		if (shipbillList.size() > 0) {
//			for (ShipBill sb : shipbillList) {
//				String jpqlc = "select DISTINCT a from BillCar a,ShipBill b where  a.billNo=b.billNo and b.billNo=:billNo ";
//				QueryParamLs paramLs2 = new QueryParamLs();
//				paramLs2.addParam("billNo", sb.getBillNo());
//				List<BillCar> billcarList = JpaUtils.findAll(jpqlc, paramLs2);
//				// 没考虑一个提单多个车辆品牌
//				sb.setCarKind(billcarList.get(0).getCarKind());
//				String jpqlb = "select count(a.portCarNo) pc,b.cyAreaNo can,b.cyRowNo crn from BillCar a ,PortCar b ,ShipBill c where b.portCarNo=a.portCarNo "
//						+ "and c.billNo=a.billNo and c.billNo =:billNo  group by b.cyAreaNo ,b.cyRowNo";
//				QueryParamLs paramLs3 = new QueryParamLs();
//				paramLs3.addParam("billNo", sb.getBillNo());
//				List<Object[]> objlist = JpaUtils.findAll(jpqlb, paramLs3);
//				BigDecimal pieces = new BigDecimal(0);
//				String cyarea = "";
//				if (null != objlist && objlist.size() > 0) {
//					for (Object[] obj : objlist) {
//						pieces = pieces.add(new BigDecimal(String.valueOf(obj[0])));
//						cyarea = cyarea + obj[1].toString() + '-' + obj[2] + ',';
//
//					}
//					sb.setPieces(pieces);
//					sb.setCyArea(cyarea);
//				}
//
//			}
//
//		}
//		return result;
//	}
	public HdEzuiDatagridData findShipBill(HdQuery hdQuery) {
		String jpql="select a.billNo,a.consignCod,a.carTyp, a.carKind,a.tranPortCod,a.discPortCod, count(a.portCarNo) pieces,a.cyAreaNo"
	+" from PortCar a where a.iEId='E' ";
		String billNo = hdQuery.getStr("billNo");
		String vinNo = hdQuery.getStr("vinNo");
		String brandCod = hdQuery.getStr("brandCod");
		String cyAreaNo = hdQuery.getStr("cyArea");
		String shipNo = hdQuery.getStr("shipNo");
		QueryParamLs paramLs = new QueryParamLs();
		if (HdUtils.strNotNull(billNo)) {
			jpql += " and a.billNo like :billNo ";
			paramLs.addParam("billNo", "%" + billNo + "%");
		}
		if (HdUtils.strNotNull(vinNo)) {
			jpql += " and a.vinNo like :vinNo ";
			paramLs.addParam("vinNo", "%" + vinNo + "%");
		}
		if (HdUtils.strNotNull(brandCod)) {
			jpql += " and a.brandCod  like :brandCod ";
			paramLs.addParam("brandCod", "%" + brandCod + "%");
		}
		if (HdUtils.strNotNull(shipNo)) {
			jpql += " and a.shipNo like :shipNo ";
			paramLs.addParam("shipNo", "%" + shipNo + "%");
		}
		if (HdUtils.strNotNull(cyAreaNo)) {
			jpql += " and a.cyAreaNo like :cyArea ";
			paramLs.addParam("cyArea", "%" + cyAreaNo + "%");
		}
		jpql+="group by a.carKind,a.billNo,a.consignCod, a.carTyp,a.carKind,a.tranPortCod, a.discPortCod, a.cyAreaNo";
		List<Object[]> objList = JpaUtils.findAll(jpql, paramLs);
		List<PortCar> allList = new ArrayList();
		for(Object[] obj : objList){
			String jqpl1 = "select a from PortCar a where 1=1";
			QueryParamLs paramLs1 = new QueryParamLs();
			if(obj[0] != null){
				jqpl1 += " and a.billNo =:billNo";
				paramLs1.addParam("billNo", obj[0]);
			}
			if(obj[1] != null){
				jqpl1 += " and a.consignCod =:consignCod";
				paramLs1.addParam("consignCod", obj[1]);
			}
			if(obj[2] != null){
				jqpl1 += " and a.carTyp =:carTyp";
				paramLs1.addParam("carTyp", obj[2]);
			}
			List<PortCar> portCarList = JpaUtils.findAll(jqpl1, paramLs1);
			if(portCarList.size()>0){
				PortCar portCar = portCarList.get(0);
				if (HdUtils.strNotNull(portCar.getConsignCod())) {
					CClientCod cct = JpaUtils.findById(CClientCod.class, portCar.getConsignCod());
					if(cct != null){
						portCar.setConsignNam(cct.getcClientNam());
					}
				}
				if (HdUtils.strNotNull(portCar.getCarTyp())) {
					CCarTyp cc = JpaUtils.findById(CCarTyp.class, portCar.getCarTyp());
					if(cc != null){
						portCar.setCarTypNam(cc.getCarTypNam());
					}
				}
				if (HdUtils.strNotNull(portCar.getCarKind())) {
					CCarKind cck = JpaUtils.findById(CCarKind.class, portCar.getCarKind());
					if(cck != null){
						portCar.setCarKindNam(cck.getCarKindNam());
					}
				}
				portCar.setPieces(new BigDecimal(obj[6].toString()));
				allList.add(portCar);
			}
		}
		HdEzuiDatagridData result = new HdEzuiDatagridData();
		result.setRows(allList);
		return result;
	}
	@Override
	public HdMessageCode save(HdEzuiSaveDatagridData<ShipLoadPlan> hdEzuiSaveDatagridData) {
		// TODO Auto-generated method stub
		return JpaUtils.save(hdEzuiSaveDatagridData);
	}

	@Transactional
	public void removeAll(String planNos) {
		// TODO Auto-generated method stub
		List<String> planNoList = HdUtils.paraseStrs(planNos);
		for (String planNo : planNoList) {
			JpaUtils.remove(ShipLoadPlan.class, planNo);
		}
	}

	@Override
	public ShipLoadPlan findone(String planNo) {
		ShipLoadPlan ShipLoadPlan = JpaUtils.findById(ShipLoadPlan.class, planNo);
		return ShipLoadPlan;

	}

	@Override
	public HdMessageCode saveone(@RequestBody ShipLoadPlan shipLoadPlan) {
	    String jpqlmb="select max(cast(a.workSeqNo as int)) as maxWorkSeqNo from ShipLoadPlan a where a.shipNo=:shipNo";
	    QueryParamLs paramLsb = new QueryParamLs();
	    paramLsb.addParam("shipNo",shipLoadPlan.getShipNo());
	    List<BigDecimal> contractList=JpaUtils.findAll(jpqlmb, paramLsb);
	    if(contractList.get(0)==null){
	    	shipLoadPlan.setWorkSeqNo(new BigDecimal("1"));
        }else{
        	Integer maxWorkSeqNo=contractList.get(0).intValue()+1;
        	shipLoadPlan.setWorkSeqNo(new BigDecimal(maxWorkSeqNo.toString()));	
        }
		JpaUtils.save(shipLoadPlan);
		return HdUtils.genMsg();
	}

	@Override
	public HdMessageCode genWorkQueue(@RequestBody WorkQueue workQueue) {
		String workQueueNo = workQueue.getWorkQueueNo();
		WorkQueue workqueue = JpaUtils.findById(WorkQueue.class, workQueueNo);
		if (workqueue != null) {
			throw new HdRunTimeException("队列已生成！不能重复生成");
		} else {
			JpaUtils.save(workQueue);
			String jpql2 = "select a from PortCar a where a.shipNo=:shipNo ";
			QueryParamLs paramLs2 = new QueryParamLs();
			paramLs2.addParam("shipNo", workQueue.getShipNo());
			List<PortCar> pcList = JpaUtils.findAll(jpql2, paramLs2);
			for (int i = 0; i < pcList.size(); i++) {
				String Jpql3 = "update PortCar a  set a.outPortNo=:outPortNo where a.shipNo=:shipNo";
				QueryParamLs paramLs3 = new QueryParamLs();
				paramLs3.addParam("shipNo", workQueue.getShipNo());
				String outPortNo = workQueue.getShipNo();
				paramLs3.addParam("outPortNo", outPortNo);
				JpaUtils.execUpdate(Jpql3, paramLs3);
			}
		}
		return HdUtils.genMsg();
	}

	@Override
	public HdMessageCode findShipLoadPlan(String planNo) {
		if (HdUtils.strNotNull(planNo)) {
			ShipLoadPlan ShipLoadPlan = JpaUtils.findById(ShipLoadPlan.class, planNo);
			if (ShipLoadPlan != null) {
				throw new HdRunTimeException("该代码已存在，请重新输入！");// 主键已存在
			}
		}
		return HdUtils.genMsg();
	}	

		@Override
		public List<EzTreeBean> findTreeslp() {

			ShipTask task=new ShipTask();
			List<EzTreeBean> lst=task.getTree();
			List<EzTreeBean> rtLst=new ArrayList<>();
			for (EzTreeBean ezTreeBean : lst) {
				rtLst.add(ezTreeBean);
				List<EzTreeBean> childShip= new ArrayList<>();  
				for (EzTreeBean ezTreeBeanch : ezTreeBean.getChildren()) {
					String objStr=ezTreeBeanch.getAttributes();
					JSONObject objShip=JSONObject.fromObject(objStr);
					if(objShip.containsKey("tradeId")&&objShip.getString("tradeId").equals("2")) {
						childShip.add(ezTreeBeanch);
					}
				}
				if(childShip.size()==0) ezTreeBean.setState(null);
				ezTreeBean.setChildren(childShip);

			}
			return lst;

		}

		@Override
		public void genWorkQueue(String planNos) {
			List<String> planNoList = HdUtils.paraseStrs(planNos);
			for (String planNo : planNoList) {
				ShipLoadPlan slp=JpaUtils.findById(ShipLoadPlan.class, planNo);
			WorkQueue workqueue=new WorkQueue();
			workqueue.setWorkQueueNo(slp.getWorkSeqNo()+"SO"+slp.getShipNo());
			workqueue.setWorkTyp("SO");
			workqueue.setShipNo(slp.getShipNo());
			workqueue.setRecNam(HdUtils.getCurUser().getAccount());
			workqueue.setRecTim(HdUtils.getDateTime());
			JpaUtils.save(workqueue);
			}	
		}
}
