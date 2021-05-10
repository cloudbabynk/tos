/*
 * Copyright (C) 2013 HUADONG CO.,LTD.
 * Author:jason <caiyj@huadong.net>
 */

/**
 * @fileoverview ezui的扩展。
 */
// ezui datagrid 事件扩展。
var tid;

/**
 * @author
 *
 * @requires jQuery,EasyUI
 *
 * 扩展datagrid，添加动态增加或删除Editor的方法
 *
 * 例子如下，第二个参数可以是数组
 *
 * datagrid.datagrid('removeEditor', 'cpwd');
 *
 * datagrid.datagrid('addEditor', [ { field : 'ccreatedatetime', editor : { type :
 * 'datetimebox', options : { editable : false } } }, { field :
 * 'cmodifydatetime', editor : { type : 'datetimebox', options : { editable :
 * false } } } ]);
 *
 */

function change(str) {
    var ts;
    $.i18n.properties({
        name : 'menu', // 资源文件名称
        path : '../i18n/project/', // 资源文件路径
        mode : 'map', // 用Map的方式使用资源文件中的值
        language : getBrowserLanguage(),
        callback : function() { // 加载成功后设置显示内容
            ts = $.i18n.prop(str);
        }
    });
    return ts;
}
$.extend($.fn.datagrid.methods, {
    addEditor : function(jq, param) {
        if (param instanceof Array) {
            $.each(param, function(index, item) {
                var e = $(jq).datagrid('getColumnOption', item.field);
                e.editor = item.editor;
            });
        } else {
            var e = $(jq).datagrid('getColumnOption', param.field);
            e.editor = param.editor;
        }
    },
    removeEditor : function(jq, param) {
        if (param instanceof Array) {
            $.each(param, function(index, item) {
                var e = $(jq).datagrid('getColumnOption', item.field);
                e.editor = {};
            });
        } else {
            var e = $(jq).datagrid('getColumnOption', param.field);
            e.editor = {};
        }
    },
    // 编辑状态下动态设置某列的editor
    // datagrid.datagrid('setEditorWhenRowEditing', {index: xxx, field: xxx,
    // editor: xxx});
    setEditorWhenRowEditing : function(jq, param) {
        var target = jq[0];
        var opts = $.data(target, 'datagrid').options;
        var tr = opts.finder.getTr(target, param.index);
        var cell = tr.children("td[field='" + param.field + "']").find('div.datagrid-cell');
        if (cell.length > 0 && param.editor) {
            // get edit type and options
            var edittype, editoptions;
            if (typeof param.editor == 'string') {
                edittype = param.editor;
            } else {
                edittype = param.editor.type;
                editoptions = param.editor.options;
            }
            // get the specified editor
            var editor = opts.editors[edittype];
            if (editor) {
                var oldHtml = cell.html();
                var width = cell._outerWidth();
                cell._outerWidth(width);
                cell.html('<table border="0" cellspacing="0" cellpadding="1"><tr><td></td></tr></table>');
                cell.children('table').bind('click dblclick contextmenu', function(e) {
                    e.stopPropagation();
                });
                $.data(cell[0], 'datagrid.editor', {
                    actions : editor,
                    target : editor.init(cell.find('td'), editoptions),
                    field : param.field,
                    type : edittype,
                    oldHtml : oldHtml
                });
                $(target).datagrid('fixColumnSize', param.field);
                var vbox = cell.find('.validatebox-text');
                if (vbox.length > 0) {
                    vbox.validatebox('validate');
                }
            }
        }
    }
});

$
    .extend($.fn.datagrid.defaults, {
        /**
         * 获得当前index，并保存。
         *
         * @name datagrid#onClickRow
         */
        onClickRow : function(idx, row) {
            var editIdx = $(this).datagrid("options").hdEditIndex;
            if (idx != editIdx && editIdx != undefined) {
                $(this).datagrid("hdIsEndEdit");
                if (!$(this).datagrid("options").hdIsEndEdit) {
                    $(this).datagrid("selectRow", editIdx);
                    $(this).datagrid("options").hdCurrentRowIndex = editIdx;
                    return;
                }
            }
            $(this).datagrid("options").hdCurrentRowIndex = idx;
            $(this).parent().parent().parent().parent().click();
        },
        /**
         * 右键菜单。
         *
         * @name datagrid#onRowContextMenu
         */
        onRowContextMenu : function(e, rowIndex, rowData, opts) {
            if ((opts.isExportExcel == false || opts.isExportExcel == undefined) && opts.isExportExcel != true) {
                /*
                 * opts的isExportExcel属性为是否导出excel的属性,如果为false或者没设置默认不导出,为true才导出
                 * --修改了easyui源码的onRowContextMenu方法,给此方法添加了opts参数
                 */
                return;
            }
            e.preventDefault();
            var cmenu = $('<div/>').appendTo('body');
            var dg = $(this);
            cmenu.menu({
                onClick : function(item) {
                    if (item.name == 'exportExcel') {
                        dg.datagrid("hdExportExcel", {
                            url : dg.datagrid('options').url.replace("/find", "/exportExcel"),
                            exportFileName : (dg.datagrid('options').exportFileName ? (dg.datagrid('options').exportFileName + "-" + new Date()
                                .format("yyyyMMddhhmmss")) : ($('#main').tabs('getSelected').panel('options').title + "-" + new Date()
                                .format("yyyyMMddhhmmss")))
                        });
                    }
                }
            });
            cmenu.menu('show', {
                left : e.pageX,
                top : e.pageY
            });
            cmenu.menu('appendItem', {
                text : '导出Excel',
                name : 'exportExcel',
                iconCls : 'icon-save'
            });
        },
        loader : function(param, successCallback, errorCallback) {
            var opts = $(this).datagrid("options");
            if (!opts.url) {
                return false;
            } else { // V2 增加延迟加载的支持，防止在打开界面时datagrid就加载数据
                if (false === opts.autoLoad) { // autoLoad属性，默认为TRUE。 只生效一次
                    if (!opts.___hasLoaded) {
                        opts.___hasLoaded = true; // 在options中增加标志串
                        var data = {
                            total : 0,
                            rows : []
                        }; // 强制赋值，防止访问rows为空
                        successCallback(data);
                        return false;
                    }
                }
            }

            if (!opts.method || "post" === opts.method.toLowerCase()) { // V1
                // 对POST的行为进行修正，消除服务器端不能解析JSON的问题
                $
                    .ajax({
                        type : "post",
                        url : opts.url,
                        data : $.toJSON(param),
                        dataType : "json", // 以JSON发送全部参数，方便服务端统一处理
                        contentType : "application/json",
                        processData : false, // 此为所加限定，见jQuery.ajax(url,[settings])说明文档
                        success : function(data) {
                            opts.___originalRows = $.extend(true, {}, data); // 保存原始数据，辅助后台判定并发修改---由datagrid('getData')获得的是最终数据而不是原始值
                            successCallback(data);
                        },
                        error : function(XMLHttpRequest, textStatus, errorThrown) {
                            HdUtils.messager
                                .bottomRight("状态信息:" + textStatus + "<br>url:" + this.url + "<br>type:" + this.type + "<br>dataType:" + this.dataType + "<br>contentType:" + this.contentType, "错误状态-[" + XMLHttpRequest.status + "]");
                            errorCallback.apply(this, arguments);
                        }
                    });
            } else {
                // 以下为dataGrid jasonLoader的标准代码
                $
                    .ajax({
                        type : opts.method,
                        url : opts.url,
                        data : param,
                        dataType : "json",
                        success : function(data) {
                            opts.___originalRows = $.extend(true, {}, data);
                            successCallback(data);
                        },
                        error : function(XMLHttpRequest, textStatus, errorThrown) {
                            HdUtils.messager
                                .bottomRight("状态信息:" + textStatus + "<br>url:" + this.url + "<br>type:" + this.type + "<br>dataType:" + this.dataType + "<br>contentType:" + this.contentType, "错误状态-[" + XMLHttpRequest.status + "]");
                            errorCallback.apply(this, arguments);
                        }
                    });
            }
            return true;
        }
    });

$
    .extend($.fn.datagrid.defaults.editors, {
        uppercasebox : {
            init : function(container, options) {
                var input = $('<input type="text" class="easyui-validatebox" style="text-transform:uppercase" onchange="">').appendTo(container);
                if (!options.width) {
                    options.width = container.context.offsetWidth - 3;// fix
                    // prefect
                    // to
                    // -3
                }
                $(container.selector + " input").on("keyup", function() {
                    $(this).val($(this).val().toUpperCase());
                });
                return input.validatebox(options);
            },
            getValue : function(target) {
                return $(target).val();
            },
            setValue : function(target, value) {
                $(target).val(value);
            },
            resize : function(target, width) {
                width = width - 3;// fix prefect to -3
                var input = $(target);
                if ($.boxModel == true) {
                    input.width(width - (input.outerWidth() - input.width()));
                } else {
                    input.width(width);
                }
            },
            destroy : function(target) {
                $(target).validatebox("destroy");
            }
        },
        upload : {
            init : function(container, options) {
                var input = $('<input type="button" value="上传" style="width:50px;" onclick="upload()"/>').appendTo(container);
                return input;
            },
            getValue : function(target) {
                return $(target).val();
            },
            setValue : function(target, value) {
                $(target).val("上传");
            }
        },
        combogrid : {
            init : function(container, options) {
                var input = $('<input type="text" class="datagrid-editable-input">').appendTo(container);
                input.combogrid(options);
                return input;
            },
            resize : function(target, width) {
                $(target).combogrid('resize', width);
            },
            setValue : function(target, value) {
                var options = $(target).combogrid('grid').datagrid('options');
                var selectedRow = null;
                if (options.mapping !== undefined && options.mapping.parent !== undefined) {
                    var parent = $(options.mapping.parent);
                    selectedRow = parent.datagrid('getSelected');
                    var rowIndex = parent.datagrid('getRowIndex', selectedRow);
                    options.mapping.rowIndex = rowIndex;
                }
                if (value == "null" || value == null || value == undefined || value == "") {
                    $(target).combogrid('setValue', "");
                } else {
                    var val = value;
                    var text = value;
                    var mapping = $(target).combogrid("options").fieldMapping;
                    // 将fieldMapping转换为数组。
                    var mv = [];
                    for ( var m in mapping) {
                        mv.push(m);
                        mv.push(eval("mapping[\"" + m + "\"]"));
                    }
                    if (typeof value == "object") {
                        options.mapping.isObject = true;
                        val = eval("value." + eval("mv[1]"));
                        text = eval("value." + eval("mv[3]"));
                    }
                    // else {
                    // val = eval("selectedRow[\"" + eval("mv[0]") + "\"]");
                    // text = eval("selectedRow[\"" + eval("mv[2]") +
                    // "\"]");
                    // }

                    $(target).combogrid("setValue", text);
                    // $(target).combogrid("setText", text);
                }
            },
            getValue : function(target) {
                if ($(target).combogrid('grid').datagrid('options').mapping != undefined && $(target).combogrid('grid').datagrid('options').mapping.isObject != undefined) {
                    return $(target.combogrid('grid').datagrid('getSelected'));
                } else {
                    return $(target).combogrid('getValue');
                }

            },
            destroy : function(target) {
                $(target).combogrid('destroy');
                return;
                // 以下代码在双击编辑时，第一次保存报错，第二次保存正常，原因不明，暂时不调用以下代码 update by huxj
                // 2016-10-12
                var currentDatagrid = $(target).combogrid('grid');
                var mapping = currentDatagrid.datagrid('options').mapping;
                var changes = currentDatagrid.datagrid('getSelected');
                if (changes !== null && mapping !== undefined && mapping.parent !== undefined && mapping.fields !== undefined && mapping.rowIndex !== undefined) {
                    var row = $(mapping.parent).datagrid('getRows')[mapping.rowIndex];
                    $.each(mapping.fields, function(key, value) {
                        eval("(row." + key + "=changes['" + value + "'])");
                    });
                } else if (mapping !== undefined && mapping.parent !== undefined && mapping.fields !== undefined && mapping.rowIndex !== undefined) {
                    // // 清空代码值。
                    // var selectedRow = null;
                    // var parent = $(mapping.parent);
                    // selectedRow = parent.datagrid('getSelected');
                    // var rowIndex = parent.datagrid('getRowIndex',
                    // selectedRow);
                    // for (var m in mapping.fields) {
                    // selectedRow[m]="";
                    // break;
                    // }
                    // parent.datagrid("updateRow",{index:rowIndex,row:selectedRow});
                }
                // else {
                // // $(target).combogrid('setValue','');
                // $(target).combogrid('setValue');
                // var row =
                // $(mapping.parent).datagrid('getRows')[mapping.rowIndex];
                // $.each(mapping.fields, function(key, value) {
                // eval("(row." + key + "='')");
                // });
                //
                // alert($(target).combogrid('isValid'));
                // }
                // if ($(target).combogrid('getValue') == "") {
                // alert("$.data($(target),'cacheData')");
                // $(target).combogrid('setValue','01');
                // alert($($(target).siblings('span').children()[2]).val());
                // // $(target).combogrid('clear');
                // // $(target).combogrid('validate');
                // // alert($(target).combogrid('isValid') + "-" +
                // $(target).combogrid('getText'));
                // }

                $(target).combogrid('destroy');
            }
        },
        /**
         *
         * datagrid中编辑日期datetimebox。
         *
         * @name datagrid#datatimebox
         */
        datetimebox : {
            init : function(container, options) {
                var input = $('<input class="easyui-datetimebox">').appendTo(container);
                return input.datetimebox({
                    formatter : function(date) {
                        return new Date(date).format("yyyy-MM-dd hh:mm:ss");
                    }
                });
            },
            getValue : function(target) {
                return new Date($(target).parent().find('input.combo-value').val()).getTime();
            },
            setValue : function(target, value) {
                if (value != null && value != "") {
                    $(target).datetimebox("setValue", new Date(value).format("yyyy-MM-dd hh:mm:ss"));
                }
            },
            resize : function(target, width) {//wxl 17.12.13
                setTimeout(function() {
                    $(target).datetimebox('resize', width);
                }, 100);

            }
        }
    });

// datebox就是你要自定义editor的名称
$.extend($.fn.datagrid.defaults.editors.datebox, {
    setValue : function(target, value) {
        if (value != null && value != "") {
            $(target).datebox("setValue", new Date(value).format("yyyy-MM-dd"));
        }
    }
});

/**
 * datagrid 扩展方法。
 *
 * @namespace datagrid
 */
$.extend($.fn.datagrid.methods, {
    updateRowWhenEditing : function(jq, param) {
        return jq.each(function() {
            var datagrid = $(this);
            var panel = datagrid.datagrid('getPanel');
            var index = param.index;
            var rowData = datagrid.datagrid('getRows')[index];
            var baseSelector = 'tr[datagrid-row-index="' + index + '"]';
            var div = {};
            $.each(param.row, function(key, value) {
                // FXQ2014-8-20在编辑态，不对rowData.colume=value赋值，因为原代码会导致$(#dg).datagrid('getChanges')返回‘未更改’
                div = panel.find(baseSelector + ' td[field="' + key + '"] div');
                if (div.hasClass('datagrid-editable')) { // 编辑状态
                    var editor = datagrid.datagrid('getEditor', {
                        index : index,
                        field : key
                    });
                    if (editor.type == 'numberbox') {
                        $(editor.target).numberbox('setValue', value);
                    } else if (editor.type == 'validatebox' || editor.type == 'text' || editor.type == 'disabled') {
                        $(editor.target).val(value);
                    } else if (editor.type == 'datetimebox') {
                        $(editor.target).datetimebox('setValue', value);
                    } else if (editor.type == 'datebox') {
                        $(editor.target).datebox('setValue', value);
                    } else if (editor.type == 'combogrid') {
                        $(editor.target).combogrid('setValue', value);
                    } else if (editor.type == 'combobox') {
                        $(editor.target).combobox('setValue', value);
                    }
                    // ADD MORE TYPE SUPPORT
                } else { // 非编辑状态
                    if (!value && 0 != value) { // fxq 2013-9-5 防止null值被更新为
                        // 'null'
                        eval('(rowData.' + key + '=null)');
                    } else {
                        eval('(rowData.' + key + '="' + value + '")');
                    }
                    div.text(value);
                }
            });
        });
    },
    /**
     *
     * @function
     * @name datagrid#hdGetColumnNames
     * @return 返回当前表格显示字段的属性。
     */
    hdGetColumnNames : function(jq) {
        // return jq.each(function () {
        // var columnNames = "";
        // var dg = $(this);
        // // console.log(dg.datagrid("options").columns[0]);
        // if (dg.datagrid("options").frozenColumns != undefined &&
        // dg.datagrid("options").frozenColumns.length > 0) {
        // $.each(dg.datagrid("options").frozenColumns[0], function (idx, val) {
        // if (val.hidden != "true" && !val.checkbox) {
        // columnNames += "," + val.field;
        // }
        // });
        // }
        // $.each(dg.datagrid("options").columns[0], function (idx, val) {
        // if (val.hidden != "true" && !val.checkbox) {
        // columnNames += "," + val.field;
        // }
        // });
        // dg.datagrid("options").queryParams.queryColumns =
        // columnNames.substr(1);
        // });
    },
    /**
     * 右键导出excel功能函数。（由onRowContextMenu实现。）
     *
     * @function
     * @name datagrid#hdContextMenu
     */
    hdContextMenu : function(jq) {
        return jq.each(function() {

        });
    },
    /**
     * 导出excel。excel:false,可以不导出此字段wxl
     *
     * @param {object}
     *            p {url:'是处理导出的Resources。',exportFileName:'导出excel的名称。'}
     * @function
     * @name datagrid#hdExportExcel
     */
    hdExportExcel : function(jq, p) {
        return jq.each(function() {
            if ($(this).datagrid("getRows").length > 0) { 
                var hdExportExcel = {};
                hdExportExcel.columnTitles = "";
                hdExportExcel.columnNames = "";
                hdExportExcel.exportFileName = p.exportFileName;
                var dg = $(this);
                // console.log(dg.datagrid("options").columns[0]);
                if (dg.datagrid("options").frozenColumns != undefined && dg.datagrid("options").frozenColumns.length > 0) {
                    $.each(dg.datagrid("options").frozenColumns[0], function(idx, val) {
                        if (val.excel == false) {
                            return true;// 等同continue;
                        }
                        if (val.excel || (!val.hidden && !val.checkbox)) {// 显性excel,必然会导出,否则hdden和checkbox不导出
                            hdExportExcel.columnTitles += "," + val.title;
                            hdExportExcel.columnNames += "," + val.field;
                        }
                    });
                }
                $.each(dg.datagrid("options").columns[0], function(idx, val) {// wxl
                    // 17.8.3增加判断,和excel:false扩展
                    if (val.excel == false) {
                        return true;// 等同continue;
                    }
                    if (val.excel || (!val.hidden && !val.checkbox)) {// 显性excel,必然会导出,否则hdden和checkbox不导出
                        hdExportExcel.columnTitles += "," + val.title;
                        hdExportExcel.columnNames += "," + val.field;
                    }
                });
                hdExportExcel.columnTitles = hdExportExcel.columnTitles.substr(1);
                hdExportExcel.columnNames = hdExportExcel.columnNames.substr(1);

                dg.datagrid("options").queryParams.hdConditions.hdExportExcel = hdExportExcel;

                var params = dg.datagrid("options").queryParams;

                // exportExcel now.
                // HdUtils.ajax.post({url:p.url,data:params});
                var form = $('<form id="form000" method="POST" action="' + p.url + '" accept-charset="utf-8"></form>');
                $.each(params, function(k, v) {
                    form.append($("<input type='hidden' name='" + k + "' value='" + $.toJSON(v) + "'>"));
                });
                $('body').append(form);
                // console.log(form);
                form.submit();
                form.submit();// document.getElementById("form000").submit();
                // $('body').remove(form);
            } else {
                HdUtils.messager.info("请先查询！");
            }
        });
    },
    /**
     * 获得当前所选中行index。
     *
     * @function
     * @name datagrid#hdGetCurrentRowIndex
     */
    hdGetCurrentRowIndex : function(jq) {
        return jq.each(function() {
            var selectRow = $(this).datagrid("getSelected");
            if (selectRow == undefined) {
                $(this).datagrid("options").hdCurrentRowIndex = -1;
            }
            var selectIdx = $(this).datagrid("getRowIndex", selectRow);
            $(this).datagrid("options").hdCurrentRowIndex = selectIdx;
        });
    },
    /**
     * 判断是否能结束编辑。存在$(this).datagrid("options").hdIsEndEdit中。
     *
     * @function
     * @name datagrid#hdIsEndEdit
     */
    hdIsEndEdit : function(jq) {
        return jq.each(function() {
            var editIdx = $(this).datagrid("options").hdEditIndex;
            if ($(this).datagrid("validateRow", editIdx)) {
                $(this).datagrid("endEdit", editIdx);
                $(this).datagrid("options").hdEditIndex = undefined;
                $(this).datagrid("options").hdIsEndEdit = true;
            } else {
                $(this).datagrid("options").hdIsEndEdit = false;
            }
        });
    },
    /**
     * 增加功能。
     *
     * @function
     * @name datagrid#hdAdd
     */
    hdAdd : function(jq, row) {
        return jq.each(function() {
            $(this).datagrid("hdIsEndEdit");
            if ($(this).datagrid("options").hdIsEndEdit) {
                if (row == undefined || row == "") {
                    row = {};
                }
                var selectIdx = 0;
                $(this).datagrid("insertRow", {
                    index : selectIdx,
                    row : row
                }).datagrid("selectRow", selectIdx).datagrid("beginEdit", selectIdx);
                $(this).datagrid("options").hdEditIndex = selectIdx;
                $(this).datagrid("options").hdCurrentRowIndex = selectIdx;
            }
        });
    },

    /**
     * 增加功能。
     *
     * @function
     * @name datagrid#hdAdd
     */
    hdAdd1 : function(jq, row) {
        return jq.each(function() {
            $(this).datagrid("hdIsEndEdit");
            if ($(this).datagrid("options").hdIsEndEdit) {
                if (row == undefined || row == "") {
                    row = {};
                }
                var selectIdx = row;
                $(this).datagrid("insertRow", {
                    index : selectIdx,
                    row : row
                }).datagrid("selectRow", selectIdx).datagrid("beginEdit", selectIdx);
                $(this).datagrid("options").hdEditIndex = selectIdx;
                $(this).datagrid("options").hdCurrentRowIndex = selectIdx;
            }
        });
    },
    /**
     * 编辑功能。
     *
     * @function
     * @name datagrid#hdEdit
     */
    hdEdit : function(jq) {
        return jq.each(function() {
            // var selectRow = $(this).datagrid("getSelected");
            // if (selectRow == undefined) {
            // $.messager.alert(Resources.WB_INFO, Resources.WB_NOSELECTED,
            // "info");
            // } else {
            // $(this).datagrid("hdIsEndEdit");
            // if ($(this).datagrid("options").hdIsEndEdit) {
            // var selectIdx = $(this).datagrid("getRowIndex", selectRow);
            // $(this).datagrid("beginEdit", selectIdx);
            // $(this).datagrid("options").hdEditIndex = selectIdx;
            //
            // }
            // }
            // 解决datagrid中包含checkbox的情况下，编辑bug update by huxj 2016-09-29
            var selectIdx = $(this).datagrid("options").hdCurrentRowIndex;
            if (selectIdx == undefined) {
                $.messager.alert(Resources.WB_INFO, Resources.WB_NOSELECTED, "info");
                return;
            }
            $(this).datagrid("beginEdit", selectIdx);
            $(this).datagrid("options").hdEditIndex = selectIdx;
        });
    },
    /**
     * 删除功能。 callack 是删除后的callback函数，可以为空。
     *
     * @function
     * @name datagrid#hdRemove
     */
    hdRemove : function(jq, callback) {
        return jq.each(function() {
            var selectIdx = $(this).datagrid("options").hdCurrentRowIndex;
            if (selectIdx == undefined) {
                $.messager.alert(Resources.WB_INFO, Resources.WB_NOSELECTED, "info");
                return;
            }
            var dg = $(this);
            $.messager.confirm(Resources.WB_CONFIRM, Resources.WB_REMOVE_CONFIRM, function(r) {
                if (r) {
                    dg.datagrid('cancelEdit', selectIdx).datagrid('deleteRow', selectIdx);
                    dg.datagrid("options").hdCurrentRowIndex = undefined;
                    if (jQuery.isFunction(callback)) {
                        callback();
                    }
                }
            });
        });
    },
    hdRealRemove : function(jq, params, callback) {
        return jq.each(function() {
            var changedData = {};
            var selectData = $(this).datagrid("getChecked");

            if (selectData.length == 0) {
                $.messager.alert(Resources.WB_INFO, Resources.WB_NOSELECTED, "info");
                return;
            }
            changedData.insertedRows = [];
            changedData.updatedRows = [];
            changedData.deletedRows = selectData;
            var url;
            if (params == undefined || params.url == undefined) {
                url = $(this).datagrid("options").url;
            } else {
                url = params.url;
            }
            var dg = $(this);
            $.messager.confirm(Resources.WB_CONFIRM, Resources.WB_REMOVE_CONFIRM, function(r) {
                if (r) {
                    $.ajax({
                        url : url,
                        type : "POST",
                        dataType : "json",
                        contentType : "application/json",
                        data : $.toJSON(changedData),
                        success : function(data) {
                            data.message = change("successmessage");
                            HdUtils.messager.show(data, function() {
                                dg.datagrid("reload");
                                for (var i = selectData.length - 1; i >= 0; i--) {
                                    var index = dg.datagrid("getRowIndex", selectData[i]);
                                    dg.datagrid('cancelEdit', index).datagrid('deleteRow', index);
                                }
                                if (jQuery.isFunction(callback)) {
                                    callback();
                                }
                            });
                        },
                        error : function(data) {
                            data.message = change("errormessage");
                            HdUtils.messager.show(data);
                        }
                    });
                }
            });
        });
    },
    /**
     * 保存功能。
     *
     * @function
     * @param {object}
     *            params {url:xxx,callback:function(){}}。
     * @name datagrid#hdSave
     */
    hdSave : function(jq, params) {
        return jq.each(function() {
            $(this).datagrid("hdIsEndEdit");
            if ($(this).datagrid("options").hdIsEndEdit) {
                var dg = $(this);
                var changedData = {};
                var changedRows = dg.datagrid('getChanges');

                if (changedRows.length > 0) {
                    changedData.insertedRows = dg.datagrid('getChanges', 'inserted');
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
                    $.messager.confirm(Resources.WB_CONFIRM, msg, function(r) {
                        if (r) {
                            $.ajax({
                                url : url,
                                type : "POST",
                                dataType : "json",
                                contentType : "application/json",
                                data : $.toJSON(changedData),
                                success : function(data) { 
                                    if (data&&data.code) {//消息提示兼容idev8 wxl
                                        if (data.code == "-1") {
                                            HdUtils.messager.info(data.message);
                                            return false;
                                        }
                                        HdUtils.messager.bottomRight(data.message);
                                        dg.datagrid("reload");
                                        if (jQuery.isFunction(callback)) {
                                            callback();
                                        }
                                        return true;
                                    }
                                    HdUtils.messager.show(data, function() {
                                        dg.datagrid("load");
                                        if (jQuery.isFunction(callback)) {
                                            callback();
                                        }
                                    });
                                },
                                error : function(data) {

                                    HdUtils.messager.info(data.responseJSON.message);
                                }
                            });
                        }
                    });
                } else {
                    $.messager.alert(Resources.WB_INFO, Resources.WB_NOCHANGE_INFO, "info");
                }
            }
        });
    },
    /**
     * 重载datagrid。
     *
     * @function
     * @name datagrid#hdReload
     */
    hdReload : function(jq) {
        return jq.each(function() {
            $(this).datagrid("hdIsEndEdit");
            if ($(this).datagrid("options").hdIsEndEdit) {
                var changes = $(this).datagrid('getChanges');
                var dg = $(this);
                if (changes.length > 0) {
                    $.messager.confirm(Resources.WB_CONFIRM, Resources.WB_RELOAD_CONFIRM, function(r) {
                        if (r) {
                            dg.datagrid("options").hdEditIndex = undefined;
                            dg.datagrid("options").hdCurrentRowIndex = undefined;
                            dg.datagrid("reload");
                        }
                    });
                } else {
                    dg.datagrid("options").hdEditIndex = undefined;
                    dg.datagrid("options").hdCurrentRowIndex = undefined;
                    dg.datagrid("reload");
                }
            }
        });
    },
    // support column drag&drop
    columnMoving : function(jq) {
        return jq.each(function() {
            var target = this;
            var cells = $(this).datagrid('getPanel').find('div.datagrid-header td[field]');
            cells.draggable({
                revert : true,
                cursor : 'pointer',
                edge : 5,
                proxy : function(source) {
                    var p = $('<div class="tree-node-proxy tree-dnd-no" style="position:absolute;border:1px solid #00ff00"/>').appendTo('body');
                    p.html($(source).text());
                    p.hide();
                    return p;
                },
                onBeforeDrag : function(e) {
                    // if(event.button == 1)
                    // {
                    e.data.startLeft = $(this).offset().left;
                    e.data.startTop = $(this).offset().top;
                    // }
                },
                onStartDrag : function() {
                    // if(event.button == 1)
                    // {
                    $(this).draggable('proxy').css({
                        left : -10000,
                        top : -10000
                    });
                    // }
                },
                onDrag : function(e) {
                    // if(event.button == 1)
                    // {
                    $(this).draggable('proxy').show().css({
                        left : e.pageX + 15,
                        top : e.pageY + 15
                    });
                    // }
                    return false;
                }
            }).droppable({
                accept : 'td[field]',
                onDragOver : function(e, source) {
                    // if(event.button == 1)
                    // {
                    $(source).draggable('proxy').removeClass('tree-dnd-no').addClass('tree-dnd-yes');
                    $(this).css('border-left', '1px solid #ff0000');
                    // }
                },
                onDragLeave : function(e, source) {
                    // if(event.button == 1)
                    // {
                    $(source).draggable('proxy').removeClass('tree-dnd-yes').addClass('tree-dnd-no');
                    $(this).css('border-left', 0);
                    // }
                },
                onDrop : function(e, source) {
                    // if(event.button == 1)
                    // {
                    $(this).css('border-left', 0);
                    var fromField = $(source).attr('field');
                    var toField = $(this).attr('field');
                    setTimeout(function() {
                        moveField(fromField, toField);
                        $(target).datagrid();
                        $(target).datagrid('columnMoving');
                    }, 0);
                    // }
                }
            });

            // move field to another location
            function moveField(from, to) {
                var columns = $(target).datagrid('options').columns;
                var cc = columns[0];
                var c = _remove(from);
                if (c) {
                    _insert(to, c);
                }

                function _remove(field) {
                    for (var i = 0; i < cc.length; i++) {
                        if (cc[i].field == field) {
                            var c = cc[i];
                            cc.splice(i, 1);
                            return c;
                        }
                    }
                    return null;
                }
                function _insert(field, c) {
                    var newcc = [];
                    for (var i = 0; i < cc.length; i++) {
                        if (cc[i].field == field) {
                            newcc.push(c);
                        }
                        newcc.push(cc[i]);
                    }
                    columns[0] = newcc;
                }
            }
        });
    }
});
