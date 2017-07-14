package org.tio.http.server.demo1.controller;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.ChannelContext;
import org.tio.http.common.HttpPacket;
import org.tio.http.common.HttpSessionContext;
import org.tio.http.common.http.HttpRequestPacket;
import org.tio.http.common.http.HttpResponsePacket;
import org.tio.http.server.HttpServerConfig;
import org.tio.http.server.annotation.RequestPath;
import org.tio.http.server.demo1.HttpServerDemo1Starter;
import org.tio.http.server.util.Resps;

import com.typesafe.config.ConfigFactory;

/**
 * @author tanyaowu 
 * 2017年6月29日 下午7:53:59
 */
@RequestPath(value = "/config")
public class ConfigController {
	private static Logger log = LoggerFactory.getLogger(ConfigController.class);
	
	String html = "<div style='position:relation;border-radius:10px;text-align:center;padding:10px;font-size:40pt;font-weight:bold;background-color:##e4eaf4;color:#2d8cf0;border:0px solid #2d8cf0; width:600px;height:400px;margin:auto;box-shadow: 1px 1px 50px #000;position: fixed;top:0;left:0;right:0;bottom:0;'>"
			+ "<a style='text-decoration:none' href='https://git.oschina.net/tywo45/t-io' target='_blank'>"
			+ "<div style='text-shadow: 8px 8px 8px #99e;'>hello tio httpserver</div>"
			+ "</a>"
			+ "</div>";
	
	String txt = html;

	/**
	 * 
	 * @author: tanyaowu
	 */
	public ConfigController() {
	}

	@RequestPath(value = "/update")
	public HttpResponsePacket json(HttpRequestPacket httpRequestPacket, HttpServerConfig httpServerConfig, ChannelContext<HttpSessionContext, HttpPacket, Object> channelContext)
			throws Exception {
		HttpServerDemo1Starter.conf = ConfigFactory.load("app.conf");
		
		HttpResponsePacket ret = Resps.json(httpRequestPacket, "更新成功", httpServerConfig.getCharset());
		return ret;
	}

	@RequestPath(value = "/txt")
	public HttpResponsePacket txt(HttpRequestPacket httpRequestPacket, HttpServerConfig httpServerConfig, ChannelContext<HttpSessionContext, HttpPacket, Object> channelContext)
			throws Exception {
		HttpResponsePacket ret = Resps.txt(httpRequestPacket, txt, httpServerConfig.getCharset());
		return ret;
	}

	@RequestPath(value = "/html")
	public HttpResponsePacket html(HttpRequestPacket httpRequestPacket, HttpServerConfig httpServerConfig, ChannelContext<HttpSessionContext, HttpPacket, Object> channelContext)
			throws Exception {
		HttpResponsePacket ret = Resps.html(httpRequestPacket,
				html,
				httpServerConfig.getCharset());
		return ret;
	}

	@RequestPath(value = "/abtest")
	public HttpResponsePacket abtest(HttpRequestPacket httpRequestPacket, HttpServerConfig httpServerConfig, ChannelContext<HttpSessionContext, HttpPacket, Object> channelContext)
			throws Exception {
		HttpResponsePacket ret = Resps.html(httpRequestPacket, "OK", httpServerConfig.getCharset());
		return ret;
	}

	/**
	 * 测试映射重复
	 */
	@RequestPath(value = "/abtest")
	public HttpResponsePacket abtest1(HttpRequestPacket httpRequestPacket, HttpServerConfig httpServerConfig, ChannelContext<HttpSessionContext, HttpPacket, Object> channelContext)
			throws Exception {
		log.info("");
		HttpResponsePacket ret = Resps.html(httpRequestPacket, "OK---------1", httpServerConfig.getCharset());
		return ret;
	}

	@RequestPath(value = "/filetest")
	public HttpResponsePacket filetest(HttpRequestPacket httpRequestPacket, HttpServerConfig httpServerConfig,
			ChannelContext<HttpSessionContext, HttpPacket, Object> channelContext) throws Exception {
		HttpResponsePacket ret = Resps.file(httpRequestPacket, new File("d:/迷你pdf阅读器.exe"));
		return ret;
	}

	@RequestPath(value = "/filetest.zip")
	public HttpResponsePacket filetest_zip(HttpRequestPacket httpRequestPacket, HttpServerConfig httpServerConfig,
			ChannelContext<HttpSessionContext, HttpPacket, Object> channelContext) throws Exception {
		HttpResponsePacket ret = Resps.file(httpRequestPacket, new File("d:/eclipse-jee-neon-R-win32-x86_64.zip"));
		return ret;
	}

	/**
	 * @param args
	 * @author: tanyaowu
	 */
	public static void main(String[] args) {

	}
}
