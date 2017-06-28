package org.tio.http.demo.server1;

import java.io.File;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.ChannelContext;
import org.tio.http.common.HttpPacket;
import org.tio.http.common.HttpSessionContext;
import org.tio.http.common.http.HttpRequestPacket;
import org.tio.http.common.http.HttpResponsePacket;
import org.tio.http.common.http.HttpResponseStatus;
import org.tio.http.common.http.RequestLine;
import org.tio.http.server.HttpServerConfig;
import org.tio.http.server.handler.IHttpRequestHandler;

/**
 * @author tanyaowu 
 * 2017年6月28日 下午5:32:38
 */
public class HttpRequestHandler implements IHttpRequestHandler {
	private static Logger log = LoggerFactory.getLogger(HttpRequestHandler.class);

	private HttpServerConfig httpServerConfig = null;

	/**
	 * 
	 * @author: tanyaowu
	 */
	public HttpRequestHandler(HttpServerConfig httpServerConfig) {
		this.setHttpServerConfig(httpServerConfig);
	}

	/**
	 * 
	 * @author: tanyaowu
	 */
	public HttpRequestHandler() {
	}

	/** 
	 * @param packet
	 * @param requestLine
	 * @param channelContext
	 * @return
	 * @throws Exception
	 * @author: tanyaowu
	 */
	@Override
	public HttpResponsePacket handler(HttpRequestPacket packet, RequestLine requestLine, ChannelContext<HttpSessionContext, HttpPacket, Object> channelContext) throws Exception {
		String url = requestLine.getRequestUrl();
		if (StringUtils.endsWith(url, "/")) {
			url = url + "index.html";
		}

		File file = new File(httpServerConfig.getRoot(), url);
		if (file.exists()) {
			HttpResponsePacket ret = HttpResponsePacket.createFile(file);
//			ret.gzip(packet);
			return ret;
		}

		HttpResponsePacket ret = HttpResponsePacket.createHtml("404--并没有找到你想要的内容", httpServerConfig.getCharset());
		ret.setStatus(HttpResponseStatus.C404);
		return ret;
	}

	/**
	 * @param args
	 * @author: tanyaowu
	 */
	public static void main(String[] args) {

	}

	/**
	 * @return the httpServerConfig
	 */
	public HttpServerConfig getHttpServerConfig() {
		return httpServerConfig;
	}

	/**
	 * @param httpServerConfig the httpServerConfig to set
	 */
	public void setHttpServerConfig(HttpServerConfig httpServerConfig) {
		this.httpServerConfig = httpServerConfig;
	}
}
