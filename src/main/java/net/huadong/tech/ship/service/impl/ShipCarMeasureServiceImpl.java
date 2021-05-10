package net.huadong.tech.ship.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.eclipse.persistence.config.QueryHints;
import org.eclipse.persistence.config.ResultType;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.bean.HdRunTimeException;
import net.huadong.tech.base.entity.CCarTyp;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.tech.ship.entity.MNobillWorkCommand;
import net.huadong.tech.ship.entity.Ship;
import net.huadong.tech.ship.entity.ShipCarMeasure;
import net.huadong.tech.ship.entity.ShipDispatchLog;
import net.huadong.tech.ship.entity.VShipCarMeasure;
import net.huadong.tech.ship.service.ShipCarMeasureService;
import net.huadong.tech.shipbill.entity.ContractIeDoc;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;
import net.huadong.tech.util.HdUtils;
import net.huadong.tech.work.entity.VWorkCommand;
import net.huadong.tech.work.entity.WorkCommand;

/**
 *
 * @author 于如河
 * @date 2019-07-08
 */
@Component
public class ShipCarMeasureServiceImpl implements ShipCarMeasureService {

	@Override
	public HdEzuiDatagridData find(HdQuery hdQuery) {
		String jpql = "select a from VShipCarMeasure a where 1=1 ";
		QueryParamLs paramLs = new QueryParamLs();
		String shipNo = hdQuery.getStr("shipNo");
		String iEId = hdQuery.getStr("iEId");
		String consignCod = hdQuery.getStr("consignCod");
		if (HdUtils.strNotNull(shipNo)) {
			jpql += "and a.shipNo =:shipNo ";
			paramLs.addParam("shipNo", shipNo);
		}
		if (HdUtils.strNotNull(iEId)) {
			jpql += "and a.iEId =:iEId ";
			paramLs.addParam("iEId", iEId);
		}
		if (HdUtils.strNotNull(consignCod)) {
			jpql += "and a.consignCod =:consignCod ";
			paramLs.addParam("consignCod", consignCod);
		}
		HdEzuiDatagridData result = JpaUtils.findByEz(jpql, paramLs, hdQuery);
		return result;
	}
	@Override
	public HdEzuiDatagridData exportExcelCk(HdQuery hdQuery,String ids) {//增加的出口复尺单按照选择行进行excel导出的方法
		String iEId = hdQuery.getStr("iEId");
		String shipNo = hdQuery.getStr("shipNo");
		String sql = "select * from (select  rownum id_,id,ship_no,ship_nam,voyage,CAR_TYP_VERSION,car_Typ_Nam,OLD_CAR_SIZE,OLD_CAR_VOL,car_size,car_vol,car_num,car_vin,remark_txt from ("
			      + "select max(t.id) id, t.ship_no,max(t.ship_nam) ship_nam,max(t.voyage) voyage, max(t.CAR_TYP_VERSION) CAR_TYP_VERSION, max(t.car_Typ_Nam) car_Typ_Nam, "
                +"max(t.OLD_CAR_SIZE) OLD_CAR_SIZE,max(t.OLD_CAR_VOL) OLD_CAR_VOL,max(t.car_size) car_size,t.car_vol, count(t.id)||'辆' car_num,decode(count(t.id),'1',max(t.car_vin)) car_vin,"
                +"max(remark_txt) remark_txt,t.CAR_TYP from V_ship_car_measure t WHERE 1=1\n";
	    if (HdUtils.strNotNull(iEId)) {
			sql += "and t.i_e_id ='" + iEId + "' ";
		}
		if (HdUtils.strNotNull(shipNo)) {
			sql += "and t.ship_no ='" + shipNo + "' ";
		}
	    sql += "group by t.ship_no,  t.car_vol,t.CAR_TYP";
		sql +=") where 1=1) where 1=1";
		if(!"".equals(ids)&&ids!=null){//如果进行选中导出excel,只导出选中行的数据,此处根据所选数据的行号进行导出
			String[] ids2=ids.split(",");
			String endIds="";
		    for (int i = 0; i < ids2.length; i++)  
	        {
	            String ids3 = "'" + ids2[i] + "'";  
	            if (i < ids2.length-1)  
	            {
	            	ids3 += ",";
	            }
	            endIds += ids3;
	        }
			sql += " and id_ in(" + endIds + ")";
		   
		}
		List<Map<String, Object>> list = JpaUtils.getEntityManager().createNativeQuery(sql)
				.setHint(QueryHints.RESULT_TYPE, ResultType.Map).getResultList();
		HdEzuiDatagridData result = new HdEzuiDatagridData();
		result.setRows(list);
		result.setTotal(list.size());
		return result;
	}
	
	@Override
	public HdEzuiDatagridData findFcdck(HdQuery hdQuery) {
		String iEId = hdQuery.getStr("iEId");
		String shipNo = hdQuery.getStr("shipNo");
		String consignCod = hdQuery.getStr("consignCod");
		String sql = "select  rawtohex(sys_guid()) id_,id,ship_no,ship_nam,voyage,CAR_TYP_VERSION,car_Typ_Nam,OLD_CAR_SIZE,OLD_CAR_VOL,car_size,car_vol,car_num,car_vin,remark_txt from ("
				      + "select max(t.id) id, t.ship_no,max(t.ship_nam) ship_nam,max(t.voyage) voyage, max(t.CAR_TYP_VERSION) CAR_TYP_VERSION, max(t.car_Typ_Nam) car_Typ_Nam, "
                      +"max(t.OLD_CAR_SIZE) OLD_CAR_SIZE,max(t.OLD_CAR_VOL) OLD_CAR_VOL,max(t.car_size) car_size,t.car_vol, count(t.id)||'辆' car_num,decode(count(t.id),'1',max(t.car_vin)) car_vin,"
                      +"max(remark_txt) remark_txt,t.CAR_TYP from V_ship_car_measure t WHERE 1=1\n";
		if (HdUtils.strNotNull(iEId)) {
			sql += "and t.i_e_id ='" + iEId + "' ";
		}
		if (HdUtils.strNotNull(shipNo)) {
			sql += "and t.ship_no ='" + shipNo + "' ";
		}
		if (HdUtils.strNotNull(consignCod)) {
			sql += "and t.consign_cod ='" + consignCod + "' ";
		}
		sql += "group by t.ship_no,  t.car_vol,t.CAR_TYP";
		sql +=") where 1=1";
	    List<Map<String, Object>> list = JpaUtils.getEntityManager().createNativeQuery(sql)
				.setHint(QueryHints.RESULT_TYPE, ResultType.Map).getResultList();
		HdEzuiDatagridData result = new HdEzuiDatagridData();
		result.setRows(list);
		result.setTotal(list.size());
		return result;
		
	}
	/*@Override
	public HdEzuiDatagridData findFcd(HdQuery hdQuery) {
		String ieId = hdQuery.getStr("ieId");
		String shipNo = hdQuery.getStr("shipNo");
		String sql = "select max(t.id) id, t.ship_no,max(t.ship_nam) ship_nam,max(t.voyage) voyage, t.bill_no,"
				+ " max(t.car_Typ_Nam) car_Typ_Nam, decode(count(t.id),'1',max(t.car_vin)) car_vin, count(t.id)||'辆' car_num,max(t.car_size) car_size,\n"
				+ " t.car_vol, t.car_weight, max(remark_txt) remark_txt from V_ship_car_measure t where 1=1 \n";
		if (HdUtils.strNotNull(ieId)) {
			sql += "and t.i_e_id ='" + ieId + "' ";
		}
		if (HdUtils.strNotNull(shipNo)) {
			sql += "and t.ship_no ='" + shipNo + "' ";
		}
		sql += "group by t.ship_no, t.bill_no, t.car_typ, t.car_vol, t.car_weight";

		Query query = JpaUtils.getEntityManager().createNativeQuery(sql, VShipCarMeasure.class);
		List<VShipCarMeasure> list = query.getResultList();
		HdEzuiDatagridData result = new HdEzuiDatagridData();
		result.setRows(list);
		return result;
	}*/
	@Override
	public HdEzuiDatagridData findFcd(HdQuery hdQuery) {
		String iEId = hdQuery.getStr("iEId");
		String shipNo = hdQuery.getStr("shipNo");
		String sql = "select  rawtohex(sys_guid()) id_,id,ship_no,ship_nam,voyage,bill_no,car_Typ_Nam,car_vin,car_num,car_size,car_vol,car_weight,remark_txt from ("
				+ "select max(t.id) id, t.ship_no,max(t.ship_nam) ship_nam,max(t.voyage) voyage, t.bill_no,"
				+ " max(t.car_Typ_Nam) car_Typ_Nam, decode(count(t.id),'1',max(t.car_vin)) car_vin, count(t.id)||'辆' car_num,max(t.car_size) car_size,\n"
				+ " t.car_vol, t.car_weight, max(remark_txt) remark_txt from V_ship_car_measure t where 1=1\n";
		if (HdUtils.strNotNull(iEId)) {
			sql += "and t.i_e_id ='" + iEId + "' ";
		}
		if (HdUtils.strNotNull(shipNo)) {
			sql += "and t.ship_no ='" + shipNo + "' ";
		}
		
		sql += "group by t.ship_no, t.bill_no, t.car_typ, t.car_vol, t.car_weight";
		sql +=") where 1=1";
	    List<Map<String, Object>> list = JpaUtils.getEntityManager().createNativeQuery(sql)
				.setHint(QueryHints.RESULT_TYPE, ResultType.Map).getResultList();
		HdEzuiDatagridData result = new HdEzuiDatagridData();
		result.setRows(list);
		result.setTotal(list.size());
		return result;
      }
	@Override
	public HdEzuiDatagridData exportExcel(HdQuery hdQuery,String ids) {//增加的进口复尺单按照选择行进行excel导出的方法
		String iEId = hdQuery.getStr("iEId");
		String shipNo = hdQuery.getStr("shipNo");
		String sql = "select * from(select  rownum id_,id,ship_no,ship_nam,voyage,bill_no,car_Typ_Nam,car_vin,car_num,car_size,car_vol,car_weight,remark_txt from ("
				+ "select max(t.id) id, t.ship_no,max(t.ship_nam) ship_nam,max(t.voyage) voyage, t.bill_no,"
				+ " max(t.car_Typ_Nam) car_Typ_Nam, decode(count(t.id),'1',max(t.car_vin)) car_vin, count(t.id)||'辆' car_num,max(t.car_size) car_size,\n"
				+ " t.car_vol, t.car_weight, max(remark_txt) remark_txt from V_ship_car_measure t where 1=1\n";
		if (HdUtils.strNotNull(iEId)) {
			sql += "and t.i_e_id ='" + iEId + "' ";
		}
		if (HdUtils.strNotNull(shipNo)) {
			sql += "and t.ship_no ='" + shipNo + "' ";
		}
		
		sql += "group by t.ship_no, t.bill_no, t.car_typ, t.car_vol, t.car_weight";
		sql +=") where 1=1) where 1=1";
		if(!"".equals(ids)&&ids!=null){//如果进行选中导出excel,只导出选中行的数据,此处根据所选数据的行号进行导出
			String[] ids2=ids.split(",");
			String endIds="";
		    for (int i = 0; i < ids2.length; i++)  
	        {
	            String ids3 = "'" + ids2[i] + "'";  
	            if (i < ids2.length-1)  
	            {
	            	ids3 += ",";
	            }
	            endIds += ids3;
	        }
			sql += " and id_ in(" + endIds + ")";
		   
		}
		List<Map<String, Object>> list = JpaUtils.getEntityManager().createNativeQuery(sql)
				.setHint(QueryHints.RESULT_TYPE, ResultType.Map).getResultList();
		HdEzuiDatagridData result = new HdEzuiDatagridData();
		result.setRows(list);
		result.setTotal(list.size());
		return result;
      }

	@Override
	public ShipCarMeasure findone(String id) {
		ShipCarMeasure entity = JpaUtils.findById(ShipCarMeasure.class, id);
		return entity;
	}

	@Override
	public HdMessageCode saveone(ShipCarMeasure entity) {
		if (HdUtils.strNotNull(entity.getId())) {
			if (entity.getNewCarLength().compareTo(new BigDecimal("12")) == 1){
				WorkCommand work = JpaUtils.findById(WorkCommand.class, entity.getQueueId());
				if (work != null){
					if (!"1".equals(work.getLengthOverId())){
						work.setLengthOverId("1");
						work.setLength(entity.getNewCarLength());
						JpaUtils.update(work);
					}
				}
			}
			JpaUtils.update(entity);
		} else {
			entity.setId(HdUtils.genUuid());
			JpaUtils.save(entity);
		}
		return HdUtils.genMsg();
	}
	/*@Transactional
	public HdMessageCode savepl(ShipCarMeasure entity, String ids) {
		List<String> idList = HdUtils.paraseStrs(ids);
		for (String id : idList) {
			ShipCarMeasure bean = JpaUtils.findById(ShipCarMeasure.class, id);
			if (bean != null){
				if (entity.getNewCarLength() != null){
					bean.setNewCarLength(entity.getNewCarLength());
					if (entity.getNewCarLength().compareTo(new BigDecimal("12")) == 1){
						WorkCommand work = JpaUtils.findById(WorkCommand.class, bean.getQueueId());
						if (work != null){
							if (!"1".equals(work.getLengthOverId())){
								work.setLengthOverId("1");
								work.setLength(entity.getNewCarLength());
								JpaUtils.update(work);
							}
						}
					}
				}
				if (entity.getNewCarWidth() != null){
					bean.setNewCarWidth(entity.getNewCarWidth());
				}
				if (entity.getNewCarHighth() != null){
					bean.setNewCarHighth(entity.getNewCarHighth());
				}
				if (entity.getCarWeight() != null){
					bean.setCarWeight(entity.getCarWeight());
				}
				JpaUtils.update(bean);
			}
		}
		return HdUtils.genMsg();
	}*/

	@Transactional
	public HdMessageCode savepl(ShipCarMeasure entity, String ids) {
		String flag = panduan(entity,ids);
		List<String> idList = HdUtils.paraseStrs(ids);
		if("0".equals(flag)){
			//System.out.println("更新3个字段");
			for (String id : idList) {
				ShipCarMeasure bean = JpaUtils.findById(ShipCarMeasure.class, id);
				if (bean != null){
					if (entity.getNewCarLength() != null){
						bean.setNewCarLength(entity.getNewCarLength());
						if (entity.getNewCarLength().compareTo(new BigDecimal("12")) == 1){
							WorkCommand work = JpaUtils.findById(WorkCommand.class, bean.getQueueId());
							if (work != null){
								if (!"1".equals(work.getLengthOverId())){
									work.setLengthOverId("1");
									work.setLength(entity.getNewCarLength());
									JpaUtils.update(work);
								}
							}
						}
					}
					if (entity.getNewCarWidth() != null){
						bean.setNewCarWidth(entity.getNewCarWidth());
					}
					if (entity.getNewCarHighth() != null){
						bean.setNewCarHighth(entity.getNewCarHighth());
					}
	                JpaUtils.update(bean);
				}
			}
		}else if("1".equals(flag)){
			//System.out.println("更新7个字段");
			for (String id : idList) {
				ShipCarMeasure bean = JpaUtils.findById(ShipCarMeasure.class, id);
				if (bean != null){
					if (entity.getNewCarLength() != null){
						bean.setNewCarLength(entity.getNewCarLength());
						if (entity.getNewCarLength().compareTo(new BigDecimal("12")) == 1){
							WorkCommand work = JpaUtils.findById(WorkCommand.class, bean.getQueueId());
							if (work != null){
								if (!"1".equals(work.getLengthOverId())){
									work.setLengthOverId("1");
									work.setLength(entity.getNewCarLength());
									JpaUtils.update(work);
								}
							}
						}
					}
					if (entity.getNewCarWidth() != null){
						bean.setNewCarWidth(entity.getNewCarWidth());
					}
					if (entity.getNewCarHighth() != null){
						bean.setNewCarHighth(entity.getNewCarHighth());
					}
					if (entity.getOldCarLength() !=null){
						bean.setOldCarLength(entity.getOldCarLength());
					}
					if(entity.getOldCarWidth() !=null){
						bean.setOldCarWidth(entity.getOldCarWidth());
					}
					if(entity.getOldCarHighth() !=null){
						bean.setOldCarHighth(entity.getNewCarHighth());
					}
					if (entity.getCarWeight() != null){
						bean.setCarWeight(entity.getCarWeight());
					}
	                JpaUtils.update(bean);
				}
			}
	     }
		 return HdUtils.genMsg();
		
	}
	
	public String panduan(ShipCarMeasure entity, String ids) {
		String flag = "";
		List<String> idList = HdUtils.paraseStrs(ids);
        String id1=idList.get(0);
        ShipCarMeasure bean = JpaUtils.findById(ShipCarMeasure.class, id1);
        String shipNam=bean.getShipNam();
        if(shipNam==null){
        	shipNam="0";
        }
        String voyage=bean.getVoyage();
        if(voyage==null){
        	voyage="0";
        }
        String iEId=bean.getiEId();
        if(iEId==null){
        	iEId="0";
        }
        String billNo=bean.getBillNo();
        if(billNo==null){
        	billNo="0";
        }
        String carTyp=bean.getCarTyp();
        if(carTyp==null){
        	carTyp="0";
        }
        BigDecimal oldCarLength=bean.getOldCarLength();
        if(oldCarLength==null){
        	 oldCarLength = new BigDecimal(0);
        }
        BigDecimal oldCarWidth=bean.getOldCarWidth();
        if(oldCarWidth==null){
        	oldCarWidth= new BigDecimal(0);
        }
        BigDecimal oldCarHighth=bean.getOldCarHighth();
        if(oldCarHighth==null){
        	oldCarHighth= new BigDecimal(0);
        }
        BigDecimal newCarLength=bean.getNewCarLength();
        if(newCarLength==null){
        	newCarLength= new BigDecimal(0);
        }
        BigDecimal newCarWidth=bean.getNewCarWidth();
        if(newCarWidth==null){
        	newCarWidth= new BigDecimal(0);
        }
        BigDecimal newCarHighth=bean.getNewCarHighth();
        if(newCarHighth==null){
        	newCarHighth= new BigDecimal(0);
        }
        BigDecimal carWeight=bean.getCarWeight();
        if(carWeight==null){
        	carWeight= new BigDecimal(0);
        }
        String remarkTxt=bean.getRemarkTxt();
        if(remarkTxt==null){
        	remarkTxt="0";
        }
        for(int i=1;i<idList.size();i++){
        	//判断每个属性值是否相等
        	//不一样  flag = 0; break; 
        	//一样 flag = 1;
        	//return flag;
        	ShipCarMeasure remainBean = JpaUtils.findById(ShipCarMeasure.class, idList.get(i));
        	 if (remainBean != null){ 
        		    String remainshipNam=remainBean.getShipNam();
        		    if(remainshipNam==null){
        		    	remainshipNam="0";
        		    }
        	        String remainvoyage=remainBean.getVoyage();
        	        if(remainvoyage==null){
        	        	remainvoyage="0";
        	        }
        	        String remainiEId=remainBean.getiEId();
        	        if(remainiEId==null){
        	        	remainiEId="0";
        	        }
        	        String remainbillNo=remainBean.getBillNo();
        	        if(remainbillNo==null){
        	        	remainbillNo="0";
        	        }
        	        String remaincarTyp=remainBean.getCarTyp();
        	        if(remaincarTyp==null){
        	        	remaincarTyp="0";
        	        }
        	        BigDecimal remainoldCarLength=remainBean.getOldCarLength();
        	        if(remainoldCarLength==null){
        	        	remainoldCarLength=new BigDecimal(0);
        	        }
        	        BigDecimal remainoldCarWidth=remainBean.getOldCarWidth();
        	        if(remainoldCarWidth==null){
        	        	remainoldCarWidth=new BigDecimal(0);
        	        }
        	        BigDecimal remainoldCarHighth=remainBean.getOldCarHighth();
        	        if(remainoldCarHighth==null){
        	        	remainoldCarHighth=new BigDecimal(0);
        	        }
        	        BigDecimal remainnewCarLength=remainBean.getNewCarLength();
        	        if(remainnewCarLength==null){
        	        	remainnewCarLength=new BigDecimal(0);
        	        }
        	        BigDecimal remainnewCarWidth=remainBean.getNewCarWidth();
        	        if(remainnewCarWidth==null){
        	        	remainnewCarWidth=new BigDecimal(0);
        	        }
        	        BigDecimal remainnewCarHighth=remainBean.getNewCarHighth();
        	        if(remainnewCarHighth==null){
        	        	remainnewCarHighth=new BigDecimal(0);
        	        }
        	        BigDecimal remaincarWeight=remainBean.getCarWeight();
        	        if(remaincarWeight==null){
        	        	remaincarWeight=new BigDecimal(0);
        	        }
        	        String remainremarkTxt=remainBean.getRemarkTxt();
        	        if(remainremarkTxt==null){
        	        	remainremarkTxt="0";
        	        }
              if(remainshipNam.equals(shipNam)&&remainvoyage.equals(voyage)&&remainiEId.equals(iEId)&&remainbillNo.equals(billNo)
        			&&remaincarTyp.equals(carTyp)&&remainremarkTxt.equals(remarkTxt)){
        			if(remainoldCarLength.compareTo(oldCarLength)==0&&remainoldCarWidth.compareTo(oldCarWidth)==0&&remainoldCarHighth.compareTo(oldCarHighth)==0
        			&&remainnewCarLength.compareTo(newCarLength)==0&&remainnewCarWidth.compareTo(newCarWidth)==0&&remainnewCarHighth.compareTo(newCarHighth)==0&&remaincarWeight.compareTo(carWeight)==0){
        				//System.out.println("都一样的话 可以批量修改 ：原长、原宽、原高、新长、新宽、新高、重量");
        				flag="1";
        			}else{
        				//System.out.println("如果有一个不一样，只能批量修改 新长、新宽、新高");
        				flag="0";
        				break;
        			}
        			
                  }else{
                	  //System.out.println("如果有一个不一样，只能批量修改 新长、新宽、新高");
                	  flag="0";
                	  break;
                  }
				
			}
        }
	     return flag;
	}

	@Transactional
	public HdMessageCode updateAll(String queueIds) {
		List<String> queueIdList = HdUtils.paraseStrs(queueIds);
		for (String queueId : queueIdList) {
			WorkCommand bean = JpaUtils.findById(WorkCommand.class, queueId);
			if (bean == null) {
				throw new HdRunTimeException("理货记录不存在！");
			}
			String jpql = "select a from ShipCarMeasure a where a.shipNo=:shipNo and a.carVin=:carVin";
			QueryParamLs paramLs = new QueryParamLs();
			paramLs.addParam("shipNo", bean.getShipNo());
			paramLs.addParam("carVin", bean.getVinNo());
			List<ShipCarMeasure> list = JpaUtils.findAll(jpql, paramLs);
			if (list.size() > 0){
				throw new HdRunTimeException("该车已经复尺了！");
			}
			
			ShipCarMeasure data = new ShipCarMeasure();
			data.setShipNo(bean.getShipNo());
			if (HdUtils.strNotNull(bean.getShipNo())) {
				Ship ship = JpaUtils.findById(Ship.class, bean.getShipNo());
				data.setShipNam(ship.getcShipNam());
				if ("SI".equals(bean.getWorkTyp())) {
					data.setVoyage(ship.getIvoyage());
					data.setiEId("I");
				} else {
					data.setVoyage(ship.getEvoyage());
					data.setiEId("E");
				}
			}
			data.setBillNo(bean.getBillNo());
			data.setCarTyp(bean.getCarTyp());
			data.setCarVin(bean.getVinNo());
			data.setId(HdUtils.generateUUID());
			data.setQueueId(bean.getQueueId());
			if ("TI".equals(bean.getWorkTyp())){
				if (HdUtils.strNotNull(bean.getContractNo())){
					ContractIeDoc contractIeDoc = JpaUtils.findById(ContractIeDoc.class, bean.getContractNo());
					if (contractIeDoc != null){
						data.setConsignCod(contractIeDoc.getConsignCod());
					}
				}
			}
			JpaUtils.save(data);
		}
		return HdUtils.genMsg();
	}

	@Override
	public HdMessageCode save(HdEzuiSaveDatagridData<ShipCarMeasure> hdEzuiSaveDatagridData) {
		List<ShipCarMeasure> deleteList = hdEzuiSaveDatagridData.getDeletedRows();
		for (ShipCarMeasure bean : deleteList){
			if (bean.getNewCarLength() != null){
				if (bean.getNewCarLength().compareTo(new BigDecimal("12")) == 1 && HdUtils.strNotNull(bean.getQueueId())){
					WorkCommand work = JpaUtils.findById(WorkCommand.class, bean.getQueueId());
					if (work != null){
						work.setWidthOverId("");
						work.setLength(new BigDecimal("0"));
						JpaUtils.update(work);
					}
				}
			}
		
		}
		return JpaUtils.save(hdEzuiSaveDatagridData);
	}
}