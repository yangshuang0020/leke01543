/**
 * **************************************************************************
 *
 * @说明: 
 * @项目名称: tio-core
 *
 * @author: tanyaowu 
 * @创建时间: 2016年11月17日 下午1:12:56
 *
 * **************************************************************************
 */
package org.tio.core.maintain;

import org.tio.core.intf.Packet;

/**
 * 所有已经关闭或从未连上的连接
 *
 * @author tanyaowu
 * @param <Ext> the generic type
 * @param <P> the generic type
 * @param <R> the generic type
 * @创建时间 2016年11月17日 下午1:12:56
 * @操作列表  编号	| 操作时间	| 操作人员	 | 操作说明
 *  (1) | 2016年11月17日 | tanyaowu | 新建类
 */
public class Closeds<SessionContext, P extends Packet, R> extends ChannelContextSetWithLock<SessionContext, P, R>
{
}
