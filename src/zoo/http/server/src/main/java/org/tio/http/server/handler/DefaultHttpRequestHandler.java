package org.tio.http.server.handler;

import java.io.File;
import java.lang.reflect.Method;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.ChannelContext;
import org.tio.http.common.HttpPacket;
import org.tio.http.common.HttpSessionContext;
import org.tio.http.common.http.HttpRequestPacket;
import org.tio.http.common.http.HttpResponsePacket;
import org.tio.http.common.http.RequestLine;
import org.tio.http.server.HttpServerConfig;
import org.tio.http.server.mvc.Routes;
import org.tio.http.server.util.Resps;

/**
 * 
 * @author tanyaowu 
 *
 */
public class DefaultHttpRequestHandler implements IHttpRequestHandler {
	private static Logger log = LoggerFactory.getLogger(DefaultHttpRequestHandler.class);

	protected HttpServerConfig httpServerConfig;

	protected Routes routes = null;

	public DefaultHttpRequestHandler() {
		//默认构造器;
	}

	@Override
	public HttpResponsePacket handler(HttpRequestPacket httpRequestPacket, RequestLine requestLine, ChannelContext<HttpSessionContext, HttpPacket, Object> channelContext)
			throws Exception {
		try {
			String path = requestLine.getPath();
			if (StringUtils.endsWith(path, "/")) {
				path = path + "index.html";
			}

			Method method = routes.pathMethodMap.get(path);
			if (method != null) {
				//String[] paramnames = methodParamnameMap.get(method);
				Object bean = routes.methodBeanMap.get(method);

				Object obj = method.invoke(bean, httpRequestPacket, httpServerConfig, channelContext);

				if (obj instanceof HttpResponsePacket) {
					return (HttpResponsePacket) obj;
				} else {
					//					log.error(bean.getClass().getName() + "#"+method.getName()+"返回的对象不是" + HttpResponsePacket.class.getName());
					throw new Exception(bean.getClass().getName() + "#" + method.getName() + "返回的对象不是" + HttpResponsePacket.class.getName());
				}
			} else {
				String root = httpServerConfig.getRoot();
				File file = new File(root, path);
				if (file.exists()) {
					HttpResponsePacket ret = Resps.file(httpRequestPacket, file);
					return ret;
				}
			}

			HttpResponsePacket ret = resp404(httpRequestPacket, requestLine, channelContext);//Resps.html(httpRequestPacket, "404--并没有找到你想要的内容", httpServerConfig.getCharset());
			return ret;
		} catch (Exception e) {
			String errorlog = "";//"error occured,\r\n";
			errorlog += requestLine.getInitStr();// + "\r\n";
//			errorlog += e.toString();
			log.error(errorlog, e);
			HttpResponsePacket ret = resp500(httpRequestPacket, requestLine, channelContext, e);//Resps.html(httpRequestPacket, "500--服务器出了点故障", httpServerConfig.getCharset());
			return ret;
		}
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

	/**
	 * 
	 *
	 * @author: tanyaowu
	 * 2016年11月18日 上午9:13:15
	 * 
	 */
	public DefaultHttpRequestHandler(HttpServerConfig httpServerConfig) {
		this.httpServerConfig = httpServerConfig;
	}

	public DefaultHttpRequestHandler(HttpServerConfig httpServerConfig, Routes routes) {
		this(httpServerConfig);
		this.routes = routes;
	}

	/**
	 * @param args
	 *
	 * @author: tanyaowu
	 * 2016年11月18日 上午9:13:15
	 * 
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
