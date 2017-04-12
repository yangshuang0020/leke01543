package com.runyuan.scan.network.server;

import com.runyuan.scan.network.common.ScanPacket;
import com.runyuan.scan.network.common.ScanSessionContext;
import com.runyuan.scan.network.common.json.Json;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.ChannelContext;
import org.tio.server.intf.ServerAioListener;

/**
 * @author tanyaowu 
 * 2017年3月26日 下午8:22:31
 */
public class ScanServerAioListener implements ServerAioListener<ScanSessionContext, ScanPacket, Object>
{
	private static Logger log = LoggerFactory.getLogger(ScanServerAioListener.class);

	/**
	 * 
	 * @author: tanyaowu
	 */
	public ScanServerAioListener()
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
	 * @param channelContext
	 * @param isConnected
	 * @param isReconnect
	 * @throws Exception
	 * @author: tanyaowu
	 */
	@Override
	public void onAfterConnected(ChannelContext<ScanSessionContext, ScanPacket, Object> channelContext, boolean isConnected, boolean isReconnect) throws Exception
	{
		log.info("onAfterConnected channelContext:{}, isConnected:{}, isReconnect:{}", channelContext, isConnected, isReconnect);

		//连接后，需要把连接会话对象设置给channelContext
		channelContext.setSessionContext(new ScanSessionContext());
	}

	/** 
	 * @param channelContext
	 * @param packet
	 * @param isSentSuccess
	 * @throws Exception
	 * @author: tanyaowu
	 */
	@Override
	public void onAfterSent(ChannelContext<ScanSessionContext, ScanPacket, Object> channelContext, ScanPacket packet, boolean isSentSuccess) throws Exception
	{
		log.info("onAfterSent channelContext:{}, packet:{}, isSentSuccess:{}", channelContext, Json.toJson(packet), isSentSuccess);
	}

	/** 
	 * @param channelContext
	 * @param packet
	 * @param packetSize
	 * @throws Exception
	 * @author: tanyaowu
	 */
	@Override
	public void onAfterReceived(ChannelContext<ScanSessionContext, ScanPacket, Object> channelContext, ScanPacket packet, int packetSize) throws Exception
	{
		log.info("onAfterReceived channelContext:{}, packet:{}, packetSize:{}", channelContext, Json.toJson(packet), packetSize);
	}

	/** 
	 * @param channelContext
	 * @param throwable
	 * @param remark
	 * @param isRemove
	 * @throws Exception
	 * @author: tanyaowu
	 */
	@Override
	public void onAfterClose(ChannelContext<ScanSessionContext, ScanPacket, Object> channelContext, Throwable throwable, String remark, boolean isRemove) throws Exception
	{
		log.info("onAfterClose channelContext:{}, throwable:{}, remark:{}, isRemove:{}", channelContext, throwable, remark, isRemove);
	}
}
