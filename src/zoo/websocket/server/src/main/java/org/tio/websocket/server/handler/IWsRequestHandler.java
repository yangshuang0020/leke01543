package org.tio.websocket.server.handler;

import org.tio.core.ChannelContext;
import org.tio.websocket.common.WsPacket;
import org.tio.websocket.common.WsRequestPacket;
import org.tio.websocket.common.WsResponsePacket;
import org.tio.websocket.common.WsSessionContext;

/**
 * 
 * @author tanyaowu 
 *
 */
public interface IWsRequestHandler
{
	/**
	 * @param packet
	 * @param requestLine
	 * @param channelContext
	 * @return
	 * @throws Exception
	 * @author: tanyaowu
	 */
	public WsResponsePacket handler(WsRequestPacket packet, ChannelContext<WsSessionContext, WsPacket, Object> channelContext)  throws Exception;
}
