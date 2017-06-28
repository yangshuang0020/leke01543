package org.tio.http.demo.server1;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.http.server.HttpServerConfig;
import org.tio.http.server.HttpServerStarter;

/**
 * @author tanyaowu 
 * 2017年6月28日 下午5:34:04
 */
public class HttpServerDemo1Starter {
	private static Logger log = LoggerFactory.getLogger(HttpServerDemo1Starter.class);

	/**
	 * 
	 * @author: tanyaowu
	 */
	public HttpServerDemo1Starter() {
	}

	/**
	 * @param args
	 * @author: tanyaowu
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		
		HttpRequestHandler httpRequestHandler = new HttpRequestHandler();
		int port = 9527;
		HttpServerConfig httpServerConfig = new HttpServerConfig(httpRequestHandler, port);
		httpServerConfig.setRoot("classpath:page");
		
		httpRequestHandler.setHttpServerConfig(httpServerConfig);
		
		
		HttpServerStarter httpServerStarter = new HttpServerStarter();
		httpServerStarter.start(httpServerConfig);

	}
}
