package net.huadong.tech.gate.service.impl;
import com.alibaba.dubbo.config.annotation.Service;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.bean.HdRunTimeException;
import net.huadong.tech.base.entity.CBrand;
import net.huadong.tech.base.entity.CCarKind;
import net.huadong.tech.base.entity.CCarTyp;
import net.huadong.tech.base.entity.CClientCod;
import net.huadong.tech.base.entity.CFactory;
import net.huadong.tech.base.entity.CGate;
import net.huadong.tech.base.entity.CTruck;
import net.huadong.tech.gate.entity.GateTruck;
import net.huadong.tech.gate.entity.GateTruckContract;
import net.huadong.tech.gate.service.GateTruckContractService;
import net.huadong.tech.ship.entity.Ship;
import net.huadong.tech.shipbill.entity.ContractIeDoc;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.idev.hdmessagecode.HdMessageCode;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;
import net.huadong.tech.util.HdUtils;
import net.huadong.tech.work.entity.WorkQueue;

/**
 * @author 
 */
@Component
public class GateTruckContractServiceImpl implements GateTruckContractService {
	@Override
	public HdEzuiDatagridData find(HdQuery hdQuery) {
		String jpql="select a from GateTruckContract a, ContractIeDoc b where a.contractNo = b.contractNo ";
		String ingateId = hdQuery.getStr("ingateId");
		String type = hdQuery.getStr("Type");
		QueryParamLs paramLs = new QueryParamLs();
		if(HdUtils.strNotNull(ingateId)){
			jpql += "and a.ingateId =:ingateId ";
			paramLs.addParam("ingateId", ingateId);
		}else{
			jpql += "and a.ingateId =:ingateId ";
			paramLs.addParam("ingateId", "123456789###");
		}
		if(type.startsWith("N")){
			jpql += "and b.tradeId =:tradeId ";
			paramLs.addParam("tradeId", "1");
		}else if(type.startsWith("W")){
			jpql += "and b.tradeId =:tradeId ";
			paramLs.addParam("tradeId", "2");
		}
		if("JG".equals(type)){
			jpql += "and a.carryId =:carryId ";
			paramLs.addParam("carryId", "A");
		}else if("SG".equals(type)){
			jpql += "and a.carryId =:carryId ";
			paramLs.addParam("carryId", "T");
		}
		HdEzuiDatagridData result = JpaUtils.findByEz(jpql, paramLs, hdQuery);
		List<GateTruckContract> gateTruckContractList = result.getRows();
		for (GateTruckContract gateTruckContract : gateTruckContractList) {
			if(HdUtils.strNotNull(gateTruckContract.getContractNo())){
				ContractIeDoc contractIeDoc = JpaUtils.findById(ContractIeDoc.class, gateTruckContract.getContractNo());
				if(contractIeDoc != null){
					if(HdUtils.strNotNull(contractIeDoc.getShipNam())){
						gateTruckContract.setShipNam(contractIeDoc.getShipNam());
					}
					if(HdUtils.strNotNull(contractIeDoc.getFactoryCod())){
						gateTruckContract.setFactoryCod(contractIeDoc.getFactoryCod());
						CFactory bean = JpaUtils.findById(CFactory.class, contractIeDoc.getFactoryCod());
						gateTruckContract.setFactoryNam(bean.getFactoryNam());
					}
					if(HdUtils.strNotNull(contractIeDoc.getFlow())){
						gateTruckContract.setFlow(contractIeDoc.getFlow());
					}
					if(HdUtils.strNotNull(contractIeDoc.getTranPortCod())){
						gateTruckContract.setTranPortCod(contractIeDoc.getTranPortCod());
					}
					if(HdUtils.strNotNull(contractIeDoc.getCarTyp())){
						gateTruckContract.setCarTyp(contractIeDoc.getCarTyp());
					}
					if(HdUtils.strNotNull(contractIeDoc.getVoyage())){
						gateTruckContract.setVoyage(contractIeDoc.getVoyage());
					}
					if(HdUtils.strNotNull(contractIeDoc.getShipNo())){
						gateTruckContract.setShipNo(contractIeDoc.getShipNo());
					}
					if(HdUtils.strNotNull(contractIeDoc.getFlow())){
						gateTruckContract.setFlow(HdUtils.getSysCodeName("FLOW_AREA", contractIeDoc.getFlow()));
					}
					if(HdUtils.strNotNull(contractIeDoc.getBrand())){
						CBrand cBrand = JpaUtils.findById(CBrand.class, contractIeDoc.getBrand());
						if(cBrand != null){
							gateTruckContract.setBrandNam(cBrand.getBrandNam());
							gateTruckContract.setBrandCod(contractIeDoc.getBrand());
						}
					}
					if(HdUtils.strNotNull(contractIeDoc.getCarTyp())){
						CCarTyp cCarTyp = JpaUtils.findById(CCarTyp.class, contractIeDoc.getCarTyp());
						if(cCarTyp != null){
							gateTruckContract.setCarTypNam(cCarTyp.getCarTypNam());
						}
					}
					if(HdUtils.strNotNull(contractIeDoc.getCarKind())){
						CCarKind cCarKind = JpaUtils.findById(CCarKind.class, contractIeDoc.getCarKind());
						if(cCarKind != null){
							gateTruckContract.setCarKind(cCarKind.getCarKind());
							gateTruckContract.setCarKindNam(cCarKind.getCarKindNam());
						}
					}
					if(HdUtils.strNotNull(contractIeDoc.getConsignCod())){
						CClientCod cClientCod = JpaUtils.findById(CClientCod.class, contractIeDoc.getConsignCod());
						if(cClientCod != null){
							gateTruckContract.setConsignNam(cClientCod.getcClientNam());
						}
						
					}
					gateTruckContract.setDockCod(contractIeDoc.getDockCod());
				}
			}
		}
		return result;
	}

	@Override
	public HdMessageCode save(HdEzuiSaveDatagridData<GateTruckContract> hdEzuiSaveDatagridData,String singleId,String truckNo,String planNum,String inGatTim,String gateNo) {
		if("0".equals(singleId)){
			if(HdUtils.strNotNull(truckNo)){
				String jpql = "Select a from GateTruck a where a.truckNo =:truckNo";
				QueryParamLs paramLs = new QueryParamLs();
				paramLs.addParam("truckNo", truckNo);
				List<GateTruck> gateTruckList = JpaUtils.findAll(jpql, paramLs);
				if(gateTruckList.size()>0){
					throw new HdRunTimeException("拖车已经在港内,不能重复进闸!");
				}	
			}
		}
		CGate cGate = JpaUtils.findById(CGate.class, gateNo);
		String platNo = JpaUtils.findById(CTruck.class, truckNo).getPlatNo();
		List<GateTruckContract> gateTruckContractlist = hdEzuiSaveDatagridData.getInsertedRows();
		for(GateTruckContract gateTruckContract : gateTruckContractlist){
			ContractIeDoc contractIeDoc = JpaUtils.findById(ContractIeDoc.class, gateTruckContract.getContractNo());
			if(!contractIeDoc.getDockCod().equals(cGate.getDockCod())){
				throw new HdRunTimeException("委托作业码头与当前不符!");
			}
			if("1".equals(contractIeDoc.getContractTyp())){
				gateTruckContract.setCarryId("A");
				WorkQueue workQueue = new WorkQueue();
				workQueue.setWorkQueueNo(contractIeDoc.getContractNo()+"-"+gateTruckContract.getCarryId()+truckNo);
				workQueue.setWorkTyp("TI");
				workQueue.setContractNo(gateTruckContract.getContractNo());
				workQueue.setTruckNo(truckNo);
				workQueue.setRemarks("集港"+"-"+platNo+"-"+gateTruckContract.getContractNo());
				workQueue.setRecNam(HdUtils.getCurUser().getAccount());
				workQueue.setRecTim(HdUtils.getDateTime());
				JpaUtils.save(workQueue);
			}else if("2".equals(contractIeDoc.getContractTyp())){
				gateTruckContract.setCarryId("T");
				WorkQueue workQueue = new WorkQueue();
				workQueue.setWorkQueueNo(contractIeDoc.getContractNo()+"-"+gateTruckContract.getCarryId()+truckNo);
				workQueue.setWorkTyp("TO");
				workQueue.setContractNo(gateTruckContract.getContractNo());
				workQueue.setTruckNo(truckNo);
				workQueue.setRemarks("疏港"+"-"+platNo+"-"+gateTruckContract.getContractNo());
				workQueue.setRecNam(HdUtils.getCurUser().getAccount());
				workQueue.setRecTim(HdUtils.getDateTime());
				JpaUtils.save(workQueue);
			}
			BigDecimal num = new  BigDecimal("0");
			gateTruckContract.setWorkNum(num);
		}
		GateTruck gateTruck = new GateTruck();
		gateTruck.setSingleId(singleId);
		gateTruck.setTruckNo(truckNo);
		gateTruck.setPlatNo(platNo);
		gateTruck.setInGatNo(gateNo);
		gateTruck.setInRecNam(HdUtils.getCurUser().getAccount());
		gateTruck.setInGatTim(HdUtils.getDateTime());
		gateTruck.setDockCod(cGate.getDockCod());
		gateTruck.setFinishedId("0");
		JpaUtils.save(gateTruck);
		for(GateTruckContract gateTruckContract : gateTruckContractlist){
			gateTruckContract.setIngateId(gateTruck.getIngateId());
			gateTruckContract.setTruckNo(truckNo);
		}
		return JpaUtils.save(hdEzuiSaveDatagridData);
	}

	@Transactional
	public void removeAll(String ids) {
		List<String> idList = HdUtils.paraseStrs(ids);
		for (String id : idList) {
			JpaUtils.remove(GateTruckContract.class, id);
		}
	}
	
	@Override
	public GateTruckContract findone(String id) {
		GateTruckContract gateTruckContract = JpaUtils.findById(GateTruckContract.class, id);
		return gateTruckContract;

	}
	
	@Override
	public HdMessageCode saveone(@RequestBody GateTruckContract gateTruckContract) {
		if(gateTruckContract.getContractNo() != null){
			JpaUtils.update(gateTruckContract);
		}else{
			JpaUtils.save(gateTruckContract);
		}
		return HdUtils.genMsg();
	}
	
}

