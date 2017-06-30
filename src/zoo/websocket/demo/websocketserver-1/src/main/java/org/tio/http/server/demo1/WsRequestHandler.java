package org.tio.http.server.demo1;

import java.lang.reflect.Executable;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.ChannelContext;
import org.tio.http.server.demo1.annotation.RequestPath;
import org.tio.websocket.common.WsPacket;
import org.tio.websocket.common.WsRequestPacket;
import org.tio.websocket.common.WsResponsePacket;
import org.tio.websocket.common.WsSessionContext;
import org.tio.websocket.server.WsServerConfig;
import org.tio.websocket.server.handler.IWsRequestHandler;

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
public class WsRequestHandler implements IWsRequestHandler {
	private static Logger log = LoggerFactory.getLogger(WsRequestHandler.class);

	private WsServerConfig wsServerConfig = null;

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
	 * @param packet
	 * @param channelContext
	 * @return
	 * @throws Exception
	 * @author: tanyaowu
	 */
	@Override
	public WsResponsePacket handler(WsRequestPacket packet, ChannelContext<WsSessionContext, WsPacket, Object> channelContext) throws Exception {
		String path = "/test/json";  // TODO 这里通过协议得到路径
		Method method = pathMethodMap.get(path);
		if (method != null) {
//			String[] paramnames = methodParamnameMap.get(method);
			Object bean = methodBeanMap.get(method);
			
			Object obj = method.invoke(bean, packet, wsServerConfig, channelContext);
			
			if (obj instanceof WsResponsePacket) {
				return (WsResponsePacket)obj;
			} else {
				return null;
			}
		} else {
			log.error("没找到应对的处理方法");
			return null;
		}
	}
	
	/**
	 * 
	 * @author: tanyaowu
	 */
	public WsRequestHandler(WsServerConfig wsServerConfig, String[] scanPackages) {
		this.setWsServerConfig(wsServerConfig);
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
	 * @return the wsServerConfig
	 */
	public WsServerConfig getWsServerConfig() {
		return wsServerConfig;
	}

	/**
	 * @param wsServerConfig the wsServerConfig to set
	 */
	public void setWsServerConfig(WsServerConfig wsServerConfig) {
		this.wsServerConfig = wsServerConfig;
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
