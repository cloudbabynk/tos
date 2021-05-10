<!--2016-07-21 by huxj 从gh-base引入新UI，在此基础上调整修改-->
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%--  huxj 2016-7-21 在gh－base基础上引入新的UI，并作了部分调整  --%>   
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <title>华东idev7开发平台</title>
        
	    <META    HTTP-EQUIV="Pragma" CONTENT="no-cache">   
	    <META    HTTP-EQUIV="Cache-Control" CONTENT="no-cache">   
	    <META    HTTP-EQUIV="Expires" CONTENT="0">      
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>

<%--  FXQ 2015-5-5 为了实现一般的ezui网页弹出时建立Js环境 ，这里统一导入全部的js    FXQ 2015-3-5 使用JSP包含全部的Js和css定义  --%>
<%
    String path = request.getContextPath();
                    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>


<%-- 主页JS程序的正式开始。 此注释不会出现在最终HTML中 --%>    
    <script type="text/javascript">
        <%--强制加入JSESSIONID，解决在chrome环境下报表被过滤器拦截的问题【注意】测试环境URL不能用localhost否则无效 --%>
        window.JSESSIONID = '<%=session.getId()%>';
        window.CONTEXTPATH = '<%=request.getContextPath()%>';
    </script>
  <script src="<%=request.getContextPath()%>/js/hdEzuiEx_js/ezuiComboGridCode.js"></script>  
  <script src="<%=request.getContextPath()%>/js/hdUtils/HdUtils.js"></script>  
    
 </head>

 <body class="easyui-layout" id="mainBody" data-options="border:false" >
        <div id="mainWindowTopbar" class="mainHeader" data-options="region:'north',border:false,height:52" >  
            <div class="headerLogo"></div>
            <div class="headerIcon">
                <a href="../webresources/normal/PrivilegeResource/logout"id="logout" class="headerIconLink5">退出</a>
                <a href="javascript:void(0)" id="mainVisitHistory" class="headerIconLink4"></a>
                <a href="javascript:void(0)" id="mainChangeTheme" class="headerIconLink3"></a>
                <a href="javascript:void(0)" id="fullscreen" class="headerIconLink2"></a>
                <a href="javascript:void(0)" id="message" class="headerIconLink1"></a>
               <!-- 这是原来的超链接部分 -->
               <!--  <a href="javascript:void(0)" class="headerIconLink6" id="changeLanguage"></a> -->      
              <span>
                   <span id="spanlanguage">语言</span> 
                   <select id="changeLanguage" >
		                 <option id="optionlanguagefirst">中文</option>
		                 <option id="optionlanguagesecond">英文</option>
                   </select> 
              </span>  
                            
            </div> 
        </div>
     
    
     <!-- 选择语言的dialog -->
     <!-- <div id="lg" style="margin-top:30px; margin-bottom:30px;text-align:center;display:none" >
	     		<input type="radio" name="gender" value="zh" />中文
	     		<input type="radio" name="gender" value="en" style="margin-left:60px;"/>English
     </div> -->
     
     <div id="mainMenuDiv" class="mainLeft"  style="z-index:9999;" >  <%--  必须是layout中的panel --%>
         <div id="col-side">

             <div class="userDiv" id="userDiv">
                 <div class="userPic"><img src="../images/userPic.jpg" width="80" height="80"  alt=""/></div>
                 <div class="leftIcon"><a href="#" id="moveLeft"><img src="../images/leftIcon.png" width="20" height="20"  alt=""/></a></div>
                 <div class="userName" id="mainCurrentLogin">尚未登录</div>      
             </div>
             <div>
                <ul class="menuUl" id="mainMenu"></ul>
                <ul class="menuUl" style="display:none;"></ul>
               <div id="menuDownUp" class="menuNext"></div>
             </div>
         </div>
     </div>
         
    <div id="mainMenuDiv1"  data-options="region:'west',border:false,title:'菜单导航'" style="width: 142px;z-index:0;" >  <%--  此左侧layout为假写的，目的将center位置挤过去--%>
    </div>
    <%-- 工作区卡片 --%>   
    <div  data-options="region:'center',border:false" style="z-index:0;"> <%-- 这里边再套一层layout实现消息的发布 --%>
        <div class="easyui-layout" data-options="fit:true,border:false"　　　　>
                <div data-options="region:'center',border:false">   <%-- center 区不能fit否则就把别人挤没了 --%>
                <div id="main" data-options="fit:true,tools:'#mainTabsToolbar',border:false">  <%-- fxq 2015-11-17 使用特定的tabs组件 取消class="easyui-tabs"调用仍可使用tabs方法调用 --%>
                    <div id="defaultTabpage" data-options="title:'首页',border:false,closable:false,iconCls:'icon-home',fit:true,href:'<%=basePath%>/login/system/home.html'">
                        <div id="mainTabsToolbar" style="border:0px; height:20px; top:0px; border:0px; border-bottom: 0px solid #ddd; padding-right:3px;" class="tabs-tool-bottom">
                            <div>
                                <a name="btnGoHome" href="#" class="tabIconHome"><img src="<%=basePath%>/images/tabsIconEmpty.png"/></a>
                                <a name="btnRefresh" href="#" class="tabIconRefresh"><img src="<%=basePath%>/images/tabsIconEmpty.png"/></a>
                                <a name="btnCloseIt" href="#" class="tabIconClose"><img src="<%=basePath%>/images/tabsIconEmpty.png"/></a>
                                <a name="btnHideHead" href="#" class="tabIconFullUp"><img src="<%=basePath%>/images/tabsIconEmpty.png"/></a>
<!--                                <span style="float:right;margin-top:3px; margin-right: 2px;" >
                                    <input class="easyui-searchbox" prompt="在此输入过滤条件"/>
                                </span>-->
                            </div>
                        </div> 
                    </div>       
                    </div>
                </div> 
            </div> 
        </div>
    <%--主题列表对话框 --%> 
    <div id="mainChangeThemeDialog"></div>
    <%--以下独立的DIV --%> 
    <div id="mainDlg"></div>
    <div id="mainDlg2"></div>
    <%--访问列表的下拉菜单 --%> 
    <div id="mainVisitDialog" > 
    </div>

    <%--遮罩层，显示flex界面。不放入tabpage页是因为在div.show/HIDE过程中，flex应用频繁初始化 --%> 
    <div id="swfMaskDiv" style="left:0px;top:0px;width:1px; height:1px; display:block; border:0px;z-index:333333;position:absolute;"> 
         <%-- fxq201511- swf暂不使用  <jsp:include page="../pages/flex_loader/loadFlex.jsp"/> --%>
    </div>
</body>
  
<script type="text/javascript">
    /*获取浏览器语言*/
    		var browserLanguage = "";
            function getBrowserLanguage() {
    			 $.ajax({
            		url: "../webresources/login/AuthUser/findLanguage ",
            		dataType:"text",
                    method: "get",
                    async: false,
                    success:function(data){
                    	browserLanguage = data;
                    },
            		error:function(data){
                    }
            		});
            	
    			/* 该部分注释是为了改成下拉实现的 */
               /*   if (browserLanguage=="zh") {
                	$("#changeLanguage").removeClass().addClass("headerIconLink6");
                    return "zh";
                } else if (browserLanguage=="en") {
                	$("#changeLanguage").removeClass().addClass("headerIconLink99");
                    return "en";
                }else{
                	$("#changeLanguage").removeClass().addClass("headerIconLink6");
                	return "zh";
                }  
    			 */	
    			 
                 if (browserLanguage=="zh") {
                     return "zh";
                 } else if (browserLanguage=="en") {
                     return "en";
                 }else{
                 	return "zh";
                 } 
                
            } 
            function LoadJS(id, fileUrl)
            {
                var scriptTag = document.getElementById(id);
                var oHead = document.getElementsByTagName('HEAD').item(0);
                var oScript = document.createElement("script");
                if (scriptTag)
                    oHead.removeChild(scriptTag);
                oScript.id = id;
                oScript.type = "text/javascript";
                oScript.src = fileUrl;
                oHead.appendChild(oScript);
            }
            /*国际化*/
            var language = getBrowserLanguage();
            LoadJS("language2", '../js/hdEzuiEx_js/ezuiEx-lang-' + language + '.js');
            LoadJS("language", '../i18n/locale/easyui-lang-' + language + '.js');
            
    
    var killErrorPrompt = function(errorMessage, scriptURI, lineNumber, columnNumber, errorObj) {
    }; //屏蔽错误框
    var upDownTheme="";//定义全局变量，用于上下隐藏按钮
//    function detectZoom (){    var ratio = 0,     screen = window.screen,     ua = navigator.userAgent.toLowerCase();      if (window.devicePixelRatio !== undefined) {       ratio = window.devicePixelRatio;   }   else if (~ua.indexOf('msie')) {       if (screen.deviceXDPI && screen.logicalXDPI) {       ratio = screen.deviceXDPI / screen.logicalXDPI;     }   }   else if (window.outerWidth !== undefined && window.innerWidth !== undefined) {     ratio = window.outerWidth / window.innerWidth;   }        if (ratio){     ratio = Math.round(ratio * 100);   }        return ratio; };
    $(document).ready(function() {
	
    	 $("#mainDlg").dialog({closed:true});
    	 $("#mainDlg2").dialog({closed:true});
         if(browserLanguage=="zh"){
        	 $("#changeLanguage #optionlanguagefirst").removeAttr("selected");
        	 $("#changeLanguage #optionlanguagesecond").removeAttr("selected");
        	 $("#changeLanguage #optionlanguagefirst").attr("selected","selected");
         }else{
        	 $("#changeLanguage #optionlanguagefirst").removeAttr("selected");
        	 $("#changeLanguage #optionlanguagesecond").removeAttr("selected");
        	 $("#changeLanguage #optionlanguagesecond").attr("selected","selected");
         }
    
        //国际化
         $.i18n.properties({
                    name: 'main', //资源文件名称
                    path: '../i18n/project/', //资源文件路径
                    mode: 'map', //用Map的方式使用资源文件中的值
                    language: getBrowserLanguage(),
                    callback: function () {//加载成功后设置显示内容
//                        $("label").each(function (i) {
//                            if ($(this).attr("name")) {
//                                $(this).html($.i18n.prop($(this).attr("name")));
//                            }
//                        });
                        $("#mainVisitHistory").html($.i18n.prop("main.menu.favorites"));
//                      $("#defaultTabpage").attr("title", $.i18n.prop("clickChange"));
                        $("#mainChangeTheme").html($.i18n.prop("main.changeThemes"));
                        $("#message").html($.i18n.prop("main.message.msg"));
                        $("#logout").html($.i18n.prop("main.logout"));
                        $("#fullscreen").html($.i18n.prop("main.fullscreen"));
                        //以下3个是为了语言下拉的国际化
                        $("#spanlanguage").html($.i18n.prop("main.language")); 
                        $("#optionlanguagefirst").html($.i18n.prop("main.optionlanguagefirst"));
                        $("#optionlanguagesecond").html($.i18n.prop("main.optionlanguagesecond")); 
                        //原来的超链接的语言的国际化
                       /*   $("#changeLanguage").html($.i18n.prop("main.language"));  */
                    }
                });
        
        $('link:first').attr('href', '../css/themes/metro-white/easyui.css');
     
        
        //全局默认选项，设置的参数会影响所有的$.ajax请求如$.get()、$.post()、$.ajax等。
        $.ajaxSetup({
            complete: function(XMLHttpRequest, status) {
                var sessionStatus = XMLHttpRequest.getResponseHeader("session-status");
                if(sessionStatus == "timeout"){
                    $.messager.alert("消息", "session超时或服务器重启，需重新登陆", "info", function() {
                            clearInterval(newsRefreshTimer);
                            window.location.replace("<%=request.getContextPath()%>/index.jsp");  //跳转到登录界面  
                        });
                }
            }
        });  
        //顶部标准功能
        //1 主搜索栏 及卡片控制图标
        $("#mainTabsToolbar .easyui-searchbox").searchbox({
            searcher: function(value, name) {
                $.main.onSeachboxValue(value);
                <%-- 已经封装，每个页面用$.main.registerSeachbox =function(operation,builerOrQueryParam,columns)对查询信息进行注册 --%>
            }
        });
        
        //2 全屏显示功能
        $("#fullscreen").bind('click', function() {
            var el = document.documentElement,
            rfs = el.requestFullScreen || el.webkitRequestFullScreen || el.mozRequestFullScreen || el.msRequestFullScreen,
            wscript;

            if (typeof rfs != "undefined" && rfs) {
                rfs.call(el);
                return;
            }

            if (typeof window.ActiveXObject != "undefined") {
                wscript = new ActiveXObject("WScript.Shell");
                if (wscript) {
                    wscript.SendKeys("{F11}");
                }
            }
        });
         //3 收藏夹功能
        $("#mainVisitHistory").bind('click', function() {
            $("#mainVisitDialog").dialog({
                noheader: false,
                title: '功能收藏',
                closable: true,
                resizable: true,
                width: 360,   
                height: 600, 
                left: $('#main').width() + $('#main').offset().left - 360,
                top: $('#main').offset().top,   
                href: 'system/historyAndFavorite.html',   
                modal: false  
            });
        });
        
        //4 换肤功能    
        $("#mainChangeTheme").bind('click', function() {
            openTab("皮肤管理",'../login/system/skin.html' , false, null);
        });
        
        //5 用户按下图标，阅读消息    
        $("#message").bind('click', function() {
            var clazz=$(this).attr("class");
            if("headerIconLink1"==clazz){
              openTab("消息",'../login/system/news/NewsReadAndConfirm.html' , false, null);
           //   openTab("noticesend",'../login/system/news/NoticeSend.html' , false, null);

            }else{
    //           openTab("发送消息",'../login/system/news/NewsRelease.html' , false, null); 
            }
        });
        
         //6 左侧菜单隐藏
        $("#moveLeft").bind('click', function() {
            $('#mainBody').layout('collapse', 'west');
            $("#mainMenuDiv").css('display', 'none');
        });
        
        //7 展开左侧菜单
        $("#mainMenuDiv1").panel({    
            onExpand: function() {
                $('#mainBody').layout('expand', 'west');
                $("#mainMenuDiv").css('display', 'block');    
            }    
        });  
        
      
        
        //8 选择语言
       /*  $("#changeLanguage").on('click',function(){	
		  $.ajax({
        		url: "../webresources/login/AuthUser/saveLanguage?language="+browserLanguage,
        		dataType:"Json",
                method: "POST",
                async: false,
                success:function(data){
                	location.reload();
                },
        		error:function(data){
                }
        	});  
      	}); */
        
         ///8 选择语言 （语言下拉的国际化）
        $("#changeLanguage").on('change',function(){
        	var index;
        	if(browserLanguage=="zh"){
        		index=0;
        	}else if(browserLanguage=="en"){
        		index=1;
        	}else{
        		index=0; 			
        	}

        	if(index==0){
        		browserLanguage="zh";
        	}else if(index==1){
        		browserLanguage="en";
        	}else{
        		browserLanguage="zh";
        	}
        	$.ajax({
        		url: "../webresources/login/AuthUser/saveLanguage?language="+browserLanguage,
        		dataType:"Json",
                method: "POST",
                async: false,
                success:function(data){
                	location.reload();
                },
        		error:function(data){
                }
        	});
        	
        }); 
        

        // 显示当前登陆用户信息。

        $.get('../login/PrivilegeController/getLoginAccount?t=11&s='+Math.random(), function(data) {
        	//alert("--"+data);
        	if(!data){  //FXQ2015-8-20由于URL不带有login，当未登陆时，这里不能自动跳转到登录页面且报错

               return;
           }
           $('#mainCurrentLogin').html("<a href='#' >" + data.name + "</a>");
           $('#mainCurrentLogin a').on('click',function(){
               //openTab('最近访问功能','../login/system/MostRecentUsedPages.html',true);   //未来：打开收藏夹界面
               HdUtils.dialog.show({title: "密码修改", href: "../login/system/user/password.html", width: 290, height: 205, isSaveHandler: true}); 
           });
           $.main.loginOper=$.extend({},data);  <%-- 保存用于判断权限等，见$.main.isHaveRequiredRole()函数 --%>
//           defVistListMenu();
			//alert("--"+data);
           changeThemByUserInfo(data);
       });
      
    // 显示当前登陆用户信息。
        function changeThemByUserInfo(data) {
            if(!data){
                return;
            }
            onChangeTheme(data.skin);
            upDownTheme=data.skin;
        }
        
        $('#mainTabsToolbar a[name="btnGoHome"]').on('click', function() {
            $('#main').tabs('select', '首页');
           // $('#main2').tabs('select', '首页2');
        });
        
        var upDownFlag="down";
        $('#mainTabsToolbar a[name="btnHideHead"]').on('click', function() {
            var img = $(this).children('img').attr('src');
            if (upDownFlag=="down") {
                $("#mainMenuDiv").addClass('mainLeftUp')
                showOrHideHeader('up');
                upDownFlag="up";
            } else {
                $("#mainMenuDiv").removeClass('mainLeftUp')
                $("#mainMenuDiv").addClass('mainLeft')
                showOrHideHeader('down');
                upDownFlag="down";
                
            }
        });
        
        $('#mainTabsToolbar a[name="btnRefresh"]').on('click', function() {
            var tab = $('#main').tabs('getSelected');  // get selected panel
            tab.panel('refresh'); //, 'plugin/' + encodeURI(url)
        });
        
        $('#mainTabsToolbar a[name="btnCloseIt"]').on('click', function() {
            $.messager.confirm('关闭所有页面','您确认所有数据已存盘，并要关闭全部工作页面?',function(r){   
                if (r){   
                    var tabsList=$('#main').tabs('tabs');
                    for(var i=tabsList.length-1;i>=0;i--){
                        var tab=$(tabsList[i]);
                        if(tab.size()>0 && $.data(tab[0], "panel")!==undefined){  //未知的原因，tab可能是windows对象而不是tabpage
                            var num=closeTab(tab);
                        }
                    }  // get selected panel
                }   
            }); 
            function closeTab(tab){
                var isCloseable = tab.panel('options').closable; //此属性为ezui内部使用的标志
                if (isCloseable !==false) {  //首页等不能关闭
                    var index = $('#main').tabs('getTabIndex', tab);
                    $('#main').tabs('close', index);
                } 
            }
        });
        
        //2主工作区
        $("#main").tabsMain({  //
            fit: true,
            tools: '#mainTabsToolbar',
            onBeforeClose: function(title, index) {
                 $.main.destroyDialogsOnClose($('#main').tabs('getTab', index));
             } // FUNCITON onBeforeClose
        });   //tabsMain
        var menuTwoMaxHeight = 550;
        //3 主菜单
        var mainMenu1 = $(".menuUl").eq(0);
        var mainMenu2 = $(".menuUl").eq(1);
        $.ajax({ 
                method: "get",
                //url: "../webresources/normal/workspaceRest/findAllForTree?s="+Math.random(),
                url: "../webresources/normal/PrivilegeResource/findAllForTree?s="+Math.random(),
                success: function(node) {
                    menuTwoMaxHeight = $('#mainMenuDiv').height();
                    var userDivHeight = $("#userDiv").height();
                    var menuNum = parseInt((menuTwoMaxHeight - userDivHeight)/53 - 1);
                    for(var i=1;i<node.length+1;i++){
                        var nodes=node[i-1];
                        var id=node[i-1].id;
                        var menuTowClass = 'menuTwo';
//                        menuTowClass= (i==5)?"menuTwo9 hdMenuTowFlag": menuTowClass;
                        var li=" <li id=\""+id+"\"class=\"menuColor"+i+"Unselt\"><a href=\"#\">"+nodes.text+"</a><div id=\""+id+"one\" class=\""+menuTowClass+"\" style=\"display:none; max-height:"+(menuTwoMaxHeight-100)+"px;\">";
                        if(i <= menuNum - 1){
                                mainMenu1.append(li);
                        }else{
                            if(i == menuNum){
                                    $("#menuDownUp").show();
                                }
                                mainMenu2.append(li);
                        }
//                        mainMenu1.append(" <li id=\""+id+"\"class=\"menuColor"+nodes.iconCls+"Unselt\"><a href=\"#\">"+nodes.text+"</a><div id=\""+id+"one\" class=\""+menuTowClass+"\" style=\"display:none; max-height:"+menuTwoMaxHeight+"px;\">");
                        controlMenuColor();
                        mouseenterOut();
                        getTwoMenu(id,nodes);
                    }
                    $("#mainMenu li a").wrapInner('<span class="out"></span>');
                    $("#mainMenu li a").each(function(){
                        $('<span class="over">' + $(this).text() + '</span>').appendTo(this);
                    });
                    $("#mainMenu li").hover(function(){
                        $(".out",this).stop().animate({'left':'150px'},200);
                        $(".over",this).stop().animate({'left':	'48px'},200);
                    },function(){
                            $(".out",this).stop().animate({'left':'48px'},200);
                            $(".over",this).stop().animate({'left':'-150px'},200);
                    });
                }
                
        });
        var showMenuIndex = 1;
        $("#menuDownUp").on("click", function(){
            if(showMenuIndex == 1){
                    mainMenu1.hide();
                    mainMenu2.show();
                    showMenuIndex = 2;
            }else {
                    mainMenu1.show();
                    mainMenu2.hide();
                    showMenuIndex = 1;
            }
        });      
        var menu_li = $(".menuUl li");
        function getTwoMenu(id,nodes){
            var menu_li = $(".menuUl li");
            
            menu_li.bind("mouseenter", function() {
                var seq = parseInt($(this).attr('class').split(' ')[0].replace('menuColor','').replace('Unselt',''));
                var index = $(this).index();
                menu_li.each(function() {
                    var i = parseInt($(this).attr('class').split(' ')[0].replace('menuColor','').replace('Unselt',''));
                    if ($(this).hasClass('menuColor' + i+'Selt')) {
                        $(this).removeClass('menuColor' + i+'Selt');
                        $(this).children('div').css('display', 'none');
                    }
                });
                $(this).addClass('menuColor' + seq+'Selt');
                var div = $(this).children('div');
                var userDivHeight = $("#userDiv").height();
                var height = div.height()+100;
                menuTwoMaxHeight = $('#mainMenuDiv').height();
                if(index <= 4){
                    if(height + userDivHeight + index*53 > menuTwoMaxHeight){
                            div.css('top', -(userDivHeight + index*53));
                    }else{
                            div.css('top', 0);
                    }
                }
                else{
                    if(height > 60 + (index + 1)*53){
                            div.css('bottom', -(menuTwoMaxHeight - 60 - (index + 1)*53 + 15 ));
                    }
                    else{
                            div.css('bottom', 0);
                    }
                }
                $(this).children('div').css('display', 'block');
            });
            $.ajax({
                    //url: '../webresources/normal/workspaceRest/getSecondMenu?parentId=' + id+"&s=" + Math.random(),
                    url: '../webresources/normal/PrivilegeResource/getSecondMenu?parentId=' + id+"&s=" + Math.random(),
                    data: $.toJSON(),
                    dataType: "json",
                    type: 'GET',
                    success: function(secondDate) {
                            var maxTextLength = 0;
                            if (secondDate.rows.length == 0) {
                                        
                                         $("#" + id+ "one").append(
                                                "<div class=\"menuTwoDiv menuTwoBottomLine\" style=\"z-index:9999;\"><span class=\"menuSpan\">" + nodes.text + "&nbsp;&nbsp;&gt;&nbsp;&nbsp;</span>\n" +
                                                "<div id=\"" + id + "two\" class=\" menuTwoMenu\">");
                                         if( nodes.text.length > maxTextLength){
                                                maxTextLength = nodes.text.length;
                                        }
                                        for (var n = 1; n < nodes.children.length+1; n++) {
                                            if(n< nodes.children.length){
                                                var item = $("<a id='" + nodes.children[n-1].id + "' paraname='"+nodes.children[n-1].text+"' paraurl='"+nodes.children[n-1].attributes.url+"' href='#'>" +  nodes.children[n-1].text + "&nbsp;&nbsp;|</a>");
                                                $("#" + nodes.id + "two").append(item);
                                                item.on('click', function() {  //href=\"javascript:void(0);\"onclick=\"funName(this.id)\"
                                                                openMenufunc(this.id);
                                                               //openMenu($(this).attr("paraname"),$(this).attr("paraurl"));
                                                           });
                                            }else if(n== nodes.children.length){
                                                var item = $("<a id='" + nodes.children[n-1].id + "' paraname='"+nodes.children[n-1].text+"' paraurl='"+nodes.children[n-1].attributes.url+"' href='#'>" +  nodes.children[n-1].text + "&nbsp;&nbsp;&nbsp;</a>");
                                                $("#" + nodes.id + "two").append(item);
                                                item.on('click', function() {  //href=\"javascript:void(0);\"onclick=\"funName(this.id)\"
                                                               openMenufunc(this.id);
                                                               //openMenu($(this).attr("paraname"),$(this).attr("paraurl"));
                                                           });
                                            }
                                        }
//                                                        
                            } else {
                                 for (var m = 1; m < nodes.children.length + 1; m++) {
                                        var secondParentId = nodes.children[m-1].id;
                                        if(m==nodes.children.length){
                                            $("#" + id + "one").append(
                                                    "<div class=\"menuTwoDiv menuTwoBottomLine\" style=\"z-index:9999;\"><span class=\"menuSpan\">" + nodes.children[m-1].text + "&nbsp;&nbsp;&gt;&nbsp;&nbsp;</span>\n" +
                                                    "<div id=\"" + secondParentId + "two\" class=\" menuTwoMenu\">");
                                        }else{
                                            $("#" + id + "one").append(
                                                    "<div class=\"menuTwoDiv\" style=\"z-index:9999;\"><span class=\"menuSpan\">" + nodes.children[m-1].text + "&nbsp;&nbsp;&gt;&nbsp;&nbsp;</span>\n" +
                                                    "<div id=\"" + secondParentId + "two\" class=\" menuTwoMenu\">");
                                        }
                                        if(nodes.children[m-1].text.length > maxTextLength){
                                                maxTextLength = nodes.children[m-1].text.length;
                                        }
                                        for (var j = 1; j <  nodes.children[m-1].children.length+1; j++) {
                                            if(j<nodes.children[m-1].children.length){
                                                var item = $("<a id='" +nodes.children[m-1].children[j-1].id + "' paraname='"+nodes.children[m-1].children[j-1].text+"' paraurl='"+nodes.children[m-1].children[j-1].attributes.url+"' href='#'>" + nodes.children[m-1].children[j-1].text + "&nbsp;&nbsp;|</a>");
                                                $("#" + secondParentId + "two").append(item);
                                                item.on('click', function() {  //href=\"javascript:void(0);\"onclick=\"funName(this.id)\"
                                                               openMenufunc(this.id);
                                                               //openMenu($(this).attr("paraname"),$(this).attr("paraurl"));
                                                           });
                                            }else if(j==nodes.children[m-1].children.length){
                                                var item = $("<a id='" + nodes.children[m-1].children[j-1].id + "' paraname='"+nodes.children[m-1].children[j-1].text+"' paraurl='"+nodes.children[m-1].children[j-1].attributes.url+"' href='#'>" + nodes.children[m-1].children[j-1].text + "&nbsp;&nbsp;&nbsp</a>");
                                                $("#" + secondParentId + "two").append(item);
                                                item.on('click', function() {  //href=\"javascript:void(0);\"onclick=\"funName(this.id)\"
                                                               openMenufunc(this.id);
                                                               //openMenu($(this).attr("paraname"),$(this).attr("paraurl"));
                                                           });
                                            }
                                        }
                                        $("#" + secondParentId + "").append("</div>");
                                               } 
                            };
                                $("#" + id + "one").find('.menuSpan').css('width', maxTextLength*13+36);
                                $("#" + id + "one").find('.menuTwoMenu').css('width', 770-10-maxTextLength*13-36);

                        },
                    error: function(XMLHttpRequest, textStatus, errorThrown) {
                               }
                    });
        }
        
        function mouseenterOut() {
            var menu_li = $(".menuUl li");
            menu_li.bind("mouseleave", function() {
                var index = $(this).index();
                var seq = parseInt($(this).attr('class').split(' ')[0].replace('menuColor','').replace('Unselt',''));
                menu_li.each(function() {
                    var i = parseInt($(this).attr('class').split(' ')[0].replace('menuColor','').replace('Unselt',''));
                               // var num=index+1+i;
                    if ($(this).hasClass('menuColor' + i+'Selt')) {
                        $(this).removeClass('menuColor' + i+'Selt');
                        $(this).children('div').css('display', 'none');
                    }
                });
            });
        }
        function  controlMenuColor() {     //鼠标移动控制颜色变化
            var menu_li = $(".menuUl li");
            menu_li.bind("mouseenter", function() {
                var index = $(this).index();
                var seq = parseInt($(this).attr('class').split(' ')[0].replace('menuColor','').replace('Unselt',''));
                menu_li.each(function() {
                    var i = parseInt($(this).attr('class').split(' ')[0].replace('menuColor','').replace('Unselt',''));
                    if ($(this).hasClass('menuColor' + i+'Selt')) {
                        $(this).removeClass('menuColor' + i+'Selt');
                        $(this).children('div').css('display', 'none');
                    }
                });
                $(this).addClass('menuColor' + seq+'Selt');
            });
        }

        //工作方法：二级菜单显示或隐藏
        var isMenuTwoShown = false;  //工作变量，二级菜单已显示
        function toggleMenuTwo(target, show) {
            if (!show) {
                show = !isMenuTwoShown;
                }
            if (show) {
                    $(target).children('div').css('display', 'block');
            } else {
                    $(target).children('div').css('display', 'none');
                }
            isMenuTwoShown = !isMenuTwoShown;
            }
        //单击打开系统功能并关闭浮动菜单 huxj 2016/07/22 
        function openMenu(name,url) {
            $(".menuTwo").css('display', 'none'); //关闭当前二级菜单
            openTab(name,url , false, null);
        }    
        //工作方法：单击打开系统功能并关闭浮动菜单
        //解决idev7 升级hd-idev后无法openTab by huxj 2016/09/27
        function openMenufunc(id) {
            $.ajax({
                    url: "../webresources/normal/PrivilegeResource/getNodeById?privilegeId="+id+"&s="+Math.random(),
                    data: $.toJSON(),
                    dataType: "json",
                    type: 'GET',
                    success: function(data) {
                      var funcNam = document.getElementById(id).text.substring(0, document.getElementById(id).text.length - 3);
                      //var node=data[0].children[0];
                      //var url=node.data.url;
                      var node = data[0];
                      var url = data[0].url;
                        try {
                            if (!debug){
                                window.onerror = killErrorPrompt;
                            }
                            openTab(funcNam, url, false, node);
                        } catch (e) {
                                //
                        }
                    },
                    error: function(XMLHttpRequest, textStatus, errorThrown) {
                    }
               });
            }
            
        //工作方法，关闭banner区
        function showOrHideHeader(val) {
            var header = $('#mainWindowTopbar');
            var logo = header.find('.logo');
            if (val == 'up') {
                header.panel('resize', {height: 1}); //可能panel代码限制，小于10时，height总是10
                logo.css('display', 'none');
                $('#mainTabsToolbar a[name="btnHideHead"] img').attr('src', '<%=basePath%>/css/themes/'+upDownTheme+'/images/tabIconFullDown.png');
            } else if (val == 'down') {
                header.panel('resize', {height: 55});
                logo.css('display', 'block');
                $('#mainTabsToolbar a[name="btnHideHead"] img').attr('src', '<%=basePath%>/css/themes/'+upDownTheme+'/images/tabIconFullUp.png');
            }
            $('#mainBody').layout('resize');
        }; //END  showOrHideHeader

        $.get('../webresources/normal/PrivilegeResource/getLoginAccount?t='+Math.random(), function(data) {
            $.main.loginOper = $.extend({}, data);
            <%-- 保存用于判断权限等，见$.main.isHaveRequiredRole()函数 --%>
        });
            
        //禁止后退键 ，防止用户按退格导致回到登录页，再回来，当前卡片也关掉了（相当于按了F5刷新）                
        $(document).keydown($.main.forbidBackSpace);
        $(document).keypress($.main.forbidBackSpace);
      

        function onChangeTheme(theme) {
            $('link').eq(0).attr('href', '../css/themes/' + theme + '/easyui.css');
            $('link').eq(1).attr('href', '../css/themes/' + theme + '/hdMain.css');
            $('link').eq(2).attr('href', '../css/themes/' + theme + '/hdStyle.css');
        }
     });            
                
    </script>

</html>
