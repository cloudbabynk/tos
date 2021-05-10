package net.huadong.tech.ship.service.impl;
import com.alibaba.dubbo.config.annotation.Service;

import net.huadong.tech.Interface.entity.ShipPlanSend;
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
import net.huadong.tech.ship.entity.ShipReport;
import net.huadong.tech.ship.entity.ShipLoadPlan;
import net.huadong.tech.ship.service.ShipReportService;
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
import net.sf.json.JSONObject;
/**
 * @author
 */
@Component
public class ShipReportServiceImpl implements ShipReportService {
	@Value("${tjgjt.service.ip}")
	private String tjgjtServiceIp;
	
	@Override
	public HdEzuiDatagridData find(HdQuery hdQuery) {
		String jpql = "select a from ShipReport a where 1=1 ";
		QueryParamLs paramLs = new QueryParamLs();
		jpql += "order by a.recTim desc";
		HdEzuiDatagridData result = JpaUtils.findByEz(jpql, paramLs, hdQuery);
		List<ShipReport> shipList = result.getRows();
		for (ShipReport ship : shipList) {
		}
		return result;
	}
	@Override
	public HdMessageCode save(HdEzuiSaveDatagridData<ShipReport> hdEzuiSaveDatagridData) {
		List<ShipReport> insertList = hdEzuiSaveDatagridData.getInsertedRows();
		for(ShipReport shipReport : insertList){
			shipReport.setShipRptId(HdUtils.genUuid());
		}
		return JpaUtils.save(hdEzuiSaveDatagridData);
	}
	@Transactional
	public void removeAll(String shipRptIds) {
		List<String> shipNoList = HdUtils.paraseStrs(shipRptIds);
		for (String shipRptId : shipNoList) {
			JpaUtils.remove(ShipReport.class, shipRptId);
		}
	}
	
	@Override
	@Transactional
	public void uploadAll(String shipRptIds) {
		List<String> shipRptIdList = HdUtils.paraseStrs(shipRptIds);
		for (String shipRptId: shipRptIdList) {
			upload(shipRptId);
		}
	}
	
	public void upload(String shipRptId) {
		ShipReport bean = JpaUtils.findById(ShipReport.class, shipRptId);
		JSONObject jsonObj = new JSONObject();
		Map<String,String> map=new HashMap<String, String>();
		jsonObj.put("cmdId", "2008");
		jsonObj.put("coId", Ship.GZ);
		map.put("teamOrgnId", Ship.GZ);
		if (Ship.NM.equals(bean.getTradeId())){
			map.put("tradeFlag", "2");
		}else if (Ship.WM.equals(bean.getTradeId())){
			map.put("tradeFlag", "1");
		}
		map.put("splanId",bean.getShipRptId());
		map.put("ieFlag",bean.getiEId());
		map.put("shipName", bean.getShipNam());
		map.put("shipLength", bean.getShipLength());
		map.put("cargoCode", bean.getCargoNam());
		map.put("cargoWgt", bean.getCargoWgt().toString());
		map.put("consignName", bean.getRelationNam());
		map.put("tenDays", bean.getTenDays());
		map.put("description", bean.getRemarks());
		map.put("eta", bean.getRecTim().toString().substring(0, 19));
		map.put("submitFlag", "0");
		String jpql1 = "select a from AuthUser a where a.account =:account";
		QueryParamLs paramLs1 = new QueryParamLs();
		paramLs1.addParam("account", bean.getRecNam());
		List<AuthUser> authUserList = JpaUtils.findAll(jpql1, paramLs1);
		for (AuthUser authUser : authUserList) {
				map.put("submitName", authUser.getName());
		}
		map.put("submitTime", bean.getRecTim().toString().substring(0, 19));
		map.put("submitFlag", "0");
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
				JSONObject jsonObject = JSONObject.fromObject(response);
				RespInter resp = (RespInter)JSONObject.toBean(jsonObject, RespInter.class);
				String resCode ="0000";
				String resMsg = "OK";
				if (resCode.equals(resp.getResCode())&&resMsg.equals(resp.getResMsg())) {
					throw new HdRunTimeException("上报集团成功！");
				}
				if(!resCode.equals(resp.getResCode())||!resMsg.equals(resp.getResMsg())) {
					throw new HdRunTimeException(resp.getResMsg());
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
	public ShipReport findone(String shipRptId) {
		ShipReport shipReport = JpaUtils.findById(ShipReport.class, shipRptId);
		return shipReport;
	}
	@Override
	@Transactional
	public HdMessageCode saveone(@RequestBody ShipReport shipReport) {
		if (HdUtils.strNotNull(shipReport.getShipRptId())) {
			JpaUtils.update(shipReport);
		} else {
			shipReport.setShipRptId(HdUtils.genUuid());
			JpaUtils.save(shipReport);
		}
		return HdUtils.genMsg();
	}
}
