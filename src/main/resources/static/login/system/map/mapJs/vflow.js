function getWorkFlow() {
    $.ajax({
    	url: "/webresources/login/base/CGisWbill/findRelCarmera",
        contentType:"application/json",
        type: "POST",
        data: $.toJSON(selWorkBill),
        dataType: 'json',
        async:false,
        success: function (data) {
        	showFlow(data);
        	selWorkBill=null;
        },
        error: function (data) {
            console.log("保存失败！");
        }
	});
}

function showFlow(data){
	if(data.data){
		
		var flowLayer=getLayersById("FLOW_LAYER");
		var fc= (new ol.format.GeoJSON()).readFeatures(data.data);
		if(!flowLayer){
			layers.FLOW_LAYER= new ol.layer.Vector({
			    source: new ol.source.Vector({
			    	features:fc
			    }),
		    	style:folwStyleFounction
			});
			//顺序
			layers.FLOW_LAYER.setZIndex(layerConfig.FLOW_LAYER.ZINDEX);
			//设置主键FLOW_LAYER
			layers.FLOW_LAYER.set("layerKey",layerConfig.FLOW_LAYER.LayerKey);
			
			map.addLayer(layers.FLOW_LAYER);   
		}else{
			//清除-添加新元素
			flowLayer.getSource().clear();
			flowLayer.getSource().addFeatures(fc);
		}
		
	}
}

