package net.huadong.tech.ship.service.impl;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.persistence.config.QueryHints;
import org.eclipse.persistence.config.ResultType;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.entity.CBerth;
import net.huadong.tech.base.entity.CCable;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.tech.ship.entity.DayNightAttention;
import net.huadong.tech.ship.entity.DayNightTrend;
import net.huadong.tech.ship.entity.Ship;
import net.huadong.tech.ship.entity.ShipTrend;
import net.huadong.tech.ship.service.DayNightTrendService;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;
import net.huadong.tech.util.HdUtils;
import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;

/**
 * @author 
 */
@Component
public class DayNightTrendServiceImpl implements DayNightTrendService {
	@Override
	public HdEzuiDatagridData find(HdQuery hdQuery) {
		String jpql="select a from DayNightTrend a where 1=1 ";
		String days = hdQuery.getStr("days");
		String trendsId = hdQuery.getStr("trendsId");
		QueryParamLs paramLs = new QueryParamLs();
		if(HdUtils.strNotNull(days)){
			jpql += "and a.days =:days ";
			paramLs.addParam("days", HdUtils.strToDate(days));
		}
		if(HdUtils.strNotNull(trendsId)){
			jpql += "and a.trendsId =:trendsId ";
			paramLs.addParam("trendsId", trendsId);
		}
		jpql += "order by a.recTim desc";
		HdEzuiDatagridData result = JpaUtils.findByEz(jpql, paramLs, hdQuery);
		List<DayNightTrend> dayNightTrendList = result.getRows();
		for (DayNightTrend dayNightTrend : dayNightTrendList) {
			if(HdUtils.strNotNull(dayNightTrend.getShipTrendsId())){
				ShipTrend shipTrend = JpaUtils.findById(ShipTrend.class, dayNightTrend.getShipTrendsId());
				if (shipTrend == null){
					continue;
				}
				String berthName = "";
				CBerth cBerth = new CBerth();
				if(HdUtils.strNotNull(shipTrend.getTrendsEndArea())){
					cBerth = JpaUtils.findById(CBerth.class, shipTrend.getTrendsEndArea());
					berthName = cBerth.getBerthNam();
				}
				if(HdUtils.strNotNull(shipTrend.getBegCableNo()) && HdUtils.strNotNull(shipTrend.getEndCableNo()) && HdUtils.strNotNull(shipTrend.getTrendsEndArea())){
					CCable cCable = JpaUtils.findById(CCable.class, shipTrend.getBegCableNo());
					CCable cCable1 = JpaUtils.findById(CCable.class, shipTrend.getEndCableNo());
					if(cBerth != null && cCable1 != null && cCable != null){
						if(cCable.getCableNo().endsWith("#")){
							if(new BigDecimal(cCable.getCableSeq()).compareTo(new BigDecimal(cCable1.getCableSeq())) == -1){
								dayNightTrend.setBwlz(cBerth.getBerthNam()+"#"+cCable.getCableSeq()+"-"+cCable1.getCableSeq()+"桩");
							}else if(new BigDecimal(cCable.getCableSeq()).compareTo(new BigDecimal(cCable1.getCableSeq())) == 1){
								dayNightTrend.setBwlz(cBerth.getBerthNam()+"#"+cCable1.getCableSeq()+"-"+cCable.getCableSeq()+"桩");
							}
						}else{
							if(new BigDecimal(cCable.getCableNo()).compareTo(new BigDecimal(cCable1.getCableNo())) == -1){
								dayNightTrend.setBwlz(cBerth.getBerthNam()+"#"+cCable.getCableNo()+"-"+cCable1.getCableNo()+"桩");
							}else if(new BigDecimal(cCable.getCableNo()).compareTo(new BigDecimal(cCable1.getCableNo())) == 1){
								dayNightTrend.setBwlz(cBerth.getBerthNam()+"#"+cCable1.getCableNo()+"-"+cCable.getCableNo()+"桩");
							}
						}
					}
				}
				if(shipTrend.getTrendsPlanTim() != null){
					String datetime = shipTrend.getTrendsPlanTim().toString();
					String day = String.format("%02d", Integer.valueOf(datetime.substring(8, 10)));
					String hours = String.format("%02d", Integer.valueOf(datetime.substring(11, 13)));
					String minutes = String.format("%02d", Integer.valueOf(datetime.substring(14, 16)));
					if(ShipTrend.JZK.equals(shipTrend.getTrendsTermini()) || ShipTrend.YZK.equals(shipTrend.getTrendsTermini())){
						dayNightTrend.setKx("左靠");
						if(HdUtils.strNotNull(berthName)){
							if(ShipTrend.JZK.equals(shipTrend.getTrendsTermini())){
								dayNightTrend.setJcgdt(hours+minutes+"/"+day+"进"+berthName);
							}else{
								dayNightTrend.setJcgdt(hours+minutes+"/"+day+"移"+berthName);
							}
						}
					}else if(ShipTrend.JYK.equals(shipTrend.getTrendsTermini()) || ShipTrend.YYK.equals(shipTrend.getTrendsTermini())){
						dayNightTrend.setKx("右靠");
						if(HdUtils.strNotNull(berthName)){
							if(ShipTrend.JYK.equals(shipTrend.getTrendsTermini())){
								dayNightTrend.setJcgdt(hours+minutes+"/"+day+"进"+berthName);
							}else{
								dayNightTrend.setJcgdt(hours+minutes+"/"+day+"移"+berthName);
							}
							
						}
					}else if(ShipTrend.Y.equals(shipTrend.getTrendsTermini()) || ShipTrend.J.equals(shipTrend.getTrendsTermini())){
						if(HdUtils.strNotNull(berthName)){
							dayNightTrend.setJcgdt(hours+minutes+"/"+day+HdUtils.getSysCodeName("DX_ID", shipTrend.getTrendsTermini())+berthName);
						}
					}else if(ShipTrend.K.equals(shipTrend.getTrendsTermini())){
						dayNightTrend.setJcgdt(hours+minutes+"/"+day+HdUtils.getSysCodeName("DX_ID", shipTrend.getTrendsTermini())+"船");
					}else {
						dayNightTrend.setJcgdt(hours+minutes+"/"+day+HdUtils.getSysCodeName("DX_ID", shipTrend.getTrendsTermini()));
					}
				}
				if(HdUtils.strNotNull(shipTrend.getShipNo())){
					Ship ship = JpaUtils.findById(Ship.class, shipTrend.getShipNo());
					dayNightTrend.setCshipnam(ship.getcShipNam());
					dayNightTrend.setHc(ship.getIvoyage()+"/"+ship.getEvoyage());
				}
			}
		}
		return result;
	}

	@Override
	public HdMessageCode save(HdEzuiSaveDatagridData<DayNightTrend> hdEzuiSaveDatagridData,String days) {
		// TODO Auto-generated method stub
		List<DayNightTrend> insertList = hdEzuiSaveDatagridData.getInsertedRows();
		for(DayNightTrend dayNightTrend : insertList){
			dayNightTrend.setPlanId(HdUtils.genUuid());
			dayNightTrend.setDays(HdUtils.strToDate(days));
			if(StringUtils.isNotBlank(dayNightTrend.getJcgdt())&&dayNightTrend.getJcgdt().endsWith("船")){
				String jpql = "select a from DayNightAttention a where a.days =:days";
				QueryParamLs paramLs = new QueryParamLs();
				paramLs.addParam("days", dayNightTrend.getDays());
				List<DayNightAttention> dayNightAttentionList = JpaUtils.findAll(jpql, paramLs);
				if(dayNightAttentionList.size()>0){
					DayNightAttention dayNightAttention = dayNightAttentionList.get(0);
					dayNightAttention.setWorkPlan(dayNightAttention.getWorkPlan()+" "+dayNightTrend.getJcgdt());
					JpaUtils.update(dayNightAttention);
				}
			}
		}
		return JpaUtils.save(hdEzuiSaveDatagridData);
	}

	@Transactional
	public void removeAll(String planIds) {
		List<String> planIdList = HdUtils.paraseStrs(planIds);
		for (String planId : planIdList) {
			JpaUtils.remove(DayNightTrend.class, planId);
		}
	}
	
	@Override
	public DayNightTrend findone(String planId) {
		DayNightTrend dayNightTrend = JpaUtils.findById(DayNightTrend.class, planId);
		return dayNightTrend;

	}
	
	
	@Override
	public DayNightTrend findWorkNum(String days, String shipNo) {
		String jpql="select a from DayNightTrend a, ShipTrend b where 1=1 and a.shipTrendsId = b.shipTrendsId and b.trendsTermini in ('1','11','12') ";
		QueryParamLs paramLs = new QueryParamLs();
		if(HdUtils.strNotNull(days)){
			jpql += "and a.days =:days ";
			paramLs.addParam("days", HdUtils.strToDate(days));
		}
		if(HdUtils.strNotNull(shipNo)){
			jpql += "and b.shipNo =:shipNo ";
			paramLs.addParam("shipNo", shipNo);
		}
		List<DayNightTrend> dayNightTrendList = JpaUtils.findAll(jpql, paramLs);
		DayNightTrend dayNightTrend = new DayNightTrend();
		if(dayNightTrendList.size()>0){
			dayNightTrend = dayNightTrendList.get(0);
		}
		return dayNightTrend;
	}
	
	@Override
	@Transactional
	public HdMessageCode saveone(@RequestBody DayNightTrend dayNightTrend) {
		HdMessageCode hdMessageCode =new HdMessageCode();
		try {
			if (HdUtils.strNotNull(dayNightTrend.getPlanId())) {
				JpaUtils.update(dayNightTrend);
			} else {

				JpaUtils.save(dayNightTrend);
			}
			return HdUtils.genMsg();
		}catch (Exception e){
			hdMessageCode.setMessage(e.getMessage());
			hdMessageCode.setCode("-1");
			return  hdMessageCode;
		}
	}
	
	private boolean isNOtExist(String ivoyage,String evoyage) {
		String jpql = "select a from Ship a where 1=1 ";
		QueryParamLs paramLs = new QueryParamLs();
		if(HdUtils.strNotNull(ivoyage)){
			jpql += "and a.ivoyage =:ivoyage";
			paramLs.addParam("ivoyage", ivoyage);
		}
		if(HdUtils.strNotNull(evoyage)){
			jpql += "and a.evoyage =:evoyage";
			paramLs.addParam("evoyage", evoyage);
		}
		List<Ship> ShipList = JpaUtils.findAll(jpql, paramLs);
		if (ShipList.size() > 0) {
			return true;
		}
		return false;
	}
	
	private boolean isExist(String shipCodId) {
		String jpql = "select a from Ship a where a.shipCodId =:shipCodId ";
		QueryParamLs paramLs = new QueryParamLs();
		paramLs.addParam("shipCodId", shipCodId);
		List<Ship> ShipList = JpaUtils.findAll(jpql, paramLs);
		if (ShipList.size() > 0) {
			return true;
		}
		return false;
	}

	@Override
	public Map<String, Object> getDockCodByShipNo(String shipNo) {
		
		String sql = "select s.DOCK_COD from ship s where s.ship_no = '"+shipNo+"'";
		
		List<Map<String,String>> result =
				JpaUtils.getEntityManager().createNativeQuery(sql).setHint(QueryHints.RESULT_TYPE, ResultType.Map).getResultList();
		
		Map<String,Object> map = new HashMap<String,Object>();
		
		if(null!=result&& result.size()>0){
			map.put("dockCod", result.get(0).get("DOCK_COD"));
		}else{
			map.put("dockCod", "");
		}
		
		return map;
	}
	
	

}

