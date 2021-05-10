/*
 * 全局配置参数，覆盖ezui的默认设置等
 */

var CONFIG_PAGE = 1;
var CONFIG_ROWS = 30;
var CONFIG_DEBUG = true;
var CONFIG_DROPLIST_ROWS = 30;
var CONFIG_MSG_INTERVAL = 5 * 60 * 1000;  // 默认5分钟。
var CONFIG_MSG_DISAPPEAR = 3000;

var MSG_CODE_QUREY_SUCCESS='0x21002001';  //与hdMessage.properties中的代码表对应，注意同步修改
var MSG_CODE_QUREY_NODATA='0x21002003';	
var MSG_CODE_QUREY_ERROR='0x22002001';

var MSG_CODE_PERSIST_SUCCESS='0x21000000';
var MSG_CODE_PERSIST_ERROR='0x22000001';

/**
 * change the default value of datagrid
 * @requires JQuery, easyUI
 */
$.fn.datagrid.defaults.fitColumns = true;
$.fn.datagrid.defaults.fit = true;
$.fn.datagrid.defaults.striped = true;
$.fn.datagrid.defaults.method = 'GET';
//$.fn.datagrid.defaults.loadMsg = 'loading...';
$.fn.datagrid.defaults.pagination = true;
$.fn.datagrid.defaults.pageNumber = 1;
$.fn.datagrid.defaults.pageSize = 20;
$.fn.datagrid.defaults.sortOrder = 'asc';
$.fn.datagrid.defaults.rownumbers = true;
$.fn.datagrid.defaults.singleSelect = true;
$.fn.datagrid.defaults.remoteSort = true;

$.fn.datagrid.defaults.onBeforeLoad = function(param) {
    param.others=param.others||{};
    param.others.timestamp = Math.random();  //fx210150509 由于新类库不能接受多余参数 
};
 
$.fn.treegrid.defaults.onBeforeLoad = function(row,param) {
    param.others=param.others||{};
    param.others.timestamp = Math.random();  //fx210150509 由于新类库不能接受多余参数 
};

$.fn.datagrid.defaults.autoRowHeight = false;
$.fn.combobox.defaults.method = 'GET';
$.fn.combogrid.defaults.delay = 500;//combogrid文本域在不停的输入时，多久发出一次请求（easyui默认200）

$.fn.datagrid.defaults.onRowContextMenu = function(e, rowIndex, rowData){};  //fxq2015-9-17右键导出excel由调用人控制

//fxq 2014-9-10注释：默认的clear方法，由于原来的Q参数仍然起作用，导致下拉数据行数据太少，因此重载默认方法，恢复下拉datagrid中的数据
$.fn.combogrid.methods.clear = function(jq) {
    return jq.each(function() {
        var options = $(this).combogrid('options');
        $(this).combogrid("grid").datagrid('load', $.extend({}, options.queryParams, {q: ''}));
        $(this).combo("clear");
    });
}
$.extend($.fn.panel.defaults, {
	onCollapse : function () {
		//获取layout容器
		var layout = $(this).parents(".layout");
		//获取当前region的配置属性
		var opts = $(this).panel("options");
		//获取key
		var expandKey = "expand" + opts.region.substring(0, 1).toUpperCase() + opts.region.substring(1);
		//从layout的缓存对象中取得对应的收缩对象
		var expandPanel = layout.data("layout").panels[expandKey];
		//针对横向和竖向的不同处理方式
		if (opts.region == "west" || opts.region == "east") {
			//竖向的文字打竖,其实就是切割文字加br
			var split = [];
			for (var i = 0; i < opts.title.length; i++) {
				split.push(opts.title.substring(i, i + 1));
			}
			expandPanel.panel("body").addClass("panel-title").css("text-align", "center").html(split.join("<br/>"));
		} else {
			expandPanel.panel("setTitle", opts.title);
		}
	}
});
/**
 * @author Gavin
 *
 * @requires jQuery,EasyUI
 *
 * 防止panel/window/dialog组件超出浏览器边界
 * @param left
 * @param top
 */
var easyuiPanelOnMove = function(left, top) {
    var l = left;
    var t = top;
    if (l < 1) {
        l = 1;
    }
    if (t < 1) {
        t = 1;
    }
    var width = parseInt($(this).parent().css('width')) + 14;
    var height = parseInt($(this).parent().css('height')) + 14;
    var right = l + width;
    var buttom = t + height;
    var browserWidth = $(window).width();
    var browserHeight = $(window).height();
    if (right > browserWidth) {
        l = browserWidth - width;
    }
    if (buttom > browserHeight) {
        t = browserHeight - height;
    }
    $(this).parent().css({/* 修正面板位置 */
        left: l,
        top: t
    });
};
$.fn.dialog.defaults.onMove = easyuiPanelOnMove;
$.fn.window.defaults.onMove = easyuiPanelOnMove;
$.fn.panel.defaults.onMove = easyuiPanelOnMove;


/**
 * change the default value of tree
 */
$.fn.tree.defaults.method = 'GET';
$.fn.tree.defaults.lines = true;

/**
 * change the default value of searchbox
 */
$.fn.searchbox.defaults.prompt = '在此输入过滤条件';

$.ajaxSetup({
    type: 'POST',
//  cache: true,   // FXQ2013-11-5屏蔽，保证 以<script src=''>方式引入被强制刷新。
//    		      cache不设置(默认undefiend??)不会影响combogrid缓存的有效性,但设置为false 则影响!
    dataType: 'json',
    contentType: "application/json"
});

/**
 * @author jason<caiyj@huadong.net>
 *
 */
$.fn.datebox.defaults.validType = 'valDate';
$.fn.datetimebox.defaults.validType = 'valDatetime';
 
$.fn.datetimebox.defaults.showSeconds = false;
