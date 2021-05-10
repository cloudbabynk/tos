package net.huadong.tech.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HostCleanFilter implements Filter{
	 
	private static StringBuffer whiteStr = new StringBuffer();
	static{
		try {
            // 读取白名单列表
			//白名单文件serverWhiteList.txt放在tomcat工程目录的classes下
			String path=HostCleanFilter.class.getResource("/").getPath();
			File serverWhiteListFile = new File(path+"serverWhiteList.txt");
			if(serverWhiteListFile.exists()){
				InputStreamReader inStream = new InputStreamReader(HostCleanFilter.class.getResourceAsStream("/serverWhiteList.txt"));// 文件的存放路径
				int c=0;
				while((c=inStream.read())!=-1){
					whiteStr.append((char)c);
				}
				inStream.close();
			}
			System.out.println("白名单:"+whiteStr.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}
 
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		 HttpServletRequest Hrequest = (HttpServletRequest)request;
	     HttpServletResponse Hresponse = (HttpServletResponse) response;
	     //http host头攻击检测,若HostHeader不在白名单中 则无法访问
	     String requestHost=Hrequest.getHeader("Host");
	     if(requestHost !=null && !whiteStr.toString().contains("'"+requestHost+"'")){
	    	 Hresponse.setStatus(403);
	    	 Hresponse.sendError(403,"访问地址不在白名单中，无法访问！");
	    	 return;
	     }
	      
         chain.doFilter(Hrequest, Hresponse);
	}
 
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	
	
}
