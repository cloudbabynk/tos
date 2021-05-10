/*项目扩展的js请添加在此*/
HdUtils.selShip = function (callback) {
    HdUtils.dialog.setValue({
        selShipCallBack: callback,
        type:"",
        splitTyp:"",
        workTyp:""
    });
    HdUtils.dialog.show({
        width: 750,
        height: 400,
        title: '选船操作',
        href: "/login/system/contract/chooseshipform.html",
        isSaveHandler: true
    });

}

HdUtils.selTypShip = function (callback, type) {
    HdUtils.dialog.setValue({
        selShipCallBack: callback,
        type: type,
        splitTyp:"",
        workTyp:""
    });
    HdUtils.dialog.show({
        width: 750,
        height: 400,
        title: '选船操作',
        href: "/login/system/contract/chooseshipform.html",
        isSaveHandler: true
    });


}

//计费数据审核专用
HdUtils.selTypShipSplitOut = function (callback, type, splitTyp) {
    HdUtils.dialog.setValue({
        selShipCallBack: callback,
        type: type,
        splitTyp: splitTyp
    });
    HdUtils.dialog.show({
        width: 750,
        height: 400,
        title: '选船操作',
        href: "/login/system/contract/chooseshipform.html",
        isSaveHandler: true
    });

}

// 选择场地车
HdUtils.selPortCarZ = function (callback2, shipNo) {
    HdUtils.dialog.setValue({
        selPortCarCallBack2: callback2,
        shipNo: shipNo
    });
    HdUtils.dialog.show({
        width: 750,
        height: 380,
        title: '选场地车',
        href: "/login/system/ship/chooseportcarform.html",
        isSaveHandler: true
    });

}
// 选仓单
HdUtils.selBill = function (callback1, shipNo) {
    HdUtils.dialog.setValue({
        selBillCallBack: callback1,
        shipNo: shipNo
    });
    HdUtils.dialog.show({
        width: 750,
        height: 380,
        title: '选舱单',
        href: "/login/system/ship/choosebillform.html",
        isSaveHandler: true
    });
}
// 选择在港车
HdUtils.selPortCar = function (callback) {
    HdUtils.dialog.setValue({
        selPortCarCallBack: callback
    });
    HdUtils.dialog.show({
        width: 600,
        height: 500,
        title: '选场地车',
        href: "/login/system/damage/chooseportcarform.html",
        isSaveHandler: true
    });

}

// 超长管理选车
HdUtils.selPortCarC = function (callback) {
    HdUtils.dialog.setValue({
        selPortCarCallBack: callback
    });
    HdUtils.dialog.show({
        width: 700,
        height: 500,
        title: '选场地车',
        href: "/login/system/damage/chooseportcarform2.html",
        isSaveHandler: true
    });

}
// 选车
HdUtils.selCar = function (callback) {
    HdUtils.dialog.setValue({
        selCarCallBack: callback
    });
    HdUtils.dialog.show({
        width: 750,
        height: 380,
        title: '选车操作',
        href: "/login/system/contract/choosecarform.html",
        isSaveHandler: true
    });

}

HdUtils.code.unResean = function (params) {
    var ret = {
        fieldMapping: {
            CODE: 'CODE',
            NAME: 'NAME'
        },
        panelWidth: 450,
        height: 21,
        panelHeight: 300,
        pagination: false,
        showColumns: 'CODE,NAME',
        filterColumns: 'CODE,NAME',
        url: '../webresources/login/privilege/SysCode/findUnReason',
        columns: [[{
            field: 'CODE',
            title: '代码',
            sortable: true,
            width: 80
        }, {
            field: 'NAME',
            title: '名称',
            sortable: true,
            width: 100
        }
        ]]
    };
    $.extend(ret, params);
    return HdUtils.code.base(ret);
};

HdUtils.code.cPort = function (params) {
    var ret = {
        fieldMapping: {
            CODE: 'CODE',
            NAME: 'NAME'
        },
        panelWidth: 450,
        height: 21,
        panelHeight: 300,
        pagination: false,
        showColumns: 'CODE,NAME',
        filterColumns: 'CODE,NAME',
        url: '../webresources/login/privilege/SysCode/cPort',
        columns: [[{
            field: 'CODE',
            title: '代码',
            sortable: true,
            width: 80
        }, {
            field: 'NAME',
            title: '名称',
            sortable: true,
            width: 100
        }
        ]]
    };
    $.extend(ret, params);
    return HdUtils.code.base(ret);
};

// 班次下拉
HdUtils.code.workRun = function (params) {
    var ret = {
        fieldMapping: {
            workRun: 'workRun',
            workRunNam: 'workRunNam',
        },
        pagination: false,
        idField: 'workRun',
        textField: 'workRunNam',
        panelWidth: 400,
        panelHeight: 240,
        method: "POST",
        url: '/webresources/login/base/CWorkRun/find',
        columns: [[{
            title: '班次代码',
            field: 'workRun',
            halign: "center",
            width: 120
        }, {
            title: '班次名称',
            field: 'workRunNam',
            halign: "center",
            width: 120
        }]],
    };
    $.extend(ret, params);
    return HdUtils.code.base(ret);
};
// 班组下拉
HdUtils.code.workClass = function (params) {
    var ret = {
        fieldMapping: {
            classCod: 'classCod',
            classNam: 'classNam',
        },
        pagination: false,
        idField: 'classCod',
        textField: 'classNam',
        panelWidth: 400,
        panelHeight: 240,
        method: "POST",
        url: '/webresources/login/base/CWorkClass/find',
        columns: [[{
            title: '班组代码',
            field: 'classCod',
            halign: "center",
            width: 120
        }, {
            title: '班组名称',
            field: 'classNam',
            halign: "center",
            width: 120
        }]],
    };
    $.extend(ret, params);
    return HdUtils.code.base(ret);
};
// 船舶停时下拉
HdUtils.code.shipStat = function (params) {
    var ret = {
        fieldMapping: {
            shipStatCod: 'shipStatCod',
            shipStatShort: 'shipStatShort',
            shipStatNam: 'shipStatNam',
        },
        pagination: false,
        idField: 'shipStatCod',
        textField: 'shipStatNam',
        panelWidth: 400,
        panelHeight: 240,
        method: "POST",
        url: '/webresources/login/base/CShipStat/find',
        columns: [[{
            title: '动态代码',
            field: 'shipStatCod',
            halign: "center",
            width: 120
        }, {
            title: '动态拼音',
            field: 'shipStatShort',
            halign: "center",
            width: 120
        }, {
            title: '动态名称',
            field: 'shipStatNam',
            halign: "center",
            width: 120
        }]],
    };
    $.extend(ret, params);
    return HdUtils.code.base(ret);
};
// fileupload重写
HdUtils.lyupload = function (param) {
    var callback = param.callback;// 鍥炶皟
    var mutiple = param.mutiple;// 鏄惁鏀寔澶氫笂浼�
    var allowType = param.allowType;// 鍏佽绫诲瀷
    var isReadOnly = param.isReadOnly;// 鏄惁鍙
    if (!mutiple) {
        mutiple = "true";
    }
    if (!allowType) {
        allowType = ".*";
    }
    var url = 'webresources/login/com/FileUpload/?callback=' + callback + "&mutiple=" + mutiple + "&allowType=" + allowType + "&isReadOnly=" + isReadOnly;
    var btnType = 0;
    if (isReadOnly) {
        btnType = 1;
    }
    $.ezModelDialogPassbackValue(url, '上传excel', '', 650, 450, btnType);
}
$.ezModelDialogPassbackValue = function (url, title, func, width, height, type) {// type=1琛ㄧず鏃犳寜閽�
    var iframeDiv = '<iframe name="ez_myDlFrame" frameborder="0" src=' + url + ' style="width: 100%; height: 99%;"></iframe>'
    $.ezModelDialogPassbackValue.handler = $("<div style='padding:2px'/>").dialog({
        content: iframeDiv,
        closed: false,
        title: title,
        width: width,
        height: height,
        onClose: function () {
            $(this).dialog('destroy');
        },
        buttons: function () {
            if (type == 1) {
                return [{
                    text: "关闭",
                    handler: function () {
                        $.ezModelDialogPassbackValue.handler.dialog('destroy');
                    }
                }]
            }
            return [{
                text: "上传",
                handler: function () {
                    window.frames['ez_myDlFrame'].upload(func);
                    window.frames['ez_myDlFrame'].callback = func;
                }
            }, {
                text: "关闭",
                handler: function () {
                    $.ezModelDialogPassbackValue.handler.dialog('destroy');
                }
            }]
        }()
    });
}
// fileupload重写
HdUtils.nmsgupload =
    function (param) {
        var callback = param.callback;// 鍥炶皟
        var mutiple = param.mutiple;// 鏄惁鏀寔澶氫笂浼�
        var allowType = param.allowType;// 鍏佽绫诲瀷
        var isReadOnly = param.isReadOnly;// 鏄惁鍙
        var ingateId = param.ingateId;
        var contractNo = param.contractNo;
        var inCyNam = param.inCyNam;
        if (!mutiple) {
            mutiple = "true";
        }
        if (!allowType) {
            allowType = ".*";
        }
        var url = 'webresources/login/work/FileUpload/?callback=' + callback + "&mutiple=" + mutiple + "&allowType=" + allowType + "&isReadOnly="
            + isReadOnly + "&ingateId=" + ingateId + "&contractNo=" + contractNo + "&inCyNam=" + inCyNam;
        var btnType = 0;
        if (isReadOnly) {
            btnType = 1;
        }
        $.ezModelDialogPassbackValue(url, '上传excel', '', 650, 450, btnType);
    }
//fileupload重写
HdUtils.nmjgupload =
    function (param) {
        var callback = param.callback;// 鍥炶皟
        var mutiple = param.mutiple;// 鏄惁鏀寔澶氫笂浼�
        var allowType = param.allowType;// 鍏佽绫诲瀷
        var isReadOnly = param.isReadOnly;// 鏄惁鍙
        var ingateId = param.ingateId;
        var contractNo = param.contractNo;
        var inCyNam = param.inCyNam;
        if (!mutiple) {
            mutiple = "true";
        }
        if (!allowType) {
            allowType = ".*";
        }
        var url = 'webresources/login/nmjg/FileUpload/?callback=' + callback + "&mutiple=" + mutiple + "&allowType=" + allowType + "&isReadOnly="
            + isReadOnly + "&ingateId=" + ingateId + "&contractNo=" + contractNo + "&inCyNam=" + inCyNam;
        var btnType = 0;
        if (isReadOnly) {
            btnType = 1;
        }
        $.ezModelDialogPassbackValue(url, '上传excel', '', 650, 450, btnType);
    }
// fileupload重写
HdUtils.billVinupload =
    function (param) {
        var callback = param.callback;// 鍥炶皟
        var mutiple = param.mutiple;// 鏄惁鏀寔澶氫笂浼�
        var allowType = param.allowType;// 鍏佽绫诲瀷
        var isReadOnly = param.isReadOnly;// 鏄惁鍙
        var shipNo = param.shipNo;
        var iEId = param.iEId;
        if (!mutiple) {
            mutiple = "true";
        }
        if (!allowType) {
            allowType = ".*";
        }
        var url = 'webresources/login/billvin/FileUpload/?callback=' + callback + "&mutiple=" + mutiple + "&allowType=" + allowType + "&isReadOnly="
            + isReadOnly + "&shipNo=" + shipNo + "&iEId=" + iEId;
        var btnType = 0;
        if (isReadOnly) {
            btnType = 1;
        }
        $.ezModelDialogPassbackValue(url, '上传excel', '', 650, 450, btnType);
    }
// fileupload重写
HdUtils.nmxcupload =
    function (param) {
        var callback = param.callback;// 鍥炶皟
        var mutiple = param.mutiple;// 鏄惁鏀寔澶氫笂浼�
        var allowType = param.allowType;// 鍏佽绫诲瀷
        var isReadOnly = param.isReadOnly;// 鏄惁鍙
        var shipNo = param.shipNo;
        var inCyNam = param.inCyNam;
        if (!mutiple) {
            mutiple = "true";
        }
        if (!allowType) {
            allowType = ".*";
        }
        var url = 'webresources/login/work/FileUpload/?callback=' + callback + "&mutiple=" + mutiple + "&allowType=" + allowType + "&isReadOnly="
            + isReadOnly + "&shipNo=" + shipNo + "&inCyNam=" + inCyNam;
        var btnType = 0;
        if (isReadOnly) {
            btnType = 1;
        }
        $.ezModelDialogPassbackValue(url, '上传excel', '', 650, 450, btnType);
    }
//在场车更新车架号
HdUtils.portCarVinUpload =
    function (param) {
        var callback = param.callback;//
        var mutiple = param.mutiple;//
        var allowType = param.allowType;//
        var isReadOnly = param.isReadOnly;//
        if (!mutiple) {
            mutiple = "true";
        }
        if (!allowType) {
            allowType = ".*";
        }
        var tradeId = param.tradeId;//
        var billNo = param.billNo;//
        var iEId = param.iEId;//
        var cyAreaNo = param.cyAreaNo;//
        var brandCod = param.brandCod;//
        var tranPortCod = param.tranPortCod;//
        var portCarNos =param.portCarNos


        var url = 'webresources/login/portCarVin/FileUpload/?callback=' + callback + "&mutiple=" + mutiple + "&allowType=" + allowType
            + "&isReadOnly=" + isReadOnly +
            "&tradeId=" + tradeId +
            "&billNo=" + billNo +
            "&cyAreaNo=" + cyAreaNo +
            "&brandCod=" + brandCod+
        "&tranPortCod=" + tranPortCod+
        "&iEId=" + iEId+
            "&portCarNos="+portCarNos

        var btnType = 0;
        if (isReadOnly) {
            btnType = 1;
        }
        $.ezModelDialogPassbackValue(url, '上传excel', '', 650, 450, btnType);
    }
//fileupload重写
HdUtils.nmzcupload =
    function (param) {
        var callback = param.callback;// 鍥炶皟
        var mutiple = param.mutiple;// 鏄惁鏀寔澶氫笂浼�
        var allowType = param.allowType;// 鍏佽绫诲瀷
        var isReadOnly = param.isReadOnly;// 鏄惁鍙
        var shipNo = param.shipNo;
        var inCyNam = param.inCyNam;
        if (!mutiple) {
            mutiple = "true";
        }
        if (!allowType) {
            allowType = ".*";
        }
        var url = 'webresources/login/nmzc/FileUpload/?callback=' + callback + "&mutiple=" + mutiple + "&allowType=" + allowType + "&isReadOnly="
            + isReadOnly + "&shipNo=" + shipNo + "&inCyNam=" + inCyNam;
        var btnType = 0;
        if (isReadOnly) {
            btnType = 1;
        }
        $.ezModelDialogPassbackValue(url, '上传excel', '', 650, 450, btnType);
    }


HdUtils.fileCommonupload =
    function (param) {
        var callback = param.callback;//回调
        var isReadOnly = param.isReadOnly;
        var url = param.url;
        var btnType = 0;
        if (isReadOnly) {
            btnType = 1;
        }
        var mutiple = param.mutiple;
        if (!mutiple) {
            mutiple = "true";
        }
        var isReadOnly = param.isReadOnly;
        var pam = "&callback=" + callback + "&mutiple=" + mutiple + "&isReadOnly=" + isReadOnly;
        $.ezModelDialogPassbackValue(url + pam, '上传excel', callback, 650, 450, btnType);
    }

HdUtils.fileCommonupload.close =
    function (param) {
        $.ezModelDialogPassbackValue.handler.dialog('destroy');
    }


// 集疏港计划导入Excel
HdUtils.contractupload = function (param) {
    var callback = param.callback;// 鍥炶皟
    var mutiple = param.mutiple;// 鏄惁鏀寔澶氫笂浼�
    var allowType = param.allowType;// 鍏佽绫诲瀷
    var isReadOnly = param.isReadOnly;// 鏄惁鍙
    if (!mutiple) {
        mutiple = "true";
    }
    if (!allowType) {
        allowType = ".*";
    }
    var url = 'webresources/login/shipbill/FileUpload/?callback=' + callback + "&mutiple=" + mutiple + "&allowType=" + allowType + "&isReadOnly=" + isReadOnly;
    var btnType = 0;
    if (isReadOnly) {
        btnType = 1;
    }
    $.ezModelDialogPassbackValue(url, '上传excel', '', 650, 450, btnType);
}
// 船公司提供的抵港报导入Excel用来生成舱单
HdUtils.sbillupload =
    function (param) {
        var callback = param.callback;// 鍥炶皟
        var mutiple = param.mutiple;// 鏄惁鏀寔澶氫笂浼�
        var allowType = param.allowType;// 鍏佽绫诲瀷
        var isReadOnly = param.isReadOnly;// 鏄惁鍙
        var shipNo = param.shipNo;
        if (!mutiple) {
            mutiple = "true";
        }
        if (!allowType) {
            allowType = ".*";
        }
        var url =
            'webresources/login/shipbill/ShipBillUpload/?callback=' + callback + "&mutiple=" + mutiple + "&allowType=" + allowType + "&isReadOnly="
            + isReadOnly + "&shipNo=" + shipNo;
        var btnType = 0;
        if (isReadOnly) {
            btnType = 1;
        }
        $.ezModelDialogPassbackValue(url, '上传excel', '', 650, 450, btnType);
    }
// 装船计划导入Excel
HdUtils.slpupload =
    function (param) {
        var callback = param.callback;// 鍥炶皟
        var mutiple = param.mutiple;// 鏄惁鏀寔澶氫笂浼�
        var allowType = param.allowType;// 鍏佽绫诲瀷
        var isReadOnly = param.isReadOnly;// 鏄惁鍙
        if (!mutiple) {
            mutiple = "true";
        }
        if (!allowType) {
            allowType = ".*";
        }
        var url =
            'webresources/login/ship/ShipLoadPlanUpload/?callback=' + callback + "&mutiple=" + mutiple + "&allowType=" + allowType + "&isReadOnly="
            + isReadOnly;
        var btnType = 0;
        if (isReadOnly) {
            btnType = 1;
        }
        $.ezModelDialogPassbackValue(url, '上传excel', '', 650, 450, btnType);
    }
//堆场数据导入Excel
HdUtils.dcsjupload =
    function (param) {
        var callback = param.callback;// 鍥炶皟
        var mutiple = param.mutiple;// 鏄惁鏀寔澶氫笂浼�
        var allowType = param.allowType;// 鍏佽绫诲瀷
        var isReadOnly = param.isReadOnly;// 鏄惁鍙
        var workDte = param.workDte;
        if (!mutiple) {
            mutiple = "true";
        }
        if (!allowType) {
            allowType = ".*";
        }
        var url = 'webresources/login/base/MAreaInfoUpload/?callback=' + callback + "&mutiple=" + mutiple + "&allowType=" + allowType + "&isReadOnly="
            + isReadOnly + "&workDte=" + workDte;
        var btnType = 0;
        if (isReadOnly) {
            btnType = 1;
        }
        $.ezModelDialogPassbackValue(url, '上传excel', '', 650, 450, btnType);
    }
// hdSave重写
$.extend($.fn.datagrid.methods, {
    lySave: function (jq, params) {
        return jq.each(function () {
            $(this).datagrid("hdIsEndEdit");
            if ($(this).datagrid("options").hdIsEndEdit) {
                var dg = $(this);
                var changedData = {};
                var changedRows = dg.datagrid('getChecked');

                if (changedRows.length > 0) {
                    changedData.insertedRows = dg.datagrid('getChecked');
                    changedData.updatedRows = dg.datagrid('getChanges', 'updated');
                    changedData.deletedRows = dg.datagrid('getChanges', 'deleted');
                    var msg = "";
                    if (changedData.insertedRows.length > 0) {
                        msg += "将增加" + changedData.insertedRows.length + "条数据！<br />";
                    }
                    if (changedData.updatedRows.length > 0) {
                        msg += "将更新" + changedData.updatedRows.length + "条数据！<br />";
                    }
                    if (changedData.deletedRows.length > 0) {
                        msg += "将删除" + changedData.deletedRows.length + "条数据！";
                    }
                    var url;
                    if (params == undefined || params.url == undefined) {
                        url = dg.datagrid("options").url;
                    } else {
                        url = params.url;
                    }
                    var callback;
                    if (params != undefined && params.callback != undefined) {
                        callback = params.callback;
                    }
                    $.messager.confirm(Resources.WB_CONFIRM, msg, function (r) {
                        if (r) {
                            $.ajax({
                                url: url,
                                type: "POST",
                                dataType: "json",
                                contentType: "application/json",
                                data: $.toJSON(changedData),
                                success: function (data) {
                                    console.log("**********" + data);
                                    HdUtils.messager.show(data, function () {
                                        dg.datagrid("load");
                                        if (jQuery.isFunction(callback)) {
                                            callback();
                                        }
                                    });
                                },
                                error: function (data) {
                                    HdUtils.messager.show(data);
                                }
                            });
                        }
                    });
                } else {
                    $.messager.alert(Resources.WB_INFO, Resources.WB_NOCHANGE_INFO, "info");
                }
            }
        });
    }
})
// 所有泊位下拉
HdUtils.code.berth = function (params) {
    var ret = {
        fieldMapping: {
            berthCod: 'berthCod',
            berthNam: 'berthNam',
        },
        pagination: false,
        idField: 'berthCod',
        textField: 'berthNam',
        panelWidth: 400,
        panelHeight: 240,
        method: "POST",
        url: '/webresources/login/base/CBerth/find',
        columns: [[{
            title: '泊位代码',
            field: 'berthCod',
            halign: "center",
            width: 120
        }, {
            title: '泊位名称',
            field: 'berthNam',
            halign: "center",
            width: 120
        }]],
    };
    $.extend(ret, params);
    return HdUtils.code.base(ret);
};
// 公司泊位下拉
HdUtils.code.gsberth = function (params) {
    var ret = {
        fieldMapping: {
            berthCod: 'berthCod',
            berthNam: 'berthNam',
        },
        pagination: false,
        idField: 'berthCod',
        textField: 'berthNam',
        panelWidth: 400,
        panelHeight: 240,
        method: "POST",
        url: '/webresources/login/base/CBerth/findGs',
        columns: [[{
            title: '泊位代码',
            field: 'berthCod',
            halign: "center",
            width: 120
        }, {
            title: '泊位名称',
            field: 'berthNam',
            halign: "center",
            width: 120
        }]],
    };
    $.extend(ret, params);
    return HdUtils.code.base(ret);
};
// 船舶停时泊位下拉
HdUtils.code.shipstatberth = function (params) {
    var ret = {
        fieldMapping: {
            berthCod: 'berthCod',
            berthNam: 'berthNam',
        },
        pagination: false,
        idField: 'berthCod',
        textField: 'berthNam',
        panelWidth: 400,
        panelHeight: 240,
        method: "POST",
        url: '/webresources/login/base/CBerth/findShipstat',
        columns: [[{
            title: '泊位代码',
            field: 'berthCod',
            halign: "center",
            width: 120
        }, {
            title: '泊位名称',
            field: 'berthNam',
            halign: "center",
            width: 120
        }]],
    };
    $.extend(ret, params);
    return HdUtils.code.base(ret);
};
// 揽桩下拉
HdUtils.code.cable = function (params) {
    var ret = {
        fieldMapping: {
            cableCod: 'cableCod',
            cableNo: 'cableNo',
        },
        pagination: false,
        idField: 'cableCod',
        textField: 'cableNo',
        panelWidth: 400,
        panelHeight: 240,
        method: "POST",
        url: '/webresources/login/base/CCable/findLz',
        columns: [[{
            title: '揽桩代码',
            field: 'cableCod',
            halign: "center",
            width: 120
        }, {
            title: '揽桩名称',
            field: 'cableNo',
            halign: "center",
            width: 120
        }]],
    };
    $.extend(ret, params);
    return HdUtils.code.base(ret);
};
// 机械代码下拉
HdUtils.code.machTypCod = function (params) {
    var ret = {
        fieldMapping: {
            machTypCod: 'machTypCod',
            machTyp: 'machTyp',
        },
        pagination: false,
        idField: 'machTypCod',
        textField: 'machTyp',
        panelWidth: 400,
        panelHeight: 240,
        method: "POST",
        url: '/webresources/login/base/CMachTyp/find',
        columns: [[{
            title: '机械代码',
            field: 'machTypCod',
            halign: "center",
            width: 120
        }, {
            title: '机械名称',
            field: 'machTyp',
            halign: "center",
            width: 120
        }]],
    };
    $.extend(ret, params);
    return HdUtils.code.base(ret);
};
// 司机下拉
HdUtils.code.driver = function (params) {
    var ret = {
        fieldMapping: {
            empNo: 'empNo',
            empNam: 'empNam',
        },
        pagination: false,
        idField: 'empNo',
        textField: 'empNam',
        panelWidth: 400,
        panelHeight: 240,
        method: "POST",
        url: '/webresources/login/base/CEmployee/findSj',
        columns: [[{
            title: '司机工号',
            field: 'empNo',
            halign: "center",
            width: 120
        }, {
            title: '司机姓名',
            field: 'empNam',
            halign: "center",
            width: 120
        }]],
    };
    $.extend(ret, params);
    return HdUtils.code.base(ret);
};
HdUtils.code.bill = function (params) {
    var ret = {
        fieldMapping: {
            billNo: 'billNo'
        },
        panelWidth: 220,
        pagination: false,
        rownumbers: true,
        isPost: false,
        idField: 'billNo',
        textField: 'billNo',
        method: 'post',
        filterColumns: 'billNo',
        showColumns: 'billNo,pieces',
        url: '/webresources/login/plan/PlanGroup/findBill',
        columns: [[{
            field: "billNo",
            title: '提单号',
            width: 120
        }, {
            field: "pieces",
            title: '件数',
            width: 100
        }, {
            field: "iEId",
            title: '进出口',
            hidden: true,
            width: 100
        }]]
    };
    $.extend(ret, params);
    return HdUtils.code.base(ret);
};

// 选择出入库数据上报
HdUtils.selStatisticCount = function (callback) {
    HdUtils.dialog.setValue({
        selSCCallBack: callback
    });
    HdUtils.dialog.show({
        width: 750,
        height: 550,
        title: '货运吞吐量',
        href: "/login/system/work/chooseinoutcytimdata.html",
        isSaveHandler: true
    });
}
// 施工计划导入Excel
HdUtils.constructionupload = function (param) {
    var callback = param.callback;// 鍥炶皟
    var mutiple = param.mutiple;// 鏄惁鏀寔澶氫笂浼�
    var allowType = param.allowType;// 鍏佽绫诲瀷
    var isReadOnly = param.isReadOnly;// 鏄惁鍙
    if (!mutiple) {
        mutiple = "true";
    }
    if (!allowType) {
        allowType = ".*";
    }
    var url = 'webresources/login/plan/FileUpload/?callback=' + callback + "&mutiple=" + mutiple + "&allowType=" + allowType + "&isReadOnly=" + isReadOnly;
    var btnType = 0;
    if (isReadOnly) {
        btnType = 1;
    }
    $.ezModelDialogPassbackValue(url, '上传excel', '', 650, 450, btnType);
}
