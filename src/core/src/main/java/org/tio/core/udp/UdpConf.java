package org.tio.core.udp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.Node;

/**
 * @author tanyaowu 
 * 2017年7月5日 下午2:53:38
 */
public class UdpConf {
	private static Logger log = LoggerFactory.getLogger(UdpConf.class);
	
	
	
	private Node serverNode = null;
	
	private String charset = "utf-8";

	/**
	 * 
	 * @author: tanyaowu
	 */
	public UdpConf() {
	}

	/**
	 * @param args
	 * @author: tanyaowu
	 */
	public static void main(String[] args) {

	}



	public Node getServerNode() {
		return serverNode;
	}

	public void setServerNode(Node serverNode) {
		this.serverNode = serverNode;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}
}
