package net.huadong.tech.base.service;

import net.huadong.idev.hdmessagecode.HdMessageCode;

import net.huadong.tech.base.entity.ReportClient;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;

import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;

import java.util.List;

/**
 * @author 孙璐
 * @date 2021-04-06
 */
public interface ReportClientService {
    HdEzuiDatagridData find(HdQuery hdQuery);
    ReportClient findone(String clientId);
    HdMessageCode saveone(ReportClient reportclient);
    void removeAll(String ids);
    void remove(String clientId);
    HdMessageCode saveAll(HdEzuiSaveDatagridData<ReportClient> reportclientLs);
}
