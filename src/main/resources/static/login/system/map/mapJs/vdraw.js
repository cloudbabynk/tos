//绘制形状使用
var drawType=null;//绘制的类型
var ddraw=null;
var darea=null;//面积
var dcoordinates =null;//返回的坐标
var modifyHanlder, selectHanlder;
var dfc=new ol.Collection();
var dsource= new ol.source.Vector({
	features:dfc
});

var drawCallFunc="";//绘制的回调句柄
var editdrawCallFunc="";//回调函数句柄
function beginDraw(type){
	drawType=type;//绘制类型
	//绘制句柄图层
	layers.DRAW_LAYER=new ol.layer.Vector({
		source:createEmptyVectorSource(),
		style:new ol.style.Style({
	    fill: new ol.style.Fill({
	        color: 'rgba(81, 146, 4, 0.5)'
	      }),
	      stroke: new ol.style.Stroke({
	        color: '#ffcc33',
	        width: 1
	      }),
	      image: new ol.style.Circle({
	        radius: 7,
	        fill: new ol.style.Fill({
	          color: '#ffcc33'
	        })
	      })
	    })
	});
	layers.DRAW_LAYER.setZIndex(layerConfig.DRAW_LAYER.ZINDEX);
	map.addLayer(layers.DRAW_LAYER);
	addInteraction() ;
}
//这个方法做了一些操作、然后调用回调函数    
function doCallback(fn,args)    
{    
    fn.apply(this, args);  
}

function saveLocationAndArea(geom){
	dcoordinates=geom.getCoordinates();
	if(geom.getType()=="Point"){
		darea='0';
	}if(geom.getType()=="LineString"){
		darea=formatLength(geom);//计算长度
	}
	if(geom.getType()=="Polygon")
	{
		darea= Math.abs(wgs84Sphere.geodesicArea(geom.getLinearRing(0).getCoordinates()));
		darea=(Math.round(darea * 100) / 100);
	}
	if(drawCallFunc!="")
	{
		doCallback(eval(drawCallFunc),[dcoordinates,darea]); 
		if(editdrawCallFunc==""){
			drawCallFunc="";
		}
		editdrawCallFunc="";
	}
	drapDRAW_LAYER();
}
//具体绘制方法
function addInteraction() {
	if(ddraw) map.removeInteraction(ddraw);
	if (drawType !== 'None') {
	  var geometryFunction;
	  if (drawType === 'Square') {
		drawType = 'Circle';
	    geometryFunction = ol.interaction.Draw.createRegularPolygon(4);
	  } else if (drawType === 'Box') {
		drawType = 'Circle';
		geometryFunction = ol.interaction.Draw.createBox();
	  } else if (drawType === 'Star') {
		drawType = 'Circle';
	    geometryFunction = function(coordinates, geometry) {
	      if (!geometry) {
	        geometry = new ol.geom.Polygon(null);
	      }
	      var center = coordinates[0];
	      var last = coordinates[1];
	      var dx = center[0] - last[0];
	      var dy = center[1] - last[1];
	      var radius = Math.sqrt(dx * dx + dy * dy);
	      var rotation = Math.atan2(dy, dx);
	      var newCoordinates = [];
	      var numPoints = 12;
	      for (var i = 0; i < numPoints; ++i) {
	        var angle = rotation + i * 2 * Math.PI / numPoints;
	        var fraction = i % 2 === 0 ? 1 : 0.5;
	        var offsetX = radius * fraction * Math.cos(angle);
	        var offsetY = radius * fraction * Math.sin(angle);
	        newCoordinates.push([center[0] + offsetX, center[1] + offsetY]);
	      }
	      newCoordinates.push(newCoordinates[0].slice());
	      geometry.setCoordinates([newCoordinates]);
	      return geometry;
	    };
	  }
	   ddraw = new ol.interaction.Draw({
		      source: layers.DRAW_LAYER.getSource(),
		      type: drawType,
		      style: new ol.style.Style({
		            fill: new ol.style.Fill({
		              color: 'rgba(222, 222, 0, 0.2)'
		            }),
		            snapTolerance:0,
		            stroke: new ol.style.Stroke({
		            	  color: 'rgba(0, 200,255,1)',
			              //lineDash: [10, 10],
			              width: 1
		            }),
		            image: new ol.style.Circle({
		              radius: 2,
		              stroke: new ol.style.Stroke({
		                color: 'rgba(0, 0, 0, 0.7)'
		              }),
		              fill: new ol.style.Fill({
		                color: 'rgba(222, 222, 222, 0.5)'
		              })
		            })
		       }),
		       geometryFunction: geometryFunction
	   });	   
	   map.addInteraction(ddraw);
	   ddraw.on('drawend',
	  	   function(evt) {
	  	    	var geom= evt.feature.getGeometry();
	  		 	saveLocationAndArea(geom);
	  		 	return false;
	  	   }, this);
	  }else
	  {
		  drapDRAW_LAYER();
	  }
}
//移除绘制图层
function drapDRAW_LAYER(){
	 map.removeInteraction(ddraw);
	if(layers.DRAW_LAYER){
		layers.DRAW_LAYER.setSource(null);
		map.removeLayer(layers.DRAW_LAYER);
		layers.DRAW_LAYER=null;
	}
}

function beginModifyFts(fts,ftType) {
	if(selectHanlder)
		map.removeInteraction(selectHanlder);
	if(modifyHanlder)
		map.removeInteraction(modifyHanlder);

	if (layers.DRAW_LAYER) {
		clearLayers('DRAW_LAYER');
	}
	var features =new ol.format.GeoJSON().readFeatures(fts);
	layers.DRAW_LAYER = new ol.layer.Vector({
		
		source : new ol.source.Vector({ features : features	}),
		style: new ol.style.Style({
	          fill: new ol.style.Fill({
	            color: 'rgba(255, 255, 255, 0.2)'
	          }),
	          stroke: new ol.style.Stroke({
	            color: '#ffcc33',
	            width: 2
	          }),
	          image: new ol.style.Circle({
	            radius: 7,
	            fill: new ol.style.Fill({
	              color: '#ffcc33'
	            })
	          })
	     })
	});
	layers.DRAW_LAYER.setZIndex(layerConfig.DRAW_LAYER.ZINDEX);
	map.addLayer(layers.DRAW_LAYER);

	//选择器
	selectHanlder= new ol.interaction.Select({
	        style:overlayStyle,
	        layers:[layers.DRAW_LAYER]
	});      
	//修改器
	modifyHanlder = new ol.interaction.Modify({
		 features: selectHanlder.getFeatures(),
	        style: overlayStyle
	});
	//修改结束
	modifyHanlder.on('modifyend',
   	      function(evt) {
   	    	var geom= evt.features.item(0).getGeometry();
   	    	editdrawCallFunc='edit';
   		 	saveLocationAndArea(geom);
   	 }, this);
	map.addInteraction(selectHanlder);
	map.addInteraction(modifyHanlder);
}


