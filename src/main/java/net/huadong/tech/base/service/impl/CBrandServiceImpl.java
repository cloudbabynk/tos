package net.huadong.tech.base.service.impl;
import com.alibaba.dubbo.config.annotation.Service;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.bean.HdRunTimeException;
import net.huadong.tech.base.entity.CBrand;
import net.huadong.tech.base.entity.CFactory;
import net.huadong.tech.base.service.CBrandService;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.idev.hdmessagecode.HdMessageCode;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
public class CBrandServiceImpl implements CBrandService {
	@Override
	public HdEzuiDatagridData find(HdQuery hdQuery) {
		String jpql="select a from CBrand a where 1=1 ";
		String brandShort = hdQuery.getStr("brandShort");
		String brandNam = hdQuery.getStr("brandNam");
		String factoryCod = hdQuery.getStr("factoryCod");
		String checkFlag = hdQuery.getStr("checkFlag");
		QueryParamLs paramLs = new QueryParamLs();
		if(HdUtils.strNotNull(brandShort)){
			jpql += "and a.brandShort like :brandShort ";
			paramLs.addParam("brandShort", "%" + brandShort + "%");
		}
		if(HdUtils.strNotNull(brandNam)){
			jpql += "and a.brandNam like :brandNam ";
			paramLs.addParam("brandNam", "%" + brandNam + "%");
		}
		if(HdUtils.strNotNull(factoryCod)){
			jpql += "and a.factoryCod =:factoryCod ";
			paramLs.addParam("factoryCod", factoryCod);
		}
		if(HdUtils.strNotNull(checkFlag)){
			jpql += "and a.checkFlag =:checkFlag ";
			paramLs.addParam("checkFlag", checkFlag);
		}
		jpql += "order by a.recTim desc";
		HdEzuiDatagridData result = JpaUtils.findByEz(jpql, paramLs , hdQuery);
		List<CBrand> cBrandList = result.getRows();
		for(CBrand cBrand : cBrandList){
			if(HdUtils.strNotNull(cBrand.getFactoryCod())){
			cBrand.setFactoryCodNam(JpaUtils.findById(CFactory.class, cBrand.getFactoryCod()).getFactoryNam());
		}
		}
		return result;
	}

	@Override
	public HdMessageCode save(HdEzuiSaveDatagridData<CBrand> hdEzuiSaveDatagridData) {
		// TODO Auto-generated method stub
		return JpaUtils.save(hdEzuiSaveDatagridData);
	}

	@Transactional
	public void removeAll(String brandCods) {
		List<String> brandCodList = HdUtils.paraseStrs(brandCods);
		for (String brandCod : brandCodList) {
			JpaUtils.remove(CBrand.class, brandCod);
		}
	}
	
	@Transactional
	public void checkAll(String brandCods) {
		List<String> brandCodList = HdUtils.paraseStrs(brandCods);
		for (String brandCod : brandCodList) {
			CBrand cBrand = JpaUtils.findById(CBrand.class, brandCod);
			if(CBrand.N.equals(cBrand.getCheckFlag())){
				cBrand.setCheckFlag(CBrand.Y);
			}
			JpaUtils.update(cBrand);
		}
	}
	
	@Override
	public CBrand findone(String brandCod) {
		CBrand cBrand = JpaUtils.findById(CBrand.class, brandCod);
		return cBrand;

	}
	
	@Override
	public HdMessageCode saveone(@RequestBody CBrand cBrand) {
		if(HdUtils.strNotNull(cBrand.getBrandCod())){
			JpaUtils.update(cBrand);
		}else{
			String datetime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()); 
			cBrand.setBrandCod(datetime.substring(4));
			JpaUtils.save(cBrand);
		}
		return HdUtils.genMsg();
	}
	
	@Override
	public HdMessageCode findCBrand(String brandEname) {
		if(HdUtils.strNotNull(brandEname)){
			String jpql = "select a from CBrand a where a.brandEname =:brandEname";
			QueryParamLs paramLs = new QueryParamLs();
			paramLs.addParam("brandEname", brandEname);
			List<CBrand> cBrandList = JpaUtils.findAll(jpql, paramLs);
			if (cBrandList.size()>0){
				throw new HdRunTimeException("该代码已存在，请重新输入！");// 主键已存在
			}
		}
		return HdUtils.genMsg();
	}
	
	
	@Transactional
	@Override
	public HdMessageCode generate(String shipworkTim) {
		List<Object> list = new ArrayList<Object>();
		list.add(shipworkTim.substring(0, 4));
		list.add(shipworkTim.substring(5, 7));
		List<String> list1 = new ArrayList<>();
		String outInfo = "";
		JpaUtils.executeOracleProcWithResult("p_rpt_create_car_stat", list, list1, list.size()+1);
		outInfo = list1.get(0);
		if (!"ok".equals(outInfo)){
			throw new HdRunTimeException(outInfo);
		}
		return HdUtils.genMsg();
	}
}

