package com.runyuan.scan;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;

/**
 * 应用完全启动后, Spring执行自定义初始化 
 * 此处初始化避免了初始化static时 bean还没注入的问题 
 * @author  kleen
 */  
public class InstantiationListener implements InitializingBean
{
    public static Logger logger = LogManager.getLogger(InstantiationListener.class);

    //==>继承于InitializingBean 的afterPropertiesSet()方法是初始化方法
    public void afterPropertiesSet()  
    {
        //1.初始化
        logger.info("=====================================");
        logger.info("===ScanProvider start success!!!===");
        logger.info("=====================================");


    }
}  