package net.huadong.tech.base.service.impl;
import com.alibaba.dubbo.config.annotation.Service;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.bean.HdRunTimeException;
import net.huadong.tech.base.entity.MWorkMeet;
import net.huadong.tech.base.service.MWorkMeetService;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.idev.hdmessagecode.HdMessageCode;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;
import net.huadong.tech.util.HdUtils;

/**
 * @author 
 */
@Component
public class MWorkMeetServiceImpl implements MWorkMeetService {
	@Override
	public HdEzuiDatagridData find(HdQuery hdQuery) {
		String jpql="select a from MWorkMeet a where 1=1 ";
		String shipNo = hdQuery.getStr("shipNo");
		QueryParamLs paramLs = new QueryParamLs();
		if(HdUtils.strNotNull(shipNo)){
			jpql += "and a.shipNo =:shipNo ";
			paramLs.addParam("shipNo", shipNo);
		}
		jpql += "order by a.recTim desc";
		return JpaUtils.findByEz(jpql, paramLs , hdQuery);
	}

	@Override
	public HdMessageCode save(HdEzuiSaveDatagridData<MWorkMeet> hdEzuiSaveDatagridData) {
		// TODO Auto-generated method stub
		return JpaUtils.save(hdEzuiSaveDatagridData);
	}

	@Transactional
	public void removeAll(String ids) {
		List<String> idList = HdUtils.paraseStrs(ids);
		for (String id : idList) {
			JpaUtils.remove(MWorkMeet.class, id);
		}
	}
	
	@Override
	public MWorkMeet findone(String id) {
		MWorkMeet bean = JpaUtils.findById(MWorkMeet.class, id);
		return bean;

	}
	
	@Override
	public HdMessageCode saveone(@RequestBody MWorkMeet bean) {
		MWorkMeet mWorkMeet = JpaUtils.findById(MWorkMeet.class, bean.getId());
		if(mWorkMeet != null){
			JpaUtils.update(bean);
		}else{
			JpaUtils.save(bean);
		}
		return HdUtils.genMsg();
	}
	
}

