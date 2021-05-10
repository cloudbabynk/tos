package net.huadong.tech.ship.service.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.bean.HdRunTimeException;
import net.huadong.tech.base.entity.CArea;
import net.huadong.tech.base.entity.CCarTyp;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.tech.ship.entity.CShipData;
import net.huadong.tech.ship.entity.Ship;
import net.huadong.tech.ship.entity.ShipStat;
import net.huadong.tech.ship.entity.ShipThruput;
import net.huadong.tech.ship.service.ShipService;
import net.huadong.tech.ship.service.ShipThruputService;
import net.huadong.tech.shipbill.entity.BillSplit;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;
import net.huadong.tech.util.HdUtils;
import net.sf.json.JSONObject;

@Component
public class ShipThruputServiceImpl implements ShipThruputService {
	private static final String ZYGS = "03406500";

	@Value("${api.service.ip}")
	private String apiServiceIp;

	public HdEzuiDatagridData find(HdQuery hdQuery) {
		// TODO Auto-generated method stub
		String jpql = "select a from ShipThruput a where 1=1 ";
		String shipThruputId = hdQuery.getStr("shipThruputId");
		String shipNo = hdQuery.getStr("shipNo");
		QueryParamLs paramLs = new QueryParamLs();
		if (HdUtils.strIsNull(shipNo)) {
			jpql += "and a.shipNo =:shipNo ";
			paramLs.addParam("shipNo", "#");
		}
		if (HdUtils.strNotNull(shipThruputId)) {
			jpql += "and a.shipThruputId =:shipThruputId ";
			paramLs.addParam("shipThruputId", shipThruputId);
		}
		if (HdUtils.strNotNull(shipNo)) {
			jpql += "and a.shipNo =:shipNo ";
			paramLs.addParam("shipNo", shipNo);
		}
		jpql += "order by a.recTim desc";
		return JpaUtils.findByEz(jpql, paramLs, hdQuery);
	}

	public HdMessageCode save(HdEzuiSaveDatagridData<ShipThruput> hdEzuiSaveDatagridData) {
		// TODO Auto-generated method stub
		return JpaUtils.save(hdEzuiSaveDatagridData);
	}

	@Transactional
	public void removeAll(String shipThruputIds) {
		// TODO Auto-generated method stub
		List<String> shipThruputIdList = HdUtils.paraseStrs(shipThruputIds);
		for (String shipThruputId : shipThruputIdList) {
			JpaUtils.remove(ShipThruput.class, shipThruputId);
		}
	}

	public ShipThruput findone(String shipThruputId) {
		// TODO Auto-generated method stub
		ShipThruput shipThruput = JpaUtils.findById(ShipThruput.class, shipThruputId);
		return shipThruput;
	}

	public HdMessageCode saveone(@RequestBody ShipThruput shipThruput) {
		if (HdUtils.strNotNull(shipThruput.getShipThruputId())) {
			JpaUtils.update(shipThruput);
		} else {
			shipThruput.setShipThruputId(HdUtils.genUuid());
			shipThruput.setShipNo(shipThruput.getShipNo());
			shipThruput.setGroupShipNo(shipThruput.getGroupShipNo());
			JpaUtils.save(shipThruput);
		}
		return HdUtils.genMsg();
	}

	// 上报外签吨
	// @Override
	// public void sendData(String shipThruputId) {
	// List<String> shipThruputList = HdUtils.paraseStrs(shipThruputId);
	// for (String shipthruputid : shipThruputList) {
	// ShipThruput shipThruput = JpaUtils.findById(ShipThruput.class,
	// shipthruputid);
	// JSONObject jsonObj = new JSONObject();
	// jsonObj.put("cbxh", shipThruput.getGroupShipNo());
	// jsonObj.put("zygs", ZYGS);
	// jsonObj.put("jkwqd", shipThruput.getiVisaWeight());
	// jsonObj.put("ckwqd", shipThruput.geteVisaWeight());
	// jsonObj.put("jkyjbz", shipThruput.getiPreKnot());
	// jsonObj.put("ckyjbz", shipThruput.getePreKnot());
	// jsonObj.put("token", ZYGS);
	// String url = "http://10.128.141.111:8081/ScheduleSysWebApi/setShipWQD";
	// String query = jsonObj.toString();
	// String response = "";
	// try {
	// URL httpUrl = null; // HTTP URL类 用这个类来创建连接
	// // 创建URL
	// httpUrl = new URL(url);
	// // 建立连接
	// HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();
	// conn.setRequestMethod("POST");
	// conn.setRequestProperty("Content-Type", "application/json");
	// conn.setDoOutput(true);
	// conn.setDoInput(true);
	// conn.connect();
	// // POST请求
	// try (OutputStream os = conn.getOutputStream()) {
	// os.write(query.getBytes("UTF-8"));
	// }
	// // out.flush();
	// // 读取响应
	// try (BufferedReader reader = new BufferedReader(new
	// InputStreamReader(conn.getInputStream()))) {
	// String lines;
	// StringBuffer sbf = new StringBuffer();
	// while ((lines = reader.readLine()) != null) {
	// lines = new String(lines.getBytes(), "utf-8");
	// sbf.append(lines);
	// }
	// response = sbf.toString();
	// String str = '{' + "\"code\"" + ":0" + '}';
	// String str2 = '{' + "\"code\"" + ":1" + '}';
	// if (str.equals(response)) {
	// throw new HdRunTimeException("上报外签吨数据失败！");
	// }
	// if (str2.equals(response)) {
	// ShipThruput
	// shipthruput=JpaUtils.findById(ShipThruput.class,shipThruputId);
	// shipthruput.setSendFlag("1");
	// JpaUtils.update(shipthruput);
	// throw new HdRunTimeException("上报外签吨数据成功！");
	// }
	// }catch (Exception e){
	// // System.out.println("上报出现异常！" + e);
	// }
	// // 断开连接
	// conn.disconnect();
	// } catch (Exception e) {
	// System.out.println("发送 POST 请求出现异常！" + e);
	// e.printStackTrace();
	// }
	// // 使用finally块来关闭输出流、输入流
	// finally {
	// // try {
	// // if (os != null) {
	// // out.close();
	// // }
	// // if (reader != null) {
	// // reader.close();
	// // }
	// // } catch (IOException ex) {
	// // ex.printStackTrace();
	// // }
	// }
	// }
	// }
	public void upload(String shipNo) {

	}

	public void sendData(String shipNo) {
		String jpql = "select a from ShipThruput a where a.shipNo=:shipNo ";
		ShipThruput shipThruput = new ShipThruput();
		QueryParamLs paramLs = new QueryParamLs();
		paramLs.addParam("shipNo", shipNo);
		List<ShipThruput> shipthruputList = JpaUtils.findAll(jpql, paramLs);
		if (shipthruputList.size() > 0) {
			for (ShipThruput s : shipthruputList) {
				String shipThruputId = s.getShipThruputId();
				shipThruput = JpaUtils.findById(ShipThruput.class, shipThruputId);
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("cbxh", shipThruput.getGroupShipNo());
				jsonObj.put("zygs", ZYGS);
				jsonObj.put("jkwqd", shipThruput.getiVisaWeight());
				jsonObj.put("ckwqd", shipThruput.geteVisaWeight());
				jsonObj.put("jkyjbz", shipThruput.getiPreKnot());
				jsonObj.put("ckyjbz", shipThruput.getePreKnot());
				jsonObj.put("token", ZYGS);
				String url = apiServiceIp + "8091/ScheduleSysWebApi/setShipWQD";
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
						String str = '{' + "\"code\"" + ":0" + '}';
						String str2 = '{' + "\"code\"" + ":1" + '}';
						if (str.equals(response)) {
							throw new HdRunTimeException("上报外签吨数据失败！");
						}
						if (str2.equals(response)) {
							ShipThruput shipthruput = JpaUtils.findById(ShipThruput.class, shipThruputId);
							shipthruput.setSendFlag("1");
							JpaUtils.update(shipthruput);
							throw new HdRunTimeException("上报外签吨数据成功！");
						}
					} catch (Exception e) {
						// System.out.println("上报出现异常！" + e);
					}
					// 断开连接
					conn.disconnect();
				} catch (Exception e) {
					System.out.println("发送 POST 请求出现异常！" + e);
					e.printStackTrace();
				}
				// 使用finally块来关闭输出流、输入流
				finally {
					// try {
					// if (os != null) {
					// out.close();
					// }
					// if (reader != null) {
					// reader.close();
					// }
					// } catch (IOException ex) {
					// ex.printStackTrace();
					// }
				}
			}
		}
	}

	@Override
	public ShipThruput findData(String shipNo) {
		String jpql = "select a from ShipThruput a where a.shipNo=:shipNo ";
		ShipThruput shipThruput = new ShipThruput();
		QueryParamLs paramLs = new QueryParamLs();
		paramLs.addParam("shipNo", shipNo);
		List<ShipThruput> shipthruputList = JpaUtils.findAll(jpql, paramLs);
		if (shipthruputList.size() > 0) {
			for (ShipThruput s : shipthruputList) {
				String shipThruputId = s.getShipThruputId();
				shipThruput = JpaUtils.findById(ShipThruput.class, shipThruputId);
			}
		} else {
			String jpql1 = "select a from ShipStat a where a.shipStatCod = '1006' and a.shipNo =:shipNo";//1006为卸
			List<ShipStat> jklist = JpaUtils.findAll(jpql1, paramLs);
			if (jklist.size()>0){
				shipThruput.setInBegTim(jklist.get(0).getStatBegTim());
				shipThruput.setInEndTim(jklist.get(0).getStatEndTim());
			}
			String jpql2 = "select a from ShipStat a where a.shipStatCod = '1007' and a.shipNo =:shipNo";//1006为卸
			List<ShipStat> cklist = JpaUtils.findAll(jpql2, paramLs);
			if (cklist.size()>0){
				shipThruput.setOutBegTim(cklist.get(0).getStatBegTim());
				shipThruput.setOutEndTim(cklist.get(0).getStatEndTim());
			}

		}
		return shipThruput;
	}

	@Transactional
	public void removeByshipNo(String shipNo) {
		String jpql = "select a from ShipThruput a where a.shipNo=:shipNo ";
		ShipThruput shipThruput = new ShipThruput();
		QueryParamLs paramLs = new QueryParamLs();
		paramLs.addParam("shipNo", shipNo);
		List<ShipThruput> shipthruputList = JpaUtils.findAll(jpql, paramLs);
		if (shipthruputList.size() > 0) {
			for (ShipThruput s : shipthruputList) {
				String shipThruputId = s.getShipThruputId();
				shipThruput = JpaUtils.findById(ShipThruput.class, shipThruputId);
			}
		}
		JpaUtils.remove(shipThruput);
	}
}
