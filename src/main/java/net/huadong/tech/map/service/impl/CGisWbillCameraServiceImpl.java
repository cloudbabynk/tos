package net.huadong.tech.map.service.impl;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;

import org.eclipse.persistence.config.QueryHints;
import org.eclipse.persistence.config.ResultType;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.tech.map.entity.CGisWbillCamera;
import net.huadong.tech.map.service.CGisWbillCameraService;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;
import net.huadong.tech.util.HdUtils;

/**
 * @author 
 */
@Component
public class CGisWbillCameraServiceImpl implements CGisWbillCameraService {
	@Override
	public HdEzuiDatagridData find(HdQuery hdQuery) {
		String jpql="select a from CGisWbillCamera a where 1=1 ";
		QueryParamLs paramLs = new QueryParamLs();
		if(HdUtils.strNotNull(hdQuery.getStr("wbId"))){
			jpql+=" and  a.wbId=:wbId ";
			paramLs.addParam("wbId",hdQuery.getStr("wbId"));
		}
		jpql += " order by a.sortNum asc";
		HdEzuiDatagridData result = JpaUtils.findByEz(jpql, paramLs, hdQuery);
		return result;
	}
	

	@Override
	public HdMessageCode save(HdEzuiSaveDatagridData<CGisWbillCamera> hdEzuiSaveDatagridData) {
		List<CGisWbillCamera> insertRows=hdEzuiSaveDatagridData.getInsertedRows();
		
		Iterator<CGisWbillCamera> it = insertRows.iterator();
		while(it.hasNext()){
			CGisWbillCamera cgiswbillcamera=it.next();
			String jpql="select a from CGisWbillCamera a where a.wbId=:wbId and a.camId=:camId ";
			QueryParamLs paramLs = new QueryParamLs();
			paramLs.addParam("wbId",cgiswbillcamera.getWbId());
			paramLs.addParam("camId",cgiswbillcamera.getCamId());
			HdEzuiDatagridData result = JpaUtils.findByEz(jpql, paramLs, new HdQuery());
			if(result.getRows().size()==0) {
				cgiswbillcamera.setId(HdUtils.genUuid());
				cgiswbillcamera.setRecNam(HdUtils.getCurUser().getName());
				cgiswbillcamera.setRecTim(HdUtils.getDateTime());
				cgiswbillcamera.setUpdNam(HdUtils.getCurUser().getName());
				cgiswbillcamera.setUpdTim(HdUtils.getDateTime());
			}else {
				insertRows.remove(cgiswbillcamera);
			}
		}
		
		List<CGisWbillCamera> updateRows=hdEzuiSaveDatagridData.getUpdatedRows();
		for (CGisWbillCamera cgiswbillcamera : updateRows) {
			cgiswbillcamera.setUpdNam(HdUtils.getCurUser().getName());
			cgiswbillcamera.setUpdTim(HdUtils.getDateTime());
		}		
		return JpaUtils.save(hdEzuiSaveDatagridData);
	}

	@Transactional
	public void removeAll(String carmeraIds) {
		List<String> carmeraList = HdUtils.paraseStrs(carmeraIds);
		for (String carmeraId : carmeraList) {
			JpaUtils.remove(CGisWbillCamera.class, carmeraId);
		}
	}
	
	@Override
	public CGisWbillCamera findone(String id) {
		CGisWbillCamera cgiswbillcamera = JpaUtils.findById(CGisWbillCamera.class, id);
		return cgiswbillcamera;

	}
	
	@Override
	public HdMessageCode saveone(@RequestBody CGisWbillCamera savecgiswbillcamera) {
		String idStr = savecgiswbillcamera.getId();
		CGisWbillCamera cgiswbillcamera = JpaUtils.findById(CGisWbillCamera.class, idStr);
		if(cgiswbillcamera != null){
			JpaUtils.update(cgiswbillcamera);
		}else{
			JpaUtils.save(cgiswbillcamera);
		}
		return HdUtils.genMsg();
	}


	@Override
	public HdEzuiDatagridData findCamera(HdQuery hdQuery) {
		String jpql = "select a from CCamera a order by a.cameraNam";
		QueryParamLs paramLs = new QueryParamLs();
		HdEzuiDatagridData result = JpaUtils.findByEz(jpql, paramLs, hdQuery);
		return result;
	}


	@Override
	public HdEzuiDatagridData findCGWCamera(HdQuery hdQuery) {
		String wbId = hdQuery.getStr("wbId");
		String sql="select t.id, t.wb_id wbid,t.cam_id camid,cc.camera_nam cameranam,t.sort_num sortnum from c_gis_wbill_camera t , c_camera cc "
				+ "where t.cam_id = cc.id and t.wb_id = '"+wbId+"' order by t.sort_num asc nulls last";
		QueryParamLs paramLs = new QueryParamLs();
		List<Map> cgwCamera =JpaUtils.getEntityManager().createNativeQuery(sql).setHint(QueryHints.RESULT_TYPE, ResultType.Map).getResultList(); 
		HdEzuiDatagridData data = new HdEzuiDatagridData();
		
		data.setRows(cgwCamera);
		return data;
	}

	@Transactional
	@Override
	public HdMessageCode saveRightDialog(HdEzuiSaveDatagridData<Map<String, Object>> saveData) {
		List<Map<String, Object>> insertRows = saveData.getInsertedRows();
		List<Map<String, Object>> updateRows = saveData.getUpdatedRows();
		List<Map<String, Object>> deleteRows = saveData.getDeletedRows();
		
		for(Map<String, Object> ins : insertRows){
			String sql = "insert into c_gis_wbill_camera\n" +
							"    (sort_num, id, wb_id, cam_id, rec_nam, rec_tim, upd_nam, upd_tim)\n" + 
							"values\n" + 
							"    ('"+ins.get("SORTNUM")+"',sys_guid(),'"+ins.get("WBID")+"','"+ins.get("CAMID")+"','"+HdUtils.getCurUser().getName()+"',sysdate,'"+HdUtils.getCurUser().getName()+"',sysdate)";
			JpaUtils.getEntityManager().createNativeQuery(sql).executeUpdate();
		}
		
		for(Map<String, Object> upd : updateRows){
			String sql = "update c_gis_wbill_camera t set t.sort_num = '"+upd.get("SORTNUM")+"' where t.id='"+upd.get("ID")+"'";
			JpaUtils.getEntityManager().createNativeQuery(sql).executeUpdate();
		}
		
		for(Map<String, Object> del : deleteRows){
			String sql = "delete from c_gis_wbill_camera t where t.id='"+del.get("ID")+"'";
			JpaUtils.getEntityManager().createNativeQuery(sql).executeUpdate();
		}
		
		return HdUtils.genMsg();
	}

}

