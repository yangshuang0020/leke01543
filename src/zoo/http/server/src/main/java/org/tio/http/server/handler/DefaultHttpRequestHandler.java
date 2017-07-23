package org.tio.http.server.handler;

import java.io.File;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

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

import com.xiaoleilu.hutool.util.BeanUtil;
import com.xiaoleilu.hutool.util.ClassUtil;

import ognl.NoSuchPropertyException;
import ognl.Ognl;
import ognl.OgnlException;
import ognl.OgnlRuntime;

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
				String[] paramnames = routes.methodParamnameMap.get(method);
				Class<?>[] parameterTypes = method.getParameterTypes();

				Object bean = routes.methodBeanMap.get(method);
				Object obj = null;
				Map<String, String[]> params = httpRequestPacket.getParams();
				//				OgnlContext context = new OgnlContext(params);
				if (parameterTypes == null || parameterTypes.length == 0) {
					//					obj = method.invoke(bean, httpRequestPacket, httpServerConfig, channelContext);
					obj = method.invoke(bean);
				} else {
					Object[] paramValues = new Object[parameterTypes.length];
					int i = 0;
					for (Class<?> paramType : parameterTypes) {
						try {
							if (paramType.isAssignableFrom(HttpRequestPacket.class)) {
								paramValues[i] = httpRequestPacket;
							} else if (paramType.isAssignableFrom(HttpServerConfig.class)) {
								paramValues[i] = httpServerConfig;
							} else if (paramType.isAssignableFrom(ChannelContext.class)) {
								paramValues[i] = channelContext;
							} else {
								if (ClassUtil.isSimpleTypeOrArray(paramType)) {
									paramValues[i] = Ognl.getValue(paramnames[i], (Object) params, paramType);
								} else {
									paramValues[i] = paramType.newInstance();//BeanUtil.mapToBean(params, paramType, true);

									Set<Entry<String, String[]>> set = params.entrySet();
									for (Entry<String, String[]> entry : set) {
										String fieldName = entry.getKey();
										String[] fieldValue = entry.getValue();
										//										Ognl.setValue(paramValues[i], fieldName, fieldValue);
										try {
											Ognl.setValue(fieldName, paramValues[i], fieldValue);
										} catch (NoSuchPropertyException e) {
											// 暂时skip it，后续优化
										} catch (Exception e) {
											log.error(e.toString(), e);
										}

									}

									//									Ognl.setValue(paramValues[i], params, value);
									//									Ognl.setValue(fieldName, paramValues[i], fieldValue);
								}

								//								paramValues[i] = Ognl.getValue("name", (Object)params, (Class<?>)String.class);
							}

						} catch (Exception e) {
							log.error(e.toString(), e);
						} finally {
							i++;
						}
					}
					obj = method.invoke(bean, paramValues);
				}

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
	public HttpResponsePacket resp500(HttpRequestPacket httpRequestPacket, RequestLine requestLine, ChannelContext<HttpSessionContext, HttpPacket, Object> channelContext,
			Throwable throwable) {
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

		System.out.println(ClassUtil.isBasicType(String.class));
		System.out.println(ClassUtil.isBasicType(Object.class));
		System.out.println(ClassUtil.isBasicType(Integer.class));
		System.out.println(ClassUtil.isBasicType(int.class));

		Map<String, String[]> params = new HashMap<>();
		String[] names = new String[] { "111" };
		params.put("id", names);

		User user = BeanUtil.mapToBean(params, User.class, true);

		try {
			Object obj = Ognl.getValue("id", (Object) params, (Class<?>) Integer.class);
			System.out.println(obj);

		} catch (OgnlException e) {
			log.error(e.toString(), e);
		}
	}

	public static class User {
		private int[] id;

		/**
		 * @return the id
		 */
		public int[] getId() {
			return id;
		}

		/**
		 * @param id the id to set
		 */
		public void setId(int[] id) {
			this.id = id;
		}
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
