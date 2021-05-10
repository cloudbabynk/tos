package net.huadong.tech.base.service.impl;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.entity.ReportTeu;
import net.huadong.tech.base.service.ReportTeuService;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.tech.map.entity.CGisWbillCamera;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;
import net.huadong.tech.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;

import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.util.HdUtils;
import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author
 */
@Component
public class ReportTeuServiceImpl implements ReportTeuService {

    @Override
    public HdEzuiDatagridData find(HdQuery hdQuery) {
        String jpql = "select a from ReportTeu a where 1=1 order by a.teuTim desc ";
        QueryParamLs paramLs = new QueryParamLs();
        HdEzuiDatagridData list = JpaUtils.findByEz(jpql, paramLs, hdQuery);
        return list;
    }


    @Override
    public ReportTeu findone(String teuId) {
        ReportTeu reportteu= JpaUtils.findById( ReportTeu.class, teuId);
        return reportteu;
    }

    @Override
    @Transactional
    public void removeAll(String ids) {
        List<String> idList = HdUtils.paraseStrs(ids);
        for (String carmeraId : idList) {
            JpaUtils.remove(ReportTeu.class, carmeraId);
        }
    }

    @Override
    @Transactional
    public void remove(String teuId) {
        JpaUtils.remove(ReportTeu.class,teuId);
    }

    @Override
    @Transactional
    public HdMessageCode saveone(ReportTeu reportteu) {
        if(HdUtils.strIsNull(reportteu.getTeuId())){
            reportteu.setTeuId(HdUtils.genUuid());
            HdUtils.initEntity(reportteu);
            JpaUtils.save(reportteu);}
        else
            JpaUtils.update(reportteu);
        return HdUtils.genMsg(reportteu);

    }
    @Override
    @Transactional
    public HdMessageCode saveAll(HdEzuiSaveDatagridData<ReportTeu> reportteuLs) {
        for (ReportTeu reportteu : reportteuLs.getInsertedRows()) {
            this.saveone(reportteu);
        }
        return HdUtils.genMsg(reportteuLs);
    }

}

