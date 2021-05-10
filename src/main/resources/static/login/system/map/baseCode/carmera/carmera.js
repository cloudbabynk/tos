function intiDVRCamera(){
	var dgCCamera=$("#CCameraDatagrid1489649064228");
    var builderCCamera = new HdQuery();
    
 // 增加。
    $("#CCameraToolBar1489649064228 a[iconCls='icon-add']").on("click", function () {
    		dgCCamera.datagrid("hdAdd",{});	
    });
    // 编辑。
    $("#CCameraToolBar1489649064228 a[iconCls='icon-edit']").on("click", function () {
    	dgCCamera.datagrid("hdEdit");
    });
    // 删除。
    $("#CCameraToolBar1489649064228 a[iconCls='icon-remove']").on("click", function () {
    	dgCCamera.datagrid("hdRemove");
    });
    // 保存。
    $("#CCameraToolBar1489649064228 a[iconCls='icon-save']").on("click", function () {
    	// var selectRow=$("#CCameraDatagrid1489649064228").datagrid("getSelected");
    	dgCCamera.datagrid("hdSave", {url: "/webresources/login/base/CCamera/save"});
    });
    // 刷新。
    $("#CCameraToolBar1489649064228 a[iconCls='icon-reload']").on("click", function () {
			dgCCamera.datagrid("hdReload");
			showNewCamera("");
    	
    });
    
    $("#CCameraSearchbox1489649064228").searchbox({prompt: "请输入...", searcher: function (value, name) {
		builderCCamera.add("cameraNam",value);
    	dgCCamera.datagrid("hdReload");
    }});
// datagrid
	$("#CCameraDatagrid1489649064228").datagrid({
        striped:true,
	    url: "/webresources/login/base/CCamera/find",
	    queryParams: builderCCamera.build(),
	    method: "POST",
	    pagination: true,
	    singleSelect: true,
	    rownumbers: true,
	    pageSize: 20,
	    autoLoad:false,
	    toolbar: "#CCameraToolBar1489649064228",
	    fit: true,
	    fitColumns: false,
	    columns: [[ 
	            {field: "id", title: '<font color="red">ID</font>',  hidden:true },
	            {field: "cameraNam", title: "摄像头名称", multiSort: true, halign: "center", editor:"text",  width:160,  sortable: true },
	            {field: "url",title: "连接地址", multiSort: true,  halign: "center",width:200, align:"center", editor:"text",sortable: true},
	            {field: "xpos",title: "坐标X",multiSort: true,halign: "center",width:135, sortable: true },
	            {field: "ypos",title: "坐标Y",multiSort: true, halign: "center",width:135,sortable: true },
	            {field: "cameraTyp",title: "备注",multiSort: true, halign: "center",width:135, editor:"text",sortable: true }
	        ]],
	        onDblClickRow:function(rowIndex,rowData){
	        	showNewCamera(rowData.id);
	        }
		});
	//绘制
	$("#drawCameraBtn").on("click",function(){
		var rowData=$("#CCameraDatagrid1489649064228").datagrid("getSelected");
 		if(rowData){
 			drawCallFunc="drawBackCamera";
 			beginDraw("Point");
 		}else{
 			console.log("请先选择摄像头！");
 		}
	});
	//编辑坐标
	$("#editCameraPosBtn").on("click",function(){
		var rowData=$("#CCameraDatagrid1489649064228").datagrid("getSelected");
		if(rowData){
			$("#cameraPosX").val(rowData.xpos);
			$("#cameraPosY").val(rowData.ypos);
			$("#cameraId").val(rowData.id);
			$("#openEditCameraPos").dialog({
				title:'坐标位置',
				closed:false,
				width:380,
				height:200,
				buttons:[{
					text:'保存',
					handler:function(){
						var args=rowData;
						args.pos=$("#cameraPosX").val()+","+$("#cameraPosY").val();
				        $.ajax({
				        	url: "/webresources/login/base/CCamera/saveone",
				            contentType:"application/json",
				            type: "POST",
				            data: $.toJSON(args),
				            dataType: 'json',
				            async:false,
				            success: function (data) {
				            	showNewCamera(rowData.id);
				            	$("#openEditCameraPos").dialog({closed:true});
				            	dgCCamera.datagrid("hdReload");
				            },
				            error: function (data) {
				                console.log("保存失败！");
				            }
				        });
					}
				},{
					text:'取消',
					handler:function(){
						$("#openEditCameraPos").dialog({closed:true});
					}
				}]
			});
 		}else{
 			console.log("请先选择摄像头！");
 		}
	});
	
	dgCCamera.datagrid("hdReload");
}

// /**
//  * 选择摄像头
//  * @param extent
//  * @returns
//  */
// var sendUrl="";
// function openDvrDialog(rpUrl)
// {
// 	sendUrl=rpUrl;
// 	modDolage=$("<div></div>").dialog({
// 		id:'openCameraDialog',
// 		width:590,    
// 		height:460,
// 		modal:true,
// 		cache: true,
// 		collapsible:true,
// 		onClose:function(){
// 			$(this).dialog('destroy');
// 			modDolage=null;
// 		},
// 		title:"摄像头信息",
// 		href: '/login/camare/vod.html'
// 	});
// }


function drawBackCamera(geomStr,gArea)
{
	$.messager.confirm('提示框','你确定要保存摄像头的位置吗?',function(r){
	    if (r){
	        var rowData=$("#CCameraDatagrid1489649064228").datagrid("getSelected");
			var args=rowData;
			var pos=geomStr.toString().split(",");
			args.xpos=pos[0];
			args.ypos=pos[1];
	        $.ajax({
	        	url: "/webresources/login/base/CCamera/saveone",
	            contentType:"application/json",
	            type: "POST",
	            data: $.toJSON(args),
	            dataType: 'json',
	            async:false,
	            success: function (data) {
	            	showNewCamera(rowData.id);
	            	$("#CCameraDatagrid1489649064228").datagrid("load");
	            },
	            error: function (data) {
	                console.log("保存失败！");
	            }
	        });
	    }
	});
}


//显示当前保存的摄像头的位置
function showNewCamera(carmeraId){
	var cameraMap=new HdQuery();
	if(carmeraId){
		cameraMap.add("cameraId",carmeraId);
	}
	$.ajax({
		type : "POST",
		url :"/webresources/login/map/mapFeature/getMapCamera",
		data:$.toJSON(cameraMap.build()), 
		contentType:"application/json",
		dataType : 'json',
		success : function(datas) {
			//显示摄像头
			var cameraLayer=getLayersById("CAMERA_LAYER");
			if(datas.data){
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
					for(var i=0;i<fc.length;i++)
					{
						var fetItem=fc[i];
						var optFet=cameraLayer.getSource().getFeatureById(fetItem.getId());
						if(!optFet)  cameraLayer.getSource().addFeature(fetItem);
						else {
							optFet.setProperties(fetItem.getProperties());
							optFet.setGeometry(fetItem.getGeometry());
						}
					}
				}
				var pointCamera=fc[0].getGeometry().getCoordinates();
				// mview.setZoom(19);
				// mview.setCenter(pointCamera);
			}else
			{
				cameraLayer.getSource().removeFeature(cameraLayer.getSource().getFeatureById(layerConfig.CAMERA_LAYER.FID+id));
			}
		}
	});
}
