package net.huadong.tech.ship.service.impl;
import com.alibaba.dubbo.config.annotation.Service;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.bean.HdRunTimeException;
import net.huadong.tech.base.entity.CBerth;
import net.huadong.tech.base.entity.CClientCod;
import net.huadong.tech.base.entity.CDamage;
import net.huadong.tech.base.entity.CPort;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.tech.ship.entity.CShipData;
import net.huadong.tech.ship.entity.DayNightAttention;
import net.huadong.tech.ship.entity.DayNightTrend;
import net.huadong.tech.ship.entity.Ship;
import net.huadong.tech.ship.entity.ShipTrend;
import net.huadong.tech.ship.service.DayNightAttentionService;
import net.huadong.idev.hdmessagecode.HdMessageCode;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiQueryParams;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;
import net.huadong.tech.util.HdUtils;

/**
 * @author 
 */
@Component
public class DayNightAttentionServiceImpl implements DayNightAttentionService {
	@Override
	public HdEzuiDatagridData find(HdQuery hdQuery) {
		String jpql="select a from DayNightPlan a where 1=1 ";
		String shipNo = hdQuery.getStr("shipNo");
		String days = hdQuery.getStr("days");
		QueryParamLs paramLs = new QueryParamLs();
		if(HdUtils.strNotNull(shipNo)){
			jpql += "and a.shipNo =:shipNo ";
			paramLs.addParam("shipNo", shipNo);
		}
		if(HdUtils.strNotNull(days)){
			jpql += "and a.days =:days ";
			paramLs.addParam("days", HdUtils.strToDate(days));
		}
		jpql += "order by a.recTim desc";
		return JpaUtils.findByEz(jpql, paramLs, hdQuery);
	}

	@Override
	public HdMessageCode save(HdEzuiSaveDatagridData<DayNightAttention> hdEzuiSaveDatagridData) {
		// TODO Auto-generated method stub
		return JpaUtils.save(hdEzuiSaveDatagridData);
	}

	@Transactional
	public void removeAll(String planIds) {
		List<String> planIdList = HdUtils.paraseStrs(planIds);
		for (String planId : planIdList) {
			JpaUtils.remove(CShipData.class, planId);
		}
	}
	
	@Override
	public DayNightAttention findone(String days) {
		String jpql = "select a from DayNightAttention a where a.days =:days";
		QueryParamLs paramLs = new QueryParamLs();
		paramLs.addParam("days", HdUtils.strToDate(days));
		List<DayNightAttention> dayNightAttentionList = JpaUtils.findAll(jpql, paramLs);
		DayNightAttention dayNightAttention = new DayNightAttention();
		if(dayNightAttentionList.size()>0){
			dayNightAttention = dayNightAttentionList.get(0);
		}
		return dayNightAttention;

	}
	
	
	@Override
	public DayNightAttention findAttention(String days) {
		String jpql = "select a from DayNightAttention a where a.days =:days";
		QueryParamLs paramLs = new QueryParamLs();
		paramLs.addParam("days", HdUtils.strToDate(days));
		List<DayNightAttention> dayNightAttentionList = JpaUtils.findAll(jpql, paramLs);
		DayNightAttention dayNightAttention = new DayNightAttention();
		if(dayNightAttentionList.size()>0){
			dayNightAttention = dayNightAttentionList.get(0);
			String jpql1 = "select a from DayNightTrend a where a.days =:days";
			QueryParamLs paramLs1 = new QueryParamLs();
			paramLs1.addParam("days", HdUtils.addDay(HdUtils.strToDate(days), 1));
            List<DayNightTrend> dayNightTrendList = JpaUtils.findAll(jpql1, paramLs1);
            String str = "";
            for(DayNightTrend dayNightTrend : dayNightTrendList){
            	ShipTrend shipTrend = JpaUtils.findById(ShipTrend.class, dayNightTrend.getShipTrendsId());
            	String berthName = "";
				CBerth cBerth = new CBerth();
            	if(HdUtils.strNotNull(shipTrend.getTrendsEndArea())){
					cBerth = JpaUtils.findById(CBerth.class, shipTrend.getTrendsEndArea());
					berthName = cBerth.getBerthNam();
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
				}
            	str += "\"" +dayNightTrend.getCshipnam() + "\"" + dayNightTrend.getJcgdt() + ";\n" ;
            }
            dayNightAttention.setWorkPlan(str);
		}
		return dayNightAttention;

	}
	
	@Override
	@Transactional
	public HdMessageCode saveone(@RequestBody DayNightAttention dayNightAttention) {
		if(HdUtils.strNotNull(dayNightAttention.getAttentionId())){
			JpaUtils.update(dayNightAttention);
		}else{
			dayNightAttention.setAttentionId(HdUtils.genUuid());
			JpaUtils.save(dayNightAttention);
		}
		return HdUtils.genMsg();
	}
	
	
	
	

}

