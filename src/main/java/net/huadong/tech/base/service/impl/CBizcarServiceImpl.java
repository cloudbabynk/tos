package net.huadong.tech.base.service.impl;
import com.alibaba.dubbo.config.annotation.Service;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.bean.HdRunTimeException;
import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.entity.CBizcar;
import net.huadong.tech.base.entity.CCarKind;
import net.huadong.tech.base.entity.CCarTyp;
import net.huadong.tech.base.entity.CClientCod;
import net.huadong.tech.base.entity.CDock;
import net.huadong.tech.base.service.CBizcarService;
import net.huadong.tech.base.service.CBizcarService;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.tech.shipbill.entity.ContractIeDoc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiQueryParams;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;
import net.huadong.tech.util.HdUtils;

/**
 * @author 
 */
@Component
public class CBizcarServiceImpl implements CBizcarService {
	@Override
	public HdEzuiDatagridData find(HdQuery hdQuery) {
		// TODO Auto-generated method stub
		String jpql="select a from CBizcar a where 1=1 ";
		String bizcarNo = hdQuery.getStr("bizcarNo");
		String platNo = hdQuery.getStr("platNo");
		String driverNam = hdQuery.getStr("driverNam");
		String corpNam = hdQuery.getStr("corpNam");
		String carProp = hdQuery.getStr("carProp");
		String carTyp = hdQuery.getStr("carTyp");
		String carColor = hdQuery.getStr("carColor");
		QueryParamLs paramLs = new QueryParamLs();
		if(HdUtils.strNotNull(carColor)){
			jpql += "and a.carColor like :carColor ";
			paramLs.addParam("carColor", "%" + carColor + "%");
		}
		if(HdUtils.strNotNull(carTyp)){
			jpql += "and a.carTyp like :carTyp ";
			paramLs.addParam("carTyp", "%" + carTyp + "%");
		}
		if(HdUtils.strNotNull(carProp)){
			if (carProp.equals("*")) {
			}else {
				jpql += "and a.carProp =:carProp ";
				paramLs.addParam("carProp", carProp);
			}
		}
		if(HdUtils.strNotNull(bizcarNo)){
			jpql += "and a.bizcarNo =:bizcarNo ";
			paramLs.addParam("bizcarNo", bizcarNo);
		}
		if(HdUtils.strNotNull(platNo)){
			jpql += "and a.platNo like :platNo ";
			paramLs.addParam("platNo", "%" + platNo + "%");
		}
		if(HdUtils.strNotNull(driverNam)){
			jpql += "and a.driverNam like :driverNam ";
			paramLs.addParam("driverNam", "%" + driverNam + "%");
		}
		if(HdUtils.strNotNull(corpNam)){
			jpql += "and a.corpNam like :corpNam ";
			paramLs.addParam("corpNam", "%" + corpNam + "%");
		}
		jpql += "order by a.platNo desc";
		HdEzuiDatagridData result= JpaUtils.findByEz(jpql, paramLs , hdQuery);
		List<CBizcar> cBizcarList = result.getRows();
		if(cBizcarList.size()!=0){
		for (CBizcar cBizcar : cBizcarList) {
			cBizcar.setCarProp(HdUtils.getSysCodeName("CAR_PROP", cBizcar.getCarProp()));
			//cBizcar.setCarTyp(HdUtils.getSysCodeName("car_Typ", cBizcar.getCarTyp()));
			}
		}
		return result;
	}

	@Override
	public HdMessageCode save(HdEzuiSaveDatagridData<CBizcar> hdEzuiSaveDatagridData) {
		// TODO Auto-generated method stub
	return JpaUtils.save(hdEzuiSaveDatagridData);
	}
	@Transactional
	public void removeAll(String bizcarNos) {
		// TODO Auto-generated method stub
		List<String> bizcarNoList = HdUtils.paraseStrs(bizcarNos);
		for (String bizcarNo : bizcarNoList) {
			JpaUtils.remove(CBizcar.class, bizcarNo);
		}	
	}

	@Override
	public CBizcar findone(String bizcarNo) {
		// TODO Auto-generated method stub
		CBizcar cBizcar = JpaUtils.findById(CBizcar.class, bizcarNo);
		return cBizcar;

	}

	@Override
	public HdMessageCode saveone(@RequestBody CBizcar cBizcar) {
		// TODO Auto-generated method stub
		String bizcarNo = cBizcar.getBizcarNo();
		CBizcar cbizcar = JpaUtils.findById(CBizcar.class, bizcarNo);
		if(cbizcar != null){
			JpaUtils.update(cBizcar);
		}else{
			JpaUtils.save(cBizcar);
		}
		return HdUtils.genMsg();
	}
	@Override
	public HdMessageCode findCBizcar(String bizcarNo) {
		if(HdUtils.strNotNull(bizcarNo)){
			CBizcar CBizcar = JpaUtils.findById(CBizcar.class, bizcarNo);
			if(CBizcar != null){
				throw new HdRunTimeException("该代码已存在，请重新输入！");// 主键已存在
			}
		}
		return HdUtils.genMsg();
	}
}

