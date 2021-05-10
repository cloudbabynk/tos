//多选
//var select = new ol.interaction.Select();
//选中的集合
//var selectedFeatures = select.getFeatures();
var selectedFeatures = [];
var selectedShip = [];
var selectedMach=[];
var selectedCamera=[];
//拖拽控件 a DragBox interaction used to select features by drawing boxes
var dragBox = new ol.interaction.DragBox({
	condition: ol.events.condition.platformModifierKeyOnly
});


//弹出窗口的url
var openDialogUrl="functionArea";

//缓冲点
var selectBuffer=0.0000001;

function initMapEventAction(){
/*	map.on('singleclick', function(evt) {
		var pixel = map.getEventPixel(evt.originalEvent);
		var feature=getFeatureByPixel(pixel);
		if(feature!=null){
			var id=feature.getId();
			//船
			if(id.indexOf('SHIP_') == 0){
				selectedShip=[];
				selectedShip.push(feature);
				openShipDialog(selectedShip);
			}
		}
	});*/
	
	
	map.on('dblclick', function(evt) {
		var pixel = map.getEventPixel(evt.originalEvent);
		var feature=getFeatureByPixel(pixel);
		if(feature!=null&&id){
			var id=feature.getId();
			
			if(id.indexOf('SHIP_') == 0){
				selectedShip=[];
				selectedShip.push(feature);
				openShipDialog(selectedShip);
			}else if(id.indexOf('MACH_') == 0){
				//机械
				selectedMach=[];
				selectedMach.push(feature);
			}else if(id.indexOf('CAMERA_') == 0){
				selectedCamera=[];
				selectedCamera.push(feature);
				openCameraDialog(selectedCamera);
			}else
			{    

				//货物
				pixel = map.getEventCoordinate(evt.originalEvent);
				var extend=[pixel[0]-selectBuffer,pixel[1]-selectBuffer,pixel[0]+selectBuffer,pixel[1]+selectBuffer];
				getCargoInfo(extend);
			}
		}else{
			//货物
			pixel = map.getEventCoordinate(evt.originalEvent);
			var extend=[pixel[0]-selectBuffer,pixel[1]-selectBuffer,pixel[0]+selectBuffer,pixel[1]+selectBuffer];
			getCargoInfo(extend);
		}
	});
	
	var container = document.getElementById('popup');  
	var content = document.getElementById('popup-content');  
	var closer = document.getElementById('popup-closer'); 
	
	closer.onclick = function() {  
		  overlay.setPosition(undefined);  
		  closer.blur();  
		  return false;  
	};  
	
	/** 
	 * Create an overlay to anchor the popup to the map. 
	 */  
	var overlay = new ol.Overlay(/** @type {olx.OverlayOptions} */ ({  
	  id:'main_pop',
	  element: container,  
	  autoPan: true,  
	  autoPanAnimation: {  
	    duration: 250   //当Popup超出地图边界时，为了Popup全部可见，地图移动的速度. 单位为毫秒（ms）  
	  }  
	})); 
	
	
	 map.on('pointermove', function(evt) {
	     if (evt.dragging) {
	          return;
	     }

	     displayFeatureInfo(evt); 	
	 });
	 var displayFeatureInfo = function (evt) {
	        var pixel = map.getEventPixel(evt.originalEvent);
	        var feature = map.forEachFeatureAtPixel(pixel, function (feature) { return feature; } );
	        if(feature==null){
				overlay.setPosition(undefined);
				map.removeOverlay(overlay);
				  return false;
			}
	        if(feature){
				var id=feature.getId();
				if(id&&id!=undefined){
					var coordinate = evt.coordinate;
					if(id.indexOf('CAMERA_') == 0){
						if(map.getOverlayById('main_pop'))map.removeOverlay(overlay);
						content.innerHTML = feature.get("cameraNam");  
						overlay.setPosition(coordinate);  
						map.addOverlay(overlay); 
					}else if(id.indexOf("CNTR_")==0){
						if(map.getOverlayById('main_pop'))map.removeOverlay(overlay);
						var cargoPopup=feature.get('cargoPop');
						var contenthtml=cargoPopup;
						content.innerHTML = contenthtml;
						 map.addOverlay(overlay);
						 overlay.setPosition(coordinate);
					}else if(id.indexOf("CAR_")==0){
						if(map.getOverlayById('main_pop'))map.removeOverlay(overlay);
						var cargoPopup=feature.get('cargoPop');
						var contenthtml=cargoPopup;
						content.innerHTML = contenthtml;
						 map.addOverlay(overlay);
						 overlay.setPosition(coordinate);
					}else{
						overlay.setPosition(undefined);
						map.removeOverlay(overlay);
						  return false;
					}
				}
	        }
	 };
	
	
	//多选功能
	//map.addInteraction(select);
	map.addInteraction(dragBox);
	dragBox.on('boxend', function() {
		selectedFeatures=[];//清空选中
		//拖拽的范围
		var extent = dragBox.getGeometry().getExtent();
		getCargoInfo(extent);
	});
	
	//zoom改变
	//map.getView().on('change:resolution',checkZoom);//大小改变: 调用的函数
	
	//地图移动事件
	//map.on('moveend',mapDrag);
	
}

//zoom改变
function checkZoom() {
	/* if (map.getView().getZoom() >= 18) {}	}*/
}
//地图移动事件
function mapDrag(evt)
{
	//alert(map.getView().calculateExtent(map.getSize()));
	//初始化箱子的图形功能
	//initMapCntr();
}


//map 尺寸发生改变的函数
function resizeLayoutCenter(w,h)
{
	$("#map").css("width",w-3);
	$("#map").css("height",h-2);
	if(map)map.setSize([w-5,h-2]);
}
//查询工具控制器
var tlshow=[false,false,false,false,false];
var show=false;
function showTl(index){
	//查询工具面板
	var tldiv=document.getElementById('searchTool');
	tlshow[index]=!tlshow[index];
	show=false;
	var tl=null;
	var idx=0;
	if(tlshow[index]){
		for(var i=0;i<tlshow.length;i++){
			if(index!=i){
				tlshow[i]=false;
			}
		}
	}
	for(var i=0;i<tlshow.length;i++){
		if(tlshow[i]){
			show=true;
			break;
		}
	}

	if(show){
		tldiv.style.display='';
		for(var i=0;i<tlshow.length-1;i++){
			idx=i;
			tl=document.getElementById('tl'+idx);
			if(tlshow[i]){
				tl.style.display="";
				$("#toolPanle"+i).panel("open");
			}else{
				if(tl){
					tl.style.display='none';
					//$("#toolPanle"+i).panel("close");
				}
			}
		}
	}else{
		tldiv.style.display='none';
	}
}
function onClosTool(num)
{
	showTl(num);
}
/**
 * 刷新按钮
 * @returns
 */
function refreshThing(){
	//箱信息、机械信息、船舶信息等
	var cntrLayer=getLayersById("CNTR_LAYER");
	if(cntrLayer){
		cntrLayer.getSource().clear();
	}
	//initMapCntr();
	
	var machLayer=getLayersById("MACH_LAYER");
	if(machLayer){
		machLayer.getSource().clear();
	}
	var yardLayer=getLayersById("YARD_BRIDGE_LAYER");
	if(yardLayer){
		yardLayer.getSource().clear();
	}
	var bankLayer=getLayersById("BANK_BRIDGE_LAYER");
	if(bankLayer){
		bankLayer.getSource().clear();
	}
	initMachInfo();
	
	var shipLayer=getLayersById("SHIP_LAYER");
	if(shipLayer){
		shipLayer.getSource().clear();
	}
	var shipObj={};
	shipObj.countryCod=companyCod;
	showShip(shipObj);
	/**
	 * 统计
	 */
	//initCountNum();
}
