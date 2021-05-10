package net.huadong.tech.base.service;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.entity.BigBrand;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;
import org.springframework.stereotype.Service;

/**
 * @author
 */
@Service
public interface BigBrandService {
	HdEzuiDatagridData find(HdQuery hdQuery);
	HdMessageCode save(HdEzuiSaveDatagridData<BigBrand> gridData);
	void removeAll(String bigBrandCod);
	void checkAll(String bigBrandCod);
	BigBrand findone(String bigBrandCod);
	HdMessageCode saveone(BigBrand bigBrandCod);
	HdMessageCode findCCarKind(String bigBrandCod);
}
