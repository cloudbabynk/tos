package net.huadong.tech.base.service.impl;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.entity.CCamera;
import net.huadong.tech.base.service.CCameraService;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;
import net.huadong.tech.util.HdUtils;

/**
 * @author 
 */
@Component
public class CCameraServiceImpl implements CCameraService {
	@Override
	public HdEzuiDatagridData find(HdQuery hdQuery) {
		String jpql="select a from CCamera a where 1=1 ";
		String cameraNam = hdQuery.getStr("cameraNam");
		String cameraId= hdQuery.getStr("cameraId");
		String companyCod = hdQuery.getStr("companyCod");
		String pos = hdQuery.getStr("pos");
		QueryParamLs paramLs = new QueryParamLs();
		
		
		if(HdUtils.strNotNull(cameraId)){
			jpql += "and a.id = :cameraId ";
			paramLs.addParam("cameraId", cameraId);
		}
		if (HdUtils.strNotNull(companyCod)) {
			jpql += "and a.dockCod =:companyCod ";
			paramLs.addParam("dockCod", companyCod);
		}
		
		if(HdUtils.strNotNull(cameraNam)){
			jpql += "and a.cameraNam like :cameraNam ";
			paramLs.addParam("cameraNam", "%"+cameraNam+"%");
		}
		
		if(HdUtils.strNotNull(pos)){
			jpql += " and a.xpos is not null and  a.ypos is not null ";
		}
		HdEzuiDatagridData result = JpaUtils.findByEz(jpql, paramLs, hdQuery);
		return result;
	}
	

	@Override
	public HdMessageCode save(HdEzuiSaveDatagridData<CCamera> hdEzuiSaveDatagridData) {
		// TODO Auto-generated method stub
		return JpaUtils.save(hdEzuiSaveDatagridData);
	}

	@Transactional
	public void removeAll(String carmeraIds) {
		List<String> carmeraList = HdUtils.paraseStrs(carmeraIds);
		for (String carmeraId : carmeraList) {
			JpaUtils.remove(CCamera.class, carmeraId);
		}
	}
	
	@Override
	public CCamera findone(String carmeraId) {
		CCamera CCamera = JpaUtils.findById(CCamera.class, carmeraId);
		return CCamera;

	}
	
	@Override
	public HdMessageCode saveone(@RequestBody CCamera cCamera) {
		String idStr = cCamera.getId();
		CCamera cCameraCk = JpaUtils.findById(CCamera.class, idStr);
		if(cCameraCk != null){
			JpaUtils.update(cCamera);
		}else{
			JpaUtils.save(cCamera);
		}
		return HdUtils.genMsg();
	}

}

