package org.tio.http.server.demo1;

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
import org.tio.http.common.http.HttpResponseStatus;
import org.tio.http.common.http.RequestLine;
import org.tio.http.server.HttpServerConfig;
import org.tio.http.server.handler.AbstractHttpServerAioHandler;
import org.tio.http.server.mvc.Routes;
import org.tio.http.server.util.Resps;

/**
 * @author tanyaowu 
 * 2017年6月28日 下午5:32:38
 */
public class HttpRequestHandler extends AbstractHttpServerAioHandler{
	
	private static Logger log = LoggerFactory.getLogger(HttpRequestHandler.class);


	public HttpRequestHandler(HttpServerConfig httpServerConfig){
		super(httpServerConfig);
	}
	/**
	 * 
	 * @author: tanyaowu
	 */
	public HttpRequestHandler(HttpServerConfig httpServerConfig, Routes routes) {
		super(httpServerConfig, routes);
	}
	
	/** 
	 * @param httpRequestPacket
	 * @param requestLine
	 * @param channelContext
	 * @return
	 * @throws Exception
	 * @author: tanyaowu
	 */
	@Override
	public HttpResponsePacket handler(HttpRequestPacket httpRequestPacket, RequestLine requestLine, ChannelContext<HttpSessionContext, HttpPacket, Object> channelContext)
			throws Exception {
		String path = requestLine.getPath();
//		if (StringUtils.equals("/test/abtest", path)) {
//			HttpResponsePacket ret = Resps.txt(httpRequestPacket, "OK", httpServerConfig.getCharset());
//			return ret;
//		}
		
		if (StringUtils.endsWith(path, "/")) {
			path = path + "index.html";
		}
		
		Method method = routes.pathMethodMap.get(path);
		if (method != null) {
//			String[] paramnames = methodParamnameMap.get(method);
			Object bean = routes.methodBeanMap.get(method);
			
			Object obj = method.invoke(bean, httpRequestPacket, httpServerConfig, channelContext);
			
			if (obj instanceof HttpResponsePacket) {
				return (HttpResponsePacket)obj;
			} else {
				log.error("{}#{}返回的对象不是{}", bean.getClass().getName(), method.getName(), HttpResponsePacket.class.getName());
				HttpResponsePacket ret = Resps.html(httpRequestPacket, "500--服务器出了点故障", httpServerConfig.getCharset());
				ret.setStatus(HttpResponseStatus.C500);
				return ret;
			}
		} else {
			File file = new File(httpServerConfig.getRoot(), path);
			if (file.exists()) {
				HttpResponsePacket ret = Resps.file(httpRequestPacket, file);
				return ret;
			}
		}

		HttpResponsePacket ret = Resps.html(httpRequestPacket, "404--并没有找到你想要的内容", httpServerConfig.getCharset());
		ret.setStatus(HttpResponseStatus.C404);
		return ret;
	}
}
