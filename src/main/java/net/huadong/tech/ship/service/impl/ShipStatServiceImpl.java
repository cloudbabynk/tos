package net.huadong.tech.ship.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.huadong.tech.Interface.entity.VGroupCorpShip;
import net.huadong.tech.base.bean.EzTreeBean;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.bean.HdRunTimeException;
import net.huadong.tech.base.entity.CBerth;
import net.huadong.tech.base.entity.CClientCod;
import net.huadong.tech.base.entity.CCountry;
import net.huadong.tech.base.entity.CDamage;
import net.huadong.tech.base.entity.CPort;
import net.huadong.tech.base.entity.CShipStat;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.tech.privilege.entity.AuthUser;
import net.huadong.tech.privilege.entity.SysCode;
import net.huadong.tech.ship.controller.ShipController;
import net.huadong.tech.ship.entity.CShipData;
import net.huadong.tech.ship.entity.CShipDataGroup;
import net.huadong.tech.ship.entity.Ship;
import net.huadong.tech.ship.entity.ShipBerth;
import net.huadong.tech.ship.entity.ShipStat;
import net.huadong.tech.ship.entity.ShipStatGroup;
import net.huadong.tech.ship.entity.ShipTrend;
import net.huadong.tech.ship.entity.ShipLoadPlan;
import net.huadong.tech.ship.entity.ShipMoor;
import net.huadong.tech.ship.service.ShipStatService;
import net.huadong.idev.hdmessagecode.HdMessageCode;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiQueryParams;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;
import net.huadong.tech.util.ComparatorU;
import net.huadong.tech.util.HdUtils;

/**
 * @author
 */
@Component
public class ShipStatServiceImpl implements ShipStatService {

	@Value("${cshipdata.token}")
	private String token;
	
	@Value("${api.service.ip}")
	private String apiServiceIp;
	
	@Value("${tjgjt.service.ip}")
	private String tjgjtServiceIp;
	
	
	private static char[] charSet = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();

	@Override
	public HdEzuiDatagridData find(HdQuery hdQuery) {
		String jpql = "select a from ShipStat a where 1=1 ";
		String shipNo = hdQuery.getStr("shipNo");
		String iEId = hdQuery.getStr("iEId");
		QueryParamLs paramLs = new QueryParamLs();
		if (HdUtils.strNotNull(shipNo)) {
			jpql += "and a.shipNo =:shipNo ";
			paramLs.addParam("shipNo", shipNo);
		} else {
			// 为了实现刚打开页面时 grid不出现数据
			jpql += "and a.shipNo =:shipNo ";
			paramLs.addParam("shipNo", "12345678#$%@");
		}
		if (HdUtils.strNotNull(iEId)) {
			jpql += "and a.iEId =:iEId ";
			paramLs.addParam("iEId", iEId);
		}
		HdEzuiDatagridData result = JpaUtils.findByEz(jpql, paramLs, hdQuery);
		List<ShipStat> shipStatList = result.getRows();
		for (ShipStat shipStat : shipStatList) {
			Ship ship = JpaUtils.findById(Ship.class, shipStat.getShipNo());
			if (HdUtils.strNotNull(shipStat.getShipStatCod())) {
				CShipStat cShipStat = JpaUtils.findById(CShipStat.class, shipStat.getShipStatCod());
				if (cShipStat != null) {
					shipStat.setShipStatCodNam(cShipStat.getShipStatNam());
				}
			}
			if (HdUtils.strNotNull(shipStat.getiEId())) {
				shipStat.setiEIdNam(HdUtils.getSysCodeName("I_E_ID", shipStat.getiEId()));
			}
			if (HdUtils.strNotNull(shipStat.getGroupCargoTyp())) {
				shipStat.setGroupCargoTypNam(HdUtils.getSysCodeName("GROUP_CARGO_TYP", shipStat.getGroupCargoTyp()));
			}
			if (HdUtils.strNotNull(shipStat.getSendFlag())) {
				shipStat.setSendFlagNam(HdUtils.getSysCodeName("Y/N_TYP", shipStat.getSendFlag()));
			}
			if (HdUtils.strNotNull(shipStat.getBerthCod())) {
				CBerth cBerth = JpaUtils.findById(CBerth.class, shipStat.getBerthCod());
				if (cBerth != null) {
					shipStat.setBerthNam(cBerth.getBerthNam());
				}
			}
		}
		return result;
	}

	@Override
	public HdMessageCode save(HdEzuiSaveDatagridData<ShipStat> hdEzuiSaveDatagridData) {
		// TODO Auto-generated method stub
		List<ShipStat> shipStatList = hdEzuiSaveDatagridData.getInsertedRows();
       
		for (ShipStat shipStat : shipStatList) {
			String jpql = "select max(a.seqNo) from ShipStat a where 1=1 ";
			QueryParamLs paramLs = new QueryParamLs();
			if (HdUtils.strNotNull(shipStat.getShipNo())) {
				jpql += "and a.shipNo =:shipNo ";
				paramLs.addParam("shipNo", shipStat.getShipNo());
			}
			if (HdUtils.strNotNull(shipStat.getiEId())) {
				jpql += "and a.iEId =:iEId";
				paramLs.addParam("iEId", shipStat.getiEId());
			}
			List<BigDecimal> seqNoList = JpaUtils.findAll(jpql, paramLs);
			BigDecimal seqNo;
			if (seqNoList.get(0) != null) {
				seqNo = seqNoList.get(0);
			} else {
				seqNo = new BigDecimal("0");
			}
			BigDecimal one = new BigDecimal("1");
			seqNo = seqNo.add(one);
			shipStat.setSeqNo(seqNo);
			if (HdUtils.strNotNull(shipStat.getShipStatCodNam())) {
				shipStat.setShipStatCod(shipStat.getShipStatCodNam());
			}
			if (HdUtils.strNotNull(shipStat.getBerthNam())) {
				shipStat.setBerthCod(shipStat.getBerthNam());
			}
			if (HdUtils.strNotNull(shipStat.getiEIdNam())) {
				shipStat.setiEId(shipStat.getiEIdNam());
			}
			if (HdUtils.strNotNull(shipStat.getGroupCargoTypNam())) {
				shipStat.setGroupCargoTyp(shipStat.getGroupCargoTypNam());
			}
			shipStat.setShipStatId(HdUtils.genUuid());
			shipStat.setSendFlag("N");
			Ship ship = JpaUtils.findById(Ship.class, shipStat.getShipNo());
			if (shipStat.getStatBegTim() != null && shipStat.getStatEndTim() != null) {
				Long t1 = shipStat.getStatBegTim().getTime();
				Long t2 = shipStat.getStatEndTim().getTime();
				if (t2 < t1) {
					throw new HdRunTimeException("结束时间不得早于开始时间！");
				} else {
					double hours = (double) (t2 - t1) / 60 / 1000;
					shipStat.setStatTim(new BigDecimal(hours));
				}

			}
			// 停时项目为靠泊
			if ("1001".equals(shipStat.getShipStatCod())) {
				if (ship != null) {
					if (HdUtils.strNotNull(shipStat.getBerthNam())) {
						CBerth cBerth = JpaUtils.findById(CBerth.class, shipStat.getBerthNam());
						if (cBerth != null) {
							ship.setBerthCod(shipStat.getBerthNam());
							ship.setDockCod(cBerth.getDockCod());
						}
					}
					ship.setToPortTim(shipStat.getStatBegTim());
					/*if (ship.getToPortTim() != null && !Ship.STATUS_4.equals(ship.getShipStat())) {//新增加的数据的停时项目为靠泊,初始的是船状态不是L时，将船状态改为Y
						ship.setShipStat(ship.STATUS_3);
					}*/
					ship.setShipStat(ship.STATUS_3);//此处为新增加的数据的停时项目为靠泊，该船的状态改就改为为Y
					JpaUtils.update(ship);
				}
				String jpqlc = "select a from ShipMoor a where a.shipNo=:shipNo and a.moorId=:moorId and a.workBegTim=:workBegTim";
				QueryParamLs paramLsc = new QueryParamLs();
				paramLsc.addParam("shipNo", ship.getShipNo());
				paramLsc.addParam("moorId", "T");
				paramLsc.addParam("workBegTim", shipStat.getStatBegTim());
				List<ShipMoor> shipmoorList = JpaUtils.findAll(jpqlc, paramLsc);
				if (shipmoorList.size() > 0){
					for (ShipMoor sm : shipmoorList) {
						sm.setWorkEndTim(shipStat.getStatBegTim());
						sm.setBerthCod(shipStat.getBerthCod());
						JpaUtils.update(sm);
					}
				} else {
					ShipMoor shipMoor = new ShipMoor();
					shipMoor.setShipMoorId(HdUtils.genUuid());
					shipMoor.setShipNo(shipStat.getShipNo());
					shipMoor.setMoorId("T");
					shipMoor.setWorkBegTim(shipStat.getStatBegTim());
					shipMoor.setWorkEndTim(shipStat.getStatEndTim());
					shipMoor.setBerthCod(shipStat.getBerthCod());
					JpaUtils.save(shipMoor);
				}
			}
			//移泊开船
			if ("1020".equals(shipStat.getShipStatCod())) {
				if (ship != null) {
					ship.setLeavPortTim(shipStat.getStatEndTim());
					ship.setShipStat(ship.STATUS_4);
					JpaUtils.update(ship);
				}
			}
			// 停时项目为开船
			if ("1002".equals(shipStat.getShipStatCod())) {
				if (ship != null) {
					ship.setLeavPortTim(shipStat.getStatEndTim());
					ship.setShipStat(ship.STATUS_4);
					JpaUtils.update(ship);
				}
				String jpqlc = "select a from ShipMoor a where a.shipNo=:shipNo and a.moorId=:moorId and a.workBegTim=:workBegTim";
				QueryParamLs paramLsc = new QueryParamLs();
				paramLsc.addParam("shipNo", ship.getShipNo());
				paramLsc.addParam("moorId", "U");
				paramLsc.addParam("workBegTim", shipStat.getStatBegTim());
				List<ShipMoor> shipmoorList = JpaUtils.findAll(jpqlc, paramLsc);
				if (shipmoorList.size() > 0){
					for (ShipMoor sm : shipmoorList) {
						sm.setWorkEndTim(shipStat.getStatEndTim());
						sm.setBerthCod(shipStat.getBerthCod());
						JpaUtils.update(sm);
					}
				} else {
					ShipMoor shipMoor = new ShipMoor();
					shipMoor.setShipMoorId(HdUtils.genUuid());
					shipMoor.setShipNo(shipStat.getShipNo());
					shipMoor.setMoorId("U");
					shipMoor.setWorkBegTim(shipStat.getStatBegTim());
					shipMoor.setWorkEndTim(shipStat.getStatEndTim());
					shipMoor.setBerthCod(shipStat.getBerthCod());
					JpaUtils.save(shipMoor);
				}
				CShipData cShipData = JpaUtils.findById(CShipData.class, ship.getShipCodId());
				if (HdUtils.strNotNull(shipStat.getBerthCod())) {
					CBerth cBerth = JpaUtils.findById(CBerth.class, shipStat.getBerthCod());
					if(cShipData.getShipNetWgt() == null){
						importBilling(shipStat.getShipNo(),shipStat.getiEId(),cBerth.getDockCod(),"0");
					}else{
						importBilling(shipStat.getShipNo(),shipStat.getiEId(),cBerth.getDockCod(),cShipData.getShipNetWgt().toString());
					}
				}
			}
			// 停时项目为离泊
			if ("1003".equals(shipStat.getShipStatCod())) {
				//离泊shipstat算沽口
				if (ship != null&&!ship.STATUS_4.equals(ship.getShipStat())) {
					ship.setLeavPortTim(shipStat.getStatEndTim());
					ship.setShipStat(ship.STATUS_2);
					JpaUtils.update(ship);
				}
				ShipMoor shipMoor = new ShipMoor();
				shipMoor.setShipMoorId(HdUtils.genUuid());
				shipMoor.setShipNo(shipStat.getShipNo());
				shipMoor.setMoorId("U");
				shipMoor.setWorkBegTim(shipStat.getStatBegTim());
				shipMoor.setWorkEndTim(shipStat.getStatEndTim());
				shipMoor.setBerthCod(shipStat.getBerthCod());
				String jpqlc = "select a from ShipMoor a where a.shipNo=:shipNo and a.moorId=:moorId and a.workBegTim=:workBegTim";
				QueryParamLs paramLsc = new QueryParamLs();
				paramLsc.addParam("shipNo", ship.getShipNo());
				paramLsc.addParam("moorId", "U");
				paramLsc.addParam("workBegTim", shipStat.getStatBegTim());
				List<ShipMoor> shipmoorList = JpaUtils.findAll(jpqlc, paramLsc);
				if (shipmoorList.size() > 0) {
					for (ShipMoor sm : shipmoorList) {
						sm.setWorkEndTim(shipStat.getStatEndTim());
						sm.setBerthCod(shipStat.getBerthCod());
						JpaUtils.update(sm);
					}
				} else {
					JpaUtils.save(shipMoor);
				}
				CShipData cShipData = JpaUtils.findById(CShipData.class, ship.getShipCodId());
				if (HdUtils.strNotNull(shipStat.getBerthCod())) {
					CBerth cBerth = JpaUtils.findById(CBerth.class, shipStat.getBerthCod());
					if(cShipData.getShipNetWgt() == null){
						importBilling(shipStat.getShipNo(),shipStat.getiEId(),cBerth.getDockCod(),"0");
					}else{
						importBilling(shipStat.getShipNo(),shipStat.getiEId(),cBerth.getDockCod(),cShipData.getShipNetWgt().toString());
					}
				}
			}
			// 停时项目为移泊
			if ("1008".equals(shipStat.getShipStatCod())) {
				//移泊shipstat算沽口
				if (ship != null&&!ship.STATUS_4.equals(ship.getShipStat())) {
					ship.setShipStat(ship.STATUS_2);
					JpaUtils.update(ship);
				}
				//
				ShipMoor shipMoor = new ShipMoor();
				shipMoor.setShipMoorId(HdUtils.genUuid());
				shipMoor.setShipNo(shipStat.getShipNo());
				shipMoor.setMoorId("UY");
				shipMoor.setWorkBegTim(shipStat.getStatBegTim());
				shipMoor.setWorkEndTim(shipStat.getStatEndTim());
				shipMoor.setBerthCod(shipStat.getBerthCod());
				String jpqlc = "select a from ShipMoor a where a.shipNo=:shipNo and a.moorId=:moorId and a.workBegTim=:workBegTim";
				QueryParamLs paramLsc = new QueryParamLs();
				paramLsc.addParam("shipNo", ship.getShipNo());
				paramLsc.addParam("moorId", "UY");
				paramLsc.addParam("workBegTim", shipStat.getStatBegTim());
				List<ShipMoor> shipmoorList = JpaUtils.findAll(jpqlc, paramLsc);
				if (shipmoorList.size() > 0) {
					for (ShipMoor sm : shipmoorList) {
						sm.setWorkEndTim(shipStat.getStatEndTim());
						sm.setBerthCod(shipStat.getBerthCod());
						JpaUtils.update(sm);
					}
				} else {
					JpaUtils.save(shipMoor);
				}
			}
		}
		List<ShipStat> shipStatList1 = hdEzuiSaveDatagridData.getUpdatedRows();
		for (ShipStat shipStat : shipStatList1) {
			if (HdUtils.strNotNull(shipStat.getShipStatCodNam())) {
				CShipStat cShipStat = JpaUtils.findById(CShipStat.class, shipStat.getShipStatCodNam());
				if (cShipStat != null) {
					shipStat.setShipStatCod(shipStat.getShipStatCodNam());
				}
			}
			if (HdUtils.strNotNull(shipStat.getBerthNam())) {
				CBerth cBerth = JpaUtils.findById(CBerth.class, shipStat.getBerthNam());
				if (cBerth != null) {
					shipStat.setBerthCod(shipStat.getBerthNam());
				}
			}
			Ship ship = JpaUtils.findById(Ship.class, shipStat.getShipNo());
			if ("1001".equals(shipStat.getShipStatCod())) {
				if (ship != null) {
					ship.setToPortTim(shipStat.getStatBegTim());
					ship.setShipStat(ship.STATUS_3);;//此处为更新的数据的停时项目为靠泊，该船的状态改就改为为Y
					JpaUtils.update(ship);
				}
				String jpqlc = "select a from ShipMoor a where a.shipNo=:shipNo and a.moorId=:moorId and a.workBegTim=:workBegTim";
				QueryParamLs paramLsc = new QueryParamLs();
				paramLsc.addParam("shipNo", ship.getShipNo());
				paramLsc.addParam("moorId", "T");
				paramLsc.addParam("workBegTim", shipStat.getStatBegTim());
				List<ShipMoor> shipmoorList = JpaUtils.findAll(jpqlc, paramLsc);
				if (shipmoorList.size() > 0){
					for (ShipMoor sm : shipmoorList) {
						sm.setWorkEndTim(shipStat.getStatBegTim());
						sm.setBerthCod(shipStat.getBerthCod());
						JpaUtils.update(sm);
					}
				}
			}
			//移泊开船
			if ("1020".equals(shipStat.getShipStatCod())) {
				if (ship != null) {
					ship.setLeavPortTim(shipStat.getStatEndTim());
					ship.setShipStat(ship.STATUS_4);
					JpaUtils.update(ship);
				}
			}
			// 停时项目为开船
			if ("1002".equals(shipStat.getShipStatCod())) {
				if (ship != null) {
					ship.setLeavPortTim(shipStat.getStatEndTim());
					ship.setShipStat(ship.STATUS_4);
					JpaUtils.update(ship);
				}
				String jpqlc = "select a from ShipMoor a where a.shipNo=:shipNo and a.moorId=:moorId and a.workBegTim=:workBegTim";
				QueryParamLs paramLsc = new QueryParamLs();
				paramLsc.addParam("shipNo", ship.getShipNo());
				paramLsc.addParam("moorId", "U");
				paramLsc.addParam("workBegTim", shipStat.getStatBegTim());
				List<ShipMoor> shipmoorList = JpaUtils.findAll(jpqlc, paramLsc);
				if (shipmoorList.size() > 0){
					for (ShipMoor sm : shipmoorList) {
						sm.setWorkEndTim(shipStat.getStatEndTim());
						sm.setBerthCod(shipStat.getBerthCod());
						JpaUtils.update(sm);
					}
				}
				CShipData cShipData = JpaUtils.findById(CShipData.class, ship.getShipCodId());
				if (HdUtils.strNotNull(shipStat.getBerthCod())) {
					CBerth cBerth = JpaUtils.findById(CBerth.class, shipStat.getBerthCod());
					if(cShipData.getShipNetWgt() == null){
						importBilling(shipStat.getShipNo(),shipStat.getiEId(),cBerth.getDockCod(),"0");
					}else{
						importBilling(shipStat.getShipNo(),shipStat.getiEId(),cBerth.getDockCod(),cShipData.getShipNetWgt().toString());
					}
				}
			}
			if ("1003".equals(shipStat.getShipStatCod())) {
				if (ship != null&&!ship.STATUS_4.equals(ship.getShipStat())) {
					ship.setLeavPortTim(shipStat.getStatEndTim());
					ship.setShipStat(ship.STATUS_2);
					JpaUtils.update(ship);
				}
				String jpqlc = "select a from ShipMoor a where a.shipNo=:shipNo and a.moorId=:moorId and a.workBegTim=:workBegTim";
				QueryParamLs paramLsc = new QueryParamLs();
				paramLsc.addParam("shipNo", ship.getShipNo());
				paramLsc.addParam("moorId", "U");
				paramLsc.addParam("workBegTim", shipStat.getStatBegTim());
				List<ShipMoor> shipmoorList = JpaUtils.findAll(jpqlc, paramLsc);
				if (shipmoorList.size() > 0) {
					for (ShipMoor sm : shipmoorList) {
						sm.setWorkEndTim(shipStat.getStatEndTim());
						sm.setBerthCod(shipStat.getBerthCod());
						JpaUtils.update(sm);
					}
				}
				CShipData cShipData = JpaUtils.findById(CShipData.class, ship.getShipCodId());
				if (HdUtils.strNotNull(shipStat.getBerthCod())) {
					CBerth cBerth = JpaUtils.findById(CBerth.class, shipStat.getBerthCod());
					if(cShipData.getShipNetWgt() == null){
						importBilling(shipStat.getShipNo(),shipStat.getiEId(),cBerth.getDockCod(),"0");
					}else{
						importBilling(shipStat.getShipNo(),shipStat.getiEId(),cBerth.getDockCod(),cShipData.getShipNetWgt().toString());
					}
				}
			}
			if ("1008".equals(shipStat.getShipStatCod())) {
				String jpqlc = "select a from ShipMoor a where a.shipNo=:shipNo and a.moorId=:moorId and a.workBegTim=:workBegTim";
				QueryParamLs paramLsc = new QueryParamLs();
				paramLsc.addParam("shipNo", ship.getShipNo());
				paramLsc.addParam("moorId", "UY");
				paramLsc.addParam("workBegTim", shipStat.getStatBegTim());
				List<ShipMoor> shipmoorList = JpaUtils.findAll(jpqlc, paramLsc);
				if (shipmoorList.size() > 0) {
					for (ShipMoor sm : shipmoorList) {
						sm.setWorkEndTim(shipStat.getStatEndTim());
						sm.setBerthCod(shipStat.getBerthCod());
						JpaUtils.update(sm);
					}
				}
				//
				if (ship != null&&!ship.STATUS_3.equals(ship.getShipStat())) {
					ship.setLeavPortTim(shipStat.getStatEndTim());
					ship.setShipStat(ship.STATUS_2);
					JpaUtils.update(ship);
				}
			}
			if (shipStat.getStatBegTim() != null && shipStat.getStatEndTim() != null) {
				Long t1 = shipStat.getStatBegTim().getTime();
				Long t2 = shipStat.getStatEndTim().getTime();
				if (t2 < t1) {
					throw new HdRunTimeException("结束时间不得早于开始时间！");
				} else {
					double hours = (double) (t2 - t1) / 60 / 1000;
					shipStat.setStatTim(new BigDecimal(hours));
				}
			}
		}
		return JpaUtils.save(hdEzuiSaveDatagridData);
	}

	@Transactional
	public void removeAll(String shipStatIds) {
		List<String> shipStatIdList = HdUtils.paraseStrs(shipStatIds);
		for (String shipStatId : shipStatIdList) {
			ShipStat bean = JpaUtils.findById(ShipStat.class, shipStatId);
			//System.out.println(bean);
			String jpql = "select a from Ship a where a.shipNo=:shipNo";
			QueryParamLs paramLs = new QueryParamLs();
			paramLs.addParam("shipNo", bean.getShipNo());
			List<Ship> shipList = JpaUtils.findAll(jpql, paramLs);
			Ship ship=shipList.get(0);
			String shipstat=ship.getShipStat();
			if(shipstat.equals("L")){
				//System.out.println("该船的状态为离港");
				ship.setShipStat("Y");//回退为到港
				JpaUtils.update(ship);
			}
		    JpaUtils.remove(bean);
			if(ShipStat.KAOBO.equals(bean.getShipStatCod())){
				ship.setShipStat(ship.STATUS_1);//如果删除的船停时项目为靠泊，该船的状态变成E
				String jpqlc = "select a from ShipMoor a where a.shipNo=:shipNo and a.moorId=:moorId and a.workBegTim=:workBegTim";
				QueryParamLs paramLsc = new QueryParamLs();
				paramLsc.addParam("shipNo", bean.getShipNo());
				paramLsc.addParam("moorId", "T");
				paramLsc.addParam("workBegTim", bean.getStatBegTim());
				List<ShipMoor> shipmoorList = JpaUtils.findAll(jpqlc, paramLsc);
				for (ShipMoor sm : shipmoorList) {
					JpaUtils.remove(sm);
				}
			}
			
			if(ShipStat.KAICHUAN.equals(bean.getShipStatCod())){
				String jpqlc = "select a from ShipMoor a where a.shipNo=:shipNo and a.moorId=:moorId and a.workBegTim=:workBegTim";
				QueryParamLs paramLsc = new QueryParamLs();
				paramLsc.addParam("shipNo", bean.getShipNo());
				paramLsc.addParam("moorId", "U");
				paramLsc.addParam("workBegTim", bean.getStatBegTim());
				List<ShipMoor> shipmoorList = JpaUtils.findAll(jpqlc, paramLsc);
				for (ShipMoor sm : shipmoorList) {
					JpaUtils.remove(sm);
				}
			}
			
			if(ShipStat.LIBO.equals(bean.getShipStatCod())){
				if(shipstat.equals("A")){
				ship.setShipStat(ship.STATUS_3);//船舶状态为沽口的，如果删除的船停时项目为离泊，该船的状态回退为到港Y
				}
				String jpqlc = "select a from ShipMoor a where a.shipNo=:shipNo and a.moorId=:moorId and a.workBegTim=:workBegTim";
				QueryParamLs paramLsc = new QueryParamLs();
				paramLsc.addParam("shipNo", bean.getShipNo());
				paramLsc.addParam("moorId", "U");
				paramLsc.addParam("workBegTim", bean.getStatBegTim());
				List<ShipMoor> shipmoorList = JpaUtils.findAll(jpqlc, paramLsc);
				for (ShipMoor sm : shipmoorList) {
					JpaUtils.remove(sm);
				}
			}
			
			if(ShipStat.YIBO.equals(bean.getShipStatCod())){
				String jpqlc = "select a from ShipMoor a where a.shipNo=:shipNo and a.moorId=:moorId and a.workBegTim=:workBegTim";
				QueryParamLs paramLsc = new QueryParamLs();
				paramLsc.addParam("shipNo", bean.getShipNo());
				paramLsc.addParam("moorId", "UY");
				paramLsc.addParam("workBegTim", bean.getStatBegTim());
				List<ShipMoor> shipmoorList = JpaUtils.findAll(jpqlc, paramLsc);
				for (ShipMoor sm : shipmoorList) {
					JpaUtils.remove(sm);
				}
			}
		}
	}

	@Transactional
	public void importShipStats(String shipStatIds) {
		List<String> shipStatIdList = HdUtils.paraseStrs(shipStatIds);
		for (String shipStatId : shipStatIdList) {
			importShipStat(shipStatId);
		}
	}
	
	@Transactional
	public void importNewShipStats(String shipStatIds) {
		List<String> shipStatIdList = HdUtils.paraseStrs(shipStatIds);
		for (String shipStatId : shipStatIdList) {
			importNewShipStat(shipStatId);
		}
	}

	@Override
	public ShipStat findone(String shipStatId) {
		ShipStat shipStat = JpaUtils.findById(ShipStat.class, shipStatId);
		return shipStat;

	}

	@Override
	public ShipStat findHl(String shipNo, String iEId) {
		Ship ship = JpaUtils.findById(Ship.class, shipNo);
		String jpql = "select a from SysCode a where a.fieldCod =:fieldCod";
		QueryParamLs paramLs = new QueryParamLs();
		paramLs.addParam("fieldCod", "GROUP_CARGO_TYP");
		List<SysCode> sysCodeList = JpaUtils.findAll(jpql, paramLs);
		ShipStat shipStat = new ShipStat();
		for (SysCode sysCode : sysCodeList) {
			if (iEId.equals(ship.JK)) {
				if (sysCode.getName().equals(ship.getiCargoNam())) {
					shipStat.setGroupCargoTyp(sysCode.getCode());
				}
			} else if (iEId.equals(ship.CK)) {
				if (sysCode.getName().equals(ship.geteCargoNam())) {
					shipStat.setGroupCargoTyp(sysCode.getCode());
				}
			}

		}
		return shipStat;

	}

	@Override
	public ShipTrend findJgsj(String shipNo) {
		String jpql = "select a from ShipTrend a where a.shipNo =:shipNo and a.trendsTermini in ('1','11','12') and a.sendFlag =:sendFlag";
		QueryParamLs paramLs = new QueryParamLs();
		paramLs.addParam("shipNo", shipNo);
		paramLs.addParam("sendFlag", ShipTrend.S);
		List<ShipTrend> shipTrendList = JpaUtils.findAll(jpql, paramLs);
		ShipTrend shipTrend = new ShipTrend();
		if (shipTrendList.size() > 1) {
			throw new HdRunTimeException("进港动态时间有误");
		} else if (shipTrendList.size() > 0) {
			shipTrend = shipTrendList.get(0);
		}
		return shipTrend;

	}

	@Override
	@Transactional
	public HdMessageCode saveone(@RequestBody ShipStat shipStat) {
		if (HdUtils.strNotNull(shipStat.getShipStatId())) {
			JpaUtils.update(shipStat);
		} else {
			JpaUtils.save(shipStat);
		}
		return HdUtils.genMsg();
	}

	public void cancelShipStat(String shipStatId) {
		ShipStat shipStat = JpaUtils.findById(ShipStat.class, shipStatId);
		JSONObject jsonObj = new JSONObject();
		String jpql = "select a from AuthUser a where a.account =:account";
		QueryParamLs paramLs = new QueryParamLs();
		paramLs.addParam("account", shipStat.getRecNam());
		List<AuthUser> authUserList = JpaUtils.findAll(jpql, paramLs);
		for (AuthUser authUser : authUserList) {
			if ("admin".equals(authUser.getAccount())) {
				jsonObj.put("czr", "管理员");
			} else {
				jsonObj.put("czr", authUser.getName());
			}
		}
		if (shipStat != null) {
			if (HdUtils.strNotNull(shipStat.getShipNo())) {
				Ship ship = JpaUtils.findById(Ship.class, shipStat.getShipNo());
				jsonObj.put("cbxh", ship.getGroupShipNo());
				jsonObj.put("myxz", ship.getTradeId());
				jsonObj.put("bwdm", ship.getBerthCod());
			}
			jsonObj.put("hldm", shipStat.getGroupCargoTyp());
			jsonObj.put("tsxh", shipStat.getSeqNo());
			jsonObj.put("gsdm", "03406500");
			jsonObj.put("zyjtsdm", shipStat.getShipStatCod());
			jsonObj.put("qssj", shipStat.getStatBegTim());
			jsonObj.put("jssj", shipStat.getStatEndTim());
			jsonObj.put("zyys", shipStat.getStatTim());
			jsonObj.put("bz", shipStat.getRemarks());
			jsonObj.put("czdw", "03406500");
			jsonObj.put("czsj", shipStat.getRecTim());
			jsonObj.put("fsbz", shipStat.getSendFlag());
			jsonObj.put("qrbz", "");
			if ("E".equals(shipStat.getiEId())) {
				jsonObj.put("jckbz", "2");
			} else if ("I".equals(shipStat.getiEId())) {
				jsonObj.put("jckbz", "1");
			}
			jsonObj.put("token", token);
		}

		String url = apiServiceIp + "8091/ScheduleSysWebApi/cancelShipStat";
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
				if (str2.equals(response)) {
					shipStat.setSendFlag("N");
					JpaUtils.update(shipStat);
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

	public void importNewShipStat(String shipStatId) {
		ShipStat shipStat = JpaUtils.findById(ShipStat.class, shipStatId);
		if(shipStat == null){
			throw new HdRunTimeException("数据有误，上传失败！");
		}
		JSONObject jsonObj = new JSONObject();
		Map<String,String> map = new HashMap<String, String>();
		jsonObj.put("cmdId", "2014");
		Ship ship = JpaUtils.findById(Ship.class, shipStat.getShipNo());
		jsonObj.put("coId",ship.GZ);	
		map.put("teamOrgnId",ship.GZ);
		map.put("sstatusId",shipStat.getShipStatId());
		map.put("svoyageId", ship.getNewGroupShipNo());
		if (Ship.JK.equals(shipStat.getiEId())){
			map.put("shipId", ship.getNewIShipId());
		}else if(ship.CK.equals(shipStat.getiEId())){
			map.put("shipId", ship.getNewEShipId());
		}
		map.put("ieFlag",shipStat.getiEId());
		map.put("sequence",shipStat.getSeqNo().toString());
		map.put("begTime",shipStat.getStatBegTim().toString().substring(0, 16));
		map.put("endTime", shipStat.getStatEndTim().toString().substring(0, 16));
		map.put("shipStatCode",shipStat.getShipStatCod());
		if (HdUtils.strNotNull(shipStat.getBerthCod())){
			CBerth cBerth = JpaUtils.findById(CBerth.class, shipStat.getBerthCod());
			if (cBerth != null){
				map.put("berthCode", cBerth.getGroupBerthCod());
			}
		}
//		map.put("berthCode", shipStat.getBerthCod());
		map.put("description", shipStat.getRemarks());
		map.put("submitFlag", "0");
		String jpql = "select a from AuthUser a where a.account =:account";
		QueryParamLs paramLs = new QueryParamLs();
		paramLs.addParam("account", shipStat.getRecNam());
		List<AuthUser> authUserList = JpaUtils.findAll(jpql, paramLs);
		for (AuthUser authUser : authUserList) {
			map.put("submitName", authUser.getName());
		}
		map.put("submitTime", shipStat.getRecTim().toString());
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
	
	
	public void importShipStat(String shipStatId) {
		ShipStat shipStat = JpaUtils.findById(ShipStat.class, shipStatId);
		JSONObject jsonObj = new JSONObject();
		String jpql = "select a from AuthUser a where a.account =:account";
		QueryParamLs paramLs = new QueryParamLs();
		paramLs.addParam("account", shipStat.getRecNam());
		List<AuthUser> authUserList = JpaUtils.findAll(jpql, paramLs);
		for (AuthUser authUser : authUserList) {
			if ("admin".equals(authUser.getAccount())) {
				jsonObj.put("czr", "管理员");
			} else {
				jsonObj.put("czr", authUser.getName());
			}
		}
		SimpleDateFormat sdf =   new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		if (shipStat != null) {
			if (HdUtils.strNotNull(shipStat.getShipNo())) {
				Ship ship = JpaUtils.findById(Ship.class, shipStat.getShipNo());
				jsonObj.put("cbxh", ship.getGroupShipNo());
				jsonObj.put("myxz", ship.getTradeId());
			}
			jsonObj.put("bwdm", shipStat.getBerthCod());
			jsonObj.put("hldm", shipStat.getGroupCargoTyp());
			jsonObj.put("tsxh", shipStat.getSeqNo());
			jsonObj.put("gsdm", "03406500");
			jsonObj.put("zyjtsdm", shipStat.getShipStatCod());
			if(shipStat.getStatBegTim() != null){
				jsonObj.put("qssj", shipStat.getStatBegTim().toString().substring(0, 19));
			}
			
//			jsonObj.put("jssj", shipStat.getStatEndTim());
			if(shipStat.getStatEndTim() != null){
				jsonObj.put("jssj", shipStat.getStatEndTim().toString().substring(0, 19));
			}
			jsonObj.put("zyys", shipStat.getStatTim());
			jsonObj.put("bz", shipStat.getRemarks());
			jsonObj.put("czdw", "03406500");
//			jsonObj.put("czsj", shipStat.getRecTim());
			if(shipStat.getRecTim() != null){
				jsonObj.put("czsj", shipStat.getRecTim().toString().substring(0, 19));
			}
			jsonObj.put("fsbz", shipStat.getSendFlag());
			jsonObj.put("qrbz", "");
			if ("E".equals(shipStat.getiEId())) {
				jsonObj.put("jckbz", "2");
			} else if ("I".equals(shipStat.getiEId())) {
				jsonObj.put("jckbz", "1");
			}
			jsonObj.put("token", token);
		}

		String url = apiServiceIp + "8091/ScheduleSysWebApi/setShipStat";
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
				if (str2.equals(response)) {
					shipStat.setSendFlag("Y");
					JpaUtils.update(shipStat);
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
	public void getshipstat(String groupShipNo) {
		String resp = null;
		JSONObject obj = new JSONObject();
		if (HdUtils.strNotNull(groupShipNo)) {
			obj.put("cbxh", groupShipNo);
		}
		obj.put("token", token);
		String query = obj.toString();

		String urlStr = apiServiceIp + "8091/ScheduleSysWebApi/getShipStat";
		// 返回结果集
		List<ShipStatGroup> list = new ArrayList<ShipStatGroup>();
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
			list = gson.fromJson(resp, new TypeToken<List<ShipStatGroup>>() {
			}.getType());
		}
		boolean updateFlag = true;
//		ComparatorU comparator = new ComparatorU();
//		Collections.sort(list, comparator);
		if (list.size() > 0) {
			for (ShipStatGroup shipStatGroup : list) {
				String jpql = "select a from Ship a where a.groupShipNo=:groupShipNo";
				QueryParamLs paramLs = new QueryParamLs();
				paramLs.addParam("groupShipNo", groupShipNo);
				List<Ship> shipList = JpaUtils.findAll(jpql, paramLs);
				if (shipList.size() > 0) {
					String shipNo = shipList.get(0).getShipNo();
					Date date =shipStatGroup.getQssj();
			        Timestamp statBegTim = new Timestamp(date.getTime());
					String jpql2 = "select a from ShipStat a where a.shipNo=:shipNo and a.shipStatCod=:shipStatCod and a.statBegTim =:statBegTim";
					QueryParamLs paramLs2 = new QueryParamLs();
					paramLs2.addParam("shipNo", shipNo);
					paramLs2.addParam("shipStatCod", shipStatGroup.getZyjtsdm());
					paramLs2.addParam("statBegTim", statBegTim);
					List<ShipStat> shipstatList = JpaUtils.findAll(jpql2, paramLs2);
					if (shipstatList.size() > 0) {
						for (ShipStat shipStat : shipstatList) {
							shipStat.setStatBegTim(new Timestamp(shipStatGroup.getQssj().getTime()));
							shipStat.setStatEndTim(new Timestamp(shipStatGroup.getJssj().getTime()));
							shipStat.setRemarks(shipStatGroup.getBz());
							shipStat.setStatTim(new BigDecimal(shipStatGroup.getZyys()));
							if ("1".equals(shipStatGroup.getJckbz())) {
								shipStat.setiEId("I");
							}
							if ("2".equals(shipStatGroup.getJckbz())) {
								shipStat.setiEId("E");
							}
							shipStat.setGroupCargoTyp(shipStatGroup.getHldm());
							shipStat.setBerthCod(shipStatGroup.getBwdm());
							shipStat.setSendFlag("Y");
							JpaUtils.update(shipStat);
							Ship ship = JpaUtils.findById(Ship.class, shipNo);
							// 停时项目为靠泊
							if ("1001".equals(shipStatGroup.getZyjtsdm())) {
								if (ship != null && updateFlag) {
									ship.setBerthCod(shipStatGroup.getBwdm());
									if (HdUtils.strNotNull(shipStatGroup.getBwdm())) {
										CBerth cBerth = JpaUtils.findById(CBerth.class, shipStatGroup.getBwdm());
										ship.setDockCod(cBerth.getDockCod());
									}
									ship.setToPortTim(new Timestamp(shipStatGroup.getJssj().getTime()));
									if (ship.getToPortTim() != null && ship.STATUS_1.equals(ship.getShipStat())) {
										ship.setShipStat(ship.STATUS_3);
									}
									JpaUtils.update(ship);
									updateFlag = false;
								}else if(ship != null && !updateFlag){
									String jpql1 = "select a from ShipBerth a where a.shipNo =:shipNo";
									QueryParamLs paramLs1 = new QueryParamLs();
								    paramLs1.addParam("shipNo", ship.getShipNo());
								    List<ShipBerth> shipBerthList = JpaUtils.findAll(jpql1, paramLs1);
								    if(shipBerthList.size()>0){
								    	ShipBerth shipBerth = shipBerthList.get(0);
								    	shipBerth.setOriBerthCod(ship.getBerthCod());
										shipBerth.setBerthCod(shipStatGroup.getBwdm());
										shipBerth.setBerthBegTim(new Timestamp(shipStatGroup.getQssj().getTime()));
										shipBerth.setBerthEndTim(new Timestamp(shipStatGroup.getJssj().getTime()));
										JpaUtils.update(shipBerth);
								    }
								}
								String jpqlc = "select a from ShipMoor a where a.shipNo=:shipNo and a.moorId=:moorId and a.workBegTim=:workBegTim";
								QueryParamLs paramLsc = new QueryParamLs();
								paramLsc.addParam("shipNo", shipNo);
								paramLsc.addParam("moorId", "T");
								paramLsc.addParam("workBegTim", new Timestamp(shipStatGroup.getQssj().getTime()));
								List<ShipMoor> shipmoorList = JpaUtils.findAll(jpqlc, paramLsc);
								for (ShipMoor sm : shipmoorList) {
									sm.setWorkEndTim(new Timestamp(shipStatGroup.getJssj().getTime()));
									sm.setBerthCod(shipStatGroup.getBwdm());
									JpaUtils.update(sm);
								}
							}
							// 停时项目为开船
							if ("1002".equals(shipStatGroup.getZyjtsdm())) {
								if (ship != null) {
									ship.setLeavPortTim(new Timestamp(shipStatGroup.getJssj().getTime()));
									ship.setShipStat(ship.STATUS_4);
									JpaUtils.update(ship);
								}
								String jpqlc = "select a from ShipMoor a where a.shipNo=:shipNo and a.moorId=:moorId and a.workBegTim=:workBegTim";
								QueryParamLs paramLsc = new QueryParamLs();
								paramLsc.addParam("shipNo", shipNo);
								paramLsc.addParam("moorId", "U");
								paramLsc.addParam("workBegTim", new Timestamp(shipStatGroup.getQssj().getTime()));
								List<ShipMoor> shipmoorList = JpaUtils.findAll(jpqlc, paramLsc);
								for (ShipMoor sm : shipmoorList) {
									sm.setWorkEndTim(new Timestamp(shipStatGroup.getJssj().getTime()));
									sm.setBerthCod(shipStatGroup.getBwdm());
									JpaUtils.update(sm);
								}
								//调用发送计费接口
								CShipData cShipData = JpaUtils.findById(CShipData.class, ship.getShipCodId());
								if (HdUtils.strNotNull(shipStatGroup.getBwdm())) {
									CBerth cBerth = JpaUtils.findById(CBerth.class, shipStatGroup.getBwdm());
									if(cShipData.getShipNetWgt() == null){
										importBilling(shipNo,shipStat.getiEId(),cBerth.getDockCod(),"0");
									}else{
										importBilling(shipNo,shipStat.getiEId(),cBerth.getDockCod(),cShipData.getShipNetWgt().toString());
									}
									
								}
							}
							if ("1003".equals(shipStatGroup.getZyjtsdm())) {
								if (ship != null) {
									ship.setLeavPortTim(new Timestamp(shipStatGroup.getJssj().getTime()));
									ship.setShipStat(ship.STATUS_4);
									JpaUtils.update(ship);
								}
								String jpqlc = "select a from ShipMoor a where a.shipNo=:shipNo and a.moorId=:moorId and a.workBegTim=:workBegTim";
								QueryParamLs paramLsc = new QueryParamLs();
								paramLsc.addParam("shipNo", shipNo);
								paramLsc.addParam("moorId", "U");
								paramLsc.addParam("workBegTim", new Timestamp(shipStatGroup.getQssj().getTime()));
								List<ShipMoor> shipmoorList = JpaUtils.findAll(jpqlc, paramLsc);
								for (ShipMoor sm : shipmoorList) {
									sm.setWorkEndTim(new Timestamp(shipStatGroup.getJssj().getTime()));
									sm.setBerthCod(shipStatGroup.getBwdm());
									JpaUtils.update(sm);
								}
								//调用发送计费接口
								CShipData cShipData = JpaUtils.findById(CShipData.class, ship.getShipCodId());
								if (HdUtils.strNotNull(shipStatGroup.getBwdm())) {
									CBerth cBerth = JpaUtils.findById(CBerth.class, shipStatGroup.getBwdm());
									if(cShipData.getShipNetWgt() == null){
										importBilling(shipNo,shipStat.getiEId(),cBerth.getDockCod(),"0");
									}else{
										importBilling(shipNo,shipStat.getiEId(),cBerth.getDockCod(),cShipData.getShipNetWgt().toString());
									}
								}
							}
							if ("1008".equals(shipStatGroup.getZyjtsdm())) {
								String jpqlc = "select a from ShipMoor a where a.shipNo=:shipNo and a.moorId=:moorId and a.workBegTim=:workBegTim";
								QueryParamLs paramLsc = new QueryParamLs();
								paramLsc.addParam("shipNo", shipNo);
								paramLsc.addParam("moorId", "UY");
								paramLsc.addParam("workBegTim", new Timestamp(shipStatGroup.getQssj().getTime()));
								List<ShipMoor> shipmoorList = JpaUtils.findAll(jpqlc, paramLsc);
								for (ShipMoor sm : shipmoorList) {
									sm.setWorkEndTim(new Timestamp(shipStatGroup.getJssj().getTime()));
									sm.setBerthCod(shipStatGroup.getBwdm());
									JpaUtils.update(sm);
								}
							}
						}
					} else {
						ShipStat shipStat = new ShipStat();
						shipStat.setShipStatId(HdUtils.genUuid());
						shipStat.setShipNo(shipNo);
						shipStat.setSeqNo(new BigDecimal(shipStatGroup.getTsxh()));
						shipStat.setShipStatCod(shipStatGroup.getZyjtsdm());
						shipStat.setStatBegTim(new Timestamp(shipStatGroup.getQssj().getTime()));
						shipStat.setStatEndTim(new Timestamp(shipStatGroup.getJssj().getTime()));
						shipStat.setRemarks(shipStatGroup.getBz());
						shipStat.setStatTim(new BigDecimal(shipStatGroup.getZyys()));
						shipStat.setRecNam(HdUtils.getCurUser().getName());
						shipStat.setRecTim(HdUtils.getDateTime());
						if ("1".equals(shipStatGroup.getJckbz())) {
							shipStat.setiEId("I");
						}
						if ("2".equals(shipStatGroup.getJckbz())) {
							shipStat.setiEId("E");
						}
						shipStat.setGroupCargoTyp(shipStatGroup.getHldm());
						shipStat.setBerthCod(shipStatGroup.getBwdm());
						shipStat.setSendFlag("Y");
						JpaUtils.save(shipStat);
						Ship ship = JpaUtils.findById(Ship.class, shipNo);
						// 停时项目为靠泊
						if ("1001".equals(shipStatGroup.getZyjtsdm())) {
							if (ship != null && updateFlag) {
								ship.setBerthCod(shipStatGroup.getBwdm());
								if (HdUtils.strNotNull(shipStatGroup.getBwdm())) {
									CBerth cBerth = JpaUtils.findById(CBerth.class, shipStatGroup.getBwdm());
									ship.setDockCod(cBerth.getDockCod());
								}
								ship.setToPortTim(new Timestamp(shipStatGroup.getJssj().getTime()));
								if (ship.getToPortTim() != null && ship.STATUS_1.equals(ship.getShipStat())) {
									ship.setShipStat(ship.STATUS_3);
								}
								JpaUtils.update(ship);
								updateFlag = false;
							}else if(ship != null && !updateFlag){
								ShipBerth shipBerth = new ShipBerth();
								shipBerth.setShipBerthId(HdUtils.genUuid());
								shipBerth.setShipNo(ship.getShipNo());
								String jpql1 = "select max(a.seqNo) from ShipBerth a where 1=1 ";
								QueryParamLs paramLs1 = new QueryParamLs();
								List<BigDecimal> seqNoList = JpaUtils.findAll(jpql1, paramLs1);
								BigDecimal seqNo;
								if (seqNoList.get(0) != null) {
									seqNo = seqNoList.get(0);
								} else {
									seqNo = new BigDecimal("0");
								}
								BigDecimal one = new BigDecimal("1");
								seqNo = seqNo.add(one);
								shipBerth.setSeqNo(seqNo);
								shipBerth.setOriBerthCod(ship.getBerthCod());
								shipBerth.setBerthCod(shipStatGroup.getBwdm());
								shipBerth.setBerthBegTim(new Timestamp(shipStatGroup.getQssj().getTime()));
								shipBerth.setBerthEndTim(new Timestamp(shipStatGroup.getJssj().getTime()));
								JpaUtils.save(shipBerth);
							}
							ShipMoor shipMoor = new ShipMoor();
							shipMoor.setShipMoorId(HdUtils.genUuid());
							shipMoor.setShipNo(shipNo);
							shipMoor.setMoorId("T");
							shipMoor.setWorkBegTim(new Timestamp(shipStatGroup.getQssj().getTime()));
							shipMoor.setWorkEndTim(new Timestamp(shipStatGroup.getJssj().getTime()));
							shipMoor.setBerthCod(shipStatGroup.getBwdm());
							String jpqlc = "select a from ShipMoor a where a.shipNo=:shipNo and a.moorId=:moorId and a.workBegTim=:workBegTim";
							QueryParamLs paramLsc = new QueryParamLs();
							paramLsc.addParam("shipNo", shipNo);
							paramLsc.addParam("moorId", "T");
							paramLsc.addParam("workBegTim", new Timestamp(shipStatGroup.getQssj().getTime()));
							List<ShipMoor> shipmoorList = JpaUtils.findAll(jpqlc, paramLsc);
							if (shipmoorList.size() > 0) {
								for (ShipMoor sm : shipmoorList) {
									sm.setWorkEndTim(new Timestamp(shipStatGroup.getJssj().getTime()));
									sm.setBerthCod(shipStatGroup.getBwdm());
									JpaUtils.update(sm);
								}
							} else {
								JpaUtils.save(shipMoor);
							}
						}
						// 停时项目为开船
						if ("1002".equals(shipStatGroup.getZyjtsdm())) {
							if (ship != null) {
								ship.setLeavPortTim(new Timestamp(shipStatGroup.getJssj().getTime()));
								ship.setShipStat(ship.STATUS_4);
								JpaUtils.update(ship);
							}
							ShipMoor shipMoor = new ShipMoor();
							shipMoor.setShipMoorId(HdUtils.genUuid());
							shipMoor.setShipNo(shipNo);
							shipMoor.setMoorId("U");
							shipMoor.setWorkBegTim(new Timestamp(shipStatGroup.getQssj().getTime()));
							shipMoor.setWorkEndTim(new Timestamp(shipStatGroup.getJssj().getTime()));
							shipMoor.setBerthCod(shipStatGroup.getBwdm());
							String jpqlc = "select a from ShipMoor a where a.shipNo=:shipNo and a.moorId=:moorId and a.workBegTim=:workBegTim";
							QueryParamLs paramLsc = new QueryParamLs();
							paramLsc.addParam("shipNo", shipNo);
							paramLsc.addParam("moorId", "U");
							paramLsc.addParam("workBegTim", new Timestamp(shipStatGroup.getQssj().getTime()));
							List<ShipMoor> shipmoorList = JpaUtils.findAll(jpqlc, paramLsc);
							if (shipmoorList.size() > 0) {
								for (ShipMoor sm : shipmoorList) {
									sm.setWorkEndTim(new Timestamp(shipStatGroup.getJssj().getTime()));
									sm.setBerthCod(shipStatGroup.getBwdm());
									JpaUtils.update(sm);
								}
							} else {
								JpaUtils.save(shipMoor);
							}
							//调用发送计费接口
							CShipData cShipData = JpaUtils.findById(CShipData.class, ship.getShipCodId());
							if (HdUtils.strNotNull(shipStatGroup.getBwdm())) {
								CBerth cBerth = JpaUtils.findById(CBerth.class, shipStatGroup.getBwdm());
								if(cShipData.getShipNetWgt() == null){
									importBilling(shipNo,shipStat.getiEId(),cBerth.getDockCod(),"0");
								}else{
									importBilling(shipNo,shipStat.getiEId(),cBerth.getDockCod(),cShipData.getShipNetWgt().toString());
								}
							}
						}
						//离泊
						if ("1003".equals(shipStatGroup.getZyjtsdm())) {
							if (ship != null) {
								ship.setLeavPortTim(new Timestamp(shipStatGroup.getJssj().getTime()));
								ship.setShipStat(ship.STATUS_4);
								JpaUtils.update(ship);
							}
							ShipMoor shipMoor = new ShipMoor();
							shipMoor.setShipMoorId(HdUtils.genUuid());
							shipMoor.setShipNo(shipNo);
							shipMoor.setMoorId("U");
							shipMoor.setWorkBegTim(new Timestamp(shipStatGroup.getQssj().getTime()));
							shipMoor.setWorkEndTim(new Timestamp(shipStatGroup.getJssj().getTime()));
							shipMoor.setBerthCod(shipStatGroup.getBwdm());
							String jpqlc = "select a from ShipMoor a where a.shipNo=:shipNo and a.moorId=:moorId and a.workBegTim=:workBegTim";
							QueryParamLs paramLsc = new QueryParamLs();
							paramLsc.addParam("shipNo", shipNo);
							paramLsc.addParam("moorId", "U");
							paramLsc.addParam("workBegTim", new Timestamp(shipStatGroup.getQssj().getTime()));
							List<ShipMoor> shipmoorList = JpaUtils.findAll(jpqlc, paramLsc);
							if (shipmoorList.size() > 0) {
								for (ShipMoor sm : shipmoorList) {
									sm.setWorkEndTim(new Timestamp(shipStatGroup.getJssj().getTime()));
									sm.setBerthCod(shipStatGroup.getBwdm());
									JpaUtils.update(sm);
								}
							} else {
								JpaUtils.save(shipMoor);
							}
							//调用发送计费接口
							CShipData cShipData = JpaUtils.findById(CShipData.class, ship.getShipCodId());
							if (HdUtils.strNotNull(shipStatGroup.getBwdm())) {
								CBerth cBerth = JpaUtils.findById(CBerth.class, shipStatGroup.getBwdm());
								if(cShipData.getShipNetWgt() == null){
									importBilling(shipNo,shipStat.getiEId(),cBerth.getDockCod(),"0");
								}else{
									importBilling(shipNo,shipStat.getiEId(),cBerth.getDockCod(),cShipData.getShipNetWgt().toString());
								}
							}
							
						}
						//移泊
						if ("1008".equals(shipStatGroup.getZyjtsdm())) {
							ShipMoor shipMoor = new ShipMoor();
							shipMoor.setShipMoorId(HdUtils.genUuid());
							shipMoor.setShipNo(shipNo);
							shipMoor.setMoorId("UY");
							shipMoor.setWorkBegTim(new Timestamp(shipStatGroup.getQssj().getTime()));
							shipMoor.setWorkEndTim(new Timestamp(shipStatGroup.getJssj().getTime()));
							shipMoor.setBerthCod(shipStatGroup.getBwdm());
							String jpqlc = "select a from ShipMoor a where a.shipNo=:shipNo and a.moorId=:moorId and a.workBegTim=:workBegTim";
							QueryParamLs paramLsc = new QueryParamLs();
							paramLsc.addParam("shipNo", shipNo);
							paramLsc.addParam("moorId", "UY");
							paramLsc.addParam("workBegTim", new Timestamp(shipStatGroup.getQssj().getTime()));
							List<ShipMoor> shipmoorList = JpaUtils.findAll(jpqlc, paramLsc);
							if (shipmoorList.size() > 0) {
								for (ShipMoor sm : shipmoorList) {
									sm.setWorkEndTim(new Timestamp(shipStatGroup.getJssj().getTime()));
									sm.setBerthCod(shipStatGroup.getBwdm());
									JpaUtils.update(sm);
								}
							} else {
								JpaUtils.save(shipMoor);
							}
						}
					}
				}

			}
		} else {
			throw new HdRunTimeException("获取停时失败，请先确认集团调度系统是否有数据！");
		}

	}
	public static String _10_to_62(long number, int length){  
        Long rest=number;  
        Stack<Character> stack=new Stack<Character>();  
        StringBuilder result=new StringBuilder(0);  
        while(rest!=0){  
            stack.add(charSet[new Long((rest-(rest/62)*62)).intValue()]);  
            rest=rest/62;         
        }  
        for(;!stack.isEmpty();){  
            result.append(stack.pop());  
        }  
        int result_length = result.length();  
        StringBuilder temp0 = new StringBuilder();  
        for(int i = 0; i < length - result_length; i++){  
            temp0.append('0');  
        }  
          
        return temp0.toString() + result.toString();  
 
   }  
	public void importBilling(String shipNo, String ieId, String dockCod, String feeTon) {
		Ship ship = JpaUtils.findById(Ship.class, shipNo);
		JSONObject jsonObj = new JSONObject();
		if (ship != null) {
			jsonObj.put("oid", ship.getShipNo());
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
			jsonObj.put("to_port_tim", ship.getToPortTim());
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
//			System.out.println("发送 POST 请求出现异常！" + e);
//			e.printStackTrace();
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
}
