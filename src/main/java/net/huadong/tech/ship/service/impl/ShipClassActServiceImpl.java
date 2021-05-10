package net.huadong.tech.ship.service.impl;
import com.alibaba.fastjson.JSONObject;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.bean.HdRunTimeException;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.tech.privilege.entity.AuthUser;
import net.huadong.tech.ship.entity.Ship;
import net.huadong.tech.Interface.entity.ShipClassAct;
import net.huadong.tech.ship.service.ShipClassActService;
import net.huadong.tech.shipbill.entity.ShipBill;
import net.huadong.idev.hdmessagecode.HdMessageCode;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
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
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;
import net.huadong.tech.util.HdUtils;
/**
 * @author
 */
@Component
public class ShipClassActServiceImpl implements ShipClassActService {
	
	@Value("${tjgjt.service.ip}")
	private String tjgjtServiceIp;
	
	@Override
	public HdEzuiDatagridData find(HdQuery hdQuery) {
		
		String jpql = "select a from ShipClassAct a where a.shipNo=:shipNo ";
		QueryParamLs paramLs = new QueryParamLs();
		String workDate = hdQuery.getStr("workDte");
		String shiftCode = hdQuery.getStr("shiftCode");
		String unloadFlag = hdQuery.getStr("unloadFlag");
		paramLs.addParam("shipNo",  hdQuery.getStr("shipNo"));
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
		
		String sql="select nvl(sum(FACT_WGT1),0) WEIGHTS, nvl(sum(FACT_CAR_NUM),0) CAR_NUM  from SHIP_CLASS_ACT  where ship_no='"+hdQuery.getStr("shipNo")+"' and UNLOAD_FLAG='"+unloadFlag+"'";
		
		String hsql="select  nvl(count(t.PORT_CAR_NO),0) ch_car_num, nvl(sum(nvl(t1.WEIGHTS,0)),0) ch_car_wgt from work_command t,c_car_typ t1 where " + 
				"T.CAR_TYP=T1.CAR_TYP(+)  and  t.SHIP_NO='"+hdQuery.getStr("shipNo")+"'";
		
		if (HdUtils.strNotNull(unloadFlag)){
			 //作业方式(SI-卸船SO-装船TI-集港 TO-疏港 MV-捣场TZ-转栈)	
			 if(unloadFlag.equals("1")) {
				 hsql+="  and t.WORK_TYP ='SI' ";
			 }else {
				 hsql+=" and t.WORK_TYP ='SO' ";
			 }
			 
		}
		
		//
		String dshipno;
		String dworkdte;
//		String dayplansql="select class20_load_work_num as bx,  class20_unload_work_num as bz, class20_tons as bt, " +
//		                  "       class08_load_work_num as yx,  class08_unload_work_num as yz, class08_tons as yt " +
//		                  " from day_night_plan " +
//		                  "where ship_no='" + hdQuery.getStr("shipNo")+"'"+		                 
//		                  "  and days = to_date('" + hdQuery.getStr("workDte") +"', 'yyyy-mm-dd')" ;  hdQuery.getStr("shiftCode") hdQuery.getStr("unloadFlag")
		//  hiftCode c_work_run :   1= 白班 ： 2=夜班 ; unloadFlag  sys_cod.LOAD_TYP: 1= 卸 2= 装
		String dayplansql="select decode('" + hdQuery.getStr("shiftCode") + "', '1', decode('"+hdQuery.getStr("unloadFlag")+"', '1', nvl(class20_load_work_num,0), nvl(class20_unload_work_num,0) ), " +
                          "                                                     '2', decode('"+hdQuery.getStr("unloadFlag")+"', '1', nvl(class08_load_work_num,0), nvl(class08_unload_work_num,0) ) ) as plan_car_Nums, " +
                          "       decode('" + hdQuery.getStr("shiftCode") + "', '1', nvl(class20_tons, 0), nvl(class08_tons, 0)) as plan_car_Weigts " +
                          " from day_night_plan " +
                          "where ship_no='" + hdQuery.getStr("shipNo")+"'";	
        if (HdUtils.strNotNull(workDate)) {
        	dayplansql += "  and    days= to_date('" + hdQuery.getStr("workDte") +"', 'yyyy-mm-dd')" ;
        }
                          	
		
		//
		List<Map> lstTole=JpaUtils.getEntityManager().createNativeQuery(sql).setHint(QueryHints.RESULT_TYPE, ResultType.Map).getResultList();
		List<Map> lstChTole=JpaUtils.getEntityManager().createNativeQuery(hsql).setHint(QueryHints.RESULT_TYPE, ResultType.Map).getResultList();
		List<Map> dpsTole=JpaUtils.getEntityManager().createNativeQuery(dayplansql).setHint(QueryHints.RESULT_TYPE, ResultType.Map).getResultList();
		
		List rtFooterLst=new ArrayList<>();
		if(lstTole.size()>0) {
			Map ctMap=new HashMap();
			ctMap.putAll(lstTole.get(0));
			if(lstChTole.size()>0) {
				ctMap.putAll(lstChTole.get(0));
			}
			if(dpsTole.size()>0) {
				ctMap.putAll(dpsTole.get(0));
			}			
			rtFooterLst.add(ctMap);
		}
		
		result.setFooter(rtFooterLst);
	

		return result;
	}
	@Override
	public HdMessageCode save(HdEzuiSaveDatagridData<ShipClassAct> hdEzuiSaveDatagridData) {
		List<ShipClassAct> insertList = hdEzuiSaveDatagridData.getInsertedRows();
		for(ShipClassAct bean : insertList){
			bean.setScactId(HdUtils.genUuid());
			String jpql = "select a from ShipClassAct a where a.shipNo=:shipNo and a.workDate =:workDate and a.shiftCode=:shiftCode";
			QueryParamLs paramLs = new QueryParamLs();
			paramLs.addParam("shipNo", bean.getShipNo());
			paramLs.addParam("workDate", bean.getWorkDate());
			paramLs.addParam("shiftCode", bean.getShiftCode());
			List<ShipClassAct> list = JpaUtils.findAll(jpql, paramLs);
			if (list.size() > 0){
				throw new HdRunTimeException("该船此班次已存在数据！");
			}
			
		}
		return JpaUtils.save(hdEzuiSaveDatagridData);
	}
	@Transactional
	public void removeAll(String scactIds) {
		List<String> scactIdList = HdUtils.paraseStrs(scactIds);
		for (String scactId : scactIdList) {
			JpaUtils.remove(ShipClassAct.class, scactId);
		}
	}
	
	@Override
	@Transactional
	public void uploadAll(String scactIds) {
		List<String> scactIdList = HdUtils.paraseStrs(scactIds);
		for (String scactId: scactIdList) {
			upload(scactId);
		}
	}
	
	public void upload(String scactId) {
		ShipClassAct bean = JpaUtils.findById(ShipClassAct.class, scactId);
		JSONObject jsonObj = new JSONObject();
		Map<String,String> map=new HashMap<String, String>();
		Ship ship = JpaUtils.findById(Ship.class, bean.getShipNo());
		jsonObj.put("cmdId", "2007");
		jsonObj.put("coId",ship.GZ);	
		map.put("teamOrgnId", ship.GZ);
		map.put("workDate",bean.getWorkDate().toString().substring(0,10));
		map.put("shiftCode", bean.getShiftCode());
		map.put("shipName", ship.getcShipNam());
		map.put("shipId", bean.getShipId());
		map.put("svoyageId", ship.getNewGroupShipNo());
		map.put("scactId", bean.getScactId());
		if ("1".equals(bean.getUnloadFlag())){
			map.put("unloadFlag", "-");
		}else if ("2".equals(bean.getUnloadFlag())){
			map.put("unloadFlag", "+");
		}
		map.put("factCarNum", bean.getFactCarNum().toString());
		map.put("factStdCarNum", bean.getFactStdCarNum().toString());
		String jpql1 = "select a from AuthUser a where a.account =:account";
		QueryParamLs paramLs1 = new QueryParamLs();
		paramLs1.addParam("account", bean.getRecNam());
		List<AuthUser> authUserList = JpaUtils.findAll(jpql1, paramLs1);
		for (AuthUser authUser : authUserList) {
				map.put("recNam", authUser.getName());
		}
		QueryParamLs paramLs2 = new QueryParamLs();
		paramLs2.addParam("account", bean.getUpdNam());
		List<AuthUser> authUser1List = JpaUtils.findAll(jpql1, paramLs2);
		for (AuthUser authUser : authUser1List) {
				map.put("updNam", authUser.getName());
		}
		map.put("recTim", bean.getRecTim().toString().substring(0,16));
		map.put("updTim", bean.getUpdTim().toString().substring(0,16));
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
	public ShipClassAct findone(String scactId) {
		ShipClassAct bean = JpaUtils.findById(ShipClassAct.class, scactId);
		return bean;
	}
	@Override
	@Transactional
	public HdMessageCode saveone(@RequestBody ShipClassAct bean) {
		if (HdUtils.strNotNull(bean.getScactId())) {
			JpaUtils.update(bean);
		} else {
			bean.setScactId(HdUtils.genUuid());
			JpaUtils.save(bean);
		}
		return HdUtils.genMsg();
	}
}
