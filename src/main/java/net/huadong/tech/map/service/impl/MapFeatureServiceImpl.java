package net.huadong.tech.map.service.impl;
import java.awt.geom.Point2D;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.persistence.config.QueryHints;
import org.eclipse.persistence.config.ResultType;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.entity.CCyArea;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.tech.map.entity.ContainerMap;
import net.huadong.tech.map.entity.PortCargoMap;
import net.huadong.tech.map.service.MapFeatureService;
import net.huadong.tech.map.util.GisUtil;
import net.huadong.tech.shipbill.entity.PortCar;
import net.huadong.tech.shipbill.entity.VPortCar;
import net.huadong.tech.task.GisTask;
import net.huadong.tech.util.CommonUtil;
import net.huadong.tech.util.HdUtils;
import net.huadong.tech.util.beanUtil;

/**
 * @author 
 */
@Component
public class MapFeatureServiceImpl implements MapFeatureService {

	@Transactional
	@SuppressWarnings("unchecked")
	@Override
	public HdMessageCode gensubCargo(CCyArea cyArea) {
		// TODO Auto-generated method stub
		HdMessageCode mess=new HdMessageCode();
		//判断行列是否大于0
		if(cyArea.getRowNum().compareTo(BigDecimal.ZERO)==1&&cyArea.getBayNum().compareTo(BigDecimal.ZERO)==1) {
			try {
				generCargoPos(cyArea);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				mess.setMessage(e.getMessage());
				e.printStackTrace();
				
			}
		}
		return mess;
	}
	//堆场的长,宽,间距  0.0001 是缩进比例  3.8 2 ，0 是 长和高的比例  0不管 目前
	private String customSize="0.4,0.8,0";
	private double radioBl=0.0001;
	/**
	 * 支持累计宽度和高度的货垛加载
	 * @param mapPos
	 * @param sunMap
	 * @throws Exception
	 */

	public void generCargoPos(CCyArea cyArea) throws Exception {
			String dockCod=cyArea.getDockCod();
			String LabYard="";
			String LabBay="";
			String LabRow="";
			String container="";
			if(dockCod.equals("03409000")) {   
				//环球
				LabYard="LABYARD";
				LabBay="LABBAY";
				LabRow="LABROW";
				container="CONTAINER";
			}else if(dockCod.equals("03406500")){ 
				//滚装 
				LabYard="GZ_LABYARD";
				LabBay="GZ_LABBAY";
				LabRow="GZ_LABROW";
				container="GZ_CONTAINER";
			}
		    //默认0 规则堆场
			String bufferMark="0";
			//普通堆场
			if(bufferMark.equals("0")){
			  	DecimalFormat df = new DecimalFormat("#.00000000");  
	    		// 排的点
	    		String yardPos =  cyArea.getPos();
	    		String[] yardPosArr = yardPos.split(",");
	    		double xpos, ypos;
	    		Point2D.Double pt;
	    		Map<String, Point2D.Double> mapP = new HashMap<String, Point2D.Double>();
	    		for (int i = 0; i < yardPosArr.length; i+=2) {    			
	    			xpos = Double.parseDouble(yardPosArr[i]);
	    			ypos = Double.parseDouble(yardPosArr[i+1]);
	    			pt = new Point2D.Double(xpos, ypos);
	    			mapP.put(i + "", pt);
	    		}
	    		// 移除重复点
	    		mapP = GisUtil.RemoveDupMap(mapP);
	    		// 排序点
	    		Map<String, Point2D.Double> sortMap =GisUtil.ListArrPt(mapP);
	    		
	    		
	    		if (sortMap.keySet().size() >= 4) {
	    			
	    			// 排序后的4个点
	    			/**
	    			 * 所有点排序后 pt1,pt2,pt3,pt4
	    			 *  3___4
	    			 *  |   |
	    			 *  |   |
	    			 * 1|___|2
	    			 */
	    			Point2D.Double pt1 = sortMap.get("0");//初始点
	
	    			//几行 几贝
	    			double scalebayNum=cyArea.getBayNum().doubleValue();
	    			double scalerowNum=cyArea.getRowNum().doubleValue();
	    			//贝和行间距
	    			double disBay=0;
	    			double disRow=0;   			
	    			//每个货垛的长度和宽度
	    			String[] strArr = customSize.split(",");
	
	    			double cargoWdt=Double.parseDouble(strArr[0])*radioBl;
	    			double cargoHgt=Double.parseDouble(strArr[1])*radioBl;
	    			double disPam=Double.parseDouble(strArr[2])*radioBl;//间距系数
	    			
	    			if(scalebayNum>0&&scalerowNum>0)
	    			{    						
	    				//定位的贝的 4个点
	    				Point2D.Double posBayPt1;
	    				//行的4个位置点
	    				Point2D.Double posRowPt1=null, posRowPt2=null, posRowPt3=null, posRowPt4=null;
	    				String POS="";
	    				
	    				String bayNo="",rowNo="",delSql="",optSql="",labSql="";
	    				double labYardx=0,labBayx=0,labRowx=0,labYardy=0,labBayy=0,labRowy=0;
	
	    				
	    				delSql="DELETE  "+container+" WHERE  BLOCKNO='"+cyArea.getCyAreaNo()+"'   and COMPANYCOD='"+cyArea.getDockCod()+"'";
	    				JpaUtils.getEntityManager().createNativeQuery(delSql).executeUpdate();
	    				//删除库场
	    				labSql="delete "+LabYard+" where  CODE='"+cyArea.getCyAreaNo()+"' and COMPANYCOD='"+cyArea.getDockCod()+"'";
	    				JpaUtils.getEntityManager().createNativeQuery(labSql).executeUpdate();
	    				//删除排
	    				labSql="delete "+LabRow+" where   BLOCKNO='"+cyArea.getCyAreaNo()+"' and COMPANYCOD='"+cyArea.getDockCod()+"'";
	    				JpaUtils.getEntityManager().createNativeQuery(labSql).executeUpdate();
	    				//删除排
	    				labSql="delete "+LabBay+" where  BLOCKNO='"+cyArea.getCyAreaNo()+"'  and  COMPANYCOD='"+cyArea.getDockCod()+"'";
	    				JpaUtils.getEntityManager().createNativeQuery(labSql).executeUpdate();
	    				
	    				
	    				
	    		
	    				//循环贝
	    				for (int i = 0; i < scalebayNum; i++) {
	    					bayNo=String.format("%02d",i+1);//获取贝号	    					
	    					posBayPt1=new Point2D.Double(pt1.getX(),pt1.getY()) ;
			
	    					for (int j = 0; j < scalerowNum; j++) {
	    						POS="";//初始化
	    						rowNo=String.format("%02d",j+1);//获取行号
	    						
	    						posRowPt1=new Point2D.Double(posBayPt1.getX()+disBay,posBayPt1.getY()+disRow);
	    						posRowPt2=new Point2D.Double(posBayPt1.getX()+(disBay+cargoWdt),posBayPt1.getY()+disRow);
	    						posRowPt3=new Point2D.Double(posBayPt1.getX()+(disBay+cargoWdt),posBayPt1.getY()+(disRow+cargoHgt));
	    						posRowPt4=new Point2D.Double(posBayPt1.getX()+disBay,posBayPt1.getY()+(disRow+cargoHgt));
	    						    						
	    						POS=df.format( posRowPt1.getX())+","+df.format(posRowPt1.getY())+";"+df.format(posRowPt2.getX())+","+df.format(posRowPt2.getY())+";"+
	    						df.format(posRowPt3.getX())+","+df.format(posRowPt3.getY())+";"+df.format(posRowPt4.getX())+","+df.format(posRowPt4.getY());
	
	    					
	    						
	    						optSql="INSERT INTO "+container+" (ID,CODE, NAME,BAYNO, ROWNO,BLOCKNO,COMPANYCOD, POS, GEOM) "+
									" VALUES (0,sys_guid(),'"+cyArea.getCyAreaNo()+"', '"+bayNo+"','"+rowNo+"', '"+cyArea.getCyAreaNo()+"', '"+cyArea.getDockCod()+"','"+POS+"', "
									+" SDO_GEOMETRY(2003, null,NULL, SDO_ELEM_INFO_ARRAY(1,1003,1), SDO_ORDINATE_ARRAY("+POS.replaceAll(";", ",")+")))";
	    						
	    						
	    						JpaUtils.getEntityManager().createNativeQuery(optSql).executeUpdate();
	    						
	    						//每次循环行  Y坐标递增 X不变
	    						posBayPt1.setLocation(posBayPt1.getX(), posRowPt3.getY());
	    						
	    						//库场标签的位置
	    						if(i==0&&j==(scalerowNum-1))
	    						{
	    							labYardx=posRowPt4.getX();
	    							labYardy=posRowPt4.getY();
	    							

	    		    				//新添加库存
	    		    				labSql="INSERT INTO "+LabYard+" (CODE, NAME, COMPANYCOD,GEOM) "+
	    								" VALUES ('"+cyArea.getCyAreaNo()+"', '"+cyArea.getCyAreaNo()+"','"+cyArea.getDockCod()+"', "+
	    								" MDSYS.SDO_GEOMETRY (  2001, NULL,  MDSYS.SDO_POINT_TYPE ("+labYardx+","+labYardy+", 0),  NULL,NULL))";	
	    		    				JpaUtils.getEntityManager().createNativeQuery(labSql).executeUpdate();
	    						}
	    						//排标签
	    						if(i==0)
	    						{
	    							labRowx=posRowPt4.getX();
	    							labRowy=(posRowPt1.getY()+posRowPt4.getY())/2;
	    		    				//新添加排
	    		    				labSql="INSERT INTO  "+LabRow+" (BLOCKNO,CODE, NAME, COMPANYCOD,GEOM) "+
	    								" VALUES ('"+cyArea.getCyAreaNo()+"','"+rowNo+"', '"+rowNo+"','"+cyArea.getDockCod()+"', "+
	    								" MDSYS.SDO_GEOMETRY (  2001, NULL,  MDSYS.SDO_POINT_TYPE ("+labRowx+","+labRowy+", 0),  NULL,NULL))";
	    		    				JpaUtils.getEntityManager().createNativeQuery(labSql).executeUpdate();
	    						}
	    						//贝标签
	    						if(j==(scalerowNum-1))
	    						{
	    							labBayx=(posRowPt3.getX()+posRowPt4.getX())/2;
	    							labBayy=posRowPt4.getY();

	    		    				//新添加排
	    		    				labSql="INSERT INTO "+LabBay+" (NAME,COMPANYCOD,CODE,ROWNO,BLOCKNO,GEOM) "+
	    								" VALUES ('"+bayNo+"', '"+cyArea.getDockCod()+"','"+bayNo+"','"+rowNo+"','"+cyArea.getCyAreaNo()+"', "+
	    								" MDSYS.SDO_GEOMETRY (  2001, NULL,  MDSYS.SDO_POINT_TYPE ("+labBayx+","+labBayy+", 0),  NULL,NULL))";
	    		    				JpaUtils.getEntityManager().createNativeQuery(labSql).executeUpdate();
	    		    				
	    						}
							}
	    					//每次循环贝  X坐标递增 Y不变
	    					pt1.setLocation(posBayPt1.getX()+(disBay+cargoWdt), pt1.getY());
	    				}	    				
	    			}
	    		}
			}else{//特殊堆场处理
				String labSql=null;
//				labSql="delete LABBAY where  BLOCKNO='"+cyArea.getCyAreaNo()+"' and ROWNO='"+cyArea.getCyAreaNo()+"' and CODE='"+cyArea.getCyAreaNo()+"' COMPANYCOD='"+cyArea.getDockCod()+"'";
//				JpaUtils.getEntityManager().createNativeQuery(labSql).executeUpdate();
				
				String[] posArr=cyArea.getPos().split(";");
				double centX=0,centY=0;
				for (int i=0;i< posArr.length;i++) {
					centX+=Double.parseDouble(posArr[i].split(",")[0]);
					centY+=Double.parseDouble(posArr[i].split(",")[1]);
				}
				centX=new BigDecimal(centX/posArr.length).setScale(8,BigDecimal.ROUND_HALF_UP).doubleValue()+0.00005;
				centY=new BigDecimal(centY/posArr.length).setScale(8,BigDecimal.ROUND_HALF_UP).doubleValue()-0.00002;
				
				//新添贝 标签  特殊堆场 贝和block 是一样的

				//删除库场
				labSql="delete "+container+" where COMPANYCOD='"+cyArea.getDockCod()+"' and CODE='"+cyArea.getCyAreaNo()+"'";
				JpaUtils.getEntityManager().createNativeQuery(labSql).executeUpdate();
				//新添加库存
				labSql="INSERT INTO "+container+" (CODE, NAME, COMPANYCOD,GEOM) "+
					" VALUES (sys_guid(), '"+cyArea.getCyAreaNo()+"','"+cyArea.getDockCod()+"', "+
					" MDSYS.SDO_GEOMETRY (  2001, NULL,  MDSYS.SDO_POINT_TYPE ("+centX+","+centY+", 0),  NULL,NULL))";
				JpaUtils.getEntityManager().createNativeQuery(labSql).executeUpdate();
							
				String optSql=null;
				
				optSql="DELETE  "+LabBay+" WHERE COMPANYCOD='"+cyArea.getDockCod()+"'"
						+ "	 AND BLOCKNO='"+cyArea.getCyAreaNo()+"' AND BAYNO='00' AND ROWNO='00' ";
				JpaUtils.getEntityManager().createNativeQuery(optSql).executeUpdate();
				
				optSql="INSERT INTO "+container+" (ID,CODE, NAME,BAYNO, ROWNO,BLOCKNO,COMPANYCOD, POS, GEOM) "+
						" VALUES (0,sys_guid(),'"+cyArea.getCyAreaNo()+"', '00','00', '"+cyArea.getCyAreaNo()+"', '"+cyArea.getDockCod()+"','"+cyArea.getPos()+"', "
						+" SDO_GEOMETRY(2003, null,NULL, SDO_ELEM_INFO_ARRAY(1,1003,1), SDO_ORDINATE_ARRAY("+cyArea.getPos().replaceAll(";", ",")+")))";
				JpaUtils.getEntityManager().createNativeQuery(optSql).executeUpdate();
			}		
	}
	@Override
	public HdMessageCode getPortCarMap(Map cntrMap) {
		HdMessageCode message=HdUtils.genMsg();
		String  companyCod =(String) cntrMap.get("comapnyCod");
		String dockCod="";
		 List<Map> portCarlst=null;
		if(companyCod.equals("TJG_GZ")) {
			//滚装
			dockCod="03406500";
			if(GisTask.getGzMapPortCar().size()==0)GisTask.getPortCarList(dockCod);
			portCarlst=GisTask.getGzPortCarlst();
		}else{
			//环球
			dockCod="03409000";
			if(GisTask.getHqMapPortCar().size()==0)GisTask.getPortCarList(dockCod);
			portCarlst=GisTask.getHqPortCarLst();
		}
		 List<PortCargoMap> listPortCar=new ArrayList<PortCargoMap>();
		 for (Map map : portCarlst) {	
			 PortCargoMap  portCargoMap=new PortCargoMap( map.get("COLOUR_SET")+"", map.get("BLOCKNO")+"",   map.get("ROWNO")+"",  map.get("BAYNO")+"", 
						map.get("PORT_CAR_NO")+"",map.get("COMPANYCOD")+"",map.get("POS")+"","",
						map.get("I_E_NAM")==null?"":map.get("I_E_NAM").toString(),
						map.get("TRADE_NAM")==null?"":map.get("TRADE_NAM").toString(),
						map.get("CAR_TYP_NAM")==null?"":map.get("CAR_TYP_NAM").toString(),
						map.get("C_SHIP_NAM")==null?"":map.get("C_SHIP_NAM").toString(),
						map.get("BILL_NO")==null?"":map.get("BILL_NO").toString(),
						map.get("VIN_NO")==null?"":map.get("VIN_NO").toString()
				);
			 
			portCargoMap.setInTime(map.get("IN_CY_TIM")==null?"":map.get("IN_CY_TIM").toString());
			listPortCar.add(portCargoMap);
		 }
		message.setData(listPortCar);		
		return message;
	}


    //查询车辆所在位置
	@Override
	public HdMessageCode getPortCarLocal(Map cntrMap) throws Exception {
		
		String  companyCod =(String) cntrMap.get("comapnyCod");
		String  billNo =(String) cntrMap.get("billNo");
		String dockCod="";
		String containerTab="";
		if(companyCod.equals("TJG_GZ")) {
			//滚装
			dockCod="03406500";
			containerTab="GZ_CONTAINER";
		}else{
			//环球
			dockCod="03409000";
			containerTab="CONTAINER";
		}
		HdMessageCode message=HdUtils.genMsg();
		String key=null;
		ContainerMap container=null;
		HdQuery hdQuery=new HdQuery();
		String jpql="select a from CCyArea a where a.dockCod=:dockCod";
		QueryParamLs queryParamLs=new QueryParamLs();
		queryParamLs.addParam("dockCod",dockCod);
		List<CCyArea> cCyAreaList = JpaUtils.findAll(jpql, queryParamLs);	
		LinkedHashMap<String,Double> areaMap=new LinkedHashMap();
		for (CCyArea cCyArea : cCyAreaList) {
			areaMap.put(cCyArea.getCyAreaNo()+"_BAY", cCyArea.getBayNum().doubleValue());
			areaMap.put(cCyArea.getCyAreaNo()+"_ROW", cCyArea.getRowNum().doubleValue());	
		}
		//所有的位置
		String sqlContainer=" SELECT /*+ USE_HASH(T1,T) ORDERED */ t1.port_car_no, t1.vin_no, t1.bill_no, "+ 
				" (SELECT car_typ_nam FROM c_car_typ m WHERE m.car_typ = t1.car_typ) car_typ, "+
				" (SELECT brand_nam FROM c_brand m WHERE m.brand_cod = t1.brand_cod) brand_cod, "+
				" t.blockno, t.rowno, t.bayno, t.companycod, t.pos "+
				" FROM "+containerTab+" t, port_car t1 "+
				" WHERE t.blockno = t1.cy_area_no (+) AND t.rowno = t1.cy_row_no (+) AND t.bayno = t1.cy_bay_no (+) AND t.COMPANYCOD='"+dockCod+"' "+
				" ORDER BY t.blockno, t.rowno, t.bayno ";
		List<Map> lstContainer=JpaUtils.getEntityManager().createNativeQuery(sqlContainer).setHint(QueryHints.RESULT_TYPE, ResultType.Map).getResultList(); 
  		 
 		 
		LinkedHashMap<String,ContainerMap> containMap=new LinkedHashMap<String,ContainerMap>();
		String txt=null;
		for (Map map : lstContainer) {
			key=map.get("BLOCKNO")+"_"+map.get("ROWNO")+"_"+map.get("BAYNO");
			txt=(map.get("VIN_NO")==null?"": (map.get("VIN_NO")+"\\r\\n"))
					+(map.get("BILL_NO")==null?"":(map.get("BILL_NO")+"\\r\\n"))
					+(map.get("CAR_TYP")==null?"": (map.get("CAR_TYP")+"\\r\\n"))
					+(map.get("BRAND_COD")==null?"":( map.get("BRAND_COD")+""));
			container=new ContainerMap(map.get("PORT_CAR_NO")+"", map.get("BLOCKNO")+"", map.get("ROWNO")+"", map.get("BAYNO")+"", map.get("COMPANYCOD")+"", map.get("POS")+"",txt,(map.get("VIN_NO")==null?"": (map.get("VIN_NO")+"")));
			containMap.put(key, container);
		}
		String sqlPortCar="  SELECT t.port_car_no, T.BILL_NO," +
				"         t.cy_area_no, t.cy_row_no,    t.cy_bay_no,   T.CAR_TYP, " +
				"         p.CAR_TYP_NAM,  s.name I_E_NAM,   T.I_E_ID,  T.TRADE_ID, " +
				"         c.name TRADE_NAM,  '123' C_SHIP_NAM,T.TRADE_ID || T.I_E_ID colour_set " +
				"    FROM port_car t, " +
				"         (SELECT *  FROM SYS_CODE   WHERE FIELD_COD = 'I_E_ID') S, " +
				"         (SELECT *  FROM SYS_CODE   WHERE FIELD_COD = 'TRADE_ID') C,  C_CAR_TYP p, " +
				"   WHERE     T.I_E_ID = s.CODE " +
				"         AND T.TRADE_ID = c.code " +
				"         AND T.CAR_TYP = p.CAR_TYP " +
				"         AND INSTR (t.cy_area_no, '#') = 0 ";
		if (CommonUtil.strNotNull(billNo)) {
			sqlPortCar+=" AND T.BILL_NO like '%"+billNo+"%'";
		}

		sqlPortCar+="ORDER BY t.cy_area_no, t.cy_row_no, t.cy_bay_no " ;
		String area=null,row=null,bay=null;
		List<Map> lstPortCar=JpaUtils.getEntityManager().createNativeQuery(sqlPortCar).setHint(QueryHints.RESULT_TYPE, ResultType.Map).getResultList();
  		 
 		 
		List<PortCargoMap> listPortCar=new ArrayList<PortCargoMap>();
		PortCargoMap portCargoMap=null;
			
		int i=0;
		for (Map map : lstPortCar) {	
			if(map.containsKey(("CY_AREA_NO")) && areaMap.containsKey(map.get("CY_AREA_NO")+"_ROW")) {
				int rowNum=areaMap.get(map.get("CY_AREA_NO")+"_ROW").intValue();
				int bayNum=areaMap.get(map.get("CY_AREA_NO")+"_BAY").intValue();	
				if(bayNum==0) continue;
				
				if(area==null||!area.equals(map.get("CY_AREA_NO")+"")) {
					i=0;
				}
					area=map.get("CY_AREA_NO")+"";	
				//获取行号
				if((map.get("CY_ROW_NO")+"").indexOf("#")>=0) {
					row=String.format("%02d", (i%rowNum)+1);
	
				}else {
					row=map.get("CY_ROW_NO")+"";
				}
				//获取贝号
				if((map.get("CY_BAY_NO")+"").indexOf("#")>=0) {
					bay=String.format("%02d", (i/rowNum)+1);
				}else {
					bay=map.get("CY_BAY_NO")+"";
				}
				//对应库场的ID
				key=area+"_"+row+"_"+bay;
				if(containMap.containsKey(key)) {
					container=containMap.get(key);
					
					portCargoMap=new PortCargoMap(container.getAreaNo(), container.getRowNo(), container.getBayNo(), 
							map.get("PORT_CAR_NO")+"", container.getCompanyCod(), container.getPos(),
							container.getTxt(),map.get("I_E_NAM").toString(),map.get("TRADE_NAM").toString(),
							map.get("CAR_TYP_NAM").toString(),map.get("C_SHIP_NAM")==null?"":map.get("C_SHIP_NAM").toString(),
									map.get("BILL_NO")==null?"":map.get("BILL_NO").toString(),container.getVinNo()==null?"":container.getVinNo());
					listPortCar.add(portCargoMap);
					i++;
				}
			}
			
		}
		message.setData(listPortCar);		
		return message;
	}


	@Override
	public HdMessageCode getPortCarSearchLocal(Map cntrMap) throws Exception {
		HdMessageCode message=HdUtils.genMsg();
		String sqlPam="";
		String dockCod=cntrMap.get("dockCod")+"";
     	String tabName="";
     	if(dockCod.equals("03406500")) {
     	  tabName="GZ_CONTAINER";
		}else {
		  tabName="CONTAINER";
		}
     	sqlPam=" AND (t.bill_no,t.ship_no,t.brand_cod,t.cy_area_no,t.car_kind, t.car_typ,   t.i_e_id,  t.trade_id)     IN  (";
     	List<Map> lstCargo=(List<Map>) cntrMap.get("ckRows");
     	int i=0;
     	for (Map map : lstCargo) {
			if(i==0) {
				sqlPam+=" select '"+map.get("BILL_NO")+"','"+map.get("SNO")+"','"+map.get("BRAND_COD")+"','"+map.get("CY_AREA_NO")+"','"+map.get("CAR_KIND")+"'"
						+ ",'"+map.get("CTP")+"','"+map.get("IED")+"','"+map.get("TID")+"' from dual ";
			}else {
				sqlPam+=" union all select '"+map.get("BILL_NO")+"','"+map.get("SNO")+"','"+map.get("BRAND_COD")+"','"+map.get("CY_AREA_NO")+"','"+map.get("CAR_KIND")+"'"
						+ ",'"+map.get("CTP")+"','"+map.get("IED")+"','"+map.get("TID")+"' from dual ";
			}
			i++;
		}
     	sqlPam+=" ) ";
     	
 		String sqlPortCar="SELECT /*+ USE_HASH(H,T1,T) */ t.port_car_no,  t.bill_no,  t.cy_area_no,  t.cy_row_no, t.cy_bay_no,    t.car_typ,  t.i_e_id,  t.trade_id,   h.c_ship_nam,  t1.pos,   '' id "+
 				" FROM port_car t, "+tabName+" t1, ship h "+
 				" WHERE t.ship_no = h.ship_no (+)   AND t.cy_area_no = t1.blockno    AND t.cy_bay_no = t1.bayno    AND t.cy_row_no = t1.rowno   AND t.dock_cod='"+dockCod+"' "+sqlPam+
 				" AND t.current_stat = '2' AND INSTR(t.cy_area_no, '#') = 0 "+
 				" ORDER BY t.cy_area_no, t.cy_row_no, t.cy_bay_no";
 		List<Map> lstPortCar=JpaUtils.getEntityManager().createNativeQuery(sqlPortCar).setHint(QueryHints.RESULT_TYPE, ResultType.Map).getResultList();
 		message.setData(lstPortCar);	
		return message;
	}
	@Transactional
	@Override
	public HdMessageCode initPortCarByDock(String dockCod) {
		HdMessageCode message=HdUtils.genMsg();		
		String area=null,row=null,bay=null;
		//获取在场车辆信息
		List<Map> lstPortCar=getPortCar(dockCod,null);

		//没有库场的位置  位置没有被占了
		Map anAreaMap=getContain(dockCod,null,null);
		int i=0;
		for (Map map : lstPortCar) {
			if(area==null||!area.equals(map.get("CY_AREA_NO")+"")) {
				i=0;
			}
			area=map.get("CY_AREA_NO")+"";
			
			if(anAreaMap.containsKey(area)) {
				List<Map> lstArea=(List<Map>) anAreaMap.get(area);
				if(i<lstArea.size()) {
					row=lstArea.get(i).get("CY_ROW_NO")+"";
					bay=lstArea.get(i).get("CY_BAY_NO")+"";
					String upSql="update port_car set CY_ROW_NO='"+row+"' , CY_BAY_NO='"+bay+"',CONTACT_INFO='t' where PORT_CAR_NO='"+map.get("PORT_CAR_NO")+"'";
					JpaUtils.getEntityManager().createNativeQuery(upSql).executeUpdate();
					i++;
				}
			}			
		}
		return message;
	}
	private void getLocKey(String dockCod,String area,String row,String bay,int rowNum,int bayNum,Map rtMap) {
		String sqlext="select count(1) num from port_car where DOCK_COD='"+dockCod+"' and CY_AREA_NO='"+area+"' and CY_ROW_NO='"+row+"' and  CY_BAY_NO='"+bay+"' and CURRENT_STAT='2'";
		List<Map>  lstNum = JpaUtils.getEntityManager().createNativeQuery(sqlext).setHint(QueryHints.RESULT_TYPE, ResultType.Map).getResultList();
		int boardIndex=(int) rtMap.get("boardIndex");
		String nextLoc=area+"_"+row+"_"+bay;
		if(Integer.parseInt(lstNum.get(0).get("NUM")+"")>0) {
			boardIndex++;
			nextLoc=countRowAndBay(area, row, bay, rowNum, bayNum);
			//库场已经满了
			if(nextLoc.equals("00")) {
				rtMap.put("code", "full");
			}else {
				String[] locArr=nextLoc.split("_");
				rtMap.put("code", "suc");
				getLocKey( dockCod, locArr[0], locArr[1], locArr[2], rowNum, bayNum,rtMap);
			}
		}else {
			rtMap.put("code", "suc1");
		}
		rtMap.put("boardIndex", boardIndex);
		rtMap.put("key", nextLoc);
	}
	//计算行列
	private String countRowAndBay(String area, String row, String bay, int rowNum, int bayNum) {
		String nextLoc = null;
		//贝不变
		int nextBay=Integer.parseInt(bay);
		//行+1
		int nextRow=Integer.parseInt(row)+1;
		//如果贝号大于总贝数
		if(nextRow>rowNum) {
			//换行=01
			nextRow=1;
			//贝+1
			nextBay+=1;
			//如果贝号大于总贝
			if(nextBay>bayNum) {
				nextLoc= "00";
			}else {
				nextLoc=area+"_"+String.format("%02d",nextRow)+"_"+String.format("%02d",nextBay);
			}
		}else {
			nextLoc=area+"_"+String.format("%02d",nextRow)+"_"+String.format("%02d",nextBay);
		}
		return nextLoc;
	}
	
    /**
     * 初始化库存的使用情况
     * 加载库存结构
     */
	private  List<Map> initPortArea(String dockCod) {
    	System.out.println("init Area ing.....");
    	String tabName="";
   	 	if(dockCod.equals("03406500")) {
   		 tabName="GZ_CONTAINER";
		 }else {
			 tabName="CONTAINER";
		 }
    	//滚装
		String sqlContainer=" SELECT /*+ USE_HASH(T1,T) ORDERED */ t1.port_car_no, t1.vin_no, t1.bill_no, "+ 
				" (SELECT car_typ_nam FROM c_car_typ m WHERE m.car_typ = t1.car_typ) car_typ, "+
				" (SELECT brand_nam FROM c_brand m WHERE m.brand_cod = t1.brand_cod) brand_cod, "+
				" t.blockno, t.rowno, t.bayno, t.companycod, t.pos,t1.IN_CY_TIM "+
				" FROM "+tabName+" t, port_car t1 "+
				" WHERE t.blockno = t1.cy_area_no (+) "
				+ " AND t.rowno = t1.cy_row_no (+) "
				+ " AND t.bayno = t1.cy_bay_no (+) "
				+ " AND t.COMPANYCOD='"+dockCod+"' "+
				" ORDER BY t.blockno, t.rowno, t.bayno ";
		return JpaUtils.getEntityManager().createNativeQuery(sqlContainer).setHint(QueryHints.RESULT_TYPE, ResultType.Map).getResultList(); 

    }
     
     private  List<Map> getPortCar(String dockCod,String areaNo) {
     	String tabName="";
     	if(dockCod.equals("03406500")) {
     		tabName="GZ_CONTAINER";
		}else {
			tabName="CONTAINER";
		}
     	String sqlcon="";
     	if(areaNo!=null) {
     		sqlcon+=" and  t.cy_area_no='"+areaNo+"'";
     	}
 		String sqlPortCar=" SELECT /*+ ORDERED */ t.port_car_no,                                                                                 "+
 				" t.bill_no, t.cy_area_no, t.cy_row_no, t.cy_bay_no, "+
 				" t.car_typ, p.car_typ_nam, "+
 				" s.name i_e_nam, t.i_e_id, t.trade_id, c.name trade_nam, h.c_ship_nam, t.trade_id || t.i_e_id colour_set "+
 				" FROM port_car t, "+
 				" (SELECT companycod, blockno FROM "+tabName+" GROUP BY companycod, blockno) t1, "+
 				" (SELECT code, name FROM sys_code SYS_CODE1 WHERE field_cod = 'I_E_ID') s, "+
 				" (SELECT code, name FROM sys_code SYS_CODE2 WHERE field_cod = 'TRADE_ID') c, "+
 				" c_car_typ p, ship h "+
 				" WHERE t.ship_no = h.ship_no (+) "+
 				" AND t.i_e_id = s.code AND t.trade_id = c.code "+
 				" AND t.car_typ = p.car_typ AND t.dock_cod = t1.companycod AND t.cy_area_no = t1.blockno "+
 				" AND t.dock_cod = '"+dockCod+"' "+sqlcon+" AND  (t.cy_row_no = '###'  or t.cy_bay_no = '###')"+
 				" AND t.current_stat = '2' AND INSTR(t.cy_area_no, '#') = 0 "+
 				" ORDER BY t.cy_area_no,t.cy_bay_no, t.cy_row_no ";
 		List<Map> lstPortCar=JpaUtils.getEntityManager().createNativeQuery(sqlPortCar).setHint(QueryHints.RESULT_TYPE, ResultType.Map).getResultList();
 		return lstPortCar;
     }
     
     private Map getContain(String dockCod,String areaNo,String rowNo) {
      	String tabName="";
      	 if(dockCod.equals("03406500")) {
      			tabName="GZ_CONTAINER";
 		 }else {
 			 tabName="CONTAINER";
 		 }
      	 
      	 String sqlPam="";
      	if(areaNo!=null) sqlPam+= " and   C.BLOCKNO='"+areaNo+"' ";   	
      	if(rowNo!=null)  sqlPam+= "  and C.ROWNO>='"+rowNo+"'  ";
      	 
  		String sqlPortCar=  " select c.BLOCKNO CY_AREA_NO,c.ROWNO CY_ROW_NO,c.BAYNO CY_BAY_NO from  "+tabName+" c where " + 
			  				"      not exists (select 1 from c_cy_bay t1 where t1.cy_area_no = c.BLOCKNO and t1.cy_row_no = c.ROWNO and t1.cy_bay_no = c.BAYNO and t1.lock_id = '1') " + 
			  				"  and not exists ( " + 
			  				"  SELECT  p.cy_area_no,p.CY_ROW_NO,p.CY_BAY_NO  FROM port_car p where  c.BLOCKNO =p.cy_area_no and c.ROWNO=p.CY_ROW_NO and c.BAYNO=p.CY_BAY_NO  " + 
			  				"    and   p.dock_cod = '"+dockCod+"'  AND p.current_stat = '2' ) " + sqlPam+
			  				" order by c.BLOCKNO ,c.BAYNO,c.ROWNO  ";
  		
  		List<Map> lstPortCar=JpaUtils.getEntityManager().createNativeQuery(sqlPortCar).setHint(QueryHints.RESULT_TYPE, ResultType.Map).getResultList();
       // System.out.println(lstPortCar.size()+"--------------------");
  		Map areaMap=new HashMap<>();
  		List<Map> areaLst;
  		for (Map map : lstPortCar) {
  			//System.out.println(map.get("CY_AREA_NO")+"");
  			if(areaMap.containsKey(map.get("CY_AREA_NO")+"")){
  				areaLst=(List<Map>) areaMap.get(map.get("CY_AREA_NO")+"");
  			}else {
  				areaLst=new ArrayList<>();
  				areaMap.put(map.get("CY_AREA_NO")+"", areaLst);	
  			}
  			areaLst.add(map);
		}
  		return areaMap;
      }
 	@Transactional
	@Override
	public HdMessageCode mulGenPortCarPostion(Map portCarInfo) {
		// TODO Auto-generated method stub
		String rowNo=portCarInfo.get("rowNo")+"";
		String areaNo=portCarInfo.get("areaNo")+"";
		String dockCod=portCarInfo.get("dockCod")+"";
		List<Map> pcLst=(List<Map>) portCarInfo.get("portCar");
		Map anAreaMap=getContain(dockCod,areaNo,rowNo);	
		HdMessageCode message=HdUtils.genMsg();		
		String area=null,row=null,bay=null;
		int i=0;

		for (Map portCar : pcLst) {

			if(area==null||!area.equals(portCar.get("cyAreaNo")+"")) {
				i=0;
			}
			area=portCar.get("cyAreaNo")+"";
			
			if(anAreaMap.containsKey(area)) {
				List<Map> lstArea=(List<Map>) anAreaMap.get(area);
				if(i<lstArea.size()) {
					row=lstArea.get(i).get("CY_ROW_NO")+"";
					bay=lstArea.get(i).get("CY_BAY_NO")+"";
					String upSql="update port_car set CY_ROW_NO='"+row+"' , CY_BAY_NO='"+bay+"',CONTACT_INFO='t' where PORT_CAR_NO='"+portCar.get("portCarNo")+"'";
					JpaUtils.getEntityManager().createNativeQuery(upSql).executeUpdate();
					i++;
				}
			}
		}
		return message;
	}
     
	@Transactional
	@Override
	public HdMessageCode transPortCar(Map  map) {
		HdMessageCode message=HdUtils.genMsg();		
		String outArea=null,row=null,bay=null;
		String  companyCod =(String) map.get("comapnyCod");
		String inArea=(String) map.get("inArea");
		String dockCod="";
		if(companyCod.equals("TJG_GZ")) {
			//滚装
			dockCod="03406500";

		}else{
			//环球
			dockCod="03409000";
		}
		//获取在场车辆信息
		List<Map> lstPortCar=(List<Map>) map.get("transPortList");

		//没有库场的位置  位置没有被占了
		Map anAreaMap=getContain(dockCod,inArea,null);
		int i=0;
		int less=0;
		for (Map mapPortCar : lstPortCar) {
			List<Map> lstArea=(List<Map>) anAreaMap.get(inArea);
			outArea=mapPortCar.get("cyAreaNo")+"";
			if(i<lstArea.size()) {
				row=lstArea.get(i).get("CY_ROW_NO")+"";
				bay=lstArea.get(i).get("CY_BAY_NO")+"";
				String upSql="update port_car set CY_AREA_NO='"+inArea+"' ,CY_ROW_NO='"+row+"' , CY_BAY_NO='"+bay+"',CONTACT_INFO='ts' where PORT_CAR_NO='"+mapPortCar.get("portCarNo")+"'";
				JpaUtils.getEntityManager().createNativeQuery(upSql).executeUpdate();
				i++;
			}else {
				less++;
			}
			
		}
		
		Map<String,Map> mapPortCar;
		
		if(companyCod.equals("TJG_GZ")) {
			//滚装
			dockCod="03406500";		
			mapPortCar=GisTask.getGzMapPortCar();
		}else{
			//环球
			dockCod="03409000";
			mapPortCar=GisTask.getHqMapPortCar();
		}

		List<Map> inPortLst=this.getPortCarList(dockCod, inArea);
		List <Map> outPortLst=null;
		if(outArea!=null) outPortLst=this.getPortCarList(dockCod, outArea);
		else outPortLst=new ArrayList();
		
		for (Map mapItem : inPortLst) {
			mapPortCar.put(mapItem.get("PORT_CAR_NO")+"", mapItem);
		}
		for (Map mapItem : outPortLst) {
			mapPortCar.put(mapItem.get("PORT_CAR_NO")+"", mapItem);
		}
		if(less==0) {
			message.setMessage("操作成功！");
		}else {
			message.setMessage("剩下"+(lstPortCar.size()-less)+"无法分配场地");
		}
		return message;
	}
	
    private List<Map> getPortCarList(String dockCod,String area) {
    	String tabName="";
    	 if(dockCod.equals("03406500")) {
    		 tabName="GZ_CONTAINER";
		 }else {
			 tabName="CONTAINER";
		 }

		String sql=" SELECT /*+ USE_HASH(T1,T) ORDERED */ "+
				" t1.port_car_no, t1.vin_no, t1.bill_no, "+
				" (SELECT car_typ_nam FROM c_car_typ m WHERE m.car_typ = t1.car_typ) car_typ_nam, "+
				" (SELECT brand_nam FROM c_brand m WHERE m.brand_cod = t1.brand_cod) brand_cod, "+
				" t.blockno,  t.rowno,  t.bayno, t.companycod,  h.c_ship_nam, "+
				" s.name i_e_nam, t1.i_e_id, t1.trade_id, c.name trade_nam, to_char(t1.IN_CY_TIM,'yyyy-MM-dd HH:mm') IN_CY_TIM, "+
				" t.pos, t1.TRADE_ID || t1.I_E_ID  colour_set"+
				" FROM "+tabName+" t, port_car t1,ship h "+
				" , (SELECT code, name FROM sys_code WHERE field_cod = 'I_E_ID') s, "+
				" (SELECT code, name FROM sys_code WHERE field_cod = 'TRADE_ID') c "+
				" WHERE t.blockno = t1.cy_area_no(+) "+
				" AND t.rowno = t1.cy_row_no "+
				" AND t.bayno = t1.cy_bay_no "+
				" AND t1.ship_no = h.ship_no "+
				" AND t1.i_e_id = s.code(+)  and t1.CURRENT_STAT='2' AND t1.trade_id = c.code（+） "+
				" AND t.companycod = '"+dockCod+"'  and t.blockno='"+area+"'"+
				"ORDER BY t.blockno, t.rowno, t.bayno ";
		 List<Map> portCarlst=JpaUtils.getEntityManager().createNativeQuery(sql).setHint(QueryHints.RESULT_TYPE, ResultType.Map).getResultList();

		 return portCarlst;
		 
    }
}

