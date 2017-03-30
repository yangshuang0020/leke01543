package org.tio.examples.showcase.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 一般生产项目中，都需要定义一个这样的SessionContext，用于保存连接的会话数据
 * @author tanyaowu 
 * 2017年3月25日 下午12:07:25
 */
public class ShowcaseSessionContext
{
	private static Logger log = LoggerFactory.getLogger(ShowcaseSessionContext.class);

	/**
	 * 是否已经登录过
	 */
	private boolean isLogined = false;
	
	/**
	 * 
	 * @author: tanyaowu
	 */
	public ShowcaseSessionContext()
	{
	}

	/**
	 * @param args
	 * @author: tanyaowu
	 */
	public static void main(String[] args)
	{

	}

	/**
	 * @return the isLogined
	 */
	public boolean isLogined()
	{
		return isLogined;
	}

	/**
	 * @param isLogined the isLogined to set
	 */
	public void setLogined(boolean isLogined)
	{
		this.isLogined = isLogined;
	}
}
