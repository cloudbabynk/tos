<!-- written by fuxinqi/zhangyi on 2013-2-1, [called by /pages/framework/workspace/workspace.jsp] -->  
<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
 <%
       String path = request.getContextPath();
       String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
 %>
<script type="text/javascript" src="<%=basePath%>/flex-release/swfobject.js"></script>
<script type="text/javascript">
<%-- $(document).ready(function() { --%>
    <!-- For version detection, set to min. required Flash Player version, or 0 (or 0.0.0), for no version detection. --> 
    var swfVersionStr = "10.0.0";
    <!-- To use express install, set to playerProductInstall.swf, otherwise the empty string. -->
    var xiSwfUrlStr = "playerProductInstall.swf";
    var flashvars = {};
    var params = {};
    params.quality = "high";
    params.bgcolor = "#ffffff";
    params.allowscriptaccess = "sameDomain";
    params.allowfullscreen = "true";
    var attributes = {};
    attributes.id = "appMain";
    attributes.name = "appMain";
    attributes.align = "middle";
    swfobject.embedSWF(
	    "<%=basePath%>/pages/flex_loader/appMain.swf", "flashContent", 
	    "100%", "100%", 
	    swfVersionStr, xiSwfUrlStr, 
	    flashvars, params, attributes);
    <!-- JavaScript enabled so display the flashContent div in case it is not replaced with a swf object. -->
    swfobject.createCSS("#flashContent", "display:block;text-align:left;");
<%--}); --%>
</script>

<div id="flashContent">
    <p>
        To view this page ensure that Adobe Flash Player version 
        10.0.0 or greater is installed. 
    </p>
    <script type="text/javascript"> 
        var pageHost = ((document.location.protocol == "https:") ? "https://" :	"http://"); 
        document.write("<a href='http://www.adobe.com/go/getflashplayer'><img src='" 
            + pageHost + "www.adobe.com/images/shared/download_buttons/get_flash_player.gif' alt='Get Adobe Flash player' /></a>" ); 
    </script> 
</div>

<noscript>   
<object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" width="100%" height="100%" id="appMain" style="border:solid;" >
    <param name="movie" value="<%=basePath%>/pages/flex_loader/appMain.swf" />
    <param name="quality" value="high" />
    <param name="bgcolor" value="#ffffff" />
    <param name="allowScriptAccess" value="sameDomain" />
    <param name="allowFullScreen" value="true" />
    <!--[if !IE]>-->
    <object type="application/x-shockwave-flash" data="<%=basePath%>/pages/flex_loader/appMain.swf" width="100%" height="100%" id="appMain" style="border:solid;" >
        <param name="quality" value="high" />
        <param name="bgcolor" value="#ffffff" />
        <param name="allowScriptAccess" value="sameDomain" />
        <param name="allowFullScreen" value="true" />
        <!--<![endif]-->
        <!--[if gte IE 6]>-->
        <p> 
            Either scripts and active content are not permitted to run or Adobe Flash Player version
            10.0.0 or greater is not installed.
        </p>
        <!--<![endif]-->
        <a href="http://www.adobe.com/go/getflashplayer">
            <img src="http://www.adobe.com/images/shared/download_buttons/get_flash_player.gif" alt="Get Adobe Flash Player" />
        </a>
        <!--[if !IE]>-->
    </object>
    <!--<![endif]-->
</object>
</noscript>		

