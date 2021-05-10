
var selWorkBill;
var builderCCamera;
var initWrokbill=0;
function initWrokbillGrad(){
		if(initWrokbill!=0) return;
		
		initWrokbill=1;
		
		
		
		var gdWorkBill=$("#gdWorkBill");
		var builderWorkBill = new HdQuery();	
		gdWorkBill.datagrid({
	        striped:true,
		    url: "/webresources/login/base/CGisWbill/find",
		    queryParams: builderWorkBill.build(),
		    method: "POST",
/*		    pagination: true,
		    pageSize: 20,*/
		    singleSelect: true,
		    rownumbers: true,		
		    autoLoad:true,
		    toolbar: "#gdWorkBillToolBar",
		    fit: true,
		    fitColumns: false,
		    columns: [[ 
		            {field: "wbNam", title: "线路名称", multiSort: true, halign: "center", editor:"text",  width:160,  sortable: true }
		        ]],
		    onSelect(rowIndex,rowData){
		    	builderCCamera.add("wbId",rowData.wbId);
		    	gdWorkBillCamera.datagrid("hdReload");
		    },
	        onDblClickRow : function(rowIndex, rowData) {
	        	selWorkBill=rowData;
	        	getWorkFlow();
	        }
		});
		
		 // 增加。
	    $("#gdWorkBillToolBar a[iconCls='icon-add']").on("click", function () {
	    	gdWorkBill.datagrid("hdAdd",{});	
	    });
	    // 编辑。
	    $("#gdWorkBillToolBar a[iconCls='icon-edit']").on("click", function () {
	    	gdWorkBill.datagrid("hdEdit");
	    });
	    // 删除。
	    $("#gdWorkBillToolBar a[iconCls='icon-remove']").on("click", function () {
	    	gdWorkBill.datagrid("hdRemove");
	    });
	    // 保存。
	    $("#gdWorkBillToolBar a[iconCls='icon-save']").on("click", function () {
	    	gdWorkBill.datagrid("hdSave", {url: "/webresources/login/base/CGisWbill/save"});
	    });
	    // 刷新。
	    $("#gdWorkBillToolBar a[iconCls='icon-reload']").on("click", function () {
	    	gdWorkBill.datagrid("hdReload");
	    	
	    });
	    // 发送。
	    $("#gdWorkBillToolBar a[iconCls='icon-redo']").on("click", function () {
	    	var selectRows=$("#gdWorkBillCamera").datagrid("getRows");
	    	if(selectRows.length==0){
	    		$.messager.alert("提示","请选择一条有摄像头的线");
	    		return;
	    	}
	    	var postData="";
	    	var postarry=[];
	    	var postjson={};
	    	for(var i=0;i<selectRows.length;i++){
	    		postarry.push(selectRows[i].cCamera.id);
	    	}
	    	postjson.Channels=postarry;
	    	$.ajax({
	        	url: "/webresources/login/base/CGisWbillCamera/sendtoCamera?channels=" + JSON.stringify(postjson),
	            contentType:"text",
	            type: "POST",
	            dataType: 'text',
	            async:false,
	            success: function (data) {
	    	    	if(data=="true"){
	    	    		$.messager.alert("提示","发送成功！")
	    	    	}else{
	    	    		$.messager.alert("提示","发送失败！")
	    	    	}
	            },
	            error: function (data) {
	            	$.messager.alert("提示","发送失败！！")
	            }
	        });
	    	
	    });
	    // 作业线规划。
	    $("#gdWorkBillToolBar a[iconCls='icon-draw']").on("click", function () {
			var rowData=gdWorkBill.datagrid("getSelected");
			if(rowData){
				selWorkBill=rowData;
		    	drawCallFunc="drawBackWrokbill";
		    	beginDraw("LineString");
	    	}else{
	    		   $.messager.alert(Resources.WB_INFO, "请先选择作业线！", "info");
	 		}
	    	
	    });   
		var gdWorkBillCamera=$("#gdWorkBillCamera");
		builderCCamera = new HdQuery();	
		builderCCamera.add("wbId","");
		gdWorkBillCamera.datagrid({
	        striped:true,
		    url: "/webresources/login/base/CGisWbillCamera/find",
		    queryParams: builderCCamera.build(),
		    method: "POST",
		    singleSelect: true,
		    rownumbers: true,
		    autoLoad:false,
		    toolbar: "#gdWorkBillCameraToolBar",
		    fit: true,
		    fitColumns: false,
		    columns: [[ 
	            {
	            	field: "cameraNam", title: "名称", multiSort: true, halign: "center",width:160,  sortable: true,
	            	formatter: function(value,row,index){
	            		return row.cCamera.cameraNam
    				}
	            }
	        ]]
		});	    

}


function getMapCargoPam(){
    var onlinquery = new HdQuery();
    onlinquery.add("billNo",$("#billNoSearch").val());
    onlinquery.add("brandCod",$("#bandCodSearch").combobox('getValue'));
    onlinquery.add("carTyp",$("#carTypeSearch").combobox('getValue'));
    onlinquery.add("carKind",$("#carKindSearch").combobox('getValue'));
    onlinquery.add("shipNo",$("#cShipNo").val());
    onlinquery.add("cyAreaNo",$("#areaNoSearch").combobox('getValue'));  
    onlinquery.add("incyTime",$("#inTimeSearch").datebox('getValue'));  
    
    onlinquery.add("companyCod",companyCod);   
    return onlinquery;
}
function mapClearVal(){
	$("#billNoSearch").val("");
	$("#cmapShipNam").val("");
	$("#cShipNo").val("");
	$("#selShip").val("");
	$("#areaNoSearch").combobox('setValue', '');
	$("#bandCodSearch").combobox('setValue', '');
	$("#carTypeSearch").combobox('setValue', '');
	$("#carKindSearch").combobox('setValue', '');
}
var initMapCargo=0;
function showMapCargo(cargoPam,isForce){
	if(isForce=="1"|| initMapCargo==0){
		 
	    initMapCargo=1;
		 var pam=getMapCargoPam();
	     if(cargoPam&&cargoPam.BLOCKNO){
			 $("#areaNoSearch").combobox('setValue',cargoPam.BLOCKNO);  
			 pam.add("cyAreaNo",cargoPam.BLOCKNO);
	   }
	  $("#cAreaDatagrid").datagrid({
	        striped : true,
	        url: "/webresources/login/base/CCyArea/findDcclhz",
	        queryParams:pam.build(),
	        method : "post",
/*	        pagination : true,  
	        pageSize : 20,*/
	        singleSelect : true,
	        selectOnCheck : false,
	        checkOnSelect : false,
	        rownumbers : true,	  
	        toolbar : "#cAreaDatagridToolBar",
	        fit : true,
	        fitColumns : false,
	        columns : [ [ {
	            field : "ck", checkbox : true,
	            sortable : false,
	        }, 
	        {field : "IN_CY_TIM", title : '入场时间',  multiSort : true,halign : "center", align : "left",  sortable : false },
	        {field : "CY_AREA_NAM", title : '堆场名称',  multiSort : true,halign : "center", align : "left",  sortable : false }, 
	        {field : "C_SHIP_NAM",  title : '船名', multiSort : true, width : 80, align : "left", sortable : false }, 
	        {field : "VOYAGE", title : '航次', multiSort : true, width:60,  align : "left", sortable : false},
	        {field : "IED",  title : '进出口', multiSort : true,halign : "center",align : "left",
	            formatter : function(value) {
		            return HdUtils.code.name('I_E_ID', value);
	            },
	            sortable : false
	        },
	        {field : "TID", title : '内外贸', multiSort : true,  halign : "center", align : "left",
	            formatter : function(value) {
		            return HdUtils.code.name('TRADE_ID', value);
	            },
	            sortable : false
	        },
	        {field : "BRAND_NAM", title : "品牌",  multiSort : true,  width : 60, align : "left",   sortable : false  },
	        {field : "CAR_KIND_NAM",title : "车类",   multiSort : true,width : 60,align : "left", sortable : false  },
	        { field : "CAR_TYP_NAM",  title : "车型", multiSort : true,width : 60,align : "left", sortable : false },
	        {field : "CNT", title : "数量", multiSort : true, width:50, align : "center", sortable : false},
	        {field : "BILL_NO", title : '提单号',  multiSort : true,halign : "center", align : "left",  sortable : false }
	        ] ]
	    });
	 }
}
function locArea(){
	if($("#mapLoc").text()=="定位"){
		portCarSerach();
	}else{
		$("#mapLoc").text("定位");
		var cntrLayer=getLayersById("CARLGT_LAYER");
		if(cntrLayer){
			cntrLayer.getSource().clear();
		}
	}
	
}

function portCarSerach(){
	
	var ckRows=$("#cAreaDatagrid").datagrid("getChecked");
	if(ckRows.length>0){
		let pamObj={};
		pamObj.ckRows=ckRows;
		pamObj.comapnyCod=companyCod;
		 $.ajax({
	        url: "/webresources/login/map/mapFeature/getPortCarSearchLocal",
	        contentType:"application/json",
	        type: "POST",
	        dataType: 'json',
	        data:$.toJSON(pamObj), 
	        success: function (data) {               
	        	loadLght(data.data);
	        	$("#mapLoc").text("取消定位");
	        }
	    });
	}else{
		$.messager.alert("提示","请选择定位数据！")
	}
}

var initDao=0;
function loadDaoCargo(){
	if(initDao!=0) return;
	
	initDao=1;
    $("#inCargoGrad").datagrid({
        striped : true,
        url : "/webresources/login/cargo/PortCar/findAll",
        //queryParams : query(),
        method : "post",
        pagination : false,
        singleSelect : false,
        selectOnCheck : false,
        checkOnSelect : true,
        rownumbers : true,
        autoLoad : false,
        toolbar : "inCargoToolBar",
        fit : true,
        fitColumns : false,
        columns : [ [ 
			{field : "brandNam", title : "品牌", multiSort : true,halign : "center", align : "left",sortable : false },
			{field : "carTypNam", title : "车型", multiSort : true,halign : "center",  sortable : false,  align : "left" },
			{field : "cyAreaNo", title : "场号",multiSort : true, halign : "center", align : "left",sortable : false },
			{field : "cyRowNo", title : "行号", multiSort : true, halign : "center",align : "left", sortable : false}, 
			{field : "cyBayNo", title : "车位号",multiSort : true,halign : "center",align : "left",sortable : false}, 
			{field : "cShipNam", title : "船名",  multiSort : true, halign : "center", align : "left", sortable : false },
			{field : "iEId",title : "进出口",multiSort : true, halign : "center", align : "left",
			formatter : function(value) {
			    	if (value == 'I') {	return "进口";} 
			    	else if (value == 'E') {   	return "出口";   }
			    },
			    sortable : false
			}, 
			{field : "tradeId",title : "内外贸",multiSort : true,halign : "center", align : "left",
			formatter : function(value) {
			    if (value == '2') {return "外贸"; } 
			else if (value == '1') {	return "内贸"; }
			    },
			    sortable : false }, 
			{field : "voyage", title : "航次", multiSort : true, halign : "center",align : "left",sortable : false },
			{field : "billNo", title : "提单号", multiSort : true, halign : "center", align : "left",sortable : false },
			{field : "vinNo", title : '车架号', multiSort : true, halign : "center",  align : "left",sortable : false  },

        ] ]
    });
    
    $("#outCargoGrad").datagrid({
        striped : true,
        url : "/webresources/login/cargo/PortCar/findAll",
        //queryParams : query(),
        method : "post",
        pagination : false,
        singleSelect : false,
        selectOnCheck : false,
        checkOnSelect : true,
        rownumbers : true,
        autoLoad : false,
        toolbar : "outCargoToolBar",
        fit : true,
        fitColumns : false,
        columns : [ [ 
			{field : "ck",  checkbox : true,  sortable : false },
			{field : "brandNam", title : "品牌", multiSort : true,halign : "center", align : "left",sortable : false },
			{field : "carTypNam", title : "车型", multiSort : true,halign : "center",  sortable : false,  align : "left" },
			{field : "cyAreaNo", title : "场号",multiSort : true, halign : "center", align : "left",sortable : false },
			{field : "cyRowNo", title : "行号", multiSort : true, halign : "center",align : "left", sortable : false}, 
			{field : "cyBayNo", title : "车位号",multiSort : true,halign : "center",align : "left",sortable : false}, 
			{field : "cShipNam", title : "船名",  multiSort : true, halign : "center", align : "left", sortable : false },
			{field : "iEId",title : "进出口",multiSort : true, halign : "center", align : "left",
			formatter : function(value) {
			    	if (value == 'I') {	return "进口";} 
			    	else if (value == 'E') {   	return "出口";   }
			    },
			    sortable : false
			}, 
			{field : "tradeId",title : "内外贸",multiSort : true,halign : "center", align : "left",
			formatter : function(value) {
			    if (value == '2') {return "外贸"; } 
			else if (value == '1') {	return "内贸"; }
			    },
			    sortable : false }, 
			{field : "voyage", title : "航次", multiSort : true, halign : "center",align : "left",sortable : false },
			{field : "billNo", title : "提单号", multiSort : true, halign : "center", align : "left",sortable : false },
			{field : "vinNo", title : '车架号', multiSort : true, halign : "center",  align : "left",sortable : false  },
        ] ]
    });
	
}

