<meta charset="UTF-8" />

<!-- 工具栏 结束 -->
<!-- Datagrid -->
<div class="easyui-tabs" data-options="fit:true" id="workflowtab">
	<div data-options="title:'待办任务',border:false,closable:false,fit:true">
		<div id="WorkFlowNeedDoToolBar" style="float: left">
			<div>
				<span style="float: left"> <a class="easyui-linkbutton"
					iconCls="icon-reload" plain="false">查询</a>
				</span>
			</div>
		</div>
		<table id="WorkFlowNeedDoDatagrid"></table>
	</div>
	<div data-options="title:'已办查询',border:false,closable:false,fit:true">
		<div id="WorkFlowAllDoToolBar" style="float: left">
			<div>
				<span style="float: left"> <a class="easyui-linkbutton"
					iconCls="icon-reload" plain="false">查询</a>
				</span>
			</div>
		</div>
		<table id="WorkFlowAllDoDatagrid" style="height: 90%"></table>
	</div>
</div>

<!-- end Datagrid -->
<div id="chart-dlg">
	<div id="chart"></div>
</div>

<div id="chart-buttons">
	<a href="javascript:void(0)" class="easyui-linkbutton"
		onclick="javascript:$('#chart-dlg').dialog('close')">关闭</a>
</div>

<!-- 脚本控制 -->
<script type="text/javascript">
	$(document)
            .ready(function() {
	            var selworkflowRId = '';
	            var dg = $("#WorkFlowNeedDoDatagrid");
	            var dgAllDo = $("#WorkFlowAllDoDatagrid");
	            var builder = new HdEzuiQueryParamsBuilder();
	            var builder2 = new HdEzuiQueryParamsBuilder();
	            // 	            // 搜索框
	            // 	            $("#WorkFlowSearchbox1501049623861").searchbox({
	            // 	                prompt : "描述",
	            // 	                searcher : function(value, name) {
	            // 		                var buildersearch = new HdEzuiQueryParamsBuilder();
	            // 		                buildersearch.set("q", value);
	            // 		                var queryType = $('input:radio[name="workflowquery"]:checked').val();
	            // 		                hdConditions.setOtherskeyValue("hisShow", queryType);
	            // 		                dg.datagrid({
	            // 		                    queryParams : buildersearch.build(),
	            // 		                    url : "../webresources/login/workflow/WorkFlow/find"

	            // 		                });
	            // 	                }
	            // 	            });

	            /* // 保存。
	                $("#WorkFlowToolBar1501049623861 a[iconCls='icon-save']").on("click",function() {
	                    dg.datagrid("hdSave",{url:"../webresources/login/WorkFlow/save"});  
	            }); */
	            // 刷新。
	            $("#WorkFlowNeedDoToolBar a[iconCls='icon-reload']").on("click", function() {
		            dg.datagrid("hdReload");
	            });
	            $("#WorkFlowAllDoToolBar a[iconCls='icon-reload']").on("click", function() {
		            dgAllDo.datagrid("hdReload");
	            });
	            $('#workflowtab').tabs({//tab切换时触发
		            onSelect : function(title, index) {
			            if (index == 1) {
				            $("#WorkFlowAllDoDatagrid").datagrid({
					            url : "../webresources/login/workflow/WorkFlow/find?type=2"
				            });
			            }
		            }
	            });
	            // 	            $("#WorkFlowNeedDoToolBar a[iconCls='icon-send']").on("click", function() {
	            // 		            var data = dg.datagrid("getSelected");
	            // 		            if (!data) {
	            // 			            HdUtils.messager.info('请先选择数据！');
	            // 			            return;
	            // 		            }
	            // 		            HdUtils.ajax.post({
	            // 		                url : "../webresources/login/workflow/WorkFlow/send?taskId=" + data.taskId,
	            // 		                success : function(data) {
	            // 			                HdUtils.messager.bottomRight(data.message);
	            // 		                }
	            // 		            });
	            // 	            });
	            // 	            $("#WorkFlowToolBar a[iconCls='icon-back']").on("click", function() {//退回
	            // 		            var data = dg.datagrid("getSelected");
	            // 		            if (!data) {
	            // 			            HdUtils.messager.info('请先选择数据！');
	            // 			            return;
	            // 		            }
	            // 		            HdUtils.ajax.post({
	            // 		                url : "../webresources/login/workflow/WorkFlow/back?taskId=" + data.taskId,
	            // 		                success : function(data) {
	            // 			                HdUtils.messager.bottomRight(data.message);
	            // 		                }
	            // 		            });
	            // 	            });
	            $("#WorkFlowNeedDoDatagrid")
	                    .datagrid({
	                        striped : true,
	                        url : "../webresources/login/workflow/WorkFlow/find?type=1",
	                        queryParams : builder.build(),
	                        method : "post",
	                        pagination : true,
	                        singleSelect : true,
	                        rownumbers : true,
	                        pageSize : 20,
	                        toolbar : "#WorkFlowNeedDoToolBar",
	                        fit : true,
	                        fitColumns : false,
	                        columns : [ [
	                                {
	                                    field : 'opt',
	                                    title : "操作",
	                                    width : 70,
	                                    align : "center",
	                                    halign : "center",
	                                    formatter : function(val, row, idx) {
		                                    return "<input type='button' value='操作'  onclick='workflowShow(\"" + row.taskId + "\",\"" + row.formKey + "\",\"" + row.workFlowName + "\",\"" + row.busiId + "\")'/>";
	                                    }
	                                }, {
	                                    field : "userName",
	                                    title : "申请人",
	                                    width : 70,
	                                    multiSort : true,
	                                    halign : "center",
	                                }, {
	                                    field : "applyTime",
	                                    title : "申请时间",
	                                    width : 110,
	                                    multiSort : true,
	                                    halign : "center",
	                                }, {
	                                    field : "workFlowName",
	                                    title : "当前环节",
	                                    width : 140,
	                                    multiSort : true,
	                                    halign : "center",
	                                }, {
	                                    field : "lastAccount",
	                                    title : "传来人",
	                                    width : 70,
	                                    multiSort : true,
	                                    halign : "center",
	                                }, {
	                                    field : "startTime",
	                                    title : "传来时间",
	                                    width : 110,
	                                    multiSort : true,
	                                    halign : "center",
	                                }, {
	                                    field : "content",
	                                    title : "内容",
	                                    width : 400,
	                                    multiSort : true,
	                                    halign : "center",
	                                } ] ]
	                    });
	            $("#WorkFlowAllDoDatagrid")
	                    .datagrid({
	                        striped : true,//url在tab触发时赋值
	                        // url : "../webresources/login/workflow/WorkFlow/find?type=2",
	                        queryParams : builder2.build(),
	                        method : "post",
	                        pagination : true,
	                        singleSelect : true,
	                        rownumbers : true,
	                        pageSize : 20,
	                        toolbar : "#WorkFlowAllDoToolBar",
	                        fit : true,
	                        fitColumns : false,
	                        columns : [ [
	                                {
	                                    field : 'opt2',
	                                    title : "操作",
	                                    width : 70,
	                                    align : "center",
	                                    halign : "center",
	                                    formatter : function(val, row, idx) {
		                                    return "<input type='button' value='操作'  onclick='workflowShow(\"" + row.taskId + "\",\"" + row.formKey + "\",\"" + row.workFlowName + "\",\"" + row.busiId + "\")'/>";
	                                    }
	                                }, {
	                                    field : "userName",
	                                    title : "申请人",
	                                    width : 70,
	                                    multiSort : true,
	                                    halign : "center",
	                                }, {
	                                    field : "applyTime",
	                                    title : "申请时间",
	                                    multiSort : true,
	                                    width : 110,
	                                    halign : "center",
	                                }, {
	                                    field : "workFlowName",
	                                    title : "环节名称",
	                                    width : 140,
	                                    multiSort : true,
	                                    halign : "center",
	                                }, {
	                                    field : "startTime",
	                                    title : "开始时间",
	                                    multiSort : true,
	                                    width : 110,
	                                    halign : "center",
	                                }, {
	                                    field : "endTime",
	                                    title : "结束时间",
	                                    multiSort : true,
	                                    width : 110,
	                                    halign : "center",
	                                }, {
	                                    field : "lastAccount",
	                                    title : "传来人",
	                                    width : 70,
	                                    multiSort : true,
	                                    halign : "center",
	                                }, {
	                                    field : "operTypeStr",
	                                    title : "类型",
	                                    width : 70,
	                                    multiSort : true,
	                                    halign : "center",
	                                }, {
	                                    field : "content",
	                                    title : "内容",
	                                    width : 400,
	                                    multiSort : true,
	                                    halign : "center",
	                                } ] ]
	                    });

            });

    function workflowShow(taskId, formKey, title, busiId) {//busiId业务主键

	    if (!formKey) {
		    return false;
	    }
	    if (formKey.endWith("jsp") || formKey.endWith("html") || formKey.endWith("htm")) {
		    formKey += "?";
	    }
	    var flowUrl = formKey + "&taskId=" + taskId + "&busiId=" + busiId;
	    HdUtils.dialog.show({
	        width : 1050,
	        height : 550,
	        title : title,
	        href : flowUrl
	    });

    }
    function workflowreload() {
	    $("#WorkFlowNeedDoDatagrid").datagrid("hdReload");
	    $("#WorkFlowAllDoDatagrid").datagrid("hdReload");
    }
