package net.huadong.tech.wechat.serviceimpl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.eclipse.persistence.config.QueryHints;
import org.eclipse.persistence.config.ResultType;
import org.springframework.stereotype.Component;

import net.huadong.tech.base.entity.CCarTyp;
import net.huadong.tech.base.entity.CCarVin;
import net.huadong.tech.base.entity.CCyBay;
import net.huadong.tech.damage.entity.CarDamage;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.tech.privilege.entity.AuthOrgn;
import net.huadong.tech.ship.entity.Ship;
import net.huadong.tech.shipbill.entity.BillCar;
import net.huadong.tech.shipbill.entity.PortCar;
import net.huadong.tech.shipbill.entity.ShipBill;
import net.huadong.tech.util.HdUtils;
import net.huadong.tech.wechat.service.ShipAnbianService;
import net.huadong.tech.work.entity.WorkCommand;
import net.sf.json.JSONObject;

@Component
public class ShipAnbianServiceImpl implements ShipAnbianService {

	@Override
	public StringBuffer loadBillData(String shipNo) {
		StringBuffer result = new StringBuffer();
		// 该船作业数
		String cJpql = "select count(a) as data from WorkCommand a where a.shipNo =:shipNo and a.workTyp = 'SO' and a.shipWorkTim is not null";
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

	@Override
	public String shiploader(String req) {
		System.out.println(req);
		JSONObject jobject = JSONObject.fromObject(req);
		Date sysDate = HdUtils.getDateTime();
		String Account =(String) jobject.get("account");
		String result = "true";

		// 先校验rfid信息
		String rJpql = "select a from PortCar a where a.rfidCardNo =:rfid";
		QueryParamLs rParam = new QueryParamLs();
		rParam.addParam("rfid", jobject.get("rfid").toString());
		List<PortCar> pcList = JpaUtils.findAll(rJpql, rParam);
		if (pcList.size() > 0) {
			if (!(pcList.get(0).getCurrentStat() == null || pcList.get(0).getCurrentStat().toString().equals(""))) {
				if (pcList.get(0).getCurrentStat().toString().equals("2")) {
					// 报错：此车还在堆场，不能装船，停止
					result = "inplace";
					return result;
				} else if (pcList.get(0).getCurrentStat().toString().equals("5")) {
					// 报错：此车已经在船，不能重复作业，停止
					result = "inship";
					return result;
				} else if (pcList.get(0).getCurrentStat().toString().equals("4") || pcList.get(0).getCurrentStat().toString().equals("3")) {
					// 状态为装船时
					// 验证海关是否放行( 外贸车辆)
					if (!(pcList.get(0).getTradeId() == null || pcList.get(0).getTradeId().toString().equals("")) && !(pcList.get(0).getCustomId() == null || pcList.get(0).getCustomId().equals(""))) {
						if (pcList.get(0).getTradeId().toString().equals("1")
								&& pcList.get(0).getCustomId().toString().equals("0")) {
							// 报错：海关未放行，不能出场装船
							result = "insea";
							return result;
						}
					}
					//先判断WORK_COMMAND表里是否有这条数据
					String workJpql = "SELECT w FROM WorkCommand w where w.rfidCardNo=:rfidCardNo";
					QueryParamLs workParams = new QueryParamLs();
					workParams.addParam("rfidCardNo", String.valueOf(jobject.get("rfid")));
					List<WorkCommand> workList = JpaUtils.findAll(workJpql, workParams);
					if(workList.size() < 1) {
						result = "workError";
						return result;
					}
					
					if (!(jobject.get("damCod") == null || jobject.get("damCod").toString().equals(""))) {
						String jpqlup = " update PortCar a  set a.currentStat='5' , a.loadShipTim=:loadShipTim , set a.damId= '1' , a.damLevel=:damLevel , a.damArea=:damArea , a.damCod=:damCod , a.updNam=:updNam , a.updTim=:updTim where a.rfidCardNo=:rfid ";
						QueryParamLs paramLsup = new QueryParamLs();
						paramLsup.addParam("loadShipTim", sysDate);
						paramLsup.addParam("damArea", jobject.get("damArea").toString());
						paramLsup.addParam("damLevel", jobject.get("damLevel").toString());
						paramLsup.addParam("damCod", jobject.get("damCod").toString());
						paramLsup.addParam("updNam", Account);
						paramLsup.addParam("updTim", sysDate);
						paramLsup.addParam("rfid", jobject.get("rfid").toString());
						JpaUtils.execUpdate(jpqlup, paramLsup);

						// 获取当前车辆的流水号
						String jpqlpcar = "select a from PortCar a where a.shipNo =:shipNo and a.billNo =:billNo and a.rfidCardNo =:rfid and a.vinNo =:vinNo";
						QueryParamLs pcarParams = new QueryParamLs();
						pcarParams.addParam("shipNo", jobject.get("shipNo").toString());
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
							cdlist.setRecTim((Timestamp) sysDate);
							cdlist.setVinNo(jobject.get("vinNo").toString());
							cdlist.setIncharge(jobject.get("incharge").toString());
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
							JpaUtils.getBaseDao().save(cdlist);
						}
					} else {
						// 更新port_car
						String jpqlBl = " update PortCar a set a.currentStat='5' , a.loadShipTim=:loadShipTim , a.updNam=:updNam , a.updTim=:updTim where a.rfidCardNo=:rfid ";
						QueryParamLs paramLBl = new QueryParamLs();
						paramLBl.addParam("loadShipTim", sysDate);
						paramLBl.addParam("updNam", Account);
						paramLBl.addParam("updTim", sysDate);
						paramLBl.addParam("rfid", jobject.get("rfid").toString());
						JpaUtils.execUpdate(jpqlBl, paramLBl);
					}
					// 更新指令记录
					String jpqlWc = " update WorkCommand a set a.finishedId='1' , a.shipWorkTim=:shipWorkTim , a.shipWorkNam=:shipWorkNam where a.rfidCardNo=:rfid and a.workTyp = 'SO' ";
					QueryParamLs paramLWc = new QueryParamLs();
					paramLWc.addParam("shipWorkNam", Account);
					paramLWc.addParam("shipWorkTim", sysDate);
					paramLWc.addParam("rfid", jobject.get("rfid").toString());
					JpaUtils.execUpdate(jpqlWc, paramLWc);
				} else {
					// 报错：此车不在岸边，不能装船，停止
					result = "inab";
					return result;
				}
			}
		}
		return result;
	}

	@Override
	public String shipUnloader(String req) {
		JSONObject jobject = JSONObject.fromObject(req);
		Date sysDate = HdUtils.getDateTime();
		String Account = (String) jobject.get("account");
		String result = "true";
		// 校验VIN
		if (jobject.get("vinNo") == null || jobject.get("vinNo").toString().equals("")) {
		} else {
			String jpqlVin = "select a from PortCar a where a.vinNo =:vinNo";
			QueryParamLs vinParamLs = new QueryParamLs();
			vinParamLs.addParam("vinNo", jobject.get("vinNo").toString());
			List<PortCar> vinStatList = JpaUtils.findAll(jpqlVin, vinParamLs);
			if (vinStatList.size() > 0) {
				if (vinStatList.get(0).getCurrentStat().equals("2")
						|| vinStatList.get(0).getCurrentStat().equals("3")) {
					// 报错：当前车架号已经在场，不能重复卸船。停止继续操作
					result = "vinerror";
					return result;
				}
			}
		}

		// 校验RFID
		if (jobject.get("rfid") == null || jobject.get("rfid").toString().equals("")) {
		} else {
			String jpqlRFID = "select a from PortCar a where a.rfidCardNo =:rfidCardNo";
			QueryParamLs rfidParamLs = new QueryParamLs();
			rfidParamLs.addParam("rfidCardNo", jobject.get("rfid").toString());
			List<PortCar> rfidList = JpaUtils.findAll(jpqlRFID, rfidParamLs);
			if (rfidList.size() > 0 && result.equals("true")) {
				if (rfidList.get(0).getCurrentStat().equals("2") || rfidList.get(0).getCurrentStat().equals("3")) {
					// 报错：当前rfid已经在场，不能重复卸船。停止继续操作
					result = "rfiderror";
					return result;
				}
			}
		}

		if (!(jobject.get("vinNo") == null || jobject.get("vinNo").toString().equals(""))) {
			String jpqlbc = "select a from BillCar a where a.shipNo =:shipNo and a.billNo =:billNo and a.vinNo =:vinNo";
			QueryParamLs bcParamLs = new QueryParamLs();
			bcParamLs.addParam("shipNo", jobject.get("shipNo").toString());
			bcParamLs.addParam("billNo", jobject.get("billNo").toString());
			bcParamLs.addParam("vinNo", jobject.get("vinNo").toString());
			List<BillCar> bcList = JpaUtils.findAll(jpqlbc, bcParamLs);
			if (bcList.size() > 0) {
				// 车架号已经存在于舱单，需要更新舱单
				bcList.get(0).setRfidCardNo(jobject.get("rfid").toString());
				JpaUtils.getBaseDao().updateAll(bcList);

				// 车架号是否存在于port_car
				String jpqlpc = "select p from PortCar p where p.vinNo =:vinNo";
				QueryParamLs pcParaLs = new QueryParamLs();
				pcParaLs.addParam("vinNo", jobject.get("vinNo").toString());
				List<PortCar> pcList = JpaUtils.findAll(jpqlpc, pcParaLs);
				if (pcList.size() > 0) {
					String shipJpql = "select s from Ship s where s.shipNo =:shipNo";
					QueryParamLs shipParams = new QueryParamLs();
					shipParams.addParam("shipNo", jobject.get("shipNo").toString());
					List<Ship> shipList = JpaUtils.findAll(shipJpql, shipParams);
					// port_car存在了，直接更新即可
					String uppcJpql = "select a from PortCar a where a.shipNo =:shipNo and a.billNo =:billNo and a.vinNo =:vinNo";
					QueryParamLs uppcParams = new QueryParamLs();
					uppcParams.addParam("shipNo", jobject.get("shipNo").toString());
					uppcParams.addParam("billNo", jobject.get("billNo").toString());
					uppcParams.addParam("vinNo", jobject.get("vinNo").toString());
					List<PortCar> pcupList = JpaUtils.findAll(uppcJpql, uppcParams);
					if (pcupList.size() > 0) {
						pcupList.get(0).setCurrentStat("3");
						pcupList.get(0).setRfidCardNo(jobject.get("rfid").toString());
						pcupList.get(0).setToPortTim(shipList.get(0).getToPortTim());
						pcupList.get(0).setDiscShipTim((Timestamp) sysDate);
						pcupList.get(0).setUpdNam(Account);
						JpaUtils.getBaseDao().updateAll(pcupList);
					}
				} else {
					// port_car不存在此车信息
					String jpql2 = "select s from ShipBill s where s.iEId = 'I' and s.shipNo =:shipNo and s.billNo =:billNo";
					QueryParamLs sbParam = new QueryParamLs();
					sbParam.addParam("shipNo", jobject.get("shipNo").toString());
					sbParam.addParam("billNo", jobject.get("billNo").toString());
					List<ShipBill> sbList = JpaUtils.findAll(jpql2, sbParam);
					String jpqlship = "select a from Ship a where a.shipNo =:shipNo";
					QueryParamLs shipParam = new QueryParamLs();
					shipParam.addParam("shipNo", jobject.get("shipNo").toString());
					List<Ship> shipList = JpaUtils.findAll(jpqlship, shipParam);
					if (sbList.size() > 0 && shipList.size() > 0) {
						PortCar portCar = new PortCar();
						portCar.setRfidCardNo(jobject.get("rfid").toString());
						portCar.setVinNo(jobject.get("vinNo").toString());
						portCar.setCurrentStat("3");
						portCar.setShipNo(jobject.get("shipNo").toString());
						portCar.setBillNo(jobject.get("billNo").toString());
						portCar.setiEId(sbList.get(0).getiEId());
						portCar.setTradeId(sbList.get(0).getTradeId());
						portCar.setConsignCod(sbList.get(0).getConsignCod());
						portCar.setConsignNam(sbList.get(0).getConsignNam());
						portCar.setReceiveCod(sbList.get(0).getReceiveCod());
						portCar.setReceiveNam(sbList.get(0).getReceiveNam());
						portCar.setCarTyp(sbList.get(0).getCarTyp());
						portCar.setBrandCod(sbList.get(0).getBrandCod());
						portCar.setCarKind(sbList.get(0).getCarKind());
						if (jobject.get("damCod") == null || jobject.get("damCod").toString().equals("")) {

						} else {
							portCar.setDamId("1");
							portCar.setDamCod(jobject.get("damCod").toString());
							portCar.setDamLevel(jobject.get("damLevel").toString());
							portCar.setDamArea(jobject.get("damArea").toString());
						}
						portCar.setLoadPortCod(sbList.get(0).getLoadPortCod());
						portCar.setTranPortCod(sbList.get(0).getTranPortCod());
						portCar.setDiscPortCod(sbList.get(0).getDiscPortCod());
						//暂时不存
						// portCar.setInToolNo(sbList.get(0).getShipNo());
						portCar.setInPortNo(sbList.get(0).getShipNo());
						portCar.setToPortTim(shipList.get(0).getToPortTim());
						portCar.setDiscShipTim((Timestamp) sysDate);
						portCar.setInCyTim((Timestamp) sysDate);
						portCar.setDockCod(shipList.get(0).getDockCod());
						portCar.setRemarks("手持录入");
						portCar.setRecNam(Account);
						portCar.setRecTim((Timestamp) sysDate);
						JpaUtils.getBaseDao().save(portCar);
					}
				}
				// workQueueNo 选择船时的队列号
				String jpqlpcar = "select a from PortCar a where a.shipNo =:shipNo and a.billNo =:billNo and a.rfidCardNo =:rfid and a.vinNo =:vinNo";
				QueryParamLs pcarParams = new QueryParamLs();
				pcarParams.addParam("shipNo", jobject.get("shipNo").toString());
				pcarParams.addParam("billNo", jobject.get("billNo").toString());
				pcarParams.addParam("rfid", jobject.get("rfid").toString());
				pcarParams.addParam("vinNo", jobject.get("vinNo").toString());
				List<PortCar> pcarList = JpaUtils.findAll(jpqlpcar, pcarParams);

				WorkCommand wcList = new WorkCommand();
				wcList.setQueueId(HdUtils.genUuid());
				wcList.setWorkQueueNo(jobject.get("workQueueNo").toString());
				wcList.setPortCarNo(pcarList.get(0).getPortCarNo());
				wcList.setRfidCardNo(jobject.get("rfid").toString());
				wcList.setVinNo(jobject.get("vinNo").toString());
				wcList.setShipNo(jobject.get("shipNo").toString());
				wcList.setBillNo(jobject.get("billNo").toString());
				wcList.setWorkTyp("SI");
				wcList.setBrandCod(pcarList.get(0).getBrandCod()); // 车辆品牌
				wcList.setCarTyp(pcarList.get(0).getCarTyp()); // 车型
				wcList.setCarKind(pcarList.get(0).getCarKind()); // 车数类别
				// 直取直装（暂时不存）
				// wcList.setDirectId(); 
				wcList.setPlanPlac(jobject.get("planPlac").toString());
				wcList.setSendTim((Timestamp) sysDate);
				wcList.setSendNam(Account);
				wcList.setShipWorkNam(Account);
				wcList.setShipWorkTim((Timestamp) sysDate);
				wcList.setFinishedId("0");
				wcList.setDriver(jobject.get("driver").toString());
				wcList.setNightId((String) jobject.get("nightId"));
				wcList.setHolidayId((String)jobject.get("holidayId"));
				wcList.setLengthOverId((String)jobject.get("lengthOverId"));
				if(jobject.get("lengthOverId").toString().equals("1")) {
					BigDecimal overLength=new BigDecimal((String)jobject.get("length"));
					wcList.setLength(overLength);
				}
				wcList.setUseMachId(jobject.get("useMachId").toString());
				wcList.setUseWorkerId(jobject.get("useWorkerId").toString());
				//暂时不存
				// wcList.setRemarks(remarks);
				wcList.setLengthOverId((String)jobject.get("lengthOverId"));
				//暂时不存
				// wcList.setWidthOverId(widthOverId);
				JpaUtils.getBaseDao().save(wcList);
			} else {
				// 车架不存在与舱单
				String overId = "1";
				jump(req, overId);
			}
		} else {
			jump(req, null);
		}
		return result;
	}

	public void jump(String req, String overId) {
		JSONObject jobject = JSONObject.fromObject(req);
		Date sysDate = HdUtils.getDateTime();
		String Account =(String)jobject.get("account");
//		String port = HdUtils.getCurUser().getOrgnName();

		// 生成舱单明细
		String jpqlBc = "select s from ShipBill s where s.iEId = 'I' and s.shipNo =:shipNo and s.billNo =:billNo";
		QueryParamLs sbParam = new QueryParamLs();
		sbParam.addParam("shipNo", jobject.get("shipNo").toString());
		sbParam.addParam("billNo", jobject.get("billNo").toString());
		List<ShipBill> sbList = JpaUtils.findAll(jpqlBc, sbParam);
		String jpqlship = "select a from Ship a where a.shipNo =:shipNo";
		QueryParamLs shipParam = new QueryParamLs();
		shipParam.addParam("shipNo", jobject.get("shipNo").toString());
		List<Ship> shipList = JpaUtils.findAll(jpqlship, shipParam);

		PortCar pcLists = new PortCar();
		pcLists.setRfidCardNo(jobject.get("rfid").toString());
		pcLists.setVinNo(jobject.get("vinNo").toString());
		pcLists.setCurrentStat("3");
		pcLists.setShipNo(jobject.get("shipNo").toString());
		pcLists.setBillNo(jobject.get("billNo").toString());
		pcLists.setiEId(sbList.get(0).getiEId());
		pcLists.setTradeId(sbList.get(0).getTradeId());
		pcLists.setConsignCod(sbList.get(0).getConsignCod());
		pcLists.setConsignNam(sbList.get(0).getConsignNam());
		pcLists.setReceiveCod(sbList.get(0).getReceiveCod());
		pcLists.setReceiveNam(sbList.get(0).getReceiveNam());
		pcLists.setCarTyp(sbList.get(0).getCarTyp());
		pcLists.setBrandCod(sbList.get(0).getBrandCod());
		pcLists.setCarKind(sbList.get(0).getCarKind());
		if (jobject.get("damCod") == null || jobject.get("damCod").toString().equals("")) {
		} else {
			pcLists.setDamId("1");
			pcLists.setDamCod(jobject.get("damCod").toString());
			pcLists.setDamLevel(jobject.get("damLevel").toString());
			pcLists.setDamArea(jobject.get("damArea").toString());
		}
		pcLists.setLoadPortCod(sbList.get(0).getDiscPortCod());
		pcLists.setTranPortCod(sbList.get(0).getTranPortCod());
		pcLists.setDiscPortCod(sbList.get(0).getDiscPortCod());
		// pcLists.setInToolNo(shipNo);
		pcLists.setInPortNo(jobject.get("shipNo").toString());
		pcLists.setToPortTim(sysDate);
		pcLists.setDiscShipTim((Timestamp) sysDate);
		pcLists.setDockCod(shipList.get(0).getDockCod());
		pcLists.setRemarks("手持录入");
		pcLists.setRecNam(Account);
		pcLists.setRecTim((Timestamp) sysDate);
		JpaUtils.getBaseDao().save(pcLists);

		String jpqlpcar = "select a from PortCar a where a.shipNo =:shipNo and a.billNo =:billNo and a.rfidCardNo =:rfid and a.vinNo =:vinNo";
		QueryParamLs pcarParams = new QueryParamLs();
		pcarParams.addParam("shipNo", jobject.get("shipNo").toString());
		pcarParams.addParam("billNo", jobject.get("billNo").toString());
		pcarParams.addParam("rfid", jobject.get("rfid").toString());
		pcarParams.addParam("vinNo", jobject.get("vinNo").toString());
		List<PortCar> pcarList = JpaUtils.findAll(jpqlpcar, pcarParams);

		BillCar bcList = new BillCar();
		String BillcarId = HdUtils.genUuid();
		bcList.setBillcarId(BillcarId);
		bcList.setShipbillId(sbList.get(0).getShipbillId());
		bcList.setPortCarNo(pcarList.get(0).getPortCarNo());
		bcList.setRfidCardNo(jobject.get("rfid").toString());
		bcList.setVinNo(jobject.get("vinNo").toString());
		bcList.setShipNo(jobject.get("shipNo").toString());
		bcList.setBillNo(jobject.get("billNo").toString());
		bcList.setiEId(sbList.get(0).getiEId());
		bcList.setTradeId(sbList.get(0).getTradeId());
		bcList.setCarTyp(sbList.get(0).getCarTyp());
		bcList.setBrandCod(sbList.get(0).getBrandCod());
		bcList.setCarKind(sbList.get(0).getCarKind());
		bcList.setRemarks("手持录入");
		bcList.setRecNam(Account);
		bcList.setRecTim((Timestamp) sysDate);
		bcList.setOverId(overId);
		JpaUtils.getBaseDao().save(bcList);

		WorkCommand wcList = new WorkCommand();
		wcList.setQueueId(HdUtils.genUuid());
		wcList.setWorkQueueNo(jobject.get("workQueueNo").toString());
		wcList.setPortCarNo(pcarList.get(0).getPortCarNo());
		wcList.setRfidCardNo(jobject.get("rfid").toString());
		wcList.setVinNo(jobject.get("vinNo").toString());
		wcList.setShipNo(jobject.get("shipNo").toString());
		wcList.setBillNo(jobject.get("billNo").toString());
		wcList.setWorkTyp("SI");
		wcList.setBrandCod(pcarList.get(0).getBrandCod()); // 车辆品牌
		wcList.setCarTyp(pcarList.get(0).getCarTyp()); // 车型
		wcList.setCarKind(pcarList.get(0).getCarKind()); // 车数类别
		// wcList.setDirectId(); // 直取直装
		wcList.setPlanPlac(jobject.get("planPlac").toString());
		wcList.setSendTim((Timestamp) sysDate);
		wcList.setSendNam(Account);
		wcList.setShipWorkNam(Account);
		wcList.setShipWorkTim((Timestamp) sysDate);
		wcList.setFinishedId("0");
		wcList.setDriver(jobject.get("driver").toString());
		wcList.setNightId((String) jobject.get("nightId"));
		wcList.setHolidayId((String)jobject.get("holidayId"));
		wcList.setLengthOverId((String)jobject.get("lengthOverId"));
		if(jobject.get("lengthOverId").toString().equals("1")) {
			BigDecimal overLength=new BigDecimal((String)jobject.get("length"));
			wcList.setLength(overLength);
		}
		wcList.setUseMachId(jobject.get("useMachId").toString());
		wcList.setUseWorkerId(jobject.get("useWorkerId").toString());
		// wcList.setRemarks(remarks);
		// wcList.setWidthOverId(widthOverId);
		JpaUtils.getBaseDao().save(wcList);

		// 如果残损信息不为空，更新port_car信息
		if (jobject.get("damCod") == null || jobject.get("damCod").toString().equals("")) {
		} else {
			// pcarList.get(0).setDamId("1");
			// pcLists.setDamCod(jobject.get("damCod").toString());
			// pcLists.setDamLevel(jobject.get("damLevel").toString());
			// pcLists.setDamArea(jobject.get("damArea").toString());
			// pcLists.setUpdNam(Account);
			// pcLists.setUpdTim((Timestamp) sysDate);
			String jpqlup = " update PortCar a set a.damId='1' , a.damLevel=:damLevel , a.damArea=:damArea , a.damCod=:damCod , a.updNam=:updNam , a.updTim=:updTim where a.rfidCardNo=:rfid ";
			QueryParamLs paramLsup = new QueryParamLs();
			paramLsup.addParam("damArea", jobject.get("damArea").toString());
			paramLsup.addParam("damLevel", jobject.get("damLevel").toString());
			paramLsup.addParam("damCod", jobject.get("damCod").toString());
			paramLsup.addParam("updNam", Account);
			paramLsup.addParam("updTim", sysDate);
			paramLsup.addParam("rfid", jobject.get("rfid").toString());
			JpaUtils.execUpdate(jpqlup, paramLsup);

			CarDamage cdlist = new CarDamage();
			cdlist.setCardamagId(HdUtils.genUuid());
			cdlist.setDamArea(jobject.get("damArea").toString());
			cdlist.setPortCarNo(pcarList.get(0).getPortCarNo());
			cdlist.setDamCod(jobject.get("damCod").toString());
			cdlist.setDamLevel(jobject.get("damLevel").toString());
			cdlist.setRecNam(Account);
			cdlist.setRecTim((Timestamp) sysDate);
			cdlist.setVinNo(jobject.get("vinNo").toString());
			cdlist.setIncharge((String) jobject.get("incharge"));
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
			JpaUtils.getBaseDao().save(cdlist);
		}
		String jpqlsi = "select a from Ship a where a.shipNo =:shipNo";
		QueryParamLs shipPar = new QueryParamLs();
		shipPar.addParam("shipNo", jobject.get("shipNo").toString());
		List<Ship> shipL = JpaUtils.findAll(jpqlsi, shipPar);
		if (shipL.size() > 0) {
			shipL.get(0).setDiscBegTim((Timestamp) sysDate);
			JpaUtils.getBaseDao().updateAll(shipL);
		}
	}

	@Override
	public StringBuffer shipBillData(String shipNo, String billNo) {
		StringBuffer result = new StringBuffer();
		// 该提单作业数
		String jpql = "select a from WorkCommand a where a.workTyp = 'SI' and a.billNo =:billNo and a.shipNo=:shipNo";
		QueryParamLs paramLs = new QueryParamLs();
		paramLs.addParam("shipNo", shipNo);
		paramLs.addParam("billNo", billNo);
		List<WorkCommand> wcNum = JpaUtils.findAll(jpql, paramLs);
		if (wcNum.size() == 0) {
			result.append("0" + "/");
		} else {
			result.append(wcNum.size() + "/");
		}

		// 该提单计划数
		String jpql0 = "select a from ShipBill a where a.iEId='I' and a.billNo =:billNo and  a.shipNo=:shipNo";
		QueryParamLs paramLs0 = new QueryParamLs();
		paramLs0.addParam("shipNo", shipNo);
		paramLs0.addParam("billNo", billNo);
		List<ShipBill> sbNum = JpaUtils.findAll(jpql0, paramLs0);
		if (sbNum.size() == 0) {
			result.append("0");
		} else {
			if (sbNum.get(0).getCarNum() == null || sbNum.get(0).getCarNum().equals("")) {
				result.append(sbNum.get(0).getCarNum().toString());
			} else {
				result.append("0");
			}
		}
		return result;
	}

	@Override
	public String checkRFID(String rfid) {
		String checkrfid = "0";
		String jpql = "select a from PortCar a where a.rfidCardNo =:rfid";
		QueryParamLs paramLs = new QueryParamLs();
		paramLs.addParam("rfid", rfid);
		List<PortCar> rfidList = JpaUtils.findAll(jpql, paramLs);
		if (rfidList.size() > 0) {
			checkrfid = "1";
		}
		return checkrfid;
	}

	/* (non-Javadoc)
	 * @see net.huadong.tech.wechat.service.ShipAnbianService#checkVIN(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public String checkVIN(String vin, String shipNo,String tradId,String directId) {

		JSONObject jobject =new  JSONObject();
		jobject.put("state", "1");//1表示成功 -1 表示失败
		String sql="select f_load_ship_check('"+tradId+"','"+shipNo+"','"+vin+"','"+directId+"') MESINFO from dual";
		List<Map> lst=JpaUtils.getEntityManager().createNativeQuery(sql).setHint(QueryHints.RESULT_TYPE, ResultType.Map).getResultList(); 
	  	Map mapMes=lst.get(0);
	  	String messInfo=mapMes.get("MESINFO")+"";
	  	if(messInfo.equals("ok")) {
	  		String message=checkVINNo(vin,shipNo);
	  		jobject.put("message", message);
	  	}else {
	  		jobject.put("state", "-1");//表示成功 -1 表示失败
	  		jobject.put("message", messInfo);
	  	}
		return jobject.toString();
	}
	@Override
	public String checkVIN(String vin, String shipNo) {
		return checkVINNo(vin,shipNo);
	}
	
	/**
	 * 1、目前只是 外贸
	 * 2、装卸船验证+参数返回
	 * 3、后期如果扩展需要请及时提交注释
	 */
	@Override
	public String checkVINHandling(String vin, String shipNo,String tradeId,String directId,String ieId) {
		String rtStr=null;
		//进口 卸船
		if(ieId.equals("I")) {
			rtStr=checkVINNoUnLoad(vin,shipNo);
		}else {//出口装船
			rtStr= checkVIN(vin,shipNo,tradeId,directId);
		}
		return rtStr;
	}
	
	
	/**
	 * 外贸装船的校验 返回前台的显示 billNo 品牌 车类
	 * 历史的校验也再此功能里，例如丰田那边也走这个方法
	 * @param vin
	 * @param shipNo
	 * @return
	 */
	private String checkVINNo(String vin, String shipNo) {
		//根据提单确认预置的车架号进行提单号关联
				String result = null;
				String jpql = "select a from BillCar a where 1=1 ";
				QueryParamLs paramLs = new QueryParamLs();
				if(HdUtils.strNotNull(vin)){
					jpql += "and a.vinNo like :vin ";
					paramLs.addParam("vin", "%" + vin );
				}
				if(HdUtils.strNotNull(shipNo)){
					jpql += "and a.shipNo =:shipNo ";
					paramLs.addParam("shipNo", shipNo );
				}
				
				List<BillCar> billCarList = JpaUtils.findAll(jpql, paramLs);
				if(billCarList.size()>0){
					BillCar billCar = billCarList.get(0);
					//result = billCar.getVinNo() + "/" + billCar.getBillNo();
					//车架号前八位解析品牌,车类,车型
					String substr8=billCar.getVinNo().substring(0,8);
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
						result = billCar.getVinNo() + "/" + billCar.getBillNo()+"/"+brandCod+"/"+carTyp+ "/" + carKind;
						}
					}else{
						result = billCar.getVinNo() + "/" + billCar.getBillNo() + "/" + billCar.getBrandCod() + "/" + billCar.getCarTyp() + "/" + billCar.getCarKind();	
					}
				}else{
					String billNo="";
					String brandCod = "";
					String carTyp = "";
					String carKind = "";
					String sqlBillNo="select a from PortCar a where a.vinNo =:vinNo and a.currentStat ='2'";
					QueryParamLs paramLs1 = new QueryParamLs();
					paramLs1.addParam("vinNo", vin);
					List<PortCar> lst=JpaUtils.findAll(sqlBillNo, paramLs1); 
					if(lst.size()>0) {
						billNo=lst.get(0).getBillNo()==null?"":lst.get(0).getBillNo()+"";
						brandCod=lst.get(0).getBrandCod()==null?"":lst.get(0).getBrandCod()+"";
						carTyp=lst.get(0).getCarTyp()==null?"":lst.get(0).getCarTyp()+"";
						carKind=lst.get(0).getCarKind()==null?"":lst.get(0).getCarKind()+"";
					}
					result = vin + "/" +billNo+"/"+brandCod+"/"+carTyp+"/"+carKind;
					if(vin.length() >= 8) {
						String substr8=vin.substring(0,8);
						String jpqla="select a from CCarVin a where a.vinNo=:vinNo ";
						QueryParamLs paramLsa = new QueryParamLs();
						paramLsa.addParam("vinNo",substr8);
						List<CCarVin> ccvList=JpaUtils.findAll(jpqla, paramLsa);
						if(ccvList.size()>0){
							CCarTyp cct=JpaUtils.findById(CCarTyp.class,ccvList.get(0).getCarTyp());
							brandCod=cct.getBrandCod();
							carTyp=cct.getCarTyp();
							carKind = cct.getCarKind();
							if(HdUtils.strNotNull(carTyp)){
								if (billNo.equals("")||billNo == null){
									billNo = "";
								}
							result = vin + "/" +billNo+"/"+brandCod+"/"+carTyp+"/"+carKind;
							}
						}
					}
				}
				return result;
	}
	/**
	 * 只有：外贸卸船的校验 返回前台的显示 billNo 品牌 车类
	 * @param vin
	 * @param shipNo
	 * @return
	 */
	private String checkVINNoUnLoad(String vin, String shipNo) {
		//根据提单确认预置的车架号进行提单号关联
				String result = null;
				String jpql = "select a from BillCar a where 1=1 ";
				QueryParamLs paramLs = new QueryParamLs();
				if(HdUtils.strNotNull(vin)){
					jpql += "and a.vinNo like :vin ";
					paramLs.addParam("vin", "%" + vin );
				}
				if(HdUtils.strNotNull(shipNo)){
					jpql += "and a.shipNo =:shipNo ";
					paramLs.addParam("shipNo", shipNo );
				}
				
				List<BillCar> billCarList = JpaUtils.findAll(jpql, paramLs);
				if(billCarList.size()>0){
					BillCar billCar = billCarList.get(0);
					//result = billCar.getVinNo() + "/" + billCar.getBillNo();
					//车架号前八位解析品牌,车类,车型
					String substr8=billCar.getVinNo().substring(0,8);
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
						result = billCar.getVinNo() + "/" + billCar.getBillNo()+"/"+brandCod+"/"+carTyp+ "/" + carKind;
						}
					}else{
						result = billCar.getVinNo() + "/" + billCar.getBillNo() + "/" + billCar.getBrandCod() + "/" + billCar.getCarTyp() + "/" + billCar.getCarKind();	
					}
				}else{
					String billNo="";
					String sqlBillNo=" select BILL_NO  from M_BILL_VIN where ship_no='"+shipNo+"' and  vin_no='"+vin+"' ";	

					List<Map> lst=JpaUtils.getEntityManager().createNativeQuery(sqlBillNo).setHint(QueryHints.RESULT_TYPE, ResultType.Map).getResultList(); 
					if(lst.size()>0) {
						billNo=lst.get(0).get("BILL_NO")==null?"":lst.get(0).get("BILL_NO")+"";
					}
					if(vin.length() == 17) {
						String substr8=vin.substring(0,8);
						String jpqla="select a from CCarVin a where a.vinNo=:vinNo ";
						QueryParamLs paramLsa = new QueryParamLs();
						paramLsa.addParam("vinNo",substr8);
						List<CCarVin> ccvList=JpaUtils.findAll(jpqla, paramLsa);
						if(ccvList.size()>0){
							CCarTyp cct=JpaUtils.findById(CCarTyp.class,ccvList.get(0).getCarTyp());
							String brandCod=cct.getBrandCod();
							String carTyp=cct.getCarTyp();
							String carKind = cct.getCarKind();
							if(HdUtils.strNotNull(carTyp)){
							result = vin + "/" +billNo+"/"+brandCod+"/"+carTyp+"/"+carKind;
							}
						}else{
							result= vin + "/"+billNo + "/ " + "/ " + "/ ";
						}
					}else {
						result= vin + "/"+billNo + "/ " + "/ " + "/ ";
					}
				}
				return result;
	}


	@Override
	public String checkcyPlac(String cyPlac, String shipNo) {
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
	public String checkVINbillCar(String vin, String shipNo) {
		String billCarError = "";
		String billCarJpql = "SELECT b FROM BillCar b where b.shipNo =:shipNo and b.vinNo =:vinNo and b.lhFlag = '1'";
		QueryParamLs billCarParams = new QueryParamLs();
		billCarParams.addParam("shipNo", shipNo);
		billCarParams.addParam("vinNo", vin);
		List<BillCar> billCarList = JpaUtils.findAll(billCarJpql, billCarParams);
		if(billCarList.size() > 0) {
			billCarError = "billCarError";
		}
		return billCarError;
	}

	public String checkFlow(String vin, String flow, String directid) {
		JSONObject jobject =new  JSONObject();
		String sql="select f_flow_check('"+vin+"','"+flow+"','"+directid+"') MESINFO from dual";
		List<Map> lst=JpaUtils.getEntityManager().createNativeQuery(sql).setHint(QueryHints.RESULT_TYPE, ResultType.Map).getResultList(); 
	  	Map mapMes=lst.get(0);
	  	String messInfo=mapMes.get("MESINFO")+"";
	  	jobject.put("messInfo", messInfo);
	  	return jobject.toString();
	}

	public String getCarInfo(String vin) {
		JSONObject jobject =new  JSONObject();
		String sql="select f_get_car_info('"+vin+"') MESINFO from dual";
		List<Map> lst=JpaUtils.getEntityManager().createNativeQuery(sql).setHint(QueryHints.RESULT_TYPE, ResultType.Map).getResultList(); 
		Map mapMes=lst.get(0);
		String messInfo=mapMes.get("MESINFO")+"";
		jobject.put("messInfo", messInfo);
		String aaa=jobject.toString();
		return aaa;
		//return messInfo;
		}
	
	public String countCar(String shipNo, String workTyp,  String brandcod, String cartyp) {
		JSONObject jobject =new  JSONObject();
		String sql="select f_get_car_count('"+shipNo+"','"+workTyp+"','"+brandcod+"','"+cartyp+"') MESINFO from dual";
		List<Map> lst=JpaUtils.getEntityManager().createNativeQuery(sql).setHint(QueryHints.RESULT_TYPE, ResultType.Map).getResultList(); 
		Map mapMes=lst.get(0);
		String messInfo=mapMes.get("MESINFO")+"";
		jobject.put("messInfo", messInfo);
		return jobject.toString();
		}
}
