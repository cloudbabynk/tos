package net.huadong.tech.map.service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.entity.CCyArea;

/**
 * @author 
 */
public interface PortCarMapServer {

	
	 //在场定位
	public HdMessageCode getPortCarLocal(Map cntrMap) throws Exception;
	
}
