
<meta charset="UTF-8">
<!-- 工具栏 -->
<div id="TongjiToolBar1487053926981"  align="center"  style="font-size:24px;">
	<table style="margin:0px auto;margin-top: 120px;" align="center" height="200px;" >
		<tr>
		<td>用户：</td>
		<td>1144041515</td>
		</tr>
		<tr></tr>
		<tr>
		<td>密码：</td>
		<td>******</td>
		</tr>
		<tr></tr>
		<tr>
		<td colspan="2">密保：你绑定的手机号？</td>
		</tr>
		<tr></tr>
		<tr>
		<td colspan="2">答案：18253595993</td>
		</tr>
	</table>
	
    <a class="easyui-linkbutton" iconCls="icon-href" plain="false" onclick="open_win()" style="margin-top:20px;"><span style=";font-size:24px;">跳转</span></a>
         
</div>
<!-- 脚本控制 -->	
<script type="text/javascript">
function open_win() 
{
	var tab = $('#main').tabs('getSelected');
	var index = $('#main').tabs('getTabIndex', tab);
    $('#main').tabs('close', index);
	$.main.openUrlInFrame("平台统计","http://tongji.baidu.com/web/welcome/login");
}
    $(document).ready(function () { 
    	
    });
</script>

