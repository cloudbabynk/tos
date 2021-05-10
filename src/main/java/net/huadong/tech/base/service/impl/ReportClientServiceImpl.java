package net.huadong.tech.base.service.impl;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.entity.ReportClient;
import net.huadong.tech.base.service.ReportClientService;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;
import net.huadong.tech.util.CommonUtil;
import net.huadong.tech.util.HdUtils;
import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author
 */
@Component
public class ReportClientServiceImpl implements ReportClientService {

    @Override
    public HdEzuiDatagridData find(HdQuery hdQuery) {
        String startTim =hdQuery.getStr("startTim");
        String weekNum =hdQuery.getStr("weekNum");

        String jpql = "select a from ReportClient a where 1=1 " ;
        QueryParamLs queryParamLs =new QueryParamLs();
        if(CommonUtil.strNotNull(startTim)){
            jpql+=" and a.startTim =:startTim ";
            queryParamLs.addParam("startTim",startTim);
        }
        if(CommonUtil.strNotNull(weekNum)){
            Integer week =Integer.valueOf(weekNum);
            jpql+=" and a.weekNum =:weekNum ";
            queryParamLs.addParam("weekNum",week);
        }
        jpql+= " order by  a.startTim desc,a.clientName,a.weekNum ";
        HdEzuiDatagridData list = JpaUtils.findByEz(jpql, queryParamLs, hdQuery);
        return list;
    }


    @Override
    public ReportClient findone(String clientId) {
        ReportClient reportclient= JpaUtils.findById( ReportClient.class, clientId);
        return reportclient;
    }

    @Override
    @Transactional
    public void removeAll(String ids) {
        List<String> idList = HdUtils.paraseStrs(ids);
        for (String carmeraId : idList) {
            ReportClient reportClient =JpaUtils.findById(ReportClient.class,carmeraId);
            JpaUtils.remove(reportClient);
        }
    }

    @Override
    @Transactional
    public void remove(String clientId) {
        JpaUtils.remove(ReportClient.class,clientId);
    }

    @Override
    @Transactional
    public HdMessageCode saveone(ReportClient reportclient) {
        if(HdUtils.strIsNull(reportclient.getClientId())){
            reportclient.setClientId(HdUtils.genUuid());
            JpaUtils.save(reportclient);}
        else
            JpaUtils.update(reportclient);
        return HdUtils.genMsg(reportclient);

    }
    @Override
    @Transactional
    public HdMessageCode saveAll(HdEzuiSaveDatagridData<ReportClient> reportclientLs) {
        List<ReportClient> reportClientInsert=reportclientLs.getInsertedRows();
        List<ReportClient> reportClientsUpdate=reportclientLs.getUpdatedRows();
        for (ReportClient reportclient : reportClientInsert) {
        	ReportClient reportclient1=new ReportClient();
        	reportclient1.setClientId(CommonUtil.genUuid());
        	reportclient1.setClientName(reportclient.getClientName());
        	reportclient1.setStartTim(reportclient.getStartTim());
        	reportclient1.setWeekNum(reportclient.getWeekNum());
        	reportclient1.setTeuNum(reportclient.getTeuNum());
            JpaUtils.save(reportclient1);
        }
        for (ReportClient reportclient : reportClientsUpdate) {
            JpaUtils.update(reportclient);
        }
        return HdUtils.genMsg(reportclientLs);
    }

}

