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
import org.tio.http.server.demo1.model.Donate;
import org.tio.http.server.demo1.service.DonateService;
import org.tio.http.server.util.Resps;
import org.tio.json.Json;

import com.jfinal.plugin.activerecord.Page;

/**
 * @author tanyaowu 
 * 2017年7月22日 上午10:44:13
 */
@RequestPath(value = "/donate")
public class DonateController {
	private static Logger log = LoggerFactory.getLogger(DonateController.class);

	static final DonateService srv = DonateService.me;

	/**
	 * 
	 * @author: tanyaowu
	 */
	public DonateController() {
	}

	@RequestPath(value = "/page")
	public HttpResponsePacket page(Integer pageNumber, Integer pageSize, HttpRequestPacket httpRequestPacket, HttpServerConfig httpServerConfig,
			ChannelContext<HttpSession, HttpPacket, Object> channelContext) throws Exception {
		Page<Donate> page = srv.page(pageNumber, pageSize);
		HttpResponsePacket ret = Resps.json(httpRequestPacket, Json.toJson(page), httpServerConfig.getCharset());
		ret.addHeader("Access-Control-Allow-Origin", "*");
		ret.addHeader("Access-Control-Allow-Headers", "x-requested-with,content-type");
		return ret;
	}

	/**
	 * @param args
	 * @author: tanyaowu
	 */
	public static void main(String[] args) {

	}
}
