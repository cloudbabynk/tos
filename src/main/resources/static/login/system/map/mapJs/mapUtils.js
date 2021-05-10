
function MapHdUtils() {
}
/**
 * @namespace 全局变量
 * @memberOf MapHdUtils#
 */
MapHdUtils.global = {};
/**
 * 通用配置属性
 */
MapHdUtils.global.config={};
//地图地址：
//MapHdUtils.global.config.geoIp ="http://127.0.0.1:8080/geoserver"; 

//地图地址：
MapHdUtils.global.config.geoIp=function ()
{
	var hostUrl=window.location.hostname;
	var pamUrl=hostUrl.split(".");

//	return "http://127.0.0.1:7878/geoserver"; //地图		
	return "http://10.163.204.56:7878/geoserver"; //地图		
  
}

