package net.huadong.tech.base.service.impl;
import com.alibaba.dubbo.config.annotation.Service;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.bean.HdRunTimeException;
import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.entity.CCyArea;
import net.huadong.tech.base.entity.CCyRow;
import net.huadong.tech.base.entity.CDock;
import net.huadong.tech.base.service.CCyRowService;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;

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
public class CCyRowServiceImpl implements CCyRowService {
	@Override
	public HdEzuiDatagridData find(HdQuery hdQuery) {
		// TODO Auto-generated method stub
		String jpql="select a from CCyRow a where 1=1 ";
		String cyAreaNo = hdQuery.getStr("cyAreaNo");
		QueryParamLs paramLs = new QueryParamLs();
		if(HdUtils.strNotNull(cyAreaNo)){
			jpql += "and a.cyAreaNo =:cyAreaNo ";
			paramLs.addParam("cyAreaNo", cyAreaNo);
		}
		jpql += "order by a.recTim desc";
		return JpaUtils.findByEz(jpql, paramLs , hdQuery);
	}

	@Override
	public HdMessageCode save(HdEzuiSaveDatagridData<CCyRow> hdEzuiSaveDatagridData) {
		// TODO Auto-generated method stub
	return JpaUtils.save(hdEzuiSaveDatagridData);
	}

	@Override
	public HdEzuiDatagridData findcdch(HdQuery hdQuery) {
		String jpql="select a from CCyRow a where 1=1 ";
		String cyAreaNo = hdQuery.getStr("cyAreaNo");
		QueryParamLs paramLs = new QueryParamLs();
		if(HdUtils.strNotNull(cyAreaNo)){
			jpql += "and a.cyAreaNo =:cyAreaNo ";
			paramLs.addParam("cyAreaNo", cyAreaNo);
		}
		jpql += "order by a.recTim desc";
		HdEzuiDatagridData result=JpaUtils.findByEz(jpql, paramLs , hdQuery);
		List<CCyRow> cCyRowList=result.getRows();
		if(cCyRowList.size()>0){
		for(CCyRow c:cCyRowList){
			String jpqlc="SELECT count(a) as num FROM CCyBay a, CCyRow b, CCyArea c WHERE a.lockId = '0'"
					+ " AND a.cyRowNo = b.cyRowNo and a.cyAreaNo=b.cyAreaNo AND b.cyAreaNo = c.cyAreaNo"
					+ " and a.cyAreaNo=:cyAreaNo and a.cyRowNo=:cyRowNo";
			QueryParamLs paramLs2 = new QueryParamLs();
			paramLs2.addParam("cyAreaNo",cyAreaNo);
			paramLs2.addParam("cyRowNo",c.getCyRowNo());
			List<Long> num=JpaUtils.findAll(jpqlc, paramLs2);
			if(num.size()>0){
			c.setUnlockNum(num.get(0));
			}
		
		}	
		}
		return result;
	}
}

