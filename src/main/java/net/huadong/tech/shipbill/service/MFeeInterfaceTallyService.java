package net.huadong.tech.shipbill.service;

import net.huadong.tech.shipbill.entity.ContractIeDoc;
import net.huadong.tech.shipbill.entity.MFeeInterfaceTally;
import net.huadong.tech.shipbill.entity.MFeeInterfaceTallyBak;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;
import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.HdQuery;
import java.util.List;

/**
 * @author yuliang
 * @date 2020-05-13
 */
public interface MFeeInterfaceTallyService {
	HdEzuiDatagridData find(HdQuery hdQuery);
    MFeeInterfaceTally findone(String id);
    HdMessageCode saveone(MFeeInterfaceTally mfeeinterfacetally);
    void removeAll(String ids);
    void remove(String id);
    HdMessageCode saveAll(HdEzuiSaveDatagridData<MFeeInterfaceTally> gridData);
    HdMessageCode updateData(String shipNo,String iEId,String transPortTypeId, String moveTyp);
    MFeeInterfaceTallyBak copyData(MFeeInterfaceTally mfeeinterfacetally);
}
