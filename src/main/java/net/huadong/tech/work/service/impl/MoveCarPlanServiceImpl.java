package net.huadong.tech.work.service.impl;
import com.alibaba.dubbo.config.annotation.Service;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.bean.HdRunTimeException;
import net.huadong.tech.cargo.entity.TruckWork;
import net.huadong.tech.work.entity.MoveCarPlan;
import net.huadong.tech.work.entity.MoveCarPlanBak;
import net.huadong.tech.work.entity.WorkCommand;
import net.huadong.tech.work.entity.WorkQueue;
import net.huadong.tech.work.service.MoveCarPlanService;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.tech.gate.entity.GateTruck;
import net.huadong.tech.gate.entity.GateTruckContract;
import net.huadong.tech.shipbill.entity.PortCar;
import net.huadong.idev.hdmessagecode.HdMessageCode;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
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
public class MoveCarPlanServiceImpl implements MoveCarPlanService {
	@Override
	public HdEzuiDatagridData find(HdQuery hdQuery) {
		String jpql="select a from MoveCarPlan a where 1=1 ";
		String workRunCod = hdQuery.getStr("workRunCod");
		QueryParamLs paramLs = new QueryParamLs();
		String workDte = hdQuery.getStr("workDte");
		if(HdUtils.strNotNull(workDte)){
			jpql += "and a.workDte =:workDte ";
			paramLs.addParam("workDte", HdUtils.strToDate(workDte));
		}
		if(HdUtils.strNotNull(workRunCod)){
			jpql += "and a.workRunCod =:workRunCod ";
			paramLs.addParam("workRunCod", workRunCod);
		}
		jpql += "order by a.recTim desc";
		return JpaUtils.findByEz(jpql, paramLs , hdQuery);
	}
	@Override
	public HdEzuiDatagridData findB(HdQuery hdQuery) {
		String jpql="select a from MoveCarPlan a where 1=1 ";
		String planPlac = hdQuery.getStr("planPlac");
		String dockCod = hdQuery.getStr("dockCod");
		String movePlan = hdQuery.getStr("movePlan");
		QueryParamLs paramLs = new QueryParamLs();
		
		if(HdUtils.strNotNull(planPlac)){
			jpql += "and a.planPlac =:planPlac ";
			paramLs.addParam("planPlac", planPlac);
		}
		if(HdUtils.strNotNull(movePlan)){
			jpql += "and a.movePlan =:movePlan ";
			paramLs.addParam("movePlan", movePlan);
		}
		if(HdUtils.strNotNull(dockCod)){
			jpql += "and a.dockCod =:dockCod ";
			paramLs.addParam("dockCod", dockCod);
		}
		jpql += "order by a.recTim desc";
		return JpaUtils.findByEz(jpql, paramLs , hdQuery);
	}
	@Override
	public HdMessageCode save(HdEzuiSaveDatagridData<MoveCarPlan> hdEzuiSaveDatagridData) {
		// TODO Auto-generated method stub
		return JpaUtils.save(hdEzuiSaveDatagridData);
	}

	@Transactional
	public void removeAll(String moveplanIds) {
		List<String> moveplanIdList = HdUtils.paraseStrs(moveplanIds);
		for (String moveplanId : moveplanIdList) {
			JpaUtils.remove(MoveCarPlan.class, moveplanId);
		}
	}
	
	@Override
	public MoveCarPlan findone(String damCod) {
		MoveCarPlan cDamage = JpaUtils.findById(MoveCarPlan.class, damCod);
		return cDamage;

	}
	
	@Override
	public HdMessageCode saveone(@RequestBody MoveCarPlan moveCarPlan) {
		if(HdUtils.strNotNull(moveCarPlan.getMoveplanId())){
			JpaUtils.update(moveCarPlan);
		}else{
			JpaUtils.save(moveCarPlan);
		}
		return HdUtils.genMsg();
	}
	
	@Transactional
	public HdMessageCode saveAll(String moveplanIds,String workNam,String driver,String planPlac) {
		List<String> moveplanIdList = HdUtils.paraseStrs(moveplanIds);
		for (String moveplanId : moveplanIdList) {
			MoveCarPlan moveCarPlan = JpaUtils.findById(MoveCarPlan.class, moveplanId);
			PortCar portCar = JpaUtils.findById(PortCar.class, moveCarPlan.getPortCarNo());
			if(portCar != null){
				WorkCommand workCommand = new WorkCommand();
				workCommand.setQueueId(HdUtils.genUuid());
				workCommand.setWorkQueueNo(portCar.getContractNo()+"-MV");
				workCommand.setRfidCardNo(portCar.getRfidCardNo().toString());
				workCommand.setWorkTyp("MV");
				BeanUtils.copyProperties(portCar, workCommand);
				JpaUtils.save(workCommand);
				
				
				portCar.setCyPlac(planPlac);
				portCar.setUpdNam(HdUtils.getCurUser().getAccount());
				portCar.setUpdTim(HdUtils.getDateTime());
				JpaUtils.save(portCar);
				
				MoveCarPlanBak moveCarPlanBak = new MoveCarPlanBak();
				BeanUtils.copyProperties(moveCarPlan, moveCarPlanBak);
				JpaUtils.save(moveCarPlanBak);
				JpaUtils.remove(moveCarPlan);
				
				String jpql = "select a from MoveCarPlan a where a.movePlanNo =:movePlanNo";
				QueryParamLs paramLs = new QueryParamLs();
				paramLs.addParam("movePlanNo", moveCarPlan.getMovePlanNo());
				List<MoveCarPlan> moveCarPlanList = JpaUtils.findAll(jpql, paramLs);
				if(moveCarPlanList.size()<=0){
					String jpql1 = "select a from WorkQueue a where a.workTyp = 'MV' and a.contractNo=:contractNo";
					QueryParamLs paramLs1 = new QueryParamLs();
					paramLs1.addParam("contractNo", moveCarPlan.getMovePlanNo());
					List<WorkQueue> workQueueList = JpaUtils.findAll(jpql, paramLs);
					if(workQueueList.size()>0){
						JpaUtils.removeAll(workQueueList);
					}
				}
				
			}
		}
		return HdUtils.genMsg();
	}


	
}

