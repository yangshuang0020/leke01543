package org.tio.http.server.demo1.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.ChannelContext;
import org.tio.http.common.HttpPacket;
import org.tio.http.common.HttpSession;
import org.tio.http.common.http.HttpRequestPacket;
import org.tio.http.common.http.HttpResponsePacket;
import org.tio.http.server.HttpServerConfig;
import org.tio.http.server.annotation.RequestPath;
import org.tio.http.server.util.Resps;

/**
 * @author tanyaowu 
 * 2017年6月29日 下午7:53:59
 */
@RequestPath(value = "/osc")
public class OscController {
	private static Logger log = LoggerFactory.getLogger(OscController.class);

	/**
	 * 
	 * @author: tanyaowu
	 */
	public OscController() {
	}

	@RequestPath(value = "/cb")
	public HttpResponsePacket json(HttpRequestPacket httpRequestPacket, HttpServerConfig httpServerConfig, ChannelContext<HttpSession, HttpPacket, Object> channelContext)
			throws Exception {
		HttpResponsePacket ret = Resps.json(httpRequestPacket, "ok", httpServerConfig.getCharset());
		return ret;
	}

	/**
	 * @param args
	 * @author: tanyaowu
	 */
	public static void main(String[] args) {

	}
}
