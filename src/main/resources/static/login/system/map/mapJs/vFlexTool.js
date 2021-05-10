function initSearchTool() {
	var dockCod = (companyCod == "TJG_GZ" ? "03406500" : "03409000");
	// 品牌下拉
	$('#bandCodSearch').combobox({
	    url : '/webresources/login/base/CCarTyp/getCBrandDrop',
	    valueField : 'value',
	    textField : 'label',
	    // panelHeight : 500,
	    onSelect : function(rec) {
		    return rec.value;
	    }
	});

	$('#areaNoSearch').combobox({
	    url : '/webresources/login/base/CCyArea/getCCyAreaDrop?dockCod=' + dockCod,
	    valueField : 'value',
	    textField : 'label',
	    panelHeight : '200',
	    onSelect : function(rec) {
		    return rec.value;
	    }
	});
	$('#selAreaIn').combobox({
	    url : '/webresources/login/base/CCyArea/getCCyAreaDrop?dockCod=' + dockCod,
	    valueField : 'value',
	    textField : 'label',
	    panelHeight : '200',
	    onSelect : function(rec) {
		    if ($("#inCargoGrad")) {
			    var pamQuery = new HdQuery();
			    pamQuery.add("currentStat", "2");
			    pamQuery.add("cyAreaNo", rec.value);
			    $("#inCargoGrad").datagrid("load", pamQuery.build());
		    }
	    }
	});
	$('#selAreaOut').combobox({
	    url : '/webresources/login/base/CCyArea/getCCyAreaDrop?dockCod=' + dockCod,
	    valueField : 'value',
	    textField : 'label',
	    panelHeight : '200',
	    onSelect : function(rec) {
		    if ($("#outCargoGrad")) {
			    var pamQuery = new HdQuery();
			    pamQuery.add("currentStat", "2");
			    pamQuery.add("cyAreaNo", rec.value);
			    $("#outCargoGrad").datagrid("load", pamQuery.build());
		    }
	    }
	});

	// 选船
	$("#selShip").on("click", function() {
		HdUtils.selShip(callback);
	});
	function callback(data) {
		$("#cmapShipNam").val(data.cShipNam + "/" + data.ivoyage);
		$("#cShipNo").val(data.shipNo);
	}
	
	$("#selShipDy").on("click", function() {
		HdUtils.selShip(callbackDy);
	});
	function callbackDy(data) {
		$("#cmapShipNamDy").val(data.cShipNam + "/" + data.ivoyage);
		$("#cShipNoDy").val(data.shipNo);
	}

	// 车类下拉
	$('#carKindSearch').combobox({
	    url : '/webresources/login/base/CCarTyp/getCCarKindDrop',
	    valueField : 'value',
	    textField : 'label',
	    // panelHeight : 500,
	    onSelect : function(rec) {
		    return rec.value;
	    }
	});
	// 车型下拉
	$('#carTypeSearch').combobox({
	    url : '/webresources/login/ship/BillCar/getCCarTypDrop',
	    valueField : 'value',
	    textField : 'label',
	    // panelHeight : 500,
	    onSelect : function(rec) {
		    return rec.value;
	    }
	});
}

function portCarInit() {
	var pam = {
		'comapnyCod' : companyCod
	};
	$.ajax({
	    url : "/webresources/login/map/mapFeature/initPortCarByDock",
	    contentType : "application/json",
	    type : "POST",
	    dataType : 'json',
	    data : $.toJSON(pam),
	    success : function(data) {
		    if (data.key == '-1') {
			    HdUtils.messager.info(data.message);
			    return false;
		    }
		    HdUtils.dialog.close();
		    HdUtils.messager.bottomRight('保存成功', '保存操作');
	    }
	});
}
function transChange() {
	var ckRows = $("#outCargoGrad").datagrid("getChecked");
	if (ckRows.length > 0) {

		if (!$("#selAreaIn").combobox("getValue")) {
			$.messager.alert("提示", "请选择导入堆场！")
		}
		let pamObj = {};
		pamObj.transPortList = ckRows;
		pamObj.comapnyCod = companyCod;
		pamObj.inArea = $("#selAreaIn").combobox("getValue");
		$.ajax({
		    url : "/webresources/login/map/mapFeature/transPortCar",
		    contentType : "application/json",
		    type : "POST",
		    dataType : 'json',
		    data : $.toJSON(pamObj),
		    success : function(data) {
			    $.messager.alert("提示", data.message);
			    if (data.code == "1") {
				    $("#outCargoGrad").datagrid("reload");
				    $("#inCargoGrad").datagrid("reload");
				    initMapPortCar();
			    }

		    }
		});
	} else {
		$.messager.alert("提示", "请从堆场选择要导出的数据！")
	}
}
