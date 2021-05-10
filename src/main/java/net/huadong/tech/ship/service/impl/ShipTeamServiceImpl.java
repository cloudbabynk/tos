package net.huadong.tech.ship.service.impl;
import com.alibaba.dubbo.config.annotation.Service;
import net.huadong.tech.base.bean.EzTreeBean;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.bean.HdRunTimeException;
import net.huadong.tech.base.entity.CBerth;
import net.huadong.tech.base.entity.CClientCod;
import net.huadong.tech.base.entity.CDamage;
import net.huadong.tech.base.entity.CPort;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.tech.inter.entity.RespInter;
import net.huadong.tech.privilege.entity.AuthUser;
import net.huadong.tech.ship.entity.CShipData;
import net.huadong.tech.ship.entity.Ship;
import net.huadong.tech.Interface.entity.ShipTeam;
import net.huadong.tech.Interface.entity.VGroupCorpShip;
import net.huadong.tech.Interface.entity.VGroupCorpShipTeam;
import net.huadong.tech.ship.service.ShipTeamService;
import net.huadong.tech.shipbill.entity.ShipBill;
import net.huadong.idev.hdmessagecode.HdMessageCode;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
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
import com.alibaba.fastjson.JSONObject;
/**
 * @author
 */
@Component
public class ShipTeamServiceImpl implements ShipTeamService {
	
	@Value("${tjgjt.service.ip}")
	private String tjgjtServiceIp;
	
	@Override
	public HdEzuiDatagridData find(HdQuery hdQuery) {
		String jpql = "select a from ShipTeam a where 1=1 ";
		QueryParamLs paramLs = new QueryParamLs();
		HdEzuiDatagridData result = JpaUtils.findByEz(jpql, paramLs, hdQuery);
		List<ShipTeam> shipTeamList = result.getRows();
		for(ShipTeam bean : shipTeamList){
			VGroupCorpShip vGroupCorpShip = JpaUtils.findById(VGroupCorpShip.class, bean.getShipId());
			if (vGroupCorpShip != null){
				bean.setShipName(vGroupCorpShip.getShipName());
				bean.setVoyage(vGroupCorpShip.getVoyage());
				bean.setIeFlagNam(HdUtils.getSysCodeName("I_E_ID", bean.getIeFlag()));
			}
		}
		return result;
	}
	
	@Override
	public HdEzuiDatagridData findTeamJt(HdQuery hdQuery) {
		String jpql = "select a from VGroupCorpShipTeam a where a.teamOrgnId in ('03406500','03409000') and a.quickDate is not null order by a.quickDate desc";
		QueryParamLs paramLs = new QueryParamLs();
		HdEzuiDatagridData result = JpaUtils.findByEz(jpql, paramLs, hdQuery);
		List<VGroupCorpShipTeam> vGroupCorpShipTeamList = result.getRows();
		List<ShipTeam> shipTeamList = new ArrayList();
		for(VGroupCorpShipTeam bean : vGroupCorpShipTeamList){
			if (HdUtils.strNotNull(bean.getShipId())){
				VGroupCorpShip vGroupCorpShip = JpaUtils.findById(VGroupCorpShip.class, bean.getShipId());
				if (vGroupCorpShip != null){
				   ShipTeam shipTeam = new ShipTeam();
				   shipTeam.setSteamId(bean.getSteamId());
				   shipTeam.setSvoyageId(bean.getSvoyageId());
				   shipTeam.setShipId(bean.getShipId());
				   shipTeam.setTradeFlag(bean.getTradeFlag());
				   shipTeam.setShipName(vGroupCorpShip.getShipName());
				   shipTeam.setVoyage(vGroupCorpShip.getVoyage());
				   shipTeam.setIeFlag(bean.getIeFlag());
				   shipTeam.setIeFlagNam(HdUtils.getSysCodeName("I_E_ID", bean.getIeFlag()));
				   shipTeam.setTeamOrgnId(bean.getTeamOrgnId());
				   shipTeamList.add(shipTeam);
				}
			}
		}
		HdEzuiDatagridData resultFinal = new HdEzuiDatagridData();
		resultFinal.setRows(shipTeamList);
		return resultFinal;
	}
	@Override
	public HdMessageCode save(HdEzuiSaveDatagridData<ShipTeam> hdEzuiSaveDatagridData) {
		List<ShipTeam> insertList = hdEzuiSaveDatagridData.getInsertedRows();
		for(ShipTeam bean : insertList){
			bean.setSteamId(HdUtils.genUuid());
			if (HdUtils.strNotNull(bean.getSvoyageId())){
				String jpql = "select a from Ship a where a.newGroupShipNo =:newGroupShipNo";
				QueryParamLs paramLs = new QueryParamLs();
				paramLs.addParam("newGroupShipNo", bean.getSvoyageId());
				List<Ship> shipList = JpaUtils.findAll(jpql, paramLs);
				if (shipList.size() > 0){
					bean.setShipNo(shipList.get(0).getShipNo());
				}else {
					throw new HdRunTimeException("请先在船舶航次功能里与新局调建立联系！");
				}
			}
		}
		return JpaUtils.save(hdEzuiSaveDatagridData);
	}
	@Transactional
	public void removeAll(String steamIds) {
		List<String> steamIdList = HdUtils.paraseStrs(steamIds);
		for (String steamId : steamIdList) {
			JpaUtils.remove(ShipTeam.class, steamId);
		}
	}
	
	@Override
	@Transactional
	public void uploadAll(String steamIds) {
		List<String> steamIdList = HdUtils.paraseStrs(steamIds);
		for (String steamId: steamIdList) {
			upload(steamId);
		}
	}
	
	public void upload(String steamId) {
		ShipTeam bean = JpaUtils.findById(ShipTeam.class, steamId);
		JSONObject jsonObj = new JSONObject();
		Map<String,String> map=new HashMap<String, String>();
		Ship ship = JpaUtils.findById(Ship.class, bean.getShipNo());
		jsonObj.put("cmdId", "2013");
		jsonObj.put("coId",ship.GZ);	
		map.put("teamOrgnId", ship.GZ);
		map.put("steamId", bean.getSteamId());
		map.put("shipId",bean.getShipId());
		map.put("svoyageId",bean.getSvoyageId());
		map.put("startTime",bean.getStartTime().toString().substring(0, 16));
		map.put("endTime", bean.getEndTime().toString().substring(0, 16));
		map.put("workWgt", bean.getWorkWgt().toString());
		map.put("cntrNum", bean.getCntrNum().toString());
		map.put("carNum", bean.getCarNum().toString());
		map.put("stdcarNum", bean.getStdcarNum().toString());
		map.put("planHour", bean.getPlanHour().toString());
		map.put("preTtlId", bean.getPreTtlFlag());
		map.put("thruputName", bean.getRecNam());
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
	public ShipTeam findone(String steamId) {
		ShipTeam bean = JpaUtils.findById(ShipTeam.class, steamId);
		return bean;
	}
	@Override
	@Transactional
	public HdMessageCode saveone(@RequestBody ShipTeam bean) {
		if (HdUtils.strNotNull(bean.getSteamId())) {
			JpaUtils.update(bean);
		} else {
			bean.setSteamId(HdUtils.genUuid());
			JpaUtils.save(bean);
		}
		return HdUtils.genMsg();
	}
}
