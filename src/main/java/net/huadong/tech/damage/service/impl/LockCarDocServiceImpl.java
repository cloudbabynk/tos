package net.huadong.tech.damage.service.impl;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.bean.HdRunTimeException;
import net.huadong.tech.base.entity.CBrand;
import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.damage.entity.LockCarDoc;
import net.huadong.tech.damage.service.LockCarDocService;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;

import java.math.BigDecimal;
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
public class LockCarDocServiceImpl implements LockCarDocService {
	@Override
	public HdEzuiDatagridData find(HdQuery hdQuery) {
		// TODO Auto-generated method stub
		String jpql="select a from LockCarDoc a where 1=1 ";
		String billNo = hdQuery.getStr("billNo");
		String vinNo = hdQuery.getStr("vinNo");
		String rfidCardNo = hdQuery.getStr("rfidCardNo");
		String portCarNo = hdQuery.getStr("portCarNo");
		QueryParamLs paramLs = new QueryParamLs();
		if(HdUtils.strNotNull(billNo)){
			jpql += "and a.billNo =:billNo ";
			paramLs.addParam("billNo", billNo);
		}
		if(HdUtils.strNotNull(portCarNo)){
			BigDecimal portcarno=new BigDecimal(portCarNo);
			jpql += "and a.portCarNo =:portcarno ";
			paramLs.addParam("portcarno", portcarno);
		}
		if(HdUtils.strNotNull(vinNo)){
			jpql += "and a.vinNo =:vinNo ";
			paramLs.addParam("vinNo", vinNo);
		}
		if(HdUtils.strNotNull(rfidCardNo)){
			jpql += "and a.rfidCardNo =:rfidCardNo ";
			paramLs.addParam("rfidCardNo", rfidCardNo);
		}
		jpql += "order by a.portCarNo desc";
		HdEzuiDatagridData results=JpaUtils.findByEz(jpql, paramLs , hdQuery);
		List<LockCarDoc> lockCarDocList=results.getRows();
		if(lockCarDocList.size()>0){
		for(LockCarDoc lcd:lockCarDocList){
		if(HdUtils.strNotNull(lcd.getBrandCod())){
			CBrand cb=JpaUtils.findById(CBrand.class,lcd.getBrandCod());
			lcd.setBrandNam(cb.getBrandNam());
		}	
		if(HdUtils.strNotNull(lcd.getLockReason())){
			lcd.setLockReasonNam(HdUtils.getSysCodeName("LOCK_REASON", lcd.getLockReason()));
		}
		if(HdUtils.strNotNull(lcd.getLockUnit())){
			lcd.setLockUnitNam(HdUtils.getSysCodeName("LOCK_UNIT", lcd.getLockUnit()));
		}
		}	
		}
		return results;
	}

	@Override
	public HdMessageCode save(HdEzuiSaveDatagridData<LockCarDoc> hdEzuiSaveDatagridData) {
		// TODO Auto-generated method stub
		return JpaUtils.save(hdEzuiSaveDatagridData);
	}
	@Transactional
	public void removeAll(String locakcarIds) {
		// TODO Auto-generated method stub
		List<String> locakcarIdList = HdUtils.paraseStrs(locakcarIds);
		for (String locakcarId : locakcarIdList) {
			JpaUtils.remove(LockCarDoc.class, locakcarId);
		}	
	}

	@Override
	public LockCarDoc findone(String locakcarId) {
		// TODO Auto-generated method stub
		LockCarDoc lockCarDoc = JpaUtils.findById(LockCarDoc.class, locakcarId);
		return lockCarDoc;

	}

	@Override
	public HdMessageCode saveone(@RequestBody LockCarDoc lockCarDoc) {
		if(HdUtils.strNotNull(lockCarDoc.getLockcarId())){
			JpaUtils.update(lockCarDoc);
			String jpql3="update PortCar a set a.lockId='0' where a.portCarNo=:portCarNo ";
			QueryParamLs paramLs3=new QueryParamLs();
			paramLs3.addParam("portCarNo",lockCarDoc.getPortCarNo());
			JpaUtils.execUpdate(jpql3, paramLs3);
		}else{
			BigDecimal portCarNo=lockCarDoc.getPortCarNo();
			QueryParamLs paramLs=new QueryParamLs();
			paramLs.addParam("portCarNo",portCarNo);
			List<LockCarDoc> lockCarList=JpaUtils.findAll("select a from LockCarDoc a where a.portCarNo=:portCarNo", paramLs);
		if(lockCarList.size()>0){
			throw new HdRunTimeException("车辆流水号已存在，不允许重复插入！");
		}else {
			   String  uuid=HdUtils.genUuid();
				lockCarDoc.setLockcarId(uuid);
				lockCarDoc.setLockRecNam(HdUtils.getCurUser().getAccount());
				lockCarDoc.setLockRecTim(HdUtils.getDateTime());
			JpaUtils.save(lockCarDoc);
			String jpql="update PortCar a set a.lockId='1' where a.portCarNo=:portCarNo ";
			QueryParamLs paramLs2=new QueryParamLs();
			paramLs2.addParam("portCarNo",lockCarDoc.getPortCarNo());
			JpaUtils.execUpdate(jpql, paramLs2);
			}
		}
		return HdUtils.genMsg();
	}
}

