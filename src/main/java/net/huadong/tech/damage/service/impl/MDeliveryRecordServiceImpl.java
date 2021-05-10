package net.huadong.tech.damage.service.impl;
import com.alibaba.dubbo.config.annotation.Service;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.bean.HdRunTimeException;
import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.entity.CArea;
import net.huadong.tech.base.entity.SParam;
import net.huadong.tech.base.service.CAreaService;
import net.huadong.tech.damage.entity.MDeliveryRecord;
import net.huadong.tech.damage.service.MDeliveryRecordService;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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
public class MDeliveryRecordServiceImpl implements MDeliveryRecordService {
	@Override
	public HdEzuiDatagridData find(HdQuery hdQuery) {
		// TODO Auto-generated method stub
		String jpql="select a from MDeliveryRecord a where 1=1 ";
		String workDte = hdQuery.getStr("workDte");
		String classRun = hdQuery.getStr("classRun");
		QueryParamLs paramLs = new QueryParamLs();
		if(HdUtils.strNotNull(workDte)){
			SimpleDateFormat sdf =   new SimpleDateFormat("yyyy-MM-dd" );
			Date date = null;
			try {
				date= (Date) sdf.parse(workDte);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			jpql += "and a.workDte =:date ";
			paramLs.addParam("date", date);
		}
		if(HdUtils.strNotNull(classRun)){
			jpql += "and a.classRun =:classRun ";
			paramLs.addParam("classRun", classRun);
		}
		jpql += "order by a.recTim desc";
		
		return JpaUtils.findByEz(jpql, paramLs , hdQuery);
	}

	@Override
	public HdMessageCode save(HdEzuiSaveDatagridData<MDeliveryRecord> hdEzuiSaveDatagridData) {
		// TODO Auto-generated method stub
	return JpaUtils.save(hdEzuiSaveDatagridData);
	}
	@Transactional
	public void removeAll(String deliveryids) {
		// TODO Auto-generated method stub
		List<String> deliveryidList = HdUtils.paraseStrs(deliveryids);
		for (String deliveryid : deliveryidList) {
			JpaUtils.remove(MDeliveryRecord.class, deliveryid);
		}	
	}

	@Override
	public MDeliveryRecord findone(String deliveryid) {
		// TODO Auto-generated method stub
		MDeliveryRecord mDeliveryRecord = JpaUtils.findById(MDeliveryRecord.class, deliveryid);
		return mDeliveryRecord;

	}

	@Override
	public HdMessageCode saveone(@RequestBody MDeliveryRecord mDeliveryRecord) {
		if(HdUtils.strNotNull(mDeliveryRecord.getDeliveryid())){
			JpaUtils.update(mDeliveryRecord);
		}else{
			String uuid=UUID.randomUUID().toString();
			MDeliveryRecord mdeliveryRecord = JpaUtils.findById(MDeliveryRecord.class, uuid);
			if(mdeliveryRecord!= null){
				throw new HdRunTimeException("该代码已存在，请重新输入！");// 主键已存在
			}else {
				mDeliveryRecord.setDeliveryid(uuid);
			JpaUtils.save(mDeliveryRecord);}
		}
		return HdUtils.genMsg();
	}
}

