package net.huadong.tech.shipbill.service.impl;

import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.bean.HdRunTimeException;
import net.huadong.tech.base.entity.CBerth;
import net.huadong.tech.base.entity.CBrand;
import net.huadong.tech.base.entity.CCarKind;
import net.huadong.tech.base.entity.CCarTyp;
import net.huadong.tech.base.entity.SParam;
import net.huadong.tech.shipbill.entity.BillCar;
import net.huadong.tech.shipbill.entity.PortCar;
import net.huadong.tech.shipbill.entity.ShipBill;
import net.huadong.tech.shipbill.service.BillCarService;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.tech.ship.entity.Ship;
import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.EzTreeBean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;
import net.huadong.tech.util.HdUtils;
import net.huadong.tech.work.entity.WorkCommand;
//import net.huadong.tech.util.CommonUtil;

/**
 * @author
 */
@Component
public class BillCarServiceImpl implements BillCarService {
	public HdEzuiDatagridData findShipVoyage(HdQuery hdQuery) {
		String jpql = "select a from Ship a where 1=1 ";
		String shipNam = hdQuery.getStr("shipNam");
		String Voyage = hdQuery.getStr("Voyage");
		String portId = hdQuery.getStr("portId");
		QueryParamLs paramLs = new QueryParamLs();
		if (HdUtils.strNotNull(shipNam)) {
			jpql += "and a.shipNam =:shipNam ";
			paramLs.addParam("shipNam", shipNam);
		}
		if (HdUtils.strNotNull(portId) && portId == "I") {
			jpql += "and a.ivoyage =:Voyage ";
			paramLs.addParam("ivoyage", Voyage);
		}
		if (HdUtils.strNotNull(portId) && portId == "E") {
			jpql += "and a.evoyage =:Voyage ";
			paramLs.addParam("evoyage", Voyage);
		}

		jpql += " order by a.recTim desc";
		return JpaUtils.findByEz(jpql, paramLs, hdQuery);
	}

	public HdEzuiDatagridData findShipBillCar(HdQuery hdQuery) {
		String jpql = "select a from BillCar a where 1=1 ";
		String billNo = hdQuery.getStr("billNo");
		QueryParamLs paramLs = new QueryParamLs();
		if (HdUtils.strNotNull(billNo)) {
			jpql += "and a.billNo =:billNo ";
			paramLs.addParam("billNo", billNo);
		}
		jpql += " order by a.recTim desc";
		HdEzuiDatagridData results = JpaUtils.findByEz(jpql, paramLs, hdQuery);
		List<BillCar> billCarList = results.getRows();
		if (billCarList.size() > 0) {
			for (BillCar bc : billCarList) {
				CCarTyp ccartyp = JpaUtils.findById(CCarTyp.class, bc.getCarTyp());
				CCarKind carkind = JpaUtils.findById(CCarKind.class, bc.getCarKind());
				CBrand cbrand = JpaUtils.findById(CBrand.class, bc.getBrandCod());
				bc.setCarKindNam(ccartyp.getCarKindNam());
				bc.setCarTypNam(carkind.getCarKindNam());
				bc.setBrandNam(cbrand.getBrandNam());
			}
		}
		return results;
	}

	public HdEzuiDatagridData findXclh(HdQuery hdQuery) {
		String jpql = "select a from BillCar a where 1=1 and a.lhFlag = '0' ";
		String type = hdQuery.getStr("type");
		String shipNo = hdQuery.getStr("shipNo");
		String billNo = hdQuery.getStr("billNo");
		String carTyp = hdQuery.getStr("carTyp");

		QueryParamLs paramLs = new QueryParamLs();
		if ("XC".equals(type) || "PLXC".equals(type)) {
			jpql += "and a.iEId =:iEId ";
			paramLs.addParam("iEId", "I");
		} else if ("ZC".equals(type) || "PLZC".equals(type)) {
			jpql += "and a.iEId =:iEId ";
			paramLs.addParam("iEId", "E");
		}
		if (HdUtils.strNotNull(shipNo)) {
			jpql += "and a.shipNo =:shipNo ";
			paramLs.addParam("shipNo", shipNo);
		}else{
			jpql += "and a.shipNo=:shipNo ";
			paramLs.addParam("shipNo", "123456789##");
		}
		if (HdUtils.strNotNull(billNo)) {
			jpql += "and a.billNo =:billNo ";
			paramLs.addParam("billNo", billNo);
		}
		if (HdUtils.strNotNull(carTyp)) {
			jpql += "and a.carTyp =:carTyp ";
			paramLs.addParam("carTyp", carTyp);
		}
		jpql += " order by a.recTim desc";
		HdEzuiDatagridData result = JpaUtils.findByEz(jpql, paramLs, hdQuery);
		List<BillCar> billCarList = result.getRows();
		List<BillCar> billCarList1 = new ArrayList();
		if (HdUtils.strNotNull(shipNo)) {
			String jpql1 = "select a from WorkCommand a where a.shipNo ='" + shipNo + "' and a.workTyp = 'SI' and "
					+ "a.portCarNo =:portCarNo and a.inCyTim is not null";
			QueryParamLs paramLs1 = new QueryParamLs();
			for (BillCar billCar : billCarList) {
				paramLs1.addParam("portCarNo", billCar.getPortCarNo());
				List<WorkCommand> workCommandList = JpaUtils.findAll(jpql1, paramLs1);
				if (workCommandList.size() > 0) {
					billCarList1.add(billCar);
				}
			}
		}
		for (BillCar billCar : billCarList1) {
			billCarList.remove(billCar);
		}
		for (BillCar bc : billCarList) {
			if (HdUtils.strNotNull(bc.getCarTyp())) {
				CCarTyp ccartyp = JpaUtils.findById(CCarTyp.class, bc.getCarTyp());
				bc.setCarTypNam(ccartyp.getCarTypNam());
			}
			if (HdUtils.strNotNull(bc.getCarKind())) {
				CCarKind carkind = JpaUtils.findById(CCarKind.class, bc.getCarKind());
				bc.setCarKindNam(carkind.getCarKindNam());
			}
			if (HdUtils.strNotNull(bc.getBrandCod())) {
				CBrand cbrand = JpaUtils.findById(CBrand.class, bc.getBrandCod());
				if (cbrand != null)
					bc.setBrandNam(cbrand.getBrandNam());
			}
		}
		return result;
	}

	public HdEzuiDatagridData find(HdQuery hdQuery) {
		String jpql = "select a from BillCar a where 1=1 ";
		// String shipNo = hdQuery.getStr("shipNo");
		// String Voyage = hdQuery.getStr("Voyage");
		// String portId = hdQuery.getStr("portId");
		String billNo = hdQuery.getStr("billNo");
		QueryParamLs paramLs = new QueryParamLs();
		/*
		 * if (HdUtils.strNotNull(shipNo)) { jpql += "and a.shipNo =:shipNo ";
		 * paramLs.addParam("shipNo", shipNo); }
		 */
		if (HdUtils.strNotNull(billNo)) {
			jpql += "and a.billNo =:billNo ";
			paramLs.addParam("billNo", billNo);
		}
		/*
		 * if (HdUtils.strNotNull(portId) && portId == "I") { jpql +=
		 * "and a.iEId =:portId "; paramLs.addParam("iEId", portId); } if
		 * (HdUtils.strNotNull(portId) && portId == "E") { jpql +=
		 * "and a.iEId =:portId "; paramLs.addParam("iEId", portId); }
		 */
		jpql += " order by a.billNo desc";
		HdEzuiDatagridData results = JpaUtils.findByEz(jpql, paramLs, hdQuery);
		List<BillCar> billCarList = results.getRows();
		if (billCarList.size() > 0) {
			for (BillCar bc : billCarList) {

				if (HdUtils.strNotNull(bc.getCarKind())) {
					CCarKind carkind = JpaUtils.findById(CCarKind.class, bc.getCarKind());
					if(carkind!=null)
					bc.setCarKindNam(carkind.getCarKindNam());
				}
				if (HdUtils.strNotNull(bc.getCarTyp())) {
					CCarTyp ccartyp = JpaUtils.findById(CCarTyp.class, bc.getCarTyp());
					if(ccartyp!=null)
					bc.setCarTypNam(ccartyp.getCarTypNam());
				}
				if (HdUtils.strNotNull(bc.getBrandCod())) {
					CBrand cbrand = JpaUtils.findById(CBrand.class, bc.getBrandCod());
					if(cbrand!=null)
					bc.setBrandNam(cbrand.getBrandNam());
				}
			}
		}
		return results;
	}

	@Override
	public HdMessageCode save(HdEzuiSaveDatagridData<BillCar> hdEzuiSaveDatagridData) {
		// TODO Auto-generated method stub
		if (hdEzuiSaveDatagridData.getInsertedRows().size() != 0) {
			PortCar pc = new PortCar();
			pc.setBillNo(hdEzuiSaveDatagridData.getInsertedRows().get(0).getBillNo());
			pc.setRfidCardNo(hdEzuiSaveDatagridData.getInsertedRows().get(0).getRfidCardNo());
			pc.setPortCarNo(hdEzuiSaveDatagridData.getInsertedRows().get(0).getPortCarNo());
			pc.setVinNo(hdEzuiSaveDatagridData.getInsertedRows().get(0).getVinNo());
			pc.setCurrentStat(PortCar.CURRENT_STAT_2);
			pc.setiEId(hdEzuiSaveDatagridData.getInsertedRows().get(0).getiEId());
			pc.setTradeId(hdEzuiSaveDatagridData.getInsertedRows().get(0).getTradeId());
			String jpql = "select a from ShipBill a where a.billNo=:billNo ";
			QueryParamLs paramLs1 = new QueryParamLs();
			paramLs1.addParam("billNo", hdEzuiSaveDatagridData.getInsertedRows().get(0).getBillNo());
			List<ShipBill> shipBillList = JpaUtils.findAll(jpql, paramLs1);
			for (ShipBill shipBill : shipBillList) {
				Ship ship = JpaUtils.findById(Ship.class, shipBill.getShipNo());
				pc.setDockCod(ship.getDockCod());
				pc.setInPortNo(" ");
				pc.setRecNam(hdEzuiSaveDatagridData.getInsertedRows().get(0).getRecNam());

				pc.setRecTim(hdEzuiSaveDatagridData.getInsertedRows().get(0).getRecTim());
			}
			PortCar portCar = JpaUtils.findById(PortCar.class,
					hdEzuiSaveDatagridData.getInsertedRows().get(0).getPortCarNo());
			if (portCar == null) {
				JpaUtils.save(pc);
			} else {
				throw new HdRunTimeException("流水号已存在，请重新输入！");// 主键已存在
			}
		}
		return JpaUtils.save(hdEzuiSaveDatagridData);
	}

	// 增加billcar同时向portcar插入一条相同流水号的空数据
	@Override
	public HdMessageCode saveone(@RequestBody BillCar billCar) {
		String uuid = UUID.randomUUID().toString();
		BillCar bc = JpaUtils.findById(BillCar.class, uuid);
		if (bc != null) {
			throw new HdRunTimeException("该代码已存在，请重新输入！");// 主键已存在
		} else {
			billCar.setBillcarId(uuid);
			JpaUtils.save(billCar);
			PortCar portCar = JpaUtils.findById(PortCar.class, billCar.getPortCarNo());
			if (portCar == null) {
				PortCar pc = new PortCar();
				pc.setBillNo(billCar.getBillNo());
				String jpql2 = "select a from ShipBill a where a.billNo=:billNo ";
				QueryParamLs paramLs2 = new QueryParamLs();
				paramLs2.addParam("billNo", billCar.getBillNo());
				List<ShipBill> shipbillList = JpaUtils.findAll(jpql2, paramLs2);
				if (shipbillList.size() > 0) {
					for (ShipBill s : shipbillList) {
						pc.setDiscPortCod(s.getDiscPortCod());
						pc.setTranPortCod(s.getTranPortCod());
						pc.setLoadPortCod(s.getLoadPortCod());
						pc.setPortCarNo(billCar.getPortCarNo());
						pc.setConsignCod(s.getConsignCod());
						pc.setConsignNam(s.getConsignNam());
						pc.setReceiveCod(s.getReceiveCod());
						pc.setReceiveNam(s.getReceiveNam());
						pc.setCarTyp(s.getCarTyp());
						pc.setBrandCod(s.getBrandCod());
						pc.setCarKind(billCar.getCarKind());
						pc.setMarks(s.getMarks());
						pc.setColorCod(billCar.getColorCod());
						pc.setWeights(billCar.getWeights());
						pc.setVolumes(billCar.getVolumes());
						pc.setWidth(billCar.getWidth());
						pc.setLength(billCar.getLength());
						pc.setHeight(billCar.getHeight());
						pc.setCustomBillNo(s.getCustomBillNo());
						pc.setVinNo(billCar.getVinNo());
						pc.setCurrentStat(PortCar.CURRENT_STAT_2);
						pc.setiEId(billCar.getiEId());
						pc.setTradeId(billCar.getTradeId());
						pc.setShipNo(billCar.getShipNo());
						String jpql = "select a from ShipBill a where a.billNo=:billNo ";
						QueryParamLs paramLs1 = new QueryParamLs();
						paramLs1.addParam("billNo", billCar.getBillNo());
						List<ShipBill> shipBillList = JpaUtils.findAll(jpql, paramLs1);
						for (ShipBill shipBill : shipBillList) {
							Ship ship = JpaUtils.findById(Ship.class, shipBill.getShipNo());
							pc.setDockCod(ship.getDockCod());
							pc.setInPortNo(" ");
							pc.setRecNam(billCar.getRecNam());
							pc.setRecTim(billCar.getRecTim());
						}
						JpaUtils.save(pc);
					}
				}

			} else {
				throw new HdRunTimeException("该流水号车辆已在港，不能添加！");
			}
		}
		return HdUtils.genMsg();
	}

	@Transactional
	public void removeAll(String billcarId) {
		List<String> BillCarList = HdUtils.paraseStrs(billcarId);

		for (String billcarid : BillCarList) {
			//删除明细，去掉初始化的在场车验证
			/*if (checkExist(billcarid)) {
				throw new HdRunTimeException("在港车辆不能删除！");
			}*/
			JpaUtils.remove(BillCar.class, billcarid);
		}
	}

	public boolean checkExist(String billcarid) {
		BillCar bc = JpaUtils.findById(BillCar.class, billcarid);
		PortCar pc = JpaUtils.findById(PortCar.class, bc.getPortCarNo());
		if (pc != null) {
			return true;
		}
		return false;
	}

}
