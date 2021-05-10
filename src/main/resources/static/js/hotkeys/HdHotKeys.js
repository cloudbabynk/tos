//快捷键工具类
$.HdHotKeys = {};
hotkeys.filter = function(event) {//input,textarea也有效
	return true;
}
// 公共快捷键
$.HdHotKeys.toIUDQ = function() {
	hotkeys('ALT+I,ALT+M,ALT+D,ALT+Q,ALT+S,ALT+R,ALT+C', function(event, handler) {
		event.preventDefault();// 组织浏览器部分冲突快捷键
		switch (handler.key) {
		case "ALT+I":
			
			$('#main').tabs('getSelected').find("a[iconCls='icon-add']")[0].click();
			break;
		case "ALT+M":
			$('#main').tabs('getSelected').find("a[iconCls='icon-edit']")[0].click();
			break;
		case "ALT+D":
			$('#main').tabs('getSelected').find("a[iconCls='icon-remove']")[0].click();
			break;
		case "ALT+S":
			$('#main').tabs('getSelected').find("a[iconCls='icon-save']")[0].click();
			break;
		case "ALT+R":
			$('#main').tabs('getSelected').find("a[iconCls='icon-reload']")[0].click();
			break;
		case "ALT+C":
			$('#main').tabs('getSelected').find("a[iconCls='icon-reset']")[0].click();
			break;
		case "ALT+Q":
			$('#main').tabs('getSelected').find("a[iconCls='icon-search']")[0].click();
			break;
		}
	});

}
// 导出
$.HdHotKeys.toExport = function(callback) {
	var scope = hotkeys.getScope();
	hotkeys('ALT+E', scope, function(event, handler) {
		callback();
	});
};
// 确定
$.HdHotKeys.toOk = function(callback) {
	var scope = hotkeys.getScope();
	hotkeys('ALT+O', scope, function(event, handler) {
		callback();
	});
};
