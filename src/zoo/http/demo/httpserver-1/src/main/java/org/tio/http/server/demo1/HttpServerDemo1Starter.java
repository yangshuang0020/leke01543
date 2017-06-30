package org.tio.http.server.demo1;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.http.server.HttpServerConfig;
import org.tio.http.server.HttpServerStarter;

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
		int port = 9527;
		
		HttpServerConfig httpServerConfig = new HttpServerConfig(port);
		httpServerConfig.setRoot("classpath:page");
		
		String[] scanPackages = new String[]{HttpServerDemo1Starter.class.getPackage().getName()};
		HttpRequestHandler httpRequestHandler = new HttpRequestHandler(httpServerConfig, scanPackages);
		
		HttpServerStarter httpServerStarter = new HttpServerStarter();
		httpServerStarter.start(httpServerConfig, httpRequestHandler);
	}
}
