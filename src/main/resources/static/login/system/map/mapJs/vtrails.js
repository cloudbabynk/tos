
var ttime=20;
var trailsPoints=null;
var trailsstyles = {
		  'point':style = new ol.style.Style({
              image: new ol.style.Circle({
                  radius: 3,
                  stroke: new ol.style.Stroke({
                    color: '#fff'
                  }),
                  fill: new ol.style.Fill({
                    color: '#b7b7b7'
                  })
                }),
                text: new ol.style.Text({
                  fill: new ol.style.Fill({
                    color: '#000'
                  })
                })
              }),
		  'route': new ol.style.Style({
		    stroke: new ol.style.Stroke({
		      width: 6, color: [0, 234, 255, 0.8]
		    })
		  }),
		  'icon': new ol.style.Style({
			  image: new ol.style.Circle({
			      radius: 5,
			      snapToPixel: false,
			      fill: new ol.style.Fill({color: 'rgba(0,0,0,0.8)'}),
			      stroke: new ol.style.Stroke({
			        color:'white', width: 2
			      })
			    })
		  }),
		  'geoworkMarker': new ol.style.Style({
			  image: layerConfig.TRIAL.workerIcon
		  })
		,
		'geotruckMarker': new ol.style.Style({
			  image: layerConfig.TRIAL.machIcon
		})
	};
var currentMarkStyle=null;
var route;
var routeCoords;
var routeLength;
var routeFeature;
var geoMarker;
var startMarker;
var endMarker;

var trailInfo={};//所有轨迹定位的参数  新定位用的 老定位的参数 还在原来的地方没动
function doTrails(){
	var beginTime=$('#beginTrailsTime').datetimebox('getValue');
	var endTime=$('#endTrailsTime').datetimebox('getValue');
	var lur="manage/getTrailsFeature.action?did="+tdid+"&begin="+beginTime+"&idtype="+idtype+"&end="+endTime+"&tw="+ttime;
	//beginToTrail();
	var objPam={};
	objPam.objStr=JSON.stringify(trailInfo);
	$.ajax({
		type : "POST",
		url : lur,
		data:objPam,
		dataType : 'json',
		success : function(datas) {
			currentMarkStyle=null;
			if(showTrails(datas))
				$('#trailsButtons').show();
			
			trailInfo={};
		}
    });
}



function showTrails(datas){
	var fc = (new ol.format.GeoJSON()).readFeatures(datas);	
	if(layers.TRIAL){
		layers.TRIAL.getSource().clear();
		layers.TRIAL.setVisible(true);
		map.removeLayer(layers.TRIAL);
		layers.TRIAL=null;
	}
	if(fc!=null&&fc.length>0){
		 var routeInfo=fc[fc.length-1];
		 trailsPoints=fc;
		 route=routeInfo.getGeometry();
		 routeCoords = route.getCoordinates();
		 routeLength = routeCoords.length;
		 routeFeature=  new ol.Feature({
			  type: 'route',
			  geometry: route
		});
		 geoMarker = new ol.Feature({
			  type: 'geoMarker',
			  geometry: new ol.geom.Point(routeCoords[0])
			});
		 startMarker = new ol.Feature({
			  type: 'icon',
			  geometry: new ol.geom.Point(routeCoords[0])
			});
		 endMarker = new ol.Feature({
			  type: 'icon',
			  geometry: new ol.geom.Point(routeCoords[routeLength - 1])
			});
		 layers.TRIAL= new ol.layer.Vector({
			  source: new ol.source.Vector({
				    features: [ startMarker, endMarker,routeFeature, geoMarker]
				  }),
				  style: function(feature, resolution) {
				    if (animating && feature.get('type') === 'geoMarker') {
				      return [];
				    }
				    if(feature.get('type') === 'geoMarker'){
				    	if(currentMarkStyle==null){
				    	//M 代表堆场员和管理员
				    	if(routeInfo.get("r")=="M")
				    	{
				    		var ot=routeInfo.get('ot');
							var key=null;
							if(ot=='M'){
								key='MN';//管理员
							}else if(ot=='G')
							{
								key='DC';//单船指导员
							}else if(ot=='A')
							{
								key='AJ';//安检员
							}else if(ot=='B')
							{
								key='BZ';//班组长
							}
							else
								key="KCY";//堆场员
							var img=getManagerImage(key,'1');
							var src=img.getSrc();
							var imageAnchor=src.substring(src.indexOf(".png")-3,src.indexOf(".png")).replace("_","");
							var ban=0;
							
							if(!isNaN(imageAnchor))
							{
								ban=Number(imageAnchor);
							}else
							{
								var an=temp.getAnchor();
								
								if(mview.getZoom()<18)
									ban=16;//返回图标配置信息
								else if (mview.getZoom()<=19)
									ban=32;
								else if(mview.getZoom()<=20)
									ban=64;
								else
									ban=128;
							}
							currentMarkStyle = new ol.style.Style({
								image:new ol.style.Icon({
									 anchor:[ban/2,ban/2],
									 anchorXUnits: 'pixels',
									 anchorYUnits: 'pixels',
									 src:src
								}),
								text: new ol.style.Text({

									textAlign : 'left',
									textBaseline : 'middle',
									offsetX : 5,
									offsetY : 12,
									font : 'normal 12px Arial',
							          fill: new ol.style.Fill({
							            color: '#000'
							          })
							        })
							});
				    	}else if(routeInfo.get("r")=="E")//E:代表机械
				    	{
				    	
				    		var type=routeInfo.get("MACH_TYP_COD");
							var mj=isMj(type);
							var image=null;
							var offestx=5;
							if(mj){
								offestx=20;
								var k=null;
								key='readyIcon';
								image=groupIcon.MACH.children.EM1[key];
							}else{
								if(type!=null&&type!='')
								image=getImage('MACH',type,'1');
								var oc=routeInfo.get('outcar');
								var ik='on';
								if(oc=='1'){
									ik=ik+"_32";
									image=groupIcon.outcar[ik];
								}
							}
							var src=image.getSrc();
							var imageAnchor=src.substring(src.indexOf(".png")-3,src.indexOf(".png")).replace("_","");
							var ban=0;
							
							if(!isNaN(imageAnchor))
							{
								ban=Number(imageAnchor);
							}else
							{
								var an=temp.getAnchor();
								
								if(mview.getZoom()<18)
									ban=16;//返回图标配置信息
								else if (mview.getZoom()<=19)
									ban=32;
								else if(mview.getZoom()<=20)
									ban=64;
								else
									ban=128;
							}
							currentMarkStyle = new ol.style.Style({
								image:new ol.style.Icon({
									 anchor:[ban/2,ban/2],
									 anchorXUnits: 'pixels',
									 anchorYUnits: 'pixels',
									 src:src
								}),
								text: new ol.style.Text({
									textAlign : 'left',
									textBaseline : 'middle',
									offsetX : offestx,
									offsetY : 5,
							          fill: new ol.style.Fill({
							            color: '#000000'
							          })
							        })
							});
				    	}
				    }
				    return [currentMarkStyle];
				    }else  return [trailsstyles[feature.get('type')]];
				  }
				});
		 layers.TRIAL.setZIndex(layerConfig.TRIAL.ZINDEX);
		 map.addLayer(layers.TRIAL);
		 fc.pop();
		 layers.TRIALPOINTS=new ol.layer.Vector({
			 source: new ol.source.Vector({
				    features:fc
				  }),
			 style:function(feature, resolution) {
				 if(mview.getZoom()>=18){
				style=trailsstyles['point'];
				if(showName){
					var t=feature.get('tt');
					style.getText().setText(t);
				}else{
					style.getText().setText('');
				}
				return style;
				}
			 } 
		 });
		 layers.TRIALPOINTS.setZIndex(layerConfig.TRIAL.ZINDEX+1);
		 map.addLayer(layers.TRIALPOINTS);
	}else{
		$.messager.alert('警告','未找到轨迹点');
		endTrail();
		return false;
	}
	return true;
}

var moveFeature = function(event) {
	  var vectorContext = event.vectorContext;
	  var frameState = event.frameState;
	  if (animating) {
	    var elapsedTime = frameState.time - now;
	    var index = Math.round(speed * elapsedTime / 1000);
	    if (index >= routeLength) {
	      stopAnimation(true);
	      return;
	    }
	    var currentPoint = new ol.geom.Point(routeCoords[index]);
	    var feature = new ol.Feature(currentPoint);
	    if(routeCoords[index+1]){
			rot=getAngle(routeCoords[index][0],routeCoords[index][1],routeCoords[index+1][0],routeCoords[index+1][1]);
			currentMarkStyle.getImage().setRotation(rot* Math.PI / 180);
		    vectorContext.drawFeature(feature, currentMarkStyle);
	    }
	  }
	  // tell OL3 to continue the postcompose animation
	  map.render();
	};
	/**
	 * 角度  |  顺时针180  逆时针 -180
	 * @param startx
	 * @param starty
	 * @param endx
	 * @param endy
	 * @return
	 */
	function getAngle( startx,  starty,  endx,  endy)
	{
	  //除数不能为0
      var tan = Math.atan(Math.abs((endy - starty) / (endx - startx))) * 180 / Math.PI;
      if (endx > startx && endy > starty)//第一象限
      {
          return 90-tan;
      }
      else if (endx < startx && endy > starty)//第二象限
      {
          return tan-90;
      }
      else if (endx < startx && endy < starty)//第三象限
      {
          return -(tan+90);
      }
      else
      {
          return 90+tan;
      }
	}
	function startAnimation() {
		  if (animating) {
		    stopAnimation(false);
		    currentMarkStyle.getImage().setRotation(0);
		    startButton.value = '回放';
		  } else {
		    animating = true;
		    now = new Date().getTime();
		    speed = document.getElementById('speed').value;
		    startButton.value = '取消';
		    // hide geoMarker
		    geoMarker.setStyle(null);
		    // just in case you pan somewhere else
		    //map.getView().setCenter(center);
		    map.on('postcompose', moveFeature);
		    map.render();
		  }
	}
	
		/**
		 * @param {boolean} ended end of animation.
		 */
	function stopAnimation(ended) {
		  animating = false;
		  startButton.value = '回放';
	
		  // if animation cancelled set the marker at the beginning
		  var coord = ended ? routeCoords[routeLength - 1] : routeCoords[0];
		  /** @type {ol.geom.Point} */ (geoMarker.getGeometry())
		    .setCoordinates(coord);
		  //remove listener
		  map.un('postcompose', moveFeature);
	}
	
	var needFlash=[];
	function beginToTrail(){
		for(var i=0;i<layerKeys.length;i++){
			if(layerKeys[i]!='TRIAL'&&layerConfig[layerKeys[i]].show){
				if(layers[layerKeys[i]]!=null&&layers[layerKeys[i]]!=undefined){
					layers[layerKeys[i]].setVisible(false);
				}
				if(layerConfig[layerKeys[i]].si){
					window.clearInterval(layerConfig[layerKeys[i]].si);
					layerConfig[layerKeys[i]].si=null;
				}
			}
		}
	}
	
	function endTrail(){
		for(var i=0;i<layerKeys.length;i++){
			if(layerKeys[i]!='TRIAL'&&layerConfig[layerKeys[i]].show){
				if(layers[layerKeys[i]]!=null&&layers[layerKeys[i]]!=undefined)
				layers[layerKeys[i]].setVisible(true);
			}
		}	
		doFlush() ;
	}
	
	function closeTrail(){
		stopAnimation(true);
		layers.TRIAL.getSource().clear();
		layers.TRIAL.setVisible(true);
		map.removeLayer(layers.TRIAL);
		 layers.TRIALPOINTS.getSource().clear();
		 map.removeLayer( layers.TRIALPOINTS);
		 layers.TRIALPOINTS=null;
		layers.TRIAL=null;
		endTrail();
		$('#trailsButtons').hide();
	}
	
	var speed, now;
	var animating = false;
	var speedInput = document.getElementById('speed');
	var startButton = document.getElementById('beginAn');
	startButton.addEventListener('click', startAnimation, false);