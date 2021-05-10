/**
 * 非模态窗口：name必须唯一
 * **/
var HdShipPicNamedDialog = {};
HdShipPicNamedDialog.names = {};
HdShipPicNamedDialog.show = function (name,params) {
    if (!HdShipPicNamedDialog.names[name]) {
        HdShipPicNamedDialog.names[name] = {};
        HdShipPicNamedDialog.names[name].maps = {};
    }
    var _def = {
        title: "标题", // 标题
        width: 1050,
        height: 550,
        closed: false,
        cache: false,
        resizable: true,
        modal: false,
        // 关闭窗口的时候，unbind键。
        onClose: function () {
            // console.log(name, 'close');
            $(this).dialog('destroy');
            HdShipPicNamedDialog.names[name] = undefined;
        }

    };

    var str = "?";

    if (params != undefined && params.href != undefined && params.href.indexOf("?") >= 0) {
        str = "&";
    }
    params.href = params.href + str + "timestamp=" + new Date().getTime();
    //添加menuId
    params.href += "&mainMenuId=" + getSelTabMenuId();
    if (params.global != undefined) {
        params.hdGlobal = params.global;
    }
    if (params.openWithIframe) {
        params.content = '<iframe scrolling="no" frameborder="0"  src="' + encodeURI(params.href) + '" style="width:100%;height:100%;"></iframe>';
        delete params.href;
    }
    $.extend(_def, params);
    if (params.isSaveHandler) {
        _def.buttons = [
            {
                text: change("sure"),
                iconCls: 'icon-ok',
                handler: function () {
                    HdShipPicNamedDialog.getValue(name,'saveHandler')();

                }
            }, {
                text: change("cancel"),
                iconCls: "icon-cancel",
                handler: function () {
                    HdShipPicNamedDialog.names[name].handler.dialog("close");
                }
            }
        ];
    } else if (params.isOkHandler) {
        _def.buttons = [
            {
                text: change("sure"),
                iconCls: 'icon-ok',
                handler: function () {
                    HdShipPicNamedDialog.names[name].getValue(name,"okHandler")();
                }
            }, {
                text: change("cancel"),
                iconCls: "icon-cancel",
                handler: function () {
                    HdShipPicNamedDialog.names[name].handler.dialog("close");
                }
            }
        ];
    } else if (params.isShowExitBtn) {
        _def.buttons = [
            {
                text: '关闭',
                iconCls: "icon-cancel",
                handler: function () {
                    HdShipPicNamedDialog.names[name].handler.dialog("close");
                }
            }
        ];
    }
    var myHandler = $('<div/>').dialog(_def);
    // 绑定enter,esc键。
    // $(document).bind("keyup", function (event) {
    //     if (event.which == 27) {
    //         HdShipPicNamedDialog.handler.dialog("options").callback = undefined;
    //
    //         HdShipPicNamedDialog.handler.dialog("close");
    //     }
    // });

    HdShipPicNamedDialog.names[name].handler = myHandler;
    // 1.最后一次窗口的handler
    return myHandler;
};

HdShipPicNamedDialog.setValue = function (name,params) {
    if (!HdShipPicNamedDialog.names[name]) {
        HdShipPicNamedDialog.names[name] = {};
        HdShipPicNamedDialog.names[name].maps = {};
    }
    var opts = HdShipPicNamedDialog.names[name].maps;
    if (params.saveHandler) {// 弹窗保存是栈的形式,这样支持多层弹窗
        HdShipPicNamedDialog.names[name].handler.saveHandler = params.saveHandler;
    }
    if (params.okHandler) {// 弹窗保存是栈的形式,这样支持多层弹窗
        HdShipPicNamedDialog.names[name].handler.okHandler = params.okHandler;
    }
    if (!opts.globalHd) {
        opts.globalHd = {}
    }
    $.extend(opts.globalHd, params);
};

HdShipPicNamedDialog.getValue = function (name,param) {
    if ('saveHandler' == param && HdShipPicNamedDialog.names[name].handler) {// 弹窗保存是栈的形式,这样支持多层弹窗
        return HdShipPicNamedDialog.names[name].handler.saveHandler;
    }
    if ('okHandler' == param && HdShipPicNamedDialog.names[name].handler) {// 弹窗保存是栈的形式,这样支持多层弹窗
        return HdShipPicNamedDialog.names[name].handler.okHandler;
    }
    var opts = HdShipPicNamedDialog.names[name].maps;
    if (opts != undefined) {
        try {
            // var p = eval("opts.globalHd." + param);
            var p = opts.globalHd[param];
            return p;
        } catch (error) {
            return null;
        }
    } else {
        return undefined;
    }
};

HdShipPicNamedDialog.close = function (name) {
    var opts = HdShipPicNamedDialog.names[name].handler.dialog("options");
    if (opts != undefined) {
        opts.params = undefined;
        opts.buttons = [];
    }
    HdShipPicNamedDialog.names[name].handler.dialog("close");
};


////////////////////////////////////
/**
 * 模态对话框
 * */
var HdShipPicDialog = {};

HdShipPicDialog.show = function (params) {
    if (!HdShipPicDialog.arr) {
        HdShipPicDialog.arr = [];
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
        onClose: function () {
            //$(document).unbind("keyup");
            HdShipPicDialog.arr.pop();
            if (HdShipPicDialog.arr.length > 0) {
                HdShipPicDialog.handler = HdShipPicDialog.arr[HdShipPicDialog.arr.length - 1];
                // 2.关闭窗口,切换到上一个
            } else {
                //console.log("dialog onClose clear setValue");
                //清空 setValue的值，否则再次调用dialog上次setValue的值还存在
                HdShipPicDialog.maps = {};
                HdShipPicDialog.handler = null;
            }
            $(this).dialog('destroy');
        }

    }

    var str = "?";

    if (params != undefined && params.href != undefined && params.href.indexOf("?") >= 0) {
        str = "&";
    }
    params.href = params.href + str + "timestamp=" + new Date().getTime();
    //添加menuId
    params.href += "&mainMenuId=" + getSelTabMenuId();
    if (params.global != undefined) {
        params.hdGlobal = params.global;
    }
    if (params.openWithIframe) {
        params.content = '<iframe scrolling="no" frameborder="0"  src="' + encodeURI(params.href) + '" style="width:100%;height:100%;"></iframe>';
        delete params.href;
    }
    $.extend(_def, params);
    if (params.isSaveHandler) {
        _def.buttons = [
            {
                text: change("sure"),
                iconCls: 'icon-ok',
                handler: function () {
                    HdShipPicDialog.getValue('saveHandler')();

                }
            }, {
                text: change("cancel"),
                iconCls: "icon-cancel",
                handler: function () {
                    HdShipPicDialog.handler.dialog("close");
                }
            }
        ];
    } else if (params.isOkHandler) {
        _def.buttons = [
            {
                text: change("sure"),
                iconCls: 'icon-ok',
                handler: function () {
                    HdShipPicDialog.getValue("okHandler")();
                }
            }, {
                text: change("cancel"),
                iconCls: "icon-cancel",
                handler: function () {
                    HdShipPicDialog.handler.dialog("close");
                }
            }
        ];
    } else if (params.isShowExitBtn) {
        _def.buttons = [
            {
                text: '关闭',
                iconCls: "icon-cancel",
                handler: function () {
                    HdShipPicDialog.handler.dialog("close");
                }
            }
        ];
    }
    var myHandler = $('<div/>').dialog(_def);
    // 绑定enter,esc键。
    // $(document).bind("keyup", function (event) {
    //     if (event.which == 27) {
    //         HdShipPicDialog.handler.dialog("options").callback = undefined;
    //
    //         HdShipPicDialog.handler.dialog("close");
    //     }
    // });

    HdShipPicDialog.arr.push(myHandler);
    HdShipPicDialog.handler = myHandler;
    // 1.最后一次窗口的handler
    return myHandler;
};
HdShipPicDialog.maps = {};// wxl 数据存储的
HdShipPicDialog.setValue = function (params) {
    var opts = HdShipPicDialog.maps;
    if (params.saveHandler) {// 弹窗保存是栈的形式,这样支持多层弹窗
        HdShipPicDialog.handler.saveHandler = params.saveHandler;
    }
    if (params.okHandler) {// 弹窗保存是栈的形式,这样支持多层弹窗
        HdShipPicDialog.handler.okHandler = params.okHandler;
    }
    if (!opts.globalHd) {
        opts.globalHd = {}
    }
    $.extend(opts.globalHd, params);
};

HdShipPicDialog.getValue = function (param) {
    if ('saveHandler' == param && HdShipPicDialog.handler) {// 弹窗保存是栈的形式,这样支持多层弹窗
        return HdShipPicDialog.handler.saveHandler;
    }
    if ('okHandler' == param && HdShipPicDialog.handler) {// 弹窗保存是栈的形式,这样支持多层弹窗
        return HdShipPicDialog.handler.okHandler;
    }
    var opts = HdShipPicDialog.maps;
    if (opts != undefined) {
        try {
            var p = eval("opts.globalHd." + param);
            return p;
        } catch (error) {
            return null;
        }
    } else {
        return undefined;
    }
};

HdShipPicDialog.close = function () {
    var opts = HdShipPicDialog.handler.dialog("options");
    if (opts != undefined) {
        opts.params = undefined;
        opts.buttons = [];
    }
    HdShipPicDialog.handler.dialog("close");
};