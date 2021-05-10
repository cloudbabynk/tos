package net.huadong.tech.util;

import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
//xml节点中包含下划线，对象转换XML时，会产生2个下划线的问题
//每个下划线都额外多了一个下划线。

//解决办法是，继承XmlFriendlyNameCoder类
public class XStreamNameCoder extends XmlFriendlyNameCoder  {
	   public XStreamNameCoder() {  
	        super("_-", "_");  
	    }  
}
