package org.tio.http.server.demo1;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.utils.SystemTimer;
import org.tio.http.server.HttpServerConfig;
import org.tio.http.server.HttpServerStarter;
import org.tio.http.server.mvc.Routes;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

/**
 * ab -c 10 -n 200000 -k http://127.0.0.1:9527/test/abtest
 * ab -c 20 -n 200000 -k http://127.0.0.1:9527/test/abtest
 * ab -c 40 -n 200000 -k http://127.0.0.1:9527/test/abtest
 * ab -c 60 -n 200000 -k http://127.0.0.1:9527/test/abtest
 * ab -c 80 -n 200000 -k http://127.0.0.1:9527/test/abtest
 * ab -c 100 -n 200000 -k http://127.0.0.1:9527/test/abtest
 * ab -c 200 -n 200000 -k http://127.0.0.1:9527/test/abtest
 * ab -c 300 -n 200000 -k http://127.0.0.1:9527/test/abtest
 * ab -c 400 -n 200000 -k http://127.0.0.1:9527/test/abtest
 * @author tanyaowu 
 * 2017年6月28日 下午5:34:04
 */
public class HttpServerDemo1Starter {
	private static Logger log = LoggerFactory.getLogger(HttpServerDemo1Starter.class);
	
	public static Config conf = ConfigFactory.load("app.conf");
	
	public static HttpServerConfig httpServerConfig;
	
	public static HttpRequestHandler httpRequestHandler;

	public static HttpServerStarter httpServerStarter;

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
		long start = SystemTimer.currentTimeMillis();
		
		int port = conf.getInt("http.port");
		String pageRoot = conf.getString("page.root");
		
		httpServerConfig = new HttpServerConfig(port);
		httpServerConfig.setRoot(pageRoot);
		
		String[] scanPackages = new String[]{HttpServerDemo1Starter.class.getPackage().getName()};
		Routes routes = new Routes(scanPackages);
		httpRequestHandler = new HttpRequestHandler(httpServerConfig, routes);
		
		httpServerStarter = new HttpServerStarter();
		httpServerStarter.start(httpServerConfig, httpRequestHandler);
		
		long end = SystemTimer.currentTimeMillis();
		long iv = end - start;
		log.info("系统启动完毕,耗时:{}ms,您现在可以访问:http://127.0.0.1:{}", iv, port);
		
	}
}
