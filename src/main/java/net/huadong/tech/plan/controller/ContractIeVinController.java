package net.huadong.tech.plan.controller;

import java.util.Date;
import java.util.Random;
import java.util.UUID;
import java.util.List;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.plan.entity.ContractIeVin;
import net.huadong.tech.plan.service.ContractIeVinService;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import org.springframework.ui.Model;

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
 * @date 2021-04-16
 */
@Controller
@RequestMapping("webresources/login/privilege/ContractIeVin")  //注意路径
public class ContractIeVinController  {

    @Autowired
    private ContractIeVinService contractIeVinService;

    @RequestMapping(value = "/tiplanprintdata.htm")
    public String pagePrint( Model model) {
        // SG疏货 JG集港
//		if ("JG".equals(contractTyp)) {
//			model.addAttribute("contractTyp", contractTyp);
//		}
//		if ("SG".equals(contractTyp)) {
//			model.addAttribute("contractTyp", contractTyp);
//		}
        Random random = new Random();
        model.addAttribute("radi", random.nextInt() * 1000);
        return "system/plan/tiplanprintdata";
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
        HdEzuiDatagridData hdEzuiDatagridData = contractIeVinService.find(hdQuery);
        return hdEzuiDatagridData;
    }

    //genPrint genPrintExt

    @RequestMapping(value = "/genPrint", method={RequestMethod.POST})
    @ResponseBody
    public HdMessageCode genPrint(String contractNos,String carNums) {

        return contractIeVinService.genPrint(contractNos,carNums);
    }

    @RequestMapping(value = "/genPrintExt", method={RequestMethod.POST})
    @ResponseBody
    public HdMessageCode genPrintExt(String ieVinIds) {

        return contractIeVinService.genPrintExt(ieVinIds);
    }

    /**
     * 查询
     *
     * @param params
     *            参数
     * @return 查询结果
     */
    @RequestMapping(value = "/findone", method={RequestMethod.POST, RequestMethod.GET})
    public ContractIeVin findone(String noPkName) {
        if (HdUtils.strIsNull(noPkName)) {// 增加时默认初值
            ContractIeVin entity = new ContractIeVin();
            return entity;
        }
        return contractIeVinService.findone(noPkName);
    }

    /**
     * 单条删除
     */
    @RequestMapping(value = "/remove", method={RequestMethod.POST})
    public HdMessageCode remove(@RequestBody ContractIeVin contractievin) {
      //  contractIeVinService.remove(contractievin.getNoPkName());
        return HdUtils.genMsg();
    }


    /**
     * 批量删除
     */
    @RequestMapping(value = "/removeAll", method={RequestMethod.POST})
    public HdMessageCode removeAll(@RequestBody List<ContractIeVin> contractievinLs) {
        contractIeVinService.removeAll(contractievinLs);
        return HdUtils.genMsg();
    }
    /**
     * 保存资源信息
     *
     * @param ContractIeVin
     * @return
     */
    @RequestMapping(value = "/saveone", method={RequestMethod.POST})
    public HdMessageCode saveone(@RequestBody ContractIeVin contractievin) {
        return contractIeVinService.saveone(contractievin);
    }
    /**
     * 保存资源信息
     *
     * @param ContractIeVin
     * @return
     */
    @RequestMapping(value = "/saveAll", method={RequestMethod.POST})
    public HdMessageCode saveAll(@RequestBody List<ContractIeVin> contractievinLs) {
        return contractIeVinService.saveAll(contractievinLs);
    }
}

