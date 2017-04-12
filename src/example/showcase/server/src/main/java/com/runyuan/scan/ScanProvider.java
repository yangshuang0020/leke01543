package com.runyuan.scan;


import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 *
 * @描述: 启动Dubbo服务用的MainClass.
 * @作者: kleen .
 * @创建时间: 2016.09.01
 * @版本: 1.0 .
 */
public class ScanProvider {

	static Logger logger = LogManager.getLogger(ScanProvider.class.getName());

	public static void main(String[] args) {
		try {
			ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/spring-application.xml");
			context.start();
		} catch (Exception e) {
			logger.error("== ScanProvider context start error:",e);
 		}
		synchronized (ScanProvider.class) {
			while (true) {
				try {
					ScanProvider.class.wait();
				} catch (InterruptedException e) {
					logger.error("== synchronized error:",e);
				}
			}
		}
	}
    
}