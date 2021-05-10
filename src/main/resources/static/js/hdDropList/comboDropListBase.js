/**
 说明： 对combogrid下拉组件常用的代码表（！类库部分！）进行定义, 每个对象(function)返回值对应于combogrid的options属性
 
 author:fuxinqi
 last version :1.6
 
 Modifications:
 
 1.6 2015-06-25  by fuxiqni 只保留类库用到的代码表，其他的删除。同时改名防止覆盖
 1.5  2015-05-09 by fuxiqni 删除原$.hd.combogrid.options.xx系列代码表。 消除默认的onHidePanel方法（在combogrid-extended.js已处理）
 1.4  2013-11-3  by fuxiqni 将hd.ezui.code.cCommon及标准的下拉定义全部移动到本文件，减少extension.js体积
 1.3  2013-8-9  by fuxiqni在检查船舶模块的html/jsp过程中，重新打开了cShipLine\	cPort\cCountry
 1.2  2013-8-9  by fuxinqi鉴于在 main.jsp中实际只用到dopDownList中的部分文件，其他的清理掉
 1.1  2013-8-9  by fuxinqi 合并生成本文件,同时直接使用extension.js中已有的代码表(保持接口兼容)以消除代码重复、并增加校验等通用功能
 1.0  2013-5月: by 贺传勃\马甜甜等项目成员: 创建独立的js文件,分别对一类代码表进行支持
 */

$.extend($.hd.ezui, {
    code: {
            /**
             * 通用代码表。
             * jason <caiyj@huadong.net>
             * @param {type} parentId   父窗
             * @param {type} required   是否必需 default:false
             * @param {type} fieldMapping   字段映射(js对象，0-code，1-name) （必填）
             * @param {type} url    数据url （必填）
             * @param {type} andItems   数据过滤条件 default：
             * @param {type} onChange   下拉值改变触发事件
             * @param {type} columns    下拉列信息（必填）
             * @param {type} filterColumns  过滤 default：下拉过滤fieldMapping列名
             * @param {type} multiple   多选。default:false
             * @returns {jQuery.hd.ezui.code.cCommon.ret}   返回options
             */
            cCommon: function(params) {
                var ret = {};
                var valueField = "";
                var textField = "";
                var i = 0;
                
                if(params.mapping){           //FXQ2013-8-9 允许调用方以传统方式传来mapping{parent:, field:,} 映射对像
                    ret.mapping=$.extend({},params.mapping);
                }else{                        //以新方式传来parentId和 fieldMapping单立设置
                    ret.mapping = (params.parentId ||params.fieldMapping)?     //原来只判断parentId可能丢失mapping信息导致找不到valueField和nameField
                      {    parent: params.parentId ? params.parentId : undefined ,
                           fields: params.fieldMapping ? params.fieldMapping : undefined   // fxq2013-8-9 防范没有给定fieldMapping导致fieldMapping=null从而后续报错的问题
                      }
                    : undefined ;      //？？？是否需要防范对combogrid的影响,见datagrid-helper代码
                }
                //自动查找idField/valueField 和 nameField
                if(ret.mapping && ret.mapping.fields){  //FXQ 为保持兼容，从设置mapping的代码前移动到代码后，并改变if条件。 原条件params && params.fieldMapping
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
                
                ret.queryParams = (params && params.queryParams) ? params.queryParams :  //fxq2013-8-9 如果调用者指定了queryParams 则直接使用之
                    {
                        queryColumns: params.filterColumns ? params.filterColumns : valueField + "," + textField,
                        andItems: params.andItems ? $.toJSON(params.andItems) : ""
                    };
                //傅新奇 2015-5-9 由于 combogrid-extended.js 已经消除了代码-名称对照的问题，因此这里删除了原默认的onHidePanel方法（处理不一致的CODE取值）  
                ret.onHidePanel= null; // 由于combogrid默认有一个空函数作为onHidePanel，会导致combogrid-extended.js中误判，因此这里取消其默认定义
                ret.required = false;
                ret.idField = valueField;
                ret.valueField = valueField;
                ret.textField = textField;
                ret.sortName = valueField;
                ret.remoteSort = params.sortName ? true : false;            //fxq 修正排序无效的BUG
                ret.sortOrder = params.sortOrder? params.sortOrder : 'asc';
                ret.mode = (params.mode)? params.mode : 'remote';  //本地过滤，除非明确指定   //本地过滤不可行  ret.mode = (true===params.pagination)? 'remote' : 'local'; 
                ret.pagination = (true===params.pagination);       //未指定，则默认不分页
                ret.pageSize = ret.pagination ? CONFIG_DROPLIST_ROWS: undefined;  //用默认函数没关系，还会被params的值替换
                ret.method = 'GET';
                ret.fitColumns = true;  //问题：true 速度太慢，但false导致调整字段宽度不合理
                ret.striped = true;
                ret.singleSelect = true;
                ret.panelWidth = params.panelWidth ? params.panelWidth : 250;
                ret.rownumbers = false;
                
                //自适应列宽
                $.each(params.columns[0], function(idx, column){
                    if(!column.width ||column.width<6){
                        column.width=40;
                    }
                });
                //处理多选
                if (params.multiple && params.columns) {
                    _columns = [[{checkbox: true}]];
                    params.columns[0] = _columns[0].concat(params.columns[0]);
                }
                return $.extend(true,ret, params);  //  fxq2013-8-9 改为深拷贝，防范不同实例之间的冲突。 
            },
            
             /**
             * 通用S_CODE代码表。
             * @param {object} params 附加属性，覆盖cCommon()的默认设置
             * @returns {jQuery.hd.ezui.code.cCommon.ret}   返回options
             */            
            sCode: function(params) {
                var ret = {fieldMapping: {
                        code: 'code',
                        name: 'name'
                    },
                    sortName: 'code',
                    sortOrder: 'asc',
                    idField: 'code',  
                    textField: 'name',
                    panelWidth: 205,
                    filterColumns: 'code,name',
//                    url: '../../webresources/login/SysCode/findAll',   
                    method:'post',
                    columns: [[{
                                field: 'code',
                                title: '代码',
                                sortable: true,
                                width: 60
                            }, {
                                field: 'name',
                                title: '名称',
                                sortable: true,
                                width: 120
                            }
                        ]]};
                $.extend(ret, params);
                return $.hd.ezui.code.cCommon(ret);
            },
        
            sCodeFor: function(fldEng,params) {
            	if(!$.isPlainObject(params)){
            		params={};
            	}
                $.extend(params,{andItems: [{column: 'fieldCod', value: fldEng }]});
                return $.hd.ezui.code.sCode(params);
            },
            /**
             * 当前操作员可访问的“公司”的列表。对公司人员只有一个选择。对集团可选“全部“
             * @returns
             */
            companyId: function(params) {
                var ret = {fieldMapping: {
                	companyId: 'companyId',
                	companyCod: 'companyCod',
                	cnCompanyNam:'cnCompanyNam',
                    },
                    idField: 'companyCod',            //fxq 8-14 防止 调用者在fieldeMapping中只有一个字段，导致找不到textField的问题
                    textField: 'cnCompanyNam',                    
                    sortName: 'companyCod',
                    sortOrder: 'asc',
                    method:"POST",
                    url: '../webresources/login/SbcCompany/find',
                    columns: [[
                               //COMPANY_ID, COMPANY_COD, CN_COMPANY_NAM
                               {   
                                title:'公司代码',
                                field: 'companyCod',
                                width: 60
                            },
                            {   
                                title:'公司名称',
                                field: 'cnCompanyNam',
                                width: 60
                            }
                        ]]  ,
                };
                $.extend(ret, params);
                return $.hd.ezui.code.cCommon(ret);
            },
            /**
             * 当前操作员可访问的“公司”的列表。对公司人员只有一个选择。对集团可选“全部“
             * @returns
             */
            machTyp: function(params) {
                var ret = {fieldMapping: {
                	machTypCod: 'machTypCod',
                	machTypNam:'machTypNam',
                    },
                    idField: 'machTypCod',            //fxq 8-14 防止 调用者在fieldeMapping中只有一个字段，导致找不到textField的问题
                    textField: 'machTypNam',                    
                    sortName: 'machTypCod',
                    sortOrder: 'asc',
                    method:"POST",
                    url: '../webresources/login/SbcMachineType/find',
                    columns: [[
                               {   
                                title:'机械类型代码',
                                field: 'machTypCod',
                                width: 60
                            },
                            {   
                            	title:'机械类型名称',
                                field: 'machTypNam',
                                width: 60
                            }
                        ]]  ,
                };
                $.extend(ret, params);
                return $.hd.ezui.code.cCommon(ret);
            },
    }
 });

 
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// 
//FXQ 2015-5-08 原$.hd.combogrid.options空间的系列函数已经删除
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
 
//===========================================================================

/* 
 * 系列combogrid下拉option对象，重构的笔记： 2013-8-9 by fuxinqi 

$.hd.ezui.code.xx  有：( 基本对应独立实体类)
  
cCommon
sCode
cClient
corpId

cProcessNam
vjProcessWay
cMachineNo
cEmployee
cYard
cLoc
cSubLoc
cBerth
cDept
cPost
cCargoKind
cCargo
cPkgKind
cargoControlId
cPort
cShipData
cClass
cCountry
cShipLine

==============================================================
$.hd.combogrid.options.xx 系列，已经废弃， fuxinqi 2015-05

===========================================
所有的S_CODE应统一使用：

../rest/login/common/comboGridREST/scode/CONTROL_ID
 
 */
