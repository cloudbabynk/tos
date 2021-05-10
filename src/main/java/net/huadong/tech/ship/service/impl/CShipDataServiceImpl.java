package net.huadong.tech.ship.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.huadong.tech.Interface.entity.VGroupCorpShipData;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.bean.HdRunTimeException;
import net.huadong.tech.base.entity.CClientCod;
import net.huadong.tech.base.entity.CCountry;
import net.huadong.tech.base.entity.CPort;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.tech.ship.entity.CShipData;
import net.huadong.tech.ship.entity.CShipDataGroup;
import net.huadong.tech.ship.entity.CShipDataHis;
import net.huadong.tech.ship.entity.Ship;
import net.huadong.tech.ship.service.CShipDataService;
import net.huadong.idev.hdmessagecode.HdMessageCode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiQueryParams;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;
import net.huadong.tech.util.HdUtils;
import net.sf.json.JSON;
import net.sf.json.JSONObject;

/**
 * @author
 */
@Component
public class CShipDataServiceImpl implements CShipDataService {

	@Value("${cshipdata.token}")
	private String token;
	
	@Value("${api.service.ip}")
	private String apiServiceIp;

	@Override
	public HdEzuiDatagridData find(HdQuery hdQuery) {
		String jpql = "select a from CShipData a where 1=1 ";
		String cShipNam = hdQuery.getStr("cShipNam");
		String shipImo = hdQuery.getStr("shipImo");
		String eShipNam = hdQuery.getStr("eShipNam");
		QueryParamLs paramLs = new QueryParamLs();
		if (HdUtils.strNotNull(cShipNam)) {
			jpql += "and a.cShipNam like :cShipNam ";
			paramLs.addParam("cShipNam", "%" + cShipNam + "%");
		}
		if (HdUtils.strNotNull(shipImo)) {
			jpql += "and a.shipImo like :shipImo ";
			paramLs.addParam("shipImo", "%" + shipImo + "%");
		}
		if (HdUtils.strNotNull(eShipNam)) {
			jpql += "and a.eShipNam like :eShipNam ";
			paramLs.addParam("eShipNam", "%" + eShipNam + "%");
		}
		jpql += "order by a.recTim desc";
		HdEzuiDatagridData result = JpaUtils.findByEz(jpql, paramLs, hdQuery);
		List<CShipData> cShipDataList = result.getRows();
		for (CShipData cShipData : cShipDataList) {
			CCountry cCountry = JpaUtils.findById(CCountry.class, cShipData.getCountryCod());
			if (cCountry != null) {
				cShipData.setCountryCodNam(cCountry.getcCountryNam());
			}
			CClientCod cClientCod = JpaUtils.findById(CClientCod.class, cShipData.getShipCorpCod());
			if (cClientCod != null) {
				cShipData.setShipCorpCodNam(cClientCod.getcClientNam());
			}
		}
		return result;
	}
	
	@Override
	public HdEzuiDatagridData findHisData(HdQuery hdQuery) {
		String jpql = "select a from CShipDataHis a where 1=1 ";
		String shipCodId = hdQuery.getStr("shipCodId");
		QueryParamLs paramLs = new QueryParamLs();
		if (HdUtils.strNotNull(shipCodId)) {
			jpql += "and a.shipCodId =:shipCodId ";
			paramLs.addParam("shipCodId", shipCodId);
		}
		jpql += "order by a.recTim desc";
		HdEzuiDatagridData result = JpaUtils.findByEz(jpql, paramLs, hdQuery);
		List<CShipDataHis> cShipDataList = result.getRows();
		for (CShipDataHis cShipData : cShipDataList) {
			CCountry cCountry = JpaUtils.findById(CCountry.class, cShipData.getCountryCod());
			if (cCountry != null) {
				cShipData.setCountryCodNam(cCountry.getcCountryNam());
			}
			CClientCod cClientCod = JpaUtils.findById(CClientCod.class, cShipData.getShipCorpCod());
			if (cClientCod != null) {
				cShipData.setShipCorpCodNam(cClientCod.getcClientNam());
			}
		}
		return result;
	}

	@Override
	public HdEzuiDatagridData findJt(HdQuery hdQuery) {
		String resp = null;
		// application里设置的参数
		JSONObject obj = new JSONObject();
		String shipImo = hdQuery.getStr("shipImo");
		String cShipNam = hdQuery.getStr("cShipNam");
		String eShipNam = hdQuery.getStr("eShipNam");
		if (HdUtils.strNotNull(shipImo)) {
			obj.put("Imo", shipImo);
		} else if (HdUtils.strNotNull(eShipNam)) {
			obj.put("Ywcm", eShipNam);
		} else if (HdUtils.strNotNull(cShipNam)) {
			obj.put("Zwcm", cShipNam);
		}
		obj.put("token", token);
		String query = obj.toString();

		String urlStr = apiServiceIp + "8091/ScheduleSysWebApi/getShipData";
		// 返回结果集
		List<CShipDataGroup> list = new ArrayList<CShipDataGroup>();
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

			try (BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream(),"UTF-8"))) {
				String lines;
				StringBuffer sbf = new StringBuffer();
				while ((lines = reader.readLine()) != null) {
//					lines = new String(lines.getBytes(), "utf-8");
					sbf.append(lines);
				}
				resp = sbf.toString();
			}
			con.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Gson gson = new Gson();
			list = gson.fromJson(resp, new TypeToken<List<CShipDataGroup>>() {
			}.getType());
		}
		HdEzuiDatagridData result = new HdEzuiDatagridData();
		List<CShipData> cShipDatalist = new ArrayList();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		for (CShipDataGroup cShipDataGroup : list) {
			CShipData cShipData = new CShipData();
			cShipData.setShipCod(cShipDataGroup.getCbdm());
			cShipData.setShipImo(cShipDataGroup.getCjsdm());
			cShipData.setShipAgentCod(cShipDataGroup.getCddm());
			cShipData.setShipCorpCod(cShipDataGroup.getCgsdm());
			cShipData.setShipProperty(cShipDataGroup.getCbxz());
			cShipData.setShipTyp(cShipDataGroup.getCbzldm());
			cShipData.setcShipNam(cShipDataGroup.getZwcm());
			cShipData.seteShipNam(cShipDataGroup.getYwcm());
			cShipData.setCountryCod(cShipDataGroup.getGjdm());
			if(HdUtils.strNotNull(cShipDataGroup.getCc())){
				cShipData.setShipLongNum(new BigDecimal(cShipDataGroup.getCc()));
			}
			if(HdUtils.strNotNull(cShipDataGroup.getCk())){
				cShipData.setShipWidthNum(new BigDecimal(cShipDataGroup.getCk()));
			}
			if(HdUtils.strNotNull(cShipDataGroup.getCs())){
				cShipData.setTypeDeep(new BigDecimal(cShipDataGroup.getCs()));
			}
			if(HdUtils.strNotNull(cShipDataGroup.getZzd())){
				cShipData.setShipDeadWt(new BigDecimal(cShipDataGroup.getZzd()));
			}
			if(HdUtils.strNotNull(cShipDataGroup.getCsf())){
				cShipData.setShipSpeed(new BigDecimal(cShipDataGroup.getCsf()));
			}
			if(HdUtils.strNotNull(cShipDataGroup.getKzscs())){
				cShipData.setEmptyDraftFront(new BigDecimal(cShipDataGroup.getKzscs()));
			}
			if(HdUtils.strNotNull(cShipDataGroup.getKzwcs())){
				cShipData.setEmptyDraftBack(new BigDecimal(cShipDataGroup.getKzwcs()));
			}
			if(HdUtils.strNotNull(cShipDataGroup.getMzscs())){
				cShipData.setFullDraftFront(new BigDecimal(cShipDataGroup.getMzscs()));
			}
			if(HdUtils.strNotNull(cShipDataGroup.getMzwcs())){
				cShipData.setFullDraftBack(new BigDecimal(cShipDataGroup.getMzwcs()));
			}
			if(HdUtils.strNotNull(cShipDataGroup.getJzny())){
				cShipData.setBuildDte(format.format(new Date(Long.valueOf(cShipDataGroup.getJzny()))));
			}
			cShipData.setShipCall(cShipDataGroup.getHh());
			cShipData.setShipShort(cShipDataGroup.getPym());
			cShipData.setPushId(cShipDataGroup.getYwct());
			cShipData.setPushPower(cShipDataGroup.getCtgl());
			cShipData.setMmsiShipCod(cShipDataGroup.getMmsi());
	//		cShipData.setShipGrossWgt(new BigDecimal(cShipDataGroup.getZd()));
	//		cShipData.setShipNetWgt(new BigDecimal(cShipDataGroup.getJd()));
			cShipDatalist.add(cShipData);
		}
		for (CShipData cShipData : cShipDatalist) {
			CCountry cCountry = JpaUtils.findById(CCountry.class, cShipData.getCountryCod());
			if (cCountry != null) {
				cShipData.setCountryCodNam(cCountry.getcCountryNam());
			}
			CClientCod cClientCod = JpaUtils.findById(CClientCod.class, cShipData.getShipCorpCod());
			if (cClientCod != null) {
				cShipData.setShipCorpCodNam(cClientCod.getcClientNam());
			}
		}
		result.setRows(cShipDatalist);

		return result;
	}
	
	@Override
	public HdEzuiDatagridData findNewJt(HdQuery hdQuery) {
		String shipImo = hdQuery.getStr("shipImo");
		String cShipNam = hdQuery.getStr("cShipNam");
		String eShipNam = hdQuery.getStr("eShipNam");
		String jpql = "select a from VGroupCorpShipData a where 1=1 ";
		QueryParamLs paramLs = new QueryParamLs();
		if (HdUtils.strNotNull(shipImo)) {
			jpql += "and a.imo =:imo";
			paramLs.addParam("imo", shipImo);
		} else if (HdUtils.strNotNull(eShipNam)) {
			jpql += "and a.enShipName =:enShipName";
			paramLs.addParam("enShipName", eShipNam);
		} else if (HdUtils.strNotNull(cShipNam)) {
			jpql += "and a.shipName =:shipName";
			paramLs.addParam("shipName", cShipNam);
		} else {
			jpql += "and a.imo =:imo";
			paramLs.addParam("imo", "123456789##");//为了加快页面打开速度
		}
		// 返回结果集
		List<VGroupCorpShipData> list = JpaUtils.findAll(jpql, paramLs);
		HdEzuiDatagridData result = new HdEzuiDatagridData();
		List<CShipData> cShipDatalist = new ArrayList();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		for (VGroupCorpShipData bean : list) {
			CShipData cShipData = new CShipData();
			cShipData.setShipCod(bean.getShipCode());
			cShipData.setShipImo(bean.getImo());
			cShipData.setShipAgentCod(bean.getShipAgentCode());
			cShipData.setShipCorpCod(bean.getShiplineCode());
			cShipData.setShipProperty(bean.getShipTradeCode());
			cShipData.setShipTyp(bean.getShipTypeCode());
			cShipData.setcShipNam(bean.getShipName());
			cShipData.seteShipNam(bean.getEnShipName());
			cShipData.setCountryCod(bean.getCountryCode());
			if(bean.getShipLength() != null){
				cShipData.setShipLongNum(bean.getShipLength());
			}
			if(bean.getShipWidth() != null){
				cShipData.setShipWidthNum(bean.getShipWidth());
			}
			if(bean.getTypeDeep() != null){
				cShipData.setTypeDeep(bean.getTypeDeep());
			}
			if(bean.getSdw() != null){
				cShipData.setShipDeadWt(bean.getSdw());
			}
			if(bean.getSgt() != null){
				cShipData.setShipGrossWgt(bean.getSgt());
			}
			if(bean.getSnt() != null){
				cShipData.setShipNetWgt(bean.getSnt());
			}
			if(bean.getShipSpeed() != null){
				cShipData.setShipSpeed(bean.getShipSpeed());
			}
			if(bean.getEfd() != null){
				cShipData.setEmptyDraftFront(bean.getEfd());
			}
			if(bean.getEfd() != null){
				cShipData.setEmptyDraftBack(bean.getEad());
			}
			
			if(bean.getFfd() != null){
				cShipData.setFullDraftFront(bean.getFfd());
			}
			if(bean.getFad() != null){
				cShipData.setFullDraftFront(bean.getFad());
			}
			if(bean.getBuildDate() != null){
				cShipData.setBuildDte(format.format(bean.getBuildDate()));
			}
			cShipData.setShipCall(bean.getCallSign());
//			cShipData.setShipShort(cShipDataGroup.getPym());
//			cShipData.setPushId(cShipDataGroup.getYwct());
//			cShipData.setPushPower(cShipDataGroup.getCtgl());
			cShipData.setMmsiShipCod(bean.getMmsi());
			cShipDatalist.add(cShipData);
		}
		for (CShipData cShipData : cShipDatalist) {
			CCountry cCountry = JpaUtils.findById(CCountry.class, cShipData.getCountryCod());
			if (cCountry != null) {
				cShipData.setCountryCodNam(cCountry.getcCountryNam());
			}
			CClientCod cClientCod = JpaUtils.findById(CClientCod.class, cShipData.getShipCorpCod());
			if (cClientCod != null) {
				cShipData.setShipCorpCodNam(cClientCod.getcClientNam());
			}
		}
		result.setRows(cShipDatalist);

		return result;
	}

	@Override
	public HdMessageCode save(HdEzuiSaveDatagridData<CShipData> hdEzuiSaveDatagridData) {
		// TODO Auto-generated method stub
		List<CShipData> cShipDataList = hdEzuiSaveDatagridData.getInsertedRows();
		String jpql = "select a from CShipData a where a.shipCod =:shipCod";
		QueryParamLs paramLs = new QueryParamLs();
		for (CShipData cShipData : cShipDataList) {
			paramLs.addParam("shipCod", cShipData.getShipCod());
			List<CShipData> cShipDataList1 = JpaUtils.findAll(jpql, paramLs);
			if(cShipDataList1.size()>0){
				throw new HdRunTimeException("该船已存在,请更新！");
			}
			cShipData.setShipCodId(HdUtils.genUuid());
		}
		return JpaUtils.save(hdEzuiSaveDatagridData);
	}
	
	@Override
	public HdMessageCode saveData(HdEzuiSaveDatagridData<CShipData> hdEzuiSaveDatagridData,String shipCodId) {
		// TODO Auto-generated method stub
		List<CShipData> cShipDataList = hdEzuiSaveDatagridData.getInsertedRows();
		CShipData cShipDataGs = JpaUtils.findById(CShipData.class, shipCodId);
		if (cShipDataGs == null){
			throw new HdRunTimeException("请确认公司船舶数据存在！");
		}
		for (CShipData cShipData : cShipDataList) {
				cShipDataGs.setShipCod(cShipData.getShipCod());
				cShipDataGs.setShipImo(cShipData.getShipImo());
				cShipDataGs.setShipAgentCod(cShipData.getShipAgentCod());
				cShipDataGs.setShipCorpCod(cShipData.getShipCorpCod());
				cShipDataGs.setShipProperty(cShipData.getShipProperty());
				cShipDataGs.setcShipNam(cShipData.getcShipNam());
				cShipDataGs.seteShipNam(cShipData.geteShipNam());
				cShipDataGs.setShipNetWgt(cShipData.getShipNetWgt());
				cShipDataGs.setShipGrossWgt(cShipData.getShipGrossWgt());
				JpaUtils.update(cShipDataGs);
		}
		return HdUtils.genMsg();
	}

	@Transactional
	public void removeAll(String shipCodIds) {
		List<String> shipCodIdList = HdUtils.paraseStrs(shipCodIds);
		for (String shipCodId : shipCodIdList) {
			if (isExist(shipCodId)) {
				throw new HdRunTimeException("此船信息已被使用,暂时无法删除！");// 船代码重复
			}
			JpaUtils.remove(CShipData.class, shipCodId);
		}
	}

	@Override
	public CShipData findone(String shipCodId) {
		CShipData cShipData = JpaUtils.findById(CShipData.class, shipCodId);
		return cShipData;

	}
	
	@Override
	public CShipDataHis findHis(String id) {
		CShipDataHis cShipDataHis = JpaUtils.findById(CShipDataHis.class, id);
		return cShipDataHis;

	}
	
	@Override
	public CShipData findData(String eShipNam, String shipImo, String cShipNam) {
		String jpql = "select a from CShipData a where 1=1 ";
		QueryParamLs paramLs = new QueryParamLs();
		if(HdUtils.strNotNull(eShipNam)){
			jpql += "and a.eShipNam =:eShipNam ";
			paramLs.addParam("eShipNam", eShipNam);
		}
		if(HdUtils.strNotNull(shipImo)){
			jpql += "and a.shipImo =:shipImo ";
			paramLs.addParam("shipImo", shipImo);
		}
		if(HdUtils.strNotNull(cShipNam)){
			jpql += "and a.cShipNam =:cShipNam ";
			paramLs.addParam("cShipNam", cShipNam);
		}
		CShipData cShipData = new CShipData();
		List<CShipData> cShipDataList = JpaUtils.findAll(jpql, paramLs);
		if(cShipDataList.size() == 1){
			cShipData = cShipDataList.get(0);
		}else if(cShipDataList.size() > 1){
			throw new HdRunTimeException("船舶档案数据有误，请检查！");
		}
		return cShipData;

	}

	@Override
	@Transactional
	public HdMessageCode saveone(@RequestBody CShipData cShipData) {
		if (HdUtils.strNotNull(cShipData.getShipCodId())) {
			JpaUtils.update(cShipData);
		} else {
			if (isNOtExist(cShipData.getShipCod(), "")) {
				throw new HdRunTimeException("船代码已存在");// 船代码重复
			}
			if (isNOtExist("", cShipData.getcShipNam())) {
				throw new HdRunTimeException("中文船名已存在");// 中文船名重复
			}
			cShipData.setShipCodId(HdUtils.genUuid());
			JpaUtils.save(cShipData);
		}
		return HdUtils.genMsg();
	}

	private boolean isNOtExist(String shipCod, String cShipNam) {
		String jpql = "select a from CShipData a where 1=1 ";
		QueryParamLs paramLs = new QueryParamLs();
		if (HdUtils.strNotNull(shipCod)) {
			jpql += "and a.shipCod =:shipCod";
			paramLs.addParam("shipCod", shipCod);
		}
		if (HdUtils.strNotNull(cShipNam)) {
			jpql += "and a.cShipNam =:cShipNam";
			paramLs.addParam("cShipNam", cShipNam);
		}
		List<CShipData> CShipDataList = JpaUtils.findAll(jpql, paramLs);
		if (CShipDataList.size() > 0) {
			return true;
		}
		return false;
	}

	private boolean isExist(String shipCodId) {
		String jpql = "select a from Ship a where a.shipCodId =:shipCodId ";
		QueryParamLs paramLs = new QueryParamLs();
		paramLs.addParam("shipCodId", shipCodId);
		List<Ship> ShipList = JpaUtils.findAll(jpql, paramLs);
		if (ShipList.size() > 0) {
			return true;
		}
		return false;
	}

}
