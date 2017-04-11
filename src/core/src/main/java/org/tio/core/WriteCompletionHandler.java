package org.tio.core;

import java.nio.channels.CompletionHandler;
import java.util.List;
import java.util.concurrent.Semaphore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.intf.AioListener;
import org.tio.core.intf.Packet;
import org.tio.core.stat.GroupStat;

/**
 * 
 * @author tanyaowu 
 *
 */
public class WriteCompletionHandler<SessionContext, P extends Packet, R> implements CompletionHandler<Integer, Object>
{

	private static Logger log = LoggerFactory.getLogger(WriteCompletionHandler.class);

	private ChannelContext<SessionContext, P, R> channelContext = null;

	private java.util.concurrent.Semaphore writeSemaphore = new Semaphore(1);
	//	private P packet;

	/**
	 * 
	 *
	 * @author: tanyaowu
	 * 2016年11月15日 下午1:31:04
	 * 
	 */
	public WriteCompletionHandler(ChannelContext<SessionContext, P, R> channelContext)
	{
		this.channelContext = channelContext;
	}

	/** 
	 * @see java.nio.channels.CompletionHandler#completed(java.lang.Object, java.lang.Object)
	 * 
	 * @param result
	 * @param packets
	 * @author: tanyaowu
	 * 2016年11月16日 下午1:40:59
	 * 
	 */
	@Override
	public void completed(Integer result, Object packets)
	{
		handle(result, null, packets);
	}

	/** 
	 * @see java.nio.channels.CompletionHandler#failed(java.lang.Throwable, java.lang.Object)
	 * 
	 * @param throwable
	 * @param packets
	 * @author: tanyaowu
	 * 2016年11月16日 下午1:40:59
	 * 
	 */
	@Override
	public void failed(Throwable throwable, Object packets)
	{
		handle(0, throwable, packets);
	}

	public void handle(Integer result, Throwable throwable, Object packets)
	{
		this.writeSemaphore.release();
		GroupContext<SessionContext, P, R> groupContext = channelContext.getGroupContext();
		GroupStat groupStat = groupContext.getGroupStat();
		AioListener<SessionContext, P, R> aioListener = groupContext.getAioListener();
		boolean isSentSuccess = (result > 0);

		if (isSentSuccess)
		{
			groupStat.getSentBytes().addAndGet(result);
		}

		int packetCount = 0;
		if (packets instanceof Packet)
		{
			@SuppressWarnings("unchecked")
			P packet = (P) packets;
			if (isSentSuccess)
			{
				packetCount = 1;
				groupStat.getSentPacket().addAndGet(packetCount);
			}

			try
			{
				log.info("{} 已经发送:{}", channelContext, packet.logstr());
				aioListener.onAfterSent(channelContext, packet, isSentSuccess);
			} catch (Exception e)
			{
				log.error(e.toString(), e);
			}
		} else
		{
			@SuppressWarnings("unchecked")
			List<P> ps = (List<P>) packets;
			if (isSentSuccess)
			{
				packetCount = ps.size();
				groupStat.getSentPacket().addAndGet(packetCount);
			}

			for (P p : ps)
			{
				try
				{
					log.info("{} 已经发送:{}", channelContext, p.logstr());
					aioListener.onAfterSent(channelContext, p, isSentSuccess);
				} catch (Exception e)
				{
					log.error(e.toString(), e);
				}
			}
		}

		if (!isSentSuccess)
		{
			Aio.close(channelContext, throwable, "写数据返回:" + result);
		}
	}

	/**
	 * @return the writeSemaphore
	 */
	public java.util.concurrent.Semaphore getWriteSemaphore()
	{
		return writeSemaphore;
	}

}
