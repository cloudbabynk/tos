package net.huadong.tech.base.controller;

import java.util.*;


import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.EzDropBean;
import net.huadong.tech.base.entity.CCarKind;
import net.huadong.tech.base.entity.CCarTypFeeName;

import net.huadong.tech.base.entity.ReportClient;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;
import org.springframework.ui.Model;
import  net.huadong.tech.base.service.CCarTypFeeNameService;
import net.huadong.tech.privilege.controller.PrivilegeController;
import net.huadong.tech.privilege.entity.AuthUser;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.util.HdUtils;

/**
 * @author 孙璐
 * @date 2021-05-06
 */
@Controller
@RequestMapping("webresources/login/base/CCarTypFeeName")  //注意路径
public class CCarTypFeeNameController  {

    @Autowired
    private CCarTypFeeNameService cCarTypFeeNameService;
    //菜单进入
    @RequestMapping(value = "/ccartypfeename.htm")
    public String  pageTh(Model model){
        Random random = new Random();
        model.addAttribute("radi", random.nextInt()*1000);
        return "system/base/ccartypfeename";
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
        return cCarTypFeeNameService.find(hdQuery);
    }


    /**
     * 通过ID查询计费类型
     *
     * @param params
     * @return
     */
    @RequestMapping(value = "/findCarFeeTyp", method={RequestMethod.POST})
    @ResponseBody
    public List<EzDropBean> findCarFeeTyp(String id) {
        List<EzDropBean> list = new ArrayList<EzDropBean>();
       CCarTypFeeName carTypFeeName = JpaUtils.findById(CCarTypFeeName.class,id);
       if(null!= carTypFeeName)
       {
           String code=carTypFeeName.getCarFeeTyp();
           String label=HdUtils.getSysCodeName("CAR_FEE_TYP",code);
               EzDropBean drop = new EzDropBean();
               drop.setValue(code);
               drop.setLabel(label);
           list.add(drop);
           return  list;
       }else {
           return  null;
       }
       }





    /**
     * 单条删除
     */
    @RequestMapping(value = "/remove", method={RequestMethod.POST})
    @ResponseBody
    public HdMessageCode remove(@RequestBody ReportClient reportclient) {
        cCarTypFeeNameService.remove(reportclient.getClientId());
        return HdUtils.genMsg();
    }



    /**
     * 批量删除
     */
    @RequestMapping(value = "/removeAll", method={RequestMethod.POST})
    @ResponseBody
    public HdMessageCode removeAll(String clientIds) {
        cCarTypFeeNameService.removeAll(clientIds);
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
    public HdMessageCode saveone(@RequestBody CCarTypFeeName cCarTypFeeName) {
        return cCarTypFeeNameService.saveone(cCarTypFeeName);
    }

    /**
     * 保存资源信息
     *
     * @param ReportClient
     * @return
     */
    @RequestMapping(value = "/saveAll", method={RequestMethod.POST})
    @ResponseBody
    public HdMessageCode saveAll(@RequestBody HdEzuiSaveDatagridData<CCarTypFeeName> cCarTypFeeNameLs) {
        return cCarTypFeeNameService.saveAll(cCarTypFeeNameLs);
    }

}

