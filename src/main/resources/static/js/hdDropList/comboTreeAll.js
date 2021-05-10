/**
 说明： 对comboTree下拉数组件的进行定义,其中公司/部门的树可以根据数据源多选
 
 author:fuxinqi
 last version :1.0
 
 Modifications:
 
 1.0  2015-08-28  by fuxiqni 为了解决部门树多选的问题，建立此文件，后续容纳所有的树型节点

 */

$.hd.ezui.tree=$.hd.ezui.tree||{};

$.extend($.hd.ezui.tree, {
    //根据combotree的id得到对应的节点
    findNodeById:function(combotreeTarget,id){
        var tree =undefined;
        try{
            tree = $(combotreeTarget).combotree('tree');
        }catch(e){
            alert('编程错误，调用$.hd.ezui.tree.findNodeById给的第一个参数不是combotree的target:'+e);
        }
        if(!tree){
            return null;
        } 
        var node = tree.tree('find', id);
        return node;
    },
    /**
     * @param {object} opts 调用方指定的参数属性，如果重名，将覆盖默认属性
     * @returns {comboTreeOptions} 定义树型下拉的默认属性
     */
    cCommon:function(opts){
        var ret={
            method:'post',
            //deltaX: 0,
            lines:true,
            onSelect:function(node){}
        };
        return $.extend(true,ret,opts);
    },
     /**
     * 定义带多选框的树的默认行为（为了不重新定义新组件[类似multicombogrid编辑组件]，引入了getCheckedIds和 onHidePanelCallback回调方法）
     * @param {object} opts 调用方指定的参数属性，如果重名，将覆盖默认属性
     * @returns {comboTreeOptions} 定义树型下拉的默认属性
     */
    cCheckedTree:function(opts){
        if(!opts.getCheckedIds || !$.isFunction(opts.getCheckedIds) ){  //回调函数，哪些节点选中,返回数组
           alert('编程提示，调用$.hd.ezui.tree.cCheckedTree在初始化参数必须指定getCheckedIds回调函数，此函数返回数组，代表初始选中节点的ID');
        }
        if(!opts.onHidePanel && ! opts.onHidePanelCallback){ //调用方应重载onHidePanel事件或者定义onHidePanelCallback回调函数
            opts.onHidePanelCallback=function(selectedIds,selectedTexts){
                console.log('selected:'+selectedIds+'；即：'+selectedTexts);
            };
        }
        var ret={
            multiple:true,
            cascadeCheck:true,
            onLoadSuccess:function(node, data){
                if(!opts.getCheckedIds || !$.isFunction(opts.getCheckedIds) ){
                    return;
                }
                opts.checkedIds=opts.getCheckedIds();  //必须在开始编辑时，才能得到主datagrid的行，否则combotree可能还没有初始化
                var _this=this;  //这个this就是树的根节点
                $.each(opts.checkedIds,function(i,id){ //根据主datagrid数据(或某种方式得到，对form编辑)，这些节点选中
                    var node = $(_this).tree('find', id); 
                    if(node){
                        $(_this).tree('check', node.target);
                    }
                });
            },
            onHidePanel:function(){
                //获取选中的公司。 使用getChecke得不到数据？？！$(this).tree('getChecked','checked');
                var checkedValues=$(this).combotree('getValues');
                var checkedIds="",selectedTexts="";
                var _this=this;
                $.each(checkedValues,function(i,id){
                    checkedIds+=","+id;
                    var node = $.hd.ezui.tree.findNodeById(_this,id);
                    selectedTexts +=","+(node?node.text:'');
                });
                if(checkedIds!==""){
                    checkedIds=checkedIds.substring(1);
                    selectedTexts=selectedTexts.substring(1);
                }
                if($.isFunction(opts.onHidePanelCallback)){
                    opts.onHidePanelCallback(checkedIds,selectedTexts);
                }
            }
        };
        $.extend(ret,opts);
        return $.hd.ezui.tree.cCommon(ret);
    },
    /**
     * @param {object} opts 调用方指定的附加属性
     * @returns {comboTreeOptions} 部门树，所有节点，单选
     */
    cDeptAll: function (opts){
        var ret={
        	url:'../baserest/login/code/CDeptREST/asTree'
           };
        $.extend(ret,opts);
        return $.hd.ezui.tree.cCommon(ret);   
    },
    /**
     * @param {object} opts 调用方指定的附加属性
     * @returns {comboTreeOptions} 当前人员的下级部门树，所有节点，通过checkbox多选
     */   
    deptBellowMultiSelect:function(opts){
        if(opts.isCorp==undefined||opts.isCorp==null){     //显示公司树，false代表显示部门树
            opts.isCorp=true;
        }

        var ret={
            url:'../baserest/login/common/UtilsREST/belowDeptTree?isCorp='+opts.isCorp,
            method:'get',
            cascadeCheck:false
        };
        $.extend(ret,opts);
        return $.hd.ezui.tree.cCheckedTree(ret);    
    },
    /**
     * @param {object} opts 调用方指定的附加属性
     * @returns {comboTreeOptions} 当前人员所在在公司及下属公司的树，所有节点，通过checkbox多选
     */       
    corpsBellowMultiSelect:function(opts){
        opts.isCorp=true;
        return $.hd.ezui.tree.deptBellowMultiSelect(opts);    
    }
});

//$.hd=$.hd||{};
//$.hd.commonUtils=$.hd.commonUtils||{};
//
///**
// * 定义多个div切换编辑的默认动作 [警告]此方法必须在datagrid初始化完毕之后调用
// * @param {array} divArray  div对一个的jquery对象的数组
// * @param {function} selectionChageCallback 回调函数，返回当前选中的div的Id或者序号
// * @returns {undefined}
// */
//$.hd.commonUtils.switchedDivs=function(divArray,selectionChageCallback){
//    if(!$.isArray(divArray)|| !$.isFunction(divArray[0].size) || divArray[0].size()<1){
//        alert('编程错误，$.hd.commonUtils.switchedDivs第一个参数应该是div对一个的jquery对象的数组。');
//        return;
//    }
//    if(!selectionChageCallback){
//        selectionChageCallback=function(selected){lg('selected:'+selected);};
//    }else if(!$.isFunction(selectionChageCallback)){
//        alert('编程错误，$.hd.commonUtils.switchedDivs第2个参数应该是接受选中div的回调函数。');
//        return;
//    }
//    $.each(divArray,function(i,jq){
//        var dg=findInsideDatagrid(jq);
//        if(dg!=undefined){
//            modiDatagridClickEvent($(dg),jq);
//        }
//        jq.on("click",function(){
//            var selected= jq.attr('id')?jq.attr('id'):(function(){return i;})() ; //如果没有ID就返回序号
//            selectDiv(jq);
//            selectionChageCallback(selected);
//        });
//    });
//    //选中某个特定的div
//    function selectDiv(div){
//        div.css('border', '2px solid #0092DC'); //设置选中状态
//        $.each(divArray,function(i,jq2){ //其他的div都没有选中色
//            if(jq2==div){return true;}
//            jq2.css('border', '1px solid #d3d3d3');
//            jq2.css('border', '1px solid #d3d3d3');
//        });        
//    }
//    
//    //找到div中的datagrid
//    function findInsideDatagrid(div){
//        var childs=div.find('table[id]:hidden'); //有Id属性，且具有style="display:none">  //children('.datagrid');    //
//        return childs.length>0? childs[0]:undefined;
//    }
//    //在datagrid点击时，认为是div点击了
//    function modiDatagridClickEvent(dg,div){
//        var opt=dg.datagrid('options');
//        var oldCallback=opt.onClickRow;
//        if(!oldCallback){
//            opt.onClickRow=function(rowIndex, field, value){
//                selectDiv(div);
//            };
//        }else{
//            opt.onClickRow=function(rowIndex, field, value){
//                selectDiv(div);
//                oldCallback(rowIndex, field, value);
//            };  
//        }
//    }
//};
//
