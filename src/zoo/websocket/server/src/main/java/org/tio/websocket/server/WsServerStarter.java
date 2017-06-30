package org.tio.websocket.server;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.server.AioServer;
import org.tio.server.ServerGroupContext;
import org.tio.websocket.common.WsPacket;
import org.tio.websocket.common.WsSessionContext;
import org.tio.websocket.common.WsTioUuid;
import org.tio.websocket.server.handler.IWsRequestHandler;

/**
 * 
 * @author tanyaowu
 */
public class WsServerStarter {
	@SuppressWarnings("unused")
	private static Logger log = LoggerFactory.getLogger(WsServerStarter.class);

//	public static Config conf = ConfigFactory.load("app.conf");

	/**
	 * 
	 *
	 * @author: tanyaowu
	 * 2016年11月17日 下午5:59:24
	 * 
	 */
	public WsServerStarter() {

	}
	private WsServerConfig wsServerConfig = null;
	
	private IWsRequestHandler wsRequestHandler = null;
	
	private WsServerAioHandler wsServerAioHandler = null;
	
	private WsServerAioListener wsServerAioListener = null;
	
//	private HttpGroupListener wsGroupListener = null;
	
	private ServerGroupContext<WsSessionContext, WsPacket, Object> serverGroupContext = null;
	
	private AioServer<WsSessionContext, WsPacket, Object> aioServer = null;
	


	/**
	 * @param args
	 *
	 * @author: tanyaowu
	 * @throws IOException 
	 * 2016年11月17日 下午5:59:24
	 * 
	 */
	public static void main(String[] args) throws IOException {
	
//		HttpServerStarter httpServerStarter = new HttpServerStarter();
//		HttpServerConfig httpServerConfig = new HttpServerConfig();
//		httpServerStarter.start(httpServerConfig);
		
	}
	
	public void start(WsServerConfig wsServerConfig, IWsRequestHandler wsRequestHandler) throws IOException {
		this.wsServerConfig = wsServerConfig;
		this.wsRequestHandler = wsRequestHandler;
		wsServerAioHandler = new WsServerAioHandler(wsServerConfig, wsRequestHandler);
		wsServerAioListener = new WsServerAioListener();
//		wsGroupListener = new HttpGroupListener();
		serverGroupContext = new ServerGroupContext<>(wsServerAioHandler, wsServerAioListener);
		serverGroupContext.setHeartbeatTimeout(1000 * 120);
		
		aioServer = new AioServer<>(serverGroupContext);
		
		WsTioUuid imTioUuid = new WsTioUuid();
		serverGroupContext.setTioUuid(imTioUuid);
		
//		serverGroupContext.setGroupListener(wsGroupListener);
		aioServer.start(wsServerConfig.getBindIp(), wsServerConfig.getBindPort());
	}

	/**
	 * @return the wsServerAioHandler
	 */
	public WsServerAioHandler getHttpServerAioHandler() {
		return wsServerAioHandler;
	}

	/**
	 * @return the wsServerAioListener
	 */
	public WsServerAioListener getHttpServerAioListener() {
		return wsServerAioListener;
	}

	/**
	 * @return the wsGroupListener
	 */
//	public HttpGroupListener getHttpGroupListener() {
//		return wsGroupListener;
//	}

	/**
	 * @return the serverGroupContext
	 */
	public ServerGroupContext<WsSessionContext, WsPacket, Object> getServerGroupContext() {
		return serverGroupContext;
	}

	/**
	 * @return the wsServerConfig
	 */
	public WsServerConfig getHttpServerConfig() {
		return wsServerConfig;
	}

	/**
	 * @return the wsRequestHandler
	 */
	public IWsRequestHandler getHttpRequestHandler() {
		return wsRequestHandler;
	}
}
