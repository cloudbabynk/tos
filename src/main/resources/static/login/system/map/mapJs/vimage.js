var groupIcon={keys:[]};
var groupICONURL = "manage/getGroupIcons.action?date="
+ (new Date()).getTime();
function initIcon(){
	$.ajax({
		type : "POST",
		url : groupICONURL,
		dataType : 'json',
		success : function(datas) {
			processICON(datas,groupIcon);
			initMJICON();
			initWzc();
		}
	});
}

function isMj(type){
	return groupIcon.MACH!=null&&groupIcon.MACH.children!=undefined&&groupIcon.MACH.children.EM1.metas.indexOf(type)>=0;
}

function getImage(layerKey,metaId,state){
	var gc=groupIcon[layerKey];
	var cgc=null;
	if(metaId&&gc){
		if(gc.children){//根据分类查如果层内有字图标配置，并且元素ID不为空
			for(var i=0;i<gc.children.keys.length;i++){//遍历该层的子图标配置，找到对应的图标配置类
				cgc=gc.children[gc.children.keys[i]];
				if(cgc&&cgc.metas){
					if(cgc.metas.indexOf(metaId)>=0){
						var key='';
						if(mview.getZoom()<18)
							key=state=='1'?'icon':'iconstop';//返回图标配置信息
						else if (mview.getZoom()<=19)
							key=state=='1'?'icon32':'iconstop32';
						else if(mview.getZoom()<=20)
							key=state=='1'?'icon64':'iconstop64';
						else
							key=state=='1'?"icon128":'iconstop128';
						return cgc[key];
					}
				}
			}
		}
	}else{
		if(gc)
			return gc.icon;//如果层图标配置类存在，返回层图标
		else
			return layers[layerKey].defaultIcon;//如果没有返回默认图标
	}
}

function getManagerImage(type,state){
	if(groupIcon.MANAGER){
		var gc=groupIcon.MANAGER.children[type];
		if(state=='1')
			return gc.icon;
		else
			return gc.iconstop;
	}
	return null;
}

function initMJICON(){
	if(groupIcon.MACH.children.EM1){
		groupIcon.MACH.children.EM1.onIcon=new ol.style.Icon(/** @type {olx.style.IconOptions} */ {
		    anchor: [16, 16],
		    anchorXUnits: 'pixels',
		    anchorYUnits: 'pixels',
		    opacity: 1,
		    src: 'map-icon/6ProfTerminal_green_32.png'
		  });
		groupIcon.MACH.children.EM1.onIcon64=new ol.style.Icon(/** @type {olx.style.IconOptions} */ {
		    anchor: [32, 32],
		    anchorXUnits: 'pixels',
		    anchorYUnits: 'pixels',
		    opacity: 1,
		    src: 'map-icon/6ProfTerminal_green_64.png'
		  });
		groupIcon.MACH.children.EM1.onIcon128=new ol.style.Icon(/** @type {olx.style.IconOptions} */ {
		    anchor: [64,64],
		    anchorXUnits: 'pixels',
		    anchorYUnits: 'pixels',
		    opacity: 1,
		    src: 'map-icon/6ProfTerminal_green_128.png'
		  });
		groupIcon.MACH.children.EM1.readyIcon=new ol.style.Icon(/** @type {olx.style.IconOptions} */ {
		    anchor: [16,16],
		    anchorXUnits: 'pixels',
		    anchorYUnits: 'pixels',
		    opacity: 1,
		    src: 'map-icon/6ProfTerminal_red_32.png'
		  });
		groupIcon.MACH.children.EM1.readyIcon64=new ol.style.Icon(/** @type {olx.style.IconOptions} */ {
		    anchor: [32,32],
		    anchorXUnits: 'pixels',
		    anchorYUnits: 'pixels',
		    opacity: 1,
		    src: 'map-icon/6ProfTerminal_red_64.png'
		  });
		groupIcon.MACH.children.EM1.readyIcon128=new ol.style.Icon(/** @type {olx.style.IconOptions} */ {
		    anchor: [64,64],
		    anchorXUnits: 'pixels',
		    anchorYUnits: 'pixels',
		    opacity: 1,
		    src: 'map-icon/6ProfTerminal_red_128.png'
		  });
		groupIcon.MACH.children.EM1.stopIcon=new ol.style.Icon(/** @type {olx.style.IconOptions} */ {
		    anchor: [16,16],
		    anchorXUnits: 'pixels',
		    anchorYUnits: 'pixels',
		    opacity: 1,
		    src: 'map-icon/6ProfTerminal_gray_32.png'
		  });
		groupIcon.MACH.children.EM1.stopIcon64=new ol.style.Icon(/** @type {olx.style.IconOptions} */ {
		    anchor: [32,32],
		    anchorXUnits: 'pixels',
		    anchorYUnits: 'pixels',
		    opacity: 1,
		    src: 'map-icon/6ProfTerminal_gray_64.png'
		  });
		groupIcon.MACH.children.EM1.stopIcon128=new ol.style.Icon(/** @type {olx.style.IconOptions} */ {
		    anchor: [64,64],
		    anchorXUnits: 'pixels',
		    anchorYUnits: 'pixels',
		    opacity: 1,
		    src: 'map-icon/6ProfTerminal_gray_128.png'
		  });
		groupIcon.MACH.children.EM1.repaireIcon=new ol.style.Icon(/** @type {olx.style.IconOptions} */ {
		    anchor: [16,16],
		    anchorXUnits: 'pixels',
		    anchorYUnits: 'pixels',
		    opacity: 1,
		    src: 'map-icon/6ProfTerminal_yellow_32.png'
		  });
		groupIcon.MACH.children.EM1.repaireIcon64=new ol.style.Icon(/** @type {olx.style.IconOptions} */ {
		    anchor: [32,32],
		    anchorXUnits: 'pixels',
		    anchorYUnits: 'pixels',
		    opacity: 1,
		    src: 'map-icon/6ProfTerminal_yellow_64.png'
		  });
		groupIcon.MACH.children.EM1.repaireIcon128=new ol.style.Icon(/** @type {olx.style.IconOptions} */ {
		    anchor: [64,64],
		    anchorXUnits: 'pixels',
		    anchorYUnits: 'pixels',
		    opacity: 1,
		    src: 'map-icon/6ProfTerminal_yellow_128.png'
		  });
	}
}

function initWzc(){
	groupIcon.outcar={};
	groupIcon.outcar.on=new ol.style.Icon(/** @type {olx.style.IconOptions} */ {
	    anchor: [8, 8],
	    anchorXUnits: 'pixels',
	    anchorYUnits: 'pixels',
	    opacity: 1,
	    src: 'map-icon/wz_16.png'
	  });
	groupIcon.outcar.on_32=new ol.style.Icon(/** @type {olx.style.IconOptions} */ {
	    anchor: [16, 16],
	    anchorXUnits: 'pixels',
	    anchorYUnits: 'pixels',
	    opacity: 1,
	    src: 'map-icon/wz_32.png'
	  });
	groupIcon.outcar.on_64=new ol.style.Icon(/** @type {olx.style.IconOptions} */ {
	    anchor: [32, 32],
	    anchorXUnits: 'pixels',
	    anchorYUnits: 'pixels',
	    opacity: 1,
	    src: 'map-icon/wz_64.png'
	  });
	groupIcon.outcar.on_128=new ol.style.Icon(/** @type {olx.style.IconOptions} */ {
	    anchor: [64, 64],
	    anchorXUnits: 'pixels',
	    anchorYUnits: 'pixels',
	    opacity: 1,
	    src: 'map-icon/wz_128.png'
	  });
	groupIcon.outcar.stop=new ol.style.Icon(/** @type {olx.style.IconOptions} */ {
	    anchor: [8, 8],
	    anchorXUnits: 'pixels',
	    anchorYUnits: 'pixels',
	    opacity: 1,
	    src: 'map-icon/wzstop_16.png'
	  });
	groupIcon.outcar.stop_32=new ol.style.Icon(/** @type {olx.style.IconOptions} */ {
	    anchor: [16, 16],
	    anchorXUnits: 'pixels',
	    anchorYUnits: 'pixels',
	    opacity: 1,
	    src: 'map-icon/wzstop_32.png'
	  });
	groupIcon.outcar.stop_64=new ol.style.Icon(/** @type {olx.style.IconOptions} */ {
	    anchor: [32, 32],
	    anchorXUnits: 'pixels',
	    anchorYUnits: 'pixels',
	    opacity: 1,
	    src: 'map-icon/wzstop_64.png'
	  });
	groupIcon.outcar.stop_128=new ol.style.Icon(/** @type {olx.style.IconOptions} */ {
	    anchor: [64, 64],
	    anchorXUnits: 'pixels',
	    anchorYUnits: 'pixels',
	    opacity: 1,
	    src: 'map-icon/wzstop_128.png'
	  });
}

function processICON(datas,gc){
	var l;
	var ext='.png';
	if(datas!=null&&datas.length>0){
		var d=null;
		var k=0;
		for(var i=0;i<datas.length;i++){
			d=datas[i];
			if(d.LOGO){
				gc.keys[k]=d.GROUP_ID;
				l=d.LOGO.substring(0,d.LOGO.indexOf('.'));
				gc[d.GROUP_ID]={key:d.GROUP_ID,metas:d.GROUP_CONTENTS,icon:new ol.style.Icon(/** @type {olx.style.IconOptions} */ {
				    anchor: [8, 8],
				    anchorXUnits: 'pixels',
				    anchorYUnits: 'pixels',
				    opacity: 1,
				    src: l+"_16"+ext
				  })			
				};
				gc[d.GROUP_ID].iconstop=new ol.style.Icon(/** @type {olx.style.IconOptions} */ {
				    anchor: [8,8],
				    anchorXUnits: 'pixels',
				    anchorYUnits: 'pixels',
				    opacity: 1,
				    src: l+"stop_16"+ext
				  });
				gc[d.GROUP_ID].icon24=new ol.style.Icon(/** @type {olx.style.IconOptions} */ {
				    anchor: [12,12],
				    anchorXUnits: 'pixels',
				    anchorYUnits: 'pixels',
				    opacity: 1,
				    src: l+"_24"+ext
				  });
				gc[d.GROUP_ID].icon32=new ol.style.Icon(/** @type {olx.style.IconOptions} */ {
				    anchor: [16,16],
				    anchorXUnits: 'pixels',
				    anchorYUnits: 'pixels',
				    opacity: 1,
				    src: l+"_32"+ext
				  });
				gc[d.GROUP_ID].icon64=new ol.style.Icon(/** @type {olx.style.IconOptions} */ {
				    anchor: [32,32],
				    anchorXUnits: 'pixels',
				    anchorYUnits: 'pixels',
				    opacity: 1,
				    src: l+"_64"+ext
				  });
				gc[d.GROUP_ID].icon128=new ol.style.Icon(/** @type {olx.style.IconOptions} */ {
				    anchor: [64,64],
				    anchorXUnits: 'pixels',
				    anchorYUnits: 'pixels',
				    opacity: 1,
				    src: l+"_128"+ext
				  });
				
				gc[d.GROUP_ID].iconstop24=new ol.style.Icon(/** @type {olx.style.IconOptions} */ {
				    anchor: [12,12],
				    anchorXUnits: 'pixels',
				    anchorYUnits: 'pixels',
				    opacity: 1,
				    src: l+"stop_24"+ext
				  })	;
				gc[d.GROUP_ID].iconstop32=new ol.style.Icon(/** @type {olx.style.IconOptions} */ {
				    anchor: [16,16],
				    anchorXUnits: 'pixels',
				    anchorYUnits: 'pixels',
				    opacity: 1,
				    src: l+"stop_32"+ext
				  });
				gc[d.GROUP_ID].iconstop64=new ol.style.Icon(/** @type {olx.style.IconOptions} */ {
				    anchor: [32,32],
				    anchorXUnits: 'pixels',
				    anchorYUnits: 'pixels',
				    opacity: 1,
				    src: l+"stop_64"+ext
				});
				gc[d.GROUP_ID].iconstop128=new ol.style.Icon(/** @type {olx.style.IconOptions} */ {
				    anchor: [64,64],
				    anchorXUnits: 'pixels',
				    anchorYUnits: 'pixels',
				    opacity: 1,
				    src: l+"stop_128"+ext
				});
				if(d.GROUP_TYP=='L'){
					gc[d.GROUP_ID].children={keys:[]};
					if(d.children!=null&&d.children.length>0){
						processICON(d.children,gc[d.GROUP_ID].children);
					}
				}
				k++;
			}
		}
	}
}