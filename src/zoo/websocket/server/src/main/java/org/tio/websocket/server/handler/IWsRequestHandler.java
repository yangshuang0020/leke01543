package org.tio.websocket.server.handler;

import org.tio.core.ChannelContext;
import org.tio.http.common.http.HttpRequestPacket;
import org.tio.http.common.http.HttpResponsePacket;
import org.tio.websocket.common.Opcode;
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
	 * 对httpResponsePacket参数进行补充并返回，如果返回null表示不想和对方建立连接，框架会断开连接，如果返回非null，框架会把这个对象发送给对方
	 * @param httpRequestPacket
	 * @param httpResponsePacket
	 * @param channelContext
	 * @return
	 * @throws Exception
	 * @author: tanyaowu
	 */
	public HttpResponsePacket handshake(HttpRequestPacket httpRequestPacket, HttpResponsePacket httpResponsePacket, ChannelContext<WsSessionContext, WsPacket, Object> channelContext)  throws Exception;
	
	/**
	 * @param packet
	 * @param requestLine
	 * @param channelContext
	 * @return
	 * @throws Exception
	 * @author: tanyaowu
	 */
	public WsResponsePacket handler(WsRequestPacket packet, byte[] bodyBytes, Opcode opcode, ChannelContext<WsSessionContext, WsPacket, Object> channelContext) throws Exception;

	/**
	 * @param websocketPacket
	 * @param text
	 * @param channelContext
	 * @return
	 * @throws Exception
	 * @author: tanyaowu
	 */
	Object onText(WsRequestPacket websocketPacket, String text, ChannelContext<WsSessionContext, WsPacket, Object> channelContext) throws Exception;

	/**
	 * @param websocketPacket
	 * @param bytes
	 * @param channelContext
	 * @return
	 * @throws Exception
	 * @author: tanyaowu
	 */
	Object onBytes(WsRequestPacket websocketPacket, byte[] bytes, ChannelContext<WsSessionContext, WsPacket, Object> channelContext) throws Exception;
}
