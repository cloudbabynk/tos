package net.huadong.tech.plan.service.impl;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.dao.QueryParamLs;

import net.huadong.tech.plan.entity.ContractIeVin;
import net.huadong.tech.plan.service.ContractIeVinService;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.util.HdUtils;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author
 */
@Component
public class ContractIeVinServiceImpl implements ContractIeVinService {

    @Override
    public HdEzuiDatagridData find(HdQuery hdQuery) {
        HdEzuiDatagridData hdEzuiDatagridData = new HdEzuiDatagridData();
        String contractNo= hdQuery.getStr("contractNo");
        String jpql="";
        if(CommonUtil.strNotNull(contractNo)) {
            jpql = "select a from ContractIeVin a where 1=1 and a.contractIdPlan=:contractNo  ";

            QueryParamLs paramLs = new QueryParamLs();
            paramLs.addParam("contractNo", contractNo);
            hdEzuiDatagridData = JpaUtils.findByEz(jpql, paramLs, hdQuery);
        }
        return hdEzuiDatagridData;
    }

    @Override
    public HdMessageCode genPrint(String contractNos, String carNums) {
        HdMessageCode hdMessageCode = new HdMessageCode();
        try {
            String[] contractNosArr = contractNos.split(",");
            String[] carNumsArr = carNums.split(",");
            for (int i = 0; i < contractNosArr.length; i++) {

                for (int j = 0; j < Integer.parseInt(carNumsArr[i]); j++) {
                    ContractIeVin contractIeVin = new ContractIeVin();
                    contractIeVin.setIeVinId(HdUtils.genUuid());
                    contractIeVin.setSeqNo(String.valueOf(i+1));
                    contractIeVin.setContractIdPlan(contractNosArr[i]);
                    contractIeVin.setIsTally("0");
                    contractIeVin.setIsUser("1");
                    contractIeVin.setVinNo("vepc" + new Date().getTime());
                    JpaUtils.save(contractIeVin);
                    //todo:这里调用打印接口
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            hdMessageCode.setMessage(e.getMessage());
            hdMessageCode.setMessage("-1");
            return hdMessageCode;
        }
        hdMessageCode.setMessage("二维码已生成！");
        hdMessageCode.setCode("200");
        return hdMessageCode;
    }


    @Override
    public HdMessageCode genPrintExt(String ieVinIds) {
        HdMessageCode hdMessageCode = new HdMessageCode();
        try {
            String[] ieVinIdArr = ieVinIds.split(",");
            for (int i = 0; i < ieVinIdArr.length; i++) {
            ContractIeVin contractIeVin =JpaUtils.findById(ContractIeVin.class,ieVinIdArr[i]);
            //todo:这里要调用打印接口
            contractIeVin.setIsUser("1");
            JpaUtils.update(contractIeVin);
            }
        } catch (Exception e) {
            e.printStackTrace();
            hdMessageCode.setMessage(e.getMessage());
            hdMessageCode.setMessage("-1");
            return hdMessageCode;
        }
        hdMessageCode.setMessage("二维码已生成！");
        hdMessageCode.setCode("200");
        return hdMessageCode;
    }

    @Override
    public ContractIeVin findone(String noPkName) {
        ContractIeVin contractievin = JpaUtils.findById(ContractIeVin.class, noPkName);
        return contractievin;
    }

    @Override
    @Transactional
    public void removeAll(List<ContractIeVin> contractievinLs) {

    }

    @Override
    @Transactional
    public void remove(String noPkName) {
        JpaUtils.remove(ContractIeVin.class, noPkName);
    }

    @Override
    @Transactional
    public HdMessageCode saveone(ContractIeVin contractievin) {


        return HdUtils.genMsg(contractievin);

    }

    @Override
    @Transactional
    public HdMessageCode saveAll(List<ContractIeVin> contractievinLs) {
        for (ContractIeVin contractievin : contractievinLs) {
            this.saveone(contractievin);
        }
        return HdUtils.genMsg(contractievinLs);
    }

}

