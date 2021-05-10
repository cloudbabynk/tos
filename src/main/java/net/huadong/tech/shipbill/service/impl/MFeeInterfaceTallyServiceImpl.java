package net.huadong.tech.shipbill.service.impl;
import net.huadong.tech.shipbill.entity.ContractIeDoc;
import net.huadong.tech.shipbill.entity.MFeeInterfaceTally;
import net.huadong.tech.shipbill.entity.MFeeInterfaceTallyBak;
import net.huadong.tech.shipbill.service.MFeeInterfaceTallyService;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.eclipse.persistence.config.QueryHints;
import org.eclipse.persistence.config.ResultType;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.bean.HdRunTimeException;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.tech.ship.entity.Ship;
import net.huadong.tech.ship.entity.ShipWorkman;
import net.huadong.tech.util.HdUtils;
import net.huadong.tech.util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.common.json.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author 
 */
@Component
public class MFeeInterfaceTallyServiceImpl implements MFeeInterfaceTallyService {

  /*@Override
	public HdEzuiDatagridData find(HdQuery hdQuery) {       
      	String jpql = "select a from MFeeInterfaceTally a where 1=1 ";
      	QueryParamLs paramLs = new QueryParamLs();
      	String shipNo = hdQuery.getStr("shipNo");
      	if (HdUtils.strNotNull(shipNo)) {
			jpql += "and a.vesselvisitid =:vesselvisitid ";
			paramLs.addParam("vesselvisitid", shipNo);
		}
      	jpql += "order by a.manifestno asc,a.chargecargoname,a.chargecargotypeid";
      	HdEzuiDatagridData list = JpaUtils.findByEz(jpql, paramLs, hdQuery);
        return list;
	}*/

	
	@Override
	public HdEzuiDatagridData find(HdQuery hdQuery) {       
      	String jpql = "select new net.huadong.tech.shipbill.entity.MFeeInterfaceTally(a ,b.usefreightmac)\n"
      			+ " from MFeeInterfaceTally a left join TallySplit b on a.tallySplitId=b.id ";
      	QueryParamLs paramLs = new QueryParamLs();
      	String shipNo = hdQuery.getStr("shipNo");
      	if (HdUtils.strNotNull(shipNo)) {
			jpql += "where a.vesselvisitid =:vesselvisitid ";
			paramLs.addParam("vesselvisitid", shipNo);
		}
      	jpql += "order by a.manifestno asc,a.chargecargoname,a.chargecargotypeid";
      	HdEzuiDatagridData list = JpaUtils.findByEz(jpql, paramLs, hdQuery);
        return list;
	}

	@Override
	public MFeeInterfaceTally findone(String id) {		
        MFeeInterfaceTally mfeeinterfacetally= JpaUtils.findById( MFeeInterfaceTally.class, id);
        return mfeeinterfacetally;
	}

    @Override
	@Transactional
	public void removeAll(String ids) {
		List<String> idList = HdUtils.paraseStrs(ids);
		for (String id : idList) {
			JpaUtils.remove(MFeeInterfaceTally.class, id);
		}
	} 
	
	@Override
	@Transactional
	public void remove(String id) {
		JpaUtils.remove(MFeeInterfaceTally.class,id);
	}

	@Override
	@Transactional
	public HdMessageCode saveone(MFeeInterfaceTally mfeeinterfacetally) {		
		if(HdUtils.strIsNull(mfeeinterfacetally.getId())){
			mfeeinterfacetally.setId(HdUtils.genUuid());
			JpaUtils.save(mfeeinterfacetally);}
        else
			JpaUtils.update(mfeeinterfacetally);
		return HdUtils.genMsg(mfeeinterfacetally);
	
	}
	@Override
	@Transactional
	public HdMessageCode saveAll(HdEzuiSaveDatagridData<MFeeInterfaceTally> gridData) {
		return JpaUtils.save(gridData);
	} 
	
	@Transactional
	@Override
	public HdMessageCode updateData(String shipNo, String iEId, String transPortTypeId, String moveTyp) {
			HdMessageCode result1 = new HdMessageCode();
			String outinfo = "";
			List<Object> inParamLs = new ArrayList<Object>();
			inParamLs.add(shipNo);//过程参数值
	        inParamLs.add(iEId);//过程参数值
	        inParamLs.add(transPortTypeId);//过程参数值-航陆运
	        if(moveTyp.equals("0")){
	        	inParamLs.add("");//
	        }else{
	        	inParamLs.add(moveTyp);
	        }
	        inParamLs.add(HdUtils.getCurUser().getAccount());//过程参数值
	        inParamLs.add(HdUtils.getCurUser().getName());//过程参数值
			List<String> result = new ArrayList<String>();//过程返回值
			JpaUtils.executeOracleProcWithResult("p_fee_interface_tally", inParamLs, result, inParamLs.size()+1);
			outinfo = result.get(0);
			result1.setCode(outinfo);
			return HdUtils.genMsg();
	} 
	
	@Override
	public MFeeInterfaceTallyBak copyData(MFeeInterfaceTally data) {		
		MFeeInterfaceTallyBak bean = new MFeeInterfaceTallyBak();
		bean.setManifestTradeId(data.getManifesttradeid());
		bean.setCargoLength(data.getCargolength());
		bean.setComplexId(data.getComplexid());
		bean.setVesselName(data.getVesselname());
		bean.setManifestVolumn(data.getManifestvolumn());
		bean.setWorkCargoVolumn(data.getWorkcargovolumn());
		bean.setManifestNo(data.getManifestno());
		bean.setCargoOwnerId(data.getCargoownerid());
		bean.setStorageQuantity(data.getStoragequantity());
		bean.setVesselVisitId(data.getVesselvisitid());
		bean.setVoyage(data.getVoyage());
		bean.setChargeCargotypeId(data.getChargecargotypeid());
		bean.setMoveTypeId(data.getMovetypeid());
		bean.setChargeEndTime(data.getChargeendtime());
		bean.setVoyageFacilityInterfaceId(data.getVoyagefacilityinterfaceid());
		bean.setCargoMechanicalId(data.getCargomechanicalid());
		bean.setMafiNumber(data.getMafinumber());
		bean.setVesselAgentId(data.getVesselagentid());
		bean.setWorkCargoWeight(data.getWorkcargoweight());
		bean.setManifestWeight(data.getManifestweight());
		bean.setInvShiftId(data.getInvshiftid());
		bean.setStorageTypeId(data.getStoragetypeid());
		bean.setManifestPkgs(data.getManifestpkgs());
		bean.setWorkDate(data.getWorkdate());
		bean.setWorkerNumber(data.getWorkernumber());
		bean.setImpExpId(data.getImpexpid());
		bean.setOutShiftId(data.getOutshiftid());
		bean.setJpId(data.getJpid());
		bean.setToolBoxId(data.getToolboxid());
		bean.setTransportTypeId(data.getTransporttypeid());
		bean.setChargeTerm(data.getChargeterm());
		bean.setMoveToolId(data.getMovetoolid());
		bean.setFacilityId(data.getFacilityid());
		bean.setInvWarhouseDate(data.getInvwarhousedate());
		bean.setChargeCargoName(data.getChargecargoname());
		bean.setWorkCargoPkg(data.getWorkcargopkg());
		bean.setOrgManifestNo(data.getOrgmanifestno());
		bean.setYardTypeId(data.getYardtypeid());
		bean.setPortWokerId(data.getPortwokerid());
		bean.setSendName(data.getSendname());
		bean.setOceanOilId(data.getOceanoilid());
		bean.setInvoiceCargoName(data.getInvoicecargoname());
		bean.setCargoMoveWorkId(data.getCargomoveworkid());
		bean.setCargoAgentId(data.getCargoagentid());
		bean.setChargeBeginTime(data.getChargebegintime());
		bean.setGjId(data.getGjid());
		bean.setVoyageInterfaceId(data.getVoyageinterfaceid());
		bean.setWorkShiftId(data.getWorkshiftid());
		bean.setCargoChargeUnitId(data.getCargochargeunitid());
		bean.setDirectId(data.getDirectid());
		bean.setWorkerId(data.getWorkerid());
		bean.setPortMechanicalId(data.getPortmechanicalid());
		bean.setShipMechanicalId(data.getShipmechanicalid());
		bean.setCarrierId(data.getCarrierid());
		bean.setVesselNameEn(data.getVesselnameen());
		bean.setDeliveryTerm(data.getDeliveryterm());
		bean.setHazardId(data.getHazardid());
		bean.setOutWarhouseDate(data.getOutwarhousedate());
		bean.setBrandid(data.getBrandid());
		bean.setModelid(data.getModelid());
		bean.setSendTime(data.getSendtime());
		bean.setHolidayid(data.getHolidayid());
		bean.setNightid(data.getNightid());
        return bean;
	}

}

