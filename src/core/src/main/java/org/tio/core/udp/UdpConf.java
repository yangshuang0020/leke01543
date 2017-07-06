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
}
