package net.huadong.tech.ship.service.impl;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONObject;

import net.huadong.tech.Interface.entity.VGroupCorpShip;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.bean.HdRunTimeException;
import net.huadong.tech.base.entity.CBerth;
import net.huadong.tech.base.entity.CClientCod;
import net.huadong.tech.base.entity.CDamage;
import net.huadong.tech.base.entity.CPort;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.tech.privilege.entity.AuthUser;
import net.huadong.tech.ship.entity.CShipData;
import net.huadong.tech.ship.entity.DayNightPlan;
import net.huadong.tech.ship.entity.Ship;
import net.huadong.tech.ship.entity.ShipStat;
import net.huadong.tech.ship.service.DayNightPlanService;
import net.huadong.idev.hdmessagecode.HdMessageCode;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiQueryParams;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;
import net.huadong.tech.util.HdUtils;

/**
 * @author 
 */
@Component
public class DayNightPlanServiceImpl implements DayNightPlanService {
	@Value("${tjgjt.service.ip}")
	private String tjgjtServiceIp;
	
	@Override
	public HdEzuiDatagridData find(HdQuery hdQuery) {
		String jpql="select a from DayNightPlan a where 1=1 ";
		String shipNo = hdQuery.getStr("shipNo");
		String days = hdQuery.getStr("days");
		QueryParamLs paramLs = new QueryParamLs();
		if(HdUtils.strNotNull(shipNo)){
			jpql += "and a.shipNo =:shipNo ";
			paramLs.addParam("shipNo", shipNo);
		}
		if(HdUtils.strNotNull(days)){
			jpql += "and a.days =:days ";
			paramLs.addParam("days", HdUtils.strToDate(days));
		}
		jpql += "order by a.recTim desc";
		return JpaUtils.findByEz(jpql, paramLs, hdQuery);
	}

	@Override
	public HdMessageCode save(HdEzuiSaveDatagridData<DayNightPlan> hdEzuiSaveDatagridData,String days,String shipNo,String dockCod) {
		// TODO Auto-generated method stub
		List<DayNightPlan> insertList = hdEzuiSaveDatagridData.getInsertedRows();
		for(DayNightPlan dayNightPlan : insertList){
			dayNightPlan.setPlanId(HdUtils.genUuid());
			dayNightPlan.setDays(HdUtils.strToDate(days));
			if(HdUtils.strIsNull(dayNightPlan.getDockCod())){
				dayNightPlan.setDockCod(dockCod);
			}
			if(HdUtils.strIsNull(dayNightPlan.getShipNo())){
				dayNightPlan.setShipNo(shipNo);
			}
			if (dayNightPlan.getClass20UnloadWorkNum() != null){
				dayNightPlan.setClass20WorkNum(dayNightPlan.getClass20UnloadWorkNum().add(dayNightPlan.getClass20LoadWorkNum()));
			}else{
				dayNightPlan.setClass20WorkNum(dayNightPlan.getClass20LoadWorkNum());
			}
			
			if (dayNightPlan.getClass08UnloadWorkNum() != null){
				dayNightPlan.setClass08WorkNum(dayNightPlan.getClass08UnloadWorkNum().add(dayNightPlan.getClass08LoadWorkNum()));
			}else{
				dayNightPlan.setClass08WorkNum(dayNightPlan.getClass08LoadWorkNum());
			}
		}
		
		List<DayNightPlan> updateList = hdEzuiSaveDatagridData.getUpdatedRows();
		for(DayNightPlan dayNightPlan : updateList){
			if (dayNightPlan.getClass20UnloadWorkNum() != null){
				dayNightPlan.setClass20WorkNum(dayNightPlan.getClass20UnloadWorkNum().add(dayNightPlan.getClass20LoadWorkNum()));
			}else{
				dayNightPlan.setClass20WorkNum(dayNightPlan.getClass20LoadWorkNum());
			}
			
			if (dayNightPlan.getClass08UnloadWorkNum() != null){
				dayNightPlan.setClass08WorkNum(dayNightPlan.getClass08UnloadWorkNum().add(dayNightPlan.getClass08LoadWorkNum()));
			}else{
				dayNightPlan.setClass08WorkNum(dayNightPlan.getClass08LoadWorkNum());
			}
		}
		return JpaUtils.save(hdEzuiSaveDatagridData);
	}

	@Transactional
	public void removeAll(String planIds) {
		List<String> planIdList = HdUtils.paraseStrs(planIds);
		for (String planId : planIdList) {
			JpaUtils.remove(DayNightPlan.class, planId);
		}
	}
	
	@Override
	public DayNightPlan findone(String planId) {
		DayNightPlan dayNightPlan = JpaUtils.findById(DayNightPlan.class, planId);
		return dayNightPlan;

	}
	
	@Override
	@Transactional
	public HdMessageCode saveone(@RequestBody DayNightPlan dayNightPlan) {
		if(HdUtils.strNotNull(dayNightPlan.getPlanId())){
			JpaUtils.update(dayNightPlan);
		}else{
//			if (isNOtExist(ship.getIvoyage(),"")) {
//				throw new HdRunTimeException("进口航次已存在");// 进口航次重复
//			}
//			if (isNOtExist("",ship.getEvoyage())) {
//				throw new HdRunTimeException("出口航次已存在");// 出口航次重复
//			}
			JpaUtils.save(dayNightPlan);
		}
		return HdUtils.genMsg();
	}
	
	private boolean isNOtExist(String ivoyage,String evoyage) {
		String jpql = "select a from Ship a where 1=1 ";
		QueryParamLs paramLs = new QueryParamLs();
		if(HdUtils.strNotNull(ivoyage)){
			jpql += "and a.ivoyage =:ivoyage";
			paramLs.addParam("ivoyage", ivoyage);
		}
		if(HdUtils.strNotNull(evoyage)){
			jpql += "and a.evoyage =:evoyage";
			paramLs.addParam("evoyage", evoyage);
		}
		List<Ship> ShipList = JpaUtils.findAll(jpql, paramLs);
		if (ShipList.size() > 0) {
			return true;
		}
		return false;
	}
	
	private boolean isExist(String shipCodId) {
		String jpql = "select a from Ship a where a.shipCodId =:shipCodId ";
		QueryParamLs paramLs = new QueryParamLs();
		paramLs.addParam("shipCodId", shipCodId);
		List<Ship> ShipList = JpaUtils.findAll(jpql, paramLs);
		if (ShipList.size() > 0) {
			return true;
		}
		return false;
	}
	
	@Transactional
	public void uploadDayNightPlans(String planIds) {
		List<String> dayNightPlanIdList = HdUtils.paraseStrs(planIds);
		for (String planId : dayNightPlanIdList) {
			DayNightPlan dayNightPlan = JpaUtils.findById(DayNightPlan.class, planId);
			if (dayNightPlan != null){
				uploadDayNightPlan(dayNightPlan,"20-08",Ship.JK);
				uploadDayNightPlan(dayNightPlan,"20-08",Ship.CK);
				uploadDayNightPlan(dayNightPlan,"08-20",Ship.JK);
				uploadDayNightPlan(dayNightPlan,"08-20",Ship.CK);
			}else{
				throw new HdRunTimeException("昼夜计划信息有误！");
			}
			
		}
	}
	
	public void uploadDayNightPlan(DayNightPlan bean,String workRun, String iEId) {
		JSONObject jsonObj = new JSONObject();
		Map<String,String> map=new HashMap<String, String>();
		Ship ship = JpaUtils.findById(Ship.class, bean.getShipNo());
		if (HdUtils.strIsNull(ship.getNewGroupShipNo())){
			throw new HdRunTimeException("航次预报还没有跟新集团调度系统对接，请先对接！");
		}
		jsonObj.put("cmdId", "2006");
		jsonObj.put("coId",ship.GZ);	
		map.put("teamOrgnId", ship.GZ);
		SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd ");
		map.put("planDate",format2.format(bean.getDays()));
		
		if ("20-08".equals(workRun)){
			map.put("shiftCode", "2");
			if (ship.JK.equals(iEId)){
				map.put("planCarNum", bean.getClass08LoadWorkNum().toString());
			}else {
				map.put("planCarNum", bean.getClass08UnloadWorkNum().toString());
			}
		}else {
			map.put("shiftCode", "1");
			if (ship.JK.equals(iEId)){
				map.put("planCarNum", bean.getClass20LoadWorkNum().toString());
			}else {
				map.put("planCarNum", bean.getClass20UnloadWorkNum().toString());
			}
		}
		map.put("scplanId",bean.getPlanId().substring(0, 30) + map.get("shiftCode") + iEId);
		map.put("svoyageId", ship.getNewGroupShipNo());
		if (ship.JK.equals(iEId)){
			map.put("shipId", ship.getNewIShipId());
			map.put("unloadFlag","-");
		}else {
			map.put("shipId", ship.getNewEShipId());
			map.put("unloadFlag","+");
		}
	
		map.put("submitFlag", "0");
		String jpql = "select a from AuthUser a where a.account =:account";
		QueryParamLs paramLs = new QueryParamLs();
		paramLs.addParam("account", bean.getRecNam());
		List<AuthUser> authUserList = JpaUtils.findAll(jpql, paramLs);
		for (AuthUser authUser : authUserList) {
			map.put("submitName", authUser.getName());
		}
		map.put("submitTime", bean.getRecTim().toString().substring(0, 16));
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
			try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
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

}

