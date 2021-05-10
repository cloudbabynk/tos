//版权提示
 var marribute  =   new ol.Attribution({ html: '© ' + '广州港'  });
 var cright = new ol.control.Attribution({tipLabel:'版权'});
 //全屏
 var mfs = new ol.control.FullScreen({tipLabel:'全屏'});
 var styleCache={};
 var wgs84Sphere = new ol.Sphere(6378137);//计算面积长度的工具
//坐标
 var mousePositionControl = new ol.control.MousePosition({
        coordinateFormat: ol.coordinate.createStringXY(6),
       projection: 'EPSG:4326'//,

      });
 
//比例尺
 var scaleLineControl = new ol.control.ScaleLine();
 //点
 function npoint(x,y){
	  return new ol.geom.Point(ol.proj.fromLonLat([x,y]));
 }
 
 function npolygon(coordinates){
	  return new ol.geom.Polygon(coordinates,"Polygon");
 }
 
 function createPoint(cord){
	 var cs=cord.split(",");
	 return  new ol.geom.Point(parseFloat(cs[0]),parseFloat(cs[1]));
 }
 function sortNumber(a,b)
 {
 return a - b;
 }
 function getInnerExtent(feature){
	 var cords=feature.getGeometry().getCoordinates()[0];
	 var xArray=new Array();
	 for( var i=0;i<cords.length-1;i++){
		 xArray[i]=cords[i][0];
	 }
	 xArray.sort(sortNumber);
	 var x1=xArray[0],x2=xArray[1];
	 var y1=0;y2=0;
	 for(var i=0;i<cords.length-1;i++){
		 if(x1!=x2){
		 if(cords[i][0]==x1){
			 y1=cords[i][1];
		 }
		 if(cords[i][0]==x2){
			 y2=cords[i][1];
		 }
		 }else{
			 if(cords[i][0]==x1&&y1==0){
				 y1=cords[i][1];
				 continue;
			 }
			 if(cords[i][0]==x2&&y2==0){
				 y2=cords[i][1];
				 continue;
			 }
		 }
	 }
	 return [[x1,y1],[x2,y2]];
 }
 
 function createStyle(border,fill){
	  return createStyle(border,fill,3);
 }
var regs=[[/&nbsp;/g,' '],[/&gt;/g,'>'],[/&lt;/g,'<'],[/&quot;/g,'"'],[/&#39;/g,"'"],[/\\\\/g,'\\'],[/\\n/g,'\n'],[/\\r/g,'\r']];
 function dealText(text){
	// return text.replace(/&nbsp;/g,' ').replace().replace(/&gt;/)
	 if(text==null||text=='')
		 return  text; 
	 var t=text;
	 for(var i=0;i<regs.length;i++){
		 t=text.replace(regs[i][0],regs[i][1]);
	 }
	 return t;
 }
 
 function getFeatureData(lkey,feature){
	 var fc=layerConfig[lkey];
	 var id=feature.getId();
	 if(id.indexOf(fc.FID)==0){
		 if(giscache[lkey].features[parseInt(id.substring(2))]){
			 return giscache[lkey].features[parseInt(id.substring(2))];
		 }
	 }
	 return null;
 }
 //字符串分割
 function stringDivider(str, width, spaceReplacer) {
	  if (str.length > width) {
	    var p = width;
	    for (; p > 0 && (str[p] != ' ' && str[p] != '-'); p--) {
	    }
	    if (p > 0) {
	      var left;
	      if (str.substring(p, p + 1) == '-') {
	        left = str.substring(0, p + 1);
	      } else {
	        left = str.substring(0, p);
	      }
	      var right = str.substring(p + 1);
	      return left + spaceReplacer + stringDivider(right, width, spaceReplacer);
	    }
	  }
	  return str;
	}
 
 //创建多边形样式，border颜色，和填充颜色 
 function createStyle(border,fill ,width){
	   	var id=border+fill+width;
	   	if(!styleCache[id]){
	   		styleCache[id]=new ol.style.Style({
                stroke: new ol.style.Stroke({
	                  color:border,//'#e5e5ff'
	                  width: width
	                }),
	                fill: new ol.style.Fill({
	                  color: fill//#1EB61E
	                })
	              });
	   	}
		return [styleCache[id]];
	}
 
 //字符串转多边形--船舶
 function npolygonbyStr(corstr){	  
	  var cs=new Array();
	  var ccs=corstr.split(";");
	  var nn;
	 var cc;
	  for(var i=0;i<ccs.length;i++){
		  if(i==0||i==4||i==5||i===6||i==9||i==10)
			  continue;
		  nn=ccs[i].split(',');
		  cc=new Array();
		  cc[0]=parseFloat(nn[0]);
		  cc[1]=parseFloat(nn[1]);
		  cs.push(ol.proj.fromLonLat(cc));
		 // cs.push(new Array[parseFloat(nn[0]),parseFloat(nn[1])]);
	  }
	  var go= new ol.geom.Polygon(null);
	  go.setCoordinates(cs);
	  return go;
 }
 
 
 
var zoomslider = new ol.control.ZoomSlider();
//放大缩小
var zoomb = new ol.control.Zoom({zoomInTipLabel:'放大',zoomOutTipLabel:'缩小'});


var maprot =  new ol.interaction.DragRotateAndZoom();

var upId = -1;
var nowId = -1;

//根据像素去地图元素
function getFeatureByPixel(pixel){
	return map.forEachFeatureAtPixel(pixel, function(feature) {		 
	    return feature;
	  });
}

//取船舶形状样式
function spBigStyle(cor,rot,w,h){
	var zm = (mview.getZoom()-14)*2;
	w = zm*w/5;
	h = zm*h/5;
	var shapeStyle = new ol.style.Style({
		image: new ol.style.ArbitraryShape({
			fill: new ol.style.Fill({
				color: cor
			}),
			stroke:new ol.style.Stroke({color: 'black', width: 1}),
			size: [w+1, h+1],
			points: [[0, 0], [0, h*4/5], [w/2, h],[w, h*4/5],[w, 0], [0, 0]],
			rotation: (180+rot) * Math.PI / 180
		})
	});
	return shapeStyle;
}
var measureTooltip;
var measureTooltipElement;

function createMeasureTooltip() {
  if (measureTooltipElement) {
    measureTooltipElement.parentNode.removeChild(measureTooltipElement);
  }
  measureTooltipElement = document.createElement('div');
  measureTooltipElement.className = 'mtooltip mtooltip-measure';
  measureTooltip = new ol.Overlay({
    element: measureTooltipElement,
    offset: [0, -15],
    positioning: 'bottom-center'
  });
  //measureTooltip.setPosition(ol.proj.fromLonLat([124.9744, 37.0128]));
  map.addOverlay(measureTooltip);
}
var msource = new ol.source.Vector();
var mvector = new ol.layer.Vector({
  source: msource,
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

var helpTooltipElement;
var sketch;

var helpTooltip;
var continuePolygonMsg = '单击继续绘制区域';
var continueLineMsg = '单击继续测量距离';


var pointerMoveHandler = function(evt) {
  if (evt.dragging) {
	    return;
	  }
  var helpMsg = '单击开始测量';

  if (sketch) {
    var geom = (sketch.getGeometry());
    if (geom instanceof ol.geom.Polygon) {
      helpMsg = continuePolygonMsg;
    } else if (geom instanceof ol.geom.LineString) {
      helpMsg = continueLineMsg;
    }
  }

  helpTooltipElement.innerHTML = helpMsg;
  helpTooltip.setPosition(evt.coordinate);

  $(helpTooltipElement).removeClass('hidden');
};

var typeSelect = 'Length';
var draw; // global so we can remove it later

function getHeight(coordinets){
	var length=0,tmp=0;
	var minx,miny,maxx,maxy
	for(var i=0;i<coordinets.length;i++){
		if(i==0){
			minx=maxx=coordinets[i][0];
			miny=maxy=coordinets[i][1];
		}else{
			if(minx>coordinets[i][0])
				minx=coordinets[i][0];
			if(maxx<coordinets[i][0])
				maxx=coordinets[i][0];
			if(miny>coordinets[i][1])
				miny=coordinets[i][1];
			if(maxy<coordinets[i][1]){
				maxy=coordinets[i][1];
			}
		}
	}
	var xh=wgs84Sphere.haversineDistance([minx,miny], [maxx,miny]);
	var yh=wgs84Sphere.haversineDistance([minx,miny], [minx,maxy]);
	length=xh;
	if(length>yh)
		length=yh;
	return length;
}


var formatLength = function(line) {
  var length;
    var coordinates = line.getCoordinates();
    length = 0;
    var sourceProj = map.getView().getProjection();
    for (var i = 0, ii = coordinates.length - 1; i < ii; ++i) {
      var c1 = ol.proj.transform(coordinates[i], sourceProj, 'EPSG:4326');
      var c2 = ol.proj.transform(coordinates[i + 1], sourceProj, 'EPSG:4326');
      length += wgs84Sphere.haversineDistance(c1, c2);
    }
  var output;
  if (length > 100) {
    output = (Math.round(length / 1000 * 100) / 100) +
        ' ' + '千米';
  } else {
    output = (Math.round(length * 100) / 100) +
        ' ' + '米';
  }
  return output;
};


function computeLength(c1,c2){
	var tp='';
	var length = wgs84Sphere.haversineDistance(c1, c2);
	 if (length > 100) {
		 tp = (Math.round(length / 1000 * 100) / 100) +
		        ' ' + '千米';
	} else {
		tp = (Math.round(length * 100) / 100) +
		        ' ' + '米';
	}
	return tp;
}

var formatArea = function(polygon) {

	 var sourceProj = map.getView().getProjection();
	    var geom = (polygon.clone().transform(
	        sourceProj, 'EPSG:4326'));
	 var length;
	  var coordinates = geom.getLinearRing(0).getCoordinates();
	    length = 0;
	    var sourceProj = map.getView().getProjection();
	    var c1 = ol.proj.transform(coordinates[coordinates.length-2], sourceProj, 'EPSG:4326');
	      var c2 = ol.proj.transform(coordinates[coordinates.length-1], sourceProj, 'EPSG:4326');
	      length = wgs84Sphere.haversineDistance(c1, c2);
	  var output;
	  output = (Math.round(length * 100) / 100) +
      ' ' + '米';
	  return output;
};

 /**
 * Creates a new help tooltip
 */
function createHelpTooltip() {
  if (helpTooltipElement) {
    helpTooltipElement.parentNode.removeChild(helpTooltipElement);
  }
  helpTooltipElement = document.createElement('div');
  helpTooltipElement.className = 'mtooltip hidden';
  helpTooltip = new ol.Overlay({
    element: helpTooltipElement,
    offset: [15, 0],
    positioning: 'center-left'
  });
  map.addOverlay(helpTooltip);
}
/*
 * 监听draw
 * @param {Object} evt
 */

function addMeasureInteraction() {
	map.addLayer(mvector);
	mvector.setZIndex(1001);
	$("#closeM").show();
	$('#measureButton').attr("disabled",true);
  var type = 'LineString';
  draw = new ol.interaction.Draw({
    source: msource,
    type: /** @type {ol.geom.GeometryType} */ (type),
    style: new ol.style.Style({
      fill: new ol.style.Fill({
        color: 'rgba(255, 255, 255, 0.2)'
      }),
      stroke: new ol.style.Stroke({
        color: 'rgba(0, 0, 0, 0.5)',
        width: 2
      }),
      image: new ol.style.Circle({
        radius: 2,
        stroke: new ol.style.Stroke({
          color: 'rgba(0, 0, 0, 0.7)'
        }),
        fill: new ol.style.Fill({
          color: 'rgba(255, 255, 255, 0.2)'
        })
      })
    })
  });
  map.addInteraction(draw);

  createMeasureTooltip();
  createHelpTooltip();

  var listener;
  draw.on('drawstart',
      function(evt) {
        // set sketch
        sketch = evt.feature;

        /** @type {ol.Coordinate|undefined} */
        var tooltipCoord = evt.coordinate;

        listener = sketch.getGeometry().on('change', function(evt) {
          var geom = evt.target;
          var output=0;
          if (geom instanceof ol.geom.Polygon) {
            output = formatArea(/** @type {ol.geom.Polygon} */ (geom));
            tooltipCoord = geom.getInteriorPoint().getCoordinates();
          } else if (geom instanceof ol.geom.LineString) {
            output = formatLength(/** @type {ol.geom.LineString} */ (geom));
            tooltipCoord = geom.getLastCoordinate();
          }
          measureTooltipElement.innerHTML = output;
          measureTooltip.setPosition(tooltipCoord);
        });
      }, this);

  draw.on('drawend',
      function() {
        measureTooltipElement.className = 'mtooltip mtooltip-static';
        measureTooltip.setOffset([0, -7]);
        // unset sketch
        sketch = null;
        // unset tooltip so that a new one can be created
        measureTooltipElement = null;
        createMeasureTooltip();
        ol.Observable.unByKey(listener);
      }, this);
  
  measureId = '1';
}

//取字符串数组中的位置
function getIndexInArray(str,array){
	if(array!=null&&array.length>0){
		for(var i=0;i<array.length;i++){
			if(array[i]==str)
				return i;
		}
	}
	return -1;
}

function closeMeasure(){
	map.removeInteraction(draw);
	measureId = '0';
	mvector.getSource().clear();
	map.removeLayer(mvector);
	//map.removeOverlay(measureTooltip);
//	map.removeOverlay(helpTooltip);
	$(".ol-overlay-container").remove();
	$("#closeM").hide();
	$('#measureButton').attr("disabled",false);
	createMeasureTooltip();
	}
function RGBtoRGBA(r, g, b){
    if((g==void 0) && (typeof r == 'string')){
        r = r.replace(/^\s*#|\s*$/g, '');
        if(r.length == 3){
            r = r.replace(/(.)/g, '$1$1');
        }
        g = parseInt(r.substr(2, 2), 16);
        b = parseInt(r.substr(4, 2), 16);
        r = parseInt(r.substr(0, 2), 16);
    }

    var min, a = ( 255 - (min = Math.min(r, g, b)) ) / 255;

    return {
        r    : r = 0|( r - min ) / a,
        g    : g = 0|( g - min ) / a,
        b    : b = 0|( b - min ) / a,
        a    : a = (0|1000*a)/1000,
        rgba : 'rgba(' + r + ', ' + g + ', ' + b + ', ' + a + ')'
    };
}


var duration = 5000;
var flashKey=[];
function flash(feature) {
  var start = new Date().getTime();
  var listenerKey;

  function animate(event) {
    var vectorContext = event.vectorContext;
    var frameState = event.frameState;
   
    var flashGeom = feature.getGeometry().clone();
    var geoType=flashGeom.getType();
    if(geoType=="Polygon")
    {
    	var codXy=flashGeom.getExtent();
    	var x=0,y=0;
    	x=(Number(codXy[0])+Number(codXy[2]))/2;
    	y=(Number(codXy[1])+Number(codXy[3]))/2;
    	flashGeom=new ol.geom.Point([x,y]);
    }
    var elapsed = frameState.time - start;
    var elapsedRatio = elapsed / duration;
    // radius will be 5 at start and 30 at end.
    var radius = ol.easing.easeOut(elapsedRatio) * 10 + 5;
    var opacity = ol.easing.easeOut(1 - elapsedRatio);

    var flashStyle = new ol.style.Circle({
      radius: radius,
      snapToPixel: false,
      stroke: new ol.style.Stroke({
        color: 'rgba(255, 0, 0, ' + opacity + ')',
        width: 2
      })
    });

    vectorContext.setImageStyle(flashStyle);
    vectorContext.drawPointGeometry(flashGeom, null);
    if (elapsed > duration) {
    //  ol.Observable.unByKey(listenerKey);
    	start= new Date().getTime();
      return;
    }
    // tell OL3 to continue postcompose animation
    map.render();
  }
  listenerKey = map.on('postcompose', animate);
  flashKey[flashKey.length]=listenerKey;
}

function cancelflash(){
	for(var i=0;i<flashKey.length;i++){
		 ol.Observable.unByKey(flashKey[i]);
	}
}

//十六进制颜色值域RGB格式颜色值之间的相互转换  
/*
var sRgb = "RGB(255, 255, 255)" , sHex = "#00538b";  
var sHexColor = sRgb.colorHex();//转换为十六进制方法<code></code>  
var sRgbColor = sHex.colorRgb();//转为RGB颜色值的方法  
*/
//十六进制颜色值的正则表达式  
var reg = /^#([0-9a-fA-f]{3}|[0-9a-fA-f]{6})$/;  
/*RGB颜色转换为16进制*/  
String.prototype.colorHex = function(){  
  var that = this;  
  if(/^(rgb|RGB)/.test(that)){  
      var aColor = that.replace(/(?:||rgb|RGB)*/g,"").split(",");  
      var strHex = "#";  
      for(var i=0; i<aColor.length; i++){  
          var hex = Number(aColor[i]).toString(16);  
          if(hex === "0"){  
              hex += hex;   
          }  
          strHex += hex;  
      }  
      if(strHex.length !== 7){  
          strHex = that;    
      }  
      return strHex;  
  }else if(reg.test(that)){  
      var aNum = that.replace(/#/,"").split("");  
      if(aNum.length === 6){  
          return that;      
      }else if(aNum.length === 3){  
          var numHex = "#";  
          for(var i=0; i<aNum.length; i+=1){  
              numHex += (aNum[i]+aNum[i]);  
          }  
          return numHex;  
      }  
  }else{  
      return that;      
  }  
};  

//-------------------------------------------------  

/*16进制颜色转为RGB格式*/  
String.prototype.colorRgb = function(){  
  var sColor = this.toLowerCase();  
  if(sColor && reg.test(sColor)){  
      if(sColor.length === 4){  
          var sColorNew = "#";  
          for(var i=1; i<4; i+=1){  
              sColorNew += sColor.slice(i,i+1).concat(sColor.slice(i,i+1));     
          }  
          sColor = sColorNew;  
      }  
      //处理六位的颜色值  
      var sColorChange = [];  
      for(var i=1; i<7; i+=2){  
          sColorChange.push(parseInt("0x"+sColor.slice(i,i+2)));    
      }  
      return "RGB(" + sColorChange.join(",") + ")";  
  }else{  
      return sColor;    
  }  
};  
  


