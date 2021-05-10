package net.huadong.tech.ship.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.google.gson.Gson;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.reflect.TypeToken;

import net.huadong.tech.Interface.entity.VGroupCorpDayStatPlan;
import net.huadong.tech.Interface.entity.VGroupCorpShip;
import net.huadong.tech.base.bean.EzTreeBean;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.bean.HdRunTimeException;
import net.huadong.tech.base.entity.CBerth;
import net.huadong.tech.base.entity.CCable;
import net.huadong.tech.base.entity.CClientCod;
import net.huadong.tech.base.entity.CDamage;
import net.huadong.tech.base.entity.CPort;
import net.huadong.tech.base.entity.CShipStat;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.tech.inter.entity.RespInter;
import net.huadong.tech.privilege.entity.AuthUser;
import net.huadong.tech.ship.entity.CShipData;
import net.huadong.tech.ship.entity.Ship;
import net.huadong.tech.ship.entity.ShipGroup;
import net.huadong.tech.ship.entity.ShipTrend;
import net.huadong.tech.ship.entity.ShipTrendJhGroup;
import net.huadong.tech.ship.entity.ShipLoadPlan;
import net.huadong.tech.ship.service.ShipTrendService;
import net.huadong.tech.shipbill.entity.BillSplit;
import net.huadong.idev.hdmessagecode.HdMessageCode;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiQueryParams;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;
import net.huadong.tech.util.HdUtils;
import net.huadong.tech.work.entity.WorkBill;

/**
 * @author
 */
@Component
public class ShipTrendServiceImpl implements ShipTrendService {

	@Value("${cshipdata.token}")
	private String token;

	@Value("${api.service.ip}")
	private String apiServiceIp;

	@Value("${tjgjt.service.ip}")
	private String tjgjtServiceIp;

	@Override
	public HdEzuiDatagridData find(HdQuery hdQuery) {
		String jpql = "select a from ShipTrend a where 1=1 ";
		String shipNo = hdQuery.getStr("shipNo");
		String trendsTyp = hdQuery.getStr("trendsTyp");
		QueryParamLs paramLs = new QueryParamLs();
		if (HdUtils.strNotNull(shipNo)) {
			jpql += "and a.shipNo =:shipNo ";
			paramLs.addParam("shipNo", shipNo);
		}
		if (HdUtils.strNotNull(trendsTyp) && !"A".equals(trendsTyp) && !ShipTrend.SQJH.equals(trendsTyp)) {
			jpql += "and a.trendsTyp =:trendsTyp ";
			paramLs.addParam("trendsTyp", trendsTyp);
		}
		if (ShipTrend.SQJH.equals(trendsTyp)) {
			jpql += "and a.trendsTyp in ('01','02') ";
		}
		jpql += "order by a.trendsBegTim desc";
		HdEzuiDatagridData result = JpaUtils.findByEz(jpql, paramLs, hdQuery);
		List<ShipTrend> shipTrendList = result.getRows();
		for (ShipTrend shipTrend : shipTrendList) {
			if (HdUtils.strNotNull(shipTrend.getBegCableNo())) {
				CCable cCable = JpaUtils.findById(CCable.class, shipTrend.getBegCableNo());
				CBerth cBerth = JpaUtils.findById(CBerth.class, cCable.getBerthCod());
				shipTrend.setBegCableNoNam(cCable.getCableNo() + "/" + cBerth.getBerthNam());
			}
			if (HdUtils.strNotNull(shipTrend.getEndCableNo())) {
				CCable cCable = JpaUtils.findById(CCable.class, shipTrend.getEndCableNo());
				CBerth cBerth = JpaUtils.findById(CBerth.class, cCable.getBerthCod());
				shipTrend.setEndCableNoNam(cCable.getCableNo() + "/" + cBerth.getBerthNam());
			}
			if (HdUtils.strNotNull(shipTrend.getShipNo())) {
				Ship ship = JpaUtils.findById(Ship.class, shipTrend.getShipNo());
				if (ship != null) {
					shipTrend.setShipVoyage(ship.getcShipNam() + "/" + ship.getIvoyage());
				}
			}

			if (HdUtils.strNotNull(shipTrend.getTrendsBegArea())) {
				shipTrend.setTrendsBegAreaNam(
						JpaUtils.findById(CBerth.class, shipTrend.getTrendsBegArea()).getBerthNam());
			}
			if (HdUtils.strNotNull(shipTrend.getTrendsEndArea())) {
				shipTrend.setTrendsEndAreaNam(
						JpaUtils.findById(CBerth.class, shipTrend.getTrendsEndArea()).getBerthNam());
			}
			if (HdUtils.strNotNull(shipTrend.getTrendsTyp())) {
				shipTrend.setTrendsTypNam(HdUtils.getSysCodeName("TRENDS_TYP", shipTrend.getTrendsTyp()));
			}
		}
		return result;
	}

	@Override
	public void updateShipTrendJt() {
		String resp = null;
		JSONObject obj = new JSONObject();
		obj.put("token", token);
		String query = obj.toString();
		// application里设置的参数
		String urlStr = apiServiceIp + "8091/ScheduleSysWebApi/getShipTrendsPlan";
		// 返回结果集
		List<ShipTrendJhGroup> list = new ArrayList<ShipTrendJhGroup>();
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
			list = gson.fromJson(resp, new TypeToken<List<ShipTrendJhGroup>>() {
			}.getType());
		}
		List<ShipTrend> shipTrendAllList = new ArrayList();
		List<Ship> shipList = new ArrayList();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for (ShipTrendJhGroup shipTrendJhGroup : list) {
			String jpql = "select a from ShipTrend a where a.shipTrendsCod =:shipTrendsCod and a.groupShipNo =:groupShipNo";
			QueryParamLs paramLs = new QueryParamLs();
			paramLs.addParam("shipTrendsCod", shipTrendJhGroup.getDtbh());
			paramLs.addParam("groupShipNo", shipTrendJhGroup.getCbxh());
			List<ShipTrend> shipTrendList = JpaUtils.findAll(jpql, paramLs);
			if (shipTrendList.size() <= 0) {
				continue;
			} else {
				ShipTrend shipTrend = shipTrendList.get(0);
				try {
					shipTrend.setTrendsPlanTim(new Timestamp(sf.parse(shipTrendJhGroup.getJhsj()).getTime()));
				} catch (Exception e) {
					e.printStackTrace();
				}
				shipTrend.setUseTag(shipTrendJhGroup.getTl());
				shipTrend.setUsePilot(shipTrendJhGroup.getYhbzdm());
				shipTrend.setSendFlag(shipTrendJhGroup.getFsbz());
				shipTrend.setTrendsTyp(shipTrend.JH);
				shipTrendAllList.add(shipTrend);
				Ship ship = JpaUtils.findById(Ship.class, shipTrend.getShipNo());
				if (ship != null && HdUtils.strNotNull(shipTrendJhGroup.getDksj())) {
					try {
						ship.setConArrvTim(new Timestamp(sf.parse(shipTrendJhGroup.getDksj()).getTime()));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				if (shipTrend.J.equals(shipTrend.getTrendsTermini())
						|| shipTrend.JZK.equals(shipTrend.getTrendsTermini())
						|| shipTrend.JYK.equals(shipTrend.getTrendsTermini())) {
					try {
						ship.setEtdArrvTim(new Timestamp(sf.parse(shipTrendJhGroup.getJhsj()).getTime()));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				shipList.add(ship);
			}
		}
		JpaUtils.updateAll(shipTrendAllList);
		JpaUtils.updateAll(shipList);
		// 调用接口更新状态为实际的数据
		JSONObject obj1 = new JSONObject();
		obj1.put("token", token);
		String query1 = obj.toString();
		// application里设置的参数
		String urlStr1 = apiServiceIp + "8091/ScheduleSysWebApi/getShipTrendsHis";
		// 返回结果集
		List<ShipTrendJhGroup> list1 = new ArrayList<ShipTrendJhGroup>();
		try {
			URL url1 = new URL(urlStr1);
			HttpURLConnection con1 = (HttpURLConnection) url1.openConnection();

			// 设置链接超时
			con1.setConnectTimeout(3000);

			// 设置读取超时
			con1.setReadTimeout(3000);

			// 设置参数
			con1.setDoOutput(true);
			con1.setDoInput(true);
			con1.setRequestMethod("POST");
			con1.setRequestProperty("Content-type", "application/json");
			con1.connect();

			try (OutputStream os1 = con1.getOutputStream()) {
				os1.write(query1.getBytes("UTF-8"));
			}

			try (BufferedReader reader1 = new BufferedReader(new InputStreamReader(con1.getInputStream(), "UTF-8"))) {
				String lines1;
				StringBuffer sbf1 = new StringBuffer();
				while ((lines1 = reader1.readLine()) != null) {
					// lines = new String(lines.getBytes(), "utf-8");
					sbf1.append(lines1);
				}
				resp = sbf1.toString();
			}
			con1.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Gson gson1 = new Gson();
			list1 = gson1.fromJson(resp, new TypeToken<List<ShipTrendJhGroup>>() {
			}.getType());
		}
		List<ShipTrend> shipList1 = new ArrayList();
		List<Ship> shipList2 = new ArrayList();
		for (ShipTrendJhGroup shipTrendJhGroup : list1) {
			if (ShipTrend.S.equals(shipTrendJhGroup.getFsbz())) {
				String jpql = "select a from ShipTrend a where a.shipTrendsCod =:shipTrendsCod and a.groupShipNo =:groupShipNo";
				QueryParamLs paramLs = new QueryParamLs();
				paramLs.addParam("shipTrendsCod", shipTrendJhGroup.getDtbh());
				paramLs.addParam("groupShipNo", shipTrendJhGroup.getCbxh());
				List<ShipTrend> shipTrendList = JpaUtils.findAll(jpql, paramLs);
				if (shipTrendList.size() <= 0) {
					continue;
				} else {
					ShipTrend shipTrend = shipTrendList.get(0);
					try {
						shipTrend.setTrendsPlanTim(new Timestamp(sf.parse(shipTrendJhGroup.getJhsj()).getTime()));
					} catch (Exception e) {
						e.printStackTrace();
					}
					shipTrend.setUseTag(shipTrendJhGroup.getTl());
					shipTrend.setUsePilot(shipTrendJhGroup.getYhbzdm());
					shipTrend.setSendFlag(shipTrendJhGroup.getFsbz());
					shipTrend.setTrendsTyp(ShipTrend.SJ);
					shipList1.add(shipTrend);
					Ship ship = JpaUtils.findById(Ship.class, shipTrend.getShipNo());
					if (ship != null && HdUtils.strNotNull(shipTrendJhGroup.getDksj())) {
						try {
							ship.setConArrvTim(new Timestamp(sf.parse(shipTrendJhGroup.getDksj()).getTime()));
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					if (shipTrend.J.equals(shipTrend.getTrendsTermini())
							|| shipTrend.JZK.equals(shipTrend.getTrendsTermini())
							|| shipTrend.JYK.equals(shipTrend.getTrendsTermini())) {
						try {
							ship.setEtdArrvTim(new Timestamp(sf.parse(shipTrendJhGroup.getJhsj()).getTime()));
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					shipList2.add(ship);
				}
			}
		}
		JpaUtils.updateAll(shipList1);
		JpaUtils.updateAll(shipList2);
	}

	@Override
	public HdEzuiDatagridData findShipTrendJt(HdQuery hdQuery) {
		String jpql = "select a from VGroupCorpDayStatPlan a where a.statType =:statType and a.teamOrgnId =:teamOrgnId";
		QueryParamLs paramLs = new QueryParamLs();
		paramLs.addParam("statType", "Y");
		paramLs.addParam("teamOrgnId", "03406500");
		List<VGroupCorpDayStatPlan> list = JpaUtils.findAll(jpql, paramLs);
		HdEzuiDatagridData result = new HdEzuiDatagridData();
		List<ShipTrend> shipTrendAllList = new ArrayList();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for (VGroupCorpDayStatPlan bean : list) {
			ShipTrend shipTrend = new ShipTrend();
			String date = HdUtils.getDateTime().toString();
			String sql = "select a from Ship a where a.newGroupShipNo =:newGroupShipNo";
			QueryParamLs param = new QueryParamLs();
			param.addParam("newGroupShipNo", bean.getSvoyageId());
			List<Ship> shipList = JpaUtils.findAll(sql, param);
			if(shipList.size() > 0){
				shipTrend.setShipNo(shipList.get(0).getShipNo());
				shipTrend.setShipVoyage(shipList.get(0).getcShipNam());
			}else{
				shipTrend.setShipVoyage(bean.getShipName());
			}
			shipTrend.setShipTrendsCod(date.substring(11, 19));
			shipTrend.setTrendsPlanTim(new Timestamp(bean.getStatTime().getTime()));
			shipTrend.setGroupShipNo(bean.getSvoyageId());
			if (HdUtils.strNotNull(bean.getBerthCode())) {
				String jpql1 = "select a from CBerth a where a.groupBerthCod =:groupBerthCod";
				QueryParamLs paramLs1 = new QueryParamLs();
				paramLs1.addParam("groupBerthCod", bean.getBerthCode());
				List<CBerth> list1 = JpaUtils.findAll(jpql1, paramLs1);
				if (list1.size() > 0) { 
					shipTrend.setTrendsBegArea(list1.get(0).getBerthCod());
					shipTrend.setTrendsBegAreaNam(list1.get(0).getBerthNam());
				}
			}
			if (HdUtils.strNotNull(bean.getPlanBerthCode())) {
				String jpql1 = "select a from CBerth a where a.groupBerthCod =:groupBerthCod";
				QueryParamLs paramLs1 = new QueryParamLs();
				paramLs1.addParam("groupBerthCod", bean.getPlanBerthCode());
				List<CBerth> list1 = JpaUtils.findAll(jpql1, paramLs1);
				if (list1.size() > 0) { 
					shipTrend.setTrendsEndArea(list1.get(0).getBerthCod());
					shipTrend.setTrendsEndAreaNam(list1.get(0).getBerthNam());
				}
			}
			shipTrend.setTrendsTermini(bean.getStatCode());
			shipTrend.setShipDraft(bean.getDraft());
			if (bean.getTugNum() != null){
				shipTrend.setUseTag(bean.getTugNum().toString());
			}
			shipTrend.setUsePilot(bean.getPilotFlag());
			shipTrend.setTrendsBegTim(HdUtils.getDateTime());
			if(HdUtils.strNotNull(bean.getBegCable())){
				String jpql2 = "select a from CCable a,VGroupCorpBerthCable b, VGroupCorpBerth c,CBerth d where c.berthCode =:berthCode and c.berthName = d.berthNam and d.berthCod = a.berthCod  and a.cableSeq =:cableSeq";
				QueryParamLs paramLs2 = new QueryParamLs();
				paramLs2.addParam("berthCode", bean.getPlanBerthCode());
				if (bean.getBegCable().endsWith("#")){
					paramLs2.addParam("cableSeq", Long.valueOf("333"));
				} else {
					paramLs2.addParam("cableSeq", Long.valueOf(bean.getBegCable()));
				}
				List<CCable> cCableList = JpaUtils.findAll(jpql2, paramLs2);
				if(cCableList.size() > 0){
					shipTrend.setBegCableNo(cCableList.get(0).getCableCod());
				}
			}
			if(HdUtils.strNotNull(bean.getEndCable())){
				String jpql2 = "select a from CCable a,VGroupCorpBerthCable b, VGroupCorpBerth c,CBerth d where c.berthCode =:berthCode and c.berthName = d.berthNam and d.berthCod = a.berthCod  and a.cableSeq =:cableSeq";
				QueryParamLs paramLs2 = new QueryParamLs();
				paramLs2.addParam("berthCode", bean.getPlanBerthCode());
				if (bean.getEndCable().endsWith("#")){
					paramLs2.addParam("cableSeq", Long.valueOf("333"));
				} else {
					paramLs2.addParam("cableSeq", Long.valueOf(bean.getEndCable()));
				}
				List<CCable> cCableList = JpaUtils.findAll(jpql2, paramLs2);
				if(cCableList.size() > 0){
					shipTrend.setEndCableNo(cCableList.get(0).getCableCod());
				}
			}
			shipTrend.setTrendsTyp(ShipTrend.JH);
			shipTrend.setiDanCargo("WN");
	        shipTrend.seteDanCargo("WN");
			shipTrendAllList.add(shipTrend);
		}
		result.setRows(shipTrendAllList);
		return result;
	}

	@Override
	public HdMessageCode save(HdEzuiSaveDatagridData<ShipTrend> hdEzuiSaveDatagridData) {
		// TODO Auto-generated method stub
		List<ShipTrend> shipTrendList = hdEzuiSaveDatagridData.getInsertedRows();
		for (ShipTrend shipTrend : shipTrendList) {
			if(HdUtils.strIsNull(shipTrend.getShipNo())){
				throw new HdRunTimeException("请先在船舶航次预报里完善该船的信息！");
			}
			String jpql = "select a from ShipTrend a where a.groupShipNo =:groupShipNo and a.shipTrendsCod =:shipTrendsCod";
			QueryParamLs paramLs = new QueryParamLs();
			paramLs.addParam("groupShipNo", shipTrend.getGroupShipNo());
			paramLs.addParam("shipTrendsCod", shipTrend.getShipTrendsCod());
			List<ShipTrend> resultList = JpaUtils.findAll(jpql, paramLs);
			if (resultList.size() > 0) {
				throw new HdRunTimeException("该条数据已存在！");
			}
			shipTrend.setShipTrendsId(HdUtils.genUuid());
			String date = HdUtils.getDateTime().toString();
			shipTrend.setShipTrendsCod(date.substring(11, 19));
			if (HdUtils.strNotNull(shipTrend.getTrendsBegAreaNam())
					&& HdUtils.strIsNull(shipTrend.getTrendsBegArea())) {
				shipTrend.setTrendsBegArea(shipTrend.getTrendsBegAreaNam());
			}
			if (HdUtils.strNotNull(shipTrend.getTrendsEndAreaNam())
					&& HdUtils.strIsNull(shipTrend.getTrendsEndArea())) {
				shipTrend.setTrendsEndArea(shipTrend.getTrendsEndAreaNam());
			}
			if (HdUtils.strNotNull(shipTrend.getBegCableNoNam()) && HdUtils.strIsNull(shipTrend.getBegCableNo())) {
				shipTrend.setBegCableNo(shipTrend.getBegCableNoNam());
			}
			if (HdUtils.strNotNull(shipTrend.getEndCableNoNam()) && HdUtils.strIsNull(shipTrend.getEndCableNo())) {
				shipTrend.setEndCableNo(shipTrend.getEndCableNoNam());
			}
//			Ship ship = JpaUtils.findById(Ship.class, shipTrend.getShipNo());
//			if (ship != null) {
//				if (shipTrend.getTrendsBegTim().compareTo(ship.getConArrvTim()) < 0) {
//					throw new HdRunTimeException("动态申请时间不得早于抵口时间");// 出口航次重复
//				}
//			}

		}
		List<ShipTrend> shipTrendList1 = hdEzuiSaveDatagridData.getUpdatedRows();
		for (ShipTrend shipTrend : shipTrendList1) {
			if ("02".equals(shipTrend.getTrendsTyp())) {
				shipTrend.setTrendsTyp("03");
			}
			if (HdUtils.strNotNull(shipTrend.getTrendsBegArea())) {
				CBerth cBerth1 = JpaUtils.findById(CBerth.class, shipTrend.getTrendsBegArea());
				if (!cBerth1.getBerthNam().equals(shipTrend.getTrendsBegAreaNam())) {
					shipTrend.setTrendsBegArea(shipTrend.getTrendsBegAreaNam());
				}
			}
			if (HdUtils.strNotNull(shipTrend.getTrendsEndArea())) {
				CBerth cBerth2 = JpaUtils.findById(CBerth.class, shipTrend.getTrendsEndArea());
				if (!cBerth2.getBerthNam().equals(shipTrend.getTrendsEndAreaNam())) {
					shipTrend.setTrendsEndArea(shipTrend.getTrendsEndAreaNam());
				}
			} else {
				shipTrend.setTrendsEndArea(shipTrend.getTrendsEndAreaNam());
			}

			if (HdUtils.strNotNull(shipTrend.getBegCableNoNam())) {
				CCable cCable = JpaUtils.findById(CCable.class, shipTrend.getBegCableNoNam());
				if (cCable != null) {
					shipTrend.setBegCableNo(shipTrend.getBegCableNoNam());
				}
			}

			if (HdUtils.strNotNull(shipTrend.getEndCableNoNam())) {
				CCable cCable1 = JpaUtils.findById(CCable.class, shipTrend.getEndCableNoNam());
				if (cCable1 != null) {
					shipTrend.setEndCableNo(shipTrend.getEndCableNoNam());
				}
			}
			Ship ship = JpaUtils.findById(Ship.class, shipTrend.getShipNo());
			if (ship != null) {
				if (shipTrend.getTrendsBegTim().compareTo(ship.getConArrvTim()) < 0) {
					throw new HdRunTimeException("动态申请时间不得早于抵口时间");// 出口航次重复
				}
			}
		}
		return JpaUtils.save(hdEzuiSaveDatagridData);
	}

	@Override
	public void saveDtedit(HdEzuiSaveDatagridData<ShipTrend> hdEzuiSaveDatagridData) {
		// TODO Auto-generated method stub
		List<ShipTrend> shipTrendList = hdEzuiSaveDatagridData.getInsertedRows();
		String shipTrendsId = "";
		for (ShipTrend shipTrend : shipTrendList) {
			shipTrendsId = shipTrend.getShipTrendsId();
			shipTrend.setShipTrendsId(HdUtils.genUuid());
			shipTrend.setSendFlag(ShipTrend.B);
			if (HdUtils.strNotNull(shipTrend.getTrendsBegArea())) {
				CBerth cBerth1 = JpaUtils.findById(CBerth.class, shipTrend.getTrendsBegArea());
				if (!cBerth1.getBerthNam().equals(shipTrend.getTrendsBegAreaNam())) {
					shipTrend.setTrendsBegArea(shipTrend.getTrendsBegAreaNam());
				}
			}
			if (HdUtils.strNotNull(shipTrend.getTrendsEndArea())) {
				CBerth cBerth2 = JpaUtils.findById(CBerth.class, shipTrend.getTrendsEndArea());
				if (!cBerth2.getBerthNam().equals(shipTrend.getTrendsEndAreaNam())) {
					shipTrend.setTrendsEndArea(shipTrend.getTrendsEndAreaNam());
				}
			}

			if (HdUtils.strNotNull(shipTrend.getBegCableNoNam())) {
				CCable cCable = JpaUtils.findById(CCable.class, shipTrend.getBegCableNoNam());
				if (cCable != null) {
					shipTrend.setBegCableNo(shipTrend.getBegCableNoNam());
				}
			}

			if (HdUtils.strNotNull(shipTrend.getEndCableNoNam())) {
				CCable cCable1 = JpaUtils.findById(CCable.class, shipTrend.getEndCableNoNam());
				if (cCable1 != null) {
					shipTrend.setEndCableNo(shipTrend.getEndCableNoNam());
				}
			}
		}
		JpaUtils.save(hdEzuiSaveDatagridData);
		if (HdUtils.strNotNull(shipTrendsId)) {
			ShipTrend shipTrend1 = JpaUtils.findById(ShipTrend.class, shipTrendsId);
			if (shipTrend1 != null) {
				shipTrend1.setTrendsTyp(ShipTrend.SJ);
				shipTrend1.setSendFlag(ShipTrend.F);
				JpaUtils.update(shipTrend1);
			}
		}
		for (ShipTrend shipTrend : shipTrendList) {
			if (HdUtils.strNotNull(shipTrend.getShipTrendsId())) {
				dtchange(shipTrend.getShipTrendsId());
			} else {
				throw new HdRunTimeException("发送动态变更失败！");
			}
		}
	}

	// 集团导入数据的保存
	@Override
	public HdMessageCode savejt(HdEzuiSaveDatagridData<ShipTrend> hdEzuiSaveDatagridData) {
		// TODO Auto-generated method stub
		List<ShipTrend> shipTrendList = hdEzuiSaveDatagridData.getInsertedRows();
		for (ShipTrend shipTrend : shipTrendList) {
			shipTrend.setShipTrendsId(HdUtils.genUuid());
			String date = HdUtils.getDateTime().toString();
			shipTrend.setShipTrendsCod(date.substring(11, 19));
			shipTrend.setTrendsTyp(ShipTrend.JH);
		}
		return JpaUtils.save(hdEzuiSaveDatagridData);
	}

	@Transactional
	public void removeAll(String shipTrendsIds) {
		List<String> shipTrendsIdList = HdUtils.paraseStrs(shipTrendsIds);
		for (String shipTrendsId : shipTrendsIdList) {
			// if (isExist(shipNo)) {
			// throw new HdRunTimeException("此信息已被使用,暂时无法删除！");// 船代码重复
			// }
			JpaUtils.remove(ShipTrend.class, shipTrendsId);
			// 删掉work_queue表关联数据
			// String jpql = "select a from WorkQueue a where a.workTyp in
			// ('SI','SO') and a.shipNo =:shipNo";
			// QueryParamLs params=new QueryParamLs();
			// params.addParam("shipNo", shipNo);
			// List<WorkQueue> workQueueList = JpaUtils.findAll(jpql, paramLs);
			// JpaUtils.removeAll(workQueueList);

		}
	}

	@Override
	@Transactional
	public void importAll(String shipTrendsIds) {
		List<String> shipTrendsIdList = HdUtils.paraseStrs(shipTrendsIds);
		for (String shipTrendsId : shipTrendsIdList) {
			importShipTrend(shipTrendsId);
		}
	}

	@Override
	@Transactional
	public void uploadAll(String shipTrendsIds) {
		List<String> shipTrendsIdList = HdUtils.paraseStrs(shipTrendsIds);
		for (String shipTrendsId : shipTrendsIdList) {
			uploadShipTrend(shipTrendsId);
		}
	}

	@Override
	public ShipTrend findone(String shipTrendsId) {
		ShipTrend shipTrend = JpaUtils.findById(ShipTrend.class, shipTrendsId);
		return shipTrend;

	}

	@Override
	@Transactional
	public HdMessageCode saveone(@RequestBody ShipTrend shipTrend) {
		if (HdUtils.strNotNull(shipTrend.getShipTrendsId())) {
			JpaUtils.update(shipTrend);
		} else {
			JpaUtils.save(shipTrend);
		}
		return HdUtils.genMsg();
	}

	public void uploadShipTrend(String shipTrendsId) {
		ShipTrend bean = JpaUtils.findById(ShipTrend.class, shipTrendsId);
		JSONObject jsonObj = new JSONObject();
		Map<String, String> map = new HashMap<String, String>();
		Ship ship = JpaUtils.findById(Ship.class, bean.getShipNo());
		jsonObj.put("cmdId", "2005");
		jsonObj.put("coId", ship.GZ);
		map.put("teamOrgnId", ship.GZ);
		map.put("dsaskId", bean.getShipTrendsId());
		map.put("svoyageId", ship.getNewGroupShipNo());
		map.put("shipName", ship.getcShipNam());
		map.put("statTime", bean.getTrendsBegTim().toString().substring(0, 16));
		if (HdUtils.strNotNull(bean.getTrendsBegArea())) {
			CBerth cBerth = JpaUtils.findById(CBerth.class, bean.getTrendsBegArea());
			map.put("berthCode", cBerth.getGroupBerthCod());
		}
		map.put("statCode", bean.getTrendsTermini());
		if (HdUtils.strNotNull(bean.getTrendsEndArea())) {
			CBerth cBerth = JpaUtils.findById(CBerth.class, bean.getTrendsEndArea());
			map.put("planBerthCode", cBerth.getGroupBerthCod());
		}
		map.put("draft", bean.getShipDraft().toString());
		map.put("tugNum", bean.getUseTag());
		if (HdUtils.strNotNull(bean.getBegCableNo())) {
			CCable cCable = JpaUtils.findById(CCable.class, bean.getBegCableNo());
			map.put("begCable", cCable.getCableNo());
		}
		if (HdUtils.strNotNull(bean.getEndCableNo())) {
			CCable cCable = JpaUtils.findById(CCable.class, bean.getEndCableNo());
			map.put("endCable", cCable.getCableNo());
		}
		map.put("cableDistance", bean.getShipToCable());
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
					throw new HdRunTimeException("上报集团成功！");
				}
				if (!resCode.equals(jsonObject.getString("resCode"))
						|| !resMsg.equals(jsonObject.getString("resMsg"))) {
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

	public void importShipTrend(String shipTrendsId) {
		ShipTrend shipTrend = JpaUtils.findById(ShipTrend.class, shipTrendsId);
		JSONObject jsonObj = new JSONObject();
		String jpql = "select a from AuthUser a where a.account =:account";
		QueryParamLs paramLs = new QueryParamLs();
		paramLs.addParam("account", shipTrend.getRecNam());
		List<AuthUser> authUserList = JpaUtils.findAll(jpql, paramLs);
		for (AuthUser authUser : authUserList) {
			if ("admin".equals(authUser.getAccount())) {
				jsonObj.put("czr", "管理员");
			} else {
				jsonObj.put("czr", authUser.getName());
			}
		}
		if (shipTrend != null) {
			jsonObj.put("cbxh", shipTrend.getGroupShipNo());
			jsonObj.put("dtbh", shipTrend.getShipTrendsCod());
			jsonObj.put("qssj", shipTrend.getTrendsBegTim());
			jsonObj.put("gsdm", "03406500");
			jsonObj.put("qsdd", shipTrend.getTrendsBegArea());
			jsonObj.put("cbdxdm", shipTrend.getTrendsTermini());
			jsonObj.put("zzdd", shipTrend.getTrendsEndArea());
			jsonObj.put("cs", shipTrend.getShipDraft());
			jsonObj.put("tl", shipTrend.getUseTag());
			jsonObj.put("yhbzdm", shipTrend.getUsePilot());
			jsonObj.put("bz", shipTrend.getRemarks());
			jsonObj.put("czdw", "03406500");
			jsonObj.put("czsj", shipTrend.getRecTim());
			jsonObj.put("fsbz", shipTrend.getSendFlag());
			jsonObj.put("qrr", "");
			jsonObj.put("qrbz", "");
			jsonObj.put("xgbm", "");
			jsonObj.put("tzjl", shipTrend.getShipToCable());
			jsonObj.put("wzh", shipTrend.getBegCableNo());
			jsonObj.put("wzfx", shipTrend.getEndCableNo());
			jsonObj.put("gsbgyy", shipTrend.getTrendsChangeTxt());
			jsonObj.put("wxp", shipTrend.getiDanCargo());
			jsonObj.put("gq", "1");
			jsonObj.put("token", token);
		}

		String url = apiServiceIp + "8091/ScheduleSysWebApi/setShipTrendsApply";
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
					throw new HdRunTimeException("船舶动态上报失败！");
				}
				if (str2.equals(response) && HdUtils.strNotNull(response)) {
					shipTrend.setSendFlag(ShipTrend.F);
					JpaUtils.update(shipTrend);
				}
				// JSONObject j = JSON.parseObject(response);
			}
			// 断开连接
			conn.disconnect();
		} catch (Exception e) {
			throw new HdRunTimeException("发送 POST 请求出现异常！");
			// System.out.println("发送 POST 请求出现异常！" + e);
			// e.printStackTrace();
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

		// 危险品接口
		JSONObject jsonObj1 = new JSONObject();
		if (shipTrend != null) {
			jsonObj1.put("cbxh", shipTrend.getGroupShipNo());
			jsonObj1.put("gsdm", "03406500");
			jsonObj1.put("jkwpbz", shipTrend.getiDanCargo());
			jsonObj1.put("ckwpbz", shipTrend.geteDanCargo());
			jsonObj1.put("czsj", shipTrend.getRecTim());
			jsonObj1.put("token", token);
		}

		String url1 = apiServiceIp + "8091/ScheduleSysWebApi/setShipDanInfo";
		String query1 = jsonObj1.toString();

		String response1 = "";
		try {
			URL httpUrl1 = null; // HTTP URL类 用这个类来创建连接
			// 创建URL
			httpUrl1 = new URL(url1);
			// 建立连接
			HttpURLConnection conn1 = (HttpURLConnection) httpUrl1.openConnection();
			conn1.setRequestMethod("POST");
			conn1.setRequestProperty("Content-Type", "application/json");
			conn1.setDoOutput(true);
			conn1.setDoInput(true);
			conn1.connect();
			// POST请求
			try (OutputStream os1 = conn1.getOutputStream()) {
				os1.write(query1.getBytes("UTF-8"));
			}
			// out.flush();
			// 读取响应
			try (BufferedReader reader1 = new BufferedReader(new InputStreamReader(conn1.getInputStream()))) {
				String lines1;
				StringBuffer sbf1 = new StringBuffer();
				while ((lines1 = reader1.readLine()) != null) {
					lines1 = new String(lines1.getBytes(), "utf-8");
					sbf1.append(lines1);
				}
				response1 = sbf1.toString();
				String str = '{' + "\"code\"" + ":0" + '}';
				String str2 = '{' + "\"code\"" + ":1" + '}';
				if (str.equals(response1)) {
					throw new HdRunTimeException("危品上报失败！");
				}
				// JSONObject j = JSON.parseObject(response);
			}
			// 断开连接
			conn1.disconnect();
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

	public void dtdelete(String shipTrendsId) {
		ShipTrend shipTrend = JpaUtils.findById(ShipTrend.class, shipTrendsId);
		JSONObject jsonObj = new JSONObject();
		String jpql = "select a from AuthUser a where a.account =:account";
		QueryParamLs paramLs = new QueryParamLs();
		paramLs.addParam("account", shipTrend.getRecNam());
		List<AuthUser> authUserList = JpaUtils.findAll(jpql, paramLs);
		for (AuthUser authUser : authUserList) {
			if ("admin".equals(authUser.getAccount())) {
				jsonObj.put("czr", "管理员");
			} else {
				jsonObj.put("czr", authUser.getName());
			}
		}
		if (HdUtils.strNotNull(shipTrend.getShipNo())) {
			Ship ship = JpaUtils.findById(Ship.class, shipTrend.getShipNo());
			jsonObj.put("wzh", ship.getBegCableNo());
			jsonObj.put("wzfx", ship.getEndCableNo());
		}
		if (shipTrend != null) {
			jsonObj.put("cbxh", shipTrend.getGroupShipNo());
			jsonObj.put("dtbh", shipTrend.getShipTrendsCod());
			jsonObj.put("qssj", shipTrend.getTrendsBegTim());
			jsonObj.put("gsdm", "03406500");
			jsonObj.put("qsdd", shipTrend.getTrendsBegArea());
			jsonObj.put("cbdxdm", shipTrend.getTrendsTermini());
			jsonObj.put("zzdd", shipTrend.getTrendsEndArea());
			jsonObj.put("cs", shipTrend.getShipDraft());
			jsonObj.put("tl", shipTrend.getUseTag());
			jsonObj.put("yhbzdm", shipTrend.getUsePilot());
			jsonObj.put("bz", shipTrend.getRemarks());
			jsonObj.put("czdw", "03406500");
			jsonObj.put("czsj", shipTrend.getRecTim());
			jsonObj.put("fsbz", "T");// 发送标志设为T 视为删除
			jsonObj.put("qrr", "");
			jsonObj.put("qrbz", "");
			jsonObj.put("xgbm", "");
			jsonObj.put("tzjl", shipTrend.getShipToCable());
			jsonObj.put("gsbgyy", shipTrend.getTrendsChangeTxt());
			jsonObj.put("wxp", shipTrend.getiDanCargo());
			jsonObj.put("token", token);
		}

		String url = apiServiceIp + "8091/ScheduleSysWebApi/setShipTrendsChange";
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
					throw new HdRunTimeException("上报失败！");
				}
				// JSONObject j = JSON.parseObject(response);
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

	public void dtchange(String shipTrendsId) {
		ShipTrend shipTrend = JpaUtils.findById(ShipTrend.class, shipTrendsId);
		JSONObject jsonObj = new JSONObject();
		String jpql = "select a from AuthUser a where a.account =:account";
		QueryParamLs paramLs = new QueryParamLs();
		paramLs.addParam("account", shipTrend.getRecNam());
		List<AuthUser> authUserList = JpaUtils.findAll(jpql, paramLs);
		for (AuthUser authUser : authUserList) {
			if ("admin".equals(authUser.getAccount())) {
				jsonObj.put("czr", "管理员");
			} else {
				jsonObj.put("czr", authUser.getName());
			}
		}
		if (HdUtils.strNotNull(shipTrend.getShipNo())) {
			Ship ship = JpaUtils.findById(Ship.class, shipTrend.getShipNo());
			jsonObj.put("wzh", ship.getBegCableNo());
			jsonObj.put("wzfx", ship.getEndCableNo());
		}
		if (shipTrend != null) {
			jsonObj.put("cbxh", shipTrend.getGroupShipNo());
			jsonObj.put("dtbh", shipTrend.getShipTrendsCod());
			jsonObj.put("qssj", shipTrend.getTrendsBegTim());
			jsonObj.put("gsdm", "03406500");
			jsonObj.put("qsdd", shipTrend.getTrendsBegArea());
			jsonObj.put("cbdxdm", shipTrend.getTrendsTermini());
			jsonObj.put("zzdd", shipTrend.getTrendsEndArea());
			jsonObj.put("cs", shipTrend.getShipDraft());
			jsonObj.put("tl", shipTrend.getUseTag());
			jsonObj.put("yhbzdm", shipTrend.getUsePilot());
			jsonObj.put("bz", shipTrend.getRemarks());
			jsonObj.put("czdw", "03406500");
			jsonObj.put("czsj", shipTrend.getRecTim());
			jsonObj.put("fsbz", "B");// 发送标志设为B 视为更改
			jsonObj.put("qrr", "");
			jsonObj.put("qrbz", "");
			jsonObj.put("xgbm", "");
			jsonObj.put("tzjl", shipTrend.getShipToCable());
			jsonObj.put("gsbgyy", shipTrend.getTrendsChangeTxt());
			jsonObj.put("wxp", shipTrend.getiDanCargo());
			jsonObj.put("token", token);
		}

		String url = apiServiceIp + "8091/ScheduleSysWebApi/setShipTrendsChange";
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
					throw new HdRunTimeException("上报失败！");
				}
				// JSONObject j = JSON.parseObject(response);
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

	// 撤销申请
	public void dtcancle(String shipTrendsId) {
		ShipTrend shipTrend = JpaUtils.findById(ShipTrend.class, shipTrendsId);
		JSONObject jsonObj = new JSONObject();
		String jpql = "select a from AuthUser a where a.account =:account";
		QueryParamLs paramLs = new QueryParamLs();
		paramLs.addParam("account", shipTrend.getRecNam());
		List<AuthUser> authUserList = JpaUtils.findAll(jpql, paramLs);
		for (AuthUser authUser : authUserList) {
			if ("admin".equals(authUser.getAccount())) {
				jsonObj.put("czr", "管理员");
			} else {
				jsonObj.put("czr", authUser.getName());
			}
		}
		if (HdUtils.strNotNull(shipTrend.getShipNo())) {
			Ship ship = JpaUtils.findById(Ship.class, shipTrend.getShipNo());
			jsonObj.put("wzh", ship.getBegCableNo());
			jsonObj.put("wzfx", ship.getEndCableNo());
		}
		if (shipTrend != null) {
			jsonObj.put("cbxh", shipTrend.getGroupShipNo());
			jsonObj.put("dtbh", shipTrend.getShipTrendsCod());
			jsonObj.put("qssj", shipTrend.getTrendsBegTim());
			jsonObj.put("gsdm", "03406500");
			jsonObj.put("qsdd", shipTrend.getTrendsBegArea());
			jsonObj.put("cbdxdm", shipTrend.getTrendsTermini());
			jsonObj.put("zzdd", shipTrend.getTrendsEndArea());
			jsonObj.put("cs", shipTrend.getShipDraft());
			jsonObj.put("tl", shipTrend.getUseTag());
			jsonObj.put("yhbzdm", shipTrend.getUsePilot());
			jsonObj.put("bz", shipTrend.getRemarks());
			jsonObj.put("czdw", "03406500");
			jsonObj.put("czsj", shipTrend.getRecTim());
			jsonObj.put("fsbz", "B");// 发送标志设为B 视为更改
			jsonObj.put("qrr", "");
			jsonObj.put("qrbz", "");
			jsonObj.put("xgbm", "");
			jsonObj.put("tzjl", shipTrend.getShipToCable());
			jsonObj.put("gsbgyy", shipTrend.getTrendsChangeTxt());
			jsonObj.put("wxp", shipTrend.getiDanCargo());
			jsonObj.put("token", token);
		}

		String url = apiServiceIp + "8091/ScheduleSysWebApi/cancelShipTrendsApply";
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
				// String str = '{' + "\"code\"" + ":0" + '}';
				// String str2 = '{' + "\"code\"" + ":1" + '}';
				// if (str.equals(response)) {
				// throw new HdRunTimeException("上报失败！");
				// }
				// if (str2.equals(response)) {
				//
				// }
				shipTrend.setSendFlag("");
				JpaUtils.update(shipTrend);
				// JSONObject j = JSON.parseObject(response);
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
