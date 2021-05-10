package net.huadong.tech.base.service.impl;

import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.bean.HdRunTimeException;
import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.entity.*;
import net.huadong.tech.base.service.CCarTypService;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.tech.ship.entity.Ship;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;
import net.huadong.tech.util.HdUtils;

/**
 * @author
 */
@Component
public class CCarTypServiceImpl implements CCarTypService {
    @Override
    public HdEzuiDatagridData find(HdQuery hdQuery) {

//		String sql=" select \n"
//				+" t1.*, t2.BRAND_NAM, t3.CAR_KIND_NAM, \n"
//				+" (select name from sys_code where code=t1.CAR_FEE_TYP and FIELD_COD='CAR_FEE_TYP') as CAR_FEE_TYP_NAM  ,t4.FACTORY_NAM\n"
//				+"  from C_CAR_TYP t1,C_BRAND t2,C_CAR_KIND t3 ,C_Factory t4\n"
//				+"  where t1.BRAND_COD=t2.BRAND_COD(+) and t1.CAR_KIND=t3.CAR_KIND(+)  and t1.FACTORY_COD=t4.FACTORY_COD(+)\n";
//
//				sql+="  order by t1.BRAND_COD,t1.CAR_KIND,t1.CAR_TYP ";

        String jpql = "select a from CCarTyp a where 1=1 ";
        String carTyp = hdQuery.getStr("carTyp");
        String carTypNam = hdQuery.getStr("carTypNam");
        String brandCod = hdQuery.getStr("brandCod");
        String carKind = hdQuery.getStr("carKind");
        String vinNo = hdQuery.getStr("vinNo");
        String feeFlag = hdQuery.getStr("feeFlag");
        String carTypVinNo="";
        QueryParamLs paramLs = new QueryParamLs();
        if (HdUtils.strNotNull(vinNo)) {
            QueryParamLs vinNoParamLs = new QueryParamLs();
            String vinNoJpql = "select DISTINCT(a) from CCarVin a where  1 = 1 ";
            vinNoJpql += " and a.vinNo like :vinNo";
            vinNoParamLs.addParam("vinNo", "%" + vinNo + "%");
            List<CCarVin> cCarVinList = JpaUtils.findAll(vinNoJpql, vinNoParamLs);
            if (cCarVinList.size() > 0&&cCarVinList.size() <2) {
                carTypVinNo = cCarVinList.get(0).getCarTyp();
                jpql += "and a.carTyp =:carTyp ";
                paramLs.addParam("carTyp", carTypVinNo);
            } else if (cCarVinList.size() > 1) {
                for (int i = 0; i < cCarVinList.size(); i++) {
                    carTypVinNo += ",'" + cCarVinList.get(i).getCarTyp() + "'";
                }
                carTypVinNo = carTypVinNo.substring(1);
                jpql += "and a.carTyp in( " + carTypVinNo + ")";
            }else if (cCarVinList.size()==0) {
                if (HdUtils.strIsNull(carTyp)) {
                    jpql += "and a.carTyp =:carTyp ";
                    paramLs.addParam("carTyp", "0");
                }
            }
        }
        if (HdUtils.strIsNull(carTyp) && HdUtils.strIsNull(carTypNam) && HdUtils.strIsNull(brandCod) && HdUtils.strIsNull(carKind) && HdUtils.strIsNull(vinNo)) {
            jpql += "and a.carTyp =:carTyp ";
            paramLs.addParam("carTyp", "1");
        }
       /* if (HdUtils.strNotNull(carTyp)) {
            jpql += "and a.carTyp =:carTyp ";
            paramLs.addParam("carTyp", carTyp);
        }*/
        if (HdUtils.strNotNull(carTyp)) {
            jpql += "and a.carTyp =:carTyp ";
            paramLs.addParam("carTyp", carTyp);
        }
        if (HdUtils.strNotNull(carTypNam)) {
            jpql += "and a.carTypNam like :carTypNam ";
            paramLs.addParam("carTypNam", "%" + carTypNam + "%");
        }
        if (HdUtils.strNotNull(brandCod)) {
            jpql += "and a.brandCod =:brandCod ";
            paramLs.addParam("brandCod", brandCod);
        }
        if (HdUtils.strNotNull(carKind)) {
            jpql += "and a.carKind =:carKind ";
            paramLs.addParam("carKind", carKind);
        }
        if(HdUtils.strNotNull(feeFlag)){
            jpql += "and ( a.carFeeTypNam  is null  or a.carFeeTyp is null )";
            //paramLs.addParam("carKind", carKind);
        }
        jpql += "order by a.brandCod,a.carKind,a.carTypNam  asc";
        HdEzuiDatagridData result = JpaUtils.findByEz(jpql, paramLs, hdQuery);
        List<CCarTyp> cCarTypList = result.getRows();
        for (CCarTyp ccartyp : cCarTypList) {
            if (HdUtils.strNotNull(ccartyp.getCarKind())) {
                CCarKind cc = JpaUtils.findById(CCarKind.class, ccartyp.getCarKind());
                if (cc != null) {
                    ccartyp.setCarKindNam(cc.getCarKindNam());
                }

            }

            if(HdUtils.strNotNull(ccartyp.getCarFeeTypNam()))
            {
                CCarTypFeeName carTypFeeName = JpaUtils.findById(CCarTypFeeName.class,ccartyp.getCarFeeTypNam());
                if(null != carTypFeeName){
                    ccartyp.setCarFeeTypNamNam(carTypFeeName.getCarTypName());
                }
            }

            if (HdUtils.strNotNull(ccartyp.getFactoryCod())) {
                CFactory cf = JpaUtils.findById(CFactory.class, ccartyp.getFactoryCod());
                if (cf != null) {
                    ccartyp.setFactoryNam(cf.getFactoryNam());
                }
            }
            if (HdUtils.strNotNull(ccartyp.getBrandCod())) {
                CBrand cb = JpaUtils.findById(CBrand.class, ccartyp.getBrandCod());
                if (cb != null) {
                    ccartyp.setBrandNam(cb.getBrandNam());
                }

            }
            if (HdUtils.strNotNull(ccartyp.getCarFeeTyp())) {
                ccartyp.setCarFeeTyp(HdUtils.getSysCodeName("CAR_FEE_TYP", ccartyp.getCarFeeTyp()));
            }
        }
        return result;
    }

    @Override
    public HdMessageCode save(HdEzuiSaveDatagridData<CCarTyp> hdEzuiSaveDatagridData) {
        // TODO Auto-generated method stub
        return JpaUtils.save(hdEzuiSaveDatagridData);
    }

    @Transactional
    public void removeAll(String carTyps) {
        // TODO Auto-generated method stub
        List<String> carTypList = HdUtils.paraseStrs(carTyps);
        for (String carTyp : carTypList) {
            JpaUtils.remove(CCarTyp.class, carTyp);
        }
    }

    @Override
    public CCarTyp findone(String carTyp) {
        // TODO Auto-generated method stub
        CCarTyp cCarTyp = JpaUtils.findById(CCarTyp.class, carTyp);
        return cCarTyp;
    }


    public  HdMessageCode saveoneByCarTyp(String carTyp,String carFeeTypNam,String carFeeTyp){
        CCarTyp cCarTyp=JpaUtils.findById(CCarTyp.class,carTyp);
        cCarTyp.setCarFeeTyp(carFeeTyp);
        cCarTyp.setCarFeeTypNam(carFeeTypNam);
        JpaUtils.update(cCarTyp);
        return  HdUtils.genMsg();

    }

    @Override
    public HdMessageCode saveone(@RequestBody CCarTyp cCarTyp) {
        HdMessageCode hdMessageCode = new HdMessageCode();
        try {
            if ("".equals(cCarTyp.getCarTyp())) {
                String jpqlx = "select max(cast(a.carTyp as int)) as carTyp from CCarTyp a where 1=1";
                List<BigDecimal> carTypList = JpaUtils.findAll(jpqlx, null);
                Integer c = carTypList.get(0).intValue();
                Integer MaxcarTyp = c + 1;
                cCarTyp.setCarTyp(MaxcarTyp.toString());
                JpaUtils.save(cCarTyp);
            } else {
                JpaUtils.update(cCarTyp);
            }
            return HdUtils.genMsg();
        } catch (Exception e) {
            hdMessageCode.setCode("-1");
            hdMessageCode.setMessage(e.getMessage());
            return hdMessageCode;
        }
    }

    @Override
    public HdEzuiDatagridData findBrandCod(HdQuery hdQuery) {
        // TODO Auto-generated method stub
        String jpql = "select a from c_brand a";
        QueryParamLs paramLs = new QueryParamLs();
        return JpaUtils.findByEz(jpql, paramLs, hdQuery);
    }

    @Override
    public HdMessageCode findCCarTyp(String carTyp) {
        if (HdUtils.strNotNull(carTyp)) {
            CCarTyp cCarTyp = JpaUtils.findById(CCarTyp.class, carTyp);
            if (cCarTyp != null) {
                // 主键已存在
                throw new HdRunTimeException("该代码已存在，请重新输入！");
            }
        }
        return HdUtils.genMsg();
    }

    @Override
    public CCarTyp findOne(String brandCod, String carKind) {
        String jpql = "select a from CCarTyp a where 1=1";
        QueryParamLs paramLs = new QueryParamLs();
        if (HdUtils.strNotNull(brandCod)) {
            jpql += " and a.brandCod =:brandCod";
            paramLs.addParam("brandCod", brandCod);
        }
        if (HdUtils.strNotNull(carKind)) {
            jpql += " and a.carKind =:carKind";
            paramLs.addParam("carKind", carKind);
        }
        List<CCarTyp> cCarTypList = JpaUtils.findAll(jpql, paramLs);
        CCarTyp cCarTyp = new CCarTyp();
        if (cCarTypList.size() > 0) {
            cCarTyp = cCarTypList.get(0);
        }
        return cCarTyp;
    }

    @Transactional
    public void checkAll(String carTyps) {
        List<String> carTypList = HdUtils.paraseStrs(carTyps);
        for (String carTyp : carTypList) {
            CCarTyp cCarTyp = JpaUtils.findById(CCarTyp.class, carTyp);
            if (CCarTyp.N.equals(cCarTyp.getCheckFlag()) || cCarTyp.getCheckFlag() == ""
                    || cCarTyp.getCheckFlag() == null) {
                cCarTyp.setCheckFlag(CBrand.Y);
            }
            JpaUtils.update(cCarTyp);
        }
    }
}
