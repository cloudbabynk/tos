package net.huadong.tech.ship.service.impl;


import net.huadong.tech.base.bean.EzTreeBean;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.bean.HdRunTimeException;
import net.huadong.tech.base.entity.CBerth;
import net.huadong.tech.base.entity.CBrand;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.tech.inter.entity.RespInter;
import net.huadong.tech.ship.entity.CShipData;
import net.huadong.tech.ship.entity.Ship;
import net.huadong.tech.ship.entity.ShipThruputRecord;
import net.huadong.tech.ship.service.ShipThruputRecordService;
import net.huadong.idev.hdmessagecode.HdMessageCode;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.eclipse.persistence.config.QueryHints;
import org.eclipse.persistence.config.ResultType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;
import net.huadong.tech.task.ShipTask;
import net.huadong.tech.util.HdUtils;
import net.sf.json.JSONObject;

/**
 * @author
 */
@Component
public class ShipThruputRecordServiceImpl implements ShipThruputRecordService {
	private static final String JK = "I";
	private static final String CK = "E";
	@Value("${tjgjt.service.ip}")
	private String tjgjtServiceIp;

	@Override
	public HdEzuiDatagridData find(HdQuery hdQuery) {
		String jpql = "select a from ShipThruputRecord a where 1=1 ";
		String shipNo = hdQuery.getStr("shipNo");
		String tradeId = hdQuery.getStr("tradeId");
		String iEId = hdQuery.getStr("iEId");
		String statDateBeg = hdQuery.getStr("statDateBeg");
		String statDateEnd = hdQuery.getStr("statDateEnd");
		String checkFlag = hdQuery.getStr("checkFlag");
		String submitFlag = hdQuery.getStr("submitFlag");
		QueryParamLs paramLs = new QueryParamLs();
		if (HdUtils.strNotNull(shipNo)) {
			jpql += "and a.shipNo =:shipNo ";
			paramLs.addParam("shipNo", shipNo);
		}
		if (HdUtils.strNotNull(tradeId)) {
			jpql += "and a.tradeId =:tradeId ";
			paramLs.addParam("tradeId", tradeId);
		}
		if (HdUtils.strNotNull(iEId)) {
			jpql += "and a.iEId =:iEId ";
			paramLs.addParam("iEId", iEId);
		}
		if (HdUtils.strNotNull(checkFlag)) {
			jpql += "and a.checkFlag =:checkFlag ";
			paramLs.addParam("checkFlag", checkFlag);
		}
		if (HdUtils.strNotNull(submitFlag)) {
			jpql += "and a.submitFlag =:submitFlag ";
			paramLs.addParam("submitFlag", submitFlag);
		}
		if (HdUtils.strNotNull(statDateBeg)) {
			jpql += "and a.statDate >=:statDateBeg ";
			paramLs.addParam("statDateBeg", HdUtils.strToDate(statDateBeg));
		}
		if (HdUtils.strNotNull(statDateEnd)) {
			jpql += "and a.statDate <=:statDateEnd ";
			paramLs.addParam("statDateEnd", HdUtils.strToDate(statDateEnd));
		}
		jpql += "order by a.recTim desc";
        HdEzuiDatagridData result = new HdEzuiDatagridData();		
		List<ShipThruputRecord> shipThruputRecordL = JpaUtils.findAll(jpql, paramLs);
		if (shipThruputRecordL.size() > 0) {
			for (ShipThruputRecord s : shipThruputRecordL) {
				if (HdUtils.strNotNull(s.getShipNo())) {
					Ship ship = JpaUtils.findById(Ship.class, s.getShipNo());
					if (HdUtils.strNotNull(ship.getTradeId()))
					s.setTradeIdStr(HdUtils.getSysCodeName("TRADE_ID", ship.getTradeId()));
					s.setConArrvTim(ship.getConArrvTim());
					s.setiEIdStr(HdUtils.getSysCodeName("I_E_ID", s.getiEId()));
				}
			}

		}
		result.setRows(shipThruputRecordL);
		return result;
	}

	@Override
	public HdMessageCode save(HdEzuiSaveDatagridData<ShipThruputRecord> hdEzuiSaveDatagridData) {
		// TODO Auto-generated method stub
		return JpaUtils.save(hdEzuiSaveDatagridData);
	}

	@Transactional
	public void removeAll(String sthruputIds) {
		List<String> sthruputIdsList = HdUtils.paraseStrs(sthruputIds);
		for (String sthruputId : sthruputIdsList) {
			JpaUtils.remove(ShipThruputRecord.class, sthruputId);
		}
	}

	@Transactional
	public void checkAll(String brandCods) {
		List<String> brandCodList = HdUtils.paraseStrs(brandCods);
		for (String brandCod : brandCodList) {
			CBrand cBrand = JpaUtils.findById(CBrand.class, brandCod);
			if (CBrand.N.equals(cBrand.getCheckFlag())) {
				cBrand.setCheckFlag(CBrand.Y);
			}
			JpaUtils.update(cBrand);
		}
	}

	@Override
	public ShipThruputRecord findone(String sthruputId) {
		ShipThruputRecord shipThruputRecord = JpaUtils.findById(ShipThruputRecord.class, sthruputId);
		return shipThruputRecord;

	}

	@Override
	public HdMessageCode saveone(@RequestBody ShipThruputRecord shipThruputRecord) {
		if (HdUtils.strNotNull(shipThruputRecord.getSthruputId())) {
			JpaUtils.update(shipThruputRecord);
		} else {
			shipThruputRecord.setSthruputId(HdUtils.generateUUID());
			Ship ship = JpaUtils.findById(Ship.class, shipThruputRecord.getShipNo());
			shipThruputRecord.setShipName(ship.getcShipNam());
			shipThruputRecord.setTradeId(ship.getTradeId());
			shipThruputRecord.setThruputType("0");
			if(shipThruputRecord.getSubmitFlag().equals("1")) {
				shipThruputRecord.setSubmitTime(new Timestamp(new Date().getTime()));
				shipThruputRecord.setSubmitName(HdUtils.getCurUser().getAccount());
			}
			JpaUtils.save(shipThruputRecord);
		}
		return HdUtils.genMsg();
	}

	@Override
	public HdMessageCode findShipThruputRecord(String sthruputId) {
		if (HdUtils.strNotNull(sthruputId)) {
			ShipThruputRecord shipThruputRecord = JpaUtils.findById(ShipThruputRecord.class, sthruputId);
			if (shipThruputRecord != null) {
				throw new HdRunTimeException("该代码已存在，请重新输入！");// 主键已存在
			}
		}
		return HdUtils.genMsg();
	}

	// 树生成
	public List<EzTreeBean> findTree() {
		ShipTask task=new ShipTask();
		List<EzTreeBean> lst=task.getTree();
		return lst;

	}

	@Override
	public HdEzuiDatagridData findQ(HdQuery hdQuery) {
		String jpql = "select a from ShipThruputRecord a where 1=1 ";
		// String shipNo = hdQuery.getStr("shipNo");
		String tradeId = hdQuery.getStr("tradeId");
		String iEId = hdQuery.getStr("iEId");
		String statDate = hdQuery.getStr("statDate");
		String checkFlag = hdQuery.getStr("checkFlag");
		String submitFlag = hdQuery.getStr("submitFlag");
		QueryParamLs paramLs = new QueryParamLs();

		if (HdUtils.strNotNull(tradeId)) {
			jpql += "and a.tradeId =:tradeId ";
			paramLs.addParam("tradeId", tradeId);
		}
		if (HdUtils.strNotNull(iEId)) {
			jpql += "and a.iEId =:iEId ";
			paramLs.addParam("iEId", iEId);
		}
		if (HdUtils.strNotNull(checkFlag)) {
			jpql += "and a.checkFlag =:checkFlag ";
			paramLs.addParam("checkFlag", checkFlag);
		}
		if (HdUtils.strNotNull(submitFlag)) {
			jpql += "and a.submitFlag =:submitFlag ";
			paramLs.addParam("submitFlag", submitFlag);
		}
		if (HdUtils.strNotNull(statDate)) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = null;
			try {
				date = (Date) sdf.parse(statDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			jpql += "and a.statDate =:date ";
			paramLs.addParam("date", date);
		}
		jpql += "order by a.recTim desc";
		HdEzuiDatagridData result = JpaUtils.findByEz(jpql, paramLs, hdQuery);
		List<ShipThruputRecord> shipThruputRecordL = result.getRows();
		if (shipThruputRecordL.size() > 0) {
			for (ShipThruputRecord s : shipThruputRecordL) {
				if (HdUtils.strNotNull(s.getShipNo())) {
					Ship ship = JpaUtils.findById(Ship.class, s.getShipNo());
					s.setTradeIdStr(HdUtils.getSysCodeName("TRADE_ID", ship.getTradeId()));
					s.setConArrvTim(ship.getConArrvTim());
					s.setiEIdStr(HdUtils.getSysCodeName("I_E_ID", s.getiEId()));
				}
			}

		}
		return result;
	}

	@Override
	public String sendjt(String sthruputIds) {
		List<String> sthruputIdsList = HdUtils.paraseStrs(sthruputIds);
		String message = "";
		for (String sthruputId : sthruputIdsList) {
			ShipThruputRecord shipThruputRecord = JpaUtils.findById(ShipThruputRecord.class, sthruputId);
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("cmdId", "2001");
			jsonObj.put("coId", Ship.GZ);
			Map<String, String> map = new HashMap<String, String>();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm");
			map.put("sthruputId", shipThruputRecord.getSthruputId());
			map.put("teamOrgnId", shipThruputRecord.getDockCod());
			SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
			map.put("statDate", format2.format(shipThruputRecord.getStatDate()));
			Ship ship = JpaUtils.findById(Ship.class, shipThruputRecord.getShipNo());
			map.put("svoyageId", ship.getNewGroupShipNo());
			// String jpqla="select a from VGroupCorpShip a where 1=1 and
			// a.svoyageId=:svoyageId ";
			QueryParamLs paramLs = new QueryParamLs();
			if (shipThruputRecord.getiEId().equals("I")) {
				map.put("shipId", ship.getNewIShipId());
				map.put("shipName", ship.getcShipNam());
				if (ship.getDiscEndTim() != null) {
					map.put("endTime", ship.getDiscEndTim().toString());
				} else {
					map.put("endTime", "");
				}
				map.put("ieFlag", "I");
			}
			if (shipThruputRecord.getiEId().equals("E")) {
				map.put("shipId", ship.getNewEShipId());
				map.put("shipName", ship.getcShipNam());
				map.put("ieFlag", "E");
			}
			if ("1".equals(ship.getTradeId())) {
				map.put("tradeFlag", "2");
			}
			if ("2".equals(ship.getTradeId())) {
				map.put("tradeFlag", "1");
			}
			if (HdUtils.strNotNull(ship.getBerthCod())) {
				CBerth cb = JpaUtils.findById(CBerth.class, ship.getBerthCod());
				map.put("berthCode", cb.getGroupBerthCod());
			}
			map.put("portCode", "");
			map.put("shipLineCode", "");
			map.put("cargoAgentCod", "");
			map.put("shipperCod", "");
			map.put("consignCod", "");
			// 汽车
			map.put("cargoCode", "9910");
			map.put("cnorCode", "");
			map.put("cneeCode", "");
			map.put("originPlaceCod", "");
			map.put("brandCode", "");
			if (shipThruputRecord.getDgrCargoWgt() != null) {
				map.put("cargoWgt", shipThruputRecord.getDgrCargoWgt().toString());
			} else {
				map.put("cargoWgt", "");
			}
			map.put("cntrNum", "");
			map.put("teuNum", "");
			if (shipThruputRecord.getCarNum() != null) {
				map.put("carNum", shipThruputRecord.getCarNum().toString());
			} else {
				map.put("carNum", "");
			}
			if (shipThruputRecord.getStdcarNum() != null) {
				map.put("stdcarNum", shipThruputRecord.getStdcarNum().toString());
			} else {
				map.put("stdcarNum", "");
			}
			if (shipThruputRecord.getDgrCargoWgt() != null) {
				map.put("dgrType", "1");
				map.put("dgrKind", "9");
			} else {
				map.put("dgrType", "");
				map.put("dgrKind", "");
			}
			if (shipThruputRecord.getDgrCargoWgt() != null) {
				map.put("dgrCargoWgt", shipThruputRecord.getDgrCargoWgt().toString());
			}
			map.put("submitFlag", "1");
			map.put("submitName", HdUtils.getCurUser().getAccount());
			map.put("submitTime", format.format(HdUtils.getDateTime()));
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
					JSONObject jsonObject = JSONObject.fromObject(response);
					RespInter resp = (RespInter) JSONObject.toBean(jsonObject, RespInter.class);
					String resCode = "0000";
					String resMsg = "OK";
					if (resCode.equals(resp.getResCode()) && resMsg.equals(resp.getResMsg())) {
						shipThruputRecord.setSubmitFlag("1");
						JpaUtils.update(shipThruputRecord);
						message = "success";
						return message;
					}
					if (!resCode.equals(resp.getResCode()) || !resMsg.equals(resp.getResMsg())) {
						System.out.println("上报集团失败！");
						message = "error";
						break;
						// return message;
					}
				} catch (Exception e) {
					message = "error";
					break;

				}
				// 断开连接
				conn.disconnect();
			} catch (Exception e) {
				// return message;
				message = "error";
				break;
				// throw new HdRunTimeException("上报集团失败!");
			}

			finally {

			}
		}
		return message;
	}

	@Override
	public HdMessageCode getShipTueInfo(Map map) {
		HdMessageCode message=HdUtils.genMsg();
		String shipNo=map.get("shipNo")+"";
		Ship bean = JpaUtils.findById(Ship.class, shipNo);
		if (bean == null){
			throw new HdRunTimeException("该船已不存在！");
		}
		String ieId=map.get("ieId")+"";
		String sql=null;
		if(ieId.equals("E")) {
			if ("2800".equals(bean.getShipTyp())){
				sql ="select   t2.E_VISA_WEIGHT CAR_WGT from ship_thruput t2 where t2.ship_no ='"+shipNo+"'";
			} else {
				sql="select count(decode(T1.car_typ, '182', null,'8', null, 1)) CAR_NUM,   count(decode(T1.car_typ, '182',1, '8', 1, null)) SHEBI_NUM,   max( t2.E_VISA_WEIGHT) CAR_WGT " + 
						"  from work_command T1,   ship_thruput t2 " + 
						" where t1.ship_no = t2.ship_no " + 
						"  and T1.ship_no ='"+shipNo+"' and T1.work_typ = 'SO'  " + 
						"  group by t1.car_typ";
			}
			
		}else {
			if ("2800".equals(bean.getShipTyp())){
				sql ="select   t2.E_VISA_WEIGHT CAR_WGT from ship_thruput t2 where t2.ship_no ='"+shipNo+"'";
			} else {
				sql="select count(decode(T1.car_typ, '182', null, '8', null, 1)) car_num,   count(decode(T1.car_typ, '182',1, '8', 1, null)) shebi_num,   max( t2.I_VISA_WEIGHT) car_wgt " + 
						"  from work_command T1,   ship_thruput t2 " + 
						" where t1.ship_no = t2.ship_no " + 
						"  and T1.ship_no ='"+shipNo+"' and T1.work_typ = 'SI'  " + 
						"  group by t1.car_typ";
			}
		}
		List<Map> lstCargo=JpaUtils.getEntityManager().createNativeQuery(sql).setHint(QueryHints.RESULT_TYPE, ResultType.Map).getResultList();
		if(lstCargo.size()>0) {
			Map mapOne=lstCargo.get(0);
			Map mapData=new HashMap();
			mapData.put("carNum",mapOne.get("CAR_NUM")+"");
			mapData.put("cargoWgt",mapOne.get("CAR_WGT")+"");
			mapData.put("pieces",mapOne.get("SHEBI_NUM")+""  );
			mapData.put("dgrCargoWgt",mapOne.get("CAR_WGT")+"");
			message.setData(mapData);
		}
		return message;
	}

	@Override
	public HdMessageCode getUnitCargo(Map map) {
		HdMessageCode message=HdUtils.genMsg();
		String sql="  select CARGO_CODE,CARGO_NAME, TTL_TYP  from V_GROUP_CORP_CARGO " + 
		"   where ttl_typ in ('1', '2')     order by ttl_typ ";
		List<Map> lstCargo=JpaUtils.getEntityManager().createNativeQuery(sql).setHint(QueryHints.RESULT_TYPE, ResultType.Map).getResultList(); 
		message.setData(lstCargo);
		return message;
	}

}
