/**
 * **************************************************************************
 *
 * @说明: 
 * @项目名称: t-io-client
 *
 * @author: tanyaowu 
 * @创建时间: 2016年12月6日 下午1:43:46
 *
 * **************************************************************************
 */
package org.tio.server.intf;

import org.tio.core.intf.AioHandler;
import org.tio.core.intf.Packet;

/**
 * 
 * @author tanyaowu 
 * @创建时间 2016年12月6日 下午1:43:46
 *
 * @操作列表
 *  编号	| 操作时间	| 操作人员	 | 操作说明
 *  (1) | 2016年12月6日 | tanyaowu | 新建类
 *
 */
public interface ServerAioHandler <SessionContext, P extends Packet, R> extends AioHandler<SessionContext, P, R>
{
	
}
