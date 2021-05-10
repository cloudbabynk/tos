//显示作业信息
function showWorkInfo() {
	if (layers.POS == undefined || layers.POS == null) {
		initPOSLayer();
	}
	var url = "yardPlan/getCurWorkPostion.action?ucod=" + unit + "&date="
			+ (new Date()).getTime();
	$.ajax({
		type : "POST",
		url : url,
		dataType : 'json',
		success : function(datas) {
			addWorkInfo(datas);
		}
	});
}
// 显示作业状态
function showWorkStatInfo() {
	if (layers.WORK == undefined || layers.WORK == null) {
		initWorkLayer();
	}
	var url = "yardPlan/getOutWorkState.action?ucod=" + unit + "&date="
			+ (new Date()).getTime();
	$.ajax({
		type : "POST",
		url : url,
		dataType : 'json',
		success : function(datas) {
			addWorkStateInfo(datas);
		}
	});
}
function getName(feature) {
	var id = feature.getId();
	var index = id.substring(2);
	if (index)
		return giscache['workStat'].features[index].name;
	return "";
}
// 不同作业状态对应的图形
var stateStyles = {
	'square' : new ol.style.Icon(/** @type {olx.style.IconOptions} */
	({
		anchor : [ 0, 16 ],
		anchorXUnits : 'fraction',
		anchorYUnits : 'pixels',
		opacity : 0.75,
		src : 'images/flag-white.png'
	})),
	'triangle' : new ol.style.Icon(/** @type {olx.style.IconOptions} */
	({
		anchor : [ 0, 16 ],
		anchorXUnits : 'fraction',
		anchorYUnits : 'pixels',
		opacity : 0.75,
		src : 'images/flag-yellow.png'
	})),
	'flash' : new ol.style.Icon(/** @type {olx.style.IconOptions} */
	({
		anchor : [ 0, 16 ],
		anchorXUnits : 'fraction',
		anchorYUnits : 'pixels',
		opacity : 0.75,
		src : 'images/flag-red.gif'
	})),
	'circle' :new ol.style.Icon(/** @type {olx.style.IconOptions} */
			({
				anchor : [ 0, 16 ],
				anchorXUnits : 'fraction',
				anchorYUnits : 'pixels',
				opacity : 0.75,
				src : 'images/flag-black.png'
			}))
};
// 根据作业状态取图形
function getStylesImage(name) {
	var styles = null;
	if (name) {
		// c=statecolors[name];
		if (name == "●")
			styles =stateStyles['circle'];
		else if (name == "▲")
			styles = stateStyles['triangle'];
		else if (name == "■")
			styles = stateStyles['square'];
		else
			styles = stateStyles['flash'];

	}
	return styles;
}
// 增加作业状态到地图

function createLabelPoint(labelPoint) {
	var pp = labelPoint.split(",");
	return new ol.geom.Point([ parseFloat(pp[0]), parseFloat(pp[1]) ]);
};
//
function addWorkStateInfo(datas) {
	var fc = (new ol.format.GeoJSON()).readFeatures(datas);
	if (layers.WORK) {
		layers.WORK.getSource().addFeatures(fc);
	}
}
var colors = {
	"BILL_ID1" : [ '#FC7D7D', '#030303' ],
	"BILL_ID2" : [ '#FBE319', '#030303' ],
	"BILL_ID3" : [ '#FFC835', '#030303' ],
	"BILL_ID4" : [ '#E0FB9F', '#030303' ]
};
function getColorForCurWork(feature) {
	var billId = feature.get("BILL_TYP");
	if (colors["BILL_ID" + billId])
		return colors["BILL_ID" + billId];
	return colors[0];
}
var createCurWorkPositionTextStyle = function(feature, resolution) {
	return new ol.style.Text({
		textAlign : 'left',
		textBaseline : 'ideographic',
		font : 'bold  10px Arial',
		text : getCurWorkPositionText(feature, resolution),
		fill : new ol.style.Fill({
			color : 'black'
		}),
		stroke : new ol.style.Stroke({
			color : 'white',
			width : 1
		}),
		offsetX : 0,
		offsetY : 0,
		rotation : 0
	});
};
var getCurWorkPositionText = function(feature, resolution) {
	var id=feature.get("id");
	var cid="C_"+id.substring(2);
	if(layers.CARGO!=null){
		var ft=layers.CARGO.getSource().getFeatureById(cid);
		if(ft!=null){
			var text=ft.get('cargoTitle');
			if(showName&&(mview.getZoom()>18)){
				text=dealText(text);
				ta=text.split(" ");
				text=ta.join("\n");
				text=text.replace(/\n\n\n/g,'\n\n');
				return text;
			}else{
				return "";
			}
		}else{
			var text = feature.get('name');
			var ta;
			if (showName&&mview.getZoom()>18) {
				ta = text.split(" ");
				text = ta.join("\n");
				return text;
			}
			return "";
		}
	}
	return "";

};

function initPOSLayer() {
	var olayer = null;
	if (layers.POS) {
		olayer = layers.POS;
	}
	layers.POS = new ol.layer.Vector({
		source : new ol.source.Vector({
			features : []
		}),
		style : function(feature, resolution) {
					var id = feature.getId();
					if (id.indexOf('P_') == 0) {
						
						var cs = getColorForCurWork(feature);
						if (cs == null) {
							cs = colors["4"];
						}
						var style= new ol.style.Style({
			                  stroke: new ol.style.Stroke({color: cs[1],width: 1}),
			                  fill: new ol.style.Fill({ color:cs[0] }),
							  text : new ol.style.Text({
									textAlign : 'left',
									textBaseline : 'bottom',
									font : 'bold 12px Arial',
									text : getCurWorkPositionText(feature, resolution),
									fill : new ol.style.Fill({
										color : 'black'
									}),
									offsetX : 0,
									offsetY : 0,
									rotation : 0
							  })
			            });
						 if(mview.getZoom()>21){
								style.getText().setFont("bold 16px Arial");
							}else{
								style.getText().setFont("bold 12px Arial");
							}
						return style;
					}
		}
	});
	layers.POS.setZIndex(layerConfig.POS.ZINDEX);
	if (olayer) {
		olayer.getSource().clear();
		map.removeLayer(olayer);
	}
	layerConfig.POS.show=true;
	map.addLayer(layers.POS);
}
//
var wwstep={'●':1,'▲':3,'■':2,'√':4};
function getWorkStateMetry(feature ,name){
	var ext=getInnerExtent(feature);
	var step=(ext[1][1]-ext[0][1])/8;
	var stepx=(ext[1][0]-ext[0][0])/8;
	return new ol.geom.Point([ext[0][0]+stepx*(wwstep[name]),ext[0][1]+step*(wwstep[name])]);
}
function initWorkLayer() {
	var olayer = null;
	if (layers.WORK) {
		olayer = layers.WORK;
	}
	layers.WORK = new ol.layer.Vector({
		source : new ol.source.Vector({
			features : []
		}),
		style : function(feature, resolution) {
			if (mview.getZoom() >= 18){						
				 if( layers.CARGO!=null&& layers.CARGO!=undefined){
					var id = feature.getId();							 
						var cid = "C_" + id.substring(id.lastIndexOf("_")+1);
						var cf = layers.CARGO.getSource().getFeatureById(cid);						
						if (cf != null) {
							
								var style=new ol.style.Style({
									geometry : getWorkStateMetry(cf,feature.get('name')),
									image : getStylesImage(feature.get('name')),
									text : new ol.style.Text({
										textAlign : 'left',
										textBaseline : 'bottom',
										font : 'bold 12px Arial',
										fill : new ol.style.Fill({
											color : 'black'
										}),
										offsetX : 0,
										offsetY : 0,
										rotation : 0
								  })
								});
								if(showName&&mview.getZoom()>=21){
									style.getText().setText(feature.get("BILLS"));
									style.getText().setFont("bold 12px Arial");
								}else
								{
									style.getText().setText("");
								}
									
								return style;
						}
				 }
			}

		}
	});
	layers.WORK.setZIndex(layerConfig.WORK.ZINDEX);
	if (olayer) {
		olayer.getSource().clear();
		map.removeLayer(olayer);
	}
	map.addLayer(layers.WORK);
}

function addWorkInfo(datas) {
	var fc = (new ol.format.GeoJSON()).readFeatures(datas);
	if (layers.POS) {
		layers.POS.getSource().addFeatures(fc);
	}
}