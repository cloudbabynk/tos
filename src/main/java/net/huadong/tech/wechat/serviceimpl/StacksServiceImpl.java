package net.huadong.tech.wechat.serviceimpl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.persistence.config.QueryHints;
import org.eclipse.persistence.config.ResultType;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import net.huadong.tech.base.entity.CCyBay;
import net.huadong.tech.damage.entity.CarDamage;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.tech.his.entity.PortCarBak;
import net.huadong.tech.privilege.entity.AuthOrgn;
import net.huadong.tech.shipbill.entity.ContractIeDoc;
import net.huadong.tech.shipbill.entity.PortCar;
import net.huadong.tech.util.CommonUtil;
import net.huadong.tech.util.HdUtils;
import net.huadong.tech.wechat.service.StacksService;
import net.huadong.tech.work.entity.WorkCommand;
import net.huadong.tech.work.entity.WorkCommandBak;
import net.sf.json.JSONObject;

@Component
public class StacksServiceImpl implements StacksService {

	@Override
	public Map<String, Object> checkshgRfid(String rfid, String billNo) {
		System.out.println(billNo);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rfidError", "0");
		map.put("vinNo", null);
		map.put("cyAreaNo", null);
		map.put("cyRowNo", null);
		map.put("cyBayNo", null);
		map.put("cyPlac", null);
		String pcJpql = "select a from PortCar a where a.rfidCardNo =:rfid";
		QueryParamLs pcParamLs = new QueryParamLs();
		pcParamLs.addParam("rfid", rfid);
		List<PortCar> pcList = JpaUtils.findAll(pcJpql, pcParamLs);
		if (pcList.size() > 0) {
			if (pcList.get(0).getCurrentStat().toString().equals("2")) {
				// 此车在场，继续
				if (pcList.get(0).getBillNo().toString().equals(billNo)) {
					// 提单号也匹配，继续
					map.put("vinNo", pcList.get(0).getVinNo());
					map.put("cyAreaNo", pcList.get(0).getCyAreaNo());
					map.put("cyRowNo", pcList.get(0).getCyRowNo());
					map.put("cyBayNo", pcList.get(0).getCyBayNo());
					map.put("cyPlac", pcList.get(0).getCyPlac());
				} else {
					// 报错：场地中车辆的提单号与委托不符，不能疏港。停止运行
					map.put("rfidError", "billNoError");
				}
			} else {
				// 报错：RFID不存在，此车不在场。停止运行
				map.put("rfidError", "rfidError");
			}
		}
		return map;
	}

	@Override
	public String checkcyPlacshg(String cyAreaNo, String cyRowNo, String cyBayNo, String contractNo) {
		String result = "";
		//String cyPlac = cyAreaNo.substring((cyAreaNo.length() - 2), cyAreaNo.length()) + cyRowNo + cyBayNo;
		String cidJpql = "select a from  ContractIeDoc a where a.contractNo =:contractNo";
		QueryParamLs cidParam = new QueryParamLs();
		cidParam.addParam("contractNo", contractNo);
		List<ContractIeDoc> cidList = JpaUtils.findAll(cidJpql, cidParam);
		String tJpql = "select a.dockCod from CCyArea a, CCyBay b where a.cyAreaNo = b.cyAreaNo and b.cyAreaNo =:cyAreaNo and b.cyRowNo=:cyRowNo and b.cyBayNo=:cyBayNo";
		List<Map<String, Object>> abList = JpaUtils.getEntityManager().createQuery(tJpql)
				.setParameter("cyAreaNo", cyAreaNo).setParameter("cyRowNo", cyRowNo).setParameter("cyBayNo", cyBayNo).setHint(QueryHints.RESULT_TYPE, ResultType.Map).getResultList();
		if(cidList.size() > 0 && abList.size() > 0) {
			if(cidList.get(0).getDockCod().toString().equals(abList.get(0).get("dockCod"))) {
				result = "true";
			} else {
				//报错：当前堆场与预约的堆场不符  停止
				result = "error";
			}
		}
		return result;
	}
	
	@Override
	public String checkVinsg(String vinNo, String billNo) {
		String result = "true";
		String pcJpql = "select a from PortCar a where a.vinNo =:vinNo";
		QueryParamLs pcParamLs = new QueryParamLs();
		pcParamLs.addParam("vinNo", vinNo);
		List<PortCar> pcList = JpaUtils.findAll(pcJpql, pcParamLs);
		if (pcList.size() > 0) {
			if (pcList.get(0).getCurrentStat().toString().equals("2")) {
				// 此车辆已经在场
				if (pcList.get(0).getBillNo().toString().equals(billNo)) {
					// 提单号也匹配，继续
					result = pcList.get(0).getRfidCardNo() + "&" + pcList.get(0).getCyPlac();
				} else {
					// 报错：场地中车辆的提单号与委托不符，不能疏港。停止运行
					result = "billNoError";
				}
			}
		}
		return result;
	}
	@Override
	public String shiploaderzhz(String req) {
		System.out.println(req);
		JSONObject jobject = JSONObject.fromObject(req);
		Date sysDate = HdUtils.getDateTime();
		String Account = (String) jobject.get("account");
		String cyAreaNo0 = String.valueOf(jobject.get("cyAreaNo"));
		String cyRowNo0 = String.valueOf(jobject.get("cyRowNo"));
		String cyBayNo0 = String.valueOf(jobject.get("cyBayNo"));
		String cyplac0 = cyAreaNo0.subSequence(cyAreaNo0.length()-2, cyAreaNo0.length()) + cyRowNo0 + cyBayNo0;
		// 先校验提单号、rfid不能为空 先校验rfid信息
		String result = "true";
		String pcJpql = "select a from PortCar a where a.rfidCardNo =:rfid";
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
						String jpqlpcar = "select a from PortCar a where a.rfidCardNo =:rfid and a.vinNo =:vinNo";
						QueryParamLs pcarParams = new QueryParamLs();
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
							cdlist.setIncharge((String)jobject.get("incharge"));
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
					}
					// 更新port_car
					String jpqlBl = " update PortCar a set a.damId= '0' , a.cyPlac=:cyPlac , a.cyAreaNo=:cyAreaNo , a.cyRowNo=:cyRowNo , a.cyBayNo=:cyBayNo, a.outCyTim=:outCyTim, a.updNam=:updNam , a.updTim=:updTim where a.rfidCardNo=:rfid";
					QueryParamLs paramLBl = new QueryParamLs();
					paramLBl.addParam("cyPlac", null);
					paramLBl.addParam("cyAreaNo", null);
					paramLBl.addParam("cyRowNo", null);
					paramLBl.addParam("cyBayNo", null);
					paramLBl.addParam("outCyTim", sysDate);
					paramLBl.addParam("updNam", Account);
					paramLBl.addParam("updTim", sysDate);
					paramLBl.addParam("rfid", jobject.get("rfid").toString());
					JpaUtils.execUpdate(jpqlBl, paramLBl);
					
					// 生成指令记录
					String pclJpql = "select a from PortCar a where a.rfidCardNo =:rfid and a.vinNo =:vinNo";
					QueryParamLs pclParams = new QueryParamLs();
					pclParams.addParam("rfid", jobject.get("rfid").toString());
					pclParams.addParam("vinNo", jobject.get("vinNo").toString());
					List<PortCar> pcarList = JpaUtils.findAll(pclJpql, pclParams);
					if (pcarList.size() > 0) {
						//疏港-解锁车位
						String arbJpql = "SELECT c FROM CCyBay c where c.cyAreaNo=:cyAreaNo and c.cyRowNo=:cyRowNo and c.cyBayNo=:cyBayNo";
						QueryParamLs arbParams = new QueryParamLs();
						arbParams.addParam("cyAreaNo", pcList.get(0).getCyAreaNo());
						arbParams.addParam("cyRowNo", pcList.get(0).getCyRowNo());
						arbParams.addParam("cyBayNo", pcList.get(0).getCyBayNo());
						List<CCyBay> arbList = JpaUtils.findAll(arbJpql, arbParams);
						if(arbList.size() > 0) {
							CCyBay cCyBayEntity = arbList.get(0);
							cCyBayEntity.setLockId("0");
							JpaUtils.getBaseDao().update(cCyBayEntity);
						}
						
						// 生成指令记录(转栈)
						//String platNo = CommonUtil.getId().substring(0,8);
						String pcWorkQueueNo = "SI" + "-" + String.valueOf(jobject.get("shipNo"));
						WorkCommand wcList = new WorkCommand();
						wcList.setQueueId(HdUtils.genUuid());
						wcList.setWorkQueueNo(pcWorkQueueNo);
						wcList.setPortCarNo(pcarList.get(0).getPortCarNo());
						wcList.setRfidCardNo(jobject.get("rfid").toString());
						wcList.setVinNo(jobject.get("vinNo").toString());
						wcList.setContractNo(jobject.get("contractNo").toString());
						wcList.setBillNo(jobject.get("vinNo").toString());
						wcList.setWorkTyp("TZ");
						wcList.setBrandCod(pcarList.get(0).getBrandCod());
						wcList.setCarTyp(pcarList.get(0).getCarTyp());
						wcList.setCarKind(pcarList.get(0).getCarKind());
						// 直装、直取(暂时不填)
						// wcList.setDirectId(directId);
						// wcList.setPlanPlac(planPlac);
						wcList.setCyPlac(cyplac0);
						wcList.setSendTim((Timestamp) sysDate);
						wcList.setSendNam(Account);
						wcList.setInCyTim((Timestamp) sysDate);
						wcList.setInCyNamNam(Account);
						wcList.setFinishedId("1");
//						wcList.setNightId(String.valueOf(jobject.get("nightId")));
//						wcList.setHolidayId(String.valueOf(jobject.get("holidayId")));
						wcList.setUseMachId(jobject.get("useMachId").toString());
						wcList.setUseWorkerId(jobject.get("useWorkerId").toString());
						wcList.setRemarks("手持录入");
//						wcList.setLengthOverId((String) jobject.get("lengthOverId"));
//						if (jobject.get("lengthOverId").toString().equals("1")) {
//							BigDecimal overLength = new BigDecimal((String) jobject.get("length"));
//							wcList.setLength(overLength);
//						}
						// wcList.setWidthOverId(widthOverId);
						JpaUtils.getBaseDao().save(wcList);
					}
					// 转栈出场，不经过闸口，所以车辆直接离港进入历史
					String pocaJpql = "select a from PortCar a where a.rfidCardNo =:rfid";
					QueryParamLs pocaParams = new QueryParamLs();
					pocaParams.addParam("rfid", jobject.get("rfid").toString());
					List<PortCar> pocaList = JpaUtils.findAll(pocaJpql, pcParamLs);
					if (pocaList.size() > 0) {
						PortCarBak pcb = new PortCarBak();
						BeanUtils.copyProperties(pcList.get(0), pcList);
						JpaUtils.getBaseDao().save(pcb);
						JpaUtils.remove(pocaList.get(0));
					}

					String wkcJpql = "select a from WorkCommand a where a.workTyp = 'TZ' and a.rfidCardNo =:rfid";
					QueryParamLs wkcParams = new QueryParamLs();
					wkcParams.addParam("rfid", jobject.get("rfid").toString());
					List<WorkCommand> wkcList = JpaUtils.findAll(wkcJpql, wkcParams);
					if (wkcList.size() > 0) {
						WorkCommandBak wcbList = new WorkCommandBak();
						BeanUtils.copyProperties(wkcList.get(0), wcbList);
						JpaUtils.getBaseDao().save(wcbList);
						JpaUtils.remove(wkcList.get(0));
					}

				}
			}
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

}
