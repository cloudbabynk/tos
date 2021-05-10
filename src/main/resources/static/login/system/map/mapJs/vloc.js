var high = false;// 高亮
var cargoLoc = "CARGO_LOC";
var highLight = new ol.style.Style({
	stroke : new ol.style.Stroke({
		color : 'red',
		width : 2
	}),
	fill : new ol.style.Fill({
		color : '#EBEBEB'
	}),
	text : new ol.style.Text({
		textAlign : 'left',
		textBaseline : 'bottom',
		font : 'Normal  14px',
		fill : new ol.style.Fill({
			color : 'white'
		}),
		stroke : new ol.style.Stroke({
			color : 'white',
			width : 1
		}),
		offsetX : 0,
		offsetY : 0,
		rotation : 0
	})
});
var nameStyle = new ol.style.Style({
	text : new ol.style.Text({
		textAlign : 'left',
		textBaseline : 'bottom',
		font : 'Normal  14px',
		fill : new ol.style.Fill({
			color : 'black'
		}),
		offsetX : 0,
		offsetY : 0,
		rotation : 0
	})
});
var common = new ol.style.Style({
	stroke : new ol.style.Stroke({
		color : '#cccccc',
		width : 1
	}),
	fill : new ol.style.Fill({
		color : '#EBEBEB'
	}),
	text : new ol.style.Text({
		textAlign : 'left',
		textBaseline : 'bottom',
		font : 'Normal  14px',
		fill : new ol.style.Fill({
			color : 'black'
		}),
		offsetX : 0,
		offsetY : 0,
		rotation : 0
	})
});
var LocfontStyle = new ol.style.Style({
	text : new ol.style.Text({
		textAlign : 'left',
		textBaseline : 'top',
		font : 'Normal  14px',
		fill : new ol.style.Fill({
			color : 'black'
		}),
		offsetX : -10,
		offsetY : 0,
		rotation : 0
	})
});
function hideAllLoc() {
	if (layers.LOC) {
		layers.LOC.setVisible(false);
	}

	if (layers.TEMP) {
		map.removeLayer(layers.TEMP);
		layers.TEMP = null;
	}
	showCargoLoc = false;
}

function highShowAll() {
	high = true;
	showAllLoc(false);
	// locationLayer.setStyle(highLight);
}
function cancelHighShowAll() {
	high = false;
	layers.LOC.getSource().forEachFeature(function(feature) {
		feature.setStyle(common);
	});
	hideAllLoc();
}

/*
 * function getLocFeature(locCod){ var
 * index=getIndexInArray(locCod,giscache['locationfm']); if(index!=-1) return
 * layers.LOC.getSource().getFeatureById(layerConfig.LOC.FID+index); return
 * null; }
 */

function getLocFeature(locCod) {
	return layers.LOC!=undefined ?layers.LOC.getSource().forEachFeature(function(feature) {
		if (feature.get("yl") == locCod)
			return feature;
	}):null;
}

function initAllLoc() {
	var url = "manage/showLocAll.action?ucod=" + unit + "&date="
			+ (new Date()).getTime();
	$.ajax({
		type : "POST",
		url : url,
		dataType : 'json',
		success : function(datas) {
			// alert('hahahas');
			addLocation(datas);
		}
	});
}
function showAllLoc(fresh) {
	if (fresh || !hasLayer('LOC')) {
		if (hasLayer('LOC')) {
			map.removeLayer(getLayer('LOC'));
		}
		initAllLoc();
	} else {
		if (hasLayer('LOC')) {
			getLayer('LOC').setVisible(true);
		}
		if (high) {
			getLayer('LOC').getSource().forEachFeature(function(feature) {
				feature.setStyle(highLight);
			});
		}
	}
	showFlag.showCargoLoc = true;
}

function addLocation(datas) {
	var olayer = null;
	giscache['location'] = datas;
	giscache['locationfm'] = new Array();
	var fc = (new ol.format.GeoJSON()).readFeatures(datas);
	// var fcn=new Array();
	/*
	 * if(fc!=null&&fc.length>0){ var name_loc; var locs; for(var i=0;i<fc.length;i++){
	 * giscache['locationfm'][i]=fc[i].get("name")+fc[i].get("YARD_COD");//datas.features[i].name+datas.features[i].YARD_COD;
	 * fc[i].setId("L_"+i); name_loc=fc[i].get("name_loc"); if(name_loc){
	 * locs=name_loc.split(","); fcn[i]=new ol.Feature({ geometry:new
	 * ol.geom.Point([parseFloat(locs[0]),parseFloat(locs[1])]),
	 * name:fc[i].get("loc_cod") }); fcn[i].setId("LN"+i); } } }
	 */
	if (layers.LOC) {
		olayer = layers.LOC;
	}
	layers.LOC = new ol.layer.Vector({
		source : new ol.source.Vector({
			features : fc
		}),
		style : function(feature, resolution) {
			var id = feature.getId();
			var style = feature.getStyle();
			if (style == null) {
				if (id && id.indexOf('L_') == 0)
					style = high ? highLight : common;
				else
					style = LocfontStyle;
			}
			if (id && id.indexOf("LN") >= 0) {
				if (showName && mview.getZoom() >= 19) {
					style.getText().setText(feature.get('name'));
				} else {
					style.getText().setText('');
				}
			}
			 if(mview.getZoom()>21){
					style.getText().setFont("bold 28px Arial");
				}else{
					style.getText().setFont("bold 14px Arial");
				}
			return style;
		}

	});
	layers.LOC.setZIndex(layerConfig.LOC.ZINDEX);
	if (olayer) {
		olayer.getSource().clear();
		map.removeLayer(olayer);
	}
	map.addLayer(layers.LOC);
}

function showLoc(locCod, cords) {
	var feature = getLocFeature(locCod);
	if (!feature) {
		if (cords) {
			feature = new ol.Feature({
				geometry : new ol.geom.Polygon([ cords ]),
				style : highLight,
				yarloc : locCod,
				id : "L_" +new Date().getTime()
			});
		}
	} else {
		feature = feature.clone();
		feature.setId("L_" + layers.LOC.getSource().getFeatures().length);
		feature.setStyle(highLight);
	}
	if (feature) {
		if (layers.LOC) {
			// layers.LOC.getSource().clear();
			layers.LOC.getSource().addFeature(feature);
			layers.LOC.setVisible(true);
		} else {
			feature.setStyle(highLight);
			layers.LOC = new ol.layer.Vector({
				source : new ol.source.Vector({
					features : [ feature ]
				}),
				style : function(feature, resolution) {
					var id = feature.getId();
					if (id.indexOf('L_') == 0)
						return high ? highLight : common;
					else {
						if (mview.getZoom() >= 19) {
							nameStyle.getText().setText(feature.get('name'));
						} else {
							nameStyle.getText().setText('');
						}
						return nameStyle;
					}
				}
			});
			layers.LOC.setZIndex(layerConfig.LOC.ZINDEX);
			map.addLayer(layers.LOC);
			mview.setCenter(feature.getGeometry().getFirstCoordinate());
		}
	}
}

// 初始化货位图层
/*
 * function addLocation(datas){ giscache[layerConfig.LOC.LayerKey]=datas;
 * giscache['locationfm']=new Array(); var fc=(new
 * ol.format.GeoJSON()).readFeatures(datas); var fcn=new Array();
 * if(fc!=null&&fc.length>0){ var name_loc; var locs; for(var i=0;i<fc.length;i++){
 * giscache['locationfm'][i]=datas.features[i].name+datas.features[i].YARD_COD;
 * fc[i].setId("L_"+i); fc[i].setStyle(high?highLight:common);
 * name_loc=datas.features[i].name_loc; if(name_loc){ locs=name_loc.split(",");
 * fcn[i]=new ol.Feature({ geometry:new
 * ol.geom.Point([parseFloat(locs[0]),parseFloat(locs[1])]),
 * name:datas.features[i].name }); fcn[i].setId("LN"+i); fcn[i].setStyle(new
 * ol.style.Style({ text: new ol.style.Text({ textAlign: 'left', textBaseline:
 * 'bottom', font: 'Normal 10px', text: function(feature, resolution) { var
 * text=''; if(mview.getZoom()>=19){ text=feature.get("name"); } return text; },
 * fill: new ol.style.Fill({color: 'white'}), // stroke: new
 * ol.style.Stroke({color: 'white', width: 1}), offsetX: 0, offsetY: 0,
 * rotation: 0 }) })); } } } var
 * l=createLayerByFeature(layerConfig.LOC.LayerKey,fc.concat(fcn));
 * l.setStyle(function (feature,resolution){ var id=feature.getId();
 * if(id.indexOf("LN")==0){ if(mview.getZoom()>=19){
 * feature.getStyle().getText().setText(feature.get("name")); } } return
 * feature.getStyle(); }); map.addLayer(l); }
 */
// 绘制货位
var dlocLayer;
var draw;
var modify;
var listener;
var coordinates;
var fts;
var drawpointStyle = new ol.style.Style({
	fill : new ol.style.Fill({
		color : 'rgba(255, 255, 255, 0.2)'
	}),
	stroke : new ol.style.Stroke({
		color : '#ffcc33',
		width : 1
	}),
	image : new ol.style.Circle({
		radius : 4,
		fill : new ol.style.Fill({
			color : '#ffcc33'
		})
	})
});

function beginDrawLoc() {
	/*
	 * dlocLayer=getLayer(layerConfig.TEMP.LayerKey,'V',true);
	 * dlocLayer.setMap(map);
	 */
	fts = new ol.Collection();
	
	if (layers.TEMP) {
		clearLayers('TEMP')
	}
	
	layers.TEMP = new ol.layer.Vector({
		source : new ol.source.Vector({
			features : fts
		}),
		style:drawpointStyle
	});
	layers.TEMP.setZIndex(layerConfig.TEMP.ZINDEX);
	map.addLayer(layers.TEMP);
	var modify = new ol.interaction.Modify({
		features : fts,
		style:drawpointStyle,
		deleteCondition : function(event) {
			return ol.events.condition.shiftKeyOnly(event)
					&& ol.events.condition.singleClick(event);
		}
	});
	map.addInteraction(modify);
	
	draw = new ol.interaction.Draw({
		features : fts,
		type : 'Point',
		style:drawpointStyle
	});
	map.addInteraction(draw);
}

function stopDrawLoc() {
	if (fts.getLength() > 0) {
		var point;
		coordinates = new Array();
		for (var i = 0; i < fts.getLength(); i++) {
			point = fts.item(i).getGeometry();
			coordinates[coordinates.length] = point.getCoordinates();
		}
	}
	map.removeInteraction(draw);
	map.removeInteraction(modify);
	clearLayers('TEMP');
	return coordinates;
}