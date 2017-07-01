package org.tio.http.server.demo1;

import java.lang.reflect.Executable;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.http.server.demo1.annotation.MessageType;

import com.thoughtworks.paranamer.BytecodeReadingParanamer;
import com.thoughtworks.paranamer.Paranamer;

import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner;
import io.github.lukehutch.fastclasspathscanner.matchprocessor.ClassAnnotationMatchProcessor;
import io.github.lukehutch.fastclasspathscanner.matchprocessor.MethodAnnotationMatchProcessor;

/**
 * @author tanyaowu 
 * 2017年7月1日 下午12:14:50
 */
public class Routes {
	private static Logger log = LoggerFactory.getLogger(Routes.class);

//	private WsServerConfig wsServerConfig = null;
//
//	private String[] scanPackages = null;

	/**
	 * Controller路径映射
	 * key: /user
	 * value: object
	 */
	Map<String, Object> pathBeanMap = new HashMap<>();

	/**
	 * 路径和class映射
	 * key: class
	 * value: /user
	 */
	Map<Class<?>, String> classPathMap = new HashMap<>();

	/**
	 * Method路径映射
	 */
	Map<String, Method> pathMethodMap = new HashMap<>();

	/**
	 * 方法参数名映射
	 */
	Map<Method, String[]> methodParamnameMap = new HashMap<>();

	/**
	 * 
	 */
	Map<Method, Object> methodBeanMap = new HashMap<>();

	/**
	 * 
	 * @author: tanyaowu
	 */
	public Routes(String[] scanPackages) {
//		this.scanPackages = scanPackages;

		if (scanPackages != null) {
			final FastClasspathScanner fastClasspathScanner = new FastClasspathScanner(scanPackages);
			//			fastClasspathScanner.verbose();

			fastClasspathScanner.matchClassesWithAnnotation(MessageType.class, new ClassAnnotationMatchProcessor() {
				@Override
				public void processMatch(Class<?> classWithAnnotation) {
					try {
						Object bean = classWithAnnotation.newInstance();
						MessageType mapping = classWithAnnotation.getAnnotation(MessageType.class);
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

			fastClasspathScanner.matchClassesWithMethodAnnotation(MessageType.class, new MethodAnnotationMatchProcessor() {
				@Override
				public void processMatch(Class<?> matchingClass, Executable matchingMethodOrConstructor) {
					//					log.error(matchingMethodOrConstructor + "");
					MessageType mapping = matchingMethodOrConstructor.getAnnotation(MessageType.class);

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
	private static String formateBeanPath(String initPath) {
		return initPath;
		
//		if (StringUtils.isBlank(initPath)) {
//			return "/";
//		}
//		initPath = StringUtils.replaceAll(initPath, "//", "/");
//		if (!StringUtils.startsWith(initPath, "/")) {
//			initPath = "/" + initPath;
//		}
//
//		if (StringUtils.endsWith(initPath, "/")) {
//			initPath = initPath.substring(0, initPath.length() - 1);
//		}
//		return initPath;
	}

	private static String formateMethodPath(String initPath) {
		return initPath;
		
//		if (StringUtils.isBlank(initPath)) {
//			return "/";
//		}
//		initPath = StringUtils.replaceAll(initPath, "//", "/");
//		if (!StringUtils.startsWith(initPath, "/")) {
//			initPath = "/" + initPath;
//		}
//
//		return initPath;
	}

	/**
	 * @param args
	 * @author: tanyaowu
	 */
	public static void main(String[] args) {

	}
}
