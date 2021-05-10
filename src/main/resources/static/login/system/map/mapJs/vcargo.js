/**
 * 场上的车辆信息查询
 * 参数的查询条件 默认查找全部
 */
var cntrPage=1;//箱子分批查询
var cntrSize=2000;//每批1000个
function initMapPortCar()
{
	cntrSize=2000;
	initCargo({'curPage':cntrPage,'pageSize':cntrSize,'comapnyCod':companyCod});
}
function initCargo(datas)
{
	
	var extend=map.getView().calculateExtent(map.getSize());
	datas.extend=extend;
    $.ajax({   	
    	url: "/webresources/login/map/mapFeature/getPortCarMap",
        contentType:"application/json",
        type: "POST",
        data: $.toJSON(datas),
        dataType: 'json',
        success: function (data) {
			loadCntr(data.data);
        },
        error: function (data) {
        	console.log(data);
        }
    });
}
//加载箱子
function loadCntr(jsonObj)
{
	var cntrLayer=getLayersById("CNTR_LAYER");
	if(cntrLayer&&jsonObj==""){
		cntrLayer.getSource().clear();
	}
	var fc= (new ol.format.GeoJSON()).readFeatures(jsonObj);
	if(!cntrLayer){
		layers.CNTR_LAYER= new ol.layer.Vector({
		    source: new ol.source.Vector({
		    	features:null
		    }),
	    	style: portCarPosFunction
		});
		layers.CNTR_LAYER.getSource().addFeatures(fc);
		//顺序
		layers.CNTR_LAYER.setZIndex(layerConfig.CNTR_LAYER.ZINDEX);
		//设置主键
		layers.CNTR_LAYER.set("layerKey",layerConfig.CNTR_LAYER.LayerKey);
		map.addLayer(layers.CNTR_LAYER);   
	}else{
		//清除-添加新元素
		cntrLayer.getSource().clear();
		cntrLayer.getSource().addFeatures(fc);
	}
	
}
//加载高亮
function loadLght(jsonObj)
{
	var cntrLayer=getLayersById("CARLGT_LAYER");
	if(cntrLayer&&jsonObj==""){
		cntrLayer.getSource().clear();
	}
	var fc= (new ol.format.GeoJSON()).readFeatures(jsonObj);
	if(!cntrLayer){
		layers.CARLGT_LAYER= new ol.layer.Vector({
		    source: new ol.source.Vector({
		    	features:null
		    }),
	    	style: plyLightStyle
		});
		layers.CARLGT_LAYER.getSource().addFeatures(fc);
		//顺序
		layers.CARLGT_LAYER.setZIndex(layerConfig.CARLGT_LAYER.ZINDEX);
		//设置主键
		layers.CARLGT_LAYER.set("layerKey",layerConfig.CARLGT_LAYER.LayerKey);
		map.addLayer(layers.CARLGT_LAYER); 

	}else{
		//清除-添加新元素
		cntrLayer.getSource().addFeatures(fc);
	}
	var cpt=fc[0].getGeometry().getFirstCoordinate();
    map.getView().setCenter([cpt[0]+0.001,cpt[1]]);
    map.getView().setZoom(19);
	
}
/**
 * 多选货垛
 * @param extent
 * @returns
 */
function getCargoInfo(extent)
{  
	 $.ajax({   	
    	url: comMapConfig[companyCod].baseUrl+comMapConfig[companyCod].cargoInfoUrl+extent,
        success: function (data) {
        	selectedFeatures=[];
        	selectedFeatures=data.features;
    		//弹出贝图daloge
        	if(selectedFeatures.length>0){
        		openBayDialog(selectedFeatures);
        	}
        },
        error: function (data) {
        	console.log(data);
        	$.messager.progress('close');
        }
    }); 
}


/**
 * 贝信息
 * @param bayFeatures //贝的图形元素
 * @returns
 */
var modDolage=null;
var selAreaInfo;
function  openBayDialog(bayFeatures)
{	
	selAreaInfo=selectedFeatures[0].properties;
	selLayRightBoard("billNoSearchTool","1");

	// $('#billNoSearchTool').trigger("click");
/*	var bUrl='../webresources/login/base/CCyArea/dcclxxhzcx.htm?cyAreaNo=' + bInfo.NAME;			  
	//参数为选中的贝箱  因为版本问题 是传不进去 但是已经放到全局变量 selectedFeatures
	modDolage=$("<div></div>").dialog({
		id:'openModleDiloge',
		width:950,    
		height:540,
		modal:true,
		cache: true,
		resizable:true,
		collapsible:true,
		onClose:function(){
			$(this).dialog('destroy');
			modDolage=null;
		},
		title:"堆场车辆信息",
		href: bUrl
	});*/      
}


function  openShipDialog(bayFeatures)
{	
	//参数为选中的贝箱  因为版本问题 是传不进去 但是已经放到全局变量 selectedFeatures
	modDolage=$("<div></div>").dialog({
		id:'openModleDiloge',
		width:650,    
		height:260,
		modal:true,
		cache: true,
		collapsible:true,
		onClose:function(){
			$(this).dialog('destroy');
			modDolage=null;
		},
		title:"船舶基本资料",
		href: '/login/gisMap/mapModle/mapShip.html'
	});      
}
//绑定enter,esc键。
$(this).bind("keyup", function(event) {
    if (event.which == 27&&modDolage!=null) {
    	modDolage.dialog("destroy");
    	modDolage=null;
    }
});

//统计在场箱，在场船，预报船数量
function initCountNum(){
	var mapObj={};
	mapObj.companyCod=companyCod;
	$.ajax({   	
    	url: "/webresources/login/mapFacilities/getCountCntrAndShip",
        contentType:"application/json",
        type: "POST",
        data: $.toJSON(mapObj),
        dataType: 'json',
        success: function (data) {
        	$("#cntrNam").html("在场箱：");
        	$("#shipBerthNam").html("在港船：");
        	$("#shipForecastNam").html("预报船：");
        	$("#cntrNum").html(data.entity.cntr.CNT);
        	$("#shipBerthNum").html(data.entity.berthShip.CNT);
        	$("#shipForecastNum").html(data.entity.forecastShip["CNT"]);
        },
        error: function (data) {
        	console.log(data);
        }
    });
	
	
}
  
