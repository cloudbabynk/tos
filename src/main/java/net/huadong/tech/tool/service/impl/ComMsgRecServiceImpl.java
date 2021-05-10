package net.huadong.tech.tool.service.impl;
import com.alibaba.dubbo.config.annotation.Service;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.bean.HdRunTimeException;
import net.huadong.tech.com.entity.ComMsgRec;
import net.huadong.tech.tool.service.ComMsgRecService;
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

/**
 * @author 
 */
@Component
public class ComMsgRecServiceImpl implements ComMsgRecService {
	@Override
	public HdEzuiDatagridData find(HdQuery hdQuery) {
		String jpql="select a from ComMsgRec a where 1=1";
		QueryParamLs paramLs = new QueryParamLs();
		HdEzuiDatagridData data = JpaUtils.findByEz(jpql, paramLs , hdQuery);
		List<ComMsgRec> comMsgRecList = data.getRows();
//		for(ComMsgRec comMsgRec : comMsgRecList){
//			
//		}
		return data;
	}

	@Transactional
	public void removeAll(String recIds) {
		List<String> recIdList = HdUtils.paraseStrs(recIds);
		for (String recId : recIdList) {
			JpaUtils.remove(ComMsgRec.class, recId);
		}
	}
	
	public ComMsgRec findNum(){
		String sql = "select count(a) from ComMsgRec a where a.readId =:readId";
		QueryParamLs paramLs = new QueryParamLs();
		paramLs.addParam("readId", "0");
		Long num = JpaUtils.single(sql, paramLs);
		ComMsgRec bean = new ComMsgRec();
		bean.setUnReadNum(String.valueOf(num));
		return bean;
	}
	
	public void update(){
		String sql = "select a from ComMsgRec a where a.readId =:readId";
		QueryParamLs paramLs = new QueryParamLs();
		paramLs.addParam("readId", "0");
		List<ComMsgRec> list = JpaUtils.findAll(sql, paramLs);
		for(ComMsgRec bean : list){
			bean.setReadId("1");
			JpaUtils.update(bean);
		}
	}
	
}

