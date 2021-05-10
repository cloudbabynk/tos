package net.huadong.tech.work.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONObject;

import net.huadong.tech.Interface.entity.VGroupCorpShip;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.bean.HdRunTimeException;
import net.huadong.tech.base.entity.CBerth;
import net.huadong.tech.base.entity.CEmployee;
import net.huadong.tech.base.entity.CWorkRun;
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
import net.huadong.tech.work.service.WorkBillService;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.tech.privilege.entity.AuthUser;
import net.huadong.tech.ship.entity.DayNightPlan;
import net.huadong.tech.ship.entity.Ship;
import net.huadong.idev.hdmessagecode.HdMessageCode;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
public class WorkBillServiceImpl implements WorkBillService {
	@Value("${tjgjt.service.ip}")
	private String tjgjtServiceIp;

	@Override
	public HdEzuiDatagridData find(HdQuery hdQuery) {
		String jpql = "select a from WorkBill a where 1=1 ";
		String workRunCod = hdQuery.getStr("workRunCod");
		String workDte = hdQuery.getStr("workDte");
		QueryParamLs paramLs = new QueryParamLs();
		if (HdUtils.strNotNull(workDte)) {
			jpql += "and a.workDte =:workDte ";
			paramLs.addParam("workDte", HdUtils.strToDate(workDte));
		}
		if (HdUtils.strNotNull(workRunCod)) {
			jpql += "and a.workRunCod =:workRunCod ";
			paramLs.addParam("workRunCod", workRunCod);
		}
		jpql += "order by a.recTim desc";
		HdEzuiDatagridData result = JpaUtils.findByEz(jpql, paramLs, hdQuery);
		List<WorkBill> workBillList = result.getRows();
		for (WorkBill workBill : workBillList) {
			CWorkRun cWorkRun = JpaUtils.findById(CWorkRun.class, workBill.getWorkRunCod());
			workBill.setWorkRunCodNam(cWorkRun.getWorkRunNam());
		}
		return result;
	}

	@Override
	public HdMessageCode save(HdEzuiSaveDatagridData<WorkBill> hdEzuiSaveDatagridData) {
		// TODO Auto-generated method stub
		return JpaUtils.save(hdEzuiSaveDatagridData);
	}

	@Override
	public void generate(String workbillNo) {
		WorkBill workBill = JpaUtils.findById(WorkBill.class, workbillNo);
		if (workBill != null) {
			String workTyp = workBill.getWorkTyp();
			Timestamp begTim = workBill.getBegTim();
			Timestamp endTim = workBill.getEndTim();
			BigDecimal count1 = null;
			BigDecimal count2 = null;
			if ("SI".equals(workTyp)) {
				// 生成司机作业票
				String jpql = "select a from WorkBillDriverSecond a where a.workbillNo =:workbillNo";
				QueryParamLs paramLs = new QueryParamLs();
				paramLs.addParam("workbillNo", workbillNo);
				List<WorkBillDriverSecond> workBillDriverSecondList = JpaUtils.findAll(jpql, paramLs);
				for (WorkBillDriverSecond workBillDriverSecond : workBillDriverSecondList) {
					String jpql1 = "select count(a) from WorkCommand a where  a.driverCod =:driverCod and "
							+ "a.inCyTim >=:begTim and a.inCyTim <=:endTim and a.workTyp =:workTyp and a.shipNo=:shipNo";
					QueryParamLs paramLs1 = new QueryParamLs();
					paramLs1.addParam("driverCod", workBillDriverSecond.getDriverCod());
					paramLs1.addParam("begTim", begTim);
					paramLs1.addParam("endTim", endTim);
					paramLs1.addParam("workTyp", "SI");
					paramLs1.addParam("shipNo", workBill.getShipNo());
					List<BigDecimal> countList = JpaUtils.findAll(jpql1, paramLs1);
					if (countList.get(0) != null) {
						count1 = countList.get(0);
					}
					String jpql2 = "select count(a) from WorkCommandBak a where  a.driverCod =:driverCod and "
							+ "a.inCyTim >=:begTim and a.inCyTim <=:endTim and a.workTyp =:workTyp and a.shipNo=:shipNo";
					List<BigDecimal> count2List = JpaUtils.findAll(jpql2, paramLs1);
					if (count2List.get(0) != null) {
						count2 = countList.get(0);
					}
					WorkBillDriver workBillDriver = new WorkBillDriver();
					workBillDriver.setWorkbillNo(workbillNo);
					workBillDriver.setClassCod(workBillDriverSecond.getClassCod());
					workBillDriver.setDriverCod(workBillDriverSecond.getDriverCod());
					workBillDriver.setWorkNum(count1.add(count2));
					JpaUtils.save(workBillDriver);
				}
				// 生成理货员作业票
				String jpql2 = "select a from WorkBillTallyerSecond a where a.workbillNo =:workbillNo";
				QueryParamLs paramLs2 = new QueryParamLs();
				paramLs.addParam("workbillNo", workbillNo);
				List<WorkBillTallyerSecond> workBillTallyerSecondList = JpaUtils.findAll(jpql, paramLs);
				for (WorkBillTallyerSecond workBillTallyerSecond : workBillTallyerSecondList) {
					String jpql1 = "select count(a) from WorkCommand a where  a.inCyNam =:inCyNam and "
							+ "a.inCyTim >=:begTim and a.inCyTim <=:endTim and a.workTyp =:workTyp and a.shipNo=:shipNo";
					QueryParamLs paramLs1 = new QueryParamLs();
					paramLs1.addParam("inCyNam", workBillTallyerSecond.getTallyCod());
					paramLs1.addParam("begTim", begTim);
					paramLs1.addParam("endTim", endTim);
					paramLs1.addParam("workTyp", "SI");
					paramLs1.addParam("shipNo", workBill.getShipNo());
					List<BigDecimal> countList = JpaUtils.findAll(jpql1, paramLs1);
					if (countList.get(0) != null) {
						count1 = countList.get(0);
					}
					String jpql3 = "select count(a) from WorkCommandBak a where  a.inCyNam =:inCyNam and "
							+ "a.inCyTim >=:begTim and a.inCyTim <=:endTim and a.workTyp =:workTyp and a.shipNo=:shipNo";
					List<BigDecimal> count2List = JpaUtils.findAll(jpql2, paramLs1);
					if (count2List.get(0) != null) {
						count2 = countList.get(0);
					}
					WorkBillTallyer workBillTallyer = new WorkBillTallyer();
					workBillTallyer.setWorkbillNo(workbillNo);
					workBillTallyer.setClassCod(workBillTallyerSecond.getClassCod());
					workBillTallyer.setTallyCod(workBillTallyerSecond.getTallyCod());
					workBillTallyer.setWorkNum(count1.add(count2));
					JpaUtils.save(workBillTallyer);
				}
				// 生成机械作业票
				String jpql3 = "select a from WorkBillMachSecond a where a.workbillNo =:workbillNo";
				QueryParamLs paramLs3 = new QueryParamLs();
				paramLs.addParam("workbillNo", workbillNo);
				List<WorkBillMachSecond> workBillMachSecondList = JpaUtils.findAll(jpql, paramLs);
				for (WorkBillMachSecond workBillMachSecond : workBillMachSecondList) {
					String jpql1 = "select count(a) from WorkCommand a where  a.driverCod =:driverCod and "
							+ "a.inCyTim >=:begTim and a.inCyTim <=:endTim and a.workTyp =:workTyp and a.shipNo=:shipNo";
					QueryParamLs paramLs1 = new QueryParamLs();
					paramLs1.addParam("driverCod", workBillMachSecond.getDriverCod());
					paramLs1.addParam("begTim", begTim);
					paramLs1.addParam("endTim", endTim);
					paramLs1.addParam("workTyp", "SI");
					paramLs1.addParam("shipNo", workBill.getShipNo());
					List<BigDecimal> countList = JpaUtils.findAll(jpql1, paramLs1);
					if (countList.get(0) != null) {
						count1 = countList.get(0);
					}
					String jpql4 = "select count(a) from WorkCommandBak a where  a.driverCod =:driverCod and "
							+ "a.inCyTim >=:begTim and a.inCyTim <=:endTim and a.workTyp =:workTyp and a.shipNo=:shipNo";
					List<BigDecimal> count2List = JpaUtils.findAll(jpql4, paramLs1);
					if (count2List.get(0) != null) {
						count2 = countList.get(0);
					}
					WorkBillMachine workBillMachine = new WorkBillMachine();
					workBillMachine.setWorkbillNo(workbillNo);
					workBillMachine.setMachTypCod(workBillMachSecond.getMachTypCod());
					workBillMachine.setMachNo(workBillMachSecond.getMachNo());
					workBillMachine.setDriverCod(workBillMachSecond.getDriverCod());
					workBillMachine.setWorkNum(count1.add(count2));
					JpaUtils.save(workBillMachine);
				}
			} else if ("SO".equals(workTyp)) {
				String jpql = "select a from WorkBillDriverSecond a where a.workbillNo =:workbillNo";
				QueryParamLs paramLs = new QueryParamLs();
				paramLs.addParam("workbillNo", workbillNo);
				List<WorkBillDriverSecond> workBillDriverSecondList = JpaUtils.findAll(jpql, paramLs);
				for (WorkBillDriverSecond workBillDriverSecond : workBillDriverSecondList) {
					String jpql1 = "select count(a) from WorkCommand a where  a.driverCod =:driverCod and "
							+ "a.outCyTim >=:begTim and a.outCyTim <=:endTim and a.workTyp =:workTyp and a.shipNo=:shipNo";
					QueryParamLs paramLs1 = new QueryParamLs();
					paramLs1.addParam("driverCod", workBillDriverSecond.getDriverCod());
					paramLs1.addParam("begTim", begTim);
					paramLs1.addParam("endTim", endTim);
					paramLs1.addParam("workTyp", "SO");
					paramLs1.addParam("shipNo", workBill.getShipNo());
					List<BigDecimal> countList = JpaUtils.findAll(jpql1, paramLs1);
					if (countList.get(0) != null) {
						count1 = countList.get(0);
					}
					String jpql2 = "select count(a) from WorkCommandBak a where  a.driverCod =:driverCod and "
							+ "a.outCyTim >=:begTim and a.outCyTim <=:endTim and a.workTyp =:workTyp and a.shipNo=:shipNo";
					List<BigDecimal> count2List = JpaUtils.findAll(jpql2, paramLs1);
					if (count2List.get(0) != null) {
						count2 = countList.get(0);
					}
					WorkBillDriver workBillDriver = new WorkBillDriver();
					workBillDriver.setWorkbillNo(workbillNo);
					workBillDriver.setClassCod(workBillDriverSecond.getClassCod());
					workBillDriver.setDriverCod(workBillDriverSecond.getDriverCod());
					workBillDriver.setWorkNum(count1.add(count2));
					JpaUtils.save(workBillDriver);
				}
				// 生成理货员作业票
				String jpql2 = "select a from WorkBillTallyerSecond a where a.workbillNo =:workbillNo";
				QueryParamLs paramLs2 = new QueryParamLs();
				paramLs.addParam("workbillNo", workbillNo);
				List<WorkBillTallyerSecond> workBillTallyerSecondList = JpaUtils.findAll(jpql, paramLs);
				for (WorkBillTallyerSecond workBillTallyerSecond : workBillTallyerSecondList) {
					String jpql1 = "select count(a) from WorkCommand a where  a.outCyNam =:outCyNam and "
							+ "a.inCyTim >=:begTim and a.inCyTim <=:endTim and a.workTyp =:workTyp and a.shipNo=:shipNo";
					QueryParamLs paramLs1 = new QueryParamLs();
					paramLs1.addParam("outCyNam", workBillTallyerSecond.getTallyCod());
					paramLs1.addParam("begTim", begTim);
					paramLs1.addParam("endTim", endTim);
					paramLs1.addParam("workTyp", "SO");
					paramLs1.addParam("shipNo", workBill.getShipNo());
					List<BigDecimal> countList = JpaUtils.findAll(jpql1, paramLs1);
					if (countList.get(0) != null) {
						count1 = countList.get(0);
					}
					String jpql3 = "select count(a) from WorkCommandBak a where  a.outCyNam =:outCyNam and "
							+ "a.inCyTim >=:begTim and a.inCyTim <=:endTim and a.workTyp =:workTyp and a.shipNo=:shipNo";
					List<BigDecimal> count2List = JpaUtils.findAll(jpql2, paramLs1);
					if (count2List.get(0) != null) {
						count2 = countList.get(0);
					}
					WorkBillTallyer workBillTallyer = new WorkBillTallyer();
					workBillTallyer.setWorkbillNo(workbillNo);
					workBillTallyer.setClassCod(workBillTallyerSecond.getClassCod());
					workBillTallyer.setTallyCod(workBillTallyerSecond.getTallyCod());
					workBillTallyer.setWorkNum(count1.add(count2));
					JpaUtils.save(workBillTallyer);
				}
				// 生成机械作业票
				String jpql1 = "select a from WorkBillMachSecond a where a.workbillNo =:workbillNo";
				QueryParamLs paramLs1 = new QueryParamLs();
				paramLs.addParam("workbillNo", workbillNo);
				List<WorkBillMachSecond> workBillMachSecondList = JpaUtils.findAll(jpql1, paramLs1);
				for (WorkBillMachSecond workBillMachSecond : workBillMachSecondList) {
					String jpql3 = "select count(a) from WorkCommand a where  a.driverCod =:driverCod and "
							+ "a.outCyTim >=:begTim and a.outCyTim <=:endTim and a.workTyp =:workTyp and a.shipNo=:shipNo";
					QueryParamLs paramLs3 = new QueryParamLs();
					paramLs1.addParam("driverCod", workBillMachSecond.getDriverCod());
					paramLs1.addParam("begTim", begTim);
					paramLs1.addParam("endTim", endTim);
					paramLs1.addParam("workTyp", "SO");
					paramLs1.addParam("shipNo", workBill.getShipNo());
					List<BigDecimal> countList = JpaUtils.findAll(jpql3, paramLs1);
					if (countList.get(0) != null) {
						count1 = countList.get(0);
					}
					String jpql4 = "select count(a) from WorkCommandBak a where  a.driverCod =:driverCod and "
							+ "a.outCyTim >=:begTim and a.outCyTim <=:endTim and a.workTyp =:workTyp and a.shipNo=:shipNo";
					List<BigDecimal> count2List = JpaUtils.findAll(jpql4, paramLs1);
					if (count2List.get(0) != null) {
						count2 = countList.get(0);
					}
					WorkBillMachine workBillMachine = new WorkBillMachine();
					workBillMachine.setWorkbillNo(workbillNo);
					workBillMachine.setMachTypCod(workBillMachSecond.getMachTypCod());
					workBillMachine.setMachNo(workBillMachSecond.getMachNo());
					workBillMachine.setDriverCod(workBillMachSecond.getDriverCod());
					workBillMachine.setWorkNum(count1.add(count2));
					JpaUtils.save(workBillMachine);
				}
			} else if ("TI".equals(workTyp)) {
				String jpql = "select a from WorkBillDriverSecond a where a.workbillNo =:workbillNo";
				QueryParamLs paramLs = new QueryParamLs();
				paramLs.addParam("workbillNo", workbillNo);
				List<WorkBillDriverSecond> workBillDriverSecondList = JpaUtils.findAll(jpql, paramLs);
				for (WorkBillDriverSecond workBillDriverSecond : workBillDriverSecondList) {
					String jpql1 = "select count(a) from WorkCommand a where  a.driverCod =:driverCod and "
							+ "a.inCyTim >=:begTim and a.inCyTim <=:endTim and a.workTyp =:workTyp and a.contractNo=:contractNo";
					QueryParamLs paramLs1 = new QueryParamLs();
					paramLs1.addParam("driverCod", workBillDriverSecond.getDriverCod());
					paramLs1.addParam("begTim", begTim);
					paramLs1.addParam("endTim", endTim);
					paramLs1.addParam("workTyp", "TI");
					paramLs1.addParam("contractNo", workBill.getContractNo());
					List<BigDecimal> countList = JpaUtils.findAll(jpql1, paramLs1);
					if (countList.get(0) != null) {
						count1 = countList.get(0);
					}
					String jpql2 = "select count(a) from WorkCommandBak a where  a.driverCod =:driverCod and "
							+ "a.inCyTim >=:begTim and a.inCyTim <=:endTim and a.workTyp =:workTyp and a.contractNo=:contractNo";
					List<BigDecimal> count2List = JpaUtils.findAll(jpql2, paramLs1);
					if (count2List.get(0) != null) {
						count2 = countList.get(0);
					}
					WorkBillDriver workBillDriver = new WorkBillDriver();
					workBillDriver.setWorkbillNo(workbillNo);
					workBillDriver.setClassCod(workBillDriverSecond.getClassCod());
					workBillDriver.setDriverCod(workBillDriverSecond.getDriverCod());
					workBillDriver.setWorkNum(count1.add(count2));
					JpaUtils.save(workBillDriver);
				}
				// 生成理货员作业票
				String jpql2 = "select a from WorkBillTallyerSecond a where a.workbillNo =:workbillNo";
				QueryParamLs paramLs2 = new QueryParamLs();
				paramLs.addParam("workbillNo", workbillNo);
				List<WorkBillTallyerSecond> workBillTallyerSecondList = JpaUtils.findAll(jpql, paramLs);
				for (WorkBillTallyerSecond workBillTallyerSecond : workBillTallyerSecondList) {
					String jpql1 = "select count(a) from WorkCommand a where  a.inCyNam =:inCyNam and "
							+ "a.inCyTim >=:begTim and a.inCyTim <=:endTim and a.workTyp =:workTyp and a.contractNo=:contractNo";
					QueryParamLs paramLs1 = new QueryParamLs();
					paramLs1.addParam("inCyNam", workBillTallyerSecond.getTallyCod());
					paramLs1.addParam("begTim", begTim);
					paramLs1.addParam("endTim", endTim);
					paramLs1.addParam("workTyp", "TI");
					paramLs1.addParam("contractNo", workBill.getContractNo());
					List<BigDecimal> countList = JpaUtils.findAll(jpql1, paramLs1);
					if (countList.get(0) != null) {
						count1 = countList.get(0);
					}
					String jpql3 = "select count(a) from WorkCommandBak a where  a.inCyNam =:inCyNam and "
							+ "a.inCyTim >=:begTim and a.inCyTim <=:endTim and a.workTyp =:workTyp and a.contractNo=:contractNo";
					List<BigDecimal> count2List = JpaUtils.findAll(jpql2, paramLs1);
					if (count2List.get(0) != null) {
						count2 = countList.get(0);
					}
					WorkBillTallyer workBillTallyer = new WorkBillTallyer();
					workBillTallyer.setWorkbillNo(workbillNo);
					workBillTallyer.setClassCod(workBillTallyerSecond.getClassCod());
					workBillTallyer.setTallyCod(workBillTallyerSecond.getTallyCod());
					workBillTallyer.setWorkNum(count1.add(count2));
					JpaUtils.save(workBillTallyer);
				}
				// 生成机械作业票
				String jpql1 = "select a from WorkBillMachSecond a where a.workbillNo =:workbillNo";
				QueryParamLs paramLs1 = new QueryParamLs();
				paramLs1.addParam("workbillNo", workbillNo);
				List<WorkBillMachSecond> workBillMachSecondList = JpaUtils.findAll(jpql1, paramLs1);
				for (WorkBillMachSecond workBillMachSecond : workBillMachSecondList) {
					String jpql3 = "select count(a) from WorkCommand a where  a.driverCod =:driverCod and "
							+ "a.inCyTim >=:begTim and a.inCyTim <=:endTim and a.workTyp =:workTyp and a.contractNo=:contractNo";
					QueryParamLs paramLs3 = new QueryParamLs();
					paramLs1.addParam("driverCod", workBillMachSecond.getDriverCod());
					paramLs1.addParam("begTim", begTim);
					paramLs1.addParam("endTim", endTim);
					paramLs1.addParam("workTyp", "TI");
					paramLs1.addParam("contractNo", workBill.getContractNo());
					List<BigDecimal> countList = JpaUtils.findAll(jpql3, paramLs3);
					if (countList.get(0) != null) {
						count1 = countList.get(0);
					}
					String jpql4 = "select count(a) from WorkCommandBak a where  a.driverCod =:driverCod and "
							+ "a.inCyTim >=:begTim and a.inCyTim <=:endTim and a.workTyp =:workTyp and a.contractNo=:contractNo";
					List<BigDecimal> count2List = JpaUtils.findAll(jpql4, paramLs1);
					if (count2List.get(0) != null) {
						count2 = countList.get(0);
					}
					WorkBillMachine workBillMachine = new WorkBillMachine();
					workBillMachine.setWorkbillNo(workbillNo);
					workBillMachine.setMachTypCod(workBillMachSecond.getMachTypCod());
					workBillMachine.setMachNo(workBillMachSecond.getMachNo());
					workBillMachine.setDriverCod(workBillMachSecond.getDriverCod());
					workBillMachine.setWorkNum(count1.add(count2));
					JpaUtils.save(workBillMachine);
				}
			} else if ("TO".equals(workTyp)) {
				String jpql = "select a from WorkBillDriverSecond a where a.workbillNo =:workbillNo";
				QueryParamLs paramLs = new QueryParamLs();
				paramLs.addParam("workbillNo", workbillNo);
				List<WorkBillDriverSecond> workBillDriverSecondList = JpaUtils.findAll(jpql, paramLs);
				for (WorkBillDriverSecond workBillDriverSecond : workBillDriverSecondList) {
					String jpql1 = "select count(a) from WorkCommand a where  a.driverCod =:driverCod and "
							+ "a.outCyTim >=:begTim and a.outCyTim <=:endTim and a.workTyp =:workTyp and a.contractNo=:contractNo";
					QueryParamLs paramLs1 = new QueryParamLs();
					paramLs1.addParam("driverCod", workBillDriverSecond.getDriverCod());
					paramLs1.addParam("begTim", begTim);
					paramLs1.addParam("endTim", endTim);
					paramLs1.addParam("workTyp", "TO");
					paramLs1.addParam("contractNo", workBill.getContractNo());
					List<BigDecimal> countList = JpaUtils.findAll(jpql1, paramLs1);
					if (countList.get(0) != null) {
						count1 = countList.get(0);
					}
					String jpql2 = "select count(a) from WorkCommandBak a where  a.driverCod =:driverCod and "
							+ "a.outCyTim >=:begTim and a.outCyTim <=:endTim and a.workTyp =:workTyp and a.contractNo=:contractNo";
					List<BigDecimal> count2List = JpaUtils.findAll(jpql2, paramLs1);
					if (count2List.get(0) != null) {
						count2 = countList.get(0);
					}
					WorkBillDriver workBillDriver = new WorkBillDriver();
					workBillDriver.setWorkbillNo(workbillNo);
					workBillDriver.setClassCod(workBillDriverSecond.getClassCod());
					workBillDriver.setDriverCod(workBillDriverSecond.getDriverCod());
					workBillDriver.setWorkNum(count1.add(count2));
					JpaUtils.save(workBillDriver);
				}
				// 生成理货员作业票
				String jpql2 = "select a from WorkBillTallyerSecond a where a.workbillNo =:workbillNo";
				QueryParamLs paramLs2 = new QueryParamLs();
				paramLs.addParam("workbillNo", workbillNo);
				List<WorkBillTallyerSecond> workBillTallyerSecondList = JpaUtils.findAll(jpql, paramLs);
				for (WorkBillTallyerSecond workBillTallyerSecond : workBillTallyerSecondList) {
					String jpql1 = "select count(a) from WorkCommand a where  a.outCyNam =:outCyNam and "
							+ "a.inCyTim >=:begTim and a.inCyTim <=:endTim and a.workTyp =:workTyp and a.contractNo=:contractNo";
					QueryParamLs paramLs1 = new QueryParamLs();
					paramLs1.addParam("outCyNam", workBillTallyerSecond.getTallyCod());
					paramLs1.addParam("begTim", begTim);
					paramLs1.addParam("endTim", endTim);
					paramLs1.addParam("workTyp", "TO");
					paramLs1.addParam("contractNo", workBill.getContractNo());
					List<BigDecimal> countList = JpaUtils.findAll(jpql1, paramLs1);
					if (countList.get(0) != null) {
						count1 = countList.get(0);
					}
					String jpql3 = "select count(a) from WorkCommandBak a where  a.outCyNam =:outCyNam and "
							+ "a.inCyTim >=:begTim and a.inCyTim <=:endTim and a.workTyp =:workTyp and a.contractNo=:contractNo";
					List<BigDecimal> count2List = JpaUtils.findAll(jpql2, paramLs1);
					if (count2List.get(0) != null) {
						count2 = countList.get(0);
					}
					WorkBillTallyer workBillTallyer = new WorkBillTallyer();
					workBillTallyer.setWorkbillNo(workbillNo);
					workBillTallyer.setClassCod(workBillTallyerSecond.getClassCod());
					workBillTallyer.setTallyCod(workBillTallyerSecond.getTallyCod());
					workBillTallyer.setWorkNum(count1.add(count2));
					JpaUtils.save(workBillTallyer);
				}
				// 生成机械作业票
				String jpql1 = "select a from WorkBillMachSecond a where a.workbillNo =:workbillNo";
				QueryParamLs paramLs1 = new QueryParamLs();
				paramLs.addParam("workbillNo", workbillNo);
				List<WorkBillMachSecond> workBillMachSecondList = JpaUtils.findAll(jpql1, paramLs1);
				for (WorkBillMachSecond workBillMachSecond : workBillMachSecondList) {
					String jpql3 = "select count(a) from WorkCommand a where  a.driverCod =:driverCod and "
							+ "a.outCyTim >=:begTim and a.outCyTim <=:endTim and a.workTyp =:workTyp and a.contractNo=:contractNo";
					QueryParamLs paramLs3 = new QueryParamLs();
					paramLs1.addParam("driverCod", workBillMachSecond.getDriverCod());
					paramLs1.addParam("begTim", begTim);
					paramLs1.addParam("endTim", endTim);
					paramLs1.addParam("workTyp", "TO");
					paramLs1.addParam("contractNo", workBill.getContractNo());
					List<BigDecimal> countList = JpaUtils.findAll(jpql3, paramLs3);
					if (countList.get(0) != null) {
						count1 = countList.get(0);
					}
					String jpql4 = "select count(a) from WorkCommandBak a where  a.driverCod =:driverCod and "
							+ "a.outCyTim >=:begTim and a.outCyTim <=:endTim and a.workTyp =:workTyp and a.contractNo=:contractNo";
					List<BigDecimal> count2List = JpaUtils.findAll(jpql4, paramLs3);
					if (count2List.get(0) != null) {
						count2 = countList.get(0);
					}
					WorkBillMachine workBillMachine = new WorkBillMachine();
					workBillMachine.setWorkbillNo(workbillNo);
					workBillMachine.setMachTypCod(workBillMachSecond.getMachTypCod());
					workBillMachine.setMachNo(workBillMachSecond.getMachNo());
					workBillMachine.setDriverCod(workBillMachSecond.getDriverCod());
					workBillMachine.setWorkNum(count1.add(count2));
					JpaUtils.save(workBillMachine);
				}
			} else if ("MV".equals(workTyp)) {
				String jpql = "select a from WorkBillDriverSecond a where a.workbillNo =:workbillNo";
				QueryParamLs paramLs = new QueryParamLs();
				paramLs.addParam("workbillNo", workbillNo);
				List<WorkBillDriverSecond> workBillDriverSecondList = JpaUtils.findAll(jpql, paramLs);
				for (WorkBillDriverSecond workBillDriverSecond : workBillDriverSecondList) {
					String jpql1 = "select count(a) from WorkCommand a where  a.driverCod =:driverCod and "
							+ "a.inCyTim >=:begTim and a.inCyTim <=:endTim and a.workTyp =:workTyp and a.contractNo=:contractNo";
					QueryParamLs paramLs1 = new QueryParamLs();
					paramLs1.addParam("driverCod", workBillDriverSecond.getDriverCod());
					paramLs1.addParam("begTim", begTim);
					paramLs1.addParam("endTim", endTim);
					paramLs1.addParam("workTyp", "MV");
					paramLs1.addParam("contractNo", workBill.getContractNo());
					List<BigDecimal> countList = JpaUtils.findAll(jpql1, paramLs1);
					if (countList.get(0) != null) {
						count1 = countList.get(0);
					}
					String jpql2 = "select count(a) from WorkCommandBak a where  a.driverCod =:driverCod and "
							+ "a.inCyTim >=:begTim and a.inCyTim <=:endTim and a.workTyp =:workTyp and a.contractNo=:contractNo";
					List<BigDecimal> count2List = JpaUtils.findAll(jpql2, paramLs1);
					if (count2List.get(0) != null) {
						count2 = countList.get(0);
					}
					WorkBillDriver workBillDriver = new WorkBillDriver();
					workBillDriver.setWorkbillNo(workbillNo);
					workBillDriver.setClassCod(workBillDriverSecond.getClassCod());
					workBillDriver.setDriverCod(workBillDriverSecond.getDriverCod());
					workBillDriver.setWorkNum(count1.add(count2));
					JpaUtils.save(workBillDriver);
				}
				// 生成理货员作业票
				String jpql2 = "select a from WorkBillTallyerSecond a where a.workbillNo =:workbillNo";
				QueryParamLs paramLs2 = new QueryParamLs();
				paramLs.addParam("workbillNo", workbillNo);
				List<WorkBillTallyerSecond> workBillTallyerSecondList = JpaUtils.findAll(jpql, paramLs);
				for (WorkBillTallyerSecond workBillTallyerSecond : workBillTallyerSecondList) {
					String jpql1 = "select count(a) from WorkCommand a where  a.inCyNam =:inCyNam and "
							+ "a.inCyTim >=:begTim and a.inCyTim <=:endTim and a.workTyp =:workTyp and a.contractNo=:contractNo";
					QueryParamLs paramLs1 = new QueryParamLs();
					paramLs1.addParam("inCyNam", workBillTallyerSecond.getTallyCod());
					paramLs1.addParam("begTim", begTim);
					paramLs1.addParam("endTim", endTim);
					paramLs1.addParam("workTyp", "MV");
					paramLs1.addParam("contractNo", workBill.getContractNo());
					List<BigDecimal> countList = JpaUtils.findAll(jpql1, paramLs1);
					if (countList.get(0) != null) {
						count1 = countList.get(0);
					}
					String jpql3 = "select count(a) from WorkCommandBak a where  a.inCyNam =:inCyNam and "
							+ "a.inCyTim >=:begTim and a.inCyTim <=:endTim and a.workTyp =:workTyp and a.contractNo=:contractNo";
					List<BigDecimal> count2List = JpaUtils.findAll(jpql2, paramLs1);
					if (count2List.get(0) != null) {
						count2 = countList.get(0);
					}
					WorkBillTallyer workBillTallyer = new WorkBillTallyer();
					workBillTallyer.setWorkbillNo(workbillNo);
					workBillTallyer.setClassCod(workBillTallyerSecond.getClassCod());
					workBillTallyer.setTallyCod(workBillTallyerSecond.getTallyCod());
					workBillTallyer.setWorkNum(count1.add(count2));
					JpaUtils.save(workBillTallyer);
				}
				// 生成机械作业票
				String jpql1 = "select a from WorkBillMachSecond a where a.workbillNo =:workbillNo";
				QueryParamLs paramLs1 = new QueryParamLs();
				paramLs.addParam("workbillNo", workbillNo);
				List<WorkBillMachSecond> workBillMachSecondList = JpaUtils.findAll(jpql1, paramLs1);
				for (WorkBillMachSecond workBillMachSecond : workBillMachSecondList) {
					String jpql3 = "select count(a) from WorkCommand a where  a.driverCod =:driverCod and "
							+ "a.inCyTim >=:begTim and a.inCyTim <=:endTim and a.workTyp =:workTyp and a.contractNo=:contractNo";
					QueryParamLs paramLs3 = new QueryParamLs();
					paramLs3.addParam("driverCod", workBillMachSecond.getDriverCod());
					paramLs3.addParam("begTim", begTim);
					paramLs3.addParam("endTim", endTim);
					paramLs3.addParam("workTyp", "MV");
					paramLs3.addParam("contractNo", workBill.getContractNo());
					List<BigDecimal> countList = JpaUtils.findAll(jpql3, paramLs3);
					if (countList.get(0) != null) {
						count1 = countList.get(0);
					}
					String jpql4 = "select count(a) from WorkCommandBak a where  a.driverCod =:driverCod and "
							+ "a.inCyTim >=:begTim and a.inCyTim <=:endTim and a.workTyp =:workTyp and a.contractNo=:contractNo";
					List<BigDecimal> count2List = JpaUtils.findAll(jpql4, paramLs3);
					if (count2List.get(0) != null) {
						count2 = countList.get(0);
					}
					WorkBillMachine workBillMachine = new WorkBillMachine();
					workBillMachine.setWorkbillNo(workbillNo);
					workBillMachine.setMachTypCod(workBillMachSecond.getMachTypCod());
					workBillMachine.setMachNo(workBillMachSecond.getMachNo());
					workBillMachine.setDriverCod(workBillMachSecond.getDriverCod());
					workBillMachine.setWorkNum(count1.add(count2));
					JpaUtils.save(workBillMachine);
				}
			}
		}
	}

	@Transactional
	public void removeAll(String workbillNos) {
		List<String> workBillList = HdUtils.paraseStrs(workbillNos);
		for (String workbillNo : workBillList) {
			String jpql = "select a from WorkBillMach a where a.workbillNo =:workbillNo";
			QueryParamLs paramLs = new QueryParamLs();
			paramLs.addParam("workbillNo", workbillNo);
			List<WorkBillMach> machlist = JpaUtils.findAll(jpql, paramLs);
			if (machlist.size() > 0) {
				throw new HdRunTimeException("存在机械配工相关信息，无法删除！");
			}
			String jpql1 = "select a from WorkBillDriverClass a where a.workbillNo =:workbillNo";
			QueryParamLs paramLs1 = new QueryParamLs();
			paramLs1.addParam("workbillNo", workbillNo);
			List<WorkBillDriverClass> driverlist = JpaUtils.findAll(jpql1, paramLs1);
			if (driverlist.size() > 0) {
				throw new HdRunTimeException("存在司机配工相关信息，无法删除！");
			}
			String jpql2 = "select a from WorkBillTallyerClass a where a.workbillNo =:workbillNo";
			QueryParamLs paramLs2 = new QueryParamLs();
			paramLs2.addParam("workbillNo", workbillNo);
			List<WorkBillTallyerClass> tallyerlist = JpaUtils.findAll(jpql1, paramLs1);
			if (tallyerlist.size() > 0) {
				throw new HdRunTimeException("存在理货员配工相关信息，无法删除！");
			}
			JpaUtils.remove(WorkBill.class, workbillNo);
		}
	}

	@Override
	public WorkBill findone(String workbillNo) {
		WorkBill workBill = JpaUtils.findById(WorkBill.class, workbillNo);
		String jpql = "select a from WorkBillMach a where a.workbillNo =:workbillNo and a.machTypCod =:machTypCod";
		QueryParamLs paramLs = new QueryParamLs();
		paramLs.addParam("workbillNo", workBill.getWorkbillNo());
		paramLs.addParam("machTypCod", WorkBillMach.JTC);
		List<WorkBillMach> jtcList = JpaUtils.findAll(jpql, paramLs);
		if (jtcList.size() > 0) {
			workBill.setJtcNum(jtcList.get(0).getManNum().toString());
		}
		QueryParamLs paramLs2 = new QueryParamLs();
		paramLs2.addParam("workbillNo", workBill.getWorkbillNo());
		paramLs2.addParam("machTypCod", WorkBillMach.CC);
		List<WorkBillMach> ccList = JpaUtils.findAll(jpql, paramLs2);
		if (ccList.size() > 0) {
			workBill.setCcNum(ccList.get(0).getManNum().toString());
		}
		QueryParamLs paramLs3 = new QueryParamLs();
		paramLs3.addParam("workbillNo", workBill.getWorkbillNo());
		paramLs3.addParam("machTypCod", WorkBillMach.QYC);
		List<WorkBillMach> qycList = JpaUtils.findAll(jpql, paramLs3);
		if (qycList.size() > 0) {
			workBill.setQycNum(qycList.get(0).getManNum().toString());
		}
		return workBill;

	}

	@Transactional
	@Override
	public HdMessageCode saveone(@RequestBody WorkBill workBill) {
		if (HdUtils.strNotNull(workBill.getfDirectorCod())) {
			CEmployee zdy1 = JpaUtils.findById(CEmployee.class, workBill.getfDirectorCod());
			workBill.setfDirector(zdy1.getEmpNam());
		}
		if (HdUtils.strNotNull(workBill.getsDirectorCod())) {
			CEmployee zdy2 = JpaUtils.findById(CEmployee.class, workBill.getsDirectorCod());
			workBill.setsDirector(zdy2.getEmpNam());
		}
		if (workBill.getBegTim() != null && workBill.getEndTim() != null) {
			Long t1 = workBill.getBegTim().getTime();
			Long t2 = workBill.getEndTim().getTime();
			if (t2 < t1) {
				throw new HdRunTimeException("结束时间不得早于开始时间！");
			} else {
				double hours = (double) (t2 - t1) / 60 / 1000;
				workBill.setWorktimeNum(new BigDecimal(hours));
			}
		}
		if (HdUtils.strIsNull(workBill.getCheckId())) {
			workBill.setCheckId("0");
			workBill.setCheckTim(new Timestamp(System.currentTimeMillis()));
		}
		if (workBill.getTotalNum() == null) {
			workBill.setTotalNum(new BigDecimal("0"));
		}
		if (workBill.getTotalWeight() == null) {
			workBill.setTotalWeight(new BigDecimal("0"));
		}
		String machTxt = null;
		if (HdUtils.strNotNull(workBill.getJtcNum())) {
			machTxt = "交通车为：" + workBill.getJtcNum();
		}
		if (HdUtils.strNotNull(workBill.getCcNum())) {
			if (HdUtils.strNotNull(machTxt)) {
				machTxt += " 叉车为：" + workBill.getCcNum();
			} else {
				machTxt = "叉车为：" + workBill.getCcNum();
			}
		}
		if (HdUtils.strNotNull(workBill.getQycNum())) {
			if (HdUtils.strNotNull(machTxt)) {
				machTxt += " 牵引车为：" + workBill.getQycNum();
			} else {
				machTxt = "牵引车为：" + workBill.getQycNum();
			}
		}
		if (HdUtils.strNotNull(machTxt)) {
			workBill.setMachTxt(machTxt);
		}
		if (HdUtils.strNotNull(workBill.getWorkbillNo())) {
			JpaUtils.update(workBill);
		} else {
			String datetime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
			String id = datetime.substring(0, 8) + workBill.getWorkRunCod();
			String jpql = "select MAX(a.workbillNo) from WorkBill a where a.workbillNo like :workbillNo";
			QueryParamLs paramLs = new QueryParamLs();
			paramLs.addParam("workbillNo", id + "%");
			String workbillNo = JpaUtils.single(jpql, paramLs);
			if (HdUtils.strNotNull(workbillNo)) {
				BigDecimal seqNo = new BigDecimal(workbillNo);
				seqNo.add(new BigDecimal("1"));
				workBill.setWorkbillNo(seqNo.toString());
			} else {
				workBill.setWorkbillNo(id + "001");
			}
			// 添加默认值
			workBill.setDriverBillConf("0");
			workBill.setTallyBillConf("0");
			workBill.setMachBillConf("0");
			workBill.setCheckId("0");
			workBill.setCheckerMan("*");
			JpaUtils.save(workBill);
		}
		String jpql = "select a from WorkBillMach a where a.workbillNo =:workbillNo and a.machTypCod =:machTypCod";
		// 更新交通车信息
		if (HdUtils.strNotNull(workBill.getJtcNum())) {
			WorkBillMach workBillMach = new WorkBillMach();
			QueryParamLs paramLs = new QueryParamLs();
			paramLs.addParam("workbillNo", workBill.getWorkbillNo());
			paramLs.addParam("machTypCod", WorkBillMach.JTC);
			List<WorkBillMach> jtcList = JpaUtils.findAll(jpql, paramLs);
			if (jtcList.size() > 0) {
				workBillMach = jtcList.get(0);
				workBillMach.setManNum(new BigDecimal(workBill.getJtcNum()));
				JpaUtils.update(workBillMach);
			} else {
				workBillMach.setWorkbillNo(workBill.getWorkbillNo());
				workBillMach.setMachTypCod(WorkBillMach.JTC);
				workBillMach.setManNum(new BigDecimal(workBill.getJtcNum()));
				JpaUtils.save(workBillMach);
			}
		}
		// 更新叉车信息
		if (HdUtils.strNotNull(workBill.getCcNum())) {
			WorkBillMach workBillMach1 = new WorkBillMach();
			QueryParamLs paramLs2 = new QueryParamLs();
			paramLs2.addParam("workbillNo", workBill.getWorkbillNo());
			paramLs2.addParam("machTypCod", WorkBillMach.CC);
			List<WorkBillMach> ccList = JpaUtils.findAll(jpql, paramLs2);
			if (ccList.size() > 0) {
				workBillMach1 = ccList.get(0);
				workBillMach1.setManNum(new BigDecimal(workBill.getCcNum()));
				JpaUtils.update(workBillMach1);
			} else {
				workBillMach1.setWorkbillNo(workBill.getWorkbillNo());
				workBillMach1.setMachTypCod(WorkBillMach.CC);
				workBillMach1.setManNum(new BigDecimal(workBill.getCcNum()));
				JpaUtils.save(workBillMach1);
			}
		}
		// 更新牵引车信息
		if (HdUtils.strNotNull(workBill.getQycNum())) {
			WorkBillMach workBillMach2 = new WorkBillMach();
			QueryParamLs paramLs3 = new QueryParamLs();
			paramLs3.addParam("workbillNo", workBill.getWorkbillNo());
			paramLs3.addParam("machTypCod", WorkBillMach.QYC);
			List<WorkBillMach> qycList = JpaUtils.findAll(jpql, paramLs3);
			if (qycList.size() > 0) {
				workBillMach2 = qycList.get(0);
				workBillMach2.setManNum(new BigDecimal(workBill.getCcNum()));
				JpaUtils.update(workBillMach2);
			} else {
				workBillMach2.setWorkbillNo(workBill.getWorkbillNo());
				workBillMach2.setMachTypCod(WorkBillMach.QYC);
				workBillMach2.setManNum(new BigDecimal(workBill.getQycNum()));
				JpaUtils.save(workBillMach2);
			}
		}
		return HdUtils.genMsg();
	}

	@Transactional
	public void uploadWorkBills(String workbillNos) {
		List<String> workbillNoList = HdUtils.paraseStrs(workbillNos);
		for (String workbillNo : workbillNoList) {
			uploadWorkBill(workbillNo);
			uploadJurp(workbillNo);
		}
	}

	public void uploadWorkBill(String workbillNo) {
		WorkBill bean = JpaUtils.findById(WorkBill.class, workbillNo);
		JSONObject jsonObj = new JSONObject();
		Map<String, String> map = new HashMap<String, String>();
		Ship ship = JpaUtils.findById(Ship.class, bean.getShipNo());
		jsonObj.put("cmdId", "2015");
		jsonObj.put("coId",ship.GZ);
		map.put("teamOrgnId",ship.GZ);
		map.put("jobId", bean.getWorkbillNo());
		map.put("jobType", "1");
		map.put("workDate", bean.getWorkDte().toString().substring(0, 10));
		if ("20-08".equals(bean.getWorkRunCod())){
			map.put("shiftCode", "2");
		}else {
			map.put("shiftCode", "1");
		}
		map.put("svoyageId", ship.getNewGroupShipNo());
		if ("SI".equals(bean.getWorkTyp()) || "TO".equals(bean.getWorkTyp()) ) {
			map.put("shipId", ship.getNewIShipId());
			map.put("unloadFlag", "-");
			
		} else if ("SO".equals(bean.getWorkTyp()) || "TI".equals(bean.getWorkTyp())) {
			map.put("shipId", ship.getNewEShipId());
			map.put("unloadFlag", "+");
		}
		map.put("shiftWgt", bean.getPlanNum().toString());
		map.put("ebegTime", bean.getBegTim().toString().substring(0, 16));
		map.put("eendTime", bean.getEndTim().toString().substring(0, 16));
	    map.put("workNum", bean.getWorkCarNum().toString());
		map.put("submitFlag", "0");
		String jpql1 = "select a from AuthUser a where a.account =:account";
		QueryParamLs paramLs1 = new QueryParamLs();
		paramLs1.addParam("account", bean.getRecNam());
		List<AuthUser> authUserList = JpaUtils.findAll(jpql1, paramLs1);
		for (AuthUser authUser : authUserList) {
			map.put("submitName", authUser.getName());
		}
		map.put("submitTime", bean.getRecTim().toString().substring(0, 16));
		jsonObj.put("data", map);

		String url = tjgjtServiceIp + "8081/inface/company/upload";
		String query = jsonObj.toString();

		String response = "";
		try {
			URL httpUrl = null; // HTTP URL类 用这个类来创建连接
			// 创建URL
			httpUrl = new URL(url);
			// 建立连接
			HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.connect();
			// POST请求
			try (OutputStream os = conn.getOutputStream()) {
				os.write(query.getBytes("UTF-8"));
			}
			// out.flush();
			// 读取响应
			try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
				String lines;
				StringBuffer sbf = new StringBuffer();
				while ((lines = reader.readLine()) != null) {
					lines = new String(lines.getBytes(), "utf-8");
					sbf.append(lines);
				}
				response = sbf.toString();
				String resCode = "0000";
				String resMsg = "OK";
				if (resCode.equals(response)) {
					throw new HdRunTimeException("上报集团成功！");
				}
			} catch (Exception e) {
				// System.out.println("上报计费数据异常！" + e);
			}
			// 断开连接
			conn.disconnect();
		} catch (Exception e) {
			// System.out.println("发送 POST 请求出现异常！" + e);
			// e.printStackTrace();
			throw new HdRunTimeException("发送 POST 请求出现异常!");
		}
	}
	
	
	
	public void uploadJurp(String workbillNo) {
		WorkBill bean = JpaUtils.findById(WorkBill.class, workbillNo);
		JSONObject jsonObj = new JSONObject();
		Map<String, String> map = new HashMap<String, String>();
		Ship ship = JpaUtils.findById(Ship.class, bean.getShipNo());
		jsonObj.put("cmdId", "2016");
		jsonObj.put("coId",ship.GZ);
		map.put("teamOrgnId",ship.GZ);
		map.put("jurpId", bean.getWorkbillNo() + "jurp");
		map.put("jobId", bean.getWorkbillNo());
		map.put("urpType", "0");
		map.put("urpNum", bean.getTallyNum().toString());
		map.put("submitFlag", "0");
		map.put("positionCode", "环球滚装码头");
		String jpql1 = "select a from AuthUser a where a.account =:account";
		QueryParamLs paramLs1 = new QueryParamLs();
		paramLs1.addParam("account", bean.getRecNam());
		List<AuthUser> authUserList = JpaUtils.findAll(jpql1, paramLs1);
		for (AuthUser authUser : authUserList) {
			map.put("recNam", authUser.getName());
			map.put("updNam", authUser.getName());
			
		}
		map.put("recTim", bean.getRecTim().toString().substring(0, 16));
		map.put("updTim", bean.getUpdTim().toString().substring(0, 16));
		jsonObj.put("data", map);

		String url = tjgjtServiceIp + "8081/inface/company/upload";
		String query = jsonObj.toString();

		String response = "";
		try {
			URL httpUrl = null; // HTTP URL类 用这个类来创建连接
			// 创建URL
			httpUrl = new URL(url);
			// 建立连接
			HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.connect();
			// POST请求
			try (OutputStream os = conn.getOutputStream()) {
				os.write(query.getBytes("UTF-8"));
			}
			// out.flush();
			// 读取响应
			try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
				String lines;
				StringBuffer sbf = new StringBuffer();
				while ((lines = reader.readLine()) != null) {
					lines = new String(lines.getBytes(), "utf-8");
					sbf.append(lines);
				}
				response = sbf.toString();
				String resCode = "0000";
				String resMsg = "OK";
				if (resCode.equals(response)) {
					throw new HdRunTimeException("上报集团成功！");
				}
			} catch (Exception e) {
				// System.out.println("上报计费数据异常！" + e);
			}
			// 断开连接
			conn.disconnect();
		} catch (Exception e) {
			// System.out.println("发送 POST 请求出现异常！" + e);
			// e.printStackTrace();
			throw new HdRunTimeException("发送 POST 请求出现异常!");
		}
	}
}
