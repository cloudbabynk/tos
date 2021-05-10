/**
 * 显示场上的摄像头
 * @returns
 */
function initCamera(cameraInfo) {
		var url="/webresources/login/map/mapFeature/getMapCamera";
		$.ajax({
			type : "POST",
			url : url,
			data: $.toJSON(cameraInfo),
			contentType:"application/json",
			dataType : 'json',
			success : function(datas) {
				//显示摄像头
				if(datas.data){
					showCamera(datas);
				}
			}
		});
}
function showCamera(datas) {
	var cameraLayer=getLayersById("CAMERA_LAYER");
	var fc= (new ol.format.GeoJSON()).readFeatures(datas.data);
	if(!cameraLayer){
		layers.cameraLayer= new ol.layer.Vector({
		    source: new ol.source.Vector({
		    	features:fc
		    }),
	    	style:cameraStyleFunction
		});
		//顺序
		layers.cameraLayer.setZIndex(layerConfig.CAMERA_LAYER.ZINDEX);
		//设置主键
		layers.cameraLayer.set("layerKey",layerConfig.CAMERA_LAYER.LayerKey);
		
		map.addLayer(layers.cameraLayer);   
	}else{
		//清除-添加新元素
		cameraLayer.getSource().clear();
		cameraLayer.getSource().addFeatures(fc);
	}
}

var playBol=false;
function openCameraDialog(mediaData)
{

	if(!playBol&&mediaData&&mediaData.length>0){
		playBol=true;
		var rowPam={};
		rowPam.ID=mediaData[0].get("CAMERA_ID");
		rowPam.URL=mediaData[0].get("URL");
		var cameraNam=mediaData[0].get("CAMERA_NAM");
        $.ajax({
        	url: HdUtils.global.config.mediaPlay,
            contentType:"application/json",
            type: "POST",
            dataType: 'json',
            data: $.toJSON(rowPam),
            success: function (data) {
            	openXchMediaDialog(data.message,cameraNam);
            	playBol=false;
            },
            error: function (data) {
                console.log("保存失败！");
            }
        });
	}
}
/**
 * 选择摄像头
 * @param extent
 * @returns
 */
var sendUrl="";
function openXchMediaDialog(rpUrl,cameraNam)
{
	sendUrl=rpUrl;
	modDolage=$("<div></div>").dialog({
		id:'openCameraDialog',
		width:590,    
		height:460,
		modal:true,
		cache: true,
		collapsible:true,
		onClose:function(){
			$(this).dialog('destroy');
			modDolage=null;
		},
		title:cameraNam,
		href: '/login/camare/vod.html'
	});
}
