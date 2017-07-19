package org.tio.http.server.demo1.init;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.utils.SystemTimer;
import org.tio.http.server.HttpServerConfig;
import org.tio.http.server.HttpServerStarter;
import org.tio.http.server.demo1.AppStarter;
import org.tio.http.server.handler.DefaultHttpRequestHandler;
import org.tio.http.server.handler.IHttpRequestHandler;
import org.tio.http.server.mvc.Routes;

/**
 * @author tanyaowu 
 * 2017年7月19日 下午4:59:04
 */
public class HttpServerInit {
	private static Logger log = LoggerFactory.getLogger(HttpServerInit.class);

	public static HttpServerConfig httpServerConfig;

	public static IHttpRequestHandler httpRequestHandler;

	public static HttpServerStarter httpServerStarter;

	/**
	 * 
	 * @author: tanyaowu
	 */
	public HttpServerInit() {
	}

	public static void init() throws Exception {
		long start = SystemTimer.currentTimeMillis();

		int port = AppStarter.conf.getInt("http.port");
		String pageRoot = AppStarter.conf.getString("page.root");

		httpServerConfig = new HttpServerConfig(port);
		httpServerConfig.setRoot(pageRoot);

		String[] scanPackages = new String[] { AppStarter.class.getPackage().getName() };
		Routes routes = new Routes(scanPackages);
		httpRequestHandler = new DefaultHttpRequestHandler(httpServerConfig, routes);

		httpServerStarter = new HttpServerStarter();
		httpServerStarter.start(httpServerConfig, httpRequestHandler);

		long end = SystemTimer.currentTimeMillis();
		long iv = end - start;
		log.info("Http Server启动完毕,耗时:{}ms,访问地址:http://127.0.0.1:{}", iv, port);
	}

	/**
	 * @param args
	 * @author: tanyaowu
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
	}
}
