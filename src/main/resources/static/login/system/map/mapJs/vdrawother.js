 var newStackNo=null;
 var oldStackNo=null;
	 
	 function drawStoreZD()
		{
			drawCallFunc="actSaveStoreTZD";
			$("#corgoinfodialog").dialog('close');
			beginDraw('Polygon');
		}
		
		function actSaveStoreTZD(posInfo,areaInfo){
			if(posInfo&&posInfo.length>0)
			{
				var pam={};
				
				var arrayArea=posInfo[0];
				var aeraStr="";
				for(var i=0;i<arrayArea.length;i++)
				{
					var pos=arrayArea[i];
					if(i==0)
						aeraStr+=pos[0]+","+pos[1];
					else
						aeraStr+="|"+pos[0]+","+pos[1];
				}
				pam.strLoc=aeraStr;	
				pam.id= newStackNo.substring(0,newStackNo.indexOf("@"));
				pam.stack_no=newStackNo;
				pam.graphType="2";
				//pam.GATHER_NO=selectStore.GATHER_NO;
				//pam.SPLIT_NO=selectStore.SPLIT_NO;
				//pam.INFORM_NO=selectStore.INFORM_NO;

				var url=path+"/area/addPositionInit.action";
				$.ajax({
					type:"POST",
					url:url,
					data:pam,
					success : function(datas) {
						//$.messager.alert('提示',datas,"info");
						drapDrawLayer();	
						changeCargoStack(oldStackNo);	
						changeCargoStack(newStackNo);	
					},
					faild:function (result) {  
						 $.messager.alert('提示','失败','info');
					}
				});
			}
		}