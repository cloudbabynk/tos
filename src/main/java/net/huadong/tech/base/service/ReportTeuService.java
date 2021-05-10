package net.huadong.tech.base.service;

import net.huadong.idev.hdmessagecode.HdMessageCode;

import net.huadong.tech.base.entity.ReportTeu;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;

import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;

import java.util.List;

/**
 * @author 孙璐
 * @date 2021-04-06
 */
public interface ReportTeuService {
    HdEzuiDatagridData find(HdQuery hdQuery);
    ReportTeu findone(String teuId);
    HdMessageCode saveone(ReportTeu reportteu);
    void removeAll(String ids);
    void remove(String teuId);
    HdMessageCode saveAll(HdEzuiSaveDatagridData<ReportTeu> reportteuLs);
}
