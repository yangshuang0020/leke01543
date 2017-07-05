package org.tio.websocket.server.demo1;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.websocket.server.WsServerConfig;
import org.tio.websocket.server.WsServerStarter;

/**
 * @author tanyaowu 
 * 2017年6月28日 下午5:34:04
 */
public class WsServerDemo1Starter {
	private static Logger log = LoggerFactory.getLogger(WsServerDemo1Starter.class);

	/**
	 * 
	 * @author: tanyaowu
	 */
	public WsServerDemo1Starter() {
	}

	/**
	 * @param args
	 * @author: tanyaowu
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		int port = 9321;
		
		WsServerConfig wsServerConfig = new WsServerConfig(port);
		
		String[] scanPackages = new String[]{WsServerDemo1Starter.class.getPackage().getName()};
		WsRequestHandler wsRequestHandler = new WsRequestHandler(wsServerConfig, scanPackages);
		
		WsServerStarter wsServerStarter = new WsServerStarter();
		wsServerStarter.start(wsServerConfig, wsRequestHandler);
	}
}
