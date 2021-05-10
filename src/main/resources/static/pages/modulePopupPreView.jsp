<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String moduleTitle = java.net.URLDecoder.decode(""+request.getParameter("moduleTitle"),"UTF-8");
String moduleUrl =  java.net.URLDecoder.decode(""+request.getParameter("moduleUrl"),"UTF-8");
String mainJson =  ""+request.getParameter("mainJson");
mainJson=java.net.URLDecoder.decode(mainJson,"UTF-8");

%>

<html>
   <head>
        <title><%=moduleTitle%></title>
          
<%--  FXQ 2015-3-5 使用JSP包含全部的Js和css定义  --%>   

    <script type="text/javascript">
    
    $(document).ready(function() {
    	
        var openerMain=$.evalJSON('<%=mainJson%>');  <%--  FXQ 2015-3-6 Java解析后的JSON串是双引号！  --%>   
    	console.log(openerMain);
    	$.extend(true,$.main,openerMain);  <%-- 将程序主页面的$.main对象赋值过来，防止程序执行异常。但这样仍有潜在问题，本模块不能修改$.main否则无法回传 --%>
    	
        $("#moduleUIPreview").load("<%=basePath%>login/<%=moduleUrl%>",{random:$.hd.commonUtils.getRandomId()},function (){  //加载结束后的回调
            $.parser.parse("#moduleUIPreview");
        });
        
    });
    
    </script>  
    
     <%-- 防止module代码中因为引用 #main而异常 --%>  
 <body class="easyui-layout" id="mainBody" data-options="border:false,fit:true" >   
        <div id="main" class="easyui-tabs" data-options="fit:true">
            <div id="moduleUIPreview" data-options="title:'<%=moduleTitle%>',border:false,closable:false,fit:true,href:'welcome.htm'">
                <!--  <div id="moduleUIPreview"/>      -->  
            </div>   
       </div>  
</body>
</html>