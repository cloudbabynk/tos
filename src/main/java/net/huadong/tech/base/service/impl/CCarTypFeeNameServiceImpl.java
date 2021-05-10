package net.huadong.tech.base.service.impl;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.entity.CCarTypFeeName;
import net.huadong.tech.base.entity.ReportClient;
import net.huadong.tech.base.service.CCarTypFeeNameService;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;
import net.huadong.tech.util.CommonUtil;
import net.huadong.tech.util.HdUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class CCarTypFeeNameServiceImpl  implements CCarTypFeeNameService {

    @Override
    public HdEzuiDatagridData find(HdQuery hdQuery){

        String carTypName=hdQuery.getStr("carTypName");
        String jpql =" select a from CCarTypFeeName a where 1=1 ";
        QueryParamLs queryParamLs =new QueryParamLs();
        if(CommonUtil.strNotNull(carTypName))
        {
            queryParamLs.addParam("carTypName",carTypName);
            jpql+= " and a.carTypName =:carTypName ";
        }
        HdEzuiDatagridData list = JpaUtils.findByEz(jpql, queryParamLs, hdQuery);
        return list;
    }

    @Override
    @Transactional
    public void remove(String id) {
        JpaUtils.remove(CCarTypFeeName.class,id);
    }

    @Override
    public HdMessageCode saveone(CCarTypFeeName carTypFeeName) {
        if(HdUtils.strIsNull(carTypFeeName.getId())) {
            HdUtils.initEntity(carTypFeeName);
            JpaUtils.save(carTypFeeName);
        }
        return HdUtils.genMsg(carTypFeeName);

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
    public HdMessageCode saveAll(HdEzuiSaveDatagridData<CCarTypFeeName> carTypFeeNameLs) {
        List<CCarTypFeeName> carTypFeeNameInsert=carTypFeeNameLs.getInsertedRows();
        List<CCarTypFeeName> carTypFeeNameUpdate=carTypFeeNameLs.getUpdatedRows();
        for (CCarTypFeeName carTypFeeName : carTypFeeNameInsert) {
            carTypFeeName.setId(HdUtils.genUuid());
            HdUtils.initEntity(carTypFeeName);
            JpaUtils.save(carTypFeeName);
        }
        for (CCarTypFeeName carTypFeeName : carTypFeeNameUpdate) {
            HdUtils.initEntity(carTypFeeName);
            JpaUtils.update(carTypFeeName);
        }
        return HdUtils.genMsg(carTypFeeNameLs);
    }


}