
function initYard(datas)
{
var searchBuilder=new HdEzuiQueryParamsBuilder();
searchBuilder.setOtherskeyValue("dockCod",datas.dockCod);
searchBuilder.setOtherskeyValue("cyAreaNo",datas.cyAreaNo);
    $.ajax({   	
    	url: "/webresources/login/map/mapFeature/getCyArea",
        contentType:"application/json",
        type: "POST",
       // data: $.toJSON(datas),
        data: JSON.stringify(searchBuilder.hdEzuiQueryParams),
        dataType: 'json',
        success: function (data) {
        	loadYardMap(data.data);
        },
        error: function (data) {
        	console.log(data);
        }
    });
}
//加载箱子
function loadYardMap(jsonObj){
	var yardLayer=getLayersById("YARDAREA_LAYER");
	var fc= (new ol.format.GeoJSON()).readFeatures(jsonObj);
	if(!yardLayer){
		layers.YARDAREA_LAYER= new ol.layer.Vector({
		    source: new ol.source.Vector({
		    	features:null
		    }),
	    	style: yAreaStyle
		});
		layers.YARDAREA_LAYER.getSource().addFeatures(fc);
		//顺序
		layers.YARDAREA_LAYER.setZIndex(layerConfig.YARDAREA_LAYER.ZINDEX);
		//设置主键
		layers.YARDAREA_LAYER.set("layerKey",layerConfig.YARDAREA_LAYER.LayerKey);
		map.addLayer(layers.YARDAREA_LAYER);   
	}else{
		//清除-添加新元素
		//yardLayer.getSource().clear();
		yardLayer.getSource().addFeatures(fc);
	}
	
}
