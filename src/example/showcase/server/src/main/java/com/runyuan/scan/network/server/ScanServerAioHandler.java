package com.runyuan.scan.network.server;

import com.runyuan.scan.network.common.ScanAbsAioHandler;
import com.runyuan.scan.network.common.ScanPacket;
import com.runyuan.scan.network.common.ScanSessionContext;
import com.runyuan.scan.network.common.Type;
import com.runyuan.scan.network.common.intf.AbsScanBsHandler;
import com.runyuan.scan.network.server.handler.ScanInHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.ChannelContext;
import org.tio.server.intf.ServerAioHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author tanyaowu 
 *
 */
public class ScanServerAioHandler extends ScanAbsAioHandler implements ServerAioHandler<ScanSessionContext, ScanPacket, Object>
{
	private static Logger log = LoggerFactory.getLogger(ScanServerAioHandler.class);

	private static Map<Byte, AbsScanBsHandler<?>> handlerMap = new HashMap<>();
//	static
//	{
//		handlerMap.put(Type.GROUP_MSG_REQ, new GroupMsgReqHandler());
//		handlerMap.put(Type.HEART_BEAT_REQ, new HeartbeatReqHandler());
//		handlerMap.put(Type.JOIN_GROUP_REQ, new JoinGroupReqHandler());
//		handlerMap.put(Type.LOGIN_REQ, new LoginReqHandler());
//		handlerMap.put(Type.P2P_REQ, new P2PReqHandler());
//	}
	static {
		handlerMap.put(Type.SCAN_IN_REQ,new ScanInHandler());
	}
	
	/** 
	 * 处理消息
	 */
	@Override
	public Object handler(ScanPacket packet, ChannelContext<ScanSessionContext, ScanPacket, Object> channelContext) throws Exception
	{
		Byte type = packet.getType();
		AbsScanBsHandler<?> scanBsHandler = handlerMap.get(type);
		if (scanBsHandler == null)
		{
			log.error("{}, 找不到处理类，type:{}", channelContext, type);
			return null;
		}
		scanBsHandler.handler(packet, channelContext);
		return null;
	}
}
