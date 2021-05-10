package net.huadong.tech.map.controller;


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
import net.huadong.tech.base.entity.CCyArea;
import net.huadong.tech.base.service.CCameraService;
import net.huadong.tech.base.service.CCyAreaService;
import net.huadong.tech.map.entity.Feature;
import net.huadong.tech.map.entity.FeatureCollection;
import net.huadong.tech.map.entity.GeoObjectFactory;
import net.huadong.tech.map.entity.GeoObjectFactory.FeatureCreater;
import net.huadong.tech.map.entity.Point;
import net.huadong.tech.map.entity.Polygon;
import net.huadong.tech.map.entity.PortCargoMap;
import net.huadong.tech.map.service.MapFeatureService;
import net.huadong.tech.map.util.GisUtil;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiQueryParams;
import net.huadong.tech.util.HdUtils;
import net.huadong.tech.util.beanUtil;

/**
 * @author 
 */
@Controller
@RequestMapping("webresources/login/map/mapFeature")
public class MapFeatureController  {
	
	@Autowired
	private MapFeatureService mapFeatureService; 
	@Autowired
	private CCameraService cCameraService; 	
	
	@Autowired
	private CCyAreaService cCyAreaService; 	
	
	
	
	/**
     * 保存资源信息
     *
     * @param hdEzuiSaveDatagridData
     * @return
     */

	@RequestMapping("/getMapCamera")
	@ResponseBody
	public HdMessageCode getMapCamera(@RequestBody HdQuery hdQuery) {
		HdMessageCode messageInfo=new HdMessageCode();
    	try {		
    		HdEzuiDatagridData  data =cCameraService.find(hdQuery);
    		List<CCamera> cameraLst=data.getRows();
     		if(cameraLst!=null&&cameraLst.size()>0){
	    		FeatureCollection fc = (FeatureCollection) GeoObjectFactory.create(FeatureCollection.class);
	    			GeoObjectFactory.FeatureCreater f = new FeatureCreater() {
		    			@Override
		    			public Feature create(Map<String, Object> data,FeatureCollection fc, double index) {
		    				// TODO Auto-generated method stub
		    				Feature f = (Feature) GeoObjectFactory.create(Feature.class);
		    				Point poly = (Point) GeoObjectFactory.create(Point.class);
		    				String pos="["+(String) data.get("xpos")+","+(String) data.get("ypos")+"]";
		    				poly.setCoordinate(pos);
		    				f.setProps(data);
		    				f.getProps().put("id", "CAMERA_" + data.get("id"));
		    				f.getProps().remove("xpos");
		    				f.getProps().remove("ypos");
		    				f.setGeometry(poly);
		    				return f;
		    			}
		    		};	    		
		    		for (CCamera cCamera : cameraLst) {		
		    			fc.addFeature(f.create(beanUtil.convertBean(cCamera), fc, Math.random()));
		    		}
		    		messageInfo.setData(fc.toGson());
    		}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return messageInfo;
	}
	@RequestMapping("/getPortCarMap")
	@ResponseBody
	public HdMessageCode getPortCarMap(@RequestBody Map map) throws Exception {
		HdMessageCode messageInfo=null;
    	try {
    		long startTime=System.currentTimeMillis();//记录开始时间
    		 
    		 messageInfo =mapFeatureService.getPortCarMap(map);
     		long endTime=System.currentTimeMillis();//记录结束时间
   		 
     		float excTime=(float)(endTime-startTime)/1000;
     		 
     		System.out.println("执行时间："+excTime+"s");
     		
     		
     		List<PortCargoMap> listPortCar=(List<PortCargoMap>) messageInfo.getData();
     		
    		FeatureCollection fc = (FeatureCollection) GeoObjectFactory.create(FeatureCollection.class);
    		
    		GeoObjectFactory.FeatureCreater f = new FeatureCreater() {
    			@Override
    			public Feature create(Map<String, Object> data,FeatureCollection fc, double index) {
    				// TODO Auto-generated method stub
    				Feature f = (Feature) GeoObjectFactory.create(Feature.class);
    				Polygon poly = (Polygon) GeoObjectFactory.create(Polygon.class);

    				poly.setShipCoordinates((String) data.get("pos"));
    				f.setProps(data);
    				f.getProps().put("id", "CNTR_" + data.get("portCarNo"));
    				f.getProps().put("cargoPop", buildCargoPopup(data));
    				f.setGeometry(poly);
    				return f;
    			}
    		};
			
     		for (PortCargoMap portCargoMap : listPortCar) {
     			fc.addFeature(f.create(beanUtil.convertBean(portCargoMap), fc, Math.random()));
			}
     		messageInfo.setData(fc.toGson());
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return messageInfo;
	}
	
	/**
	 * 检索在场箱
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getPortCarSearchLocal")
	@ResponseBody
	public HdMessageCode getPortCarSearchLocal(@RequestBody Map map) throws Exception {
		HdMessageCode messageInfo=null;
    	try {
    		String  companyCod =(String) map.get("comapnyCod");
    		String dockCod="";
    		if(companyCod.equals("TJG_GZ")) {
    			//滚装
    			dockCod="03406500";

    		}else{
    			//环球
    			dockCod="03409000";
    		}
    		map.put("dockCod", dockCod);
    		long startTime=System.currentTimeMillis();//记录开始时间 
    		 messageInfo =mapFeatureService.getPortCarSearchLocal(map);
     		long endTime=System.currentTimeMillis();//记录结束时间
     		float excTime=(float)(endTime-startTime)/1000; 
     		System.out.println("执行时间："+excTime+"s");
     		List<Map> listPortCar=(List<Map>) messageInfo.getData();
    		FeatureCollection fc = (FeatureCollection) GeoObjectFactory.create(FeatureCollection.class);
    		GeoObjectFactory.FeatureCreater f = new FeatureCreater() {
    			@Override
    			public Feature create(Map<String, Object> data,FeatureCollection fc, double index) {
    				Feature f = (Feature) GeoObjectFactory.create(Feature.class);
    				Polygon poly = (Polygon) GeoObjectFactory.create(Polygon.class);
    				poly.setShipCoordinates((String) data.get("POS"));
    				data.remove("ID");
    				f.setProps(data);
    				f.getProps().put("id", "CARLGT_" + data.get("PORT_CAR_NO"));
    				f.setGeometry(poly);
    				return f;
    			}
    		};
    		if(listPortCar.size()>0) {
	     		for (Map<String,Object> portCargoMap : listPortCar) {
	     			fc.addFeature(f.create(portCargoMap, fc, Math.random()));
				}
	     		messageInfo.setData(fc.toGson());
    		}else {
    			messageInfo.setData("");
    		}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return messageInfo;
	}
	@RequestMapping("/getPortCarLocal")
	@ResponseBody
	public HdMessageCode getPortCarLocal(@RequestBody Map map) throws Exception {
		HdMessageCode messageInfo=null;
    	try {
    		long startTime=System.currentTimeMillis();//记录开始时间
    		 
    		 messageInfo =mapFeatureService.getPortCarLocal(map);
     		long endTime=System.currentTimeMillis();//记录结束时间
     		float excTime=(float)(endTime-startTime)/1000;
     		System.out.println("执行时间："+excTime+"s");
     		List<PortCargoMap> listPortCar=(List<PortCargoMap>) messageInfo.getData();
    		FeatureCollection fc = (FeatureCollection) GeoObjectFactory.create(FeatureCollection.class);
    		GeoObjectFactory.FeatureCreater f = new FeatureCreater() {
    			@Override
    			public Feature create(Map<String, Object> data,FeatureCollection fc, double index) {
    				// TODO Auto-generated method stub
    				Feature f = (Feature) GeoObjectFactory.create(Feature.class);
    				Polygon poly = (Polygon) GeoObjectFactory.create(Polygon.class);

    				poly.setShipCoordinates((String) data.get("pos"));
    				f.setProps(data);
    				f.getProps().put("id", "CAR_" + data.get("portCarNo"));
    				f.getProps().put("cargoPop", buildCargoPopup(data));
    				f.setGeometry(poly);
    				return f;
    			}
    		};
     		for (PortCargoMap portCargoMap : listPortCar) {
     			fc.addFeature(f.create(beanUtil.convertBean(portCargoMap), fc, Math.random()));
			}
     		messageInfo.setData(fc.toGson());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return messageInfo;
	}
	
	private static String buildCargoPopup(Map<String, Object> cargo) {
		StringBuffer sb = new StringBuffer();
		sb.append("<p>船名：<code>"+cargo.get("shipNam")).append("</code></p>")
		  .append("<p>进出口：<code>"+cargo.get("ieNam")).append("</code></p>")
		  .append("<p>内外贸：<code>"+cargo.get("traidNam")).append("</code></p>")
		  .append("<p>车架号：<code>"+(cargo.get("vinNo")==null?"":cargo.get("vinNo"))).append("</code></p>")
		  .append("<p>车类型：<code>"+(cargo.get("carTyp")==null?"":cargo.get("carTyp"))).append("</code></p>")
		  .append("<p>进场：<code>"+(cargo.get("inTime")==null?"":cargo.get("inTime"))).append("</code></p>");
		return sb.toString();
	}
	
	
	
	
	
	/**
     * 查询库场信息
     * @param hdEzuiSaveDatagridData
     * @return
     */

	@RequestMapping("/getCyArea")
	@ResponseBody
	public HdMessageCode getCyArea(@RequestBody HdEzuiQueryParams hdEzuiQueryParams) {
		HdMessageCode messageInfo=new HdMessageCode();
    	try {		
    		//if(hdQuery.getStr("companyCod"))
    		
    		HdEzuiDatagridData  data =cCyAreaService.find(hdEzuiQueryParams);
    		List<CCyArea> cyAreaLst=data.getRows();
     		if(cyAreaLst!=null&&cyAreaLst.size()>0){
	    		FeatureCollection fc = (FeatureCollection) GeoObjectFactory.create(FeatureCollection.class);
	    		
	    		GeoObjectFactory.FeatureCreater f = new FeatureCreater() {
	    			@Override
	    			public Feature create(Map<String, Object> data,FeatureCollection fc, double index) {
	    				// TODO Auto-generated method stub
	    				Feature f = (Feature) GeoObjectFactory.create(Feature.class);
	    				Polygon poly = (Polygon) GeoObjectFactory.create(Polygon.class);
	    
	    				poly.setShipCoordinates(GisUtil.splitAreaPos(data.get("pos")+""));
	    				f.setProps(data);
	    				f.getProps().put("id", "YARDAREA_" + data.get("cyAreaNo"));
	    				f.setGeometry(poly);
	    				return f;
	    			}
	    		};
	    		for (CCyArea cCyArea : cyAreaLst) {
	    			if(HdUtils.strNotNull(cCyArea.getPos())){
	    				fc.addFeature(f.create(beanUtil.convertBean(cCyArea), fc, Math.random()));
	    			}
	    		}
		    	messageInfo.setData(fc.toGson());
    		}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return messageInfo;
	}

	@RequestMapping("/gensubCargo")
	@ResponseBody
	public HdMessageCode gensubCargo(@RequestBody CCyArea cyArea) {
		HdMessageCode messageInfo=mapFeatureService.gensubCargo(cyArea);

		return messageInfo;
	}
	/**
	 * init车辆信息
	 * @param map
	 * @return
	 */
	@RequestMapping("/initPortCarByDock")
	@ResponseBody
	public HdMessageCode initPortCarByDock(@RequestBody Map map) {
		String  companyCod =(String) map.get("comapnyCod");
		String dockCod="";
		if(companyCod.equals("TJG_GZ")) {
			//滚装
			dockCod="03406500";

		}else{
			//环球
			dockCod="03409000";
		}
		return mapFeatureService.initPortCarByDock(dockCod);
				
	}
	/**
	 * 倒运
	 * @param map
	 * @return
	 */
	@RequestMapping("/transPortCar")
	@ResponseBody
	public HdMessageCode transPortCar(@RequestBody Map map) {
		return mapFeatureService.transPortCar(map);
	}
	
	
	
	@RequestMapping("/mulGenPortCarPostion")
	@ResponseBody
	public HdMessageCode mulGenPortCarPostion(@RequestBody Map portCarInfo) {
		return mapFeatureService.mulGenPortCarPostion(portCarInfo);
	}
	
}
