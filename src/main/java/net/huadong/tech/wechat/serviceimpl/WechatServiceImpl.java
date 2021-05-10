package net.huadong.tech.wechat.serviceimpl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher; 
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.apache.shiro.SecurityUtils;
import org.eclipse.persistence.config.QueryHints;
import org.eclipse.persistence.config.ResultType;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.entity.CCarTyp;
import net.huadong.tech.base.entity.CHoliday;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.PageInfo;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.tech.privilege.entity.AuthUser;
import net.huadong.tech.shipbill.entity.PortCar;
import net.huadong.tech.springboot.core.HdEzuiQueryParams;
import net.huadong.tech.thirdparty.service.Result;
import net.huadong.tech.util.HdUtils;
import net.huadong.tech.wechat.entity.Crfid;
import net.huadong.tech.wechat.service.WechatService;
import net.huadong.tech.work.entity.MPdaWorkCommand;
import net.sf.json.JSONObject;

/**
 * 
 * @Description: 微信服务
 * @author 
 * @date 2017年5月2日 上午10:15:56
 *
 */
@Component
public class WechatServiceImpl implements WechatService {
	
	@Override
	public Result isLogin(String account, String password, HttpServletRequest request) {
		String sql = "select * from AUTH_USER   where lower(account) =lower('"+account+"') and lower(password)=lower('"+password+"')";
		List<Map> mList = JpaUtils.getEntityManager().createNativeQuery(sql).setHint(QueryHints.RESULT_TYPE, ResultType.Map).getResultList();
		Result result = new Result();
		if (mList != null && mList.size() > 0) {
			
			// 节假日标识
			String holiday = "0";
			Calendar c = Calendar.getInstance();
			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH) + 1;
			int date = c.get(Calendar.DATE);
			String jpql = "SELECT c FROM CHoliday c where c.holidayYear=:holidayYear and c.holidayMonth=:holidayMonth and c.holidayDay=:holidayDay";
			QueryParamLs params = new QueryParamLs();
			params.addParam("holidayYear", String.valueOf(year));
			params.addParam("holidayMonth", String.valueOf(month));
			params.addParam("holidayDay", String.valueOf(date));
			List<CHoliday> hlist = JpaUtils.findAll(jpql, params);
			if (hlist.size() > 0) {
				if (hlist.get(0).getHolidayId().equals("1")) {
					holiday = "1";
				}
			}
			
			
			Map user = mList.get(0);
			/*
			 * HttpSession session = request.getSession(); session.setAttribute("user",
			 * user);
			 */
			Map data = new HashMap<>();
			data.put("id", user.get("USER_ID"));
			data.put("name", user.get("NAME"));

			// 节假日标识
			data.put("holiday", holiday);

			result.setStatus(1);
			result.setInfo("用户名和密码正确，登录成功！");
			result.setData(data);
			// 构建session-user信息
			AuthUser authUser = new AuthUser();
			authUser.setUserId(user.get("USER_ID").toString());
			authUser.setAccount(account);
			authUser.setName(user.get("NAME").toString());
			SecurityUtils.getSubject().getSession().setAttribute("user", authUser);
		} else {
			result.setStatus(-1);
			result.setInfo("用户名或密码错误！");
		}
		return result;

	}
	
	public Result chooseShip(HdEzuiQueryParams hdEzuiQueryParams) {
		String shipCod = (String) hdEzuiQueryParams.getOthers().get("shipCod");
		QueryParamLs paramLs = new QueryParamLs();
		String jpql = "select a.shipCod,a.ivoyage from Ship a where 1=1";
		if (HdUtils.strNotNull(shipCod)) {
			jpql += " and a.shipCod like :shipCod";
			paramLs.addParam("shipCod", "%" + shipCod + "%");
		}
		PageInfo pageInfo = new PageInfo();
		pageInfo.setCurPageNum(1);
		pageInfo.setRowOfPage(10);
		List mList = JpaUtils.findAll(jpql, paramLs, pageInfo);
		Result result = new Result();
		result.setData(mList);
		return result;
	}

	public Result chooseShipwmzc(HdEzuiQueryParams hdEzuiQueryParams) {
		QueryParamLs paramLs = new QueryParamLs();
		// 外贸装船队列
		String jpql2 = "select a.cShipNam,a.ivoyage voyage, b.workTyp, a.shipNo, b.workQueueNo from Ship a,WorkQueue b "
				+ "where 1=1 and a.shipNo=b.shipNo and  b.workTyp='SO' and a.tradeId='2' ";
		PageInfo pageInfo = new PageInfo();
		pageInfo.setCurPageNum(1);
		pageInfo.setRowOfPage(10);
		List mList2 = JpaUtils.findAll(jpql2, paramLs, pageInfo);
		Result result = new Result();
		result.setData(mList2);
		return result;
	}

	public Result chooseShipzx(HdEzuiQueryParams hdEzuiQueryParams) {
		QueryParamLs paramLs = new QueryParamLs();
		// 先查装船，再查卸船的
		String jpql = "select a.cShipNam,a.evoyage voyage, b.workTyp, a.shipNo, b.workQueueNo from Ship a,WorkQueue b "
				+ "where 1=1 and a.shipNo=b.shipNo and b.workTyp='SO' ";
		String jpql2 = "select a.cShipNam,a.ivoyage voyage, b.workTyp, a.shipNo, b.workQueueNo from Ship a,WorkQueue b "
				+ "where 1=1 and a.shipNo=b.shipNo and  b.workTyp='SI' ";
		PageInfo pageInfo = new PageInfo();
		pageInfo.setCurPageNum(1);
		pageInfo.setRowOfPage(10);
		List mList = JpaUtils.findAll(jpql, paramLs, pageInfo);
		List mList2 = JpaUtils.findAll(jpql2, paramLs, pageInfo);
		mList.addAll(mList2);
		Result result = new Result();
		result.setData(mList);
		return result;
	}

	@Override
	public Result findSparseset(HdEzuiQueryParams hdEzuiQueryParams) {
		QueryParamLs paramLs = new QueryParamLs();
		String jpql = "select a.truckNo, b.carryId, b.contractNo," + "b.truckNo, a.singleId, a.ingateId "
				+ " from GateTruck a, GateTruckContract b " + " where 1=1 and a.ingateId = b.ingateId";
		PageInfo pageInfo = new PageInfo();
		pageInfo.setCurPageNum(1);
		pageInfo.setRowOfPage(10);
		List mList = JpaUtils.findAll(jpql, paramLs);
		Result result = new Result();
		result.setData(mList);
		return result;
	}
	
	@Override
	public Result findStacks(HdEzuiQueryParams hdEzuiQueryParams) {
		String jpql = "select a.contractNo, a.billNo, a.shipNo from ContractIeDoc a where a.contractTyp = '2' and a.workWay = '3'";
		QueryParamLs paramLs = new QueryParamLs();
		PageInfo pageInfo = new PageInfo();
		pageInfo.setCurPageNum(1);
		pageInfo.setRowOfPage(10);
		List mList = JpaUtils.findAll(jpql, paramLs, pageInfo);
		Result result = new Result();
		result.setData(mList);
		return result;
	}

	@Override
	public Result chooseDamCod(HdEzuiQueryParams hdEzuiQueryParams) {
		// TODO Auto-generated method stub
		QueryParamLs paramLs = new QueryParamLs();
		String jpql = "select a from CDamage a where 1=1";
		PageInfo pageInfo = new PageInfo();
		pageInfo.setCurPageNum(1);
		pageInfo.setRowOfPage(20);
		List mList = JpaUtils.findAll(jpql, paramLs, pageInfo);
		Result result = new Result();
		result.setData(mList);
		return result;
	}

	@Override
	public Result chooseDamArea(HdEzuiQueryParams hdEzuiQueryParams) {
		QueryParamLs paramLs = new QueryParamLs();
		String jpql = "select a from CDamgArea a where 1=1";
		PageInfo pageInfo = new PageInfo();
		pageInfo.setCurPageNum(1);
		pageInfo.setRowOfPage(20);
		List mList = JpaUtils.findAll(jpql, paramLs, pageInfo);
		Result result = new Result();
		result.setData(mList);
		return result;
	}

	@Override
	public Result chooseDamLevel(HdEzuiQueryParams hdEzuiQueryParams) {
		// TODO Auto-generated method stub
		QueryParamLs paramLs = new QueryParamLs();
		String jpql = "select a from CDamgLevel a where 1=1";
		PageInfo pageInfo = new PageInfo();
		pageInfo.setCurPageNum(1);
		pageInfo.setRowOfPage(20);
		List mList = JpaUtils.findAll(jpql, paramLs, pageInfo);
		Result result = new Result();
		result.setData(mList);
		return result;
	}

	@Override
	public Result chooseInCharge(HdEzuiQueryParams hdEzuiQueryParams) {
		QueryParamLs paramLs = new QueryParamLs();
		String jpql = "select a from SCode a where a.fldEng ='INCHARGE' ";
		PageInfo pageInfo = new PageInfo();
		pageInfo.setCurPageNum(1);
		pageInfo.setRowOfPage(20);
		List mList = JpaUtils.findAll(jpql, paramLs, pageInfo);
		Result result = new Result();
		result.setData(mList);
		return result;
	}

	@Override
	public Result findtradeId(HdEzuiQueryParams hdEzuiQueryParams) {
		QueryParamLs paramLs = new QueryParamLs();
		String jpql = "select a from SCode a where a.fldEng ='TRADE_ID' ";
		PageInfo pageInfo = new PageInfo();
		pageInfo.setCurPageNum(1);
		pageInfo.setRowOfPage(20);
		List mList = JpaUtils.findAll(jpql, paramLs, pageInfo);
		Result result = new Result();
		result.setData(mList);
		return result;
	}

	@Override
	public Result findiEId(HdEzuiQueryParams hdEzuiQueryParams) {
		QueryParamLs paramLs = new QueryParamLs();
		String jpql = "select a from SCode a where a.fldEng ='I_E_ID' ";
		PageInfo pageInfo = new PageInfo();
		pageInfo.setCurPageNum(1);
		pageInfo.setRowOfPage(20);
		List mList = JpaUtils.findAll(jpql, paramLs, pageInfo);
		Result result = new Result();
		result.setData(mList);
		return result;
	}

	@Override
	public Result findcarTyp(HdQuery hdQuery) {
		String jpql = "SELECT c FROM CCarTyp c  ";
		QueryParamLs carTypParam = new QueryParamLs();
		if(hdQuery.getOthers() == null ||hdQuery.getOthers().isEmpty()) {
			
		} else {
			String brandCod = (String) hdQuery.getOthers().get("brandCod");
			if(HdUtils.strNotNull(brandCod)) {
				jpql += " where c.brandCod =:brandCod";
				carTypParam.addParam("brandCod", brandCod);
			}
		}
		PageInfo pageInfo = new PageInfo();
		pageInfo.setCurPageNum(1);
		pageInfo.setRowOfPage(200);
		List mList = JpaUtils.findAll(jpql, carTypParam);
		Result result = new Result();
		result.setData(mList);
		return result;
	}

	@Override
	public Result findbrandCod(HdQuery hdQuery) {
		QueryParamLs paramLs = new QueryParamLs();
		String jpql = "SELECT c FROM CBrand c where 1=1";
		if(hdQuery.getOthers() == null || hdQuery.getOthers().isEmpty()) {
			jpql += "  order by c.brandShort asc";
		} else {
			String isFT = (String) hdQuery.getOthers().get("isFT");
			if(isFT.equals("true")) {
//				jpql += " and c.brandNam = '123' or c.brandCod = '0713113307' order by c.brandCod desc";
				jpql += " and c.brandNam = '广汽丰田' or c.brandNam = '一汽丰田'  order by c.brandShort asc";
			}
			else{
				jpql += "  order by c.brandShort asc";
			}
		}
		PageInfo pageInfo = new PageInfo();
		List mList = JpaUtils.findAll(jpql, paramLs);
		Result result = new Result();
		result.setData(mList);
		return result;
	}

	@Override
	public Result findcarKind(HdQuery hdQuery) {
		String carTypJpql = "SELECT c FROM CCarTyp c";
		QueryParamLs carTypParams = new QueryParamLs();
		if(hdQuery.getOthers() == null || hdQuery.getOthers().isEmpty()) {
			
		} else {
			String carTyp = (String) hdQuery.getOthers().get("carTyp");
			if(HdUtils.strNotNull(carTyp)) {
				carTypJpql += "  where c.carTyp =:carTyp";
				carTypParams.addParam("carTyp", carTyp);
			}
		}
		List<CCarTyp> carTypList = JpaUtils.findAll(carTypJpql, carTypParams);
		
		QueryParamLs paramLs = new QueryParamLs();
		String jpql = "SELECT c FROM CCarKind  c where 1=1";
		if(carTypList.size() == 1) {
			String carKindID = carTypList.get(0).getCarKind();
			if(HdUtils.strNotNull(carKindID)) {
				jpql += "  and c.carKind =:carKind";
				paramLs.addParam("carKind", carKindID);
			}
		}
		PageInfo pageInfo = new PageInfo();
		pageInfo.setCurPageNum(1);
		pageInfo.setRowOfPage(20);
		List mList = JpaUtils.findAll(jpql, paramLs);
		Result result = new Result();
		result.setData(mList);
		return result;
	}

	@Override
	public Result findport(HdEzuiQueryParams hdEzuiQueryParams) {
		QueryParamLs paramLs = new QueryParamLs();
		String jpql = "SELECT c FROM CPort c where 1=1";
		PageInfo pageInfo = new PageInfo();
		pageInfo.setCurPageNum(1);
		pageInfo.setRowOfPage(20);
		List mList = JpaUtils.findAll(jpql, paramLs);
		Result result = new Result();
		result.setData(mList);
		return result;
	}

	@Override
	public Result findworkRunNam(HdEzuiQueryParams hdEzuiQueryParams) {
		QueryParamLs paramLs = new QueryParamLs();
		String jpql = "SELECT c FROM CWorkRun c where 1=1";
		PageInfo pageInfo = new PageInfo();
		pageInfo.setCurPageNum(1);
		pageInfo.setRowOfPage(20);
		List mList = JpaUtils.findAll(jpql, paramLs, pageInfo);
		Result result = new Result();
		result.setData(mList);
		return result;
	}

	@Override
	public Result chooseShipBill(String shipNo) {
		String jpql = "select a from ShipBill a where a.iEId = 'I' and a.shipNo in :shipNo";
		QueryParamLs paramLs = new QueryParamLs();
		paramLs.addParam("shipNo", Arrays.asList(shipNo.split(",")));
		List mList = JpaUtils.findAll(jpql, paramLs);
		Result result = new Result();
		result.setData(mList);
		return result;
	}
	
	@Override
	public Result chooseLoadShipBill(String shipNo) {
		String jpql = "select a from ShipLoadBill a where  a.shipNo in :shipNo";
		QueryParamLs paramLs = new QueryParamLs();
		paramLs.addParam("shipNo", Arrays.asList(shipNo.split(",")));
		List mList = JpaUtils.findAll(jpql, paramLs);
		Result result = new Result();
		result.setData(mList);
		return result;
	}

	@Override
	public Result choosecyAreaNo(HdEzuiQueryParams hdEzuiQueryParams) {
		QueryParamLs paramLs = new QueryParamLs();
		// String jpql = "SELECT distinct(c.cyAreaNo) as cyAreaNo, c.cyAreaNam
		// FROM CCyArea c, CCyBay b where 1=1 and c.cyAreaNo = b.cyAreaNo and
		// b.lockId='0'";
		String jpql = "SELECT c.cyAreaNo, c.cyAreaNam  FROM CCyArea c where (c.lockId='0' or c.lockId is null)  group by c.cyAreaNo, c.cyAreaNam";
		List mList = JpaUtils.findAll(jpql, paramLs);
		Result result = new Result();
		result.setData(mList);
		return result;
	}

	@Override
	public Result choosecyRowNo(HdEzuiQueryParams hdEzuiQueryParams) {
		String jpql = "SELECT c.cy_Row_No FROM C_Cy_Bay c where c.cy_Area_No=?cyAreaNo and nvl(c.lock_Id,'0')='0' group by  c.cy_Row_No ORDER BY  to_number(c.cy_Row_No)";
		List mList = JpaUtils.getEntityManager().createNativeQuery(jpql)
				.setParameter("cyAreaNo",  hdEzuiQueryParams.getOthers().get("cyAreaNo")).getResultList();
		Result result = new Result();
		result.setData(mList);
		return result;
	}

	@Override
	public Result choosecyBayNo(HdEzuiQueryParams hdEzuiQueryParams) {
		String jpql = "SELECT c.cy_Bay_No FROM C_Cy_Bay c where c.cy_Area_No=?cyAreaNo and c.cy_Row_No=?cyRowNo and nvl(c.lock_Id,'0')='0' ORDER BY  to_number(c.cy_Bay_No)";
		List mList = JpaUtils.getEntityManager().createNativeQuery(jpql)
				.setParameter("cyAreaNo",  hdEzuiQueryParams.getOthers().get("cyAreaNo"))
				.setParameter("cyRowNo",  hdEzuiQueryParams.getOthers().get("cyRowNo")).getResultList();
		Result result = new Result();
		result.setData(mList);
		return result;
	}

	@Override
	public String getCarLocation(String cyAreaNo) {
		String sql="select f_get_car_location(?p_cy_area_no) ws_loc from dual";
		List<Map> list =JpaUtils.getEntityManager().createNativeQuery(sql).setParameter("p_cy_area_no", cyAreaNo).setHint(QueryHints.RESULT_TYPE, ResultType.Map).getResultList();
		return (String)list.get(0).get("WS_LOC");
	}

	@Override
	public Result chooseWorkRun(HdEzuiQueryParams hdEzuiQueryParams) {
		String jpql = "SELECT c FROM CWorkRun c where 1=1 ";
		QueryParamLs paramLs = new QueryParamLs();
		List mList = JpaUtils.findAll(jpql, paramLs);
		Result result = new Result();
		result.setData(mList);
		return result;
	}

	@Override
	public Result findShipwmxc(HdEzuiQueryParams hdEzuiQueryParams) {
		QueryParamLs paramLs = new QueryParamLs();
		// 外贸卸船队列
		String jpql2 = "select a.cShipNam,a.ivoyage voyage, b.workTyp, a.shipNo, b.workQueueNo from Ship a,WorkQueue b "
				+ "where a.shipNo=b.shipNo and  b.workTyp='SI' and a.tradeId='2' and a.shipStat in ('E', 'Y')";
		PageInfo pageInfo = new PageInfo();
//		pageInfo.setCurPageNum(1);
//		pageInfo.setRowOfPage(10);
		List mList2 = JpaUtils.findAll(jpql2, paramLs, null);
		Result result = new Result();
		result.setData(mList2);
		return result;
	}

	//丰田内贸装船
	@Override
	public Result findShipftnmzc(HdEzuiQueryParams hdEzuiQueryParams) {
		QueryParamLs paramLs = new QueryParamLs();
		// 丰田内贸装船队列
//		String jpql2 = "select a.cShipNam,a.evoyage voyage, a.shipNo from Ship a "
//				+ "where 1=1  and a.tradeId='1' ";
		String jpql2 = "select a.cShipNam,a.evoyage voyage, b.workTyp, a.shipNo, b.workQueueNo from Ship a,WorkQueue b "
				+ "where 1=1 and a.shipNo=b.shipNo and  b.workTyp='SO' and a.tradeId='1' and a.shipStat in ('E','Y') ";
		PageInfo pageInfo = new PageInfo();
		pageInfo.setCurPageNum(1);
		pageInfo.setRowOfPage(100);
		List mList2 = JpaUtils.findAll(jpql2, paramLs, null);
		Result result = new Result();
		result.setData(mList2);
		return result;	
	}

	@Override
	public Result findShipnmxc(HdEzuiQueryParams hdEzuiQueryParams) {
		QueryParamLs paramLs = new QueryParamLs();
		// 内贸卸船，选船,直接理
		String jpql2 = "select a.cShipNam,a.ivoyage voyage, b.workTyp, a.shipNo, b.workQueueNo from Ship a,WorkQueue b "
				+ "where 1=1 and a.shipNo=b.shipNo and  b.workTyp='SI' and a.tradeId='1'  and a.shipStat in ('E','Y') ";
		PageInfo pageInfo = new PageInfo();
		//pageInfo.setCurPageNum(1);
		//pageInfo.setRowOfPage(10);
		List mList2 = JpaUtils.findAll(jpql2, paramLs, null);
		Result result = new Result();
		result.setData(mList2);
		return result;
	}


	

	@Override
	public Result findWmjg(HdEzuiQueryParams hdEzuiQueryParams) {
		QueryParamLs paramLs = new QueryParamLs();
		String dockCod=(String) hdEzuiQueryParams.getOthers().get("dockCod");
		String day = (String) hdEzuiQueryParams.getOthers().get("day");
		String sql = "select a.truck_No, b.carry_Id, b.contract_No," 
				+ "b.truck_No, a.single_Id, a.ingate_Id ,c.ship_Nam,d.brand_Nam,c.car_Num,c.plan_area,d.brand_cod,to_char(c.validat_dte,'yyyy-mm-dd')"
				+ " from Gate_Truck a, Gate_Truck_Contract b,Contract_Ie_Doc c,C_Brand d  " 
				+ " where a.ingate_Id = b.ingate_Id  and d.brand_Cod(+)=c.brand and b.contract_No=c.contract_No "
				+ "   and c.trade_Id='2' and b.carry_Id='A' AND c.confirm_Id = '1' "
				+ "   and trunc(c.validat_Dte, 'dd') >= trunc(sysdate, 'dd') and  a.dock_cod= ?dockCod  order by b.contract_no desc ";
		if ("Yesterday".equals(day)) {
			sql = "select a.truck_No, b.carry_Id, b.contract_No," 
					+ "b.truck_No, a.single_Id, a.ingate_Id ,c.ship_Nam,d.brand_Nam,c.car_Num,c.plan_area,d.brand_cod,to_char(c.validat_dte,'yyyy-mm-dd')"
					+ " from Gate_Truck a, Gate_Truck_Contract b,Contract_Ie_Doc c,C_Brand d  " 
					+ " where a.ingate_Id = b.ingate_Id  and d.brand_Cod(+)=c.brand and b.contract_No=c.contract_No "
					+ "   and c.trade_Id='2' and b.carry_Id='A' AND c.confirm_Id = '1' "
					+ "   and trunc(c.validat_Dte, 'dd') >= trunc(sysdate-1, 'dd') and  a.dock_cod= ?dockCod  order by b.contract_no desc ";
		}
		Date date=new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		date = calendar.getTime();
//		paramLs.addParam("yestoday",date);
		PageInfo pageInfo = new PageInfo();
		pageInfo.setCurPageNum(1);
		pageInfo.setRowOfPage(10);

        List mList = JpaUtils.getEntityManager().createNativeQuery(sql).setParameter("dockCod", dockCod).getResultList(); 
		Result result = new Result();
		result.setData(mList);
		return result;
		
//		List mList = JpaUtils.findAll(jpql, paramLs);
//		Result result = new Result();
//		result.setData(mList);
//		return result;
	}
	@Override
	public Result findWmsg(HdEzuiQueryParams hdEzuiQueryParams) {
		String dockCod=(String) hdEzuiQueryParams.getOthers().get("dockCod");
		String day = (String) hdEzuiQueryParams.getOthers().get("day");
		String sql = "SELECT t0.TRUCK_NO,  t1.CARRY_ID, t1.CONTRACT_NO, t1.TRUCK_NO,  " + 
				"  t0.SINGLE_ID, t0.INGATE_ID, t2.SHIP_NAM, t3.BRAND_NAM,  " + 
				"  t2.CAR_NUM, t2.brand, t2.car_typ, t2.car_kind, to_char(t2.validat_dte,'yyyy-mm-dd')  " + 
				"  FROM C_BRAND t3,CONTRACT_IE_DOC t2, GATE_TRUCK_CONTRACT t1,GATE_TRUCK     t0  " + 
				" WHERE t0.INGATE_ID = t1.INGATE_ID   " + 
				"  AND t1.CONTRACT_NO = t2.CONTRACT_NO AND t2.TRADE_ID = '2' and t2.confirm_id = '1'  AND t1.CARRY_ID = 'T'  " + 
				"  AND trunc(t2.VALIDAT_DTE, 'dd') >= trunc(sysdate, 'dd')   AND t3.BRAND_COD(+) = t2.BRAND and t0.dock_cod= ?dockCod "   +
		        " order by t1.CONTRACT_NO desc ";
		if ("Yesterday".equals(day)) {
			sql = "SELECT t0.TRUCK_NO,  t1.CARRY_ID, t1.CONTRACT_NO, t1.TRUCK_NO,  " + 
					"  t0.SINGLE_ID, t0.INGATE_ID, t2.SHIP_NAM, t3.BRAND_NAM,  " + 
					"  t2.CAR_NUM, t2.brand, t2.car_typ, t2.car_kind, to_char(t2.validat_dte,'yyyy-mm-dd')  " + 
					"  FROM C_BRAND t3,CONTRACT_IE_DOC t2, GATE_TRUCK_CONTRACT t1,GATE_TRUCK     t0  " + 
					" WHERE t0.INGATE_ID = t1.INGATE_ID   " + 
					"  AND t1.CONTRACT_NO = t2.CONTRACT_NO AND t2.TRADE_ID = '2' and t2.confirm_id = '1'  AND t1.CARRY_ID = 'T'  " + 
					"  AND trunc(t2.VALIDAT_DTE, 'dd') >= trunc(sysdate-1, 'dd')   AND t3.BRAND_COD(+) = t2.BRAND and t0.dock_cod= ?dockCod "   +
			        " order by t1.CONTRACT_NO desc ";
		}
		List mList = JpaUtils.getEntityManager().createNativeQuery(sql).setParameter("dockCod", dockCod).getResultList();
		Result result = new Result();
		result.setData(mList);
		return result;
	}
	@Override
	public Result findNmsg(HdEzuiQueryParams hdEzuiQueryParams) {
		String dockCod=(String) hdEzuiQueryParams.getOthers().get("dockCod");
		String day = (String) hdEzuiQueryParams.getOthers().get("day");
		String sql = "SELECT t0.TRUCK_NO,t1.CARRY_ID,t1.CONTRACT_NO,t1.TRUCK_NO,"
				+ "  t0.SINGLE_ID,t0.INGATE_ID,t2.SHIP_NAM,t3.BRAND_NAM,t2.CAR_NUM,to_char(t2.validat_dte,'yyyy-mm-dd')"
				+ "  FROM GATE_TRUCK t0 , GATE_TRUCK_CONTRACT t1,CONTRACT_IE_DOC t2,C_BRAND t3"
				+ "     WHERE t0.INGATE_ID = t1.INGATE_ID AND t1.CONTRACT_NO = t2.CONTRACT_NO AND t3.BRAND_COD(+) = t2.BRAND"
				+ "  AND t2.TRADE_ID = '1' AND t1.CARRY_ID = 'T' and t2.confirm_id = '1'  and t0.dock_cod= ?dockCod "
				+ " AND trunc(t2.VALIDAT_DTE, 'dd') >= trunc(sysdate, 'dd') ";
		if ("Yesterday".equals(day)) {
			sql = "SELECT t0.TRUCK_NO,t1.CARRY_ID,t1.CONTRACT_NO,t1.TRUCK_NO,"
					+ "  t0.SINGLE_ID,t0.INGATE_ID,t2.SHIP_NAM,t3.BRAND_NAM,t2.CAR_NUM,to_char(t2.validat_dte,'yyyy-mm-dd')"
					+ "  FROM GATE_TRUCK t0 , GATE_TRUCK_CONTRACT t1,CONTRACT_IE_DOC t2,C_BRAND t3"
					+ "     WHERE t0.INGATE_ID = t1.INGATE_ID AND t1.CONTRACT_NO = t2.CONTRACT_NO AND t3.BRAND_COD(+) = t2.BRAND"
					+ "  AND t2.TRADE_ID = '1' AND t1.CARRY_ID = 'T' and t2.confirm_id = '1'  and t0.dock_cod= ?dockCod "
					+ " AND trunc(t2.VALIDAT_DTE, 'dd') >= trunc(sysdate-1, 'dd') ";
		}
		List mList = JpaUtils.getEntityManager().createNativeQuery(sql).setParameter("dockCod", dockCod).getResultList();
		Result result = new Result();
		result.setData(mList);
		return result;
	}

	@Override
	public Result findNmjg(HdEzuiQueryParams hdEzuiQueryParams) {
		QueryParamLs paramLs = new QueryParamLs();
		String dockCod = (String) hdEzuiQueryParams.getOthers().get("dockCod");
		String day = (String) hdEzuiQueryParams.getOthers().get("day");
		String sql = "SELECT t0.truck_no,t1.carry_id,t1.contract_no,t1.truck_no,t0.single_id,t0.ingate_id,t2.ship_nam,t3.brand_nam,nvl(t2.car_num,'0'),to_char(t2.validat_dte,'yyyy-mm-dd')"
				+ "  from c_brand t3,contract_ie_doc t2, gate_truck_contract t1,gate_truck t0" 
				+ "  where t0.ingate_id = t1.ingate_id "
				+ "  and t1.contract_no = t2.contract_no and t2.trade_id = '1' and t1.carry_id = 'A' "
				+ "  and trunc(t2.validat_dte,'dd') >= trunc(sysdate,'dd')"
				+ "  and t3.brand_cod(+) = t2.brand and t2.confirm_id = '1' and t0.dock_cod= ?dockCod";
		if ("Yesterday".equals(day)){
			sql = "SELECT t0.truck_no,t1.carry_id,t1.contract_no,t1.truck_no,t0.single_id,t0.ingate_id,t2.ship_nam,t3.brand_nam,nvl(t2.car_num,'0'),to_char(t2.validat_dte,'yyyy-mm-dd')"
					+ "  from c_brand t3,contract_ie_doc t2, gate_truck_contract t1,gate_truck t0" 
					+ "  where t0.ingate_id = t1.ingate_id "
					+ "  and t1.contract_no = t2.contract_no and t2.trade_id = '1' and t1.carry_id = 'A' "
					+ "  and trunc(t2.validat_dte,'dd') >= trunc(sysdate-1,'dd')"
					+ "  and t3.brand_cod(+) = t2.brand and t2.confirm_id = '1' and t0.dock_cod= ?dockCod";
		}
        List mList = JpaUtils.getEntityManager().createNativeQuery(sql).setParameter("dockCod", dockCod).getResultList();
		Result result = new Result();
		result.setData(mList);
		return result;
	}

	@Override
	public Result findShipzc(HdEzuiQueryParams hdEzuiQueryParams) {
		QueryParamLs paramLs = new QueryParamLs();
		// 内贸装船，选船,直接理
		String jpql2 = "select a.cShipNam,a.evoyage voyage, b.workTyp, a.shipNo, b.workQueueNo from Ship a,WorkQueue b "
				+ "where 1=1 and a.shipNo=b.shipNo and  b.workTyp='SO' and a.shipStat in ('E', 'Y')";
		PageInfo pageInfo = new PageInfo();
		//pageInfo.setCurPageNum(1);
		//pageInfo.setRowOfPage(100);
		List mList2 = JpaUtils.findAll(jpql2, paramLs, pageInfo);
		Result result = new Result();
		result.setData(mList2);
		return result;
	}
	
	@Override
	public Result findShipnmzc(HdEzuiQueryParams hdEzuiQueryParams) {
		QueryParamLs paramLs = new QueryParamLs();
		// 内贸装船，选船,直接理
		String jpql2 = "select a.cShipNam,a.evoyage voyage, b.workTyp, a.shipNo, b.workQueueNo from Ship a,WorkQueue b "
				+ "where 1=1 and a.shipNo=b.shipNo and  b.workTyp='SO' and a.tradeId='1' and a.shipStat in ('E', 'Y')";
		PageInfo pageInfo = new PageInfo();
		//pageInfo.setCurPageNum(1);
		//pageInfo.setRowOfPage(100);
		List mList2 = JpaUtils.findAll(jpql2, paramLs, pageInfo);
		Result result = new Result();
		result.setData(mList2);
		return result;
	}	

	@Override
	public Result findShipwmzc(HdEzuiQueryParams hdEzuiQueryParams) {
		QueryParamLs paramLs = new QueryParamLs();
		// 外贸装船，根据装船计划理
		String jpql2 = "select a.cShipNam,a.evoyage voyage, b.workTyp, a.shipNo, b.workQueueNo from Ship a,WorkQueue b "
				+ "where a.shipNo=b.shipNo and  b.workTyp='SO' and a.tradeId='2' and  a.shipStat in ('E', 'Y')";
		PageInfo pageInfo = new PageInfo();
//		pageInfo.setCurPageNum(1);
//		pageInfo.setRowOfPage(10);
		List mList2 = JpaUtils.findAll(jpql2, paramLs, pageInfo);
		Result result = new Result();
		result.setData(mList2);
		return result;
	}

	@Override
	public Result findShipftnmxc(HdEzuiQueryParams hdEzuiQueryParams) {
		QueryParamLs paramLs = new QueryParamLs();
		// 丰田内贸卸船队列
//		String jpql2 = "select a.cShipNam,a.evoyage voyage, a.shipNo from Ship a "
//				+ "where 1=1  and a.tradeId='1' ";
		
		String jpql2 = "select a.cShipNam,a.ivoyage voyage, b.workTyp, a.shipNo, b.workQueueNo from Ship a,WorkQueue b "
				+ "where 1=1 and a.shipNo=b.shipNo and  b.workTyp='SI' and a.tradeId='1' and a.shipStat in ('E','Y') ";
		
		PageInfo pageInfo = new PageInfo();
		pageInfo.setCurPageNum(1);
		pageInfo.setRowOfPage(100);
		List mList2 = JpaUtils.findAll(jpql2, paramLs, pageInfo);
		Result result = new Result();
		result.setData(mList2);
		return result;	
	}

	@Override
	public Result findVcPortID(HdEzuiQueryParams hdEzuiQueryParams) {
		String sql = "select t.PORT_ID, t.vc_port_name  " + 
				"  from c_port_ft t  " + 
				" where t.vc_port_id <> '2011000'  " + 
				"   and t.vc_port_id <> '2041000'";
		
		List objList = JpaUtils.getEntityManager().createNativeQuery(sql).getResultList();
		Result result = new Result();
		result.setData(objList);
		return result;
	}
	
	@Override
	public Result findAllPortID(HdEzuiQueryParams hdEzuiQueryParams) {
		String sql = "select t.PORT_ID, t.c_port_nam  from c_port t  where t.port_class = '3' " ;  //暂时默认国内港口
		
		List objList = JpaUtils.getEntityManager().createNativeQuery(sql).getResultList();
		Result result = new Result();
		result.setData(objList);
		return result;
	}
	
	//离线模式下载船列表信息
	@Override
	public Result findShipList(String shipNo) {
		QueryParamLs paramLs = new QueryParamLs();
		String jpql2 = "select a.shipNo,a.cShipNam,(case when b.workTyp='SI' then a.ivoyage else a.evoyage end) voyage,a.tradeId, b.workTyp, b.workQueueNo from Ship a,WorkQueue b "
				+ "where a.shipNo=b.shipNo and b.workTyp in ('SO','SI') and a.shipStat in ('E','Y')";
		String shipNos=shipNo==null?"":shipNo;//hdEzuiQueryParams.getOthers().get("shipNo")==null?"":hdEzuiQueryParams.getOthers().get("shipNo").toString();
		if(!"".equals(shipNos)){
			String[] s=shipNos.split(",");
			if(s.length>0){
				String q="";
				for(int i=0;i<s.length;i++){
					if(i!=0) q+=",";
					q+="'"+s[i]+"'";
				}
				jpql2+=" and a.shipNo in ("+q+")";
			}
		}
		PageInfo pageInfo = new PageInfo();
		pageInfo.setCurPageNum(1);
		pageInfo.setRowOfPage(100);
		List mList2 = JpaUtils.findAll(jpql2, paramLs, null);
		Result result = new Result();
		result.setData(mList2);
		return result;	
	}
	
	//离线模式下载人员列表信息
	@Override
	public Result findUserList(HdEzuiQueryParams hdEzuiQueryParams) {
		QueryParamLs paramLs = new QueryParamLs();
		//String jpql = "select account,name,password from AuthUser ";
		String sql = "select a.account,a.name,a.password from AuthUser a where a.lockId =:lockId";
		QueryParamLs paramls = new QueryParamLs();
		paramls.addParam("lockId", "0");
		List mList2 = JpaUtils.findAll(sql, paramls, null);
		Result result = new Result();
		result.setData(mList2);
		return result;	
	}
	
	//离线模式下载丰田流向列表
	@Override
	public Result findPortFTList(HdEzuiQueryParams hdEzuiQueryParams) {
		String sql = "select t.PORT_FT_ID,t.VC_PORT_ID, t.vc_port_name, t.PORT_ID  " + 
				"  from c_port_ft t  " + 
				" where t.vc_port_id <> '2011000' " + 
				"   and t.vc_port_id <> '2041000' ";
		
		List objList = JpaUtils.getEntityManager().createNativeQuery(sql).getResultList();
		Result result = new Result();
		result.setData(objList);
		return result;
	}
	
	//离线模式下载
	@Override
	public Result findShipInOutCheckList(String shipNo,String workTyp) {
		String sql = "select t.checkId,t.shipNo, t.vcVinNo, t.vcPort, t.workTyp,t.vcGarage from ShipInOutCheck t where 1=1" ;
		String shipNos=shipNo==null?"":shipNo;
		if(!"".equals(shipNos)){
			String[] s=shipNos.split(",");
			String[] workTyps=workTyp.split(",");
			if(s.length>0){
				String q="";
				for(int i=0;i<s.length;i++){
					if(i>0) q+=" or ";
					q+="(t.shipNo='"+s[i]+"' and t.workTyp='"+workTyps[i]+"')";
				}
				sql+=" and ("+q+")";
			}
		}
		List mList2 = JpaUtils.findAll(sql, null);
		Result result = new Result();
		result.setData(mList2);
		return result;	
	}
	
	//离线模式下载
	@Override
	public Result findYardInList(String shipNo) {
		String sql = "select t.inId,t.shipNo, t.vcVinNo, t.vcSite " + 
				"  from YardIn t where 1=1 " ;
		String shipNos=shipNo==null?"":shipNo;
		if(!"".equals(shipNos)){
			String[] s=shipNos.split(",");
			if(s.length>0){
				String q="";
				for(int i=0;i<s.length;i++){
					if(i!=0) q+=",";
					q+="'"+s[i]+"'";
				}
				sql+=" and t.shipNo in ("+q+")";
			}
		}
		List mList2 = JpaUtils.findAll(sql, null);
		Result result = new Result();
		result.setData(mList2);
		return result;	
	}
	
	//离线模式在港车辆列表
	@Override
	public Result findPortCarList(HdEzuiQueryParams hdEzuiQueryParams) {
		String sql = "select t.portCarNo,t.currentStat, t.vinNo, t.shipNo, t.billNo , t.iEId, t.tradeId  " + 
				"  from PortCar t ,CBrand c where t.brandCod=c.brandCod and t.currentStat ='2' and c.brandNam  in ('一汽丰田','广汽丰田') " ;
		
//		String sql = "select t.portCarNo,t.currentStat, t.vinNo, t.shipNo, t.billNo , t.iEId, t.tradeId  " + 
//				"  from PortCar t where t.currentStat ='2' and t.shipNo = '20190401152310' " ;
		
//		String sql = "select t.portCarNo,t.currentStat, t.vinNo, t.shipNo, t.billNo , t.iEId, t.tradeId  " + 
//				"  from PortCar t where t.currentStat ='2' " ;
		
		List mList2 = JpaUtils.findAll(sql, null);
		Result result = new Result();
		result.setData(mList2);
		return result;	
	}
	@Override
	public Result findPortCarStateList(Map map) {
		String sql="";
		if(map.containsKey("sign")&&map.get("sign").equals("1")) {
			sql = "select t.portCarNo,t.currentStat, t.vinNo, t.shipNo, t.billNo , t.iEId, t.tradeId  " + 
					"  from PortCar t ,CBrand c where t.brandCod=c.brandCod and t.currentStat ='2' and c.brandNam  in ('一汽丰田','广汽丰田') " ;
		}else {
			sql = "select t.portCarNo,t.currentStat, t.vinNo, t.shipNo, t.billNo , t.iEId, t.tradeId  " + 
					"  from PortCar t ,CBrand c where t.brandCod=c.brandCod and t.currentStat ='2' " ;
		}
		List mList2 = JpaUtils.findAll(sql, null);
		Result result = new Result();
		result.setData(mList2);
		return result;
	}
	
	//离线模式下载车辆品牌
	@Override
	public Result findBrandList(HdEzuiQueryParams hdEzuiQueryParams) {
		String sql = "select t.brandCod,t.brandNam, t.brandShort, t.brandEname  " + 
				"  from CBrand t " ;
		
		List mList2 = JpaUtils.findAll(sql, null);
		Result result = new Result();
		result.setData(mList2);
		return result;	
	}
	
	//离线模式下载车类
	@Override
	public Result findCCarKindList(HdEzuiQueryParams hdEzuiQueryParams) {
		String sql = "select t.carKind,t.carKindNam " + 
				"  from CCarKind t " ;
		
		List mList2 = JpaUtils.findAll(sql, null);
		Result result = new Result();
		result.setData(mList2);
		return result;	
	}

	//离线模式下载车型
	@Override
	public Result findCCarTypList(HdEzuiQueryParams hdEzuiQueryParams) {
		String sql = "select t.carTyp,t.carTypNam, t.brandCod, t.carKind  " + 
				"  from CCarTyp t" ;
		
		List mList2 = JpaUtils.findAll(sql, null);
		Result result = new Result();
		result.setData(mList2);
		return result;	
	}
	
	//离线模式下载车型
	@Override
	public Result findCCarVinList(HdEzuiQueryParams hdEzuiQueryParams) {
		String sql = "select t.vinId,t.carTyp, t.vinNo, t.typCod  " + 
				"  from CCarVin t " ;
		
		List mList2 = JpaUtils.findAll(sql, null);
		Result result = new Result();
		result.setData(mList2);
		return result;	
	}
	
	//离线模式下载车型
	@Override
	public Result findCyAreaNoList(HdEzuiQueryParams hdEzuiQueryParams) {
		QueryParamLs paramLs = new QueryParamLs();
		// String jpql = "SELECT distinct(c.cyAreaNo) as cyAreaNo, c.cyAreaNam
		// FROM CCyArea c, CCyBay b where 1=1 and c.cyAreaNo = b.cyAreaNo and
		// b.lockId='0'";
		String jpql = "SELECT distinct(c.cyAreaNo) as cyAreaNo, c.cyAreaNam  FROM CCyArea c where 1=1";
		List mList = JpaUtils.findAll(jpql, paramLs);
		Result result = new Result();
		result.setData(mList);
		return result;
	}
	
	@Override
	public Result findCyRowNoList(HdEzuiQueryParams hdEzuiQueryParams) {
		String jpql = "SELECT max(to_number(c.cy_row_no)) cy_row_no FROM C_Cy_Bay c ";
		List mList = JpaUtils.getEntityManager().createNativeQuery(jpql).getResultList();
		Result result = new Result();
		result.setData(mList);
		return result;
	}
	@Override
	public Result findCyBayNoList(HdEzuiQueryParams hdEzuiQueryParams) {
		String jpql = "SELECT max(to_number(c.cy_Bay_No)) FROM C_Cy_Bay c ";
		List mList = JpaUtils.getEntityManager().createNativeQuery(jpql).getResultList();
		Result result = new Result();
		result.setData(mList);
		return result;
	}
	@Transactional
	public String ftnmLoadShip(String req) {
		JSONObject jobject = JSONObject.fromObject(req);
		Date sysDate = HdUtils.getDateTime();
		String result = "true";
		QueryParamLs paramLs = new QueryParamLs();
		String jpql = "SELECT  a FROM MPdaWorkCommand a where a.vinNo =:vinNo and a.shipNo=:shipNo and a.workTyp=:workTyp";
		paramLs.addParam("vinNo", jobject.get("vinNo").toString());
		paramLs.addParam("shipNo", jobject.get("shipNo").toString());
		paramLs.addParam("workTyp", jobject.get("workTyp").toString());
		List mList = JpaUtils.findAll(jpql, paramLs);
		if(mList.size()>0){
			return "已经理过数据";
		}
		// 先校验vinNo信息
		String rJpql = "select a from PortCar a where a.vinNo =:vinNo";
		QueryParamLs rParam = new QueryParamLs();
		rParam.addParam("vinNo", jobject.get("vinNo").toString());
		
		List<PortCar> pcList = JpaUtils.findAll(rJpql, rParam);
		//如果车架号存在
		if (pcList.size() > 0) {
			if (pcList.get(0).getCurrentStat() != null &&! pcList.get(0).getCurrentStat().toString().equals("")) {
				if (!pcList.get(0).getCurrentStat().toString().equals("2")) {
					// 此车不在场，不能装船，停止
					result = "placeError";
				} else {

					// 流水号
					String jpqlpcar = "select a from PortCar a where a.vinNo =:vinNo";
					QueryParamLs pcarParams = new QueryParamLs();
					pcarParams.addParam("vinNo", jobject.get("vinNo").toString());
					List<PortCar> pcarList = JpaUtils.findAll(jpqlpcar, pcarParams);
					
					// 生成指令记录
					MPdaWorkCommand wcList = new MPdaWorkCommand();
					wcList.setQueueId(HdUtils.genUuid());
					wcList.setWorkQueueNo(jobject.get("workQueueNo").toString());
					wcList.setPortCarNo(pcarList.get(0).getPortCarNo());
					wcList.setVinNo(jobject.get("vinNo").toString());
					wcList.setShipNo(jobject.get("shipNo").toString());
					wcList.setWorkTyp("SO");
					wcList.setBrandCod(pcarList.get(0).getBrandCod()); // 车辆品牌
					wcList.setCarTyp(pcarList.get(0).getCarTyp()); // 车型
					wcList.setCarKind(pcarList.get(0).getCarKind()); // 车数类别
					// 直取直装(暂时不填)
					wcList.setRemarks("手持录入");
					wcList.setSendTim((Timestamp) sysDate);
					wcList.setSendNam(jobject.optString("userNam"));
					wcList.setShipWorkNam(jobject.optString("userNam"));
					wcList.setShipWorkTim((Timestamp) sysDate);
					wcList.setFinishedId("0");
					wcList.setQzId(jobject.get("qzId")==null?"":jobject.get("qzId").toString());
					JpaUtils.getBaseDao().save(wcList);

				}
			}
		}
		return result;
	}
	
	@Transactional
	public void ftnmUnLoadShip(String req) {
		JSONObject jobject = JSONObject.fromObject(req);
		QueryParamLs paramLs = new QueryParamLs();
		String jpql = "SELECT w FROM MPdaWorkCommand w where w.vinNo =:vinNo and w.shipNo=:shipNo ";
		paramLs.addParam("vinNo", jobject.get("vinNo").toString());
		paramLs.addParam("shipNo", jobject.get("shipNo").toString());
		List mList = JpaUtils.findAll(jpql, paramLs);
		if(mList.size()>0){
			return ;
		}
		MPdaWorkCommand workCommand = new MPdaWorkCommand();
		workCommand.setWorkQueueNo(jobject.optString("workQueueNo"));
		workCommand.setVinNo(jobject.optString("vinNo"));
		workCommand.setWorkTyp("SI");
		workCommand.setPortCarNo(new BigDecimal(1));
		workCommand.setBrandCod(jobject.optString("brandCod"));
		workCommand.setCarTyp(jobject.optString("carTyp"));
		workCommand.setShipWorkTim(HdUtils.getDateTime());
		workCommand.setFinishedId("0");
		String vcGarage = jobject.optString("cyAreaNo");
		String[]  vcGarageList = vcGarage.split("&");
		if(vcGarageList.length == 2) {
			String row = vcGarageList[1];
			Pattern pattern = Pattern.compile("[0-9]*");
       Matcher isNum = pattern.matcher(row);
       if(isNum.matches()){
       	workCommand.setCyRowNo(row.substring(row.length()-2,row.length()));
       }
		}
		
		workCommand.setCyPlac(vcGarage.split("&")[0]);
		workCommand.setCyRowNo1(jobject.optString("cyRowNo"));
		workCommand.setCyBayNo1(jobject.optString("cyBayNo"));
		workCommand.setLengthOverId(jobject.optString("lengthOverId"));
		workCommand.setUseMachId(jobject.optString("useMachId"));
		workCommand.setUseWorkerId(jobject.optString("useWorkedId"));
		workCommand.setNightId(jobject.optString("nightId"));
		workCommand.setHolidayId(jobject.optString("holidayId"));
		workCommand.setQueueId(HdUtils.genUuid());
		workCommand.setInCyTim(HdUtils.getDateTime());
		workCommand.setInCyNam(jobject.optString("userNam"));
		workCommand.setShipNo(jobject.optString("shipNo"));
		workCommand.setCarKind(jobject.optString("carKind"));
		workCommand.setDirectId(jobject.optString("directId"));
		workCommand.setBillNo(jobject.optString("billNo"));
		workCommand.setRemarks("手持录入");
		String lhFlag = (String) jobject.optString("lhFlag");
		if(lhFlag.equals("1")) {
			workCommand.setVcexception("1");
		}
		JpaUtils.getBaseDao().save(workCommand);
	}

	@Override
	public String createRfidVinno(String account,String rfidNo) {
		String sql="select f_create_rfid_vinno(?account) VINNO,RFID_NO from c_rfid where rfid_cod=?rifdNo ";
		List<Map> list =JpaUtils.getEntityManager().createNativeQuery(sql).setParameter("account", account).setParameter("rifdNo", rfidNo).setHint(QueryHints.RESULT_TYPE, ResultType.Map).getResultList();
		
	    if(list.size()>0) {
	    	Map<String,String> map=list.get(0);
	    	String sql1="SELECT /*+ FULL(PORT_CAR) */ COUNT(1) CT    FROM port_car  " + 
	    		" WHERE rfid_card_no =?rfidNo    AND current_stat = '2' ";
	    	
	    	List<Map> lstNum =JpaUtils.getEntityManager().createNativeQuery(sql1).setParameter("rfidNo", map.get("RFID_NO")+"").setHint(QueryHints.RESULT_TYPE, ResultType.Map).getResultList();
	    	Map mapCt=lstNum.get(0);
	    	if(Integer.parseInt( mapCt.get("CT")+"")>0) {
	    		return "-2";
	    	}else { 
		    	JSONObject obj=new JSONObject();
		    	obj.putAll(map);
		    	return obj.toString();
	    	}
	    }else {
	    	return  "-1";
	    }
	}

	@Override
	public String getRfidVinno(String rifdNo) {
		String sql="select  f_get_rfid_vinno(?rifdNo) VINNO,RFID_NO from c_rfid where rfid_cod=?rifdNo2";
		List<Map> list =JpaUtils.getEntityManager().createNativeQuery(sql).setParameter("rifdNo", rifdNo).setParameter("rifdNo2", rifdNo).setHint(QueryHints.RESULT_TYPE, ResultType.Map).getResultList();

	    if(list.size()>0) {
	    	Map<String,String> map=list.get(0);
	    	JSONObject obj=new JSONObject();
	    	obj.putAll(map);
	    	return obj.toString();
	    }else {
	    	return  "-1";
	    }
		
	}	
	
	@Override
	public String createMbvRfidVin(String rfidNo) {
		String sql="select f_create_rfid_vinno('MBV') VINNO,RFID_NO from c_rfid where rfid_no=?rifdNo ";
		List<Map> list =JpaUtils.getEntityManager().createNativeQuery(sql).setParameter("rifdNo", rfidNo).setHint(QueryHints.RESULT_TYPE, ResultType.Map).getResultList();
		
	    if(list.size()>0) {
	    	Map<String,String> map=list.get(0);
	    	String sql1="SELECT /*+ FULL(PORT_CAR) */ COUNT(1) CT    FROM port_car  " + 
	    		" WHERE rfid_card_no =?rfidNo    AND current_stat = '2' ";	    	
	    	List<Map> lstNum =JpaUtils.getEntityManager().createNativeQuery(sql1).setParameter("rfidNo", map.get("RFID_NO")+"").setHint(QueryHints.RESULT_TYPE, ResultType.Map).getResultList();
	    	Map mapCt=lstNum.get(0);
	    	if(Integer.parseInt( mapCt.get("CT")+"")>0) {
	    		return "-2";
	    	}else { 
		    	JSONObject obj=new JSONObject();
		    	obj.putAll(map);
		    	return obj.toString();
	    	}
	    }else {
	    	return  "-1";
	    }
	}
	
	@Override
	public String getMbvRfidVinno(String shipNo, String rifdNo) {
		
		String sql="select t1.VIN_NO VINNO,  t2.rfid_no RFID_NO from m_bill_vin t1, c_rfid t2 where t1.rfid_no = t2.rfid_no and t1.ship_no = ?shipNo2 and t2.rfid_cod=?rifdNo2";
		List<Map> list =JpaUtils.getEntityManager().createNativeQuery(sql).setParameter("shipNo2", shipNo).setParameter("rifdNo2", rifdNo).setHint(QueryHints.RESULT_TYPE, ResultType.Map).getResultList();
	    if(list.size()>0) {
	    	Map<String,String> map=list.get(0);
	    	JSONObject obj=new JSONObject();
	    	obj.putAll(map);
	    	return obj.toString();
	    }else {
	    	return  "-1";
	    }
		
	}
	

	@Override
	public Result findFcd(String vinNo) {
		String sql = "select t.PORT_CAR_NO,t.RFID_CARD_NO, t.LENGTH, t.WIDTH,t.HEIGHT,t.vin_no  from port_car t   where t.CURRENT_STAT='2' and t.vin_no=?";	
		List objList = JpaUtils.getEntityManager().createNativeQuery(sql).setParameter(1,vinNo).setHint(QueryHints.RESULT_TYPE, ResultType.Map).getResultList();
		Result result = new Result();
		result.setData(objList);
		return result;
	}

	@Override
	public String saveFcd(String req) {
		JSONObject jobject = JSONObject.fromObject(req);
		PortCar portCar = JpaUtils.findById(PortCar.class, BigDecimal.valueOf(jobject.optLong("portCarNo")));
		if(portCar==null) {
			return "车架号不存在";
		}
		portCar.setLength(BigDecimal.valueOf(jobject.optLong("length")));
		portCar.setWidth(BigDecimal.valueOf(jobject.optLong("width")));
		portCar.setHeight(BigDecimal.valueOf(jobject.optLong("height")));
		JpaUtils.getBaseDao().update(portCar);
		return "1";
	}

	@Override
	public String saveShipBillPortCar(String req) {
		// TODO Auto-generated method stub
		JSONObject jobject = JSONObject.fromObject(req);
		//
		
		//
		PortCar portCar = JpaUtils.findById(PortCar.class, BigDecimal.valueOf(jobject.optLong("portCarNo")));
		if(portCar==null) {
			return "车架号不存在";
		}
//		else{
//			return String.valueOf(jobject.optLong("portCarNo"));
//		}
		if(!portCar.getCurrentStat().equals("2")){
			return "该车不在场，禁止绑定！"; // 
		}
		
		//
		portCar.setBillNo(jobject.getString("billNo"));
		portCar.setShipNo(jobject.getString("shipNo"));
		JpaUtils.getBaseDao().update(portCar);
		return "1";
	}


	@Override
	public Result findMbillVin(String shipNo) {

		String sql = "select t  from MBillVin t where t.shipNo in :shipNo" ;
		QueryParamLs paramLs = new QueryParamLs();
		List<String> shipArray = Arrays.asList( shipNo.split(","));
		paramLs.addParam("shipNo", shipArray);
		List mList2 = JpaUtils.findAll(sql, paramLs);
		
		Result result = new Result();
		result.setData(mList2);
		return result;	
	}

	@Override
	public Result findCrfid(String shipNo) {
		String sql = "select t  from Crfid t " ;
		QueryParamLs paramLs = new QueryParamLs();
		List mList2 = JpaUtils.findAll(sql, paramLs);	
		Result result = new Result();
		result.setData(mList2);
		return result;	
	}
	@Override
	public Result findBillCarBytShipNo(String shipNo) {

		String sql = "select t  from BillCar t where t.shipNo in :shipNo" ;
		QueryParamLs paramLs = new QueryParamLs();
		List<String> shipArray = Arrays.asList( shipNo.split(","));
		paramLs.addParam("shipNo", shipArray);
		List mList2 = JpaUtils.findAll(sql, paramLs);
		
		Result result = new Result();
		result.setData(mList2);
		return result;	
	}

	@Override
	public String findCarNumByShip(HdQuery hdQuery) {    
		String totalSql="select sum(a.pieces) from ShipBill a where a.shipNo=:shipNo and a.iEId=:workTyp";
		String workSql="select count(a) from WorkCommand a where a.shipNo=:shipNo and a.workTyp=:workTyp";
		QueryParamLs paramLs = new QueryParamLs();
		paramLs.addParam("shipNo",hdQuery.getStr("shipNo"));
		paramLs.addParam("workTyp",hdQuery.getStr("workTyp"));		
		Long work=JpaUtils.single(workSql, paramLs);
		paramLs.addParam("workTyp",hdQuery.getStr("workTyp")=="SI"?"I":"E");
		BigDecimal total=(BigDecimal) JpaUtils.single(totalSql, paramLs);
		String num=work+"/"+(total==null?"0":total);
		return num;
	}

	@Override
	public String findCarNumByShipAndBill(HdQuery hdQuery) {    
		String totalSql="select sum(a.pieces) from ShipBill a where a.shipNo=:shipNo and a.iEId=:workTyp and a.billNo=:billNo";
		String workSql="select count(a) from WorkCommand a where a.shipNo=:shipNo and a.workTyp=:workTyp and a.billNo=:billNo";
		QueryParamLs paramLs = new QueryParamLs();
		paramLs.addParam("shipNo",hdQuery.getStr("shipNo"));
		paramLs.addParam("workTyp",hdQuery.getStr("workTyp"));
		paramLs.addParam("billNo", hdQuery.getStr("billNo"));	
		Long work=JpaUtils.single(workSql, paramLs);
		paramLs.addParam("workTyp",hdQuery.getStr("workTyp")=="SI"?"I":"E");
		BigDecimal total=(BigDecimal) JpaUtils.single(totalSql, paramLs);
		String num=work+"/"+(total==null?"0":total);
		return num;
	}



}
