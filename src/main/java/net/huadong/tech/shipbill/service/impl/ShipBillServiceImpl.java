package net.huadong.tech.shipbill.service.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.UUID;

import net.huadong.tech.shipbill.entity.*;

import net.huadong.tech.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.Interface.entity.VGroupCorpCargo;
import net.huadong.tech.base.bean.EzTreeBean;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.bean.HdRunTimeException;
import net.huadong.tech.base.entity.CBrand;
import net.huadong.tech.base.entity.CCarKind;
import net.huadong.tech.base.entity.CCarTyp;
import net.huadong.tech.base.entity.CClientCod;
import net.huadong.tech.base.entity.CDock;
import net.huadong.tech.base.entity.CPort;
import net.huadong.tech.base.entity.VWlBillVehicle;
import net.huadong.tech.base.service.CPortService;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.tech.inter.entity.RespInter;
import net.huadong.tech.inter.entity.ShipBillInter;
import net.huadong.tech.ship.entity.CShipData;
import net.huadong.tech.ship.entity.Ship;
import net.huadong.tech.shipbill.service.ShipBillService;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;
import net.huadong.tech.task.ShipTask;
import net.huadong.tech.util.HdUtils;
import net.huadong.tech.work.entity.CargoInfo;
import net.huadong.tech.work.entity.WorkCommand;
import net.huadong.tech.work.entity.WorkQueue;
import net.sf.json.JSONArray;
//import net.huadong.tech.util.CommonUtil;
import net.sf.json.JSONObject;

import javax.sound.sampled.Port;

/**
 * @author
 */
@Component
public class ShipBillServiceImpl implements ShipBillService {
    private static final String JK = "I";
    private static final String CK = "E";
    private static char[] charSet = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();
    @Value("${api.service.ip}")
    private String apiServiceIp;
    @Value("${tjgjt.service.ip}")
    private String tjgjtServiceIp;
    @Autowired
    private CPortService cPortService;

    public HdEzuiDatagridData findShipVoyage(HdQuery hdQuery) {
        String jpql = "select a from Ship a where 1=1 ";
        String shipNam = hdQuery.getStr("shipNam");
        String Voyage = hdQuery.getStr("Voyage");
        String portId = hdQuery.getStr("portId");
        QueryParamLs paramLs = new QueryParamLs();
        if (HdUtils.strNotNull(shipNam)) {
            jpql += "and a.shipNam =:shipNam ";
            paramLs.addParam("shipNam", shipNam);
        }
        if (HdUtils.strNotNull(portId) && portId == "I") {
            jpql += "and a.ivoyage =:Voyage ";
            paramLs.addParam("ivoyage", Voyage);
        }
        if (HdUtils.strNotNull(portId) && portId == "E") {
            jpql += "and a.evoyage =:Voyage ";
            paramLs.addParam("evoyage", Voyage);
        }

        jpql += " order by a.recTim desc";
        return JpaUtils.findByEz(jpql, paramLs, hdQuery);
    }

    @Override
    @Transactional//下货纸写死外贸出口
    public  HdMessageCode genPaper(String shipNos, String billNos,String carNums){
        HdMessageCode hdMessageCode = new HdMessageCode();
      //  try {
                String jpqlShipLoadBill = "select a  from ShipBill a where a.shipNo=:shipNo and (a.iEId='2' or a.iEId='E')";
                QueryParamLs queryParamLsForce = new QueryParamLs();
                queryParamLsForce.addParam("shipNo", shipNos); 
                List<ShipBill> shipLoadBills = JpaUtils.findAll(jpqlShipLoadBill, queryParamLsForce);
                if(shipLoadBills.size()>0){
                	//下货纸
                	for(int i=0;i<shipLoadBills.size();i++){
                		//SHIP_NO   BILL_NO REC_NAM REC_TIM BILL_NUM
                    	String jpqlShipLoadBills = "select a  from ShipLoadBill a where a.shipNo=:shipNo and a.billNo=:billNo ";
                        QueryParamLs queryParamLsForces = new QueryParamLs();
                        queryParamLsForces.addParam("shipNo", shipLoadBills.get(i).getShipNo()); 
                        queryParamLsForces.addParam("billNo", shipLoadBills.get(i).getBillNo()); 
                        List<ShipLoadBill> shipLoadBillss = JpaUtils.findAll(jpqlShipLoadBills, queryParamLsForces);
                        if(shipLoadBillss.size()>0){
                        	//找到了，进行更新  
                        	ShipLoadBill  shipLoadBill=shipLoadBillss.get(0);
                        	//提单数
                        	shipLoadBill.setBillNum(shipLoadBills.get(i).getPieces().intValue());
                        	shipLoadBill.setRecNam(HdUtils.getCurUser().getName());
                        	shipLoadBill.setRecTim(HdUtils.getDateTime());
                        	JpaUtils.update(shipLoadBill);
                        }else{
                        	//找不到插入
                        	ShipLoadBill  shipLoadBill=new ShipLoadBill();
                        	shipLoadBill.setId(CommonUtil.genUuid());
                        	shipLoadBill.setiEId("E");
                        	shipLoadBill.setShipNo(shipLoadBills.get(i).getShipNo());
                        	shipLoadBill.setBillNo(shipLoadBills.get(i).getBillNo());
                        	if(null!= shipLoadBills.get(i).getPieces())
                            {
                                shipLoadBill.setBillNum(shipLoadBills.get(i).getPieces().intValue());
                            }

                        	shipLoadBill.setRecNam(HdUtils.getCurUser().getName());
                        	shipLoadBill.setRecTim(HdUtils.getDateTime());
                        	JpaUtils.save(shipLoadBill);
                        }
                	}
                	
                }else{
                	hdMessageCode.setMessage("该船舶无提单，请到舱单中添加后再生成下货纸！");
                    hdMessageCode.setCode("-1");
                }
            hdMessageCode.setMessage("下货纸确认生成成功！");
            hdMessageCode.setCode("200");

//        }catch (Exception e){
//            hdMessageCode.setMessage(e.getMessage());
//            hdMessageCode.setCode("-1");
//        }
        return hdMessageCode;
    }


    @Override
    @Transactional
    public HdMessageCode paperBind(String shipNos, String billNos, String carNums) {
            //tips:放入这个shipLoadBill表里是为了配合手持；
    		//查询这个提单下的所有下货纸数量
    	HdMessageCode hdMessageCode = new HdMessageCode();
    	String jpqlShipLoadBills = "select a  from ShipLoadBill a where a.shipNo=:shipNo  ";
        QueryParamLs queryParamLsForces = new QueryParamLs();
        queryParamLsForces.addParam("shipNo",shipNos);  
        List<ShipLoadBill> shipLoadBillss = JpaUtils.findAll(jpqlShipLoadBills, queryParamLsForces);
        String msg="";
        if(shipLoadBillss.size()>0){
        	//下货纸有数据
        	for(ShipLoadBill shipLoadBill:shipLoadBillss){
        		String jpql = " select a from PortCar a where a.shipNo =:shipNo and a.billNo=:billNo and a.currentStat =:currentStat ";//在场预提的状态0
                QueryParamLs queryParamLs = new QueryParamLs();
                queryParamLs.addParam("shipNo", shipLoadBill.getShipNo());
                queryParamLs.addParam("billNo", shipLoadBill.getBillNo());
                queryParamLs.addParam("currentStat","2");
                List<PortCar> portCarList = JpaUtils.findAll(jpql, queryParamLs);  
                if("1".equals(shipLoadBill.getForceId())||portCarList.size()>=shipLoadBill.getBillNum()){
                    shipLoadBill.setJqId("1");
                    String jpqlShipBill = "select a  from ShipBill a where a.shipNo=:shipNo and a.billNo =:billNo ";
                    QueryParamLs queryParamLsForce = new QueryParamLs();
                    queryParamLsForce.addParam("shipNo", shipLoadBill.getShipNo());
                    queryParamLsForce.addParam("billNo", shipLoadBill.getBillNo()); 
                    List<ShipBill> shipBills = JpaUtils.findAll(jpqlShipBill, queryParamLsForce);
                    for(ShipBill shipBills1:shipBills){ 
                    	shipBills1.setJqId("1");
                    	JpaUtils.update(shipBills1);
                    }
                    
                    JpaUtils.update(shipLoadBill);
                    for(PortCar portCar:portCarList){
                        portCar.setIsTiComplete("1");
                        JpaUtils.update(portCar);
                    }
                }else {
                    Integer unCar=shipLoadBill.getBillNum()-portCarList.size();
                    String temp ="单号："+shipLoadBill.getBillNo()+" 缺少"+unCar+" 辆;";
                    msg=msg+temp;
                }
        	}
        }else{
        	hdMessageCode.setMessage("下货纸中没有数据，无法验证是否集齐！ ");
            hdMessageCode.setCode("-1");
            return hdMessageCode;
        } 
        hdMessageCode.setMessage("下货纸下的提单集齐！ "+msg);
        hdMessageCode.setCode("200");
        return hdMessageCode;
    }

    //强制理货
    @Override
    @Transactional
    public HdMessageCode dealForce(String shipNos,String billNos){
        HdMessageCode hdMessageCode = new HdMessageCode();
        String[] shipNoArr = shipNos.split(",");
        String[] billNoArr = billNos.split(",");
        for (int i = 0; i < shipNoArr.length; i++) {
            String jpql = "select a  from ShipLoadBill a where a.shipNo=:shipNo and a.billNo =:billNo ";
            String jpqlShipBill = "select a  from ShipBill a where a.shipNo=:shipNo and a.billNo =:billNo ";
            QueryParamLs queryParamLsForce = new QueryParamLs();
            queryParamLsForce.addParam("shipNo", shipNoArr[i]);
            queryParamLsForce.addParam("billNo", billNoArr[i]);
            List<ShipLoadBill> shipLoadBills = JpaUtils.findAll(jpql, queryParamLsForce);
            List<ShipBill> shipBills = JpaUtils.findAll(jpqlShipBill, queryParamLsForce);
            if(shipLoadBills.size()>0){
                if(shipBills.size()>0){

                    ShipLoadBill shipLoadBill=shipLoadBills.get(0);
                    ShipBill shipBill=shipBills.get(0);
                    shipBill.setForceId("1");
                    shipLoadBill.setForceId("1");
                    shipLoadBill.setRecNam(HdUtils.getCurUser().getName());
                    shipLoadBill.setRecTim(new Date());
                    JpaUtils.update(shipBill);
                    JpaUtils.update(shipLoadBill);

                }else {
                    hdMessageCode.setMessage("提单号："+billNoArr[i]+"未找到舱单！");
                    hdMessageCode.setCode("-1");
                    return  hdMessageCode;
                }

            }else {
                hdMessageCode.setMessage("提单号："+billNoArr[i]+"未生成下货纸！");
                hdMessageCode.setCode("-1");
                return  hdMessageCode;
            }
        }
        hdMessageCode.setMessage("强制理货成功！");
        hdMessageCode.setCode("200");
        return  hdMessageCode;
    }



    //更新在场车状态为集齐
    public void  updatePortCarForPaper(List<PortCar> portCars){
    	for(PortCar portCar :portCars){
    		portCar.setIsTiComplete("1");
		}
	}

    public HdEzuiDatagridData findShipBillCar(HdQuery hdQuery) {
        String jpql = "select a from BillCar a where 1=1 ";
        String billNo = hdQuery.getStr("billNo");
        QueryParamLs paramLs = new QueryParamLs();
        if (HdUtils.strNotNull(billNo)) {
            jpql += "and a.billNo =:billNo ";
            paramLs.addParam("billNo", billNo);
        }
        jpql += " order by a.recTim desc";
        HdEzuiDatagridData results = JpaUtils.findByEz(jpql, paramLs, hdQuery);
        List<BillCar> billCarList = results.getRows();
        if (billCarList.size() > 0) {
            for (BillCar bc : billCarList) {
                if (HdUtils.strNotNull(bc.getCarKind())) {
                    CCarKind carkind = JpaUtils.findById(CCarKind.class, bc.getCarKind());
                    bc.setCarKindNam(carkind.getCarKindNam());
                }
                if (HdUtils.strNotNull(bc.getCarTyp())) {
                    CCarTyp ccartyp = JpaUtils.findById(CCarTyp.class, bc.getCarTyp());
                    bc.setCarTypNam(ccartyp.getCarTypNam());
                }
                if (HdUtils.strNotNull(bc.getBrandCod())) {
                    CBrand cbrand = JpaUtils.findById(CBrand.class, bc.getBrandCod());
                    bc.setBrandNam(cbrand.getBrandNam());
                }
            }
        }

        return results;
    }

    public HdEzuiDatagridData find(HdQuery hdQuery) {
        String jpql = "select a from ShipBill a where 1=1 ";
        String shipNo = hdQuery.getStr("shipNo");
        String iEId = hdQuery.getStr("iEId");
        String billNo = hdQuery.getStr("billNo");
        String brandCod = hdQuery.getStr("brandCod");

        QueryParamLs paramLs = new QueryParamLs();
        if (HdUtils.strNotNull(shipNo)) {
            jpql += "and a.shipNo =:shipNo ";
            paramLs.addParam("shipNo", shipNo);
        }
        if (HdUtils.strNotNull(iEId)) {
            jpql += "and a.iEId =:iEId ";
            paramLs.addParam("iEId", iEId);
        }
        if (HdUtils.strNotNull(billNo)) {
            jpql += "and a.billNo like :billNo ";
            paramLs.addParam("billNo", "%" + billNo + "%");
        }
        if (HdUtils.strIsNull(shipNo)) {
            jpql += "and a.shipNo =:shipNo ";
            paramLs.addParam("shipNo", "10000");
        }

        if (HdUtils.strNotNull(brandCod)) {
            jpql += "and a.brandCod =:brandCod ";
            paramLs.addParam("brandCod", brandCod);
        }

        // jpql += "order by a.billNo asc";
        HdEzuiDatagridData result = JpaUtils.findByEz(jpql, paramLs, hdQuery);
        List<ShipBill> shipbillList = result.getRows();
        if (shipbillList.size() > 0) {
            for (ShipBill sb : shipbillList) {
                if (HdUtils.strNotNull(sb.getBrandCod())) {
                    CBrand cb = JpaUtils.findById(CBrand.class, sb.getBrandCod());

                    if (cb != null)
                        sb.setBrandNam(cb.getBrandNam());
                }
                if (HdUtils.strNotNull(sb.getLoadPortCod())) {
                    CPort cp = JpaUtils.findById(CPort.class, sb.getLoadPortCod());
                    String jpqlc = "select a from CPort a where a.portCod=:portCod";
                    QueryParamLs paramLsc = new QueryParamLs();
                    paramLsc.addParam("portCod", sb.getLoadPortCod());
                    List<CPort> cportList = JpaUtils.findAll(jpqlc, paramLsc);
                    if (cportList.size() > 0) {
                        for (CPort c : cportList) {
                            sb.setLoadPortNam(c.getcPortNam());
                        }
                    }
                }
                if (HdUtils.strNotNull(sb.getDiscPortCod())) {
                    CPort cp = JpaUtils.findById(CPort.class, sb.getDiscPortCod());
                    String jpqlc = "select a from CPort a where a.portCod=:portCod";
                    QueryParamLs paramLsc = new QueryParamLs();
                    paramLsc.addParam("portCod", sb.getDiscPortCod());
                    List<CPort> cportList = JpaUtils.findAll(jpqlc, paramLsc);
                    if (cportList.size() > 0) {
                        for (CPort c : cportList) {
                            sb.setDiscPortNam(c.getcPortNam());
                        }
                    }

                }
                if (HdUtils.strNotNull(sb.getTranPortCod())) {
                    CPort cp = JpaUtils.findById(CPort.class, sb.getTranPortCod());
                    String jpqlc = "select a from CPort a where a.portCod=:portCod";
                    QueryParamLs paramLsc = new QueryParamLs();
                    paramLsc.addParam("portCod", sb.getTranPortCod());
                    List<CPort> cportList = JpaUtils.findAll(jpqlc, paramLsc);
                    if (cportList.size() > 0) {
                        for (CPort c : cportList) {
                            sb.setTranPortNam(c.getcPortNam());
                        }
                    }
                }

                if (HdUtils.strNotNull(sb.getDockCod())) {
                    CDock cd = JpaUtils.findById(CDock.class, sb.getDockCod());
                    if (cd != null) {
                        sb.setDockNam(cd.getcDockNam());
                    }
                    // sb.setDockNam(cd.getcDockNam());
                }
                String tempBillNo=sb.getBillNo();
                String tempShipNo =sb.getShipNo();
                String jpqlShipLoadBill = "select a  from ShipLoadBill a where a.shipNo=:shipNo and a.billNo =:billNo ";
                QueryParamLs queryParamLsForce = new QueryParamLs();
                queryParamLsForce.addParam("shipNo", tempShipNo);
                queryParamLsForce.addParam("billNo", tempBillNo);
                List<ShipLoadBill> shipLoadBills = JpaUtils.findAll(jpqlShipLoadBill, queryParamLsForce);
                if(shipLoadBills.size()>0)
                {
                    sb.setFittings(new BigDecimal(1));
                }else {
                    sb.setFittings(new BigDecimal(0));
                }

            }
        }
        return result;
    }

    /**
     * 舱单查询
     */
    public HdEzuiDatagridData findSBQuery(HdQuery hdQuery) {
        String jpql = "select a from ShipBill a where 1=1 ";

        QueryParamLs paramLs = new QueryParamLs();
        if (HdUtils.strNotNull(hdQuery.getStr("shipNo"))) {
            jpql += " and a.shipNo=:shipNo";
            paramLs.addParam("shipNo", hdQuery.getStr("shipNo"));
        }
        if (HdUtils.strNotNull(hdQuery.getStr("billNo"))) {
            jpql += " and a.billNo like :billNo";
            paramLs.addParam("billNo", "%" + hdQuery.getStr("billNo") + "%");
        }
        if (HdUtils.strNotNull(hdQuery.getStr("iEId"))) {
            jpql += " and a.iEId=:iEId";
            paramLs.addParam("iEId", hdQuery.getStr("iEId"));
        }
        if (HdUtils.strNotNull(hdQuery.getStr("brandCod"))) {
            jpql += " and a.brandCod=:brandCod";
            paramLs.addParam("brandCod", hdQuery.getStr("brandCod"));
        }
        if (HdUtils.strNotNull(hdQuery.getStr("consignCod"))) {
            jpql += " and a.consignCod=:consignCod";
            paramLs.addParam("consignCod", hdQuery.getStr("consignCod"));
        }
        jpql += " order by a.recTim desc";
        HdEzuiDatagridData results = JpaUtils.findByEz(jpql, paramLs, hdQuery);
        List<ShipBill> shipBillList = results.getRows();
        if (shipBillList.size() > 0) {
            for (ShipBill shipbill : shipBillList) {
                Ship ship = JpaUtils.findById(Ship.class, shipbill.getShipNo());
                shipbill.setcShipNam(ship.getcShipNam());
                if (shipbill.getiEId() == "I") {
                    shipbill.setVoyage(ship.getIvoyage());
                } else {
                    shipbill.setVoyage(ship.getEvoyage());
                }
                if (HdUtils.strNotNull(shipbill.getCarTyp())) {
                    CCarTyp ccartyp = JpaUtils.findById(CCarTyp.class, shipbill.getCarTyp());
                    if (ccartyp != null)
                        shipbill.setCarTypNam(ccartyp.getCarTypNam());
                }
                if (HdUtils.strNotNull(shipbill.getBrandCod())) {
                    CBrand cBrand = JpaUtils.findById(CBrand.class, shipbill.getBrandCod());
                    if (cBrand != null)
                        shipbill.setBrandNam(cBrand.getBrandNam());
                }
                if (HdUtils.strNotNull(shipbill.getLoadPortCod())) {
                    CPort cPort = cPortService.findByCode(shipbill.getLoadPortCod());
                    if (cPort != null)
                        shipbill.setLoadPortNam(cPort.getcPortNam());
                }
                if (HdUtils.strNotNull(shipbill.getTranPortCod())) {
                    CPort cPort = cPortService.findByCode(shipbill.getTranPortCod());
                    if (cPort != null)
                        shipbill.setTranPortNam(cPort.getcPortNam());
                }
            }
        }
        return results;
    }

    @Override
    public HdMessageCode save(HdEzuiSaveDatagridData<ShipBill> hdEzuiSaveDatagridData) {
        // TODO Auto-generated method stub
        List<ShipBill> shipbillList = hdEzuiSaveDatagridData.getUpdatedRows();
        if (shipbillList.size() > 0) {
            for (ShipBill s : shipbillList) {
                String jpql = "select a from CBrand a where a.brandCod=:brandNam ";
                QueryParamLs paramLs = new QueryParamLs();
                paramLs.addParam("brandNam", s.getBrandNam());
                List<CBrand> cbrandList = JpaUtils.findAll(jpql, paramLs);
                if (cbrandList.size() > 0) {
                    for (CBrand cb : cbrandList) {
                        s.setBrandCod(cb.getBrandCod());
                    }
                }
                CCarTyp ccp = JpaUtils.findById(CCarTyp.class, s.getCarTypNam());
                s.setCarTyp(ccp.getCarTyp());

            }
        }
        return JpaUtils.save(hdEzuiSaveDatagridData);
    }

    @Override
    public HdMessageCode saveone(@RequestBody ShipBill shipBill) {
        if (HdUtils.strNotNull(shipBill.getShipbillId())) {
			/*if(shipBill.getYdRecId().equals("1")) {
				Timestamp createTime = new Timestamp(new Date().getTime());
				shipBill.setYdTim(createTime);
			}*/
            if (HdUtils.strNotNull(shipBill.getCarTyp())) {
                CCarTyp cct = JpaUtils.findById(CCarTyp.class, shipBill.getCarTyp());
                shipBill.setCargoNam(cct.getCarTypNam());
            }
            JpaUtils.update(shipBill);
            String jpqlc = "select a from BillSplit a where a.shipbillId=:shipbillId";
            QueryParamLs paramLsc = new QueryParamLs();
            paramLsc.addParam("shipbillId", shipBill.getShipbillId());
            List<BillSplit> billsplitList = JpaUtils.findAll(jpqlc, paramLsc);
            if (billsplitList.size() > 0) {
                for (BillSplit billsplit : billsplitList) {
                    // 只改变分单提单号
                    billsplit.setBillNo(shipBill.getBillNo());
                    JpaUtils.update(billsplit);
                }
            }

            // 更新bill_car
            String billCarJpql = "SELECT b FROM BillCar b where b.shipbillId =:shipbillId";
            QueryParamLs billcarParams = new QueryParamLs();
            billcarParams.addParam("shipbillId", shipBill.getShipbillId());
            List<BillCar> billCarList = JpaUtils.findAll(billCarJpql, billcarParams);
            if (billCarList.size() > 0) {
                for (BillCar billCar : billCarList) {
                    billCar.setBrandCod(shipBill.getBrandCod());
                    JpaUtils.update(billCar);
                }
            }

        } else {
            String uuid = UUID.randomUUID().toString();
            ShipBill sb = JpaUtils.findById(ShipBill.class, uuid);
            if (sb != null) {
                throw new HdRunTimeException("该代码已存在，请重新输入！");// 主键已存在
            } else {
                shipBill.setShipbillId(uuid);
                shipBill.setConfirmId("0");
                String consignCod = shipBill.getConsignCod();
                if (HdUtils.strNotNull(consignCod)) {
                    CClientCod c = JpaUtils.findById(CClientCod.class, consignCod);
                    shipBill.setConsignNam(c.getcClientNam());
                }
                String receiveCod = shipBill.getReceiveCod();
                if (HdUtils.strNotNull(receiveCod)) {
                    CClientCod r = JpaUtils.findById(CClientCod.class, receiveCod);
                    shipBill.setReceiveNam(r.getcClientNam());
                }
                if (HdUtils.strIsNull(shipBill.getCargoNam())) {
                    CCarTyp cct = JpaUtils.findById(CCarTyp.class, shipBill.getCarTyp());
                    if (cct != null) {
                        shipBill.setCargoNam(cct.getCarTypNam());
                    }
                }
                if ("1".equals(shipBill.getTradeId()) && HdUtils.strIsNull(shipBill.getDockCod())) {
                    throw new HdRunTimeException("请输入作业公司！");
                } else {
                    JpaUtils.save(shipBill);
                    BillSplit billSplit = new BillSplit();
                    String uid = HdUtils.genUuid();
                    billSplit.setBillspId(uid);
                    billSplit.setShipbillId(shipBill.getShipbillId());
                    String jpql = "select a from Ship a where a.shipNo=:shipNo ";
                    QueryParamLs paramLs = new QueryParamLs();
                    paramLs.addParam("shipNo", shipBill.getShipNo());
                    List<Ship> shipList = JpaUtils.findAll(jpql, paramLs);
                    if (shipList.size() > 0) {
                        for (Ship s : shipList) {
                            billSplit.setcShipNam(s.getcShipNam());
                            billSplit.setVoyage(s.getIvoyage() + '/' + s.getEvoyage());
                            billSplit.setInPortTim(s.getToPortTim());
                            billSplit.setOutPortTim(s.getLeavPortTim());
                        }
                    }
                    billSplit.setTradeId(shipBill.getTradeId());
                    // 内贸计费类型默认轿车
                    if ("1".equals(shipBill.getTradeId())) {
                        billSplit.setCarFeeTyp("01");
                    }
                    billSplit.setiEId(shipBill.getiEId());
                    billSplit.setShipNo(shipBill.getShipNo());
                    billSplit.setBillNo(shipBill.getBillNo());
                    billSplit.setBrandCod(shipBill.getBrandCod());
                    billSplit.setCarTyp(shipBill.getCarTyp());
                    billSplit.setCargoNam(shipBill.getCargoNam());
                    billSplit.setPieces(shipBill.getPieces());
                    billSplit.setWeights(shipBill.getWeights());
                    billSplit.setVolumes(shipBill.getValumes());
                    billSplit.setRecNam(HdUtils.getCurUser().getName());
                    billSplit.setRecTim(HdUtils.getDateTime());
                    billSplit.setUseShipworkPerson("1");
                    billSplit.setDuringDays("0");
                    JpaUtils.save(billSplit);
                }
            }
        }
        return HdUtils.genMsg();
    }

    public void createData(String shipNo, String iEId, String tradeId) {
        if (HdUtils.strIsNull(shipNo)) {
            throw new HdRunTimeException("请先选择船！");
        }
        Ship ship = JpaUtils.findById(Ship.class, shipNo);
        String shipShort = "";
        if (HdUtils.strNotNull(ship.getShipCodId())) {
            CShipData cShipData = JpaUtils.findById(CShipData.class, ship.getShipCodId());
            if (cShipData != null) {
                shipShort = cShipData.getShipShort();
                if (HdUtils.strIsNull(shipShort)) {
                    throw new HdRunTimeException("船舶资料里英文简称为空，请先完善！");
                }
            }
        }
        String jpql = "select a from PortCar a where a.shipNo =:shipNo and a.iEId =:iEId and a.tradeId =:tradeId";
        QueryParamLs paramLs = new QueryParamLs();
        paramLs.addParam("shipNo", shipNo);
        paramLs.addParam("iEId", iEId);
        paramLs.addParam("tradeId", tradeId);
        List<PortCar> portCarList = JpaUtils.findAll(jpql, paramLs);
        for (PortCar bean : portCarList) {
            if (HdUtils.strNotNull(bean.getBrandCod())) {
                CBrand cBrand = JpaUtils.findById(CBrand.class, bean.getBrandCod());
                if (cBrand == null) {
                    throw new HdRunTimeException("库存车辆品牌信息有误，请核实！");
                } else if (HdUtils.strIsNull(cBrand.getBrandShort())) {
                    throw new HdRunTimeException("库存车辆品牌简称信息不完善，请去车辆品牌代码功能维护！");
                }
                if (ship.NM.equals(tradeId) && Ship.CK.equals(iEId) && cBrand.getBrandNam().contains("丰田")) {
                    String sql = "select a from CPort a where a.portShort =:portShort";
                    QueryParamLs param = new QueryParamLs();
                    param.addParam("portShort", bean.getTranPortCod());
                    List<CPort> cPortList = JpaUtils.findAll(sql, param);
                    if (cPortList.size() > 0) {
                        if (HdUtils.strIsNull(cPortList.get(0).getePortNam())) {
                            throw new HdRunTimeException("请在港口代码里维护该港的英文名称！");
                        }
                        String flowNam = String.valueOf(cPortList.get(0).getePortNam().charAt(0));
                        bean.setBillNo(shipShort + " " + ship.getEvoyage() + cBrand.getBrandShort() + flowNam);
                    }
                } else if (ship.NM.equals(tradeId) && Ship.CK.equals(iEId)) {
                    bean.setBillNo(shipShort + " " + ship.getEvoyage() + cBrand.getBrandShort());
                } else if (ship.NM.equals(tradeId) && Ship.JK.equals(iEId)) {
                    bean.setBillNo(shipShort + " " + ship.getIvoyage() + cBrand.getBrandShort());
                }

            }
            JpaUtils.update(bean);
            String jpql1 = "select a from WorkCommand a where a.portCarNo =:portCarNo";
            QueryParamLs paramLs1 = new QueryParamLs();
            paramLs1.addParam("portCarNo", bean.getPortCarNo());
            List<WorkCommand> workCommandList = JpaUtils.findAll(jpql1, paramLs1);
            for (WorkCommand workCommand : workCommandList) {
                workCommand.setBillNo(bean.getBillNo());
                JpaUtils.update(workCommand);
            }
        }
        String jpql2 = "select a.billNo bno, count(a.queueId) num, b.tranPortCod tpd from WorkCommand a,PortCar b where a.portCarNo = b.portCarNo and a.shipNo =:shipNo and a.billNo is not null and a.workTyp =:workTyp group by a.billNo,b.tranPortCod";
        QueryParamLs paramLs2 = new QueryParamLs();
        paramLs2.addParam("shipNo", shipNo);
        if (Ship.JK.equals(iEId)) {
            paramLs2.addParam("workTyp", "SI");
        } else if (Ship.CK.equals(iEId)) {
            paramLs2.addParam("workTyp", "SO");
        }
        List<Object[]> objlist = JpaUtils.findAll(jpql2, paramLs2);
        for (Object[] obj : objlist) {
            String jpql3 = "select a from ShipBill a where a.shipNo =:shipNo and a.billNo =:billNo";
            QueryParamLs paramLs3 = new QueryParamLs();
            paramLs3.addParam("shipNo", shipNo);
            paramLs3.addParam("billNo", obj[0]);
            List<ShipBill> resultList = JpaUtils.findAll(jpql3, paramLs3);
            if (resultList.size() > 0) {
                ShipBill shipBill = resultList.get(0);
                shipBill.setPieces(new BigDecimal(obj[1].toString()));
                JpaUtils.update(shipBill);
            } else {
                ShipBill shipBill = new ShipBill();
                shipBill.setShipbillId(HdUtils.generateUUID());
                shipBill.setShipNo(shipNo);
                shipBill.setBillNo(obj[0].toString());
                shipBill.setiEId(iEId);
                shipBill.setTradeId(tradeId);
                String jpql4 = "select a from WorkCommand a where a.billNo =:billNo and a.shipNo =:shipNo and a.workTyp =:workTyp";
                QueryParamLs paramLs4 = new QueryParamLs();
                paramLs4.addParam("billNo", obj[0].toString());
                paramLs4.addParam("shipNo", shipNo);
                if (Ship.JK.equals(iEId)) {
                    paramLs4.addParam("workTyp", "SI");
                } else if (Ship.CK.equals(iEId)) {
                    paramLs4.addParam("workTyp", "SO");
                }
                List<WorkCommand> workcommandList = JpaUtils.findAll(jpql4, paramLs4);
                if (workcommandList.size() > 0) {
                    shipBill.setBrandCod(workcommandList.get(0).getBrandCod());
                    if (ship.NM.equals(tradeId) && Ship.CK.equals(iEId)) {
                        String jpql5 = "select a from PortCar a,WorkCommand b where a.portCarNo = b.portCarNo and b.queueId =:queueId";
                        QueryParamLs paramLs5 = new QueryParamLs();
                        paramLs5.addParam("queueId", workcommandList.get(0).getQueueId());
                        List<PortCar> list = JpaUtils.findAll(jpql5, paramLs5);
                        if (list.size() > 0) {
                            shipBill.setTranPortCod(list.get(0).getTranPortCod());
                        }
                    }
                }
                shipBill.setPieces(new BigDecimal(obj[1].toString()));
                shipBill.setCarNum(new BigDecimal(obj[1].toString()));
                shipBill.setConfirmId("0");
                shipBill.setDockCod(ship.getDockCod());
                JpaUtils.save(shipBill);
                //生成billsplit
                BillSplit billSplit = new BillSplit();
                String uid = HdUtils.genUuid();
                billSplit.setBillspId(HdUtils.genUuid());
                billSplit.setShipbillId(shipBill.getShipbillId());
                String jpql1 = "select a from Ship a where a.shipNo=:shipNo ";
                QueryParamLs paramLs1 = new QueryParamLs();
                paramLs1.addParam("shipNo", shipBill.getShipNo());
                List<Ship> shipList = JpaUtils.findAll(jpql1, paramLs1);
                if (shipList.size() > 0) {
                    for (Ship s : shipList) {
                        billSplit.setcShipNam(s.getcShipNam());
                        billSplit.setVoyage(s.getIvoyage() + '/' + s.getEvoyage());
                        billSplit.setInPortTim(s.getToPortTim());
                        billSplit.setOutPortTim(s.getLeavPortTim());
                    }
                }
                billSplit.setTradeId(shipBill.getTradeId());
                // 内贸计费类型默认轿车
                if ("1".equals(shipBill.getTradeId())) {
                    billSplit.setCarFeeTyp("01");
                }
                billSplit.setiEId(shipBill.getiEId());
                billSplit.setShipNo(shipBill.getShipNo());
                billSplit.setBillNo(shipBill.getBillNo());
                billSplit.setBrandCod(shipBill.getBrandCod());
                billSplit.setCarTyp(shipBill.getCarTyp());
                billSplit.setCargoNam(shipBill.getCargoNam());
                billSplit.setPieces(shipBill.getPieces());
                billSplit.setWeights(shipBill.getWeights());
                billSplit.setVolumes(shipBill.getValumes());
                billSplit.setRecNam(HdUtils.getCurUser().getName());
                billSplit.setRecTim(HdUtils.getDateTime());
                billSplit.setUseShipworkPerson("1");
                billSplit.setDuringDays("0");
                JpaUtils.save(billSplit);
            }
        }
    }

    @Transactional
    public void removeAll(String shipbillId) {
        List<String> shipBillList = HdUtils.paraseStrs(shipbillId);
        for (String shipbillid : shipBillList) {
            // 删除bill_split
            ShipBill shipBIll = JpaUtils.findById(ShipBill.class, shipbillid);
            String billSplitJpql = "SELECT b FROM BillSplit b where b.shipbillId =:shipbillId and b.iEId =:iEId and b.shipNo =:shipNo";
            QueryParamLs bsParams = new QueryParamLs();
            bsParams.addParam("shipbillId", shipbillid);
            bsParams.addParam("iEId", shipBIll.getiEId());
            bsParams.addParam("shipNo", shipBIll.getShipNo());
            List<BillSplit> billSplitList = JpaUtils.findAll(billSplitJpql, bsParams);
            if (billSplitList.size() > 0) {
                JpaUtils.removeAll(billSplitList);
            }

            if (Ship.WM.equals(shipBIll.getTradeId()) && Ship.JK.equals(shipBIll.getiEId())) {
                String jpql1 = "select a from BillCar a where a.shipbillId =:shipbillId ";
                QueryParamLs paramLs1 = new QueryParamLs();
                paramLs1.addParam("shipbillId", shipbillid);
                List<BillCar> billCarList = JpaUtils.findAll(jpql1, paramLs1);
                if (billCarList.size() > 0) {
                    for (BillCar billCar : billCarList) {
                        // 删除port_car
                        PortCar portCar = JpaUtils.findById(PortCar.class, billCar.getPortCarNo());
                        if (portCar != null) {
                            JpaUtils.remove(portCar);
                        }
                        JpaUtils.remove(billCar);
                    }
                }
            }
        }
        for (String shipbillid : shipBillList) {
            JpaUtils.remove(ShipBill.class, shipbillid);
        }
    }

    // 检验提单里billcar是否存在
    public boolean checkExist(String shipbillid) {
        String jpql1 = "select a from BillCar a where a.shipbillId =:shipbillId ";
        QueryParamLs paramLs1 = new QueryParamLs();
        paramLs1.addParam("shipbillId", shipbillid);
        List<BillCar> billCarList = JpaUtils.findAll(jpql1, paramLs1);
        if (billCarList.size() > 0) {
            return true;
        }
        String jpql2 = "select a from PortCar a,BillCar b where a.billNo =b.billNo and b.shipbillId=:shipbillId ";
        QueryParamLs paramLs2 = new QueryParamLs();
        paramLs2.addParam("shipbillId", shipbillid);
        List<PortCar> portCarList = JpaUtils.findAll(jpql2, paramLs2);
        if (portCarList.size() > 0) {
            return true;
        }
        return false;
    }

    // 先删除明细bill_car
    public void removeAllDetailBillCar(String shipbillid) {
        String jpql1 = "select a from BillCar a where a.shipbillId =:shipbillId ";
        QueryParamLs paramLs1 = new QueryParamLs();
        paramLs1.addParam("shipbillId", shipbillid);
        List<BillCar> billCarList = JpaUtils.findAll(jpql1, paramLs1);
        if (billCarList.size() > 0) {
            for (BillCar billCar : billCarList) {
                // 删除port_car
                String pcJpql = "SELECT p FROM PortCar p where p.portCarNo =:portCarNo";
                QueryParamLs portCarParamLs = new QueryParamLs();
                portCarParamLs.addParam("portCarNo", billCar.getPortCarNo());
                List<PortCar> portCarList = JpaUtils.findAll(pcJpql, portCarParamLs);
                if (portCarList.size() > 0) {
                    JpaUtils.remove(portCarList.get(0));
                }
                JpaUtils.remove(billCar);
            }
        }
    }


    // 树生成
    public List findTree(String iEId, String tradeId) {
        ShipTask task = new ShipTask();
        List<EzTreeBean> lst = task.getTree();

        List<EzTreeBean> rtLst = new ArrayList<>();
        for (EzTreeBean ezTreeBean : lst) {

            List<EzTreeBean> childShip = new ArrayList<>();
            for (EzTreeBean ezTreeBeanch : ezTreeBean.getChildren()) {
                String objStr = ezTreeBeanch.getAttributes();
                JSONObject objShip = JSONObject.fromObject(objStr);
                if (objShip.containsKey("tradeId") && objShip.getString("tradeId").equals(tradeId)) {
                    if (ezTreeBeanch.getObj() instanceof Ship) {
                        Ship shipItem = (Ship) ezTreeBeanch.getObj();
                        String strText = ShipTask.namestr(shipItem, shipItem.getShipStat(), iEId);
                        ezTreeBeanch.setText(strText);
                    }
                    childShip.add(ezTreeBeanch);
                }
            }
            ezTreeBean.setChildren(childShip);
            if (childShip.size() == 0) ezTreeBean.setState(null);
            rtLst.add(ezTreeBean);
        }

        return rtLst;

    }


    // 签证单树生成
    public List<EzTreeBean> findTreec(String iEId) {
        ShipTask task = new ShipTask();
        List<EzTreeBean> lst = task.getTree();

        return lst;

    }

    @Override
    public ShipBill findone(String shipbillId) {
        ShipBill shipbill = JpaUtils.findById(ShipBill.class, shipbillId);
        return shipbill;
    }

    @Override
    public ShipBill findShipBill(CargoInfo cargoInfo) {
        String jpql = "select a from ShipBill a where a.iEId = 'E' ";
        QueryParamLs query = new QueryParamLs();
        if (cargoInfo.getShipNo() != null) {
            jpql += "and a.shipNo =:shipNo ";
            query.addParam("shipNo", cargoInfo.getShipNo());
        }
        if (cargoInfo.getBillNo() != null) {
            jpql += "and a.billNo =:billNo";
            query.addParam("billNo", cargoInfo.getBillNo());
        }
        List<ShipBill> shipBillList = JpaUtils.findAll(jpql, query);
        ShipBill shipBill = new ShipBill();
        if (shipBillList.size() > 0) {
            shipBill = shipBillList.get(0);
        }
        return shipBill;
    }

    @Override
    public ShipBillRecord findShipBillRecord(CargoInfo cargoInfo) {
        String jpql = "select a from ShipBillRecord a where a.iEId = 'E' ";
        QueryParamLs query = new QueryParamLs();
        if (cargoInfo.getShipNo() != null) {
            jpql += "and a.shipNo =:shipNo ";
            query.addParam("shipNo", cargoInfo.getShipNo());
        }
        if (cargoInfo.getBillNo() != null) {
            jpql += "and a.billNo =:billNo";
            query.addParam("billNo", cargoInfo.getBillNo());
        }
        List<ShipBillRecord> shipBillList = JpaUtils.findAll(jpql, query);
        ShipBillRecord shipBill = new ShipBillRecord();
        if (shipBillList.size() > 0) {
            shipBill = shipBillList.get(0);
        }
        return shipBill;
    }

    @Override
    public ShipBill getShipBillInfo(String billNo) {
        String jpql = "select a from ShipBill a where a.billNo=:billNo ";
        QueryParamLs paramLs = new QueryParamLs();
        paramLs.addParam("billNo", billNo);
        List<ShipBill> shipbillList = JpaUtils.findAll(jpql, paramLs);
        ShipBill s = new ShipBill();
        if (shipbillList.size() > 0) {
            for (ShipBill sb : shipbillList) {
                // s.setCarNum(sb.getCarNum());
                s.setPieces(sb.getPieces());
                s.setiEId(sb.getiEId());
            }
        }
        return s;
    }

    @Override
    public void genbillcar(String billNo) {
        // 加count

        String jpql = "select a from ShipBill a where a.billNo=:billNo";
        QueryParamLs paramLs = new QueryParamLs();
        paramLs.addParam("billNo", billNo);
        List<ShipBill> shipbillList = JpaUtils.findAll(jpql, paramLs);

        if (shipbillList.size() > 0) {
            for (ShipBill sb : shipbillList) {
                String jpqlc = "select count(a)  from BillCar a where a.billNo=:billNo";
                QueryParamLs paramLsc = new QueryParamLs();
                paramLsc.addParam("billNo", billNo);
                List<Long> countNum = JpaUtils.findAll(jpqlc, paramLsc);
                if (sb.getPieces().equals(countNum) || countNum.get(0) > 0) {
                    throw new HdRunTimeException("提单已确认！不能重复确认");
                } else {
                    for (int i = 0; i < sb.getPieces().intValue(); i++) {
                        PortCar portCar = new PortCar();
                        BillCar billCar = new BillCar();
                        portCar.setiEId(sb.getiEId());
                        portCar.setTradeId(sb.getTradeId());
                        portCar.setCurrentStat("0");
                        portCar.setInPortNo(" ");
                        portCar.setBillNo(sb.getBillNo());
                        portCar.setShipNo(sb.getShipNo());
                        portCar.setRecNam(HdUtils.getCurUser().getAccount());
                        portCar.setRecTim(HdUtils.getDateTime());
                        Ship ship = JpaUtils.findById(Ship.class, sb.getShipNo());
                        portCar.setDockCod(ship.getDockCod());
                        JpaUtils.save(portCar);
                        String billcarId = HdUtils.genUuid();
                        billCar.setBillcarId(billcarId);
                        billCar.setShipbillId(sb.getShipbillId());
                        billCar.setShipNo(sb.getShipNo());
                        billCar.setBillNo(sb.getBillNo());
                        billCar.setiEId(sb.getiEId());
                        billCar.setTradeId(sb.getTradeId());
                        billCar.setPortCarNo(portCar.getPortCarNo());
                        billCar.setRecNam(sb.getRecNam());
                        billCar.setRecTim(HdUtils.getDateTime());
                        billCar.setRecTim(HdUtils.getDateTime());
                        billCar.setLhFlag("0");
                        JpaUtils.save(billCar);
                    }
                }
            }
        }

    }

    @Override
    public void async(String shipbillId, String dockCod) {
        List<String> shipbillIdList = HdUtils.paraseStrs(shipbillId);
        for (String shipbillid : shipbillIdList) {
            ShipBill shipbill = JpaUtils.findById(ShipBill.class, shipbillid);
            insertIntoZongheCha(shipbill, dockCod);
        }
    }

    public void insertIntoZongheCha(ShipBill shipBill, String dockCod) {
        JSONObject jsonObj = new JSONObject();
        String uuid = HdUtils.genUuid().substring(0, 8);
        jsonObj.put("id", uuid);
        String vss_no = _10_to_62(Long.parseLong(shipBill.getShipNo()), 5);
        jsonObj.put("vss_no", vss_no);
        Ship ship = JpaUtils.findById(Ship.class, shipBill.getShipNo());
        jsonObj.put("vss_name", ship.getcShipNam());
        if ("I".equals(shipBill.getiEId())) {
            jsonObj.put("voyage", ship.getIvoyage());
            jsonObj.put("in_out_flag", "进口");
        }
        if ("E".equals(shipBill.getiEId())) {
            jsonObj.put("voyage", ship.getEvoyage());
            jsonObj.put("in_out_flag", "出口");
        }
        jsonObj.put("b_l", shipBill.getBillNo());
        jsonObj.put("quantity", shipBill.getPieces().longValue());
        jsonObj.put("true_quantity", shipBill.getPieces().longValue());
        jsonObj.put("vol", shipBill.getValumes());
        jsonObj.put("single_weight", shipBill.getWeights().divide(shipBill.getPieces(), 3, BigDecimal.ROUND_HALF_UP));
        jsonObj.put("charge_weight", shipBill.getWeights().divide(new BigDecimal(1000), 3, BigDecimal.ROUND_HALF_UP));
        jsonObj.put("charge_vol", shipBill.getValumes());
        jsonObj.put("charge_quantity", shipBill.getPieces().longValue());
        jsonObj.put("lh_check_people", HdUtils.getCurUser().getName());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        jsonObj.put("lh_check_tim", format.format(HdUtils.getDateTime()));
        jsonObj.put("hy_check_people", HdUtils.getCurUser().getName());
        jsonObj.put("hy_check_tim", format.format(HdUtils.getDateTime()));
        jsonObj.put("check_flag", "0");
        jsonObj.put("flag_charge", "0");
        jsonObj.put("flag_apply", "0");
        jsonObj.put("check_time", format.format(HdUtils.getDateTime()));
        jsonObj.put("check_person", HdUtils.getCurUser().getName());
        if ("03406500".equals(dockCod)) {
            jsonObj.put("token", "roroBilling");
        }
        if ("03409000".equals(dockCod)) {
            jsonObj.put("token", "globalBilling");
        }
        String url = apiServiceIp + "8081/RoroBillingSysWebApi/setShipBill";
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
                String str = '{' + "\"code\"" + ":0" + '}';
                String str2 = '{' + "\"code\"" + ":1" + '}';
                if (str.equals(response)) {
                    throw new HdRunTimeException("上报舱单数据失败！");
                }
                if (str2.equals(response)) {
                    throw new HdRunTimeException("上报舱单数据成功！");
                }
                // JSONObject j = JSON.parseObject(response);
            } catch (Exception e) {
                // System.out.println("上报计费数据异常！" + e);
            }
            // 断开连接
            conn.disconnect();
        } catch (Exception e) {
            /**
             * 计费连接接口问题导致上报失败！
             */
            // System.out.println("发送 POST 请求出现异常！" + e);
            // e.printStackTrace();
            throw new HdRunTimeException("发送 POST 请求出现异常！");
        }
        // 使用finally块来关闭输出流、输入流
        finally {
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
        }
    }

    public String _10_to_62(long number, int length) {
        Long rest = number;
        Stack<Character> stack = new Stack<Character>();
        StringBuilder result = new StringBuilder(0);
        while (rest != 0) {
            stack.add(charSet[new Long((rest - (rest / 62) * 62)).intValue()]);
            rest = rest / 62;
        }
        for (; !stack.isEmpty(); ) {
            result.append(stack.pop());
        }
        int result_length = result.length();
        StringBuilder temp0 = new StringBuilder();
        for (int i = 0; i < length - result_length; i++) {
            temp0.append('0');
        }

        return temp0.toString() + result.toString();

    }

    @Override
    public void exitCustom(String shipbillId) {
        List<String> shipbillIdList = HdUtils.paraseStrs(shipbillId);
        for (String shipbillid : shipbillIdList) {
            ShipBill shipbill = JpaUtils.findById(ShipBill.class, shipbillid);
            shipbill.setExitCustomId("1");
            JpaUtils.update(shipbill);
            String jpqln = "select a from BillSplit a where a.billNo=:billNo ";
            QueryParamLs paramLsn = new QueryParamLs();
            paramLsn.addParam("billNo", shipbill.getBillNo());
            List<BillSplit> billSplitList = JpaUtils.findAll(jpqln, paramLsn);
            if (billSplitList.size() > 0) {
                for (BillSplit bs : billSplitList) {
                    bs.setExitCustomId("1");
                    JpaUtils.update(bs);
                }
            }
            String jpql = "select a from PortCar a where a.billNo=:billNo";
            QueryParamLs paramLs = new QueryParamLs();
            paramLs.addParam("billNo", shipbill.getBillNo());
            List<PortCar> portcarList = JpaUtils.findAll(jpql, paramLs);
            if (portcarList.size() > 0) {
                for (PortCar pc : portcarList) {
                    pc.setExitCustomId("1");
                    JpaUtils.update(pc);
                }
            }
        }
    }

    /**
     * 针对外贸出口装船进行调整，不能再生成在场车了
     */
    @Override
    public void generatebcpc(String billNo) {
        String jpqlb = "select a from ShipBill a where a.confirmId='1' and a.billNo=:billNo ";
        QueryParamLs paramLb = new QueryParamLs();
        paramLb.addParam("billNo", billNo);
        List<ShipBill> shipbillListb = JpaUtils.findAll(jpqlb, paramLb);
        if (shipbillListb.size() > 0) {
            throw new HdRunTimeException("不能重复确认！");
        } else {
            String jpqlm = "select a from ShipBill a where a.billNo=:billNo ";
            List<ShipBill> shipbillListc = JpaUtils.findAll(jpqlm, paramLb);
            String shipNo = shipbillListc.get(0).getShipNo();
            String jpql = "select a from WorkQueue a where a.shipNo=:shipNo and a.workTyp='SI'";
            QueryParamLs paramLs = new QueryParamLs();
            paramLs.addParam("shipNo", shipNo);
            List<WorkQueue> workQueueList = JpaUtils.findAll(jpql, paramLs);
            if (workQueueList.size() > 0) {
                // 该船舶已生成作业队列，把没有billcar初始的单子初始化。
                String jpqlp = "select a from ShipBill a where a.billNo=:billNo";
                QueryParamLs paramLsp = new QueryParamLs();
                paramLsp.addParam("billNo", billNo);
                List<ShipBill> shipbillList = JpaUtils.findAll(jpqlp, paramLsp);
                if (shipbillList.size() > 0) {
                    // 外贸进口舱单根据外代提供的车架号,把车架号初始化到billcar
                    // 提单号能查到数据的做初始化，没查到车架号的车架号还是默认空
                    if (shipbillList.get(0).getTradeId().equals("2") && shipbillList.get(0).getiEId().equals("I")) {
                        String imo = "";
                        if (HdUtils.strNotNull(shipbillList.get(0).getShipNo())) {
                            Ship ship = JpaUtils.findById(Ship.class, shipbillList.get(0).getShipNo());
                            if (ship != null) {
                                CShipData cShipData = JpaUtils.findById(CShipData.class, ship.getShipCodId());
                                imo = cShipData.getShipImo();
                            }
                        }
                        String jpqlw = "select a from VWlBillVehicle a where a.billNo=:billNo ";
                        QueryParamLs paramLsw = new QueryParamLs();
                        paramLsw.addParam("billNo", billNo);
                        if (HdUtils.strNotNull(imo)) {
                            jpqlw += "and a.imo =:imo ";
                            paramLsw.addParam("imo", imo);
                        }
                        // 这里需要考虑外理视图提供单子上的数量是否和导进来的单子上的数量的一致性（现在无考虑，默认一致）
                        List<VWlBillVehicle> vWlBillVehicleList = JpaUtils.findAll(jpqlw, paramLsw);
                        if (vWlBillVehicleList.size() > 0) {
                            for (int i = 0; i < shipbillList.size(); i++) {
                                for (int j = 0; j < shipbillList.get(i).getPieces().intValue(); j++) {
                                    PortCar portCar = new PortCar();
                                    BillCar billCar = new BillCar();
                                    portCar.setiEId(shipbillList.get(i).getiEId());
                                    portCar.setTradeId(shipbillList.get(i).getTradeId());
                                    portCar.setCurrentStat("0");
                                    portCar.setInPortNo(" ");
                                    portCar.setBillNo(shipbillList.get(i).getBillNo());
                                    if (HdUtils.strNotNull(shipbillList.get(i).getBrandCod()))
                                        portCar.setBrandCod(shipbillList.get(i).getBrandCod());
                                    if (HdUtils.strNotNull(shipbillList.get(i).getCarTyp())) {
                                        portCar.setCarTyp(shipbillList.get(i).getCarTyp());
                                        CCarTyp cct = JpaUtils.findById(CCarTyp.class, shipbillList.get(i).getCarTyp());
                                        if (HdUtils.strNotNull(cct.getCarKind())) {
                                            portCar.setCarKind(cct.getCarKind());
                                        } else {
                                            portCar.setCarKind("");
                                        }
                                    }
                                    portCar.setShipNo(shipbillList.get(i).getShipNo());
                                    portCar.setVinNo(vWlBillVehicleList.get(j).getVin());
                                    portCar.setRecNam(HdUtils.getCurUser().getAccount());
                                    portCar.setRecTim(HdUtils.getDateTime());
                                    Ship ship = JpaUtils.findById(Ship.class, shipbillList.get(i).getShipNo());
                                    portCar.setDockCod(ship.getDockCod());
                                    JpaUtils.save(portCar);
                                    String billcarId = HdUtils.genUuid();
                                    billCar.setBillcarId(billcarId);
                                    billCar.setShipbillId(shipbillList.get(i).getShipbillId());
                                    billCar.setShipNo(shipbillList.get(i).getShipNo());
                                    billCar.setBillNo(shipbillList.get(i).getBillNo());
                                    if (HdUtils.strNotNull(shipbillList.get(i).getBrandCod()))
                                        billCar.setBrandCod(shipbillList.get(i).getBrandCod());
                                    if (HdUtils.strNotNull(shipbillList.get(i).getCarTyp())) {
                                        billCar.setCarTyp(shipbillList.get(i).getCarTyp());
                                        CCarTyp cct = JpaUtils.findById(CCarTyp.class, shipbillList.get(i).getCarTyp());
                                        if (HdUtils.strNotNull(cct.getCarKind())) {
                                            billCar.setCarKind(cct.getCarKind());
                                        } else {
                                            billCar.setCarKind("");
                                        }
                                    }
                                    billCar.setiEId(shipbillList.get(i).getiEId());
                                    billCar.setVinNo(vWlBillVehicleList.get(j).getVin());
                                    billCar.setTradeId(shipbillList.get(i).getTradeId());
                                    billCar.setPortCarNo(portCar.getPortCarNo());
                                    billCar.setRecNam(shipbillList.get(i).getRecNam());
                                    billCar.setRecTim(HdUtils.getDateTime());
                                    billCar.setRecTim(HdUtils.getDateTime());
                                    billCar.setLhFlag("0");
                                    JpaUtils.save(billCar);
                                }
                            }
                        } else {
                            for (int i = 0; i < shipbillList.size(); i++) {
                                for (int j = 0; j < shipbillList.get(i).getPieces().intValue(); j++) {
                                    PortCar portCar = new PortCar();
                                    BillCar billCar = new BillCar();
                                    portCar.setiEId(shipbillList.get(i).getiEId());
                                    portCar.setTradeId(shipbillList.get(i).getTradeId());
                                    portCar.setCurrentStat("0");
                                    portCar.setInPortNo(" ");
                                    portCar.setBillNo(shipbillList.get(i).getBillNo());
                                    portCar.setShipNo(shipbillList.get(i).getShipNo());
                                    if (HdUtils.strNotNull(shipbillList.get(i).getBrandCod()))
                                        portCar.setBrandCod(shipbillList.get(i).getBrandCod());
                                    if (HdUtils.strNotNull(shipbillList.get(i).getCarTyp())) {
                                        portCar.setCarTyp(shipbillList.get(i).getCarTyp());
                                        CCarTyp cct = JpaUtils.findById(CCarTyp.class, shipbillList.get(i).getCarTyp());
                                        if (HdUtils.strNotNull(cct.getCarKind())) {
                                            portCar.setCarKind(cct.getCarKind());
                                        } else {
                                            portCar.setCarKind("");
                                        }
                                    }
                                    portCar.setRecNam(HdUtils.getCurUser().getAccount());
                                    portCar.setRecTim(HdUtils.getDateTime());
                                    Ship ship = JpaUtils.findById(Ship.class, shipbillList.get(i).getShipNo());
                                    portCar.setDockCod(ship.getDockCod());
                                    JpaUtils.save(portCar);
                                    String billcarId = HdUtils.genUuid();
                                    billCar.setBillcarId(billcarId);
                                    billCar.setShipbillId(shipbillList.get(i).getShipbillId());
                                    billCar.setShipNo(shipbillList.get(i).getShipNo());
                                    billCar.setBillNo(shipbillList.get(i).getBillNo());
                                    billCar.setiEId(shipbillList.get(i).getiEId());
                                    if (HdUtils.strNotNull(shipbillList.get(i).getBrandCod()))
                                        billCar.setBrandCod(shipbillList.get(i).getBrandCod());
                                    if (HdUtils.strNotNull(shipbillList.get(i).getCarTyp())) {
                                        billCar.setCarTyp(shipbillList.get(i).getCarTyp());
                                        CCarTyp cct = JpaUtils.findById(CCarTyp.class, shipbillList.get(i).getCarTyp());
                                        if (HdUtils.strNotNull(cct.getCarKind())) {
                                            billCar.setCarKind(cct.getCarKind());
                                        } else {
                                            billCar.setCarKind("");
                                        }
                                    }
                                    billCar.setTradeId(shipbillList.get(i).getTradeId());
                                    billCar.setPortCarNo(portCar.getPortCarNo());
                                    billCar.setRecNam(shipbillList.get(i).getRecNam());
                                    billCar.setRecTim(HdUtils.getDateTime());
                                    billCar.setRecTim(HdUtils.getDateTime());
                                    billCar.setLhFlag("0");
                                    JpaUtils.save(billCar);
                                }
                            }
                        }
                    } else {
                        // 内贸舱单不需要做把车架号初始到billCar
                        for (int i = 0; i < shipbillList.size(); i++) {
                            for (int j = 0; j < shipbillList.get(i).getPieces().intValue(); j++) {
                                PortCar portCar = new PortCar();
                                BillCar billCar = new BillCar();
                                portCar.setiEId(shipbillList.get(i).getiEId());
                                portCar.setTradeId(shipbillList.get(i).getTradeId());
                                portCar.setCurrentStat("0");
                                portCar.setInPortNo(" ");
                                portCar.setBillNo(shipbillList.get(i).getBillNo());
                                portCar.setShipNo(shipbillList.get(i).getShipNo());
                                if (HdUtils.strNotNull(shipbillList.get(i).getBrandCod()))
                                    portCar.setBrandCod(shipbillList.get(i).getBrandCod());
                                if (HdUtils.strNotNull(shipbillList.get(i).getCarTyp()))
                                    portCar.setCarTyp(shipbillList.get(i).getCarTyp());
                                if (HdUtils.strNotNull(shipbillList.get(i).getCarKind()))
                                    portCar.setCarKind(shipbillList.get(i).getCarKind());
                                portCar.setRecNam(HdUtils.getCurUser().getAccount());
                                portCar.setRecTim(HdUtils.getDateTime());
                                Ship ship = JpaUtils.findById(Ship.class, shipbillList.get(i).getShipNo());
                                portCar.setDockCod(ship.getDockCod());
                                JpaUtils.save(portCar);
                                String billcarId = HdUtils.genUuid();
                                billCar.setBillcarId(billcarId);
                                billCar.setShipbillId(shipbillList.get(i).getShipbillId());
                                billCar.setShipNo(shipbillList.get(i).getShipNo());
                                billCar.setBillNo(shipbillList.get(i).getBillNo());
                                billCar.setiEId(shipbillList.get(i).getiEId());
                                if (HdUtils.strNotNull(shipbillList.get(i).getBrandCod()))
                                    billCar.setBrandCod(shipbillList.get(i).getBrandCod());
                                if (HdUtils.strNotNull(shipbillList.get(i).getCarTyp())) {
                                    billCar.setCarTyp(shipbillList.get(i).getCarTyp());
                                    CCarTyp cct = JpaUtils.findById(CCarTyp.class, shipbillList.get(i).getCarTyp());
                                    if (HdUtils.strNotNull(cct.getCarKind())) {
                                        billCar.setCarKind(cct.getCarKind());
                                    } else {
                                        billCar.setCarKind("");
                                    }
                                }
                                billCar.setTradeId(shipbillList.get(i).getTradeId());
                                billCar.setPortCarNo(portCar.getPortCarNo());
                                billCar.setRecNam(shipbillList.get(i).getRecNam());
                                billCar.setRecTim(HdUtils.getDateTime());
                                billCar.setRecTim(HdUtils.getDateTime());
                                billCar.setLhFlag("0");
                                JpaUtils.save(billCar);
                            }
                        }
                    }
                }

                // throw new HdRunTimeException("已生成作业队列！");
            } else {
                //装船
                String jpqln = "select a from ShipBill a where a.billNo=:billNo";
                QueryParamLs paramLsn = new QueryParamLs();
                paramLsn.addParam("billNo", billNo);
                List<ShipBill> shipbillList = JpaUtils.findAll(jpqln, paramLsn);
                if (shipbillList.size() > 0) {
                    // 外贸进口舱单根据外代提供的车架号,把车架号初始化到billcar
                    // 提单号能查到数据的做初始化，没查到车架号的车架号还是默认空
                    //外贸进口
                    if (shipbillList.get(0).getTradeId().equals("2") && shipbillList.get(0).getiEId().equals("I")) {
                        String imo = "";
                        if (HdUtils.strNotNull(shipbillList.get(0).getShipNo())) {
                            Ship ship = JpaUtils.findById(Ship.class, shipbillList.get(0).getShipNo());
                            if (ship != null) {
                                CShipData cShipData = JpaUtils.findById(CShipData.class, ship.getShipCodId());
                                imo = cShipData.getShipImo();
                            }
                        }
                        String jpqlw = "select a from VWlBillVehicle a where a.billNo=:billNo ";
                        QueryParamLs paramLsw = new QueryParamLs();
                        paramLsw.addParam("billNo", billNo);
                        if (HdUtils.strNotNull(imo)) {
                            jpqlw += "and a.imo =:imo ";
                            paramLsw.addParam("imo", imo);
                        }
                        List<VWlBillVehicle> vWlBillVehicleList = JpaUtils.findAll(jpqlw, paramLsw);
                        if (vWlBillVehicleList.size() > 0) {
                            for (int i = 0; i < shipbillList.size(); i++) {
                                for (int j = 0; j < shipbillList.get(i).getPieces().intValue(); j++) {
                                    PortCar portCar = new PortCar();
                                    BillCar billCar = new BillCar();
                                    portCar.setiEId(shipbillList.get(i).getiEId());
                                    portCar.setTradeId(shipbillList.get(i).getTradeId());
                                    portCar.setCurrentStat("0");
                                    portCar.setInPortNo(" ");
                                    portCar.setBillNo(shipbillList.get(i).getBillNo());
                                    portCar.setShipNo(shipbillList.get(i).getShipNo());
                                    if (HdUtils.strNotNull(shipbillList.get(i).getBrandCod()))
                                        portCar.setBrandCod(shipbillList.get(i).getBrandCod());
                                    if (HdUtils.strNotNull(shipbillList.get(i).getCarTyp())) {
                                        portCar.setCarTyp(shipbillList.get(i).getCarTyp());
                                        CCarTyp cct = JpaUtils.findById(CCarTyp.class, shipbillList.get(i).getCarTyp());
                                        if (HdUtils.strNotNull(cct.getCarKind())) {
                                            portCar.setCarKind(cct.getCarKind());
                                        } else {
                                            portCar.setCarKind("");
                                        }
                                    }
                                    portCar.setVinNo(vWlBillVehicleList.get(j).getVin());
                                    portCar.setRecNam(HdUtils.getCurUser().getAccount());
                                    portCar.setRecTim(HdUtils.getDateTime());
                                    Ship ship = JpaUtils.findById(Ship.class, shipbillList.get(i).getShipNo());
                                    portCar.setDockCod(ship.getDockCod());
                                    JpaUtils.save(portCar);
                                    String billcarId = HdUtils.genUuid();
                                    billCar.setBillcarId(billcarId);
                                    billCar.setShipbillId(shipbillList.get(i).getShipbillId());
                                    billCar.setShipNo(shipbillList.get(i).getShipNo());
                                    billCar.setBillNo(shipbillList.get(i).getBillNo());
                                    billCar.setiEId(shipbillList.get(i).getiEId());
                                    if (HdUtils.strNotNull(shipbillList.get(i).getBrandCod()))
                                        billCar.setBrandCod(shipbillList.get(i).getBrandCod());
                                    if (HdUtils.strNotNull(shipbillList.get(i).getCarTyp())) {
                                        billCar.setCarTyp(shipbillList.get(i).getCarTyp());
                                        CCarTyp cct = JpaUtils.findById(CCarTyp.class, shipbillList.get(i).getCarTyp());
                                        if (HdUtils.strNotNull(cct.getCarKind())) {
                                            billCar.setCarKind(cct.getCarKind());
                                        } else {
                                            billCar.setCarKind("");
                                        }
                                    }
                                    billCar.setVinNo(vWlBillVehicleList.get(j).getVin());
                                    billCar.setTradeId(shipbillList.get(i).getTradeId());
                                    billCar.setPortCarNo(portCar.getPortCarNo());
                                    billCar.setRecNam(shipbillList.get(i).getRecNam());
                                    billCar.setRecTim(HdUtils.getDateTime());
                                    billCar.setRecTim(HdUtils.getDateTime());
                                    billCar.setLhFlag("0");
                                    JpaUtils.save(billCar);
                                }
                            }
                        } else {
                            for (int i = 0; i < shipbillList.size(); i++) {
                                for (int j = 0; j < shipbillList.get(i).getPieces().intValue(); j++) {
                                    PortCar portCar = new PortCar();
                                    BillCar billCar = new BillCar();
                                    portCar.setiEId(shipbillList.get(i).getiEId());
                                    portCar.setTradeId(shipbillList.get(i).getTradeId());
                                    portCar.setCurrentStat("0");
                                    portCar.setInPortNo(" ");
                                    portCar.setBillNo(shipbillList.get(i).getBillNo());
                                    portCar.setShipNo(shipbillList.get(i).getShipNo());
                                    if (HdUtils.strNotNull(shipbillList.get(i).getBrandCod()))
                                        portCar.setBrandCod(shipbillList.get(i).getBrandCod());
                                    if (HdUtils.strNotNull(shipbillList.get(i).getCarTyp())) {
                                        portCar.setCarTyp(shipbillList.get(i).getCarTyp());
                                        CCarTyp cct = JpaUtils.findById(CCarTyp.class, shipbillList.get(i).getCarTyp());
                                        if (HdUtils.strNotNull(cct.getCarKind())) {
                                            portCar.setCarKind(cct.getCarKind());
                                        } else {
                                            portCar.setCarKind("");
                                        }
                                    }
                                    portCar.setRecNam(HdUtils.getCurUser().getAccount());
                                    portCar.setRecTim(HdUtils.getDateTime());
                                    Ship ship = JpaUtils.findById(Ship.class, shipbillList.get(i).getShipNo());
                                    portCar.setDockCod(ship.getDockCod());
                                    JpaUtils.save(portCar);
                                    String billcarId = HdUtils.genUuid();
                                    billCar.setBillcarId(billcarId);
                                    billCar.setShipbillId(shipbillList.get(i).getShipbillId());
                                    billCar.setShipNo(shipbillList.get(i).getShipNo());
                                    billCar.setBillNo(shipbillList.get(i).getBillNo());
                                    billCar.setiEId(shipbillList.get(i).getiEId());
                                    if (HdUtils.strNotNull(shipbillList.get(i).getBrandCod()))
                                        billCar.setBrandCod(shipbillList.get(i).getBrandCod());
                                    if (HdUtils.strNotNull(shipbillList.get(i).getCarTyp())) {
                                        billCar.setCarTyp(shipbillList.get(i).getCarTyp());
                                        CCarTyp cct = JpaUtils.findById(CCarTyp.class, shipbillList.get(i).getCarTyp());
                                        if (HdUtils.strNotNull(cct.getCarKind())) {
                                            billCar.setCarKind(cct.getCarKind());
                                        } else {
                                            billCar.setCarKind("");
                                        }
                                    }
                                    billCar.setTradeId(shipbillList.get(i).getTradeId());
                                    billCar.setPortCarNo(portCar.getPortCarNo());
                                    billCar.setRecNam(shipbillList.get(i).getRecNam());
                                    billCar.setRecTim(HdUtils.getDateTime());
                                    billCar.setRecTim(HdUtils.getDateTime());
                                    billCar.setLhFlag("0");
                                    JpaUtils.save(billCar);
                                }
                            }
                        }
                    } else {
                        // 内贸舱单不需要做把车架号初始到billCar
                        if (shipbillList.get(0).getTradeId().equals("2") && shipbillList.get(0).getiEId().equals("E")) {
                            //装船，不需要生成新的在场车信息和bill_car信息
                        } else {
                            for (int i = 0; i < shipbillList.size(); i++) {
                                for (int j = 0; j < shipbillList.get(i).getPieces().intValue(); j++) {
                                    PortCar portCar = new PortCar();
                                    BillCar billCar = new BillCar();
                                    portCar.setiEId(shipbillList.get(i).getiEId());
                                    portCar.setTradeId(shipbillList.get(i).getTradeId());
                                    portCar.setCurrentStat("0");
                                    portCar.setInPortNo(" ");
                                    portCar.setBillNo(shipbillList.get(i).getBillNo());
                                    portCar.setShipNo(shipbillList.get(i).getShipNo());
                                    if (HdUtils.strNotNull(shipbillList.get(i).getBrandCod()))
                                        portCar.setBrandCod(shipbillList.get(i).getBrandCod());
                                    if (HdUtils.strNotNull(shipbillList.get(i).getCarTyp())) {
                                        portCar.setCarTyp(shipbillList.get(i).getCarTyp());
                                        CCarTyp cct = JpaUtils.findById(CCarTyp.class, shipbillList.get(i).getCarTyp());
                                        if (HdUtils.strNotNull(cct.getCarKind())) {
                                            portCar.setCarKind(cct.getCarKind());
                                        } else {
                                            portCar.setCarKind("");
                                        }
                                    }
                                    portCar.setRecNam(HdUtils.getCurUser().getAccount());
                                    portCar.setRecTim(HdUtils.getDateTime());
                                    Ship ship = JpaUtils.findById(Ship.class, shipbillList.get(i).getShipNo());
                                    portCar.setDockCod(ship.getDockCod());
                                    JpaUtils.save(portCar);
                                    String billcarId = HdUtils.genUuid();
                                    billCar.setBillcarId(billcarId);
                                    billCar.setShipbillId(shipbillList.get(i).getShipbillId());
                                    billCar.setShipNo(shipbillList.get(i).getShipNo());
                                    billCar.setBillNo(shipbillList.get(i).getBillNo());
                                    billCar.setiEId(shipbillList.get(i).getiEId());
                                    if (HdUtils.strNotNull(shipbillList.get(i).getBrandCod()))
                                        billCar.setBrandCod(shipbillList.get(i).getBrandCod());
                                    if (HdUtils.strNotNull(shipbillList.get(i).getCarTyp())) {
                                        billCar.setCarTyp(shipbillList.get(i).getCarTyp());
                                        CCarTyp cct = JpaUtils.findById(CCarTyp.class, shipbillList.get(i).getCarTyp());
                                        if (HdUtils.strNotNull(cct.getCarKind())) {
                                            billCar.setCarKind(cct.getCarKind());
                                        } else {
                                            billCar.setCarKind("");
                                        }
                                    }
                                    billCar.setTradeId(shipbillList.get(i).getTradeId());
                                    billCar.setPortCarNo(portCar.getPortCarNo());
                                    billCar.setRecNam(shipbillList.get(i).getRecNam());
                                    billCar.setRecTim(HdUtils.getDateTime());
                                    billCar.setRecTim(HdUtils.getDateTime());
                                    billCar.setLhFlag("0");
                                    JpaUtils.save(billCar);
                                }
                            }
                        }

                    }
                }
            }
            String jpa = "update ShipBill a set a.confirmId='1' where a.billNo=:billNo";
            QueryParamLs params = new QueryParamLs();
            params.addParam("billNo", billNo);
            JpaUtils.execUpdate(jpa, params);
        }
    }

    @Override
    public ShipBill searchShipBill(String billNo) {
        ShipBill shipbill = new ShipBill();
        String jpql = "select a from ShipBill a where a.billNo=:billNo ";
        QueryParamLs params = new QueryParamLs();
        params.addParam("billNo", billNo);
        List<ShipBill> shipbillList = JpaUtils.findAll(jpql, params);
        if (shipbillList.size() > 0) {
            shipbill = shipbillList.get(0);
        }
        return shipbill;
    }

    @Override
    public HdMessageCode copy(ShipBill shipBill) {
        String shipbillId = shipBill.getShipbillId();
        ShipBill shipbill = JpaUtils.findById(ShipBill.class, shipbillId);
        if (shipbill != null) {
            JpaUtils.update(shipBill);
        } else {
            JpaUtils.save(shipBill);
            BillSplit billSplit = new BillSplit();
            String uid = HdUtils.genUuid();
            billSplit.setBillspId(uid);
            billSplit.setShipbillId(shipBill.getShipbillId());
            String jpql = "select a from Ship a where a.shipNo=:shipNo ";
            QueryParamLs paramLs = new QueryParamLs();
            paramLs.addParam("shipNo", shipBill.getShipNo());
            List<Ship> shipList = JpaUtils.findAll(jpql, paramLs);
            if (shipList.size() > 0) {
                for (Ship s : shipList) {
                    billSplit.setcShipNam(s.getcShipNam());
                    billSplit.setVoyage(s.getIvoyage() + '/' + s.getEvoyage());
                    billSplit.setInPortTim(s.getToPortTim());
                    billSplit.setOutPortTim(s.getLeavPortTim());
                }
            }
            billSplit.setTradeId(shipBill.getTradeId());
            // 内贸计费类型默认轿车
            if ("1".equals(shipBill.getTradeId())) {
                billSplit.setCarFeeTyp("01");
            }
            billSplit.setiEId(shipBill.getiEId());
            billSplit.setShipNo(shipBill.getShipNo());
            billSplit.setBillNo(shipBill.getBillNo());
            billSplit.setBrandCod(shipBill.getBrandCod());
            billSplit.setCarTyp(shipBill.getCarTyp());
            billSplit.setCargoNam(shipBill.getCargoNam());
            billSplit.setPieces(shipBill.getPieces());
            billSplit.setWeights(shipBill.getWeights());
            billSplit.setVolumes(shipBill.getValumes());
            billSplit.setRecNam(HdUtils.getCurUser().getName());
            billSplit.setRecTim(HdUtils.getDateTime());
            billSplit.setUseShipworkPerson("1");
            billSplit.setDuringDays("0");
            JpaUtils.save(billSplit);
        }
        return HdUtils.genMsg();
    }

    @Override
    public List<EzTreeBean> gentreerep() {
        //String jpql = " select  a  from  Ship a  where  a.shipStat=:shipStat and a.tradeId='1' ";

        ShipTask task = new ShipTask();
        List<EzTreeBean> lst = task.getTree();
        List<EzTreeBean> rtLst = new ArrayList<>();
        for (EzTreeBean ezTreeBean : lst) {
            rtLst.add(ezTreeBean);
            List<EzTreeBean> childShip = new ArrayList<>();
            for (EzTreeBean ezTreeBeanch : ezTreeBean.getChildren()) {
                String objStr = ezTreeBeanch.getAttributes();
                JSONObject objShip = JSONObject.fromObject(objStr);
                if (objShip.containsKey("tradeId") && objShip.getString("tradeId").equals("1")) {
                    childShip.add(ezTreeBeanch);
                }
            }
            if (childShip.size() == 0) ezTreeBean.setState(null);
            ezTreeBean.setChildren(childShip);
        }

        return rtLst;

    }

    // 上报货运单证
    @Override
    public String uploadshipbill(String shipbillId, String dockCod) {
        List<String> shipbillList = HdUtils.paraseStrs(shipbillId);
        String message = "";
        for (String shipbillid : shipbillList) {
            ShipBill sb = JpaUtils.findById(ShipBill.class, shipbillid);
            Ship ship = JpaUtils.findById(Ship.class, sb.getShipNo());
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("cmdId", "2017");
            if ("03406500".equals(dockCod)) {
                jsonObj.put("coId", "03406500");
            }
            if ("03409000".equals(dockCod)) {
                // jsonObj.put("coId","03409000");
                jsonObj.put("coId", "03406500");
            }
            Map<String, String> map = new HashMap<String, String>();
            ShipBillInter shipBillInter = new ShipBillInter();
            if ("I".equals(sb.getiEId())) {
                shipBillInter.setShipId(ship.getNewIShipId());
                shipBillInter.setSvoyageId(ship.getNewGroupShipNo());
                shipBillInter.setShipName(ship.getcShipNam());
                shipBillInter.setVoyage(ship.getIvoyage());

                // 获取cargoCode
                String cardoCodeJpql = "select p from ShipBill s, BillCar b, PortCar p where s.shipbillId = b.shipbillId and b.portCarNo = p.portCarNo and s.shipbillId =:shipbillId";
                QueryParamLs cargoCodeParams = new QueryParamLs();
                cargoCodeParams.addParam("shipbillId", shipbillid);
                List<PortCar> cargoCodeList = JpaUtils.findAll(cardoCodeJpql, cargoCodeParams);
                if (cargoCodeList.size() > 0) {
                    String carType = "";
                    for (PortCar portCar : cargoCodeList) {
                        if (HdUtils.strNotNull(portCar.getCarKind())) {
                            carType = portCar.getCarKind();
                            break;
                        }
                    }
                    String cCarType = "SELECT c FROM CCarKind c where c.carKind =:carTyp";
                    QueryParamLs carTypParams = new QueryParamLs();
                    carTypParams.addParam("carTyp", carType);
                    List<CCarKind> CCarTypList = JpaUtils.findAll(cCarType, carTypParams);
                    if (CCarTypList.size() > 0) {
                        String jpqla = "select a from VGroupCorpCargo a where a.cargoName=:cargoNam and a.typeFlag = '4'";
                        QueryParamLs paramLsa = new QueryParamLs();
                        paramLsa.addParam("cargoNam", CCarTypList.get(0).getCarKindNam());
                        List<VGroupCorpCargo> vGroupCorpCargoList = JpaUtils.findAll(jpqla, paramLsa);
                        if (vGroupCorpCargoList.size() > 0) {
                            shipBillInter.setCargoCode(String.valueOf(vGroupCorpCargoList.get(0).getCargoCode()));
                        } else {
                            shipBillInter.setCargoCode("");
                        }
                    } else {
                        shipBillInter.setCargoCode("");
                    }
                } else {
                    shipBillInter.setCargoCode("");
                }
                // if(HdUtils.strNotNull(ship.getiCargoNam())){
                // String jpqla="select a from VGroupCorpCargo a where
                // a.cargoName=:cargoNam and a.typeFlag = '4'";
                // QueryParamLs paramLsa = new QueryParamLs();
                // paramLsa.addParam("cargoNam", ship.getiCargoNam());
                // List<VGroupCorpCargo>
                // vGroupCorpCargoList=JpaUtils.findAll(jpqla, paramLsa);
                // if(vGroupCorpCargoList.size()>0){
                // shipBillInter.setCargoCode(String.valueOf(vGroupCorpCargoList.get(0).getCargoCode()));
                // }
                // }else{
                // shipBillInter.setCargoCode("");
                // }
            }
            if ("E".equals(sb.getiEId())) {
                shipBillInter.setShipId(ship.getNewEShipId());
                shipBillInter.setSvoyageId(ship.getNewGroupShipNo());
                shipBillInter.setShipName(ship.getcShipNam());
                shipBillInter.setVoyage(ship.getEvoyage());
                if (HdUtils.strNotNull(ship.getiCargoNam())) {
                    String jpqla = "select a from VGroupCorpCargo a where a.cargoName=:cargoNam";
                    QueryParamLs paramLsa = new QueryParamLs();
                    paramLsa.addParam("cargoNam", ship.getECargoNam());
                    List<VGroupCorpCargo> vGroupCorpCargoList = JpaUtils.findAll(jpqla, paramLsa);
                    if (vGroupCorpCargoList.size() > 0) {
                        shipBillInter.setCargoCode(vGroupCorpCargoList.get(0).getCargoCode());
                    }
                } else {
                    shipBillInter.setCargoCode("");
                }
            }
            shipBillInter.setCargoId(sb.getShipbillId());
            if ("I".equals(sb.getiEId()) && "1".equals(sb.getTradeId())) {
                // 我们的内进
                // 集团1是外贸，2是内贸，和我们相反
                shipBillInter.setBillType("NJ");
                shipBillInter.setTradeFlag("2");
                shipBillInter.setIeFlag("I");
            }
            if ("I".equals(sb.getiEId()) && "2".equals(sb.getTradeId())) {
                // 我们的外进
                // 集团1是外贸，2是内贸，和我们相反
                shipBillInter.setBillType("WJ");
                shipBillInter.setTradeFlag("1");
                shipBillInter.setIeFlag("I");
            }
            if ("E".equals(sb.getiEId()) && "1".equals(sb.getTradeId())) {
                // 我们的内出
                // 集团1是外贸，2是内贸，和我们相反
                shipBillInter.setBillType("NC");
                shipBillInter.setTradeFlag("2");
                shipBillInter.setIeFlag("E");
            }
            if ("E".equals(sb.getiEId()) && "2".equals(sb.getTradeId())) {
                // 我们的外出
                // 集团1是外贸，2是内贸，和我们相反
                shipBillInter.setBillType("WC");
                shipBillInter.setTradeFlag("1");
                shipBillInter.setIeFlag("E");
            }
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            shipBillInter.setBillDate(format.format(sb.getRecTim()));

            shipBillInter.setBillNo(sb.getBillNo());
            shipBillInter.setConsignerCode("");
            shipBillInter.setForwarderCode("");

            shipBillInter.setCnorCode("");
            shipBillInter.setCneeCode("");
            shipBillInter.setPol(sb.getLoadPortCod());
            shipBillInter.setPod(sb.getTranPortCod());

            shipBillInter.setCargoMark("");
            shipBillInter.setPackageCode("");
            shipBillInter.setFormat("");
            shipBillInter.setOriginCode("");
            shipBillInter.setMataCode("");
            shipBillInter.setCargoNum(sb.getPieces());
            shipBillInter.setCargoWgt(sb.getWeights());
            if (sb.getValumes() != null) {
                shipBillInter.setCargoVol(sb.getValumes());
            } else {
                shipBillInter.setCargoVol(new BigDecimal("0"));
            }
            shipBillInter.setDirTruckNum(new BigDecimal("0"));
            shipBillInter.setDirTruckWgt(new BigDecimal("0"));
            shipBillInter.setOutTruckVol(new BigDecimal("0"));
            shipBillInter.setDirTrainNum(new BigDecimal("0"));
            shipBillInter.setDirTrainWgt(new BigDecimal("0"));
            shipBillInter.setDirTrainVol(new BigDecimal("0"));
            shipBillInter.setDirShipNum(new BigDecimal("0"));
            shipBillInter.setDirShipVol(new BigDecimal("0"));
            shipBillInter.setDirShipWgt(new BigDecimal("0"));
            String jpqz = "select count(a.billNo) from WorkCommand a where a.billNo=:billNo and a.shipNo=:shipNo ";
            QueryParamLs paramLsz = new QueryParamLs();
            paramLsz.addParam("billNo", sb.getBillNo());
            paramLsz.addParam("shipNo", sb.getShipNo());
            List<Long> workNum = JpaUtils.findAll(jpqz, paramLsz);
            if (workNum.size() > 0) {
                shipBillInter.setWorkNum(new BigDecimal(workNum.get(0)));
            } else {
                shipBillInter.setWorkNum(new BigDecimal("0"));
            }
            shipBillInter.setWorkWgt(new BigDecimal("0"));
            shipBillInter.setWorkVol(new BigDecimal("0"));
            shipBillInter.setVisaVol(new BigDecimal("0"));
            shipBillInter.setVisaWgt(new BigDecimal("0"));
            shipBillInter.setVisaNum(new BigDecimal("0"));
            shipBillInter.setCiqNum(new BigDecimal("0"));
            shipBillInter.setCiqVol(new BigDecimal("0"));
            shipBillInter.setCiqWgt(new BigDecimal("0"));
            shipBillInter.setOutTrainNum(new BigDecimal("0"));
            shipBillInter.setOutTrainVol(new BigDecimal("0"));
            shipBillInter.setOutTrainWgt(new BigDecimal("0"));
            shipBillInter.setOutCntrNum(new BigDecimal("0"));
            shipBillInter.setOutCntrVol(new BigDecimal("0"));
            shipBillInter.setOutCntrWgt(new BigDecimal("0"));
            shipBillInter.setOutShipNum(new BigDecimal("0"));
            shipBillInter.setOutShipVol(new BigDecimal("0"));
            shipBillInter.setOutShipWgt(new BigDecimal("0"));
            shipBillInter.setBangFlag("");
            shipBillInter.setBangDemand("");
            if (sb.getReleaseTim() != null) {
                shipBillInter.setReleaseFlag("1");
            } else {
                shipBillInter.setReleaseFlag("0");
            }
            shipBillInter.setFlowdirTxt("");
            shipBillInter.setDemand("");
            shipBillInter.setNotes("");
            shipBillInter.setHandset("");
            shipBillInter.setLinktel("");
            shipBillInter.setLinkman("");
            shipBillInter.setDescription("");
            shipBillInter.setTeamOrgnId(dockCod);
            shipBillInter.setSubmitFlag("1");
            shipBillInter.setSubmitName(HdUtils.getCurUser().getAccount());
            shipBillInter.setSubmitTime(format.format(HdUtils.getDateTime()));
            map.put("cargoId", shipBillInter.getCargoId());
            map.put("shipId", shipBillInter.getShipId());
            map.put("svoyageId", shipBillInter.getSvoyageId());
            map.put("billType", shipBillInter.getBillType());
            map.put("billDate", shipBillInter.getBillDate());
            map.put("shipName", shipBillInter.getShipName());
            map.put("voyage", shipBillInter.getVoyage());
            map.put("tradeFlag", shipBillInter.getTradeFlag());
            map.put("ieFlag", shipBillInter.getIeFlag());
            map.put("billNo", shipBillInter.getBillNo());
            map.put("consignerCode", shipBillInter.getConsignerCode());
            map.put("consignCode", shipBillInter.getConsignCode());
            map.put("forwarderCode", shipBillInter.getForwarderCode());
            map.put("cnorCode", shipBillInter.getCnorCode());
            map.put("cneeCode", shipBillInter.getCneeCode());
            map.put("pol", shipBillInter.getPol());
            map.put("pod", shipBillInter.getPod());
            map.put("cargoCode", shipBillInter.getCargoCode());
            map.put("cargoMark", shipBillInter.getCargoMark());
            map.put("packageCode", shipBillInter.getPackageCode());
            map.put("format", shipBillInter.getFormat());
            map.put("originCode", shipBillInter.getOriginCode());
            map.put("mataCode", shipBillInter.getMataCode().toString());
            map.put("cargoNum", shipBillInter.getCargoNum().toString());
            map.put("cargoWgt", shipBillInter.getCargoWgt().toString());
            map.put("cargoVol", shipBillInter.getCargoVol().toString());
            if (shipBillInter.getDirTruckNum() != null) {
                map.put("dirTruckNum", shipBillInter.getDirTruckNum().toString());
            }
            if (shipBillInter.getDirTruckWgt() != null) {
                map.put("dirTruckWgt", shipBillInter.getDirTruckWgt().toString());
            }
            if (shipBillInter.getDirTruckVol() != null) {
                map.put("dirTruckVol", shipBillInter.getDirTruckVol().toString());
            }
            if (shipBillInter.getDirTrainNum() != null) {
                map.put("dirTrainNum", shipBillInter.getDirTrainNum().toString());
            }
            if (shipBillInter.getDirTrainWgt() != null) {
                map.put("dirTrainWgt", shipBillInter.getDirTrainWgt().toString());
            }
            if (shipBillInter.getDirTrainVol() != null) {
                map.put("dirTrainVol", shipBillInter.getDirTrainVol().toString());
            }
            if (shipBillInter.getDirShipNum() != null) {
                map.put("dirShipNum", shipBillInter.getDirShipNum().toString());
            }
            if (shipBillInter.getDirShipWgt() != null) {
                map.put("dirShipWgt", shipBillInter.getDirShipWgt().toString());
            }
            if (shipBillInter.getDirShipVol() != null) {
                map.put("dirShipVol", shipBillInter.getDirShipVol().toString());
            }
            if (shipBillInter.getWorkNum() != null) {
                map.put("workNum", shipBillInter.getWorkNum().toString());
            }
            if (shipBillInter.getWorkWgt() != null) {
                map.put("workWgt", shipBillInter.getWorkWgt().toString());
            }
            if (shipBillInter.getWorkVol() != null) {
                map.put("workVol", shipBillInter.getWorkVol().toString());
            }
            if (shipBillInter.getVisaNum() != null) {
                map.put("visaNum", shipBillInter.getVisaNum().toString());
            }
            if (shipBillInter.getVisaWgt() != null) {
                map.put("visaWgt", shipBillInter.getVisaWgt().toString());
            }
            if (shipBillInter.getVisaVol() != null) {
                map.put("visaVol", shipBillInter.getVisaVol().toString());
            }
            if (shipBillInter.getCiqNum() != null) {
                map.put("ciqNum", shipBillInter.getCiqNum().toString());
            }
            if (shipBillInter.getCiqWgt() != null) {
                map.put("ciqWgt", shipBillInter.getCiqWgt().toString());
            }
            if (shipBillInter.getCiqVol() != null) {
                map.put("ciqVol", shipBillInter.getCiqVol().toString());
            }
            if (shipBillInter.getOutTruckNum() != null) {
                map.put("outTruckNum", shipBillInter.getOutTruckNum().toString());
            }
            if (shipBillInter.getOutTruckWgt() != null) {
                map.put("outTruckWgt", shipBillInter.getOutTruckWgt().toString());
            }
            if (shipBillInter.getOutTruckVol() != null) {
                map.put("outTruckVol", shipBillInter.getOutTruckVol().toString());
            }
            if (shipBillInter.getOutTrainNum() != null) {
                map.put("outTrainNum", shipBillInter.getOutTrainNum().toString());
            }
            if (shipBillInter.getOutTrainWgt() != null) {
                map.put("outTrainWgt", shipBillInter.getOutTrainWgt().toString());
            }
            if (shipBillInter.getOutTrainVol() != null) {
                map.put("outTrainVol", shipBillInter.getOutTrainVol().toString());
            }
            if (shipBillInter.getOutShipNum() != null) {
                map.put("outShipNum", shipBillInter.getOutShipNum().toString());
            }
            if (shipBillInter.getOutShipWgt() != null) {
                map.put("outShipWgt", shipBillInter.getOutShipWgt().toString());
            }
            if (shipBillInter.getOutShipVol() != null) {
                map.put("outShipVol", shipBillInter.getOutShipVol().toString());
            }
            if (shipBillInter.getOutCntrNum() != null) {
                map.put("outCntrNum", shipBillInter.getOutCntrNum().toString());
            }
            if (shipBillInter.getOutCntrWgt() != null) {
                map.put("outCntrWgt", shipBillInter.getOutCntrWgt().toString());
            }
            if (shipBillInter.getOutCntrVol() != null) {
                map.put("outCntrVol", shipBillInter.getOutCntrVol().toString());
            }
            map.put("bangFlag", shipBillInter.getBangFlag());
            map.put("bangDemand", shipBillInter.getBangDemand());
            map.put("releaseFlag", shipBillInter.getReleaseFlag());
            map.put("flowdirTxt", shipBillInter.getFlowdirTxt());
            map.put("demand", shipBillInter.getDemand());
            map.put("notes", shipBillInter.getNotes());
            map.put("handset", shipBillInter.getHandset());
            map.put("linktel", shipBillInter.getLinktel());
            map.put("linkman", shipBillInter.getLinkman());
            map.put("description", shipBillInter.getDescription());
            map.put("teamOrgnId", shipBillInter.getTeamOrgnId());
            map.put("submitFlag", shipBillInter.getSubmitFlag());
            map.put("submitName", shipBillInter.getSubmitName());
            map.put("submitTime", shipBillInter.getSubmitTime());
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
                        message = "success";
                    }
                    if (!resCode.equals(resp.getResCode()) || !resMsg.equals(resp.getResMsg())) {
                        message = "error";
                        // break;
                    }
                } catch (Exception e) {
                }
                // 断开连接
                conn.disconnect();
            } catch (Exception e) {

                throw new HdRunTimeException("发送 POST 请求出现异常!");
            }
            // 使用finally块来关闭输出流、输入流
            finally {
            }
        }
        return message;
    }

    @Override
    public List<ShipBill> getShipBillByShip(String shipNo, String iEId) {
        String sql = "select a  from ShipBill a where a.shipNo = :shipNo and a.iEId =:iEId";
        QueryParamLs params = new QueryParamLs();
        params.addParam("shipNo", shipNo);
        params.addParam("iEId", iEId);
        return JpaUtils.findAll(sql, params);
    }
}
