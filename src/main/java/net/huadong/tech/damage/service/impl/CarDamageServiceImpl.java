package net.huadong.tech.damage.service.impl;
import com.alibaba.dubbo.config.annotation.Service;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.bean.HdRunTimeException;
import net.huadong.tech.base.entity.CBrand;
import net.huadong.tech.base.entity.CCarKind;
import net.huadong.tech.base.entity.CCarTyp;
import net.huadong.tech.base.entity.CDamage;
import net.huadong.tech.base.entity.CDamgArea;
import net.huadong.tech.base.entity.CDamgLevel;
import net.huadong.tech.base.entity.CEmpTyp;
import net.huadong.tech.base.entity.CEmployee;
import net.huadong.tech.custom.entity.CustomRelease;
import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.damage.entity.CarDamage;
import net.huadong.tech.damage.service.CarDamageService;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.tech.his.entity.PortCarBak;
import net.huadong.tech.shipbill.entity.BillCar;
import net.huadong.tech.shipbill.entity.PortCar;

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
public class CarDamageServiceImpl implements CarDamageService {
	@Override
	public HdEzuiDatagridData find(HdQuery hdQuery) {
		// TODO Auto-generated method stub
		String jpql="select a from CarDamage a where 1=1 ";
		String portCarNo = hdQuery.getStr("portCarNo");
		String damCod = hdQuery.getStr("damCod");
		String damArea = hdQuery.getStr("damArea");
		String damLevel = hdQuery.getStr("damLevel");
		QueryParamLs paramLs = new QueryParamLs();
		if(HdUtils.strNotNull(portCarNo)){
			jpql += "and a.portCarNo =:portCarNo ";
			paramLs.addParam("portCarNo", portCarNo);
		}
		if(HdUtils.strNotNull(damCod)){
			jpql += "and a.damCod =:damCod ";
			paramLs.addParam("damCod", damCod);
		}
		if(HdUtils.strNotNull(damArea)){
			jpql += "and a.damArea =:damArea ";
			paramLs.addParam("damArea", damArea);
		}
		if(HdUtils.strNotNull(damLevel)){
			jpql += "and a.damLevel =:damLevel ";
			paramLs.addParam("damLevel", damLevel);
		}
		jpql += "order by a.recTim desc";
		HdEzuiDatagridData results= JpaUtils.findByEz(jpql, paramLs , hdQuery);
		List<CarDamage> carDamageList = results.getRows();
		for(CarDamage carDamage : carDamageList){
			carDamage.setDamCod(JpaUtils.findById(CDamage.class, carDamage.getDamCod()).getDamNam());
			carDamage.setDamArea(JpaUtils.findById(CDamgArea.class, carDamage.getDamArea()).getDamgArea());
			carDamage.setDamLevel(JpaUtils.findById(CDamgLevel.class, carDamage.getDamLevel()).getDamgLevel());
		}
		return results;
	}
	public HdEzuiDatagridData findPortCar(HdQuery hdQuery) {
		// TODO Auto-generated method stub
		String jpql="select a from PortCar a where 1=1 ";
		String vinNo = hdQuery.getStr("vinNo");
		String shipNo = hdQuery.getStr("shipNo");
		String billNo = hdQuery.getStr("billNo");
		String cyAreaNo = hdQuery.getStr("cyAreaNo");
		String rfidCardNo = hdQuery.getStr("rfidCardNo");
		String brandCod = hdQuery.getStr("brandCod");
		String cyRowNo = hdQuery.getStr("cyRowNo");
		String portCarNo = hdQuery.getStr("portCarNo");
		String carTyp = hdQuery.getStr("carTyp");
		String cyBayNo = hdQuery.getStr("cyBayNo");
		QueryParamLs paramLs = new QueryParamLs();
		if(HdUtils.strNotNull(shipNo)){
			jpql += "and a.shipNo =:shipNo ";
			paramLs.addParam("shipNo", shipNo);
		}
		if(HdUtils.strNotNull(portCarNo)){
			jpql += "and a.portCarNo =:portCarNo ";
			paramLs.addParam("portCarNo", portCarNo);
		}
		if(HdUtils.strNotNull(vinNo)){
			jpql += "and a.vinNo =:vinNo ";
			paramLs.addParam("vinNo", vinNo);
		}
		if(HdUtils.strNotNull(billNo)){
			jpql += "and a.billNo =:billNo ";
			paramLs.addParam("billNo", billNo);
		}
		if(HdUtils.strNotNull(cyAreaNo)){
			jpql += "and a.cyAreaNo =:cyAreaNo ";
			paramLs.addParam("cyAreaNo", cyAreaNo);
		}
		if(HdUtils.strNotNull(rfidCardNo)){
			jpql += "and a.rfidCardNo =:rfidCardNo ";
			paramLs.addParam("rfidCardNo", rfidCardNo);
		}
		if(HdUtils.strNotNull(brandCod)){
			jpql += "and a.brandCod =:brandCod ";
			paramLs.addParam("brandCod", brandCod);
		}
		if(HdUtils.strNotNull(cyRowNo)){
			jpql += "and a.cyRowNo =:cyRowNo ";
			paramLs.addParam("cyRowNo", cyRowNo);
		}
		if(HdUtils.strNotNull(cyBayNo)){
			jpql += "and a.cyBayNo =:cyBayNo ";
			paramLs.addParam("cyBayNo", cyBayNo);
		}
		if(HdUtils.strNotNull(carTyp)){
			jpql += "and a.carTyp =:carTyp ";
			paramLs.addParam("carTyp", carTyp);
		}
		jpql += "order by a.recTim desc";
		HdEzuiDatagridData result = JpaUtils.findByEz(jpql, paramLs, hdQuery);
		List<PortCar> portCarList = result.getRows();
		for (PortCar pc : portCarList) {
			if (HdUtils.strNotNull(pc.getCarTyp())) {
				CCarTyp ccartyp = JpaUtils.findById(CCarTyp.class, pc.getCarTyp());
				pc.setCarTypNam(ccartyp.getCarTypNam());
			}
			if (HdUtils.strNotNull(pc.getCarKind())) {
				CCarKind carkind = JpaUtils.findById(CCarKind.class, pc.getCarKind());
				pc.setCarKindNam(carkind.getCarKindNam());
			}
			if (HdUtils.strNotNull(pc.getBrandCod())) {
				CBrand cbrand = JpaUtils.findById(CBrand.class, pc.getBrandCod());
				pc.setBrandNam(cbrand.getBrandNam());
			}
		}
		return result;
	}
	@Override
	public HdMessageCode save(HdEzuiSaveDatagridData<CarDamage> hdEzuiSaveDatagridData) {
		// TODO Auto-generated method stub
	return JpaUtils.save(hdEzuiSaveDatagridData);
	}
	@Transactional
	public void removeAll(String cardamagIds) {
		List<String> cardamagIdList = HdUtils.paraseStrs(cardamagIds);
		for (String cardamagId : cardamagIdList) {
			if (checkExist(cardamagId)) {
			throw new HdRunTimeException("该流水号车辆在场或有历史数据！不能删除！");
			}
			JpaUtils.remove(CarDamage.class, cardamagId);
		}	
	}
	//检验是否在场

		public boolean checkExist(String cardamagId) {
			CarDamage carDamage=JpaUtils.findById(CarDamage.class, cardamagId);
			PortCar portcar=JpaUtils.findById(PortCar.class, carDamage.getPortCarNo());
			//PortCarBak portcarbak=JpaUtils.findById(PortCarBak.class, carDamage.getPortCarNo());
		    if(portcar!=null){
		    	return true;
		    }
		    /*if(portcarbak!=null){
		    	return true;
		    }*/
			return false;
		}
	@Override
	public CarDamage findone(String cardamagId) {
		// TODO Auto-generated method stub
		CarDamage carDamage = JpaUtils.findById(CarDamage.class, cardamagId);
		return carDamage;

	}

	@Override
	public HdMessageCode saveone(@RequestBody CarDamage carDamage) {
		// TODO Auto-generated method stub
		String cardamagId = carDamage.getCardamagId();
		CarDamage cardamage = JpaUtils.findById(CarDamage.class, cardamagId);
		if(cardamage != null){
			JpaUtils.update(carDamage);
			String jpql="update PortCar a set a.damId='1' ,a.damCod=:damCod ,a.damArea=:damArea , a.damLevel=:damLevel where a.portCarNo=:portCarNo ";
			QueryParamLs paramLs = new QueryParamLs();
			paramLs.addParam("portCarNo",carDamage.getPortCarNo());
			paramLs.addParam("damCod",carDamage.getDamCod());
			paramLs.addParam("damArea",carDamage.getDamArea());
			paramLs.addParam("damLevel",carDamage.getDamLevel());
			JpaUtils.execUpdate(jpql, paramLs);
		}else{
			carDamage.setCardamagId(HdUtils.generateUUID());
			JpaUtils.save(carDamage);
				String jpql="update PortCar a set a.damId='1' ,a.damCod=:damCod ,a.damArea=:damArea , a.damLevel=:damLevel where a.portCarNo=:portCarNo ";
				QueryParamLs paramLs = new QueryParamLs();
				paramLs.addParam("portCarNo",carDamage.getPortCarNo());
				paramLs.addParam("damCod",carDamage.getDamCod());
				paramLs.addParam("damArea",carDamage.getDamArea());
				paramLs.addParam("damLevel",carDamage.getDamLevel());
				JpaUtils.execUpdate(jpql, paramLs);
		}
		return HdUtils.genMsg();
	}
	@Override
	public HdMessageCode findCarDamage(String cardamagId) {
		if(HdUtils.strNotNull(cardamagId)){
			CarDamage carDamage = JpaUtils.findById(CarDamage.class, cardamagId);
			if(carDamage != null){
				throw new HdRunTimeException("该代码已存在，请重新输入！");// 主键已存在
			}
		}
		return HdUtils.genMsg();
	}
	
}

