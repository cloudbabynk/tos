package net.huadong.tech.ship.service.impl;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.tech.privilege.entity.AuthUser;
import net.huadong.tech.ship.entity.ShipDispatchLog;
import net.huadong.tech.ship.service.ShipDispatchLogService;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;
import net.huadong.tech.util.HdUtils;
/**
 * @author
 */
@Component
public class ShipDispatchLogServiceImpl implements ShipDispatchLogService {
	@Override
	public HdEzuiDatagridData find(HdQuery hdQuery) {
		String jpql = "select a from ShipDispatchLog a where 1=1 ";
		QueryParamLs paramLs = new QueryParamLs();
		String dispatchDte = hdQuery.getStr("dispatchDte");
		if(HdUtils.strNotNull(dispatchDte)){
			jpql += "and a.dispatchDte =:dispatchDte ";
			paramLs.addParam("dispatchDte", HdUtils.strToDate(dispatchDte));
		}
		if(HdUtils.strNotNull(hdQuery.getStr("workRun"))){
			jpql += "and a.workRun =:workRun ";
			paramLs.addParam("workRun", hdQuery.getStr("workRun"));
		}
		jpql += "order by a.recTim desc";
		HdEzuiDatagridData result = JpaUtils.findByEz(jpql, paramLs, hdQuery);
		List<ShipDispatchLog> shipList = result.getRows();
		for (ShipDispatchLog ship : shipList) {
		}
		return result;
	}
	@Override
	public HdMessageCode save(HdEzuiSaveDatagridData<ShipDispatchLog> hdEzuiSaveDatagridData) {
		List<ShipDispatchLog> insertList = hdEzuiSaveDatagridData.getInsertedRows();
		for(ShipDispatchLog shipDispatchLog : insertList){
			shipDispatchLog.setDispatchId(HdUtils.genUuid());
		}
		return JpaUtils.save(hdEzuiSaveDatagridData);
	}
	@Transactional
	public void removeAll(String dispatchIds) {
		List<String> shipNoList = HdUtils.paraseStrs(dispatchIds);
		for (String shipNo : shipNoList) {
			JpaUtils.remove(ShipDispatchLog.class, shipNo);
		}
	}
	@Override
	public ShipDispatchLog findone(String shipCodId) {
		ShipDispatchLog shipDispatchLog = JpaUtils.findById(ShipDispatchLog.class, shipCodId);
		return shipDispatchLog;
	}
	@Override
	@Transactional
	public HdMessageCode saveone(@RequestBody ShipDispatchLog shipDispatchLog) {
	    HdMessageCode hdMessageCode=HdUtils.genMsg();
		if (HdUtils.strNotNull(shipDispatchLog.getDispatchId())) {
		    //保存：1、当前用户；2、管理员
		    ShipDispatchLog old=findone(shipDispatchLog.getDispatchId());
		    AuthUser user=HdUtils.getCurUser();
		    if(HdUtils.strNotNull(old.getJbNam2())&&!user.isAdmin()) {
		        hdMessageCode.setCode("-1");
		        hdMessageCode.setMessage("已交班不能修改");
		        return hdMessageCode;		        
		    }
		    if(user.isAdmin()||user.getAccount().equals(shipDispatchLog.getJbNam1())) {
		        JpaUtils.update(shipDispatchLog);
		    }else {
		        hdMessageCode.setCode("-1");
                hdMessageCode.setMessage("非管理员和录入人不能修改");
                return hdMessageCode;           
		    }
			
		} else {
			shipDispatchLog.setDispatchId(HdUtils.genUuid());
			shipDispatchLog.setJbNam1(HdUtils.getCurUser().getAccount());
			shipDispatchLog.setJbTim1(HdUtils.getDateTime());
			JpaUtils.save(shipDispatchLog);
		}
		return hdMessageCode;
	}
	
	@Transactional
    @Override
    public void jiaoban(String dispatchIds) {
        // TODO Auto-generated method stub
	    //交班更新并且已交班不更新
        String sql="update ShipDispatchLog a set a.jbNam2=:jbNam2,a.jbTim2=:jbTim2 where a.dispatchId in :dispatchIdLs and a.jbNam2 is null"; 
        QueryParamLs paramLs = new QueryParamLs();
        paramLs.addParam("jbNam2", HdUtils.getCurUser().getAccount());
        paramLs.addParam("jbTim2", HdUtils.getDateTime());
        paramLs.addParam("dispatchIdLs",HdUtils.paraseStrs(dispatchIds));
        JpaUtils.execUpdate(sql, paramLs);
    }
}
