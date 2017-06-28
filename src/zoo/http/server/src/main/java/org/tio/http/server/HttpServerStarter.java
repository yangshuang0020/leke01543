package org.tio.http.server;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.http.common.HttpPacket;
import org.tio.http.common.HttpUuid;
import org.tio.http.common.HttpSessionContext;
import org.tio.server.AioServer;
import org.tio.server.ServerGroupContext;

/**
 * 
 * @author tanyaowu
 */
public class HttpServerStarter {
	@SuppressWarnings("unused")
	private static Logger log = LoggerFactory.getLogger(HttpServerStarter.class);

//	public static Config conf = ConfigFactory.load("app.conf");

	/**
	 * 
	 *
	 * @author: tanyaowu
	 * 2016年11月17日 下午5:59:24
	 * 
	 */
	public HttpServerStarter() {

	}
	private HttpServerConfig httpServerConfig = null;
	
	private HttpServerAioHandler httpServerAioHandler = null;
	
	private HttpServerAioListener httpServerAioListener = null;
	
	private HttpGroupListener httpGroupListener = null;
	
	private ServerGroupContext<HttpSessionContext, HttpPacket, Object> serverGroupContext = null;
	
	private AioServer<HttpSessionContext, HttpPacket, Object> aioServer = null;
	


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
	
	public void start(HttpServerConfig httpServerConfig) throws IOException {
		this.httpServerConfig = httpServerConfig;
		httpServerAioHandler = new HttpServerAioHandler(httpServerConfig);
		httpServerAioListener = new HttpServerAioListener();
		httpGroupListener = new HttpGroupListener();
		serverGroupContext = new ServerGroupContext<>(httpServerAioHandler, httpServerAioListener);
		aioServer = new AioServer<>(serverGroupContext);
		
		HttpUuid imTioUuid = new HttpUuid();
		serverGroupContext.setTioUuid(imTioUuid);
		
		serverGroupContext.setGroupListener(httpGroupListener);
		aioServer.start(httpServerConfig.getBindIp(), httpServerConfig.getBindPort());
	}

	/**
	 * @return the httpServerAioHandler
	 */
	public HttpServerAioHandler getHttpServerAioHandler() {
		return httpServerAioHandler;
	}

	/**
	 * @return the httpServerAioListener
	 */
	public HttpServerAioListener getHttpServerAioListener() {
		return httpServerAioListener;
	}

	/**
	 * @return the httpGroupListener
	 */
	public HttpGroupListener getHttpGroupListener() {
		return httpGroupListener;
	}

	/**
	 * @return the serverGroupContext
	 */
	public ServerGroupContext<HttpSessionContext, HttpPacket, Object> getServerGroupContext() {
		return serverGroupContext;
	}

	/**
	 * @return the httpServerConfig
	 */
	public HttpServerConfig getHttpServerConfig() {
		return httpServerConfig;
	}


}
