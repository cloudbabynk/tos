package net.huadong.tech.map.controller;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.map.entity.CGisWbillCamera;
import net.huadong.tech.map.service.CGisWbillCameraService;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;
import net.huadong.tech.util.HdUtils;
/**
 * @author 
 */
@Controller
@RequestMapping("webresources/login/base/CGisWbillCamera")
public class CGisWbillCameraController  {
	
	@Autowired
	private CGisWbillCameraService cgiswbillcameraservice; 	
	/**
	 * 批量删除
	 */
	@RequestMapping(value = "/removeAll")
	@ResponseBody
	public HdMessageCode removeAll(String ids) {
		cgiswbillcameraservice.removeAll(ids);
		return HdUtils.genMsg();
	}

	/**
     * 通用列表查询
     *
     * @param params
     * @return
     */
	@RequestMapping(value = "/find")
	@ResponseBody
	public HdEzuiDatagridData find(@RequestBody HdQuery hdQuery) {
	    return cgiswbillcameraservice.find(hdQuery);
	}
	

	/**
     * 实体查询
     *
     * @param params
     * @return
     */
	@RequestMapping(value = "/findone")
	@ResponseBody
	public CGisWbillCamera findone(String id) {
		if (HdUtils.strIsNull(id)) {
			CGisWbillCamera cgiswbillcamera = new CGisWbillCamera();
			return cgiswbillcamera;
		}
		return cgiswbillcameraservice.findone(id);
	}
	/**
     * 保存资源信息
     *
     * @param hdEzuiSaveDatagridData
     * @return
     */
	@RequestMapping("/save")
	@ResponseBody	
	public HdMessageCode save(@RequestBody HdEzuiSaveDatagridData<CGisWbillCamera> gridData) {
	 	 //CommonUtil.initEntity(gridData);
		return cgiswbillcameraservice.save(gridData);
	}	
	/*
	 * form保存
	 */
	@RequestMapping("/saveone")
	@ResponseBody
	public HdMessageCode saveone(@RequestBody CGisWbillCamera cgiswbillcamera) {
		return cgiswbillcameraservice.saveone(cgiswbillcamera);
	}
	
	@RequestMapping("/sendtoCamera")
	@ResponseBody
	public static boolean sendtoCamera( String channels){
	        BufferedReader reader = null;
	        try {
	            URL url = new URL("http://10.130.4.10/v1/live_view/batch_action");// 创建连接
	            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	            connection.setDoOutput(true);
	            connection.setDoInput(true);
	            connection.setUseCaches(false);
	            connection.setInstanceFollowRedirects(true);
	            connection.setRequestMethod("POST"); // 设置请求方式
	            // connection.setRequestProperty("Accept", "application/json"); // 设置接收数据的格式
	            connection.setRequestProperty("Content-Type", "application/json"); // 设置发送数据的格式
	            connection.connect();
	            //一定要用BufferedReader 来接收响应， 使用字节来接收响应的方法是接收不到内容的
	            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8"); // utf-8编码
	           /* JSONObject jsonObj=new JSONObject();  
		        jsonObj.put("Channels",channels);*/
	            out.append(channels);
	            out.flush();
	            out.close();
	            // 读取响应
	            reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
	            String line;
	            String res = "";
	            while ((line = reader.readLine()) != null) {
	                res += line;
	            }
	            JSONObject backstr=JSONObject.fromObject(res);
	            if(backstr.get("Status")!=null&&backstr.get("Status").toString().equals("1")){
	            	return true;
	            }else{
	            	return false;
	            }
	        } catch (IOException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }finally{
	        	if(reader!=null){
	        		 try {
						reader.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	        	}
	        }
	        return false;
	   
	}
	
	// 构建唯一会话Id
	public static String getSessionId(){
	    UUID uuid = UUID.randomUUID();
	    String str = uuid.toString();
	    return str.substring(0, 8) + str.substring(9, 13) + str.substring(14, 18) + str.substring(19, 23) + str.substring(24);
	}

	
	/**
	 * 作业线规划--视频【新增】弹窗-左侧列表。
	 * @param hdQuery
	 * @return
	 */
	@RequestMapping(value = "/cGWCameraDialogDatagrid")
	@ResponseBody
	public HdEzuiDatagridData cGWCameraDialogDatagrid(@RequestBody HdQuery hdQuery) {
		
		 return cgiswbillcameraservice.findCamera(hdQuery);
	}
	 
	/**
	 * 作业线规划--视频【新增】弹窗-右侧列表。
	 * @param hdQuery
	 * @return
	 */
	@RequestMapping(value = "/cGWCameraDialogRightDatagrid")
	@ResponseBody
	public HdEzuiDatagridData cGWCameraDialogRightDatagrid(@RequestBody HdQuery hdQuery) {
		
		 return cgiswbillcameraservice.findCGWCamera(hdQuery);
	}
	 
	
	/**
	 * 作业线规划--视频【新增】弹窗-右侧列表-保存
	 * @param saveData
	 * @return
	 */
	@RequestMapping("/saveRightDialog")
	@ResponseBody	
	public HdMessageCode saveRightDialog(@RequestBody HdEzuiSaveDatagridData<Map<String,Object>> saveData) {
	 	 
		return cgiswbillcameraservice.saveRightDialog(saveData);
	}	
}
