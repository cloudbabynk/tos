
var managerFlash=false;
function showManagers() {
	var url = "manage/getAllManagerPositon.action?ucod=" + unit + "&date="
			+ (new Date()).getTime();
	$.ajax({
		type : "POST",
		url : url,
		dataType : 'json',
		success : function(datas) {
			managerFlash=false;
			initManager(datas);
		}
	});
}

function locManagerById(did,opercod,unitcod,flag){
	if(flag!=null&&flag!=undefined&&flag!='undefined'&&flag!='') {
		flag='line';
	}else flag='own';
	var rul="manage/getManagerPosition.action?dids="+did+"&opercod="+opercod+"&unitcod="+unitcod+"&locflag="+flag;
	$.ajax({
		type : "POST",
		url : rul,
		dataType : 'json',
		success : function(datas) {
			if(JSON.parse(datas).features==null||JSON.parse(datas).features==undefined||JSON.parse(datas).features==""){
				if(flag==null)
				$.messager.alert("提示","所选人员暂无位置信息,清确认手机或平板是否转借他人使用");
			}
			managerFlash=true;
			initManager(datas);
		}
	});
}


function initManager(datas){
	var olayer=null;

	var fc = (new ol.format.GeoJSON()).readFeatures(datas);
	if(layers.MANAGER==null||layers.MANAGER==undefined){
		layers[layerConfig.MANAGER.LayerKey] = new ol.layer.Vector({
			source : new ol.source.Vector({
				features : []
			}),
			style : function(feature, resolution) {
				var ot=feature.get('ot');
				var key=null;
				if(ot=='M'){
					key='MN';//管理员
				}else if(ot=='G')
				{
					key='DC';//单船指导员
				}else if(ot=='A')
				{
					key='AJ';//安检员
				}else if(ot=='B')
				{
					key='BZ';//班组长
				}
				else
					key="KCY";//堆场员
				
			//	
				var img=getManagerImage(key,feature.get("state"));
				var style = new ol.style.Style({
					image:img,
					text: new ol.style.Text({
						textAlign : 'left',
						textBaseline : 'middle',
						font : 'bold 13px Arial',
				          fill: new ol.style.Fill({
				            color: '#000'
				          }),
						  stroke: new ol.style.Stroke({
						    color: '#fff',
					        width: 3
						  })
				     })
				});
				if(mview.getZoom()>18&&showName){
					var text=feature.get("un");
					if(text==null||text==undefined)
						text=feature.get("OPER_NAM");
					if(text==undefined||text==''||text==null)	
						text=feature.get('ddid');
					style.getText().setText(text);
				}
				return [style];
			}
		
		});
		layers[layerConfig.MANAGER.LayerKey].setZIndex(layerConfig.MANAGER.ZINDEX);
		map.addLayer(layers[layerConfig.MANAGER.LayerKey]);
	}
	
	//查看地图上 目前已有的人员
	var cachfids=layers.MANAGER.getSource().getFeatures();
	
	var pubCachManager={};//缓存人员地图上显示的人员信息
	for(var i=0;i<cachfids.length;i++)
	{
		//目前主要是为了存key 赋值为0 地图原来的要素
		pubCachManager[cachfids[i].getId()]=0;	
	}
	   
	
	
	//地图添加
	var old;
	for(var i=0;i<fc.length;i++){
		var id=fc[i].getId();
		pubCachManager[id]=1;//新修改的
		old=layers.MANAGER.getSource().getFeatureById(id);
		if(old!=null){
			old.setProperties(fc[i].getProperties());
			old.setGeometry(fc[i].getGeometry());
		}else{
			layers.MANAGER.getSource().addFeature(fc[i]);
		}
	}
	//移除为0 的
	 for(var key in pubCachManager){  
	     if(pubCachManager[key]==0)
	     {
			var rmFeature=layers.MANAGER.getSource().getFeatureById(pubCachManager[key]);
			if(rmFeature) layers.MANAGER.getSource().removeFeature(rmFeature);
	     }
	 } 
	 //闪烁 和 移除
	 if(managerFlash){
		flashNos=new Array();//缓存刷新数组
		for(var i=0;i<fc.length;i++){
			flash(fc[i]);
			flashNos.push(fc[i].getId());
		}
		if(fc.length>0){
			mview.setCenter(fc[0].getGeometry().getCoordinates());
			showCancelButton();
		}
	}
	if(flashNos&&!locSign)
	{
		for(var i=0;i<flashNos.length;i++){
			var rmFeature=layers.MANAGER.getSource().getFeatureById(flashNos[i]);
			if(rmFeature) layers.MANAGER.getSource().removeFeature(rmFeature);
		}
	}
}