package net.huadong.tech.cargo.service.impl;

import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.bean.HdRunTimeException;
import net.huadong.tech.base.entity.CBrand;
import net.huadong.tech.base.entity.CCarTyp;
import net.huadong.tech.base.entity.CPort;
import net.huadong.tech.cargo.entity.MChangeShip;
import net.huadong.tech.cargo.service.MChangeShipService;
import net.huadong.tech.gate.entity.GateTruck;
import net.huadong.tech.gate.entity.GateTruckContract;
import net.huadong.tech.his.entity.GateTruckContractHis;
import net.huadong.tech.his.entity.GateTruckHis;
import net.huadong.tech.his.entity.PortCarBak;
import net.huadong.tech.his.entity.TruckWorkHis;
import net.huadong.tech.ship.entity.Ship;
import net.huadong.tech.shipbill.entity.PortCar;
import net.huadong.tech.shipbill.entity.ShipBill;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.idev.hdmessagecode.HdMessageCode;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;
import net.huadong.tech.util.HdUtils;
import net.huadong.tech.work.entity.WorkCommand;
import net.huadong.tech.work.entity.WorkCommandBak;
import net.huadong.tech.work.entity.WorkQueue;

/**
 * @author
 */
@Component
public class MChangeShipServiceImpl implements MChangeShipService {
	@Override
	public HdEzuiDatagridData find(HdQuery hdQuery) {
		String jpql = "select a from MChangeShip a where 1=1 ";
		QueryParamLs paramLs = new QueryParamLs();
		String shipNo = hdQuery.getStr("shipNo");
		if (HdUtils.strNotNull(shipNo)) {
			jpql += "and a.newShipNo =:shipNo ";
			paramLs.addParam("shipNo", shipNo);
		}
		jpql += "order by a.recTim desc";
		HdEzuiDatagridData result = JpaUtils.findByEz(jpql, paramLs, hdQuery);
		List<MChangeShip> List = result.getRows();
		for (MChangeShip mChangeShip : List) {
			if (HdUtils.strNotNull(mChangeShip.getOldShipNo())) {
				Ship oldShip = JpaUtils.findById(Ship.class, mChangeShip.getOldShipNo());
				mChangeShip.setOldNamVoyage(oldShip.getcShipNam() + "/" + oldShip.getEvoyage());
			}
			if (HdUtils.strNotNull(mChangeShip.getNewShipNo())) {
				Ship newShip = JpaUtils.findById(Ship.class, mChangeShip.getNewShipNo());
				mChangeShip.setNewNamVoyage(newShip.getcShipNam() + "/" + newShip.getEvoyage());
			}
			if (HdUtils.strNotNull(mChangeShip.getNewDiscPortCod())) {
				String jpqlc = "select a from CPort a where a.portCod=:portCod";
				QueryParamLs paramLsc = new QueryParamLs();
				paramLsc.addParam("portCod", mChangeShip.getNewDiscPortCod());
				List<CPort> cportList = JpaUtils.findAll(jpqlc, paramLsc);
				if (cportList.size() > 0) {
					mChangeShip.setNewDiscPortNam(cportList.get(0).getcPortNam());
				}
			}
			if (HdUtils.strNotNull(mChangeShip.getNewTranPortCod())) {
				String jpqlc = "select a from CPort a where a.portCod=:portCod";
				QueryParamLs paramLsc = new QueryParamLs();
				paramLsc.addParam("portCod", mChangeShip.getNewDiscPortCod());
				List<CPort> cportList = JpaUtils.findAll(jpqlc, paramLsc);
				if (cportList.size() > 0) {
					mChangeShip.setNewTranPortNam(cportList.get(0).getcPortNam());
				}
			}
			if (HdUtils.strNotNull(mChangeShip.getBrandCod())) {
				CBrand cbrand = JpaUtils.findById(CBrand.class, mChangeShip.getBrandCod());
				mChangeShip.setBrandNam(cbrand.getBrandNam());
			}

		}
		return result;
	}

	@Override
	public HdMessageCode save(HdEzuiSaveDatagridData<MChangeShip> hdEzuiSaveDatagridData, String ingateId,
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
				List<MChangeShip> truckWorkList = JpaUtils.findAll(jpql, paramLs);
				for (MChangeShip truckWork : truckWorkList) {
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
				List<MChangeShip> truckWorkList1 = JpaUtils.findAll(jpql3, paramLs3);
				for (MChangeShip truckWork : truckWorkList1) {
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

	@Transactional
	public HdMessageCode saveAll(String portCarNos, String shipbillId, String newTranPortCod, String newDiscPortCod) {
		List<String> portCarNoList = HdUtils.paraseStrs(portCarNos);
		for (String portCarNo : portCarNoList) {
			BigDecimal id = new BigDecimal(portCarNo);
			PortCar portCar = JpaUtils.findById(PortCar.class, id);
			if (portCar != null) {
				MChangeShip mChangeShip = new MChangeShip();
				mChangeShip.setChangeid(HdUtils.genUuid());
				mChangeShip.setPortCarNo(portCar.getPortCarNo());
				// if(portCar.getRfidCardNo() != null){
				// mChangeShip.setRfidCardNo("1");
				// }
				mChangeShip.setRfidCardNo("1");
				mChangeShip.setVinNo(portCar.getVinNo());
				mChangeShip.setBrandCod(portCar.getBrandCod());
				mChangeShip.setOIEId(portCar.getiEId());
				mChangeShip.setnIEId("E");
				mChangeShip.setOldShipNo(portCar.getShipNo());
				ShipBill shipBill = JpaUtils.findById(ShipBill.class, shipbillId);
				if (shipBill != null) {
					mChangeShip.setNewShipNo(shipBill.getShipNo());
					mChangeShip.setNewBillNo(shipBill.getBillNo());
				}
				mChangeShip.setOldBillNo(portCar.getBillNo());
				mChangeShip.setOldDiscPortCod(portCar.getDiscPortCod());
				mChangeShip.setOldTranPortCod(portCar.getTranPortCod());
				if (HdUtils.strNotNull(newDiscPortCod)) {
					mChangeShip.setNewDiscPortCod(newDiscPortCod);
				} else {
					mChangeShip.setNewDiscPortCod(portCar.getDiscPortCod());
				}
				if (HdUtils.strNotNull(newTranPortCod)) {
					mChangeShip.setNewTranPortCod(newTranPortCod);
				} else {
					mChangeShip.setNewTranPortCod(portCar.getTranPortCod());
				}
				mChangeShip.setCyPlac(portCar.getCyAreaNo());
				mChangeShip.setRecNam(HdUtils.getCurUser().getAccount());
				mChangeShip.setRecTim(HdUtils.getDateTime());
				JpaUtils.save(mChangeShip);
				if (shipBill != null) {
					portCar.setShipNo(shipBill.getShipNo());
					portCar.setBillNo(shipBill.getBillNo());
				}
				portCar.setTranPortCod(newTranPortCod);
				portCar.setDiscPortCod(newDiscPortCod);
				JpaUtils.save(portCar);

			}
		}
		return HdUtils.genMsg();
	}

	@Override
	public MChangeShip findone(String id) {
		MChangeShip mChangeShip = JpaUtils.findById(MChangeShip.class, id);
		return mChangeShip;
	}

	@Override
	public HdMessageCode saveone(@RequestBody MChangeShip truckWork) {
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
		JpaUtils.save(portCar);

		WorkCommand workCommand = new WorkCommand();
		String jpql3 = "select a from WorkQueue a where a.workTyp = 'TI' and a.contractNo=:contractNo and a.truckNo=:truckNO ";
		QueryParamLs paramLs3 = new QueryParamLs();
		List<WorkQueue> workQueueList = JpaUtils.findAll(jpql3, paramLs3);
		for (WorkQueue workQueue : workQueueList) {
			workCommand.setWorkQueue(workQueue);
		}
		workCommand.setQueueId(HdUtils.genUuid());
		workCommand.setPortCarNo(portCar.getPortCarNo());
		workCommand.setRfidCardNo(truckWork.getRfidCardNo());
		workCommand.setWorkTyp("TI");
		workCommand.setVinNo(truckWork.getVinNo());
		JpaUtils.save(workCommand);

		return HdUtils.genMsg();
	}

	@Override
	public HdEzuiDatagridData findPL(HdQuery hdQuery) {
		String jpql = "select a.oldBillNo,a.newBillNo,a.brandCod,a.cyPlac,count(a.oldBillNo) from MChangeShip a where 1=1 ";
		QueryParamLs paramLs = new QueryParamLs();
		String shipNo = hdQuery.getStr("shipNo");
		String type = hdQuery.getStr("type");
		String billNo = hdQuery.getStr("billNo");
		if (HdUtils.strIsNull(shipNo)) {
			jpql += " and a.oldShipNo like :shipNo ";
			paramLs.addParam("shipNo", "%" + "#123456#" + "%");
		} else if (HdUtils.strIsNull(billNo)){
			if ("I".equals(type)){
				jpql += "and a.newShipNo =:shipNo ";
				paramLs.addParam("shipNo", shipNo);
			} else if ("E".equals(type)){
				jpql += "and a.oldShipNo =:shipNo ";
				paramLs.addParam("shipNo", shipNo);
			} else if ("ALL".equals(type)){
				jpql += "and (a.oldShipNo =:ashipNo or a.newShipNo =:bshipNo) ";
				paramLs.addParam("ashipNo", shipNo);
				paramLs.addParam("bshipNo", shipNo);
			}
		} else if ("I".equals(type)){
			jpql += "and a.newShipNo =:shipNo and a.newBillNo =:newBillNo ";
			paramLs.addParam("shipNo", shipNo);
			paramLs.addParam("newBillNo", billNo);
		} else if ("E".equals(type)){
			jpql += "and a.oldShipNo =:shipNo and a.oldBillNo =:oldBillNo ";
			paramLs.addParam("shipNo", shipNo);
			paramLs.addParam("oldBillNo", billNo);
		} else if ("ALL".equals(type)){
			jpql += "and (a.oldShipNo =:ashipNo or a.newShipNo =:bshipNo) and (a.oldBillNo =:aBillNo or a.newBillNo =:bBillNo) ";
			paramLs.addParam("ashipNo", shipNo);
			paramLs.addParam("bshipNo", shipNo);
			paramLs.addParam("aBillNo", billNo);
			paramLs.addParam("bBillNo", billNo);
		}
		jpql += "group by a.oldBillNo,a.brandCod,a.newBillNo,a.cyPlac,a.oldShipNo ";
		List<Object[]> objList = JpaUtils.findAll(jpql, paramLs);
		List<MChangeShip> allList = new ArrayList();
		if (objList.size() > 0) {
			for (Object[] obj : objList) {
				String jpqlm = "select a from MChangeShip a where 1=1 and a.nIEId='E' ";
				QueryParamLs paramLsm = new QueryParamLs();
				if (obj[0] != null) {
					jpqlm += "and a.oldBillNo =:oldBillNo ";
					paramLsm.addParam("oldBillNo", obj[0]);
				}
				if (obj[1] != null) {
					jpqlm += "and a.newBillNo =:newBillNo ";
					paramLsm.addParam("newBillNo", obj[1]);
				}
				if (obj[2] != null) {
					jpqlm += "and a.brandCod =:brandCod ";
					paramLsm.addParam("brandCod", obj[2]);
				}
				if (HdUtils.strIsNull(shipNo)) {
					jpql += " and a.oldShipNo like :shipNo ";
					paramLs.addParam("shipNo", "%" + "#123456#" + "%");
				} else if (HdUtils.strIsNull(billNo)){
					if ("I".equals(type)){
						jpql += "and a.newShipNo =:shipNo ";
						paramLs.addParam("shipNo", shipNo);
					} else if ("E".equals(type)){
						jpql += "and a.oldShipNo =:shipNo ";
						paramLs.addParam("shipNo", shipNo);
					} else if ("ALL".equals(type)){
						jpql += "and (a.oldShipNo =:ashipNo or a.newShipNo =:bshipNo) ";
						paramLs.addParam("ashipNo", shipNo);
						paramLs.addParam("bshipNo", shipNo);
					}
				} else if ("I".equals(type)){
					jpql += "and a.newShipNo =:shipNo and a.newBillNo =:newBillNo ";
					paramLs.addParam("shipNo", shipNo);
					paramLs.addParam("newBillNo", billNo);
				} else if ("E".equals(type)){
					jpql += "and a.oldShipNo =:shipNo and a.oldBillNo =:oldBillNo ";
					paramLs.addParam("shipNo", shipNo);
					paramLs.addParam("oldBillNo", billNo);
				} else if ("ALL".equals(type)){
					jpql += "and (a.oldShipNo =:ashipNo or a.newShipNo =:bshipNo) and (a.oldBillNo =:aBillNo or a.newBillNo =:bBillNo) ";
					paramLs.addParam("ashipNo", shipNo);
					paramLs.addParam("bshipNo", shipNo);
					paramLs.addParam("aBillNo", billNo);
					paramLs.addParam("bBillNo", billNo);
				}
				if (obj[3] != null) {
					jpqlm += "and a.cyPlac =:cyAreaNo";
					paramLsm.addParam("cyAreaNo", obj[3]);
				}
				List<MChangeShip> mChangeShipList = JpaUtils.findAll(jpqlm, paramLsm);
				if (mChangeShipList.size() > 0) {
					MChangeShip mChangeShip = mChangeShipList.get(0);
					mChangeShip.setCountNum((obj[4].toString()));
					mChangeShip.setCyPlac((obj[3].toString()));
					if (HdUtils.strNotNull(mChangeShip.getOldShipNo())) {
						Ship oldShip = JpaUtils.findById(Ship.class, mChangeShip.getOldShipNo());
						mChangeShip.setOldNamVoyage(oldShip.getcShipNam() + "/" + oldShip.getEvoyage());
					}
					if (HdUtils.strNotNull(mChangeShip.getNewShipNo())) {
						Ship newShip = JpaUtils.findById(Ship.class, mChangeShip.getNewShipNo());
						mChangeShip.setNewNamVoyage(newShip.getcShipNam() + "/" + newShip.getEvoyage());
					}
					if (HdUtils.strNotNull(mChangeShip.getBrandCod())) {
						CBrand cbrand = JpaUtils.findById(CBrand.class, mChangeShip.getBrandCod());
						mChangeShip.setBrandNam(cbrand.getBrandNam());
					}
					if (HdUtils.strNotNull(mChangeShip.getOldTranPortCod())) {
						String jpqlc = "select a from CPort a where a.portCod=:portCod";
						QueryParamLs paramLsc = new QueryParamLs();
						paramLsc.addParam("portCod", mChangeShip.getOldTranPortCod());
						List<CPort> cportList = JpaUtils.findAll(jpqlc, paramLsc);
						if (cportList.size() > 0) {
							mChangeShip.setNewTranPortNam((cportList.get(0).getcPortNam()));
						}
					}
					if (HdUtils.strNotNull(mChangeShip.getOldDiscPortCod())) {
						String jpqlc = "select a from CPort a where a.portCod=:portCod";
						QueryParamLs paramLsc = new QueryParamLs();
						paramLsc.addParam("portCod", mChangeShip.getOldDiscPortCod());
						List<CPort> cportList = JpaUtils.findAll(jpqlc, paramLsc);
						if (cportList.size() > 0) {
							mChangeShip.setNewDiscPortNam(cportList.get(0).getcPortNam());
						}
					}
					allList.add(mChangeShip);
				}

			}
		}
		HdEzuiDatagridData results = new HdEzuiDatagridData();
		results.setRows(allList);
		return results;
	}

	@Transactional
	public HdMessageCode savePL(String shipNos, String billNos, String brandCods, String carTyps, String cyAreaNos,
			String countNum, String newTranPortCod, String newDiscPortCod, String newShipNo, String newBillNo,
			int num,String flag,String dockCod) {
		List<String> shipNoList = HdUtils.paraseStrs(shipNos);
		List<String> billNoList = HdUtils.paraseStrs(billNos);
		List<String> brandCodList = HdUtils.paraseStrs(brandCods);
		List<String> carTypList = HdUtils.paraseStrs(carTyps);
		List<String> cyAreaNoList = HdUtils.paraseStrs(cyAreaNos);
		List<String> countNumList = HdUtils.paraseStrs(countNum);
		System.err.println(countNumList);
		for (int i = 0; i < shipNoList.size(); i++) {
			String jpql = "select a from PortCar a where a.currentStat = '2'";
			QueryParamLs paramLs = new QueryParamLs();
			if (HdUtils.strNotNull(shipNoList.get(i))) {
				jpql += " and a.shipNo=:shipNo ";
				paramLs.addParam("shipNo", shipNoList.get(i));
			}
			if (HdUtils.strNotNull(billNoList.get(i))) {
				jpql += " and a.billNo like :billNo ";
				paramLs.addParam("billNo", "%"+billNoList.get(i)+"%");
			}
			if (HdUtils.strNotNull(carTypList.get(i))) {
				jpql += " and a.carTyp=:carTyp ";
				paramLs.addParam("carTyp", carTypList.get(i));
			}
			if (brandCodList.size() > 0) {
				if (HdUtils.strNotNull(brandCodList.get(i))) {
					jpql += " and a.brandCod=:brandCod ";
					paramLs.addParam("brandCod", brandCodList.get(i));
				}
			}
			if (HdUtils.strNotNull(cyAreaNoList.get(i))) {
				jpql += " and a.cyAreaNo=:cyAreaNo ";
				paramLs.addParam("cyAreaNo", cyAreaNoList.get(i));
			}
			List<PortCar> portCarList = JpaUtils.findAll(jpql, paramLs);
				if (portCarList.size() > 0) {
					for (int m = 0; m < num; m++) {
						String jpqln = "select a from MChangeShip a where a.portCarNo=:portCarNo ";
						QueryParamLs paramLsn = new QueryParamLs();
						paramLsn.addParam("portCarNo", portCarList.get(m).getPortCarNo());
						List<MChangeShip> mchangeList = JpaUtils.findAll(jpqln, paramLsn);
						if (mchangeList.size() > 0) {
							String jpqlnMore = "select count(a) from MChangeShip a where a.portCarNo=:portCarNo ";
							QueryParamLs paramLsnjpqlnMore = new QueryParamLs();
							paramLsnjpqlnMore.addParam("portCarNo", portCarList.get(m).getPortCarNo());
							List<MChangeShip> mchangeListMore = JpaUtils.findAll(jpqlnMore, paramLsnjpqlnMore);
							if (mchangeListMore.size() > 0){
								if (mchangeListMore.size()<portCarList.size()||mchangeListMore.size()==portCarList.size()){
									MChangeShip mChangeShip = new MChangeShip();
									mChangeShip.setChangeid(HdUtils.genUuid());
									mChangeShip.setPortCarNo(portCarList.get(m).getPortCarNo());
									mChangeShip.setRfidCardNo("1");
									mChangeShip.setVinNo(portCarList.get(m).getVinNo());
									mChangeShip.setBrandCod(portCarList.get(m).getBrandCod());
									mChangeShip.setOIEId(portCarList.get(m).getiEId());
									mChangeShip.setnIEId("E");
									mChangeShip.setNewBillNo(newBillNo);
									mChangeShip.setNewShipNo(newShipNo);
									mChangeShip.setOldShipNo(portCarList.get(m).getShipNo());
									mChangeShip.setOldBillNo(portCarList.get(m).getBillNo());
									mChangeShip.setOldDiscPortCod(portCarList.get(m).getDiscPortCod());
									mChangeShip.setOldTranPortCod(portCarList.get(m).getTranPortCod());
									if (HdUtils.strNotNull(newDiscPortCod)) {
										mChangeShip.setNewDiscPortCod(newDiscPortCod);
									} else {
										mChangeShip.setNewDiscPortCod(portCarList.get(m).getDiscPortCod());
									}
									if (HdUtils.strNotNull(newTranPortCod)) {
										mChangeShip.setNewTranPortCod(newTranPortCod);
									} else {
										mChangeShip.setNewTranPortCod(portCarList.get(m).getTranPortCod());
									}
									if ("T".equals(flag)){
										mChangeShip.setFlag("T");
									}else {
										mChangeShip.setFlag("F");
									}
									mChangeShip.setCyPlac(portCarList.get(m).getCyAreaNo());
									mChangeShip.setRecNam(HdUtils.getCurUser().getAccount());
									mChangeShip.setRecTim(HdUtils.getDateTime());
									JpaUtils.save(mChangeShip);
									portCarList.get(m).setShipNo(newShipNo);
									portCarList.get(m).setBillNo(newBillNo);
									if (HdUtils.strNotNull(dockCod)){
										portCarList.get(m).setDockCod(dockCod);
									}
									portCarList.get(m).setTranPortCod(newTranPortCod);
									portCarList.get(m).setDiscPortCod(newDiscPortCod);
									if (Ship.JK.equals(portCarList.get(m).getiEId())){
										portCarList.get(m).setiEId(Ship.CK);
									}
									JpaUtils.update(portCarList.get(m));
								}
							}
							//continue;
						} else {
							MChangeShip mChangeShip = new MChangeShip();
							mChangeShip.setChangeid(HdUtils.genUuid());
							mChangeShip.setPortCarNo(portCarList.get(m).getPortCarNo());
							mChangeShip.setRfidCardNo("1");
							mChangeShip.setVinNo(portCarList.get(m).getVinNo());
							mChangeShip.setBrandCod(portCarList.get(m).getBrandCod());
							mChangeShip.setOIEId(portCarList.get(m).getiEId());
							mChangeShip.setnIEId("E");
							mChangeShip.setNewBillNo(newBillNo);
							mChangeShip.setNewShipNo(newShipNo);
							mChangeShip.setOldShipNo(portCarList.get(m).getShipNo());
							mChangeShip.setOldBillNo(portCarList.get(m).getBillNo());
							mChangeShip.setOldDiscPortCod(portCarList.get(m).getDiscPortCod());
							mChangeShip.setOldTranPortCod(portCarList.get(m).getTranPortCod());
							if (HdUtils.strNotNull(newDiscPortCod)) {
								mChangeShip.setNewDiscPortCod(newDiscPortCod);
							} else {
								mChangeShip.setNewDiscPortCod(portCarList.get(m).getDiscPortCod());
							}
							if (HdUtils.strNotNull(newTranPortCod)) {
								mChangeShip.setNewTranPortCod(newTranPortCod);
							} else {
								mChangeShip.setNewTranPortCod(portCarList.get(m).getTranPortCod());
							}
							if ("T".equals(flag)){
								mChangeShip.setFlag("T");
							}else {
								mChangeShip.setFlag("F");
							}
							mChangeShip.setCyPlac(portCarList.get(m).getCyAreaNo());
							mChangeShip.setRecNam(HdUtils.getCurUser().getAccount());
							mChangeShip.setRecTim(HdUtils.getDateTime());
							JpaUtils.save(mChangeShip);
							portCarList.get(m).setShipNo(newShipNo);
							portCarList.get(m).setBillNo(newBillNo);
							if (HdUtils.strNotNull(dockCod)){
								portCarList.get(m).setDockCod(dockCod);
							}
							portCarList.get(m).setTranPortCod(newTranPortCod);
							portCarList.get(m).setDiscPortCod(newDiscPortCod);
							if (Ship.JK.equals(portCarList.get(m).getiEId())){
								portCarList.get(m).setiEId(Ship.CK);
							}
							JpaUtils.update(portCarList.get(m));
						}
					}
				}
		}
		return HdUtils.genMsg();
	}

}
