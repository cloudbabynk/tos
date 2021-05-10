package net.huadong.tech.work.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.bean.HdRunTimeException;
import net.huadong.tech.base.entity.CBrand;
import net.huadong.tech.base.entity.CCarTyp;
import net.huadong.tech.base.entity.CDock;
import net.huadong.tech.cargo.entity.TruckWork;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.tech.gate.entity.GateTruckContract;
import net.huadong.tech.privilege.entity.AuthUser;
import net.huadong.tech.ship.entity.Ship;
import net.huadong.tech.shipbill.entity.BillCar;
import net.huadong.tech.shipbill.entity.PortCar;
import net.huadong.tech.shipbill.entity.ShipBill;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.util.HdUtils;
import net.huadong.tech.work.entity.CargoInfo;
import net.huadong.tech.work.entity.WorkCommand;
import net.huadong.tech.work.entity.WorkCommandRollback;
import net.huadong.tech.work.service.WorkCommandRollbackService;

@Component
public class WorkCommandRollbackServiceImpl implements WorkCommandRollbackService {

	@Override
	public HdEzuiDatagridData find(HdQuery hdQuery) {
		// TODO Auto-generated method stub
		String jpql = "select a from WorkCommand a where 1=1 ";
		String billNo = hdQuery.getStr("billNo");
		String contractNo = hdQuery.getStr("contractNo");
		String shipNo = hdQuery.getStr("shipNo");
		String carTyp = hdQuery.getStr("carTyp");
		String brandCod = hdQuery.getStr("brandCod");
		String workTyp = hdQuery.getStr("workTyp");
		String vinNo =hdQuery.getStr("vinNo");
		QueryParamLs paramLs = new QueryParamLs();
		if (HdUtils.strNotNull(billNo)) {
			jpql += "and a.billNo like :billNo ";
			paramLs.addParam("billNo", "%" + billNo + "%");
		}
		if(HdUtils.strNotNull(vinNo)){
			jpql += "and a.vinNo like :vinNo ";
			paramLs.addParam("vinNo", "%" + vinNo + "%");
		}
		if (HdUtils.strNotNull(contractNo)) {
			jpql += "and a.contractNo =:contractNo ";
			paramLs.addParam("contractNo", contractNo);
		}
		if (HdUtils.strNotNull(carTyp)) {
			jpql += "and a.carTyp =:carTyp ";
			paramLs.addParam("carTyp", carTyp);
		}
		if (HdUtils.strNotNull(brandCod)) {
			jpql += "and a.brandCod =:brandCod ";
			paramLs.addParam("brandCod", brandCod);
		}
		if (HdUtils.strNotNull(shipNo)) {
			jpql += "and a.shipNo =:shipNo ";
			paramLs.addParam("shipNo", shipNo);
		}
		if (HdUtils.strNotNull(workTyp)) {
			jpql += "and a.workTyp =:workTyp ";
			if ("CZSO".equals(workTyp)){
				paramLs.addParam("workTyp", "SO");
			} else {
				paramLs.addParam("workTyp", workTyp);
			}
		}
		jpql += "order by a.shipWorkTim desc";
		HdEzuiDatagridData result = JpaUtils.findByEz(jpql, paramLs, hdQuery);
		List<WorkCommand> workCommandList = result.getRows();
		if (workCommandList.size() > 0) {
			for (WorkCommand wc : workCommandList) {
				if (HdUtils.strNotNull(wc.getShipNo())) {
					Ship ship = JpaUtils.findById(Ship.class, wc.getShipNo());
					wc.setcShipNam(ship.getcShipNam());
					wc.setVoyage(ship.getIvoyage() + '/' + ship.getEvoyage());
				}
				if (HdUtils.strNotNull(wc.getBrandCod())) {
					CBrand cb = JpaUtils.findById(CBrand.class, wc.getBrandCod());
					if (cb != null)
					wc.setBrandNam(cb.getBrandNam());
				}
				if (HdUtils.strNotNull(wc.getWorkTyp())) {
					wc.setWorkTypNam(HdUtils.getSysCodeName("WORK_TYP", wc.getWorkTyp()));
				}
				if (HdUtils.strNotNull(wc.getCarTyp())) {
					CCarTyp cct = JpaUtils.findById(CCarTyp.class, wc.getCarTyp());
					if (cct != null)
					wc.setCarTypNam(cct.getCarTypNam());
				}
				if (wc.getPortCarNo() != null) {
					PortCar bean = JpaUtils.findById(PortCar.class, wc.getPortCarNo());
					if (bean != null){
						wc.setCyPlac(bean.getCyAreaNo());
						if (HdUtils.strNotNull(bean.getTranPortCod())){
							String tranPortCodNam = HdUtils.getSysCodeName("NM_FLOW_AREA", bean.getTranPortCod());
							if (HdUtils.strNotNull(tranPortCodNam)){
								wc.setTranPortCodNam(tranPortCodNam);
							}
						}
					}
				}
			}
		}
		return result;
	}

	/**
	 * 卸船回退
	 */
	@Transactional
	@Override
	public void unloadBack(String portCarNo) {
		List<String> portCarNoList = HdUtils.paraseStrs(portCarNo);
		for (String portCarN : portCarNoList) {
			String jpql = "select a  from WorkCommand a where a.portCarNo=:portCarNo and a.workTyp='SI' ";
			QueryParamLs paramLs = new QueryParamLs();
			BigDecimal portcarno = new BigDecimal(portCarN);
			paramLs.addParam("portCarNo", portcarno);
			List<WorkCommand> workCommandList = JpaUtils.findAll(jpql, paramLs);
			if (workCommandList.size() > 0) {
				for (WorkCommand wc : workCommandList) {
					insertIntoWorkCommandRollback(portcarno);
					JpaUtils.remove(wc);
				}
			}

			String jpql2 = "select a  from PortCar a where a.portCarNo=:portCarNo";
			QueryParamLs paramLs2 = new QueryParamLs();
			paramLs2.addParam("portCarNo", portcarno);
			List<PortCar> portCarList = JpaUtils.findAll(jpql2, paramLs2);
			if (portCarList.size() > 0) {
				for (PortCar pc : portCarList) {
					if (Ship.NM.equals(pc.getTradeId())) {
						JpaUtils.remove(pc);
					} else {
						pc.setCurrentStat("0");
						pc.setInCyTim(null);
						pc.setRfidCardNo("");
						pc.setVinNo("");
						pc.setBrandCod("");
						pc.setLengthOverId("");
						pc.setCarTyp("");
						pc.setCarKind("");
						pc.setCyAreaNo("");
						pc.setCyRowNo("");
						pc.setCyBayNo("");
						pc.setCyPlac("");
						pc.setCarLevel("");
						pc.setRemarks("");
						JpaUtils.update(pc);
					}

				}
			}

			String jpql3 = "select a from BillCar a where a.portCarNo=:portCarNo ";
			QueryParamLs paramLs3 = new QueryParamLs();
			paramLs3.addParam("portCarNo", portcarno);
			List<BillCar> billCarList = JpaUtils.findAll(jpql3, paramLs3);
			if (billCarList.size() > 0) {
				for (BillCar bc : billCarList) {
					if (Ship.JK.equals(bc.getiEId()) && Ship.NM.equals(bc.getTradeId())) {
						JpaUtils.remove(bc);
					} else {
//						bc.setBrandCod("");
						bc.setCarKind("");
						bc.setCarTyp("");
						bc.setRfidCardNo("");
						bc.setVinNo("");
						bc.setLhFlag("0");
						JpaUtils.update(bc);
					}
				}

			}
			insertIntoWorkCommandRollback(portcarno);
		}
	}

	// 回退成功操作
	private void insertIntoWorkCommandRollback(BigDecimal portcarno) {

		String jpql4 = "select a from WorkCommand a where a.portCarNo=:portCarNo ";
		QueryParamLs paramLs4 = new QueryParamLs();
		// BigDecimal portcarno=new BigDecimal(portCarNo);
		paramLs4.addParam("portCarNo", portcarno);
		List<WorkCommand> workCommadnList = JpaUtils.findAll(jpql4, paramLs4);
		if (workCommadnList.size() > 0) {
			for (WorkCommand wc : workCommadnList) {
				WorkCommandRollback workCommandRollback = new WorkCommandRollback();
				BeanUtils.copyProperties(wc, workCommandRollback);
				String uuid = HdUtils.genUuid();
				workCommandRollback.setRollbackId(uuid);
				workCommandRollback.setRollbackOper(HdUtils.getCurUser().getAccount());
				workCommandRollback.setRollbackTim(HdUtils.getDateTime());
				JpaUtils.save(workCommandRollback);
			}
		}
	}

	// 装船回退
	@Transactional
	@Override
	public void loadBack(String portCarNo) {
		List<String> portCarNoList = HdUtils.paraseStrs(portCarNo);
		for (String portCarN : portCarNoList) {
			String jpql = "select a  from WorkCommand a where a.portCarNo=:portCarNo and a.workTyp='SO' ";
			QueryParamLs paramLs = new QueryParamLs();
			BigDecimal portcarno = new BigDecimal(portCarN);
			paramLs.addParam("portCarNo", portcarno);
			List<WorkCommand> workCommandList = JpaUtils.findAll(jpql, paramLs);
			if (workCommandList.size() > 0) {
				for (WorkCommand wc : workCommandList) {
					insertIntoWorkCommandRollback(portcarno);
					PortCar portcar = JpaUtils.findById(PortCar.class, portcarno);
					portcar.setCurrentStat("2");
					portcar.setCyPlac(wc.getOldPlac());
					portcar.setOutCyTim(null);
					portcar.setRemarks("指令回退");
					String jpql2 = "select a  from WorkCommand a where a.portCarNo=:portCarNo and a.workTyp='TI' ";
					List<WorkCommand> jgResult = JpaUtils.findAll(jpql2, paramLs);
					if (jgResult.size()>0){
						if (Ship.CZNO.equals(jgResult.get(0).getShipNo())){
							portcar.setShipNo(Ship.CZNO);
						}
					}
					portcar.setUpdNam(HdUtils.getCurUser().getAccount());
					portcar.setUpdTim(HdUtils.getDateTime());
					
					//作业回退需要判断内外贸，目前是只改变外贸装船
					String sql = "select a from BillCar a where a.shipNo =:shipNo and a.billNo =:billNo and a.portCarNo =:portCarNo";
					QueryParamLs paramLs1 = new QueryParamLs();
					paramLs1.addParam("shipNo", portcar.getShipNo());
					paramLs1.addParam("billNo", portcar.getBillNo());
					paramLs1.addParam("portCarNo", portcar.getPortCarNo());
					List<BillCar> billCarList = JpaUtils.findAll(sql, paramLs1);
					if (billCarList.size() > 0) {
						Ship shiplist=JpaUtils.findById(Ship.class, wc.getShipNo()); 
						if("2".equals(shiplist.getTradeId())){
							for(BillCar billCar:billCarList){
								billCar.setLhFlag("0");
								JpaUtils.update(billCar);
							}
							
						}else{
							JpaUtils.remove(billCarList.get(0));
						} 
						
					}
					
					JpaUtils.update(portcar);
					JpaUtils.remove(wc);
				}
			}
			insertIntoWorkCommandRollback(portcarno);
		}
	}
	
	// 存栈装船回退
	@Transactional
	@Override
	public void loadBackCz(String portCarNo) {
		List<String> portCarNoList = HdUtils.paraseStrs(portCarNo);
		for (String portCarN : portCarNoList) {
			String jpql = "select a  from WorkCommand a where a.portCarNo=:portCarNo and a.workTyp='SO' ";
			QueryParamLs paramLs = new QueryParamLs();
			BigDecimal portcarno = new BigDecimal(portCarN);
			paramLs.addParam("portCarNo", portcarno);
			List<WorkCommand> workCommandList = JpaUtils.findAll(jpql, paramLs);
			if (workCommandList.size() > 0) {
				for (WorkCommand wc : workCommandList) {
					insertIntoWorkCommandRollback(portcarno);
					PortCar portcar = JpaUtils.findById(PortCar.class, portcarno);
					portcar.setCurrentStat("2");
					portcar.setCyPlac(wc.getOldPlac());
					portcar.setOutCyTim(null);
					portcar.setRemarks("指令回退");
					portcar.setShipNo("20190311082013");
					portcar.setUpdNam(HdUtils.getCurUser().getAccount());
					portcar.setUpdTim(HdUtils.getDateTime());
					String sql = "select a from BillCar a where a.shipNo =:shipNo and a.billNo =:billNo and a.portCarNo =:portCarNo";
					QueryParamLs paramLs1 = new QueryParamLs();
					paramLs1.addParam("shipNo", portcar.getShipNo());
					paramLs1.addParam("billNo", portcar.getBillNo());
					paramLs1.addParam("portCarNo", portcar.getPortCarNo());
					List<BillCar> billCarList = JpaUtils.findAll(sql, paramLs1);
					if (billCarList.size() > 0) {
						JpaUtils.remove(billCarList.get(0));
					}
					JpaUtils.update(portcar);
					JpaUtils.remove(wc);
				}
			}
			insertIntoWorkCommandRollback(portcarno);
		}
	}

	// 集港回退
	@Transactional
	@Override
	public void jgBack(String portCarNo) {
		List<String> portCarNoList = HdUtils.paraseStrs(portCarNo);
		for (String portCarN : portCarNoList) {
			String jpql = "select a  from WorkCommand a where a.portCarNo=:portCarNo and a.workTyp='TI' ";
			QueryParamLs paramLs = new QueryParamLs();
			BigDecimal portcarno = new BigDecimal(portCarN);
			paramLs.addParam("portCarNo", portcarno);
			List<WorkCommand> workCommandList = JpaUtils.findAll(jpql, paramLs);
			if (workCommandList.size() > 0) {
				for (WorkCommand wc : workCommandList) {
					insertIntoWorkCommandRollback(portcarno);
					JpaUtils.remove(wc);
				}
			}
			String jpql2 = "select a  from TruckWork a where a.portCarNo=:portCarNo and a.carryId='A' ";
			QueryParamLs paramLs2 = new QueryParamLs();
			paramLs2.addParam("portCarNo", portcarno);
			List<TruckWork> truckWorkList = JpaUtils.findAll(jpql2, paramLs2);
			if (truckWorkList.size() > 0) {
				for (TruckWork tw : truckWorkList) {
					String jpql6 = "select a from GateTruckContract a where a.truckNo=:truckNo and a.contractNo=:contractNo";
					QueryParamLs paramLs6 = new QueryParamLs();
					paramLs6.addParam("truckNo", tw.getTruckNo());
					paramLs6.addParam("contractNo", tw.getContractNo());
					List<GateTruckContract> gateTruckContractList = JpaUtils.findAll(jpql6, paramLs6);
					if (gateTruckContractList.size() > 0) {
						for (GateTruckContract gtc : gateTruckContractList) {
							// String jpql3 = "update GateTruckContract a set
							// a.workNum=:workNum "
							// + " where a.truckNo=:truckNo and
							// a.contractNo=:contractNo ";
							// QueryParamLs paramLs3 = new QueryParamLs();
							// paramLs3.addParam("workNum",
							// gtc.getWorkNum().subtract(new BigDecimal("1")));
							// paramLs3.addParam("truckNo", tw.getTruckNo());
							// paramLs3.addParam("contractNo",
							// tw.getContractNo());
							gtc.setWorkNum(gtc.getWorkNum().subtract(new BigDecimal("1")));
							JpaUtils.update(gtc);
						}
					}
					JpaUtils.remove(tw);
				}
			}

			String jpql4 = "update PortCar a set a.cyPlac='' " + " ,a.currentStat='2',a.inCyTim=null "
					+ " ,a.updNam=:updNam ,a.updTim=:updTim ,a.remarks='收车指令回退' " + " where a.portCarNo=:portCarNo ";
			QueryParamLs paramLs4 = new QueryParamLs();
			paramLs4.addParam("portCarNo", portcarno);
			paramLs4.addParam("updNam", HdUtils.getCurUser().getAccount());
			paramLs4.addParam("updTim", HdUtils.getDateTime());
			JpaUtils.execUpdate(jpql4, paramLs4);

			String jpql5 = "select a  from PortCar a where a.portCarNo=:portCarNo";
			QueryParamLs paramLs5 = new QueryParamLs();
			paramLs5.addParam("portCarNo", portcarno);
			List<PortCar> portCarList = JpaUtils.findAll(jpql5, paramLs5);
			if (portCarList.size() > 0) {
				for (PortCar pc : portCarList) {
					// 内贸集港作业回退还要减少内贸集港理货生成提单上的数量
					if (pc.getTradeId().equals("1")) {
						String jpqlc = "select a from ShipBill a where a.billNo=:billNo and a.tranPortCod=:tranPortCod ";
						QueryParamLs paramLsc = new QueryParamLs();
						paramLsc.addParam("billNo", pc.getBillNo());
						paramLsc.addParam("tranPortCod", pc.getTranPortCod());
						List<ShipBill> sbL = JpaUtils.findAll(jpqlc, paramLsc);
						if (sbL.size() > 0) {
							sbL.get(0).setPieces(sbL.get(0).getPieces().subtract(new BigDecimal("1")));
							JpaUtils.update(sbL.get(0));
						}
					}
					JpaUtils.remove(pc);

				}
			}
		}
	}

	// 转栈回退
	@Transactional
	@Override
	public void tzBack(String portCarNo) {
		List<String> portCarNoList = HdUtils.paraseStrs(portCarNo);
		for (String portCarN : portCarNoList) {
			String jpql = "select a  from WorkCommand a where a.portCarNo=:portCarNo and a.workTyp='TZ' ";
			QueryParamLs paramLs = new QueryParamLs();
			BigDecimal portcarno = new BigDecimal(portCarN);
			paramLs.addParam("portCarNo", portcarno);
			List<WorkCommand> workCommandList = JpaUtils.findAll(jpql, paramLs);
			if (workCommandList.size() > 0) {
				for (WorkCommand wc : workCommandList) {
					insertIntoWorkCommandRollback(portcarno);
					JpaUtils.remove(wc);
				}
			}

			String jpql4 = "update PortCar a set a.cyPlac=null " + " ,a.currentStat='2',a.outCyTim=null"
					+ " ,a.updNam=:updNam ,a.updTim=:updTim ,a.remarks='提车指令回退' " + " where a.portCarNo=:portCarNo ";
			QueryParamLs paramLs4 = new QueryParamLs();
			paramLs4.addParam("portCarNo", portcarno);
			paramLs4.addParam("updNam", HdUtils.getCurUser().getAccount());
			paramLs4.addParam("updTim", HdUtils.getDateTime());
			JpaUtils.execUpdate(jpql4, paramLs4);
		}
	}

	// 疏港回退
	@Transactional
	@Override
	public void sgBack(String portCarNo) {
		List<String> portCarNoList = HdUtils.paraseStrs(portCarNo);
		for (String portCarN : portCarNoList) {
			String jpql = "select a  from WorkCommand a where a.portCarNo=:portCarNo and a.workTyp='TO' ";
			QueryParamLs paramLs = new QueryParamLs();
			BigDecimal portcarno = new BigDecimal(portCarN);
			paramLs.addParam("portCarNo", portcarno);
			List<WorkCommand> workCommandList = JpaUtils.findAll(jpql, paramLs);
			if (workCommandList.size() > 0) {
				for (WorkCommand wc : workCommandList) {
					insertIntoWorkCommandRollback(portcarno);
					JpaUtils.remove(wc);
				}
			}
			String jpql2 = "select a  from TruckWork a where a.portCarNo=:portCarNo and a.carryId='T' ";
			QueryParamLs paramLs2 = new QueryParamLs();
			paramLs2.addParam("portCarNo", portcarno);
			List<TruckWork> truckWorkList = JpaUtils.findAll(jpql2, paramLs2);
			if (truckWorkList.size() > 0) {
				for (TruckWork tw : truckWorkList) {
					String jpql6 = "select a from GateTruckContract a where a.truckNo=:truckNo and a.contractNo=:contractNo";
					QueryParamLs paramLs6 = new QueryParamLs();
					paramLs6.addParam("truckNo", tw.getTruckNo());
					paramLs6.addParam("contractNo", tw.getContractNo());
					List<GateTruckContract> gateTruckContractList = JpaUtils.findAll(jpql6, paramLs6);
					if (gateTruckContractList.size() > 0) {
						for (GateTruckContract gtc : gateTruckContractList) {
							String jpql3 = "update GateTruckContract a set a.workNum=:workNum "
									+ " where a.truckNo=:truckNo and a.contractNo=:contractNo ";
							QueryParamLs paramLs3 = new QueryParamLs();
							paramLs3.addParam("workNum", gtc.getWorkNum().subtract(new BigDecimal("1")));
							paramLs3.addParam("truckNo", tw.getTruckNo());
							paramLs3.addParam("contractNo", tw.getContractNo());
							JpaUtils.execUpdate(jpql3, paramLs3);
						}
					}

					JpaUtils.remove(tw);
				}
			}

			String jpql4 = "update PortCar a set a.cyPlac=null " + " ,a.currentStat='2',a.outCyTim=null"
					+ " ,a.updNam=:updNam ,a.updTim=:updTim ,a.remarks='提车指令回退' " + " where a.portCarNo=:portCarNo ";
			QueryParamLs paramLs4 = new QueryParamLs();
			paramLs4.addParam("portCarNo", portcarno);
			paramLs4.addParam("updNam", HdUtils.getCurUser().getAccount());
			paramLs4.addParam("updTim", HdUtils.getDateTime());
			JpaUtils.execUpdate(jpql4, paramLs4);
		}
	}

	@Override
	public HdMessageCode cargoBack(@RequestBody CargoInfo cargoInfo, String type) {
		String jpql = "select distinct a from PortCar a,WorkCommand b where a.portCarNo = b.portCarNo ";
		QueryParamLs paramLs = new QueryParamLs();
		AuthUser user = HdUtils.getCurUser();
		if ("WMPLXC".equals(type) || "NMPLXC".equals(type) || "NMPLJG".equals(type) || "WMPLJG".equals(type)){
			jpql += "and a.currentStat = '2'";
			if (cargoInfo.getInCyTim() != null){
				jpql += "and a.inCyTim >=:begTim and a.inCyTim <:endTim ";
				paramLs.addParam("begTim", cargoInfo.getInCyTim());
				paramLs.addParam("endTim", HdUtils.addDay(cargoInfo.getInCyTim(), 1));
			}
		} else {
			jpql += "and a.currentStat = '5'";
			if (cargoInfo.getInCyTim() != null){
				jpql += "and a.outCyTim >=:begTim and a.outCyTim <:endTim ";
				paramLs.addParam("begTim", cargoInfo.getInCyTim());
				paramLs.addParam("endTim", HdUtils.addDay(cargoInfo.getInCyTim(), 1));
			}
		}
		
		if (HdUtils.strNotNull(cargoInfo.getShipNo())) {
			jpql += "and a.shipNo =:shipNo ";
			paramLs.addParam("shipNo", cargoInfo.getShipNo());
		}
		if (HdUtils.strNotNull(cargoInfo.getBillNo())) {
			jpql += "and a.billNo =:billNo ";
			paramLs.addParam("billNo", cargoInfo.getBillNo());
		}
		if (HdUtils.strNotNull(cargoInfo.getContractNo())) {
			jpql += "and a.contractNo =:contractNo ";
			paramLs.addParam("contractNo", cargoInfo.getContractNo());
		}
		if (HdUtils.strNotNull(cargoInfo.getTransId())) {
			jpql += "and a.tradeId =:tradeId ";
			paramLs.addParam("tradeId", cargoInfo.getTransId());
		}
		if (HdUtils.strNotNull(cargoInfo.getiEId())) {
			jpql += "and a.iEId =:iEId ";
			paramLs.addParam("iEId", cargoInfo.getiEId());
		}
		if (HdUtils.strNotNull(cargoInfo.getBrandCod())) {
			jpql += "and a.brandCod =:brandCod ";
			paramLs.addParam("brandCod", cargoInfo.getBrandCod());
		}
		if (HdUtils.strNotNull(cargoInfo.getCarTyp())) {
			jpql += "and a.carTyp =:carTyp ";
			paramLs.addParam("carTyp", cargoInfo.getCarTyp());
		}
		if ("NMPLJG".equals(type) || "WMPLJG".equals(type)){
			if (!user.getAccount().equals(cargoInfo.getInCyNam())){
				throw new HdRunTimeException("只有理货员本人能够删除，非本人不能删除！");
			}
			if (HdUtils.strNotNull(cargoInfo.getInCyNam())) {
				jpql += "and a.recNam =:recNam ";
				paramLs.addParam("recNam", cargoInfo.getInCyNam());
			}
		} else if ("WMPLSG".equals(type) || "PLTZ".equals(type)){
			if (HdUtils.strNotNull(cargoInfo.getInCyNam())) {
				jpql += "and a.updNam =:updNam ";
				paramLs.addParam("updNam", cargoInfo.getInCyNam());
			}
		}
		if (HdUtils.strNotNull(cargoInfo.getUseMachId())){
			jpql += "and b.useMachId =:useMachId ";
			paramLs.addParam("useMachId", cargoInfo.getUseMachId());
		}
		if (HdUtils.strNotNull(cargoInfo.getUseWorkerId())){
			jpql += "and b.useWorkerId =:useWorkerId ";
			paramLs.addParam("useWorkerId", cargoInfo.getUseWorkerId());
		}
		if (HdUtils.strNotNull(cargoInfo.getHolidayId())){
			jpql += "and b.holidayId =:holidayId ";
			paramLs.addParam("holidayId", cargoInfo.getHolidayId());
		}
		if (HdUtils.strNotNull(cargoInfo.getNightId())){
			jpql += "and b.nightId =:nightId ";
			paramLs.addParam("nightId", cargoInfo.getNightId());
		}
		List<PortCar> portCarList = JpaUtils.findAll(jpql, paramLs);
		
		if (portCarList.size() != Integer.valueOf(cargoInfo.getRcsl().toString())) {
			if (type.endsWith("JG") && !user.getAccount().equals(cargoInfo.getInCyNam())){
				throw new HdRunTimeException("理货操作时，登录帐号和所选理货员信息不匹配，无法删除！");
			}
			throw new HdRunTimeException("此条数据不能进行删除，请选择作业回退！");
		}
		String portCarNos = "";
		for (PortCar portCar : portCarList) {
			portCarNos += portCar.getPortCarNo() + ",";
		}
		if ("NMPLJG".equals(type) || "WMPLJG".equals(type)) {
			jgBack(portCarNos);
		}
		if ("NMPLXC".equals(type) || "WMPLXC".equals(type)) {
			unloadBack(portCarNos);
		}
		if ("WMPLSG".equals(type)){
			sgBack(portCarNos);
		}
		if ("WMPLZC".equals(type) ) {
			loadBack(portCarNos);
		}
		if ("PLTZ".equals(type)) {
			tzBack(portCarNos);
		}
		if ("CZZC".equals(type)){
			loadBackCz(portCarNos);
		}

		return HdUtils.genMsg();
	}
}
