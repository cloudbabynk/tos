//图形样式函数
//集装箱样式 layers 加载集装箱使用

var colorS="CNTR_CLASS";//箱类型
var colorF="CNTR_CLASS";//fieldCod
var cntrPosStyles={};//集装箱在场图样式



function initCntrStyle(){//初始化集装箱的所有的样式
	var args={};
	args.value=companyCod;
	$.ajax({
		type : "POST",
		url : "/webresources/login/ColorSet/findColor",
		contentType:"application/json",
		dataType:"JSON",
		async:false,
		data:$.toJSON(args),
		success : function(datas) {
			var colorSet=datas.entity.rows;
			initCntrC(colorSet,new Array());

		}
	});
	
}

//初始化在场集装箱样式
function initCntrC(datas,dataCus){//初始化集装箱颜色样式
	for(var i=0;i<datas.length;i++){
		var d=datas[i];
		for(var j=0;j<dataCus.length;j++){
			var cus=dataCus[j];
			if(d.COLOR_FILED_COD==cus.COLOR_FILED_COD&&d.COLOR_FILED_VALUE==cus.COLOR_FILED_VALUE){
				d.COLOR=cus.COLOR;
				break;
			}
		}
	}
	
	if(datas!=null&&datas.length>0){
		for(var i=0;i<datas.length;i++){
			var d=datas[i];
			var color=d.COLOR.replace("0x","#");
			var field=d.COLOR_FILED_COD+"_"+d.COLOR_FILED_VALUE;
			cntrPosStyles[field]=new ol.style.Style({
			      	stroke: new ol.style.Stroke({
			      	  color: '#D4D2D2',
			          width: 1
			        }),
			        fill: new ol.style.Fill({
			        	color: color
			        }),
			        text:new ol.style.Text({
					    textAlign: 'center',// 'left', 'right', 'center', 'end' or 'start'.
					    font: 'normal 12px Arial',
					    fill: new ol.style.Fill({color: 'black'}),
					    text:''
			        })
			 });
			
		}
	}
}

//加载在场集装箱样式函数
var portCarPosFunction=function(feature){
	var txt=null;
	if(mview.getZoom()>20){
		txt= new ol.style.Text({
				textAlign : 'left',
				textBaseline : 'center',
			    font: 'normal 11px Arial',
				stroke : new ol.style.Stroke({
					color : '#f1f1f1',
					width : 2
				}),
				offsetX:-50,
				text:feature.get("txt")
		});
	}

	var col=feature.get("col");
		return new ol.style.Style({
	    stroke : new ol.style.Stroke({
			color : '#817968',
			width : 1
		}),
		fill : new ol.style.Fill({
			color: col,
		}),
		text:txt,
	    offsetX: 0,
	    offsetY: 0,
	    rotation: 0
		
	})

	
}
//加载在场集装箱样式函数
var LockPosStyles=function(feature){
	var txt=null;
	if(mview.getZoom()>20){
		txt= new ol.style.Text({
				textAlign : 'left',
				textBaseline : 'center',
			    font: 'normal 11px Arial',
				stroke : new ol.style.Stroke({
					color : '#f1f1f1',
					width : 2
				}),
				offsetX:-50,
				text:feature.get("txt")
		});
	}

	var col=feature.get("col");
		return new ol.style.Style({
	    stroke : new ol.style.Stroke({
			color : '#FFFF66',
			width : 1
		}),
		fill : new ol.style.Fill({
			color: col,
		}),
		text:txt,
	    offsetX: 0,
	    offsetY: 0,
	    rotation: 0
		
	})

	
}


//加载贝型图样式
var cntrPicFunction=function(cntrInfo){
	if(cntrInfo[colorS]){
		var field=colorF+"_"+cntrInfo[colorS];
		return cntrPosStyles[field].getFill('color').getColor();
	}
	return "#FFFFFF";
}
//面的高亮样式
function plyLightStyle(feature, resolution) {

	return new ol.style.Style({
	    stroke : new ol.style.Stroke({
			color : '#FF0042',
			width : 2
		}),
		fill : new ol.style.Fill({
			color: 'rgba(255,0, 0,0)',
		}),
	    offsetX: 0,
	    offsetY: 0,
	    rotation: 0
		
	});


}
function yAreaStyle(feature, resolution) {
	return new ol.style.Style({
         fill : new ol.style.Fill({
            color: 'rgba(255, 255, 255, 0.2)'
          }),
   			 stroke : new ol.style.Stroke({
            color: '#11D707',
            width: 1
		  })
		});
}

//绘制修改的样式 vdraw.js 使用
var overlayStyle = (function() {
    var styles = {};
    styles['Polygon'] = [
      new ol.style.Style({
    	  stroke : new ol.style.Stroke({
  			color : '#817968',
  			width : 1
  		}),
        fill: new ol.style.Fill({
          color: '#817968'
        })
      }),
      new ol.style.Style({
        stroke: new ol.style.Stroke({
          color: '#817968',
          width: 5
        })
      }),
      new ol.style.Style({
        stroke: new ol.style.Stroke({
          color: '#817968',
          width: 3
        })
      })
    ];
    styles['MultiPolygon'] = styles['Polygon'];

    styles['LineString'] = [
      new ol.style.Style({
        stroke: new ol.style.Stroke({
          color: [255, 255, 255, 1],
          width: 5
        })
      }),
      new ol.style.Style({
        stroke: new ol.style.Stroke({
          color: [0, 153, 255, 1],
          width: 3
        })
      })
    ];
    styles['MultiLineString'] = styles['LineString'];

    styles['Point'] = [
      new ol.style.Style({
        image: new ol.style.Circle({
          radius: 7,
          fill: new ol.style.Fill({
            color: [0, 153, 255, 1]
          }),
          stroke: new ol.style.Stroke({
            color: [255, 255, 255, 0.75],
            width: 1.5
          })
        }),
        zIndex: 100000
      })
    ];
    styles['MultiPoint'] = styles['Point'];

    styles['GeometryCollection'] = styles['Polygon'].concat(styles['Point']);

    return function(feature) {
      return styles[feature.getGeometry().getType()];
    };
  })();



/**
 * 定位样式
 */
var LockPosStyles = {
    'Polygon': new ol.style.Style({
      stroke: new ol.style.Stroke({
        color: 'blue',
        lineDash: [4],
        width: 3
      }),
      fill: new ol.style.Fill({
        color: '#817968'
      })
    })	
  };
/**
 * 定位样式函数
 */
var LockPosFunction = function(feature) {
   return LockPosStyles[feature.getGeometry().getType()];
};


/**
 * 在场船样式函数
 */
var shipStyleFunction = function(feature) {
	var moorDir=feature.get("MOOR_DIRECTION");
	var textStr=feature.get("CN_SHIP_NAM");
	var shipStyle="";
	if(moorDir=="L"||moorDir=="R"){
		shipStyle=new ol.style.Style({
		      stroke: new ol.style.Stroke({
		          color: '#D4D2D2',
		          //lineDash: [4],
		          width:2
		        }),
		        fill: new ol.style.Fill({
		          color:  '#FFFF66'
		          //E37171
		        }),
				text:new ol.style.Text({
				    textAlign: 'left',
				    textBaseline: 'center',
				    offsetY:10,
				    rotateWithView:false,
				    rotation:67.5,
				    scale:1,
				    font: 'blod 14px Arial',
				    fill: new ol.style.Fill({color: '#333333'})
			})
		});
		
	}else{
		shipStyle=new ol.style.Style({
		      stroke: new ol.style.Stroke({
		          color: '#D4D2D2',
		          //lineDash: [4],
		          width:2
		        }),
		        fill: new ol.style.Fill({
		          color:  '#FFFF66'
		          //E37171
		        }),
				text:new ol.style.Text({
				    textAlign: 'left',
				    textBaseline: 'ideographic',
				    font: 'blod 14px Arial',
				    fill: new ol.style.Fill({color: '#333333'})
			})
		});
	}
	
	var zoom=18;
	if(companyCod=="NCT"){//南沙一期
		zoom=18;
	}
	if(companyCod=="GOCT"){//南沙二期
		zoom=18;
	}
	if(companyCod=="NICT"){//南沙三期
		zoom=18;
	}
	if(companyCod=="HPCT"){//黄埔
		zoom=17;
	}
	if(companyCod=="XSCT"){//新沙
		zoom=18;
	}
	if(companyCod=="GCT"){//集装箱
		zoom=17;
	}
	if(mview.getZoom()<zoom){
		shipStyle.getText().setText("");
	}else{
		shipStyle.getText().setText(textStr);
	}
   return shipStyle;
};
/**
 * 拖车显示样式
 */
var TCMachStyleFunction = function(feature) {
	var textTcStr=feature.get("MACH_COD");
	var machStat=feature.get("MACH_STAT");
	var machTcStyle="";
	var srcStyle="";
	var anchorStyle;
	if(machStat=="B"){//故障 灰色
		if(companyCod!="HPCT"&&companyCod!="GCT"){
			if(mview.getZoom()<17){
				anchorStyle=[8,8];
				srcStyle="/login/gisMap/image/tcs_16.png";
			}else if(mview.getZoom()==17){
				anchorStyle=[12,12];
				srcStyle="/login/gisMap/image/tcs_24.png";
			}else if(mview.getZoom()==18){
				anchorStyle=[16,16];
				srcStyle="/login/gisMap/image/tcs_32.png";
			}else if(mview.getZoom()==19){
				anchorStyle=[24,24];
				srcStyle="/login/gisMap/image/tcs_48.png";
			}else if(mview.getZoom()==20){
				anchorStyle=[32,32];
				srcStyle="/login/gisMap/image/tcs_64.png";
			}else if(mview.getZoom()==21){
				anchorStyle=[64,64];
				srcStyle="/login/gisMap/image/tcs_128.png";
			}else{
				anchorStyle=[128,128];
				srcStyle="/login/gisMap/image/tcs_256.png";
			}
		}else{
			if(mview.getZoom()<16){
				anchorStyle=[8,8];
				srcStyle="/login/gisMap/image/tcs_16.png";
			}else if(mview.getZoom()==16){
				anchorStyle=[12,12];
				srcStyle="/login/gisMap/image/tcs_24.png";
			}else if(mview.getZoom()==17){
				anchorStyle=[16,16];
				srcStyle="/login/gisMap/image/tcs_32.png";
			}else if(mview.getZoom()==18){
				anchorStyle=[24,24];
				srcStyle="/login/gisMap/image/tcs_48.png";
			}else if(mview.getZoom()==19){
				anchorStyle=[32,32];
				srcStyle="/login/gisMap/image/tcs_64.png";
			}else if(mview.getZoom()==20){
				anchorStyle=[64,64];
				srcStyle="/login/gisMap/image/tcs_128.png";
			}else{
				anchorStyle=[128,128];
				srcStyle="/login/gisMap/image/tcs_256.png";
			}
		}
	}
	if(machStat=="C"){//关机  深红
		if(companyCod!="HPCT"&&companyCod!="GCT"){
			if(mview.getZoom()<17){
				anchorStyle=[8,8];
				srcStyle="/login/gisMap/image/tc_16.png";
			}else if(mview.getZoom()==17){
				anchorStyle=[12,12];
				srcStyle="/login/gisMap/image/tc_24.png";
			}else if(mview.getZoom()==18){
				anchorStyle=[16,16];
				srcStyle="/login/gisMap/image/tc_32.png";
			}else if(mview.getZoom()==19){
				anchorStyle=[24,24];
				srcStyle="/login/gisMap/image/tc_48.png";
			}else if(mview.getZoom()==20){
				anchorStyle=[32,32];
				srcStyle="/login/gisMap/image/tc_64.png";
			}else if(mview.getZoom()==21){
				anchorStyle=[64,64];
				srcStyle="/login/gisMap/image/tc_128.png";
			}else{
				anchorStyle=[128,128];
				srcStyle="/login/gisMap/image/tc_256.png";
			}
		}else{
			if(mview.getZoom()<16){
				anchorStyle=[8,8];
				srcStyle="/login/gisMap/image/tc_16.png";
			}else if(mview.getZoom()==16){
				anchorStyle=[12,12];
				srcStyle="/login/gisMap/image/tc_24.png";
			}else if(mview.getZoom()==17){
				anchorStyle=[16,16];
				srcStyle="/login/gisMap/image/tc_32.png";
			}else if(mview.getZoom()==18){
				anchorStyle=[24,24];
				srcStyle="/login/gisMap/image/tc_48.png";
			}else if(mview.getZoom()==19){
				anchorStyle=[32,32];
				srcStyle="/login/gisMap/image/tc_64.png";
			}else if(mview.getZoom()==20){
				anchorStyle=[64,64];
				srcStyle="/login/gisMap/image/tc_128.png";
			}else{
				anchorStyle=[128,128];
				srcStyle="/login/gisMap/image/tc_256.png";
			}
		}
	}
	if(machStat=="O"){//开机  绿色
		if(companyCod!="HPCT"&&companyCod!="GCT"){
			if(mview.getZoom()<17){
				anchorStyle=[8,8];
				srcStyle="/login/gisMap/image/tcg_16.png";
			}else if(mview.getZoom()==17){
				anchorStyle=[12,12];
				srcStyle="/login/gisMap/image/tcg_24.png";
			}else if(mview.getZoom()==18){
				anchorStyle=[16,16];
				srcStyle="/login/gisMap/image/tcg_32.png";
			}else if(mview.getZoom()==19){
				anchorStyle=[24,24];
				srcStyle="/login/gisMap/image/tcg_48.png";
			}else if(mview.getZoom()==20){
				anchorStyle=[32,32];
				srcStyle="/login/gisMap/image/tcg_64.png";
			}else if(mview.getZoom()==21){
				anchorStyle=[64,64];
				srcStyle="/login/gisMap/image/tcg_128.png";
			}else{
				anchorStyle=[128,128];
				srcStyle="/login/gisMap/image/tcg_256.png";
			}
		}else{
			if(mview.getZoom()<16){
				anchorStyle=[8,8];
				srcStyle="/login/gisMap/image/tcg_16.png";
			}else if(mview.getZoom()==16){
				anchorStyle=[12,12];
				srcStyle="/login/gisMap/image/tcg_24.png";
			}else if(mview.getZoom()==17){
				anchorStyle=[16,16];
				srcStyle="/login/gisMap/image/tcg_32.png";
			}else if(mview.getZoom()==18){
				anchorStyle=[24,24];
				srcStyle="/login/gisMap/image/tcg_48.png";
			}else if(mview.getZoom()==19){
				anchorStyle=[32,32];
				srcStyle="/login/gisMap/image/tcg_64.png";
			}else if(mview.getZoom()==20){
				anchorStyle=[64,64];
				srcStyle="/login/gisMap/image/tcg_128.png";
			}else{
				anchorStyle=[128,128];
				srcStyle="/login/gisMap/image/tcg_256.png";
			}
		}
	}
	if(mview.getZoom()<17){
		machTcStyle=new ol.style.Style({
			image: new ol.style.Icon(/** @type {olx.style.IconOptions} */ {
			    anchor: anchorStyle,
			    anchorXUnits: 'pixels',
			    anchorYUnits: 'pixels',
			    opacity: 1,
			    src: srcStyle
			  }),
			  text: new ol.style.Text({
					textAlign : 'center',
					textBaseline : 'center',
					font : 'bold  12px Arial',
					fill: new ol.style.Fill({color: '#333333'})
				})
		});
	}else if(mview.getZoom()==17){
		machTcStyle=new ol.style.Style({
			image: new ol.style.Icon(/** @type {olx.style.IconOptions} */ {
			    anchor: anchorStyle,
			    anchorXUnits: 'pixels',
			    anchorYUnits: 'pixels',
			    opacity: 1,
			    src: srcStyle
			  }),
			  text: new ol.style.Text({
					textAlign : 'center',
					textBaseline : 'center',
					font : 'bold  12px Arial',
					fill: new ol.style.Fill({color: '#333333'})
				})
		});
	}else if(mview.getZoom()==18){
		machTcStyle=new ol.style.Style({
			image: new ol.style.Icon(/** @type {olx.style.IconOptions} */ {
			    anchor: anchorStyle,
			    anchorXUnits: 'pixels',
			    anchorYUnits: 'pixels',
			    opacity: 1,
			    src: srcStyle
			  }),
			  text: new ol.style.Text({
					textAlign : 'center',
					textBaseline : 'center',
					font : 'bold  12px Arial',
					fill: new ol.style.Fill({color: '#333333'})
				})
		});
	}else if(mview.getZoom()==19){
		machTcStyle=new ol.style.Style({
			image: new ol.style.Icon(/** @type {olx.style.IconOptions} */ {
			    anchor:anchorStyle,
			    anchorXUnits: 'pixels',
			    anchorYUnits: 'pixels',
			    opacity: 1,
			    src: srcStyle
			  }),
			  text: new ol.style.Text({
					textAlign : 'center',
					textBaseline : 'center',
					font : 'bold  12px Arial',
					fill: new ol.style.Fill({color: '#333333'})
				})
		});
	}else if(mview.getZoom()==20){
		machTcStyle=new ol.style.Style({
			image: new ol.style.Icon(/** @type {olx.style.IconOptions} */ {
			    anchor:anchorStyle,
			    anchorXUnits: 'pixels',
			    anchorYUnits: 'pixels',
			    opacity: 1,
			    src: srcStyle
			  }),
			  text: new ol.style.Text({
					textAlign : 'center',
					textBaseline : 'center',
					font : 'bold  12px Arial',
					fill: new ol.style.Fill({color: '#333333'})
				})
		});
	}else if(mview.getZoom()==21){
		machTcStyle=new ol.style.Style({
			image: new ol.style.Icon(/** @type {olx.style.IconOptions} */ {
			    anchor: anchorStyle,
			    anchorXUnits: 'pixels',
			    anchorYUnits: 'pixels',
			    opacity: 1,
			    src: srcStyle
			  }),
			  text: new ol.style.Text({
					textAlign : 'center',
					textBaseline : 'center',
					font : 'bold  12px Arial',
					fill: new ol.style.Fill({color: '#333333'})
				})
		});
	}else{
		machTcStyle=new ol.style.Style({
			image: new ol.style.Icon(/** @type {olx.style.IconOptions} */ {
			    anchor: anchorStyle,
			    anchorXUnits: 'pixels',
			    anchorYUnits: 'pixels',
			    opacity: 1,
			    src: srcStyle
			  }),
			  text: new ol.style.Text({
					textAlign : 'center',
					textBaseline : 'center',
					font : 'bold  12px Arial',
					fill: new ol.style.Fill({color: '#333333'})
				})
		});
	}
	var zoom=20;
	if(companyCod=="NCT"){//南沙一期
		zoom=20;
	}
	if(companyCod=="GOCT"){//南沙二期
		zoom=20;
	}
	if(companyCod=="NICT"){//南沙三期
		zoom=20;
	}
	if(companyCod=="HPCT"){//黄埔
		zoom=19;
	}
	if(companyCod=="XSCT"){//新沙
		zoom=20;
	}
	if(companyCod=="GCT"){//集装箱
		zoom=19;
	}
	if(mview.getZoom()>zoom){
		machTcStyle.getText().setText(textTcStr);
	}else{
		machTcStyle.getText().setText("");
	}
	return machTcStyle;
};

/**
 * 场桥显示样式
 */
var RTGMachStyleFunction=function(feature){
	var textRTGStr=feature.get("MACH_COD");
	var machStat=feature.get("MACH_STAT");
	var machRTGStyle=new ol.style.Style({
	  	stroke: new ol.style.Stroke({
	    	  color: '#804000',
	    	  //lineDash: [4],
	    	  width: 1
	     }),
	     fill: new ol.style.Fill({
	      	color: '#804000'
	     }),
	     text: new ol.style.Text({
			textAlign : 'center',
			textBaseline : 'center',
			font : 'bold  14px Arial',
			stroke: new ol.style.Stroke({
		    	  color: '#804000',
		    	  width: 2
		     }),
			fill: new ol.style.Fill({color: '#FFFF00'})
		})
	});
	var zoom=18;
	if(companyCod=="NCT"){//南沙一期
		zoom=18;
	}
	if(companyCod=="GOCT"){//南沙二期
		zoom=18;
	}
	if(companyCod=="NICT"){//南沙三期
		zoom=18;
	}
	if(companyCod=="HPCT"){//黄埔
		zoom=17;
	}
	if(companyCod=="XSCT"){//新沙
		zoom=18;
	}
	if(companyCod=="GCT"){//集装箱
		zoom=17;
	}
	if(mview.getZoom()<=zoom){
		machRTGStyle.getText().setText("");
	}else{
		machRTGStyle.getText().setText(textRTGStr);
	}
	if(machStat=="B"){
		machRTGStyle.getFill().setColor("#666666");
		machRTGStyle.getStroke().setColor("#666666");
		machRTGStyle.getText().getStroke().setColor("#666666");
	}
	if(machStat=="C"){
		machRTGStyle.getFill().setColor("#CC0033");
		machRTGStyle.getStroke().setColor("#CC0033");
		machRTGStyle.getText().getStroke().setColor("#CC0033");
	}
	if(machStat=="O"){
		machRTGStyle.getFill().setColor("#339933");
		machRTGStyle.getStroke().setColor("#339933");
		machRTGStyle.getText().getStroke().setColor("#339933");
	}
	return machRTGStyle;
}
/**
 * 岸桥显示样式
 */
var RMQCMachStyleFunction=function(feature){
	var textRMQCStr=feature.get("MACH_COD");
	var machStat=feature.get("MACH_STAT");
	var curBollardCod=feature.get("CUR_BOLLARD_COD");
	var moorDir=feature.get("MOOR_DIRECTION");
	var machRMQCStyle="";
	if(moorDir=="L"||moorDir=="R"){
		var offX=75;
		var offY=5;
		if(moorDir=="L"){
			if(companyCod=='GCT'){
				if(mview.getZoom()==18){
					offX=75;
					offY=5;
				}else if(mview.getZoom()==19){
					offX=140;
					offY=5;
				}else if(mview.getZoom()==20){
					offX=280;
					offY=5;
				}else{
					offX=560;
					offY=5;
				}
			}else{
				if(mview.getZoom()==19){
					offX=75;
					offY=5;
				}else if(mview.getZoom()==20){
					offX=140;
					offY=5;
				}else{
					offX=280;
					offY=5;
				}
			}
		}else{
			if(mview.getZoom()==19){
				offX=75;
				offY=5;
			}else if(mview.getZoom()==20){
				offX=140;
				offY=5;
			}else if(mview.getZoom()==21){
				offX=280;
				offY=5;
			}else{
				offX=600;
				offY=5;
			}
		}
		machRMQCStyle=new ol.style.Style({
		  	stroke: new ol.style.Stroke({
		    	  color: '#F59E35',
		    	  width: 1
		     }),
		     fill: new ol.style.Fill({
		      	color: '#F59E35',
		     }),
		     text: new ol.style.Text({
				textAlign : 'center',
				textBaseline : 'ideographic',//'bottom', 'top', 'middle', 'alphabetic', 'hanging', 'ideographic
				offsetX:offX,
				offsetY:offY,
				font : 'bold  14px Arial',
				stroke: new ol.style.Stroke({
			    	  color: '#F59E35',
			    	  width: 2
			     }),
				fill: new ol.style.Fill({color: '#FFFF00'}),
				
			})
		});
	}else{
		machRMQCStyle=new ol.style.Style({
		  	stroke: new ol.style.Stroke({
		    	  color: '#F59E35',
		    	  width: 1
		     }),
		     fill: new ol.style.Fill({
		      	color: '#F59E35',
		     }),
		     text: new ol.style.Text({
				textAlign : 'center',
				textBaseline : 'ideographic',//'bottom', 'top', 'middle', 'alphabetic', 'hanging', 'ideographic
//				offsetX:75,
//				offsetY:5,
				font : 'bold  14px Arial',
				stroke: new ol.style.Stroke({
			    	  color: '#F59E35',
			    	  width: 2
			     }),
				fill: new ol.style.Fill({color: '#FFFF00'}),
				
			})
		});
	}
	var zoom=18;
	if(companyCod=="NCT"){//南沙一期
		zoom=18;
	}
	if(companyCod=="GOCT"){//南沙二期
		zoom=18;
	}
	if(companyCod=="NICT"){//南沙三期
		zoom=18;
	}
	if(companyCod=="HPCT"){//黄埔
		zoom=17;
	}
	if(companyCod=="XSCT"){//新沙
		zoom=18;
	}
	if(companyCod=="GCT"){//集装箱
		zoom=17;
	}
	if(mview.getZoom()<=zoom){
		machRMQCStyle.getText().setText("");
	}else{
//		if(curBollardCod=="IT001"){//缆桩原点只显示一个岸桥且不显示岸桥机械代码
//			machRMQCStyle.getText().setText("");
//		}else{
			machRMQCStyle.getText().setText(textRMQCStr);
//		}
	}
	if(machStat=="B"){
		machRMQCStyle.getFill().setColor("#666666");
		machRMQCStyle.getStroke().setColor("#666666");
		machRMQCStyle.getText().getStroke().setColor("#666666");
	}
	if(machStat=="C"){
		machRMQCStyle.getFill().setColor("#CC0033");
		machRMQCStyle.getStroke().setColor("#CC0033");
		machRMQCStyle.getText().getStroke().setColor("#CC0033");
	}
	if(machStat=="O"){
		machRMQCStyle.getFill().setColor("#339933");
		machRMQCStyle.getStroke().setColor("#339933");
		machRMQCStyle.getText().getStroke().setColor("#339933");
	}
	return machRMQCStyle;
}
/**
 * 摄像头样式
 */
var cameraStyleFunction=function(feature){

	var jkUrl="/login/images/gis/jk18.png";
	if(mview.getZoom()>=20&&mview.getZoom()<=22) jkUrl="/login/images/gis/jk24.png";
	else if(mview.getZoom()>22) jkUrl="/login/images/gis/jk32.png";

	var cameraStyle=new ol.style.Style({
		image: new ol.style.Icon(/** @type {olx.style.IconOptions} */ {
		    anchor: [16, 16],
		    anchorXUnits: 'pixels',
		    anchorYUnits: 'pixels',
		    opacity: 1,
		    src: jkUrl
		  })
	});
	return cameraStyle;
}
var folwStyleFounction=function(feature){
	var folwStyle;
	if(feature.get("wType")=="wkline"){
		
		folwStyle=new ol.style.Style({
          stroke:  new ol.style.Stroke({
            color: 'green',
            width: 1
          })
        });
	}else{
		var jkUrl="/login/images/gis/jk18_red.png";
		if(mview.getZoom()>=20&&mview.getZoom()<=22) jkUrl="/login/images/gis/jk24_red.png";
		else if(mview.getZoom()>22) jkUrl="/login/images/gis/jk32_red.png";
		
		folwStyle=new ol.style.Style({
			image: new ol.style.Icon({
			    anchor: [16, 16],
			    anchorXUnits: 'pixels',
			    anchorYUnits: 'pixels',
			    opacity: 1,
			    src: jkUrl
			  })
		});
	}
	return folwStyle;
}
var stroke = new ol.style.Stroke({color: 'black', width: 2});
var fill = new ol.style.Fill({color: 'red'});

var MachStyles = {
       /* 'square': new ol.style.Style({
				image: new ol.style.ArbitraryShape({
					fill : new ol.style.Fill({
						color : '#7cc965'
					}),
					stroke : new ol.style.Stroke({
						color : '#7cc965',
						width : 2
					}),
					size : [40,16],
					points : [[0,5],[10,3],[10,0],[40,0],[40,16],[10,16],[10,13] ,[0,11]]
					//rotation : (180 +90) * Math.PI / 180
				}),
				text: new ol.style.Text({
	  				textAlign : 'center',
	  				textBaseline : 'center',
	  				font : 'bold  12px Arial',
	  				stroke : new ol.style.Stroke({
	  					color : '#f1f1f1',
	  					width : 2
	  				}),
					text:"TC"//拖车
	  			})
        }),
        'triangle': new ol.style.Style({
				image: new ol.style.ArbitraryShape({
					fill : new ol.style.Fill({
						color : '#804000'
					}),
					stroke : new ol.style.Stroke({
						color : '#804000',
						width : 2
					}),
					size : [12,60],
					points : [[0,0],[0,60],[3,60],[3,32],[9,32],[9,60],[12,60] ,[12,0],[9,0],[9,28],[3,28],[3,0]]
					//rotation : (180 +90) * Math.PI / 180
				}),
				text: new ol.style.Text({
	  				textAlign : 'center',
	  				textBaseline : 'center',
	  				font : 'bold  12px Arial',
	  				stroke : new ol.style.Stroke({
	  					color : '#804000',
	  					width : 2
	  				}),
					text:"QC"//场桥
	  			})
        }),
       'star': new ol.style.Style({
				image: new ol.style.ArbitraryShape({
					fill : new ol.style.Fill({
						color : '#7cc965'
					}),
					stroke : new ol.style.Stroke({
						color : '#7cc965',
						width : 1
					}),
					size : [12,36],
					points : [[0,0],[0,36],[3,36],[3,12],[9,12],[9,36],[12,36] ,[12,0],[9,0],[9,20],[3,20],[3,8],[9,8],[9,0]],
					rotation : 180 * Math.PI / 180
				}),
				text: new ol.style.Text({
	  				textAlign : 'center',
	  				textBaseline : 'center',
	  				font : 'bold  12px Arial',
	  				stroke : new ol.style.Stroke({
	  					color : '#f1f1f1',
	  					width : 1
	  				}),
					text:"YC"//岸桥
	  			})
        }),*/
        'cross': new ol.style.Style({
          image: new ol.style.RegularShape({
            fill: fill,
            stroke: stroke,
            points: 4,
            radius: 10,
            radius2: 0,
            angle: 0
          })
        }),
        'x': new ol.style.Style({
          image: new ol.style.RegularShape({
            fill: fill,
            stroke: stroke,
            points: 4,
            radius: 10,
            radius2: 0,
            angle: Math.PI / 4
          })
        })
      };