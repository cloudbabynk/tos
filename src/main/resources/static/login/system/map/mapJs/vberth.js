function showBerth() {
		var url = "ship/getInerRiverBerth.action?cu=1";
		$.ajax({
			type : "POST",
			url : url,
			dataType : 'json',
			success : function(datas) {
				createBerth(datas);
			}
		});
}

var berthStyle=new ol.style.Style({
	image:layerConfig.BERTH.defaultIcon,
	text: new ol.style.Text({
		textAlign : 'left',
		textBaseline : 'middle',
		offsetX : 5,
		offsetY : 5,
          fill: new ol.style.Fill({
            color: '#000000'
          })
        })
});

function createBerth(datas){
	var fc = (new ol.format.GeoJSON()).readFeatures(datas);
	var oldLayer=null;
	if(layers.BERTH){
		oldLayer=layers.BERTH;
	}
	if(fc.length>0){
		layers.BERTH= new ol.layer.Vector({
			source : new ol.source.Vector({
				features : fc
			}),
			style : function(feature, resolution) {
				var style=berthStyle;
				if(mview.getZoom()>19)
					style.getText().setText(feature.get('name'));
				else
					style.getText().setText("");
				return style;
			}
		});
		if(oldLayer!=null){
			oldLayer.getSource().clear();
			map.removeLayer(oldLayer);
		}
		layers.BERTH.setZIndex(1000);
		map.addLayer(layers.BERTH);
	}
}