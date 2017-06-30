package org.tio.http.server.demo1;

import java.io.File;
import java.lang.reflect.Executable;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

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
import org.tio.http.server.demo1.annotation.RequestPath;
import org.tio.http.server.handler.IHttpRequestHandler;
import org.tio.http.server.util.Resps;

import com.thoughtworks.paranamer.BytecodeReadingParanamer;
import com.thoughtworks.paranamer.Paranamer;

import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner;
//import org.voovan.tools.reflect.TReflect;
import io.github.lukehutch.fastclasspathscanner.matchprocessor.ClassAnnotationMatchProcessor;
import io.github.lukehutch.fastclasspathscanner.matchprocessor.MethodAnnotationMatchProcessor;

/**
 * @author tanyaowu 
 * 2017年6月28日 下午5:32:38
 */
public class HttpRequestHandler implements IHttpRequestHandler {
	private static Logger log = LoggerFactory.getLogger(HttpRequestHandler.class);

	private HttpServerConfig httpServerConfig = null;

	private String[] scanPackages = null;

	/**
	 * Controller路径映射
	 * key: /user
	 * value: object
	 */
	private Map<String, Object> pathBeanMap = new HashMap<>();
	
	/**
	 * 路径和class映射
	 * key: class
	 * value: /user
	 */
	private Map<Class<?>, String> classPathMap = new HashMap<>();
	
	/**
	 * Method路径映射
	 */
	private Map<String, Method> pathMethodMap = new HashMap<>();
	
	/**
	 * 方法参数名映射
	 */
	private Map<Method, String[]> methodParamnameMap = new HashMap<>();
	
	/**
	 * 
	 */
	private Map<Method, Object> methodBeanMap = new HashMap<>();
	
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
		if (StringUtils.endsWith(path, "/")) {
			path = path + "index.html";
		}
		
		Method method = pathMethodMap.get(path);
		if (method != null) {
//			String[] paramnames = methodParamnameMap.get(method);
			Object bean = methodBeanMap.get(method);
			
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


	/**
	 * 
	 * @author: tanyaowu
	 */
	public HttpRequestHandler(HttpServerConfig httpServerConfig, String[] scanPackages) {
		this.setHttpServerConfig(httpServerConfig);
		this.scanPackages = scanPackages;
		if (scanPackages != null) {
			final FastClasspathScanner fastClasspathScanner = new FastClasspathScanner(scanPackages);
//			fastClasspathScanner.verbose();
			
			fastClasspathScanner.matchClassesWithAnnotation(RequestPath.class, new ClassAnnotationMatchProcessor() {
				@Override
				public void processMatch(Class<?> classWithAnnotation) {
					try {
						Object bean = classWithAnnotation.newInstance();
						RequestPath mapping = classWithAnnotation.getAnnotation(RequestPath.class);
						String beanPath = mapping.value();
//						if (!StringUtils.endsWith(beanUrl, "/")) {
//							beanUrl = beanUrl + "/";
//						}

						beanPath = formateBeanPath(beanPath);
						
						Object obj = pathBeanMap.get(beanPath);
						if (obj != null) {
							log.error("mapping[{}] already exists in class [{}]", beanPath, obj.getClass().getName());
						} else {
							pathBeanMap.put(beanPath, bean);
							classPathMap.put(classWithAnnotation, beanPath);
						}
					} catch (Exception e) {
						
						log.error(e.toString(), e);
					}
				}
			});
			
			fastClasspathScanner.matchClassesWithMethodAnnotation(RequestPath.class, new MethodAnnotationMatchProcessor(){
				@Override
				public void processMatch(Class<?> matchingClass, Executable matchingMethodOrConstructor) {
//					log.error(matchingMethodOrConstructor + "");
					RequestPath mapping = matchingMethodOrConstructor.getAnnotation(RequestPath.class);
					
					String methodName = matchingMethodOrConstructor.getName();

					
					String methodPath = mapping.value();
					methodPath = formateMethodPath(methodPath);
					String beanPath = classPathMap.get(matchingClass);
					
					if (StringUtils.isBlank(beanPath)) {
						log.error("方法有注解，但类没注解, method:{}, class:{}", methodName, matchingClass);
						return;
					}
					
					Object bean = pathBeanMap.get(beanPath);
					String completeMethodPath = methodPath;
					if (beanPath != null) {
						completeMethodPath = beanPath + methodPath;
					}
					
					Class<?>[] parameterTypes = matchingMethodOrConstructor.getParameterTypes();
					Method method;
					try {
						method = matchingClass.getMethod(methodName, parameterTypes);
						
						Paranamer paranamer = new BytecodeReadingParanamer();
						String[] parameterNames = paranamer.lookupParameterNames(method, false); // will return null if not found
						
						Method checkMethod = pathMethodMap.get(completeMethodPath);
						if (checkMethod != null) {
							log.error("mapping[{}] already exists in method [{}]", completeMethodPath, checkMethod.getDeclaringClass() + "#" + checkMethod.getName());
							return;
						}
						
						pathMethodMap.put(completeMethodPath, method);
						methodParamnameMap.put(method, parameterNames);
						methodBeanMap.put(method, bean);
					} catch (Exception e) {
						log.error(e.toString(), e);
					} 
				}
			});
			
			fastClasspathScanner.scan();
		}
	}
	
	/**
	 * 格式化成"/user","/"这样的路径
	 * @param initPath
	 * @return
	 * @author: tanyaowu
	 */
	private static String formateBeanPath(String initPath){
		if (StringUtils.isBlank(initPath)) {
			return "/";
		}
		initPath = StringUtils.replaceAll(initPath, "//", "/");
		if (!StringUtils.startsWith(initPath, "/")) {
			initPath = "/" + initPath;
		}
		
		if (StringUtils.endsWith(initPath, "/")) {
			initPath = initPath.substring(0, initPath.length() - 1);
		}
		return initPath;
	}
	
	private static String formateMethodPath(String initPath){
		if (StringUtils.isBlank(initPath)) {
			return "/";
		}
		initPath = StringUtils.replaceAll(initPath, "//", "/");
		if (!StringUtils.startsWith(initPath, "/")) {
			initPath = "/" + initPath;
		}
		
		return initPath;
	}

	//	/**
	//	 * 
	//	 * @author: tanyaowu
	//	 */
	//	public HttpRequestHandler() {
	//	}

	

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

	/**
	 * @return the scanPackages
	 */
	public String[] getScanPackages() {
		return scanPackages;
	}

	/**
	 * @param scanPackages the scanPackages to set
	 */
	public void setScanPackages(String[] scanPackages) {
		this.scanPackages = scanPackages;
	}
}
