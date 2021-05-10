package net.huadong.tech.work.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.bean.HdRunTimeException;
import net.huadong.tech.base.entity.CBerth;
import net.huadong.tech.base.entity.CBrand;
import net.huadong.tech.base.entity.CCarKind;
import net.huadong.tech.base.entity.CCarTyp;
import net.huadong.tech.base.entity.CEmployee;
import net.huadong.tech.base.entity.CWorkClass;
import net.huadong.tech.base.entity.CWorkRun;
import net.huadong.tech.work.entity.ClassBill;
import net.huadong.tech.work.entity.WorkBill;
import net.huadong.tech.work.entity.WorkBillDriver;
import net.huadong.tech.work.entity.WorkBillDriverClass;
import net.huadong.tech.work.entity.WorkBillDriverSecond;
import net.huadong.tech.work.entity.WorkBillMach;
import net.huadong.tech.work.entity.WorkBillMachSecond;
import net.huadong.tech.work.entity.WorkBillMachine;
import net.huadong.tech.work.entity.WorkBillTallyer;
import net.huadong.tech.work.entity.WorkBillTallyerClass;
import net.huadong.tech.work.entity.WorkBillTallyerSecond;
import net.huadong.tech.work.entity.WorkCommand;
import net.huadong.tech.work.service.ClassBillService;
import net.huadong.tech.work.service.WorkBillService;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.tech.privilege.entity.AuthUser;
import net.huadong.tech.ship.entity.Ship;
import net.huadong.idev.hdmessagecode.HdMessageCode;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
public class ClassBillServiceImpl implements ClassBillService {
	@Override
	public HdEzuiDatagridData find(HdQuery hdQuery) {
		String jpql = "select a from ClassBill a where a.workNum != 0 ";
		String workRunCod = hdQuery.getStr("workRunCod");
		String shipNo = hdQuery.getStr("shipNo");
		String workDte = hdQuery.getStr("workDte");
		String billNo = hdQuery.getStr("billNo");
		String billTyp = hdQuery.getStr("billTyp");
		String driverClass = hdQuery.getStr("driverClass");
		QueryParamLs paramLs = new QueryParamLs();
		if (HdUtils.strNotNull(workDte)) {
			jpql += "and a.workDte =:workDte ";
			paramLs.addParam("workDte", HdUtils.strToDate(workDte));
		}
		if (HdUtils.strNotNull(workRunCod)) {
			jpql += "and a.workRunCod =:workRunCod ";
			paramLs.addParam("workRunCod", workRunCod);
		}
		if (HdUtils.strNotNull(shipNo)) {
			jpql += "and a.shipNo =:shipNo ";
			paramLs.addParam("shipNo", shipNo);
		}
		if (HdUtils.strNotNull(billTyp)) {
			jpql += "and a.billTyp =:billTyp ";
			paramLs.addParam("billTyp", billTyp);
		}
		if (HdUtils.strNotNull(driverClass)) {
			jpql += "and a.driverClass =:driverClass ";
			paramLs.addParam("driverClass", driverClass);
		}
		if (HdUtils.strNotNull(billNo)) {
			jpql += "and a.billNo like :billNo ";
			paramLs.addParam("billNo", "%" + billNo + "%");
		}
		jpql += "order by a.recTim desc";
		HdEzuiDatagridData result = JpaUtils.findByEz(jpql, paramLs, hdQuery);
		List<ClassBill> classBillList = result.getRows();
		for(ClassBill classBill : classBillList){
			if(HdUtils.strNotNull(classBill.getWorkRunCod())){
				CWorkRun cWorkRun = JpaUtils.findById(CWorkRun.class, classBill.getWorkRunCod());
				classBill.setWorkRunCodNam(cWorkRun.getWorkRunNam());
			}
			if(HdUtils.strNotNull(classBill.getBillTyp())){
				classBill.setBillTypNam(HdUtils.getSysCodeName("WORK_TYP", classBill.getBillTyp()));
			}
			if(HdUtils.strNotNull(classBill.getiEId())){
				classBill.setiEIdNam(HdUtils.getSysCodeName("I_E_ID", classBill.getiEId()));
			}
			if (HdUtils.strNotNull(classBill.getBrand())) {
				CBrand cbrand = JpaUtils.findById(CBrand.class, classBill.getBrand());
				if (cbrand != null) {
					classBill.setBrandNam(cbrand.getBrandNam());
				}
			}
			if (HdUtils.strNotNull(classBill.getCarTyp())) {
				CCarTyp ccartyp = JpaUtils.findById(CCarTyp.class, classBill.getCarTyp());
				if (ccartyp != null) {
					classBill.setCarTypNam(ccartyp.getCarTypNam());
				}
			}
			if (HdUtils.strNotNull(classBill.getCarKind())) {
				CCarKind carkind = JpaUtils.findById(CCarKind.class, classBill.getCarKind());
				if(carkind != null){
					classBill.setCarKindNam(carkind.getCarKindNam());
				}
			}
			if (HdUtils.strNotNull(classBill.getDriverClass())){
				CWorkClass cWorkClass = JpaUtils.findById(CWorkClass.class, classBill.getDriverClass());
				if(cWorkClass != null){
					classBill.setDriverClassNam(cWorkClass.getClassNam());
				}
			}
		}
		return result;
	}

	@Override
	public HdMessageCode findZx(String shipNo,String workDte,String workRunCod) {
		String jpqlAll = "select a from ClassBill a where a.shipNo =:shipNo and a.workDte =:workDte and a.workRunCod =:workRunCod";
		QueryParamLs paramLsAll = new QueryParamLs();
		paramLsAll.addParam("shipNo", shipNo);
		paramLsAll.addParam("workDte", HdUtils.strToDate(workDte));
		paramLsAll.addParam("workRunCod", workRunCod);
		List<ClassBill> classBillList = JpaUtils.findAll(jpqlAll, paramLsAll);
		if(classBillList.size()>0){
			throw new HdRunTimeException("该船已生成装卸工班票！");
		}
		List<WorkCommand> allList = new ArrayList();
		List<Object[]> objList = new ArrayList();
		List<Object[]> objList1 = new ArrayList();
		String jpql = "select a.carTyp ckd, a.billNo bno, count(a.queueId) cnt,a.nightId nid from WorkCommand a where 1=1 and a.workTyp='SI' ";
		QueryParamLs paramLs = new QueryParamLs();
		if (HdUtils.strNotNull(shipNo)) {
			jpql += "and a.shipNo=:shipNo ";
			paramLs.addParam("shipNo", shipNo);
		}
		jpql += "group by a.billNo,a.carTyp,cast(a.inCyTim as date),a.nightId order by cast(a.inCyTim as date) desc";
		objList = JpaUtils.findAll(jpql, paramLs);
		// 装船
		String jpql1 = "select a.carTyp ckd, a.billNo bno, count(a.queueId) cnt,a.nightId nid from WorkCommand a where 1=1 and a.workTyp='SO' ";
		QueryParamLs paramLs1 = new QueryParamLs();
		if (HdUtils.strNotNull(shipNo)) {
			jpql1 += "and a.shipNo=:shipNo ";
			paramLs1.addParam("shipNo", shipNo);
		}
		jpql1 += "group by a.billNo,a.carTyp,cast(a.outCyTim as date),a.nightId order by a.billNo asc, cast(a.outCyTim as date) desc";
		objList1 = JpaUtils.findAll(jpql1, paramLs1);
		for (Object[] obj : objList) {
			String jqpl2 = "select a from WorkCommand a where a.workTyp = 'SI'";
			QueryParamLs paramLs2 = new QueryParamLs();
			if (obj[1] != null) {
				jqpl2 += " and a.billNo =:billNo";
				paramLs2.addParam("billNo", obj[1]);
			}
			if (obj[0] != null) {
				jqpl2 += " and a.carTyp =:carTyp";
				paramLs2.addParam("carTyp", obj[0]);
			}
			if (obj[3] != null) {
				jqpl2 += " and a.nightId =:nightId";
				paramLs2.addParam("nightId", obj[3]);
			}
			List<WorkCommand> workCommandList = JpaUtils.findAll(jqpl2, paramLs2);
			if (workCommandList.size() > 0) {
				WorkCommand workCommand = workCommandList.get(0);
				workCommand.setRksl(String.valueOf(obj[2]));
				allList.add(workCommand);
			}
		}
		for (Object[] obj : objList1) {
			String jqpl2 = "select a from WorkCommand a where a.workTyp = 'SO'";
			QueryParamLs paramLs2 = new QueryParamLs();
			if (obj[1] != null) {
				jqpl2 += " and a.billNo =:billNo";
				paramLs2.addParam("billNo", obj[1]);
			}
			if (obj[0] != null) {
				jqpl2 += " and a.carTyp =:carTyp";
				paramLs2.addParam("carTyp", obj[0]);
			}
			if (obj[3] != null) {
				jqpl2 += " and a.nightId =:nightId";
				paramLs2.addParam("nightId", obj[3]);
			}
			List<WorkCommand> workCommandList = JpaUtils.findAll(jqpl2, paramLs2);
			if (workCommandList.size() > 0) {
				WorkCommand workCommand = workCommandList.get(0);
				workCommand.setRksl(String.valueOf(obj[2]));
				allList.add(workCommand);
			}
		}
		for (WorkCommand workCommand : allList) {
			ClassBill classBill = new ClassBill();
			classBill.setClassbillNo(HdUtils.genUuid().substring(3));;
			if ("SI".equals(workCommand.getWorkTyp())) {
				classBill.setWorkDte(workCommand.getInCyTim());
				classBill.setBillTyp("SI");
				classBill.setiEId("I");
				classBill.setTallierNam(workCommand.getInCyNam());
			} else if ("SO".equals(workCommand.getWorkTyp())) {
				classBill.setWorkDte(workCommand.getOutCyTim());
				classBill.setBillTyp("SO");
				classBill.setiEId("E");
				classBill.setTallierNam(workCommand.getOutCyNam());
			}
			if ("1".equals(workCommand.getNightId())) {
				classBill.setWorkRunCod("1");
			} else {
				classBill.setWorkRunCod("2");
			}
			classBill.setShipNo(shipNo);
			classBill.setBillNo(workCommand.getBillNo());
			classBill.setBrand(workCommand.getBrandCod());
			classBill.setCarKind(workCommand.getCarKind());
			classBill.setCarTyp(workCommand.getCarTyp());
			classBill.setCarLevel(workCommand.getCarLevel());
			classBill.setWorkNum(new BigDecimal(workCommand.getRksl()));
			JpaUtils.save(classBill);
		}
		return HdUtils.genMsg();
	}

	@Override
	public HdMessageCode save(HdEzuiSaveDatagridData<ClassBill> hdEzuiSaveDatagridData) {
		// TODO Auto-generated method stub
		return JpaUtils.save(hdEzuiSaveDatagridData);
	}

	@Transactional
	public void removeAll(String classbillNos) {
		List<String> classbillList = HdUtils.paraseStrs(classbillNos);
		for (String classbillNo : classbillList) {
			ClassBill classBill = JpaUtils.findById(ClassBill.class, classbillNo);
			if(classBill != null){
				String jpql = "select a from ClassBill a where a.billNo =:billNo and a.carTyp =:carTyp and a.shipNo =:shipNo and a.billTyp =:billTyp and a.workRunCod =:workRunCod and a.classbillNo !=:classbillNo";
				QueryParamLs paramLs = new QueryParamLs();
				paramLs.addParam("billNo", classBill.getBillNo());
				paramLs.addParam("carTyp", classBill.getCarTyp());
				paramLs.addParam("shipNo", classBill.getShipNo());
				paramLs.addParam("billTyp", classBill.getBillTyp());
				paramLs.addParam("workRunCod", classBill.getWorkRunCod());
				paramLs.addParam("classbillNo", classBill.getClassbillNo());
				List<ClassBill> classBillList = JpaUtils.findAll(jpql, paramLs);
				if(classBillList.size()>0){
					ClassBill bean = classBillList.get(0);
					bean.setWorkNum(bean.getWorkNum().add(classBill.getWorkNum()));
					JpaUtils.update(bean);
				}
			}
			JpaUtils.remove(ClassBill.class, classbillNo);
		}
	}

	@Override
	public ClassBill findone(String classbillNo) {
		ClassBill classBill = JpaUtils.findById(ClassBill.class, classbillNo);
		return classBill;

	}

	@Override
	public HdMessageCode saveone(@RequestBody ClassBill classBill) {
		String classbillNo = classBill.getClassbillNo();
		if(HdUtils.strIsNull(classbillNo)){
			classBill.setClassbillNo(HdUtils.genUuid().substring(3));
			JpaUtils.save(classBill);
		}else if("SM".equals(classBill.getBillTyp()) || "ST".equals(classBill.getBillTyp())){
			JpaUtils.update(classBill);
		}else{
			ClassBill bean = JpaUtils.findById(ClassBill.class, classBill.getClassbillNo());
			if(bean != null){
				if(bean.getWorkNum().compareTo(classBill.getWorkNum()) == -1){
					throw new HdRunTimeException("作业数量过大！");
				}else{
					bean.setClassbillNo(HdUtils.genUuid().substring(3));
					bean.setWorkNum(bean.getWorkNum().subtract(classBill.getWorkNum()));
					JpaUtils.save(bean);
					JpaUtils.update(classBill);
				}
			}
		}
		return HdUtils.genMsg();
	}

}
