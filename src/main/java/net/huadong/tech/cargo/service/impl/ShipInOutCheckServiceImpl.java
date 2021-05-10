package net.huadong.tech.cargo.service.impl;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.persistence.config.QueryHints;
import org.eclipse.persistence.config.ResultType;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.bean.HdRunTimeException;
import net.huadong.tech.base.entity.CClientCod;
import net.huadong.tech.cargo.entity.ShipMaster;
import net.huadong.tech.cargo.service.ShipInOutCheckService;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.tech.inter.entity.CPortFt;
import net.huadong.tech.inter.entity.MShipOutToFt;
import net.huadong.tech.inter.entity.ShipIn;
import net.huadong.tech.inter.entity.ShipInOutCheck;
import net.huadong.tech.inter.entity.ShipOut;
import net.huadong.tech.inter.entity.YardIn;

import net.huadong.tech.ship.entity.CShipDataFt;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;
import net.huadong.tech.util.HdUtils;

import net.huadong.tech.cargo.service.impl.DBUtil;

/**
 * @author
 */
@Component
public class ShipInOutCheckServiceImpl implements ShipInOutCheckService {

	@Override
	public HdEzuiDatagridData find(HdQuery hdQuery) {
		// TODO Auto-generated method stub
		String jpql = "select a from ShipInOutCheck a where 1=1 ";
		String shipNo = hdQuery.getStr("shipNo");
		String workTyp = hdQuery.getStr("workTyp");
		QueryParamLs paramLs = new QueryParamLs();
		if (HdUtils.strNotNull(workTyp)) {
			jpql += "and a.workTyp =:workTyp ";
			paramLs.addParam("workTyp", workTyp);
		}
		if (HdUtils.strNotNull(shipNo)) {
			jpql += "and a.shipNo =:shipNo ";
			paramLs.addParam("shipNo", shipNo);
		} else {
			jpql += "and a.shipNo =:shipNo ";
			paramLs.addParam("shipNo", "123456789##");//过滤船舶信息
		}
			
		jpql += "order by a.recTim desc";
		HdEzuiDatagridData result = JpaUtils.findByEz(jpql, paramLs, hdQuery);
		List<ShipInOutCheck> shipInOutCheckList = result.getRows();
		for(ShipInOutCheck bean : shipInOutCheckList){
			String sql = "select a from CPortFt a where a.vcPortId =:vcPortId";
			QueryParamLs paramLs1 = new QueryParamLs();
			paramLs1.addParam("vcPortId", bean.getVcPort());
			List<CPortFt> list = JpaUtils.findAll(sql, paramLs1);
			if (list.size()>0){
				bean.setVcPortNam(list.get(0).getVcPortName());
			}
		}
		return result;
	}
	
	

	@Override
	public HdMessageCode save(HdEzuiSaveDatagridData<ShipInOutCheck> hdEzuiSaveDatagridData) {
		// TODO Auto-generated method stub
		return JpaUtils.save(hdEzuiSaveDatagridData);
	}
	@Override
	public HdMessageCode saveone(@RequestBody ShipInOutCheck shipInOutCheck) {
		// TODO Auto-generated method stub
		if(HdUtils.strIsNull(shipInOutCheck.getCheckId())) {
			shipInOutCheck.setCheckId(HdUtils.genUuid());
			shipInOutCheck.setRecNam(HdUtils.getCurUser().getAccount());
			shipInOutCheck.setRecTim(HdUtils.getDateTime());
			JpaUtils.save(shipInOutCheck);
		} else {
			shipInOutCheck.setUpdNam(HdUtils.getCurUser().getAccount());
			shipInOutCheck.setUpdTim(HdUtils.getDateTime());
			JpaUtils.update(shipInOutCheck);
		}
		
		return HdUtils.genMsg();
	}

	
	@Override
	public ShipInOutCheck findone(String shipInOutCheck) {
		ShipInOutCheck bean = JpaUtils.findById(ShipInOutCheck.class, shipInOutCheck);
		return bean;
	}



	@Override
	public HdEzuiDatagridData findYardIn(HdQuery hdQuery) {
		// TODO Auto-generated method stub
		String jpql = "SELECT c FROM YardIn c where 1=1";
		String shipNo = hdQuery.getStr("shipNo");
		QueryParamLs paramLs = new QueryParamLs();
		if (HdUtils.strNotNull(shipNo)) {
			jpql += " and c.shipNo =:shipNo ";
			paramLs.addParam("shipNo", shipNo);
		} else {
			jpql += " and c.shipNo =:shipNo ";
			paramLs.addParam("shipNo", "123456789##");//过滤船舶信息
		}
			
		jpql += "order by c.recTim desc";
		HdEzuiDatagridData result = JpaUtils.findByEz(jpql, paramLs, hdQuery);
		List<YardIn> yardInList = result.getRows();
		for(YardIn bean : yardInList){
			String sql = "select a from CPortFt a where a.vcPortId =:vcPortId";
			QueryParamLs paramLs1 = new QueryParamLs();
			paramLs1.addParam("vcPortId", bean.getVcSite());
			List<CPortFt> list = JpaUtils.findAll(sql, paramLs1);
			if (list.size()>0){
				bean.setVcSite(list.get(0).getVcPortName());
			}
		}
		return result;
	}



	@Transactional
	public HdMessageCode removeAll(String shipNo, String workTyp) {
		// TODO Auto-generated method stub
		String shipInOutCheckJpql = "select a from ShipInOutCheck a where 1=1 and a.shipNo =:shipNo and a.workTyp =:workTyp";
		QueryParamLs shipInOutCheckParams = new QueryParamLs();
		shipInOutCheckParams.addParam("shipNo", shipNo);
		shipInOutCheckParams.addParam("workTyp", workTyp);
		List<ShipInOutCheck> shipInOutCheckList = JpaUtils.findAll(shipInOutCheckJpql, shipInOutCheckParams);
		if(shipInOutCheckList.size() > 0) {
			JpaUtils.removeAll(shipInOutCheckList);
		}
		
		if(workTyp.equals("SI")) {
			String yardInJpql = "SELECT c FROM YardIn c where 1=1 and c.shipNo =:shipNo";
			QueryParamLs yardInParams = new QueryParamLs();
			yardInParams.addParam("shipNo", shipNo);
			List<YardIn> yardInList = JpaUtils.findAll(yardInJpql, yardInParams);
			if(yardInList.size() > 0) {
				JpaUtils.removeAll(yardInList);
			}
		}
		return HdUtils.genMsg();
	}



	@Override
	public HdEzuiDatagridData findShipOut(HdQuery hdQuery) {
		// TODO Auto-generated method stub
		String shipNo = hdQuery.getStr("shipNo");
		String workTyp = hdQuery.getStr("workTyp");
		if(HdUtils.strNotNull(shipNo)) {
			String shipOutJpql = "SELECT c FROM ShipOut c where c.shipNo=:shipNo";
			QueryParamLs shipOutParams = new QueryParamLs();
			shipOutParams.addParam("shipNo", shipNo);
			List<ShipOut> shipOutList = JpaUtils.findAll(shipOutJpql, shipOutParams);
			if(shipOutList.size() > 0) {
				JpaUtils.removeAll(shipOutList);
			}
			
			String vcPortId = "";
				String portCode = "SELECT c FROM CPortFt c where c.vcPortName=:vcPortName";
				QueryParamLs vcportParams = new QueryParamLs();
				vcportParams.addParam("vcPortName", "天津港");
			List<CPortFt> vcPortFtList = JpaUtils.findAll(portCode, vcportParams);
			if(vcPortFtList.size() > 0) {
				String vcport = (String) vcPortFtList.get(0).getVcPortId();
				vcPortId = vcport;
			}
			
			String woorkCommandJpql = "SELECT w.shipNo,w.vinNo,p.discPortCod,w.inCyTim,w.vcexception,c.vcShipId FROM WorkCommand w, PortCar p, CShipDataFt c, Ship s "
					+ "where w.shipNo =:shipNo and w.workTyp =:workTyp and w.shipNo = s.shipNo and s.shipCodId =c.shipCodId and w.remarks = '手持录入' and w.portCarNo = p.portCarNo and (w.qzId <> '1' or w.qzId is null) "
					+ "  and p.brandCod in(SELECT c.brandCod FROM CBrand c where c.brandNam like '%丰田%')";
			QueryParamLs workCommandParams = new QueryParamLs();
			workCommandParams.addParam("shipNo", shipNo);
			workCommandParams.addParam("workTyp", workTyp);
			List<Object[]> workCommandList = JpaUtils.findAll(woorkCommandJpql, workCommandParams);
			if(workCommandList.size() > 0) {
				String Account = HdUtils.getCurUser().getAccount();
				ShipOut shipOutEntity = new ShipOut();
				if(workTyp.equals("SI")) {
					for(Object[] obj : workCommandList) {
//						String inCyTim = "";
//						if(!obj[3].equals("")) {
//							inCyTim = HdUtils.getDateStr((Timestamp) obj[3]);
//						} else {
//							Date sysDate = HdUtils.getDateTime();
//							inCyTim = HdUtils.getDateStr((Timestamp) sysDate);
//						}
						String inCyTim = "";
						 String str = ObjectUtils.toString(obj[3]);
						 DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						if(!str.equals("")) {
							inCyTim = format.format((Timestamp) obj[3]);
						} else {
							Date sysDate = HdUtils.getDateTime();
							inCyTim = format.format((Timestamp) sysDate);
						}
						
						shipOutEntity.setOutId(HdUtils.genUuid());
						shipOutEntity.setShipNo((String)obj[0]);
						shipOutEntity.setVcVinNo((String)obj[1]);
//						shipOutEntity.setVcSite((String)obj[2]);
						shipOutEntity.setVcSite(vcPortId);
						shipOutEntity.setVcCreateDate(inCyTim);
//						shipOutEntity.setVcException((String) obj[4]);
						
						String errorFlag = (String) obj[4];
						if(HdUtils.strIsNull(errorFlag)) {
							shipOutEntity.setVcException("0");
						} else {
							shipOutEntity.setVcException((String) obj[4]);
						}
						
						shipOutEntity.setVcShipId((String) obj[5]);
						shipOutEntity.setRecNam(Account);
						shipOutEntity.setRecTim(HdUtils.getDateTime());
						shipOutEntity.setUpdNam(Account);
						shipOutEntity.setUpdTim(HdUtils.getDateTime());
						JpaUtils.save(shipOutEntity);
						
					}
				}
			}
		}
		
		String shipOutShowJpql = "SELECT c FROM ShipOut c where c.shipNo=:shipNo";
		QueryParamLs shipOutShowParams = new QueryParamLs();
		shipOutShowParams.addParam("shipNo", shipNo);
		HdEzuiDatagridData result = JpaUtils.findByEz(shipOutShowJpql, shipOutShowParams, hdQuery);
//		List<ShipOut> shipOutShowList = JpaUtils.findAll(shipOutShowJpql, shipOutShowParams);
		List<ShipOut> shipOutListShow = result.getRows();{
			if(shipOutListShow.size() > 0) {
				for(ShipOut bean : shipOutListShow){
					String sql = "select a from CPortFt a where a.vcPortId =:vcPortId";
					QueryParamLs paramLs1 = new QueryParamLs();
					paramLs1.addParam("vcPortId", bean.getVcSite());
					List<CPortFt> list = JpaUtils.findAll(sql, paramLs1);
					if (list.size()>0){
						bean.setVcSite(list.get(0).getVcPortName());
					}
				}
			}
		}
		return result;
	}
	
	@Override
	public HdEzuiDatagridData findShipIn(HdQuery hdQuery) {
		// TODO Auto-generated method stub
				String shipNo = hdQuery.getStr("shipNo");
				String workTyp = hdQuery.getStr("workTyp");
				if(HdUtils.strNotNull(shipNo)) {
					String shipInJpql = "SELECT c FROM ShipIn c where c.shipNo=:shipNo";
					QueryParamLs shipInParams = new QueryParamLs();
					shipInParams.addParam("shipNo", shipNo);
					List<ShipIn> shipInList = JpaUtils.findAll(shipInJpql, shipInParams);
					if(shipInList.size() > 0) {
						JpaUtils.removeAll(shipInList);
					}
					
					String shipMasterJpql = "SELECT s FROM ShipMaster s where 1=1";
					List<ShipMaster> shipMasterList = JpaUtils.findAll(shipMasterJpql, null);
					if(shipMasterList.size() > 0) {
						JpaUtils.removeAll(shipMasterList);
					}
					
					if(HdUtils.strNotNull(shipNo)) {
						String shipMasterSql = "select t1.vc_ship_id as VCSHIPID, t1.vc_ship_name as VCSHIPNAM\r\n" + 
								"  from c_ship_data_ft t1, c_ship_data t2, ship t3\r\n" + 
								" where t2.ship_cod_id = t3.ship_cod_id\r\n" + 
								"   and t1.ship_cod_id = t2.ship_cod_id\r\n" + 
								"   and t3.ship_no = '"+ shipNo +"'";
						List<Map<String,Object>> list=JpaUtils.getEntityManager().createNativeQuery(shipMasterSql).setHint(QueryHints.RESULT_TYPE, ResultType.Map).getResultList();
						if(list.size() > 0) {
							ShipMaster shipMaster = new ShipMaster();
							shipMaster.setId(HdUtils.genUuid());
							shipMaster.setVcShipID(list.get(0).get("VCSHIPID").toString());
							shipMaster.setVcShipName(list.get(0).get("VCSHIPNAM").toString());
							JpaUtils.save(shipMaster);
						}
					}
					
					
					String vcPortId = "";
 					String portCode = "SELECT c FROM CPortFt c where c.vcPortName=:vcPortName";
 					QueryParamLs vcportParams = new QueryParamLs();
 					vcportParams.addParam("vcPortName", "天津港");
					List<CPortFt> vcPortFtList = JpaUtils.findAll(portCode, vcportParams);
					if(vcPortFtList.size() > 0) {
						String vcport = (String) vcPortFtList.get(0).getVcPortId();
						vcPortId = vcport;
					}
					
					String woorkCommandJpql = "SELECT w.shipNo,w.vinNo,p.discPortCod,w.inCyTim,w.vcexception,c.vcShipId FROM WorkCommand w, PortCar p, CShipDataFt c, Ship s "
							+ "where w.shipNo =:shipNo and w.workTyp =:workTyp and w.shipNo = s.shipNo and s.shipCodId =c.shipCodId and w.portCarNo = p.portCarNo and w.remarks = '手持录入' "//and (w.qzId <> '1' or w.qzId is null) 
							+ "  and p.brandCod in(SELECT c.brandCod FROM CBrand c where c.brandNam like '%丰田%')";
					QueryParamLs workCommandParams = new QueryParamLs();
					workCommandParams.addParam("shipNo", shipNo);
					workCommandParams.addParam("workTyp", workTyp);
					List<Object[]> workCommandList = JpaUtils.findAll(woorkCommandJpql, workCommandParams);
					if(workCommandList.size() > 0) {
						String Account = HdUtils.getCurUser().getAccount();
						ShipIn shipInEntity = new ShipIn();
						if(workTyp.equals("SO")) {
							for(Object[] obj : workCommandList) {
								String inCyTim = "";
								 String str = ObjectUtils.toString(obj[3]);
								 DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
								if(!str.equals("")) {
									inCyTim = format.format((Timestamp) obj[3]);
								} else {
									Date sysDate = HdUtils.getDateTime();
//									inCyTim = HdUtils.getDateStr((Timestamp) sysDate);
									inCyTim = format.format((Timestamp) sysDate);
								}
//								inCyTim = HdUtils.getDateStr((Timestamp) obj[3]);
								
								shipInEntity.setInId(HdUtils.genUuid());
								shipInEntity.setShipNo((String)obj[0]);
								shipInEntity.setVcVinNo((String)obj[1]);
//								shipInEntity.setVcSite((String)obj[2]);
								shipInEntity.setVcPort("");
//								shipInEntity.setVcStartSite((String)obj[2]);
								shipInEntity.setVcStartSite(vcPortId);
								shipInEntity.setVcCreateDate(inCyTim);
//								shipInEntity.setVcException((String) obj[4]);
								shipInEntity.setVcShipId((String) obj[5]);
								shipInEntity.setRecNam(Account);
								shipInEntity.setRecTim(HdUtils.getDateTime());
								shipInEntity.setUpdNam(Account);
								shipInEntity.setUpdTim(HdUtils.getDateTime());
								JpaUtils.save(shipInEntity);
								
							}
						}
					}
				}
				
				String shipInShowJpql = "SELECT c FROM ShipIn c where c.shipNo=:shipNo";
				QueryParamLs shipInShowParams = new QueryParamLs();
				shipInShowParams.addParam("shipNo", shipNo);
				HdEzuiDatagridData result = JpaUtils.findByEz(shipInShowJpql, shipInShowParams, hdQuery);
//				List<ShipOut> shipOutShowList = JpaUtils.findAll(shipOutShowJpql, shipOutShowParams);
				List<ShipIn> shipIntListShow = result.getRows();{
					if(shipIntListShow.size() > 0) {
						for(ShipIn bean : shipIntListShow){
							String sql = "select a from CPortFt a where a.vcPortId =:vcPortId";
							QueryParamLs paramLs1 = new QueryParamLs();
							paramLs1.addParam("vcPortId", bean.getVcStartSite());
							List<CPortFt> list = JpaUtils.findAll(sql, paramLs1);
							if (list.size()>0){
								bean.setVcPort(list.get(0).getVcPortName());
							}
						}
					}
				}
				return result;
	}

	@Override
	public HdMessageCode createDB(String shipNo, String workTyp) {
		String shipOutShowJpql = "SELECT c FROM ShipOut c,WorkCommand d where c.shipNo=:shipNo and c.vcVinNo = d.vinNo and (d.remarks = '手持录入' or d.qzId = '1')";
		QueryParamLs shipOutShowParams = new QueryParamLs();
		shipOutShowParams.addParam("shipNo", shipNo);
		List<ShipOut> shipOutList = JpaUtils.findAll(shipOutShowJpql, shipOutShowParams);
		
		
		String shipInShowJpql = "SELECT c FROM ShipIn c where c.shipNo=:shipNo";
		QueryParamLs shipInShowParams = new QueryParamLs();
		shipInShowParams.addParam("shipNo", shipNo);
		List<ShipIn> shipInList = JpaUtils.findAll(shipInShowJpql, shipInShowParams);
		for(ShipIn shipIn : shipInList) {
			String vcPortSql = "select max(t4.vc_port_id) as PORT\r\n" + 
					"  from port_car t1,\r\n" + 
					"       work_command t2,\r\n" + 
					"       c_port t3,\r\n" + 
					"       c_port_ft t4\r\n" + 
					" where t1.port_car_no = t2.port_car_no\r\n" + 
					"   and t3.port_cod = t1.tran_port_cod\r\n" + 
					"   and t4.port_id = t3.port_id   \r\n" + 
					"   and t2.work_typ = 'SO'\r\n" + 
					"   and t2.ship_no = '"+ shipNo +"'\r\n" + 
					"   and t1.vin_no = '"+ shipIn.getVcVinNo() +"'\r\n";
			List<Map<String,Object>> list=JpaUtils.getEntityManager().createNativeQuery(vcPortSql).setHint(QueryHints.RESULT_TYPE, ResultType.Map).getResultList();
//			List<Object[]> objList = JpaUtils.getEntityManager().createNativeQuery(vcPortSql).getResultList();
			if(list.size() > 0) {
				String vcPort = (String) list.get(0).get("PORT");
				if(HdUtils.strNotNull(vcPort)) {
					shipIn.setVcPort(vcPort);
				}
			}
		}
		
		
		
		//卸船
		if(workTyp.equals("SI")) {
			if(shipOutList.size() > 0) {
				DBUtil.setConnection(workTyp);
				String tableNam = "shipOut";
				String cNam = "net.huadong.tech.inter.entity.ShipOut";
				DBUtil.create(tableNam, cNam, workTyp);
				DBUtil.insert(tableNam, shipOutList, workTyp);
				DBUtil.endConnection();
			} else {
				throw new HdRunTimeException("没有卸船信息，请核实！");
			}
		}
		//装船
		if(workTyp.equals("SO")) {
			String smJpql = "SELECT s FROM ShipMaster s";
			List<ShipMaster> smList = JpaUtils.findAll(smJpql, null);
			if(smList.size() > 0) {
				ShipMaster shipMaster = smList.get(0);
				if(shipInList.size() > 0) {
					DBUtil.setConnection(workTyp);
					String tableNam = "shipIn";
					String cNam = "net.huadong.tech.inter.entity.ShipIn";
					DBUtil.create(tableNam, cNam, workTyp);
					DBUtil.insertShipIn(tableNam, shipInList, shipMaster, workTyp);
					DBUtil.endConnection();
				} else {
					throw new HdRunTimeException("没有装船信息，请核实！");
				}
			} else {
				throw new HdRunTimeException("没有ShipMaster信息，请核实！");
			}
			
		}
		return HdUtils.genMsg();
	}



	@Override
	public HdMessageCode removeshipInOutCheck(String checkIds) {
		// TODO Auto-generated method stub
		List<String> checkIdList = HdUtils.paraseStrs(checkIds);
		for (String checkId : checkIdList) {
			JpaUtils.remove(ShipInOutCheck.class, checkId);
		}
		return HdUtils.genMsg();
	}



	@Override
	public HdMessageCode checkShip(String shipNo) {
		//校验和丰田船对应关系
		String checkShipJpql = "select t3 from Ship t1, CShipData t2, CShipDataFt t3 where t1.shipNo=:shipNo and t1.shipCodId=t2.shipCodId and t3.shipCodId=t2.shipCodId";
		QueryParamLs checkShipParams = new QueryParamLs();
		checkShipParams.addParam("shipNo", shipNo);
		List<CShipDataFt> checkShipLists = JpaUtils.findAll(checkShipJpql, checkShipParams);
		if(checkShipLists.size() > 0) {
		} else {
			throw new HdRunTimeException("没有船舶对应关系，请核实！");
		}
		return HdUtils.genMsg();
	}
}
