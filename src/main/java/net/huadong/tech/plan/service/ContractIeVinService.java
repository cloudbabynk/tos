package net.huadong.tech.plan.service;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.plan.entity.ContractIeVin;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;

import net.huadong.tech.base.bean.HdQuery;
import java.util.List;

/**
 * @author 孙璐
 * @date 2021-04-16
 */
public interface ContractIeVinService {
    HdEzuiDatagridData find(HdQuery hdQuery);
    HdMessageCode genPrint(String contractNos,String carNum);
    HdMessageCode genPrintExt(String ieVinIds);
    ContractIeVin findone(String noPkName);
    HdMessageCode saveone(ContractIeVin contractievin);
    void removeAll(List<ContractIeVin> contractievinLs);
    void remove(String noPkName);
    HdMessageCode saveAll(List<ContractIeVin> contractievinLs);
}
