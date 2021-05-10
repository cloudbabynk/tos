package net.huadong.tech.ship.service.impl;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.persistence.config.QueryHints;
import org.eclipse.persistence.config.ResultType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.alibaba.fastjson.JSONObject;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.Interface.entity.MFeeInterfaceBerth;
import net.huadong.tech.Interface.entity.MFeeInterfaceVoyage;
import net.huadong.tech.Interface.entity.ShipPlanSend;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.bean.HdRunTimeException;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.tech.privilege.entity.AuthUser;
import net.huadong.tech.ship.entity.Ship;
import net.huadong.tech.ship.service.ShipPlanSendService;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;
import net.huadong.tech.util.HdUtils;
/**
 * @author
 */
@Component
public class ShipPlanSendServiceImpl implements ShipPlanSendService {
	
	@Value("${tjgjt.service.ip}")
	private String tjgjtServiceIp;
	
	@Override
	public HdEzuiDatagridData find(HdQuery hdQuery) {
		String jpql = "select a from ShipPlanSend a where 1=1 ";
		QueryParamLs paramLs = new QueryParamLs();
		String workDate = hdQuery.getStr("workDte");
		String shiftCode = hdQuery.getStr("shiftCode");
		String unloadFlag = hdQuery.getStr("unloadFlag");
		String shipId=  hdQuery.getStr("shipId");
		
		if (HdUtils.strNotNull(shipId)){
			jpql += "and a.shipId =:shipId ";
			paramLs.addParam("shipId", shipId);
		}
		if (HdUtils.strNotNull(shiftCode)){
			jpql += "and a.shiftCode =:shiftCode ";
			paramLs.addParam("shiftCode", shiftCode);
		}
		if (HdUtils.strNotNull(unloadFlag)){
			jpql += "and a.unloadFlag =:unloadFlag ";
			paramLs.addParam("unloadFlag", unloadFlag);
		}
		if (HdUtils.strNotNull(workDate)){
			jpql += "and a.workDate =:workDate ";
			paramLs.addParam("workDate", HdUtils.strToDate(workDate));
		}
		
		jpql += "order by a.recTim desc";
		HdEzuiDatagridData result = JpaUtils.findByEz(jpql, paramLs, hdQuery);
		List<ShipPlanSend> shipList = result.getRows();
		for (ShipPlanSend ship : shipList) {
		}
		return result;
	}
	@Override
	public HdMessageCode save(HdEzuiSaveDatagridData<ShipPlanSend> hdEzuiSaveDatagridData) {
		List<ShipPlanSend> insertList = hdEzuiSaveDatagridData.getInsertedRows();
		for(ShipPlanSend bean : insertList){
			bean.setSpsendId(HdUtils.genUuid());
			if (HdUtils.strNotNull(bean.getShipNo())){
				Ship ship = JpaUtils.findById(Ship.class, bean.getShipNo());
				if (ship != null && HdUtils.strNotNull(ship.getShipTyp())){
					if("2500".equals(ship.getShipTyp()) && "1".equals(bean.getUnloadFlag())){ //滚装船
						bean.setCabinNo("滚卸装");
					} else if ("2500".equals(ship.getShipTyp()) && "2".equals(bean.getUnloadFlag())){
						bean.setCabinNo("滚装");
					} else if ("2800".equals(ship.getShipTyp()) && "1".equals(bean.getUnloadFlag())){
						bean.setCabinNo("管卸");
					} else if ("2800".equals(ship.getShipTyp()) && "2".equals(bean.getUnloadFlag())){
						bean.setCabinNo("管装");
					}
				}
			}
		}
		return JpaUtils.save(hdEzuiSaveDatagridData);
	}
	@Transactional
	public void removeAll(String spsendIds) {
		List<String> spsendIdList = HdUtils.paraseStrs(spsendIds);
		for (String spsendId : spsendIdList) {
			JpaUtils.remove(ShipPlanSend.class, spsendId);
		}
	}
	@Override
	public HdMessageCode saveVoyage(HdEzuiSaveDatagridData<MFeeInterfaceVoyage> hdEzuiSaveDatagridData) {
		// TODO Auto-generated method stub
	return JpaUtils.save(hdEzuiSaveDatagridData);
	}
	/*@Override
	public HdMessageCode saveBerth(HdEzuiSaveDatagridData<MFeeInterfaceBerth> hdEzuiSaveDatagridData) {
		// TODO Auto-generated method stub
		return JpaUtils.save(hdEzuiSaveDatagridData); 
	}*/
	@Override
	@Transactional
	public void uploadAll(String spsendIds) {
		List<String> spsendIdList = HdUtils.paraseStrs(spsendIds);
		for (String spsendId: spsendIdList) {
			upload(spsendId);
		}
	}
	
	public void upload(String spsendId) {
		ShipPlanSend bean = JpaUtils.findById(ShipPlanSend.class, spsendId);
		JSONObject jsonObj = new JSONObject();
		Map<String,String> map=new HashMap<String, String>();
		Ship ship = JpaUtils.findById(Ship.class, bean.getShipNo());
		jsonObj.put("cmdId", "2009");
		jsonObj.put("coId",ship.GZ);	
		map.put("teamOrgnId",ship.GZ);
		map.put("workDate",bean.getWorkDate().toString().substring(0,10));
		map.put("shiftCode", bean.getShiftCode());
		map.put("shipName", ship.getcShipNam());
		map.put("shipId", bean.getShipId());
		map.put("svoyageId", ship.getNewGroupShipNo());
		map.put("spsendId", bean.getSpsendId());
		if ("1".equals(bean.getUnloadFlag())){
			map.put("unloadFlag", "-");
		}else if ("2".equals(bean.getUnloadFlag())){
			map.put("unloadFlag", "+");
		}
		map.put("planWgt", bean.getPlanWgt().toString());
		map.put("surpWgt", bean.getSurpWgt().toString());
		map.put("yardWgt", bean.getYardWgt().toString());
		map.put("workWgt", bean.getWorkWgt().toString());
		map.put("planCarNum", bean.getSurpWgt().toString());
		map.put("surpCarNum", bean.getSurpWgt().toString());
		map.put("yardCarNum", bean.getSurpWgt().toString());
		map.put("workCarNum", bean.getSurpWgt().toString());
		map.put("description", bean.getSurpWgt().toString());
		map.put("submitFlag", "0");
		String jpql1 = "select a from AuthUser a where a.account =:account";
		QueryParamLs paramLs1 = new QueryParamLs();
		paramLs1.addParam("account", bean.getRecNam());
		List<AuthUser> authUserList = JpaUtils.findAll(jpql1, paramLs1);
		for (AuthUser authUser : authUserList) {
				map.put("submitName", authUser.getName());
		}
		map.put("submitTime", bean.getRecTim().toString().substring(0, 19));
		jsonObj.put("data", map);
		
		String url = tjgjtServiceIp + "8081/inface/company/upload";
		String query = jsonObj.toString();
		
		String response = "";
		try {
			URL httpUrl = null; // HTTP URL类 用这个类来创建连接
			// 创建URL
			httpUrl = new URL(url);
			// 建立连接
			HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.connect();
			// POST请求
			try (OutputStream os = conn.getOutputStream()) {
				os.write(query.getBytes("UTF-8"));
			}
			// out.flush();
			// 读取响应
			try (BufferedReader reader = new BufferedReader(
					new InputStreamReader(conn.getInputStream()))) {
				String lines;
				StringBuffer sbf = new StringBuffer();
				while ((lines = reader.readLine()) != null) {
					lines = new String(lines.getBytes(), "utf-8");
					sbf.append(lines);
				}
				response = sbf.toString();
				JSONObject jsonObject = JSONObject.parseObject(response);
				String resCode ="0000";
				String resMsg = "OK";
				if (resCode.equals(jsonObject.getString("resCode"))&&resMsg.equals(jsonObject.getString("resMsg"))) {
					throw new HdRunTimeException("上报集团成功！");
				}
				if(!resCode.equals(jsonObject.getString("resCode"))||!resMsg.equals(jsonObject.getString("resMsg"))) {
					throw new HdRunTimeException(jsonObject.getString("resMsg"));
				}
			} catch (Exception e) {
				// System.out.println("上报计费数据异常！" + e);
			}
			// 断开连接
			conn.disconnect();
		} catch (Exception e) {
			// System.out.println("发送 POST 请求出现异常！" + e);
			// e.printStackTrace();
			throw new HdRunTimeException("发送 POST 请求出现异常!");
		}
	}
	
	@Override
	public ShipPlanSend findone(String shipCodId) {
		ShipPlanSend bean = JpaUtils.findById(ShipPlanSend.class, shipCodId);
		return bean;
	}
	@Override
	@Transactional
	public HdMessageCode saveone(@RequestBody ShipPlanSend bean) {
		if (HdUtils.strNotNull(bean.getSpsendId())) {
			JpaUtils.update(bean);
		} else {
			bean.setSpsendId(HdUtils.genUuid());
			JpaUtils.save(bean);
		}
		return HdUtils.genMsg();
	}
	@Override
	public HdEzuiDatagridData findVoyage(HdQuery hdQuery) {
		QueryParamLs paramLs = new QueryParamLs();
		String jpql = "SELECT c FROM MFeeInterfaceVoyage c where 1=1 ";
		String shipNo = hdQuery.getStr("shipNo");
		if(HdUtils.strNotNull(shipNo)){
			jpql += "and c.vesselvisitid =:shipNo ";
			paramLs.addParam("shipNo", shipNo);
		}
		HdEzuiDatagridData result = JpaUtils.findByEz(jpql, paramLs, hdQuery);
		return result;
	}
	@Override
	public HdMessageCode procYundi(String ydId) {
		HdMessageCode result1 = new HdMessageCode();
		String outinfo = "";
		QueryParamLs paramLs = new QueryParamLs();
		String jpql = "select f_get_ship_imo('"+ydId+"') info from dual ";
		List<Map> lstPortCar=JpaUtils.getEntityManager().createNativeQuery(jpql).setHint(QueryHints.RESULT_TYPE, ResultType.Map).getResultList();
		outinfo = lstPortCar.get(0).get("INFO").toString();
		result1.setCode(outinfo);
		return result1;
	}
	@Override
	public HdEzuiDatagridData findBerth(HdQuery hdQuery) {
		QueryParamLs paramLs = new QueryParamLs();
		String jpql = "SELECT c FROM MFeeInterfaceBerth c where 1=1 ";
		String shipNo = hdQuery.getStr("shipNo");
		if(HdUtils.strNotNull(shipNo)){
			jpql += "and c.vesselvisitid =:shipNo ";
			paramLs.addParam("shipNo", shipNo);
		}
		HdEzuiDatagridData result = JpaUtils.findByEz(jpql, paramLs, hdQuery);
		return result;
	}
	@Transactional
	@Override
	public HdMessageCode procVoyage(String shipNo, String iEId) {
			HdMessageCode result1 = new HdMessageCode();
			String outinfo = "";
			List<Object> inParamLs = new ArrayList<Object>();
			inParamLs.add(shipNo);//过程参数值
	        inParamLs.add(iEId);//过程参数值
	        inParamLs.add(HdUtils.getCurUser().getAccount());//过程参数值
	        inParamLs.add(HdUtils.getCurUser().getName());//过程参数值
			List<String> result = new ArrayList<String>();//过程返回值
			JpaUtils.executeOracleProcWithResult("P_FEE_INTERFACE_VOYAGE", inParamLs, result, inParamLs.size()+1);
			outinfo = result.get(0);
			result1.setCode(outinfo);
			return result1;
	} 
	@Transactional
	@Override
	public HdMessageCode procBerth(String shipNo, String iEId) {
		HdMessageCode result1 = new HdMessageCode();
		String outinfo = "";
		List<Object> inParamLs = new ArrayList<Object>();
		inParamLs.add(shipNo);//过程参数值
		inParamLs.add(iEId);//过程参数值
		inParamLs.add(HdUtils.getCurUser().getAccount());//过程参数值
		inParamLs.add(HdUtils.getCurUser().getName());//过程参数值
		List<String> result = new ArrayList<String>();//过程返回值
		JpaUtils.executeOracleProcWithResult("P_FEE_INTERFACE_BERTH", inParamLs, result, inParamLs.size()+1);
		outinfo = result.get(0);
		result1.setCode(outinfo);
		return result1;
	}
	@Override
	public HdMessageCode saveBerth(HdEzuiSaveDatagridData<MFeeInterfaceBerth> gridData) {
		// TODO Auto-generated method stub
		return JpaUtils.save(gridData);
	} 
}
