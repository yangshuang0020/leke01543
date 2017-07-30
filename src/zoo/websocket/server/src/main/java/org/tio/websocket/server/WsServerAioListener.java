package org.tio.websocket.server;

import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.ChannelContext;
import org.tio.server.intf.ServerAioListener;
import org.tio.websocket.common.WsPacket;
import org.tio.websocket.common.WsSessionContext;

/**
 * 
 * @author tanyaowu 
 * 2017年7月30日 上午9:16:02
 */
public class WsServerAioListener implements ServerAioListener<WsSessionContext, WsPacket, Object> {

	private static Logger log = LoggerFactory.getLogger(WsServerAioListener.class);
	private static Logger iplog = LoggerFactory.getLogger("tio-ip-trace-log");

	static Map<String, AtomicLong> ipmap = new java.util.concurrent.ConcurrentHashMap<>();
	static AtomicLong accessCount = new AtomicLong();

	public WsServerAioListener() {
	}

	public static void main(String[] args) {
	}

	@Override
	public void onAfterConnected(ChannelContext<WsSessionContext, WsPacket, Object> channelContext, boolean isConnected, boolean isReconnect) {
		WsSessionContext wsSessionContext = new WsSessionContext();
		channelContext.setSessionContext(wsSessionContext);
		return;
	}

	@Override
	public void onAfterSent(ChannelContext<WsSessionContext, WsPacket, Object> channelContext, WsPacket packet, boolean isSentSuccess) {
	}

	@Override
	public void onAfterReceived(ChannelContext<WsSessionContext, WsPacket, Object> channelContext, WsPacket packet, int packetSize) {
		
	}

	@Override
	public void onAfterClose(ChannelContext<WsSessionContext, WsPacket, Object> channelContext, Throwable throwable, String remark, boolean isRemove) {
	}

	@Override
	public void onBeforeClose(ChannelContext<WsSessionContext, WsPacket, Object> channelContext, Throwable throwable, String remark, boolean isRemove) {
	}

}
