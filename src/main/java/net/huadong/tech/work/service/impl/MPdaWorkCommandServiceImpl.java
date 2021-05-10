package net.huadong.tech.work.service.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.bean.HdRunTimeException;
import net.huadong.tech.base.entity.CBrand;
import net.huadong.tech.base.entity.CCarKind;
import net.huadong.tech.base.entity.CCarTyp;
import net.huadong.tech.base.entity.CClientCod;
import net.huadong.tech.base.entity.CCyArea;
import net.huadong.tech.base.entity.CCyBay;
import net.huadong.tech.base.entity.CCyRow;
import net.huadong.tech.base.entity.CDamage;
import net.huadong.tech.base.entity.CEmployee;
import net.huadong.tech.base.entity.CPort;
import net.huadong.tech.cargo.entity.TruckWork;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.PageInfo;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.tech.gate.entity.GateTruck;
import net.huadong.tech.his.entity.PortCarBak;
import net.huadong.tech.plan.entity.PlanGroup;
import net.huadong.tech.plan.entity.PlanPlacVin;
import net.huadong.tech.plan.entity.PlanRange;
import net.huadong.tech.privilege.entity.AuthUser;
import net.huadong.tech.privilege.entity.SysCode;
import net.huadong.tech.ship.entity.CShipData;
import net.huadong.tech.ship.entity.Ship;
import net.huadong.tech.ship.entity.ShipLoadPlan;
import net.huadong.tech.shipbill.entity.BillCar;
import net.huadong.tech.shipbill.entity.ContractIeDoc;
import net.huadong.tech.shipbill.entity.PortCar;
import net.huadong.tech.shipbill.entity.ShipBill;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiQueryParams;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;
import net.huadong.tech.thirdparty.service.Result;
import net.huadong.tech.util.HdUtils;
import net.huadong.tech.work.entity.CargoInfo;
import net.huadong.tech.work.entity.MoveCarPlan;
import net.huadong.tech.work.entity.WorkCommand;
import net.huadong.tech.work.entity.MPdaWorkCommand;
import net.huadong.tech.work.entity.WorkCommandBak;
import net.huadong.tech.work.entity.WorkQueue;
import net.huadong.tech.work.service.MPdaWorkCommandService;

/**
 * @author
 */
@Component
public class MPdaWorkCommandServiceImpl implements MPdaWorkCommandService {
	@Override
	public HdEzuiDatagridData find(HdQuery hdQuery) {
		String jpql = "select a from MPdaWorkCommand a where 1=1 ";
		String shipNo = hdQuery.getStr("shipNo");
		String type = hdQuery.getStr("type");
		QueryParamLs paramLs = new QueryParamLs();
		if (HdUtils.strNotNull(shipNo)) {
			jpql += "and a.shipNo=:shipNo ";
			paramLs.addParam("shipNo", shipNo);
		} else {
			jpql += "and a.shipNo=:shipNo ";
			paramLs.addParam("shipNo", "123456789##");
		}
		if ("XC".equals(type) || "NMPLXC".equals(type) || "WMPLXC".equals(type)) {
			jpql += "and a.workTyp =:workTyp ";
			paramLs.addParam("workTyp", "SI");
		} else if ("ZC".equals(type) || "NMPLZC".equals(type)) {
			jpql += "and a.workTyp =:workTyp ";
			paramLs.addParam("workTyp", "SO");
		} else if ("PLTZ".equals(type)) {
			jpql += "and a.workTyp =:workTyp ";
			paramLs.addParam("workTyp", "TZ");
		} else if ("NMPLJG".equals(type) || "WMPLJG".equals(type)) {
			jpql += "and a.workTyp =:workTyp ";
			paramLs.addParam("workTyp", "TI");
		}
		jpql += "order by a.inCyTim desc";
		List<MPdaWorkCommand> workCommandListAll = JpaUtils.findAll(jpql, paramLs);
		for (MPdaWorkCommand bc : workCommandListAll) {
			if (HdUtils.strNotNull(bc.getCarTyp())) {
				CCarTyp ccartyp = JpaUtils.findById(CCarTyp.class, bc.getCarTyp());
				if (ccartyp != null) {
					bc.setCarTypNam(ccartyp.getCarTypNam());
				}
			}
			if (HdUtils.strNotNull(bc.getCarKind())) {
				CCarKind carkind = JpaUtils.findById(CCarKind.class, bc.getCarKind());
				if (carkind != null) {
					bc.setCarKindNam(carkind.getCarKindNam());
				}
			}
			if (HdUtils.strNotNull(bc.getBrandCod())) {
				CBrand cbrand = JpaUtils.findById(CBrand.class, bc.getBrandCod());
				if (cbrand != null) {
					bc.setBrandNam(cbrand.getBrandNam());
				}
			}
			if (HdUtils.strNotNull(bc.getShipNo())) {
				Ship ship = JpaUtils.findById(Ship.class, bc.getShipNo());
				if (ship != null) {
					bc.setShipNam(ship.getcShipNam());
					bc.setVoyage(ship.getIvoyage() + "/" + ship.getEvoyage());
				}
			}
			if (HdUtils.strNotNull(bc.getInCyNam())) {
				CEmployee cEmployee = JpaUtils.findById(CEmployee.class, bc.getInCyNam());
				if (cEmployee != null) {
					bc.setInCyNamNam(cEmployee.getEmpNam());
				}
			}
			if (HdUtils.strNotNull(bc.getOutCyNam())) {
				CEmployee cEmployee = JpaUtils.findById(CEmployee.class, bc.getOutCyNam());
				if (cEmployee != null) {
					bc.setOutCyNamNam(cEmployee.getEmpNam());
				}
			}
			if (HdUtils.strNotNull(bc.getCyPlac())) {
				CCyArea cCyArea = JpaUtils.findById(CCyArea.class, bc.getCyPlac());
				if (cCyArea != null) {
					bc.setCyArea(cCyArea.getCyAreaNam());
				}
			}
		}
		HdEzuiDatagridData result = new HdEzuiDatagridData();
		result.setRows(workCommandListAll);
		return result;
	}

	@Transactional
	public HdMessageCode saveLxlh(String shipNo, String type) {
		if ("NMPLXC".equals(type) || "WMPLXC".equals(type)) {
			String jpql = "select a from WorkQueue a where a.workTyp = 'SI' and a.shipNo =:shipNo";
			QueryParamLs paramLs = new QueryParamLs();
			paramLs.addParam("shipNo", shipNo);
			List<WorkQueue> workQueueList = JpaUtils.findAll(jpql, paramLs);
			if (workQueueList.size() < 1) {
				throw new HdRunTimeException("请先确认已卸船开工！");
			}
			String jpql1 = "select a from MPdaWorkCommand a where a.confirmId =:confirmId and a.shipNo =:shipNo and a.workTyp =:workTyp";
			QueryParamLs paramLs1 = new QueryParamLs();
			paramLs1.addParam("confirmId", MPdaWorkCommand.UNCONFIRMED);
			paramLs1.addParam("shipNo", shipNo);
			if ("XC".equals(type) || "NMPLXC".equals(type) || "WMPLXC".equals(type)) {
				paramLs1.addParam("workTyp", "SI");
			} else if ("ZC".equals(type) || "NMPLZC".equals(type)) {
				paramLs1.addParam("workTyp", "SO");
			} else if ("PLTZ".equals(type)) {
				paramLs1.addParam("workTyp", "TZ");
			} else if ("NMPLJG".equals(type) || "WMPLJG".equals(type)) {
				paramLs1.addParam("workTyp", "TI");
			}
			List<MPdaWorkCommand> mPdaWorkCommandList = JpaUtils.findAll(jpql1, paramLs1);
			if (mPdaWorkCommandList.size() < 1) {
				throw new HdRunTimeException("离线数据为空！");
			}
			for (MPdaWorkCommand bean : mPdaWorkCommandList) {
				Ship ship = JpaUtils.findById(Ship.class, shipNo);
				String jpql2 = "select a from PortCar a where a.vinNo =:vinNo and a.currentStat =:currentStat and a.shipNo =:shipNo";
				QueryParamLs params2 = new QueryParamLs();
				params2.addParam("vinNo", bean.getVinNo());
				if ("1".equals(bean.getDirectId())){
					params2.addParam("currentStat", "5");
				} else {
					params2.addParam("currentStat", "2");
				}
				params2.addParam("shipNo", shipNo);
				List<PortCar> portCarList = JpaUtils.findAll(jpql2, params2);
				if (portCarList.size()>0){
					continue;
				}
				PortCar portCar = new PortCar();
				portCar.setiEId(Ship.JK);
				portCar.setTradeId(ship.getTradeId());
				portCar.setInPortNo(" ");
				if ("1".equals(bean.getDirectId())){
					portCar.setCurrentStat("5");
				} else {
					portCar.setCurrentStat("2");
				}
				if (Ship.WM.equals(ship.getTradeId())){
					portCar.setBillNo(bean.getBillNo());
				} else {
					portCar.setBillNo("--");
				}
				portCar.setShipNo(shipNo);
				portCar.setInCyTim(bean.getInCyTim());
				portCar.setRfidCardNo(bean.getVinNo());
				portCar.setVinNo(bean.getVinNo());
				if (HdUtils.strNotNull(bean.getBrandCod())) {
					portCar.setBrandCod(bean.getBrandCod());
				}
				portCar.setLengthOverId(bean.getLengthOverId());
				portCar.setCarTyp(bean.getCarTyp());
				portCar.setCarKind(bean.getCarKind());
				portCar.setCyAreaNo(bean.getCyPlac());
				portCar.setCyRowNo(bean.getCyRowNo1());
				portCar.setCyBayNo(bean.getCyBayNo1());
//				portCar.setCyRowNo("###");
//				portCar.setCyBayNo("###");
				portCar.setCyPlac(bean.getCyPlac());
				if (HdUtils.strNotNull(bean.getCarLevel())) {
					portCar.setCarLevel(bean.getCarLevel());
				}
				portCar.setDockCod(ship.getDockCod());
				portCar.setRemarks(bean.getRemarks());
				JpaUtils.save(portCar);

				// BillCar billCar = new BillCar();
				// billCar.setBillcarId(HdUtils.genUuid());
				// billCar.setShipbillId(shipBill.getShipbillId());
				// billCar.setShipNo(cargoInfo.getShipNo());
				// billCar.setBillNo(billNo);
				// billCar.setiEId(Ship.JK);
				// billCar.setTradeId(Ship.NM);
				// billCar.setBrandCod(cargoInfo.getBrandCod());
				// billCar.setCarKind(cargoInfo.getCarKind());
				// billCar.setCarTyp(cargoInfo.getCarTyp());
				// if (HdUtils.strNotNull(cargoInfo.getBrandCod())) {
				// billCar.setBrandCod(cargoInfo.getBrandCod());
				// }
				// billCar.setLengthOverId(cargoInfo.getLengthOverId());
				// // 超长标志 1
				// if ("1".equals(cargoInfo.getLengthOverId())) {
				// billCar.setLength(cargoInfo.getLength());
				// }
				// billCar.setLhFlag("1");// 理货标志 1代表理完
				// billCar.setRfidCardNo(portCar.getRfidCardNo());
				// billCar.setVinNo(portCar.getRfidCardNo());
				// billCar.setCarKind(cargoInfo.getCarKind());
				// billCar.setRemarks(cargoInfo.getRemarks());
				// billCar.setPortCarNo(portCar.getPortCarNo());
				// JpaUtils.save(billCar);

				WorkCommand workCommand = new WorkCommand();
				workCommand.setWorkQueueNo(workQueueList.get(0).getWorkQueueNo());
				workCommand.setRfidCardNo(portCar.getRfidCardNo());
				workCommand.setVinNo(portCar.getVinNo());
				workCommand.setPortCarNo(portCar.getPortCarNo());
				workCommand.setWorkTyp("SI");
				if (HdUtils.strNotNull(bean.getBrandCod())) {
					workCommand.setBrandCod(bean.getBrandCod());
				}
				workCommand.setCarTyp(bean.getCarTyp());
				workCommand.setLength(bean.getLength());
				workCommand.setInCyTim(bean.getInCyTim());
				workCommand.setShipWorkTim(bean.getInCyTim());
				workCommand.setFinishedId("0");
				workCommand.setLengthOverId(bean.getLengthOverId());
				workCommand.setUseMachId(bean.getUseMachId());
				workCommand.setUseWorkerId(bean.getUseWorkerId());
				workCommand.setNightId(bean.getNightId());
				workCommand.setHolidayId(bean.getHolidayId());
				workCommand.setQueueId(HdUtils.genUuid());
				workCommand.setInCyTim(bean.getInCyTim());
				workCommand.setInCyNam(bean.getInCyNam());
				workCommand.setShipNo(bean.getShipNo());
				workCommand.setBillNo(portCar.getBillNo());
				workCommand.setCyPlac(portCar.getCyPlac());
				workCommand.setCarKind(bean.getCarKind());
				workCommand.setRemarks(bean.getRemarks());
				workCommand.setQzId(bean.getQzId());
				if (HdUtils.strNotNull(bean.getDirectId())){
					workCommand.setDirectId(bean.getDirectId());
				}
				if (HdUtils.strNotNull(bean.getCarLevel())) {
					workCommand.setCarLevel(bean.getCarLevel());
				}
				workCommand.setVcexception(bean.getVcexception());
				workCommand.setLxPdaId(bean.getLxPdaId());
				JpaUtils.save(workCommand);
				
				bean.setConfirmId(MPdaWorkCommand.CONFIRMED);
				JpaUtils.update(bean);
			}
		} else if ("NMPLZC".equals(type)) {
			String jpql2 = "select a from WorkQueue a where a.workTyp = 'SO' and a.shipNo =:shipNo";
			QueryParamLs paramLs2 = new QueryParamLs();
			paramLs2.addParam("shipNo", shipNo);
			List<WorkQueue> workQueueList = JpaUtils.findAll(jpql2, paramLs2);
			if (workQueueList.size() < 1) {
				throw new HdRunTimeException("请确认该船已装船开工！");
			}
			String jpql1 = "select a from MPdaWorkCommand a where a.confirmId =:confirmId and a.shipNo =:shipNo and a.workTyp =:workTyp";
			QueryParamLs paramLs1 = new QueryParamLs();
			paramLs1.addParam("confirmId", MPdaWorkCommand.UNCONFIRMED);
			paramLs1.addParam("shipNo", shipNo);
			if ("XC".equals(type) || "NMPLXC".equals(type) || "WMPLXC".equals(type)) {
				paramLs1.addParam("workTyp", "SI");
			} else if ("ZC".equals(type) || "NMPLZC".equals(type)) {
				paramLs1.addParam("workTyp", "SO");
			} else if ("PLTZ".equals(type)) {
				paramLs1.addParam("workTyp", "TZ");
			} else if ("NMPLJG".equals(type) || "WMPLJG".equals(type)) {
				paramLs1.addParam("workTyp", "TI");
			}
			List<MPdaWorkCommand> mPdaWorkCommandList = JpaUtils.findAll(jpql1, paramLs1);
			if (mPdaWorkCommandList.size() < 1) {
				throw new HdRunTimeException("离线数据为空！");
			}
			for (MPdaWorkCommand bean : mPdaWorkCommandList) {
				PortCar portCar = JpaUtils.findById(PortCar.class, bean.getPortCarNo());
				if (portCar == null) {
					throw new HdRunTimeException("场地车不存在！");
				}
				String portCarNo = portCar.getPortCarNo().toString();

				portCar.setCurrentStat("5");
				portCar.setCyPlac(null);
				portCar.setOutCyTim(bean.getInCyTim());
				portCar.setOutPortNo(shipNo);
				portCar.setRemarks(bean.getRemarks());
				JpaUtils.update(portCar);

				WorkCommand workCommand = new WorkCommand();
				BeanUtils.copyProperties(bean, workCommand);
				workCommand.setWorkQueueNo(workQueueList.get(0).getWorkQueueNo());
				workCommand.setPortCarNo(bean.getPortCarNo());
				workCommand.setWorkTyp("SO");
				workCommand.setBrandCod(portCar.getBrandCod());
				workCommand.setCarTyp(portCar.getCarTyp());
				workCommand.setCarKind(portCar.getCarKind());
				workCommand.setLength(portCar.getLength());
				workCommand.setWidth(portCar.getWidth());
				// workCommand.setInCyTim(HdUtils.getDateTime());
				workCommand.setShipWorkTim(bean.getInCyTim());
				workCommand.setFinishedId("0");
				workCommand.setNightId(bean.getNightId());
				workCommand.setHolidayId(bean.getHolidayId());
				workCommand.setQueueId(HdUtils.genUuid());
				workCommand.setRfidCardNo(portCar.getRfidCardNo());
				workCommand.setRemarks(bean.getRemarks());
				workCommand.setOutCyNam(bean.getInCyNam());
				workCommand.setOutCyTim(bean.getInCyTim());
				if (bean.getInCyTim() != null) {
					workCommand.setInCyTim(bean.getInCyTim());
				}
				workCommand.setInCyNam(bean.getInCyNam());
				workCommand.setLxPdaId(bean.getLxPdaId());
				JpaUtils.save(workCommand);
				
				bean.setConfirmId(MPdaWorkCommand.CONFIRMED);
				JpaUtils.update(bean);
			}
		}
		return HdUtils.genMsg();
	}

	@Transactional
	public MPdaWorkCommand findOne(String queueId) {
		MPdaWorkCommand bean = new MPdaWorkCommand();
		if (HdUtils.strNotNull(queueId)) {
			bean = JpaUtils.findById(MPdaWorkCommand.class, queueId);
			if (bean.getPortCarNo() != null) {
				PortCar portCar = JpaUtils.findById(PortCar.class, bean.getPortCarNo());
				bean.setCyArea(portCar.getCyAreaNo());
				if (HdUtils.strNotNull(portCar.getTranPortCod())) {
					String sql = "select a from CPort a where a.portShort =:portShort";
					QueryParamLs param = new QueryParamLs();
					param.addParam("portShort", portCar.getTranPortCod());
					List<CPort> cPortList = JpaUtils.findAll(sql, param);
					if (cPortList.size() > 0) {
						String portNam = cPortList.get(0).getcPortNam();
						String jpql = "select a from SysCode a where a.fieldCod =:fieldCod and a.name =:name";
						QueryParamLs paramLs = new QueryParamLs();
						paramLs.addParam("fieldCod", "FLOW_AREA");
						paramLs.addParam("name", portNam);
						List<SysCode> list = JpaUtils.findAll(jpql, paramLs);
						if (list.size() > 0) {
							bean.setTranPortCodNam(list.get(0).getCode());
						}
					}
				}
			}
		}
		return bean;
	}

	@Transactional
	public void removeAll(String queueIds) {
		List<String> queueIdList = HdUtils.paraseStrs(queueIds);
		for (String queueId : queueIdList) {
			JpaUtils.remove(MPdaWorkCommand.class, queueId);
		}
	}
	
	
	@Override
	public HdMessageCode save(HdEzuiSaveDatagridData<MPdaWorkCommand> hdEzuiSaveDatagridData) {
		// TODO Auto-generated method stub
		return JpaUtils.save(hdEzuiSaveDatagridData);
	}

	@Override
	public HdMessageCode saveone(@RequestBody MPdaWorkCommand workCommand, String type) {
		if ("XC".equals(type)) {
			String jpql1 = "select a from WorkQueue a where a.workTyp = 'SI' and a.shipNo =:shipNo";
			QueryParamLs paramLs1 = new QueryParamLs();
			paramLs1.addParam("shipNo", workCommand.getShipNo());
			List<WorkQueue> workQueueList = JpaUtils.findAll(jpql1, paramLs1);
			if (workQueueList.size() < 1) {
				throw new HdRunTimeException("保存失败");
			}
			if (HdUtils.strNotNull(workCommand.getBillNo())) {
				String jpql2 = "select a from BillCar a where a.billNo =:billNo";
				QueryParamLs paramLs2 = new QueryParamLs();
				paramLs2.addParam("billNo", workCommand.getBillNo());
				List<BillCar> billCarList = JpaUtils.findAll(jpql2, paramLs2);
				if (billCarList.size() <= 0) {
					throw new HdRunTimeException("出错了！");
				}
				BillCar billCar = billCarList.get(0);
				workCommand.setWorkQueueNo(workQueueList.get(0).getWorkQueueNo());
				workCommand.setPortCarNo(billCar.getPortCarNo());
				workCommand.setWorkTyp("SI");
				workCommand.setBrandCod(billCar.getBrandCod());
				workCommand.setCarTyp(billCar.getCarTyp());
				workCommand.setCarKind(billCar.getCarKind());
				workCommand.setLength(billCar.getLength());
				workCommand.setWidth(billCar.getWidth());
				workCommand.setInCyTim(HdUtils.getDateTime());
				workCommand.setFinishedId("0");
				workCommand.setNightId("0");
				workCommand.setHolidayId("0");
				workCommand.setShipWorkTim(HdUtils.getDateTime());
				workCommand.setQueueId(HdUtils.genUuid());
				JpaUtils.save(workCommand);

				PortCar portCar = JpaUtils.findById(PortCar.class, workCommand.getPortCarNo());
				portCar.setCurrentStat("2");
				portCar.setCyPlac(workCommand.getCyPlac());
				portCar.setInCyTim(HdUtils.getDateTime());
				portCar.setInToolNo(workCommand.getShipNo());

				JpaUtils.save(portCar);
			} else {
				Ship ship = JpaUtils.findById(Ship.class, workCommand.getShipNo());

				PortCar portCar = new PortCar();
				BeanUtils.copyProperties(ship, portCar);
				JpaUtils.save(portCar);

				BillCar billCar = new BillCar();
				BeanUtils.copyProperties(ship, billCar);
				billCar.setPortCarNo(portCar.getPortCarNo());
				JpaUtils.save(billCar);

				workCommand.setWorkQueueNo(workQueueList.get(0).getWorkQueueNo());
				workCommand.setPortCarNo(portCar.getPortCarNo());
				workCommand.setWorkTyp("SI");
				JpaUtils.save(workCommand);

			}
		} else if ("ZC".equals(type)) {
			String jpql1 = "select a from WorkQueue a where a.workTyp = 'SO' and a.shipNo =:shipNo";
			QueryParamLs paramLs1 = new QueryParamLs();
			paramLs1.addParam("shipNo", workCommand.getShipNo());
			List<WorkQueue> workQueueList = JpaUtils.findAll(jpql1, paramLs1);
			if (workQueueList.size() < 1) {
				throw new HdRunTimeException("保存失败");
			}
			BillCar billCar = JpaUtils.findById(BillCar.class, workCommand.getBillNo());
			workCommand.setWorkQueueNo(workQueueList.get(0).getWorkQueueNo());
			workCommand.setPortCarNo(billCar.getPortCarNo());
			workCommand.setWorkTyp("SO");
			workCommand.setBrandCod(billCar.getBrandCod());
			workCommand.setCarTyp(billCar.getCarTyp());
			workCommand.setCarKind(billCar.getCarKind());
			workCommand.setLength(billCar.getLength());
			workCommand.setWidth(billCar.getWidth());
			workCommand.setInCyTim(HdUtils.getDateTime());
			workCommand.setFinishedId("0");
			workCommand.setNightId("0");
			workCommand.setHolidayId("0");
			JpaUtils.save(workCommand);

			PortCar portCar = JpaUtils.findById(PortCar.class, workCommand.getPortCarNo());
			portCar.setCurrentStat("5");
			portCar.setCyPlac(null);
			portCar.setOutCyTim(HdUtils.getDateTime());
			portCar.setOutPortNo(workCommand.getShipNo());
			JpaUtils.save(portCar);

		}

		return HdUtils.genMsg();
	}

	@Override
	public HdMessageCode saveZclh(CargoInfo cargoInfo, String type, String portCarNos) {
		List<String> portCarNoList = HdUtils.paraseStrs(portCarNos);
		for (String portCarNo : portCarNoList) {

			String jpql1 = "select a from WorkQueue a where a.workTyp = 'SO' and a.shipNo =:shipNo";
			QueryParamLs paramLs1 = new QueryParamLs();
			paramLs1.addParam("shipNo", cargoInfo.getShipNo());
			List<WorkQueue> workQueueList = JpaUtils.findAll(jpql1, paramLs1);
			if (workQueueList.size() < 1) {
				throw new HdRunTimeException("该作业队列存在问题！");
			}
			String jpql = "select a from ShipBill a where a.shipNo =:shipNo and a.billNo =:billNo";
			QueryParamLs paramLs2 = new QueryParamLs();
			paramLs2.addParam("shipNo", workQueueList.get(0).getShipNo());
			paramLs2.addParam("billNo", JpaUtils.findById(PortCar.class, new BigDecimal(portCarNo)).getBillNo());
			List<ShipBill> shipBillList = JpaUtils.findAll(jpql, paramLs2);
			if (shipBillList.size() == 0) {
				throw new HdRunTimeException("请先完善该船出口舱单！");
			}
			PortCar portCar = JpaUtils.findById(PortCar.class, new BigDecimal(portCarNo));
			portCar.setCurrentStat("5");
			portCar.setCyPlac(null);
			portCar.setOutCyTim(cargoInfo.getOutCyTim());
			portCar.setOutPortNo(cargoInfo.getShipNo());
			portCar.setRemarks(cargoInfo.getRemarks());
			JpaUtils.update(portCar);

			MPdaWorkCommand workCommand = new MPdaWorkCommand();
			BeanUtils.copyProperties(cargoInfo, workCommand);
			workCommand.setWorkQueueNo(workQueueList.get(0).getWorkQueueNo());
			workCommand.setPortCarNo(portCar.getPortCarNo());
			workCommand.setWorkTyp("SO");
			workCommand.setBrandCod(portCar.getBrandCod());
			workCommand.setCarTyp(portCar.getCarTyp());
			workCommand.setCarKind(portCar.getCarKind());
			workCommand.setLength(portCar.getLength());
			workCommand.setWidth(portCar.getWidth());
			// workCommand.setInCyTim(HdUtils.getDateTime());
			workCommand.setShipWorkTim(cargoInfo.getOutCyTim());
			workCommand.setFinishedId("0");
			workCommand.setNightId(cargoInfo.getNightId());
			workCommand.setHolidayId(cargoInfo.getHolidayId());
			workCommand.setQueueId(HdUtils.genUuid());
			workCommand.setRfidCardNo(portCar.getRfidCardNo());
			workCommand.setRemarks(cargoInfo.getRemarks());
			workCommand.setOutCyNam(cargoInfo.getInCyNam());
			workCommand.setInCyNam(cargoInfo.getInCyNam());
			workCommand.setInCyTim(cargoInfo.getOutCyTim());
			JpaUtils.save(workCommand);

			BillCar billCar = new BillCar();
			BeanUtils.copyProperties(portCar, billCar);
			billCar.setBillcarId(HdUtils.genUuid());
			billCar.setShipbillId(shipBillList.get(0).getShipbillId());
			JpaUtils.save(billCar);
		}
		return HdUtils.genMsg();
	}

	// 批量装船理货
	@Override
	public HdMessageCode saveNmzclh(CargoInfo cargoInfo, String type) {
		String jqpl2 = "select a from PortCar a where a.currentStat = '2'";
		QueryParamLs paramLs = new QueryParamLs();
		String shipNo = cargoInfo.getShipNo();
		String cyAreaNo = cargoInfo.getCyAreaNo();
		String billNo = cargoInfo.getBillNo();
		String carTyp = cargoInfo.getCarTyp();
		String tranPortCod = cargoInfo.getTranPortCod();
		if (HdUtils.strNotNull(shipNo)) {
			jqpl2 += " and a.shipNo =:shipNo";
			paramLs.addParam("shipNo", shipNo);
		}
		if (HdUtils.strNotNull(carTyp)) {
			jqpl2 += " and a.carTyp =:carTyp";
			paramLs.addParam("carTyp", carTyp);
		}
		if (HdUtils.strNotNull(billNo)) {
			jqpl2 += " and a.billNo =:billNo";
			paramLs.addParam("billNo", billNo);
		}
		if (HdUtils.strNotNull(cyAreaNo)) {
			jqpl2 += " and a.cyAreaNo =:cyAreaNo";
			paramLs.addParam("cyAreaNo", cyAreaNo);
		}
		if ("NMPLZC".equals(type)) {
			jqpl2 += " and a.iEId = 'E' and a.tradeId = '1'";
			jqpl2 += " and a.tranPortCod =:tranPortCod";
			paramLs.addParam("tranPortCod", tranPortCod);
		} else if ("WMPLZC".equals(type)) {
			jqpl2 += " and a.iEId = 'E' and a.tradeId = '2'";
		}
		List<PortCar> portCarList = JpaUtils.findAll(jqpl2, paramLs);
		if (portCarList.size() <= 0) {
			throw new HdRunTimeException("场地车辆信息有误，请确认提单选择正确！");
		}
		for (int i = 0; i < cargoInfo.getRcsl().intValue(); i++) {
			PortCar portCar = portCarList.get(i);
			String portCarNo = portCar.getPortCarNo().toString();
			String jpql1 = "select a from WorkQueue a where a.workTyp = 'SO' and a.shipNo =:shipNo";
			QueryParamLs paramLs1 = new QueryParamLs();
			paramLs1.addParam("shipNo", cargoInfo.getShipNo());
			List<WorkQueue> workQueueList = JpaUtils.findAll(jpql1, paramLs1);
			if (workQueueList.size() < 1) {
				throw new HdRunTimeException("该作业队列存在问题！");
			}
			String jpql = "select a from ShipBill a where a.shipNo =:shipNo and a.billNo =:billNo";
			QueryParamLs paramLs2 = new QueryParamLs();
			paramLs2.addParam("shipNo", workQueueList.get(0).getShipNo());
			paramLs2.addParam("billNo", JpaUtils.findById(PortCar.class, new BigDecimal(portCarNo)).getBillNo());
			List<ShipBill> shipBillList = JpaUtils.findAll(jpql, paramLs2);
			if (shipBillList.size() == 0) {
				throw new HdRunTimeException("请先完善该船出口舱单！");
			}
			portCar.setCurrentStat("5");
			portCar.setCyPlac(null);
			portCar.setOutCyTim(cargoInfo.getOutCyTim());
			portCar.setOutPortNo(cargoInfo.getShipNo());
			portCar.setRemarks(cargoInfo.getRemarks());
			JpaUtils.update(portCar);

			MPdaWorkCommand workCommand = new MPdaWorkCommand();
			BeanUtils.copyProperties(cargoInfo, workCommand);
			workCommand.setWorkQueueNo(workQueueList.get(0).getWorkQueueNo());
			workCommand.setPortCarNo(portCar.getPortCarNo());
			workCommand.setWorkTyp("SO");
			workCommand.setBrandCod(portCar.getBrandCod());
			workCommand.setCarTyp(portCar.getCarTyp());
			workCommand.setCarKind(portCar.getCarKind());
			workCommand.setLength(portCar.getLength());
			workCommand.setWidth(portCar.getWidth());
			// workCommand.setInCyTim(HdUtils.getDateTime());
			workCommand.setShipWorkTim(cargoInfo.getOutCyTim());
			workCommand.setFinishedId("0");
			workCommand.setNightId(cargoInfo.getNightId());
			workCommand.setHolidayId(cargoInfo.getHolidayId());
			workCommand.setQueueId(HdUtils.genUuid());
			workCommand.setRfidCardNo(portCar.getRfidCardNo());
			workCommand.setRemarks(cargoInfo.getRemarks());
			workCommand.setOutCyNam(cargoInfo.getInCyNam());
			workCommand.setOutCyTim(cargoInfo.getOutCyTim());
			if (portCar.getInCyTim() != null) {
				workCommand.setInCyTim(portCar.getInCyTim());
			}
			JpaUtils.save(workCommand);

			BillCar billCar = new BillCar();
			BeanUtils.copyProperties(portCar, billCar);
			billCar.setBillcarId(HdUtils.genUuid());
			billCar.setShipbillId(shipBillList.get(0).getShipbillId());
			JpaUtils.save(billCar);
		}
		return HdUtils.genMsg();
	}

	// 批量转栈理货
	@Override
	@Transactional
	public HdMessageCode saveZzlh(CargoInfo cargoInfo, String type) {
		String jqpl2 = "select a from PortCar a where a.currentStat = '2' and a.iEId = 'I' and a.tradeId = '2'";
		QueryParamLs paramLs = new QueryParamLs();
		String shipNo = cargoInfo.getShipNo();
		String cyAreaNo = cargoInfo.getCyAreaNo();
		String billNo = cargoInfo.getBillNo();
		String carTyp = cargoInfo.getCarTyp();
		if (HdUtils.strNotNull(shipNo)) {
			jqpl2 += " and a.shipNo =:shipNo";
			paramLs.addParam("shipNo", shipNo);
		}
		if (HdUtils.strNotNull(carTyp)) {
			jqpl2 += " and a.carTyp =:carTyp";
			paramLs.addParam("carTyp", carTyp);
		}
		if (HdUtils.strNotNull(billNo)) {
			jqpl2 += " and a.billNo =:billNo";
			paramLs.addParam("billNo", billNo);
		}
		if (HdUtils.strNotNull(cyAreaNo)) {
			jqpl2 += " and a.cyAreaNo =:cyAreaNo";
			paramLs.addParam("cyAreaNo", cyAreaNo);
		}
		List<PortCar> portCarList = JpaUtils.findAll(jqpl2, paramLs);
		if (portCarList.size() <= 0) {
			throw new HdRunTimeException("场地车辆信息有误，请确认提单选择正确！");
		}
		for (int i = 0; i < cargoInfo.getRcsl().intValue(); i++) {
			PortCar portCar = portCarList.get(i);
			String jpql1 = "select a from WorkQueue a where a.workTyp = 'TZ' and a.shipNo =:shipNo";
			QueryParamLs paramLs1 = new QueryParamLs();
			paramLs1.addParam("shipNo", cargoInfo.getShipNo());
			List<WorkQueue> workQueueList = JpaUtils.findAll(jpql1, paramLs1);
			if (workQueueList.size() < 1) {
				throw new HdRunTimeException("该作业队列存在问题！");
			}
			String truckNo = workQueueList.get(0).getTruckNo();
			String jpql = "select a from GateTruck a where a.truckNo =:truckNo";
			QueryParamLs paramLs2 = new QueryParamLs();
			paramLs2.addParam("truckNo", truckNo);
			List<GateTruck> gateTruckList = JpaUtils.findAll(jpql, paramLs2);
			if (gateTruckList.size() < 1) {
				throw new HdRunTimeException("该作业进出闸存在问题！");
			}
			GateTruck gateTruck = gateTruckList.get(0);

			portCar.setCurrentStat("5");
			portCar.setCyPlac(null);
			portCar.setOutCyTim(cargoInfo.getOutCyTim());
			portCar.setOutPortNo(cargoInfo.getShipNo());
			portCar.setTransId("1");
			portCar.setTransCorp(cargoInfo.getTransCorp());
			JpaUtils.update(portCar);

			MPdaWorkCommand workCommand = new MPdaWorkCommand();
			BeanUtils.copyProperties(cargoInfo, workCommand);
			workCommand.setWorkQueueNo(workQueueList.get(0).getWorkQueueNo());
			workCommand.setPortCarNo(portCar.getPortCarNo());
			workCommand.setWorkTyp("TZ");
			workCommand.setBrandCod(portCar.getBrandCod());
			workCommand.setCarTyp(portCar.getCarTyp());
			workCommand.setCarKind(portCar.getCarKind());
			workCommand.setLength(portCar.getLength());
			workCommand.setWidth(portCar.getWidth());
			// workCommand.setInCyTim(HdUtils.getDateTime());
			workCommand.setFinishedId("0");
			// workCommand.setNightId(cargoInfo.getNightId());
			// workCommand.setHolidayId(cargoInfo.getHolidayId());
			workCommand.setQueueId(HdUtils.genUuid());
			workCommand.setRfidCardNo(portCar.getRfidCardNo());
			// workCommand.setRemarks(cargoInfo.getRemarks());
			workCommand.setOutCyNam(cargoInfo.getInCyNam());
			workCommand.setOutCyTim(cargoInfo.getOutCyTim());
			workCommand.setTruckNo(gateTruck.getTruckNo());
			JpaUtils.save(workCommand);

			// TruckWork truckWork = new TruckWork();
			// BeanUtils.copyProperties(portCar, truckWork);
			// truckWork.setContractNo(workQueueList.get(0).getContractNo());
			// truckWork.setTruckNo(gateTruck.getTruckNo());
			// truckWork.setInGateNo(gateTruck.getInGatNo());
			// truckWork.setInRecNam(gateTruck.getInRecNam());
			// truckWork.setWorkNam(cargoInfo.getInCyNam());
			// truckWork.setWorkTim(cargoInfo.getOutCyTim());
			// truckWork.setInGatTim(HdUtils.getDateTime());
			// truckWork.setCarryId("T");
			// if(TruckWork.BC.equals(cargoInfo.getTransCorp())){
			// truckWork.setCarryWay("1");
			// }else{
			// truckWork.setCarryWay("0");
			// }
			// truckWork.setVinNo(portCar.getVinNo());
			// truckWork.setIngateId(gateTruck.getIngateId());
			// JpaUtils.save(truckWork);

			// BillCar billCar = new BillCar();
			// BeanUtils.copyProperties(portCar, billCar);
			// billCar.setBillcarId(HdUtils.genUuid());
			// billCar.setShipbillId(shipBillList.get(0).getShipbillId());
			// JpaUtils.save(billCar);
		}
		return HdUtils.genMsg();
	}

	// 批量卸船理货
	@Override
	public HdMessageCode saveNmXclh(CargoInfo cargoInfo) {
		String jpql = "select a from WorkQueue a where a.workTyp = 'SI' and a.shipNo =:shipNo";
		QueryParamLs paramLs = new QueryParamLs();
		paramLs.addParam("shipNo", cargoInfo.getShipNo());
		List<WorkQueue> workQueueList = JpaUtils.findAll(jpql, paramLs);
		if (workQueueList.size() < 1) {
			throw new HdRunTimeException("不存在卸船作业队列！");
		}
		if (cargoInfo.getRcsl() != null) {
			int rcsl = cargoInfo.getRcsl().intValue();
			String dockCod = "";
			String billNo = "";
			Ship ship = JpaUtils.findById(Ship.class, cargoInfo.getShipNo());
			if (HdUtils.strNotNull(ship.getShipCodId())) {
				CShipData cShipData = JpaUtils.findById(CShipData.class, ship.getShipCodId());
				if (HdUtils.strNotNull(cargoInfo.getBrandCod())) {
					CBrand cBrand = JpaUtils.findById(CBrand.class, cargoInfo.getBrandCod());
					if (HdUtils.strIsNull(cBrand.getBrandShort())) {
						throw new HdRunTimeException("品牌简称为空 请先维护品牌简称信息！");
					}
					billNo = cShipData.getShipShort() + " " + ship.getIvoyage() + cBrand.getBrandShort();
				} else {
					throw new HdRunTimeException("请输入品牌信息！");
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
			ShipBill shipBill = new ShipBill();
			if (HdUtils.strNotNull(cargoInfo.getShipNo())) {
				String jpql1 = "select a from ShipBill a where a.iEId = 'I' ";
				QueryParamLs query = new QueryParamLs();
				if (cargoInfo.getShipNo() != null) {
					jpql1 += "and a.shipNo =:shipNo ";
					query.addParam("shipNo", cargoInfo.getShipNo());
				}
				if (HdUtils.strNotNull(billNo)) {
					jpql1 += "and a.billNo =:billNo ";
					query.addParam("billNo", cargoInfo.getBillNo());
				}
				if (HdUtils.strNotNull(dockCod)) {
					jpql1 += "and a.dockCod =:dockCod";
					query.addParam("dockCod", dockCod);
				}
				List<ShipBill> shipBillList = JpaUtils.findAll(jpql1, query);

				if (shipBillList.size() > 0) {
					shipBill = shipBillList.get(0);
					if (shipBill.getPieces() != null) {
						shipBill.setPieces(shipBill.getPieces().add(cargoInfo.getRcsl()));
					}
					JpaUtils.update(shipBill);
				} else {
					shipBill.setShipbillId(HdUtils.genUuid());
					shipBill.setShipNo(cargoInfo.getShipNo());
					shipBill.setBillNo(billNo);
					shipBill.setiEId("I");
					shipBill.setTradeId(Ship.NM);
					shipBill.setBrandCod(cargoInfo.getBrandCod());
					shipBill.setCarTyp(cargoInfo.getCarTyp());
					if (cargoInfo.getRcsl() != null) {
						shipBill.setPieces(cargoInfo.getRcsl());
					}
					shipBill.setDockCod(dockCod);
					shipBill.setSendFlag("0");
					shipBill.setConfirmId("0");
					shipBill.setExitCustomId("0");
					JpaUtils.save(shipBill);
				}
			}

			for (int i = 0; i < rcsl; i++) {
				if (HdUtils.strNotNull(billNo)) {
					PortCar portCar = new PortCar();
					portCar.setiEId(Ship.JK);
					portCar.setTradeId(Ship.NM);
					portCar.setInPortNo(" ");
					portCar.setCurrentStat("2");
					portCar.setBillNo(billNo);
					portCar.setShipNo(cargoInfo.getShipNo());
					portCar.setInCyTim(cargoInfo.getInCyTim());
					portCar.setRfidCardNo("vepc" + new Date().getTime());
					portCar.setVinNo(portCar.getRfidCardNo());
					if (HdUtils.strNotNull(cargoInfo.getBrandCod())) {
						portCar.setBrandCod(cargoInfo.getBrandCod());
					}
					portCar.setLengthOverId(cargoInfo.getLengthOverId());
					portCar.setCarTyp(cargoInfo.getCarTyp());
					portCar.setCarKind(cargoInfo.getCarKind());
					portCar.setCyAreaNo(cargoInfo.getCyAreaNo());
					portCar.setCyRowNo("###");
					portCar.setCyBayNo("###");
					portCar.setCyPlac("###");
					if (HdUtils.strNotNull(cargoInfo.getCarLevel())) {
						portCar.setCarLevel(cargoInfo.getCarLevel());
					}
					portCar.setDockCod(dockCod);
					portCar.setRemarks(cargoInfo.getRemarks());
					JpaUtils.save(portCar);

					BillCar billCar = new BillCar();
					billCar.setBillcarId(HdUtils.genUuid());
					billCar.setShipbillId(shipBill.getShipbillId());
					billCar.setShipNo(cargoInfo.getShipNo());
					billCar.setBillNo(billNo);
					billCar.setiEId(Ship.JK);
					billCar.setTradeId(Ship.NM);
					billCar.setBrandCod(cargoInfo.getBrandCod());
					billCar.setCarKind(cargoInfo.getCarKind());
					billCar.setCarTyp(cargoInfo.getCarTyp());
					if (HdUtils.strNotNull(cargoInfo.getBrandCod())) {
						billCar.setBrandCod(cargoInfo.getBrandCod());
					}
					billCar.setLengthOverId(cargoInfo.getLengthOverId());
					// 超长标志 1
					if ("1".equals(cargoInfo.getLengthOverId())) {
						billCar.setLength(cargoInfo.getLength());
					}
					billCar.setLhFlag("1");// 理货标志 1代表理完
					billCar.setRfidCardNo(portCar.getRfidCardNo());
					billCar.setVinNo(portCar.getRfidCardNo());
					billCar.setCarKind(cargoInfo.getCarKind());
					billCar.setRemarks(cargoInfo.getRemarks());
					billCar.setPortCarNo(portCar.getPortCarNo());
					JpaUtils.save(billCar);

					MPdaWorkCommand workCommand = new MPdaWorkCommand();
					workCommand.setWorkQueueNo(workQueueList.get(0).getWorkQueueNo());
					workCommand.setRfidCardNo(portCar.getRfidCardNo());
					workCommand.setVinNo(portCar.getVinNo());
					workCommand.setPortCarNo(portCar.getPortCarNo());
					workCommand.setWorkTyp("SI");
					if (HdUtils.strNotNull(cargoInfo.getBrandCod())) {
						workCommand.setBrandCod(cargoInfo.getBrandCod());
					}
					workCommand.setCarTyp(cargoInfo.getCarTyp());
					workCommand.setLength(cargoInfo.getLength());
					workCommand.setInCyTim(cargoInfo.getInCyTim());
					workCommand.setShipWorkTim(cargoInfo.getInCyTim());
					workCommand.setFinishedId("0");
					workCommand.setLengthOverId(cargoInfo.getLengthOverId());
					workCommand.setUseMachId(cargoInfo.getUseMachId());
					workCommand.setUseWorkerId(cargoInfo.getUseWorkerId());
					workCommand.setNightId(cargoInfo.getNightId());
					workCommand.setHolidayId(cargoInfo.getHolidayId());
					workCommand.setQueueId(HdUtils.genUuid());
					workCommand.setInCyTim(cargoInfo.getInCyTim());
					workCommand.setInCyNam(cargoInfo.getInCyNam());
					workCommand.setShipNo(cargoInfo.getShipNo());
					workCommand.setBillNo(billNo);
					workCommand.setCyPlac(portCar.getCyPlac());
					workCommand.setCarKind(cargoInfo.getCarKind());
					workCommand.setRemarks(cargoInfo.getRemarks());
					if (HdUtils.strNotNull(cargoInfo.getCarLevel())) {
						workCommand.setCarLevel(cargoInfo.getCarLevel());
					}
					JpaUtils.save(workCommand);
				}
			}
		}
		return HdUtils.genMsg();
	}

	// 批量卸船理货
	@Override
	public HdMessageCode saveXclh(CargoInfo cargoInfo) {
		String jpql = "select a from WorkQueue a where a.workTyp = 'SI' and a.shipNo =:shipNo";
		QueryParamLs paramLs = new QueryParamLs();
		paramLs.addParam("shipNo", cargoInfo.getShipNo());
		List<WorkQueue> workQueueList = JpaUtils.findAll(jpql, paramLs);
		if (workQueueList.size() < 1) {
			throw new HdRunTimeException("不存在卸船作业队列！");
		}
		if (cargoInfo.getRcsl() != null) {
			int rcsl = cargoInfo.getRcsl().intValue();
			for (int i = 0; i < rcsl; i++) {
				if (HdUtils.strNotNull(cargoInfo.getBillNo())) {
					String jpql1 = "select a from BillCar a where a.billNo =:billNo and a.shipNo =:shipNo and a.lhFlag = '0' order by a.recTim asc";
					QueryParamLs paramLs1 = new QueryParamLs();
					paramLs1.addParam("billNo", cargoInfo.getBillNo());
					paramLs1.addParam("shipNo", cargoInfo.getShipNo());
					List<BillCar> billCarList = JpaUtils.findAll(jpql1, paramLs1);
					if (billCarList.size() <= 0) {
						throw new HdRunTimeException("舱单明细出错了！");
					}
					BillCar billCar = billCarList.get(0);
					billCar.setCarTyp(cargoInfo.getCarTyp());
					if (HdUtils.strNotNull(cargoInfo.getBrandCod())) {
						billCar.setBrandCod(cargoInfo.getBrandCod());
					}
					billCar.setLengthOverId(cargoInfo.getLengthOverId());
					// 超长标志 1
					if ("1".equals(cargoInfo.getLengthOverId())) {
						billCar.setLength(cargoInfo.getLength());
					}
					billCar.setLhFlag("1");// 理货标志 1代表理完
					billCar.setRfidCardNo("vepc" + new Date().getTime());
					billCar.setVinNo(billCar.getRfidCardNo());
					billCar.setCarKind(cargoInfo.getCarKind());
					billCar.setRemarks(cargoInfo.getRemarks());
					JpaUtils.update(billCar);

					PortCar portCar = JpaUtils.findById(PortCar.class, billCar.getPortCarNo());
					portCar.setCurrentStat("2");
					portCar.setInCyTim(cargoInfo.getInCyTim());
					portCar.setRfidCardNo(billCar.getRfidCardNo());
					portCar.setVinNo(billCar.getVinNo());
					if (HdUtils.strNotNull(cargoInfo.getBrandCod())) {
						portCar.setBrandCod(cargoInfo.getBrandCod());
					}
					portCar.setLengthOverId(cargoInfo.getLengthOverId());
					portCar.setCarTyp(cargoInfo.getCarTyp());
					portCar.setCarKind(cargoInfo.getCarKind());
					portCar.setCyAreaNo(cargoInfo.getCyAreaNo());
					portCar.setCyRowNo("###");
					portCar.setCyBayNo("###");
					portCar.setCyPlac("###");
					if (HdUtils.strNotNull(cargoInfo.getCarLevel())) {
						portCar.setCarLevel(cargoInfo.getCarLevel());
					}
					portCar.setRemarks(cargoInfo.getRemarks());
					JpaUtils.update(portCar);

					MPdaWorkCommand workCommand = new MPdaWorkCommand();
					workCommand.setWorkQueueNo(workQueueList.get(0).getWorkQueueNo());
					workCommand.setRfidCardNo(billCar.getRfidCardNo());
					workCommand.setVinNo(billCar.getVinNo());
					workCommand.setPortCarNo(billCar.getPortCarNo());
					workCommand.setWorkTyp("SI");
					if (HdUtils.strNotNull(cargoInfo.getBrandCod())) {
						workCommand.setBrandCod(cargoInfo.getBrandCod());
					}
					workCommand.setCarTyp(cargoInfo.getCarTyp());
					workCommand.setLength(cargoInfo.getLength());
					workCommand.setInCyTim(cargoInfo.getInCyTim());
					workCommand.setShipWorkTim(cargoInfo.getInCyTim());
					workCommand.setFinishedId("0");
					workCommand.setLengthOverId(cargoInfo.getLengthOverId());
					workCommand.setUseMachId(cargoInfo.getUseMachId());
					workCommand.setUseWorkerId(cargoInfo.getUseWorkerId());
					workCommand.setNightId(cargoInfo.getNightId());
					workCommand.setHolidayId(cargoInfo.getHolidayId());
					workCommand.setQueueId(HdUtils.genUuid());
					workCommand.setInCyTim(cargoInfo.getInCyTim());
					workCommand.setInCyNam(cargoInfo.getInCyNam());
					workCommand.setShipNo(cargoInfo.getShipNo());
					workCommand.setBillNo(cargoInfo.getBillNo());
					workCommand.setCyPlac(portCar.getCyPlac());
					workCommand.setCarKind(cargoInfo.getCarKind());
					workCommand.setRemarks(cargoInfo.getRemarks());
					if (HdUtils.strNotNull(cargoInfo.getCarLevel())) {
						workCommand.setCarLevel(cargoInfo.getCarLevel());
					}
					JpaUtils.save(workCommand);
				}
			}
		}
		return HdUtils.genMsg();
	}

	@Transactional
	@Override
	public void ckWanChuan(String shipNo) {
		String jpql = "select a from PlanRange a where a.planGroupNo in (select b.planGroupNo from PlanGroup b where b.planTyp='SO' and b.shipNo=:shipNo ) ";
		QueryParamLs paramLs = new QueryParamLs();
		paramLs.addParam("shipNo", shipNo);
		List<PlanRange> planRangeList = JpaUtils.findAll(jpql, paramLs);
		if (planRangeList.size() > 0) {
			for (PlanRange p : planRangeList) {
				JpaUtils.remove(p);
			}
		}

		String jpql2 = "select a from PlanPlacVin a where a.planGroupNo in (select b.planGroupNo from PlanGroup b where b.planTyp='SO' and b.shipNo=:shipNo ) ";
		QueryParamLs paramLs2 = new QueryParamLs();
		paramLs2.addParam("shipNo", shipNo);
		List<PlanPlacVin> planPlacVinList = JpaUtils.findAll(jpql2, paramLs2);
		if (planPlacVinList.size() > 0) {
			for (PlanPlacVin p : planPlacVinList) {
				JpaUtils.remove(p);
			}
		}

		String jpql3 = "select a from PlanGroup a where a.planTyp='SO' and a.shipNo=:shipNo  ";
		QueryParamLs paramLs3 = new QueryParamLs();
		paramLs3.addParam("shipNo", shipNo);
		List<PlanGroup> planGroupList = JpaUtils.findAll(jpql3, paramLs3);
		if (planGroupList.size() > 0) {
			for (PlanGroup p : planGroupList) {
				JpaUtils.remove(p);
			}
		}
		// 根据作业数据生成舱单
		ShipBill shipBill = new ShipBill();
		String shipbillId = HdUtils.genUuid();
		shipBill.setShipbillId(shipbillId);
		shipBill.setShipNo(shipNo);
		String jpql4 = "select a from PortCar a where a.portCarNo in (select b.portCarNo from WorkCommand  b where b.workTyp='SO' and b.shipNo=:shipNo ) ";
		QueryParamLs paramLs4 = new QueryParamLs();
		paramLs4.addParam("shipNo", shipNo);
		List<PortCar> portCarList = JpaUtils.findAll(jpql4, paramLs4);
		if (portCarList.size() > 0) {
			for (PortCar wc : portCarList) {
				shipBill.setBillNo(wc.getBillNo());
				shipBill.setShipNo(wc.getShipNo());
				shipBill.setiEId("E");
				shipBill.setTradeId(wc.getTradeId());
				shipBill.setBrandCod(wc.getBrandCod());
				shipBill.setCarTyp(wc.getCarTyp());
				shipBill.setMarks(wc.getMarks());
				shipBill.setPieces(null);
				shipBill.setWeights(null);
				shipBill.setValumes(null);
				shipBill.setCarNum(null);
				shipBill.setFittings(null);
				shipBill.setLoadPortCod(null);
				shipBill.setDiscPortCod(null);
				shipBill.setConsignCod(null);
				shipBill.setConsignNam(null);
				shipBill.setCustomBillNo(null);
				shipBill.setReceiveCod(null);
				shipBill.setReceiveNam(null);
				shipBill.setContactPerson(null);
				shipBill.setContactPhone(null);
				shipBill.setSplitBillNo(null);
				shipBill.setRemarks(null);
				shipBill.setRecNam(HdUtils.getCurUser().getAccount());
				shipBill.setRecTim(HdUtils.getDateTime());
			}
		}
		// 删除捣场计划
		String jpql5 = "select a from MoveCarPlan a where a.portCarNo in (select b.portCarNo from WorkCommand  b where b.workTyp='SO' and b.shipNo=:shipNo ) ";
		QueryParamLs paramLs5 = new QueryParamLs();
		paramLs5.addParam("shipNo", shipNo);
		List<MoveCarPlan> moveCarPlanList = JpaUtils.findAll(jpql5, paramLs5);
		if (moveCarPlanList.size() > 0) {
			for (MoveCarPlan p : moveCarPlanList) {
				JpaUtils.remove(p);
			}
		}
		// 删除作业指令信息
		String jpql6 = "select a from WorkCommand a where a.workTyp='SO' and a.shipNo=:shipNo ";
		QueryParamLs paramLs6 = new QueryParamLs();
		paramLs6.addParam("shipNo", shipNo);
		List<MPdaWorkCommand> workCommandList = JpaUtils.findAll(jpql6, paramLs6);
		ArrayList<WorkCommandBak> workCommandBakList = new ArrayList<WorkCommandBak>();
		if (workCommandList.size() > 0) {
			for (int i = 0; i < workCommandList.size(); i++) {
				WorkCommandBak wcb = new WorkCommandBak();
				wcb.setQueueId(workCommandList.get(i).getQueueId());
				wcb.setWorkQueueNo(workCommandList.get(i).getWorkQueueNo());
				wcb.setPortCarNo(workCommandList.get(i).getPortCarNo());
				wcb.setRfidCardNo(workCommandList.get(i).getRfidCardNo());
				wcb.setVinNo(workCommandList.get(i).getVinNo());
				wcb.setShipNo(workCommandList.get(i).getShipNo());
				wcb.setBillNo(workCommandList.get(i).getBillNo());
				wcb.setContractNo(workCommandList.get(i).getContractNo());
				wcb.setWorkTyp(workCommandList.get(i).getWorkTyp());
				wcb.setBrandCod(workCommandList.get(i).getBrandCod());
				wcb.setCarTyp(workCommandList.get(i).getCarTyp());
				wcb.setCarKind(workCommandList.get(i).getCarKind());
				wcb.setLength(workCommandList.get(i).getLength());
				wcb.setWidth(workCommandList.get(i).getWidth());
				wcb.setTruckNo(workCommandList.get(i).getTruckNo());
				wcb.setDirectId(workCommandList.get(i).getDirectId());
				wcb.setOldPlac(workCommandList.get(i).getOldPlac());
				wcb.setPlanPlac(workCommandList.get(i).getPlanPlac());
				wcb.setCyPlac(workCommandList.get(i).getCyPlac());
				wcb.setSendTim(workCommandList.get(i).getSendTim());
				wcb.setSendNam(workCommandList.get(i).getSendNam());
				wcb.setShipWorkTim(workCommandList.get(i).getShipWorkTim());
				wcb.setShipWorkNam(workCommandList.get(i).getShipWorkNam());
				wcb.setInCyNam(workCommandList.get(i).getInCyNam());
				wcb.setInCyTim(workCommandList.get(i).getInCyTim());
				wcb.setOutCyNam(workCommandList.get(i).getOutCyNam());
				wcb.setOutCyTim(workCommandList.get(i).getOutCyTim());
				wcb.setFinishedId(workCommandList.get(i).getFinishedId());
				wcb.setNightId(workCommandList.get(i).getNightId());
				wcb.setHolidayId(workCommandList.get(i).getHolidayId());
				wcb.setUseMachId(workCommandList.get(i).getUseMachId());
				wcb.setUseWorkerId(workCommandList.get(i).getUseWorkerId());
				wcb.setDriver(workCommandList.get(i).getDriver());
				wcb.setLengthOverId(workCommandList.get(i).getLengthOverId());
				wcb.setRemarks(workCommandList.get(i).getRemarks());
				wcb.setWidthOverId(workCommandList.get(i).getWidthOverId());
				workCommandBakList.add(wcb);
				JpaUtils.remove(workCommandList.get(i));
			}
		}
		// 删除装船的车辆信息
		String jpql7 = "select a from PortCar a where a.iEId='E' and a.currentStat='5' and  a.shipNo=:shipNo ";
		QueryParamLs paramLs7 = new QueryParamLs();
		paramLs7.addParam("shipNo", shipNo);
		List<PortCar> portcarList = JpaUtils.findAll(jpql7, paramLs7);
		ArrayList<PortCarBak> portCarBakList = new ArrayList<PortCarBak>();
		if (portcarList.size() > 0) {
			for (int i = 0; i < portcarList.size(); i++) {
				PortCarBak pcb = new PortCarBak();
				pcb.setPortCarNo(portcarList.get(i).getPortCarNo());
				pcb.setRfidCardNo(portcarList.get(i).getRfidCardNo());
				pcb.setVinNo(portcarList.get(i).getVinNo());
				pcb.setCurrentStat(portcarList.get(i).getCurrentStat());
				pcb.setShipNo(portcarList.get(i).getShipNo());
				pcb.setBillNo(portcarList.get(i).getBillNo());
				pcb.setiEId(portcarList.get(i).getiEId());
				pcb.setTradeId(portcarList.get(i).getTradeId());
				pcb.setConsignCod(portcarList.get(i).getConsignCod());
				pcb.setConsignNam(portcarList.get(i).getConsignNam());
				pcb.setReceiveCod(portcarList.get(i).getReceiveCod());
				pcb.setReceiveNam(portcarList.get(i).getReceiveNam());
				pcb.setCarTyp(portcarList.get(i).getCarTyp());
				pcb.setBrandCod(portcarList.get(i).getBrandCod());
				pcb.setCarKind(portcarList.get(i).getCarKind());
				pcb.setMarks(portcarList.get(i).getMarks());
				pcb.setColorCod(portcarList.get(i).getColorCod());
				pcb.setWeights(portcarList.get(i).getWeights());
				pcb.setVolumes(portcarList.get(i).getVolumes());
				pcb.setLength(portcarList.get(i).getLength());
				pcb.setWidth(portcarList.get(i).getWidth());
				pcb.setHeight(portcarList.get(i).getHeight());
				pcb.setLengthOverId(portcarList.get(i).getLengthOverId());
				pcb.setWidthOverId(portcarList.get(i).getWidthOverId());
				pcb.setCyPlac(portcarList.get(i).getCyPlac());
				pcb.setCyAreaNo(portcarList.get(i).getCyAreaNo());
				pcb.setCyRowNo(portcarList.get(i).getCyRowNo());
				pcb.setCyBayNo(portcarList.get(i).getCyBayNo());
				pcb.setExitCustomId(portcarList.get(i).getExitCustomId());
				pcb.setSpecId(portcarList.get(i).getSpecId());
				pcb.setCustomId(portcarList.get(i).getCustomId());
				pcb.setInspectionId(portcarList.get(i).getInspectionId());
				pcb.setInspOkId(portcarList.get(i).getInspOkId());
				pcb.setLockId(portcarList.get(i).getLockId());
				pcb.setDamId(portcarList.get(i).getDamId());
				pcb.setDamCod(portcarList.get(i).getDamCod());
				pcb.setDamLevel(portcarList.get(i).getDamLevel());
				pcb.setDamArea(portcarList.get(i).getDamArea());
				pcb.setLoadPortCod(portcarList.get(i).getLoadPortCod());
				pcb.setTranPortCod(portcarList.get(i).getTranPortCod());
				pcb.setDiscPortCod(portcarList.get(i).getDiscPortCod());
				pcb.setContractNo(portcarList.get(i).getContractNo());
				pcb.setInToolNo(portcarList.get(i).getInToolNo());
				pcb.setInPortNo(portcarList.get(i).getInPortNo());
				pcb.setToPortTim(portcarList.get(i).getToPortTim());
				pcb.setDiscShipTim(portcarList.get(i).getDiscShipTim());
				pcb.setInCyTim(portcarList.get(i).getInCyTim());
				pcb.setOutToolNo(portcarList.get(i).getOutToolNo());
				pcb.setOutPortNo(portcarList.get(i).getOutPortNo());
				pcb.setOutCyTim(portcarList.get(i).getOutCyTim());
				pcb.setLoadShipTim(portcarList.get(i).getLoadShipTim());
				pcb.setCustomBillNo(portcarList.get(i).getCustomBillNo());
				pcb.setLeavPortTim(portcarList.get(i).getLeavPortTim());
				pcb.setContractNo(portcarList.get(i).getContractNo());
				pcb.setTransId(portcarList.get(i).getTransId());
				pcb.setDockCod(portcarList.get(i).getDockCod());
				pcb.setRemarks(portcarList.get(i).getRemarks());
				pcb.setRecNam(HdUtils.getCurUser().getAccount());
				pcb.setRecTim(HdUtils.getDateTime());
				pcb.setUpdNam(null);
				pcb.setUpdTim(null);
				portCarBakList.add(pcb);
				JpaUtils.remove(portcarList.get(i));
			}
		}
		Ship ship = JpaUtils.findById(Ship.class, shipNo);
		ship.setLoadEndTim(HdUtils.getDateTime());
		JpaUtils.update(ship);
	}

	@Transactional
	@Override
	public void jkWanChuan(String shipNo) {
		String jpql = "select a from PlanRange a where a.planGroupNo in (select b.planGroupNo from PlanGroup b where b.planTyp='SI' and b.shipNo=:shipNo ) ";
		QueryParamLs paramLs = new QueryParamLs();
		paramLs.addParam("shipNo", shipNo);
		List<PlanRange> planRangeList = JpaUtils.findAll(jpql, paramLs);
		if (planRangeList.size() > 0) {
			for (PlanRange p : planRangeList) {
				JpaUtils.remove(p);
			}
		}

		String jpql2 = "select a from PlanPlacVin a where a.planGroupNo in (select b.planGroupNo from PlanGroup b where b.planTyp='SI' and b.shipNo=:shipNo ) ";
		QueryParamLs paramLs2 = new QueryParamLs();
		paramLs2.addParam("shipNo", shipNo);
		List<PlanPlacVin> planPlacVinList = JpaUtils.findAll(jpql2, paramLs2);
		if (planPlacVinList.size() > 0) {
			for (PlanPlacVin p : planPlacVinList) {
				JpaUtils.remove(p);
			}
		}

		String jpql3 = "select a from PlanGroup a where a.planTyp='SI' and a.shipNo=:shipNo ";
		QueryParamLs paramLs3 = new QueryParamLs();
		paramLs3.addParam("shipNo", shipNo);
		List<PlanGroup> planGroupList = JpaUtils.findAll(jpql3, paramLs3);
		if (planGroupList.size() > 0) {
			for (PlanGroup p : planGroupList) {
				JpaUtils.remove(p);
			}
		}
		Timestamp discEndTim;
		String jpql4 = "select a from WorkCommand a where a.workTyp='SI' and a.shipNo=:shipNo ";
		QueryParamLs paramLs4 = new QueryParamLs();
		paramLs4.addParam("shipNo", shipNo);
		List<MPdaWorkCommand> workCommandList = JpaUtils.findAll(jpql4, paramLs4);
		ArrayList<WorkCommandBak> workCommandBakList = new ArrayList<WorkCommandBak>();
		if (workCommandList.size() > 0) {
			for (int i = 0; i < workCommandList.size(); i++) {
				WorkCommandBak wcb = new WorkCommandBak();
				wcb.setQueueId(workCommandList.get(i).getQueueId());
				wcb.setWorkQueueNo(workCommandList.get(i).getWorkQueueNo());
				wcb.setPortCarNo(workCommandList.get(i).getPortCarNo());
				wcb.setRfidCardNo(workCommandList.get(i).getRfidCardNo());
				wcb.setVinNo(workCommandList.get(i).getVinNo());
				wcb.setShipNo(workCommandList.get(i).getShipNo());
				wcb.setBillNo(workCommandList.get(i).getBillNo());
				wcb.setContractNo(workCommandList.get(i).getContractNo());
				wcb.setWorkTyp(workCommandList.get(i).getWorkTyp());
				wcb.setBrandCod(workCommandList.get(i).getBrandCod());
				wcb.setCarTyp(workCommandList.get(i).getCarTyp());
				wcb.setCarKind(workCommandList.get(i).getCarKind());
				wcb.setLength(workCommandList.get(i).getLength());
				wcb.setWidth(workCommandList.get(i).getWidth());
				wcb.setTruckNo(workCommandList.get(i).getTruckNo());
				wcb.setDirectId(workCommandList.get(i).getDirectId());
				wcb.setOldPlac(workCommandList.get(i).getOldPlac());
				wcb.setPlanPlac(workCommandList.get(i).getPlanPlac());
				wcb.setCyPlac(workCommandList.get(i).getCyPlac());
				wcb.setSendTim(workCommandList.get(i).getSendTim());
				wcb.setSendNam(workCommandList.get(i).getSendNam());
				wcb.setShipWorkTim(workCommandList.get(i).getShipWorkTim());
				wcb.setShipWorkNam(workCommandList.get(i).getShipWorkNam());
				wcb.setInCyNam(workCommandList.get(i).getInCyNam());
				wcb.setInCyTim(workCommandList.get(i).getInCyTim());
				wcb.setOutCyNam(workCommandList.get(i).getOutCyNam());
				wcb.setOutCyTim(workCommandList.get(i).getOutCyTim());
				wcb.setFinishedId(workCommandList.get(i).getFinishedId());
				wcb.setNightId(workCommandList.get(i).getNightId());
				wcb.setHolidayId(workCommandList.get(i).getHolidayId());
				wcb.setUseMachId(workCommandList.get(i).getUseMachId());
				wcb.setUseWorkerId(workCommandList.get(i).getUseWorkerId());
				wcb.setDriver(workCommandList.get(i).getDriver());
				wcb.setLengthOverId(workCommandList.get(i).getLengthOverId());
				wcb.setRemarks(workCommandList.get(i).getRemarks());
				wcb.setWidthOverId(workCommandList.get(i).getWidthOverId());
				workCommandBakList.add(wcb);
				JpaUtils.remove(workCommandList.get(i));
				// 记录末卸船时间
				if (i == workCommandList.size() - 1) {
					discEndTim = workCommandList.get(i).getInCyTim();
					// 修改卸船完工时间
					String jpql6 = " update Ship a set a.discEndTim=:discEndTim where a.shipNo=:shipNo ";
					QueryParamLs paramLs6 = new QueryParamLs();
					paramLs6.addParam("shipNo", shipNo);
					paramLs6.addParam("discEndTim", discEndTim);
					JpaUtils.execUpdate(jpql6, paramLs6);
				}
			}
		}
		String jpql5 = "select a from WorkQueue a where a.workTyp='SI' and a.shipNo=:shipNo ";
		QueryParamLs paramLs5 = new QueryParamLs();
		paramLs5.addParam("shipNo", shipNo);
		List<WorkQueue> workQueueList = JpaUtils.findAll(jpql5, paramLs5);
		if (workQueueList.size() > 0) {
			for (int i = 0; i < workQueueList.size(); i++) {
				JpaUtils.remove(workQueueList.get(i));
			}
		}
		Ship ship = JpaUtils.findById(Ship.class, shipNo);
		ship.setDiscEndTim(HdUtils.getDateTime());
		JpaUtils.update(ship);
	}

	@Override
	public Result chooseShipNo(HdEzuiQueryParams hdEzuiQueryParams) {
		// TODO Auto-generated method stub
		String jpql = "select a from WorkCommand a where a.workTyp = 'SI' and a.shipNo=:shipNo";
		QueryParamLs paramLs = new QueryParamLs();
		paramLs.addParam("shipNo", hdEzuiQueryParams.getOthers().get("shipNo"));
		PageInfo pageInfo = new PageInfo();
		pageInfo.setCurPageNum(1);
		pageInfo.setRowOfPage(20);
		List mList = JpaUtils.findAll(jpql, paramLs, pageInfo);
		Result result = new Result();
		result.setData(mList);
		return result;
	}

	@Override
	public MPdaWorkCommand getShipNoInfo(String shipNo) {
		String jpql = "select a from WorkCommand a where 1=1 and a.workTyp = 'SI' and a.shipNo=:shipNo ";
		QueryParamLs paramLs = new QueryParamLs();
		paramLs.addParam("shipNo", shipNo);
		List<MPdaWorkCommand> shipNoList = JpaUtils.findAll(jpql, paramLs);
		MPdaWorkCommand s = new MPdaWorkCommand();
		if (shipNoList.size() > 0) {
			for (MPdaWorkCommand workCommand : shipNoList) {
				s.setShipNo(workCommand.getShipNo());
				s.setBillNo(workCommand.getBillNo());
				s.setDriver(workCommand.getDriver());
				s.setPlanPlac(workCommand.getPlanPlac());
			}
		}
		return s;
	}

	@Override
	public HdEzuiDatagridData findShipingReport(HdQuery hdQuery) {
		String jpql = "select a.brandCod,a.inCyTim,count(a.carTyp),a.carTyp from WorkCommand a where a.workTyp='SO' ";
		// String brandCod=hdQuery.getStr("brandCod");
		String shipNo = hdQuery.getStr("shipNo");
		QueryParamLs paramLs = new QueryParamLs();
		// if(HdUtils.strNotNull(brandCod)){
		// jpql +=" and a.brandCod=:brandCod ";
		// paramLs.addParam("brandCod",brandCod);
		// }
		if (HdUtils.strNotNull(shipNo)) {
			jpql += "  and a.shipNo=:shipNo ";
			paramLs.addParam("shipNo", shipNo);
		}
		jpql += " group by a.carTyp,a.brandCod,a.inCyTim ";
		List<Object[]> objList = JpaUtils.findAll(jpql, paramLs);
		List<MPdaWorkCommand> wcList = new ArrayList();
		for (Object[] obj : objList) {
			String jqpl1 = "select a from WorkCommand a where 1=1 and a.workTyp='SO'";
			QueryParamLs paramLs1 = new QueryParamLs();
			if (HdUtils.strNotNull(shipNo)) {
				jqpl1 += "  and a.shipNo=:shipNo ";
				paramLs1.addParam("shipNo", shipNo);
			}
			if (obj[0] != null) {
				jqpl1 += " and a.brandCod =:brandCod";
				paramLs1.addParam("brandCod", obj[0]);
			}
			if (obj[1] != null) {
				jqpl1 += " and a.inCyTim =:inCyTim";
				paramLs1.addParam("inCyTim", obj[1]);
			}
			if (obj[3] != null) {
				jqpl1 += " and a.carTyp =:carTyp";
				paramLs1.addParam("carTyp", obj[3]);
			}
			List<MPdaWorkCommand> workCommandList = JpaUtils.findAll(jqpl1, paramLs1);
			if (workCommandList.size() > 0) {
				MPdaWorkCommand wc = workCommandList.get(0);
				if (HdUtils.strNotNull(wc.getBrandCod())) {
					CBrand cb = JpaUtils.findById(CBrand.class, wc.getBrandCod());
					wc.setBrandNam(cb.getBrandNam());
				}
				if (HdUtils.strNotNull(wc.getCarTyp())) {
					CCarTyp cct = JpaUtils.findById(CCarTyp.class, wc.getCarTyp());
					wc.setCarTypNam(cct.getCarTypNam());
				}
				wc.setCksl(String.valueOf(obj[2]));
				wcList.add(wc);
			}
		}
		HdEzuiDatagridData results = new HdEzuiDatagridData();
		results.setRows(wcList);
		return results;
	}

}
