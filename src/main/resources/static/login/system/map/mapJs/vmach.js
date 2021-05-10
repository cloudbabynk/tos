/**
 * 显示场上的拖车
 * @returns
 */
function initTCMach(TcInfo) {
		TcInfo.machTyp="('PM','TC')";
		var url="/webresources/login/mapFacilities/getMapMach";
		$.ajax({
			type : "POST",
			url : url,
			data: $.toJSON(TcInfo),
			contentType:"application/json",
			dataType : 'json',
			//async:false,
			success : function(datas) {
				//显示拖车
				if(datas.entity){
					showTCMach(datas);
				}
			}
		});
}
/**
 * 显示拖车
 * @param datas
 * @returns
 */
function showTCMach(datas) {
	var TCMachLayer=getLayersById("MACH_LAYER");
	var fc= (new ol.format.GeoJSON()).readFeatures(datas.entity);
	if(!TCMachLayer){
		layers.TCMachLayer= new ol.layer.Vector({
		    source: new ol.source.Vector({
		    	features:fc
		    }),
	    	style:TCMachStyleFunction
		});
		//顺序
		layers.TCMachLayer.setZIndex(layerConfig.MACH_LAYER.ZINDEX);
		//设置主键
		layers.TCMachLayer.set("layerKey",layerConfig.MACH_LAYER.LayerKey);
		
		map.addLayer(layers.TCMachLayer);   
	}else{
		//清除-添加新元素
		TCMachLayer.getSource().clear();
		TCMachLayer.getSource().addFeatures(fc);
	}
}
/**
 * 显示场上的场桥
 * @returns
 */
function initRTGMach(RTGInfo) {
		RTGInfo.machTyp="('RTG','ERTG','RMG','RS','ES')";
		var url="/webresources/login/mapFacilities/getMapRTGMach";
		$.ajax({
			type : "POST",
			url : url,
			//async:false,
			data: $.toJSON(RTGInfo),
			contentType:"application/json",
			dataType : 'json',
			success : function(datas) {
				//显示场桥
				if(datas.entity){
					var RTGMachLayer=getLayersById("YARD_BRIDGE_LAYER");
					var fc= (new ol.format.GeoJSON()).readFeatures(datas.entity);
					if(!RTGMachLayer){
						layers.RTGMachLayer= new ol.layer.Vector({
						    source: new ol.source.Vector({
						    	features:fc
						    }),
					    	style:RTGMachStyleFunction
						});
						//顺序
						layers.RTGMachLayer.setZIndex(layerConfig.YARD_BRIDGE_LAYER.ZINDEX);
						//设置主键
						layers.RTGMachLayer.set("layerKey",layerConfig.YARD_BRIDGE_LAYER.LayerKey);
						
						map.addLayer(layers.RTGMachLayer);   
					}else{
						//清除-添加新元素
						RTGMachLayer.getSource().clear();
						RTGMachLayer.getSource().addFeatures(fc);
					}
				}
			}
		});
}
/**
 * 显示场上的岸桥
 * @returns
 */
function initRMQCMach(RMQCInfo) {
		RMQCInfo.machTyp="('QC','SC')";
		var url="/webresources/login/mapFacilities/getMapRMQCMach";
		$.ajax({
			type : "POST",
			url : url,
			//async:false,
			data: $.toJSON(RMQCInfo),
			contentType:"application/json",
			dataType : 'json',
			success : function(datas) {
				//显示岸桥
				if(datas.entity){
					var RMQCMachLayer=getLayersById("BANK_BRIDGE_LAYER");
					var fc= (new ol.format.GeoJSON()).readFeatures(datas.entity);
					if(!RMQCMachLayer){
						layers.RMQCMachLayer= new ol.layer.Vector({
						    source: new ol.source.Vector({
						    	features:fc
						    }),
					    	style:RMQCMachStyleFunction
						});
						//顺序
						layers.RMQCMachLayer.setZIndex(layerConfig.BANK_BRIDGE_LAYER.ZINDEX);
						//设置主键
						layers.RMQCMachLayer.set("layerKey",layerConfig.BANK_BRIDGE_LAYER.LayerKey);
						
						map.addLayer(layers.RMQCMachLayer);   
					}else{
						//清除-添加新元素
						RMQCMachLayer.getSource().clear();
						RMQCMachLayer.getSource().addFeatures(fc);
					}
				}
			}
		});
}
/**
 * 机械信息
 * @param machFeatures 
 * @returns
 */
function  openMachDialog(machFeatures)
{	
	//参数为选中的机械 
	$("<div></div>").dialog({
		id:'openModleDiloge',
		width:850,    
		height:530,
		modal:true,
		cache: true,
		collapsible:true,
		onClose:function(){
			$(this).dialog('destroy');
			//$("#quasiupload_dg1031").datagrid("reload");
		},
		title:"机械信息",
		href: '/login/gisMap/mapModle/mapMachInfo.html'
	});      
}
