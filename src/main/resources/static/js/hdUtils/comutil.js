//模态非iframe弹窗,关闭方法  var a=$.modalDialog(...); a.dialog('close')
$.modalDialog = function(options) {
	if (!$.modalDialog.arr) {
		$.modalDialog.arr = [];
	}
	var opts = $.extend({
	    title : '',
	    modal : true,
	    cache : false,
	    onClose : function() {
		    $.modalDialog.arr.pop();
		    if ($.modalDialog.arr.length > 0) {
			    $.modalDialog.handler = $.modalDialog.arr[$.modalDialog.arr.length - 1];
			    // 2.关闭窗口,切换到上一个
		    }
		    $(this).dialog('destroy');
	    },
	    onOpen : function() {
		    // parent.$.messager.progress({
		    // title : '提示',
		    // text : '数据处理中，请稍后....'
		    // });
	    }
	}, options);
	opts.modal = true;
	// 强制此dialog为模式化，无视传递过来的modal参数
	var myHandler = $('<div/>').dialog(opts);
	$.modalDialog.arr.push(myHandler);
	$.modalDialog.handler = myHandler;
	// 1.最后一次窗口的handler
	return myHandler;

};
$.fn.serializeObject = function()// wxl 17.5.10
{
	var o = {};
	var a = this.serializeArray();
	$.each(a, function() {
		if (o[this.name]) {
			if (!o[this.name].push) {
				o[this.name] = [ o[this.name] ];
			}
			o[this.name].push(this.value || '');
		} else {
			o[this.name] = this.value || '';
		}
	});
	return o;
};

function addDropDel(jq) {

	setTimeout(function() {
		jq.each(function() {
			$(this).combobox({
				"icons" : [ {
				    iconCls : 'icon-clear',
				    handler : function(e) {
					    $(e.data.target).combobox('clear');
					    $(this).css('visibility', 'hidden');
				    }
				} ]
			});
			var icon = $(this).combobox('getIcon', 0);
			icon.css('visibility', 'hidden');
			var one = $(this);
			one.combobox('textbox').parent().mouseover(function() {
				var idVal = one.combobox('getValue');
				if (idVal) {
					icon.css('visibility', 'visible');
				} else {
					icon.css('visibility', 'hidden');
				}
			});
			$(this).combobox('textbox').parent().mouseout(function() {
				icon.css('visibility', 'hidden');
			});

		});
	}, 100);
}

function addDateDel(jq) {

	setTimeout(function() {
		jq.each(function() {
			$(this).datebox({
				"icons" : [ {
				    iconCls : 'icon-clear',
				    handler : function(e) {
					    $(e.data.target).datebox('clear');
					    $(this).css('visibility', 'hidden');
				    }
				} ]
			});
			var icon = $(this).datebox('getIcon', 0);
			icon.css('visibility', 'hidden');
			var one = $(this);
			one.datebox('textbox').parent().mouseover(function() {
				var idVal = one.datebox('getValue');
				if (idVal) {
					icon.css('visibility', 'visible');
				} else {
					icon.css('visibility', 'hidden');
				}
			});
			$(this).datebox('textbox').parent().mouseout(function() {
				icon.css('visibility', 'hidden');
			});

		});
	}, 100);
}

function addDateTimeDel(jq) {

	setTimeout(function() {
		jq.each(function() {
			$(this).datetimebox({
				"icons" : [ {
				    iconCls : 'icon-clear',
				    handler : function(e) {
					    $(e.data.target).datetimebox('clear');
					    $(this).css('visibility', 'hidden');
				    }
				} ]
			});
			var icon = $(this).datetimebox('getIcon', 0);
			icon.css('visibility', 'hidden');
			var one = $(this);
			one.datetimebox('textbox').parent().mouseover(function() {
				var idVal = one.datetimebox('getValue');
				if (idVal) {
					icon.css('visibility', 'visible');
				} else {
					icon.css('visibility', 'hidden');
				}
			});
			$(this).datetimebox('textbox').parent().mouseout(function() {
				icon.css('visibility', 'hidden');
			});

		});
	}, 100);
}
function maskLoading() {
	$("<div class=\"datagrid-mask\"></div>").css({
	    display : "block",
	    width : "100%",
	    zIndex : '99998',
	    height : $(window).height()
	}).appendTo("body");
	$("<div class=\"datagrid-mask-msg\"></div>").html("正在处理，请稍候。。。").appendTo("body").css({
	    display : "block",
	    zIndex : '99999',
	    left : ($(document.body).outerWidth(true) - 190) / 2,
	    top : ($(window).height() - 45) / 2
	});
}

function maskEnd() {
	$(".datagrid-mask").remove();
	$(".datagrid-mask-msg").remove();
}
HdUtils.fileupload =
        function(param) {// wxl
	        var entityName = param.entityName;// 实体名
	        var entityId = param.entityId;// 实体主键
	        var callback = param.callback;// 回调
	        var mutiple = param.mutiple;// 是否支持多上传
	        var allowType = param.allowType;// 允许类型
	        var isReadOnly = param.isReadOnly;// 是否只读
	        if (!mutiple) {
		        mutiple = "true";
	        }
	        if (!allowType) {
		        allowType = ".*";
	        }
	        var url =
	                'webresources/login/com/comfileupload/?entityName=' + entityName + "&entityId=" + entityId + "&callback=" + callback + "&mutiple=" + mutiple + "&allowType=" + allowType + "&isReadOnly=" + isReadOnly;
	        var btnType = 0;
	        if (isReadOnly) {
		        btnType = 1;
	        }
	        $.ezModelDialogPassbackValue(url, '文件上传', '', 650, 450, btnType);
        }
$.ezModelDialogPassbackValue = function(url, title, func, width, height, type) {// type=1表示无按钮
	var iframeDiv = '<iframe name="ez_myDlFrame" frameborder="0" src=' + url + ' style="width: 100%; height: 99%;"></iframe>'
	$.ezModelDialogPassbackValue.handler = $("<div style='padding:2px'/>").dialog({
	    content : iframeDiv,
	    closed : false,
	    title : title,
	    width : width,
	    height : height,
	    onClose : function() {
		    $(this).dialog('destroy');
	    },
	    buttons : function() {
		    if (type == 1) {
			    return [ {
			        text : "关闭",
			        handler : function() {
				        $.ezModelDialogPassbackValue.handler.dialog('destroy');
			        }
			    } ]
		    }
		    return [ {
		        text : "上传",
		        handler : function() {
			        window.frames['ez_myDlFrame'].upload(func);

		        }
		    }, {
		        text : "关闭",
		        handler : function() {
			        $.ezModelDialogPassbackValue.handler.dialog('destroy');

		        }
		    } ]
	    }()
	});
}

$.fn.formDisable = function() {
	$(this).find("input").attr("disabled", "disabled");
	// $("#ShiprTable a").unbind();
	$(this).find("textarea").attr("disabled", "disabled");

}
String.prototype.endWith = function(endStr) {
	var d = this.length - endStr.length;
	return (d >= 0 && this.lastIndexOf(endStr) == d)
}
HdUtils.workflow = {};
HdUtils.workflow.backReason = function(callback) {
	HdUtils.dialog.show({
	    width : 500,
	    height : 150,
	    title : '拒绝原因',
	    href : "/login/system/com/backreason.html",
	    buttons : [ {
	        text : '确定',
	        iconCls : 'icon-ok',
	        handler : function() {
		        var obj = HdUtils.dialog.getValue('saveHandler')();
		        if (!obj) {
			        return false;
		        }
		        callback(obj);
	        }
	    }, {
	        text : '关闭',
	        iconCls : "icon-cancel",
	        handler : function() {
		        HdUtils.dialog.handler.dialog("close");
	        }
	    } ]

	});

}
HdUtils.workflow.assignUnit = function(callback) {// 指定单位
	HdUtils.dialog.show({
	    width : 300,
	    height : 150,
	    title : '接收单位',
	    href : "/login/system/com/assignorg.html",
	    buttons : [ {
	        text : '确定',
	        iconCls : 'icon-ok',
	        handler : function() {
		        var obj = HdUtils.dialog.getValue('saveHandler')();
		        if (!obj) {
			        return false;
		        }
		        callback(obj);
	        }
	    }, {
	        text : '关闭',
	        iconCls : "icon-cancel",
	        handler : function() {
		        HdUtils.dialog.handler.dialog("close");
	        }
	    } ]

	});

}
function dateDiff(dateEnd, dateBeg) {// 日期差(字符串格式)end-start
	var oDate1 = new Date(dateBeg.replace(/-/g, "/")); // 将字符串转化成时间
	var oDate2 = new Date(dateEnd.replace(/-/g, "/"));
	var iDays = (oDate2 - oDate1) / (1000 * 60 * 60 * 24);
	return iDays;
}
function HdQuery() {// 查询HdEzuiQueryParamsBuilder的封装简化代码
	var highQuery = {};
	highQuery.adQueryLs = [];
	this.hdQueryBuilder = new HdEzuiQueryParamsBuilder();
	this.getHighQuery = function() {
		return highQuery;
	}
	this.add = function(key, value) {
		this.hdQueryBuilder.setOtherskeyValue(key, value);
	}
	this.setOrderByClause = function(name, order) {
		this.hdQueryBuilder.setOrderByClause(name, order);
	}
	this.setAndClause = function(column, value, operation, conjunction) {
		this.hdQueryBuilder.setAndClause(column, value, operation, conjunction);
	}
	this.set = function(name, order) {
		this.hdQueryBuilder.set(name, order);
	}
	this.addHq = function(key, rel, value, conn, type) {
		var oneObj = {
		    key : key,
		    rel : rel,
		    value : value,
		    conn : conn,
		    type : type
		};
		highQuery.adQueryLs.push(oneObj);
	}
	this.build = function() {
		$.extend(highQuery, this.hdQueryBuilder.build());
		return highQuery;
	}
}
function getSelTabMenuId() {
	return $('#main').tabs('getSelected').panel('options').mainMenuId;
}

function left_zero(str) {
	if (str != null && str != '' && str != 'undefined') {
		if (str.length == 2) {
			return '00' + str;
		}
	}
	return str;
}

// 中文转Unicode码
function unicodechange(str) {
	var value = '';
	for (var i = 0; i < str.length; i++) {

		if (str.charCodeAt(i) > 255) {// 大于255说明是中文，转unicode码
			value += '\\u' + left_zero(parseInt(str.charCodeAt(i)).toString(16));
		} else {
			value += str.charAt(i);// 英文或者括号不转unicode码
		}
	}
	return value;
}
var comi18nProper = [ 'commondatagrid', 'menu' ];
$(document).ready(function() {
	$.i18n.properties({
	    name : comi18nProper, // 资源文件名称
	    path : '/i18n/project/', // 资源文件路径
	    mode : 'map', // 用Map的方式使用资源文件中的值
	    language : getBrowserLanguage()
	});
});
// prop是国际化文件,str是扫描方式,匹配不到的会显示原文字
var comi18nscan = [ 'form th', 'form td', 'a', 'label', 'button' ];
HdUtils.i18n = function(prop) {
	// HdUtils.i18nsc(prop);
	if (prop) {
		comi18nProper.push(prop);
		$.i18n.properties({
		    name : comi18nProper, // 资源文件名称
		    path : '/i18n/project/', // 资源文件路径
		    mode : 'map', // 用Map的方式使用资源文件中的值
		    language : getBrowserLanguage()
		});
	}
	$.parser.onComplete = function() {
		HdUtils.i18nsc(prop);
	};
}
HdUtils.i18nsc = function(prop) {
	if (prop) {// zuij
		if (!comi18nProper.contains(prop)) {// 不包含
			comi18nProper.push(prop);
			$.i18n.properties({
			    name : comi18nProper, // 资源文件名称
			    path : '/i18n/project/', // 资源文件路径
			    mode : 'map', // 用Map的方式使用资源文件中的值
			    language : getBrowserLanguage()
			});
		}
	}
	var str = comi18nscan;
	for (var i = 0; i < str.length; i++) {
		if ($(str[i]).length > 0) {
			$(str[i]).each(function(i) {
				var oneResult = "";
				var word = $(this).html();
				if (!word) {
					return true;// 继续循环
				}
				var wordSize = word.length - 1;
				if (word.lastIndexOf("：") == wordSize || word.lastIndexOf(":") == wordSize) {
					oneResult = $.i18n.prop(unicodechange(word.substring(0, wordSize))) + "：";
				} else {
					oneResult = $.i18n.prop(unicodechange(word));
				}
				if (oneResult.indexOf("[") != 0) {// 没匹配到
					$(this).html(oneResult);
				}
			});
		}
	}

}

function left_zero(str) {
	if (str != null && str != '' && str != 'undefined') {
		if (str.length == 2) {
			return '00' + str;
		}
	}
	return str;
}

// 中文转Unicode码
function unicodechange(str) {
	var value = '';
	for (var i = 0; i < str.length; i++) {

		if (str.charCodeAt(i) > 255) {// 大于255说明是中文，转unicode码
			value += '\\u' + left_zero(parseInt(str.charCodeAt(i)).toString(16));
		} else {
			value += str.charAt(i);// 英文或者括号不转unicode码
		}
	}
	return value;
}

function i18n(title) {
	var tte = title;
	title = title.replace(/^\s+|\s+$/g, ""); // 去除两头空格
	var ts;
	var reg = /<[^>]+>/g;
	var flag = reg.test(title);// 判断是否有html样式
	if (flag) {
		var h = title;
		title = title.replace(/<.*?>/ig, "");// 去掉html样式
		h = h.replace(title, "");
		var idx = h.indexOf("</");
		first = h.substr(0, idx);// first、end 是html的标签
		end = h.substr(idx);
	}
	var titlenew = unicodechange(title);// 将中文转Unicode码
	var val = $.i18n.prop(titlenew);
	if (val.indexOf("[") != 0) {// 表示在properties中匹配上的，如果没匹配上会有"[]"
		if (flag) { // 有html样式
			ts = first + val + end;
		} else { // 没有html样式
			ts = val;
		}
	} else { // 表示在properties中没有匹配上的
		ts = tte;
	}

	return ts;
}

$.fn.hddatagrid = function(param) {// notFirst并发首次加载
	if (!param.sortName) {// 默认排序
		param.sortName = "updTim";
		param.sortOrder = "desc";
	}
	var _this = this;
	if (!param.url) {
		_this.datagrid(param);
		return false;
	}
	param.gridSelId = param.url.split('?')[0];// url去掉?后当唯一
	HdUtils.ajax.post({
	    url : "/webresources/login/com/SysCusGrid/find",
	    data : param,
	    success : function(data) {
		    var newShow = showColumns(param, data);
		    var gridUrl = newShow.url;
		    if (gridUrl) {
			    var oper = "";
			    if (gridUrl.indexOf("?") < 0) {
				    oper = "?";
			    } else {
				    oper = "&";
			    }
			    var mainMenuId = getSelTabMenuId();
			    if (mainMenuId) {
				    gridUrl += oper + "&mainMenuId=" + mainMenuId;
			    }
			    newShow.url = gridUrl;
		    }
		    newShow.onHeaderContextMenu = function(e, field) { // 右键时触发事件
			    $('#grid_dis_Setting').unbind();
			    $('#grid_dis_Setting').on('click', function() {
				    $('#gridTitlemenu').menu('hide');
				    _this.gridSet();

			    });
			    $('#grid_high_Query').unbind();
			    $('#grid_high_Query').on('click', function() {
				    $('#gridTitlemenu').menu('hide');
				    _this.adQuery();

			    });

			    // 三个参数：e里面的内容很多，真心不明白，rowIndex就是当前点击时所在行的索引，rowData当前行的数据
			    e.preventDefault(); // 阻止浏览器捕获右键事件
			    $('#gridTitlemenu').menu('show', {
			        // 显示右键菜单
			        left : e.pageX,// 在鼠标点击处显示菜单
			        top : e.pageY
			    });
			    // e.preventDefault(); // 阻止浏览器自带的右键菜单弹出
		    }
		    _this.datagrid(newShow);
	    },
	});

	var showColumns = function(allparam, showparam) {
		var result = [];
		result[0] = [];
		var showColumns = (showparam.columns)[0];
		if (showColumns.length == 0) {// 未设置或者全部不显示=同全显示
			return allparam;
		}
		var objColumns = (allparam.columns)[0];
		for (var k = 0; k < objColumns.length; k++) {// hidden的全部加上
			if (objColumns[k].hidden || !objColumns[k].title) {// hidden和ck的不处理
				result[0].push(objColumns[k])
			}

		}
		for (var i = 0; i < showColumns.length; i++) {
			for (var k = 0; k < objColumns.length; k++) {
				if (showColumns[i].field == objColumns[k].field) {
					objColumns[k].title = i18n(objColumns[k].title);
					result[0].push(objColumns[k])
				}

			}
		}
		var newAll = {};
		$.extend(newAll, allparam);
		newAll.columns = result;// 按照新的columns展示
		return newAll;
	}
	this.gridSet = function() {
		HdUtils.dialog.setValue({
		    'gridSetVal' : param,
		    "gridSetObj" : _this
		});
		HdUtils.dialog.show({
		    width : 600,
		    height : 450,
		    title : i18n('展示设置'),
		    href : "/webresources/login/com/SysCusGrid/show.htm",
		    isSaveHandler : true
		});
	}
	this.reload = function() {
		$(_this).hddatagrid(param, true);
	}

	this.adQuery = function() {// 即席查询
		HdUtils.dialog.setValue({
		    'gridSetVal' : param,
		    "gridSetObj" : _this
		});
		// if (param.hdQueryDialog) {
		// param.hdQueryDialog.dialog("open");
		// return;
		// }
		if ($('#advancedSearch')) {
			$('#advancedSearch').parent().remove();
		}
		isShowAgain = true;
		HdUtils.dialog.show({
		    width : 600,
		    height : 450,
		    title : '展示设置',
		    // onClose : function() {
		    // param.hdQueryDialog = HdUtils.dialog.handler;
		    // },// 替换工具类中的destory
		    href : "/webresources/login/com/SysCusGrid/adquery.htm",
		    buttons : [ {
		        text : '确定',
		        iconCls : 'icon-ok',
		        handler : function() {
			        var isSub = false;
			        var datagridQueryParams = $(_this).datagrid('options').queryParams;// 普通查询一起拼
			        var query = HdUtils.dialog.getValue('saveHandler')();
			        $.each(query.getHighQuery().adQueryLs, function(index, obj) {
				        if (obj.value == "") {
					        isSub = true;
				        }
			        });
			        if (isSub == true) {
				        HdUtils.messager.info("查询条件属性值不能为空");
				        return;
			        }
			        if (!datagridQueryParams) {
				        datagridQueryParams = {}
			        }
			        $.extend(datagridQueryParams, query.getHighQuery());
			        $(_this).datagrid({
				        queryParams : datagridQueryParams
			        });
			        HdUtils.dialog.close();

		        }
		    }, {
		        text : '关闭',
		        iconCls : "icon-cancel",
		        handler : function() {
			        HdUtils.dialog.handler.dialog("close");
		        }
		    } ]
		});
	}
	return this;
}
HdUtils.ezui.orgTree = function(type, param) {// 以后会扩展为单位树1,0全集团的
	if (!type) {
		type = "1";
	}
	var treeParam = {
	    url : "/webresources/login/privilege/AuthOrgn/gentree?s=" + Math.random() + "&type=" + type + "&mainMenuId=" + getSelTabMenuId(),
	    method : 'get',
	    editable : true,
	    panelHeight : 400,
	    panelWidth : 200
	}
	if (param) {
		$.extend(treeParam, param);
	}
	return treeParam;
}
