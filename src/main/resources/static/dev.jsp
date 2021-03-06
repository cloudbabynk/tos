<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<meta charset="UTF-8">
<html>
    <head>
        <title>代码生成器</title>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
        <meta name="viewport" content="width=device-width">
        <script src="js/jquery.min.js"></script>
        <script src="js/jquery.json-2.4.js"></script>
        <script src="js/jquery.easyui.min.js"></script>
        <script src="js/datagrid-groupview.js"></script> 
        <script src="i18n/locale/easyui-lang-zh_CN.js"></script>
        <!--        <link rel="stylesheet" type="text/css" href="css/huadong/themes.css"/>-->
        <link rel="stylesheet" href="css/themes/icon.css">
        <link rel="stylesheet" type="text/css" href="css/huadong/main.css"/>
      <!--   <script src="js/hdEzuiEx_js/ezuiEx.js"></script> -->
        <script src="js/hdEzuiEx_js/HdObject.js"></script>
        <script src="js/hdEzuiEx_js/HdConditions.js"></script>
        <script src="js/hdEzuiEx_js/HdUtils.js"></script>
       <!--  <script src="js/hdEzuiEx_js/ezuiEx-lang-zh_CN.js"></script> -->
        <script src="js/wabob.js"></script>
        <link href="css/themes/metro-green/easyui.css" rel="stylesheet" type="text/css"/>
        <script src="js/hdEzuiEx_js/extension.js" type="text/javascript"></script>
        <script src="js/ZeroClipboard.js" type="text/javascript"></script>
        <script src="../js/ZeroClipboard.js" type="text/javascript"></script>
        <style type="text/css">
            input{
                border-color:#B1C242 ;
                width:180px;
            }
            fieldset{
                border-color:#B1C242 ;
            }
            legend{
                color:#009900;
            }
            textarea{
                border-color:#B1C242 ;

            }
        </style>
    </head>

    <body>
        <div class="easyui-panel">
            <div class="easyui-layout" style="height:645px">   
                <div data-options="region:'west',title:'',split:true" style="width:400px;padding-left: 5px;">
                    <br>
                    <fieldset ><legend >单表查询</legend>
                        <div style="width:325px;">
                            请输入实体名(如:AuthUser)：&nbsp;<br>
                            <input  name="table" id="table" style="width:180px;"><button  onclick="getpro()">点击获取属性</button><br>
                           
                            请输入Controller类名(如:AuthUserController)：&nbsp; 
                            <input name="table" id="singleR" style="width:180px" value="1"/><br>
                            请输入Service类名(如:AuthUserService)：&nbsp;
                            <input name="table" id="singleS" style="width:180px" value="1"/><br>
                             请输入ServiceImpl类名(如:AuthUserServiceImpl)：&nbsp;
                            <input name="table" id="singleSImp" style="width:180px" value="1"/><br>
                            
                            选择反转效果：<br>
                            <select id="ssSingle" class="easyui-combobox" style="width:183px;"  
                                    data-options="valueField: 'id',
                                    textField: 'text',
                                    panelHeight: 50,
                                    data:[{    
                                    'id':1,    
                                    'text':'Datagrid'  
                                    },
                                    {    
                                    'id':2,    
                                    'text':'Form表单'   
                                    },{    
                                    'id':3,    
                                    'text':'   '   
                                    }],
                                    onSelect: function(rec){
                                    if(rec.id==1){
                                    $('.someother').css('display','block');

                                    }else{
                                    $('.someother').css('display','none');
                                    }
                                    if(rec.id==2){
                                    $('.colDivSingle').css('display','block');
                                     $('#codeform').css('display','block');
                                    }else{
                                    $('.colDivSingle').css('display','none');                                    
                                    }}">
                            </select>
                            <br>
                            <div class="colDivSingle" style="display: none">增加标题：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;编辑标题：<br>
                                <input id="dialogTitleAdd" style="width:75px" />&nbsp;&nbsp;&nbsp;&nbsp;
                                <input id="dialogTitleEdit" style="width:75px" /><br></div>
                            <div class="colDivSingle" style="display: none;" >1行显示列数：&nbsp;<br>
                                <input id="colNumSingle" class="easyui-numberbox" style="text-align:left;" value="3"><br>
                             <div class="colDivSingle" style="display: none;" >dialog嵌入form路径：&nbsp;<br>
                                <input id="formUrl" ><br>
                            </div> 
                            <div class="colDivSingle" style="display: none;">
                                请选择form需要显示的属性:<br>
                                <select class="easyui-combobox" id="pro"  style="width:183px;"
                                        data-options="valueField: 'val',
                                        textField: 'text',
                                        panelHeight: 'auto',
                                        data:[{    
                                        'val':1,    
                                        'text':'全选属性',
                                        selected:true

                                        },
                                        {    
                                        'val':2,    
                                        'text':'选择部分属性'   
                                        }],
                                        onSelect: function(idv){
                                        if(idv.val==2){
                                        $('.other').css('display','block');
                                        }else{
                                        $('.other').css('display','none');
                                        }}">
                                </select>
                            </div>
                            <div class="someother" style="display: none;">
                                请选择Datagrid需要显示的属性:<br>
                                <select class="easyui-combobox" id="ooo"  style="width:183px;"
                                        data-options="valueField: 'val',
                                        textField: 'text',
                                        panelHeight: 'auto',
                                        data:[{    
                                        'val':1,    
                                        'text':'全选属性',
                                        selected:true
                                        },
                                        {    
                                        'val':2,    
                                        'text':'选择部分属性'   
                                        }],
                                        onSelect: function(idv){
                                        if(idv.val==2){
                                        $('.other').css('display','block');
                                        }else{
                                        $('.other').css('display','none');
                                        }}">
                                </select>
                            </div>
                            <div class="other" style="display:none">选择属性<br>
                                <input id="property" class="easyui-combobox" style="width:310px"/><br>
                            </div>
                            
                        </div>
                            <div style="text-align:left"><br>
                                <button  onclick="createSingleAll()">生成代码</button><br></div>
                    	</div>            
                    </fieldset>
                    <br>
                    <!-- <fieldset><legend>多表查询</legend>
                        <div style="width:330px">
                            sql语句：<br><br>
                            <textarea  id="txt" rows="9" cols="49" style="vertical-align: top ;border-color:#B1C242">select distinct p.PRIVILEGE_ID AS ID,u.ACCOUNT,p.UPD_ACCOUNT,p.UPD_TIMESTAMP,p.INS_ACCOUNT,p.INS_TIMESTAMP,p.DESCRIPTION,p.PARENT_ID,
p.TEXT,p.URL,p.SORTER,p.STATE,p.ICON_CLS,p.OPEN_TYPE  
from auth_privilege p,auth_user u,auth_role r,auth_role_privilege rp,auth_user_role ur 
where u.user_id=ur.USER_ID and r.role_id=ur.ROLE_ID and r.role_id=rp.ROLE_ID and rp.privilege_id=p.privilege_id;</textarea>
                            <br><br>
                            查询URL(如:Mutil)：&nbsp;<br><input name="url" id="url" /><br>                            
                            请输入类名(如:MultilFacade)：<br>
                            <input name="table00" id="facade" /><br>
                            请输入类名(如:MultilResource)：<br>
                            <input name="table2" id="resource" /><br>
                            反转效果：<br><select id="ss" class="easyui-combobox" style="width:183px"
                                             data-options="valueField: 'id',
                                             textField: 'text',
                                             panelHeight: 50,
                                             data:[{    
                                             'id':1,    
                                             'text':'Datagrid'  
                                             },{    
                                             'id':2,    
                                             'text':'Form表单'   
                                             }],
                                             onSelect: function(rec){
                                             if(rec.id==2){
                                             $('.colDiv').css('display','block');
                                             $('#codeform').css('display','block');
                                             }else{
                                             $('.colDiv').css('display','none');
                                             }},"
                                             >
                            </select>
                            <div class="colDiv" style="display: none">增加标题：<br><input id="dialogTitleAddMulti" /><br></div>
                            <div class="colDiv" style="display: none">编辑标题：<br><input id="dialogTitleEditMulti" /><br></div>
                            <div class="colDiv" style="display: none">1行显示列数：<br><input id="colNum"  class="easyui-numberbox" style="text-align:left;"><br></div> 
                            <div class="colDiv" style="display: none;" >dialog嵌入form路径：&nbsp;<br>
                                <input id="formUrlMutil" /><br>
                            </div>
                            <div style="text-align: left"><br><button onclick="createALL()">生成代码</button></div>
                        </div>
                    </fieldset> -->
                </div>
                <div data-options="region:'center'" style="height:645px">                    
                        form:<textarea  id="codeform" rows="14" cols="73" style="background:#ccffcc;float:left;display: none">在此显示反转form的页面代码</textarea>
                       jsp <textarea  id="codejsp" rows="14" cols="74" style="background:#ccffcc;float:left">在此显示反转jsp页面代码</textarea>                    
                    <br>
                 	 service<textarea  id="codeService" rows="15" cols="151" style="background:#ccffcc">在此显示反转Service代码</textarea><br>
                    impl<textarea  id="codeServiceImp" rows="15" cols="151" style="background:#ccffcc">在此显示反转ServiceImp代码</textarea><br>
                    controll<textarea  id="codecontroll" rows="15" cols="151" style="background:#ccffcc">在此显示反转Controll代码</textarea><br>
                   repository<textarea  id="coderepository" rows="15" cols="151" style="background:#ccffcc">在此显示反转repository代码</textarea><br>
                      
                </div>
            </div>
            <script type="text/javascript">
                var clip = null;
                function gd(id) {
                    return document.getElementById(id);
                }
                /* function init() {
                	var zc = new ZeroClipboard();
                    clip =zc.Client();
                    clip.reposition();
                    clip.setHandCursor(true);
                    clip.addEventListener('mouseOver', copyToClickboard);
                    clip.glue('copyCode');
                } 
                function copyToClickboard() {
                    // we can cheat a little here -- update the text on mouse over
                    clip.setText(gd('code').value);
                }
                window.onload = init();
                bind(document, "click", function () {
                    clip.reposition();
                });
                bind(window, "resize", function () {
                    clip.reposition($("#copyBtn"));

                });
                */
                function getpro() {
                    var entity = $('#table').val();
                    $.ajax({
                        method: "GET",
                        url: "webresources/normal/HdGenerateResource/getproperty?tbName=" + entity+"&t="+Math.random(),
                        async: false,
                        success: function (data) {
                            $("#property").combogrid({
                                data: data,
                                idField: 'columnName',
                                //valueField:'columnName',
                                textField: 'columnName',
                                multiple: true,
                                panelHeight: 120,
                                //   editable:false 
                                columns: [[
                                        {field: 'columnName', title: 'Name', width: 40},
                                        {field: 'comments', title: 'comments', width: 50},
                                        {field: 'dataType', title: 'dataType', width: 50},
                                        {field: 'dataLength', title: 'dataLength', width: 50},
                                        {field: 'nullable', title: 'nullable', width: 50},
                                        {field: 'prim', title: 'prim', width: 40},
                                    ]]

                            });
                        }
                    })

                }
               
                function createSingle() {//生成jsp
                    // var choice= $('#property').combobox('getValues', 'key');
                    var choice = $('#property').combogrid('getValues', 'key');
                    var name = $('#table').val();
                    var ss = $("#ssSingle").combobox("getValue");
                    var ooo = $("#ooo").combobox("getValue");
                    var tt = $("#pro").combobox("getValue");
                    var col = $("#colNumSingle").val();
                    var dialogTitleAdd = $("#dialogTitleAdd").val();
                    var dialogTitleEdit = $("#dialogTitleEdit").val();
                    var formUrl=$("#formUrl").val();
                    if (name == null || name == "") {
                        $.messager.alert("提示", "实体名不能为空！");
                    }
                    else {
                    
                            $.ajax({
                                method: "GET",
                                url: "webresources/normal/HdGenerateResource/gengrid?tbName="+ name +"&col=" + col + "&ss=" + ss + "&dialogTitleAdd=" + dialogTitleAdd + "&dialogTitleEdit=" +
                                        dialogTitleEdit +"&formUrl=" + formUrl+"&t="+Math.random(),
                                async: false,
                                success: function (data) {
                                    $("#codejsp").text(data);
                                }, error: function (data) {
                                }
                            });

                        
                    }
                }
                function createForm() {
                    // var choice= $('#property').combobox('getValues', 'key');
                    var choice = "";//$('#property').combogrid('getValues', 'key');
                    var name = $('#table').val();
                    var ss = $("#ssSingle").combobox("getValue");
                    var ooo = "";//$("#ooo").combobox("getValue");
                    var tt = "";//$("#pro").combobox("getValue");
                    var col = $("#colNumSingle").val();
                    var dialogTitleAdd = $("#dialogTitleAdd").val();
                    var dialogTitleEdit = $("#dialogTitleEdit").val();
                    if (name == null || name == "") {
                        $.messager.alert("提示", "实体名不能为空！");
                    }
                    else {
                     
                            $.ajax({
                                method: "GET",
                                url: "webresources/normal/HdGenerateResource/form?tbName="+ name +"&col=" + col + "&ss=" + ss + "&dialogTitleAdd=" + dialogTitleAdd + "&dialogTitleEdit=" +
                                        dialogTitleEdit+"&t="+Math.random(),
                                async: false,
                                success: function (data) {
                                    $("#codeform").text(data);
                                }, error: function (data) {
                                }
                            });

                        
                    }
                }

                function createSingleR() {//Controll
                    var entity = $('#table').val();
                    var facadename = $('#singleF').val(); //Facade类名
                    var resourcename = $('#singleR').val();//Resource类名
                    var servicename = $('#singleS').val();//service类名

                        $.ajax({
                            method: "GET",
                            url: "webresources/normal/HdGenerateResource/singleR" + "?repositoryname=" + facadename + "&entity=" + entity + "&controllerename="
                            		+ resourcename+"&servicename=" + servicename+"&t="+Math.random()+'&ss='+$("#ssSingle").combobox("getValue"),
                            async: false,
                            success: function (data) {
                                $("#codecontroll").text(data);
                            }, error: function (data) {
                            }
                        });
                       }
                function createSingleS() {
                    var entity = $('#table').val();
                    var servicename = $('#singleS').val();//service类名

                        $.ajax({
                            method: "GET",
                            url: "webresources/normal/HdGenerateResource/singleS"
                            + "?servicename=" + servicename + "&entity=" + entity +"&t="+Math.random()+'&ss='+$("#ssSingle").combobox("getValue"),
                            async: false,
                            success: function (data) {
                                $("#codeService").text(data);
                            }, error: function (data) {
                            }
                        });
                    
                }
                function createSingleSImp() {
                    var entity = $('#table').val();
                    var facadename = $('#singleF').val(); //Facade类名
                    var servicename = $('#singleS').val(); //Facade类名
                    var serviceImpName = $('#singleSImp').val(); //Facade类名
                 
                        $.ajax({
                            method: "POST",
                            url: "webresources/normal/HdGenerateResource/singleSImp?&entity=" + entity + "&servicename=" + servicename+"&serviceImpName="
                            		+ serviceImpName+"&t="+Math.random()+'&ss='+$("#ssSingle").combobox("getValue"),
                            async: false,
                            success: function (data) {
                                $("#codeServiceImp").text(data);
                            }, error: function (data) {
                            }
                        });
                    
                }
                function createRepository() {
                	 var entity = $('#table').val();
                        $.ajax({
                            method: "GET",
                            url: "webresources/normal/HdGenerateResource/singleRepository"
                            + "?entity=" + entity + "&t="+Math.random()+'&ss='+$("#ssSingle").combobox("getValue"),
                            async: false,
                            success: function (data) {
                                $("#coderepository").text(data);
                            }, error: function (data) {
                            }
                        });

                    
                }
   
                function createSingleAll() {
                    createSingle();//jsp
                    createRepository();
                    createSingleR();
                    createForm();
                    createSingleS();
                    createSingleSImp();
                }
            </script>
        </div>
    </body>

</html>
