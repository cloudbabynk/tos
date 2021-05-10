package net.huadong.tech.wechat.serviceimpl;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.commons.collections.CollectionUtils;
import org.eclipse.persistence.config.QueryHints;
import org.eclipse.persistence.config.ResultType;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.huadong.tech.base.entity.CBrand;
import net.huadong.tech.base.entity.CCarTyp;
import net.huadong.tech.base.entity.CCarVin;
import net.huadong.tech.base.entity.CCyBay;
import net.huadong.tech.base.entity.CEmployee;
import net.huadong.tech.base.entity.CPort;
import net.huadong.tech.base.entity.MBillVin;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.tech.inter.entity.CPortFt;
import net.huadong.tech.inter.entity.ShipInOutCheck;
import net.huadong.tech.inter.entity.YardIn;
import net.huadong.tech.ship.entity.CShipData;
import net.huadong.tech.ship.entity.Ship;
import net.huadong.tech.shipbill.entity.BillCar;
import net.huadong.tech.shipbill.entity.PortCar;
import net.huadong.tech.shipbill.entity.ShipBill;
import net.huadong.tech.util.HdUtils;
import net.huadong.tech.wechat.service.ShipDuichangService;
import net.huadong.tech.wechat.service.WechatService;
import net.huadong.tech.work.entity.WorkCommand;
import net.huadong.tech.work.entity.WorkQueue;
import net.sf.json.JSONObject;
@Component
public class ShipDuichangServiceImpl implements ShipDuichangService {
	@Autowired
	WechatService wechatService;
	@Override
	public List<PortCar> checkLoadRfid(String rfid) {
		String jpql = "select a from PortCar a where a.rfidCardNo =:rfid";
		QueryParamLs paramLs = new QueryParamLs();
		paramLs.addParam("rfid", rfid);
		List<PortCar> rfidList = JpaUtils.findAll(jpql, paramLs);
		return rfidList;
	}
	
	@Override
	public String checkShipNoAndVinNo(String shipNo, String vinNo) {
		String sql="select count(0) vn_count1 from port_car where current_stat = '5' and i_e_id = 'E' and ship_no = ?shipNo    and vin_no =?vinNo"; 
		List<Map> mList = JpaUtils.getEntityManager().createNativeQuery(sql).setParameter("shipNo", shipNo).setParameter("vinNo", vinNo)
				.setHint(QueryHints.RESULT_TYPE, ResultType.Map).getResultList();
		return mList.get(0).get("VN_COUNT1").toString();
	}
	
	@Override
	public String checkLoadVin(String vinNo, String shipNo, String billNo, String planPlac) {
		String result = "true";
		String jpql = "select a from PortCar a where a.vinNo =:vinNo";
		QueryParamLs paramLs = new QueryParamLs();
		paramLs.addParam("vinNo", vinNo);
		List<PortCar> vinNoList = JpaUtils.findAll(jpql, paramLs);
		if (vinNoList.size() > 0) {
			// 验证vin和rfid的有效性
			// 验证车架号或者rfid是否是被航次装船计划内的车辆（装船计划ship_load_plan暂时不用）
			// String jpqlslp = "select s from ShipLoadPlan s where s.shipNo
			// =:shipNo and
			// s.billNo =:billNo";
			// QueryParamLs slpParams = new QueryParamLs();
			// slpParams.addParam("shipNo", shipNo);
			// slpParams.addParam("billNo", billNo);
			// List<ShipLoadPlan> slpList = JpaUtils.findAll(jpqlslp,
			// slpParams);
			// if (slpList.size() > 0) {
			// 此车辆在装船计划内。可以继续
			// 验证当前车位是否是当前航次所在的码头的
			
			
//			String shipJpql = "select s from Ship s where s.shipNo =:shipNo";
//			QueryParamLs shipParams = new QueryParamLs();
//			shipParams.addParam("shipNo", shipNo);
//			List<Ship> shipList = JpaUtils.findAll(shipJpql, shipParams);
//			String tJpql = "select a.dockCod from CCyArea a, CCyBay b where a.cyAreaNo = b.cyAreaNo and b.cyPlac =:cyPlac and b.lockId = '1'";
//			// QueryParamLs abParams = new QueryParamLs();
//			// abParams.addParam("cyPlac", planPlac);
//			List<Map<String, Object>> abList = JpaUtils.getEntityManager().createQuery(tJpql)
//					.setParameter("cyPlac", planPlac).setHint(QueryHints.RESULT_TYPE, ResultType.Map).getResultList();
//			if (shipList.size() > 0 && abList.size() > 0) {
//				if (!(shipList.get(0).getDockCod() == null || shipList.get(0).getDockCod().toString().equals(""))) {
//					if (!(abList.get(0).get("dockCod") == null || abList.get(0).get("dockCod").toString().equals(""))) {
//						if (!shipList.get(0).getDockCod().toString().equals(abList.get(0).get("dockCod").toString())) {
//							// 船舶停靠码头与堆场位置不符
//							result = "cyPlacError";
//						}
//					}
//				}
//			}
			
			
			// } else {
			// // 此车辆不在装船计划内
			// result = "shippError";
			// }
		} else {
			result = "vinError";
		}
		return result;
	}
	@Override
	public StringBuffer loaddcbill(String shipNo) {
		StringBuffer result = new StringBuffer();
		// 该船作业数（装船堆场）
		String cJpql = "select count(a) as data from WorkCommand a where a.shipNo =:shipNo and a.workTyp = 'SO'";
		List<Map<String, Object>> billc = JpaUtils.getEntityManager().createQuery(cJpql).setParameter("shipNo", shipNo)
				.setHint(QueryHints.RESULT_TYPE, ResultType.Map).getResultList();
		if (billc.size() > 0) {
			if (billc.get(0).get("data") == null || billc.get(0).get("data").toString().equals("")) {
				result.append("0");
			} else {
				result.append(billc.get(0).get("data").toString());
			}
		} else {
			result.append("0");
		}
		result.append("/");
		// 该船计划数
		String sJpql = "select sum(a.carNum) as data from ShipLoadPlan a where a.shipNo =:shipNo";
		List<Map<String, Object>> sList = JpaUtils.getEntityManager().createQuery(sJpql).setParameter("shipNo", shipNo)
				.setHint(QueryHints.RESULT_TYPE, ResultType.Map).getResultList();
		if (sList.size() > 0) {
			if (sList.get(0).get("data") == null || sList.get(0).get("data").toString().equals("")) {
				result.append("0");
			} else {
				result.append(sList.get(0).get("data").toString());
			}
		} else {
			result.append("0");
		}
		return result;
	}
	@Transactional
	public String shiploadoutplace(String req) {  
		System.out.println(req);
		
		JSONObject jobject = JSONObject.fromObject(req);
        if(jobject.get("rfid")==null) {
        	jobject.put("rfid", "");
		}
		Date sysDate = HdUtils.getDateTime();
		String Account = String.valueOf(jobject.get("account"));
		String result = "true";
		
		//校验是否为丰田车  isFT
		String isFt = (String) jobject.get("isFT");
		if(isFt.equals("true")) {
			
		} else {
			// 内外贸
	        String jpqlship = "select a from Ship a where a.shipNo =:shipNo";
	        QueryParamLs shipParam = new QueryParamLs();
	        shipParam.addParam("shipNo", jobject.get("shipNo").toString());
	        List<Ship> shipList = JpaUtils.findAll(jpqlship, shipParam);
	        // 外贸货检查提单  内贸货暂时不看提单
	        if( shipList.get(0).getTradeId() == "2"){	        		
				// 外贸货检查提单  内贸货暂时不看提单	
				String shipBillJpql = "SELECT s FROM ShipBill s where s.shipNo=:shipNo";
				QueryParamLs shipBillParams = new QueryParamLs();
				shipBillParams.addParam("shipNo", String.valueOf(jobject.get("shipNo")));
//				shipBillParams.addParam("billNo", String.valueOf(jobject.get("billNo")));
				List<ShipBill> shipBillList = JpaUtils.findAll(shipBillJpql, shipBillParams);
				if (shipBillList.size() < 1) {
					// 没有出口舱单信息
					result = "billError";
					return result;
				}	        	
	        }
			

		}
		// 先校验rfid信息
		String rJpql = "select a from PortCar a where a.vinNo =:vinNo";
		QueryParamLs rParam = new QueryParamLs();
		rParam.addParam("vinNo", jobject.get("vinNo").toString());
		List<PortCar> pcList = JpaUtils.findAll(rJpql, rParam);
		//直取操作并且port_car里面没有车架号需要插入
		if("1".equals(jobject.get("directId"))&&pcList.size()==0) {
			PortCar pcEntity =new PortCar();
			pcEntity.setCurrentStat("2");			
			pcEntity.setShipNo((String)jobject.get("shipNo"));
			pcEntity.setBillNo("--");
			pcEntity.setInPortNo((String)jobject.get("shipNo"));
			pcEntity.setDockCod("03409000");
			pcEntity.setVinNo((String)jobject.get("vinNo"));
			//wcList1.setRfidCardNo((String)jobject.get("rfid"));
			pcEntity.setRfidCardNo((String)jobject.get("rfid"));
			pcEntity.setiEId("E");
			pcEntity.setTradeId("1");

			//pcEntity.setRecNam(HdUtils.getCurUser().getAccount()); //??????
			pcEntity.setRecNam(Account);
			Date date = new Date();       
			Timestamp nousedate = new Timestamp(date.getTime());
			pcEntity.setRecTim(nousedate);
			pcEntity.setOutCyTim(nousedate);
			
			
			String jpqla="select a from CCarVin a where a.vinNo=:vinNo ";
			QueryParamLs paramLsa = new QueryParamLs();
			paramLsa.addParam("vinNo",jobject.get("vinNo").toString().substring(0,8));
			List<CCarVin> ccvList=JpaUtils.findAll(jpqla, paramLsa);
			if(ccvList.size()>0){
				CCarTyp cct=JpaUtils.findById(CCarTyp.class,ccvList.get(0).getCarTyp());
				String brandCod=cct.getBrandCod();
				String carTyp=cct.getCarTyp();
				String carKind = cct.getCarKind();
				pcEntity.setBrandCod(brandCod);
				pcEntity.setCarTyp(carTyp);
				pcEntity.setCarKind(carKind);		
			}else{
				//
				pcEntity.setBrandCod("0121132701");//默认一汽丰田
				pcEntity.setCarTyp("678");//丰田装 默认678，一汽丰田的
				pcEntity.setCarKind("01");//默认轿车
			}
			JpaUtils.getBaseDao().save(pcEntity);
			JpaUtils.flush();
			pcList = JpaUtils.findAll(rJpql, rParam);
			
			
		}		
		if (pcList.size() > 0) { //车辆在场
			String sql="select * from work_command where vin_no='"+jobject.get("vinNo")+"' and work_typ='SO'";
			List mList=JpaUtils.getEntityManager().createNativeQuery(sql).getResultList();
			if(mList.size()>0) {
				return result = "tallyError";
			}
			if (!(pcList.get(0).getCurrentStat() == null || pcList.get(0).getCurrentStat().toString().equals(""))) {
				if (!pcList.get(0).getCurrentStat().toString().equals("2")) {
					// 此车不在场，不能装船，停止
					if(!"1".equals(jobject.get("directId"))) {
						result = "placeError";
					}
					
				} else {

					
					String pcJpql = "select a from PortCar a where a.vinNo =:vinNo and a.currentStat = '2'";
					QueryParamLs pcParams = new QueryParamLs();
					pcParams.addParam("vinNo", String.valueOf(jobject.get("vinNo")));
					List<PortCar> pcLists = JpaUtils.findAll(pcJpql, pcParams);
					if(pcLists.size() > 0) {
						PortCar pcEntity = pcLists.get(0);
						
						//丰田
						if(isFt.equals("true")) {
							String tranPortCod = (String) jobject.get("vcPortId");
							String portFt = "SELECT c FROM CPort c where c.portId =:port";
							QueryParamLs portParams = new QueryParamLs();
							portParams.addParam("port", tranPortCod);
							List<CPort> cPortFtList = JpaUtils.findAll(portFt, portParams);
							if(cPortFtList.size() > 0) {
								String port = cPortFtList.get(0).getPortCod();
								if(HdUtils.strNotNull(port)) {
									pcEntity.setTranPortCod(port);
								}
							}
						} else {
							String ls_flow;
							ls_flow = (String) jobject.get("flow");
							String flowJpql = "SELECT c FROM CPort c where c.portId =:port";
							QueryParamLs flowParams = new QueryParamLs();
							flowParams.addParam("port", ls_flow);
							List<CPort> cPortFtList2 = JpaUtils.findAll(flowJpql, flowParams);
							if(cPortFtList2.size() > 0) {
								String port2 = cPortFtList2.get(0).getPortCod();
								if(HdUtils.strNotNull(port2)) {
									pcEntity.setTranPortCod(port2);
								}
							}

							pcEntity.setLoadPortCod("CNTSN");
						}
						pcEntity.setRfidCardNo((String)jobject.get("rfid"));
						pcEntity.setCurrentStat("5");
						pcEntity.setOutCyTim(HdUtils.getDateTime());
						pcEntity.setUpdNam(Account);
						pcEntity.setUpdTim(HdUtils.getDateTime());
						pcEntity.setShipNo(String.valueOf(jobject.get("shipNo")));
						JpaUtils.getBaseDao().update(pcEntity);
					}
					
					// 流水号
					String jpqlpcar = "select a from PortCar a where a.vinNo =:vinNo";
					QueryParamLs pcarParams = new QueryParamLs();
					pcarParams.addParam("vinNo", jobject.get("vinNo").toString());
					List<PortCar> pcarList = JpaUtils.findAll(jpqlpcar, pcarParams);
					
					if(isFt.equals("true")) {
						
					} else {
						String shipBillJpql = "SELECT s FROM ShipBill s where s.shipNo=:shipNo";
						QueryParamLs shipBillParams = new QueryParamLs();
						shipBillParams.addParam("shipNo", String.valueOf(jobject.get("shipNo")));
//						shipBillParams.addParam("billNo", String.valueOf(jobject.get("billNo")));
						List<ShipBill> shipBillList = JpaUtils.findAll(shipBillJpql, shipBillParams);
						if (shipBillList.size() > 0) {
							// 插入bill_car
							BillCar billCar = new BillCar();
							ShipBill shipBill = shipBillList.get(0);
							billCar.setBillcarId(HdUtils.genUuid());
							billCar.setShipbillId(shipBill.getShipbillId());
							billCar.setShipNo(String.valueOf(jobject.get("shipNo")));
							billCar.setBillNo(shipBill.getBillNo());
							billCar.setiEId(shipBill.getiEId());
							billCar.setTradeId(shipBill.getTradeId());
							billCar.setBrandCod(String.valueOf(jobject.get("brandCod")));
							billCar.setCarTyp(shipBill.getCarTyp());
							billCar.setCarKind(shipBill.getCarKind());
							billCar.setMarks(shipBill.getMarks());
							billCar.setPortCarNo(pcarList.get(0).getPortCarNo());
							billCar.setRfidCardNo(String.valueOf(jobject.get("rfid")));
							billCar.setVinNo(String.valueOf(jobject.get("vinNo")));
							billCar.setMarks(shipBill.getMarks());
							BigDecimal  ld_piece_wgt;
							BigDecimal ld_pieces;
							BigDecimal ld_weight;
							ld_pieces = shipBill.getPieces();
							if(ld_pieces.compareTo(BigDecimal.ZERO)==0){
								ld_piece_wgt = new BigDecimal(0);							 
							}else{
								ld_weight = shipBill.getWeights();
								if (ld_weight == null){
									ld_piece_wgt = new BigDecimal(0);
								}else{
									ld_piece_wgt = shipBill.getWeights().divide(ld_pieces).setScale(3) ;
								}								
							}														
							billCar.setWeights(ld_piece_wgt);
							billCar.setRemarks("手持录入");
							billCar.setRecNam(Account);
							billCar.setRecTim((Timestamp) sysDate);
							billCar.setLhFlag("0");
							JpaUtils.getBaseDao().save(billCar);
						}
					}
					
					
					// 生成指令记录
					WorkCommand wcList = new WorkCommand();
					wcList.setQueueId(HdUtils.genUuid());
					wcList.setWorkQueueNo(jobject.get("workQueueNo").toString());
					wcList.setPortCarNo(pcarList.get(0).getPortCarNo());
					wcList.setRfidCardNo((String)jobject.get("rfid"));
					wcList.setVinNo(jobject.get("vinNo").toString());
					wcList.setShipNo(jobject.get("shipNo").toString());
//					wcList.setBillNo(jobject.get("billNo").toString());
					wcList.setWorkTyp("SO");
					wcList.setBrandCod(pcarList.get(0).getBrandCod()); // 车辆品牌
					wcList.setCarTyp(pcarList.get(0).getCarTyp()); // 车型
					wcList.setCarKind(pcarList.get(0).getCarKind()); // 车数类别
					// 直取直装(暂时不填)
					// wcList.setDirectId();
					wcList.setRemarks("手持录入");
//					wcList.setPlanPlac(jobject.get("planPlac").toString());
					wcList.setOutCyNam(Account);
					wcList.setInCyNam(Account);
					wcList.setInCyTim((Timestamp) sysDate);
					wcList.setOutCyTim((Timestamp) sysDate);
					wcList.setSendTim((Timestamp) sysDate);
					wcList.setSendNam(Account);
					wcList.setShipWorkNam(Account);
					wcList.setShipWorkTim((Timestamp) sysDate);
					wcList.setFinishedId("0");
					wcList.setQzId(jobject.get("qzId")==null?"":jobject.get("qzId").toString());
					
					wcList.setDirectId(jobject.getString("directId"));

					wcList.setLengthOverId((String)jobject.get("lengthOverId"));
					JpaUtils.getBaseDao().save(wcList);
					// 修改卸船完工时间
					String jpql6 = " update Ship a set a.loadBegTim=:loadBegTim where a.shipNo=:shipNo and a.loadBegTim is null";
					QueryParamLs paramLs6 = new QueryParamLs();
					paramLs6.addParam("shipNo", jobject.get("shipNo").toString());
					paramLs6.addParam("loadBegTim", sysDate);
					JpaUtils.execUpdate(jpql6, paramLs6);
					return result;
				}
			}
		}
		return result;
	}
	public String unloadShipNull(JSONObject jobject) {
		String flag = "true";
		Date sysDate = HdUtils.getDateTime();
		String Account = String.valueOf(jobject.get("account"));
		// 更新port_car
		// 获取船到港时间
		String jpqlship = "select a from Ship a where a.shipNo =:shipNo";
		QueryParamLs shipParam = new QueryParamLs();
		shipParam.addParam("shipNo", jobject.get("shipNo").toString());
		List<Ship> shipList = JpaUtils.findAll(jpqlship, shipParam);
		if (shipList.size() > 0) {
			// 获取车位
			String cyAreaNo = String.valueOf(jobject.get("cyAreaNo"));

			String cyPlace = cyAreaNo.substring((cyAreaNo.length() - 2), cyAreaNo.length()) + "##";
			// 处理bill_car
			String billCarJpql0 = "SELECT b FROM BillCar b where b.shipNo=:shipNo and b.lhFlag='0'";
			QueryParamLs billCarParams0 = new QueryParamLs();
			billCarParams0.addParam("shipNo", String.valueOf(jobject.get("shipNo")));
			List<BillCar> billCarList0 = JpaUtils.findAll(billCarJpql0, billCarParams0);
			if (billCarList0.size() > 0) {
				BillCar billCarEntity = billCarList0.get(0);
				billCarEntity.setRfidCardNo(String.valueOf(jobject.get("rfid")));
				billCarEntity.setVinNo(String.valueOf(jobject.get("vinNo")));
				// 车架号前八位解析品牌，车类带不出信息时，页面选择品牌，车类，点击确认入场，向车架号库里维护进去改车架号的品牌，车型信息
				// 以便于后面自动解析车架号
				String vinNo = String.valueOf(jobject.get("vinNo")).substring(0, 8);
				billCarEntity.setBrandCod(String.valueOf(jobject.get("brandCod")));
				billCarEntity.setCarKind(String.valueOf(jobject.get("carKind")));
				if (HdUtils.strNotNull(String.valueOf(jobject.get("brandCod")))
						&& HdUtils.strNotNull(String.valueOf(jobject.get("carKind")))) {
					String jpqlm = "select a from CCarTyp a where a.carKind=:carKind and a.brandCod=:brandCod ";
					QueryParamLs Paramsm = new QueryParamLs();
					Paramsm.addParam("carKind", jobject.get("carKind"));
					Paramsm.addParam("brandCod", jobject.get("brandCod"));
					List<CCarTyp> ccartypList = JpaUtils.findAll(jpqlm, Paramsm);
					if (ccartypList.size() > 0) {
						billCarEntity.setCarTyp(ccartypList.get(0).getCarTyp());
						autoInsertIntoCCarVin(vinNo, ccartypList.get(0).getCarTyp(),Account);
					} else {
						billCarEntity.setCarTyp("");
					}
				} else if (HdUtils.strIsNull(String.valueOf(jobject.get("brandCod")))
						&& HdUtils.strNotNull(String.valueOf(jobject.get("carKind")))) {
					String jpqln = "select a from CCarTyp a where a.carKind=:carKind ";
					QueryParamLs paramsm = new QueryParamLs();
					paramsm.addParam("carKind", jobject.get("carKind"));
					List<CCarTyp> ccartypList = JpaUtils.findAll(jpqln, paramsm);
					if (ccartypList.size() > 0) {
						billCarEntity.setCarTyp(ccartypList.get(0).getCarTyp());
						autoInsertIntoCCarVin(vinNo, ccartypList.get(0).getCarTyp(),Account);
					} else {
						billCarEntity.setCarTyp("");
					}
				} else {
					billCarEntity.setCarTyp("");
				}
				billCarEntity.setLengthOverId(String.valueOf(jobject.get("lengthOverId")));
				if (jobject.get("lengthOverId").toString().equals("1")&&HdUtils.strNotNull((String)jobject.get("length"))) {
					BigDecimal overLength = new BigDecimal((String) jobject.get("length"));
					billCarEntity.setLength(overLength);
				}
				billCarEntity.setLhFlag("1");
				billCarEntity.setRemarks("手持录入");
				billCarEntity.setUpdNam(Account);
				billCarEntity.setUpdTim((Timestamp) sysDate);
				JpaUtils.getBaseDao().update(billCarEntity);
			} else {
				flag = "false";
				return flag;
			}
			String pNoJpql = "SELECT b FROM BillCar b where b.shipNo=:shipNo and b.vinNo=:vinNo";
			QueryParamLs pNOParams = new QueryParamLs();
			pNOParams.addParam("shipNo", String.valueOf(jobject.get("shipNo")));
//			pNOParams.addParam("billNo", String.valueOf(jobject.get("billNo")));
			pNOParams.addParam("vinNo", String.valueOf(jobject.get("vinNo")));
			List<BillCar> pNOList = JpaUtils.findAll(pNoJpql, pNOParams);
			if (pNOList.size() > 0) {
				String portCarJpql = "SELECT p FROM PortCar p where p.portCarNo=:portCarNo";
				QueryParamLs portCarParams = new QueryParamLs();
				portCarParams.addParam("portCarNo", pNOList.get(0).getPortCarNo());
				List<PortCar> PortCarList = JpaUtils.findAll(portCarJpql, portCarParams);
				if (PortCarList.size() > 0) {
					PortCar portCarEntity = PortCarList.get(0);
					portCarEntity.setVinNo(String.valueOf(jobject.get("vinNo")));
					portCarEntity.setRfidCardNo(String.valueOf(jobject.get("rfid")));
					
					String directId=jobject.getString("directId");
					if(HdUtils.strNotNull(directId)&&directId.equals("1")) {
						portCarEntity.setCurrentStat("5");
					}else{
						portCarEntity.setCurrentStat("2");
					}
					
					portCarEntity.setBrandCod(String.valueOf(jobject.get("brandCod")));
					portCarEntity.setCarKind(String.valueOf(jobject.get("carKind")));
					if (HdUtils.strNotNull(String.valueOf(jobject.get("brandCod")))
							&& HdUtils.strNotNull(String.valueOf(jobject.get("carKind")))) {
						String jpqlm = "select a from CCarTyp a where a.carKind=:carKind and a.brandCod=:brandCod ";
						QueryParamLs Paramsm = new QueryParamLs();
						Paramsm.addParam("carKind", jobject.get("carKind"));
						Paramsm.addParam("brandCod", jobject.get("brandCod"));
						List<CCarTyp> ccartypList = JpaUtils.findAll(jpqlm, Paramsm);
						if (ccartypList.size() > 0) {
							portCarEntity.setCarTyp(ccartypList.get(0).getCarTyp());
						} else {
							portCarEntity.setCarTyp("");
						}
					} else if (HdUtils.strIsNull(String.valueOf(jobject.get("brandCod")))
							&& HdUtils.strNotNull(String.valueOf(jobject.get("carKind")))) {
						String jpqln = "select a from CCarTyp a where a.carKind=:carKind ";
						QueryParamLs paramsm = new QueryParamLs();
						paramsm.addParam("carKind", jobject.get("carKind"));
						List<CCarTyp> ccartypList = JpaUtils.findAll(jpqln, paramsm);
						if (ccartypList.size() > 0) {
							portCarEntity.setCarTyp(ccartypList.get(0).getCarTyp());
						} else {
							portCarEntity.setCarTyp("");
						}
					} else {
						portCarEntity.setCarTyp("");
					}
					portCarEntity.setCyPlac(cyPlace);
					portCarEntity.setCyAreaNo(cyAreaNo);
					if(jobject.containsKey("cyRowNo")&&HdUtils.strNotNull(jobject.getString("cyRowNo"))){
						portCarEntity.setCyRowNo(jobject.getString("cyRowNo"));
					}else {
						portCarEntity.setCyRowNo("###");
					}
					if(jobject.containsKey("cyBayNo")&&HdUtils.strNotNull(jobject.getString("cyBayNo"))){
						portCarEntity.setCyBayNo(jobject.getString("cyBayNo"));
					}else {
						portCarEntity.setCyBayNo("###");
					}
					// portCarEntity.setCyRowNo(cyRowNo);
					// portCarEntity.setCyBayNo(cyBayNo);
					portCarEntity.setLengthOverId(String.valueOf(jobject.get("lengthOverId")));
					if (String.valueOf(jobject.get("lengthOverId")).equals("1")&&HdUtils.strNotNull((String) jobject.get("length"))) {
						BigDecimal overLength = new BigDecimal((String) jobject.get("length"));
						portCarEntity.setLength(overLength);
					}
					portCarEntity.setRemarks("手持录入");
					portCarEntity.setBrandCod(String.valueOf(jobject.get("brandCod")));
					if (jobject.get("damCod") == null || String.valueOf(jobject.get("damCod")).equals("")) {
					} else {
						portCarEntity.setDamId("1");
						portCarEntity.setDamCod(jobject.get("damCod").toString());
						portCarEntity.setDamLevel(jobject.get("damLevel").toString());
						portCarEntity.setDamArea(jobject.get("damArea").toString());
					}
					portCarEntity.setInCyTim((Timestamp) sysDate);
					portCarEntity.setToPortTim(shipList.get(0).getToPortTim());
					portCarEntity.setShipNo(shipList.get(0).getShipNo());
					portCarEntity.setUpdNam(Account);
					portCarEntity.setUpdTim((Timestamp) sysDate);
					JpaUtils.getBaseDao().update(portCarEntity);
					// 卸船-上锁
					if (String.valueOf(jobject.get("bayCheck")).equals("0")) {
						if (String.valueOf(jobject.get("bayCheck")).equals("0")) {
							if (String.valueOf(jobject.get("finished")).equals("0")) {
								String ccyBayJpql = "update CCyBay c set c.lockId = '1' where c.cyAreaNo=:cyAreaNo and c.cyBayNo=:cyBayNo and c.cyRowNo=:cyRowNo";
								QueryParamLs ccyBayParams = new QueryParamLs();
								ccyBayParams.addParam("cyAreaNo", String.valueOf(jobject.get("cyAreaNo")));
								ccyBayParams.addParam("cyBayNo", String.valueOf(jobject.get("cyBayNo")));
								ccyBayParams.addParam("cyRowNo", String.valueOf(jobject.get("cyRowNo")));
								JpaUtils.execUpdate(ccyBayJpql, ccyBayParams);
							} else {
								String ccyBayJpql = "update CCyBay c set c.lockId = '1' where c.cyAreaNo=:cyAreaNo and c.cyBayNo=:cyBayNo";
								QueryParamLs ccyBayParams = new QueryParamLs();
								ccyBayParams.addParam("cyAreaNo", String.valueOf(jobject.get("cyAreaNo")));
								ccyBayParams.addParam("cyBayNo", String.valueOf(jobject.get("cyBayNo")));
								JpaUtils.execUpdate(ccyBayJpql, ccyBayParams);
							}
						}
					}
					// 插入work_command
					WorkCommand wcList = new WorkCommand();
					wcList.setQueueId(HdUtils.genUuid());
					wcList.setWorkQueueNo(jobject.get("workQueueNo").toString());
					wcList.setPortCarNo(PortCarList.get(0).getPortCarNo());
					wcList.setRfidCardNo(jobject.get("rfid").toString());
					wcList.setVinNo(jobject.get("vinNo").toString());
					wcList.setShipNo(jobject.get("shipNo").toString());
					wcList.setBillNo(jobject.get("billNo").toString());
					wcList.setWorkTyp("SI");
					wcList.setRemarks("手持录入");
					wcList.setBrandCod(PortCarList.get(0).getBrandCod()); // 车辆品牌
					wcList.setCarTyp(PortCarList.get(0).getCarTyp()); // 车型
					wcList.setCarKind(PortCarList.get(0).getCarKind()); // 车数类别
					// 直取直装（暂时不存）
					// wcList.setDirectId();
					wcList.setPlanPlac(jobject.get("planPlac").toString());
					wcList.setSendTim((Timestamp) sysDate);
					wcList.setSendNam(Account);
					wcList.setShipWorkNam(Account);
					wcList.setShipWorkTim((Timestamp) sysDate);
					wcList.setFinishedId("0");
					wcList.setCyPlac(cyPlace);
					// wcList.setDriver(jobject.get("driver").toString());
					wcList.setNightId((String) jobject.get("nightId"));
					wcList.setHolidayId((String) jobject.get("holidayId"));
					wcList.setLengthOverId((String) jobject.get("lengthOverId"));
					if (jobject.get("lengthOverId").toString().equals("1")&&HdUtils.strNotNull((String) jobject.get("length"))) {
						BigDecimal overLength = new BigDecimal((String) jobject.get("length"));
						wcList.setLength(overLength);
					}
					wcList.setUseMachId(jobject.get("useMachId").toString());
					wcList.setUseWorkerId(jobject.get("useWorkerId").toString());
					// 暂时不存
					// wcList.setRemarks(remarks);
					wcList.setLengthOverId((String) jobject.get("lengthOverId"));
					wcList.setDirectId(jobject.getString("directId"));
					// 暂时不存
					// wcList.setWidthOverId(widthOverId);
					JpaUtils.getBaseDao().save(wcList);
				}
			}
			// 更新指令
			String wcJpql = "update WorkCommand a set a.finishedId = '1' , a.inCyTim =:inCyTim , a.inCyNam =:inCyNam where a.rfidCardNo =:rfid and a.workTyp = 'SI'";
			QueryParamLs paramLWC = new QueryParamLs();
			paramLWC.addParam("inCyTim", sysDate);
			paramLWC.addParam("inCyNam", Account);
			paramLWC.addParam("rfid", jobject.get("rfid").toString());
			JpaUtils.execUpdate(wcJpql, paramLWC);
		}
		return flag;
	}
	public String unloadShip(JSONObject jobject) {
		String flag = "true";
		Date sysDate = HdUtils.getDateTime();
		String Account = String.valueOf(jobject.get("account"));
		//String ssdirectId = jobject.getString("directId");
		// 更新port_car
		// 获取船到港时间
		String jpqlship = "select a from Ship a where a.shipNo =:shipNo";
		QueryParamLs shipParam = new QueryParamLs();
		shipParam.addParam("shipNo", jobject.get("shipNo").toString());
		List<Ship> shipList = JpaUtils.findAll(jpqlship, shipParam);
		if (shipList.size() > 0) {
			// 获取车位
			String cyAreaNo = String.valueOf(jobject.get("cyAreaNo"));
			// String cyRowNo = String.valueOf(jobject.get("cyRowNo"));
			// String cyBayNo = String.valueOf(jobject.get("cyBayNo"));
			// String cyPlace = cyAreaNo.substring((cyAreaNo.length() - 2),
			// cyAreaNo.length()) + cyRowNo + cyBayNo;
			String cyPlace = "";
			if(!jobject.getString("directId").equals("1")) {
				cyPlace=cyAreaNo.substring((cyAreaNo.length() - 2), cyAreaNo.length()) + "##";
			}else {
				cyPlace="##";
			}
			
			//无提单卸船理货处理===========2019-10-28=====
			if(jobject.get("nobillid").toString().equals("1")){
				//此次加过程处理bill_car , work_command, port_car //jobject.getString("nobillid")
				//
		        String outinfo = "";
		        String vinno1     = jobject.get("vinNo")+""; 
		        String shipno1    = jobject.get("shipNo")+""; 
		        String brandcod1  = jobject.get("brandCod")+""; 
				String cartyp1    = jobject.get("carTyp")+""; 
				String carKind1   = jobject.get("carKind")+"";
				String areacod1   = jobject.get("cyAreaNo")+"";
				String rowcod1    = jobject.get("cyRowNo")+"";
				String baycod1    = jobject.get("cyBayNo")+"";
				String directid1  = jobject.get("directId")+"";
				String opercod1   = jobject.get("account")+"";
				
				List<Object> inParamLs = new ArrayList<Object>();
				inParamLs.add(vinno1);//过程参数值
				inParamLs.add(shipno1);//过程参数值
		        inParamLs.add(brandcod1);//过程参数值
		        inParamLs.add(cartyp1);//过程参数值
		        inParamLs.add(carKind1);//过程参数值
		        inParamLs.add(areacod1);//过程参数值
		        inParamLs.add(rowcod1);//过程参数值
		        inParamLs.add(baycod1);//过程参数值
		        inParamLs.add(directid1);//过程参数值
		        inParamLs.add(opercod1);//过程参数值		        

				List<String> result = new ArrayList<String>();//过程返回值
				JpaUtils.executeOracleProcWithResult("p_wx_nobill_tally", inParamLs, result, inParamLs.size()+1);
				outinfo = result.get(0);
				if(outinfo.equals("ok")||outinfo.equals("comp")){
					flag = "true";
				}else {
					flag = "false";
				}
				//
				
				return flag;
			}
			
			//以下是【有提单 - 卸船处理】
			// 处理bill_car
			String billCarJpql0 = "SELECT b FROM BillCar b where b.shipNo=:shipNo and b.billNo=:billNo and b.lhFlag='0'";
			QueryParamLs billCarParams0 = new QueryParamLs();
			billCarParams0.addParam("shipNo", String.valueOf(jobject.get("shipNo")));
			billCarParams0.addParam("billNo", String.valueOf(jobject.get("billNo")));
			List<BillCar> billCarList0 = JpaUtils.findAll(billCarJpql0, billCarParams0);
			if (billCarList0.size() > 0) {
				BillCar billCarEntity = billCarList0.get(0);
				billCarEntity.setRfidCardNo(String.valueOf(jobject.get("rfid")));
				billCarEntity.setVinNo(String.valueOf(jobject.get("vinNo")));
				// 车架号前八位解析品牌，车类带不出信息时，页面选择品牌，车类，点击确认入场，向车架号库里维护进去改车架号的品牌，车型信息
				// 以便于后面自动解析车架号
				String vinNo = String.valueOf(jobject.get("vinNo")).substring(0, 8);
				billCarEntity.setBrandCod(String.valueOf(jobject.get("brandCod")));
				billCarEntity.setCarKind(String.valueOf(jobject.get("carKind")));
				billCarEntity.setCarTyp(String.valueOf(jobject.get("carTyp")));
				billCarEntity.setLengthOverId(String.valueOf(jobject.get("lengthOverId")));
				if (jobject.get("lengthOverId").toString().equals("1")&&HdUtils.strNotNull((String) jobject.get("length"))) {
					BigDecimal overLength = new BigDecimal((String) jobject.get("length"));
					billCarEntity.setLength(overLength);
				}
				billCarEntity.setLhFlag("1");
				billCarEntity.setRemarks("手持录入");
				billCarEntity.setUpdNam(Account);
				billCarEntity.setUpdTim((Timestamp) sysDate);
				JpaUtils.getBaseDao().update(billCarEntity);
			} else {
				flag = "false";
				return flag;
			}				
			
			//
			String pNoJpql = "SELECT b FROM BillCar b where b.shipNo=:shipNo and b.billNo=:billNo and b.vinNo=:vinNo";
			QueryParamLs pNOParams = new QueryParamLs();
			pNOParams.addParam("shipNo", String.valueOf(jobject.get("shipNo")));
			pNOParams.addParam("billNo", String.valueOf(jobject.get("billNo")));
			pNOParams.addParam("vinNo", String.valueOf(jobject.get("vinNo")));
			List<BillCar> pNOList = JpaUtils.findAll(pNoJpql, pNOParams);
			if (pNOList.size() > 0) {
				String portCarJpql = "SELECT p FROM PortCar p where p.portCarNo=:portCarNo";
				QueryParamLs portCarParams = new QueryParamLs();
				//
//				String lsportcarno = pNOList.get(0).getPortCarNo().toString();
//				String lsshipno = pNOList.get(0).getShipNo().toString();
//				String lsbillno = pNOList.get(0).getBillNo().toString();
//				String lsvinno =  pNOList.get(0).getVinNo().toString();
				//
				portCarParams.addParam("portCarNo", pNOList.get(0).getPortCarNo());
				List<PortCar> PortCarList = JpaUtils.findAll(portCarJpql, portCarParams);
				if (PortCarList.size() > 0) {
					PortCar portCarEntity = PortCarList.get(0);
					portCarEntity.setVinNo(String.valueOf(jobject.get("vinNo")));
					portCarEntity.setRfidCardNo(String.valueOf(jobject.get("rfid")));
					
					String directId=jobject.getString("directId");
					if(HdUtils.strNotNull(directId)&&directId.equals("1")) {
						portCarEntity.setCurrentStat("5");
					}else{
						portCarEntity.setCurrentStat("2");
					}
					
					portCarEntity.setBrandCod(String.valueOf(jobject.get("brandCod")));
					portCarEntity.setCarKind(String.valueOf(jobject.get("carKind")));
					portCarEntity.setCarTyp(String.valueOf(jobject.get("carTyp")));
					portCarEntity.setCyPlac(cyPlace);
					portCarEntity.setCyAreaNo(cyAreaNo);
					if(jobject.containsKey("cyRowNo")&&HdUtils.strNotNull(jobject.getString("cyRowNo"))){
						portCarEntity.setCyRowNo(jobject.getString("cyRowNo"));
					}else {
						portCarEntity.setCyRowNo("###");
					}
					if(jobject.containsKey("cyBayNo")&&HdUtils.strNotNull(jobject.getString("cyBayNo"))){
						portCarEntity.setCyBayNo(jobject.getString("cyBayNo"));
					}else {
						portCarEntity.setCyBayNo("###");
					}
					// portCarEntity.setCyRowNo(cyRowNo);
					// portCarEntity.setCyBayNo(cyBayNo);
					portCarEntity.setLengthOverId(String.valueOf(jobject.get("lengthOverId")));
					if (String.valueOf(jobject.get("lengthOverId")).equals("1")&&HdUtils.strNotNull((String) jobject.get("length"))) {
						BigDecimal overLength = new BigDecimal((String) jobject.get("length"));
						portCarEntity.setLength(overLength);
					}
					portCarEntity.setRemarks("手持录入");
					portCarEntity.setBrandCod(String.valueOf(jobject.get("brandCod")));
					if (jobject.get("damCod") == null || String.valueOf(jobject.get("damCod")).equals("")) {
					} else {
						portCarEntity.setDamId("1");
						portCarEntity.setDamCod(jobject.get("damCod").toString());
						portCarEntity.setDamLevel(jobject.get("damLevel").toString());
						portCarEntity.setDamArea(jobject.get("damArea").toString());
					}
					portCarEntity.setInCyTim((Timestamp) sysDate);
					portCarEntity.setToPortTim(shipList.get(0).getToPortTim());
					portCarEntity.setShipNo(shipList.get(0).getShipNo());
					portCarEntity.setUpdNam(Account);
					portCarEntity.setUpdTim((Timestamp) sysDate);
					JpaUtils.getBaseDao().update(portCarEntity);
					// 卸船-上锁
					if (String.valueOf(jobject.get("bayCheck")).equals("0")) {
						if (String.valueOf(jobject.get("bayCheck")).equals("0")) {
							if (String.valueOf(jobject.get("finished")).equals("0")) {
								String ccyBayJpql = "update CCyBay c set c.lockId = '1' where c.cyAreaNo=:cyAreaNo and c.cyBayNo=:cyBayNo and c.cyRowNo=:cyRowNo";
								QueryParamLs ccyBayParams = new QueryParamLs();
								ccyBayParams.addParam("cyAreaNo", String.valueOf(jobject.get("cyAreaNo")));
								ccyBayParams.addParam("cyBayNo", String.valueOf(jobject.get("cyBayNo")));
								ccyBayParams.addParam("cyRowNo", String.valueOf(jobject.get("cyRowNo")));
								JpaUtils.execUpdate(ccyBayJpql, ccyBayParams);
							} else {
								String ccyBayJpql = "update CCyBay c set c.lockId = '1' where c.cyAreaNo=:cyAreaNo and c.cyBayNo=:cyBayNo";
								QueryParamLs ccyBayParams = new QueryParamLs();
								ccyBayParams.addParam("cyAreaNo", String.valueOf(jobject.get("cyAreaNo")));
								ccyBayParams.addParam("cyBayNo", String.valueOf(jobject.get("cyBayNo")));
								JpaUtils.execUpdate(ccyBayJpql, ccyBayParams);
							}
						}
					}
					// 插入work_command
					WorkCommand wcList = new WorkCommand();
					wcList.setQueueId(HdUtils.genUuid());
				
					wcList.setWorkQueueNo(jobject.get("workQueueNo").toString());
					wcList.setPortCarNo(PortCarList.get(0).getPortCarNo());
					wcList.setRfidCardNo(jobject.get("rfid").toString());
					wcList.setVinNo(jobject.get("vinNo").toString());
					wcList.setShipNo(jobject.get("shipNo").toString());
					wcList.setBillNo(jobject.get("billNo").toString());
					wcList.setWorkTyp("SI");					
					wcList.setRemarks("手持录入");
					wcList.setBrandCod(PortCarList.get(0).getBrandCod()); // 车辆品牌
					wcList.setCarTyp(PortCarList.get(0).getCarTyp()); // 车型
					wcList.setCarKind(PortCarList.get(0).getCarKind()); // 车数类别
					// 直取直装（暂时不存）
					// wcList.setDirectId();
					//wcList.setPlanPlac(jobject.get("planPlac").toString());
					wcList.setSendTim((Timestamp) sysDate);
					wcList.setSendNam(Account);
					wcList.setShipWorkNam(Account);
					wcList.setShipWorkTim((Timestamp) sysDate);
					wcList.setFinishedId("0");
					wcList.setCyPlac(cyPlace);
					wcList.setNightId((String) jobject.get("nightId"));
					wcList.setHolidayId((String) jobject.get("holidayId"));
					wcList.setLengthOverId((String) jobject.get("lengthOverId"));
					if (jobject.get("lengthOverId").toString().equals("1")&&HdUtils.strNotNull((String) jobject.get("length"))) {
						BigDecimal overLength = new BigDecimal((String) jobject.get("length"));
						wcList.setLength(overLength);
					}
					wcList.setUseMachId(jobject.get("useMachId").toString());
					wcList.setUseWorkerId(jobject.get("useWorkerId").toString());
					// 暂时不存
					// wcList.setRemarks(remarks);
					wcList.setLengthOverId((String) jobject.get("lengthOverId"));
					// 暂时不存
					// wcList.setWidthOverId(widthOverId);
					wcList.setDirectId(jobject.getString("directId"));
					
					wcList.setNoBillId(jobject.getString("nobillid"));
					JpaUtils.getBaseDao().save(wcList);
					
					// 转栈的workcommand要加一条转栈记录
					if (jobject.getString("directId").equals("1")) {
						WorkCommand wcListdi = new WorkCommand();
						wcListdi.setQueueId(HdUtils.genUuid());
						//
						wcListdi.setWorkQueueNo(jobject.get("workQueueNo").toString());
						wcListdi.setPortCarNo(PortCarList.get(0).getPortCarNo());
						wcListdi.setRfidCardNo(jobject.get("rfid").toString());
						wcListdi.setVinNo(jobject.get("vinNo").toString());
						wcListdi.setShipNo(jobject.get("shipNo").toString());
						wcListdi.setBillNo(jobject.get("billNo").toString());
						wcListdi.setWorkTyp("TZ");					
						wcListdi.setRemarks("手持录入");
						wcListdi.setBrandCod(PortCarList.get(0).getBrandCod()); // 车辆品牌
						wcListdi.setCarTyp(PortCarList.get(0).getCarTyp()); // 车型
						wcListdi.setCarKind(PortCarList.get(0).getCarKind()); // 车数类别
						wcListdi.setSendTim((Timestamp) sysDate);
						wcListdi.setSendNam(Account);
						wcListdi.setShipWorkNam(Account);
						wcListdi.setShipWorkTim((Timestamp) sysDate);
						wcListdi.setFinishedId("0");
						wcListdi.setCyPlac(cyPlace);
						wcListdi.setNightId((String) jobject.get("nightId"));
						wcListdi.setHolidayId((String) jobject.get("holidayId"));
						wcListdi.setLengthOverId((String) jobject.get("lengthOverId"));
						if (jobject.get("lengthOverId").toString().equals("1")&&HdUtils.strNotNull((String) jobject.get("length"))) {
							BigDecimal overLength = new BigDecimal((String) jobject.get("length"));
							wcListdi.setLength(overLength);
						}
						wcListdi.setUseMachId(jobject.get("useMachId").toString());
						wcListdi.setUseWorkerId(jobject.get("useWorkerId").toString());
						wcListdi.setLengthOverId((String) jobject.get("lengthOverId"));
						wcListdi.setDirectId(jobject.getString("directId"));
						wcListdi.setNoBillId(jobject.getString("nobillid"));
						JpaUtils.getBaseDao().save(wcListdi);						
					}
				}
			}
			// 更新指令
			String wcJpql = "update WorkCommand a set a.finishedId = '1' , a.inCyTim =:inCyTim , a.inCyNam =:inCyNam where a.rfidCardNo =:rfid and a.workTyp = 'SI'";
			QueryParamLs paramLWC = new QueryParamLs();
			paramLWC.addParam("inCyTim", sysDate);
			paramLWC.addParam("inCyNam", Account);
			paramLWC.addParam("rfid", jobject.get("rfid").toString());
			JpaUtils.execUpdate(wcJpql, paramLWC);
		}
		return flag;
	}
	// 车架号前八位解析品牌，车类带不出信息时，页面选择品牌，车类，点击确认入场，向车架号库里维护进去改车架号的品牌，车型信息
	// 以便于后面自动解析车架号
	private void autoInsertIntoCCarVin(String vinNo, String carTyp, String Account) {
		String jpqlmx = "select a from CCarVin a where a.vinNo=:vinNo ";
		QueryParamLs paramLmx = new QueryParamLs();
		paramLmx.addParam("vinNo", vinNo);
		List<CCarVin> ccvList = JpaUtils.findAll(jpqlmx, paramLmx);
		// 车架号库里有则不维护，没有维护进去
		if (ccvList.size() > 0) {
		} else {
			String vinId = HdUtils.generateUUID();
			CCarVin ccv = new CCarVin();
			ccv.setVinId(vinId);
			ccv.setCarTyp(carTyp);
			ccv.setVinNo(vinNo);
			ccv.setRecNam(Account);
			ccv.setRecTim(HdUtils.getDateTime());
			JpaUtils.getBaseDao().save(ccv);
		}
	}
	@Override
	@Transactional
	public String shipUnloaderdc(String req) {
		//System.out.println(req);
		JSONObject jobject = JSONObject.fromObject(req);
		String result = "true";
		String sbillno = jobject.get("billNo").toString();
		String nobillid = jobject.get("nobillid").toString();
		
//		//2020-11-23 又要求外贸卸船 需要校验 无提单理货
//		//直取不校验MBillVin 2019-08 
//		if(jobject.get("directId").equals("1") ){
//			String inbillNo = jobject.get("billNo").toString();
//			if(inbillNo.equals("请选择...")||inbillNo.equals("")||inbillNo == null){
//				result = "billNoError";
//				return result;
//			}
//			if (String.valueOf(unloadShip(jobject)).equals("false")) {
//				result = "finished";
//			}else{
//				result = "true";
//			}			
//			return result;
//		}	
		
		//正常卸船的
		String checkVin="select a from MBillVin a where a.shipNo=:shipNo and a.vinNo=:vinNo";
		QueryParamLs checkParamLs = new QueryParamLs();
		checkParamLs.addParam("vinNo", jobject.get("vinNo"));
		checkParamLs.addParam("shipNo", jobject.get("shipNo"));
		List<MBillVin> checkList = JpaUtils.findAll(checkVin, checkParamLs);
		if(jobject.get("nobillid").toString().equals("0")){ // 有提单校验
			if(checkList.size()==0 ) {
				result="billNoError";//提单车架号校验
				return result;
			}else {
				MBillVin mBillVin=checkList.get(0);
				jobject.put("billNo", mBillVin.getBillNo());
			}			
		}else{
			//无提单校验
			jobject.put("billNo", "*");
		}

		if (!(jobject.get("vinNo") == null || jobject.get("vinNo").toString().equals(""))) {
			String jpqlbc = "select a from PortCar a where a.vinNo =:vinNo";
			QueryParamLs bcParamLs = new QueryParamLs();
			bcParamLs.addParam("vinNo", jobject.get("vinNo").toString());
			List<PortCar> bcList = JpaUtils.findAll(jpqlbc, bcParamLs);
			if (bcList.size() > 0) {
				if (bcList.get(0).getCurrentStat().toString().equals("2")
						|| bcList.get(0).getCurrentStat().toString().equals("3")) {
					// 当前车架号已经在场，不能重复卸船。停止继续操作
					result = "vinError";
					// break;
				} else {
					if (!(jobject.get("rfid") == null || jobject.get("rfid").toString().equals(""))) {
						String jpqlpc = "select a from PortCar a where a.rfidCardNo =:rfid";
						QueryParamLs pcParamLs = new QueryParamLs();
						pcParamLs.addParam("rfid", jobject.get("rfid").toString());
						List<PortCar> pcList = JpaUtils.findAll(jpqlpc, pcParamLs);
						if (pcList.size() > 0) {
							if (pcList.get(0).getCurrentStat().toString().equals("2")
									|| pcList.get(0).getCurrentStat().toString().equals("3")) {
								// 当前rfid已经在场，不能重复卸船。停止继续操作
								result = "rfidError";
							} else {
//										if (String.valueOf(unloadShip(jobject)).equals("false")) {
//											result = "finished";
//										}
								// 判断bill_no是否为空
								if (String.valueOf(jobject.get("billNo")) == null
										|| String.valueOf(jobject.get("billNo")).length() == 0
										|| String.valueOf(jobject.get("billNo")).equals("")) {
									if (String.valueOf(unloadShipNull(jobject)).equals("false")) {
										result = "finished";
									}
								} else {
									if (String.valueOf(unloadShip(jobject)).equals("false")) {
										result = "finished";
									}
								}
							}
						} else {
							// 判断bill_no是否为空
							if (String.valueOf(jobject.get("billNo")) == null
									|| String.valueOf(jobject.get("billNo")).length() == 0
									|| String.valueOf(jobject.get("billNo")).equals("")) {
								if (String.valueOf(unloadShipNull(jobject)).equals("false")) {
									result = "finished";
								}
							} else {
								if (String.valueOf(unloadShip(jobject)).equals("false")) {
									result = "finished";
								}
							}
						}
					}
				}
			} else {
				// 判断bill_no是否为空
				if (String.valueOf(jobject.get("billNo")) == null || String.valueOf(jobject.get("billNo")).length() == 0
						|| String.valueOf(jobject.get("billNo")).equals("")) {
					if (String.valueOf(unloadShipNull(jobject)).equals("false")) {
						result = "finished";
					}
				} else {
					if (String.valueOf(unloadShip(jobject)).equals("false")) {
						result = "finished";
					}else{
						result = "true";
					}
				}
			}
		}
		return result;
	}
	@Override
	public StringBuffer billcount(String shipNo, String billNo) {
		StringBuffer result = new StringBuffer();
		// 该提单作业数
		String cJpql = "select count(a) as data from WorkCommand a where a.shipNo =:shipNo and a.workTyp = 'SI' and a.billNo =:billNo";
		List<Map<String, Object>> billc = JpaUtils.getEntityManager().createQuery(cJpql).setParameter("shipNo", shipNo)
				.setParameter("billNo", billNo).setHint(QueryHints.RESULT_TYPE, ResultType.Map).getResultList();
		if (billc.size() > 0) {
			result.append(String.valueOf(billc.get(0).get("data")));
			result.append("/");
		} else {
			result.append("0");
			result.append("/");
		}
		// 该提单计划数
		String sJpql = "select a.pieces from ShipBill a where a.iEId = 'I' and a.shipNo =:shipNo and a.billNo =:billNo";  //carNum
		List<Map<String, Object>> sList = JpaUtils.getEntityManager().createQuery(sJpql).setParameter("shipNo", shipNo)
				.setParameter("billNo", billNo).setHint(QueryHints.RESULT_TYPE, ResultType.Map).getResultList();
		if (sList.size() > 0) {
			if (!(sList.get(0).get("pieces") == null || sList.get(0).get("pieces").toString().equals(""))) {
				result.append(String.valueOf(sList.get(0).get("pieces")));
			} else {
				result.append("0");
			}
		}
		return result;
	}
	@Override
	public StringBuffer shipCarCount(String shipNo) {
		StringBuffer result = new StringBuffer();
		// 该提单计划数
		String sJpql = "select sum(a.pieces) from ShipBill a where a.iEId = 'I' and a.shipNo =:shipNo";  //carNum
		QueryParamLs paramLs = new QueryParamLs();
		paramLs.addParam("shipNo", shipNo);
		BigDecimal num = JpaUtils.single(sJpql, paramLs);
		if (num != null) {
			result.append(num.toString());
		} else {
			result.append("0");
		}
		result.append("/");
		// 该提单作业数
		String cJpql = "select count(a) as data from WorkCommand a where a.shipNo =:shipNo and a.workTyp = 'SI'";
		List<Map<String, Object>> billc = JpaUtils.getEntityManager().createQuery(cJpql).setParameter("shipNo", shipNo).setHint(QueryHints.RESULT_TYPE, ResultType.Map).getResultList();
		if (billc.size() > 0) {
			result.append(String.valueOf(billc.get(0).get("data")));
		} else {
			result.append("0");
		}
		return result;
	}
	@Override
	public String checkDRIVER(String shipNo, String billNo, String vinNo) {
		String result = "";
		String jpql = "select b.cyAreaNo, b.cyRowNo from PlanGroup a, PlanPlacVin b where "
				+ "a.planGroupNo = b.planGroupNo and a.planTyp = 'SI' and a.toyotoId = '1' "
				+ " and a.shipNo =:shipNo and a.billNo =:billNo and b.vinNo =:vinNo";
		// QueryParamLs paramLs = new QueryParamLs();
		// and a.shipNo =:shipNo
		// paramLs.addParam("shipNo", shipNo);
		// paramLs.addParam("billNo", billNo);
		// paramLs.addParam("vinNo", vinNo);
		List<Map<String, Object>> toyotoList = JpaUtils.getEntityManager().createQuery(jpql)
				.setParameter("billNo", billNo).setParameter("vinNo", vinNo).setParameter("shipNo", shipNo)
				.setHint(QueryHints.RESULT_TYPE, ResultType.Map).getResultList();
		if (toyotoList.size() > 0) {
			Map map = (Map) toyotoList.get(0);
			String s = map.get("cyAreaNo").toString();
			String s1 = map.get("cyRowNo").toString();
			if (!s.equals(null) || s1.equals(null)) {
				result = s + " " + s1;
			}
		} else {
			String jpql0 = "Select bb.cyAreaNo\r\n" + "  from PlanGroup aa, PlanRange bb\r\n"
					+ " Where aa.planGroupNo = bb.planGroupNo\r\n" + "   and aa.planTyp = 'SI'\r\n"
					+ "   and aa.toyotoId = '0'\r\n" + "   and bb.activeId = '1'\r\n" + " and aa.shipNo =:shipNo\r\n"
					+ "   and aa.billNo =:billNo\r\n" + " order by bb.seqNo";
			QueryParamLs paramls2 = new QueryParamLs();
			// paramLs.addParam("shipNo", shipNo);
			paramls2.addParam("billNo", billNo);
			paramls2.addParam("shipNo", shipNo);
			List<Object> testList = JpaUtils.findAll(jpql0, paramls2);
			if (testList.size() > 0) {
				String jpql1 = "Select bb.cyAreaNo\r\n" + "  from PlanGroup aa, PlanRange bb\r\n"
						+ " Where aa.planGroupNo = bb.planGroupNo\r\n" + "   and aa.planTyp = 'SI'\r\n"
						+ "   and aa.toyotoId = '0'\r\n" +
						// " and bb.activeId = '1'\r\n" +
						" and aa.shipNo =:shipNo\r\n" + "   and aa.billNo =:billNo\r\n" + " order by bb.seqNo";
				List<Map<String, Object>> planList = JpaUtils.getEntityManager().createQuery(jpql1)
						.setParameter("shipNo", shipNo).setParameter("billNo", billNo)
						.setHint(QueryHints.RESULT_TYPE, ResultType.Map).getResultList();
				if (planList != null && planList.size() > 0) {
					for (Map<String, Object> map : planList) {
						result = result + map.get("cyAreaNo") + ",";
					}
					if (result.length() - 1 > 12) {
						result = result.substring(0, result.length() - 1).substring(0, 11);
					} else {
						result = result.substring(0, result.length() - 1);
					}
				}
			}
		}
		return result;
	}
	@Override
	public List<WorkCommand> checkVinRfid(String vin, String rfid) {
		// TODO Auto-generated method stub
		String jpql = "select a from WorkCommand a where a.workTyp = 'SI' and (a.rfidCardNo =:rfid or a.vinNo =:vin) ";
		QueryParamLs paramLs = new QueryParamLs();
		paramLs.addParam("vin", vin);
		paramLs.addParam("rfid", rfid);
		List<WorkCommand> checkList = JpaUtils.findAll(jpql, paramLs);
		return checkList;
	}
	@Override
	public String checkcyPlac(String cyAreaNo, String cyRowNo, String cyBayNo, String shipNo) {
		String cyPlac = cyAreaNo.substring((cyAreaNo.length() - 2), cyAreaNo.length()) + cyRowNo + cyBayNo;
		String checkKey = "0";
		String jpql = "select a from CCyBay a where a.cyPlac=:cyPlac";
		QueryParamLs paramLs = new QueryParamLs();
		paramLs.addParam("cyPlac", cyPlac);
		List<CCyBay> checkList = JpaUtils.findAll(jpql, paramLs);
		if (checkList.size() > 0) {
			String jpql2 = "select a from Ship a where a.shipNo =:shipNo ";
			QueryParamLs paramLs2 = new QueryParamLs();
			paramLs2.addParam("shipNo", shipNo);
			List<Ship> dockCode = JpaUtils.findAll(jpql2, paramLs2);
			String jpql3 = "select a.dockCod as data from CCyArea a, CCyBay b where a.cyAreaNo = b.cyAreaNo and b.cyRowNo=:cyRowNo and b.cyBayNo=:cyBayNo and b.cyAreaNo=:cyAreaNo";
			List<Map<String, Object>> dockCode2 = JpaUtils.getEntityManager().createQuery(jpql3)
					.setParameter("cyRowNo", cyRowNo).setParameter("cyBayNo", cyBayNo)
					.setParameter("cyAreaNo", cyAreaNo).setHint(QueryHints.RESULT_TYPE, ResultType.Map).getResultList();
			if (CollectionUtils.isEmpty(dockCode2) || dockCode.size() <= 0) {
				checkKey = "2";
				return checkKey;
			} else if (!dockCode2.get(0).get("data").equals(dockCode.get(0).getDockCod())) {
				// 船舶停靠码头与堆场位置不符
				checkKey = "2";
				return checkKey;
			}
		} else {
			// 特殊场位
			String jpql4 = "select a from CSpecPlac a where a.specPlac=:cyPlac";
			QueryParamLs paramLs4 = new QueryParamLs();
			paramLs4.addParam("cyPlac", cyPlac);
			List<CCyBay> checkList4 = JpaUtils.findAll(jpql4, paramLs4);
			if (checkList4.size() > 0) {
				// 场位错误
				checkKey = "1";
			}
		}
		return checkKey;
	}
	@Override
	public String checkBayNo(String cyAreaNo, String cyRowNo, String cyBayNo, String vinNo) {
		String lengthes = "0";
		// 判断录入车位是否被锁
		String lockJpql = "SELECT c FROM CCyBay c where c.cyAreaNo=:cyAreaNo and c.cyRowNo=:cyRowNo and c.cyBayNo=:cyBayNo and c.lockId = '1'";
		QueryParamLs lockParams = new QueryParamLs();
		lockParams.addParam("cyAreaNo", cyAreaNo);
		lockParams.addParam("cyRowNo", cyRowNo);
		lockParams.addParam("cyBayNo", cyBayNo);
		List<CCyBay> cCyBaylock = JpaUtils.findAll(lockJpql, lockParams);
		if (cCyBaylock.size() > 0) {
			lengthes = "2";
			return lengthes;
		}
		// 判断录入多余场位是否重复
		String pcJpql = "SELECT p FROM PortCar p where p.cyAreaNo=:cyAreaNo and p.cyRowNo=:cyRowNo and p.cyBayNo=:cyBayNo";
		QueryParamLs pcParams = new QueryParamLs();
		pcParams.addParam("cyAreaNo", cyAreaNo);
		pcParams.addParam("cyRowNo", cyRowNo);
		pcParams.addParam("cyBayNo", cyBayNo);
		List<PortCar> pcLists = JpaUtils.findAll(pcJpql, pcParams);
		if (pcLists.size() > 0) {
			lengthes = "2";
			return lengthes;
		}
		// 判断是否为空车位
		String jpql = "SELECT c FROM CCyBay c where c.cyAreaNo=:cyAreaNo and c.cyRowNo=:cyRowNo and c.cyBayNo=:cyBayNo and c.lockId = '0'";
		QueryParamLs Params = new QueryParamLs();
		Params.addParam("cyAreaNo", cyAreaNo);
		Params.addParam("cyRowNo", cyRowNo);
		Params.addParam("cyBayNo", cyBayNo);
		List<CCyBay> cCyBayList = JpaUtils.findAll(jpql, Params);
		if (cCyBayList.size() < 1) {
			lengthes = "1";
		}
		return lengthes;
	}
	@Override
	@Transactional
	public String nmshipUnloaderdc(String req) {
		System.out.println(req);
		JSONObject jobject = JSONObject.fromObject(req);
		String result = "true";
		// 读卡
		if (!(jobject.get("rfid") == null || jobject.get("rfid").toString().equals(""))) {
			String jpqlbc = "select a from PortCar a where a.rfidCardNo =:rfid";
			QueryParamLs bcParamLs = new QueryParamLs();
			bcParamLs.addParam("rfid", jobject.get("rfid").toString());
			List<PortCar> bcList = JpaUtils.findAll(jpqlbc, bcParamLs);
			if (bcList.size() > 0) {
				if (bcList.get(0).getCurrentStat().toString().equals("2")
						|| bcList.get(0).getCurrentStat().toString().equals("3")) {
					// 当前此RFID号是否已经在场，不能重复卸船。停止继续操作
					result = "rfidError";
				} else {
					if (!(jobject.get("vinNo") == null || jobject.get("vinNo").toString().equals(""))) {
						String jpqlpc = "select a from PortCar a where a.vinNo =:vinNo";
						QueryParamLs pcParamLs = new QueryParamLs();
						pcParamLs.addParam("vinNo", jobject.get("vinNo").toString());
						List<PortCar> pcList = JpaUtils.findAll(jpqlpc, pcParamLs);
						if (pcList.size() > 0) {
							if (pcList.get(0).getCurrentStat().toString().equals("2")
									|| pcList.get(0).getCurrentStat().toString().equals("3")) {
								// 当前rfid已经在场，不能重复卸船。停止继续操作
								result = "vinError";
							} else {
								if (String.valueOf(nmunloadShip(jobject)).equals("false")) {
									result = "finished";
								}
							}
						}
					}
				}
			} else {
				if (String.valueOf(nmunloadShip(jobject)).equals("false")) {
					result = "finished";
				}
			}
		} else {
			// 扫码
			if (!(jobject.get("vinNo") == null || jobject.get("vinNo").toString().equals(""))) {
				String vin1 = jobject.get("vinNo").toString();
				
				String jpqlbc = "select a from PortCar a where a.vinNo =:vinNo";
				QueryParamLs bcParamLs = new QueryParamLs();
				bcParamLs.addParam("vinNo", jobject.get("vinNo").toString());
				List<PortCar> bcList1 = JpaUtils.findAll(jpqlbc, bcParamLs);
				if (bcList1.size() > 0) {
					if (bcList1.get(0).getCurrentStat().toString().equals("2")
							|| bcList1.get(0).getCurrentStat().toString().equals("3")) {
						// 当前车架号已经在场，不能重复卸船。停止继续操作
						result = "vinError";
						// break;
					} else {
						if (!(jobject.get("rfid") == null || jobject.get("rfid").toString().equals(""))) {
							String jpqlpc = "select a from PortCar a where a.rfidCardNo =:rfid";
							QueryParamLs pcParamLs = new QueryParamLs();
							pcParamLs.addParam("rfid", jobject.get("rfid").toString());
							List<PortCar> pcList = JpaUtils.findAll(jpqlpc, pcParamLs);
							if (pcList.size() > 0) {
								if (pcList.get(0).getCurrentStat().toString().equals("2")
										|| pcList.get(0).getCurrentStat().toString().equals("3")) {
									// 当前rfid已经在场，不能重复卸船。停止继续操作
									result = "rfidError";
									// break;
								} else {
									if (String.valueOf(nmunloadShip(jobject)).equals("false")) {
										result = "finished";
										// break;
									}
								}
							} else if (String.valueOf(nmunloadShip(jobject)).equals("false")) {
								unloadShip(jobject);
								result = "finished";
							}
						}
					}
				} else {
					if (String.valueOf(nmunloadShip(jobject)).equals("false")) {
						result = "finished";
						// break;
					}
				}
			}
		}
		return result;
	}
	@Override
	public String checkLoadVinFt(String vinNo, String shipNo, String workTyp, String directId, String account, String port) {
		String errorMessage = "true";

		//校验流向
		String portFt = "SELECT c FROM CPortFt c where c.portId =:port";
		QueryParamLs portParams = new QueryParamLs();
		portParams.addParam("port", port);
		List<CPortFt> cPortFtList = JpaUtils.findAll(portFt, portParams);
		if(cPortFtList.size() > 0) {
			String jpql = "SELECT c FROM ShipInOutCheck c where c.vcVinNo =:vinNo and c.vcPort =:port";
			QueryParamLs paramsl = new QueryParamLs();
			paramsl.addParam("vinNo", vinNo);
			paramsl.addParam("port", cPortFtList.get(0).getVcPortId());
			List<ShipInOutCheck> shipInOutList = JpaUtils.findAll(jpql, paramsl);
			if(shipInOutList.size() > 0) {
				
			} else {
				if(directId.equals("1")) {
					errorMessage = "portError";
					return errorMessage;
				}else{
					
				}
			}
		}
		
		if(directId.equals("1")) {
			String portCarJpql = "SELECT p FROM PortCar p where p.vinNo =:vinNo and p.currentStat = '2'";
			QueryParamLs params = new QueryParamLs();
			params.addParam("vinNo", vinNo);
			List<PortCar> portCarList = JpaUtils.findAll(portCarJpql, params);
			if(portCarList.size() > 0) {
				errorMessage = "exsitError";
				return errorMessage;
			} else {
				String wcJpql = "SELECT w FROM WorkCommand w where w.vinNo=:vinNo and w.workTyp=:workTyp";
				QueryParamLs wcParams = new QueryParamLs();
				wcParams.addParam("vinNo", vinNo);
				wcParams.addParam("workTyp", workTyp);
				List<WorkCommand> wcLists = JpaUtils.findAll(wcJpql, wcParams);
				if(wcLists.size() > 0) {
					errorMessage = "finished";
					return errorMessage;
				}
//				//校验直装流向
//				String portFt = "SELECT c FROM CPortFt c where c.portId =:port";
//				QueryParamLs portParams = new QueryParamLs();
//				portParams.addParam("port", port);
//				List<CPortFt> cPortFtList = JpaUtils.findAll(portFt, portParams);
//				if(cPortFtList.size() > 0) {
//					String jpql = "SELECT c FROM ShipInOutCheck c where c.vcVinNo =:vinNo and c.vcPort =:port";
//					QueryParamLs paramsl = new QueryParamLs();
//					paramsl.addParam("vinNo", vinNo);
//					paramsl.addParam("port", cPortFtList.get(0).getVcPortId());
//					List<ShipInOutCheck> shipInOutList = JpaUtils.findAll(jpql, paramsl);
//					if(shipInOutList.size() > 0) {
//						
//					} else {
//						errorMessage = "portError";
//						return errorMessage;
//					}
//				}
				
				//直装向库存表里加数据
				Date sysDate = HdUtils.getDateTime();
				PortCar portEntity = new PortCar();
				portEntity.setVinNo(vinNo);
				portEntity.setRfidCardNo(vinNo);
				portEntity.setCurrentStat("2");
				portEntity.setBillNo("--");
				portEntity.setiEId("E");
				portEntity.setTradeId("1");
				portEntity.setShipNo(shipNo);
				portEntity.setInPortNo(shipNo);
				
				//车架号前八位解析品牌,车类,车型
				String substr8=vinNo.substring(0,8);
				String jpqla="select a from CCarVin a where a.vinNo=:vinNo ";
				QueryParamLs paramLsa = new QueryParamLs();
				paramLsa.addParam("vinNo",substr8);
				List<CCarVin> ccvList=JpaUtils.findAll(jpqla, paramLsa);
				if(ccvList.size()>0){
					CCarTyp cct=JpaUtils.findById(CCarTyp.class,ccvList.get(0).getCarTyp());
					String brandCod=cct.getBrandCod();
					String carTyp=cct.getCarTyp();
					String carKind = cct.getCarKind();
					if(HdUtils.strNotNull(carTyp) && HdUtils.strNotNull(carKind)){
						portEntity.setBrandCod(brandCod);
						portEntity.setCarTyp(carTyp);
						portEntity.setCarKind(carKind);
					}else{
						portEntity.setBrandCod("0121132701");//默认一汽丰田
						portEntity.setCarTyp("678");//丰田装 默认678，一汽丰田的
						portEntity.setCarKind("01");//默认轿车
					}
				}
				else{
					portEntity.setBrandCod("0121132701");//默认一汽丰田
					portEntity.setCarTyp("678");//丰田装 默认678，一汽丰田的
					portEntity.setCarKind("01");//默认轿车
				}
				portEntity.setDockCod("03406500");
				portEntity.setRemarks("手持直装");
				portEntity.setRecNam(account);
				portEntity.setRecTim((Timestamp) sysDate);
				JpaUtils.getBaseDao().save(portEntity);
				
			}
		} else {
			String portCarJpql = "SELECT p FROM PortCar p where p.vinNo =:vinNo and p.currentStat = '2'";
			QueryParamLs params = new QueryParamLs();
			params.addParam("vinNo", vinNo);
			List<PortCar> portCarList = JpaUtils.findAll(portCarJpql, params);
			if(portCarList.size() > 0) {
			} else {
				String wcJpql = "SELECT w FROM WorkCommand w where w.vinNo=:vinNo and w.workTyp=:workTyp";
				QueryParamLs wcParams = new QueryParamLs();
				wcParams.addParam("vinNo", vinNo);
				wcParams.addParam("workTyp", workTyp);
				List<WorkCommand> wcLists = JpaUtils.findAll(wcJpql, wcParams);
				if(wcLists.size() > 0) {
					errorMessage = "finished";
					return errorMessage;
				} else {
					errorMessage = "portCarError";
					return errorMessage;
				}
			}
		}
		
		String shipInOutJpql = "SELECT c FROM ShipInOutCheck c where c.vcVinNo =:vinNo and c.shipNo=:shipNo and c.workTyp=:workTyp";
		QueryParamLs shipParams = new QueryParamLs();
		shipParams.addParam("vinNo", vinNo);
		shipParams.addParam("shipNo", shipNo);
		shipParams.addParam("workTyp", workTyp);
		List<ShipInOutCheck> shipInOutCheckList = JpaUtils.findAll(shipInOutJpql, shipParams);
		if(shipInOutCheckList.size() > 0) {
			
		} else {
			errorMessage = "shipInOutCheckError";
		}
		return errorMessage;
	}
	@Override
	public String checkLoadport(String port, String vinNo) {
		String result = "true";
		
		String portFt = "SELECT c FROM CPortFt c where c.portId =:port";
		QueryParamLs portParams = new QueryParamLs();
		portParams.addParam("port", port);
		List<CPortFt> cPortFtList = JpaUtils.findAll(portFt, portParams);
		if(cPortFtList.size() > 0) {
			String jpql = "SELECT c FROM ShipInOutCheck c where c.vcVinNo =:vinNo and c.vcPort =:port";
			QueryParamLs params = new QueryParamLs();
			params.addParam("vinNo", vinNo);
			params.addParam("port", cPortFtList.get(0).getVcPortId());
			List<ShipInOutCheck> shipInOutList = JpaUtils.findAll(jpql, params);
			if(shipInOutList.size() > 0) {
				
			} else {
				result = "portError";
			}
		} else {
			result = "portError";
		}
		return result;
	}
	@Override
	public String checkLoadVinFtXC(String vinNo,String shipNo) {
		String errorMessage = "true";
		String shipInOutJpql = "SELECT c FROM ShipInOutCheck c where c.vcVinNo =:vinNo and c.shipNo=:shipNo and c.workTyp='SI'";
		QueryParamLs shipParams = new QueryParamLs();
		shipParams.addParam("vinNo", vinNo);
		shipParams.addParam("shipNo", shipNo);
		List<ShipInOutCheck> shipInOutCheckList = JpaUtils.findAll(shipInOutJpql, shipParams);
		if(shipInOutCheckList.size() > 0) {
			String cyPlace = (String) shipInOutCheckList.get(0).getVcGarage();
			if(HdUtils.strIsNull(cyPlace)) {
				errorMessage = "null";
			} else {
				errorMessage = cyPlace;
			}
		} else {
			String yardInJpql = "SELECT c FROM YardIn c where c.vcVinNo =:vinNo";
			QueryParamLs yardInParams = new QueryParamLs();
			yardInParams.addParam("vinNo", vinNo);
			List<YardIn> yardInList = JpaUtils.findAll(yardInJpql, yardInParams);
			if(yardInList.size() > 0) {
				errorMessage = "trueError";
			} else {
				errorMessage = "error";
			}
			
		}
		return errorMessage;
	}
	@Override
	public String checkVinNoShipNo(String vinNo, String shipNo) {
		String flag = "true";
		String jpql = "SELECT p FROM PortCar p where p.vinNo =:vinNo and p.shipNo=:shipNo and p.currentStat = '2'";
		QueryParamLs jpqlParams = new QueryParamLs();
		jpqlParams.addParam("vinNo", vinNo);
		jpqlParams.addParam("shipNo", shipNo);
		List<PortCar> portCarList = JpaUtils.findAll(jpql, jpqlParams);
		if(portCarList.size() > 0) {
			
		} else {
			flag = "false";
		}
		return flag;
	}
	@Override
	public String countCar(String shipNo, String workTyp, String vcPortID) {
		String num = "";
		// 计划数
		String shipInOutCheckJpql = "SELECT nvl(count(*),'0') as count FROM Ship_In_Out_Check c where c.ship_No='"
				+ shipNo + "' and c.work_Typ='" + workTyp + "'";
		List<Map<String, Object>> list = JpaUtils.getEntityManager().createNativeQuery(shipInOutCheckJpql)
				.setHint(QueryHints.RESULT_TYPE, ResultType.Map).getResultList();
		num = list.get(0).get("COUNT").toString();
		String wcJpql = "SELECT nvl(count(*),'0') as count FROM Work_Command w "
				+ " left join port_car t2 on t2.PORT_CAR_NO=w.PORT_CAR_NO "
				+ " left join c_port_ft t3 on t2.TRAN_PORT_COD = t3.port_cod"
				+ " where w.ship_No='" + shipNo+ "' and w.work_Typ='" + workTyp + "' and w.PDA_ID='1'";
		if(HdUtils.strNotNull(vcPortID)) {
			wcJpql+=" and t3.PORT_id='" + vcPortID + "'";
		}
		if("SI".equals(workTyp)) {
			wcJpql+=" and t2.brand_cod = '0121132550'";
		}else {
			wcJpql+=" and t2.brand_cod = '0121132701'";
		}
		List<Map<String, Object>> listWc = JpaUtils.getEntityManager().createNativeQuery(wcJpql)
				.setHint(QueryHints.RESULT_TYPE, ResultType.Map).getResultList();
		num = listWc.get(0).get("COUNT").toString() + "/" + num;
		return num;
	}
	/***
	 * 外贸装船
	 */
	@Override
	@Transactional	
	public String wmshipload(String req) {
		System.out.println(req);
		JSONObject jobject = JSONObject.fromObject(req);

		//直装
		if(jobject.getString("directId").equals("1")) {
			
			String rtCheck=checkDirectWmLoadShip(jobject);
			if(!rtCheck.equals("1")) {
				return rtCheck;
			}
			
			String getPortCar="SELECT SEQ_PORT_CAR_NO.NEXTVAL PORTCARNO  FROM DUAL";
			BigDecimal portCarNo=(BigDecimal) ((Map)JpaUtils.getEntityManager().createNativeQuery(getPortCar).setHint(QueryHints.RESULT_TYPE, ResultType.Map).getResultList().get(0)).get("PORTCARNO");
			WorkCommand workCommand = new WorkCommand();
			workCommand.setWorkQueueNo((String)jobject.get("workQueueNo"));
			workCommand.setRfidCardNo(jobject.getString("rfid"));
			workCommand.setVinNo(jobject.getString("vinNo"));
			workCommand.setPortCarNo(portCarNo);
			workCommand.setWorkTyp("SO");
		    workCommand.setBrandCod((String)jobject.get("brandCod"));	
			workCommand.setShipWorkTim(HdUtils.getDateTime());
			workCommand.setLengthOverId((String)jobject.get("lengthOverId"));
			workCommand.setUseMachId((String)jobject.get("useMachId"));
			workCommand.setUseWorkerId((String)jobject.get("useWorkerId"));
			workCommand.setNightId((String)jobject.get("nightId"));
			workCommand.setHolidayId((String)jobject.get("holidayId"));
			workCommand.setQueueId(HdUtils.genUuid());
			workCommand.setOutCyTim(HdUtils.getDateTime());
			workCommand.setOutCyNam((String)jobject.get("account"));
			workCommand.setShipNo((String)jobject.get("shipNo"));
			workCommand.setBillNo(jobject.getString("billNo"));
			
			//workCommand.setCyPlac(portCar.getCyPlac());
			workCommand.setCarKind(HdUtils.strNotNull(jobject.getString("carKind"))?jobject.getString("carKind"):"" );
			workCommand.setRemarks("手持录入");
			workCommand.setQzId(jobject.get("qzId")==null?"":jobject.get("qzId").toString());
			workCommand.setDirectId(jobject.getString("directId"));
			String lhFlag = (String) jobject.get("lhFlag");
			if("1".equals(lhFlag)) {
				workCommand.setVcexception("1");
			}
			PortCar portCar=new PortCar();
			BeanUtils.copyProperties(workCommand, portCar);
			portCar.setCurrentStat("5");
			portCar.setiEId("E");
			portCar.setTradeId(jobject.getString("tradeId"));
			portCar.setInPortNo(jobject.getString("shipNo"));
			String dockCod="";
			CEmployee bean = JpaUtils.findById(CEmployee.class,HdUtils.getCurUser().getAccount());
			if (bean != null) {
				if (CEmployee.TYP_GZ_01.equals(bean.getClassNo()) || CEmployee.TYP_GZ_02.equals(bean.getClassNo())
						|| CEmployee.TYP_GZ_03.equals(bean.getClassNo())
						|| CEmployee.TYP_GZ_04.equals(bean.getClassNo())
						|| CEmployee.TYP_GZ_05.equals(bean.getClassNo())) {
					dockCod = Ship.GZ;
				} else {
					dockCod = Ship.HQ;
				}
			}
			portCar.setRecNam(HdUtils.getCurUser().getAccount());
			portCar.setRecTim(new Timestamp(new Date().getTime()));
			portCar.setOutCyTim(new Timestamp(new Date().getTime()));
			if(portCar.getCarKind().equals("null"))portCar.setCarKind("");
			portCar.setDockCod(dockCod);
			JpaUtils.getBaseDao().save(portCar);
			JpaUtils.getBaseDao().save(workCommand);

		}else {
			String sql="select f_wz_vin_check('"+jobject.get("vinNo")+"','"+jobject.get("shipNo")+"') rs from dual";
			String result=(String) ((Map)JpaUtils.getEntityManager().createNativeQuery(sql).setHint(QueryHints.RESULT_TYPE, ResultType.Map).getResultList().get(0)).get("RS");
			if(!"ok".endsWith(result)) {
				return result;
			}
			
			String jpql="select a from PortCar a where a.vinNo=:vinNo and a.currentStat='2'";
			QueryParamLs paramLs=new QueryParamLs();
			paramLs.addParam("vinNo",jobject.get("vinNo"));
			List<PortCar> mList=JpaUtils.findAll(jpql, paramLs);
			if(mList.size()==0) {
				return "车辆不在场，不能装船";
			}
	        //更新port_car值
			PortCar portCar=mList.get(0);
			portCar.setCurrentStat("5");
			portCar.setOutCyTim(HdUtils.getDateTime());
//			portCar.setLengthOverId((String)jobject.get("lengthOverId"));
//			portCar.setBrandCod((String)jobject.get("brandCod"));
			portCar.setShipNo((String)jobject.get("shipNo"));
//			portCar.setUpdNam((String)jobject.get("account"));
			portCar.setUpdTim(HdUtils.getDateTime());
			JpaUtils.getBaseDao().update(portCar);		
			//向work_command中插入一条记录值
			WorkCommand workCommand = new WorkCommand();
			workCommand.setWorkQueueNo((String)jobject.get("workQueueNo"));
			String sRfidCardNo = portCar.getRfidCardNo();
			if(sRfidCardNo == null||"".equals(sRfidCardNo)){
				sRfidCardNo = "";
			}
			workCommand.setRfidCardNo(sRfidCardNo);
			workCommand.setVinNo(portCar.getVinNo().trim());
			workCommand.setPortCarNo(portCar.getPortCarNo());
			workCommand.setWorkTyp("SO");
			workCommand.setBillNo(jobject.getString("billNo"));
		    workCommand.setBrandCod((String)jobject.get("brandCod"));	
			workCommand.setCarTyp(portCar.getCarTyp());
			workCommand.setShipWorkTim(HdUtils.getDateTime());
//			workCommand.setFinishedId("0");
			workCommand.setLengthOverId((String)jobject.get("lengthOverId"));
			workCommand.setUseMachId((String)jobject.get("useMachId"));
			workCommand.setUseWorkerId((String)jobject.get("useWorkerId"));
			workCommand.setNightId((String)jobject.get("nightId"));
			workCommand.setHolidayId((String)jobject.get("holidayId"));
			workCommand.setQueueId(HdUtils.genUuid());
			workCommand.setOutCyTim(HdUtils.getDateTime());
			workCommand.setOutCyNam((String)jobject.get("account"));
			workCommand.setShipNo((String)jobject.get("shipNo"));
			workCommand.setCyPlac(portCar.getCyPlac());
			workCommand.setCarKind(portCar.getCarKind());
			workCommand.setRemarks("手持录入");
			workCommand.setQzId(jobject.get("qzId")==null?"":jobject.get("qzId").toString());
			workCommand.setDirectId(jobject.getString("directId"));
			String lhFlag = (String) jobject.get("lhFlag");
			if("1".equals(lhFlag)) {
				workCommand.setVcexception("1");
			}

			JpaUtils.getBaseDao().save(workCommand);		
		}

		return "ok";
	}
	/**
	 * 效验外贸装船直取
	 * @param jobject
	 * @return
	 */
	private String checkDirectWmLoadShip(JSONObject jobject) {
		String rtCheck="1";
		String sql="  select a  from ShipBill a where a.shipNo=:shipNo and a.billNo=:billNo and a.iEId='E'";
		QueryParamLs jpqlParams = new QueryParamLs();
		jpqlParams.addParam("billNo", jobject.getString("billNo"));
		jpqlParams.addParam("shipNo", jobject.getString("shipNo"));
		List<ShipBill> shipBillLst = JpaUtils.findAll(sql, jpqlParams);
		if(shipBillLst.size()==0) {
			rtCheck="没有提单信息！";
		}else {
			ShipBill shipBill=shipBillLst.get(0);
			if(HdUtils.strIsNull(shipBill.getCustomBillNo())||shipBill.getCustomBillNo().equals("0")) {
				rtCheck="海关未放行！";
			}else if(HdUtils.strIsNull(shipBill.getConfirmId())||shipBill.getConfirmId().equals("0")) {
				rtCheck="提单未确认！";
			}
		}
		return  rtCheck;
	}
	
	// 内贸卸船
	@Transactional
	public String nmunloadShip(JSONObject jobject) {
		String Account = (String)jobject.get("account");
		String flag = "true";
		String jpql = "select a from WorkQueue a where a.workTyp = 'SI' and a.shipNo =:shipNo";
		QueryParamLs paramLs = new QueryParamLs();
		paramLs.addParam("shipNo", jobject.get("shipNo").toString());
		List<WorkQueue> workQueueList = JpaUtils.findAll(jpql, paramLs);
		if (workQueueList.size() < 0) {
			// throw new HdRunTimeException("不存在卸船作业队列！");
			flag = "false";
			return flag;
		} else {
			String dockCod = "";
			String billNo = "";
			Ship ship = JpaUtils.findById(Ship.class, jobject.get("shipNo").toString());
			if (HdUtils.strNotNull(ship.getShipCodId())) {
				CShipData cShipData = JpaUtils.findById(CShipData.class, ship.getShipCodId());
				if (HdUtils.strNotNull((String)jobject.get("brandCod"))) {
					CBrand cBrand = JpaUtils.findById(CBrand.class, jobject.get("brandCod").toString());
					billNo = cShipData.getShipShort() + " " + ship.getIvoyage() + cBrand.getBrandShort();
				} else {
					flag = "false";
					return flag;
					// throw new HdRunTimeException("请输入品牌信息！");
				}
			}
			if (HdUtils.strNotNull(Account)) {
				CEmployee bean = JpaUtils.findById(CEmployee.class, Account);
				if (bean != null) {
					if (CEmployee.TYP_GZ_01.equals(bean.getClassNo()) || CEmployee.TYP_GZ_02.equals(bean.getClassNo())
							|| CEmployee.TYP_GZ_03.equals(bean.getClassNo())
							|| CEmployee.TYP_GZ_04.equals(bean.getClassNo())
							|| CEmployee.TYP_GZ_05.equals(bean.getClassNo())) {
						dockCod = Ship.GZ;
					} else {
						dockCod = Ship.HQ;
					}
				} else {
					flag = "false";
					return flag;
					// throw new HdRunTimeException("理货员信息不完善！");
				}
			}
			ShipBill shipBill = new ShipBill();
			if (HdUtils.strNotNull(jobject.get("shipNo").toString())) {
				String jpql1 = "select a from ShipBill a where a.iEId = 'I' ";
				QueryParamLs query = new QueryParamLs();
				if (jobject.get("shipNo").toString() != null) {
					jpql1 += "and a.shipNo =:shipNo ";
					query.addParam("shipNo", jobject.get("shipNo").toString());
				}
				if (HdUtils.strNotNull(billNo)) {
					jpql1 += "and a.billNo =:billNo ";
					query.addParam("billNo", billNo);
				}
				if (HdUtils.strNotNull(dockCod)) {
					jpql1 += "and a.dockCod =:dockCod";
					query.addParam("dockCod", dockCod);
				}
				List<ShipBill> shipBillList = JpaUtils.findAll(jpql1, query);
				if (shipBillList.size() > 0) {
					shipBill = shipBillList.get(0);
					if (shipBill.getPieces() != null) {
						shipBill.setPieces(shipBill.getPieces().add(new BigDecimal("1")));
					}
					JpaUtils.getBaseDao().update(shipBill);
				} else {
					shipBill.setShipbillId(HdUtils.genUuid());
					shipBill.setShipNo(jobject.get("shipNo").toString());
					shipBill.setBillNo(billNo);
					shipBill.setiEId("I");
					shipBill.setTradeId(Ship.NM);
					shipBill.setBrandCod(jobject.get("brandCod").toString());
					shipBill.setPieces(new BigDecimal("1"));
					shipBill.setDockCod(dockCod);
					shipBill.setSendFlag("0");
					shipBill.setConfirmId("0");
					shipBill.setExitCustomId("0");
					shipBill.setRecNam(Account);
					shipBill.setRec2Tim(new Timestamp(new Date().getTime()));
					JpaUtils.save(shipBill);
				}
				if (HdUtils.strNotNull(billNo)) {
					PortCar portCar = new PortCar();
					//判断丰田
					String isFt = (String) jobject.get("isFT");
					if(jobject.get("cyBayNo")==null||HdUtils.strIsNull(jobject.get("cyBayNo").toString())) {
						String cyBayNo=wechatService.getCarLocation(jobject.get("cyAreaNo").toString());
						if(cyBayNo.equals("no")) {
							jobject.put("cyAreaNo","##");
							jobject.put("cyRowNo", "##");
							jobject.put("cyBayNo", "##");
						}else {
							jobject.put("cyRowNo", cyBayNo.split("-")[1]);
							jobject.put("cyBayNo", cyBayNo.split("-")[2]);
						}

					}
					if("true".equals(isFt)) {
						//解析场位
						String vcGarage = (String) jobject.get("vcGarage");
						if(HdUtils.strNotNull(vcGarage)) {
							if(vcGarage.indexOf("-") != -1) {
								portCar.setCyAreaNo("HQ"+vcGarage.split("-")[0]);
								portCar.setCyRowNo(vcGarage.split("-")[1]);
							}
						} else {
							portCar.setCyAreaNo(jobject.get("cyAreaNo").toString());
							portCar.setCyRowNo(jobject.get("cyRowNo").toString());
						}
					} else {
						portCar.setCyAreaNo(jobject.get("cyAreaNo").toString());
						portCar.setCyRowNo(jobject.get("cyRowNo").toString());
					}
					
					portCar.setiEId(Ship.JK);
					portCar.setTradeId(Ship.NM);
					portCar.setInPortNo(" ");
					String directId=jobject.getString("directId");
					if(HdUtils.strNotNull(directId)&&directId.equals("1")) {
						portCar.setCurrentStat("5");
						portCar.setOutCyTim(HdUtils.getDateTime());
					}else{
						portCar.setCurrentStat("2");
					}
					portCar.setBillNo(billNo);
					portCar.setShipNo(jobject.get("shipNo").toString());
					portCar.setInCyTim(HdUtils.getDateTime());
					portCar.setRfidCardNo(jobject.get("rfid").toString().trim());
					// 虚拟车架号生成规则
					portCar.setVinNo(jobject.get("vinNo").toString().trim());
					if (HdUtils.strNotNull((String)jobject.get("brandCod"))) {
						portCar.setBrandCod((String)jobject.get("brandCod"));
					}
					portCar.setLengthOverId((String)jobject.get("lengthOverId"));
					portCar.setBrandCod((String)jobject.get("brandCod"));
					if (HdUtils.strNotNull(String.valueOf(jobject.get("brandCod")))
							&& HdUtils.strNotNull(String.valueOf(jobject.get("carTyp")))) {
						String jpqlm = "select a from CCarTyp a where a.carTyp = :carTyp";
						QueryParamLs Paramsm = new QueryParamLs();
						String cc = jobject.get("carTyp").toString();
						Paramsm.addParam("carTyp", jobject.get("carTyp").toString());
						List<CCarTyp> ccartypList = JpaUtils.findAll(jpqlm, Paramsm);
						if (ccartypList.size() > 0) {
//								portCar.setCarTyp(ccartypList.get(0).getCarTyp());							
							portCar.setCarTyp(jobject.get("carTyp").toString());
							
							String carKind = (String) ccartypList.get(0).getCarKind();
							if(HdUtils.strNotNull(carKind)) {
								portCar.setCarKind(carKind);
							}
						} else {
							portCar.setCarTyp((String)jobject.get("carTyp"));
						}
					} else if (HdUtils.strIsNull(String.valueOf(jobject.get("brandCod")))
							&& HdUtils.strNotNull(String.valueOf(jobject.get("carTyp")))) {
						String jpqln = "select a from CCarTyp a where a.carTyp=:carTyp ";
						QueryParamLs paramsm = new QueryParamLs();
						paramsm.addParam("carTyp", jobject.get("carTyp"));
						List<CCarTyp> ccartypList = JpaUtils.findAll(jpqln, paramsm);
						if (ccartypList.size() > 0) {
							portCar.setCarTyp(ccartypList.get(0).getCarTyp());
							String carKind = (String) ccartypList.get(0).getCarKind();
							if(HdUtils.strNotNull(carKind)) {
								portCar.setCarKind(carKind);
							}
						} else {
							portCar.setCarTyp("");
						}
					} else {
						portCar.setCarTyp("");
					}
					portCar.setCyBayNo(jobject.get("cyBayNo").toString().trim());
					portCar.setCyPlac("###");
					portCar.setDockCod(dockCod);
					portCar.setRemarks("手持录入");
					portCar.setTranPortCod("CNTSN");
					portCar.setRecNam(Account);
					portCar.setRecTim(HdUtils.getDateTime());
					portCar.setUpdNam(Account);
					portCar.setUpdTim(HdUtils.getDateTime());
					if(jobject.getString("directId")=="1"){
						//portCar.setOutCyTim(HdUtils.getDateTime());
					}
					
					JpaUtils.getBaseDao().save(portCar);
					
					BillCar billCar = new BillCar();
					billCar.setBillcarId(HdUtils.genUuid());
					billCar.setShipbillId(shipBill.getShipbillId());
					billCar.setShipNo(jobject.get("shipNo").toString());
					billCar.setBillNo(billNo);
					billCar.setPortCarNo(portCar.getPortCarNo());
					billCar.setiEId(Ship.JK);
					billCar.setTradeId(Ship.NM);
					billCar.setBrandCod(portCar.getBrandCod());
					billCar.setCarKind(portCar.getCarKind());
					billCar.setCarTyp(portCar.getCarTyp());
					billCar.setLengthOverId(jobject.get("lengthOverId").toString());
					// 超长标志 1
					if ("1".equals(jobject.get("lengthOverId").toString())&&HdUtils.strNotNull((String)jobject.get("length"))) {
						billCar.setLength(new BigDecimal((String)jobject.get("length")));
					}
					billCar.setLhFlag("1");// 理货标志 1代表理完
					billCar.setRfidCardNo(portCar.getRfidCardNo().trim());
					billCar.setVinNo(portCar.getVinNo().trim());
					billCar.setRemarks("手持录入");
					billCar.setRecNam(Account);
					billCar.setRecTim(HdUtils.getDateTime());
					billCar.setUpdNam(Account);
					billCar.setUpdTim(HdUtils.getDateTime());
					JpaUtils.getBaseDao().save(billCar);
					
					WorkCommand workCommand = new WorkCommand();
					workCommand.setWorkQueueNo(workQueueList.get(0).getWorkQueueNo());
					workCommand.setRfidCardNo(portCar.getRfidCardNo().trim());
					workCommand.setVinNo(portCar.getVinNo().trim());
					workCommand.setPortCarNo(portCar.getPortCarNo());
					workCommand.setWorkTyp("SI");
					if (HdUtils.strNotNull(jobject.get("brandCod").toString())) {
						workCommand.setBrandCod(jobject.get("brandCod").toString());
					}
					workCommand.setCarTyp(portCar.getCarTyp());
					workCommand.setShipWorkTim(HdUtils.getDateTime());
					workCommand.setFinishedId("0");
					workCommand.setLengthOverId((String)jobject.get("lengthOverId"));
					workCommand.setUseMachId(jobject.get("useMachId").toString());
					workCommand.setUseWorkerId(jobject.get("useWorkerId").toString());
					workCommand.setNightId(jobject.get("nightId").toString());
					workCommand.setHolidayId(jobject.get("holidayId").toString());
					workCommand.setQueueId(HdUtils.genUuid());
					workCommand.setInCyTim(HdUtils.getDateTime());
					workCommand.setInCyNam(Account);
					workCommand.setShipNo(jobject.get("shipNo").toString());
					workCommand.setBillNo(billNo);
					workCommand.setCyPlac(portCar.getCyPlac());
					workCommand.setCarKind(portCar.getCarKind());
					workCommand.setRemarks("手持录入");
					workCommand.setQzId(jobject.get("qzId")==null?"":jobject.get("qzId").toString());
					String lhFlag = (String) jobject.get("lhFlag");
					if("1".equals(lhFlag)) {
						workCommand.setVcexception("1");
					}
					workCommand.setDirectId(jobject.getString("directId"));
					JpaUtils.getBaseDao().save(workCommand);
				}
			}
		}
		return flag;
	}
}