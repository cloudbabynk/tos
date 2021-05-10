package net.huadong.tech.base.service;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.EzTreeBean;
import net.huadong.tech.base.entity.CCarTyp;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiQueryParams;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;

import java.util.List;

/**
 * @author 
 */
public interface CCarTypService {
	HdEzuiDatagridData find(HdQuery hdQuery);
	HdMessageCode save(HdEzuiSaveDatagridData<CCarTyp> gridData);
	void removeAll(String carTyps);
	CCarTyp findone(String carTyp);
	HdMessageCode saveone(CCarTyp cCarTyp);
	HdEzuiDatagridData findBrandCod(HdQuery hdQuery);
	HdMessageCode findCCarTyp(String carTyp);
	CCarTyp findOne(String brandCod, String carKind);
	HdMessageCode saveoneByCarTyp(String carTyp,String carFeeTypNam,String carFeeTyp);
	void checkAll(String carTyps);

}
