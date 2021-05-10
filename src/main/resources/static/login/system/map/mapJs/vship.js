/**
 * 显示船
 * @returns
 */
function showShip(shipInfo) {
		var url="/webresources/login/mapFacilities/getMapShip";
		$.ajax({
			type : "POST",
			url : url,
			data: $.toJSON(shipInfo),
			 contentType:"application/json",
			dataType : 'json',
			success : function(datas) {
				//绘制船
				if(datas.entity){
					createShip(datas);
				}
			}
		});
}
/**
 * 绘制船
 * @param datas
 * @returns
 */
function createShip(datas) {
	var shipLayer=getLayersById("SHIP_LAYER");
	var fc= (new ol.format.GeoJSON()).readFeatures(datas.entity);
	if(!shipLayer){
		layers.SHIP_LAYER= new ol.layer.Vector({
		    source: new ol.source.Vector({
		    	features:fc
		    }),
	    	style: shipStyleFunction
		});
		//顺序
		layers.SHIP_LAYER.setZIndex(layerConfig.SHIP_LAYER.ZINDEX);
		//设置主键
		layers.SHIP_LAYER.set("layerKey",layerConfig.SHIP_LAYER.LayerKey);
		
		map.addLayer(layers.SHIP_LAYER);   
	}else{
		//清除-添加新元素
		shipLayer.getSource().clear();
		shipLayer.getSource().addFeatures(fc);
	}
}

