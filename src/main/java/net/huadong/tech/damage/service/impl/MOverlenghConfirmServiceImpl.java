package net.huadong.tech.damage.service.impl;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.bean.HdRunTimeException;
import net.huadong.tech.base.entity.CCarTyp;
import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.damage.entity.MOverlenghConfirm;
import net.huadong.tech.damage.service.MOverlenghConfirmService;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.tech.shipbill.entity.ContractIeDoc;
import net.huadong.tech.shipbill.entity.PortCar;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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
public class MOverlenghConfirmServiceImpl implements MOverlenghConfirmService {
	@Override
	public HdEzuiDatagridData find(HdQuery hdQuery) {
		// TODO Auto-generated method stub
		String jpql="select a from MOverlenghConfirm a where 1=1 ";
		String carTyp = hdQuery.getStr("carTyp");
		String regDte = hdQuery.getStr("regDte");
		String shipNo = hdQuery.getStr("shipNo");
		QueryParamLs paramLs = new QueryParamLs();
		if(HdUtils.strNotNull(regDte)){
			SimpleDateFormat sdf =   new SimpleDateFormat("yyyy-MM-dd" );
			Date date = null;
			try {
				date= (Date) sdf.parse(regDte);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			jpql += "and a.regDte =:date ";
			paramLs.addParam("date", date);
		}
		if(HdUtils.strNotNull(carTyp)){
			jpql += "and a.carTyp =:carTyp ";
			paramLs.addParam("carTyp", carTyp);
		}
		if(HdUtils.strNotNull(shipNo)){
			jpql += " and a.shipNo =:shipNo ";
			paramLs.addParam("shipNo", shipNo);
		}
		jpql += "order by a.recTim desc";
		// return JpaUtils.findByEz(jpql, paramLs , hdQuery);
		HdEzuiDatagridData result=JpaUtils.findByEz(jpql, paramLs , hdQuery);
		List<MOverlenghConfirm> mOverlenghConfirmList = result.getRows();
		if(mOverlenghConfirmList.size()!=0){
		for (MOverlenghConfirm moverlenghconfirm : mOverlenghConfirmList) {
			if(HdUtils.strNotNull(moverlenghconfirm.getCarTyp())){
				CCarTyp ct=JpaUtils.findById(CCarTyp.class, moverlenghconfirm.getCarTyp());
				moverlenghconfirm.setCarTypNam(ct.getCarTypNam());
			}
			PortCar pc=JpaUtils.findById(PortCar.class,moverlenghconfirm.getPortCarNo());
			moverlenghconfirm.setCyPlac(pc.getCyPlac());
		}
		}
		return result;
	}

	@Override
	public HdMessageCode save(HdEzuiSaveDatagridData<MOverlenghConfirm> hdEzuiSaveDatagridData) {
		// TODO Auto-generated method stub
	return JpaUtils.save(hdEzuiSaveDatagridData);
	}
	@Transactional
	public void removeAll(String confirmids) {
		// TODO Auto-generated method stub
		List<String> confirmidList = HdUtils.paraseStrs(confirmids);
		for (String confirmid : confirmidList) {
			JpaUtils.remove(MOverlenghConfirm.class, confirmid);
		}	
	}

	@Override
	public MOverlenghConfirm findone(String confirmid) {
		// TODO Auto-generated method stub
		MOverlenghConfirm mOverlenghConfirm = JpaUtils.findById(MOverlenghConfirm.class, confirmid);
		return mOverlenghConfirm;

	}

	@Override
	public HdMessageCode saveone(@RequestBody MOverlenghConfirm mOverlenghConfirm) {
			String uuid=UUID.randomUUID().toString();
			MOverlenghConfirm moverlenghconfirm = JpaUtils.findById(MOverlenghConfirm.class, uuid);
			if(moverlenghconfirm!= null){
				throw new HdRunTimeException("该代码已存在，请重新输入！");// 主键已存在
			}else {
				mOverlenghConfirm.setConfirmid(uuid);
			JpaUtils.save(mOverlenghConfirm);
			}
		return HdUtils.genMsg();
	}
}

