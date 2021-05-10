package net.huadong.tech.base.controller;

import java.util.Date;
import java.util.Random;
import java.util.UUID;
import java.util.List;

import net.huadong.idev.hdmessagecode.HdMessageCode;

import net.huadong.tech.base.entity.ReportClient;
import net.huadong.tech.base.service.ReportClientService;
import net.huadong.tech.privilege.controller.PrivilegeController;
import net.huadong.tech.privilege.entity.AuthUser;

import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.util.HdUtils;

/**
 * @author 孙璐
 * @date 2021-04-06
 */
@Controller
@RequestMapping("webresources/login/base/ReportClient")  //注意路径
public class ReportClientController  {

    @Autowired
    private ReportClientService reportClientService;

    //菜单进入
    @RequestMapping(value = "/reportclient.htm")
    public String  pageTh(Model model){
        Random random = new Random();
        model.addAttribute("radi", random.nextInt()*1000);
        return "system/base/reportClient";
    }
    //菜单进入
    @RequestMapping(value = "/reportclientform.htm")
    public String  pageThForm(Model model){
        Random random = new Random();
        model.addAttribute("radi", random.nextInt()*1000);
        return "system/base/reportclientform";
    }


    /**
     * 通用列表查询
     *
     * @param params
     * @return
     */
    @RequestMapping(value = "/find", method={RequestMethod.POST})
    @ResponseBody
    public HdEzuiDatagridData find(@RequestBody HdQuery hdQuery) {
        return reportClientService.find(hdQuery);
    }

    /**
     * 查询
     *
     * @param params
     *            参数
     * @return 查询结果
     */
    @RequestMapping(value = "/findone", method={RequestMethod.POST, RequestMethod.GET})
    public ReportClient findone(String clientId) {
        if (HdUtils.strIsNull(clientId)) {// 增加时默认初值
            ReportClient entity = new ReportClient();
            return entity;
        }
        return reportClientService.findone(clientId);
    }

    /**
     * 单条删除
     */
    @RequestMapping(value = "/remove", method={RequestMethod.POST})
    @ResponseBody
    public HdMessageCode remove(@RequestBody ReportClient reportclient) {
        reportClientService.remove(reportclient.getClientId());
        return HdUtils.genMsg();
    }


    /**
     * 批量删除
     */
    @RequestMapping(value = "/removeAll", method={RequestMethod.POST})
    @ResponseBody
    public HdMessageCode removeAll(String clientIds) {
        reportClientService.removeAll(clientIds);
        return HdUtils.genMsg();
    }
    /**
     * 保存资源信息
     *
     * @param ReportClient
     * @return
     */
    @RequestMapping(value = "/saveone", method={RequestMethod.POST})
    @ResponseBody
    public HdMessageCode saveone(@RequestBody ReportClient reportclient) {
        return reportClientService.saveone(reportclient);
    }
    /**
     * 保存资源信息
     *
     * @param ReportClient
     * @return
     */
    @RequestMapping(value = "/saveAll", method={RequestMethod.POST})
    @ResponseBody
    public HdMessageCode saveAll(@RequestBody HdEzuiSaveDatagridData<ReportClient> reportclientLs) {
        return reportClientService.saveAll(reportclientLs);
    }
}

