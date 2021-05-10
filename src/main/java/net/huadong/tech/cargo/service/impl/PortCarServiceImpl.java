package net.huadong.tech.cargo.service.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.huadong.tech.base.entity.*;
import net.huadong.tech.util.CommonUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.eclipse.persistence.config.QueryHints;
import org.eclipse.persistence.config.ResultType;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.Interface.entity.CargoDataSpecification;
import net.huadong.tech.Interface.entity.VGroupCorpBrand;
import net.huadong.tech.Interface.entity.VGroupCorpCargo;
import net.huadong.tech.Interface.entity.VGroupCorpYard;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.bean.HdRunTimeException;
import net.huadong.tech.cargo.entity.StatisticCount;
import net.huadong.tech.cargo.entity.StatisticCountJs;
import net.huadong.tech.cargo.entity.TruckWork;
import net.huadong.tech.cargo.service.PortCarService;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.tech.gate.entity.GateTruck;
import net.huadong.tech.gate.entity.GateTruckContract;
import net.huadong.tech.his.entity.GateTruckContractHis;
import net.huadong.tech.his.entity.GateTruckHis;
import net.huadong.tech.inter.entity.PortCarInter;
import net.huadong.tech.inter.entity.RespInter;
import net.huadong.tech.privilege.entity.AuthUser;
import net.huadong.tech.ship.entity.Ship;
import net.huadong.tech.shipbill.entity.BillCar;
import net.huadong.tech.shipbill.entity.PortCar;
import net.huadong.tech.shipbill.entity.ShipBill;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;
import net.huadong.tech.util.Axis2Util;
import net.huadong.tech.util.HdUtils;
import net.huadong.tech.work.entity.WorkCommand;
import net.huadong.tech.work.entity.WorkCommandBak;
import net.huadong.tech.work.entity.WorkQueue;

/**
 * @author
 */
@Component
public class PortCarServiceImpl implements PortCarService {

    @Value("${tjgjt.service.ip}")
    private String tjgjtServiceIp;

    @Override
    public HdEzuiDatagridData find(HdQuery hdQuery) {
        String jpql = "select a from PortCar a  where a.currentStat =:currentStat ";
        String billNo = hdQuery.getStr("billNo");
        String vinNo = hdQuery.getStr("vinNo");
        String cyPlac = hdQuery.getStr("cyPlac");
        String brandCod = hdQuery.getStr("brandCod");
        String iEId = hdQuery.getStr("iEId");
        String rfidCardNo = hdQuery.getStr("rfidCardNo");
        String shipNo = hdQuery.getStr("shipNo");
        String carKind = hdQuery.getStr("carKind");
        String type = hdQuery.getStr("type");
        String cyAreaNo = hdQuery.getStr("cyAreaNo");
        QueryParamLs paramLs = new QueryParamLs();
        paramLs.addParam("currentStat", PortCar.CURRENT_STAT_3);
        if (HdUtils.strNotNull(iEId)) {
            jpql += "and a.iEId like :iEId ";
            paramLs.addParam("iEId", iEId.trim());
        }
        if (HdUtils.strNotNull(cyAreaNo)) {
            jpql += "and a.cyAreaNo =:cyAreaNo ";
            paramLs.addParam("cyAreaNo", cyAreaNo.trim());
        }
        if (HdUtils.strNotNull(carKind)) {
            jpql += "and a.carKind =:carKind ";
            paramLs.addParam("carKind", carKind.trim());
        }
        if (HdUtils.strNotNull(rfidCardNo)) {
            jpql += "and a.rfidCardNo =:rfidCardNo ";
            paramLs.addParam("rfidCardNo", rfidCardNo.trim());
        }
        if (HdUtils.strNotNull(vinNo)) {
            jpql += "and a.vinNo =:vinNo ";
            paramLs.addParam("vinNo", vinNo.trim());
        }
        if (HdUtils.strNotNull(shipNo)) {
            jpql += "and a.shipNo =:shipNo ";
            paramLs.addParam("shipNo", shipNo.trim());
        }
        if (HdUtils.strNotNull(cyPlac)) {
            jpql += "and a.cyPlac =:cyPlac ";
            paramLs.addParam("cyPlac", cyPlac.trim());
        }
        if (HdUtils.strNotNull(billNo) && !"--".equals(billNo)) {
            jpql += "and a.billNo like :billNo ";
            paramLs.addParam("billNo", "%" + billNo.trim() + "%");
        }
        if (HdUtils.strNotNull(brandCod)) {
            jpql += "and a.brandCod =:brandCod ";
            paramLs.addParam("brandCod", brandCod.trim());
        }
        jpql += "order by a.portCarNo desc";
        HdEzuiDatagridData results = JpaUtils.findByEz(jpql, paramLs, hdQuery);
        List<PortCar> portCarList = results.getRows();
        if (portCarList.size() > 0) {
            for (PortCar pc : portCarList) {
                if (HdUtils.strNotNull(pc.getShipNo())) {
                    Ship s = JpaUtils.findById(Ship.class, pc.getShipNo());
                    if (s.getcShipNam() == null || s.getcShipNam().equals("")) {
                    } else {
                        pc.setcShipNam(s.getcShipNam());
                        pc.setVoyage(s.getIvoyage() + '/' + s.getEvoyage());
                    }
                }
                if (HdUtils.strNotNull(pc.getBrandCod())) {
                    CBrand cbrand = JpaUtils.findById(CBrand.class, pc.getBrandCod());
                    if (cbrand != null)
                        pc.setBrandNam(cbrand.getBrandNam());
                }
                if (HdUtils.strNotNull(pc.getCarTyp())) {
                    CCarTyp bean = JpaUtils.findById(CCarTyp.class, pc.getCarTyp());
                    if (bean != null) {
                        pc.setCarTypNam(bean.getCarTypNam());
                    }
                }
                if (HdUtils.strNotNull(pc.getTranPortCod())) {
                    String jpqlc = "select a from CPort a where a.portCod=:portCod";
                    QueryParamLs paramLsc = new QueryParamLs();
                    paramLsc.addParam("portCod", pc.getTranPortCod());
                    List<CPort> cportList = JpaUtils.findAll(jpqlc, paramLsc);
                    if (cportList.size() > 0) {
                        pc.setTranPortNam(cportList.get(0).getcPortNam());
                    }
                }
                if (HdUtils.strNotNull(pc.getDiscPortCod())) {
                    String jpqlc = "select a from CPort a where a.portCod=:portCod";
                    QueryParamLs paramLsc = new QueryParamLs();
                    paramLsc.addParam("portCod", pc.getDiscPortCod());
                    List<CPort> cportList = JpaUtils.findAll(jpqlc, paramLsc);
                    if (cportList.size() > 0) {
                        pc.setDiscPortNam(cportList.get(0).getcPortNam());
                    }
                }
                if (HdUtils.strNotNull(pc.getRecNam())) {
                    String sql = "select a from AuthUser a where a.account =:account";
                    QueryParamLs paramls = new QueryParamLs();
                    paramls.addParam("account", pc.getRecNam());
                    List<AuthUser> authUserList = JpaUtils.findAll(sql, paramls);
                    if (authUserList.size() > 0) {
                        pc.setRecNam(authUserList.get(0).getName());
                    }
                }
                if (HdUtils.strNotNull(pc.getUpdNam())) {
                    String sql = "select a from AuthUser a where a.account =:account";
                    QueryParamLs paramls = new QueryParamLs();
                    paramls.addParam("account", pc.getUpdNam());
                    List<AuthUser> authUserList = JpaUtils.findAll(sql, paramls);
                    if (authUserList.size() > 0) {
                        pc.setUpdNam(authUserList.get(0).getName());
                    }
                }
            }
        }
        return results;
    }

    @Override
    public HdEzuiDatagridData findAll(HdQuery hdQuery) {
        String jpql = "select a from VPortCar a where  length(a.vinNo) >0  ";
        String billNo = hdQuery.getStr("billNo");
        String vinNo = hdQuery.getStr("vinNo");
        String cyPlac = hdQuery.getStr("cyPlac");
        String brandCod = hdQuery.getStr("brandCod");
        String iEId = hdQuery.getStr("iEId");
        String shipNo = hdQuery.getStr("shipNo");
        String cyAreaNo = hdQuery.getStr("cyAreaNo");
        String currentStat = hdQuery.getStr("currentStat");
        String rfidNo = hdQuery.getStr("rfidNo");
        String tradeId = hdQuery.getStr("tradeId");
        String timeLimit = hdQuery.getStr("timeLimit");

//		if(!CommonUtil.strNotNull(billNo))
//		{
//			return  new HdEzuiDatagridData
//		}
//
        QueryParamLs paramLs = new QueryParamLs();
        if (HdUtils.strNotNull(iEId)) {
            jpql += "and a.iEId =:iEId ";
            paramLs.addParam("iEId", iEId);
        }
        if (HdUtils.strNotNull(cyAreaNo)) {
            jpql += "and a.cyAreaNo =:cyAreaNo ";
            paramLs.addParam("cyAreaNo", cyAreaNo);
        }
        if (HdUtils.strNotNull(currentStat)) {
            jpql += "and a.currentStat =:currentStat ";
            paramLs.addParam("currentStat", currentStat);
        }
        if (HdUtils.strNotNull(vinNo)) {
            jpql += "and a.vinNo like :vinNo ";
            paramLs.addParam("vinNo", "%" + vinNo + "%");
        }
        if (HdUtils.strNotNull(vinNo)) {
            jpql += "and a.vinNo =:vinNo ";
            paramLs.addParam("vinNo", vinNo);
        }
        if (HdUtils.strNotNull(shipNo)) {
            jpql += "and a.shipNo =:shipNo ";
            paramLs.addParam("shipNo", shipNo);
        }
        if (HdUtils.strNotNull(cyPlac)) {
            jpql += "and a.cyPlac =:cyPlac";
            paramLs.addParam("cyPlac", cyPlac);
        }
        if (HdUtils.strNotNull(billNo)) {
            jpql += "and a.billNo =:billNo ";
            paramLs.addParam("billNo", billNo);
        }
        if (HdUtils.strNotNull(brandCod)) {
            jpql += "and a.brandCod =:brandCod ";
            paramLs.addParam("brandCod", brandCod);
        }


        if (HdUtils.strNotNull(rfidNo)) {
            jpql += "and a.rfidCardNo =:rfidNo ";
            paramLs.addParam("rfidNo", rfidNo);
        }
        if (HdUtils.strNotNull(tradeId)) {
            jpql += "and a.tradeId =:tradeId ";
            paramLs.addParam("tradeId", tradeId);
        }

        if (HdUtils.strNotNull(timeLimit)) {
            Date before7 = CommonUtil.addDay(new Date(), -7);


            jpql += "and a.inCyTim  >= :beginTim ";
            paramLs.addParam("beginTim", before7);
        }
        jpql += " order by a.cyAreaNo,a.cyBayNo,a.cyRowNo";
        HdEzuiDatagridData results = JpaUtils.findByEz(jpql, paramLs, hdQuery);
        return results;
    }

    @Override
    public HdMessageCode updateInfo(String vimNo,String isAll) {
        HdMessageCode result = new HdMessageCode();
        String sql = "SELECT\n" +
                "  c1.*\n" +
                "FROM\n" +
                "    C_CAR_TYP c1,\n" +
                "\t\tC_CAR_VIN c2\n" +
                "   \n" +
                "WHERE\n" +
                "    c1.CAR_TYP=C2.CAR_TYP\n";
        /*"\t\tAND c2.VIN_NO like 'WAUAFC4M%'";*/

        QueryParamLs paramLs = new QueryParamLs();
        if (HdUtils.strNotNull(vimNo)) {
            sql += " and c2.VIN_NO like ?1";
            paramLs.addParam(1, "%" + vimNo + "%");
        }
        List<CCarTyp> cCarTypList = JpaUtils.findBySql(sql, paramLs, CCarTyp.class);
        if (cCarTypList.size() > 0) {
            for (CCarTyp ccartyp : cCarTypList) {
                QueryParamLs billCarParamLs = new QueryParamLs();
                String billCarSql = "SELECT\n" +
                        "   *\n" +
                        " FROM\n" +
                        "    BILL_CAR " +
                        " WHERE\n" +
                        "    1 = 1   ";
                if ("0".equals(isAll)) {
                    //jpql += " and( a.carKind is null and a.carTyp is null and a.brandCod is null)   ";
                }else {
                    billCarSql += " and( CAR_KIND is null or CAR_TYP is null or BRAND_COD is null)   ";
                }
                if (HdUtils.strNotNull(vimNo)) {
                    billCarSql += " and VIN_NO like ?2";
                    billCarParamLs.addParam(2, "%" + vimNo + "%");
                }
                List<BillCar> billCarList = JpaUtils.findBySql(billCarSql, billCarParamLs, BillCar.class);
                if (billCarList.size() > 0) {
                    for (BillCar billCar : billCarList) {
                        HangUpLog hangUpLog = new HangUpLog();
                        hangUpLog.setPortCarNo(billCar.getPortCarNo());
                        hangUpLog.setRecNam(HdUtils.getCurUser().getAccount());
                        hangUpLog.setRecTim(HdUtils.getDateTime());
                        hangUpLog.setOldCarTyp(billCar.getCarTyp());
                        billCar.setCarTyp(ccartyp.getCarTyp());
                        hangUpLog.setNewCarTyp(ccartyp.getCarTyp());
                        hangUpLog.setOldBrandCod(billCar.getBrandCod());
                        billCar.setBrandCod(ccartyp.getBrandCod());
                        billCar.setBrandNam(ccartyp.getBrandNam());
                        hangUpLog.setNewBrandCod(ccartyp.getBrandCod());
                        hangUpLog.setOldCarKind(billCar.getCarKind());
                        billCar.setCarKind(ccartyp.getCarKind());
                        hangUpLog.setNewCarKind(ccartyp.getCarKind());
                        HangUpLog hang=JpaUtils.findById(HangUpLog.class,billCar.getPortCarNo());
                        if(hang!=null){
                            hangUpLog.setUpdNam(HdUtils.getCurUser().getAccount());
                            hangUpLog.setUpdTim(HdUtils.getDateTime());
                            JpaUtils.update(hangUpLog);
                        }else {
                            JpaUtils.save(hangUpLog);
                        }
                        JpaUtils.update(billCar);
                    }
                }
                QueryParamLs workCommandParamLs = new QueryParamLs();
                String workCommandSql = "SELECT\n" +
                        "   *\n" +
                        " FROM\n" +
                        "    WORK_COMMAND" +
                        " WHERE\n" +
                        "    1 = 1   ";
                if ("0".equals(isAll)) {
                    //jpql += " and( a.carKind is null and a.carTyp is null and a.brandCod is null)   ";
                }else {
                    workCommandSql += " and( CAR_KIND is null or CAR_TYP is null or BRAND_COD is null)   ";
                }
                if (HdUtils.strNotNull(vimNo)) {
                    workCommandSql += " and VIN_NO like ?2";
                    workCommandParamLs.addParam(2, "%" + vimNo + "%");
                }
                List<WorkCommand> workCommandList = JpaUtils.findBySql(workCommandSql, workCommandParamLs, WorkCommand.class);
                if (workCommandList.size() > 0) {
                    for (WorkCommand workCommand : workCommandList
                    ) {
                        HangUpLog hangUpLog = new HangUpLog();
                        hangUpLog.setPortCarNo(workCommand.getPortCarNo());
                        hangUpLog.setRecNam(HdUtils.getCurUser().getAccount());
                        hangUpLog.setRecTim(HdUtils.getDateTime());
                        hangUpLog.setOldCarTyp(workCommand.getCarTyp());
                        workCommand.setCarTyp(ccartyp.getCarTyp());
                        hangUpLog.setNewCarTyp(ccartyp.getCarTyp());
                        hangUpLog.setOldBrandCod(workCommand.getBrandCod());
                        workCommand.setBrandCod(ccartyp.getBrandCod());
                        workCommand.setBrandNam(ccartyp.getBrandNam());
                        hangUpLog.setNewBrandCod(ccartyp.getBrandCod());
                        hangUpLog.setOldCarKind(workCommand.getCarKind());
                        workCommand.setCarKind(ccartyp.getCarKind());
                        hangUpLog.setNewCarKind(ccartyp.getCarKind());
                        HangUpLog hang=JpaUtils.findById(HangUpLog.class,workCommand.getPortCarNo());
                        if(hang!=null){
                            hangUpLog.setUpdNam(HdUtils.getCurUser().getAccount());
                            hangUpLog.setUpdTim(HdUtils.getDateTime());
                            JpaUtils.update(hangUpLog);
                        }else {
                            JpaUtils.save(hangUpLog);
                        }
                        JpaUtils.update(workCommand);
                    }
                }
                QueryParamLs portCarParamLs = new QueryParamLs();
                String portCarSql = "SELECT\n" +
                        "   *\n" +
                        " FROM\n" +
                        "    PORT_CAR" +
                        " WHERE\n" +
                        "    1 = 1    ";
                if ("0".equals(isAll)) {
                    //jpql += " and( a.carKind is null and a.carTyp is null and a.brandCod is null)   ";
                }else {
                    portCarSql += " and( CAR_KIND is null or CAR_TYP is null or BRAND_COD is null)   ";
                }
                if (HdUtils.strNotNull(vimNo)) {
                    portCarSql += " and VIN_NO like ?2";
                    portCarParamLs.addParam(2, "%" + vimNo + "%");
                }
                List<PortCar> portCarList = JpaUtils.findBySql(portCarSql, portCarParamLs, PortCar.class);
                if (portCarList.size() > 0) {
                    for (PortCar portCar : portCarList
                    ) {
                        HangUpLog hangUpLog = new HangUpLog();
                        hangUpLog.setPortCarNo(portCar.getPortCarNo());
                        hangUpLog.setRecNam(HdUtils.getCurUser().getAccount());
                        hangUpLog.setRecTim(HdUtils.getDateTime());
                        hangUpLog.setOldCarTyp(portCar.getCarTyp());
                        portCar.setCarTyp(ccartyp.getCarTyp());
                        hangUpLog.setNewCarTyp(ccartyp.getCarTyp());
                        hangUpLog.setOldBrandCod(portCar.getBrandCod());
                        portCar.setBrandCod(ccartyp.getBrandCod());
                        portCar.setBrandNam(ccartyp.getBrandNam());
                        hangUpLog.setNewBrandCod(ccartyp.getBrandCod());
                        hangUpLog.setOldCarKind(portCar.getCarKind());
                        portCar.setCarKind(ccartyp.getCarKind());
                        hangUpLog.setNewCarKind(ccartyp.getCarKind());
                        HangUpLog hang=JpaUtils.findById(HangUpLog.class,portCar.getPortCarNo());
                        if(hang!=null){
                            hangUpLog.setUpdNam(HdUtils.getCurUser().getAccount());
                            hangUpLog.setUpdTim(HdUtils.getDateTime());
                            JpaUtils.update(hangUpLog);
                        }else {
                            JpaUtils.save(hangUpLog);
                        }
                        JpaUtils.update(portCar);
                    }
                }
                if(portCarList.size()== 0 && workCommandList.size() == 0 && billCarList.size() == 0){
                    result.setCode("1");
                }else{
                    result.setCode("0");
                }
            }
        }else {
            result.setCode("-1");
        }
        return result;
    }

    @Override
    public HdEzuiDatagridData findNull(HdQuery hdQuery) {
        String jpql = "select a from VPortCar a where  length(a.vinNo) >0 ";
        String billNo = hdQuery.getStr("billNo");
        String vinNo = hdQuery.getStr("vinNo");
        String cyPlac = hdQuery.getStr("cyPlac");
        String brandCod = hdQuery.getStr("brandCod");
        String iEId = hdQuery.getStr("iEId");
        String shipNo = hdQuery.getStr("shipNo");
        String cyAreaNo = hdQuery.getStr("cyAreaNo");
        String currentStat = hdQuery.getStr("currentStat");
        String rfidNo = hdQuery.getStr("rfidNo");
        String tradeId = hdQuery.getStr("tradeId");
        String timeLimit = hdQuery.getStr("timeLimit");
        String isAll = hdQuery.getStr("isAll");
//		if(!CommonUtil.strNotNull(billNo))
//		{
//			return  new HdEzuiDatagridData
//		}
//
        QueryParamLs paramLs = new QueryParamLs();
        if (HdUtils.strNotNull(iEId)) {
            jpql += "and a.iEId =:iEId ";
            paramLs.addParam("iEId", iEId);
        }
        if ("0".equals(isAll)) {
            //jpql += " and( a.carKind is null and a.carTyp is null and a.brandCod is null)   ";
        }else {
            jpql += " and( a.carKind is null or a.carTyp is null or a.brandCod is null)   ";
        }
        if (HdUtils.strNotNull(cyAreaNo)) {
            jpql += "and a.cyAreaNo =:cyAreaNo ";
            paramLs.addParam("cyAreaNo", cyAreaNo);
        }
        if (HdUtils.strNotNull(currentStat)) {
            jpql += "and a.currentStat =:currentStat ";
            paramLs.addParam("currentStat", currentStat);
        }
        if (HdUtils.strNotNull(vinNo)) {
            jpql += "and a.vinNo like :vinNo ";
            paramLs.addParam("vinNo", "%" + vinNo + "%");
        }
        if (HdUtils.strNotNull(vinNo)) {
            jpql += "and a.vinNo =:vinNo ";
            paramLs.addParam("vinNo", vinNo);
        }
        if (HdUtils.strNotNull(shipNo)) {
            jpql += "and a.shipNo =:shipNo ";
            paramLs.addParam("shipNo", shipNo);
        }
        if (HdUtils.strNotNull(cyPlac)) {
            jpql += "and a.cyPlac =:cyPlac";
            paramLs.addParam("cyPlac", cyPlac);
        }
        if (HdUtils.strNotNull(billNo)) {
            jpql += "and a.billNo =:billNo ";
            paramLs.addParam("billNo", billNo);
        }
        if (HdUtils.strNotNull(brandCod)) {
            jpql += "and a.brandCod =:brandCod ";
            paramLs.addParam("brandCod", brandCod);
        }


        if (HdUtils.strNotNull(rfidNo)) {
            jpql += "and a.rfidCardNo =:rfidNo ";
            paramLs.addParam("rfidNo", rfidNo);
        }
        if (HdUtils.strNotNull(tradeId)) {
            jpql += "and a.tradeId =:tradeId ";
            paramLs.addParam("tradeId", tradeId);
        }

        if (HdUtils.strNotNull(timeLimit)) {
            Date before7 = CommonUtil.addDay(new Date(), -7);


            jpql += "and a.inCyTim  >= :beginTim ";
            paramLs.addParam("beginTim", before7);
        }
        jpql += " order by a.cyAreaNo,a.cyBayNo,a.cyRowNo";
        HdEzuiDatagridData results = JpaUtils.findByEz(jpql, paramLs, hdQuery);
        return results;
    }


    @Override
    public HdEzuiDatagridData findVinExcel(HdQuery hdQuery) {
        String jpql = "select a from VPortCar a where  length(a.vinNo) >0  ";
        String billNo = hdQuery.getStr("billNo");
        String vinNo = hdQuery.getStr("vinNo");
        String brandCod = hdQuery.getStr("brandCod");
        String iEId = hdQuery.getStr("iEId");
        String tranPortCod = hdQuery.getStr("tranPortCod");
        String tradeId = hdQuery.getStr("tradeId");
        String cyAreaNo = hdQuery.getStr("cyAreaNo");

        QueryParamLs paramLs = new QueryParamLs();
        if (HdUtils.strNotNull(iEId)) {
            jpql += "and a.iEId =:iEId ";
            paramLs.addParam("iEId", iEId);
        }
        if (HdUtils.strNotNull(cyAreaNo)) {
            jpql += "and a.cyAreaNo =:cyAreaNo ";
            paramLs.addParam("cyAreaNo", cyAreaNo);
        }
        jpql += "and a.currentStat =:currentStat ";
        paramLs.addParam("currentStat", "2");
        if (HdUtils.strNotNull(vinNo)) {
            jpql += "and a.vinNo like :vinNo ";
            paramLs.addParam("vinNo", "%" + vinNo + "%");
        }
        if (HdUtils.strNotNull(vinNo)) {
            jpql += "and a.vinNo =:vinNo ";
            paramLs.addParam("vinNo", vinNo);
        }
        if (HdUtils.strNotNull(billNo)) {
            jpql += "and a.billNo =:billNo ";
            paramLs.addParam("billNo", billNo);
        }
        if (HdUtils.strNotNull(brandCod)) {
            jpql += "and a.brandCod =:brandCod ";
            paramLs.addParam("brandCod", brandCod);
        }
        if (HdUtils.strNotNull(tradeId)) {
            jpql += "and a.tradeId =:tradeId ";
            paramLs.addParam("tradeId", tradeId);
        }
        if (HdUtils.strNotNull(brandCod)) {
            jpql += "and a.tranPortCod =:tranPortCod ";
            paramLs.addParam("tranPortCod", tranPortCod);
        }
        jpql += "order by a.cyAreaNo,a.cyBayNo,a.cyRowNo";
        HdEzuiDatagridData results = JpaUtils.findByEz(jpql, paramLs, hdQuery);
        return results;
    }

    public HdEzuiDatagridData findZclh(HdQuery hdQuery) {
        String jpql = "select a from PortCar a where a.currentStat = '2' ";
        String type = hdQuery.getStr("type");
        String shipNo = hdQuery.getStr("shipNo");
        String billNo = hdQuery.getStr("billNo");
        String carTyp = hdQuery.getStr("carTyp");
        QueryParamLs paramLs = new QueryParamLs();
        if ("XC".equals(type) || "PLXC".equals(type)) {
            jpql += "and a.iEId =:iEId ";
            paramLs.addParam("iEId", "I");
        } else if ("ZC".equals(type) || "PLZC".equals(type)) {
            jpql += "and a.iEId =:iEId ";
            paramLs.addParam("iEId", "E");
        }
        if (HdUtils.strNotNull(shipNo)) {
            jpql += "and a.shipNo =:shipNo ";
            paramLs.addParam("shipNo", shipNo);
        } else {
            jpql += "and a.shipNo=:shipNo ";
            paramLs.addParam("shipNo", "123456789##");
        }
        if (HdUtils.strNotNull(billNo)) {
            jpql += "and a.billNo =:billNo ";
            paramLs.addParam("billNo", billNo);
        }
        if (HdUtils.strNotNull(carTyp)) {
            jpql += "and a.carTyp =:carTyp ";
            paramLs.addParam("carTyp", carTyp);
        }
        jpql += " order by a.recTim asc";
        HdEzuiDatagridData result = JpaUtils.findByEz(jpql, paramLs, hdQuery);
        List<PortCar> portCarList = result.getRows();
        List<PortCar> portCarList1 = new ArrayList();
        if (HdUtils.strNotNull(shipNo)) {
            for (PortCar portCar : portCarList) {
                String jpql1 = "select a from WorkCommand a where a.shipNo ='" + shipNo + "' and a.workTyp = 'SO' and "
                        + "a.portCarNo ='" + portCar.getPortCarNo() + "' and a.outCyTim is not null";
                List<WorkCommand> workCommandList = JpaUtils.findAll(jpql1, null);
                if (workCommandList.size() > 0) {
                    portCarList1.add(portCar);
                }
            }
        }
        for (PortCar portCar : portCarList1) {
            portCarList.remove(portCar);
        }
        for (PortCar bc : portCarList) {
            if (HdUtils.strNotNull(bc.getCarTyp())) {
                CCarTyp ccartyp = JpaUtils.findById(CCarTyp.class, bc.getCarTyp());
                if (ccartyp != null)
                    bc.setCarTypNam(ccartyp.getCarTypNam());
            }
            if (HdUtils.strNotNull(bc.getCarKind())) {
                CCarKind carkind = JpaUtils.findById(CCarKind.class, bc.getCarKind());
                if (carkind != null)
                    bc.setCarKindNam(carkind.getCarKindNam());
            }
            if (HdUtils.strNotNull(bc.getShipNo())) {
                Ship ship = JpaUtils.findById(Ship.class, bc.getShipNo());
                if (ship != null) {
                    bc.setcShipNam(ship.getcShipNam());
                    bc.setVoyage(ship.getEvoyage());
                }
            }
        }
        return result;
    }

    // 时间年月日时分秒
    public static Timestamp strToDateTime(String dateStr) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            return new Timestamp(format.parse(dateStr).getTime());
        } catch (ParseException var3) {
            var3.printStackTrace();
            return null;
        }
    }

    // 货物出入库查询
    @Override
    public HdEzuiDatagridData findHwcrk(HdQuery hdQuery) {
        // String jpql = "select a.carTyp ckd, a.billNo bno, count(a.portCarNo)
        // cnt, cast(a.inCyTim as date) ict from PortCar a where a.inCyTim is
        // not null ";
        // String shipNo = hdQuery.getStr("shipNo");
        // String iEId = hdQuery.getStr("iEId");
        // QueryParamLs paramLs = new QueryParamLs();
        // if (HdUtils.strNotNull(shipNo)) {
        // jpql += "and a.shipNo=:shipNo ";
        // paramLs.addParam("shipNo", shipNo);
        // }
        // if (HdUtils.strNotNull(iEId)) {
        // jpql += "and a.iEId=:iEId ";
        // paramLs.addParam("iEId", iEId);
        // }
        // jpql += "group by a.billNo,a.carTyp,cast(a.inCyTim as date) order by
        // a.billNo asc,cast(a.inCyTim as date) desc";
        // List<Object[]> objList = JpaUtils.findAll(jpql, paramLs);
        String shipNo = hdQuery.getStr("shipNo");
        String iEId = hdQuery.getStr("iEId");
        String billNo = hdQuery.getStr("billNo");
        String brandCod = hdQuery.getStr("brandCod");
        String carKind = hdQuery.getStr("carKind");
        String carTyp = hdQuery.getStr("carTyp");
        String inCyTim = hdQuery.getStr("inCyTim");
        String outCyTim = hdQuery.getStr("outCyTim");
        String portCarSql = "SELECT  CAR_TYP,\r\n" + "       BILL_NO,\r\n" + "       COUNT(PORT_CAR_NO),\r\n"
                + "       CAST(trunc(IN_CY_TIM, 'dd') AS date) ,DIRECT_ID\r\n" + "  FROM PORT_CAR t\r\n"
                + " WHERE IN_CY_TIM IS NOT NULL\r\n";
        if (HdUtils.strNotNull(shipNo)) {
            portCarSql += "   AND SHIP_NO = '" + shipNo + "' \r\n";
        } else {
            portCarSql += "   AND SHIP_NO = '不选船不展示' \r\n";
        }
        if (HdUtils.strNotNull(iEId)) {
            portCarSql += "   AND I_E_ID = '" + iEId + "' \r\n";
        }
        if (HdUtils.strNotNull(billNo)) {
            portCarSql += "   AND BILL_NO like '%" + billNo + "%' \r\n";
        }
        if (HdUtils.strNotNull(brandCod)) {
            portCarSql += "   AND BRAND_COD = '" + brandCod + "' \r\n";
        }
        if (HdUtils.strNotNull(carKind)) {
            portCarSql += "    AND CAR_KIND ='" + carKind + "' \r\n";
        }
        if (HdUtils.strNotNull(carTyp)) {
            portCarSql += "    AND CAR_TYP ='" + carTyp + "' \r\n";
        }
        if (HdUtils.strNotNull(inCyTim)) {
            portCarSql += "    AND to_char(IN_CY_TIM,'yyyy-MM-dd hh:mm:ss') > '" + inCyTim + "' \r\n";
        }
        if (HdUtils.strNotNull(outCyTim)) {
            portCarSql += "    AND to_char(OUT_CY_TIM,'yyyy-MM-dd hh:mm:ss') < '" + outCyTim + "' \r\n";
        }
        portCarSql += " GROUP BY DIRECT_ID,BILL_NO, CAR_TYP, CAST(trunc(IN_CY_TIM, 'dd') AS date)\r\n"
                + " ORDER BY BILL_NO ASC, CAST(trunc(IN_CY_TIM, 'dd') AS date) DESC";
        List<Object[]> objList = JpaUtils.getEntityManager().createNativeQuery(portCarSql).getResultList();
        List<PortCar> allList = new ArrayList();
        for (Object[] obj : objList) {
            String jqpl1 = "select a from PortCar a where 1=1";
            QueryParamLs paramLs1 = new QueryParamLs();
            if (HdUtils.strNotNull(shipNo)) {
                jqpl1 += " and a.shipNo=:shipNo ";
                paramLs1.addParam("shipNo", shipNo);
            }
            if (obj[1] != null) {
                jqpl1 += " and a.billNo =:billNo";
                paramLs1.addParam("billNo", obj[1]);
            }
            if (obj[0] != null) {
                jqpl1 += " and a.carTyp =:carTyp";
                paramLs1.addParam("carTyp", obj[0]);
            }
            if (HdUtils.strNotNull(iEId)) {
                jqpl1 += " and a.iEId=:iEId";
                paramLs1.addParam("iEId", iEId);
            }
            if (obj[3] != null) {
                jqpl1 += " and a.inCyTim >=:begTim and a.inCyTim <=:endTim";
                paramLs1.addParam("begTim", HdUtils.strToDate(String.valueOf(obj[3])));
                paramLs1.addParam("endTim", HdUtils.addDay(HdUtils.strToDate(String.valueOf(obj[3])), 1));
            }
            List<PortCar> portCarList = JpaUtils.findAll(jqpl1, paramLs1);
            if (portCarList.size() > 0) {
                PortCar portCar = portCarList.get(0);
                portCar.setRksl(String.valueOf(obj[2]));
                if (HdUtils.strNotNull(portCar.getCarKind())) {
                    CCarKind carkind = JpaUtils.findById(CCarKind.class, portCar.getCarKind());
                    if (carkind != null) {
                        portCar.setCarKindNam(carkind.getCarKindNam());
                    }
                }
                if (HdUtils.strNotNull(portCar.getBrandCod())) {
                    CBrand cbrand = JpaUtils.findById(CBrand.class, portCar.getBrandCod());
                    if (cbrand != null) {
                        portCar.setBrandNam(cbrand.getBrandNam());
                    }
                }
                if (HdUtils.strNotNull(portCar.getConsignCod())) {
                    CClientCod cClientCod = JpaUtils.findById(CClientCod.class, portCar.getConsignCod());
                    if (cClientCod != null) {
                        portCar.setConsignNam(cClientCod.getcClientNam());
                    }
                }
                if ("E".equals(iEId)) {
                    if (HdUtils.strNotNull(portCar.getShipNo())) {
                        Ship ship = JpaUtils.findById(Ship.class, portCar.getShipNo());
                        if (ship != null) {
                            portCar.setcShipNam(ship.getcShipNam());
                            portCar.setVoyage(ship.getIvoyage());
                        }
                    }
                } else if ("I".equals(iEId)) {
                    if (HdUtils.strNotNull(portCar.getShipNo())) {
                        Ship ship = JpaUtils.findById(Ship.class, portCar.getShipNo());
                        if (ship != null) {
                            portCar.setcShipNam(ship.getcShipNam());
                            portCar.setVoyage(ship.getIvoyage());
                        }
                    }
                }

                String jpql2 = "select  count(a.portCarNo) cnt from PortCar a where a.outCyTim is not null ";
                QueryParamLs paramLs2 = new QueryParamLs();
                if (HdUtils.strNotNull(shipNo)) {
                    jpql2 += "and a.shipNo=:shipNo ";
                    paramLs2.addParam("shipNo", shipNo);
                }
                if (HdUtils.strNotNull(portCar.getBillNo())) {
                    jpql2 += "and a.billNo=:billNo ";
                    paramLs2.addParam("billNo", portCar.getBillNo());
                }
                if (HdUtils.strNotNull(portCar.getCarTyp())) {
                    jpql2 += "and a.carTyp =:carTyp ";
                    paramLs2.addParam("carTyp", portCar.getCarTyp());
                }
                if (HdUtils.strNotNull(iEId)) {
                    jpql2 += "and a.iEId=:iEId ";
                    paramLs2.addParam("iEId", iEId);
                }
                if (obj[3] != null) {
                    jpql2 += " and a.inCyTim >=:begTim and a.inCyTim <:endTim";
                    paramLs2.addParam("begTim", HdUtils.strToDate(String.valueOf(obj[3])));
                    paramLs2.addParam("endTim", HdUtils.addDay(HdUtils.strToDate(String.valueOf(obj[3])), 1));
                }
                List<Long> dataList = JpaUtils.findAll(jpql2, paramLs2);
                portCar.setCksl(dataList.get(0).toString());
                if (portCar.getInCyTim() != null && portCar.getOutCyTim() != null) {
                    int days = (int) ((portCar.getOutCyTim().getTime() - portCar.getInCyTim().getTime())
                            / (1000 * 3600 * 24)) + 1;
                    if (days >= 0)
                        portCar.setDcts(String.valueOf(days));
                }
                portCar.setCyBayNo("");
                portCar.setCyRowNo("");
                portCar.setCyPlac("");
                allList.add(portCar);
            }
        }
        HdEzuiDatagridData result = new HdEzuiDatagridData();
        result.setRows(allList);
        return result;
    }

    @Override
    public HdMessageCode save(HdEzuiSaveDatagridData<PortCar> hdEzuiSaveDatagridData, String ingateId, String gateNo) {
        GateTruck gateTruck = JpaUtils.findById(GateTruck.class, ingateId);
        if (gateTruck != null) {
            if ("0".equals(gateTruck.getFinishedId())) {
                throw new HdRunTimeException("此拖车没有作业完成，不能出闸确认！");
            } else {
                gateTruck.setOutGatNo(gateNo);
                gateTruck.setOutGatTim(HdUtils.getDateTime());
                gateTruck.setOutGatNam(HdUtils.getCurUser().getAccount());
                JpaUtils.save(gateTruck);
                GateTruckHis gateTruckHis = new GateTruckHis();
                BeanUtils.copyProperties(gateTruck, gateTruckHis);
                JpaUtils.save(gateTruckHis);

                String truckNo = gateTruck.getTruckNo();
                JpaUtils.remove(gateTruck);

                String jpql = "select a from TruckWork a where a.ingateId =:ingateId";
                QueryParamLs paramLs = new QueryParamLs();
                paramLs.addParam("ingateId", ingateId);

                String jpql1 = "select a from WorkCommand a where a.finishedId='1' and a.workTyp in ('TO','TI') and a.truckNo=:truckNo";
                QueryParamLs paramLs1 = new QueryParamLs();
                paramLs1.addParam("truckNo", truckNo);
                List<WorkCommand> workCommandList = JpaUtils.findAll(jpql1, paramLs1);
                if (workCommandList.size() > 0) {
                    WorkCommand workCommand = workCommandList.get(0);
                    WorkCommandBak workCommandBak = new WorkCommandBak();
                    BeanUtils.copyProperties(workCommand, workCommandBak);
                    JpaUtils.save(workCommandBak);
                    JpaUtils.remove(workCommand);
                }

                GateTruckContractHis gateTruckContractHis = new GateTruckContractHis();
                String jpql2 = "select a from GateTruckContract a where a.ingateId =:ingateId";
                QueryParamLs paramLs2 = new QueryParamLs();
                paramLs.addParam("ingateId", ingateId);
                List<GateTruckContract> gateTruckContractList = JpaUtils.findAll(jpql2, paramLs2);
                if (gateTruckContractList.size() > 0) {
                    BeanUtils.copyProperties(gateTruckContractList.get(0), gateTruckContractHis);
                    JpaUtils.save(gateTruckContractHis);
                    JpaUtils.remove(gateTruckContractList.get(0));
                }

                String jpql3 = "select a from TruckWork a where a.workTyp='TO' and a.ingateId=:ingateId";
                QueryParamLs paramLs3 = new QueryParamLs();
                paramLs3.addParam("ingateId", ingateId);

                String jpql4 = "select a from WorkQueue a where a.workTyp in ('TO','TI') and a.truckNo =:truckNo";
                QueryParamLs paramLs4 = new QueryParamLs();
                paramLs4.addParam("truckNo", truckNo);
                List<WorkQueue> workQueueList = JpaUtils.findAll(jpql4, paramLs4);
                JpaUtils.removeAll(workQueueList);

            }
        }
        return HdUtils.genMsg();
    }

    @Transactional
    public void removeAll(String ids) {
        List<String> idList = HdUtils.paraseStrs(ids);
        for (String id : idList) {
            JpaUtils.remove(PortCar.class, id);
        }
    }

    @Transactional
    public void changeAll(String portCarNos) {
        List<String> idList = HdUtils.paraseStrs(portCarNos);
        for (String id : idList) {
            PortCar bean = JpaUtils.findById(PortCar.class, new BigDecimal(id));
            bean.setCurrentStat("5");
            JpaUtils.update(bean);
        }
    }

    @Transactional
    public void confrimAuto(String shipNo) {
        Ship ship = JpaUtils.findById(Ship.class, shipNo);
        if (ship == null) {
            throw new HdRunTimeException("此船无数据！");
        }
        String jpql = "select a.billNo bno, count(a.portCarNo) pno from PortCar a where a.shipNo =:shipNo and a.currentStat =:currentStat group by a.billNo";
        QueryParamLs paramLs = new QueryParamLs();
        paramLs.addParam("shipNo", shipNo);
        paramLs.addParam("currentStat", "2");
        List<Object[]> list = JpaUtils.findAll(jpql, paramLs);
        for (Object[] obj : list) {
            String sql = "select a from ShipBill a where a.billNo =:billNo and a.shipNo =:shipNo";
            QueryParamLs param = new QueryParamLs();
            param.addParam("billNo", obj[0]);
            param.addParam("shipNo", shipNo);
            List<ShipBill> shipBilllist = JpaUtils.findAll(sql, param);
            if (shipBilllist.size() > 0) {
                ShipBill bean = shipBilllist.get(0);
                if (bean.getPieces() != null) {
                    if (bean.getPieces().compareTo(new BigDecimal(obj[1].toString())) == 0) {
                        String jqpl2 = "select a from PortCar a where a.iEId =:iEId and a.currentStat =:currentStat and a.shipNo =:shipNo and a.billNo =:billNo";
                        QueryParamLs paramLs2 = new QueryParamLs();
                        paramLs2.addParam("iEId", "E");
                        paramLs2.addParam("currentStat", "2");
                        paramLs2.addParam("shipNo", shipNo);
                        paramLs2.addParam("billNo", obj[0]);
                        List<PortCar> portCarList = JpaUtils.findAll(jqpl2, paramLs2);
                        for (PortCar portCar : portCarList) {
                            portCar.setIsTiComplete("1");
                            JpaUtils.update(portCar);
                        }
                    }
                }
            }
            // 校验海关放行
            Axis2Util axis2Util = new Axis2Util();
            try {
                axis2Util.getCustomRelease(shipNo, obj[0].toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Transactional
    public void confrimJq(String portCarNo, String type) {
        if (HdUtils.strNotNull(portCarNo)) {
            PortCar portCar = JpaUtils.findById(PortCar.class, new BigDecimal(portCarNo));
            if (portCar != null) {
                String jqpl2 = "select a from PortCar a where a.iEId = 'E'";
                QueryParamLs paramLs2 = new QueryParamLs();
                String shipNo = portCar.getShipNo();
                String billNo = portCar.getBillNo();
                if (HdUtils.strNotNull(shipNo)) {
                    jqpl2 += " and a.shipNo =:shipNo";
                    paramLs2.addParam("shipNo", shipNo);
                }
                if (HdUtils.strNotNull(billNo)) {
                    jqpl2 += " and a.billNo =:billNo";
                    paramLs2.addParam("billNo", billNo);
                }
                List<PortCar> portCarList = JpaUtils.findAll(jqpl2, paramLs2);
                for (PortCar bean : portCarList) {
                    if ("jq".equals(type)) {
                        bean.setIsTiComplete("1");
                    } else if ("qxjq".equals(type)) {
                        bean.setIsTiComplete("0");
                    }
                    JpaUtils.update(bean);
                }
            }
        }
    }

    @Override
    public PortCar findone(String id) {
        BigDecimal portcarno = new BigDecimal(id);
        PortCar portCar = JpaUtils.findById(PortCar.class, portcarno);
        return portCar;
    }

    @Override
    public String findDchz(String cyAreaNo) {
        String jpql = "select a from PortCar a where a.currentStat = '2' ";
        QueryParamLs paramLs = new QueryParamLs();
        if (HdUtils.strNotNull(cyAreaNo)) {
            jpql += "and a.cyAreaNo =:cyAreaNo";
            paramLs.addParam("cyAreaNo", cyAreaNo);
        }
        List<PortCar> list = JpaUtils.findAll(jpql, paramLs);
        JSONObject json = new JSONObject();
        json.put("list", list);
        return json.toString();
    }

    @Override
    public HdMessageCode saveone(@RequestBody PortCar portCar) {
        BigDecimal portCarNo = portCar.getPortCarNo();
        PortCar portcar = JpaUtils.findById(PortCar.class, portCarNo);
        if (portcar != null) {
            JpaUtils.update(portCar);
        } else {
            String vinNo = portCar.getVinNo();
            String rfidCardNo = portCar.getRfidCardNo();
            String jpql = "select a from PortCar a where a.vinNo =:vinNo";
            QueryParamLs paramLs = new QueryParamLs();
            paramLs.addParam("vinNo", vinNo);
            List<PortCar> portCarList = JpaUtils.findAll(jpql, paramLs);
            if (portCarList.size() > 0) {
                throw new HdRunTimeException("此车已经在场，不可再次入场！");
            }
            String jpql1 = "select a from PortCar a where a.rfidCardNo =:rfidCardNo";
            QueryParamLs paramLs1 = new QueryParamLs();
            paramLs1.addParam("rfidCardNo", rfidCardNo);
            List<PortCar> portCarList1 = JpaUtils.findAll(jpql1, paramLs1);
            if (portCarList1.size() > 0) {
                throw new HdRunTimeException("此rfid卡号已经使用，不可再次使用！");
            }
            JpaUtils.save(portCar);
        }
        return HdUtils.genMsg();
    }

    @Override
    public HdEzuiDatagridData findZC(HdQuery hdQuery) {
        String jpql = "select a from PortCar a where 1=1 and a.rfidCardNo is not null ";
        String billNo = hdQuery.getStr("billNo");
        String cyAreaNo = hdQuery.getStr("cyAreaNo");
        String brandCod = hdQuery.getStr("brandCod");
        // String shipNo = hdQuery.getStr("shipNo");
        String consignCod = hdQuery.getStr("consignCod");
        String dockCod = hdQuery.getStr("dockCod");
        QueryParamLs paramLs = new QueryParamLs();
        // if (HdUtils.strNotNull(shipNo)) {
        // jpql += " and a.shipNo =:shipNo ";
        // paramLs.addParam("shipNo", shipNo);
        // }
        if (HdUtils.strNotNull(cyAreaNo)) {
            jpql += "and a.cyPlac like :cyPlac";
            paramLs.addParam("cyPlac", "%" + cyAreaNo + "%");
        }
        if (HdUtils.strNotNull(billNo)) {
            jpql += " and a.billNo like :billNo ";
            paramLs.addParam("billNo", '%' + billNo + '%');
        }
        if (HdUtils.strNotNull(brandCod)) {
            jpql += " and a.brandCod =:brandCod ";
            paramLs.addParam("brandCod", brandCod);
        }
        if (HdUtils.strNotNull(consignCod)) {
            jpql += " and a.consignCod =:consignCod ";
            paramLs.addParam("consignCod", consignCod);
        }
        if (HdUtils.strNotNull(dockCod)) {
            jpql += " and a.dockCod =:dockCod ";
            paramLs.addParam("dockCod", dockCod);
        }
        jpql += "order by a.portCarNo asc";
        HdEzuiDatagridData results = JpaUtils.findByEz(jpql, paramLs, hdQuery);
        List<PortCar> portCarList = results.getRows();
        if (portCarList.size() > 0) {
            for (PortCar pc : portCarList) {
                if (HdUtils.strNotNull(pc.getShipNo())) {
                    Ship s = JpaUtils.findById(Ship.class, pc.getShipNo());
                    pc.setcShipNam(s.getcShipNam());
                    pc.setVoyage(s.getIvoyage() + '/' + s.getEvoyage());
                }
                if (HdUtils.strNotNull(pc.getBrandCod())) {
                    CBrand cbrand = JpaUtils.findById(CBrand.class, pc.getBrandCod());
                    pc.setBrandNam(cbrand.getBrandNam());
                }
                if (HdUtils.strNotNull(pc.getTranPortCod())) {
                    String jpqlc = "select a from CPort a where a.portCod=:portCod";
                    QueryParamLs paramLsc = new QueryParamLs();
                    paramLsc.addParam("portCod", pc.getTranPortCod());
                    List<CPort> cportList = JpaUtils.findAll(jpqlc, paramLsc);
                    if (cportList.size() > 0) {
                        pc.setTranPortNam(cportList.get(0).getcPortNam());
                    }
                }
                if (HdUtils.strNotNull(pc.getDiscPortCod())) {
                    String jpqlc = "select a from CPort a where a.portCod=:portCod";
                    QueryParamLs paramLsc = new QueryParamLs();
                    paramLsc.addParam("portCod", pc.getDiscPortCod());
                    List<CPort> cportList = JpaUtils.findAll(jpqlc, paramLsc);
                    if (cportList.size() > 0) {
                        pc.setDiscPortNam(cportList.get(0).getcPortNam());
                    }
                }
                if (HdUtils.strNotNull(pc.getCarTyp())) {
                    CCarTyp ccartyp = JpaUtils.findById(CCarTyp.class, pc.getCarTyp());
                    pc.setCarTypNam(ccartyp.getCarTypNam());
                }
            }
        }
        return results;
    }

    @Override
    public Map<String, Object> findZCGroup() {
        String sql = "select count(1) car_num,C.BRAND_NAM from port_car s ,c_brand c where  S.BRAND_COD=C.BRAND_COD and S.CURRENT_STAT='2' group by C.BRAND_NAM order by count(1) desc";
        List<Map<String, Object>> list = JpaUtils.getEntityManager().createNativeQuery(sql)
                .setHint(QueryHints.RESULT_TYPE, ResultType.Map).getResultList();
        List<String> brandNams = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            // 取数量前十的品牌
            if (list.get(i).get("BRAND_NAM") != null && brandNams.size() < 10)
                brandNams.add(list.get(i).get("BRAND_NAM").toString());
        }
        sql = "select count(1) car_num,C.BRAND_NAM,d.DOCK_NAM from port_car s ,c_brand c,C_DOCK d where s.DOCK_COD=d.DOCK_COD and S.BRAND_COD=C.BRAND_COD and S.CURRENT_STAT='2' group by d.DOCK_NAM, S.BRAND_COD,C.BRAND_NAM order by count(1) desc";
        list = JpaUtils.getEntityManager().createNativeQuery(sql).setHint(QueryHints.RESULT_TYPE, ResultType.Map)
                .getResultList();

        List<Map<String, Object>> list_hq = new ArrayList<>();
        List<Map<String, Object>> list_gz = new ArrayList<>();

        Map<String, Object> otherMap_hq = new HashMap<>();
        Map<String, Object> otherMap_gz = new HashMap<>();
        int carNum_hq = 0;
        int carNum_gz = 0;
        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> map = list.get(i);
            Map<String, Object> addmap = new HashMap<>();
            int thisCarNum = Integer.parseInt(map.get("CAR_NUM") == null ? "0" : map.get("CAR_NUM").toString());
            if (map.get("DOCK_NAM").toString().indexOf("环球") >= 0 && brandNams.contains(map.get("BRAND_NAM"))) {
                addmap.put("name", map.get("BRAND_NAM"));
                addmap.put("value", map.get("CAR_NUM"));
                list_hq.add(addmap);
            }
            if (map.get("DOCK_NAM").toString().indexOf("环球") >= 0
                    && (map.get("BRAND_NAM") == null || !brandNams.contains(map.get("BRAND_NAM")))) {
                carNum_hq += thisCarNum;
            }

            if (map.get("DOCK_NAM").toString().indexOf("滚装") >= 0 && brandNams.contains(map.get("BRAND_NAM"))) {
                addmap.put("name", map.get("BRAND_NAM"));
                addmap.put("value", map.get("CAR_NUM"));
                list_gz.add(addmap);
            }
            if (map.get("DOCK_NAM").toString().indexOf("滚装") >= 0
                    && (map.get("BRAND_NAM") == null || !brandNams.contains(map.get("BRAND_NAM")))) {
                carNum_gz += thisCarNum;
            }
            /*
             * map.remove("BRAND_NAM"); map.remove("CAR_NUM");
             */
            // map.remove("DOCK_NAM");
        }
        otherMap_hq.put("value", carNum_hq);
        otherMap_hq.put("name", "其它");
        list_hq.add(otherMap_hq);
        otherMap_gz.put("value", carNum_gz);
        otherMap_gz.put("name", "其它");
        list_gz.add(otherMap_gz);
        brandNams.add("其它");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("legend", brandNams);
        map.put("hq", list_hq);
        map.put("gz", list_gz);

        /*
         * Map<String,Object> map=new HashMap<String, Object>();
         *
         * Map<String,Object> titleMap=new HashMap<String, Object>();
         * titleMap.put("text", "库存统计"); Map<String,Object> textStyleMap=new
         * HashMap<String, Object>(); textStyleMap.put("fontSize", 10);
         * textStyleMap.put("color", "#fff"); titleMap.put("textStyle",
         * textStyleMap); //map.put("title", titleMap);
         *
         * Map<String,Object> tooltipMap=new HashMap<String, Object>();
         * tooltipMap.put("trigger", "item"); tooltipMap.put("formatter",
         * "{a} <br/>{b}:{d}%<br/>({c})"); //map.put("tooltip", tooltipMap);
         *
         * Map<String,Object> legendMap=new HashMap<String, Object>();
         * legendMap.put("orient", "vertical"); legendMap.put("x", "right");
         * legendMap.put("data", brandNams); Map<String,Object>
         * legendtextStyleMap=new HashMap<String, Object>();
         * legendtextStyleMap.put("color", "#fff"); legendMap.put("textStyle",
         * legendtextStyleMap); map.put("legend", legendMap);
         *
         *
         * List<Map<String,Object>> seriesList=new ArrayList<>();
         * Map<String,Object> seriesMap1=new HashMap<String, Object>();
         * seriesMap1.put("name", "环球"); seriesMap1.put("type", "pie");
         * seriesMap1.put("selectedMode", "single"); List<Object> radiuslist=new
         * ArrayList<>(); radiuslist.add(100); radiuslist.add(150);
         * seriesMap1.put("radius", radiuslist); List<Object> centerlist=new
         * ArrayList<>(); centerlist.add("25%"); centerlist.add("50%");
         * seriesMap1.put("center", centerlist);
         *//** label **/
        /*
         * Map<String,Object> normalMap=new HashMap<>(); normalMap.put("show",
         * false); Map<String,Object> labelMap=new HashMap<>();
         * labelMap.put("normal", normalMap); seriesMap1.put("label", labelMap);
         *//** labelLine **/
        /*
         * normalMap=new HashMap<>(); normalMap.put("show", false);
         * Map<String,Object> labelLineMap=new HashMap<>();
         * labelLineMap.put("normal", normalMap); seriesMap1.put("labelLine",
         * labelLineMap);
         *//** data **//*
         * seriesMap1.put("data", list_hq);
         * seriesList.add(seriesMap1);
         *
         * seriesMap1=new HashMap<String, Object>();
         * seriesMap1.put("name", "滚装"); seriesMap1.put("type",
         * "pie"); List<Object> radiusCargolist=new
         * ArrayList<>(); radiusCargolist.add(100);
         * radiusCargolist.add(150); seriesMap1.put("radius",
         * radiusCargolist); List<Object> centerlist2=new
         * ArrayList<>(); centerlist2.add("75%");
         * centerlist2.add("50%"); seriesMap1.put("center",
         * centerlist2); normalMap=new HashMap<>();
         * normalMap.put("formatter",
         * "{a|{a}}{abg|}\n{hr|}\n  {b|{b}：}{c}  {per|{d}%}");
         * normalMap.put("backgroundColor", "#eee");
         * normalMap.put("borderColor", "#aaa");
         * normalMap.put("borderWidth", 1);
         * normalMap.put("borderRadius",4); Map<String,Object>
         * richMap=new HashMap<>(); Map<String,Object> aMap=new
         * HashMap<>(); aMap.put("color","#999");
         * aMap.put("lineHeight",22);
         * aMap.put("align","center"); richMap.put("a", aMap);
         * Map<String,Object> hrMap=new HashMap<>();
         * hrMap.put("borderColor","#aaa");
         * hrMap.put("width","100%");
         * hrMap.put("borderWidth",0.5); hrMap.put("height",0);
         * richMap.put("hr", hrMap); Map<String,Object> bMap=new
         * HashMap<>(); bMap.put("fontSize",16);
         * bMap.put("lineHeight",33); richMap.put("b", bMap);
         * Map<String,Object> perMap=new HashMap<>();
         * perMap.put("color","#eee");
         * perMap.put("backgroundColor","#334455");
         * List<Integer> paddinglist=new ArrayList<>();
         * paddinglist.add(2); paddinglist.add(4);
         * perMap.put("padding",paddinglist);
         * perMap.put("borderRadius",2); richMap.put("per",
         * perMap); normalMap.put("rich",richMap);
         * seriesMap1.put("normal", normalMap);
         * seriesMap1.put("data", list_gz);
         * seriesList.add(seriesMap1); map.put("series",
         * seriesList);
         */
        return map;
    }

    @Override
    public HashMap<String, String> findDchzcl(String cyAreaNo) {
        String jpql = "select b.cyAreaNo,b.iEId,b.brandCod, b.carTyp, b.carKind, b.shipNo,count(b.portCarNo)"
                + " from  PortCar b where 1=1 ";
        QueryParamLs paramLs = new QueryParamLs();
        if (HdUtils.strNotNull(cyAreaNo)) {
            jpql += "and b.cyAreaNo =:cyAreaNo";
            paramLs.addParam("cyAreaNo", cyAreaNo);
        }
        jpql += " group by b.cyAreaNo,b.iEId, b.brandCod, b.carTyp, b.carKind, b.shipNo ";
        List<Object[]> objList = JpaUtils.findAll(jpql, paramLs);
        HashMap<String, String> hashmap = new HashMap<String, String>();
        if (objList.size() > 0) {
            for (Object[] obj : objList) {
                PortCar pc = new PortCar();
                pc.setCyAreaNo((String) obj[0]);
                if (HdUtils.strNotNull((String) obj[2])) {
                    CBrand cb = JpaUtils.findById(CBrand.class, (String) obj[2]);
                    pc.setBrandNam(cb.getBrandNam());
                } else {
                    pc.setBrandNam("");
                }
                if (HdUtils.strNotNull((String) obj[4])) {
                    CCarKind cck = JpaUtils.findById(CCarKind.class, (String) obj[4]);
                    pc.setCarKindNam(cck.getCarKindNam());
                } else {
                    pc.setCarKindNam("");
                }
                if ((HdUtils.strIsNull((String) obj[2])) && (HdUtils.strIsNull((String) obj[4]))) {
                    pc.setBrandNam("设备");
                }
                if (HdUtils.strNotNull((String) obj[3])) {
                    CCarTyp cct = JpaUtils.findById(CCarTyp.class, (String) obj[3]);
                    pc.setCarTypNam(pc.getCarTypNam());
                }
                if (HdUtils.strNotNull((String) obj[5])) {
                    Ship ship = JpaUtils.findById(Ship.class, (String) obj[5]);
                    pc.setcShipNam(ship.getcShipNam());
                }
                if (hashmap.containsKey(pc.getCyAreaNo())) {
                    hashmap.put(pc.getCyAreaNo(), hashmap.get(pc.getCyAreaNo()) + '(' + pc.getcShipNam() + ')'
                            + pc.getBrandNam() + pc.getCarKindNam() + (Long) obj[6] + "辆" + "\n");
                } else {
                    hashmap.put(pc.getCyAreaNo(), '(' + pc.getcShipNam() + ')' + pc.getBrandNam() + pc.getCarKindNam()
                            + (Long) obj[6] + "辆" + "\n");
                }
            }
            // System.out.println(hashmap);
        }

        return hashmap;
    }

    @Override
    public HashMap<String, String> getDetail(String date, String dockCod, String tradeId) {
        String datetime = date + " 08:00";
        // 用map存数据
        HashMap<String, Map<String, String>> map = new HashMap<String, Map<String, String>>();
        // 外贸小车
        String jpql = "select b.cyAreaNo,b.shipNo,b.iEId,count(b.portCarNo)" + " from  PortCar b,CCyArea a "
                + " where b.tradeId = '2' and a.dockCod=:dockCod and b.carKind in ('0827155757','0710104337','02','01') and a.cyAreaNo = b.cyAreaNo "
                + " and ((b.outCyTim >:begTim and b.outCyTim <:endTim) or b.outCyTim is null) "
                + " group by b.cyAreaNo,b.shipNo,b.iEId ";

        QueryParamLs paramLs = new QueryParamLs();
        paramLs.addParam("endTim", HdUtils.strToDateTime(datetime));
        paramLs.addParam("begTim", HdUtils.addDay(HdUtils.strToDateTime(datetime), -1));
        paramLs.addParam("dockCod", dockCod);
        List<Object[]> objList = JpaUtils.findAll(jpql, paramLs);
        for (Object[] obj : objList) {
            if (map.containsKey(obj[0])) {
                if (map.get(obj[0]).containsKey(obj[1])) {
                    if (Ship.JK.equals(obj[2])) {
                        map.get(obj[0]).put(obj[1].toString(), map.get(obj[0]).get(obj[1]) + "进口小车：" + obj[3] + "辆");
                    } else {
                        map.get(obj[0]).put(obj[1].toString(), map.get(obj[0]).get(obj[1]) + "出口小车：" + obj[3] + "辆");
                    }

                } else {
                    if (Ship.JK.equals(obj[2])) {
                        map.get(obj[0]).put(obj[1].toString(), "进口小车：" + obj[3] + "辆");
                    } else {
                        map.get(obj[0]).put(obj[1].toString(), "出口小车：" + obj[3] + "辆");
                    }
                }
            } else {
                if (Ship.JK.equals(obj[2])) {
                    HashMap<String, String> mapBean = new HashMap<String, String>();
                    mapBean.put(obj[1].toString(), "进口小车：" + obj[3] + "辆");
                    map.put(obj[0].toString(), mapBean);
                } else {
                    HashMap<String, String> mapBean = new HashMap<String, String>();
                    mapBean.put(obj[1].toString(), "出口小车：" + obj[3] + "辆");
                    map.put(obj[0].toString(), mapBean);
                }
            }
        }
        // 外贸大车
        String jpql1 = "select b.cyAreaNo,b.shipNo,b.iEId,count(b.portCarNo)" + " from  PortCar b,CCyArea a "
                + " where b.tradeId = '2' and a.dockCod=:dockCod and b.carKind not in ('0827155757','0710104337','02','01') and a.cyAreaNo = b.cyAreaNo "
                + " and ((b.outCyTim >:begTim and b.outCyTim <:endTim) or b.outCyTim is null) "
                + " group by b.cyAreaNo,b.shipNo,b.iEId ";
        List<Object[]> objList1 = JpaUtils.findAll(jpql1, paramLs);
        for (Object[] obj : objList1) {
            if (map.containsKey(obj[0])) {
                if (map.get(obj[0]).containsKey(obj[1])) {
                    if (Ship.JK.equals(obj[2])) {
                        map.get(obj[0]).put(obj[1].toString(), map.get(obj[0]).get(obj[1]) + "进口大车：" + obj[3] + "辆");
                    } else {
                        map.get(obj[0]).put(obj[1].toString(), map.get(obj[0]).get(obj[1]) + "出口大车：" + obj[3] + "辆");
                    }

                } else {
                    if (Ship.JK.equals(obj[2])) {
                        map.get(obj[0]).put(obj[1].toString(), "进口大车：" + obj[3] + "辆");
                    } else {
                        map.get(obj[0]).put(obj[1].toString(), "出口大车：" + obj[3] + "辆");
                    }
                }
            } else {
                if (Ship.JK.equals(obj[2])) {
                    HashMap<String, String> mapBean = new HashMap<String, String>();
                    mapBean.put(obj[1].toString(), "进口大车：" + obj[3] + "辆");
                    map.put(obj[0].toString(), mapBean);
                } else {
                    HashMap<String, String> mapBean = new HashMap<String, String>();
                    mapBean.put(obj[1].toString(), "出口大车：" + obj[3] + "辆");
                    map.put(obj[0].toString(), mapBean);
                }
            }
        }
        // 内贸出口(不包含一汽丰田)
        String jpql2 = "select b.cyAreaNo,b.shipNo,a.brandNam,count(b.portCarNo)"
                + " from VWorkCommand a, PortCar b where b.dockCod=:dockCod and b.cyAreaNo is not null and a.brandCod != '0121132701' and b.tradeId = '1' and a.portCarNo = b.portCarNo and a.workTypNam = '集港' and ((a.outCyTim >:begTim and a.outCyTim <:endTim) or a.outCyTim is null) "
                + " group by b.cyAreaNo,b.shipNo,a.brandNam";

        List<Object[]> objList2 = JpaUtils.findAll(jpql2, paramLs);
        for (Object[] obj : objList2) {
            if (map.containsKey(obj[0])) {
                if (map.get(obj[0]).containsKey(obj[1])) {
                    map.get(obj[0]).put(obj[1].toString(),
                            map.get(obj[0]).get(obj[1]) + "集" + obj[2] + "：" + obj[3] + "辆");
                } else {
                    map.get(obj[0]).put(obj[1].toString(), "集" + obj[2] + "：" + obj[3] + "辆");
                }
            } else {
                HashMap<String, String> mapBean = new HashMap<String, String>();
                mapBean.put(obj[1].toString(), "集" + obj[2] + "：" + obj[3] + "辆");
                map.put(obj[0].toString(), mapBean);
            }
        }
        //一汽丰田进口
        String jpql3 = "select b.cyAreaNo,b.shipNo,a.carTypNam,count(b.portCarNo)"
                + " from VWorkCommand a, PortCar b where b.dockCod=:dockCod and b.cyAreaNo is not null and  a.brandCod = '0121132701' and b.tradeId = '1' and a.portCarNo = b.portCarNo and a.workTypNam = '集港' and ((a.outCyTim >:begTim and a.outCyTim <:endTim) or a.outCyTim is null) "
                + " group by b.cyAreaNo,b.shipNo,a.carTypNam";

        List<Object[]> objList3 = JpaUtils.findAll(jpql3, paramLs);
        for (Object[] obj : objList3) {
            if (map.containsKey(obj[0])) {
                if (map.get(obj[0]).containsKey(obj[1])) {
                    map.get(obj[0]).put(obj[1].toString(),
                            map.get(obj[0]).get(obj[1]) + "集" + obj[2] + "：" + obj[3] + "辆");
                } else {
                    map.get(obj[0]).put(obj[1].toString(), "集" + obj[2] + "：" + obj[3] + "辆");
                }
            } else {
                HashMap<String, String> mapBean = new HashMap<String, String>();
                mapBean.put(obj[1].toString(), "集" + obj[2] + "：" + obj[3] + "辆");
                map.put(obj[0].toString(), mapBean);
            }
        }
        //内贸进口(不包含广汽丰田)
        String jpql4 = "select b.cyAreaNo,b.shipNo,a.brandNam,count(b.portCarNo)"
                + " from VWorkCommand a, PortCar b where b.dockCod=:dockCod and b.cyAreaNo is not null and a.brandCod != '0121132550' and b.tradeId = '1' and a.portCarNo = b.portCarNo and a.workTypNam = '卸船' and ((a.outCyTim >:begTim and a.outCyTim <:endTim) or a.outCyTim is null) "
                + " group by b.cyAreaNo,b.shipNo,a.brandNam";

        List<Object[]> objList4 = JpaUtils.findAll(jpql4, paramLs);
        for (Object[] obj : objList4) {
            if (map.containsKey(obj[0])) {
                if (map.get(obj[0]).containsKey(obj[1])) {
                    map.get(obj[0]).put(obj[1].toString(),
                            map.get(obj[0]).get(obj[1]) + "卸船" + obj[2] + "：" + obj[3] + "辆");
                } else {
                    map.get(obj[0]).put(obj[1].toString(), "卸船" + obj[2] + "：" + obj[3] + "辆");
                }
            } else {
                HashMap<String, String> mapBean = new HashMap<String, String>();
                mapBean.put(obj[1].toString(), "卸船" + obj[2] + "：" + obj[3] + "辆");
                map.put(obj[0].toString(), mapBean);
            }
        }
        //内贸进口广汽丰田
        String jpql5 = "select b.cyAreaNo,b.shipNo,a.carTypNam,count(b.portCarNo)"
                + " from VWorkCommand a, PortCar b where b.dockCod=:dockCod and b.cyAreaNo is not null and a.brandCod = '0121132550' and b.tradeId = '1' and a.portCarNo = b.portCarNo and a.workTypNam = '卸船' and ((a.outCyTim >:begTim and a.outCyTim <:endTim) or a.outCyTim is null) "
                + " group by b.cyAreaNo,b.shipNo,a.carTypNam";

        List<Object[]> objList5 = JpaUtils.findAll(jpql5, paramLs);
        for (Object[] obj : objList5) {
            if (map.containsKey(obj[0])) {
                if (map.get(obj[0]).containsKey(obj[1])) {
                    map.get(obj[0]).put(obj[1].toString(),
                            map.get(obj[0]).get(obj[1]) + "卸船" + obj[2] + "：" + obj[3] + "辆");
                } else {
                    map.get(obj[0]).put(obj[1].toString(), "卸船" + obj[2] + "：" + obj[3] + "辆");
                }
            } else {
                HashMap<String, String> mapBean = new HashMap<String, String>();
                mapBean.put(obj[1].toString(), "卸船" + obj[2] + "：" + obj[3] + "辆");
                map.put(obj[0].toString(), mapBean);
            }
        }
        HashMap<String, String> hashmap = new HashMap<String, String>();
        for (String key : map.keySet()) {
            for (String str : map.get(key).keySet()) {
                Ship ship = JpaUtils.findById(Ship.class, str);
                if (ship != null) {
                    if (hashmap.containsKey(key)) {
                        hashmap.put(key, hashmap.get(key) + "\n" + ship.getcShipNam() + map.get(key).get(str));
                    } else {
                        hashmap.put(key, ship.getcShipNam() + map.get(key).get(str));
                    }

                }
            }
        }


        return hashmap;
    }

    @Override
    public HashMap<String, String> getJsg(String date) {
        String datetime = date + " 08:00";
        // 用map存数据
        HashMap<String, String> map = new HashMap<String, String>();
        // 轿车和吉普 只需要具体到品牌进行统计 轿车车类为01 吉普为02
        String jpql = "select a.shipNo,a.workTypNam,a.brandNam,count(a.queueId),b.tradeId"
                + " from VWorkCommand a, PortCar b where a.portCarNo = b.portCarNo and a.carKind in ('01','02') and a.inCyTim >:begTim and a.inCyTim <:endTim "
                + " group by a.shipNo,a.workTypNam,a.brandNam,b.tradeId ";
        QueryParamLs paramLs = new QueryParamLs();
        paramLs.addParam("endTim", HdUtils.strToDateTime(datetime));
        paramLs.addParam("begTim", HdUtils.addDay(HdUtils.strToDateTime(datetime), -1));
        List<Object[]> objList = JpaUtils.findAll(jpql, paramLs);

        if (objList.size() > 0) {
            for (Object[] obj : objList) {
                if (Ship.NM.equals(obj[4].toString()) && "集港".equals(obj[1].toString())) {
                    if (map.containsKey("集港")) {
                        map.put("集港", map.get("集港") + obj[2].toString() + ": " + obj[3] + "辆");
                    } else {
                        map.put("集港", obj[2].toString() + ": " + obj[3] + "辆");
                    }
                } else if (Ship.NM.equals(obj[4].toString()) && "疏港".equals(obj[1].toString())) {
                    if (map.containsKey("疏港")) {
                        map.put("疏港", map.get("疏港") + obj[2].toString() + ": " + obj[3] + "辆");
                    } else {
                        map.put("疏港", obj[2].toString() + ": " + obj[3] + "辆");
                    }
                } else if (map.containsKey(obj[0].toString())) {
                    map.put(obj[0].toString(),
                            map.get(obj[0].toString()) + " " + obj[1] + obj[2].toString() + ": " + obj[3] + "辆");
                } else {
                    map.put(obj[0].toString(), obj[1] + obj[2].toString() + ": " + obj[3] + "辆");
                }
            }
        }

        String jpql1 = "select a.shipNo,a.workTypNam,a.carTypNam,count(a.queueId),b.tradeId"
                + " from VWorkCommand a, PortCar b where a.portCarNo = b.portCarNo and a.carKind not in ('01','02') and a.inCyTim >:begTim and a.inCyTim <:endTim "
                + " group by a.shipNo,a.workTypNam,a.carTypNam,b.tradeId ";

        List<Object[]> objList1 = JpaUtils.findAll(jpql1, paramLs);
        if (objList1.size() > 0) {
            for (Object[] obj : objList1) {
                if (Ship.NM.equals(obj[3].toString()) && "集港".equals(obj[1].toString())) {
                    if (map.containsKey("集港")) {
                        map.put("集港", map.get("集港") + obj[2].toString() + ": " + obj[3] + "辆");
                    } else {
                        map.put("集港", obj[2].toString() + ": " + obj[3] + "辆");
                    }
                } else if (Ship.NM.equals(obj[3].toString()) && "疏港".equals(obj[1].toString())) {
                    if (map.containsKey("疏港")) {
                        map.put("疏港", map.get("疏港") + obj[2].toString() + ": " + obj[3] + "辆");
                    } else {
                        map.put("疏港", obj[2].toString() + ": " + obj[3] + "辆");
                    }
                } else if (map.containsKey(obj[0].toString())) {
                    map.put(obj[0].toString(),
                            map.get(obj[0].toString()) + " " + obj[1] + obj[2].toString() + ": " + obj[3] + "辆");
                } else {
                    map.put(obj[0].toString(), obj[1] + obj[2].toString() + ": " + obj[3] + "辆");
                }
            }
        }
        String str = "";
        HashMap<String, String> map1 = new HashMap<String, String>();
        for (String key : map.keySet()) {
            // System.out.println("key= "+key+" and value= "+map.get(key));
            if (!key.endsWith("港")) {
                Ship ship = JpaUtils.findById(Ship.class, key);
                if (ship != null) {
                    if (map1.containsKey("JSG")) {
                        map1.put("JSG", map1.get("JSG") + "\n" + ship.getcShipNam() + map.get(key));
                    } else {
                        map1.put("JSG", ship.getcShipNam() + map.get(key));
                    }
                }
            } else {
                if (map1.containsKey("JSG")) {
                    map1.put("JSG", map1.get("JSG") + "\n" + key + "：" + map.get(key));
                } else {
                    map1.put("JSG", key + "：" + map.get(key));
                }
            }
        }
        // 统计总计数据

        // 进场总车数
        String jpql2 = "select count(a.queueId) from VWorkCommand a where a.carKind != '04' and a.workTyp in ('TI','SI') and a.inCyTim >:begTim and a.inCyTim <:endTim";
        Long jczcs = JpaUtils.single(jpql2, paramLs);

        // 进场总件数
        String jpql3 = "select count(a.queueId) from VWorkCommand a where a.carKind = '04' and a.workTyp in ('TI','SI') and a.inCyTim >:begTim and a.inCyTim <:endTim";
        Long jczjs = JpaUtils.single(jpql3, paramLs);

        // 出场总车数
        String jpql4 = "select count(a.queueId) from VWorkCommand a where a.carKind != '04' and a.workTyp in ('TO','SO','TZ') and a.inCyTim >:begTim and a.inCyTim <:endTim";
        Long cczcs = JpaUtils.single(jpql4, paramLs);

        // 出场总件数
        String jpql5 = "select count(a.queueId) from VWorkCommand a where a.carKind = '04' and a.workTyp in ('TO','SO','TZ') and a.inCyTim >:begTim and a.inCyTim <:endTim";
        Long cczjs = JpaUtils.single(jpql5, paramLs);

        if (map1.containsKey("JSG")) {
            map1.put("JSG", map1.get("JSG") + "\n\n\n\n\n\n\n" + "进场总车数:" + jczcs.toString() + " 进场总件数:" + jczjs.toString() + "\n"
                    + "出场总车数:" + cczcs.toString() + " 出场总件数:" + cczjs.toString());
        }

        return map1;
    }

    @Override
    public HashMap<String, String> getDuiChangDetail(String date) {
        String datetime = date + " 08:00";
        // 首先统计广本的数据
        String jpql = "select a.carTypNam,count(a.portCarNo) from VPortCar a where a.brandCod = 'HONDA' and ((a.outCyTim >:begTim and a.outCyTim <:endTim) or a.outCyTim is null) "
                + "group by a.carTypNam";
        QueryParamLs paramLs = new QueryParamLs();
        paramLs.addParam("endTim", HdUtils.strToDateTime(datetime));
        paramLs.addParam("begTim", HdUtils.addDay(HdUtils.strToDateTime(datetime), -1));
        List<Object[]> objList = JpaUtils.findAll(jpql, paramLs);
        String gbmsg = "广本：";
        for (Object[] obj : objList) {
            gbmsg += obj[0] + "：" + obj[1];
        }
        String jpql1 = "select count(a.portCarNo) from VPortCar a where a.brandCod = '0121132550' and ((a.outCyTim >:begTim and a.outCyTim <:endTim) or a.outCyTim is null) ";
        Long gfmsg = JpaUtils.single(jpql1, paramLs);
        // 内贸数据
        String jpql2 = "select a.shipNo,a.workTypNam,a.carTypNam,count(a.queueId)"
                + " from VWorkCommand a, PortCar b where b.tradeId = '1' and a.portCarNo = b.portCarNo and a.workTypNam in ('集港','卸船') and ((a.outCyTim >:begTim and a.outCyTim <:endTim) or a.outCyTim is null) "
                + " group by a.shipNo,a.workTypNam,a.carTypNam";
        List<Object[]> objList1 = JpaUtils.findAll(jpql2, paramLs);
        HashMap<String, String> map = new HashMap<String, String>();
        for (Object[] obj : objList1) {
            if (map.containsKey(obj[0].toString())) {
                map.put(obj[0].toString(),
                        map.get(obj[0].toString()) + " " + obj[1].toString() + obj[2].toString() + ": " + obj[3] + "辆");
            } else {
                map.put(obj[0].toString(), obj[1].toString() + obj[2].toString() + ": " + obj[3] + "辆");
            }
        }
        // 外贸数据
        String jpql3 = "select a.shipNo,a.workTypNam,a.carTypNam,count(a.queueId)"
                + " from VWorkCommand a, PortCar b where b.tradeId = '2' and a.portCarNo = b.portCarNo and a.workTypNam in ('集港','卸船') and ((a.outCyTim >:begTim and a.outCyTim <:endTim) or a.outCyTim is null) "
                + " group by a.shipNo,a.workTypNam,a.carTypNam";
        List<Object[]> objList2 = JpaUtils.findAll(jpql3, paramLs);
        for (Object[] obj : objList2) {
            if (map.containsKey(obj[0].toString())) {
                map.put(obj[0].toString(),
                        map.get(obj[0].toString()) + " " + obj[1].toString() + obj[2].toString() + ": " + obj[3] + "辆");
            } else {
                map.put(obj[0].toString(), obj[1].toString() + obj[2].toString() + ": " + obj[3] + "辆");
            }
        }
        // 最终结果
        HashMap<String, String> map1 = new HashMap<String, String>();
        map1.put("DCDETAIL", gbmsg + "\n" + "广丰：" + gfmsg.toString());
        for (String key : map.keySet()) {
            // System.out.println("key= "+key+" and value= "+map.get(key));
            Ship ship = JpaUtils.findById(Ship.class, key);
            if (ship != null) {
                map1.put("DCDETAIL", map1.get("DCDETAIL") + "\n" + ship.getcShipNam() + map.get(key));
            }
        }

        return map1;
    }

    @Override
    public HdEzuiDatagridData findZCPL(HdQuery hdQuery) {
        String jpql = "select a.billNo,a.brandCod,a.carTyp,a.cyAreaNo,count(a.billNo),a.shipNo,a.inCyTim from PortCar a where 1=1 ";
        String billNo = hdQuery.getStr("billNo");
        String cyAreaNo = hdQuery.getStr("cyAreaNo");
        String brandCod = hdQuery.getStr("brandCod");
        String shipNo = hdQuery.getStr("shipNo");
        String consignCod = hdQuery.getStr("consignCod");
        String dockCod = hdQuery.getStr("dockCod");
        QueryParamLs paramLs = new QueryParamLs();
        if (HdUtils.strIsNull(shipNo)) {
            jpql += " and a.shipNo like :shipNo ";
            paramLs.addParam("shipNo", "%" + "#123456#" + "%");
        }
        if (HdUtils.strNotNull(shipNo)) {
            jpql += " and a.shipNo =:shipNo ";
            paramLs.addParam("shipNo", shipNo);
        }
        if (HdUtils.strNotNull(cyAreaNo)) {
            jpql += "and a.cyAreaNo like :cyAreaNo";
            paramLs.addParam("cyAreaNo", "%" + cyAreaNo + "%");
        }
        if (HdUtils.strNotNull(billNo)) {
            jpql += " and a.billNo like :billNo ";
            paramLs.addParam("billNo", '%' + billNo + '%');
        }
        if (HdUtils.strNotNull(brandCod)) {
            jpql += " and a.brandCod =:brandCod ";
            paramLs.addParam("brandCod", brandCod);
        }
        if (HdUtils.strNotNull(consignCod)) {
            jpql += " and a.consignCod =:consignCod ";
            paramLs.addParam("consignCod", consignCod);
        }
        if (HdUtils.strNotNull(dockCod)) {
            jpql += " and a.dockCod =:dockCod ";
            paramLs.addParam("dockCod", dockCod);
        }
        jpql += " group by a.brandCod,a.billNo,a.carTyp,a.cyAreaNo,a.shipNo,a.inCyTim ";
        List<PortCar> allList = new ArrayList();
        List<Object[]> objList = JpaUtils.findAll(jpql, paramLs);
        if (objList.size() > 0) {
            for (Object[] obj : objList) {
                String jpqlm = "select a from PortCar a where a.currentStat = '2'";
                QueryParamLs paramLsm = new QueryParamLs();
                if (obj[0] != null) {
                    jpqlm += " and a.billNo =:billNo";
                    paramLsm.addParam("billNo", obj[0]);
                }
                if (obj[1] != null) {
                    jpqlm += " and a.brandCod =:brandCod";
                    paramLsm.addParam("brandCod", obj[1]);
                }
                if (obj[2] != null) {
                    jpqlm += " and a.carTyp =:carTyp";
                    paramLsm.addParam("carTyp", obj[2]);
                }
                if (obj[3] != null) {
                    jpqlm += " and a.cyAreaNo =:cyAreaNo";
                    paramLsm.addParam("cyAreaNo", obj[3]);
                }
                List<PortCar> portCarList = JpaUtils.findAll(jpqlm, paramLsm);
                if (portCarList.size() > 0) {
                    PortCar portcar = portCarList.get(0);
                    portcar.setCksl(obj[4].toString());
                    if (HdUtils.strNotNull(portcar.getShipNo())) {
                        Ship s = JpaUtils.findById(Ship.class, portcar.getShipNo());
                        portcar.setcShipNam(s.getcShipNam());
                        portcar.setVoyage(s.getIvoyage() + '/' + s.getEvoyage());
                    }
                    if (HdUtils.strNotNull(portcar.getBrandCod())) {
                        CBrand cbrand = JpaUtils.findById(CBrand.class, portcar.getBrandCod());
                        if (cbrand != null) {
                            portcar.setBrandNam(cbrand.getBrandNam());
                        }
                    }
                    if (HdUtils.strNotNull(portcar.getTranPortCod())) {
                        String jpqlc = "select a from CPort a where a.portCod=:portCod";
                        QueryParamLs paramLsc = new QueryParamLs();
                        paramLsc.addParam("portCod", portcar.getTranPortCod());
                        List<CPort> cportList = JpaUtils.findAll(jpqlc, paramLsc);
                        if (cportList.size() > 0) {
                            portcar.setTranPortNam(cportList.get(0).getcPortNam());
                        }
                    }
                    if (HdUtils.strNotNull(portcar.getDiscPortCod())) {
                        String jpqlc = "select a from CPort a where a.portCod=:portCod";
                        QueryParamLs paramLsc = new QueryParamLs();
                        paramLsc.addParam("portCod", portcar.getDiscPortCod());
                        List<CPort> cportList = JpaUtils.findAll(jpqlc, paramLsc);
                        if (cportList.size() > 0) {
                            portcar.setDiscPortNam(cportList.get(0).getcPortNam());
                        }
                    }
                    if (HdUtils.strNotNull(portcar.getCarTyp())) {
                        CCarTyp ccartyp = JpaUtils.findById(CCarTyp.class, portcar.getCarTyp());
                        portcar.setCarTypNam(ccartyp.getCarTypNam());
                    }
                    allList.add(portcar);
                }
            }
        }
        HdEzuiDatagridData results = new HdEzuiDatagridData();
        results.setRows(allList);
        return results;
    }

    // 库存接口上报局调
    public void sendKc(String outCyTim) {
        String jpql = "select a.carTyp ckd, a.billNo bno, count(a.portCarNo) cnt,a.shipNo,a.tradeId,a.iEId from PortCar a where a.outCyTim is not null and a.outCyTim <:outCyTim";
        QueryParamLs paramLs = new QueryParamLs();
        jpql += "group by a.shipNo,a.tradeId,a.iEId,a.billNo,a.carTyp,cast(a.inCyTim as date) order by a.billNo asc,cast(a.inCyTim as date) desc";
        List<Object[]> objList = JpaUtils.findAll(jpql, paramLs);
        List<PortCar> allList = new ArrayList();
        for (Object[] obj : objList) {
            String jqpl1 = "select a from PortCar a where 1=1";
            QueryParamLs paramLs1 = new QueryParamLs();
            if (obj[0] != null) {
                jqpl1 += " and a.carTyp =:carTyp";
                paramLs1.addParam("carTyp", obj[0]);
            }
            if (obj[1] != null) {
                jqpl1 += " and a.billNo =:billNo";
                paramLs1.addParam("billNo", obj[1]);
            }
            if (obj[3] != null) {
                jqpl1 += " and a.shipNo =:shipNo";
                paramLs1.addParam("shipNo", obj[3]);
            }
            List<PortCar> portCarList = JpaUtils.findAll(jqpl1, paramLs1);
            if (portCarList.size() > 0) {
                PortCar portCar = portCarList.get(0);
                PortCarInter PortCarInter = new PortCarInter();
                portCar.setRksl(String.valueOf(obj[2]));
                if (HdUtils.strNotNull(portCar.getCarKind())) {
                    CCarKind carkind = JpaUtils.findById(CCarKind.class, portCar.getCarKind());
                    if (carkind != null) {
                        portCar.setCarKindNam(carkind.getCarKindNam());
                    }
                }
                if (HdUtils.strNotNull(portCar.getBrandCod())) {
                    CBrand cbrand = JpaUtils.findById(CBrand.class, portCar.getBrandCod());
                    if (cbrand != null) {
                        portCar.setBrandNam(cbrand.getBrandNam());
                    }
                }
                if (HdUtils.strNotNull(portCar.getConsignCod())) {
                    CClientCod cClientCod = JpaUtils.findById(CClientCod.class, portCar.getConsignCod());
                    if (cClientCod != null) {
                        portCar.setConsignNam(cClientCod.getcClientNam());
                    }
                }
                // if ("E".equals(iEId)) {
                // if (HdUtils.strNotNull(portCar.getContractNo())) {
                // ContractIeDoc contractIeDoc =
                // JpaUtils.findById(ContractIeDoc.class,
                // portCar.getContractNo());
                // if (contractIeDoc != null) {
                // portCar.setcShipNam(contractIeDoc.getShipNam());
                // portCar.setVoyage(contractIeDoc.getVoyage().substring(
                // contractIeDoc.getVoyage().indexOf("/") + 1,
                // contractIeDoc.getVoyage().length()));
                // }
                // }
                // } else if ("I".equals(iEId)) {
                // if (HdUtils.strNotNull(portCar.getShipNo())) {
                // Ship ship = JpaUtils.findById(Ship.class,
                // portCar.getShipNo());
                // if (ship != null) {
                // portCar.setcShipNam(ship.getcShipNam());
                // portCar.setVoyage(ship.getIvoyage());
                // }
                // }
                // }

                // String jpql2 = "select count(a.portCarNo) cnt from PortCar a
                // where a.outCyTim is not null ";
                // QueryParamLs paramLs2 = new QueryParamLs();
                // if (HdUtils.strNotNull(shipNo)) {
                // jpql2 += "and a.shipNo=:shipNo ";
                // paramLs2.addParam("shipNo", shipNo);
                // }
                // if (HdUtils.strNotNull(portCar.getBillNo())) {
                // jpql2 += "and a.billNo=:billNo ";
                // paramLs2.addParam("billNo", portCar.getBillNo());
                // }
                // if (HdUtils.strNotNull(portCar.getCarTyp())) {
                // jpql2 += "and a.carTyp =:carTyp ";
                // paramLs2.addParam("carTyp", portCar.getCarTyp());
                // }
                // if (HdUtils.strNotNull(iEId)) {
                // jpql2 += "and a.iEId=:iEId ";
                // paramLs2.addParam("iEId", iEId);
                // }
                // if (obj[3] != null) {
                // jpql2 += " and a.inCyTim=:inCyTim";
                // paramLs2.addParam("inCyTim",
                // HdUtils.strToDate(String.valueOf(obj[3])));
                // }
                // List<Long> dataList = JpaUtils.findAll(jpql2, paramLs2);
                // portCar.setCksl(dataList.get(0).toString());
                // if (portCar.getInCyTim() != null && portCar.getOutCyTim() !=
                // null) {
                // int days = (int) ((portCar.getOutCyTim().getTime() -
                // portCar.getInCyTim().getTime())
                // / (1000 * 3600 * 24)) + 1;
                // if (days >= 0)
                // portCar.setDcts(String.valueOf(days));
                // }
                portCar.setCyBayNo("");
                portCar.setCyRowNo("");
                portCar.setCyPlac("");
            }
        }
    }

    @Override
    public void statisticCount(String inoutcytim) {

        // 1入库统计分集港和卸船理货

        rktj(inoutcytim);

        // 2出库统计分疏港和装船理货
        cktj(inoutcytim);

    }

    @Transactional
    private void cktj(String inoutcytim) {
        String jpql = "select a.carTyp ckd, a.billNo bno, count(a.portCarNo) cnt, cast(a.outCyTim as date) ict from PortCar a"
                + " where a.inCyTim is not null and a.outCyTim=:outCyTim ";
        QueryParamLs paramLs = new QueryParamLs();
        paramLs.addParam("outCyTim", HdUtils.strToDate(inoutcytim));
        jpql += "group by a.billNo,a.carTyp,cast(a.outCyTim as date) order by a.billNo asc,cast(a.outCyTim as date) desc";
        List<Object[]> objList = JpaUtils.findAll(jpql, paramLs);
        List<PortCar> allList = new ArrayList();
        for (Object[] obj : objList) {
            String jqpl1 = "select a from PortCar a where 1=1";
            QueryParamLs paramLs1 = new QueryParamLs();
            if (obj[1] != null) {
                jqpl1 += " and a.billNo =:billNo";
                paramLs1.addParam("billNo", obj[1]);
            }
            if (obj[0] != null) {
                jqpl1 += " and a.carTyp =:carTyp";
                paramLs1.addParam("carTyp", obj[0]);
            }
            if (obj[3] != null) {
                jqpl1 += " and a.outCyTim=:outCyTim";
                paramLs1.addParam("outCyTim", HdUtils.strToDate(String.valueOf(obj[3])));
            }
            List<PortCar> portCarList = JpaUtils.findAll(jqpl1, paramLs1);
            if (portCarList.size() > 0) {
                PortCar portCar = portCarList.get(0);
                portCar.setRksl(String.valueOf(obj[2]));
                if (HdUtils.strNotNull(portCar.getCarKind())) {
                    CCarKind carkind = JpaUtils.findById(CCarKind.class, portCar.getCarKind());
                    if (carkind != null) {
                        portCar.setCarKindNam(carkind.getCarKindNam());
                    }
                }
                if (HdUtils.strNotNull(portCar.getBrandCod())) {
                    CBrand cbrand = JpaUtils.findById(CBrand.class, portCar.getBrandCod());
                    if (cbrand != null) {
                        portCar.setBrandNam(cbrand.getBrandNam());
                    }
                }
                if (HdUtils.strNotNull(portCar.getConsignCod())) {
                    CClientCod cClientCod = JpaUtils.findById(CClientCod.class, portCar.getConsignCod());
                    if (cClientCod != null) {
                        portCar.setConsignNam(cClientCod.getcClientNam());
                    }
                }
                if (HdUtils.strNotNull(portCar.getShipNo())) {
                    Ship ship = JpaUtils.findById(Ship.class, portCar.getShipNo());
                    if (ship != null) {
                        portCar.setcShipNam(ship.getcShipNam());
                        portCar.setVoyage(ship.getIvoyage());
                    }
                }

                Ship ship = JpaUtils.findById(Ship.class, portCar.getShipNo());
                if (HdUtils.strNotNull(ship.getNewEShipId()) && HdUtils.strNotNull(ship.getNewIShipId())) {
                    StatisticCount statisticCount = new StatisticCount();
                    statisticCount.setIoyardid(HdUtils.genUuid());
                    if ("I".equals(portCar.getiEId())) {
                        statisticCount.setShipid(ship.getNewIShipId());
                        statisticCount.setSvoyageid(ship.getNewGroupShipNo());
                        statisticCount.setShipname(ship.getcShipNam());
                        statisticCount.setVoyage(ship.getIvoyage());
                        statisticCount.setIeflag("I");
                    } else if ("E".equals(portCar.getiEId())) {
                        statisticCount.setShipid(ship.getNewEShipId());
                        statisticCount.setSvoyageid(ship.getNewGroupShipNo());
                        statisticCount.setShipname(ship.getcShipNam());
                        statisticCount.setVoyage(ship.getEvoyage());
                        statisticCount.setIeflag("E");
                    }
                    statisticCount.setIoyardway("2");
                    statisticCount.setIoyardflag("1");// 入库
                    DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    statisticCount.setIoyarddate(sdf.format(portCar.getOutCyTim()));
                    statisticCount.setJobid("*");
                    BigDecimal portCarNo = portCar.getPortCarNo();
                    String jpqla = "select a from TruckWork a where a.portCarNo=:portCarNo ";
                    QueryParamLs paramLsa = new QueryParamLs();
                    paramLsa.addParam("portCarNo", portCarNo);
                    List<TruckWork> twL = JpaUtils.findAll(jpqla, paramLsa);
                    if (twL.size() > 0) {
                        statisticCount.setInformid(twL.get(0).getContractNo());
                    } else {
                        statisticCount.setInformid("*");
                    }
                    String jpqlb = "select a from BillCar a where a.portCarNo=:portCarNo ";
                    QueryParamLs paramLsb = new QueryParamLs();
                    paramLsb.addParam("portCarNo", portCarNo);
                    List<BillCar> shipBillL = JpaUtils.findAll(jpqlb, paramLsb);
                    if (shipBillL.size() > 0) {
                        statisticCount.setCargoid(shipBillL.get(0).getShipbillId());
                    } else {
                        statisticCount.setCargoid("*");
                    }
                    statisticCount.setSvoyageid("1");// 艘次？

                    statisticCount.setTradeflag(ship.getTradeId());
                    statisticCount.setForwardercode("");// 货代
                    statisticCount.setConsignorcode("");
                    statisticCount.setConsigncode("");
                    statisticCount.setCnorcode("");
                    statisticCount.setCneecode("");

                    if (HdUtils.strNotNull(portCar.getCarTyp())) {
                        CCarTyp cartypList = JpaUtils.findById(CCarTyp.class, portCar.getCarTyp());
                        String corpCargoJpql = "SELECT v FROM VGroupCorpCargo v where v.typeFlag='4' and v.cargoName like :cargoName";
                        QueryParamLs corpCargoParams = new QueryParamLs();
                        corpCargoParams.addParam("cargoName", "%" + cartypList.getCarTypNam() + "%");
                        List<VGroupCorpCargo> vGroupCorpCargoList = JpaUtils.findAll(corpCargoJpql, corpCargoParams);
                        if (vGroupCorpCargoList.size() > 0) {
                            statisticCount.setCargocode(vGroupCorpCargoList.get(0).getCargoCode());
                        } else {
                            statisticCount.setCargocode("*");
                        }

                    } else {
                        statisticCount.setCargocode("*");
                    }

                    // statisticCount.setCargocode("");
                    statisticCount.setCargomark("");
                    statisticCount.setPackagecode("");
                    statisticCount.setFormat("");
                    statisticCount.setOrigincode("");
                    statisticCount.setMatacode("");
                    statisticCount.setBrandcode("");
                    statisticCount.setYardcode(portCar.getCyAreaNo());
                    // statisticCount.setYardcode(portCar.getCyAreaNo().substring(2,
                    // 4));
                    statisticCount.setLocationcode("");
                    statisticCount.setSublocationcode("");
                    statisticCount.setPilecode("");
                    statisticCount.setPiecesno("");
                    if (HdUtils.strNotNull(portCar.getCksl())) {
                        statisticCount.setCargonum(new BigDecimal(portCar.getCksl()));
                    } else {
                        statisticCount.setCargonum(new BigDecimal("0"));
                    }
                    statisticCount.setCargowgt(new BigDecimal("0"));
                    statisticCount.setDescription("");
                    statisticCount.setTeamorgnid(ship.getDockCod());
                    statisticCount.setSubmitflag("0");
                    statisticCount.setSubmitname(HdUtils.getCurUser().getAccount());
                    DateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    statisticCount.setSubmittime(sdf2.format(HdUtils.getDateTime()));
                    JpaUtils.save(statisticCount);
                }
            }
        }
    }

    @Transactional
    private void rktj(String inoutcytim) {
        String jpql = "select a.carTyp ckd, a.billNo bno, count(a.portCarNo) cnt, cast(a.inCyTim as date) ict from PortCar a"
                + " where a.inCyTim is not null and a.inCyTim=:inCyTim ";
        QueryParamLs paramLs = new QueryParamLs();
        paramLs.addParam("inCyTim", HdUtils.strToDate(inoutcytim));
        jpql += "group by a.billNo,a.carTyp,cast(a.inCyTim as date) order by a.billNo asc,cast(a.inCyTim as date) desc";
        List<Object[]> objList = JpaUtils.findAll(jpql, paramLs);
        List<PortCar> allList = new ArrayList();
        for (Object[] obj : objList) {
            String jqpl1 = "select a from PortCar a where 1=1";
            QueryParamLs paramLs1 = new QueryParamLs();
            if (obj[1] != null) {
                jqpl1 += " and a.billNo =:billNo";
                paramLs1.addParam("billNo", obj[1]);
            }
            if (obj[0] != null) {
                jqpl1 += " and a.carTyp =:carTyp";
                paramLs1.addParam("carTyp", obj[0]);
            }
            if (obj[3] != null) {
                jqpl1 += " and a.inCyTim=:inCyTim";
                paramLs1.addParam("inCyTim", HdUtils.strToDate(String.valueOf(obj[3])));
            }
            List<PortCar> portCarList = JpaUtils.findAll(jqpl1, paramLs1);
            if (portCarList.size() > 0) {
                PortCar portCar = portCarList.get(0);
                portCar.setRksl(String.valueOf(obj[2]));
                if (HdUtils.strNotNull(portCar.getCarKind())) {
                    CCarKind carkind = JpaUtils.findById(CCarKind.class, portCar.getCarKind());
                    if (carkind != null) {
                        portCar.setCarKindNam(carkind.getCarKindNam());
                    }
                }
                if (HdUtils.strNotNull(portCar.getBrandCod())) {
                    CBrand cbrand = JpaUtils.findById(CBrand.class, portCar.getBrandCod());
                    if (cbrand != null) {
                        portCar.setBrandNam(cbrand.getBrandNam());
                    }
                }
                if (HdUtils.strNotNull(portCar.getConsignCod())) {
                    CClientCod cClientCod = JpaUtils.findById(CClientCod.class, portCar.getConsignCod());
                    if (cClientCod != null) {
                        portCar.setConsignNam(cClientCod.getcClientNam());
                    }
                }
                if (HdUtils.strNotNull(portCar.getShipNo())) {
                    Ship ship = JpaUtils.findById(Ship.class, portCar.getShipNo());
                    if (ship != null) {
                        portCar.setcShipNam(ship.getcShipNam());
                        portCar.setVoyage(ship.getIvoyage());
                    }
                }

                Ship ship = JpaUtils.findById(Ship.class, portCar.getShipNo());
                // if(ship != null) {
                if (HdUtils.strNotNull(ship.getNewEShipId()) && HdUtils.strNotNull(ship.getNewIShipId())) {
                    StatisticCount statisticCount = new StatisticCount();
                    statisticCount.setIoyardid(HdUtils.genUuid());
                    if ("I".equals(portCar.getiEId())) {
                        statisticCount.setShipid(ship.getNewIShipId());
                        statisticCount.setSvoyageid(ship.getNewGroupShipNo());
                        statisticCount.setShipname(ship.getcShipNam());
                        statisticCount.setVoyage(ship.getIvoyage());
                        statisticCount.setIeflag("I");
                    } else if ("E".equals(portCar.getiEId())) {
                        statisticCount.setShipid(ship.getNewEShipId());
                        statisticCount.setSvoyageid(ship.getNewGroupShipNo());
                        statisticCount.setShipname(ship.getcShipNam());
                        statisticCount.setVoyage(ship.getEvoyage());
                        statisticCount.setIeflag("E");
                    }
                    statisticCount.setIoyardway("2");
                    statisticCount.setIoyardflag("1");// 入库
                    DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    statisticCount.setIoyarddate(sdf.format(portCar.getInCyTim()));
                    statisticCount.setJobid("*");
                    BigDecimal portCarNo = portCar.getPortCarNo();
                    String jpqla = "select a from TruckWork a where a.portCarNo=:portCarNo ";
                    QueryParamLs paramLsa = new QueryParamLs();
                    paramLsa.addParam("portCarNo", portCarNo);
                    List<TruckWork> twL = JpaUtils.findAll(jpqla, paramLsa);
                    if (twL.size() > 0) {
                        statisticCount.setInformid(twL.get(0).getContractNo());
                    } else {
                        statisticCount.setInformid("*");
                    }
                    String jpqlb = "select a from ShipBill a where a.iEId=:iEId and a.billNo=:billNo ";
                    QueryParamLs paramLsb = new QueryParamLs();
                    paramLsb.addParam("iEId", portCar.getiEId());
                    paramLsb.addParam("billNo", portCar.getBillNo());
                    List<ShipBill> shipBillL = JpaUtils.findAll(jpqlb, paramLsb);
                    if (shipBillL.size() > 0) {
                        statisticCount.setCargoid(shipBillL.get(0).getShipbillId());
                    } else {
                        statisticCount.setCargoid("*");
                    }
                    statisticCount.setTradeflag(ship.getTradeId());
                    statisticCount.setForwardercode("");// 货代
                    statisticCount.setConsignorcode("");
                    statisticCount.setConsigncode("");
                    statisticCount.setCnorcode("");
                    statisticCount.setCneecode("");

                    if (shipBillL.size() > 0) {
                        if (HdUtils.strNotNull(shipBillL.get(0).getCarTyp())) {
                            CCarTyp cartypList = JpaUtils.findById(CCarTyp.class, shipBillL.get(0).getCarTyp());
                            String corpCargoJpql = "SELECT v FROM VGroupCorpCargo v where v.typeFlag='4' and v.cargoName like :cargoName";
                            QueryParamLs corpCargoParams = new QueryParamLs();
                            corpCargoParams.addParam("cargoName", "%" + cartypList.getCarTypNam() + "%");
                            List<VGroupCorpCargo> vGroupCorpCargoList = JpaUtils.findAll(corpCargoJpql,
                                    corpCargoParams);
                            if (vGroupCorpCargoList.size() > 0) {
                                statisticCount.setCargocode(vGroupCorpCargoList.get(0).getCargoCode());
                            } else {
                                statisticCount.setCargocode("*");
                            }

                        } else {
                            statisticCount.setCargocode("*");
                        }
                    } else {
                        statisticCount.setCargocode("*");
                    }

                    // statisticCount.setCargocode("9910");
                    statisticCount.setCargomark("");
                    statisticCount.setPackagecode("");
                    statisticCount.setFormat("");
                    statisticCount.setOrigincode("");
                    statisticCount.setMatacode("");
                    if (HdUtils.strNotNull(portCar.getBrandCod())) {
                        statisticCount.setBrandcode(portCar.getBrandCod());
                    } else {
                        statisticCount.setBrandcode("");
                    }
                    statisticCount.setYardcode(portCar.getCyAreaNo());
                    // statisticCount.setYardcode(portCar.getCyAreaNo().substring(2,
                    // 4));
                    statisticCount.setLocationcode("");
                    statisticCount.setSublocationcode("");
                    statisticCount.setPilecode("");
                    statisticCount.setPiecesno("");
                    if (HdUtils.strNotNull(portCar.getRksl())) {
                        statisticCount.setCargonum(new BigDecimal(portCar.getRksl()));
                    } else {
                        statisticCount.setCargonum(new BigDecimal("0"));
                    }
                    statisticCount.setCargowgt(new BigDecimal("0"));
                    statisticCount.setDescription("");
                    statisticCount.setTeamorgnid(ship.getDockCod());
                    statisticCount.setSubmitflag("0");
                    statisticCount.setSubmitname(HdUtils.getCurUser().getAccount());
                    DateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    statisticCount.setSubmittime(sdf2.format(HdUtils.getDateTime()));
                    JpaUtils.save(statisticCount);
                }
            }
        }
    }

    public String sendHwcrkDatatoJT(String shipNo, String iEId) {
        String message = "success";
        String jpql = "SELECT s FROM StatisticCount s where s.shipNo=:shipNo and s.ieflag=:ieflag";
        QueryParamLs jpqlParams = new QueryParamLs();
        jpqlParams.addParam("shipNo", shipNo);
        jpqlParams.addParam("ieflag", iEId);
        List<StatisticCount> statisticCountList = JpaUtils.findAll(jpql, jpqlParams);
        for (StatisticCount sc : statisticCountList) {
            // StatisticCount sc = JpaUtils.findById(StatisticCount.class,
            // ioyardid);
            JSONObject jsonObj = new JSONObject();
            Map<String, String> map = new HashMap<String, String>();
            jsonObj.put("cmdId", "2000");
            jsonObj.put("coId", Ship.GZ);
            // sthruputId
            String shipId = sc.getShipid();
            if (HdUtils.strNotNull(shipId)) {
                String sthruputIdSql = "select STHRUPUT_ID\r\n" + "  from SHIP_THRUPUT@TJGDB\r\n" + " where ship_id = '"
                        + shipId + "'";
                List<Map<String, Object>> list = JpaUtils.getEntityManager().createNativeQuery(sthruputIdSql)
                        .setHint(QueryHints.RESULT_TYPE, ResultType.Map).getResultList();
                String sthruputId = ObjectUtils.toString(list.get(0).get("STHRUPUT_ID"));
                if (HdUtils.strNotNull(sthruputId)) {
                    map.put("sthruputId", sthruputId);
                }
            }
            map.put("billNo", sc.getBillNo());
            map.put("ioyardId", sc.getIoyardid());
            map.put("ioyardWay", sc.getIoyardway());
            map.put("ioyardFlag", sc.getIoyardflag());
            map.put("ioyardDate", sc.getIoyarddate());
            map.put("jobId", sc.getJobid());
            map.put("informId", sc.getInformid());
            map.put("caroId", sc.getCargoid());
            map.put("shipId", sc.getShipid());
            map.put("svoyageId", sc.getSvoyageid());
            map.put("shipName", sc.getShipname());
            map.put("voyage", sc.getVoyage());
            if (sc.getTradeflag().equals("1")) {
                map.put("tradeFlag", "2");
            } else if (sc.getTradeflag().equals("2")) {
                map.put("tradeFlag", "1");
            }
            map.put("ieFlag", sc.getIeflag());

            map.put("forwarderCode", sc.getForwardercode());
            map.put("consignorCode", sc.getConsignorcode());
            map.put("consignCode", sc.getConsigncode());
            map.put("cnorCode", sc.getCnorcode());

            // map.put("forwarderCode", "000434");
            // map.put("consignorCode", "000061");
            // map.put("consignCode", "000434");
            // map.put("cnorCode", "000061");
            map.put("cneeCode", sc.getCneecode());
            map.put("flowDir", sc.getFlowDir());

            // map.put("cneeCode", sc.getCneecode());
            map.put("cargoCode", sc.getCargocode());
            map.put("cargoMark", sc.getCargomark());
            map.put("packageCode", sc.getPackagecode());
            map.put("format", sc.getFormat());
            map.put("originCode", sc.getOrigincode());
            // map.put("originCode", "9");
            // String tradeID = sc.getTradeflag();
            // if(HdUtils.strNotNull(tradeID)) {
            // if(tradeID.equals("1")) {
            // map.put("originCode", "9");
            // }
            // if(tradeID.equals("2")) {
            // map.put("originCode", "CN");
            // }
            // }

            map.put("mataCode", sc.getMatacode());
            map.put("cargoNum", sc.getCargonum().toString());
            map.put("cargoWgt", sc.getCargowgt().toString());
            map.put("trainNum", sc.getCargonum().toString());
            map.put("teamOrgnld", Ship.GZ);

            if (HdUtils.strNotNull(sc.getBrandcode())) {
                CBrand brand = JpaUtils.findById(CBrand.class, sc.getBrandcode());
                if (brand != null) {
                    if (HdUtils.strNotNull(brand.getBrandNam())) {
                        String branView = "SELECT v FROM VGroupCorpBrand v where v.brandName like :brandName";
                        QueryParamLs vParams = new QueryParamLs();
                        vParams.addParam("brandName", "%" + brand.getBrandNam() + "%");
                        List<VGroupCorpBrand> vGroupCorpBrandList = JpaUtils.findAll(branView, vParams);
                        if (vGroupCorpBrandList.size() > 0) {
                            map.put("brandCode", vGroupCorpBrandList.get(0).getBrandCode());
                        } else {
                            map.put("brandCode", "");
                        }
                    } else {
                        map.put("brandCode", "");
                    }
                } else {
                    map.put("brandCode", "");
                }

            } else {
                map.put("brandCode", "");
            }
            // map.put("brandCode", sc.getBrandcode());
            map.put("yardCode", sc.getYardcode());
            map.put("locationCode", sc.getLocationcode());
            map.put("piecesNo", "");
            map.put("description", sc.getDescription());
            map.put("teamOrgnId", sc.getTeamorgnid());
            map.put("submitFlag", sc.getSubmitflag());
            map.put("submitName", sc.getSubmitname());

            map.put("submitTime", sc.getSubmittime());
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
                    JSONObject jsonObject = JSONObject.fromObject(response);
                    RespInter resp = (RespInter) JSONObject.toBean(jsonObject, RespInter.class);
                    String resCode = "0000";
                    String resMsg = "OK";
                    if (resCode.equals(resp.getResCode()) && resMsg.equals(resp.getResMsg())) {
                        // throw new HdRunTimeException("上报集团成功！");
                    }
                    if (!resCode.equals(resp.getResCode()) || !resMsg.equals(resp.getResMsg())) {
                        message = "error";
                        break;
                        // throw new HdRunTimeException("上报集团失败！");
                    }
                } catch (Exception e) {
                    message = "error";
                    // throw new HdRunTimeException("上报集团失败！");
                    // System.out.println("上报计费数据异常！" + e);
                }
                // 断开连接
                conn.disconnect();
            } catch (Exception e) {
                message = "error";
                // System.out.println("发送 POST 请求出现异常！" + e);
                // e.printStackTrace();
                throw new HdRunTimeException("上报集团失败！");
                // throw new HdRunTimeException("发送 POST 请求出现异常!");
            }
            // 使用finally块来关闭输出流、输入流
            // finally {
            // try {
            // if (os != null) {
            // out.close();
            // }
            // if (reader != null) {
            // reader.close();
            // }
            // } catch (IOException ex) {
            // ex.printStackTrace();
            // }
            // }
        }

        return message;
    }

    @Override
    public HashMap<String, Long> getFtDcqk(String date) {
        String jpql = " select b.brandCod,b.carKind,b.carTyp,count(b.portCarNo),b.tranPortCod"
                + " from PortCar b where   b.inCyTim <=:inCyTim and b.carTyp is not null "
                + " group by b.brandCod,b.carKind,b.carTyp,b.tranPortCod ";
        Date dte = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            dte = (Date) sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        QueryParamLs paramLs = new QueryParamLs();
        paramLs.addParam("inCyTim", dte);
        List<Object[]> objList = JpaUtils.findAll(jpql, paramLs);
        HashMap<String, Long> hashmap = new HashMap<String, Long>();
        if (objList.size() > 0) {
            for (Object[] obj : objList) {
                if (obj[0] != null) {
                    if (HdUtils.strNotNull(obj[0].toString())) {
                        // // 威驰FS各流向数目
                        if (obj[0].equals("TOYOTA") && obj[2].equals("192")) {
                            // 东莞
                            if (obj[4] != null) {
                                if (obj[4].equals("CNDWN")) {
                                    hashmap.put("wcfsdg", (Long) obj[3]);
                                } else {
                                    hashmap.put("wcfsdg", (long) 0);
                                }
                            }
                            // 南沙
                            if (obj[4] != null) {
                                if (obj[4].equals("CNNS1")) {
                                    hashmap.put("wcfsns", (Long) obj[3]);
                                } else {
                                    hashmap.put("wcfsns", (long) 0);
                                }
                            }
                            // 上海
                            if (obj[4] != null) {
                                if (obj[4].equals("CNSHA")) {
                                    hashmap.put("wcfssh", (Long) obj[3]);
                                } else {
                                    hashmap.put("wcfssh", (long) 0);
                                }
                            }
                        }
                        // // 威驰各流向数目
                        if (obj[0].equals("TOYOTA") && obj[2].equals("191")) {
                            // 东莞
                            if (obj[4] != null) {
                                if (obj[4].equals("CNDWN")) {
                                    hashmap.put("wcdg", (Long) obj[3]);
                                } else {
                                    hashmap.put("wcdg", (long) 0);
                                }
                            }
                            // 南沙
                            if (obj[4] != null) {
                                if (obj[4].equals("CNNS1")) {
                                    hashmap.put("wcns", (Long) obj[3]);
                                } else {
                                    hashmap.put("wcns", (long) 0);
                                }
                            }
                            // 上海
                            if (obj[4] != null) {
                                if (obj[4].equals("CNSHA")) {
                                    hashmap.put("wcsh", (Long) obj[3]);
                                } else {
                                    hashmap.put("wcsh", (long) 0);
                                }
                            }
                        }
                        // // 卡罗拉各流向数目
                        if (obj[0].equals("TOYOTA") && obj[2].equals("190")) {
                            // 东莞
                            if (obj[4] != null) {
                                if (obj[4].equals("CNDWN")) {
                                    hashmap.put("klldg", (Long) obj[3]);
                                } else {
                                    hashmap.put("klldg", (long) 0);
                                }
                            }
                            // 南沙
                            if (obj[4] != null) {
                                if (obj[4].equals("CNNS1")) {
                                    hashmap.put("kllns", (Long) obj[3]);
                                } else {
                                    hashmap.put("kllns", (long) 0);
                                }
                            }
                            // 上海
                            if (obj[4] != null) {
                                if (obj[4].equals("CNSHA")) {
                                    hashmap.put("kllsh", (Long) obj[3]);
                                } else {
                                    hashmap.put("kllsh", (long) 0);
                                }
                            }
                        }
                        // // 皇冠各流向数目
                        if (obj[0].equals("TOYOTA") && obj[2].equals("193")) {
                            // 东莞
                            if (obj[4] != null) {
                                if (obj[4].equals("CNDWN")) {
                                    hashmap.put("huanggdg", (Long) obj[3]);
                                } else {
                                    hashmap.put("huanggdg", (long) 0);
                                }
                            }
                            // 南沙
                            if (obj[4] != null) {
                                if (obj[4].equals("CNNS1")) {
                                    hashmap.put("huanggns", (Long) obj[3]);
                                } else {
                                    hashmap.put("huanggns", (long) 0);
                                }
                            }
                            // 上海
                            if (obj[4] != null) {
                                if (obj[4].equals("CNSHA")) {
                                    hashmap.put("huanggsh", (Long) obj[3]);
                                } else {
                                    hashmap.put("huanggsh", (long) 0);
                                }
                            }
                        }
                        // // 锐志各流向数目
                        if (obj[0].equals("TOYOTA") && obj[2].equals("193")) {
                            // 东莞
                            if (obj[4] != null) {
                                if (obj[4].equals("CNDWN")) {
                                    hashmap.put("huanggdg", (Long) obj[3]);
                                } else {
                                    hashmap.put("huanggdg", (long) 0);
                                }
                            }
                            // 南沙
                            if (obj[4] != null) {
                                if (obj[4].equals("CNNS1")) {
                                    hashmap.put("huanggns", (Long) obj[3]);
                                } else {
                                    hashmap.put("huanggns", (long) 0);
                                }
                            }
                            // 上海
                            if (obj[4] != null) {
                                if (obj[4].equals("CNSHA")) {
                                    hashmap.put("huanggsh", (Long) obj[3]);
                                } else {
                                    hashmap.put("huanggsh", (long) 0);
                                }
                            }
                        }
                        // // 花冠各流向数目
                        if (obj[0].equals("TOYOTA") && obj[2].equals("193")) {
                            // 东莞
                            if (obj[4] != null) {
                                if (obj[4].equals("CNDWN")) {
                                    hashmap.put("hgdg", (Long) obj[3]);
                                } else {
                                    hashmap.put("hgdg", (long) 0);
                                }
                            }
                            // 南沙
                            if (obj[4] != null) {
                                if (obj[4].equals("CNNS1")) {
                                    hashmap.put("hgns", (Long) obj[3]);
                                } else {
                                    hashmap.put("hgns", (long) 0);
                                }
                            }
                            // 上海
                            if (obj[4] != null) {
                                if (obj[4].equals("CNSHA")) {
                                    hashmap.put("hgsh", (Long) obj[3]);
                                } else {
                                    hashmap.put("hgsh", (long) 0);
                                }
                            }
                        }
                    }
                }
            }
        }
        return hashmap;
    }

    @Override
    public HashMap<String, Long> getFtJsg(String date) {
        String jpql = " select b.shipNo,b.brandCod,b.carKind,b.carTyp,count(b.portCarNo),b.iEId,b.tranPortCod "
                + " from WorkCommand a,PortCar b where  a.portCarNo=b.portCarNo and a.inCyTim=:inCyTim and b.iEId='E' and a.workTyp='TI'"
                + " group by b.shipNo,b.brandCod,b.carKind,b.carTyp,b.iEId,b.tranPortCod ";
        Date dte = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            dte = (Date) sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        QueryParamLs paramLs = new QueryParamLs();
        paramLs.addParam("inCyTim", dte);
        List<Object[]> objList = JpaUtils.findAll(jpql, paramLs);
        HashMap<String, Long> hashmap = new HashMap<String, Long>();
        if (objList.size() > 0) {
            for (Object[] obj : objList) {
                if (HdUtils.strNotNull((String) obj[1])) {
                    // 丰田各车型流向集港数据
                    // 威驰FS各流向集港数目
                    if (obj[1].equals("TOYOTA") && obj[2].equals("192")) {
                        // 东莞
                        if (obj[6] != null) {
                            if (obj[6].equals("CNDWN")) {
                                hashmap.put("wcfsdgjg", (Long) obj[4]);
                            } else {
                                hashmap.put("wcfsdgjg", (long) 0);
                            }
                        }
                        // 南沙
                        if (obj[6] != null) {
                            if (obj[6].equals("CNNS1")) {
                                hashmap.put("wcfsnsjg", (Long) obj[4]);
                            } else {
                                hashmap.put("wcfsnsjg", (long) 0);
                            }
                        }
                        // 上海
                        if (obj[6] != null) {
                            if (obj[6].equals("CNSHA")) {
                                hashmap.put("wcfsshjg", (Long) obj[4]);
                            } else {
                                hashmap.put("wcfsshjg", (long) 0);
                            }
                        }
                    }
                    // 威驰各流向集港数目
                    if (obj[1].equals("TOYOTA") && obj[2].equals("191")) {
                        // 东莞
                        if (obj[6] != null) {
                            if (obj[6].equals("CNDWN")) {
                                hashmap.put("wcdgjg", (Long) obj[4]);
                            } else {
                                hashmap.put("wcdgjg", (long) 0);
                            }
                        }
                        // 南沙
                        if (obj[6] != null) {
                            if (obj[6].equals("CNNS1")) {
                                hashmap.put("wcnsjg", (Long) obj[4]);
                            } else {
                                hashmap.put("wcnsjg", (long) 0);
                            }
                        }
                        // 上海
                        if (obj[6] != null) {
                            if (obj[6].equals("CNSHA")) {
                                hashmap.put("wcshjg", (Long) obj[4]);
                            } else {
                                hashmap.put("wcshjg", (long) 0);
                            }
                        }
                    }
                    // 卡罗拉各流向集港数目
                    if (obj[1].equals("TOYOTA") && obj[2].equals("190")) {
                        // 东莞
                        if (obj[6] != null) {
                            if (obj[6].equals("CNDWN")) {
                                hashmap.put("klldgjg", (Long) obj[4]);
                            } else {
                                hashmap.put("klldgjg", (long) 0);
                            }
                        }
                        // 南沙
                        if (obj[6] != null) {
                            if (obj[6].equals("CNNS1")) {
                                hashmap.put("kllnsjg", (Long) obj[4]);
                            } else {
                                hashmap.put("kllnsjg", (long) 0);
                            }
                        }
                        // 上海
                        if (obj[6] != null) {
                            if (obj[6].equals("CNSHA")) {
                                hashmap.put("kllshjg", (Long) obj[4]);
                            } else {
                                hashmap.put("kllshjg", (long) 0);
                            }
                        }
                    }
                    // 皇冠各流向数目
                    if (obj[1].equals("TOYOTA") && obj[2].equals("193")) {
                        // 东莞
                        if (obj[6] != null) {
                            if (obj[6].equals("CNDWN")) {
                                hashmap.put("hgdgjg", (Long) obj[4]);
                            } else {
                                hashmap.put("hgdgjg", (long) 0);
                            }
                        }
                        // 南沙
                        if (obj[6] != null) {
                            if (obj[6].equals("CNNS1")) {
                                hashmap.put("hgnsjg", (Long) obj[4]);
                            } else {
                                hashmap.put("hgnsjg", (long) 0);
                            }
                        }
                        // 上海
                        if (obj[6] != null) {
                            if (obj[6].equals("CNSHA")) {
                                hashmap.put("hgshjg", (Long) obj[4]);
                            } else {
                                hashmap.put("hgshjg", (long) 0);
                            }
                        }
                    }
                    // 锐志各流向数目
                    if (obj[1].equals("TOYOTA") && obj[2].equals("193")) {
                        // 东莞
                        if (obj[6] != null) {
                            if (obj[6].equals("CNDWN")) {
                                hashmap.put("rzdgjg", (Long) obj[4]);
                            } else {
                                hashmap.put("rzdgjg", (long) 0);
                            }
                        }
                        // 南沙
                        if (obj[6] != null) {
                            if (obj[6].equals("CNNS1")) {
                                hashmap.put("rznsjg", (Long) obj[4]);
                            } else {
                                hashmap.put("rznsjg", (long) 0);
                            }
                        }
                        // 上海
                        if (obj[6] != null) {
                            if (obj[6].equals("CNSHA")) {
                                hashmap.put("rzshjg", (Long) obj[4]);
                            } else {
                                hashmap.put("rzshjg", (long) 0);
                            }
                        }
                    }
                    // 花冠各流向数目
                    if (obj[1].equals("TOYOTA") && obj[2].equals("193")) {
                        // 东莞
                        if (obj[6] != null) {
                            if (obj[6].equals("CNDWN")) {
                                hashmap.put("huagdgjg", (Long) obj[4]);
                            } else {
                                hashmap.put("huagdgjg", (long) 0);
                            }
                        }
                        // 南沙
                        if (obj[6] != null) {
                            if (obj[6].equals("CNNS1")) {
                                hashmap.put("huagnsjg", (Long) obj[4]);
                            } else {
                                hashmap.put("huagnsjg", (long) 0);
                            }
                        }
                        // 上海
                        if (obj[6] != null) {
                            if (obj[6].equals("CNSHA")) {
                                hashmap.put("huagshjg", (Long) obj[4]);
                            } else {
                                hashmap.put("huagshjg", (long) 0);
                            }
                        }
                    }
                }
            }
        }
        return hashmap;
    }

    @Transactional
    public HdMessageCode statisticCountSave(String shipNo, String iEId) {

        // 进口入库
        if (iEId.equals("I")) {
            String jpql = "SELECT s FROM StatisticCount s where s.shipNo =:shipNo and s.ieflag =:ieflag";
            QueryParamLs params = new QueryParamLs();
            params.addParam("shipNo", shipNo);
            params.addParam("ieflag", iEId);
            List<StatisticCount> statisticCountList = JpaUtils.findAll(jpql, params);
            if (statisticCountList.size() > 0) {
                // throw new HdRunTimeException("该船已完成统计，禁止再次统计！");
                JpaUtils.removeAll(statisticCountList);
            }
            // String jkrkJpql = "select t1.billNo bno, t1.carTyp ckd,
            // t1.carKind, t2.tranPortCod, count(t1.portCarNo) cnt from
            // WorkCommand t1, PortCar t2 "
            // + "where t1.portCarNo = t2.portCarNo and t1.shipNo =:shipNo and
            // t1.workTyp = 'SI' group by t1.billNo, t1.carTyp, t1.carKind,
            // t2.tranPortCod order by t1.billNo\r\n";
            // QueryParamLs jkruParams = new QueryParamLs();
            // jkruParams.addParam("shipNo", shipNo);
            // List<Object[]> jkruList = JpaUtils.findAll(jkrkJpql, jkruParams);

            // String jkrkSql = "SELECT t0.BILL_NO,\n" +
            // " t0.CAR_TYP,\n" +
            // " t0.CAR_KIND,\n" +
            // " t1.TRAN_PORT_COD,\n" +
            // " COUNT(t0.PORT_CAR_NO)\n" +
            // " FROM WORK_COMMAND t0, PORT_CAR t1\n" +
            // " WHERE ((t0.PORT_CAR_NO = t1.PORT_CAR_NO) AND\n" +
            // " (t0.SHIP_NO = '20190307105332'))\n" +
            // " AND (t0.WORK_TYP = 'SI')\n" +
            // " and length(t0.vin_no) > '1'\n" +
            // " GROUP BY t0.BILL_NO, t0.CAR_TYP, t0.CAR_KIND,
            // t1.TRAN_PORT_COD\n" +
            // " ORDER BY t0.BILL_NO";

            // String jkrkSql = "SELECT distinct t0.BILL_NO,\n" + "
            // t0.CAR_TYP,\n"
            // + " t0.CAR_KIND,\n" + " t1.TRAN_PORT_COD,\n"
            // + " COUNT(t0.PORT_CAR_NO),\n"
            // + " max((select distinct nvl(sum(t2.weights), 0)\n"
            // + " from bill_split t2, c_car_typ t3\n"
            // + " where t2.ship_no = t0.SHIP_NO\n"
            // + " and t2.I_E_ID = 'I'\n"
            // + " and t2.bill_no = t0.BILL_NO\n"
            // + " and nvl(t2.brand_cod, '*') = nvl(t0.brand_cod, '*')\n"
            // + " and t3.car_typ = t0.car_typ\n"
            // + " and t2.car_fee_typ = t3.car_fee_typ)) / 1000 weights\n"
            // + " FROM WORK_COMMAND t0, PORT_CAR t1\n" + " WHERE
            // ((t0.PORT_CAR_NO = t1.PORT_CAR_NO) AND\n"
            // + " (t0.SHIP_NO = '" + shipNo + "'))\n" + " AND (t0.WORK_TYP =
            // 'SI')\n"
            // + " and length(t1.vin_no) > '1'\n"
            // + " GROUP BY t0.BILL_NO, t0.CAR_TYP, t0.CAR_KIND,
            // t1.TRAN_PORT_COD\n" + " ORDER BY t0.BILL_NO";
            String jkrkSql = "SELECT DISTINCT " + "         t2.BILL_NO,         " + "         t1.CAR_TYP, "
                    + "         t3.car_kind, " + "         t2.TRAN_PORT_COD, " + "         sum(t1.PIECES) pieces, "
                    + "         sum(t1.WEIGHTS)    weights " + "    FROM bill_split t1, " + "         ship_bill  t2, "
                    + "         c_car_typ  t3, " + "         C_BRAND_DETAIL t4  "
                    + "   WHERE t1.shipbill_id = t2.shipbill_id " + "     AND t1.car_typ     = t3.car_typ(+) "
                    + "     and t2.brand_cod   = t4.brand_cod(+) " + "     and t1.i_e_id      = t4.i_e_id "
                    + "     and t1.trade_id    = t4.trade_id " + "     AND t1.SHIP_NO     =  " + shipNo
                    + "     AND t1.i_e_id      = 'I'  " + "GROUP BY t2.BILL_NO, " + "         t1.CAR_TYP, "
                    + "         t3.CAR_KIND " + "       , t2.brand_cod  " + "       , t2.TRAN_PORT_COD "
                    + "ORDER BY t2.BILL_NO ";

            List<Object[]> objList = JpaUtils.getEntityManager().createNativeQuery(jkrkSql).getResultList();

            if (objList.size() > 0) {
                for (Object[] obj : objList) {
                    Ship ship = JpaUtils.findById(Ship.class, shipNo);
                    if (HdUtils.strNotNull(ship.getNewEShipId()) && HdUtils.strNotNull(ship.getNewIShipId())) {
                        StatisticCount statisticCount = new StatisticCount();
                        statisticCount.setIoyardid(HdUtils.genUuid());
                        statisticCount.setShipNo(shipNo);
                        statisticCount.setShipid(ship.getNewIShipId());
                        statisticCount.setSvoyageid(ship.getNewGroupShipNo());
                        statisticCount.setShipname(ship.getcShipNam());
                        statisticCount.setVoyage(ship.getIvoyage());
                        statisticCount.setIeflag("I");
                        statisticCount.setIoyardway("2");
                        statisticCount.setIoyardflag("1");// 入库
                        statisticCount.setIoyarddate(HdUtils.getDateTimeStr());
                        statisticCount.setJobid("*");
                        statisticCount.setInformid("*");
                        String jpqlb = "select a from ShipBill a where a.iEId=:iEId and a.billNo=:billNo ";
                        QueryParamLs paramLsb = new QueryParamLs();
                        paramLsb.addParam("iEId", iEId);
                        String billNo = ObjectUtils.toString(obj[0]);
                        statisticCount.setBillNo(billNo);
                        paramLsb.addParam("billNo", billNo);
                        List<ShipBill> shipBillL = JpaUtils.findAll(jpqlb, paramLsb);
                        if (shipBillL.size() > 0) {
                            statisticCount.setCargoid(shipBillL.get(0).getShipbillId());
                        } else {
                            statisticCount.setCargoid("*");
                        }
                        statisticCount.setTradeflag(ship.getTradeId());
                        // statisticCount.setForwardercode("");// 货代
                        // statisticCount.setConsignorcode("");
                        statisticCount.setConsigncode("");
                        // statisticCount.setCnorcode("");
                        // statisticCount.setCneecode("");
                        if (HdUtils.strIsNull(obj[1].toString())) {
                            throw new HdRunTimeException("车型信息为空！");
                        }
                        CCarTyp data = JpaUtils.findById(CCarTyp.class, obj[1].toString());
                        if (data != null) {
                            String brandCod = data.getBrandCod();
                            if (HdUtils.strNotNull(brandCod)) {
                                String jpqlx = "select a from CBrandDetail a where a.brandCod =:brandCod";
                                QueryParamLs paramx = new QueryParamLs();
                                paramx.addParam("brandCod", brandCod);
                                List<CBrandDetail> cBrandDetailList = JpaUtils.findAll(jpqlx, paramx);
                                if (cBrandDetailList.size() > 0) {
                                    CBrandDetail bean = cBrandDetailList.get(0);
                                    if (HdUtils.strNotNull(bean.getOriginCode())) {
                                        statisticCount.setOrigincode(bean.getOriginCode());// 产地
                                    } else {
                                        statisticCount.setOrigincode("");
                                    }
                                    if (HdUtils.strNotNull(bean.getFlowDir())) {
                                        statisticCount.setFlowDir(bean.getFlowDir());// 流向
                                    } else {
                                        statisticCount.setFlowDir("");
                                    }

                                    if (HdUtils.strNotNull(bean.getAgentCod())) {
                                        statisticCount.setForwardercode(bean.getAgentCod());// 货代
                                    } else {
                                        statisticCount.setForwardercode("");// 货代
                                    }

                                    if (HdUtils.strNotNull(bean.getClientCod())) {
                                        statisticCount.setConsignorcode(bean.getClientCod());// 货主
                                    } else {
                                        statisticCount.setConsignorcode("");// 货主
                                    }

                                    if (HdUtils.strNotNull(bean.getConsignCod())) {
                                        statisticCount.setCnorcode(bean.getConsignCod());// 发货人
                                    } else {
                                        statisticCount.setCnorcode("");// 发货人
                                    }

                                    if (HdUtils.strNotNull(bean.getReceiveCod())) {
                                        statisticCount.setCneecode(bean.getReceiveCod());// 发货人
                                    } else {
                                        statisticCount.setCneecode("");// 发货人
                                    }
                                }
                            }
                        }
                        String carKind = ObjectUtils.toString(obj[2]);
                        CCarKind cCarKindEntity = JpaUtils.findById(CCarKind.class, carKind);
                        if (cCarKindEntity != null) {
                            if (HdUtils.strIsNull(cCarKindEntity.getGroupCarKind())) {
                                throw new HdRunTimeException("车类不存在:" + cCarKindEntity.getCarKindNam() + "！");
                            } else {
                                CargoDataSpecification cargoDataSpecificationEntity = JpaUtils
                                        .findById(CargoDataSpecification.class, cCarKindEntity.getGroupCarKind());
                                statisticCount.setCargocode(cargoDataSpecificationEntity.getxFourthCode());
                            }
                        } else {
                            // throw new HdRunTimeException("无车类信息");
                        }
                        statisticCount.setCargomark("");
                        statisticCount.setPackagecode("");
                        statisticCount.setFormat("");
                        // statisticCount.setOrigincode("");
                        statisticCount.setMatacode("");

                        String carTyp = ObjectUtils.toString(obj[1]);
                        CCarTyp cCarTypEntity = JpaUtils.findById(CCarTyp.class, carTyp);
                        if (cCarTypEntity != null) {
                            if (HdUtils.strNotNull(cCarTypEntity.getBrandCod())) {
                                statisticCount.setBrandcode(cCarTypEntity.getBrandCod());
                            } else {
                                statisticCount.setBrandcode("");
                            }
                        } else {
                            statisticCount.setBrandcode("");
                        }

                        statisticCount.setYardcode("");
                        // statisticCount.setYardcode(portCar.getCyAreaNo().substring(2,
                        // 4));
                        statisticCount.setLocationcode("");
                        statisticCount.setSublocationcode("");
                        statisticCount.setPilecode("");
                        statisticCount.setPiecesno("");

                        String cargoNum = ObjectUtils.toString(obj[4]);
                        if (HdUtils.strNotNull(cargoNum)) {
                            statisticCount.setCargonum(new BigDecimal(cargoNum));
                        } else {
                            statisticCount.setCargonum(new BigDecimal(0));
                        }

                        String cargoWgt = ObjectUtils.toString(obj[5]);
                        if (HdUtils.strNotNull(cargoWgt)) {
                            statisticCount.setCargowgt(new BigDecimal(cargoWgt));
                        } else {
                            statisticCount.setCargowgt(new BigDecimal(0));
                        }

                        statisticCount.setDescription("");
                        statisticCount.setTeamorgnid(ship.getDockCod());
                        statisticCount.setSubmitflag("0");
                        statisticCount.setSubmitname(HdUtils.getCurUser().getAccount());
                        DateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                        statisticCount.setSubmittime(sdf2.format(HdUtils.getDateTime()));
                        JpaUtils.save(statisticCount);

                    } else {
                        throw new HdRunTimeException("暂无新局调信息！");
                    }
                }
            } else {
                throw new HdRunTimeException("该船暂无信息！");
            }
        }

        // 出口出库
        if (iEId.equals("E")) {
            String jpql = "SELECT s FROM StatisticCount s where s.shipNo =:shipNo and s.ieflag =:ieflag";
            QueryParamLs params = new QueryParamLs();
            params.addParam("shipNo", shipNo);
            params.addParam("ieflag", iEId);
            List<StatisticCount> statisticCountList = JpaUtils.findAll(jpql, params);
            if (statisticCountList.size() > 0) {
                // throw new HdRunTimeException("该船已完成统计，禁止再次统计！");
                JpaUtils.removeAll(statisticCountList);
            }

            // String jkrkJpql = "select t1.billNo bno, t1.carTyp ckd,
            // t1.carKind, t2.tranPortCod, count(t1.portCarNo) cnt from
            // WorkCommand t1, PortCar t2 "
            // + "where t1.portCarNo = t2.portCarNo and t1.shipNo =:shipNo and
            // t1.workTyp = 'SO' group by t1.billNo, t1.carTyp, t1.carKind,
            // t2.tranPortCod order by t1.billNo\r\n";
            // QueryParamLs jkruParams = new QueryParamLs();
            // jkruParams.addParam("shipNo", shipNo);
            // List<Object[]> jkruList = JpaUtils.findAll(jkrkJpql, jkruParams);

            // String ckckSql = "SELECT t0.BILL_NO,\n" +
            // " t0.CAR_TYP,\n" +
            // " t0.CAR_KIND,\n" +
            // " t1.TRAN_PORT_COD,\n" +
            // " COUNT(t0.PORT_CAR_NO)\n" +
            // " FROM WORK_COMMAND t0, PORT_CAR t1\n" +
            // " WHERE ((t0.PORT_CAR_NO = t1.PORT_CAR_NO) AND\n" +
            // " (t0.SHIP_NO = '20190307105332'))\n" +
            // " AND (t0.WORK_TYP = 'SO')\n" +
            // " and length(t0.vin_no) > '1'\n" +
            // " GROUP BY t0.BILL_NO, t0.CAR_TYP, t0.CAR_KIND,
            // t1.TRAN_PORT_COD\n" +
            // " ORDER BY t0.BILL_NO";

            // String ckckSql = "SELECT distinct t0.BILL_NO,\n" + "
            // t0.CAR_TYP,\n"
            // + " t0.CAR_KIND,\n" + " t1.TRAN_PORT_COD,\n"
            // + " COUNT(t0.PORT_CAR_NO),\n"
            // + " max((select distinct nvl(sum(t2.weights), 0)\n"
            // + " from bill_split t2, c_car_typ t3\n"
            // + " where t2.ship_no = t0.SHIP_NO\n"
            // + " and t2.I_E_ID = 'E'\n"
            // + " and t2.bill_no = t0.BILL_NO\n"
            // + " and nvl(t2.brand_cod, '*') = nvl(t0.brand_cod, '*')\n"
            // + " and t3.car_typ = t0.car_typ\n"
            // + " and t2.car_fee_typ = t3.car_fee_typ)) / 1000 weights\n"
            // + " FROM WORK_COMMAND t0, PORT_CAR t1\n" + " WHERE
            // ((t0.PORT_CAR_NO = t1.PORT_CAR_NO) AND\n"
            // + " (t0.SHIP_NO = '" + shipNo + "'))\n" + " AND (t0.WORK_TYP =
            // 'SO')\n"
            // + " and length(t1.vin_no) > '1'\n"
            // + " GROUP BY t0.BILL_NO, t0.CAR_TYP, t0.CAR_KIND,
            // t1.TRAN_PORT_COD\n" + " ORDER BY t0.BILL_NO";
            String ckckSql = "SELECT DISTINCT " + "         t2.BILL_NO,         " + "         t1.CAR_TYP, "
                    + "         t3.car_kind, " + "         t2.TRAN_PORT_COD, " + "         sum(t1.PIECES) pieces, "
                    + "         sum(t1.WEIGHTS)    weights " + "    FROM bill_split t1, " + "         ship_bill  t2, "
                    + "         c_car_typ  t3  " + "   WHERE t1.shipbill_id = t2.shipbill_id "
                    + "     AND t1.car_typ     = t3.car_typ(+) " + "     AND t1.SHIP_NO     = " + shipNo
                    + "     AND t1.i_e_id      = 'E'  " + "GROUP BY t2.BILL_NO, " + "         t1.CAR_TYP, "
                    + "         t3.CAR_KIND " + "       , t2.TRAN_PORT_COD " + "ORDER BY t2.BILL_NO ";

            List<Object[]> objList = JpaUtils.getEntityManager().createNativeQuery(ckckSql).getResultList();

            if (objList.size() > 0) {
                for (Object[] obj : objList) {
                    Ship ship = JpaUtils.findById(Ship.class, shipNo);
                    if (HdUtils.strNotNull(ship.getNewEShipId()) && HdUtils.strNotNull(ship.getNewIShipId())) {
                        StatisticCount statisticCount = new StatisticCount();
                        statisticCount.setIoyardid(HdUtils.genUuid());
                        statisticCount.setShipNo(shipNo);
                        statisticCount.setShipid(ship.getNewEShipId());
                        statisticCount.setSvoyageid(ship.getNewGroupShipNo());
                        statisticCount.setShipname(ship.getcShipNam());
                        statisticCount.setVoyage(ship.getIvoyage());
                        statisticCount.setIeflag("E");
                        statisticCount.setIoyardway("2");
                        statisticCount.setIoyardflag("0");// 入库
                        statisticCount.setIoyarddate(HdUtils.getDateTimeStr());
                        statisticCount.setJobid("*");
                        statisticCount.setInformid("*");
                        String jpqlb = "select a from ShipBill a where a.iEId=:iEId and a.billNo=:billNo ";
                        QueryParamLs paramLsb = new QueryParamLs();
                        paramLsb.addParam("iEId", iEId);
                        String billNo = ObjectUtils.toString(obj[0]);
                        statisticCount.setBillNo(billNo);
                        paramLsb.addParam("billNo", billNo);
                        List<ShipBill> shipBillL = JpaUtils.findAll(jpqlb, paramLsb);
                        if (shipBillL.size() > 0) {
                            statisticCount.setCargoid(shipBillL.get(0).getShipbillId());
                        } else {
                            statisticCount.setCargoid("*");
                        }
                        statisticCount.setTradeflag(ship.getTradeId());
                        // statisticCount.setForwardercode("");// 货代
                        // statisticCount.setConsignorcode("");
                        statisticCount.setConsigncode("");
                        // statisticCount.setCnorcode("");
                        // statisticCount.setCneecode("");
                        CCarTyp data = JpaUtils.findById(CCarTyp.class, obj[1].toString());
                        if (data != null) {
                            String brandCod = data.getBrandCod();
                            if (HdUtils.strNotNull(brandCod)) {
                                String jpqlx = "select a from CBrandDetail a where a.brandCod =:brandCod";
                                QueryParamLs paramx = new QueryParamLs();
                                paramx.addParam("brandCod", brandCod);
                                List<CBrandDetail> cBrandDetailList = JpaUtils.findAll(jpqlx, paramx);
                                if (cBrandDetailList.size() > 0) {
                                    CBrandDetail bean = cBrandDetailList.get(0);
                                    if (HdUtils.strNotNull(bean.getOriginCode())) {
                                        statisticCount.setOrigincode(bean.getOriginCode());// 产地
                                    } else {
                                        statisticCount.setOrigincode("");
                                    }
                                    if (HdUtils.strNotNull(bean.getFlowDir())) {
                                        statisticCount.setFlowDir(bean.getFlowDir());// 流向
                                    } else {
                                        statisticCount.setFlowDir("");
                                    }
                                    if (HdUtils.strNotNull(bean.getAgentCod())) {
                                        statisticCount.setForwardercode(bean.getAgentCod());// 货代
                                    } else {
                                        statisticCount.setForwardercode("");// 货代
                                    }

                                    if (HdUtils.strNotNull(bean.getClientCod())) {
                                        statisticCount.setConsignorcode(bean.getClientCod());// 货主
                                    } else {
                                        statisticCount.setConsignorcode("");// 货主
                                    }

                                    if (HdUtils.strNotNull(bean.getConsignCod())) {
                                        statisticCount.setCnorcode(bean.getConsignCod());// 发货人
                                    } else {
                                        statisticCount.setCnorcode("");// 发货人
                                    }

                                    if (HdUtils.strNotNull(bean.getReceiveCod())) {
                                        statisticCount.setCneecode(bean.getReceiveCod());// 发货人
                                    } else {
                                        statisticCount.setCneecode("");// 发货人
                                    }
                                }
                            }
                        }
                        String carKind = ObjectUtils.toString(obj[2]);
                        if (HdUtils.strIsNull(carKind)) {
                            continue;
                        }
                        CCarKind cCarKindEntity = JpaUtils.findById(CCarKind.class, carKind);
                        if (cCarKindEntity != null) {
                            if (HdUtils.strIsNull(cCarKindEntity.getGroupCarKind())) {
                                throw new HdRunTimeException("车类不存在:" + cCarKindEntity.getCarKindNam() + "！");
                            } else {
                                CargoDataSpecification cargoDataSpecificationEntity = JpaUtils
                                        .findById(CargoDataSpecification.class, cCarKindEntity.getGroupCarKind());
                                statisticCount.setCargocode(cargoDataSpecificationEntity.getxFourthCode());
                            }
                        } else {
                            throw new HdRunTimeException("无车类信息");
                        }

                        statisticCount.setCargomark("");
                        statisticCount.setPackagecode("");
                        statisticCount.setFormat("");
                        // statisticCount.setOrigincode("");
                        statisticCount.setMatacode("");
                        String carTyp = ObjectUtils.toString(obj[1]);
                        CCarTyp cCarTypEntity = JpaUtils.findById(CCarTyp.class, carTyp);
                        if (cCarTypEntity != null) {
                            if (HdUtils.strNotNull(cCarTypEntity.getBrandCod())) {
                                statisticCount.setBrandcode(cCarTypEntity.getBrandCod());
                            } else {
                                statisticCount.setBrandcode("");
                            }
                        } else {
                            statisticCount.setBrandcode("");
                        }

                        statisticCount.setYardcode("");
                        // statisticCount.setYardcode(portCar.getCyAreaNo().substring(2,
                        // 4));
                        statisticCount.setLocationcode("");
                        statisticCount.setSublocationcode("");
                        statisticCount.setPilecode("");
                        statisticCount.setPiecesno("");

                        String cargoNum = ObjectUtils.toString(obj[4]);
                        if (HdUtils.strNotNull(cargoNum)) {
                            statisticCount.setCargonum(new BigDecimal(cargoNum));
                        } else {
                            statisticCount.setCargonum(new BigDecimal(0));
                        }

                        String cargoWgt = ObjectUtils.toString(obj[5]);
                        if (HdUtils.strNotNull(cargoWgt)) {
                            statisticCount.setCargowgt(new BigDecimal(cargoWgt));
                        } else {
                            statisticCount.setCargowgt(new BigDecimal(0));
                        }

                        statisticCount.setDescription("");
                        statisticCount.setTeamorgnid(ship.getDockCod());
                        statisticCount.setSubmitflag("0");
                        statisticCount.setSubmitname(HdUtils.getCurUser().getAccount());
                        DateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                        statisticCount.setSubmittime(sdf2.format(HdUtils.getDateTime()));
                        JpaUtils.save(statisticCount);

                    } else {
                        throw new HdRunTimeException("暂无新局调信息！");
                    }
                }
            } else {
                throw new HdRunTimeException("该船暂无信息！");
            }
        }

        return HdUtils.genMsg();
    }

    @Transactional
    public HdMessageCode statisticCountJs(String inoutcytim, String portTyp) {
        String jpql = "SELECT s FROM StatisticCountJs s";
        List<StatisticCountJs> statisticCountJsLists = JpaUtils.findAll(jpql, null);
        if (statisticCountJsLists.size() > 0) {
            JpaUtils.removeAll(statisticCountJsLists);
        }
        Timestamp inoutcytimfl = HdUtils.strToDate(inoutcytim);
        String inoutcytime = HdUtils.getDateStr(inoutcytimfl);
        // 集港
        if (portTyp.equals("TI")) {
            String tiSQL = "SELECT distinct\n" + "       '1' as ioyardFlag ,\n" + "       '2' as ioyardWay,\n"
                    + "       t1.i_e_id,\n" + "       t1.trade_id,\n" + "       t2.CONTRACT_NO,\n"
                    + "       t0.BILL_NO,\n" + "       t1.CAR_TYP,\n" + "       t1.SHIP_NO,\n" + "       t1.CAR_KIND,\n"
                    + "       t1.cy_area_no,\n" + "       t2.consign_cod,\n" + "       t2.agent_cod,\n"
                    + "       t1.brand_cod,\n" + "       COUNT(t0.PORT_CAR_NO) AS CARNUMBER,\n" + "       0 weights\n"
                    + "  FROM WORK_COMMAND t0,\n" + "       PORT_CAR t1,\n" + "       CONTRACT_IE_DOC t2\n"
                    + " WHERE t0.PORT_CAR_NO = t1.PORT_CAR_NO\n" + "   AND t0.contract_no = t2.contract_no\n"
                    + "   AND t0.WORK_TYP = 'TI'\n" + "   AND length(t1.vin_no) > '1'\n"
                    + "   AND trunc(t0.in_cy_tim, 'dd') = to_date('" + inoutcytime + "','yyyy-MM-dd')\n"
                    + " GROUP BY t1.i_e_id, t1.trade_id, t0.BILL_NO,t1.CAR_TYP,t2.CONTRACT_NO,t1.CAR_KIND,t1.cy_area_no,\n"
                    + "          t2.consign_cod, t2.agent_cod, t1.brand_cod, t1.ship_no\n" + " ORDER BY t0.BILL_NO";
            List<Map<String, Object>> lists = JpaUtils.getEntityManager().createNativeQuery(tiSQL)
                    .setHint(QueryHints.RESULT_TYPE, ResultType.Map).getResultList();
            if (lists.size() > 0) {
                for (Map<String, Object> list : lists) {
                    StatisticCountJs statisticCount = new StatisticCountJs();
                    statisticCount.setIoyardid(HdUtils.genUuid());
                    // statisticCount.setShipNo(shipNo);
                    // statisticCount.setShipid(ship.getNewEShipId());
                    // statisticCount.setSvoyageid(ship.getNewGroupShipNo());
                    // statisticCount.setShipname(ship.getcShipNam());
                    // statisticCount.setVoyage(ship.getIvoyage());
                    statisticCount.setIeflag((String) list.get("I_E_ID"));
                    statisticCount.setIoyardway("2");
                    statisticCount.setIoyardflag("1");// 集港 入库
                    statisticCount.setIoyarddate(inoutcytim);
                    statisticCount.setJobid("*");
                    statisticCount.setInformid("*");
                    statisticCount.setBillNo((String) list.get("BILL_NO"));
                    statisticCount.setCargoid((String) list.get("CONTRACT_NO"));
                    statisticCount.setTradeflag((String) list.get("TRADE_ID"));
                    statisticCount.setForwardercode((String) list.get("AGENT_COD"));// 货代
                    // 船名
                    if (HdUtils.strNotNull((String) list.get("SHIP_NO"))) {
                        Ship ship = JpaUtils.findById(Ship.class, (String) list.get("SHIP_NO"));
                        if (ship != null) {
                            if ("存栈".equals(ship.getcShipNam())) {
                                statisticCount.setShipname("");
                            } else {
                                statisticCount.setShipname(ship.getcShipNam());
                            }
                            if (list.get("I_E_ID").equals("I")) {
                                statisticCount.setVoyage(ship.getIvoyage());
                            } else {
                                statisticCount.setVoyage(ship.getEvoyage());
                            }
                        }
                    }

                    // 货主
                    // if(HdUtils.strNotNull((String)list.get("CONSIGN_COD"))) {
                    //// String clientCod = (String)list.get("CONSIGN_COD");
                    // CClientCod client = JpaUtils.findById(CClientCod.class,
                    // (String)list.get("CONSIGN_COD"));
                    // if(client != null) {
                    // String hzJpql = "SELECT v FROM VGroupCorpClient v where
                    // v.clientName like :clientName";
                    // QueryParamLs params = new QueryParamLs();
                    // params.addParam("clientName", "%"+ client.getcClientNam()
                    // +"%");
                    // List<VGroupCorpClient> CClientCodList =
                    // JpaUtils.findAll(hzJpql, params);
                    // if(CClientCodList.size() > 0) {
                    // statisticCount.setConsignorcode(CClientCodList.get(0).getClientCode());
                    // } else {
                    // statisticCount.setConsignorcode("");
                    // }
                    // }
                    // } else {
                    // statisticCount.setConsignorcode("");
                    // }
                    //
                    statisticCount.setConsigncode("");
                    // statisticCount.setCnorcode("");
                    // statisticCount.setCneecode("");
                    // statisticCount.setConsignorcode("");
                    CCarTyp data = JpaUtils.findById(CCarTyp.class, (String) list.get("CAR_TYP"));
                    if (data != null) {
                        String brandCod = data.getBrandCod();
                        if (HdUtils.strNotNull(brandCod)) {
                            String jpqlx = "select a from CBrandDetail a where a.brandCod =:brandCod";
                            QueryParamLs paramx = new QueryParamLs();
                            paramx.addParam("brandCod", brandCod);
                            List<CBrandDetail> cBrandDetailList = JpaUtils.findAll(jpqlx, paramx);
                            if (cBrandDetailList.size() > 0) {
                                CBrandDetail bean = cBrandDetailList.get(0);
                                if (HdUtils.strNotNull(bean.getOriginCode())) {
                                    statisticCount.setOrigincode(bean.getOriginCode());// 产地
                                } else {
                                    statisticCount.setOrigincode("");
                                }
                                if (HdUtils.strNotNull(bean.getFlowDir())) {
                                    statisticCount.setFlowDir(bean.getFlowDir());// 流向
                                } else {
                                    statisticCount.setFlowDir("");
                                }
                                if (HdUtils.strNotNull(bean.getAgentCod())) {
                                    statisticCount.setForwardercode(bean.getAgentCod());// 货代
                                } else {
                                    statisticCount.setForwardercode("");// 货代
                                }

                                if (HdUtils.strNotNull(bean.getClientCod())) {
                                    statisticCount.setConsignorcode(bean.getClientCod());// 货主
                                } else {
                                    statisticCount.setConsignorcode("");// 货主
                                }

                                if (HdUtils.strNotNull(bean.getConsignCod())) {
                                    statisticCount.setCnorcode(bean.getConsignCod());// 发货人
                                } else {
                                    statisticCount.setCnorcode("");// 发货人
                                }

                                if (HdUtils.strNotNull(bean.getReceiveCod())) {
                                    statisticCount.setCneecode(bean.getReceiveCod());// 发货人
                                } else {
                                    statisticCount.setCneecode("");// 发货人
                                }
                            }
                        }
                    }
                    if (HdUtils.strIsNull((String) list.get("CAR_KIND"))) {
                        continue;
                    }
                    CCarKind cCarKindEntity = JpaUtils.findById(CCarKind.class, (String) list.get("CAR_KIND"));
                    if (cCarKindEntity != null) {
                        if (HdUtils.strIsNull(cCarKindEntity.getGroupCarKind())) {
                            throw new HdRunTimeException("车类不存在:" + cCarKindEntity.getCarKindNam() + "！");
                        } else {
                            CargoDataSpecification cargoDataSpecificationEntity = JpaUtils
                                    .findById(CargoDataSpecification.class, cCarKindEntity.getGroupCarKind());
                            statisticCount.setCargocode(cargoDataSpecificationEntity.getxFourthCode());
                        }
                    } else {
                        throw new HdRunTimeException("无车类信息");
                    }

                    statisticCount.setCargomark("");
                    statisticCount.setPackagecode("");
                    statisticCount.setFormat("");
                    // statisticCount.setOrigincode("");
                    statisticCount.setMatacode("");

                    if (HdUtils.strNotNull((String) list.get("BRAND_COD"))) {
                        statisticCount.setBrandcode((String) list.get("BRAND_COD"));

                        // CBrand brandEntity = JpaUtils.findById(CBrand.class,
                        // (String)list.get("BRAND_COD"));
                        // String brandJpql = "SELECT v FROM VGroupCorpBrand v
                        // where v.brandName like :brandNam";
                        // QueryParamLs brandParams = new QueryParamLs();
                        // brandParams.addParam("brandNam",
                        // '%'+brandEntity.getBrandNam()+'%');
                        // List<VGroupCorpBrand> brandCodeList =
                        // JpaUtils.findAll(brandJpql, brandParams);
                        // if(brandCodeList.size() > 0) {
                        // statisticCount.setBrandcode(brandCodeList.get(0).getBrandCode());
                        // }
                    }

                    String yardCode = "SELECT v FROM VGroupCorpYard v where v.yardCode =:yardCode";
                    QueryParamLs yardCodeParams = new QueryParamLs();
                    yardCodeParams.addParam("yardCode", list.get("CY_AREA_NO"));
                    List<VGroupCorpYard> yardCodeList = JpaUtils.findAll(yardCode, yardCodeParams);
                    if (yardCodeList.size() > 0) {
                        statisticCount.setYardcode(yardCodeList.get(0).getYardCode());
                    } else {

                    }
                    // statisticCount.setYardcode(portCar.getCyAreaNo().substring(2,
                    // 4));
                    statisticCount.setLocationcode("");
                    statisticCount.setSublocationcode("");
                    statisticCount.setPilecode("");
                    statisticCount.setPiecesno("");

                    String cargoNum = list.get("CARNUMBER").toString();
                    if (HdUtils.strNotNull(cargoNum)) {
                        statisticCount.setCargonum(new BigDecimal(cargoNum));
                    } else {
                        statisticCount.setCargonum(new BigDecimal(0));
                    }

                    // 重量
                    String carType = (String) list.get("CAR_TYP");
                    if (HdUtils.strNotNull(carType)) {
                        CCarTyp cCarTyp = JpaUtils.findById(CCarTyp.class, carType);
                        if (cCarTyp != null) {
                            BigDecimal wgt = cCarTyp.getWeights().multiply(statisticCount.getCargonum());
                            statisticCount.setCargowgt(wgt);

                        } else {
                            statisticCount.setCargowgt(new BigDecimal(0));
                        }
                    } else {
                        statisticCount.setCargowgt(new BigDecimal(0));
                    }
                    statisticCount.setDescription("");
                    statisticCount.setTeamorgnid(Ship.GZ);
                    statisticCount.setSubmitflag("0");
                    statisticCount.setSubmitname(HdUtils.getCurUser().getAccount());
                    DateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    statisticCount.setSubmittime(sdf2.format(HdUtils.getDateTime()));
                    JpaUtils.save(statisticCount);
                }
            }
        }
        // 疏港
        if (portTyp.equals("TO")) {
            String tiSQL = "SELECT distinct\n" + "       '0' as ioyardFlag ,\n" + "       '2' as ioyardWay,\n"
                    + "       t1.i_e_id,\n" + "       t1.trade_id,\n" + "       t2.CONTRACT_NO,\n"
                    + "       t0.BILL_NO,\n" + "       t1.CAR_TYP,\n" + "       t1.CAR_KIND,\n" + "       t1.SHIP_NO,\n"
                    + "       t1.cy_area_no,\n" + "       t2.consign_cod,\n" + "       t2.agent_cod,\n"
                    + "       t1.brand_cod,\n" + "       COUNT(t0.PORT_CAR_NO) AS CARNUMBER,\n" + "       0 weights\n"
                    + "  FROM WORK_COMMAND t0,\n" + "       PORT_CAR t1,\n" + "       CONTRACT_IE_DOC t2\n"
                    + " WHERE t0.PORT_CAR_NO = t1.PORT_CAR_NO\n" + "   AND t0.contract_no = t2.contract_no\n"
                    + "   AND t0.WORK_TYP = 'TO'\n" + "   AND length(t1.vin_no) > '1'\n"
                    + "   AND trunc(t0.in_cy_tim, 'dd') = to_date('" + inoutcytime + "','yyyy-MM-dd')\n"
                    + " GROUP BY t1.i_e_id, t1.trade_id, t0.BILL_NO,t1.CAR_TYP,t2.CONTRACT_NO,t1.CAR_KIND,t1.cy_area_no,\n"
                    + "          t2.consign_cod, t2.agent_cod, t1.brand_cod,t1.ship_no\n" + " ORDER BY t0.BILL_NO";
            List<Map<String, Object>> lists = JpaUtils.getEntityManager().createNativeQuery(tiSQL)
                    .setHint(QueryHints.RESULT_TYPE, ResultType.Map).getResultList();
            if (lists.size() > 0) {
                for (Map<String, Object> list : lists) {
                    StatisticCountJs statisticCount = new StatisticCountJs();
                    statisticCount.setIoyardid(HdUtils.genUuid());
                    // statisticCount.setShipNo(shipNo);
                    // statisticCount.setShipid(ship.getNewEShipId());
                    // statisticCount.setSvoyageid(ship.getNewGroupShipNo());
                    // statisticCount.setShipname(ship.getcShipNam());
                    // statisticCount.setVoyage(ship.getIvoyage());
                    statisticCount.setIeflag((String) list.get("I_E_ID"));
                    statisticCount.setIoyardway("2");
                    statisticCount.setIoyardflag("0");// 疏港 出库
                    statisticCount.setIoyarddate(inoutcytim);
                    // 船名
                    if (HdUtils.strNotNull((String) list.get("SHIP_NO"))) {
                        Ship ship = JpaUtils.findById(Ship.class, (String) list.get("SHIP_NO"));
                        if (ship != null) {
                            if ("存栈".equals(ship.getcShipNam())) {
                                statisticCount.setShipname("");
                            } else {
                                statisticCount.setShipname(ship.getcShipNam());
                            }
                            if (list.get("I_E_ID").equals("I")) {
                                statisticCount.setVoyage(ship.getIvoyage());
                            } else {
                                statisticCount.setVoyage(ship.getEvoyage());
                            }
                        }
                    }
                    statisticCount.setJobid("*");
                    statisticCount.setInformid("*");
                    statisticCount.setBillNo((String) list.get("BILL_NO"));
                    statisticCount.setCargoid((String) list.get("CONTRACT_NO"));
                    statisticCount.setTradeflag((String) list.get("TRADE_ID"));
                    statisticCount.setForwardercode((String) list.get("AGENT_COD"));// 货代
                    // 货主
                    // if(HdUtils.strNotNull((String)list.get("CONSIGN_COD"))) {
                    //// String clientCod = (String)list.get("CONSIGN_COD");
                    // CClientCod client = JpaUtils.findById(CClientCod.class,
                    // (String)list.get("CONSIGN_COD"));
                    // String hzJpql = "SELECT v FROM VGroupCorpClient v where
                    // v.clientName like :clientName";
                    // QueryParamLs params = new QueryParamLs();
                    // params.addParam("clientName", "%"+ client.getcClientNam()
                    // +"%");
                    // List<VGroupCorpClient> CClientCodList =
                    // JpaUtils.findAll(hzJpql, params);
                    // if(CClientCodList.size() > 0) {
                    // statisticCount.setConsignorcode(CClientCodList.get(0).getClientCode());
                    // } else {
                    // statisticCount.setConsignorcode("");
                    // }
                    // } else {
                    // statisticCount.setConsignorcode("");
                    // }
                    //
                    statisticCount.setConsigncode("");
                    // statisticCount.setCnorcode("");
                    // statisticCount.setCneecode("");
                    // statisticCount.setConsignorcode("");
                    CCarTyp data = JpaUtils.findById(CCarTyp.class, (String) list.get("CAR_TYP"));
                    if (data != null) {
                        String brandCod = data.getBrandCod();
                        if (HdUtils.strNotNull(brandCod)) {
                            String jpqlx = "select a from CBrandDetail a where a.brandCod =:brandCod";
                            QueryParamLs paramx = new QueryParamLs();
                            paramx.addParam("brandCod", brandCod);
                            List<CBrandDetail> cBrandDetailList = JpaUtils.findAll(jpqlx, paramx);
                            if (cBrandDetailList.size() > 0) {
                                CBrandDetail bean = cBrandDetailList.get(0);
                                if (HdUtils.strNotNull(bean.getOriginCode())) {
                                    statisticCount.setOrigincode(bean.getOriginCode());// 产地
                                } else {
                                    statisticCount.setOrigincode("");
                                }
                                if (HdUtils.strNotNull(bean.getFlowDir())) {
                                    statisticCount.setFlowDir(bean.getFlowDir());// 流向
                                } else {
                                    statisticCount.setFlowDir("");
                                }
                                if (HdUtils.strNotNull(bean.getAgentCod())) {
                                    statisticCount.setForwardercode(bean.getAgentCod());// 货代
                                } else {
                                    statisticCount.setForwardercode("");// 货代
                                }

                                if (HdUtils.strNotNull(bean.getClientCod())) {
                                    statisticCount.setConsignorcode(bean.getClientCod());// 货主
                                } else {
                                    statisticCount.setConsignorcode("");// 货主
                                }

                                if (HdUtils.strNotNull(bean.getConsignCod())) {
                                    statisticCount.setCnorcode(bean.getConsignCod());// 发货人
                                } else {
                                    statisticCount.setCnorcode("");// 发货人
                                }

                                if (HdUtils.strNotNull(bean.getReceiveCod())) {
                                    statisticCount.setCneecode(bean.getReceiveCod());// 发货人
                                } else {
                                    statisticCount.setCneecode("");// 发货人
                                }
                            }
                        }
                    }
                    CCarKind cCarKindEntity = JpaUtils.findById(CCarKind.class, (String) list.get("CAR_KIND"));
                    if (cCarKindEntity != null) {
                        if (HdUtils.strIsNull(cCarKindEntity.getGroupCarKind())) {
                            throw new HdRunTimeException("车类不存在:" + cCarKindEntity.getCarKindNam() + "！");
                        } else {
                            CargoDataSpecification cargoDataSpecificationEntity = JpaUtils
                                    .findById(CargoDataSpecification.class, cCarKindEntity.getGroupCarKind());
                            statisticCount.setCargocode(cargoDataSpecificationEntity.getxFourthCode());
                        }
                    } else {
                        throw new HdRunTimeException("无车类信息");
                    }

                    statisticCount.setCargomark("");
                    statisticCount.setPackagecode("");
                    statisticCount.setFormat("");
                    statisticCount.setMatacode("");

                    if (HdUtils.strNotNull((String) list.get("BRAND_COD"))) {
                        statisticCount.setBrandcode((String) list.get("BRAND_COD"));

                    }

                    String yardCode = "SELECT v FROM VGroupCorpYard v where v.yardCode =:yardCode";
                    QueryParamLs yardCodeParams = new QueryParamLs();
                    yardCodeParams.addParam("yardCode", list.get("CY_AREA_NO"));
                    List<VGroupCorpYard> yardCodeList = JpaUtils.findAll(yardCode, yardCodeParams);
                    if (yardCodeList.size() > 0) {
                        statisticCount.setYardcode(yardCodeList.get(0).getYardCode());
                    }
                    statisticCount.setLocationcode("");
                    statisticCount.setSublocationcode("");
                    statisticCount.setPilecode("");
                    statisticCount.setPiecesno("");

                    String cargoNum = list.get("CARNUMBER").toString();
                    if (HdUtils.strNotNull(cargoNum)) {
                        statisticCount.setCargonum(new BigDecimal(cargoNum));
                    } else {
                        statisticCount.setCargonum(new BigDecimal(0));
                    }
                    // 重量
                    String carType = (String) list.get("CAR_TYP");
                    if (HdUtils.strNotNull(carType)) {
                        CCarTyp cCarTyp = JpaUtils.findById(CCarTyp.class, carType);
                        if (cCarTyp != null) {
                            BigDecimal wgt = cCarTyp.getWeights().multiply(statisticCount.getCargonum());
                            statisticCount.setCargowgt(wgt);

                        } else {
                            statisticCount.setCargowgt(new BigDecimal(0));
                        }
                    } else {
                        statisticCount.setCargowgt(new BigDecimal(0));
                    }
                    statisticCount.setDescription("");
                    statisticCount.setTeamorgnid(Ship.GZ);
                    statisticCount.setSubmitflag("0");
                    statisticCount.setSubmitname(HdUtils.getCurUser().getAccount());
                    DateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    statisticCount.setSubmittime(sdf2.format(HdUtils.getDateTime()));
                    JpaUtils.save(statisticCount);
                }
            }
        }
        return HdUtils.genMsg();
    }

    @Override
    public String sendStatisticCountJs() {
        String message = "success";
        String jpql = "SELECT s FROM StatisticCountJs s ";
        List<StatisticCountJs> statisticCountList = JpaUtils.findAll(jpql, null);
        for (StatisticCountJs sc : statisticCountList) {
            JSONObject jsonObj = new JSONObject();
            Map<String, String> map = new HashMap<String, String>();
            jsonObj.put("cmdId", "2020");
            jsonObj.put("coId", Ship.GZ);
            map.put("billNo", sc.getBillNo());
            map.put("ioyardId", sc.getIoyardid());
            map.put("ioyardWay", sc.getIoyardway());
            map.put("ioyardFlag", sc.getIoyardflag());
            map.put("ioyardDate", sc.getIoyarddate());
            map.put("jobId", sc.getJobid());
            map.put("informId", sc.getInformid());
            map.put("caroId", sc.getCargoid());
            map.put("shipId", sc.getShipid());
            map.put("svoyageId", sc.getSvoyageid());
            map.put("shipName", sc.getShipname());
            map.put("voyage", sc.getVoyage());
            if (sc.getTradeflag().equals("1")) {
                map.put("tradeFlag", "2");
            } else if (sc.getTradeflag().equals("2")) {
                map.put("tradeFlag", "1");
            }
            map.put("ieFlag", sc.getIeflag());
            map.put("forwarderCode", sc.getForwardercode());
            map.put("consignorCode", sc.getConsignorcode());
            map.put("consignCode", sc.getConsigncode());
            map.put("cnorCode", sc.getCnorcode());
            map.put("cneeCode", sc.getCneecode());

            map.put("cargoCode", sc.getCargocode());
            map.put("cargoMark", sc.getCargomark());
            map.put("packageCode", sc.getPackagecode());
            map.put("format", sc.getFormat());
            map.put("originCode", sc.getOrigincode());

            // 流向
            map.put("flowDir", sc.getFlowDir());
            map.put("mataCode", sc.getMatacode());
            map.put("trainNum", sc.getCargonum().toString());
            map.put("teamOrgnld", Ship.GZ);
            // map.put("brandCode", sc.getBrandcode());

            if (HdUtils.strNotNull(sc.getBrandcode())) {
                CBrand brand = JpaUtils.findById(CBrand.class, sc.getBrandcode());
                if (brand != null) {
                    if (HdUtils.strNotNull(brand.getBrandNam())) {
                        String branView = "SELECT v FROM VGroupCorpBrand v where v.brandName like :brandName";
                        QueryParamLs vParams = new QueryParamLs();
                        vParams.addParam("brandName", "%" + brand.getBrandNam() + "%");
                        List<VGroupCorpBrand> vGroupCorpBrandList = JpaUtils.findAll(branView, vParams);
                        if (vGroupCorpBrandList.size() > 0) {
                            map.put("brandCode", vGroupCorpBrandList.get(0).getBrandCode());
                        } else {
                            map.put("brandCode", "");
                        }
                    } else {
                        map.put("brandCode", "");
                    }
                } else {
                    map.put("brandCode", "");
                }

            } else {
                map.put("brandCode", "");
            }
            if (HdUtils.strIsNull(sc.getYardcode())) {
                continue;
            }
            map.put("yardCode", sc.getYardcode());
            map.put("locationCode", sc.getLocationcode());
            map.put("piecesNo", "");
            map.put("cargoNum", sc.getCargonum().toString());
            map.put("cargoWgt", sc.getCargowgt().toString());
            map.put("description", sc.getDescription());
            map.put("teamOrgnId", sc.getTeamorgnid());
            map.put("submitFlag", sc.getSubmitflag());
            map.put("submitName", sc.getSubmitname());

            map.put("submitTime", sc.getSubmittime());
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
                    JSONObject jsonObject = JSONObject.fromObject(response);
                    RespInter resp = (RespInter) JSONObject.toBean(jsonObject, RespInter.class);
                    String resCode = "0000";
                    String resMsg = "OK";
                    if (resCode.equals(resp.getResCode()) && resMsg.equals(resp.getResMsg())) {
                    }
                    if (!resCode.equals(resp.getResCode()) || !resMsg.equals(resp.getResMsg())) {
                        message = "error";
                        break;
                    }
                } catch (Exception e) {
                    message = "error";
                }
                // 断开连接
                conn.disconnect();
            } catch (Exception e) {
                message = "error";
                throw new HdRunTimeException("上报集团失败！");
            }
        }
        return message;
    }

    @Override
    public HdMessageCode syncPortCatPic(Map mapPam) {
        HdMessageCode hdMessageCode = HdUtils.genMsg();
        String shipNo = mapPam.get("shipNo") + "";
        String sql = "SELECT  CAST(WM_CONCAT(CY_REMARKS) AS VARCHAR(100)) CY_REMARKS,BILL_NO FROM ( "
                + " SELECT CY_REMARKS,BILL_NO FROM V_PORT_CAR WHERE ship_no='" + shipNo
                + "' GROUP BY CY_REMARKS,BILL_NO " + " ) GROUP BY BILL_NO ";
        List<Map<String, Object>> lst = JpaUtils.getEntityManager().createNativeQuery(sql)
                .setHint(QueryHints.RESULT_TYPE, ResultType.Map).getResultList();

        Map billMap = new HashMap();
        for (Map<String, Object> mapItem : lst) {
            billMap.put(mapItem.get("BILL_NO"), mapItem.get("CY_REMARKS"));
        }

        JSONObject gradeData = JSONObject.fromObject(mapPam.get("gradeData"));

        // 添加列
        JSONArray columntielts = JSONArray.fromObject(gradeData.get("columntielts"));
        columntielts.add("yard");
        JSONArray columns = JSONArray.fromObject(gradeData.get("column"));
        columns.add("yard");

        JSONObject rows = JSONObject.fromObject(gradeData.get("row"));
        JSONArray dataLst = JSONArray.fromObject(rows.get("rows"));
        JSONArray rtArray = new JSONArray();
        if (dataLst.size() > 0) {
            for (Object object : dataLst) {
                JSONObject jsonObject = JSONObject.fromObject(object);
                if (jsonObject.containsKey("Booking No.".replaceAll("[./]", ""))) {
                    String billNo = jsonObject.getString("Booking No.".replaceAll("[./]", ""));
                    billNo = billNo.substring(billNo.indexOf("]") + 1).trim();
                    if (billMap.containsKey(billNo)) {
                        jsonObject.put("yard", billMap.get(billNo) + "");
                    } else {
                        jsonObject.put("yard", "");
                    }
                }
                rtArray.add(jsonObject);
            }
        }
        gradeData.put("columntielts", columntielts);
        gradeData.put("column", columns);
        rows.put("rows", rtArray);
        gradeData.put("row", rows);
        gradeData = JSONObject.fromObject(gradeData.toString().replaceAll("null", "\"\""));
        hdMessageCode.setData(gradeData);
        return hdMessageCode;
    }
}
