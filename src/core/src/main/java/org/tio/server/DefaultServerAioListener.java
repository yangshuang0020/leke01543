package org.tio.server;

import org.tio.core.ChannelContext;
import org.tio.core.intf.Packet;
import org.tio.server.intf.ServerAioListener;

/**
 * 
 * @author tanyaowu 
 * @创建时间 2017年2月4日 下午9:39:34
 *
 * @操作列表
 *  编号	| 操作时间	| 操作人员	 | 操作说明
 *  (1) | 2017年2月4日 | tanyaowu | 新建类
 *
 */
public class DefaultServerAioListener<SessionContext, P extends Packet, R> implements ServerAioListener<SessionContext, P, R>
{
	/** 
	 * @see org.tio.core.intf.AioListener#onAfterConnected(org.tio.core.ChannelContext, boolean, boolean)
	 * 
	 * @param channelContext
	 * @param isConnected
	 * @param isReconnect
	 * @重写人: tanyaowu
	 * @重写时间: 2017年2月4日 下午9:40:14
	 * 
	 */
	@Override
	public void onAfterConnected(ChannelContext<SessionContext, P, R> channelContext, boolean isConnected, boolean isReconnect)
	{}

	/** 
	 * @see org.tio.core.intf.AioListener#onAfterSent(org.tio.core.ChannelContext, org.tio.core.intf.Packet, boolean)
	 * 
	 * @param channelContext
	 * @param packet
	 * @param isSentSuccess
	 * @重写人: tanyaowu
	 * @重写时间: 2017年2月4日 下午9:40:14
	 * 
	 */
	@Override
	public void onAfterSent(ChannelContext<SessionContext, P, R> channelContext, P packet, boolean isSentSuccess)
	{}

	/** 
	 * @see org.tio.core.intf.AioListener#onAfterReceived(org.tio.core.ChannelContext, org.tio.core.intf.Packet, int)
	 * 
	 * @param channelContext
	 * @param packet
	 * @param packetSize
	 * @重写人: tanyaowu
	 * @重写时间: 2017年2月4日 下午9:40:14
	 * 
	 */
	@Override
	public void onAfterReceived(ChannelContext<SessionContext, P, R> channelContext, P packet, int packetSize)
	{}

	/** 
	 * @see org.tio.core.intf.AioListener#onAfterClose(org.tio.core.ChannelContext, java.lang.Throwable, java.lang.String, boolean)
	 * 
	 * @param channelContext
	 * @param throwable
	 * @param remark
	 * @param isRemove
	 * @重写人: tanyaowu
	 * @重写时间: 2017年2月4日 下午9:40:14
	 * 
	 */
	@Override
	public void onAfterClose(ChannelContext<SessionContext, P, R> channelContext, Throwable throwable, String remark, boolean isRemove)
	{}

//	/** 
//	 * @see org.tio.server.intf.ServerAioListener#onAfterAccepted(java.nio.channels.AsynchronousSocketChannel, org.tio.server.AioServer)
//	 * 
//	 * @param asynchronousSocketChannel
//	 * @param aioServer
//	 * @return
//	 * @重写人: tanyaowu
//	 * @重写时间: 2017年2月4日 下午9:40:14
//	 * 
//	 */
//	@Override
//	public boolean onAfterAccepted(AsynchronousSocketChannel asynchronousSocketChannel, AioServer<SessionContext, P, R> aioServer)
//	{
//		// TODO Auto-generated method stub
//		return true;
//	}

}
