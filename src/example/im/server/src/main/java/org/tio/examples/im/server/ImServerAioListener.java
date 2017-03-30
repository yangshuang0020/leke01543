package org.tio.examples.im.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.tio.core.ChannelContext;
import org.tio.examples.im.common.CommandStat;
import org.tio.examples.im.common.ImPacket;
import org.tio.examples.im.common.ImSessionContext;
import org.tio.server.intf.ServerAioListener;

/**
 * 
 * @author tanyaowu 
 * @创建时间 2016年12月16日 下午5:52:06
 *
 * @操作列表
 *  编号	| 操作时间	| 操作人员	 | 操作说明
 *  (1) | 2016年12月16日 | tanyaowu | 新建类
 *
 */
public class ImServerAioListener implements ServerAioListener<ImSessionContext, ImPacket, Object>
{
	@SuppressWarnings("unused")
	private static Logger log = LoggerFactory.getLogger(ImServerAioListener.class);

	/**
	 * 
	 *
	 * @author: tanyaowu
	 * @创建时间:　2016年12月16日 下午5:52:06
	 * 
	 */
	public ImServerAioListener()
	{
	}

	/**
	 * @param args
	 *
	 * @author: tanyaowu
	 * @创建时间:　2016年12月16日 下午5:52:06
	 * 
	 */
	public static void main(String[] args)
	{
	}

	/** 
	 * @see org.tio.server.intf.ServerAioListener#onAfterAccepted(java.nio.channels.AsynchronousSocketChannel, org.tio.server.AioServer)
	 * 
	 * @param asynchronousSocketChannel
	 * @param aioServer
	 * @return
	 * @重写人: tanyaowu
	 * @重写时间: 2016年12月20日 上午11:03:45
	 * 
	 */
//	@Override
//	public boolean onAfterAccepted(AsynchronousSocketChannel asynchronousSocketChannel, AioServer<ImSessionContext, ImPacket, Object> aioServer)
//	{
//		return true;
//	}

	@Override
	public void onAfterConnected(ChannelContext<ImSessionContext, ImPacket, Object> channelContext, boolean isConnected, boolean isReconnect)
	{
		ImSessionContext imSessionContext = new ImSessionContext();
		channelContext.setSessionContext(imSessionContext);
		return;
	}

	/** 
	 * @see org.tio.core.intf.AioListener#onBeforeSent(org.tio.core.ChannelContext, org.tio.core.intf.Packet, int)
	 * 
	 * @param channelContext
	 * @param packet
	 * @重写人: tanyaowu
	 * @重写时间: 2016年12月20日 上午11:08:44
	 * 
	 */
	@Override
	public void onAfterSent(ChannelContext<ImSessionContext, ImPacket, Object> channelContext, ImPacket packet, boolean isSentSuccess)
	{
		if (isSentSuccess)
		{
			CommandStat.getCount(packet.getCommand()).sent.incrementAndGet();
		}
		

	}

	/** 
	 * @see org.tio.core.intf.AioListener#onAfterReceived(org.tio.core.ChannelContext, org.tio.core.intf.Packet, int)
	 * 
	 * @param channelContext
	 * @param packet
	 * @param packetSize
	 * @重写人: tanyaowu
	 * @重写时间: 2016年12月20日 上午11:08:44
	 * 
	 */
	@Override
	public void onAfterReceived(ChannelContext<ImSessionContext, ImPacket, Object> channelContext, ImPacket packet, int packetSize)
	{
		CommandStat.getCount(packet.getCommand()).received.incrementAndGet();
	}

	/** 
	 * @see org.tio.core.intf.AioListener#onAfterClose(org.tio.core.ChannelContext, java.lang.Throwable, java.lang.String)
	 * 
	 * @param channelContext
	 * @param throwable
	 * @param remark
	 * @重写人: tanyaowu
	 * @重写时间: 2017年2月1日 上午11:03:11
	 * 
	 */
	@Override
	public void onAfterClose(ChannelContext<ImSessionContext, ImPacket, Object> channelContext, Throwable throwable, String remark, boolean isRemove)
	{

	}

}
