/**
 * **************************************************************************
 *
 * @说明: 
 * @项目名称: tio-core
 *
 * @author: tanyaowu 
 * @创建时间: 2017年2月27日 上午10:16:51
 *
 * **************************************************************************
 */
package org.tio.core.utils;

/**
 * 
 * @author tanyaowu 
 * @创建时间 2017年2月27日 上午10:16:51
 *
 * @操作列表
 *  编号	| 操作时间	| 操作人员	 | 操作说明
 *  (1) | 2017年2月27日 | tanyaowu | 新建类
 *
 */
public class ThreadUtils
{
	public static String stackTrace()
	{
		StackTraceElement[] elements = Thread.currentThread().getStackTrace();
		StringBuilder buf = new StringBuilder();
		for (int i = 0; i < elements.length; i++)
		{
			buf.append("\r\n	")
			.append(elements[i].getClassName())
			.append(".")
			.append(elements[i].getMethodName())
			.append("(")
			.append(elements[i].getFileName())
			.append(":")
			.append(elements[i].getLineNumber())
			.append(")");
		}
		return buf.toString();
	}
}
