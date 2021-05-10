/**
 * 本文件目的是用来做图层缓存和图层初始化以及构建图层使用
 * 目前系统中共有12个图层
 */
/*var giscache={};//缓存数据用
var showFlag={};
var tdid;
var idtype;
var showName=false;
var ct='';
var outAreaLayer,outAreaLabLayer; //区域定位的图层
var flashNos; //定位刷新的编号数据
var locSign=false;//定位的时候设置为true  取消定能为设置为false
*/
var layerKeys=['LOC','CARGO','WORK','UP','SHIP','BARGE','HATCH','DIRECT','MANAGER','MACH','CAMERA','POS','OUTAREA'];

var layers={};//层缓存
/**
 * 图层配置
 */
var layerConfig={
		'BASE':{FID:'BASE_',LayerKey:'BASE',ZINDEX:1,show:true,name:'地图底图',freshTime:1000*60},
		'YARDAREA_LAYER':{FID:'YARDAREA_',LayerKey:'YARDAREA_LAYER',ZINDEX:2,show:false,name:'货堆',freshTime:1000*60},
		'SHIP_LAYER':{FID:'SHIP_',LayerKey:'SHIP_LAYER',ZINDEX:300,show:true,name:'船舶图层',freshTime:1000*60*30},
		'MACH_LAYER':{FID:'MACH_',LayerKey:'MACH_LAYER',ZINDEX:400,show:true,name:'机械：拖车图层',freshTime:1000*60},
		'CNTR_LAYER':{FID:'CNTR_',LayerKey:'CNTR_LAYER',ZINDEX:200,show:true,name:'货垛图层',freshTime:1000*60},
		'CAR_LAYER':{FID:'CAR_',LayerKey:'CAR_LAYER',ZINDEX:600,show:true,name:'定位图层',freshTime:1000*60},
		'CARLGT_LAYER':{FID:'CARLGT_',LayerKey:'CARLGT_LAYER',ZINDEX:600,show:true,name:'存车高亮',freshTime:1000*60},
		'CAMERA_LAYER':{FID:'CAMERA_',LayerKey:'CAMERA_LAYER',ZINDEX:1000,show:true,name:'摄像头图层',freshTime:1000*60},
		'YARD_BRIDGE_LAYER':{FID:'YARD_BRIDGE_',LayerKey:'YARD_BRIDGE_LAYER',ZINDEX:210,show:true,name:'场桥图层',freshTime:1000*60*5},
		'BANK_BRIDGE_LAYER':{FID:'BANK_BRIDGE_',LayerKey:'BANK_BRIDGE_LAYER',ZINDEX:320,show:true,name:'岸桥图层',freshTime:1000*60*5},	
		'LOCK_LAYER':{FID:'LOCK_',LayerKey:'LOCK_LAYER',ZINDEX:999,show:true,name:'定位图层',freshTime:1000*60},
		'DRAW_LAYER':{FID:'DW_',LayerKey:'DRAW_LAYER',ZINDEX:1000,show:true,name:'绘制句柄图层',freshTime:1000*60},
		'FLOW_LAYER':{FID:'WK_',LayerKey:'FLOW_LAYER',ZINDEX:500,show:true,name:'作业线',freshTime:1000*60}
		
};
//http://192.168.1.200:8004/geoserver
var comMapConfig={	
		'TJG_HQ':{code:'NCT',name:'天津港',baseUrl:MapHdUtils.global.config.geoIp(),baseWms:'/tjg/wms',format:'image/png',basePam:{'LAYERS':'tjg:tjg_group','TILED':true,'VERSION':'1.1.0','FORMAT': 'image/png'},cargoInfoUrl:"/tjg/ows?service=WFS&version=1.0.0&request=GetFeature&typeName=tjg:CONTAINER&maxFeatures=50&outputFormat=application%2Fjson&bbox=",initZoom:17,maxZoom:22,minZoom:16,center:[113.680803,22.65332],mapExtent:[113.66005336600654,22.641524608340745,113.69137613477785,22.660405738041845]},
		'TJG_GZ':{code:'GZ',name:'天津港滚装',baseUrl:MapHdUtils.global.config.geoIp(),baseWms:'/tjg/wms',format:'image/png',basePam:{'LAYERS':'tjg:tjg_gz','TILED':true,'VERSION':'1.1.0','FORMAT': 'image/png'},cargoInfoUrl:"/tjg/ows?service=WFS&version=1.0.0&request=GetFeature&typeName=tjg:GZ_CONTAINER&maxFeatures=50&outputFormat=application%2Fjson&bbox=",initZoom:17,maxZoom:22,minZoom:16,center:[113.67846,22.65259],mapExtent:[113.66472941402787,22.644812926222187,113.69134758124574,22.6604685558122]}
};

//地图服务 底图  ImageWMS TileWMS
var baseSource=new ol.source.TileWMS({
	url:comMapConfig[companyCod].baseUrl+comMapConfig[companyCod].baseWms,
	params:comMapConfig[companyCod].basePam,
    serverType: 'geoserver',
    crossOrigin: 'anonymous'
		
});
//新建图层
layers.BASE=new ol.layer.Tile({
    source: baseSource
});

//指定主键
layers.BASE.set("layerKey",layerConfig.BASE.LayerKey);
//指定图层显示顺序
layers.BASE.setZIndex(layerConfig.BASE.ZINDEX);

                      
/**
 * 获取地图的图层
 * @param layerKey
 * @returns
 */
function getLayersById(layerKey){
	//返回的图层
	var rtLayer=null;
	
	var lsLayer=map.getLayers().getArray();
	
	if(lsLayer&&lsLayer.length>0){
		for(var i=0;i<lsLayer.length;i++){
			if(layerKey==lsLayer[i].get('layerKey')){
				rtLayer=lsLayer[i];
				break;
			}
		}
	}
	return rtLayer;
}

/**
 * 清理地图
 * @param key
 * @returns
 */
function clearLayers(key){
	var clearLayer=getLayersById(key);
	if(clearLayer){
		//地图
		clearLayer.getSource().clear();
		map.removeLayer(clearLayer);
		//配置中删除
		delete layers[key];
	}
}


/**
 * 创建空白Vector 源
 * @returns {ol.source.Vector}
 */
function createEmptyVectorSource(){
	return  new ol.source.Vector({
		features:new ol.Collection()
	});
}





