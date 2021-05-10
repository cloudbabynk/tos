
/**
 * 通过数组中每一个对象的某些属性及值过滤对象
 * 
 * @param {type}
 *            params
 * @returns Array
 * @author xiaojn
 */
Array.prototype.query = function(params) {
  if (params==null||params==undefined) return [];
  var result = [];
  for (var i=0;i<this.length;i++) {
    result.push(this[i]);
  }
  for (var n in params) {
    result = result.query1(n, params[n]);
  }
  return result;
};


/**
 * 将sysCode初始化，取到缓存数据-专用MAP
 * 
 * @param {type}
 *            params
 * @returns Array
 * @author zhangdh
 */
Array.prototype.queryMap = function(params) {
	  if(HdUtils.global.scode.length==0) {
		  HdUtils.global.mapScode(params.fieldCod);
	  } else {
		  for (var i=HdUtils.global.scode.length-1;i>=0;i--) {
			 if(HdUtils.global.scode[i].fieldCod == params.fieldCod) {
				 break;
			 } else if(i==0&&HdUtils.global.scode[i].fieldCod != params.fieldCod){
				 HdUtils.global.mapScode(params.fieldCod);
			 }
		  }
	  }
	  var center = HdUtils.global.scode;
	  var center2 = eval(center);
	  HdUtils.global.scode = center2;
	  if (params==null||params==undefined) return [];
	  var result = [];
	  for (var i=0;i<HdUtils.global.scode.length;i++) {
	    result.push(HdUtils.global.scode[i]);
	  }
	  for (var n in params) {
	    result = result.query1(n, params[n]);
	  } 
    return result;
};
/**
 * 通过数组中每一个对象的某些属性及值过滤对象
 * 
 * @param {type}
 *            params
 * @returns Array
 * @author zhangdh
 */
Array.prototype.query2 = function(params) {
	  if (params==null||params==undefined) return [];
	  var result = [];
	  for (var i=0;i<this.length;i++) {
	    result.push(this[i]);
	  }
	  for (var n in params) {
	    result = result.query1(n, params[n]);
	  } 
      return result;
};
/**
 * 通过数组中每一个对象的某个属性及值过滤对象
 * 
 * @param {type}
 *            name
 * @param {type}
 *            value
 * @returns Array
 * @author zhangdh
 */
Array.prototype.query1 = function(name, value) {
  if (name==null||value==null) return [];
  var result = [];
  for(var i = 0; i < this.length; i++){
    var data = this[i];
    if (data[name]&&data[name]==value) {
      result.push(data);
    }
  }
  return result;
};
/*
 * Copyright (C) 2013-2014 HUADONG CO.,LTD. Author:jason <caiyj@huadong.net>
 */
/**
 * @fileOverview 常用工具类文件。
 */
/**
 * @class
 * @static
 * @description 静态工具类。
 * @author jason <caiyj@huadong.net>
 */
function HdUtils() {


}
/**
 * @namespace 全局变量
 * @memberOf HdUtils#
 */
HdUtils.global = {};
/**
 * 通用配置属性
 */
HdUtils.global.config={};
// 地图内网地址：
// HdUtils.global.config.geoIp ="http://10.197.32.164:8080/geoserver";
// 地图外网地址：
HdUtils.global.config.geoIp ="http://113.108.190.168:8004/geoserver"; 

// 报表内网地址：
// HdUtils.global.config.ip = "http://10.197.32.165";
// 报表外网地址：
HdUtils.global.config.ip = "http://113.108.190.168";
HdUtils.global.config.pdfIp = HdUtils.global.config.ip+":8007/pdf/viewer.html";// 报表pdf展示
HdUtils.global.config.htmlIp = HdUtils.global.config.ip+":8007/report/html";// 报表html展示
HdUtils.global.config.chartsIp = HdUtils.global.config.ip+":8007/report/htmlimg";// 图表html展示
HdUtils.global.config.excelIp = HdUtils.global.config.ip+":8007/report/excel";// 报表excel导出
HdUtils.global.config.jtgIp = HdUtils.global.config.ip+":8003/jtgbb/";// 交统港报表展示

HdUtils.global.config.account = function(){
	 var account;
	  $.ajax({
	    method: "GET",
	    url: '/login/PrivilegeController/getLoginAccount?t=11&s=' +Math.random(),
	    dataType: "json",
	    async: false,
	    contentType: "application/json",
	    success: function (data) {
	    	account = data.account;
	    }
	 });
	return account;
}

HdUtils.global.config.token = function(account){
	 var token;
	  $.ajax({
	    method: "GET",
	    url: '/login/PrivilegeController/getLoginToken?name='+account,
	    dataType: "json",
	    async: false,
	    contentType: "application/json",
	    success: function (data) {
	    	token = data.token;
	    }
	 });
	return token;
	 }
/**
 * @namespace code
 * @memberOf HdUtils#
 */
HdUtils.code = {};

/**
 * 系统代码编辑控件data-options
 * 
 * @param {type}
 *            params 系统代码过滤条件
 * @param {type}
 *            options 控件自定义options
 * @returns data-options
 */
HdUtils.global.scode = [];
HdUtils.global.flushScode = function(params) {
	var scode;
	$.ajax({
	    method : "POST",
	    url : '/webresources/login/privilege/SysCode/findAll?fieldCod=' + params,
	    dataType : "text",
	    async : false,
	    contentType : "application/json",
	    success : function(data) {
		    scode = data;
	    }
	});
	var betweenScode = scode;
	var scodeNew = eval(betweenScode);
	var scodeOld = [];
	for (var i = 0; i < HdUtils.global.scode.length; i++) {
		scodeNew.push(HdUtils.global.scode[i]);
	}
	HdUtils.global.scode = scodeNew;
};

HdUtils.global.mapScode = function(params) {
	var scode;
	$.ajax({
	    method : "POST",
	    url : '/webresources/login/privilege/SysCode/findAll?fieldCod=' + params,
	    dataType : "text",
	    async : false,
	    contentType : "application/json",
	    success : function(data) {
		    scode = data;
	    }
	});
	var betweenScode = scode;
	var scodeNew = eval(betweenScode);
	var scodeOld = [];
	for (var i = 0; i < HdUtils.global.scode.length; i++) {
		scodeNew.push(HdUtils.global.scode[i]);
	}
	HdUtils.global.scode = scodeNew;
};

HdUtils.code.scode = function(params, options, addNull) {
	if (HdUtils.global.scode.length == 0) {
		HdUtils.global.flushScode(params.fieldCod);
	} else {
		for (var i = HdUtils.global.scode.length - 1; i >= 0; i--) {
			if (HdUtils.global.scode[i].fieldCod == params.fieldCod) {
				break;
			} else if (i == 0 && HdUtils.global.scode[i].fieldCod != params.fieldCod) {
				HdUtils.global.flushScode(params.fieldCod);
			}
		}
	}
	var scode1 = HdUtils.global.scode;
	var scode2 = eval(scode1);
	HdUtils.global.scode = scode2;
	var data = null;
	var dArr = scode2.query2(params);
	if (addNull === true) {
		data = [ {
		    name : '-',
		    code : ''
		} ];
		for (var i = 0; i < dArr.length; i++) {
			data.push(dArr[i]);
		}
	} else {
		data = dArr;
	}
	var ret = {
	    textField : 'name',
	    valueField : 'code',
	    data : data,
	    editable : true,
	    panelHeight : 'auto'
	};
	if(getBrowserLanguage()=="en"){
		ret.textField="enName";
	}
	$.extend(ret, options);
	return ret;
};



/**
 * 代码表基础数据。
 * 
 * @memberOf HdUtils#code
 * @param {object}
 *            params ezui combogrid参数。
 * @returns {object} 返回ezui combogrid参数。
 */
HdUtils.code.base = function(params) {
    var ret = {};
    var valueField = "";
    var textField = "";
    var i = 0;
    if (params != undefined && params.fieldMapping != undefined) {
        $.each(params.fieldMapping, function(idx, val) {
            if (i == 0) {
                valueField = val;
            }
            if (i == 1) {
                textField = val;
            }
            i += 1;
        });
    }
    /* params.fieldMapping 只有一个值时textField会被赋为空字符串,因此手动赋值textField.实现显示与保存同一字段 */
    if(textField==null||textField==""||textField==undefined){
    	textField=valueField;
    }
    ret.mapping = params.parentId ? {
        parent: params.parentId,
        fields: params.fieldMapping
    } : null;

// ret.url = url;
// ret.columns = columns;
// ret.queryParams = {
// queryColumns: params.filterColumns?params.filterColumns:"",
// hdConditions: params.hdConditions ? $.toJSON(params.hdConditions) : "",
// showColumns: params.showColumns ? params.showColumns : ""
// };
  // update by huxj 2016-10-17 为了兼容idev7 去掉andItems 以及自动添加queryColumns
    if (params != undefined && params.queryParams != undefined) {
   	 params.queryParams.queryColumns = params.filterColumns ? params.filterColumns : valueField + "," + textField;
    };
    ret.queryParams = (params && params.queryParams) ? params.queryParams : 
            {
                queryColumns: params.filterColumns ? params.filterColumns : valueField + "," + textField,
                hdConditions: params.hdConditions ? params.hdConditions : {}
            };
   // ret.queryParams = queryParams;
    ret.required = "false";
    ret.idField = valueField;
    ret.valueField = valueField;
    ret.textField = textField;
    ret.sortName = valueField;
    ret.remoteSort = "false";
    ret.sortOrder = "asc";
    ret.mode = "remote";
    ret.pagination = true;
    ret.pageSize = "20";
    ret.method = "POST";
    ret.fitColumns = "true";
    // ret.filterColumns = valueField + "," + textField;
    ret.striped = "true";
    ret.width = "120";
    ret.singleSelect = "true";
    ret.panelWidth = params.pagination ? 500 : 250;
    // 是否校验，默认为true(校验)
    ret.isValid = params.isValid === undefined ? true : params.isValid;
    
    if(ret.isValid){
        ret.onHidePanel = function() {
            //console.log("onHidePanel");
            var isPaging = $(this).combogrid('options').pagination;
            var selections = $(this).combogrid('grid').datagrid('getSelections');  // 得到当前值
            if (selections == null || selections.length == 0) { // 没有对应行
                //没有对应行，则获取第一页数据赋值到下拉列表
                //重新刷新datagrid
                var grid = $(this).combogrid('grid');
                $(this).combogrid('setValue',"");
                var params = grid.datagrid('options').queryParams;
                delete params.q;
                grid.datagrid('reload');
            }
        };
    }
    /**
	 * insertRows 为要插入的列[{},{}]
	 */
    if (params.insertRows) {
        ret.onLoadSuccess = function(data) {
            for (var i = 0; i < params.insertRows.length; i++) {
                $(this).combogrid("grid").datagrid("insertRow", {index: i, row: params.insertRows[i]});
                $(this).combogrid("grid").datagrid("selectRow", 0);
            }
        };
    }
    if (params.multiple && params.columns) {
        _columns = [[{checkbox: true}]];
        params.columns[0] = _columns[0].concat(params.columns[0]);
// console.log(params.columns);
    }
    // from 处理方法：parendId = formId, fieldMapping = input name
// if (params.isForm) {
// params.parentId
// }
    // 是否有必要删除datagrid不支持的参数？
    $.extend(ret, params);
    return ret;
};
/**
 * 通用代码表。 jason <caiyj@huadong.net>
 * 
 * @param {type}
 *            parentId 父窗
 * @param {type}
 *            required 是否必需 default:false
 * @param {type}
 *            fieldMapping 字段映射(js对象，0-code，1-name) （必填）
 * @param {type}
 *            url 数据url （必填）
 * @param {type}
 *            andItems 数据过滤条件 default：
 * @param {type}
 *            onChange 下拉值改变触发事件
 * @param {type}
 *            columns 下拉列信息（必填）
 * @param {type}
 *            filterColumns 过滤 default：下拉过滤fieldMapping列名
 * @param {type}
 *            multiple 多选。default:false
 * @returns {jQuery.hd.ezui.code.cCommon.ret} 返回options
 */
HdUtils.code.base_new = function(params) {
    var ret = {};
    var valueField = "";
    var textField = "";
    var i = 0;
    
    if(params.mapping){           // FXQ2013-8-9 允许调用方以传统方式传来mapping{parent:,
									// field:,} 映射对像
        ret.mapping=$.extend({},params.mapping);
    }else{                        // 以新方式传来parentId和 fieldMapping单立设置
        ret.mapping = (params.parentId ||params.fieldMapping)?     // 原来只判断parentId可能丢失mapping信息导致找不到valueField和nameField
          {    parent: params.parentId ? params.parentId : undefined ,
               fields: params.fieldMapping ? params.fieldMapping : undefined   // fxq2013-8-9
																				// 防范没有给定fieldMapping导致fieldMapping=null从而后续报错的问题
          }
        : undefined ;      // ？？？是否需要防范对combogrid的影响,见datagrid-helper代码
    }
    // 自动查找idField/valueField 和 nameField
    if(ret.mapping && ret.mapping.fields){  // FXQ
											// 为保持兼容，从设置mapping的代码前移动到代码后，并改变if条件。
											// 原条件params && params.fieldMapping
        $.each(ret.mapping.fields, function(idx, val) {
            if (i == 0) {
                valueField = val;
            }
            if (i == 1) {
                textField = val;
            }
            i += 1;
        });
    }
    /* params.fieldMapping 只有一个值时textField会被赋为空字符串,因此手动赋值textField.实现显示与保存同一字段 */
    if(textField==null||textField==""||textField==undefined){
    	textField=valueField;
    }
    
// ret.queryParams = (params && params.queryParams) ? params.queryParams :
// //fxq2013-8-9 如果调用者指定了queryParams 则直接使用之
// {
// queryColumns: params.filterColumns ? params.filterColumns : valueField + ","
// + textField,
// andItems: params.andItems ? $.toJSON(params.andItems) : ""
// };
    // update by huxj 2016-10-17 为了兼容idev7 去掉andItems 以及自动添加queryColumns
     if (params != undefined && params.queryParams != undefined) {
    	 params.queryParams.queryColumns = params.filterColumns ? params.filterColumns : valueField + "," + textField;
     }
     ret.queryParams = (params && params.queryParams) ? params.queryParams : 
     {
    	 queryColumns: params.filterColumns ? params.filterColumns : valueField + "," + textField,
         hdConditions: params.hdConditions ? params.hdConditions : {}
    };    
    // 傅新奇 2015-5-9 由于 combogrid-extended.js
	// 已经消除了代码-名称对照的问题，因此这里删除了原默认的onHidePanel方法（处理不一致的CODE取值）
    ret.onHidePanel= null; // 由于combogrid默认有一个空函数作为onHidePanel，会导致combogrid-extended.js中误判，因此这里取消其默认定义
    ret.required = false;
    ret.idField = valueField;
    ret.valueField = valueField;
    ret.textField = textField;
    ret.sortName = valueField;
    ret.remoteSort = params.sortName ? true : false;            // fxq
																// 修正排序无效的BUG
    ret.sortOrder = params.sortOrder? params.sortOrder : 'asc';
    ret.mode = (params.mode)? params.mode : 'remote';  // 本地过滤，除非明确指定 //本地过滤不可行
														// ret.mode =
														// (true===params.pagination)?
														// 'remote' : 'local';
    ret.pagination = (true===params.pagination);       // 未指定，则默认不分页
    ret.pageSize = ret.pagination ? 20: undefined;  // 用默认函数没关系，还会被params的值替换
    ret.method = 'GET';
    ret.fitColumns = true;  // 问题：true 速度太慢，但false导致调整字段宽度不合理
    ret.striped = true;
    ret.singleSelect = true;
    ret.panelWidth = params.panelWidth ? params.panelWidth : 250;
    ret.rownumbers = false;
    
    // 自适应列宽
    $.each(params.columns[0], function(idx, column){
        if(!column.width ||column.width<6){
            column.width=40;
        }
    });
    // 处理多选
    if (params.multiple && params.columns) {
        _columns = [[{checkbox: true}]];
        params.columns[0] = _columns[0].concat(params.columns[0]);
    }
    return $.extend(true,ret, params);  // fxq2013-8-9 改为深拷贝，防范不同实例之间的冲突。
};

/**
 * @namespace messager
 * @memberOf HdUtils#
 */
HdUtils.messager = {};
/**
 * 右下角弹出窗口消息。
 * 
 * @param {string}
 *            msg 消息体。
 * @param {string}
 *            title 消息标题。
 * @param {string}
 *            timeout 窗口多少毫秒消失。
 * @param {string}
 *            showType 显示类型：slide默认。
 * @returns {undefined}
 * @memberOf HdUtils#messager
 */
HdUtils.messager.bottomRight = function(msg, title, timeout, showType) {
    $.messager.show({
        title: title ? title : '提示消息',
        msg: msg,
        timeout: timeout ? timeout : 2000,
        showType: showType ? showType : 'slide',
        height: 150
    });
};
/**
 * 状态栏消息。
 * 
 * @param {string}
 *            msg 消息
 * @returns {undefined}
 * @memberOf HdUtils#messager
 */
HdUtils.messager.status = function(msg) {
    $('#mainBody').layout('panel', 'south').panel({title: msg});
};
/**
 * 标准窗口提示。
 * 
 * @param {string}
 *            data 标准消息体。
 * @param {string}
 *            callback 回调函数。
 * @returns {undefined}
 * @memberOf HdUtils#messager
 */
HdUtils.messager.show = function(data, callback) {
    if (data) {
        if (data.code && data.code.charAt(3) === '1') {
            // success
           // this.bottomRight(data.message,"["+HdUtils.ezui.getCurrTabName()+"]");
        	this.bottomRight(data.message,'提示');
// this.status(data.message);
            if ($.isFunction(callback)) {
                callback();
            }
        } else {
            // error
            var msg = "";
            msg += "代码：" + data.code + "<br />";
            msg += "信息：" + data.message + "<br />";
            if (data.detail) {
                var content = data.detail.replace(/\"/g, "").replace(/\'/g, "");
                msg += "详细：" + "<span title=\"" + content + "\" onClick=\"alert(\'" + content.replace(/\n/g, "\\n") + "\')\">点击查看</span>"
            }
            $.messager.alert("返回信息", msg, "info");
        }
    } else {
        $.messager.alert("返回信息", "无消息！", "info");
    }

};
/**
 * 普通串口提示。
 * 
 * @param {string}
 *            msg 消息体。
 * @returns {undefined}
 * @memberOf HdUtils#messager
 */
HdUtils.messager.info = function(msg) {
    $.messager.alert("提示", msg, "info");
};

/**
 * 跑马灯显示。
 * 
 * @param {string}
 *            msg 跑马灯消息。
 * @returns {undefined}
 */
HdUtils.messager.marquee = function(msg) {
    $('#mainMarquee').html("<marquee scrollamount='3'>" + msg + "</marquee>");
};

/**
 * @namespace formatter
 * @memberOf HdUtils#
 */
HdUtils.formatter = {};
/**
 * 数字通用格式化。
 * 
 * @param {type}
 *            value
 * @param {type}
 *            precision
 * @returns {String}
 * @memberOf HdUtils#formatter
 */
HdUtils.formatter.precisionCommon = function(value, precision) {
    if (value==null||value==undefined) {
        return "";
    } 
    var v = parseFloat(value).toFixed(parseInt(precision)).split(".");
    var ret = v[0].replace(/\d+?(?=(?:\d{3})+$)/img, "$&,");
    if (v.length > 1) {
        ret += "." + v[1];
    }
    return ret;
};
/**
 * 数字通用格式化。
 * 
 * @param {type}
 *            value
 * @returns {String}
 * @memberOf HdUtils#formatter
 */
HdUtils.formatter.date = function(value) {
	return value ? new Date(value).format('yyyy-MM-dd') : '';
}
/**
 * 数字通用格式化。
 * 
 * @param {type}
 *            value
 * @returns {String}
 * @memberOf HdUtils#formatter
 */
HdUtils.formatter.datetime = function(value) {
	return value ? new Date(value).format('yyyy-MM-dd hh:mm:ss') : '';
}

/**
 * @namespace dialog
 * @memberOf HdUtils#
 */
HdUtils.dialog = {};
/**
 * 显示对话框。
 * 
 * @param {object}
 *            params 例：{title:标题,href:module/内容.html,width:800,height:500}
 * @returns {undefined}
 * @memberOf HdUtils#dialog wxl
 *           17.7.19修改为栈弹窗,HdUtils.dialog.getValue('saveHandler')指定确定时的回调,isSaveHandler显示确定和关闭
 */
HdUtils.dialog.show = function(params) {
	if (!HdUtils.dialog.arr) {
		HdUtils.dialog.arr = [];
	}
    var _def = {
        title: "标题", // 标题
        width: 1050,
        height: 550,
        closed: false,
        cache: false,
        resizable: true,
        modal: true,
        // 关闭窗口的时候，unbind键。
        onClose: function() {
            $(document).unbind("keyup");
            HdUtils.dialog.arr.pop();
    			if (HdUtils.dialog.arr.length > 0) {
    				HdUtils.dialog.handler = HdUtils.dialog.arr[HdUtils.dialog.arr.length - 1];
    				// 2.关闭窗口,切换到上一个
    			}
    			$(this).dialog('destroy');
    		}
           
        }
     
    var str = "?";

    if (params != undefined && params.href != undefined && params.href.indexOf("?") >= 0) {
        str = "&";
    }
    params.href = params.href + str + "timestamp=" + new Date().getTime();
    if (params.global != undefined) {
        params.hdGlobal = params.global;
    }
    if(params.openWithIframe) {
        params.content = '<iframe scrolling="no" frameborder="0"  src="'+encodeURI(params.href)+'" style="width:100%;height:100%;"></iframe>';
        delete params.href;
    }
    $.extend(_def, params);
    
    if (params.isSaveNoCancel) {
        _def.buttons = [{
                text: i18n("sure"),
                iconCls: 'icon-ok',
                handler: function() {
                	HdUtils.dialog.getValue('saveHandler')();
      	
                }
            }];
    }else if (params.isSaveHandler) {
        _def.buttons = [{
                text: i18n("sure"),
                iconCls: 'icon-ok',
                handler: function() {
                	HdUtils.dialog.getValue('saveHandler')();
      	
                }
            }, {
                text: i18n("cancel"),
                iconCls: "icon-cancel",
                handler: function() {
                    HdUtils.dialog.handler.dialog("close");
                }
            }];
    } else if (params.isOkHandler) {
    	_def.buttons = [{
            text: i18n("sure"),
            iconCls: 'icon-ok',
            handler: function () {
                HdUtils.dialog.getValue("okHandler")();
            }
        },{
            text: i18n("cancel"),
            iconCls: "icon-cancel",
            handler: function () {
            	HdUtils.dialog.handler.dialog("close");
            }
        }] ;
    }else if (params.isShowExitBtn) {
        _def.buttons = [{
                text: i18n("cancel"),
                iconCls: "icon-cancel",
                handler: function() {
                	  HdUtils.dialog.handler.dialog("close");
                }}];
    }
    // 绑定enter,esc键。
    $(document).bind("keyup", function(event) {
        if (event.which == 27) {
        	HdUtils.dialog.handler.dialog("options").callback = undefined;
            
            HdUtils.dialog.handler.dialog("close");
        }
    });
    var myHandler = $('<div/>').dialog(_def);
    HdUtils.dialog.arr.push(myHandler);
    HdUtils.dialog.handler = myHandler;
	// 1.最后一次窗口的handler
	return myHandler;
};
HdUtils.dialog.maps = {};// wxl 数据存储的
HdUtils.dialog.setValue = function(params) {
    var opts = HdUtils.dialog.maps;
    if(params.saveHandler){// 弹窗保存是栈的形式,这样支持多层弹窗
    	HdUtils.dialog.handler.saveHandler=params.saveHandler;
    }
    if(params.okHandler){// 弹窗保存是栈的形式,这样支持多层弹窗
    	HdUtils.dialog.handler.okHandler=params.okHandler;
    }
    if (!opts.globalHd) {
    	opts.globalHd={}
      }
    $.extend(opts.globalHd,params);
};

HdUtils.dialog.getValue = function(param) {
	 if('saveHandler'==param&&HdUtils.dialog.handler){// 弹窗保存是栈的形式,这样支持多层弹窗
	    	return HdUtils.dialog.handler.saveHandler;
	    }
	 if('okHandler'==param&&HdUtils.dialog.handler){// 弹窗保存是栈的形式,这样支持多层弹窗
	    	return HdUtils.dialog.handler.okHandler;
	    }
	 var opts = HdUtils.dialog.maps;
    if (opts != undefined) {
    	try{
        var p = eval("opts.globalHd." + param);
        return p;
    	} catch(error) {
    		return null;
    	}
    } else {
        return undefined;
    }
};

HdUtils.dialog.close = function() {
    var opts = HdUtils.dialog.handler.dialog("options");
    if (opts != undefined) {
        opts.params = undefined;
        opts.buttons = [];
    }
    HdUtils.dialog.handler.dialog("close");
};

HdUtils.ajax = {};

HdUtils.ajax.get = function(p) {
    var ajaxP = {
        url: "",
        type: "GET",
        beforeSend:maskLoading,
        complete:maskEnd,
        success: function(data) {
            HdUtils.messager.info(data);
        },
        error: function(data) {
            HdUtils.messager.show(data);
        }
    };
    $.extend(ajaxP, p);
    $.ajax(ajaxP);
};
/**
 * ezui与后台的post。
 * 
 * @param {object}
 *            p
 *            {url:"",data:{},success:function(data){},error:function(data){}};
 *            data可以为对象，也可以为json字符串。
 * @returns {undefined}
 */
HdUtils.ajax.post = function(p) {
    if (p != undefined && p.data != undefined && $.isPlainObject(p.data)) {
        p.data = $.toJSON(p.data);
    }
    var ajaxP = {
        url: "",
        type: "POST",
        dataType: "json",
        contentType: "application/json",
        beforeSend:maskLoading,
        complete:maskEnd,
        success: function(data) {
            HdUtils.messager.info(data);
        },
        error: function(data) {
            HdUtils.messager.show(data);
        }
    };
    $.extend(ajaxP, p);
    $.ajax(ajaxP);
};


/**
 * @namespace ezui相关操作
 * @memberOf HdUtils#
 */
HdUtils.ezui = {};

/**
 * 获取当前打开的tab的名字。
 * 
 * @returns {unresolved}
 */
HdUtils.ezui.getCurrTabName = function() {
    return $('#main').tabs('getSelected').panel("options").title;
};
HdUtils.ezui.dateBox = {};
HdUtils.ezui.dateBox.selectMonth = function(param) {
	 var db = $(param);
     db.datebox({
         onShowPanel: function () {// 显示日趋选择对象后再触发弹出月份层的事件，初始化时没有生成月份层
             span.trigger('click'); // 触发click事件弹出月份层
             if (!tds) setTimeout(function () {// 延时触发获取月份对象，因为上面的事件触发和对象生成有时间间隔
                 tds = p.find('div.calendar-menu-month-inner td');
                 tds.click(function (e) {
                     e.stopPropagation(); // 禁止冒泡执行easyui给月份绑定的事件
                     var year = /\d{4}/.exec(span.html())[0]// 得到年份
                     , month = parseInt($(this).attr('abbr'), 10);// 月份，这里不需要+1
                     if(month<10){
                    	 month = "0"+month;       
                    	}
                     db.datebox('hidePanel')// 隐藏日期对象
                     .datebox('setValue', year + '-' + month); // 设置日期的值
                 });
             }, 0);
             yearIpt.unbind();// 解绑年份输入框中任何事件
         },
         parser: function (s) {
             if (!s) return new Date();
             var arr = s.split('-');
             return new Date(parseInt(arr[0], 10), parseInt(arr[1], 10) - 1, 1);
         },
         formatter: function (d) { return d.getFullYear() + '-' + (d.getMonth() + 1);/* getMonth返回的是0开始的，忘记了。。已修正 */ }
     });
     var p = db.datebox('panel'), // 日期选择对象
         tds = false, // 日期选择对象中月份
         aToday = p.find('a.datebox-current'),
         yearIpt = p.find('input.calendar-menu-year'),// 年份输入框
         // 显示月份层的触发控件
         span = aToday.length ? p.find('div.calendar-title span') :// 1.3.x版本
         p.find('span.calendar-text'); // 1.4.x版本
     if (aToday.length) {// 1.3.x版本，取消Today按钮的click事件，重新绑定新事件设置日期框为今天，防止弹出日期选择面板
        
         aToday.unbind('click').click(function () {
             var now=new Date();
             db.datebox('hidePanel').datebox('setValue', now.getFullYear() + '-' + (now.getMonth() + 1));
         });
     }
};

HdUtils.ezui.dateBox.getCurentDateStr = function(date)
{ 
	var now = new Date();
    var year = now.getFullYear();       //年
    var month = now.getMonth() + 1;     //月
    var day = now.getDate()-(date?date:0);            //日
    var clock = year + "-";
    if(month < 10) clock += "0";       
    clock += month + "-";
    if(day < 10) clock += "0"; 
    clock += day;
    return clock; 
}	
	

/**
 * UUID 生成
 */
var UUID = {
		  //
		  // Loose interpretation of the specification DCE 1.1: Remote
			// Procedure Call
		  // since JavaScript doesn't allow access to internal systems, the
			// last 48 bits
		  // of the node section is made up using a series of random numbers
			// (6 octets long).
		  // 
		  next: function () {
		    var dg = new Date(1582, 10, 15, 0, 0, 0, 0);
		    var dc = new Date();
		    var t = dc.getTime() - dg.getTime();
		    var tl = this.getIntegerBits(t, 0, 31);
		    var tm = this.getIntegerBits(t, 32, 47);
		    var thv = this.getIntegerBits(t, 48, 59) + '1'; // version 1,
															// security version
															// is 2
		    var csar = this.getIntegerBits(this.rand(4095), 0, 7);
		    var csl = this.getIntegerBits(this.rand(4095), 0, 7);

		    // since detection of anything about the machine/browser is far to
			// buggy,
		    // include some more random numbers here
		    // if NIC or an IP can be obtained reliably, that should be put in
		    // here instead.
		    var n = this.getIntegerBits(this.rand(8191), 0, 7) +
		        this.getIntegerBits(this.rand(8191), 8, 15) +
		        this.getIntegerBits(this.rand(8191), 0, 7) +
		        this.getIntegerBits(this.rand(8191), 8, 15) +
		        this.getIntegerBits(this.rand(8191), 0, 15); // this last
																// number is two
																// octets long
		    return tl + '-' + tm + '-' + thv + '-' + csar + csl + "-" + n;
		  },
		  // pick a random number within a range of numbers
		  // int b rand(int a); where 0 <= b <= a
		  rand: function (max) {
		    return Math.floor(Math.random() * (max + 1));
		  },
		  // Replaced from the original function to leverage the built in
			// methods in
		  // JavaScript. Thanks to Robert Kieffer for pointing this one out
		  returnBase: function (number, base) {
		    return (number).toString(base).toUpperCase();
		  },
		  // Pull out only certain bits from a very large integer, used to get
			// the time
		  // code information for the first part of a UUID. Will return zero's
			// if there
		  // aren't enough bits to shift where it needs to.
		  getIntegerBits: function (val, start, end) {
		    var base16 = this.returnBase(val, 16);
		    var quadArray = new Array();
		    var quadString = '';
		    var i = 0;
		    for (i = 0; i < base16.length; i++) {
		      quadArray.push(base16.substring(i, i + 1));
		    }
		    for (i = Math.floor(start / 4); i <= Math.floor(end / 4); i++) {
		      if (!quadArray[i] || quadArray[i] == '')
		        quadString += '0';
		      else
		        quadString += quadArray[i];
		    }
		    return quadString;
		  }
		};
	HdUtils.code.name=function(fieldCod,value){//获取下拉name
		 if (!value)
	         return;
	     var list = HdUtils.global.scode.queryMap({fieldCod: fieldCod, code: value});
	     return list.length > 0 ? list[0].name : "";
	}

