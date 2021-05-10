package net.huadong.tech.base.service.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Transient;

import net.huadong.tech.util.CommonUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.eclipse.persistence.config.QueryHints;
import org.eclipse.persistence.config.ResultType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.ctc.wstx.util.StringUtil;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.Interface.entity.CargoDataSpecification;
import net.huadong.tech.Interface.entity.VGroupCorpBrand;
import net.huadong.tech.Interface.entity.VGroupCorpYard;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.bean.HdQueryParam;
import net.huadong.tech.base.bean.HdRunTimeException;
import net.huadong.tech.base.entity.CArea;
import net.huadong.tech.base.entity.CBrand;
import net.huadong.tech.base.entity.CBrandDetail;
import net.huadong.tech.base.entity.CCarKind;
import net.huadong.tech.base.entity.CCarTyp;
import net.huadong.tech.base.entity.CClientCod;
import net.huadong.tech.base.entity.CCyArea;
import net.huadong.tech.base.entity.CDamage;
import net.huadong.tech.base.entity.CDock;
import net.huadong.tech.base.entity.CPort;
import net.huadong.tech.base.service.CCyAreaService;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.tech.inter.entity.PortCarInter;
import net.huadong.tech.inter.entity.RespInter;
import net.huadong.tech.ship.entity.Ship;
import net.huadong.tech.ship.entity.ShipStat;
import net.huadong.tech.shipbill.entity.BillCar;
import net.huadong.tech.shipbill.entity.ContractIeDoc;
import net.huadong.tech.shipbill.entity.PortCar;
import net.huadong.tech.shipbill.entity.ShipBill;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiQueryParams;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;
import net.huadong.tech.task.GisTask;
import net.huadong.tech.util.HdUtils;
import net.sf.json.JSONObject;

/**
 * @author
 */
@Component
public class CCyAreaServiceImpl implements CCyAreaService {

	@Value("${tjgjt.service.ip}")
	private String tjgjtServiceIp;

	@Override
	public HdEzuiDatagridData find(HdEzuiQueryParams hdQuery) {
		// TODO Auto-generated method stub
		String jpql = "select a from CCyArea a where 1=1 ";
		// String cyAreaNo = "HQC2"; 
		String cyAreaNo = (String) hdQuery.getOthers().get("cyAreaNo");
		String cyTyp = (String) hdQuery.getOthers().get("cyTyp");
		String dockCod = (String) hdQuery.getOthers().get("dockCod");
		QueryParamLs paramLs = new QueryParamLs();
		if (HdUtils.strNotNull(cyAreaNo)) {
			jpql += "and a.cyAreaNo like :cyAreaNo ";
			paramLs.addParam("cyAreaNo", "%"+cyAreaNo+"%");
		} /*else {
			jpql += "and a.cyAreaNo like :cyAreaNo ";
			paramLs.addParam("cyAreaNo", "%HQ%");
		}*/
		if (HdUtils.strNotNull(cyTyp)) {
			jpql += "and a.cyTyp =:cyTyp ";
			paramLs.addParam("cyTyp", cyTyp);
		}
		 
		if (HdUtils.strNotNull(dockCod)) { 
			if(dockCod.equals("TJG_HQ")) {
				//jpql +="and a.x0 != null and a.dockCod =:dockCod ";
				jpql +="  and a.dockCod =:dockCod ";
				//环球
				paramLs.addParam("dockCod", "03409000"); 
			}else if(dockCod.equals("TJG_GZ")){
				jpql +="and a.dockCod =:dockCod ";
				//滚装
				paramLs.addParam("dockCod", "03406500"); 
			}
		   }
		 

		jpql += "order by a.recTim desc";
		//HdEzuiDatagridData result = JpaUtils.findByEz(jpql, paramLs, hdQuery);
		HdEzuiDatagridData result = JpaUtils.findByEz(jpql, paramLs, null, hdQuery);
		List<CCyArea> cCyAreaList = result.getRows();
		for (CCyArea cCyArea : cCyAreaList) {
			String jpql1 = "select count(a) from PortCar a where a.cyAreaNo =:cyAreaNo";
			QueryParamLs paramLs1 = new QueryParamLs();
			paramLs1.addParam("cyAreaNo", cCyArea.getCyAreaNo());
			List<Long> countNum = JpaUtils.findAll(jpql1, paramLs1);
			String cCyAreaNo=cCyArea.getCyAreaNo();
		 
			BigDecimal dccs = cCyArea.getRowNum().multiply(cCyArea.getBayNum());
			int r=dccs.compareTo(BigDecimal.ZERO); 
			if(r==1) {
				BigDecimal zyl = BigDecimal.valueOf(countNum.get(0)).divide(dccs, 3, RoundingMode.HALF_UP);
				cCyArea.setZyl(zyl);
			}
			
			if (HdUtils.strNotNull(cCyArea.getDockCod())){
				CDock bean = JpaUtils.findById(CDock.class, cCyArea.getDockCod());
				cCyArea.setDockNam(bean.getDockNam());
			}
		}
		return result;
	}
	
	@Transactional
	public void removeAll(String cyAreaNos) {
		List<String> cyAreaNoList = HdUtils.paraseStrs(cyAreaNos);
		for (String cyAreaNo : cyAreaNoList) {
			JpaUtils.remove(CCyArea.class, cyAreaNo);
		}
	}

	@Override
	public HdEzuiDatagridData findCdxx(HdQuery hdQuery) {
		// TODO Auto-generated method stub
		String jpql = "select a from CCyArea a where 1=1 ";
		String cyAreaNo = hdQuery.getStr("cyAreaNo");
		String cyTyp = hdQuery.getStr("cyTyp");
		QueryParamLs paramLs = new QueryParamLs();
		if (HdUtils.strNotNull(cyAreaNo)) {
			jpql += "and a.cyAreaNo =:cyAreaNo ";
			paramLs.addParam("cyAreaNo", cyAreaNo);
		}
		if (HdUtils.strNotNull(cyTyp)) {
			jpql += "and a.cyTyp =:cyTyp ";
			paramLs.addParam("cyTyp", cyTyp);
		}
		jpql += "order by a.recTim desc";
		HdEzuiDatagridData result = JpaUtils.findByEz(jpql, paramLs, hdQuery);
		
		
		//（昨8点至今8点）出库数
		String sqlOut="select count(1) ct,a.cy_area_no from  work_command t,port_car a where t.port_car_no=a.port_car_no " + 
				" and t.OUT_CY_TIM>=trunc(sysdate-1)+8/24 and t.OUT_CY_TIM<=trunc(sysdate)+8/24 " + 
				"group by a.cy_area_no";
		//（今8点）在库数
		String sqlIn="select  count(1) ct,CY_AREA_NO from port_car where CURRENT_STAT =2 and in_cy_tim <=trunc(sysdate)+8/24 group by cy_area_no";
		
		
		List<Map>  lstOut = JpaUtils.getEntityManager().createNativeQuery(sqlOut).setHint(QueryHints.RESULT_TYPE, ResultType.Map).getResultList(); 	
		Map outMap=new HashMap();
		for (Map map : lstOut) {
			outMap.put(map.get("CY_AREA_NO")+"", map.get("CT")+"");
		}
		List<Map>  lstIn = JpaUtils.getEntityManager().createNativeQuery(sqlIn).setHint(QueryHints.RESULT_TYPE, ResultType.Map).getResultList();
		Map inMap=new HashMap();
		for (Map map : lstIn) {
			inMap.put(map.get("CY_AREA_NO")+"", map.get("CT")+"");
		}
		
		List<CCyArea> cCyAreaList = result.getRows();
		for (CCyArea cCyArea : cCyAreaList) {
			String jpql1 = "select count(a.portCarNo) cnt from PortCar a where a.carKind =:carKind and a.cyAreaNo =:cyAreaNo and a.currentStat=2";
			QueryParamLs paramLs1 = new QueryParamLs();
			paramLs1.addParam("cyAreaNo", cCyArea.getCyAreaNo());
			paramLs1.addParam("carKind", "01");
			QueryParamLs paramLs2 = new QueryParamLs();
			paramLs2.addParam("cyAreaNo", cCyArea.getCyAreaNo());
			paramLs2.addParam("carKind", "04");
			List<Long> jcList = JpaUtils.findAll(jpql1, paramLs1);
			cCyArea.setJcs(jcList.get(0).toString());
			List<Long> sbList = JpaUtils.findAll(jpql1, paramLs2);
			cCyArea.setSbs(sbList.get(0).toString());
			cCyArea.setZdcc(cCyArea.getRowNum().multiply(cCyArea.getBayNum()).toString());
			
			if(outMap.containsKey(cCyArea.getCyAreaNo())) {
				cCyArea.setOtNum(outMap.get(cCyArea.getCyAreaNo())+"");
			}else {
				cCyArea.setOtNum("0");
			}
			
			if(inMap.containsKey(cCyArea.getCyAreaNo())) {
				cCyArea.setInNum(inMap.get(cCyArea.getCyAreaNo())+"");
			}else {
				cCyArea.setInNum("0");
			}
		}
		return result;
	}
	@Override
	public HdEzuiDatagridData findDcclhz(HdQuery hdQuery) {
		String cyAreaNo = hdQuery.getStr("cyAreaNo");
		String shipNo = hdQuery.getStr("shipNo");
		String billNo = hdQuery.getStr("billNo");
		String incyTime = hdQuery.getStr("incyTime");
		String brandCod = hdQuery.getStr("brandCod");
		String carKind = hdQuery.getStr("carKind");
		String carTyp = hdQuery.getStr("carTyp");
		String iEId = hdQuery.getStr("iEId");
		String tradeId = hdQuery.getStr("tradeId");
		String companyCod = hdQuery.getStr("companyCod");
		String dockCod="";
		String timeLimit=hdQuery.getStr("timeLimit");
		if("TJG_GZ".equals(companyCod)) {
			//滚装
			dockCod="03406500";
		}else{
			//环球
			dockCod="03409000";
		}
	

		String jpql1 =" SELECT a.BILL_NO,A.SHIP_NO SNO, A.I_E_ID IED, A.TRADE_ID TID, A.CAR_TYP CTP,T.CAR_TYP_NAM, "
		+ "a.CY_AREA_NO, A.CAR_KIND, K.CAR_KIND_NAM,A.BRAND_COD,B.BRAND_NAM,"+
		" count(a.port_Car_No) cnt "+
		" ,S.C_SHIP_NAM , y.CY_AREA_NAM ,to_char(a.IN_CY_TIM,'yyyy-mm-dd hh24:mi') IN_CY_TIM,case when A.I_E_ID='I' then S.IVOYAGE else S.EVOYAGE end VOYAGE "+
		" FROM PORT_CAR A , C_CAR_KIND K , C_BRAND B, C_CAR_TYP T, SHIP S,C_CY_AREA Y "+
		" where A.CAR_KIND=K.CAR_KIND(+) "+
		" and A.BRAND_COD=B.BRAND_COD(+) "+
		" and A.CAR_TYP=T.CAR_TYP(+) "+
		" and A.SHIP_NO=S.SHIP_NO(+) and a.CY_AREA_NO= y.CY_AREA_NO"+
		" and A.CURRENT_STAT = '2'   ";
		

		if (HdUtils.strNotNull(shipNo)) {
			jpql1 += "and a.SHIP_NO ='"+shipNo+"'";
		}
		
		if (HdUtils.strNotNull(cyAreaNo)) {
			jpql1 += "and  a.CY_AREA_NO ='"+cyAreaNo+"'  ";
		}
		if (HdUtils.strNotNull(companyCod)) {
			jpql1 += "and a.DOCK_COD='"+dockCod+"'";
		}
		
		if (HdUtils.strNotNull(billNo)) {
			jpql1 += "and a.BILL_NO like '%"+billNo+"%' ";
		}
		if (HdUtils.strNotNull(iEId)) {
			jpql1 += "and a.I_E_ID ='"+iEId +"'";
		}
		if (HdUtils.strNotNull(tradeId)) {
			jpql1 += "and a.TRADE_ID ='"+tradeId +"'";
		}
		if (HdUtils.strNotNull(brandCod)) {
			jpql1 += "and a.BRAND_COD ='"+brandCod +"'";
		}
		if (HdUtils.strNotNull(carKind)) {
			jpql1 += "and a.CAR_KIND ='"+carKind +"'";
		}
		if (HdUtils.strNotNull(carTyp)) {
			jpql1 += "and a.CAR_TYP ='"+carTyp +"'";
		}
		if(HdUtils.strNotNull(timeLimit))
		{
			jpql1+=" AND  a.IN_CY_TIM > =to_date('"+ CommonUtil.getDateTimeStr() +"','yyyy-mm-dd hh24:mi') -7";
		}
		if (HdUtils.strNotNull(incyTime)) {
			jpql1 += "and a.IN_CY_TIM >=to_date('"+incyTime +"','yyyy-mm-dd hh24:mi')";
		}
		jpql1+=" group by a.BILL_NO, A.SHIP_NO ,a.CY_AREA_NO, A.I_E_ID , A.TRADE_ID , A.CAR_TYP "+
				" ,A.CAR_KIND, K.CAR_KIND_NAM,A.BRAND_COD,B.BRAND_NAM,T.CAR_TYP_NAM,S.C_SHIP_NAM,y.CY_AREA_NAM, to_char(a.IN_CY_TIM,'yyyy-mm-dd hh24:mi'),case when A.I_E_ID='I' then S.IVOYAGE else S.EVOYAGE end ";

		List<Map>  allList = JpaUtils.getEntityManager().createNativeQuery(jpql1).setHint(QueryHints.RESULT_TYPE, ResultType.Map).getResultList();
		HdEzuiDatagridData result1 = new HdEzuiDatagridData();
		result1.setRows(allList);
		return result1;
	}


	@Override
	public HdEzuiDatagridData findDccl(HdQuery hdQuery) {
		// TODO Auto-generated method stub
		String jpql = "select a from CCyArea a where 1=1 ";
		String cyAreaNo = hdQuery.getStr("cyAreaNo");
		String shipNo = hdQuery.getStr("shipNo");
		String billNo = hdQuery.getStr("billNo");
		String type = hdQuery.getStr("type");
		QueryParamLs paramLs = new QueryParamLs();
		if (HdUtils.strNotNull(cyAreaNo)) {
			jpql += "and a.cyAreaNo =:cyAreaNo ";
			paramLs.addParam("cyAreaNo", cyAreaNo);
		}
		if (HdUtils.strIsNull(shipNo)) {
			jpql += "and a.cyAreaNo =:cyAreaNo ";
			paramLs.addParam("cyAreaNo", "123456789##");
		}
		jpql += "order by a.recTim desc";
		// HdEzuiDatagridData result = JpaUtils.findByEz(jpql, paramLs,
		// hdQuery);
		List<CCyArea> cCyAreaList = JpaUtils.findAll(jpql, paramLs);
		List<PortCar> allList = new ArrayList();
		if ("NMPLZC".equals(type) || "CZZC".equals(type)) {
			for (CCyArea cCyArea : cCyAreaList) {
				String jpql1 = "select a.shipNo sno, a.iEId ied, a.tradeId tid, a.carTyp ctp, count(a.portCarNo) cnt, a.tranPortCod tpc from PortCar a where a.currentStat = '2' and a.cyAreaNo =:cyAreaNo ";
				QueryParamLs paramLs1 = new QueryParamLs();
				paramLs1.addParam("cyAreaNo", cCyArea.getCyAreaNo());
				if (HdUtils.strNotNull(shipNo)) {
					jpql1 += "and a.shipNo =:shipNo ";
					paramLs1.addParam("shipNo", shipNo);
				}
				if (HdUtils.strNotNull(billNo)) {
					jpql1 += "and a.billNo =:billNo ";
					paramLs1.addParam("billNo", billNo);
				}
				jpql1 += "group by a.shipNo,a.iEId,a.tradeId,a.carTyp,a.tranPortCod";
				List<Object[]> objList = JpaUtils.findAll(jpql1, paramLs1);
				for (Object[] obj : objList) {
					String jqpl2 = "select a from PortCar a where a.currentStat = '2'";
					QueryParamLs paramLs2 = new QueryParamLs();
					if (obj[0] != null) {
						jqpl2 += " and a.shipNo =:shipNo";
						paramLs2.addParam("shipNo", obj[0]);
					}
					if (HdUtils.strNotNull(cCyArea.getCyAreaNo())) {
						jqpl2 += " and a.cyAreaNo =:cyAreaNo";
						paramLs2.addParam("cyAreaNo", cCyArea.getCyAreaNo());
					}
					if (obj[3] != null) {
						jqpl2 += " and a.carTyp =:carTyp";
						paramLs2.addParam("carTyp", obj[3]);
					}
					if (obj[5] != null) {
						jqpl2 += " and a.tranPortCod =:tranPortCod";
						paramLs2.addParam("tranPortCod", obj[5]);
					}
					if (HdUtils.strNotNull(type)) {
						if (type.startsWith("N")) {
							jqpl2 += " and a.iEId =:iEId and a.tradeId =:tradeId";
							paramLs2.addParam("iEId", "E");
							paramLs2.addParam("tradeId", "1");
						} else if (type.startsWith("W")) {
							jqpl2 += " and a.iEId =:iEId and a.tradeId =:tradeId";
							paramLs2.addParam("iEId", "E");
							paramLs2.addParam("tradeId", "2");
						} else if (type.startsWith("P")) {
							jqpl2 += " and a.iEId =:iEId and a.tradeId =:tradeId";
							paramLs2.addParam("iEId", "I");
							paramLs2.addParam("tradeId", "2");
						}
					} else {
						if (obj[1] != null) {
							jqpl2 += " and a.iEId =:iEId";
							paramLs2.addParam("iEId", obj[1]);
						}
						if (obj[2] != null) {
							jqpl2 += " and a.tradeId =:tradeId";
							paramLs2.addParam("tradeId", obj[2]);
						}
					}
					List<PortCar> portCarList = JpaUtils.findAll(jqpl2, paramLs2);
					if (portCarList.size() > 0) {
						PortCar portCar = portCarList.get(0);
						portCar.setRksl(String.valueOf(obj[4]));
						if (HdUtils.strNotNull(portCar.getCarKind())) {
							CCarKind carkind = JpaUtils.findById(CCarKind.class, portCar.getCarKind());
							if (carkind != null) {
								portCar.setCarKindNam(carkind.getCarKindNam());
							}
						}
						if (HdUtils.strNotNull(portCar.getBrandCod())) {
							CBrand cbrand = JpaUtils.findById(CBrand.class, portCar.getBrandCod());
							if (cbrand != null) {
								portCar.setBrandNam(cbrand.getBrandNam());
							}
						}
						if (HdUtils.strNotNull(portCar.getCarTyp())) {
							CCarTyp ccartyp = JpaUtils.findById(CCarTyp.class, portCar.getCarTyp());
							if (ccartyp != null) {
								portCar.setCarTypNam(ccartyp.getCarTypNam());
							}
						}
						if ("E".equals(portCar.getiEId())) {
							if (HdUtils.strNotNull(portCar.getShipNo())) {
								Ship bean = JpaUtils.findById(Ship.class, portCar.getShipNo());
								if (bean != null) {
									portCar.setcShipNam(bean.getcShipNam());
									portCar.setVoyage(bean.getEvoyage());
								}
							}
						} else if ("I".equals(portCar.getiEId())) {
							if (HdUtils.strNotNull(portCar.getShipNo())) {
								Ship ship = JpaUtils.findById(Ship.class, portCar.getShipNo());
								if (ship != null) {
									portCar.setcShipNam(ship.getcShipNam());
									portCar.setVoyage(ship.getIvoyage());
								}
							}
						}
						if (HdUtils.strNotNull(portCar.getCyAreaNo())) {
							CCyArea bean = JpaUtils.findById(CCyArea.class, portCar.getCyAreaNo());
							if (bean != null) {
								portCar.setCyArea(bean.getCyAreaNam());
							}
						}
						if (HdUtils.strNotNull(portCar.getCyAreaNo())) {
							CCyArea bean = JpaUtils.findById(CCyArea.class, portCar.getCyAreaNo());
							if (bean != null) {
								portCar.setCyArea(bean.getCyAreaNam());
							}
						}
						if (HdUtils.strNotNull(portCar.getTranPortCod())) {
							String sql = "select a from CPort a where a.portCod =:portCod";
							QueryParamLs queryCport = new QueryParamLs();
							queryCport.addParam("portCod", portCar.getTranPortCod());
							List<CPort> cPortList = JpaUtils.findAll(sql, queryCport);
							if (cPortList.size() > 0) {
								portCar.setTranPortNam(cPortList.get(0).getcPortNam());
							}
						}
						allList.add(portCar);
					}
				}
			}
		} else {
			for (CCyArea cCyArea : cCyAreaList) {
				String jpql1 = "select a.shipNo sno, a.iEId ied, a.tradeId tid, a.carTyp ctp, count(a.portCarNo) cnt from PortCar a where a.currentStat = '2' and a.cyAreaNo =:cyAreaNo ";
				QueryParamLs paramLs1 = new QueryParamLs();
				paramLs1.addParam("cyAreaNo", cCyArea.getCyAreaNo());
				if (HdUtils.strNotNull(shipNo)) {
					jpql1 += "and a.shipNo =:shipNo ";
					paramLs1.addParam("shipNo", shipNo);
				}
				if (HdUtils.strNotNull(billNo)) {
					jpql1 += "and a.billNo =:billNo ";
					paramLs1.addParam("billNo", billNo);
				}
				jpql1 += "group by a.shipNo,a.iEId,a.tradeId,a.carTyp";
				List<Object[]> objList = JpaUtils.findAll(jpql1, paramLs1);
				for (Object[] obj : objList) {
					String jqpl2 = "select a from PortCar a where a.currentStat = '2'";
					QueryParamLs paramLs2 = new QueryParamLs();
					if (obj[0] != null) {
						jqpl2 += " and a.shipNo =:shipNo";
						paramLs2.addParam("shipNo", obj[0]);
					}
					if (HdUtils.strNotNull(cCyArea.getCyAreaNo())) {
						jqpl2 += " and a.cyAreaNo =:cyAreaNo";
						paramLs2.addParam("cyAreaNo", cCyArea.getCyAreaNo());
					}
					if (obj[3] != null) {
						jqpl2 += " and a.carTyp =:carTyp";
						paramLs2.addParam("carTyp", obj[3]);
					}
					if (HdUtils.strNotNull(type)) {
						if (type.startsWith("N")) {
							jqpl2 += " and a.iEId =:iEId and a.tradeId =:tradeId";
							paramLs2.addParam("iEId", "E");
							paramLs2.addParam("tradeId", "1");
						} else if (type.startsWith("W")) {
							jqpl2 += " and a.iEId =:iEId and a.tradeId =:tradeId";
							paramLs2.addParam("iEId", "E");
							paramLs2.addParam("tradeId", "2");
						} else if (type.startsWith("P")) {
							jqpl2 += " and a.iEId =:iEId and a.tradeId =:tradeId";
							paramLs2.addParam("iEId", "I");
							paramLs2.addParam("tradeId", "2");
						}
					} else {
						if (obj[1] != null) {
							jqpl2 += " and a.iEId =:iEId";
							paramLs2.addParam("iEId", obj[1]);
						}
						if (obj[2] != null) {
							jqpl2 += " and a.tradeId =:tradeId";
							paramLs2.addParam("tradeId", obj[2]);
						}
					}
					List<PortCar> portCarList = JpaUtils.findAll(jqpl2, paramLs2);
					if (portCarList.size() > 0) {
						PortCar portCar = portCarList.get(0);
						portCar.setRksl(String.valueOf(obj[4]));
						if (HdUtils.strNotNull(portCar.getCarKind())) {
							CCarKind carkind = JpaUtils.findById(CCarKind.class, portCar.getCarKind());
							if (carkind != null) {
								portCar.setCarKindNam(carkind.getCarKindNam());
							}
						}
						if (HdUtils.strNotNull(portCar.getBrandCod())) {
							CBrand cbrand = JpaUtils.findById(CBrand.class, portCar.getBrandCod());
							if (cbrand != null) {
								portCar.setBrandNam(cbrand.getBrandNam());
							}
						}
						if (HdUtils.strNotNull(portCar.getCarTyp())) {
							CCarTyp ccartyp = JpaUtils.findById(CCarTyp.class, portCar.getCarTyp());
							if (ccartyp != null) {
								portCar.setCarTypNam(ccartyp.getCarTypNam());
							}
						}
						if ("E".equals(portCar.getiEId())) {
							if (HdUtils.strNotNull(portCar.getShipNo())) {
								Ship bean = JpaUtils.findById(Ship.class, portCar.getShipNo());
								if (bean != null) {
									portCar.setcShipNam(bean.getcShipNam());
									portCar.setVoyage(bean.getEvoyage());
								}
							}
						} else if ("I".equals(portCar.getiEId())) {
							if (HdUtils.strNotNull(portCar.getShipNo())) {
								Ship ship = JpaUtils.findById(Ship.class, portCar.getShipNo());
								if (ship != null) {
									portCar.setcShipNam(ship.getcShipNam());
									portCar.setVoyage(ship.getIvoyage());
								}
							}
						}
						if (HdUtils.strNotNull(portCar.getCyAreaNo())) {
							CCyArea bean = JpaUtils.findById(CCyArea.class, portCar.getCyAreaNo());
							if (bean != null) {
								portCar.setCyArea(bean.getCyAreaNam());
							}
						}
						allList.add(portCar);
					}
				}
			}
		}

		HdEzuiDatagridData result1 = new HdEzuiDatagridData();
		result1.setRows(allList);
		return result1;
	}
	
	//存栈装船批量理货
	@Override
	public HdEzuiDatagridData findCzzc(HdQuery hdQuery) {
		// TODO Auto-generated method stub
		String cyAreaNo = hdQuery.getStr("cyAreaNo");
		String shipNo = hdQuery.getStr("shipNo");
		String billNo = hdQuery.getStr("billNo");
		String carTyp = hdQuery.getStr("carTyp");
		String brandCod = hdQuery.getStr("brandCod");
		String flow = hdQuery.getStr("flow");
		String inCyTim = hdQuery.getStr("inCyTim");
		String type = hdQuery.getStr("type");

		if ("NMPLZC".equals(type) || "CZZC".equals(type) || "XCPZ".equals(type)) {
				String jpql1 = "select a from VPortCarSta a where 1=1 ";
				if (HdUtils.strNotNull(shipNo)) {
					jpql1 += " and a.shipNo ='" + shipNo +"'";
				}
				if (HdUtils.strNotNull(billNo)) {
					jpql1 += " and a.billNo ='" + billNo +"'";
				}
				if (HdUtils.strNotNull(cyAreaNo)) {
					jpql1 += " and a.cyAreaNo ='" + cyAreaNo +"'";
				}			
				if (HdUtils.strNotNull(carTyp)) {
					jpql1 += " and a.carTyp ='" + carTyp +"'";
				}
				if (HdUtils.strNotNull(brandCod)) {
					jpql1 += " and a.brandCod ='" + brandCod +"'";
				}
				if (HdUtils.strNotNull(flow)) {
					jpql1 += " and a.tranPortCod ='" + flow +"'";
				}
				if (HdUtils.strNotNull(inCyTim)) {
					jpql1 += " and a.inCyTim ='" + inCyTim +"'";
				}
				if ("XCPZ".equals(type)){
					jpql1 += " and a.tradeId ='1'"; 
				}
				jpql1 += "  ORDER BY a.inCyTim DESC";
				
				HdEzuiDatagridData result = JpaUtils.findByEz(jpql1, new QueryParamLs(), hdQuery);
				return result;
		}else {
			return null;
		}
	}

	@Override
	public HdEzuiDatagridData findSglh(HdQuery hdQuery) {
		// TODO Auto-generated method stub
		String shipNo = hdQuery.getStr("shipNo");
		String brandCod = hdQuery.getStr("brandCod");
		String factoryCod = hdQuery.getStr("factoryCod");
		String jpql = "select a from CCyArea a where 1=1 ";
		QueryParamLs paramLs = new QueryParamLs();
		if (HdUtils.strIsNull(shipNo) && HdUtils.strIsNull(brandCod)) {
			jpql += "and a.cyAreaNo =:cyAreaNo ";
			paramLs.addParam("cyAreaNo", "123456789##"); // 委托信息为空不刷新
		}
		jpql += "order by a.recTim desc";
		List<CCyArea> cCyAreaList = JpaUtils.findAll(jpql, paramLs);
		List<PortCar> allList = new ArrayList();
		if (HdUtils.strNotNull(factoryCod)) {
			String sql = "select a from CBrand a where a.factoryCod =:factoryCod";
			QueryParamLs param = new QueryParamLs();
			param.addParam("factoryCod", factoryCod);
			List<CBrand> cBrandList = JpaUtils.findAll(sql, param);
			String str = "";
			for (CBrand bean : cBrandList) {
				str += "'" + bean.getBrandCod() + "'" + ",";
			}
			for (CCyArea cCyArea : cCyAreaList) {
				String jpql1 = "select a.carTyp ctp, count(a.portCarNo) cnt from PortCar a "
						+ "where a.currentStat = '2' and a.tradeId = '1' and a.iEId = 'I' and a.cyAreaNo =:cyAreaNo and a.brandCod in ("
						+ (str.length()==0?"''":str.substring(0, str.length() - 1)) + ") ";
				
				QueryParamLs paramLs1 = new QueryParamLs();
				paramLs1.addParam("cyAreaNo", cCyArea.getCyAreaNo());
				if (HdUtils.strNotNull(shipNo)) {
					jpql1 += "and a.shipNo =:shipNo ";
					paramLs1.addParam("shipNo", shipNo);
				}
				jpql1 += "group by a.carTyp";
				List<Object[]> objList = JpaUtils.findAll(jpql1, paramLs1);
				for (Object[] obj : objList) {
					String jqpl2 = "select a from PortCar a where a.currentStat = '2' and a.tradeId = '1' and a.iEId = 'I' and a.cyAreaNo =:cyAreaNo ";
					QueryParamLs paramLs2 = new QueryParamLs();
					paramLs2.addParam("cyAreaNo", cCyArea.getCyAreaNo());
					if (HdUtils.strNotNull(shipNo)) {
						jqpl2 += "and a.shipNo =:shipNo ";
						paramLs2.addParam("shipNo", shipNo);
					}
					if (HdUtils.strNotNull(brandCod)) {
						jqpl2 += "and a.brandCod =:brandCod ";
						paramLs2.addParam("brandCod", brandCod);
					}
					if (obj[0] != null) {
						jqpl2 += " and a.carTyp =:carTyp";
						paramLs2.addParam("carTyp", obj[0]);
					}
					List<PortCar> portCarList = JpaUtils.findAll(jqpl2, paramLs2);
					if (portCarList.size() > 0) {
						PortCar portCar = portCarList.get(0);
						portCar.setRksl(String.valueOf(obj[1]));
						if (HdUtils.strNotNull(portCar.getCarKind())) {
							CCarKind carkind = JpaUtils.findById(CCarKind.class, portCar.getCarKind());
							if (carkind != null) {
								portCar.setCarKindNam(carkind.getCarKindNam());
							}
						}
						if (HdUtils.strNotNull(portCar.getBrandCod())) {
							CBrand cbrand = JpaUtils.findById(CBrand.class, portCar.getBrandCod());
							if (cbrand != null) {
								portCar.setBrandNam(cbrand.getBrandNam());
							}
						}
						if (HdUtils.strNotNull(portCar.getCarTyp())) {
							CCarTyp ccartyp = JpaUtils.findById(CCarTyp.class, portCar.getCarTyp());
							if (ccartyp != null) {
								portCar.setCarTypNam(ccartyp.getCarTypNam());
							}
						}
						// if (HdUtils.strIsNull(shipNo)){
						// portCar.setShipNo("");
						// portCar.setcShipNam("");
						// portCar.setVoyage("");
						// }else{
						if ("E".equals(portCar.getiEId())) {
							if (HdUtils.strNotNull(portCar.getContractNo())) {
								ContractIeDoc contractIeDoc = JpaUtils.findById(ContractIeDoc.class,
										portCar.getContractNo());
								if (contractIeDoc != null) {
									portCar.setcShipNam(contractIeDoc.getShipNam());
									portCar.setVoyage(contractIeDoc.getVoyage().substring(
											contractIeDoc.getVoyage().indexOf("/") + 1,
											contractIeDoc.getVoyage().length()));
								}
							}
						} else if ("I".equals(portCar.getiEId())) {
							if (HdUtils.strNotNull(portCar.getShipNo())) {
								Ship ship = JpaUtils.findById(Ship.class, portCar.getShipNo());
								if (ship != null) {
									portCar.setcShipNam(ship.getcShipNam());
									portCar.setVoyage(ship.getIvoyage());
								}
							}
						}
						// }

						if (HdUtils.strNotNull(portCar.getCyAreaNo())) {
							CCyArea bean = JpaUtils.findById(CCyArea.class, portCar.getCyAreaNo());
							if (bean != null) {
								portCar.setCyArea(bean.getCyAreaNam());
							}
						}
						allList.add(portCar);
					}
				}
			}
		} else {
			for (CCyArea cCyArea : cCyAreaList) {
				String jpql1 = "select a.carTyp ctp, count(a.portCarNo) cnt from PortCar a where a.currentStat = '2' and a.tradeId = '1' and a.iEId = 'I' and a.cyAreaNo =:cyAreaNo ";
				QueryParamLs paramLs1 = new QueryParamLs();
				paramLs1.addParam("cyAreaNo", cCyArea.getCyAreaNo());
				if (HdUtils.strNotNull(shipNo)) {
					jpql1 += "and a.shipNo =:shipNo ";
					paramLs1.addParam("shipNo", shipNo);
				}
				if (HdUtils.strNotNull(brandCod)) {
					jpql1 += "and a.brandCod =:brandCod ";
					paramLs1.addParam("brandCod", brandCod);
				}
				jpql1 += "group by a.carTyp";
				List<Object[]> objList = JpaUtils.findAll(jpql1, paramLs1);
				for (Object[] obj : objList) {
					String jqpl2 = "select a from PortCar a where a.currentStat = '2' and a.cyAreaNo =:cyAreaNo ";
					QueryParamLs paramLs2 = new QueryParamLs();
					paramLs2.addParam("cyAreaNo", cCyArea.getCyAreaNo());
					if (HdUtils.strNotNull(shipNo)) {
						jqpl2 += "and a.shipNo =:shipNo ";
						paramLs2.addParam("shipNo", shipNo);
					}
					if (HdUtils.strNotNull(brandCod)) {
						jqpl2 += "and a.brandCod =:brandCod ";
						paramLs2.addParam("brandCod", brandCod);
					}
					if (obj[0] != null) {
						jqpl2 += " and a.carTyp =:carTyp";
						paramLs2.addParam("carTyp", obj[0]);
					}
					List<PortCar> portCarList = JpaUtils.findAll(jqpl2, paramLs2);
					if (portCarList.size() > 0) {
						PortCar portCar = portCarList.get(0);
						portCar.setRksl(String.valueOf(obj[1]));
						if (HdUtils.strNotNull(portCar.getCarKind())) {
							CCarKind carkind = JpaUtils.findById(CCarKind.class, portCar.getCarKind());
							if (carkind != null) {
								portCar.setCarKindNam(carkind.getCarKindNam());
							}
						}
						if (HdUtils.strNotNull(portCar.getBrandCod())) {
							CBrand cbrand = JpaUtils.findById(CBrand.class, portCar.getBrandCod());
							if (cbrand != null) {
								portCar.setBrandNam(cbrand.getBrandNam());
							}
						}
						if (HdUtils.strNotNull(portCar.getCarTyp())) {
							CCarTyp ccartyp = JpaUtils.findById(CCarTyp.class, portCar.getCarTyp());
							if (ccartyp != null) {
								portCar.setCarTypNam(ccartyp.getCarTypNam());
							}
						}
						// if (HdUtils.strIsNull(shipNo)){
						// portCar.setShipNo("");
						// portCar.setcShipNam("");
						// portCar.setVoyage("");
						// }else{
						if ("E".equals(portCar.getiEId())) {
							if (HdUtils.strNotNull(portCar.getContractNo())) {
								ContractIeDoc contractIeDoc = JpaUtils.findById(ContractIeDoc.class,
										portCar.getContractNo());
								if (contractIeDoc != null) {
									portCar.setcShipNam(contractIeDoc.getShipNam());
									portCar.setVoyage(contractIeDoc.getVoyage().substring(
											contractIeDoc.getVoyage().indexOf("/") + 1,
											contractIeDoc.getVoyage().length()));
								}
							}
						} else if ("I".equals(portCar.getiEId())) {
							if (HdUtils.strNotNull(portCar.getShipNo())) {
								Ship ship = JpaUtils.findById(Ship.class, portCar.getShipNo());
								if (ship != null) {
									portCar.setcShipNam(ship.getcShipNam());
									portCar.setVoyage(ship.getIvoyage());
								}
							}
						}
						// }

						if (HdUtils.strNotNull(portCar.getCyAreaNo())) {
							CCyArea bean = JpaUtils.findById(CCyArea.class, portCar.getCyAreaNo());
							if (bean != null) {
								portCar.setCyArea(bean.getCyAreaNam());
							}
						}
						allList.add(portCar);
					}
				}
			}
		}

		HdEzuiDatagridData result1 = new HdEzuiDatagridData();
		result1.setRows(allList);
		return result1;
	}

	@Override
	public HdMessageCode save(HdEzuiSaveDatagridData<CCyArea> hdEzuiSaveDatagridData) {
		// TODO Auto-generated method stub
		return JpaUtils.save(hdEzuiSaveDatagridData);
	}

	@Override
	public HdMessageCode saveone(@RequestBody CCyArea cCyArea) {
		// TODO Auto-generated method stub
		String areaCod = cCyArea.getCyAreaNo();
		CCyArea carea = JpaUtils.findById(CCyArea.class, areaCod);
		if (carea != null) { 
			JpaUtils.update(cCyArea);
		} else {
			JpaUtils.save(cCyArea);
		}
		return HdUtils.genMsg();
	}
	
	@Override
	public CCyArea findCCyArea(String cyAreaNo){
		CCyArea bean = JpaUtils.findById(CCyArea.class, cyAreaNo);
		if (bean == null){
			throw new HdRunTimeException("数据为空！");
		}
		return bean;
	}

	@Override
	public CCyArea findone(String cyAreaNo) {
		CCyArea cCyArea = JpaUtils.findById(CCyArea.class, cyAreaNo);
		if (cCyArea != null) {
			Long unlockedNum = (long) 0;
			String jpql2 = "select a.cyRowNo from CCyRow a where a.cyAreaNo=:cyareano";
			QueryParamLs paramLs2 = new QueryParamLs();
			paramLs2.addParam("cyareano", cCyArea.getCyAreaNo());
			List<String> cyRowNoList = JpaUtils.findAll(jpql2, paramLs2);
			if (cyRowNoList.size() > 0) {
				for (int i = 0; i < cyRowNoList.size(); i++) {
					String jpql3 = "select count(a.cyBayNo) from CCyBay a where a.cyAreaNo=:cyareano and a.cyRowNo=:cyrowno and a.lockId = '0'";
					QueryParamLs paramLs3 = new QueryParamLs();
					paramLs3.addParam("cyareano", cCyArea.getCyAreaNo());
					paramLs3.addParam("cyrowno", cyRowNoList.get(i));
					List<Long> unlokcedNumList = JpaUtils.findAll(jpql3, paramLs3);
					if (unlokcedNumList.size() > 0) {
						unlockedNum += unlokcedNumList.get(0);
					}
				}
			}
			cCyArea.setUnlockNum(unlockedNum);
		}
		return cCyArea;
	}

	@Override
	public HdEzuiDatagridData findcdch(HdQuery hdQuery) {
		String jpq = "select distinct a from CCyArea a ,CCyRow b,CCyBay c where 1=1 "
				+ "and a.cyAreaNo = b.cyAreaNo and b.cyRowNo = c.cyRowNo " + "and c.lockId='1'";
		String cyAreaNo = hdQuery.getStr("cyAreaNo");
		String cyTyp = hdQuery.getStr("cyTyp");
		QueryParamLs paramL = new QueryParamLs();
		if (HdUtils.strNotNull(cyAreaNo)) {
			jpq += "and a.cyAreaNo =:cyAreaNo ";
			paramL.addParam("cyAreaNo", cyAreaNo);
		}
		if (HdUtils.strNotNull(cyTyp)) {
			jpq += "and a.cyTyp =:cyTyp ";
			paramL.addParam("cyTyp", cyTyp);
		}
		jpq += "order by a.cyAreaNo desc";
		HdEzuiDatagridData result = JpaUtils.findByEz(jpq, paramL, hdQuery);
		List<CCyArea> ccyAreaList = result.getRows();
		if (ccyAreaList.size() > 0) {
			for (CCyArea ccy : ccyAreaList) {
				CDock cd = JpaUtils.findById(CDock.class, ccy.getDockCod());
				Long unlockedNum = (long) 0;
				ccy.setDockNam(cd.getDockNam());
				String jpql2 = "select a.cyRowNo from CCyRow a where a.cyAreaNo=:cyareano";
				QueryParamLs paramLs2 = new QueryParamLs();
				paramLs2.addParam("cyareano", ccy.getCyAreaNo());
				List<String> cyRowNoList = JpaUtils.findAll(jpql2, paramLs2);
				if (cyRowNoList.size() > 0) {
					for (int i = 0; i < cyRowNoList.size(); i++) {
						String jpql3 = "select count(a.cyBayNo) from CCyBay a where a.cyAreaNo=:cyareano and a.cyRowNo=:cyrowno ";
						QueryParamLs paramLs3 = new QueryParamLs();
						paramLs3.addParam("cyareano", ccy.getCyAreaNo());
						paramLs3.addParam("cyrowno", cyRowNoList.get(i));
						List<Long> unlokcedNumList = JpaUtils.findAll(jpql3, paramLs3);
						if (unlokcedNumList.size() > 0) {
							unlockedNum += unlokcedNumList.get(0);
						}
					}
				}
				ccy.setUnlockNum(unlockedNum);
			}
		}
		return result;
	}

	@Override
	public void sendDataJT() {
		String portCarSQL = 
				"select t.ship_no,\n" +
						"       t.i_e_id,\n" + 
						"       t.trade_id,\n" + 
						"       t.car_typ,\n" + 
						"       t.bill_no,\n" + 
						"       t.brand_cod,\n" + 
						"       t.cy_area_no,\n" + 
						"       t.marks,\n" + 
						"       nvl(count(t.port_car_no),'0') as carNum\n" + 
						"  from port_car t\n" + 
						" where t.current_stat = '2'\n" + 
						" group by t.ship_no,\n" + 
						"          t.i_e_id,\n" + 
						"          t.trade_id,\n" + 
						"          t.car_typ,\n" + 
						"          t.bill_no,\n" + 
						"          t.brand_cod,\n" + 
						"          t.marks,\n" + 
						"          t.cy_area_no";
//		" and t.ship_no = '20190129133002'" +
		List<Map<String,String>> portCarLists=JpaUtils.getEntityManager().createNativeQuery(portCarSQL).setHint(QueryHints.RESULT_TYPE, ResultType.Map).getResultList();
		System.out.println(portCarLists);
		for(Map<String,String> portCar : portCarLists) {
			if(HdUtils.strIsNull(portCar.get("CY_AREA_NO"))) {
				continue;
			}
			if(HdUtils.strIsNull(portCar.get("BILL_NO"))) {
				continue;
			}
			if(HdUtils.strIsNull(portCar.get("SHIP_NO"))) {
				continue;
			}
			sendDataToJt(portCar);
		}
		
//		" and t.ship_no = '20181213132828'\n" + 
//		String jpql = "select a from CCyArea a where 1=1 ";
//		QueryParamLs paramLs = new QueryParamLs();
//		jpql += "order by a.recTim desc";
//		// HdEzuiDatagridData result = JpaUtils.findByEz(jpql, paramLs,
//		// hdQuery);
//		List<CCyArea> cCyAreaList = JpaUtils.findAll(jpql, paramLs);
//		List<PortCar> allList = new ArrayList();
//		for (CCyArea cCyArea : cCyAreaList) {
//			String jpql1 = "select a.shipNo sno, a.iEId ied, a.tradeId tid, a.carTyp ctp, count(a.portCarNo) cnt from PortCar a where a.currentStat = '2' and a.cyAreaNo =:cyAreaNo ";
//			QueryParamLs paramLs1 = new QueryParamLs();
//			paramLs1.addParam("cyAreaNo", cCyArea.getCyAreaNo());
//			jpql1 += "group by a.shipNo,a.iEId,a.tradeId,a.carTyp";
//			List<Object[]> objList = JpaUtils.findAll(jpql1, paramLs1);
//			for (Object[] obj : objList) {
//				String jqpl2 = "select a from PortCar a where 1=1 and a.shipNo is not null";
//				QueryParamLs paramLs2 = new QueryParamLs();
//				if (obj[0] != null) {
//					jqpl2 += " and a.shipNo =:shipNo";
//					paramLs2.addParam("shipNo", obj[0]);
//				}
//				if (HdUtils.strNotNull(cCyArea.getCyAreaNo())) {
//					jqpl2 += " and a.cyAreaNo =:cyAreaNo";
//					paramLs2.addParam("cyAreaNo", cCyArea.getCyAreaNo());
//				}
//				if (obj[3] != null) {
//					jqpl2 += " and a.carTyp =:carTyp";
//					paramLs2.addParam("carTyp", obj[3]);
//				}
//				if (obj[1] != null) {
//					jqpl2 += " and a.iEId =:iEId";
//					paramLs2.addParam("iEId", obj[1]);
//				}
//				if (obj[2] != null) {
//					jqpl2 += " and a.tradeId =:tradeId";
//					paramLs2.addParam("tradeId", obj[2]);
//				}
//				List<PortCar> portCarList = JpaUtils.findAll(jqpl2, paramLs2);
//				if (portCarList.size() > 0) {
//					PortCar portCar = portCarList.get(0);
//					portCar.setRksl(String.valueOf(obj[4]));
//					if (HdUtils.strNotNull(portCar.getCarKind())) {
//						CCarKind carkind = JpaUtils.findById(CCarKind.class, portCar.getCarKind());
//						if (carkind != null) {
//							portCar.setCarKindNam(carkind.getCarKindNam());
//						}
//					}
//					if (HdUtils.strNotNull(portCar.getBrandCod())) {
//						CBrand cbrand = JpaUtils.findById(CBrand.class, portCar.getBrandCod());
//						if (cbrand != null) {
//							portCar.setBrandNam(cbrand.getBrandNam());
//						}
//					}
//					if (HdUtils.strNotNull(portCar.getCarTyp())) {
//						CCarTyp ccartyp = JpaUtils.findById(CCarTyp.class, portCar.getCarTyp());
//						if (ccartyp != null) {
//							portCar.setCarTypNam(ccartyp.getCarTypNam());
//						}
//					}
//					if ("E".equals(portCar.getiEId())) {
//						if (HdUtils.strNotNull(portCar.getShipNo())) {
//							Ship bean = JpaUtils.findById(Ship.class, portCar.getShipNo());
//							if (bean != null) {
//								portCar.setcShipNam(bean.getcShipNam());
//								portCar.setVoyage(bean.getEvoyage());
//							}
//						}
//					} else if ("I".equals(portCar.getiEId())) {
//						if (HdUtils.strNotNull(portCar.getShipNo())) {
//							Ship ship = JpaUtils.findById(Ship.class, portCar.getShipNo());
//							if (ship != null) {
//								portCar.setcShipNam(ship.getcShipNam());
//								portCar.setVoyage(ship.getIvoyage());
//							}
//						}
//					}
//					if (HdUtils.strNotNull(portCar.getCyAreaNo())) {
//						CCyArea bean = JpaUtils.findById(CCyArea.class, portCar.getCyAreaNo());
//						if (bean != null) {
//							portCar.setCyArea(bean.getCyAreaNam());
//						}
//					}
//					allList.add(portCar);
//					sendDataToJt(portCar);
//				}
//			}
//		}
	}

	private void sendDataToJt(Map<String,String> portCar) {
		PortCarInter portCarInter = new PortCarInter();
		portCarInter.setStoreId(HdUtils.genUuid());
		String jpqla = "select a from ShipBill a where a.billNo=:billNo ";
		QueryParamLs paramLsa = new QueryParamLs();
		paramLsa.addParam("billNo", portCar.get("BILL_NO"));
		List<ShipBill> sbL = JpaUtils.findAll(jpqla, paramLsa);
		if (sbL.size() > 0) {
			portCarInter.setCargoId(sbL.get(0).getShipbillId());
		} else {
			portCarInter.setCargoId("*");
		}
		Ship ship = JpaUtils.findById(Ship.class, portCar.get("SHIP_NO"));

		if ("I".equals(portCar.get("I_E_ID"))) {
			portCarInter.setShipId(ship.getNewIShipId());
			portCarInter.setSvoyageId(ship.getNewGroupShipNo());
			portCarInter.setShipName(ship.getcShipNam());
			portCarInter.setVoyage(ship.getIvoyage());
			portCarInter.setIeFlag("I");
		} else if ("E".equals(portCar.get("I_E_ID"))) {
			portCarInter.setShipId(ship.getNewEShipId());
			portCarInter.setSvoyageId(ship.getNewGroupShipNo());
			portCarInter.setShipName(ship.getcShipNam());
			portCarInter.setVoyage(ship.getEvoyage());
			portCarInter.setIeFlag("E");
		}
		portCarInter.setTradeFlag(ship.getTradeId());
		portCarInter.setCargoMark(portCar.get("MARKS"));
		portCarInter.setPackageCode("");
		portCarInter.setFormat("");
//		portCarInter.setOriginCode("");
		portCarInter.setMataCode("");
		CCarTyp data = JpaUtils.findById(CCarTyp.class, portCar.get("CAR_TYP"));
		if (data != null) {
			String brandCod = data.getBrandCod();
			if (HdUtils.strNotNull(brandCod)) {
				String jpqlx = "select a from CBrandDetail a where a.brandCod =:brandCod";
				QueryParamLs paramx = new QueryParamLs();
				paramx.addParam("brandCod", brandCod);
				List<CBrandDetail> cBrandDetailList = JpaUtils.findAll(jpqlx, paramx);
				if (cBrandDetailList.size() > 0) {
					CBrandDetail bean = cBrandDetailList.get(0);
					if (HdUtils.strNotNull(bean.getOriginCode())) {
						portCarInter.setOriginCode(bean.getOriginCode());// 产地
					} else {
						portCarInter.setOriginCode("");
					}
					if (HdUtils.strNotNull(bean.getAgentCod())) {
						portCarInter.setForwarderCode(bean.getAgentCod());// 货代
					} else {
						portCarInter.setForwarderCode("");// 货代
					}

					if (HdUtils.strNotNull(bean.getClientCod())) {
						portCarInter.setConsignorCode(bean.getClientCod());// 货主
					} else {
						portCarInter.setConsignorCode("");// 货主
					}

					if (HdUtils.strNotNull(bean.getConsignCod())) {
						portCarInter.setCnorCode(bean.getConsignCod());// 发货人
					} else {
						portCarInter.setCnorCode("");// 发货人
					}

					if (HdUtils.strNotNull(bean.getReceiveCod())) {
						portCarInter.setCneeCode(bean.getReceiveCod());// 发货人
					} else {
						portCarInter.setCneeCode("");// 发货人
					}
				}
			}
		}
//		portCarInter.setForwarderCode("");
		portCarInter.setConsignCode("");
//		portCarInter.setConsignorCode("");
//		portCarInter.setCneeCode("");
//		portCarInter.setCnorCode("");
		// 集团汽车代码
//		portCarInter.setCargoCode("124202");
		
		String carType = portCar.get("CAR_TYP");
		if(HdUtils.strNotNull(carType)) {
			CCarTyp cCartyp = JpaUtils.findById(CCarTyp.class, carType);
			if(cCartyp != null) {
				if(HdUtils.strNotNull(cCartyp.getCarKind())) {
					CCarKind cCarKindEntity = JpaUtils.findById(CCarKind.class, cCartyp.getCarKind());
					CargoDataSpecification cargoDataSpecificationEntity = JpaUtils.findById(CargoDataSpecification.class, cCarKindEntity.getGroupCarKind());
					portCarInter.setCargoCode(cargoDataSpecificationEntity.getxFourthCode());
				} else {
					portCarInter.setCargoCode("*");
				}
			} else {
				portCarInter.setCargoCode("*");
			}
			
		} else {
			portCarInter.setCargoCode("*");
		}
		
		//重量
		if(HdUtils.strNotNull(carType)) {
//			CCarTyp cCarTyp = JpaUtils.findById(CCarTyp.class, carType);
			String cCarTypJpql = "SELECT nvl(c.weights,1) as weight FROM C_Car_Typ c where c.car_Typ =" + carType;
			List<Map<String,BigDecimal>> list=JpaUtils.getEntityManager().createNativeQuery(cCarTypJpql).setHint(QueryHints.RESULT_TYPE, ResultType.Map).getResultList();
			BigDecimal cCarTyp = list.get(0).get("WEIGHT");

			if(cCarTyp != null) {
				Object obj = portCar.get("CARNUM");
				String carNum = ObjectUtils.toString(obj);
				if(HdUtils.strNotNull(carNum.trim())) {
					int itemCode = Integer.valueOf(obj.toString());
					BigDecimal wgt = cCarTyp.multiply(new BigDecimal(itemCode));
					portCarInter.setCargoWgt(wgt.toString());
				}
			} else {
				portCarInter.setCargoWgt("0");
			}
		} else {
			portCarInter.setCargoWgt("0");
		}
		
//		portCarInter.setCargoWgt("0");
		
		portCarInter.setBillNo(portCar.get("BILL_NO"));
//		portCarInter.setBrandCode(portCar.get("BRAND_COD"));
		
		String brandCode = portCar.get("BRAND_COD");
		if(HdUtils.strNotNull(brandCode)) {
			CBrand brandEntity = JpaUtils.findById(CBrand.class, brandCode);
			if(brandEntity != null) {
				String brandJpql = "SELECT v FROM VGroupCorpBrand v where v.brandName like :brandNam";
				QueryParamLs brandParams = new QueryParamLs();
				brandParams.addParam("brandNam", '%'+brandEntity.getBrandNam()+'%');
				List<VGroupCorpBrand> brandCodeList = JpaUtils.findAll(brandJpql, brandParams);
				if(brandCodeList.size() > 0) {
					portCarInter.setBrandCode(brandCodeList.get(0).getBrandCode());
				}
			}
		}
		
		portCarInter.setPiecesNo("");
		
		
		
//		if (portCar.getPieces() != null) {
//			portCarInter.setCargoNum(portCar.getPieces().toString());
//		} else {
//			portCarInter.setCargoNum("");
//		}
//		if (portCar.getWeights() != null) {
//			portCarInter.setCargoWgt(portCar.getWeights().toString());
//		} else {
//			portCarInter.setCargoWgt("");
//		}
//		if (portCarInter.getCargoVol() != null) {
//			portCarInter.setCargoVol(portCar.getVolumes().toString());
//		} else {
//			portCarInter.setCargoVol("");
//		}
		
		String yardCode = "SELECT v FROM VGroupCorpYard v where v.yardCode =:yardCode";
		QueryParamLs yardCodeParams = new QueryParamLs();
		yardCodeParams.addParam("yardCode", portCar.get("CY_AREA_NO"));
		List<VGroupCorpYard> yardCodeList = JpaUtils.findAll(yardCode, yardCodeParams);
		if(yardCodeList.size() > 0) {
			portCarInter.setYardCode(portCar.get("CY_AREA_NO"));
		}
		
		// portCarInter.setYardCode(portCar.getCyAreaNo().substring(2,4));
		portCarInter.setLocationCode("");
		portCarInter.setPileCode("");
		portCarInter.setSublocationCode("");
		portCarInter.setTeamOrgnId(Ship.GZ);
//		if (portCar.getDockCod().equals("03406500")) {
//			portCarInter.setTeamOrgnId("03406500");
//		} else if (portCar.getDockCod().equals("03409000")) {
//			portCarInter.setTeamOrgnId("03406500");
//			// portCarInter.setTeamOrgnId("03409000");
//		}
		portCarInter.setSubmitFlag("1");
		portCarInter.setSubmitName(HdUtils.getCurUser().getAccount());
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm");
		portCarInter.setSubmitTime(format.format(HdUtils.getDateTime()));
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("cmdId", "2021");
		jsonObj.put("coId", Ship.GZ);
//		if ("03406500".equals(ship.getDockCod())) {
//			jsonObj.put("coId", "03406500");
//		}
//		if ("03409000".equals(ship.getDockCod())) {
//			jsonObj.put("coId", "03406500");
//			// jsonObj.put("coId","03409000");
//		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("storeId", portCarInter.getStoreId());
//		map.put("cargoId", portCarInter.getCargoId());
		map.put("cargoId", "*");
		map.put("shipId", portCarInter.getShipId());
		map.put("svoyageId", portCarInter.getSvoyageId());
		map.put("shipName", portCarInter.getShipName());
		map.put("voyage", portCarInter.getVoyage());
		map.put("ieFlag", portCarInter.getIeFlag());
		map.put("cargoMark", portCarInter.getSvoyageId());
		map.put("packageCode", portCarInter.getPackageCode());
		map.put("format", portCarInter.getFormat());
		if (portCarInter.getTradeFlag().equals("1")) {
			map.put("tradeFlag", "2");
		} else if (portCarInter.getTradeFlag().equals("2")) {
			map.put("tradeFlag", "1");
		}

		map.put("originCode", portCarInter.getOriginCode());
		map.put("matacode", portCarInter.getMataCode());
		
		
//		map.put("forwarderCode ", portCarInter.getForwarderCode());
//		map.put("consignorCode", portCarInter.getConsignorCode());
//		map.put("consignCode", portCarInter.getConsignCode());
//		map.put("cnorCode", portCarInter.getCnorCode());
//		map.put("cneeCode", portCarInter.getCneeCode());
		
		map.put("forwarderCode", "000434");
		map.put("consignorCode", "000061");
//		map.put("consignCode", "000434");
		map.put("cnorCode", "000061");
		map.put("cneeCode", "000061");
		
		map.put("cargoCode", portCarInter.getCargoCode());
		map.put("cargoMark", portCarInter.getCargoMark());
		map.put("billNo", portCarInter.getBillNo());
		map.put("brandCode", portCarInter.getBrandCode());
		
		map.put("yardCode", portCarInter.getYardCode());
		map.put("locationCode", portCarInter.getLocationCode());
		map.put("sublocationCode", portCarInter.getSublocationCode());
		map.put("piecesNo", portCarInter.getPiecesNo());
//		map.put("cargoNum", portCarInter.getCargoNum());
		
		Object obj = portCar.get("CARNUM");
		int itemCode = Integer.valueOf(obj.toString());
		String carTyp = portCar.get("CAR_TYP");
		if(HdUtils.strNotNull(carTyp)) {
			CCarTyp cCartyp = JpaUtils.findById(CCarTyp.class, carTyp);
			if(HdUtils.strNotNull(cCartyp.getCarKind())) {
				if(cCartyp.getCarKind().equals("04")) {
					map.put("cargoNum", String.valueOf(itemCode));
				} else {
					map.put("truckNum", String.valueOf(itemCode));
				}
			} else {
				map.put("truckNum", String.valueOf(itemCode));
			}
		}
		
		map.put("cargoWgt", portCarInter.getCargoWgt());
		map.put("cargoVol", "100");
		map.put("pileCode", portCarInter.getPileCode());
		map.put("teamOrgnId", portCarInter.getTeamOrgnId());
		map.put("submitFlag", portCarInter.getSubmitFlag());
		map.put("submitName", portCarInter.getSubmitName());
		map.put("submitTime", portCarInter.getSubmitTime());
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
					throw new HdRunTimeException("上报集团成功！");
				}
				if (!resCode.equals(resp.getResCode()) || !resMsg.equals(resp.getResMsg())) {
					throw new HdRunTimeException("上报集团失败！");
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
	public String findAreaNam(String cyAreaNo) {
		String areaNam = "";
		String jpql = "select a from CCyArea where areaNo = :cAreaNo ";

		QueryParamLs paramLs = new QueryParamLs();
		paramLs.addParam("cAreaNo", cyAreaNo);

		List<CCyArea> areaList = JpaUtils.findAll(jpql, paramLs);
		if(areaList.size()>0){
			CCyArea carea = areaList.get(0);
		    areaNam = carea.getCyAreaNam();
		}
		return areaNam;
	}

}
