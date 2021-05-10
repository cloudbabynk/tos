package net.huadong.tech.ship.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.huadong.tech.Interface.entity.ShipTeam;
import net.huadong.tech.Interface.entity.VGroupCorpCargo;
import net.huadong.tech.Interface.entity.VGroupCorpShip;
import net.huadong.tech.Interface.entity.VGroupCorpShipData;
import net.huadong.tech.Interface.entity.VGroupCorpShipVoyage;
import net.huadong.tech.base.bean.EzTreeBean;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.bean.HdRunTimeException;
import net.huadong.tech.base.entity.CBerth;
import net.huadong.tech.base.entity.CClientCod;
import net.huadong.tech.base.entity.CCountry;
import net.huadong.tech.base.entity.CDamage;
import net.huadong.tech.base.entity.CPort;
import net.huadong.tech.base.entity.CShipStat;
import net.huadong.tech.base.entity.HdShipPicSbcBerth;
import net.huadong.tech.base.entity.HdShipPicSbcBollard;
import net.huadong.tech.base.entity.CCable;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.tech.privilege.entity.AuthUser;
import net.huadong.tech.ship.entity.BerthPlanInfo;
import net.huadong.tech.ship.entity.BerthShape;
import net.huadong.tech.ship.entity.CShipData;
import net.huadong.tech.ship.entity.CShipDataGroup;
import net.huadong.tech.ship.entity.HdShipPicBerthPlanShipVisit;
import net.huadong.tech.ship.entity.ReturnObject;
import net.huadong.tech.ship.entity.Ship;
import net.huadong.tech.ship.entity.ShipFee;
import net.huadong.tech.ship.entity.ShipGroup;
import net.huadong.tech.ship.entity.ShipImage;
import net.huadong.tech.ship.entity.ShipLine;
import net.huadong.tech.ship.entity.ShipLoadPlan;
import net.huadong.tech.ship.entity.ShipMoor;
import net.huadong.tech.ship.entity.ShipStat;
import net.huadong.tech.ship.entity.ShipTim;
import net.huadong.tech.ship.entity.ShipTrend;
import net.huadong.tech.ship.entity.Shipcount;
import net.huadong.tech.ship.entity.TimeAxisInfo;
import net.huadong.tech.ship.service.ShipService;
import net.huadong.tech.shipbill.entity.ShipBill;
import net.huadong.tech.ship.entity.BerthAxisInfo;
import net.huadong.idev.hdmessagecode.HdMessageCode;

import net.huadong.tech.util.Util;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.apache.commons.net.ntp.TimeStamp;
import org.eclipse.persistence.config.QueryHints;
import org.eclipse.persistence.config.ResultType;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiQueryParams;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;
import net.huadong.tech.task.ShipTask;
import net.huadong.tech.util.CommonUtil;
import net.huadong.tech.util.ComparatorX;
import net.huadong.tech.util.HdUtils;
import net.huadong.tech.work.entity.WorkCommand;
import net.huadong.tech.work.entity.WorkQueue;
import net.sf.json.JSONArray;
import oracle.sql.TIMESTAMP;

/**
 * @author
 */
@Component
public class ShipServiceImpl implements ShipService {
	@Value("${cshipdata.token}")
	private String token;

	@Value("${api.service.ip}")
	private String apiServiceIp;

	@Value("${tjgjt.service.ip}")
	private String tjgjtServiceIp;

	private static char[] charSet = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();

	@Override
	public HdEzuiDatagridData find(HdQuery hdQuery) {
		String jpql = "select a from Ship a where a.shipNo != '20190311082013' ";
		String shipStat = hdQuery.getStr("shipStat");
		String cShipNam = hdQuery.getStr("cShipNam");
		String shipCod = hdQuery.getStr("shipCod");
		String voyage = hdQuery.getStr("voyage");
		QueryParamLs paramLs = new QueryParamLs();
		if (HdUtils.strNotNull(shipStat)) {
			jpql += "and a.shipStat =:shipStat ";
			paramLs.addParam("shipStat", shipStat);
		}
		if (HdUtils.strNotNull(cShipNam)) {
			jpql += "and a.cShipNam like :cShipNam ";
			paramLs.addParam("cShipNam", "%" + cShipNam + "%");
		}
		if (HdUtils.strNotNull(shipCod)) {
			jpql += "and a.shipCod =:shipCod ";
			paramLs.addParam("shipCod", shipCod);
		}
		if (HdUtils.strNotNull(voyage)) {
			jpql += "and (a.ivoyage =:ivoyage or a.evoyage =:evoyage) ";
			paramLs.addParam("ivoyage", voyage);
			paramLs.addParam("evoyage", voyage);
		}
		jpql += "order by a.recTim desc";
		HdEzuiDatagridData result = JpaUtils.findByEz(jpql, paramLs, hdQuery);
		List<Ship> shipList = result.getRows();
		for (Ship ship : shipList) {
			ship.setShipStatNam(HdUtils.getSysCodeName("SHIP_STAT", ship.getShipStat()));
			if (HdUtils.strNotNull(ship.getBerthCod())) {
				CBerth cBerth = JpaUtils.findById(CBerth.class, ship.getBerthCod());
				if (cBerth != null) {
					ship.setBerthCodNam(cBerth.getBerthNam());
				}
			}
			if (HdUtils.strNotNull(ship.getShipCodId())) {
				CShipData cShipData = JpaUtils.findById(CShipData.class, ship.getShipCodId());
				if(cShipData != null){
					ship.setShipLongNum(cShipData.getShipLongNum());
					ship.setShipGrossWgt(cShipData.getShipGrossWgt());
					ship.setShipNetWgt(cShipData.getShipNetWgt());
				}
			}
		}
		return result;
	}

	@Override
	public String findDashShip() {
		String jpql = "select a from Ship a where a.shipStat in ('E','A','Y') order by a.recTim desc";
		QueryParamLs paramLs = new QueryParamLs();
		List<Ship> shipList = JpaUtils.findAll(jpql, paramLs);
		String dashShipMessage = "";
		int i = 1;
		for (Ship ship : shipList) {
			String cargoNum = "";
			if (ship.getiTonNum() != null) {
				cargoNum = String.valueOf(ship.getiTonNum().add(ship.geteTonNum()));
			} else if (ship.geteTonNum() != null) {
				cargoNum = String.valueOf(ship.geteTonNum());
			}
			String tradeId = HdUtils.getSysCodeName("TRADE_ID", ship.getTradeId());
			String cShipName = ship.getcShipNam();
			if (ship.getEtdArrvTim() != null) {
				dashShipMessage += "<p style=\"color: #97FFFF\">" + String.valueOf(i) + " " + cShipName + " " + tradeId
						+ " " + cargoNum + " " + String.valueOf(ship.getEtdArrvTim()) + "\n";
			} else {
				dashShipMessage += "<p style=\"color: #97FFFF\">" + String.valueOf(i) + " " + cShipName + " " + tradeId
						+ " " + cargoNum + " " + "\n";
			}
			i++;
		}
		return dashShipMessage;
	}

	@Override
	public List<Ship> findDashShipTable() {
		String jpql = "select a from Ship a where a.shipStat in ('E','A','Y') and a.shipNo !=:shipNo order by a.recTim desc";
		QueryParamLs paramLs = new QueryParamLs();
		paramLs.addParam("shipNo", "20190311082013");
		List<Ship> shipList = JpaUtils.findAll(jpql, paramLs);
		int i = 1;
		for (Ship ship : shipList) {
			String cargoNum = "";
			if (ship.getiTonNum() != null && ship.geteTonNum() != null) {
				cargoNum = String.valueOf(ship.getiTonNum().add(ship.geteTonNum()));
			} else if (ship.geteTonNum() != null) {
				cargoNum = String.valueOf(ship.geteTonNum());
			} else if (ship.getiTonNum() != null) {
				cargoNum = String.valueOf(ship.getiTonNum());
			} else {
				cargoNum = "0";
			}
			ship.setBegCableNo(i + "");// 序号
			ship.setBerthCod(cargoNum);// 吨数
			String tradeId = HdUtils.getSysCodeName("TRADE_ID", ship.getTradeId());
			ship.setTradeId(tradeId);
			i++;
		}
		return shipList;
	}

	@Override
	@Transactional
	public String uploadAll(String shipNos) {
		List<String> shipNoList = HdUtils.paraseStrs(shipNos);
		String str = "";
		for (String shipNo : shipNoList) {
			str = upload(shipNo);
			if ("操作成功".equals(str)) {
				continue;
			} else {
				break;
			}
		}
		return str;
	}

	public String upload(String shipNo) {
		String message = "";
		Ship bean = JpaUtils.findById(Ship.class, shipNo);
		JSONObject jsonObj = new JSONObject();
		Map<String, String> map = new HashMap<String, String>();
		Ship ship = JpaUtils.findById(Ship.class, bean.getShipNo());
		jsonObj.put("cmdId", "2010");
		jsonObj.put("coId", Ship.GZ);
		map.put("teamOrgnId", Ship.GZ);
		map.put("scorpId", ship.getShipNo());
		String jpql = "select a from VGroupCorpShipData a where a.shipCode =:shipCode";
		QueryParamLs paramLs = new QueryParamLs();
		paramLs.addParam("shipCode", ship.getShipCod());
		List<VGroupCorpShipData> vGroupCorpShipDataList = JpaUtils.findAll(jpql, paramLs);
		if (vGroupCorpShipDataList.size() > 0) {
			map.put("sdataId", vGroupCorpShipDataList.get(0).getSdataId());
			map.put("shipName", vGroupCorpShipDataList.get(0).getShipName());
		}
		map.put("shipLineCode", "CN005");
		map.put("workWay", "1");
		if (ship.getEtdArrvTim() == null) {
			message = "请完善预计抵港时间！";
			return message;
		}
		map.put("eta", ship.getEtdArrvTim().toString().substring(0, 16));
		map.put("grpVoyageId", ship.getNewGroupShipNo());
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
			try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
				String lines;
				StringBuffer sbf = new StringBuffer();
				while ((lines = reader.readLine()) != null) {
					lines = new String(lines.getBytes(), "utf-8");
					sbf.append(lines);
				}
				response = sbf.toString();
				JSONObject jsonObject = JSONObject.parseObject(response);
				String resCode = "0000";
				String resMsg = "OK";
				if (resCode.equals(jsonObject.getString("resCode")) && resMsg.equals(jsonObject.getString("resMsg"))) {
					message = "上报集团成功！";
				}
				if (!resCode.equals(jsonObject.getString("resCode"))
						|| !resMsg.equals(jsonObject.getString("resMsg"))) {
					message = jsonObject.getString("resMsg");
				}
			} catch (Exception e) {
				// System.out.println("上报计费数据异常！" + e);
			}
			// 断开连接
			conn.disconnect();
		} catch (Exception e) {
			// System.out.println("发送 POST 请求出现异常！" + e);
			// e.printStackTrace();
			message = "发送 POST 请求出现异常!";
		}
		return message;
	}

	@Override
	public HdEzuiDatagridData findShipJtNew(HdQuery hdQuery) {
		String jpql = "select distinct(a) from VGroupCorpShipVoyage a,VGroupCorpShipTeam t where a.svoyageId = t.svoyageId and t.teamOrgnId = '03406500' order by a.eta desc";
		QueryParamLs paramLs = new QueryParamLs();
		HdEzuiDatagridData vGroupCorpShipVoyageResult = JpaUtils.findByEz(jpql, paramLs, hdQuery);
		List<VGroupCorpShipVoyage> vGroupCorpShipVoyageList = vGroupCorpShipVoyageResult.getRows();
		HdEzuiDatagridData result = new HdEzuiDatagridData();
		List<Ship> shipList = new ArrayList();
		for (VGroupCorpShipVoyage bean : vGroupCorpShipVoyageList) {
			Ship ship = new Ship();
			ship.setNewGroupShipNo(bean.getSvoyageId());
			ship.setcShipNam(bean.getShipName());
			if (bean.getRta() != null)
				ship.setConArrvTim(new Timestamp(bean.getRta().getTime()));
			if (bean.getEtd() != null)
				ship.setEtdArrvTim(new Timestamp(bean.getEtd().getTime()));
			if (bean.getEta() != null)
				ship.setEtdArrvTim(new Timestamp(bean.getEta().getTime()));
			ship.setDraftFront(bean.getAfd());
			ship.setDraftBack(bean.getAad());
			VGroupCorpShipData vGroupCorpShipData = JpaUtils.findById(VGroupCorpShipData.class, bean.getSdataId());
			if (vGroupCorpShipData != null) {
				ship.setShipCod(vGroupCorpShipData.getShipCode());
				ship.seteShipNam(vGroupCorpShipData.getEnShipName());
				ship.setShipCorpCod(vGroupCorpShipData.getShiplineCode());
				ship.setShipTyp(vGroupCorpShipData.getShipTypeCode()); // 船舶类型
			}
			String jpql1 = "select a from VGroupCorpShip a where 1=1 ";
			QueryParamLs paramLs1 = new QueryParamLs();
			if (HdUtils.strNotNull(bean.getSvoyageId())) {
				jpql1 += "and a.svoyageId =:svoyageId";
				paramLs1.addParam("svoyageId", bean.getSvoyageId());
			}
			List<VGroupCorpShip> vGroupCorpShipList = JpaUtils.findAll(jpql1, paramLs1);
			for (VGroupCorpShip vGroupCorpShip : vGroupCorpShipList) {
				if (Ship.JK.equals(vGroupCorpShip.getIeFlag())) {
					ship.setIvoyage(vGroupCorpShip.getVoyage());
					ship.setGroupShip(vGroupCorpShip.getShipName() + "/" + vGroupCorpShip.getVoyage());
					ship.setShipAgentCod(vGroupCorpShip.getShipAgentCode());
					if (Ship.NM.equals(vGroupCorpShip.getTradeFlag())) {
						ship.setTradeId(Ship.WM);
					} else if (Ship.WM.equals(vGroupCorpShip.getTradeFlag())) {
						ship.setTradeId(Ship.NM);
					}
					ship.setNewIShipId(vGroupCorpShip.getShipId());
					if (HdUtils.strNotNull(vGroupCorpShip.getCargoCode())) {
						ship.setiCargoNam(
								JpaUtils.findById(VGroupCorpCargo.class, vGroupCorpShip.getCargoCode()).getCargoName());
					}
					if (vGroupCorpShip.getCargoWgt() != null) {
						ship.setiTonNum(vGroupCorpShip.getCargoWgt());
					}
				} else if (ship.CK.equals(vGroupCorpShip.getIeFlag())) {
					ship.setEvoyage(vGroupCorpShip.getVoyage());
					ship.setNewEShipId(vGroupCorpShip.getShipId());
					if (HdUtils.strNotNull(vGroupCorpShip.getCargoCode())) {
						ship.seteCargoNam(
								JpaUtils.findById(VGroupCorpCargo.class, vGroupCorpShip.getCargoCode()).getCargoName());
					}
					if (vGroupCorpShip.getCargoWgt() != null) {
						ship.seteTonNum(vGroupCorpShip.getCargoWgt());
					}
					ship.setGroupShip(vGroupCorpShip.getShipName() + "/" + vGroupCorpShip.getVoyage());
				}
			}
			shipList.add(ship);
		}
	    result.setRows(shipList);
		result.setTotal(vGroupCorpShipVoyageResult.getTotal());
		return result;
	}

	@Override
	public HdEzuiDatagridData findShipJt(HdQuery hdQuery) {
		String resp = null;
		JSONObject obj = new JSONObject();
		obj.put("token", token);
		String query = obj.toString();
		// application里设置的参数
		String urlStr = apiServiceIp + "8091/ScheduleSysWebApi/getShipYQB";
		// 返回结果集
		List<ShipGroup> list = new ArrayList<ShipGroup>();
		try {
			URL url = new URL(urlStr);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();

			// 设置链接超时
			con.setConnectTimeout(3000);

			// 设置读取超时
			con.setReadTimeout(3000);

			// 设置参数
			con.setDoOutput(true);
			con.setDoInput(true);
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-type", "application/json");
			con.connect();

			try (OutputStream os = con.getOutputStream()) {
				os.write(query.getBytes("UTF-8"));
			}

			try (BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"))) {
				String lines;
				StringBuffer sbf = new StringBuffer();
				while ((lines = reader.readLine()) != null) {
					// lines = new String(lines.getBytes(), "utf-8");
					sbf.append(lines);
				}
				resp = sbf.toString();
			}
			con.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Gson gson = new Gson();
			list = gson.fromJson(resp, new TypeToken<List<ShipGroup>>() {
			}.getType());
		}
		ComparatorX comparator = new ComparatorX();
		Collections.sort(list, comparator);
		HdEzuiDatagridData result = new HdEzuiDatagridData();
		List<Ship> shipList = new ArrayList();
		for (ShipGroup shipGroup : list) {
			Ship ship = new Ship();
			ship.setShipCod(shipGroup.getCbdm());
			ship.setcShipNam(shipGroup.getZwcm());
			ship.setIvoyage(shipGroup.getHc());
			ship.setEvoyage(shipGroup.getCkhc());
			ship.setShipCorpCod(shipGroup.getCgsdm());
			ship.setShipTyp(shipGroup.getCbzldm());
			ship.setTradeId(shipGroup.getNwmbz());
			ship.setiCargoNam(shipGroup.getJkhl());
			if (shipGroup.getJkds() != null) {
				ship.setiTonNum(new BigDecimal(shipGroup.getJkds()));
			}
			ship.seteCargoNam(shipGroup.getCkhl());
			if (shipGroup.getCkhl() != null) {
				ship.seteTonNum(new BigDecimal(shipGroup.getCkds()));
			}
			ship.setGroupShipNo(shipGroup.getCbxh());
			if (HdUtils.strNotNull(shipGroup.getHc())) {
				ship.setGroupShip(shipGroup.getZwcm() + "/" + shipGroup.getHc());
			} else if (HdUtils.strNotNull(shipGroup.getCkhc())) {
				ship.setGroupShip(shipGroup.getZwcm() + "/" + shipGroup.getCkhc());
			}
			ship.seteShipNam(shipGroup.getYwcm());
			ship.setIpod(shipGroup.getJkqyg());
			ship.setEpod(shipGroup.getCkmdg());
			ship.setIpoc(shipGroup.getJkdygkg());
			ship.setEpoc(shipGroup.getCkdygkg());
			ship.seteShipLine(shipGroup.getCkhxdm());
			ship.setDraftFront(shipGroup.getQcs());
			ship.setDraftBack(shipGroup.getHcs());
			ship.setDkrq(shipGroup.getDkrq());
			ship.setDksj(shipGroup.getDksj());
			ship.setShipLongNum(shipGroup.getCc());
			ship.setShipGrossWgt(new BigDecimal(shipGroup.getZzd()));
			// 获取船代 进口船代不为空 则为进口船代 否则则为出口船代
			if (HdUtils.strNotNull(shipGroup.getJkcd())) {
				ship.setShipAgentCod(shipGroup.getJkcd());
			} else if (HdUtils.strNotNull(shipGroup.getCkcd())) {
				ship.setShipAgentCod(shipGroup.getCkcd());
			}
			shipList.add(ship);
		}
		result.setRows(shipList);

		return result;
	}

	public static String _10_to_62(long number, int length) {
		Long rest = number;
		Stack<Character> stack = new Stack<Character>();
		StringBuilder result = new StringBuilder(0);
		while (rest != 0) {
			stack.add(charSet[new Long((rest - (rest / 62) * 62)).intValue()]);
			rest = rest / 62;
		}
		for (; !stack.isEmpty();) {
			result.append(stack.pop());
		}
		int result_length = result.length();
		StringBuilder temp0 = new StringBuilder();
		for (int i = 0; i < length - result_length; i++) {
			temp0.append('0');
		}

		return temp0.toString() + result.toString();

	}

	public void importBilling(String shipNo, String ieId, String dockCod, String feeTon) {
		Ship ship = JpaUtils.findById(Ship.class, shipNo);
		JSONObject jsonObj = new JSONObject();
		if (ship != null) {
			jsonObj.put("oid", ship.getShipNo() + ieId);
			jsonObj.put("vss_no", _10_to_62(Long.parseLong(ship.getShipNo()), 5));
			jsonObj.put("vss_name", ship.getcShipNam());
			jsonObj.put("vss_ename", ship.geteShipNam());
			jsonObj.put("trade_kind", HdUtils.getSysCodeName("TRADE_ID", ship.getTradeId()));
			if ("I".equals(ieId)) {
				jsonObj.put("in_out_flag", "进口");
				jsonObj.put("voyage", ship.getIvoyage());
			} else if ("E".equals(ieId)) {
				jsonObj.put("in_out_flag", "出口");
				jsonObj.put("voyage", ship.getEvoyage());
			}
			jsonObj.put("fee_ton", feeTon);
			jsonObj.put("sc_stop_hours", "0");
			jsonObj.put("fsc_stop_hours", "0");
			jsonObj.put("mod_stop_hours", "0");
			jsonObj.put("work_hours", new BigDecimal("0").toString());
			jsonObj.put("h_hours", new BigDecimal("0").toString());
			jsonObj.put("hn_hours", new BigDecimal("0").toString());
			jsonObj.put("person_count", "0");
			jsonObj.put("bound_times", "0");
			jsonObj.put("unbound_times", "0");
			jsonObj.put("hn_bound_times", "0");
			jsonObj.put("hn_unbound_times", "0");
			jsonObj.put("h_bound_times", "0");
			jsonObj.put("h_unbound_times", "0");
			jsonObj.put("n_bound_times", "0");
			jsonObj.put("n_unbound_times", "0");
			// 审核标志默认为N
			jsonObj.put("check_flag", "N");
			// 计费标志默认为N
			jsonObj.put("flag_charge", "N");
			// 收费标志
			jsonObj.put("flag_apply", "N");
			if (ship.getToPortTim() != null) {
				jsonObj.put("to_port_tim", ship.getToPortTim());
			} else {
				jsonObj.put("to_port_tim", HdUtils.getDateTime());
			}
			jsonObj.put("leave_port_tim", ship.getLeavPortTim());
			jsonObj.put("check_date", "");
			jsonObj.put("check_people", "");
			jsonObj.put("stop_start", "2000-01-01");
			jsonObj.put("stop_end", "2000-01-01");
			jsonObj.put("sc_stop_start", "2000-01-01");
			jsonObj.put("sc_stop_end", "2000-01-01");
			jsonObj.put("fsc_stop_start", "2000-01-01");
			jsonObj.put("fsc_stop_end", "2000-01-01");
			jsonObj.put("wait_hours", new BigDecimal("0").toString());
			jsonObj.put("n_hours", new BigDecimal("0").toString());
			if ("03406500".equals(dockCod)) {
				jsonObj.put("token", "roroBilling");
			} else if ("03409000".equals(dockCod)) {
				jsonObj.put("token", "globalBilling");
			}
		}

		String url = apiServiceIp + "8081/RoroBillingSysWebApi/setShipInfoForFee";
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
				if (str.equals(response)) {
					throw new HdRunTimeException("上报计费失败！");
				}
				// JSONObject j = JSON.parseObject(response);
			}
			// 断开连接
			conn.disconnect();
		} catch (Exception e) {
			// System.out.println("发送 POST 请求出现异常！" + e);
			// e.printStackTrace();
			throw new HdRunTimeException("发送 POST 请求出现异常！");
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

	@Override
	public HdMessageCode save(HdEzuiSaveDatagridData<Ship> hdEzuiSaveDatagridData) {
		// TODO Auto-generated method stub
		List<Ship> shipList = hdEzuiSaveDatagridData.getInsertedRows();
		for (Ship ship : shipList) {
			String jpql1 = "select a from Ship a where a.shipStat =:shipStat and a.eShipNam =:eShipNam";
			QueryParamLs paramLs1 = new QueryParamLs();
			paramLs1.addParam("shipStat", Ship.STATUS_1);
			paramLs1.addParam("eShipNam", ship.geteShipNam());
			List<Ship> shipList1 = JpaUtils.findAll(jpql1, paramLs1);
			if (shipList1.size() > 0) {
				throw new HdRunTimeException("该船在预报状态的列表中已存在，建议使用关联导入！");// 进口航次重复
			}
			if (isExisted(ship.geteShipNam(), ship.getIvoyage(), ship.getEvoyage())) {
				throw new HdRunTimeException("此预报已存在，请选择联合导入！");
			}
			String jpql = "select a from CShipData a where a.shipCod =:shipCod ";
			QueryParamLs paramLs = new QueryParamLs();
			paramLs.addParam("shipCod", ship.getShipCod());
			if (HdUtils.strNotNull(ship.getcShipNam())) {
				jpql += "and a.cShipNam =:cShipNam ";
				paramLs.addParam("cShipNam", ship.getcShipNam());
			}
			if (HdUtils.strNotNull(ship.geteShipNam())) {
				jpql += "and a.eShipNam =:eShipNam";
				paramLs.addParam("eShipNam", ship.geteShipNam());
			}
			List<CShipData> cshipList1 = JpaUtils.findAll(jpql, paramLs);
			if (cshipList1.size() > 0) {
				if (!cshipList1.get(0).getcShipNam().equals(ship.getcShipNam())
						|| !cshipList1.get(0).geteShipNam().equals(ship.geteShipNam())) {
					throw new HdRunTimeException("船舶资料不一致，请更新船舶资料！");
				} else {
					ship.setShipCodId(cshipList1.get(0).getShipCodId());
				}
			} else {
				throw new HdRunTimeException("无该船舶数据，请更新船舶资料！");
			}
			if (HdUtils.strIsNull(ship.getIvoyage())) {
				ship.setIvoyage("*");
			}
			if (HdUtils.strIsNull(ship.getEvoyage())) {
				ship.setEvoyage("*");
			}
			ship.setShipNo(CommonUtil.getId());
			ship.setDockCod("03409000");// 默认为环球码头
			ship.setShipStat("E");// 船舶状态默认为预报
		}
		JpaUtils.save(hdEzuiSaveDatagridData);
		for (Ship ship : shipList) {
			WorkQueue bean1 = new WorkQueue();
			bean1.setWorkQueueNo("SI-" + ship.getShipNo());
			bean1.setWorkTyp("SI");
			bean1.setShipNo(ship.getShipNo());
			this.generatewq(bean1);
			WorkQueue bean2 = new WorkQueue();
			bean2.setWorkQueueNo("SO-" + ship.getShipNo());
			bean2.setWorkTyp("SO");
			bean2.setShipNo(ship.getShipNo());
			this.generatewq2(bean2);
		}
		return HdUtils.genMsg();
	}

	public void generatewq(WorkQueue workQueue) {
		String shipNo = workQueue.getShipNo();
		Ship ship = JpaUtils.findById(Ship.class, shipNo);
		ship.setDiscBegTim(HdUtils.getDateTime());
		JpaUtils.update(ship);
		String jpql = "select a from WorkQueue a where a.shipNo=:shipNo and a.workTyp='SI'";
		QueryParamLs paramLs = new QueryParamLs();
		paramLs.addParam("shipNo", shipNo);
		List<WorkQueue> workQueueList = JpaUtils.findAll(jpql, paramLs);
		if (workQueueList.size() > 0) {
			throw new HdRunTimeException("已生成作业队列！");
		} else {
			workQueue.setRecNam(HdUtils.getCurUser().getAccount());
			workQueue.setRecTim(HdUtils.getDateTime());
			JpaUtils.save(workQueue);
		}
	}

	public void generatewq2(WorkQueue workQueue) {
		String shipNo = workQueue.getShipNo();
		Ship ship = JpaUtils.findById(Ship.class, shipNo);
		ship.setLoadBegTim(HdUtils.getDateTime());
		JpaUtils.update(ship);
		String jpql = "select a from WorkQueue a where a.shipNo=:shipNo and a.workTyp='SO'";
		QueryParamLs paramLs = new QueryParamLs();
		paramLs.addParam("shipNo", shipNo);
		List<WorkQueue> workQueueList = JpaUtils.findAll(jpql, paramLs);
		if (workQueueList.size() > 0) {
			throw new HdRunTimeException("已生成作业队列！");
		} else {
			workQueue.setRecNam(HdUtils.getCurUser().getAccount());
			workQueue.setRecTim(HdUtils.getDateTime());
			JpaUtils.save(workQueue);
		}
	}

	@Override
	public HdMessageCode savezong(HdEzuiSaveDatagridData<Ship> hdEzuiSaveDatagridData, String shipNo) {
		// TODO Auto-generated method stub
		List<Ship> shipList = hdEzuiSaveDatagridData.getInsertedRows();
		Ship ship1 = JpaUtils.findById(Ship.class, shipNo);
		for (Ship ship : shipList) {
			if (HdUtils.strNotNull(ship1.getGroupShipNo()) && !ship.getGroupShipNo().equals(ship1.getGroupShipNo())) {
				throw new HdRunTimeException("集团预报数据选择有误！");
			} else {
				ship1.setShipCod(ship.getShipCod());
				ship1.setcShipNam(ship.getcShipNam());
				ship1.setIvoyage(ship.getIvoyage());
				ship1.setEvoyage(ship.getEvoyage());
				ship1.setShipCorpCod(ship.getShipCorpCod());
				ship1.setShipTyp(ship.getShipTyp());
				ship1.setTradeId(ship.getTradeId());
				ship1.setiCargoNam(ship.getiCargoNam());
				if (ship.getiTonNum() != null) {
					ship1.setiTonNum(ship.getiTonNum());
				}
				ship.seteCargoNam(ship.geteCargoNam());
				if (ship.geteTonNum() != null) {
					ship1.seteTonNum(ship.geteTonNum());
				}
				ship1.setGroupShipNo(ship.getGroupShipNo());
				ship1.setGroupShip(ship.getGroupShip());
				ship1.seteShipNam(ship.geteShipNam());
				ship1.setIpod(ship.getIpod());
				ship1.setEpod(ship.getEpod());
				ship1.setIpoc(ship.getIpoc());
				ship1.setEpoc(ship.getEpoc());
				ship1.seteShipLine(ship.geteShipLine());
				ship1.setDraftFront(ship.getDraftFront());
				ship1.setDraftBack(ship.getDraftBack());
				ship1.setShipAgentCod(ship.getShipAgentCod());
				// 保存抵口时间
				SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				if ("后告".equals(ship.getDksj())) {
					ship.setDksj("00:00:00");
				} else {
					String hour = ship.getDksj().substring(0, 1);
					String minute = ship.getDksj().substring(2, 3);
					String day = ship.getDkrq().substring(0, 9);
					String dksj = day + " " + hour + ":" + minute + ":00";
					try {
						ship1.setConArrvTim(new Timestamp(sf.parse(dksj).getTime()));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				JpaUtils.update(ship1);
			}
		}
		return HdUtils.genMsg();
	}

	@Override
	public HdMessageCode saveNewZong(HdEzuiSaveDatagridData<Ship> hdEzuiSaveDatagridData, String shipNo) {
		// TODO Auto-generated method stub
		List<Ship> shipList = hdEzuiSaveDatagridData.getInsertedRows();
		Ship ship1 = JpaUtils.findById(Ship.class, shipNo);
		for (Ship ship : shipList) {
			if (HdUtils.strNotNull(ship1.getNewGroupShipNo())
					&& !ship.getNewGroupShipNo().equals(ship1.getNewGroupShipNo())) {
				throw new HdRunTimeException("集团预报数据选择有误！");
			}
			ship1.setConArrvTim(ship.getConArrvTim());
			ship1.setEtdArrvTim(ship.getEtdArrvTim());
			ship1.setEtdArrvTim(ship.getEtdArrvTim());
			ship1.setShipCod(ship.getShipCod());
			ship1.setcShipNam(ship.getcShipNam());
			ship1.setIvoyage(ship.getIvoyage());
			ship1.setEvoyage(ship.getEvoyage());
			ship1.setShipCorpCod(ship.getShipCorpCod());
			ship1.setShipTyp(ship.getShipTyp());
			ship1.setTradeId(ship.getTradeId());
			ship1.setiCargoNam(ship.getiCargoNam());
			if (ship.getiTonNum() != null) {
				ship1.setiTonNum(ship.getiTonNum());
			}
			ship.seteCargoNam(ship.geteCargoNam());
			if (ship.geteTonNum() != null) {
				ship1.seteTonNum(ship.geteTonNum());
			}
			ship1.seteShipNam(ship.geteShipNam());
			ship1.setNewGroupShipNo(ship.getNewGroupShipNo());
			ship1.setNewEShipId(ship.getNewEShipId());
			ship1.setNewIShipId(ship.getNewIShipId());
			ship1.setIpod(ship.getIpod());
			ship1.setEpod(ship.getEpod());
			ship1.setIpoc(ship.getIpoc());
			ship1.setEpoc(ship.getEpoc());
			ship1.seteShipLine(ship.geteShipLine());
			ship1.setDraftFront(ship.getDraftFront());
			ship1.setDraftBack(ship.getDraftBack());
			ship1.setShipAgentCod(ship.getShipAgentCod());
			if (isExisted(ship1.geteShipNam(), ship1.getIvoyage(), ship1.getEvoyage())) {
				throw new HdRunTimeException("此信息已存在！");
			}
			JpaUtils.update(ship1);
		}
		return HdUtils.genMsg();
	}

	@Transactional
	public void removeAll(String shipNos) {
		List<String> shipNoList = HdUtils.paraseStrs(shipNos);
		for (String shipNo : shipNoList) {
			if (isExist(shipNo)) {
				throw new HdRunTimeException("此信息已被使用,暂时无法删除！");// 船代码重复
			}
			JpaUtils.remove(Ship.class, shipNo);
		}
	}

	@Transactional
	public void removeShipPlan(HdShipPicBerthPlanShipVisit hdShipPicBerthPlanShipVisit) {
		JpaUtils.remove(hdShipPicBerthPlanShipVisit);
	}

	@Override
	public Ship findone(String shipNo) {
		Ship ship = JpaUtils.findById(Ship.class, shipNo);
		if (HdUtils.strNotNull(ship.getShipCodId())) {
			CShipData cShipData = JpaUtils.findById(CShipData.class, ship.getShipCodId());
			ship.setShipLongNum(cShipData.getShipLongNum());
			ship.setShipGrossWgt(cShipData.getShipGrossWgt());
			ship.setShipNetWgt(cShipData.getShipNetWgt());
			ship.setShipShort(cShipData.getShipShort());
		}

		return ship;
	}

	@Override
	public HdMessageCode saveSbjf(ShipFee shipFee) {
		if ("1".equals(shipFee.getJkhc())) {
			importBilling(shipFee.getShipNo(), "I", shipFee.getJkmt(), shipFee.getShipNetWgt().toString());
		}
		if ("1".equals(shipFee.getCkhc())) {
			importBilling(shipFee.getShipNo(), "E", shipFee.getCkmt(), shipFee.getShipNetWgt().toString());
		}
		return HdUtils.genMsg();
	}

	@Override
	public HdShipPicBerthPlanShipVisit findBerth(String shipNo) {
		Ship ship = JpaUtils.findById(Ship.class, shipNo);
		HdShipPicBerthPlanShipVisit hdShipPicBerthPlanShipVisit = new HdShipPicBerthPlanShipVisit();
		BeanUtils.copyProperties(ship, hdShipPicBerthPlanShipVisit);
		hdShipPicBerthPlanShipVisit.setShipVisitId(HdUtils.genUuid());
		hdShipPicBerthPlanShipVisit.setImVoyage(ship.getIvoyage());
		hdShipPicBerthPlanShipVisit.setExVoyage(ship.getEvoyage());
		hdShipPicBerthPlanShipVisit.setShipName(ship.getcShipNam());
		hdShipPicBerthPlanShipVisit.setPlanBerthingMode("L");// 默认为左靠
		if (HdUtils.strNotNull(hdShipPicBerthPlanShipVisit.getShipCodId())) {
			CShipData cShipData = JpaUtils.findById(CShipData.class, hdShipPicBerthPlanShipVisit.getShipCodId());
			hdShipPicBerthPlanShipVisit.setShipLongNum(cShipData.getShipLongNum());
			hdShipPicBerthPlanShipVisit.setShipLength(cShipData.getShipLongNum().multiply(new BigDecimal("1.2")));
		}
		hdShipPicBerthPlanShipVisit.setPlanBerthingModeName(
				HdUtils.getSysCodeName("BERTH_WAY", hdShipPicBerthPlanShipVisit.getPlanBerthingMode()));
		hdShipPicBerthPlanShipVisit.setRecNam("");
		hdShipPicBerthPlanShipVisit.setRecTim(null);
		return hdShipPicBerthPlanShipVisit;
	}

	@Override
	@Transactional
	public HdMessageCode saveone(@RequestBody Ship ship) {
		String shipNo = ship.getShipNo();
		Ship ship1 = JpaUtils.findById(Ship.class, shipNo);
		if (ship1 != null) {
			if (ship.getToPortTim() != null && ship.STATUS_1.equals(ship.getShipStat())) {
				ship.setShipStat(ship.STATUS_3);
			}
			if (ship.getLeavPortTim() != null && ship.STATUS_3.equals(ship.getShipStat())) {
				ship.setShipStat(ship.STATUS_4);
			}
			if (ship.getToPortTim() != null && ship.getLeavPortTim() != null) {
				if (ship.getToPortTim().compareTo(ship.getLeavPortTim()) > 0) {
					throw new HdRunTimeException("离港时间不得早于靠泊时间");// 出口航次重复
				}
			}
			JpaUtils.update(ship);
		} else {
			if (isExisted(ship.geteShipNam(), ship.getIvoyage(), ship.getEvoyage())) {
				throw new HdRunTimeException("此信息已存在，请选择联合导入！");
			}
			if (isNOtExist(ship.getIvoyage(), "", ship.getShipCodId())) {
				throw new HdRunTimeException("进口航次已存在");// 进口航次重复
			}
			if (isNOtExist("", ship.getEvoyage(), ship.getShipCodId())) {
				throw new HdRunTimeException("出口航次已存在");// 出口航次重复
			}
			CShipData cShipData = JpaUtils.findById(CShipData.class, ship.getShipCodId());
			if (cShipData != null) {
				ship.setShipCod(cShipData.getShipCod());
			}
			JpaUtils.save(ship);
		}
		return HdUtils.genMsg();
	}

	private boolean isNOtExist(String ivoyage, String evoyage, String shipCod) {
		String jpql = "select a from Ship a where 1=1 ";
		QueryParamLs paramLs = new QueryParamLs();
		if (HdUtils.strNotNull(ivoyage)) {
			jpql += "and a.ivoyage =:ivoyage ";
			paramLs.addParam("ivoyage", ivoyage);
		}
		if (HdUtils.strNotNull(evoyage)) {
			jpql += "and a.evoyage =:evoyage ";
			paramLs.addParam("evoyage", evoyage);
		}
		if (HdUtils.strNotNull(shipCod)) {
			jpql += "and a.shipCod =:shipCod";
			paramLs.addParam("shipCod", shipCod);
		}
		List<Ship> ShipList = JpaUtils.findAll(jpql, paramLs);
		if (ShipList.size() > 0) {
			return true;
		}
		return false;
	}

	private boolean isExisted(String eShipNam, String ivoyage, String evoyage) {
		String jpql = "select a from Ship a where 1=1 ";
		QueryParamLs paramLs = new QueryParamLs();
		if (HdUtils.strNotNull(eShipNam)) {
			jpql += "and a.eShipNam =:eShipNam ";
			paramLs.addParam("eShipNam", eShipNam);
		}
		if (HdUtils.strNotNull(evoyage)) {
			jpql += "and a.evoyage =:evoyage ";
			paramLs.addParam("evoyage", evoyage);
		}
		if (HdUtils.strNotNull(ivoyage)) {
			jpql += "and a.ivoyage =:ivoyage";
			paramLs.addParam("ivoyage", ivoyage);
		}
		List<Ship> ShipList = JpaUtils.findAll(jpql, paramLs);
		if (ShipList.size() > 0) {
			return true;
		}
		return false;
	}

	private boolean isExist(String shipNo) {
		if (HdUtils.strNotNull(shipNo)) {
			Ship ship = JpaUtils.findById(Ship.class, shipNo);
			if (!Ship.STATUS_1.equals(ship.getShipStat())) {
				return true;
			}
		}
		String jpql1 = "select a from ShipBill a where a.shipNo =:shipNo ";
		QueryParamLs paramLs1 = new QueryParamLs();
		paramLs1.addParam("shipNo", shipNo);
		List<ShipBill> shipBillList = JpaUtils.findAll(jpql1, paramLs1);
		if (shipBillList.size() > 0) {
			return true;
		}
		String jpql2 = "select a from ShipLoadPlan a where a.shipNo =:shipNo ";
		QueryParamLs paramLs2 = new QueryParamLs();
		paramLs2.addParam("shipNo", shipNo);
		List<ShipLoadPlan> shipLoadPlanList = JpaUtils.findAll(jpql2, paramLs2);
		if (shipLoadPlanList.size() > 0) {
			return true;
		}
		return false;
	}

	// ShipTask 同一个功能
	@Override
	public List<EzTreeBean> findTree() {// yl.生成
		ShipTask task = new ShipTask();
		return task.getTree();
	}

	@Override
	public String findAxis(String width, String height, String daySum, String startdate) {
		int w = Integer.parseInt(width);// 画布的宽
		int h = Integer.parseInt(height);// 画布的高 zr.getHeight
		double kd = getKd(h, daySum);
		// 此处是查询时间
		List<ShipTim> timlist = getShipTim(h, kd, daySum, startdate);
		// 此处是泊位和揽桩
		List<Shipcount> lta = getBerthCable(w);
		JSONObject json = new JSONObject();
		json.put("result", lta);
		json.put("times", timlist);
		return json.toString();
	}

	public double getKd(int height, String daySum) {
		double kd = 0;
		double sum = Double.valueOf(daySum);
		BigDecimal a1 = new BigDecimal(Double.valueOf(height));
		BigDecimal a2 = new BigDecimal(sum * 24);// 除法必须得到精确的值
		kd = a1.divide(a2, 15, BigDecimal.ROUND_HALF_UP).doubleValue();// 画布的高度除以48得到每一个刻度的高度
		return kd;
	}

	// 此方法是查询纵轴的时间集合
	public List<ShipTim> getShipTim(double height, double kd, String daySum, String startdate) {
		List<ShipTim> timlist = new ArrayList<ShipTim>();// 放时间;
		Timestamp sp1 = null;
		if (HdUtils.strNotNull(startdate)) {
			sp1 = HdUtils.strToDate(startdate);
		}
		int ptim = 17;
		int days = Integer.valueOf(daySum);// 天数
		int hours = days * 12;
		for (int i = 0; i <= hours; i++) {
			String tim = String.format("%02d", ptim);
			ShipTim shipTim = new ShipTim();
			shipTim.setTim(tim);
			shipTim.setDate(sp1);
			timlist.add(shipTim);
			if (ptim < 23) {
				ptim = ptim + 2;
			} else {
				ptim = 1;
			}
		}

		for (int j = 0; j < timlist.size(); j++) {
			ShipTim st = timlist.get(j);
			double b = j * kd;
			st.setZb(b);
		}

		return timlist;
	}

	// 此方法是得到泊位和揽桩的总的集合
	public List<Shipcount> getBerthCable(double width) {
		List<Shipcount> shipcountbwList = new ArrayList<Shipcount>();// 泊位集合
		String jpql = " select  a  from  CBerth  a  where  a.berthTyp=:berthTyp  order by  a.dispSeq  desc ";// 按照序号排序
		QueryParamLs params = new QueryParamLs();
		params.addParam("berthTyp", "01");
		List<CBerth> cBerthList = JpaUtils.findAll(jpql, params);
		for (CBerth cberth : cBerthList) {
			Shipcount st = new Shipcount();
			st.setBerthId(cberth.getBerthCod());
			st.setSeqNo(cberth.getDispSeq().doubleValue());
			st.setLength(cberth.getBerthLong().doubleValue());
			st.setName(cberth.getBerthNam());
			shipcountbwList.add(st);
		}
		List<Shipcount> shipcountlzList = new ArrayList<Shipcount>();// 揽桩集合
		String jpqls = " select  a  from  CCable  a  where  a.berthCod in ('6510','6520','7410','7420')  order by  a.cableSeq  desc ";// 按照序号排序
		QueryParamLs pa = new QueryParamLs();
		List<CCable> cCableList = JpaUtils.findAll(jpqls, pa);
		for (CCable cable : cCableList) {
			Shipcount st = new Shipcount();
			st.setBerthId(cable.getBerthCod());
			st.setCableId(cable.getCableCod());
			st.setSeqNo(cable.getCableSeq());
			st.setLength(1L);
			st.setName(cable.getCableNo());
			shipcountlzList.add(st);
		}
		List<Shipcount> shipcountList = new ArrayList<Shipcount>();// 泊位+揽桩
		for (Shipcount st : shipcountbwList) {
			String berthId = st.getBerthId();
			for (Shipcount stt : shipcountlzList) {
				if (berthId.equals(stt.getBerthId())) {
					stt.setFullNam(st.getName() + "-" + stt.getName());
					shipcountList.add(stt);
				}
			}
		}
		for (Shipcount st : shipcountList) {
			BigDecimal b1 = new BigDecimal(Double.valueOf("1"));
			BigDecimal b2 = new BigDecimal(Double.valueOf(shipcountlzList.size()));
			double b3 = b1.divide(b2, 15, BigDecimal.ROUND_HALF_UP).doubleValue();
			st.setRate(b3);
		}
		double rate = 0;
		for (int i = 0; i < shipcountList.size(); i++) {
			Shipcount st = shipcountList.get(i);
			double dd = st.getRate();
			rate += dd;
			st.setZbx(rate * width);
		}
		return shipcountList;
	}

	@Override
	public String getBerthTim(String ptim, String startdate, String enddate, String height) {
		List<ShipTim> timlist = getTimes(ptim, startdate, enddate, height);

		JSONObject json = new JSONObject();
		json.put("times", timlist);
		// json.put("days", span);
		return json.toString();
	}

	public List<ShipTim> getTimes(String ptim, String startdate, String enddate, String height) {
		int h = Integer.parseInt(height);
		double kd = getKd(h, "");
		List<ShipTim> timlist = new ArrayList<ShipTim>(49);// 放时间;
		ptim = "18";// 默认是18点
		Timestamp sp1 = HdUtils.strToDate(startdate);
		Timestamp sp2 = HdUtils.strToDate(enddate);
		long between = 0;
		if (sp2 != null && sp1 != null) {// 多日
			between = (sp2.getTime() - sp1.getTime()) / (1000 * 3600 * 24);
		} else if (sp1 != null && sp2 == null) {// 单日
			between = 1;
		}
		int span = Integer.parseInt(String.valueOf(between)); // 跨度为天
		String now = ptim + ":00";
		int flg = span % 2;// 取余
		int fa = Integer.valueOf(span / 2);// 取商
		int fb = 0;
		if (flg == 1) {// 奇数
			fb = fa + 1;
		}
		Timestamp date = sp1;

		for (int i = 0; i < 49; i++) {
			int end = now.indexOf(":");
			double f = i % 2;
			if (flg == 1) {// 奇数天
				if (f == 1) {
					now = Integer.valueOf(now.substring(0, end)) + fa + ":30";
				} else if (f == 0 && i != 0) {
					now = Integer.valueOf(now.substring(0, end)) + fb + ":00";
				}
			} else if (flg == 0) {// 偶数天
				if (i != 0) {
					now = Integer.valueOf(now.substring(0, end)) + fa + ":00";
				}
			}
			if (Integer.valueOf(now.substring(0, end)) > 24) {
				date = HdUtils.addDay(date, 1);
				String en = now.substring(end);
				now = Integer.valueOf(now.substring(0, end)) - 24 + en;
				end = now.indexOf(":");
			}
			if (Integer.valueOf(now.substring(0, end)) == 24) {
				date = HdUtils.addDay(date, 1);
				now = "0" + now.substring(end);
			}

			String nownew = "";
			String[] str = now.split(":");
			int tt = Integer.parseInt(str[0]);
			if (tt < 10) {
				nownew = "0" + now;
			} else {
				nownew = now;
			}
			ShipTim shipTim = new ShipTim();
			shipTim.setTim(nownew);
			shipTim.setDate(date);
			timlist.add(shipTim);
		}

		for (int j = 0; j < timlist.size(); j++) {
			ShipTim st = timlist.get(j);
			double b = h - j * kd;
			st.setZb(b);
		}

		return timlist;
	}

	// 点击查询按钮，查询出各种状态的船
	@Override
	public String showBerths(String startdate, String enddate, String width, String height, String daySum) {
		int w = Integer.parseInt(width);// 画布的宽
		int h = Integer.parseInt(height);// 画布的高 zr.getHeight
		double kd = getKd(h, daySum);
		List<BerthShape> ls = new ArrayList<BerthShape>();
		// 此处是查询时间
		List<ShipTim> timlist = getShipTim(h, kd, daySum, startdate);
		// 此处是泊位和揽桩
		List<Shipcount> lta = getBerthCable(w);
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String jpql = " select  a  from  HdShipPicBerthPlanShipVisit a where 1=1 ";
		String date3 = startdate + " 17:00:00";
		QueryParamLs params = new QueryParamLs();
		if (HdUtils.strNotNull(startdate)) {
			jpql += " and a.etb>=:date1 and a.etu<:date2 and a.etu>:date3";
			params.addParam("date1", HdUtils.strToDate(startdate));
			params.addParam("date2", HdUtils.strToDate(enddate));
			try {
				params.addParam("date3", new Timestamp(sf.parse(date3).getTime()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		List<HdShipPicBerthPlanShipVisit> list = JpaUtils.findAll(jpql, params);

		int span = Integer.valueOf(daySum);

		for (HdShipPicBerthPlanShipVisit berth : list) {
			BerthShape bs = getBerthSahpes(berth, lta, timlist, kd, span);
			bs.setShipNo(berth.getShipNo());
			bs.setBerthWay(berth.getPlanBerthingMode());
			ls.add(bs);
		}
		JSONObject json = new JSONObject();
		json.put("list", ls);

		return json.toString();
	}

	public BerthShape getBerthSahpes(HdShipPicBerthPlanShipVisit ship, List<Shipcount> lta, List<ShipTim> timlist,
			double kd, int span) {
		double x1 = 0;// 起始位置
		double x2 = 0;// 终止位置
		double y1 = 0;// 开始时间
		double y2 = 0;// 结束时间
		BerthShape bs = new BerthShape();
		Timestamp sp1 = ship.getEtb();
		Timestamp sp2 = ship.getEtu();
		if (HdUtils.strNotNull(ship.getPlanBerthCode())) {
			if (HdUtils.strNotNull(ship.getPlanBeginBollardCode())
					&& HdUtils.strNotNull(ship.getPlanEndBollardCode())) {
				for (Shipcount sp : lta) {
					if (ship.getPlanBeginBollardCode().equals(sp.getCableId())) {
						x1 = sp.getZbx();
					} else if (ship.getPlanEndBollardCode().equals(sp.getCableId())) {
						x2 = sp.getZbx();
					}
				}
			}
		}
		if (sp1 != null && sp2 != null) {// 泊位 离泊（有开始时间和结束时间）
			y1 = getZby(sp1, timlist, kd, span);
			y2 = getZby(sp2, timlist, kd, span);
			List<List<Object>> list = new ArrayList<List<Object>>();// 泊位 离泊
																	// 这两种在图上显示矩形
			List<Object> lst1 = new ArrayList<Object>();
			lst1.add(x1);
			lst1.add(y1);
			list.add(lst1);
			List<Object> lst2 = new ArrayList<Object>();
			lst2.add(x2);
			lst2.add(y1);
			list.add(lst2);
			List<Object> lst3 = new ArrayList<Object>();
			lst3.add(x2);
			lst3.add(y2);
			list.add(lst3);
			List<Object> lst4 = new ArrayList<Object>();
			lst4.add(x1);
			lst4.add(y2);
			list.add(lst4);
			bs.setShape(list);
			ShipImage image = new ShipImage();
			image.setX(x2);
			image.setY(y1);
			double w = (x1 - x2);
			double h = (y2 - y1);
			image.setWidth(w);
			image.setHeight(h);
			bs.setImage(image);
		} else if (sp1 != null && sp2 == null) {// 预报 沽口 离港 （只有开始时间）
			int hour1 = getHour(sp1);
			String min1 = String.valueOf(getMinute(sp1));
			y1 = getY(hour1, min1, timlist, kd);
			ShipLine sl = new ShipLine();
			sl.setX1(x1);
			sl.setX2(x2);
			sl.setY(y1);
			bs.setShape(sl);
			bs.setFlag("2");// flag=2 表示是预报 沽口 离港 船 一条线
			ShipImage image = new ShipImage();
			image.setX(x1);
			image.setY(y1 - 50);
			double w = (x2 - x1);
			image.setWidth(w);
			image.setHeight(50);
			bs.setImage(image);
		}
		if (HdUtils.strNotNull(ship.getShipCodId())) {
			CShipData cShipData = JpaUtils.findById(CShipData.class, ship.getShipCodId());
			if (HdUtils.strNotNull(ship.getRemarks())) {
				bs.setRemarks(ship.getRemarks());
			} else {
				bs.setRemarks("");
			}
			bs.setShipLongNum(cShipData.getShipLongNum().toString());
			bs.setFlag(cShipData.getShipTyp());
			if (HdUtils.strNotNull(ship.getShipNo())) {
				Ship bean = JpaUtils.findById(Ship.class, ship.getShipNo());
				if (bean != null) {
					bs.setText(bean.getcShipNam() + " 船长为：" + cShipData.getShipLongNum() + "("
							+ HdUtils.getSysCodeName("BERTH_WAY", ship.getPlanBerthingMode()) + ")");
				}
				if (HdUtils.strNotNull(bean.getShipStat())) {
					bs.setShipStat(bean.getShipStat());
				} else {
					bs.setShipStat("E");
				}
			}
		}

		// flag代表船型 展示时是不同的颜色

		return bs;
	}

	public static int getHour(Timestamp date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.HOUR_OF_DAY);
	}

	public static int getMinute(Timestamp date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.MINUTE);
	}

	// 此方法是从纵轴的时间集合里找到对应的时间的纵坐标
	public double getY(int hour, String minute, List<ShipTim> timlist, double kd) {
		double y = 0;
		for (ShipTim shipTim : timlist) {
			String[] str = shipTim.getTim().split(":");
			int h = Integer.parseInt(str[0]);
			int min = Integer.parseInt(str[1]);
			if (h == hour && min == 0) {
				y = shipTim.getZb();
				if (HdUtils.strNotNull(minute)) {
					double d = Double.parseDouble(minute);
					BigDecimal b1 = new BigDecimal(d);
					BigDecimal b2 = new BigDecimal(Double.valueOf(30));
					double b3 = b1.divide(b2, 15, BigDecimal.ROUND_HALF_UP).doubleValue();
					double z = b3 * kd;
					y -= z;

				}
			}
		}
		return y;
	}

	public double getZby(Timestamp sp, List<ShipTim> timlist, double kd, int span) {
		double y = 0;
		// double hours=(span*24)/48;//求出 每个刻度表示几个小时（1天的是0.5小时，5天的是2.5小时）

		BigDecimal a1 = new BigDecimal(span * 24);
		BigDecimal a2 = new BigDecimal(span * 24);
		double hours = a1.divide(a2, 15, BigDecimal.ROUND_HALF_UP).doubleValue();

		double second = hours * 3600;// 求出秒数

		BigDecimal b1 = new BigDecimal(kd);
		BigDecimal b2 = new BigDecimal(Double.valueOf(second));
		double b3 = b1.divide(b2, 15, BigDecimal.ROUND_HALF_UP).doubleValue();

		double z = 0;
		double b = 0;
		double aaa;
		Timestamp date = timlist.get(0).getDate();// 获取日期
		if (sp.getTime() > date.getTime()) {
			// b=(sp.getTime()-date.getTime())/(1000*3600);//获取小时数
			aaa = sp.getTime() - date.getTime() - 17 * 3600 * 1000;
			BigDecimal t1 = new BigDecimal(aaa);
			BigDecimal t2 = new BigDecimal(1000);
			b = t1.divide(t2, 15, BigDecimal.ROUND_HALF_UP).doubleValue();
			if (b > 0) {// 证明是同一天
				y = b * b3;
			}
		}

		return y;
	}

	@Override
	public String showDetail(String type, String width, String height, String berth1, String cable1, String berth2,
			String cable2, String tim1, String tim2, String date1, String date2) {

		int w = Integer.parseInt(width);// 画布的宽
		int h = Integer.parseInt(height);// 画布的高 zr.getHeight
		Timestamp sp1 = null;
		Timestamp sp2 = null;
		double kd = getKd(h, "");

		if (HdUtils.strNotNull(tim1)) {
			sp1 = HdUtils.strToDateTime(tim1);
		}
		if (HdUtils.strNotNull(tim2)) {
			sp2 = HdUtils.strToDateTime(tim2);
		}

		// List<ShipTim> timlist = getShipTim(h, kd);// 此处是查询时间
		List<ShipTim> timlist = getTimes("18", date1, date2, height);
		List<Shipcount> lta = getBerthCable(w);// 泊位+揽桩

		Ship berth = new Ship();
		berth.setBerthCodNam(berth1);
		berth.setBerthCod(berth2);
		berth.setBegCableNo(cable1);
		berth.setEndCableNo(cable2);
		berth.setEtdArrvTim(sp1);
		berth.setEtdLeavTim(sp2);

		int span = Integer.valueOf(String.valueOf((sp2.getTime() - sp1.getTime()) / (1000 * 3600 * 24)));

		// BerthShape bs = getBerthSahpes(berth, lta, timlist, kd, span);
		// bs.setShipStat(type);
		JSONObject json = new JSONObject();
		// json.put("berth", bs);
		return json.toString();
	}

	@Override
	public HdMessageCode findMsg(String start, String end, String interval) {
		ReturnObject msg = new ReturnObject();
		// 时间判断
		LocalDateTime startTime;
		LocalDateTime endTime;
		// 美好的一天从17点开始
		String startdt = start + " 17:00";
		String enddt = end + " 17:00";
		startTime = Util.getLocalDateTimeByStr(start);
		endTime = Util.getLocalDateTimeByStr(end);
		// if (startTime == null || endTime == null) {
		// msg.setFailMsg("请输入正确的时间格式：yyyy-MM-dd HH:mm");
		// return msg;
		// }
		// // todo 间隔20天
		// if (startTime.plusDays(20).isBefore(endTime)) {
		// msg.setFailMsg("起止时间间隔不能超过20天");
		// return msg;
		// }
		BerthPlanInfo berthPlanInfo = new BerthPlanInfo();
		int minutes = Integer.valueOf(interval);
		berthPlanInfo.setTimeAxisInfo(getTimeAxisInfo(startdt, enddt, minutes));
		berthPlanInfo.setBerthAxisInfoList(getBerthAxisInfo());
		berthPlanInfo.setShipList(getShipList(startdt, enddt));
		System.out.println(JSONArray.fromObject(berthPlanInfo));
		msg.setData(berthPlanInfo);
		return msg;
	}
	
	@Override
	public HdMessageCode findExpandMsg(String start, String end, String interval) {
		ReturnObject msg = new ReturnObject();

		// 美好的一天从17点开始
		String startdt = start + " 17:00";
		String enddt = end + " 17:00";
		String nativeBegSql = 
		"select to_char(trunc(min(ETD_ARRV_TIM)-1, 'dd'),'yyyy-mm-dd') as BEG_CABLE_NO,PLAN_ID\n" +
		"                from SHIP_PLAN A\n" +
		"               where  A.ETD_LEAV_TIM >= TO_DATE (?1, 'YYYY-MM-DD HH24:MI') "
				+ "AND A.ETD_LEAV_TIM <= TO_DATE (?2, 'YYYY-MM-DD HH24:MI') group by PLAN_ID";
		QueryParamLs paramLs = new QueryParamLs();
		paramLs.addParam(1, startdt);
		paramLs.addParam(2, enddt);
		List<HdShipPicBerthPlanShipVisit> listBeg =  JpaUtils.findBySql(nativeBegSql, paramLs, HdShipPicBerthPlanShipVisit.class);
		if(!listBeg.isEmpty()){
			startdt = listBeg.get(0).getPlanBeginBollardCode()+" 17:00";
		}
		System.out.println(startdt);
		String nativeEndSql = "  select to_char(trunc(max(ETD_LEAV_TIM)+1 , 'dd'),'yyyy-mm-dd') as END_CABLE_NO,PLAN_ID\n" +
				"                from SHIP_PLAN A\n" +
				"               where  A.ETD_ARRV_TIM >= TO_DATE (?1, 'YYYY-MM-DD HH24:MI') "
				+ "AND A.ETD_ARRV_TIM <= TO_DATE (?2, 'YYYY-MM-DD HH24:MI') group by PLAN_ID";
		List<HdShipPicBerthPlanShipVisit> listEnd =  JpaUtils.findBySql(nativeEndSql, paramLs, HdShipPicBerthPlanShipVisit.class);
		if(!listEnd.isEmpty()){
			enddt = listEnd.get(0).getPlanEndBollardCode()+" 17:00";
		}
		System.out.println(enddt);
		BerthPlanInfo berthPlanInfo = new BerthPlanInfo();
		int minutes = Integer.valueOf(interval);
		berthPlanInfo.setTimeAxisInfo(getTimeAxisInfo(startdt, enddt, minutes));
		berthPlanInfo.setBerthAxisInfoList(getBerthAxisInfo());
		berthPlanInfo.setShipList(getShipList(startdt, enddt));
		System.out.println(JSONArray.fromObject(berthPlanInfo));
		Map<String,Object> map  = new HashMap<String,Object>();
		map.put("berthPlanInfo", berthPlanInfo);
		map.put("startdt", startdt.substring(0, 10));
		map.put("enddt", enddt.substring(0, 10));
		msg.setData(map);
		return msg;
	}
	
	private TimeAxisInfo getTimeAxisInfo(String start, String end, int interval) {
		TimeAxisInfo timeAxisInfo = new TimeAxisInfo();
		List<Map<String, Object>> list = new ArrayList<>();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		LocalDateTime startTime = LocalDateTime.parse(start, formatter);
		LocalDateTime endTime = LocalDateTime.parse(end, formatter);
		// 时间间隔 划分
		int minutes = interval;
		LocalDateTime dateTime = startTime;
		int level = 0;
		int i = 0;
		while (dateTime.isBefore(endTime) || dateTime.isEqual(endTime)) {
			if (i % 6 == 0) {
				level = 1;
			} else {
				level = 0;
			}
			boolean showDateFlag = false;
			// 如果跨天，显示日期
			if (dateTime.getHour() == 0 && dateTime.getMinute() == 0) {
				showDateFlag = true;
			}
			if (i == 0) {
				showDateFlag = true;
			}
			list.add(getTimeMap(dateTime, showDateFlag, level));

			dateTime = dateTime.plusMinutes(minutes);
			i++;
		}
		timeAxisInfo.setTimes(list);
		timeAxisInfo.setIntervalMills(minutes * 60 * 1000);
		return timeAxisInfo;
	}

	private Map<String, Object> getTimeMap(LocalDateTime dateTime, boolean showDateFlag, int level) {
		Map<String, Object> map = new HashMap<>();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		String dateTimeStr = dateTime.format(formatter);
		map.put("timestamp", dateTime.toEpochSecond(ZoneOffset.of("+8")) * 1000);
		map.put("code", dateTimeStr);
		map.put("level", level);
		if (showDateFlag) {
			map.put("name", dateTimeStr.replace(" ", "\n"));
		} else {
			map.put("name", dateTimeStr.substring(11));
		}
		map.put("show", false);
		// 正点显示
		if (level == 1) {
			map.put("show", true);
		}
		return map;
	}

	private List<BerthAxisInfo> getBerthAxisInfo() {
		String jpql = "select a from HdShipPicSbcBerth a where 1=1 and a.berthTyp='01' order by a.dispSeq desc";
		QueryParamLs paramLs = new QueryParamLs();
		List<HdShipPicSbcBerth> list = JpaUtils.findAll(jpql, paramLs);
		List<BerthAxisInfo> berthAxisInfos = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			HdShipPicSbcBerth hdShipPicSbcBerth = list.get(i);
			BerthAxisInfo berthAxisInfo = new BerthAxisInfo();
			berthAxisInfo.setBerth(hdShipPicSbcBerth);
			jpql = "select a from HdShipPicSbcBollard a where a.berthCode =:berthCode order by a.cableSeq desc";
			paramLs = new QueryParamLs();
			paramLs.addParam("berthCode", hdShipPicSbcBerth.getBerthCode());
			List<HdShipPicSbcBollard> bollardList = JpaUtils.findAll(jpql, paramLs);
			// for (int j = 0; j < bollardList.size(); j++) {
			// bollardList.get(j).setBerthName(berth.getBerthName());
			// }
			// 没有揽桩 不添加
			if (bollardList.size() == 0) {
				continue;
			}
			berthAxisInfo.setBollardList(bollardList);
			// 计算水深
			berthAxisInfo.setWaterDepth("");
			berthAxisInfos.add(berthAxisInfo);
		}
		return berthAxisInfos;
	}

	private List<HdShipPicBerthPlanShipVisit> getShipList(String startdt, String enddt) {
		// todo 查询有工班计划的 揽桩有值的
		String jpql = "select a from HdShipPicBerthPlanShipVisit a where 1=1 and a.planBeginBollardCode is not null and a.planEndBollardCode is not null  and a.etb >=:start and a.etu <=:end ";
		QueryParamLs paramLs = new QueryParamLs();
		Timestamp startSt = Util.getTimestampByStr(startdt);
		Timestamp endSt = Util.getTimestampByStr(enddt);
		paramLs.addParam("start", startSt);
		paramLs.addParam("end", endSt);
		List<HdShipPicBerthPlanShipVisit> list = JpaUtils.findAll(jpql, paramLs);
		// todo 查询相应workTaskList
		for (HdShipPicBerthPlanShipVisit hdShipPicBerthPlanShipVisit : list) {
			if (HdUtils.strNotNull(hdShipPicBerthPlanShipVisit.getShipCodId())) {
				CShipData cShipData = JpaUtils.findById(CShipData.class, hdShipPicBerthPlanShipVisit.getShipCodId());
				hdShipPicBerthPlanShipVisit.setShipLongNum(cShipData.getShipLongNum());
				hdShipPicBerthPlanShipVisit.setShipLength(cShipData.getShipLongNum().multiply(new BigDecimal("1.2")));
				hdShipPicBerthPlanShipVisit.setPlanBerthingModeName(
						HdUtils.getSysCodeName("BERTH_WAY", hdShipPicBerthPlanShipVisit.getPlanBerthingMode()));
			}
		}
		return list;
	}

	// 泊位计划保存方法
	@Override
	@Transactional
	public HdMessageCode saveBerth(@RequestBody HdShipPicBerthPlanShipVisit hdShipPicBerthPlanShipVisit) {
		String id = hdShipPicBerthPlanShipVisit.getShipVisitId();
		String planBeginBollardCode = hdShipPicBerthPlanShipVisit.getPlanBeginBollardCode();
		String planEndBollardCode = hdShipPicBerthPlanShipVisit.getPlanEndBollardCode();
		int begCableNum = Integer.parseInt(planBeginBollardCode.substring(1, planBeginBollardCode.length()));
		int endCableNum = Integer.parseInt(planEndBollardCode.substring(1, planEndBollardCode.length()));
		if(begCableNum>501001&&endCableNum<501001){
			HdMessageCode hdMessageCode = new HdMessageCode();
			hdMessageCode.setCode("-1");
			hdMessageCode.setMessage("不允许跨码头操作船舶");
			return hdMessageCode;
		}
		if (HdUtils.strNotNull(id)) {
			HdShipPicBerthPlanShipVisit data = JpaUtils.findById(HdShipPicBerthPlanShipVisit.class, id);
			if (data != null) {
				JpaUtils.update(hdShipPicBerthPlanShipVisit);
			} else {
				JpaUtils.save(hdShipPicBerthPlanShipVisit);
			}
		}
		return HdUtils.genMsg();
	}
	
	@Override
	@Transactional
	public HdMessageCode saveBerthAndShip(HdShipPicBerthPlanShipVisit hdShipPicBerthPlanShipVisit) {
		String id = hdShipPicBerthPlanShipVisit.getShipVisitId();
		String planBeginBollardCode = hdShipPicBerthPlanShipVisit.getPlanBeginBollardCode();
		String planEndBollardCode = hdShipPicBerthPlanShipVisit.getPlanEndBollardCode();
		int begCableNum = Integer.parseInt(planBeginBollardCode.substring(1, planBeginBollardCode.length()));
		int endCableNum = Integer.parseInt(planEndBollardCode.substring(1, planEndBollardCode.length()));
		if(begCableNum>501001&&endCableNum<501001){
			HdMessageCode hdMessageCode = new HdMessageCode();
			hdMessageCode.setCode("-1");
			hdMessageCode.setMessage("不允许跨码头操作船舶");
			return hdMessageCode;
		}
		if (HdUtils.strNotNull(id)) {
			HdShipPicBerthPlanShipVisit data = JpaUtils.findById(HdShipPicBerthPlanShipVisit.class, id);
			if (data != null) {
				JpaUtils.update(hdShipPicBerthPlanShipVisit);
			} else {
				JpaUtils.save(hdShipPicBerthPlanShipVisit);
			}
			String shipNo = hdShipPicBerthPlanShipVisit.getShipNo();
			if(HdUtils.strNotNull(shipNo)){
				Ship ship = JpaUtils.findById(Ship.class,shipNo);
				if(ship!=null){
					ship.setBegCableNo(hdShipPicBerthPlanShipVisit.getPlanBeginBollardCode());
					ship.setEndCableNo(hdShipPicBerthPlanShipVisit.getPlanEndBollardCode());
					ship.setBerthWay(hdShipPicBerthPlanShipVisit.getPlanBerthingMode());
					JpaUtils.update(ship);
				}
			}
		}
		return HdUtils.genMsg();
	}

	@Override
	public HdMessageCode getBerthPlanShip(Map map) {
		String sql = "SELECT t.ship_no,\n" + "       t.c_ship_nam,\n" + "       t.ivoyage || '/' || t.evoyage voyage,\n"
				+ "       t2.i_e_id,\n" + "       NVL((CASE\n" + "             WHEN t2.i_e_id = 'I' THEN\n"
				+ "              t1.in_num\n" + "             ELSE\n" + "              t1.out_num\n"
				+ "           END),\n" + "           0) pwights,\n" + "       NVL((CASE\n"
				+ "             WHEN t2.i_e_id = 'I' THEN\n" + "              t1.in_num\n" + "             ELSE\n"
				+ "              t1.out_num\n" + "           END),\n" + "           0) pcarnum,\n"
				+ "       NVL(t2.weights, 0) actwights,\n" + "       NVL(t2.car_num, 0) actcarnum\n"
				+ "  FROM ship t,\n" + "       (SELECT SHIP_TRENDS_ID, OUT_NUM, IN_NUM FROM day_night_trends) t1,\n"
				+ "       (SELECT SHIP_TRENDS_ID, ship_no, TRENDS_TERMINI FROM ship_trends) t3,\n"
				+ "       (SELECT ship_no, i_e_id, SUM(weights) weights, SUM(car_num) car_num\n"
				+ "          FROM (SELECT m.ship_no,\n"
				+ "                       decode(m.work_typ, 'SI', 'I', 'SO', 'E') i_e_id,\n"
				+ "                       NVL(y.weights, 0) * NVL(COUNT(m.port_car_no), 0) weights,\n"
				+ "                       NVL(COUNT(m.port_car_no), 0) car_num\n"
				+ "                  FROM work_command m, c_car_typ y\n"
				+ "                 WHERE m.car_typ = y.car_typ(+) and m.work_typ in ('SI','SO')\n"
				+ "                 GROUP BY m.ship_no, y.weights, m.work_typ)\n"
				+ "         GROUP BY ship_no, i_e_id) t2\n" + " WHERE t.ship_no = t2.ship_no(+)\n"
				+ "   AND t.ship_no = t3.ship_no(+)\n" + "   ANd t2.ship_no = t3.ship_no\n"
				+ "   AND t.ship_stat = 'Y'\n" + "   AND t1.ship_trends_id = t3.ship_trends_id\n"
				+ "   AND t3.trends_termini in ('11','12')\n" + " ORDER BY UPD_TIM DESC";
		List<Map> rtLst = JpaUtils.getEntityManager().createNativeQuery(sql)
				.setHint(QueryHints.RESULT_TYPE, ResultType.Map).getResultList();
		HdMessageCode hdMessageCode = HdUtils.genMsg();
		hdMessageCode.setData(rtLst);
		return hdMessageCode;

	}

	

	
}
