package net.huadong.tech.shipbill.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.EzDropBean;
import net.huadong.tech.base.bean.EzTreeBean;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.entity.CBrand;
import net.huadong.tech.base.entity.CCarKind;
import net.huadong.tech.base.entity.CCarTyp;
import net.huadong.tech.base.entity.CDock;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.tech.shipbill.entity.BillCar;
import net.huadong.tech.shipbill.entity.ShipBill;
import net.huadong.tech.shipbill.service.BillCarService;
import net.huadong.tech.privilege.controller.PrivilegeController;
import net.huadong.tech.privilege.entity.AuthUser;
import net.huadong.tech.ship.entity.Ship;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;
import net.huadong.tech.util.CommonUtil;
import net.huadong.tech.util.HdUtils;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.dubbo.config.annotation.Reference;

/**
 * @author
 */
@Controller
@RequestMapping("webresources/login/ship/BillCar")
public class BillCarController {

    @Autowired
    private BillCarService billCarService;

    //菜单进入
    @RequestMapping(value = "/billcar.htm")
    public String pageTh(String Type, Model model) {
        Random random = new Random();
        model.addAttribute("radi", random.nextInt() * 1000);
        model.addAttribute("Type", Type);
        return "system/ship/billcar";
    }

    //菜单进入
    @RequestMapping(value = "/billcarquery.htm")
    public String pageBcq(Model model) {
        Random random = new Random();
        model.addAttribute("radi", random.nextInt() * 1000);
        return "system/ship/billcarquery";
    }

    @RequestMapping(value = "/findShipVoyage")
    @ResponseBody
    public HdEzuiDatagridData findShipVoyage(@RequestBody HdQuery hdQuery) {
        return billCarService.findShipVoyage(hdQuery);
    }

    @RequestMapping(value = "/findShipBillCar")
    @ResponseBody
    public HdEzuiDatagridData findBillCar(@RequestBody HdQuery hdQuery) {
        return billCarService.findShipBillCar(hdQuery);
    }

    /**
     * 通用列表查询
     *
     * @param params
     * @return
     */
    @RequestMapping(value = "/find")
    @ResponseBody
    public HdEzuiDatagridData find(@RequestBody HdQuery hdQuery) {
        return billCarService.find(hdQuery);
    }

    /**
     * 卸船理货查询
     *
     * @param params
     * @return
     */
    @RequestMapping(value = "/findXclh")
    @ResponseBody
    public HdEzuiDatagridData findXclh(@RequestBody HdQuery hdQuery) {
        return billCarService.findXclh(hdQuery);
    }

    // form保存
	 
	/*	@RequestMapping("/saveone")
		@ResponseBody
		public HdMessageCode saveone(@RequestBody BillCar BillCar) {

			return BillCarService.saveone(BillCar);
		}*/


    @RequestMapping("/saveone")
    @ResponseBody
    public HdMessageCode saveone(@RequestBody BillCar billCar) {
        return billCarService.saveone(billCar);
    }

    //行编辑保存
    @RequestMapping("/save")
    @ResponseBody
    public HdMessageCode save(@RequestBody HdEzuiSaveDatagridData<BillCar> gridData) {
        //CommonUtil.initEntity(gridData);
        return billCarService.save(gridData);
    }

    /**
     * 批量删除
     */
    @RequestMapping(value = "/removeAll")
    @ResponseBody
    public HdMessageCode removeAll(String billcarId) {
        billCarService.removeAll(billcarId);
        return HdUtils.genMsg();
    }

    /**
     * 车属类别下拉
     */
    @RequestMapping(value = "/getCCarKindDrop")
    @ResponseBody
    public List<EzDropBean> getCCarKindDrop() {
        List<EzDropBean> list = new ArrayList<EzDropBean>();
        String jpql = " select a  from  CCarKind a  where 1=1 ";
        QueryParamLs params = new QueryParamLs();
        List<CCarKind> ls = JpaUtils.findAll(jpql, params);
        for (CCarKind cc : ls) {
            EzDropBean drop = new EzDropBean();
            drop.setValue(cc.getCarKind());
            drop.setLabel(cc.getCarKindNam());
            list.add(drop);
        }
        return list;
    }

    /**
     * 所属品牌下拉
     */
    @RequestMapping(value = "/getCBrandDrop")
    @ResponseBody
    public List<EzDropBean> getCBrandDrop() {
        List<EzDropBean> list = new ArrayList<EzDropBean>();
        String jpql = " select a  from  CBrand a  where 1=1 ";
        QueryParamLs params = new QueryParamLs();
        List<CBrand> ls = JpaUtils.findAll(jpql, params);
        for (CBrand cc : ls) {
            EzDropBean drop = new EzDropBean();
            drop.setValue(cc.getBrandCod());
            drop.setLabel(cc.getBrandNam());
            list.add(drop);
        }
        return list;
    }

    /**
     * 所属码头下拉
     */
    @RequestMapping(value = "/getCDockDrop")
    @ResponseBody
    public List<EzDropBean> getCDockDrop() {
        List<EzDropBean> list = new ArrayList<EzDropBean>();
        String jpql = " select a  from  CDock a  where 1=1 ";
        QueryParamLs params = new QueryParamLs();
        List<CDock> ls = JpaUtils.findAll(jpql, params);
        for (CDock cc : ls) {
            EzDropBean drop = new EzDropBean();
            drop.setValue(cc.getDockCod());
            drop.setLabel(cc.getDockNam());
            list.add(drop);
        }
        return list;
    }

    /**
     * 车类下拉
     */
    @RequestMapping(value = "/getCCarTypDrop")
    @ResponseBody
    public List<EzDropBean> getCCarTypDrop(String q, String iEId, String tradeId, String brandCod, String carKind) {
        List<EzDropBean> list = new ArrayList<EzDropBean>();
        String jpql = " select a  from  CCarTyp a  where 1=1 ";
        if (Ship.JK.equals(iEId) && Ship.WM.equals(tradeId)) {
            jpql = " select  a  from  CCarTyp a, CBrandDetail b where 1=1 and b.iEId = 'I' and b.tradeId = '2' ";
        }
        QueryParamLs params = new QueryParamLs();

        if (CommonUtil.strNotNull(brandCod)) {
            jpql = jpql + " and  a.brandCod =:brandCod ";
            params.addParam("brandCod",brandCod);
        }

        if (CommonUtil.strNotNull(carKind)) {
            jpql = jpql + " and  a.carKind =:carKind ";
            params.addParam("carKind",carKind);
        }

        if (HdUtils.strNotNull(q)) {
            jpql += "and a.carTypNam like :carTypNam";
            params.addParam("carTypNam", "%" + q + "%");
        }
        List<CCarTyp> ls = JpaUtils.findAll(jpql, params);
        for (CCarTyp cc : ls) {
            EzDropBean drop = new EzDropBean();
            drop.setValue(cc.getCarTyp());
            drop.setLabel(cc.getCarTypNam());
            list.add(drop);
        }
        return list;
    }

}
