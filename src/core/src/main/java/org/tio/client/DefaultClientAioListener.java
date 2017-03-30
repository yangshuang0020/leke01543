package org.tio.client;

import org.tio.client.intf.ClientAioListener;
import org.tio.core.ChannelContext;
import org.tio.core.intf.Packet;

/**
 * 
 * @author tanyaowu 
 * @创建时间 2017年2月4日 下午9:46:05
 *
 * @操作列表
 *  编号	| 操作时间	| 操作人员	 | 操作说明
 *  (1) | 2017年2月4日 | tanyaowu | 新建类
 *
 */
public class DefaultClientAioListener<SessionContext, P extends Packet, R> implements ClientAioListener<SessionContext, P, R>
{

	/**
	 * 
	 *
	 * @author: tanyaowu
	 * @创建时间:　2017年2月4日 下午9:46:05
	 * 
	 */
	public DefaultClientAioListener()
	{
	}

	/** 
	 * @see org.tio.core.intf.AioListener#onAfterConnected(org.tio.core.ChannelContext, boolean, boolean)
	 * 
	 * @param channelContext
	 * @param isConnected
	 * @param isReconnect
	 * @重写人: tanyaowu
	 * @重写时间: 2017年2月4日 下午9:46:22
	 * 
	 */
	@Override
	public void onAfterConnected(ChannelContext<SessionContext, P, R> channelContext, boolean isConnected, boolean isReconnect)
	{
	}

	/** 
	 * @see org.tio.core.intf.AioListener#onAfterSent(org.tio.core.ChannelContext, org.tio.core.intf.Packet, boolean)
	 * 
	 * @param channelContext
	 * @param packet
	 * @param isSentSuccess
	 * @重写人: tanyaowu
	 * @重写时间: 2017年2月4日 下午9:46:22
	 * 
	 */
	@Override
	public void onAfterSent(ChannelContext<SessionContext, P, R> channelContext, P packet, boolean isSentSuccess)
	{
	}

	/** 
	 * @see org.tio.core.intf.AioListener#onAfterReceived(org.tio.core.ChannelContext, org.tio.core.intf.Packet, int)
	 * 
	 * @param channelContext
	 * @param packet
	 * @param packetSize
	 * @重写人: tanyaowu
	 * @重写时间: 2017年2月4日 下午9:46:22
	 * 
	 */
	@Override
	public void onAfterReceived(ChannelContext<SessionContext, P, R> channelContext, P packet, int packetSize)
	{
	}

	/** 
	 * @see org.tio.core.intf.AioListener#onAfterClose(org.tio.core.ChannelContext, java.lang.Throwable, java.lang.String, boolean)
	 * 
	 * @param channelContext
	 * @param throwable
	 * @param remark
	 * @param isRemove
	 * @重写人: tanyaowu
	 * @重写时间: 2017年2月4日 下午9:46:22
	 * 
	 */
	@Override
	public void onAfterClose(ChannelContext<SessionContext, P, R> channelContext, Throwable throwable, String remark, boolean isRemove)
	{
	}

	/** 
	 * @see org.tio.client.intf.ClientAioListener#onAfterReconnected(org.tio.core.ChannelContext, boolean)
	 * 
	 * @param channelContext
	 * @param isConnected
	 * @重写人: tanyaowu
	 * @重写时间: 2017年2月4日 下午9:46:22
	 * 
	 */
//	@Override
//	public void onAfterReconnected(ChannelContext<SessionContext, P, R> channelContext, boolean isConnected)
//	{
//	}

}
