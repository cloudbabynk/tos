package net.huadong.tech.base.controller;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.entity.ReportTeu;
import net.huadong.tech.base.service.ReportTeuService;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;
import org.springframework.ui.Model;

import net.huadong.tech.privilege.controller.PrivilegeController;
import net.huadong.tech.privilege.entity.AuthUser;

import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.util.HdUtils;

import java.util.List;
import java.util.Random;

/**
 * @author 孙璐
 * @date 2021-04-06
 */
@Controller
@RequestMapping("webresources/login/base/ReportTeu")  //注意路径
public class ReportTeuController  {

    @Autowired
    private ReportTeuService reportTeuService;


    //菜单进入
    @RequestMapping(value = "/reportteu.htm")
    public String  pageTh(Model model){
        Random random = new Random();
        model.addAttribute("radi", random.nextInt()*1000);
        return "system/base/reportTeu";
    }


    //菜单进入
    @RequestMapping(value = "/reportteuform.htm")
    public String  pageThForm(Model model){
        Random random = new Random();
        model.addAttribute("radi", random.nextInt()*1000);
        return "system/base/reportteuform";
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
        return reportTeuService.find(hdQuery);
    }

    /**
     * 查询
     *
     * @param params
     *            参数
     * @return 查询结果
     */
    @RequestMapping(value = "/findone", method={RequestMethod.POST, RequestMethod.GET})
    public ReportTeu findone(String teuId) {
        if (HdUtils.strIsNull(teuId)) {// 增加时默认初值
            ReportTeu entity = new ReportTeu();
            return entity;
        }
        return reportTeuService.findone(teuId);
    }

    /**
     * 单条删除
     */
    @RequestMapping(value = "/remove", method={RequestMethod.POST})
    @ResponseBody
    public HdMessageCode remove(@RequestBody ReportTeu reportteu) {
        reportTeuService.remove(reportteu.getTeuId());
        return HdUtils.genMsg();
    }


    /**
     * 批量删除
     */
    @RequestMapping(value = "/removeAll", method={RequestMethod.POST})
    @ResponseBody
    public HdMessageCode removeAll(String teuIds) {
        reportTeuService.removeAll(teuIds);
        return HdUtils.genMsg();
    }
    /**
     * 保存资源信息
     *
     * @param ReportTeu
     * @return
     */
    @RequestMapping(value = "/saveone", method={RequestMethod.POST})
    @ResponseBody
    public HdMessageCode saveone(@RequestBody ReportTeu reportteu) {
        return reportTeuService.saveone(reportteu);
    }
    /**
     * 保存资源信息
     *
     * @param ReportTeu
     * @return
     */
    @RequestMapping(value = "/saveAll", method={RequestMethod.POST})
    @ResponseBody
    public HdMessageCode saveAll(@RequestBody HdEzuiSaveDatagridData<ReportTeu> gridData ) {
        return reportTeuService.saveAll(gridData);
    }
}

