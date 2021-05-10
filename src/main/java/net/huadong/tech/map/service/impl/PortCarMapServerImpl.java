package net.huadong.tech.map.service.impl;

import java.util.Map;
import org.springframework.stereotype.Component;
import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.map.service.PortCarMapServer;


/**
 * @author 
 */
@Component
public class PortCarMapServerImpl implements PortCarMapServer {



	@Override
	public HdMessageCode getPortCarLocal(Map cntrMap) throws Exception {
		// TODO Auto-generated method stub
		String sqlPam="";
		String billNo=cntrMap.get("billNo")+"";
		String bandCod=cntrMap.get("bandCod")+"";
		String carTyp=cntrMap.get("carTyp")+"";
		String carKind=cntrMap.get("carKind")+"";
		String shipNam=cntrMap.get("shipNam")+"";
		String inTime=cntrMap.get("inTime")+"";
		return null;
	}

}

