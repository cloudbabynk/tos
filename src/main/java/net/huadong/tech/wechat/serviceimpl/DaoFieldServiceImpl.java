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

import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.tech.shipbill.entity.PortCar;
import net.huadong.tech.util.HdUtils;
import net.huadong.tech.wechat.service.DaoFieldService;
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
public class DaoFieldServiceImpl implements DaoFieldService {
	@Override
	public String checkdaofrfid(String rfid) {
		String result = "";
		String jpql = "SELECT m FROM MoveCarPlan m where m.rfidCardNo =:rfid";
		QueryParamLs mcpParams = new QueryParamLs();
		mcpParams.addParam("rfid", rfid);
		List<MoveCarPlan> mcpList = JpaUtils.findAll(jpql, mcpParams);
		if (mcpList.size() > 0) {
			BigDecimal potrCarNo = mcpList.get(0).getPortCarNo();
			String pcJpql = "SELECT p FROM PortCar p where p.portCarNo =:potrCarNo";
			QueryParamLs pcParams = new QueryParamLs();
			pcParams.addParam("potrCarNo", potrCarNo);
			List<PortCar> pcList = JpaUtils.findAll(pcJpql, pcParams);
			if (pcList.size() > 0) {
				result = pcList.get(0).getVinNo() + "&" + mcpList.get(0).getOldPlac() + "$"
						+ mcpList.get(0).getPlanPlac();
			}
		}
		return result;
	}

	@Override
	public String outfield(String req) {
		String result = "";
		JSONObject jobject = JSONObject.fromObject(req);
		Date sysDate = HdUtils.getDateTime();
		String Account = HdUtils.getCurUser().getAccount();
		String pcJpql = "SELECT p FROM PortCar p where p.rfidCardNo =:rfid";
		QueryParamLs pcParams = new QueryParamLs();
		pcParams.addParam("rfid", jobject.get("rfid"));
		List<PortCar> pcList = JpaUtils.findAll(pcJpql, pcParams);
		if (pcList.size() > 0) {
			if (pcList.get(0).getCurrentStat().equals("2")) {
				// 此车在场，继续
				// 验证新车位是否是当前的码头相符
				String tJpql = "select a.dockCod from CCyArea a, CCyBay b where a.cyAreaNo = b.cyAreaNo and b.cyPlac =:cyPlac";
				// QueryParamLs abParams = new QueryParamLs();
				// abParams.addParam("cyPlac", planPlac);
				List<Map<String, Object>> abList = JpaUtils.getEntityManager().createQuery(tJpql)
						.setParameter("cyPlac", jobject.get("cyPlac")).setHint(QueryHints.RESULT_TYPE, ResultType.Map)
						.getResultList();
				if (abList.size() > 0) {
					if (abList.get(0).get("dockCod").equals(pcList.get(0).getDockCod())) {
						// 直接更新port_car即可
						pcList.get(0).setCyPlac((String) jobject.get("cyPlac"));
						// pcList.get(0).setCyAreaNo(cyAreaNo);
						// pcList.get(0).setCyBayNo(cyBayNo);
						// pcList.get(0).setCyRowNo(cyRowNo);
						pcList.get(0).setUpdNam(Account);
						pcList.get(0).setUpdTim((Timestamp) sysDate);
						JpaUtils.getBaseDao().update(pcList.get(0));

						// 生成指令记录
						String abcJpql = "select a.workQueueNo, b.portCarNo, b.rfidCardNo, a.contractNo, c.brandCod, c.carTyp,"
								+ " c.carKind, b.planPlac, c.cyPlac, b.recTim, b.recNam from WorkQueue a, MoveCarPlan b, PortCar c where "
								+ "a.workTyp = 'MV' and a.contractNo = b.movePlanNo and b.portCarNo = c.portCarNo and b.rfidCardNo =:rfid";
						List<Map<String, Object>> abcList = JpaUtils.getEntityManager().createQuery(abcJpql)
								.setParameter("rfid", jobject.get("rfid"))
								.setHint(QueryHints.RESULT_TYPE, ResultType.Map).getResultList();
						if(abcList.size() > 0) {
							WorkCommand wcList = new WorkCommand();
							wcList.setQueueId(HdUtils.genUuid());
							wcList.setWorkQueueNo((String)abcList.get(0).get("workQueueNo"));
							wcList.setPortCarNo((BigDecimal) abcList.get(0).get("portCarNo"));
							wcList.setRfidCardNo((String) abcList.get(0).get("rfidCardNo"));
							wcList.setVinNo((String)jobject.get("vinNo"));
							wcList.setContractNo((String) abcList.get(0).get("contractNo"));
//							wcList.setBillNo(billNo);
							wcList.setWorkTyp("MV");
							wcList.setBrandCod((String)abcList.get(0).get("brandCod"));
							wcList.setCarTyp((String)abcList.get(0).get("carTyp"));
							wcList.setCarKind((String)abcList.get(0).get("carKind"));
							wcList.setPlanPlac((String)abcList.get(0).get("planPlac"));
							wcList.setCyPlac((String)abcList.get(0).get("cyPlac"));
							wcList.setSendTim((Timestamp) abcList.get(0).get("recTim"));
							wcList.setSendNam((String) abcList.get(0).get("recNam"));
							wcList.setOutCyTim((Timestamp) sysDate);
							wcList.setOutCyNam(Account);
							wcList.setInCyTim((Timestamp) sysDate);
							wcList.setInCyNam(Account);
							wcList.setFinishedId("1");
//							wcList.setNightId(nightId);
//							wcList.setHolidayId(abcJpql);
							wcList.setRemarks("手持录入");
//							wcList.setLengthOverId(lengthOverId);
//							wcList.setWidthOverId(widthOverId);
							JpaUtils.getBaseDao().save(wcList);
							
							//修改捣场计划状态
							String mcpJpql = "SELECT m FROM MoveCarPlan m where m.rfidCardNo =:rfid";
							QueryParamLs mcpParams = new QueryParamLs();
							mcpParams.addParam("rfid", jobject.get("rfid"));
							List<MoveCarPlan> mcpList = JpaUtils.findAll(mcpJpql, mcpParams);
							if(mcpList.size() > 0) {
								mcpList.get(0).setFinishedId("1");
								JpaUtils.getBaseDao().update(mcpList.get(0));
								MoveCarPlanBak mcpbList = new MoveCarPlanBak();
								BeanUtils.copyProperties(mcpList, mcpbList);
								String dmcpbJpql = "SELECT m FROM MoveCarPlan m where m.finishedId = '1' and m.rfidCardNo =:rfid";
								QueryParamLs dmcpParams = new QueryParamLs();
								dmcpParams.addParam("rfid", jobject.get("rfid"));
								List<MoveCarPlan> dmcpList = JpaUtils.findAll(dmcpbJpql, dmcpParams);
								JpaUtils.remove(dmcpList.get(0));
							}
							String wcyList = "SELECT w FROM WorkCommand w where w.rfidCardNo =:rfid";
							QueryParamLs wcyParams = new QueryParamLs();
							wcyParams.addParam("rfid", jobject.get("rfid"));
							List<WorkCommand> wcyLists = JpaUtils.findAll(wcyList, wcyParams);
							if(wcyLists.size() > 0) {
								WorkCommandBak wcbList = new WorkCommandBak();
								BeanUtils.copyProperties(wcyLists, wcbList);
								JpaUtils.remove(wcyLists.get(0));
							}
							String wqJpql = "SELECT w FROM WorkQueue w where w.workTyp = 'MV' and w.contractNo =:contractNo";
							QueryParamLs wqParams = new QueryParamLs();
							wqParams.addParam("contractNo", abcList.get(0).get("contractNo"));
							List<WorkQueue> wqList = JpaUtils.findAll(wqJpql, wqParams);
							JpaUtils.remove(wqList.get(0));
						}
					} else {
						// 报错：当前堆场与原车位不在一个堆场 停止
						result = "portError";
					}
				}
			} else {
				// 报错：RFID不存在，此车不在场。停止运行
				result = "rfidError";
			}
		}
		return result;
	}

}
