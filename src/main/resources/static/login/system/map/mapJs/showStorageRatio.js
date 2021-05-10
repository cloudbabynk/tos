function showStorageRatio()
{
	modDolage=$("<div></div>").dialog({
		id:'modDolage',
		width:850,    
		height:550,
		modal:true,
		cache: true,
		collapsible:true,
		onClose:function(){
			$(this).dialog('destroy');
			modDolage=null;
		},
		title:"堆场统计查询",
		href: '/login/gisMap/search/showStorageRatio.html'
	});
}