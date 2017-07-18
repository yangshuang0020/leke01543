package org.tio.http.server.demo1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.ChannelContext;
import org.tio.http.common.HttpPacket;
import org.tio.http.common.HttpSessionContext;
import org.tio.http.common.http.HttpRequestPacket;
import org.tio.http.common.http.HttpResponsePacket;
import org.tio.http.common.http.RequestLine;
import org.tio.http.server.HttpServerConfig;
import org.tio.http.server.handler.AbstractHttpRequestHandler;
import org.tio.http.server.mvc.Routes;
import org.tio.http.server.util.Resps;

/**
 * @author tanyaowu 
 * 2017年6月28日 下午5:32:38
 */
public class HttpRequestHandler extends AbstractHttpRequestHandler {

	private static Logger log = LoggerFactory.getLogger(HttpRequestHandler.class);

	public HttpRequestHandler(HttpServerConfig httpServerConfig) {
		super(httpServerConfig);
	}

	/**
	 * 
	 * @author: tanyaowu
	 */
	public HttpRequestHandler(HttpServerConfig httpServerConfig, Routes routes) {
		super(httpServerConfig, routes);
	}

	@Override
	public HttpResponsePacket resp404(HttpRequestPacket httpRequestPacket, RequestLine requestLine, ChannelContext<HttpSessionContext, HttpPacket, Object> channelContext) {
		HttpResponsePacket ret = Resps.redirect(httpRequestPacket, "/404.html?initpath=" + requestLine.getPathAndQuerystr());
		return ret;
	}

	@Override
	public HttpResponsePacket resp500(HttpRequestPacket httpRequestPacket, RequestLine requestLine, ChannelContext<HttpSessionContext, HttpPacket, Object> channelContext, Throwable throwable) {
		HttpResponsePacket ret = Resps.redirect(httpRequestPacket, "/500.html?initpath=" + requestLine.getPathAndQuerystr());
		return ret;
	}
}
