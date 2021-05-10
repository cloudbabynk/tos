package net.huadong.tech.wechat.serviceimpl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.persistence.config.QueryHints;
import org.eclipse.persistence.config.ResultType;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import net.huadong.tech.base.entity.CCarTyp;
import net.huadong.tech.base.entity.CCarVin;
import net.huadong.tech.base.entity.CCyArea;
import net.huadong.tech.base.entity.CCyBay;
import net.huadong.tech.base.entity.CPort;
import net.huadong.tech.base.entity.MBillVin;
import net.huadong.tech.cargo.entity.TruckWork;
import net.huadong.tech.damage.entity.CarDamage;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.tech.gate.entity.GateTruck;
import net.huadong.tech.gate.entity.GateTruckContract;
import net.huadong.tech.his.entity.GateTruckContractHis;
import net.huadong.tech.his.entity.GateTruckHis;
import net.huadong.tech.his.entity.TruckWorkHis;
import net.huadong.tech.inter.entity.ShipInOutCheck;
import net.huadong.tech.plan.entity.PlanGroup;
import net.huadong.tech.privilege.entity.AuthOrgn;
import net.huadong.tech.privilege.entity.SysCode;
import net.huadong.tech.ship.entity.Ship;
import net.huadong.tech.shipbill.entity.ContractIeDoc;
import net.huadong.tech.shipbill.entity.PortCar;
import net.huadong.tech.util.CommonUtil;
import net.huadong.tech.util.HdUtils;
import net.huadong.tech.wechat.service.SparseSetService;
import net.huadong.tech.wechat.service.WechatService;
import net.huadong.tech.work.entity.WorkCommand;
import net.huadong.tech.work.entity.WorkCommandBak;
import net.sf.json.JSONObject;

@Component
public class SparseSetServiceImpl implements SparseSetService {

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
	public String checkcyPlacIn(String cyAreaNo, String cyRowNo, String cyBayNo, String contractNo) {
		String cyPlac = cyAreaNo.substring((cyAreaNo.length() - 2), cyAreaNo.length()) + cyRowNo + cyBayNo;
		System.out.println(cyPlac);
		String checkKey = "0";
		String jpql = "select a from CCyBay a where a.cyPlac=:cyPlac";
		QueryParamLs paramLs = new QueryParamLs();
		paramLs.addParam("cyPlac", cyPlac);
		List<CCyBay> checkList = JpaUtils.findAll(jpql, paramLs);
		if (checkList.size() > 0) {
			// 场位有效
			// 验证当前车位是否是当前航次所在的码头的
			String jpql2 = "select a from ContractIeDoc a where a.contractNo =:contractNo ";
			QueryParamLs paramLs2 = new QueryParamLs();
			paramLs2.addParam("contractNo", contractNo);
			List<ContractIeDoc> dockCode = JpaUtils.findAll(jpql2, paramLs2);
			String jpql3 = "select a.dockCod as data from CCyArea a, CCyBay b where a.cyAreaNo = b.cyAreaNo and b.cyPlac =:cyPlac";
			List<Map<String, Object>> dockCode2 = JpaUtils.getEntityManager().createQuery(jpql3)
					.setParameter("cyPlac", cyPlac).setHint(QueryHints.RESULT_TYPE, ResultType.Map).getResultList();

			if (!dockCode2.get(0).get("data").toString().equals(dockCode.get(0).getDockCod().toString())) {
				// 船舶停靠码头与堆场位置不符
				checkKey = "2";
			}
		} else {
			// 特殊场位
			String jpql4 = "select a from CSpecPlac a where a.specPlac=:cyPlac";
			QueryParamLs paramLs4 = new QueryParamLs();
			paramLs4.addParam("cyPlac", cyPlac);
			List<CCyBay> checkList4 = JpaUtils.findAll(jpql4, paramLs4);
			if (checkList4.size() == 0) {
				// 场位错误
				checkKey = "1";
			}
		}
		return checkKey;
	}

	@Override
	public String checkVinIn(String vinNo) {
		String result = "0";
		String jpql = "select a from PortCar a where a.vinNo =:vin and a.currentStat='2'";
		QueryParamLs paramLs = new QueryParamLs();
		paramLs.addParam("vin", vinNo);
		List<PortCar> vinList = JpaUtils.findAll(jpql, paramLs);
		if (vinList.size() > 0) {
			// 表明此车架号已经在场，不可重复卸船
			result = "1";
		}
		return result;
	}
	
	@Override
	public String checkVinNum() {
		String result = "";
		String jpql = "select a from SysCode a where a.fieldCod =:fieldCod and a.code =:code";
		QueryParamLs paramLs = new QueryParamLs();
		paramLs.addParam("fieldCod", "VIN_NUM");
		paramLs.addParam("code", "1");
		List<SysCode> sList = JpaUtils.findAll(jpql, paramLs);
		if (sList.size() > 0) {
			SysCode data = sList.get(0);
			result = data.getName();
		}
		return result;
	}
	
	@Override
	public String checkBillVin(String vinNo, String contractNo) {
		String result = "";
		String jpql = "select a from MBillVin a where a.vinNo =:vinNo and a.iEId =:iEId";
		QueryParamLs paramLs = new QueryParamLs();
		paramLs.addParam("vinNo", vinNo);
		paramLs.addParam("iEId", "E");
		List<MBillVin> mBillVinList = JpaUtils.findAll(jpql, paramLs);
		if (mBillVinList.size() > 0) {
			MBillVin data = mBillVinList.get(0);
			result = data.getBillNo();
		}
		return result;
	}

	@Override
	public List<ContractIeDoc> billNum(String contractNo) {
		String jpql = "SELECT c FROM ContractIeDoc c where c.contractNo =:contractNo";
		QueryParamLs cParam = new QueryParamLs();
		cParam.addParam("contractNo", contractNo);
		List<ContractIeDoc> cList = JpaUtils.findAll(jpql, cParam);
		return cList;
	}

	@Transactional
	public String portloadoutplace(String req) {
		System.out.println(req);
		JSONObject jobject = JSONObject.fromObject(req);
		Date sysDate = HdUtils.getDateTime();
		String Account = String.valueOf(jobject.get("account"));
		String result = "true";
		
		String shipNoc = "";
		String contractNo = String.valueOf(jobject.get("contractNo"));
		ContractIeDoc contractIeDoc = JpaUtils.findById(ContractIeDoc.class, contractNo);
		if(HdUtils.strNotNull(contractIeDoc.getShipNo())) {
			shipNoc = contractIeDoc.getShipNo();
		}

		String rJpql = "select a from PortCar a where a.vinNo =:vinNo";
		QueryParamLs rParam = new QueryParamLs();
		rParam.addParam("vinNo", jobject.get("vinNo").toString());
		List<PortCar> pcList = JpaUtils.findAll(rJpql, rParam);
		if (pcList.size() > 0) {
			// 报错：车架号已经存在 停止
			result = "vinError";
		} else {
//			String rfJpql = "select a from PortCar a where a.rfidCardNo =:rfid";
//			QueryParamLs rfParam = new QueryParamLs();
//			rfParam.addParam("rfid", jobject.get("rfid").toString());
//			List<PortCar> pcfList = JpaUtils.findAll(rfJpql, rfParam);
//			if (pcfList.size() > 0) {
//				// 报错：rfid已经被使用 停止
//				result = "rfidError";
//			} else {
				// 生成在场车辆数据
				String cidJpql = "select c from ContractIeDoc c where c.contractNo =:contractNo";
				QueryParamLs ieParam = new QueryParamLs();
				ieParam.addParam("contractNo", jobject.get("contractNo").toString());
				List<ContractIeDoc> cidList = JpaUtils.findAll(cidJpql, ieParam);
				if (cidList.size() > 0) {
//					String cyAreaNo0 = String.valueOf(jobject.get("cyAreaNo"));
//					String cyRowNo0 = String.valueOf(jobject.get("cyRowNo"));
//					String cyBayNo0 = String.valueOf(jobject.get("cyBayNo"));
//					String cyplac0 = cyAreaNo0.subSequence(cyAreaNo0.length()-2, cyAreaNo0.length()) + cyRowNo0 + cyBayNo0;
					// 生成在场车辆数据
					PortCar portList = new PortCar();
					portList.setRfidCardNo((String)jobject.get("rfid"));
					portList.setVinNo(jobject.get("vinNo").toString());
					portList.setCurrentStat("2");
					if (jobject.get("billNo") != null) {
						portList.setBillNo((String)jobject.get("billNo"));
					} else {
						portList.setBillNo(cidList.get(0).getBillNo());
					}
					portList.setiEId("E");
					portList.setTradeId(cidList.get(0).getTradeId());
					portList.setConsignCod(cidList.get(0).getConsignCod());
					portList.setConsignNam(cidList.get(0).getConsignNam());
					portList.setContractNo(jobject.get("contractNo").toString());
					portList.setShipNo(cidList.get(0).getShipNo());
					portList.setBrandCod(String.valueOf(jobject.get("brandCod")));
					
					String tranPortCod = (String) jobject.get("vcPortId");
					String portFt = "SELECT c FROM CPort c where c.portId =:port";
					QueryParamLs portParams = new QueryParamLs();
					portParams.addParam("port", tranPortCod);
					List<CPort> cPortFtList = JpaUtils.findAll(portFt, portParams);
					if(cPortFtList.size() > 0) {
						String port = cPortFtList.get(0).getPortCod();
						portList.setTranPortCod(port.trim());
//						if(HdUtils.strNotNull(port)) {
//							portList.setTranPortCod(port.trim());
//						}
					}
					// 暂时不填
					// portList.setReceiveCod();
					// portList.setReceiveNam();
					if (jobject.get("carTyp") != null && !"".equals(jobject.get("carTyp"))) {
						portList.setCarTyp(String.valueOf(jobject.get("carTyp")));
						String carKindJpql = "SELECT c FROM CCarTyp c where c.carTyp =:carTyp";
						QueryParamLs carKindParams = new QueryParamLs();
						carKindParams.addParam("carTyp", String.valueOf(jobject.get("carTyp")));
						List<CCarTyp> CarKindList = JpaUtils.findAll(carKindJpql, carKindParams);
						String carKindP = "";
						if(CarKindList.size() > 0) {
							carKindP = CarKindList.get(0).getCarKind();
						}
						portList.setCarKind(carKindP);
					}
					if(jobject.get("cyBayNo")==null||HdUtils.strIsNull(jobject.get("cyBayNo").toString())) {
						String cyBayNo=wechatService.getCarLocation(jobject.get("cyAreaNo").toString());
						jobject.put("cyRowNo", cyBayNo.split("-")[1]);
						jobject.put("cyBayNo", cyBayNo.split("-")[2]);
					}
					portList.setCyAreaNo(CommonUtil.strIsEmpty(jobject.get("cyAreaNo")+"")?"##":jobject.getString("cyAreaNo"));
					portList.setCyRowNo(CommonUtil.strIsEmpty(jobject.get("cyRowNo")+"")?"##":jobject.getString("cyRowNo"));
					portList.setCyBayNo(CommonUtil.strIsEmpty(jobject.get("cyBayNo")+"")?"##":jobject.getString("cyBayNo"));
					
					if (jobject.get("damCod") == null || jobject.get("damCod").toString().equals("")) {

					} else {
						portList.setDamId("1");
						portList.setDamCod((String)jobject.get("damCod"));
						portList.setDamLevel((String)jobject.get("damLevel"));
						portList.setDamArea((String)jobject.get("damArea"));
					}
					// 暂时不填
					// portList.setLoadPortCod(cidList.get(0).getl);
					portList.setLengthOverId((String) jobject.get("lengthOverId"));
					portList.setDiscPortCod(cidList.get(0).getDiscPortCod());
					portList.setInToolNo(cidList.get(0).getContractNo());
					portList.setInPortNo(cidList.get(0).getContractNo());
					portList.setInCyTim((Timestamp) sysDate);
					portList.setDockCod(cidList.get(0).getDockCod());
					portList.setRemarks("手持录入");
					portList.setRecNam(Account);
					portList.setRecTim((Timestamp) sysDate);
					portList.setShipNo(shipNoc);
					JpaUtils.getBaseDao().save(portList);
					
					//该车位上锁
//					if(String.valueOf(jobject.get("bayCheck")).equals("0")) {
						if(String.valueOf(jobject.get("bayCheck")).equals("0")) {
//							if (String.valueOf(jobject.get("finished")).equals("0")) {
								String ccyBayJpql = "update CCyBay c set c.lockId = '1' where c.cyAreaNo=:cyAreaNo and c.cyBayNo=:cyBayNo and c.cyRowNo=:cyRowNo";
								QueryParamLs ccyBayParams = new QueryParamLs();
								ccyBayParams.addParam("cyAreaNo", (String)jobject.get("cyAreaNo"));
								ccyBayParams.addParam("cyBayNo",(String)jobject.get("cyBayNo"));
								ccyBayParams.addParam("cyRowNo", (String)jobject.get("cyRowNo"));
								JpaUtils.execUpdate(ccyBayJpql, ccyBayParams);
//							} 
//							else {
//								String ccyBayJpql = "update CCyBay c set c.lockId = '1' where c.cyAreaNo=:cyAreaNo and c.cyBayNo=:cyBayNo";
//								QueryParamLs ccyBayParams = new QueryParamLs();
//								ccyBayParams.addParam("cyAreaNo", String.valueOf(jobject.get("cyAreaNo")));
//								ccyBayParams.addParam("cyBayNo", String.valueOf(jobject.get("cyBayNo")));
//								JpaUtils.execUpdate(ccyBayJpql, ccyBayParams);
//							}
						}
//					}
				}
				// 生成指令记录(集港)
				String pclJpql = "select a from PortCar a where a.vinNo =:vinNo and a.contractNo =:contractNo";
				QueryParamLs pclParams = new QueryParamLs();
//				pclParams.addParam("rfid", jobject.get("rfid").toString());
				pclParams.addParam("vinNo", jobject.get("vinNo").toString());
				pclParams.addParam("contractNo", jobject.get("contractNo").toString());
				List<PortCar> pcarList = JpaUtils.findAll(pclJpql, pclParams);
				if (pcarList.size() > 0) {
//					String pcWorkQueueNo = String.valueOf(jobject.get("contractNo")) + "-"
//							+ String.valueOf(jobject.get("plantNo")) + "-" + "TI";
					String pcWorkQueueNo = String.valueOf(jobject.get("contractNo")) + "-" + "A" + String.valueOf(jobject.get("plantNo"));
//					String cyAreaNo0 = String.valueOf(jobject.get("cyAreaNo"));
//					String cyRowNo0 = String.valueOf(jobject.get("cyRowNo"));
//					String cyBayNo0 = String.valueOf(jobject.get("cyBayNo"));
//					String cyPlaceJg = cyAreaNo0.subSequence(cyAreaNo0.length()-2, cyAreaNo0.length()) + cyRowNo0 + cyBayNo0;
					
					WorkCommand wcList = new WorkCommand();
					wcList.setQueueId(HdUtils.genUuid());
					wcList.setWorkQueueNo(pcWorkQueueNo);
					wcList.setPortCarNo(pcarList.get(0).getPortCarNo());
					wcList.setRfidCardNo(jobject.get("rfid").toString());
					wcList.setVinNo(jobject.get("vinNo").toString());
					wcList.setContractNo(jobject.get("contractNo").toString());
					wcList.setBillNo(pcarList.get(0).getBillNo());
					wcList.setWorkTyp(String.valueOf(jobject.get("workTyp")));
					wcList.setBrandCod(pcarList.get(0).getBrandCod());
					if (jobject.get("carTyp") != null && !"".equals(jobject.get("carTyp"))) {
						wcList.setCarTyp(String.valueOf(jobject.get("carTyp")));
						String carKindJpql = "SELECT c FROM CCarTyp c where c.carTyp =:carTyp";
						QueryParamLs carKindParams = new QueryParamLs();
						carKindParams.addParam("carTyp", String.valueOf(jobject.get("carTyp")));
						List<CCarTyp> CarKindList = JpaUtils.findAll(carKindJpql, carKindParams);
						String carKindP = "";
						if(CarKindList.size() > 0) {
							carKindP = CarKindList.get(0).getCarKind();
						}
						wcList.setCarKind(carKindP);
					}
					wcList.setQzId(String.valueOf(jobject.get("qzId")));
					
					
					
//					wcList.setCarTyp(pcarList.get(0).getCarTyp());
//					wcList.setCarKind(pcarList.get(0).getCarKind());
					// 直装、直取暂时不放
					// wcList.setDirectId(directId);
					// wcList.setPlanPlac(planPlac);
//					wcList.setCyPlac(cyPlaceJg);
					wcList.setSendTim((Timestamp) sysDate);
					wcList.setSendNam(Account);
					wcList.setInCyTim((Timestamp) sysDate);
					wcList.setInCyNam(Account);
					wcList.setFinishedId("1");
					wcList.setUseMachId(jobject.get("useMachId").toString());
					wcList.setUseWorkerId(jobject.get("useWorkerId").toString());
					wcList.setRemarks("手持录入");
					wcList.setNightId((String) jobject.get("nightId"));
					wcList.setHolidayId((String) jobject.get("holidayId"));
					wcList.setLengthOverId((String) jobject.get("lengthOverId"));
					if (jobject.get("lengthOverId").toString().equals("1")) {
						BigDecimal overLength = new BigDecimal((String) jobject.get("lengthOverId"));
						wcList.setLength(overLength);
					}
					wcList.setShipNo(shipNoc);
					JpaUtils.getBaseDao().save(wcList);
				}
				// 生成闸口记录
				String zkJpql = "select a.ingateId, a.contractNo, a.truckNo, a.carryId, b.inGatTim, b.outGatTim, c.shipNo, c.iEId, c.voyage, a.billNo, c.tradeId,"
						+ " c.discPortCod, c.tranPortCod, c.carTyp, c.carKind, c.consignCod, b.inGatNo, b.inRecNam,　c.brand"
						+ " from GateTruckContract a, GateTruck b, ContractIeDoc c "
						+ " where a.ingateId = b.ingateId and a.contractNo = c.contractNo"
						+ " and a.ingateId =:ingateId and a.contractNo=:contractNo";
				List<Map<String, Object>> zkList = JpaUtils.getEntityManager().createQuery(zkJpql)
						.setParameter("ingateId", jobject.get("ingateId").toString())
						.setParameter("contractNo", jobject.get("contractNo").toString())
						.setHint(QueryHints.RESULT_TYPE, ResultType.Map).getResultList();			

				TruckWork twList = new TruckWork();
				twList.setIngateId(zkList.get(0).get("ingateId").toString());
				twList.setContractNo(zkList.get(0).get("contractNo").toString());
				twList.setTruckNo(zkList.get(0).get("truckNo").toString());
				twList.setCarryId(zkList.get(0).get("carryId").toString());
				twList.setCarryWay(String.valueOf(jobject.get("singleId")));
				twList.setInGatTim((Timestamp) zkList.get(0).get("inGatTim"));
				twList.setOutGatTim((Timestamp) zkList.get(0).get("outGatTim"));
				twList.setShipNo((String) zkList.get(0).get("shipNo"));
				// twList.setShipCod(shipCod);
				twList.setIEId(zkList.get(0).get("iEId").toString());
				
				String shipNo = (String) zkList.get(0).get("shipNo");
				if(HdUtils.strNotNull(shipNo)) {
					String shipJpql = "SELECT s FROM Ship s where s.shipNo =:shipNo";
					QueryParamLs shipParams = new QueryParamLs();
					shipParams.addParam("shipNo", shipNo);
					List<Ship> shipList = JpaUtils.findAll(shipJpql, shipParams);
					if(shipList.size() > 0) {
						String voy  = (String) zkList.get(0).get("voyage");
						if(HdUtils.strNotNull(voy)) {
							if(voy.equals("E")) {
								twList.setVoyage(shipList.get(0).getEvoyage());
							} else {
								twList.setVoyage(shipList.get(0).getIvoyage());
							}
						}
					}
				}
				
//				twList.setVoyage((String) zkList.get(0).get("voyage"));
				
				twList.setBillNo(pcarList.get(0).getBillNo());
				twList.setTradeId((String) zkList.get(0).get("tradeId"));
				twList.setDiscPortCod((String) zkList.get(0).get("discPortCod"));
				twList.setTranPortCod((String) zkList.get(0).get("tranPortCod"));

				twList.setPortCarNo(pcarList.get(0).getPortCarNo());
//				twList.setRfidCardNo(jobject.get("rfid").toString());
				twList.setVinNo(jobject.get("vinNo").toString());
				// twList.setNewVinNo(newVinNo);
				twList.setBrandCod((String) zkList.get(0).get("brand"));
				twList.setCarTyp(String.valueOf(jobject.get("carTyp")));
				twList.setCarKind(String.valueOf(jobject.get("carKind")));
//				twList.setCarTyp((String) zkList.get(0).get("carTyp"));
//				twList.setCarKind((String) zkList.get(0).get("carKind"));
				
				// twList.setMarks(marks);
				twList.setDamCod((String) jobject.get("damCod"));
				twList.setDamArea((String) jobject.get("damArea"));
				// twList.setDirectId(directId);
				twList.setConsignCod((String) jobject.get("consignCod"));
				// twList.setCrgAgent(crgAgent);
				// twList.setCyPlac(jobject.get("cyPlac").toString());
				twList.setWorkTim((Timestamp) sysDate);
				twList.setWorkNam(Account);
				twList.setInGateNo((String) zkList.get(0).get("inGatNo"));
				twList.setInRecNam((String) zkList.get(0).get("inRecNam"));
				twList.setRemarkTxt("手持录入");
				JpaUtils.getBaseDao().save(twList);
				// 如果残损信息不为空，更新port_car信息
				if (jobject.get("damCod") == null || jobject.get("damCod").equals("")) {
				} else {
					String jpqlup = " update PortCar a set a.damId='1' , a.damLevel=:damLevel , a.damArea=:damArea , a.damCod=:damCod , a.updNam=:updNam , a.updTim=:updTim where a.vinNo=:vinNo ";
					QueryParamLs paramLsup = new QueryParamLs();
					paramLsup.addParam("damArea", (String)jobject.get("damArea"));
					paramLsup.addParam("damLevel", (String)jobject.get("damLevel"));
					paramLsup.addParam("damCod", (String)jobject.get("damCod"));
					paramLsup.addParam("updNam", Account);
					paramLsup.addParam("updTim", sysDate);
					paramLsup.addParam("vinNo", jobject.get("vinNo").toString());
					JpaUtils.execUpdate(jpqlup, paramLsup);
					CarDamage cdlist = new CarDamage();
					cdlist.setCardamagId(HdUtils.genUuid());
					cdlist.setDamArea((String)jobject.get("damArea"));
					cdlist.setPortCarNo(pcarList.get(0).getPortCarNo());
					cdlist.setDamCod((String)jobject.get("damCod"));
					cdlist.setDamLevel((String)jobject.get("damLevel"));
					cdlist.setRecNam(Account);
					String orgnId = HdUtils.getCurUser().getOrgnId();
					String authOrgnJpql = "select t from AuthOrgn t where t.orgnId=:orgnId";
					QueryParamLs authOrgnParams = new QueryParamLs();
					authOrgnParams.addParam("orgnId", orgnId);
					List<AuthOrgn> authOrgnList = JpaUtils.findAll(authOrgnJpql, authOrgnParams);
					if(authOrgnList.size() > 0) {
						cdlist.setRegPost(authOrgnList.get(0).getName());
					} else {
						cdlist.setRegPost(Account);
					}
					cdlist.setRecTim((Timestamp) sysDate);
					cdlist.setVinNo(jobject.get("vinNo").toString());
					cdlist.setIncharge((String) jobject.get("incharge"));
					JpaUtils.getBaseDao().save(cdlist);
				}
				// 修改作业量
				String fgtJpql = "select a from GateTruckContract a where a.ingateId =:ingateId and a.contractNo =:contractNo";
				QueryParamLs fgtcParams = new QueryParamLs();
				fgtcParams.addParam("ingateId", jobject.get("ingateId").toString());
				fgtcParams.addParam("contractNo", jobject.get("contractNo").toString());
				List<GateTruckContract> fgtcList = JpaUtils.findAll(fgtJpql, fgtcParams);
				if (fgtcList.size() > 0) {
					String gtcJpql = "update GateTruckContract a set a.workNum =:workNum where a.ingateId =:ingateId and a.contractNo =:contractNo";
					QueryParamLs gtcParams = new QueryParamLs();
					BigDecimal bignum1 = new BigDecimal("1");
					gtcParams.addParam("workNum", fgtcList.get(0).getWorkNum().add(bignum1));
					gtcParams.addParam("ingateId", jobject.get("ingateId").toString());
					gtcParams.addParam("contractNo", jobject.get("contractNo").toString());
					JpaUtils.execUpdate(gtcJpql, gtcParams);
				}
			}
//		}
		return result;
	}

	@Transactional
	public String finished(String req) {
		JSONObject jobject = JSONObject.fromObject(req);
		String Account = (String) jobject.get("account");
		String result = "true";
		// 拖车作业完所有的手续，点击，对闸口数据进行标识
		String gtJpql = "update GateTruck a set a.finishedId = '1', a.finishedOper =:finishedOper where a.ingateId =:ingateId";
		QueryParamLs gtParams = new QueryParamLs();
		gtParams.addParam("finishedOper", Account);
		gtParams.addParam("ingateId", jobject.get("ingateId"));
		JpaUtils.execUpdate(gtJpql, gtParams);

		// 针对单一业务的集港车辆， 完工确认时可以堆场作业完毕之后直接放行出闸
		String gtcJpql = "select count(distinct a.carryId) as data from GateTruckContract a where a.ingateId =:ingateId";
		List<Map<String, Object>> gtcList = JpaUtils.getEntityManager().createQuery(gtcJpql)
				.setParameter("ingateId", jobject.get("ingateId")).setHint(QueryHints.RESULT_TYPE, ResultType.Map)
				.getResultList();
		if (gtcList.size() > 0) {
			if (gtcList.get(0).get("data") != null) {
				if (Integer.parseInt(String.valueOf(gtcList.get(0).get("data"))) > 1) {
					// 先集港后疏港，不能直接出闸放行
					result = "portError";
				} else {
					// 仅有单一的集港业务，提示是否进行直接放行，有操作人员来判断是否直接出闸
					String gtbJpql = "select a from GateTruck a where a.ingateId =:ingateId";
					QueryParamLs gtbParams = new QueryParamLs();
					gtbParams.addParam("ingateId", String.valueOf(jobject.get("ingateId")));
					List<GateTruck> gtbList = JpaUtils.findAll(gtbJpql, gtbParams);
					if (gtbList.size() > 0) {
						GateTruckHis gthList = new GateTruckHis();
						GateTruck gtList = gtbList.get(0);
						BeanUtils.copyProperties(gtList, gthList);
						JpaUtils.getBaseDao().save(gthList);

						// 先删外键 TruckWork
						String truckWorkJpql = "SELECT t FROM TruckWork t where t.ingateId=:ingateId";
						QueryParamLs truckWorkParams = new QueryParamLs();
						truckWorkParams.addParam("ingateId", String.valueOf(jobject.get("ingateId")));
						List<TruckWork> truckWorkList = JpaUtils.findAll(truckWorkJpql, truckWorkParams);

						// 先删外键 GateTruckContract
						String gateTruckContractJpql = "SELECT g FROM GateTruckContract g where g.ingateId=:ingateId";
						QueryParamLs gateTruckContractParams = new QueryParamLs();
						gateTruckContractParams.addParam("ingateId", String.valueOf(jobject.get("ingateId")));
						List<GateTruckContract> gateTruckContractList = JpaUtils.findAll(gateTruckContractJpql,
								gateTruckContractParams);

						if (truckWorkList.size() > 0 || gateTruckContractList.size() > 0) {
							if (truckWorkList.size() > 0 && gateTruckContractList.size() > 0) {
								TruckWorkHis truckWorkHisList = new TruckWorkHis();
								BeanUtils.copyProperties(truckWorkList.get(0), truckWorkHisList);
								JpaUtils.getBaseDao().save(truckWorkHisList);
								JpaUtils.remove(truckWorkList.get(0));

								GateTruckContractHis gateTruckContractHisList = new GateTruckContractHis();
								BeanUtils.copyProperties(gateTruckContractList.get(0), gateTruckContractHisList);
								JpaUtils.getBaseDao().save(gateTruckContractHisList);
								JpaUtils.remove(gateTruckContractList.get(0));

								JpaUtils.remove(gtList);

							} else if (truckWorkList.size() > 0) {
								TruckWorkHis truckWorkHisList = new TruckWorkHis();
								TruckWork tkList = truckWorkList.get(0);
								BeanUtils.copyProperties(tkList, truckWorkHisList);
								JpaUtils.getBaseDao().save(truckWorkHisList);
								JpaUtils.remove(truckWorkList.get(0));
								JpaUtils.remove(gtList);
							} else if (gateTruckContractList.size() > 0) {
								GateTruckContractHis gateTruckContractHisList = new GateTruckContractHis();
								BeanUtils.copyProperties(gateTruckContractList.get(0), gateTruckContractHisList);
								JpaUtils.getBaseDao().save(gateTruckContractHisList);
								JpaUtils.remove(gateTruckContractList.get(0));
								JpaUtils.remove(gtList);
							}
						} else {
							JpaUtils.remove(gtList);
						}

					}

					String wcbJpql = "select a from WorkCommand a where a.truckNo =:truckNo";
					QueryParamLs wcbParams = new QueryParamLs();
					wcbParams.addParam("truckNo", jobject.get("truckNo"));
					List<WorkCommand> wcList = JpaUtils.findAll(wcbJpql, wcbParams);
					if (wcList.size() > 0) {
						WorkCommandBak wcbList = new WorkCommandBak();
						WorkCommand workcList = wcList.get(0);
						BeanUtils.copyProperties(workcList, wcbList);
						JpaUtils.getBaseDao().save(wcbList);
						JpaUtils.remove(workcList);
					}
				}
			}
		}
		return result;
	}

	@Override
	public String checkshgRfid(String rfid, String billNo) {
		String result = "";
		String pcJpql = "select a from PortCar a where a.rfidCardNo =:rfid";
		QueryParamLs pcParamLs = new QueryParamLs();
		pcParamLs.addParam("rfid", rfid);
		List<PortCar> pcList = JpaUtils.findAll(pcJpql, pcParamLs);
		if (pcList.size() > 0) {
			if (pcList.get(0).getCurrentStat().toString().equals("2")) {
				// 此车在场，继续
				if (pcList.get(0).getBillNo().toString().equals(billNo)) {
					// 提单号也匹配，继续
					result = pcList.get(0).getVinNo() + "&" + pcList.get(0).getCyPlac();
				} else {
					// 报错：场地中车辆的提单号与委托不符，不能疏港。停止运行
					result = "billNoError";
				}
			} else {
				// 报错：RFID不存在，此车不在场。停止运行
				result = "rfidError";
			}
		}
		return result;
	}

	@Override
	public String checkcyPlacshg(String cyPlac, String contractNo) {
		String result = "";
		String cidJpql = "select a from  ContractIeDoc a where a.contractNo =:contractNo";
		QueryParamLs cidParam = new QueryParamLs();
		cidParam.addParam("contractNo", contractNo);
		List<ContractIeDoc> cidList = JpaUtils.findAll(cidJpql, cidParam);
		String tJpql = "select a.dockCod from CCyArea a, CCyBay b where a.cyAreaNo = b.cyAreaNo and b.cyPlac =:cyPlac";
		List<Map<String, Object>> abList = JpaUtils.getEntityManager().createQuery(tJpql).setParameter("cyPlac", cyPlac)
				.setHint(QueryHints.RESULT_TYPE, ResultType.Map).getResultList();
		if (cidList.size() > 0 && abList.size() > 0) {
			if (cidList.get(0).getDockCod().toString().equals(abList.get(0).get("dockCod"))) {
				result = "true";
			} else {
				// 报错：当前堆场与预约的堆场不符 停止
				result = "error";
			}
		}
		return result;
	}

	@Override
	public String checkVinsg(String vinNo, String billNo) {
		String result = "true";
		String pcJpql = "select a from PortCar a where a.vinNo =:vinNo and a.currentStat = '2'";
		QueryParamLs pcParamLs = new QueryParamLs();
		pcParamLs.addParam("vinNo", vinNo);
		List<PortCar> pcList = JpaUtils.findAll(pcJpql, pcParamLs);
		if (pcList.size() == 1) {
//			if (pcList.get(0).getCurrentStat().toString().equals("2")) {
//				// 此车辆已经在场
//				if (pcList.get(0).getBillNo().toString().equals(billNo)) {
//					// 提单号也匹配，继续
//					result = pcList.get(0).getRfidCardNo() + "&" + pcList.get(0).getCyPlac();
//				} else {
//					// 报错：场地中车辆的提单号与委托不符，不能疏港。停止运行
//					result = "billNoError";
//				}
//			} else {
//				result = "billNoError";
//			}
		} else {
			result = "billNoError";
		}
		return result;
	}

	@Transactional
	public String shiploadershg(String req) {
		System.out.println(req);
		JSONObject jobject = JSONObject.fromObject(req);
		Date sysDate = HdUtils.getDateTime();
		String Account =(String) jobject.get("account");
//		String port = HdUtils.getCurUser().getOrgnName();
		// 先校验提单号、rfid不能为空 先校验rfid信息
		String result = "true";
		String pcJpql = "select a from PortCar a where a.vinNo =:vinNo";
		QueryParamLs pcParamLs = new QueryParamLs();
		pcParamLs.addParam("rfid", jobject.get("rfid").toString());
		List<PortCar> pcList = JpaUtils.findAll(pcJpql, pcParamLs);
		if (pcList.size() > 0) {
			if (!pcList.get(0).getCurrentStat().toString().equals("2")) {
				// 此车不在场，不能疏港，停止
				result = "portError";
			} else {
				if (pcList.get(0).getTradeId().toString().equals("1")
						&& pcList.get(0).getCustomId().toString().equals("0")) {
					// 报错：海关未放行，不能出场装船
					result = "seaError";
				} else {
					if (!(jobject.get("damCod") == null || jobject.get("damCod").toString().equals(""))) {
						String jpqlup = " update PortCar a set a.damId= '1' , a.damLevel=:damLevel , a.damArea=:damArea , a.damCod=:damCod , a.updNam=:updNam , a.updTim=:updTim where a.rfidCardNo=:rfid ";
						QueryParamLs paramLsup = new QueryParamLs();
						paramLsup.addParam("damArea", jobject.get("damArea").toString());
						paramLsup.addParam("damLevel", jobject.get("damLevel").toString());
						paramLsup.addParam("damCod", jobject.get("damCod").toString());
						paramLsup.addParam("updNam", Account);
						paramLsup.addParam("updTim", sysDate);
						paramLsup.addParam("rfid", jobject.get("rfid").toString());
						JpaUtils.execUpdate(jpqlup, paramLsup);

						// 获取当前车辆的流水号
						String jpqlpcar = "select a from PortCar a where a.billNo =:billNo and a.rfidCardNo =:rfid and a.vinNo =:vinNo";
						QueryParamLs pcarParams = new QueryParamLs();
						// pcarParams.addParam("shipNo", jobject.get("shipNo").toString());
						pcarParams.addParam("billNo", jobject.get("billNo").toString());
						pcarParams.addParam("rfid", jobject.get("rfid").toString());
						pcarParams.addParam("vinNo", jobject.get("vinNo").toString());
						List<PortCar> pcarList = JpaUtils.findAll(jpqlpcar, pcarParams);
						if (pcarList.size() > 0) {
							// 生成残损记录
							CarDamage cdlist = new CarDamage();
							cdlist.setCardamagId(HdUtils.genUuid());
							cdlist.setDamArea(jobject.get("damArea").toString());
							cdlist.setPortCarNo(pcarList.get(0).getPortCarNo());
							cdlist.setDamCod(jobject.get("damCod").toString());
							cdlist.setDamLevel(jobject.get("damLevel").toString());
							cdlist.setRecNam(Account);
							String orgnId = HdUtils.getCurUser().getOrgnId();
							String authOrgnJpql = "select t from AuthOrgn t where t.orgnId=:orgnId";
							QueryParamLs authOrgnParams = new QueryParamLs();
							authOrgnParams.addParam("orgnId", orgnId);
							List<AuthOrgn> authOrgnList = JpaUtils.findAll(authOrgnJpql, authOrgnParams);
							if(authOrgnList.size() > 0) {
								cdlist.setRegPost(authOrgnList.get(0).getName());
							} else {
								cdlist.setRegPost(Account);
							}
							cdlist.setRecTim((Timestamp) sysDate);
							cdlist.setVinNo(jobject.get("vinNo").toString());
							cdlist.setIncharge((String) jobject.get("incharge"));
							JpaUtils.getBaseDao().save(cdlist);
						}
					}
					// 更新port_car
					String jpqlBl = " update PortCar a set a.currentStat = '0' , set a.damId= '0' , a.cyPlac=:cyPlac , a.cyAreaNo=:cyAreaNo , a.cyRowNo=:cyRowNo , a.cyBayNo=:cyBayNo, a.outCyTim=:outCyTim, a.updNam=:updNam , a.updTim=:updTim where a.rfidCardNo=:rfid";
					QueryParamLs paramLBl = new QueryParamLs();
					paramLBl.addParam("cyPlac", "");
					paramLBl.addParam("cyAreaNo", "");
					paramLBl.addParam("cyRowNo", "");
					paramLBl.addParam("cyBayNo", "");
					paramLBl.addParam("outCyTim", sysDate);
					paramLBl.addParam("updNam", Account);
					paramLBl.addParam("updTim", sysDate);
					paramLBl.addParam("rfid", jobject.get("rfid").toString());
					JpaUtils.execUpdate(jpqlBl, paramLBl);
					
					String pclJpql = "select a from PortCar a where a.rfidCardNo =:rfid and a.vinNo =:vinNo";
					QueryParamLs pclParams = new QueryParamLs();
					pclParams.addParam("rfid", jobject.get("rfid").toString());
					pclParams.addParam("vinNo", jobject.get("vinNo").toString());
					List<PortCar> pcarList = JpaUtils.findAll(pclJpql, pclParams);
					if (pcarList.size() > 0) {
						//疏港-解锁车位
//						String arbJpql = "SELECT c FROM CCyBay c where c.cyAreaNo=:cyAreaNo and c.cyRowNo=:cyRowNo and c.cyBayNo=:cyBayNo";
//						QueryParamLs arbParams = new QueryParamLs();
//						arbParams.addParam("cyAreaNo", pcList.get(0).getCyAreaNo());
//						arbParams.addParam("cyRowNo", pcList.get(0).getCyRowNo());
//						arbParams.addParam("cyBayNo", pcList.get(0).getCyBayNo());
//						List<CCyBay> arbList = JpaUtils.findAll(arbJpql, arbParams);
//						if(arbList.size() > 0) {
//							CCyBay cCyBayEntity = arbList.get(0);
//							cCyBayEntity.setLockId("0");
//							JpaUtils.getBaseDao().update(cCyBayEntity);
//						}
						
						// 生成指令记录(疏港)
						String platNo = CommonUtil.getId().substring(0,8);
						String pcWorkQueueNo = String.valueOf(jobject.get("contractNo")) + "-"+ "T" +  platNo;
						WorkCommand wcList = new WorkCommand();
						wcList.setQueueId(HdUtils.genUuid());
						wcList.setWorkQueueNo(pcWorkQueueNo);
						wcList.setPortCarNo(pcarList.get(0).getPortCarNo());
//						wcList.setRfidCardNo(jobject.get("rfid").toString());
						wcList.setVinNo(jobject.get("vinNo").toString());
						wcList.setContractNo(jobject.get("contractNo").toString());
						wcList.setBillNo(jobject.get("vinNo").toString());
						wcList.setWorkTyp(String.valueOf(jobject.get("workTyp")));
						wcList.setBrandCod(pcarList.get(0).getBrandCod());
						wcList.setCarTyp(pcarList.get(0).getCarTyp());
						wcList.setCarKind(pcarList.get(0).getCarKind());
						// 直装、直取(暂时不填)
						// wcList.setDirectId(directId);
						// wcList.setPlanPlac(planPlac);
						wcList.setCyPlac(String.valueOf(jobject.get("cyPlac")));
						wcList.setSendTim((Timestamp) sysDate);
						wcList.setSendNam(Account);
						wcList.setInCyTim((Timestamp) sysDate);
						wcList.setInCyNamNam(Account);
						wcList.setFinishedId("1");
						wcList.setNightId(String.valueOf(jobject.get("nightId")));
						wcList.setHolidayId(String.valueOf(jobject.get("holidayId")));
						wcList.setUseMachId(jobject.get("useMachId").toString());
						wcList.setUseWorkerId(jobject.get("useWorkerId").toString());
						wcList.setRemarks("手持录入");
						wcList.setLengthOverId((String) jobject.get("lengthOverId"));
						if (jobject.get("lengthOverId").toString().equals("1")) {
							BigDecimal overLength = new BigDecimal((String) jobject.get("length"));
							wcList.setLength(overLength);
						}
						// wcList.setWidthOverId(widthOverId);
						JpaUtils.getBaseDao().save(wcList);

						// 生成闸口记录
						String zkJpql = "select a.ingateId, a.contractNo, a.truckNo, a.carryId, b.inGatTim, b.outGatTim, c.shipNo, c.iEId, c.voyage, a.billNo, c.tradeId,"
								+ " c.discPortCod, c.tranPortCod, c.carTyp, c.carKind, c.consignCod, b.inGatNo, b.inRecNam, c.brand"
								+ " from GateTruckContract a, GateTruck b, ContractIeDoc c "
								+ " where a.ingateId = b.ingateId and a.contractNo = c.contractNo"
								+ " and a.ingateId =:ingateId and a.contractNo=:contractNo";
						List<Map<String, Object>> zkList = JpaUtils.getEntityManager().createQuery(zkJpql)
								.setParameter("ingateId", jobject.get("ingateId").toString())
								.setParameter("contractNo", jobject.get("contractNo").toString())
								.setHint(QueryHints.RESULT_TYPE, ResultType.Map).getResultList();

						// SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						// Date inGatTim = null;
						// Date outGatTim = null;
						// try {
						// inGatTim = formatter.parse(zkList.get(0).get("inGatTim").toString());
						// outGatTim = formatter.parse(zkList.get(0).get("outGatTim").toString());
						// } catch (ParseException e) {
						// e.printStackTrace();
						// }

						TruckWork twList = new TruckWork();
						twList.setIngateId(String.valueOf(jobject.get("ingateId")));
						twList.setContractNo(zkList.get(0).get("contractNo").toString());
						twList.setTruckNo(zkList.get(0).get("truckNo").toString());
						twList.setCarryId(zkList.get(0).get("carryId").toString());
						twList.setInGatTim((Timestamp) zkList.get(0).get("inGatTim"));
						twList.setOutGatTim((Timestamp) zkList.get(0).get("outGatTim"));
						twList.setShipNo(zkList.get(0).get("shipNo").toString());
						twList.setCarryWay(String.valueOf(jobject.get("singleId")));
						// twList.setShipCod(shipCod);
						twList.setIEId((String) zkList.get(0).get("iEId"));
						twList.setVoyage((String) zkList.get(0).get("voyage"));
						twList.setBillNo((String) zkList.get(0).get("billNo"));
						twList.setTradeId((String) zkList.get(0).get("tradeId"));
						twList.setDiscPortCod((String) zkList.get(0).get("discPortCod"));
						twList.setTranPortCod((String) zkList.get(0).get("tranPortCod"));

						twList.setPortCarNo(pcarList.get(0).getPortCarNo());
						twList.setRfidCardNo(jobject.get("rfid").toString());
						twList.setVinNo(jobject.get("vinNo").toString());
						// twList.setNewVinNo(newVinNo);
						twList.setBrandCod((String) zkList.get(0).get("brand"));
						twList.setCarTyp((String) zkList.get(0).get("carTyp"));
						twList.setCarKind((String) zkList.get(0).get("carKind"));
						// twList.setMarks(marks);
						twList.setDamCod((String) jobject.get("damCod"));
						twList.setDamArea((String) jobject.get("damArea"));
						// twList.setDirectId(directId);
						twList.setConsignCod((String) jobject.get("consignCod"));
						// twList.setCrgAgent(crgAgent);
						twList.setCyPlac(jobject.get("cyPlac").toString());
						twList.setWorkTim((Timestamp) sysDate);
						twList.setWorkNam(Account);
						twList.setInGateNo((String) zkList.get(0).get("inGatNo"));
						twList.setInRecNam((String) zkList.get(0).get("inRecNam"));
						JpaUtils.getBaseDao().save(twList);
						
						//更新计划数作业数
						String gtcJpql = "SELECT g FROM GateTruckContract g where g.ingateId=:ingateId";
						QueryParamLs gtcParams = new QueryParamLs();
						gtcParams.addParam("ingateId", String.valueOf(jobject.get("ingateId")));
						List<GateTruckContract> gtcList = JpaUtils.findAll(gtcJpql, gtcParams);
						if(gtcList.size() > 0) {
							GateTruckContract gateTruckContractEntity = gtcList.get(0);
							BigDecimal bigDecimalAdd = new BigDecimal("1");
							gateTruckContractEntity.setWorkNum(gateTruckContractEntity.getWorkNum().add(bigDecimalAdd));
							JpaUtils.getBaseDao().update(gateTruckContractEntity);
						}
					}
					// 修改作业量
					String fgtJpql = "select a from GateTruckContract a where a.ingateId =:ingateId and a.contractNo =:contractNo";
					QueryParamLs fgtcParams = new QueryParamLs();
					fgtcParams.addParam("ingateId", jobject.get("ingateId").toString());
					fgtcParams.addParam("contractNo", jobject.get("contractNo").toString());
					List<GateTruckContract> fgtcList = JpaUtils.findAll(fgtJpql, fgtcParams);
					if (fgtcList.size() > 0) {
						String gtcJpql = "update GateTruckContract a set a.workNum =:workNum where a.ingateId =:ingateId and a.contractNo =:contractNo";
						QueryParamLs gtcParams = new QueryParamLs();
						BigDecimal bignum1 = new BigDecimal("1");
						gtcParams.addParam("workNum", fgtcList.get(0).getWorkNum().add(bignum1));
						gtcParams.addParam("ingateId", jobject.get("ingateId").toString());
						gtcParams.addParam("contractNo", jobject.get("contractNo").toString());
						JpaUtils.execUpdate(gtcJpql, gtcParams);
					}
				}
			}
		}
		return result;
	}

	@Override
	public String finishedworkshg(String ingateId,String account) {
		String result = "true";
		String upJpql = "update GateTruck a set a.finishedId = '1', a.finishedOper =:finishedOper where a.ingateId =:ingateId";
		QueryParamLs upParams = new QueryParamLs();
		upParams.addParam("ingateId", ingateId);
		upParams.addParam("finishedOper", account);
		JpaUtils.execUpdate(upJpql, upParams);
		return result;
	}

	@Override
	public Map<String, Object> defaultAreaRowBay(String billNo) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cyAreaNo", null);
		map.put("cyRowNo", null);
		map.put("cyBayNo", null);
		String planGroupJpql = "SELECT p FROM PlanGroup p where p.billNo=:billNo";
		QueryParamLs planGroupParams = new QueryParamLs();
		planGroupParams.addParam("billNo", billNo);
		List<PlanGroup> planGroupList = JpaUtils.findAll(planGroupJpql, planGroupParams);
		if (planGroupList.size() > 0) {
			//丰田车（场、道、位）
			if (String.valueOf(planGroupList.get(0).getToyotoId()).equals("1")) {
				String toyotoJpql = "SELECT p.cyAreaNo as cyAreaNo, p.cyRowNo as cyRowNo FROM PlanPlacVin p, PlanGroup w where p.planGroupNo = w.planGroupNo";
				List<Map<String, Object>> toyotoList = JpaUtils.getEntityManager().createQuery(toyotoJpql)
						.setHint(QueryHints.RESULT_TYPE, ResultType.Map).getResultList();
				if(toyotoList.size() > 0) {
					map.put("cyAreaNo", toyotoList.get(0).get("cyAreaNo"));
					map.put("cyRowNo", toyotoList.get(0).get("cyRowNo"));
//					String cCyBayJpql = "SELECT c FROM CCyBay c where c.cyAreaNo=:cyAreaNo";
//					QueryParamLs cCyBayParams = new QueryParamLs();
//					cCyBayParams.addParam("cyAreaNo", String.valueOf(toyotoList.get(0).get("cyAreaNo")));
//					List<CCyBay> cCyBayList = JpaUtils.findAll(cCyBayJpql, cCyBayParams);
//					if(cCyBayList.size() > 0) {
//						map.put("cyBayNo", cCyBayList.get(0).getCyBayNo());
//					}
				}
			} else {
				//非丰田车（场、道）
				String planRangeJpql = "SELECT p.cyAreaNo FROM PlanRange p, PlanGroup w where p.planGroupNo = w.planGroupNo";
				List<Map<String, Object>> planRangeList = JpaUtils.getEntityManager().createQuery(planRangeJpql)
						.setHint(QueryHints.RESULT_TYPE, ResultType.Map).getResultList();
				if(planRangeList.size() > 0) {
					map.put("cyAreaNo", planRangeList.get(0).get("cyAreaNo"));
				}
			}
		}
		return map;
	}

	@Override
	public GateTruckContract billCount(String ingateId, String contractNo) {
		String gtcJpql = "SELECT g FROM GateTruckContract g where g.ingateId=:ingateId and g.contractNo=:contractNo";
		QueryParamLs gtcParams = new QueryParamLs();
		gtcParams.addParam("ingateId", ingateId);
		gtcParams.addParam("contractNo", contractNo);
		List<GateTruckContract> gtcList = JpaUtils.findAll(gtcJpql, gtcParams);
		GateTruckContract gateTruckContractEntity = gtcList.get(0);
		return gateTruckContractEntity;
	}

	@Override
	public String sgworkCount(String contractNo) {
		String sql = "select f_get_CONTRACT_worknum('"+contractNo+"') MESINFO from dual";
		List<Map> lst=JpaUtils.getEntityManager().createNativeQuery(sql).setHint(QueryHints.RESULT_TYPE, ResultType.Map).getResultList(); 
		Map mapMes=lst.get(0);
		String messInfo=mapMes.get("MESINFO")+"";
		return messInfo;
	}	
	
	@Override
	public String checkVIN(String vin, String billNo, String contractNo, String brandCod) {
		String result = null;
//		//提货校验
//		String sql = "select f_sg_check('"+vin+"','"+billNo+"','"+contractNo+"','"+brandCod +"') MESINFO from dual";
//		List<Map> lst=JpaUtils.getEntityManager().createNativeQuery(sql).setHint(QueryHints.RESULT_TYPE, ResultType.Map).getResultList(); 
//		Map mapMes=lst.get(0);
//		String messInfo=mapMes.get("MESINFO")+"";
//        if(messInfo.equals("ok")) {
//        }else {
//        	result = "error" +"/"+messInfo;
//        	return result;
//        }		
//		List mList = JpaUtils.getEntityManager().createNativeQuery(sql).getResultList();
//		Result result = new Result();
//		result.setData(mList);
//		return result;
		
		//外贸疏港根据车架后带入相关信息并销库存
		
		String jpql = "select a from PortCar a where 1=1 ";
		QueryParamLs paramLs = new QueryParamLs();
		if(HdUtils.strNotNull(vin)){
			jpql += "and a.vinNo like :vin ";
			paramLs.addParam("vin", "%" + vin );
		}
		List<PortCar> portCarList = JpaUtils.findAll(jpql, paramLs);
		if(portCarList.size()>0){
			PortCar portCar = portCarList.get(0);
			result=portCar.getBillNo()+"/"+portCar.getBrandCod()+"/"+portCar.getCarTyp()+"/"+(portCar.getRfidCardNo()==null?"":portCar.getRfidCardNo())+"/"+portCar.getVinNo();
		}
		return result;
	}

	@Transactional
	public String wmshiploadershg(String req) {
		//System.out.println(req);
		JSONObject jobject = JSONObject.fromObject(req);
		Date sysDate = HdUtils.getDateTime();
		String Account =String.valueOf(jobject.get("account"));
//		String port = HdUtils.getCurUser().getOrgnName();
		// 先校验提单号、rfid不能为空 先校验rfid信息
		String result = "true";
		String pcJpql = "select a from PortCar a where a.vinNo =:vinNo and a.currentStat = '2'";
		QueryParamLs pcParamLs = new QueryParamLs();
		pcParamLs.addParam("vinNo", jobject.get("vinNo").toString());
		List<PortCar> pcList = JpaUtils.findAll(pcJpql, pcParamLs);
		if (pcList.size() > 0) {
//			if (!pcList.get(0).getCurrentStat().toString().equals("2")) {
			if (pcList.size() != 1) {
				// 此车不在场，不能疏港，停止
				result = "portError";
			} else {
				//业务校验
				if(pcList.get(0).getBrandCod().toString() == null || pcList.get(0).getBrandCod().toString().equals("")){
					result = "brandError";
				}else{
					//提货校验							
			        String sql = "select f_sg_check('"+ jobject.get("vinNo").toString() +"','"+ jobject.get("billNo").toString() +"','','"+ jobject.get("brandCod").toString() +"') MESINFO from dual";
			        List<Map> lst=JpaUtils.getEntityManager().createNativeQuery(sql).setHint(QueryHints.RESULT_TYPE, ResultType.Map).getResultList(); 
			        Map mapMes=lst.get(0);
			        String messInfo=mapMes.get("MESINFO")+"";
			        if(messInfo.equals("ok")) {
			        }else if(messInfo.equals("noship")){
			        	result = "sgshipError";
			        	return result;
			        }else if(messInfo.equals("nobill")){
			        	result = "sgbillError";
			        	return result;
			        }else if(messInfo.equals("nofactory")){
			        	result = "sgfactoryError";
			        	return result;
			        }else if(messInfo.equals("nobrandorfactory")){
			        	result = "sgbrandorfactoryError";
			        	return result;
			        }else {
			            result = "sgError";
			            return result;
			        }					
//				String customIdTest = (String) pcList.get(0).getCustomId();
//				if(customIdTest == null) {
//					customIdTest = "0";
//				}
//				if (pcList.get(0).getTradeId().toString().equals("2")
//						&& customIdTest.equals("0")) {
//					// 报错：海关未放行，不能出场装船
//					result = "seaError";
//				} else {
					if (!(jobject.get("damCod") == null || jobject.get("damCod").toString().equals(""))) {
						String jpqlup = " update PortCar a set a.damId= '1' , a.damLevel=:damLevel , a.damArea=:damArea , a.damCod=:damCod , a.updNam=:updNam , a.updTim=:updTim where a.vinNo =:vinNo ";
						QueryParamLs paramLsup = new QueryParamLs();
						paramLsup.addParam("damArea", jobject.get("damArea").toString());
						paramLsup.addParam("damLevel", jobject.get("damLevel").toString());
						paramLsup.addParam("damCod", jobject.get("damCod").toString());
						paramLsup.addParam("updNam", Account);
						paramLsup.addParam("updTim", sysDate);
						//paramLsup.addParam("rfid", jobject.get("rfid").toString());
						paramLsup.addParam("vinNo", jobject.get("vinNo").toString());
						JpaUtils.execUpdate(jpqlup, paramLsup);

						// 获取当前车辆的流水号
						String jpqlpcar = "select a from PortCar a where a.billNo =:billNo and a.rfidCardNo =:rfid and a.vinNo =:vinNo";
						QueryParamLs pcarParams = new QueryParamLs();
						// pcarParams.addParam("shipNo", jobject.get("shipNo").toString());
						pcarParams.addParam("billNo", jobject.get("billNo").toString());
						pcarParams.addParam("rfid", jobject.get("rfid").toString());
						pcarParams.addParam("vinNo", jobject.get("vinNo").toString());
						List<PortCar> pcarList = JpaUtils.findAll(jpqlpcar, pcarParams);
						if (pcarList.size() > 0) {
							// 生成残损记录
							CarDamage cdlist = new CarDamage();
							cdlist.setCardamagId(HdUtils.genUuid());
							cdlist.setDamArea(jobject.get("damArea").toString());
							cdlist.setPortCarNo(pcarList.get(0).getPortCarNo());
							cdlist.setDamCod(jobject.get("damCod").toString());
							cdlist.setDamLevel(jobject.get("damLevel").toString());
							cdlist.setRecNam(Account);
							String orgnId = HdUtils.getCurUser().getOrgnId();
							String authOrgnJpql = "select t from AuthOrgn t where t.orgnId=:orgnId";
							QueryParamLs authOrgnParams = new QueryParamLs();
							authOrgnParams.addParam("orgnId", orgnId);
							List<AuthOrgn> authOrgnList = JpaUtils.findAll(authOrgnJpql, authOrgnParams);
							if(authOrgnList.size() > 0) {
								cdlist.setRegPost(authOrgnList.get(0).getName());
							} else {
								cdlist.setRegPost(Account);
							}
							cdlist.setRecTim((Timestamp) sysDate);
							cdlist.setVinNo(jobject.get("vinNo").toString());
							cdlist.setIncharge((String) jobject.get("incharge"));
							JpaUtils.getBaseDao().save(cdlist);
						}
					}
					
					String pclJpql = "select a from PortCar a where a.vinNo =:vinNo and a.currentStat = '2'";
					QueryParamLs pclParams = new QueryParamLs();
//					pclParams.addParam("rfid", jobject.get("rfid").toString());
					pclParams.addParam("vinNo", jobject.get("vinNo").toString());
					List<PortCar> pcarList = JpaUtils.findAll(pclJpql, pclParams);
					if (pcarList.size() > 0) {
						//疏港-解锁车位
//						String arbJpql = "SELECT c FROM CCyBay c where c.cyAreaNo=:cyAreaNo and c.cyRowNo=:cyRowNo and c.cyBayNo=:cyBayNo";
//						QueryParamLs arbParams = new QueryParamLs();
//						arbParams.addParam("cyAreaNo", pcList.get(0).getCyAreaNo());
//						arbParams.addParam("cyRowNo", pcList.get(0).getCyRowNo());
//						arbParams.addParam("cyBayNo", pcList.get(0).getCyBayNo());
//						List<CCyBay> arbList = JpaUtils.findAll(arbJpql, arbParams);
//						if(arbList.size() > 0) {
//							CCyBay cCyBayEntity = arbList.get(0);
//							cCyBayEntity.setLockId("0");
//							JpaUtils.getBaseDao().update(cCyBayEntity);
//						}
						
						// 生成指令记录(疏港)
						String platNo = CommonUtil.getId().substring(0,8);
						String pcWorkQueueNo = String.valueOf(jobject.get("contractNo")) + "-"+ "T"+ String.valueOf(jobject.get("truckNo"));
						WorkCommand wcList = new WorkCommand();
						wcList.setQueueId(HdUtils.genUuid());
						wcList.setWorkQueueNo(pcWorkQueueNo);
						wcList.setPortCarNo(pcarList.get(0).getPortCarNo());
						wcList.setRfidCardNo((String)jobject.get("rfid"));
						wcList.setVinNo(jobject.get("vinNo").toString());
						wcList.setContractNo(jobject.get("contractNo").toString());
						wcList.setBillNo(jobject.get("vinNo").toString());
						wcList.setWorkTyp(String.valueOf(jobject.get("workTyp")));
						wcList.setBrandCod(pcarList.get(0).getBrandCod());
						wcList.setCarTyp(pcarList.get(0).getCarTyp());
						wcList.setCarKind(pcarList.get(0).getCarKind());
						// 直装、直取(暂时不填)
						// wcList.setDirectId(directId);
						// wcList.setPlanPlac(planPlac);
						wcList.setCyPlac(String.valueOf(jobject.get("cyPlac")));
						wcList.setSendTim((Timestamp) sysDate);
						wcList.setSendNam(Account);
						wcList.setInCyTim((Timestamp) sysDate);
						wcList.setInCyNamNam(Account);
						wcList.setFinishedId("1");
						wcList.setNightId(String.valueOf(jobject.get("nightId")));
						wcList.setHolidayId(String.valueOf(jobject.get("holidayId")));
						wcList.setUseMachId(jobject.get("useMachId").toString());
						wcList.setUseWorkerId(jobject.get("useWorkerId").toString());
						wcList.setRemarks("手持录入");
						wcList.setLengthOverId((String) jobject.get("lengthOverId"));
						if (jobject.get("lengthOverId").toString().equals("1")&&HdUtils.strNotNull((String)jobject.get("length"))) {
							BigDecimal overLength = new BigDecimal((String)jobject.get("length") );
							wcList.setLength(overLength);
						}
						// wcList.setWidthOverId(widthOverId);
						JpaUtils.getBaseDao().save(wcList);

						// 生成闸口记录
						String zkJpql = "select a.ingateId, a.contractNo, a.truckNo, a.carryId, b.inGatTim, b.outGatTim, c.shipNo, c.iEId, c.voyage, a.billNo, c.tradeId,"
								+ " c.discPortCod, c.tranPortCod, c.carTyp, c.carKind, c.consignCod, b.inGatNo, b.inRecNam, c.brand"
								+ " from GateTruckContract a, GateTruck b, ContractIeDoc c "
								+ " where a.ingateId = b.ingateId and a.contractNo = c.contractNo"
								+ " and a.ingateId =:ingateId and a.contractNo=:contractNo";
						List<Map<String, Object>> zkList = JpaUtils.getEntityManager().createQuery(zkJpql)
								.setParameter("ingateId", jobject.get("ingateId").toString())
								.setParameter("contractNo", jobject.get("contractNo").toString())
								.setHint(QueryHints.RESULT_TYPE, ResultType.Map).getResultList();

						TruckWork twList = new TruckWork();
						twList.setIngateId(String.valueOf(jobject.get("ingateId")));
						twList.setContractNo(zkList.get(0).get("contractNo").toString());
						twList.setTruckNo(zkList.get(0).get("truckNo").toString());
						twList.setCarryId(zkList.get(0).get("carryId").toString());
						twList.setInGatTim((Timestamp) zkList.get(0).get("inGatTim"));
						twList.setOutGatTim((Timestamp) zkList.get(0).get("outGatTim"));
						String shipNoTest = (String) zkList.get(0).get("shipNo");
						if(shipNoTest == null || shipNoTest.equals("") || shipNoTest.length() == 0) {
						} else {
							twList.setShipNo(zkList.get(0).get("shipNo").toString());
						}
						twList.setCarryWay(String.valueOf(jobject.get("singleId")));
						// twList.setShipCod(shipCod);
						twList.setIEId((String) zkList.get(0).get("iEId"));
						twList.setVoyage((String) zkList.get(0).get("voyage"));
						twList.setBillNo((String) zkList.get(0).get("billNo"));
						twList.setTradeId((String) zkList.get(0).get("tradeId"));
						twList.setDiscPortCod((String) zkList.get(0).get("discPortCod"));
						twList.setTranPortCod((String) zkList.get(0).get("tranPortCod"));

						twList.setPortCarNo(pcarList.get(0).getPortCarNo());
						twList.setRfidCardNo((String)jobject.get("rfid"));
						twList.setVinNo(jobject.get("vinNo").toString());
						// twList.setNewVinNo(newVinNo);
						twList.setBrandCod((String) zkList.get(0).get("brand"));
						twList.setCarTyp((String) zkList.get(0).get("carTyp"));
						twList.setCarKind((String) zkList.get(0).get("carKind"));
						// twList.setMarks(marks);
						twList.setDamCod((String) jobject.get("damCod"));
						twList.setDamArea((String) jobject.get("damArea"));
						// twList.setDirectId(directId);
						twList.setConsignCod((String) jobject.get("consignCod"));
						// twList.setCrgAgent(crgAgent);
//						twList.setCyPlac(jobject.get("cyPlac").toString());
						twList.setWorkTim((Timestamp) sysDate);
						twList.setWorkNam(Account);
						twList.setInGateNo((String) zkList.get(0).get("inGatNo"));
						twList.setInRecNam((String) zkList.get(0).get("inRecNam"));
						JpaUtils.getBaseDao().save(twList);
						
						//更新计划数作业数
						String gtcJpql = "SELECT g FROM GateTruckContract g where g.ingateId=:ingateId";
						QueryParamLs gtcParams = new QueryParamLs();
						gtcParams.addParam("ingateId", String.valueOf(jobject.get("ingateId")));
						List<GateTruckContract> gtcList = JpaUtils.findAll(gtcJpql, gtcParams);
						if(gtcList.size() > 0) {
							GateTruckContract gateTruckContractEntity = gtcList.get(0);
							BigDecimal bigDecimalAdd = new BigDecimal("1");
							gateTruckContractEntity.setWorkNum(gateTruckContractEntity.getWorkNum().add(bigDecimalAdd));
							JpaUtils.getBaseDao().update(gateTruckContractEntity);
						}
					}
					
					// 更新port_car
					String jpqlBl = " update PortCar a set a.currentStat = '5' ,a.outCyTim=:outCyTim, a.updNam=:updNam , a.updTim=:updTim where a.vinNo=:vinNo and a.currentStat = '2'";
					QueryParamLs paramLBl = new QueryParamLs();
					paramLBl.addParam("outCyTim", sysDate);
					paramLBl.addParam("updNam", Account);
					paramLBl.addParam("updTim", sysDate);
					paramLBl.addParam("vinNo", jobject.get("vinNo").toString());
					JpaUtils.execUpdate(jpqlBl, paramLBl);
					
					// 修改作业量
					String fgtJpql = "select a from GateTruckContract a where a.ingateId =:ingateId and a.contractNo =:contractNo";
					QueryParamLs fgtcParams = new QueryParamLs();
					fgtcParams.addParam("ingateId", jobject.get("ingateId").toString());
					fgtcParams.addParam("contractNo", jobject.get("contractNo").toString());
					List<GateTruckContract> fgtcList = JpaUtils.findAll(fgtJpql, fgtcParams);
					if (fgtcList.size() > 0) {
						String gtcJpql = "update GateTruckContract a set a.workNum =:workNum where a.ingateId =:ingateId and a.contractNo =:contractNo";
						QueryParamLs gtcParams = new QueryParamLs();
						BigDecimal bignum1 = new BigDecimal("1");
						gtcParams.addParam("workNum", fgtcList.get(0).getWorkNum().add(bignum1));
						gtcParams.addParam("ingateId", jobject.get("ingateId").toString());
						gtcParams.addParam("contractNo", jobject.get("contractNo").toString());
						JpaUtils.execUpdate(gtcJpql, gtcParams);
					}
//				}
			}
		  }
		} else {
			result = "noNum";
		}
		return result;
	}

	@Override
	public Map<String, Object> checkBc(String vinNo) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("brandCod", "");
		map.put("carKind", "");
		map.put("carTyp", "");
		if (vinNo.length() >= 8) {
			String substr8=vinNo.substring(0,8);
			String jpqla="select a from CCarVin a where a.vinNo=:vinNo ";
			QueryParamLs paramLsa = new QueryParamLs();
			paramLsa.addParam("vinNo",substr8);
			List<CCarVin> ccvList=JpaUtils.findAll(jpqla, paramLsa);
			if(ccvList.size()>0){
				CCarTyp cct=JpaUtils.findById(CCarTyp.class,ccvList.get(0).getCarTyp());
				if (cct != null) {
					String brandCod=cct.getBrandCod();
					String carKind=cct.getCarKind();
					String carTyp=cct.getCarTyp();
					if(HdUtils.strNotNull(brandCod)) {
						map.put("brandCod", brandCod);
					}
					if(HdUtils.strNotNull(carKind)){
						map.put("carKind", carKind);
					}
					if(HdUtils.strNotNull(carTyp)) {
						map.put("carTyp", carTyp);
					}
				}
			}
		}
		return map;
	}

	@Override
	public Map<String, Object> getBrandCar(String vinNo) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("brandCod", "");
		map.put("carKind", "");
		map.put("carTyp", "");
		String jpql = "SELECT p FROM PortCar p where p.vinNo=:vinNo";
		QueryParamLs paramLsa = new QueryParamLs();
		paramLsa.addParam("vinNo", vinNo);
		List<PortCar> portCarList = JpaUtils.findAll(jpql, paramLsa);
		if(portCarList.size() > 0) {
			String carKind = portCarList.get(0).getCarKind();
			String brandCod = portCarList.get(0).getBrandCod();
			String carTyp = portCarList.get(0).getCarTyp();
			if(HdUtils.strNotNull(carKind)) {
				map.put("carKind", carKind);
			}
			if(HdUtils.strNotNull(brandCod)) {
				map.put("brandCod", brandCod);
			}
			if(HdUtils.strNotNull(carTyp)) {
				map.put("carTyp", carTyp);
			}
		}
		return map;
	}

	@Override
	public String checkShipInOutCheck(String vinNo) {
		String flag = "true";
		String shipInOutCheckJpql = "SELECT c FROM ShipInOutCheck c where c.vcVinNo=:vcVinNo and c.shipNo='20190311082013'";
		QueryParamLs shipInOutCheckParams = new QueryParamLs();
		shipInOutCheckParams.addParam("vcVinNo", vinNo);
		List<ShipInOutCheck> shipInOutCheckLists = JpaUtils.findAll(shipInOutCheckJpql, shipInOutCheckParams);
		if(shipInOutCheckLists.size() > 0) {
			
		} else {
			flag = "false";
		}
		return flag;
	}
	
	@Override
	public String jgCheck(String contractNo, String vinNo) {
		String flag = "true";
		//JSONObject jobject =new  JSONObject();
		String sql="select f_jg_check('"+contractNo+"','"+vinNo+"') MESINFO from dual";
		List<Map> lst=JpaUtils.getEntityManager().createNativeQuery(sql).setHint(QueryHints.RESULT_TYPE, ResultType.Map).getResultList(); 
		Map mapMes=lst.get(0);
		String messInfo=mapMes.get("MESINFO")+"";
	  	if(messInfo.equals("ok")) {
	  		//String message="ok";
	  		//jobject.put("message", messInfo);
	  		return flag;
	  	}else {
	  		flag = "false";
	  		return flag;
	  		//jobject.put("state", "-1");//表示成功 -1 表示失败
	  		//jobject.put("message", messInfo);
	  	}
		//return jobject.toString();
	}

	@Override
	public String countCarInPort(String contractNo) {
		String num = "";
		//计划数
		String iedocSql = "SELECT nvl(c.CAR_NUM,'0') as count FROM Contract_Ie_Doc c where c.CONTRACT_NO = '"+ contractNo +"'";
		List<Map<String,Object>> list=JpaUtils.getEntityManager().createNativeQuery(iedocSql).setHint(QueryHints.RESULT_TYPE, ResultType.Map).getResultList();
		num = list.get(0).get("COUNT").toString();
		//作业数
		String wcJpql = "SELECT nvl(count(*),'0') as count FROM Work_Command w where w.CONTRACT_NO='"+ contractNo +"' and w.work_Typ='TI'";
		List<Map<String,Object>> listWc=JpaUtils.getEntityManager().createNativeQuery(wcJpql).setHint(QueryHints.RESULT_TYPE, ResultType.Map).getResultList();
		num = listWc.get(0).get("COUNT").toString() + "/" + num;
		return num;
	}
	
	@Override
	public String countAreaNo(String contractNo) {
		String result = "";
		if (HdUtils.strNotNull(contractNo)) {
			ContractIeDoc bean = JpaUtils.findById(ContractIeDoc.class, contractNo);
			if (bean != null) {
				String planArea = bean.getPlanArea();
				if (HdUtils.strNotNull(planArea)) {
					if (planArea.indexOf(",") != -1) {
						planArea = planArea.split(",")[0];
					}
					String jpql = "select a from CCyArea a where a.cyAreaNam =:cyAreaNam";
					QueryParamLs paramLs = new QueryParamLs();
					paramLs.addParam("cyAreaNam", planArea);
					List<CCyArea> list = JpaUtils.findAll(jpql, paramLs);
					if (list.size() > 0) {
						result = list.get(0).getCyAreaNo();
					}
				}
			}
		}
		return result;
	}
	
	@Override
	public String countCarZc(String billNo) {
		String num = "";
		//计划数
		String iedocSql = "SELECT nvl(c.PIECES,'0') as count FROM SHIP_BILL c where c.BILL_NO = '"+ billNo +"'";
		List<Map<String,Object>> list=JpaUtils.getEntityManager().createNativeQuery(iedocSql).setHint(QueryHints.RESULT_TYPE, ResultType.Map).getResultList();
		num = list.get(0).get("COUNT").toString();
		//作业数
		String wcJpql = "SELECT nvl(count(*),'0') as count FROM Work_Command w where w.BILL_NO='"+ billNo +"' and w.work_Typ='SO'";
		List<Map<String,Object>> listWc=JpaUtils.getEntityManager().createNativeQuery(wcJpql).setHint(QueryHints.RESULT_TYPE, ResultType.Map).getResultList();
		num = listWc.get(0).get("COUNT").toString() + "/" + num;
		return num;
	}
	
	@Override
	public String countCarXhz(String shipNo, String billNo) {
		String num = "";
		String num1 = "";
		String txt = "";
		//计划数
		String planSql = "SELECT nvl(t1.bill_num, 0) as COUNT FROM ship_load_bill t1 where t1.ship_no = '"+ shipNo +"' and t1.bill_no = '" + billNo + "'" ;
		List<Map<String,Object>> list1=JpaUtils.getEntityManager().createNativeQuery(planSql).setHint(QueryHints.RESULT_TYPE, ResultType.Map).getResultList();
		num1 = list1.get(0).get("COUNT").toString();
		//作业数
		String worksql = "SELECT nvl(to_char(count(0)), 0) as COUNT FROM port_car t1 where t1.current_stat in ('2','5')  and t1.bill_no='"+ billNo +"'";
		List<Map<String,Object>> listWc=JpaUtils.getEntityManager().createNativeQuery(worksql).setHint(QueryHints.RESULT_TYPE, ResultType.Map).getResultList();
		num = (String)listWc.get(0).get("COUNT");
		//返回
		txt = num + "/" + num1;
		return txt;
	}	

}
