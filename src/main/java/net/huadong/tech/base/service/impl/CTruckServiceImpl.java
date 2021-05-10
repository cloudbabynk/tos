package net.huadong.tech.base.service.impl;
import com.alibaba.dubbo.config.annotation.Service;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.bean.HdRunTimeException;
import net.huadong.tech.base.entity.CTruck;
import net.huadong.tech.base.service.CTruckService;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.idev.hdmessagecode.HdMessageCode;

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
public class CTruckServiceImpl implements CTruckService {
	@Override
	public HdEzuiDatagridData find(HdQuery hdQuery) {
		String jpql="select a from CTruck a where 1=1 ";
		String truckNo = hdQuery.getStr("truckNo");
		String platNo = hdQuery.getStr("platNo");
		String truckUnit = hdQuery.getStr("truckUnit");
		QueryParamLs paramLs = new QueryParamLs();
		if(HdUtils.strNotNull(truckNo)){
			jpql += "and a.truckNo =:truckNo ";
			paramLs.addParam("truckNo", truckNo);
		}
		if(HdUtils.strNotNull(platNo)){
			jpql += "and a.platNo like :platNo ";
			paramLs.addParam("platNo", "%"+platNo+"%");
		}
		if(HdUtils.strNotNull(truckUnit)){
			jpql += "and a.truckUnit =:truckUnit ";
			paramLs.addParam("truckUnit", truckUnit);
		}
		jpql += "order by a.recTim desc";
		return JpaUtils.findByEz(jpql, paramLs , hdQuery);
	}

	@Override
	public HdMessageCode save(HdEzuiSaveDatagridData<CTruck> hdEzuiSaveDatagridData) {
		// TODO Auto-generated method stub
		return JpaUtils.save(hdEzuiSaveDatagridData);
	}

	@Transactional
	public void removeAll(String truckNos) {
		List<String> truckNoList = HdUtils.paraseStrs(truckNos);
		for (String truckNo : truckNoList) {
			JpaUtils.remove(CTruck.class, truckNo);
		}
	}
	
	@Override
	public CTruck findone(String truckNo) {
		CTruck cTruck = JpaUtils.findById(CTruck.class, truckNo);
		return cTruck;

	}
	
	@Override
	public HdMessageCode saveone(@RequestBody CTruck cTruck) {
		String brandCod = cTruck.getTruckNo();
		CTruck ctruck = JpaUtils.findById(CTruck.class, brandCod);
		if(ctruck != null){
			JpaUtils.update(cTruck);
		}else{
			JpaUtils.save(cTruck);
		}
		return HdUtils.genMsg();
	}
	
	@Override
	public HdMessageCode findCTruck(String truckNo) {
		if(HdUtils.strNotNull(truckNo)){
			CTruck cTruck = JpaUtils.findById(CTruck.class, truckNo);
			if(cTruck != null){
				throw new HdRunTimeException("该代码已存在，请重新输入！");// 主键已存在
			}
		}
		return HdUtils.genMsg();
	}
	
	@Override
	public HdMessageCode ifForbid(String truckNo) {
		if(HdUtils.strNotNull(truckNo)){
			CTruck cTruck = JpaUtils.findById(CTruck.class, truckNo);
			if(CTruck.ForBId.equals(cTruck.getForbidId())){
				throw new HdRunTimeException("车辆被禁，不能作业!不能进行其他操作");// 进闸车辆被禁
			}
		}
		return HdUtils.genMsg();
	}
}

