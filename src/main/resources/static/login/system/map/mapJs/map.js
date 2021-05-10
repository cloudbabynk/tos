var mview =   new ol.View({
    projection: 'EPSG:4326',
    center: comMapConfig[companyCod].center,
    extent:comMapConfig[companyCod].mapExtent,
    zoom: comMapConfig[companyCod].initZoom,
    maxZoom:comMapConfig[companyCod].maxZoom,
    minZoom:comMapConfig[companyCod].minZoom,
    units:'degrees',
    enableRotation:true
  });

var baseLayers=[layers.BASE];
//var baseLayers=[layers.BASE,layers.YARDAREA_LAYER];
//地图
var map = new ol.Map({
	interactions: ol.interaction.defaults({
		pinchRotate:false,
		doubleClickZoom:false
		/*
		pinchZoom:true
		doubleClickZoom:true,
		pinchRotate:false,
		pinchZoom:true,
		dragpan:true,
		dragzoom:true*/
	}),
	layers: baseLayers,
	target: 'map',
//	controls:ol.control.defaults().extend([mousePositionControl,zoomslider]),
	controls:ol.control.defaults().extend([zoomslider]),
	//controls:ol.control.defaults().extend([mousePositionControl,scaleLineControl,zoomslider,zoomb,cright,mfs]),
	view:mview,
	logo:false
});
var zoom=mview.getZoom();  
var tMach;
var rightBoard={};
var zdBoard;//折叠标签


function initMapThing(isShow){
	if(isShow){
		//初始化地图事件
		initMapEventAction(); 
		//初始化车辆的图形功能 initMapPortCar）
		initMapPortCar(); 
		//显示摄像头
		if(companyCod=="TJG_HQ"){
			var cameraObj={};
			cameraObj.companyCod=companyCod;
			initCamera(cameraObj);
		}
		 var yardpam=new HdQuery();
		 yardpam.dockCod=companyCod;
		//initYard(yardpam);
		// //初始化加载一次
		// initMachInfo();
		// tMach= window.setInterval(initMachInfo,layerConfig.MACH_LAYER.freshTime); 
		//初始化折叠框
		initLayRightBoard();
		//折叠框查询
		initSearchTool();
	}
}
//tMach
//显示在场车辆
function initMachInfo(){
	var machObj={};

	machObj.companyCod=companyCod;
	//显示拖车
	initTCMach(machObj);
	//显示场桥
	initRTGMach(machObj);
	//显示岸桥
	initRMQCMach(machObj);
	
}
//显示在场船
function initShipInfo(){
	var shipObj={};
	shipObj.countryCod=companyCod;
	showShip(shipObj);
}
//自定义显示
function pageSelfShow(showLayer,isShow)
{
	if(layers[showLayer]) layers[showLayer].setVisible(isShow);
}
function pageSelfCheck(con)
{
	if(con.value=="mach"){
		pageSelfShow("TCMachLayer",con.checked);
		pageSelfShow("RTGMachLayer",con.checked);
		pageSelfShow("RMQCMachLayer",con.checked);
	}else if(con.value=="ship")
	{
		pageSelfShow("SHIP_LAYER",con.checked);
	}else if(con.value=="monitor")
	{
		pageSelfShow("cameraLayer",con.checked);
	}
}
//折叠框
function initLayRightBoard(){
	$("div.text").hide();//默认隐藏div，或者在样式表中添加.text{display:none}，推荐使用后者
	$(".foldBox h1").click(function(){
		selLayRightBoard($(this)[0].id,"0");
	});
}
function selLayRightBoard(htextId,isForce){

	if(isForce=="0"){
		//初始  0 关闭  1打开
		rightBoard[htextId]=0;
		for(var key in rightBoard){ 	
			if(htextId==key){
				if(!$("#"+key).next(".text").is(":visible")){
					rightBoard[key]=1;
				}else{
					rightBoard[key]=0;
				}
				$("#"+key).next(".text").slideToggle("normal",function(){	
					switchGradLoad(htextId,isForce);
				});
			}else{
				//关闭 isChang=0时
				if($("#"+key).next(".text").is(":visible")){		
					$("#"+key).next(".text").slideToggle("normal");
					//合上状态=0
					rightBoard[key]=0;
				}
			}
		}
	}else if(isForce=="1"){
		//初始  0 关闭  1打开
		rightBoard[htextId]=1;
		if(!$("#"+htextId).next(".text").is(":visible")){	
			$("#"+htextId).next(".text").slideToggle("normal",function(){	
				switchGradLoad(htextId,isForce);
			});
		}else{
			switchGradLoad(htextId,isForce);

		}
		//关闭其他
		for(var key in rightBoard){ 	
			if(htextId!=key){
				if($("#"+key).next(".text").is(":visible")){		
					$("#"+key).next(".text").slideToggle("normal");
					//合上状态=0
					rightBoard[key]=0;
				}
			}
		}
		
	}
}
/**
 * 根据key 执行加载不同的表格
 * @param gradKey
 * @returns
 */
function switchGradLoad(gradKey,isForce){
	switch (gradKey) {
	case "divEmergeList":
		 initWrokbillGrad();
		break;
	case "billNoSearchTool":
		showMapCargo(selAreaInfo,isForce);
		break;
	case "yardDaoList":
		loadDaoCargo();
		break;
	default:
		break;
	}
	
}
function drawBackWrokbill(geomStr,gArea){
	var posStr="";
	for(var i=0;i<geomStr.length;i++){
		if(i==0){
			posStr+=geomStr[i][0]+","+geomStr[i][1];
		}else{
			posStr+=";"+geomStr[i][0]+","+geomStr[i][1];
		}
	}
	selWorkBill.pos=posStr;
    $.ajax({
    	url: "/webresources/login/base/CGisWbill/saveRelCamera",
        contentType:"application/json",
        type: "POST",
        data: $.toJSON(selWorkBill),
        dataType: 'json',
        async:false,
        success: function (data) {
	    	builderCCamera.add("wbId",selWorkBill.wbId);
	    	$("#gdWorkBillCamera").datagrid("hdReload");
        	selWorkBill=null;
        },
        error: function (data) {
            console.log("保存失败！");
        }
    });
    
}


