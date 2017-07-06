package org.tio.core.udp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.Node;

/**
 * @author tanyaowu 
 * 2017年7月5日 下午3:53:20
 */
public class UdpClientConf extends UdpConf {
	private static Logger log = LoggerFactory.getLogger(UdpClientConf.class);

	private int timeout = 5000;
	
	/**
	 * 
	 * @author: tanyaowu
	 */
	public UdpClientConf(String serverip, int serverport, int timeout) {
		Node node = new Node(serverip, serverport);
		this.setServerNode(node);
		this.timeout = timeout;
	}

	/**
	 * @param args
	 * @author: tanyaowu
	 */
	public static void main(String[] args) {

	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}
}
