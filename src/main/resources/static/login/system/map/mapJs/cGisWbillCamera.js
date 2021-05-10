$(function(){
     // 配船窗口
     $('#gdWorkBillCameraToolBar a[iconCls="icon-add"]').on('click', function () {
         let gdWorkBillSelectRow = $("#gdWorkBill").datagrid('getSelected');
         if (gdWorkBillSelectRow) {

             HdUtils.dialog.show({ height: 500, width: 730, title: '配置', href: "/login/system/map/cGWCameraDialog.html", isSaveHandler: true });
             HdUtils.dialog.setValue({
                 data: {
                     "wbId":gdWorkBillSelectRow.wbId
                },
                 dataGrid: $("#shipSendWorkDatagrid")
             });
         }
    });
})