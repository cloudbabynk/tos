package net.huadong.tech.cargo.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.bean.HdRunTimeException;
import net.huadong.tech.base.entity.CBrand;
import net.huadong.tech.base.entity.CCarKind;
import net.huadong.tech.base.entity.CCarTyp;
import net.huadong.tech.base.entity.CCyArea;
import net.huadong.tech.base.entity.CCyBay;
import net.huadong.tech.base.entity.CCyRow;
import net.huadong.tech.base.entity.CEmployee;
import net.huadong.tech.base.entity.CGate;
import net.huadong.tech.base.entity.CPort;
import net.huadong.tech.base.entity.CTruck;
import net.huadong.tech.cargo.entity.MChangeShip;
import net.huadong.tech.cargo.entity.TruckWork;
import net.huadong.tech.cargo.service.TruckWorkService;
import net.huadong.tech.gate.entity.GateTruck;
import net.huadong.tech.gate.entity.GateTruckContract;
import net.huadong.tech.his.entity.GateTruckContractHis;
import net.huadong.tech.his.entity.GateTruckHis;
import net.huadong.tech.his.entity.PortCarBak;
import net.huadong.tech.his.entity.TruckWorkHis;
import net.huadong.tech.privilege.entity.AuthUser;
import net.huadong.tech.ship.entity.Ship;
import net.huadong.tech.shipbill.entity.ContractIeDoc;
import net.huadong.tech.shipbill.entity.PortCar;
import net.huadong.tech.shipbill.entity.ShipBill;
import net.huadong.tech.shipbill.entity.ShipBillRecord;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.idev.hdmessagecode.HdMessageCode;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.net.ntp.TimeStamp;
import org.eclipse.persistence.config.QueryHints;
import org.eclipse.persistence.config.ResultType;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;
import net.huadong.tech.util.Axis2Util;
import net.huadong.tech.util.CommonUtil;
import net.huadong.tech.util.HdUtils;
import net.huadong.tech.work.entity.CargoInfo;
import net.huadong.tech.work.entity.WorkCommand;
import net.huadong.tech.work.entity.WorkCommandBak;
import net.huadong.tech.work.entity.WorkQueue;

/**
 * @author
 */
@Component
public class TruckWorkServiceImpl implements TruckWorkService {
	@Override
	public HdEzuiDatagridData find(HdQuery hdQuery) {
		String jpql = "select a from GateTruck a,GateTruckContract b where a.ingateId = b.ingateId and b.carryId =:carryId ";
		QueryParamLs paramLs = new QueryParamLs();
		paramLs.addParam("carryId", "A");
		jpql += "order by a.inGatTim desc";
		return JpaUtils.findByEz(jpql, paramLs, hdQuery);
	}

	@Override
	public HdEzuiDatagridData findJglh(HdQuery hdQuery) {
		String jpql = "select a.billNo bno, a.carTyp ctp, count(a.vinNo) num, a.workNam wkm from TruckWork a where 1=1 ";
		String contractNo = hdQuery.getStr("contractNo");
		String type = hdQuery.getStr("type");
		QueryParamLs paramLs = new QueryParamLs();
		if (HdUtils.strNotNull(contractNo)) {
			jpql += " and a.contractNo =:contractNo ";
			paramLs.addParam("contractNo", contractNo);
		} else {
			jpql += " and a.contractNo =:contractNo ";
			paramLs.addParam("contractNo", "123456789###");
		}
		if ("JG".equals(type) || "NMPLJG".equals(type) || "WMPLJG".equals(type)) {
			jpql += " and a.carryId =:carryId ";
			paramLs.addParam("carryId", "A");
		} else if ("SG".equals(type) || "NMPLSG".equals(type) || "WMPLSG".equals(type)) {
			jpql += " and a.carryId =:carryId ";
			paramLs.addParam("carryId", "T");
		}
		jpql += "group by a.billNo,a.carTyp,a.workNam";
		List<Object[]> objlist = JpaUtils.findAll(jpql, paramLs);
		List<TruckWork> truckWorkListAll = new ArrayList();
		for (Object[] obj : objlist) {
			String jpql1 = "select a from TruckWork a where a.billNo =:billNo and a.carTyp =:carTyp and a.workNam =:workNam";
			QueryParamLs paramLs1 = new QueryParamLs();
			paramLs1.addParam("billNo", obj[0]);
			paramLs1.addParam("carTyp", obj[1]);
			paramLs1.addParam("workNam", obj[3]);
			if (HdUtils.strNotNull(contractNo)) {
				jpql1 += " and a.contractNo =:contractNo ";
				paramLs1.addParam("contractNo", contractNo);
			}
			List<TruckWork> truckWorkList = JpaUtils.findAll(jpql1, paramLs1);
			if (truckWorkList.size() > 0) {
				TruckWork truckWork = truckWorkList.get(0);
				truckWork.setRksl(String.valueOf(obj[2]));
				truckWorkListAll.add(truckWork);
			}
		}
		for (TruckWork truckWork : truckWorkListAll) {
			if (HdUtils.strNotNull(truckWork.getCarTyp())) {
				CCarTyp cCarTyp = JpaUtils.findById(CCarTyp.class, truckWork.getCarTyp());
				if (cCarTyp != null) {
					truckWork.setCarTypNam(cCarTyp.getCarTypNam());
				}
			}
			if (HdUtils.strNotNull(truckWork.getBrandCod())) {
				CBrand cBrand = JpaUtils.findById(CBrand.class, truckWork.getBrandCod());
				if (cBrand != null) {
					truckWork.setBrandNam(cBrand.getBrandNam());
				}
			}
			if (HdUtils.strNotNull(truckWork.getWorkNam())) {
				CEmployee cEmployee = JpaUtils.findById(CEmployee.class, truckWork.getWorkNam());
				if (cEmployee != null) {
					truckWork.setWorkNamNam(cEmployee.getEmpNam());
				}
			}
			if (HdUtils.strNotNull(truckWork.getShipNo())) {
				Ship ship = JpaUtils.findById(Ship.class, truckWork.getShipNo());
				if (ship != null) {
					truckWork.setShipNam(ship.getcShipNam());
					truckWork.setVoyage(ship.getIvoyage() + "/" + ship.getEvoyage());
				}
			}
			if (truckWork.getPortCarNo() != null) {
				PortCar portCar = JpaUtils.findById(PortCar.class, truckWork.getPortCarNo());
				if (portCar != null){
					if (HdUtils.strNotNull(portCar.getCyAreaNo())) {
						CCyArea cCyArea = JpaUtils.findById(CCyArea.class, portCar.getCyAreaNo());
						if (cCyArea != null) {
							truckWork.setCyAreaNo(cCyArea.getCyAreaNam());
						}
					}
				}
				
			}
		}
		HdEzuiDatagridData result = new HdEzuiDatagridData();
		result.setRows(truckWorkListAll);
		return result;
	}
	
	@Override
	public HdEzuiDatagridData findHycz(HdQuery hdQuery) {
		String jpql = "select a from TruckWork a where 1=1 ";
		String ingateId = hdQuery.getStr("ingateId");
		QueryParamLs paramLs = new QueryParamLs();
		if (HdUtils.strNotNull(ingateId)) {
			jpql += " and a.ingateId =:ingateId ";
			paramLs.addParam("ingateId", ingateId);
		}
		jpql += "order by a.inGatTim desc";
		return JpaUtils.findByEz(jpql, paramLs, hdQuery);
	}

	// 内贸疏港理货
	@Transactional
	public HdMessageCode saveAllSglh(CargoInfo cargoInfo) {
		String jqpl2 = "select a from PortCar a where a.currentStat = '2' and a.iEId = 'I' and a.tradeId = '1'";
		QueryParamLs paramLs2 = new QueryParamLs();
		String shipNo = cargoInfo.getShipNo();
		String cyAreaNo = cargoInfo.getCyAreaNo();
		String carTyp = cargoInfo.getCarTyp();
		if (HdUtils.strNotNull(shipNo)) {
			jqpl2 += " and a.shipNo =:shipNo";
			paramLs2.addParam("shipNo", shipNo);
		}
		if (HdUtils.strNotNull(carTyp)) {
			jqpl2 += " and a.carTyp =:carTyp";
			paramLs2.addParam("carTyp", carTyp);
		}
		if (HdUtils.strNotNull(cyAreaNo)) {
			jqpl2 += " and a.cyAreaNo =:cyAreaNo";
			paramLs2.addParam("cyAreaNo", cyAreaNo);
		}
		List<PortCar> portCarList = JpaUtils.findAll(jqpl2, paramLs2);
		if (portCarList.size() < cargoInfo.getRcsl().intValue()) {
			throw new HdRunTimeException("疏港数量过多！");
		}
		for (int i = 0; i < cargoInfo.getRcsl().intValue(); i++) {
			PortCar portCar = portCarList.get(i);
			if (HdUtils.strIsNull(portCar.getVinNo())) {
				throw new HdRunTimeException("存在车辆大架号为空的情况!");
			}
			if (portCar != null) {
				GateTruck gateTruck1 = JpaUtils.findById(GateTruck.class, cargoInfo.getIngateId());
				WorkCommand workCommand = new WorkCommand();
				workCommand.setQueueId(HdUtils.genUuid());
				GateTruck gateTruck = JpaUtils.findById(GateTruck.class, cargoInfo.getIngateId());
				String jpql = "select a from WorkQueue a where a.contractNo =:contractNo and a.workTyp='TO' and a.truckNo=:truckNo";
				QueryParamLs paramLs = new QueryParamLs();
				paramLs.addParam("contractNo", cargoInfo.getContractNo());
				paramLs.addParam("truckNo", gateTruck.getTruckNo());
				List<WorkQueue> workQueueList = JpaUtils.findAll(jpql, paramLs);
				if (workQueueList.size() > 0) {
					workCommand.setWorkQueueNo(workQueueList.get(0).getWorkQueueNo());
				}
				workCommand.setRfidCardNo(portCar.getRfidCardNo().toString());
				BeanUtils.copyProperties(portCar, workCommand);
				workCommand.setWorkTyp("TO");
				workCommand.setTruckNo(gateTruck1.getTruckNo());
				workCommand.setUseMachId(cargoInfo.getUseMachId());
				workCommand.setOutCyNam(cargoInfo.getInCyNam());
				workCommand.setInCyTim(cargoInfo.getInCyTim());
				workCommand.setContractNo(cargoInfo.getContractNo());
				JpaUtils.save(workCommand);

				TruckWork truckWork = new TruckWork();
				BeanUtils.copyProperties(portCar, truckWork);
				truckWork.setIngateId(cargoInfo.getIngateId());
				truckWork.setContractNo(cargoInfo.getContractNo());
				truckWork.setTruckNo(gateTruck1.getTruckNo());
				truckWork.setInGateNo(gateTruck1.getInGatNo());
				truckWork.setInRecNam(cargoInfo.getInCyNam());
				truckWork.setWorkNam(cargoInfo.getInCyNam());
				truckWork.setWorkTim(cargoInfo.getInCyTim());
				truckWork.setInGatTim(HdUtils.getDateTime());
				truckWork.setCarryId("T");
				truckWork.setCarryWay("0");
				truckWork.setVinNo(portCar.getVinNo());
				JpaUtils.save(truckWork);

				portCar.setCurrentStat("0");
				portCar.setCyPlac(null);
				portCar.setOutCyTim(cargoInfo.getInCyTim());
				portCar.setOutToolNo(gateTruck.getPlatNo());
				JpaUtils.update(portCar);

				String jpql1 = "select a from GateTruckContract a where a.ingateId=:ingateId and a.contractNo=:contractNo";
				QueryParamLs paramLs1 = new QueryParamLs();
				paramLs1.addParam("ingateId", cargoInfo.getIngateId());
				paramLs1.addParam("contractNo", cargoInfo.getContractNo());
				List<GateTruckContract> gateTruckContractList = JpaUtils.findAll(jpql1, paramLs1);
				for (GateTruckContract gateTruckContract : gateTruckContractList) {
					gateTruckContract.setWorkNum(gateTruckContract.getWorkNum().add(new BigDecimal("1")));
					JpaUtils.update(gateTruckContract);
				}
			}
		}
		return HdUtils.genMsg();
	}

	@Override
	public HdMessageCode updateJgsclh(String shipNo, String billNos, String newBillNo) {
		String sql = "select a from ShipBill a where a.shipNo =:shipNo and a.billNo =:billNo";
		QueryParamLs param = new QueryParamLs();
		param.addParam("shipNo", shipNo);
		param.addParam("billNo", newBillNo);
		List<ShipBill> shipBillList = JpaUtils.findAll(sql, param);
		if (shipBillList.size() < 1){
			throw new HdRunTimeException("新提单号不存在！");
		}
		ShipBill shipBill = shipBillList.get(0);
		List<String> idList = HdUtils.paraseStrs(billNos);
		String billNoList = "";
		for (String str : idList){
			billNoList += str + "','";
		}
		String sql1 = "select count(a.portCarNo) from PortCar a where a.currentStat = '2' and a.shipNo =:shipNo and a.billNo in ('" + billNoList + newBillNo +"')";
		QueryParamLs param1 = new QueryParamLs();
		param1.addParam("shipNo", shipNo);
		Long num = JpaUtils.single(sql1, param1);
		if (num > 0){
			if (shipBill.getPieces().compareTo(new BigDecimal(num)) == -1){
				throw new HdRunTimeException("转配数量过多！");
			}
		}
		for (String str : idList){
			String jpql = "select a from PortCar a where a.shipNo =:shipNo and a.billNo =:billNo and a.currentStat =:currentStat";
			QueryParamLs paramLs = new QueryParamLs();
			paramLs.addParam("shipNo", shipNo);
			paramLs.addParam("billNo", str);
			paramLs.addParam("currentStat", "2");
			List<PortCar> portCarList = JpaUtils.findAll(jpql, paramLs);
			for(PortCar bean : portCarList){
				bean.setBillNo(newBillNo);
				JpaUtils.update(bean);
				String jpql1 = "select a from WorkCommand a where a.portCarNo =:portCarNo";
				QueryParamLs paramLS1 = new QueryParamLs();
				paramLS1.addParam("portCarNo", bean.getPortCarNo());
				List<WorkCommand> wdList = JpaUtils.findAll(jpql1, paramLS1);
				if (wdList.size() > 0){
					WorkCommand data = wdList.get(0);
					data.setBillNo(newBillNo);
					JpaUtils.update(data);
				}
			}
		}
		Axis2Util axis2Util = new Axis2Util();
		try {
			axis2Util.getCustomRelease(shipNo, newBillNo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return HdUtils.genMsg();
	}
	
	@Override
	public HdMessageCode updateBillNo(CargoInfo cargoInfo) {
		String shipNo = cargoInfo.getShipNo();
		String billNo = cargoInfo.getBillNo();
		String carTyp = cargoInfo.getCarTyp();
		String cyAreaNo = cargoInfo.getCyAreaNo();
		String currentStat = cargoInfo.getCurrentStat();
		Timestamp inCyTim = cargoInfo.getInCyTim();
		BigDecimal rcsl = cargoInfo.getRcsl();
		String jpql = "select a from PortCar a where 1=1 ";
		QueryParamLs paramLs = new QueryParamLs();
		if (HdUtils.strNotNull(shipNo)){
			jpql += "and a.shipNo =:shipNo ";
			paramLs.addParam("shipNo", shipNo);
		}
		if (HdUtils.strNotNull(billNo)){
			jpql += "and a.billNo =:billNo ";
			paramLs.addParam("billNo", billNo);
		}
		if (HdUtils.strNotNull(carTyp)){
			jpql += "and a.carTyp =:carTyp ";
			paramLs.addParam("carTyp", carTyp);
		}
		if (HdUtils.strNotNull(cyAreaNo)){
			jpql += "and a.cyAreaNo =:cyAreaNo ";
			paramLs.addParam("cyAreaNo", cyAreaNo);
		}
		if (HdUtils.strNotNull(currentStat)){
			jpql += "and a.currentStat =:currentStat ";
			paramLs.addParam("currentStat", currentStat);
		}
		if (inCyTim != null){
			jpql += "and a.inCyTim =:inCyTim ";
			paramLs.addParam("inCyTim", inCyTim);
		}
		List<PortCar> portCarList = JpaUtils.findAll(jpql, paramLs);
		if (portCarList.size() != rcsl.intValue()){
			throw new HdRunTimeException("在场车信息不足！");
		}
		if (billNo.contains("##")){
			throw new HdRunTimeException("不允许重复改变！");
		}
		String newBillNo = billNo + "AA" + CommonUtil.getId().substring(10);
		for(PortCar bean : portCarList){
			bean.setBillNo(newBillNo);
			JpaUtils.update(bean);
			String jpql1 = "select a from WorkCommand a where a.portCarNo =:portCarNo";
			QueryParamLs paramLs1 = new QueryParamLs();
			paramLs1.addParam("portCarNo", bean.getPortCarNo());
			List<WorkCommand> wdList = JpaUtils.findAll(jpql1, paramLs1);
			if (wdList.size() < 1){
				throw new HdRunTimeException("集港理货记录不存在！");
			} else {
				WorkCommand wd = wdList.get(0);
				wd.setBillNo(bean.getBillNo());
				JpaUtils.update(wd);
			}
		}
		return HdUtils.genMsg();
	}
	
	@Override
	public HdMessageCode updateJgsclhBill(CargoInfo cargoInfo) {
		String shipNo = cargoInfo.getShipNo();
		String billNo = cargoInfo.getBillNo();
		String carTyp = cargoInfo.getCarTyp();
		String cyAreaNo = cargoInfo.getCyAreaNo();
		String currentStat = cargoInfo.getCurrentStat();
		Timestamp inCyTim = cargoInfo.getInCyTim();
		BigDecimal rcsl = cargoInfo.getRcsl();
		String jpql = "select a from PortCar a where 1=1 ";
		QueryParamLs paramLs = new QueryParamLs();
		if (HdUtils.strNotNull(shipNo)){
			jpql += "and a.shipNo =:shipNo ";
			paramLs.addParam("shipNo", shipNo);
		}
		if (HdUtils.strNotNull(billNo)){
			jpql += "and a.billNo =:billNo ";
			paramLs.addParam("billNo", billNo);
		}
		if (HdUtils.strNotNull(carTyp)){
			jpql += "and a.carTyp =:carTyp ";
			paramLs.addParam("carTyp", carTyp);
		}
		if (HdUtils.strNotNull(cyAreaNo)){
			jpql += "and a.cyAreaNo =:cyAreaNo ";
			paramLs.addParam("cyAreaNo", cyAreaNo);
		}
		if (HdUtils.strNotNull(currentStat)){
			jpql += "and a.currentStat =:currentStat ";
			paramLs.addParam("currentStat", currentStat);
		}
		if (inCyTim != null){
			jpql += "and a.inCyTim =:inCyTim ";
			paramLs.addParam("inCyTim", inCyTim);
		}
		List<PortCar> portCarList = JpaUtils.findAll(jpql, paramLs);
		if (portCarList.size() < rcsl.intValue()){
			throw new HdRunTimeException("在场车信息不足！");
		}
		if (billNo.contains("##")){
			throw new HdRunTimeException("不允许重复拆单！");
		}
		String newBillNo = billNo + "AA" + CommonUtil.getId().substring(10);
		for(int i=1;i<=rcsl.intValue();i++){
			PortCar bean = portCarList.get(i);
			bean.setBillNo(newBillNo);
			JpaUtils.update(bean);
			String jpql1 = "select a from WorkCommand a where a.portCarNo =:portCarNo";
			QueryParamLs paramLs1 = new QueryParamLs();
			paramLs1.addParam("portCarNo", bean.getPortCarNo());
			List<WorkCommand> wdList = JpaUtils.findAll(jpql1, paramLs1);
			if (wdList.size() < 1){
				throw new HdRunTimeException("集港理货记录不存在！");
			} else {
				WorkCommand wd = wdList.get(0);
				wd.setBillNo(bean.getBillNo());
				JpaUtils.update(wd);
			}
		}
		return HdUtils.genMsg();
	}
	
	@Transactional
	public HdMessageCode saveAll(String portCarNos, CargoInfo cargoInfo) {
		List<String> portCarNoList = HdUtils.paraseStrs(portCarNos);
		for (String portCarNo : portCarNoList) {
			BigDecimal id = new BigDecimal(portCarNo);
			PortCar portCar = JpaUtils.findById(PortCar.class, id);
			if (HdUtils.strIsNull(portCar.getVinNo())) {
				throw new HdRunTimeException("存在车辆大架号为空的情况!");
			}
			if (portCar != null) {
				GateTruck gateTruck1 = JpaUtils.findById(GateTruck.class, cargoInfo.getIngateId());
				WorkCommand workCommand = new WorkCommand();
				workCommand.setQueueId(HdUtils.genUuid());
				GateTruck gateTruck = JpaUtils.findById(GateTruck.class, cargoInfo.getIngateId());
				String jpql = "select a from WorkQueue a where a.contractNo =:contractNo and a.workTyp='TO' and a.truckNo=:truckNo";
				QueryParamLs paramLs = new QueryParamLs();
				paramLs.addParam("contractNo", cargoInfo.getContractNo());
				paramLs.addParam("truckNo", gateTruck.getTruckNo());
				List<WorkQueue> workQueueList = JpaUtils.findAll(jpql, paramLs);
				if (workQueueList.size() > 0) {
					workCommand.setWorkQueueNo(workQueueList.get(0).getWorkQueueNo());
				}
				workCommand.setRfidCardNo(portCar.getRfidCardNo().toString());
				BeanUtils.copyProperties(portCar, workCommand);
				workCommand.setWorkTyp("TO");
				workCommand.setTruckNo(gateTruck1.getTruckNo());
				workCommand.setUseMachId(cargoInfo.getUseMachId());
				workCommand.setContractNo(cargoInfo.getContractNo());
				workCommand.setInCyTim(cargoInfo.getInCyTim());
				workCommand.setOutCyTim(cargoInfo.getInCyTim());
				workCommand.setInCyNam(cargoInfo.getInCyNam());
				workCommand.setOutCyNam(cargoInfo.getInCyNam());
				JpaUtils.save(workCommand);

				TruckWork truckWork = new TruckWork();
				BeanUtils.copyProperties(portCar, truckWork);
				truckWork.setIngateId(cargoInfo.getIngateId());
				truckWork.setContractNo(cargoInfo.getContractNo());
				truckWork.setTruckNo(gateTruck1.getTruckNo());
				truckWork.setInGateNo(gateTruck1.getInGatNo());
				truckWork.setInRecNam(cargoInfo.getInCyNam());
				truckWork.setWorkNam(cargoInfo.getInCyNam());
				truckWork.setWorkTim(cargoInfo.getInCyTim());
				truckWork.setInGatTim(HdUtils.getDateTime());
				truckWork.setCarryId("T");
				truckWork.setCarryWay("0");
				truckWork.setVinNo(portCar.getVinNo());
				JpaUtils.save(truckWork);

				portCar.setCurrentStat("5");
				portCar.setCyPlac(null);
				portCar.setContractNo(cargoInfo.getContractNo());
				portCar.setOutCyTim(cargoInfo.getInCyTim());
				portCar.setOutToolNo(gateTruck.getPlatNo());
				JpaUtils.save(portCar);

				String jpql1 = "select a from GateTruckContract a where a.ingateId=:ingateId and a.contractNo=:contractNo";
				QueryParamLs paramLs1 = new QueryParamLs();
				paramLs1.addParam("ingateId", cargoInfo.getIngateId());
				paramLs1.addParam("contractNo", cargoInfo.getContractNo());
				List<GateTruckContract> gateTruckContractList = JpaUtils.findAll(jpql1, paramLs1);
				for (GateTruckContract gateTruckContract : gateTruckContractList) {
					gateTruckContract.setWorkNum(gateTruckContract.getWorkNum().add(new BigDecimal("1")));
					JpaUtils.save(gateTruckContract);
				}

			}
		}
		return HdUtils.genMsg();
	}

	// 货运出闸
	@Override
	public HdMessageCode save(HdEzuiSaveDatagridData<TruckWork> hdEzuiSaveDatagridData, String ingateId,
			String gateNo) {
		GateTruck gateTruck = JpaUtils.findById(GateTruck.class, ingateId);
		if (gateTruck != null) {
			if ("0".equals(gateTruck.getFinishedId())) {
				throw new HdRunTimeException("此拖车没有作业完成，不能出闸确认！");
			} else {
				gateTruck.setOutGatNo(gateNo);
				gateTruck.setOutGatTim(HdUtils.getDateTime());
				gateTruck.setOutGatNam(HdUtils.getCurUser().getAccount());
				JpaUtils.save(gateTruck);
				GateTruckHis gateTruckHis = new GateTruckHis();
				BeanUtils.copyProperties(gateTruck, gateTruckHis);
				JpaUtils.save(gateTruckHis);

				String truckNo = gateTruck.getTruckNo();
				JpaUtils.remove(gateTruck);

				String jpql = "select a from TruckWork a where a.ingateId =:ingateId";
				QueryParamLs paramLs = new QueryParamLs();
				paramLs.addParam("ingateId", ingateId);
				List<TruckWork> truckWorkList = JpaUtils.findAll(jpql, paramLs);
				for (TruckWork truckWork : truckWorkList) {
					truckWork.setOutGatNo(gateNo);
					truckWork.setOutGatTim(HdUtils.getDateTime());
					truckWork.setOutRecNam(HdUtils.getCurUser().getAccount());
					JpaUtils.save(truckWork);
					TruckWorkHis truckWorkBak = new TruckWorkHis();
					BeanUtils.copyProperties(truckWork, truckWorkBak);
					JpaUtils.save(truckWorkBak);
					JpaUtils.remove(truckWork);
				}

				String jpql1 = "select a from WorkCommand a where a.finishedId='1' and a.workTyp in ('TO','TI') and a.truckNo=:truckNo";
				QueryParamLs paramLs1 = new QueryParamLs();
				paramLs1.addParam("truckNo", truckNo);
				List<WorkCommand> workCommandList = JpaUtils.findAll(jpql1, paramLs1);
				if (workCommandList.size() > 0) {
					WorkCommand workCommand = workCommandList.get(0);
					WorkCommandBak workCommandBak = new WorkCommandBak();
					BeanUtils.copyProperties(workCommand, workCommandBak);
					JpaUtils.save(workCommandBak);
					JpaUtils.remove(workCommand);
				}

				GateTruckContractHis gateTruckContractHis = new GateTruckContractHis();
				String jpql2 = "select a from GateTruckContract a where a.ingateId =:ingateId";
				QueryParamLs paramLs2 = new QueryParamLs();
				paramLs.addParam("ingateId", ingateId);
				List<GateTruckContract> gateTruckContractList = JpaUtils.findAll(jpql2, paramLs2);
				if (gateTruckContractList.size() > 0) {
					BeanUtils.copyProperties(gateTruckContractList.get(0), gateTruckContractHis);
					JpaUtils.save(gateTruckContractHis);
					JpaUtils.remove(gateTruckContractList.get(0));
				}

				String jpql3 = "select a from TruckWork a where a.workTyp='TO' and a.ingateId=:ingateId";
				QueryParamLs paramLs3 = new QueryParamLs();
				paramLs3.addParam("ingateId", ingateId);
				List<TruckWork> truckWorkList1 = JpaUtils.findAll(jpql3, paramLs3);
				for (TruckWork truckWork : truckWorkList1) {
					PortCar portCar = JpaUtils.findById(PortCar.class, truckWork.getPortCarNo());
					if ("4".equals(portCar.getCurrentStat())) {
						portCar.setCurrentStat("0");
						portCar.setLeavPortTim(HdUtils.getDateTime());
						portCar.setUpdNam(HdUtils.getCurUser().getAccount());
						portCar.setUpdTim(HdUtils.getDateTime());
						JpaUtils.save(portCar);
						PortCarBak portCarBak = new PortCarBak();
						BeanUtils.copyProperties(portCar, portCarBak);
						JpaUtils.save(portCarBak);
						JpaUtils.remove(portCar);
					}
				}

				String jpql4 = "select a from WorkQueue a where a.workTyp in ('TO','TI') and a.truckNo =:truckNo";
				QueryParamLs paramLs4 = new QueryParamLs();
				paramLs4.addParam("truckNo", truckNo);
				List<WorkQueue> workQueueList = JpaUtils.findAll(jpql4, paramLs4);
				JpaUtils.removeAll(workQueueList);

			}
		}
		return HdUtils.genMsg();
	}

	// 疏港批量理货货运出闸
	@Override
	public HdMessageCode saveHycz(String ingateId, String gateNo) {
		GateTruck gateTruck = JpaUtils.findById(GateTruck.class, ingateId);
		if (gateTruck != null) {
			String jpql = "select a from GateTruckContract a where a.ingateId =:ingateId";
			QueryParamLs paramLs = new QueryParamLs();
			paramLs.addParam("ingateId", ingateId);
			List<GateTruckContract> gateTruckContractList = JpaUtils.findAll(jpql, paramLs);
			gateTruck.setOutGatNo(gateNo);
			gateTruck.setOutGatTim(HdUtils.getDateTime());
			gateTruck.setOutGatNam(HdUtils.getCurUser().getAccount());
			JpaUtils.update(gateTruck);
			GateTruckHis gateTruckHis = new GateTruckHis();
			BeanUtils.copyProperties(gateTruck, gateTruckHis);
			JpaUtils.save(gateTruckHis);

			String truckNo = gateTruck.getTruckNo();

			String jpql4 = "select a from TruckWork a where a.ingateId=:ingateId";
			QueryParamLs paramLs4 = new QueryParamLs();
			paramLs4.addParam("ingateId", ingateId);
			List<TruckWork> truckWorkList1 = JpaUtils.findAll(jpql4, paramLs4);
			for (TruckWork truckWork : truckWorkList1) {
				PortCar portCar = JpaUtils.findById(PortCar.class, truckWork.getPortCarNo());
				if ("4".equals(portCar.getCurrentStat())) {
					portCar.setCurrentStat("0");
					portCar.setLeavPortTim(HdUtils.getDateTime());
					portCar.setUpdNam(HdUtils.getCurUser().getAccount());
					portCar.setUpdTim(HdUtils.getDateTime());
					JpaUtils.update(portCar);
					PortCarBak portCarBak = new PortCarBak();
					BeanUtils.copyProperties(portCar, portCarBak);
					JpaUtils.save(portCarBak);
					JpaUtils.remove(portCar);
				}
			}

			String jpql1 = "select a from TruckWork a where a.ingateId =:ingateId";
			QueryParamLs paramLs1 = new QueryParamLs();
			paramLs1.addParam("ingateId", ingateId);
			List<TruckWork> truckWorkList = JpaUtils.findAll(jpql1, paramLs1);
			for (TruckWork truckWork : truckWorkList) {
				truckWork.setOutGatNo(gateNo);
				truckWork.setOutGatTim(HdUtils.getDateTime());
				truckWork.setOutRecNam(HdUtils.getCurUser().getAccount());
				JpaUtils.update(truckWork);
				TruckWorkHis truckWorkBak = new TruckWorkHis();
				BeanUtils.copyProperties(truckWork, truckWorkBak);
				truckWorkBak.setNewVinNo(truckWorkBak.getVinNo());
				JpaUtils.save(truckWorkBak);
				JpaUtils.remove(truckWork);
			}

			String jpql2 = "select a from WorkCommand a where a.finishedId='1' and a.workTyp in ('TO','TI') and a.truckNo=:truckNo";
			QueryParamLs paramLs2 = new QueryParamLs();
			paramLs2.addParam("truckNo", truckNo);
			List<WorkCommand> workCommandList = JpaUtils.findAll(jpql2, paramLs2);
			for (WorkCommand bean : workCommandList) {
				WorkCommandBak workCommandBak = new WorkCommandBak();
				BeanUtils.copyProperties(bean, workCommandBak);
				JpaUtils.save(workCommandBak);
				JpaUtils.remove(bean);
			}

			String jpql5 = "select a from WorkQueue a where a.workTyp in ('TO','TI') and a.truckNo =:truckNo";
			QueryParamLs paramLs5 = new QueryParamLs();
			paramLs5.addParam("truckNo", truckNo);
			List<WorkQueue> workQueueList = JpaUtils.findAll(jpql5, paramLs5);
			JpaUtils.removeAll(workQueueList);

			String jpql3 = "select a from GateTruckContract a where a.ingateId =:ingateId";
			QueryParamLs paramLs3 = new QueryParamLs();
			paramLs3.addParam("ingateId", ingateId);
			List<GateTruckContract> gateTruckContractList1 = JpaUtils.findAll(jpql3, paramLs3);
			for (GateTruckContract gateTruckContract : gateTruckContractList1) {
				GateTruckContractHis gateTruckContractHis = new GateTruckContractHis();
				BeanUtils.copyProperties(gateTruckContract, gateTruckContractHis);
				JpaUtils.save(gateTruckContractHis);
				JpaUtils.remove(gateTruckContract);
			}
			JpaUtils.remove(gateTruck);
		}
		return HdUtils.genMsg();
	}

	@Transactional
	public void removeAll(String ids) {
		List<String> idList = HdUtils.paraseStrs(ids);
		for (String id : idList) {
			JpaUtils.remove(TruckWork.class, id);
		}
	}

	@Override
	public TruckWork findone(String id) {
		TruckWork truckWork = JpaUtils.findById(TruckWork.class, id);
		return truckWork;
	}

	@Override
	public HdMessageCode saveone(@RequestBody TruckWork truckWork, String driver, String lengthOverId,
			String widthOverId, String useMachId, String useWorkerId) {
		String jpql = "select a from PortCar a where a.vinNo =:vinNo";
		QueryParamLs paramLs = new QueryParamLs();
		paramLs.addParam("vinNo", truckWork.getVinNo());
		List<PortCar> portCarList = JpaUtils.findAll(jpql, paramLs);
		if (portCarList.size() > 0) {
			throw new HdRunTimeException("此车已经在场，不可再次入场！");
		}
		String jpql1 = "select a from PortCar a where a.rfidCardNo =:rfidCardNo";
		QueryParamLs paramLs1 = new QueryParamLs();
		paramLs1.addParam("rfidCardNo", truckWork.getRfidCardNo());
		List<PortCar> portCarList1 = JpaUtils.findAll(jpql1, paramLs1);
		if (portCarList1.size() > 0) {
			throw new HdRunTimeException("此rfid卡号已经使用，不可再次使用！");
		}

		PortCar portCar = new PortCar();
		portCar.setRfidCardNo(truckWork.getRfidCardNo());
		portCar.setVinNo(truckWork.getVinNo());
		portCar.setCurrentStat("1");
		portCar.setLengthOverId(lengthOverId);
		portCar.setWidthOverId(widthOverId);
		portCar.setCyPlac(truckWork.getCyPlac());
		portCar.setBillNo(truckWork.getBillNo());
		ContractIeDoc contractIeDoc = JpaUtils.findById(ContractIeDoc.class, truckWork.getContractNo());
		portCar.setiEId(contractIeDoc.getiEId());
		portCar.setTradeId(contractIeDoc.getTradeId());
		portCar.setDockCod(contractIeDoc.getDockCod());
		portCar.setInPortNo(contractIeDoc.getContractNo());
		JpaUtils.save(portCar);

		truckWork.setPortCarNo(portCar.getPortCarNo());
		String jpql2 = "select a from GateTruckContract a where a.ingateId=:ingateId and a.contractNo=:contractNo";
		QueryParamLs paramLs2 = new QueryParamLs();
		paramLs2.addParam("ingateId", truckWork.getIngateId());
		paramLs2.addParam("contractNo", truckWork.getContractNo());
		List<GateTruckContract> gateTruckContractList = JpaUtils.findAll(jpql2, paramLs2);
		for (GateTruckContract gateTruckContract : gateTruckContractList) {
			truckWork.setBillNo(gateTruckContract.getBillNo());
			truckWork.setCarryId(gateTruckContract.getCarryId());
			truckWork.setTruckNo(gateTruckContract.getTruckNo());
		}
		GateTruck gateTruck = JpaUtils.findById(GateTruck.class, truckWork.getIngateId());
		if (gateTruck != null) {
			truckWork.setInGatTim(gateTruck.getInGatTim());
			truckWork.setCarryWay(gateTruck.getSingleId());
			truckWork.setInGateNo(gateTruck.getInGatNo());
			truckWork.setInRecNam(gateTruck.getInRecNam());
		}
		truckWork.setCarTyp(contractIeDoc.getCarTyp());
		truckWork.setCarKind(contractIeDoc.getCarKind());
		truckWork.setDiscPortCod(contractIeDoc.getDiscPortCod());
		truckWork.setTranPortCod(contractIeDoc.getTranPortCod());
		truckWork.setWorkTim(HdUtils.getDateTime());
		JpaUtils.save(truckWork);

		WorkCommand workCommand = new WorkCommand();
		String jpql3 = "select a from WorkQueue a where a.workTyp = 'TI' and a.contractNo=:contractNo and a.truckNo=:truckNo";
		QueryParamLs paramLs3 = new QueryParamLs();
		paramLs3.addParam("contractNo", truckWork.getContractNo());
		paramLs3.addParam("truckNo", truckWork.getTruckNo());
		List<WorkQueue> workQueueList = JpaUtils.findAll(jpql3, paramLs3);
		if (workQueueList.size() < 1) {
			throw new HdRunTimeException("作业指令保存失败");
		}
		for (WorkQueue workQueue : workQueueList) {

			workCommand.setWorkQueueNo(workQueue.getWorkQueueNo());
		}
		workCommand.setQueueId(HdUtils.genUuid());
		workCommand.setPortCarNo(portCar.getPortCarNo());
		workCommand.setRfidCardNo(truckWork.getRfidCardNo());
		workCommand.setWorkTyp("TI");
		workCommand.setVinNo(truckWork.getVinNo());
		workCommand.setContractNo(truckWork.getContractNo());
		JpaUtils.save(workCommand);

		return HdUtils.genMsg();
	}

	@Override
	public HdMessageCode saveJgsclh(@RequestBody CargoInfo cargoInfo) {
		String jpql = "select a from TruckWork a where a.iEId = 'E' ";
		QueryParamLs paramLs = new QueryParamLs();
		if (HdUtils.strNotNull(cargoInfo.getShipNo())) {
			jpql += "and a.shipNo =:shipNo ";
			paramLs.addParam("shipNo", cargoInfo.getShipNo());
		}
		if (HdUtils.strNotNull(cargoInfo.getBillNo())) {
			jpql += "and a.billNo =:billNo";
			paramLs.addParam("billNo", cargoInfo.getBillNo());
		}
		List<TruckWork> truckWorkList = JpaUtils.findAll(jpql, paramLs);
		String type = "";
		if (truckWorkList.size() > 0) {
			TruckWork bean = truckWorkList.get(0);
			ContractIeDoc contractIeDoc = JpaUtils.findById(ContractIeDoc.class, bean.getContractNo());
			if (contractIeDoc != null) {
				if (Ship.CK.equals(contractIeDoc.getiEId()) && Ship.NM.equals(contractIeDoc.getTradeId())) {
					type = "NMPLJG";
				} else if (Ship.CK.equals(contractIeDoc.getiEId()) && Ship.WM.equals(contractIeDoc.getTradeId())) {
					type = "WMPLJG";
				}
			}
			if (HdUtils.strNotNull(bean.getIngateId())) {
				cargoInfo.setIngateId(bean.getIngateId());
			} else {
				throw new HdRunTimeException("理货数据有误！");
			}
		} else {
			throw new HdRunTimeException("理货数据有误！");
		}
		return saveJglh(cargoInfo, type);
	}

	@Override
	public HdMessageCode saveJglh(@RequestBody CargoInfo cargoInfo, String type) {
		if (cargoInfo.getRcsl() != null) {
			int rcsl = cargoInfo.getRcsl().intValue();
			String tranPortCod = "";
			String dockCod = "";
			if ("NMPLJG".equals(type)) {
				String flowNam = HdUtils.getSysCodeName("FLOW_AREA", cargoInfo.getFlow());
				if (HdUtils.strIsNull(flowNam)) {
					throw new HdRunTimeException("流向不存在！");
				} else {
					String sql = "select a from CPort a where a.cPortNam =:cPortNam";
					QueryParamLs queryCport = new QueryParamLs();
					queryCport.addParam("cPortNam", flowNam);
					List<CPort> cPortList = JpaUtils.findAll(sql, queryCport);
					if (cPortList.size() > 0) {
						tranPortCod = cPortList.get(0).getPortCod();
					}
				}
			} else if ("WMPLJG".equals(type)){
				String shipNo = cargoInfo.getShipNo();
				String billNo = cargoInfo.getBillNo();
				String recordSql = "select a from ShipBillRecord a where 1=1 ";
				QueryParamLs paramLs = new QueryParamLs();
				if (HdUtils.strNotNull(shipNo)){
					recordSql += "and a.shipNo =:shipNo ";
					paramLs.addParam("shipNo", shipNo);
				}
				if (HdUtils.strNotNull(billNo)){
					recordSql += "and a.billNo =:billNo";
					paramLs.addParam("billNo", billNo);
				}
				List<ShipBillRecord> recordList = JpaUtils.findAll(recordSql, paramLs);
				if (recordList.size()>0){
					ShipBillRecord bean = recordList.get(0);
					if (cargoInfo.getPieces() != null)
					bean.setPieces(cargoInfo.getPieces());
					JpaUtils.update(bean);
				} else {
					ShipBillRecord shipBillRecord = new ShipBillRecord();
					shipBillRecord.setRecordId(HdUtils.generateUUID());
					shipBillRecord.setShipNo(shipNo);
					shipBillRecord.setBillNo(billNo);
					shipBillRecord.setiEId(Ship.CK);
					shipBillRecord.setTradeId(Ship.WM);
					if (cargoInfo.getPieces() != null)
					shipBillRecord.setPieces(cargoInfo.getPieces());
					JpaUtils.save(shipBillRecord);
				}
			}
			if (HdUtils.strNotNull(cargoInfo.getInCyNam())) {
				CEmployee bean = JpaUtils.findById(CEmployee.class, cargoInfo.getInCyNam());
				if (bean != null) {
					if (CEmployee.TYP_GZ_01.equals(bean.getClassNo()) || CEmployee.TYP_GZ_02.equals(bean.getClassNo())
							|| CEmployee.TYP_GZ_03.equals(bean.getClassNo())
							|| CEmployee.TYP_GZ_04.equals(bean.getClassNo())
							|| CEmployee.TYP_GZ_05.equals(bean.getClassNo())) {
						dockCod = Ship.GZ;
					} else {
						dockCod = Ship.HQ;
					}
				} else {
					throw new HdRunTimeException("理货员信息不完善！");
				}
			}
			for (int i = 0; i < rcsl; i++) {
				PortCar portCar = new PortCar();
				portCar.setRfidCardNo("vepc" + new Date().getTime());
				portCar.setVinNo(portCar.getRfidCardNo());
				portCar.setCurrentStat("2");
				portCar.setLengthOverId(cargoInfo.getLengthOverId());
				portCar.setCarTyp(cargoInfo.getCarTyp());
				portCar.setCarKind(cargoInfo.getCarKind());
				portCar.setBrandCod(cargoInfo.getBrandCod());
				portCar.setConsignCod(cargoInfo.getConsignCod());
				portCar.setTranPortCod(tranPortCod);
				portCar.setContactInfo(cargoInfo.getContactInfo());

				portCar.setBillNo(cargoInfo.getBillNo());
				portCar.setContractNo(cargoInfo.getContractNo());
				ContractIeDoc contractIeDoc = JpaUtils.findById(ContractIeDoc.class, cargoInfo.getContractNo());
				portCar.setiEId(contractIeDoc.getiEId());
				portCar.setTradeId(contractIeDoc.getTradeId());
				portCar.setDockCod(contractIeDoc.getDockCod());
				portCar.setInPortNo(contractIeDoc.getContractNo());
				// updatePortCar(portCar, cargoInfo); //自动理货计算车位
				portCar.setCyAreaNo(cargoInfo.getCyAreaNo());
				portCar.setCyRowNo("###");
				portCar.setCyBayNo("###");
				portCar.setCyPlac("###");
				if (HdUtils.strNotNull(cargoInfo.getCarLevel())) {
					portCar.setCarLevel(cargoInfo.getCarLevel());
				}
				portCar.setInCyTim(cargoInfo.getInCyTim());
				if (HdUtils.strNotNull(cargoInfo.getShipNo())) {
					portCar.setShipNo(cargoInfo.getShipNo());
				}
				if (HdUtils.strNotNull(cargoInfo.getCyRemarks())) {
					portCar.setCyRemarks(cargoInfo.getCyRemarks());
				}
				JpaUtils.save(portCar);

				TruckWork truckWork = new TruckWork();
				BeanUtils.copyProperties(cargoInfo, truckWork);
				truckWork.setPortCarNo(portCar.getPortCarNo());
				String jpql2 = "select a from GateTruckContract a where a.ingateId=:ingateId and a.contractNo=:contractNo";
				QueryParamLs paramLs2 = new QueryParamLs();
				paramLs2.addParam("ingateId", truckWork.getIngateId());
				paramLs2.addParam("contractNo", truckWork.getContractNo());
				List<GateTruckContract> gateTruckContractList = JpaUtils.findAll(jpql2, paramLs2);
				for (GateTruckContract gateTruckContract : gateTruckContractList) {
					truckWork.setCarryId(gateTruckContract.getCarryId());
					truckWork.setTruckNo(gateTruckContract.getTruckNo());
				}
				GateTruck gateTruck = JpaUtils.findById(GateTruck.class, truckWork.getIngateId());
				if (gateTruck != null) {
					truckWork.setInGatTim(gateTruck.getInGatTim());
					truckWork.setCarryWay(gateTruck.getSingleId());
					truckWork.setInGateNo(gateTruck.getInGatNo());
					truckWork.setInRecNam(gateTruck.getInRecNam());
				}
				truckWork.setBillNo(cargoInfo.getBillNo());
				truckWork.setCarTyp(cargoInfo.getCarTyp());
				truckWork.setBrandCod(cargoInfo.getBrandCod());
				truckWork.setCarKind(cargoInfo.getCarKind());
				truckWork.setTradeId(portCar.getTradeId());
				truckWork.setDiscPortCod(contractIeDoc.getDiscPortCod());
				truckWork.setTranPortCod(contractIeDoc.getTranPortCod());
				truckWork.setWorkTim(cargoInfo.getInCyTim());
				truckWork.setWorkNam(cargoInfo.getInCyNam());
				truckWork.setVinNo(portCar.getRfidCardNo());
				truckWork.setRfidCardNo(portCar.getRfidCardNo());
				truckWork.setCrgAgent(cargoInfo.getConsignCod());
				truckWork.setCyPlac(portCar.getCyPlac());
				truckWork.setiEId(portCar.getiEId());
				if (HdUtils.strNotNull(cargoInfo.getShipNo())) {
					truckWork.setShipNo(cargoInfo.getShipNo());
				}
				if (HdUtils.strNotNull(cargoInfo.getRemarks()))
					truckWork.setRemarkTxt(cargoInfo.getRemarks());
				if (HdUtils.strNotNull(cargoInfo.getCyRemarks()))
					truckWork.setCyRemarks(cargoInfo.getCyRemarks());
				JpaUtils.save(truckWork);

				WorkCommand workCommand = new WorkCommand();
				String jpql3 = "select a from WorkQueue a where a.workTyp = 'TI' and a.contractNo=:contractNo and a.truckNo=:truckNo";
				QueryParamLs paramLs3 = new QueryParamLs();
				paramLs3.addParam("contractNo", truckWork.getContractNo());
				paramLs3.addParam("truckNo", truckWork.getTruckNo());
				List<WorkQueue> workQueueList = JpaUtils.findAll(jpql3, paramLs3);
				if (workQueueList.size() < 1) {
					throw new HdRunTimeException("作业指令保存失败");
				}
				for (WorkQueue workQueue : workQueueList) {

					workCommand.setWorkQueueNo(workQueue.getWorkQueueNo());
				}
				workCommand.setQueueId(HdUtils.genUuid());
				workCommand.setPortCarNo(portCar.getPortCarNo());
				workCommand.setRfidCardNo(truckWork.getRfidCardNo());
				workCommand.setWorkTyp("TI");
				workCommand.setVinNo(truckWork.getVinNo());
				workCommand.setCyPlac(portCar.getCyPlac());
				workCommand.setContractNo(truckWork.getContractNo());
				if (HdUtils.strNotNull(cargoInfo.getCarLevel())) {
					workCommand.setCarLevel(cargoInfo.getCarLevel());
				}
				if (HdUtils.strNotNull(cargoInfo.getCarKind())) {
					workCommand.setCarKind(cargoInfo.getCarKind());
				}

				if (HdUtils.strNotNull(cargoInfo.getCarTyp())) {
					workCommand.setCarTyp(cargoInfo.getCarTyp());
				}

				if (HdUtils.strNotNull(cargoInfo.getBrandCod())) {
					workCommand.setBrandCod(cargoInfo.getBrandCod());
				}
				if (HdUtils.strNotNull(cargoInfo.getBillNo())) {
					workCommand.setBillNo(cargoInfo.getBillNo());
				}
				if (HdUtils.strNotNull(cargoInfo.getShipNo())) {
					workCommand.setShipNo(cargoInfo.getShipNo());
					;
				}
				if (HdUtils.strNotNull(cargoInfo.getRemarks())) {
					workCommand.setRemarks(cargoInfo.getRemarks());
				}
				if (HdUtils.strNotNull(cargoInfo.getCyRemarks())) {
					workCommand.setCyRemarks(cargoInfo.getCyRemarks());
				}
				workCommand.setInCyTim(cargoInfo.getInCyTim());
				workCommand.setInCyNam(cargoInfo.getInCyNam());
				workCommand.setLengthOverId(cargoInfo.getLengthOverId());
				JpaUtils.save(workCommand);

				GateTruckContract gateTruckContract = gateTruckContractList.get(0);
				gateTruckContract.setWorkNum(gateTruckContract.getWorkNum().add(new BigDecimal("1")));
				JpaUtils.update(gateTruckContract);
			}
		}

		return HdUtils.genMsg();
	}

	public void updatePortCar(PortCar portCar, CargoInfo cargoInfo) {

		String jpql2 = "select a from CCyRow a where a.cyAreaNo=:cyareano";
		QueryParamLs paramLs2 = new QueryParamLs();
		paramLs2.addParam("cyareano", cargoInfo.getCyAreaNo());
		List<CCyRow> cyRowNoList = JpaUtils.findAll(jpql2, paramLs2);
		if (cyRowNoList.size() > 0) {
			for (CCyRow cCyRow : cyRowNoList) {
				if (cargoInfo.getMlcs().compareTo(cCyRow.getMaxBayNum()) == 1) {
					String jpql3 = "select count(a) form PortCar a where a.cyAreaNo=:cyareano and a.cyRowNo=:cyrowno";
					QueryParamLs paramLs3 = new QueryParamLs();
					paramLs3.addParam("cyareano", cargoInfo.getCyAreaNo());
					paramLs3.addParam("cyrowno", cCyRow.getCyRowNo());
					List<BigDecimal> portCarlist = JpaUtils.findAll(jpql3, paramLs3);
					if (portCarlist.get(0).compareTo(cCyRow.getMaxBayNum()) == -1) {
						String jpql4 = "select a from CCyBay a where a.cyAreaNo=:cyareano and a.cyRowNo=:cyrowno and a.lockId = '0' order by a.cyBayNo asc";
						List<CCyBay> cCyBayList = JpaUtils.findAll(jpql4, paramLs3);
						if (cCyBayList.size() > 0) {
							CCyBay cCyBay = cCyBayList.get(0);
							portCar.setCyAreaNo(cCyBay.getCyAreaNo());
							portCar.setCyRowNo(cCyBay.getCyRowNo());
							portCar.setCyBayNo(cCyBay.getCyBayNo());
							portCar.setCyPlac(cCyBay.getCyPlac());
							cCyBay.setLockId("1");
							JpaUtils.update(cCyBay);
						}
						break;
					} else if (portCarlist.get(0).compareTo(cargoInfo.getMlcs()) == -1) {
						portCar.setCyAreaNo(cargoInfo.getCyAreaNo());
						portCar.setCyRowNo(cCyRow.getCyRowNo());
						portCar.setCyBayNo("####");
						portCar.setCyPlac("####");
					}
				} else {
					String jpql3 = "select a from CCyBay a where a.cyAreaNo=:cyareano and a.cyRowNo=:cyrowno and a.lockId = '0' order by a.cyBayNo asc";
					QueryParamLs paramLs3 = new QueryParamLs();
					paramLs3.addParam("cyareano", cargoInfo.getCyAreaNo());
					paramLs3.addParam("cyrowno", cCyRow.getCyRowNo());
					List<CCyBay> cCyBayList = JpaUtils.findAll(jpql3, paramLs3);
					if (cCyBayList.size() > 0) {
						CCyBay cCyBay = cCyBayList.get(0);
						portCar.setCyAreaNo(cCyBay.getCyAreaNo());
						portCar.setCyRowNo(cCyBay.getCyRowNo());
						portCar.setCyBayNo(cCyBay.getCyBayNo());
						portCar.setCyPlac(cCyBay.getCyPlac());
						cCyBay.setLockId("1");
						JpaUtils.update(cCyBay);
						if (cargoInfo.getMlcs().compareTo(cCyRow.getMaxBayNum()) == -1) {
							String jpql4 = "select count(a) from CCyBay a where a.cyAreaNo=:cyareano and a.cyRowNo=:cyrowno and a.lockId = '1' ";
							List<Long> ylcs = JpaUtils.findAll(jpql4, paramLs3);
							if (cargoInfo.getMlcs().compareTo(BigDecimal.valueOf(ylcs.get(0))) == 0) {
								List<CCyBay> cCyBayUncheckList = JpaUtils.findAll(jpql3, paramLs3);
								for (CCyBay bean : cCyBayUncheckList) {
									bean.setLockId("1");
								}
								JpaUtils.updateAll(cCyBayUncheckList);
							}
						}
						break;
					}
				}
			}
		}
	}
}
