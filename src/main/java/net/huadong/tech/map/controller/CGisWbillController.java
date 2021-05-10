package net.huadong.tech.map.controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.entity.CCamera;
import net.huadong.tech.base.service.CCameraService;
import net.huadong.tech.map.entity.CGisWbill;
import net.huadong.tech.map.entity.CGisWbillCamera;
import net.huadong.tech.map.entity.Feature;
import net.huadong.tech.map.entity.FeatureCollection;
import net.huadong.tech.map.entity.GeoObjectFactory;
import net.huadong.tech.map.entity.GeoObjectFactory.FeatureCreater;
import net.huadong.tech.map.entity.LineString;
import net.huadong.tech.map.entity.Point;
import net.huadong.tech.map.entity.Point2;
import net.huadong.tech.map.service.CGisWbillCameraService;
import net.huadong.tech.map.service.CGisWbillService;
import net.huadong.tech.map.util.CoorUtile;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;
import net.huadong.tech.util.HdUtils;
import net.huadong.tech.util.beanUtil;
/**
 * @author 
 */
@Controller
@RequestMapping("webresources/login/base/CGisWbill")
public class CGisWbillController  {
	
	@Autowired
	private CGisWbillService cgiswbillservice; 	
	
	@Autowired
	private CGisWbillCameraService cgiswbillcameraservice; 
	
	
	@Autowired
	private CCameraService ccameraservice;
	
	
	/**
	 * 批量删除
	 */
	@RequestMapping(value = "/removeAll")
	@ResponseBody
	public HdMessageCode removeAll(String ids) {
		cgiswbillservice.removeAll(ids);
		return HdUtils.genMsg();
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
	    return cgiswbillservice.find(hdQuery);
	}
	/**
     * 通用列表查询
     *
     * @param params
     * @return
     */
	@RequestMapping(value = "/findRelCarmera")
	@ResponseBody
	public HdMessageCode findRelCarmera(@RequestBody CGisWbill 	cgiswbill) {
		HdMessageCode messageCode=HdUtils.genMsg();
		HdQuery hdQuery=new HdQuery();
		HashMap mapPam=new HashMap();
		mapPam.put("wbId", cgiswbill.getWbId());
		hdQuery.setOthers(mapPam);
		HdEzuiDatagridData dataGrad=cgiswbillservice.find(hdQuery);
		List<CGisWbill> cGisWbillList=dataGrad.getRows();	
		FeatureCollection fc = (FeatureCollection) GeoObjectFactory.create(FeatureCollection.class);
		GeoObjectFactory.FeatureCreater f = new FeatureCreater() {
			@Override
			public Feature create(Map<String, Object> data,FeatureCollection fc, double index) {
				// TODO Auto-generated method stub
				Feature f = (Feature) GeoObjectFactory.create(Feature.class);
				
				if(data.get("wType").equals("wkline")) {
					f.getProps().put("id", "WK_" + data.get("wbId"));
					LineString linestring=new LineString();
					linestring.orgnCoordinates(data.get("pos")+"");
					f.getProps().remove("cGisWbillCamera");
					f.setGeometry(linestring);
				}else {
    				Point point = (Point) GeoObjectFactory.create(Point.class);
    				CCamera camera=(CCamera) data.get("cCamera");
    				String pos="["+ camera.getXpos()+","+camera.getYpos()+"]";
    				point.setCoordinate(pos); 				
    				f.getProps().put("id", "WK_" + data.get("camId"));
    				f.getProps().remove("cCamera");
    				f.setGeometry(point);
				}
				f.setProps(data);
				return f;
			}
		};		
		for (CGisWbill cGisWbill : cGisWbillList) {		
			List<CGisWbillCamera> lstCamera= cGisWbill.getcGisWbillCamera();
			Map map=beanUtil.convertBean(cGisWbill);
			map.put("wType", "wkline");
			fc.addFeature(f.create(map, fc, Math.random()));
			for (CGisWbillCamera cgiswbillcamera : lstCamera) {
				Map mapCamrea=beanUtil.convertBean(cgiswbillcamera);
				mapCamrea.put("wType", "wkCamera");
				fc.addFeature(f.create(mapCamrea, fc, Math.random()));
			}
		}	
		messageCode.setData(fc.toGson());
	    return messageCode;
	}

	/**
     * 实体查询
     * @param params
     * @return
     */
	@RequestMapping(value = "/findone")
	@ResponseBody
	public CGisWbill findone(String id) {
		if (HdUtils.strIsNull(id)) {
			CGisWbill cgiswbill = new CGisWbill();
			return cgiswbill;
		}
		return cgiswbillservice.findone(id);
	}
	/**
     * 保存资源信息Cgisd
     *
     * @param hdEzuiSaveDatagridData
     * @return
     */
	@RequestMapping("/save")
	@ResponseBody	
	public HdMessageCode save(@RequestBody HdEzuiSaveDatagridData<CGisWbill> gridData) {
	 	 //CommonUtil.initEntity(gridData);
		return cgiswbillservice.save(gridData);
	}	
	/*
	 * form保存
	 */
	@RequestMapping("/saveone")
	@ResponseBody
	public HdMessageCode saveone(@RequestBody CGisWbill cgiswbill) {
		return cgiswbillservice.saveone(cgiswbill);
	}
	
	/**
     * 保存资源信息
     *
     * @param hdEzuiSaveDatagridData
     * @return
     */
	@RequestMapping("/saveRelCamera")
	@ResponseBody	
	public HdMessageCode saveRelCamera(@RequestBody CGisWbill cgiswbill) {
		HdEzuiSaveDatagridData<CGisWbill> gridData=new HdEzuiSaveDatagridData<>();
		List  rows=new ArrayList();
		rows.add(cgiswbill);
		gridData.setUpdatedRows(rows);
		gridData.setInsertedRows(new ArrayList());
		gridData.setDeletedRows(new ArrayList());
		//保存作业线位置
		cgiswbillservice.save(gridData);
		//关联摄像头
		relRelCarmera(cgiswbill);
		HdMessageCode rtMessage=HdUtils.genMsg();
		return rtMessage;
	}	

	private final double disRang=0.001;
	private void relRelCarmera(CGisWbill cgiswbill){
		HdQuery hdQuery=new HdQuery();
		HashMap mapPam=new HashMap();
		mapPam.put("wbId", cgiswbill.getWbId());
		mapPam.put("pos", "no");
		hdQuery.setOthers(mapPam);
		//获取所有摄像头信息
		HdEzuiDatagridData dataGrad=ccameraservice.find(hdQuery);
		List<CCamera> lstCarmera=dataGrad.getRows();
		//作业线的点维护
		String[] posStr=cgiswbill.getPos().split(";");
		List<Point2> listpt=new ArrayList();
		for (String strpos : posStr) {
			String[] cord=strpos.split(",");
			Point2 pt=new Point2(Double.parseDouble(cord[0]),Double.parseDouble(cord[1]));
			listpt.add(pt);
		}
		List<CGisWbillCamera> lstWbCamera=new ArrayList();
		//维护点
		//2点组成线 	
		for (CCamera ccamera : lstCarmera) {
			//摄像头的点
			Point2 ptcarmera=new Point2(Double.parseDouble(ccamera.getXpos()),Double.parseDouble(ccamera.getYpos()));
			for (int i=0;i<listpt.size()-1;i++) {
				//路径上的点
				Point2 pt1=listpt.get(i);
				Point2 pt2=listpt.get(i+1);
				double distince=CoorUtile.pointToLine(pt1.getX(), pt1.getY(), pt2.getX(), pt2.getY(), ptcarmera.getX(), ptcarmera.getY());
				System.out.println(distince*10000+"---"+10000*disRang);
				if(distince<disRang) {
					CGisWbillCamera cgiswbillcamera=new CGisWbillCamera();
					cgiswbillcamera.setCamId(ccamera.getId());
					cgiswbillcamera.setWbId(cgiswbill.getWbId());
					lstWbCamera.add(cgiswbillcamera);
					break;
				}
			}
		}
		
		//保存摄像头信息
		HdEzuiSaveDatagridData<CGisWbillCamera> gridData=new HdEzuiSaveDatagridData<>();
		gridData.setUpdatedRows(new ArrayList());
		gridData.setInsertedRows(lstWbCamera);
		gridData.setDeletedRows(new ArrayList());
		cgiswbillcameraservice.save(gridData);
		
	}

}
