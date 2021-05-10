package net.huadong.tech.thirdparty.service;

/**
 * 第三方接口服务。
 * Created by jason on 3/25/17.
 */
public interface PartnerService {

    // 获取数据命令。
    Result executeCommand(Command command);
    //接受第三方数据并且入库操作
    Result accpteCommand(RequestData command);
    
    //rest 风格数据上传，目前用于码头数据上传
    Result dataInterfaceRestByDataReceive(RequestData command);
    //船舶调度菜单接口，通过userId，返回用户代码和用户类型
	Result menuClickInterface(String params);
}
