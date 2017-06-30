package org.tio.http.server;

import java.nio.ByteBuffer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.Aio;
import org.tio.core.ChannelContext;
import org.tio.core.GroupContext;
import org.tio.core.exception.AioDecodeException;
import org.tio.http.common.HttpPacket;
import org.tio.http.common.HttpSessionContext;
import org.tio.http.common.http.HttpRequestDecoder;
import org.tio.http.common.http.HttpRequestPacket;
import org.tio.http.common.http.HttpResponseEncoder;
import org.tio.http.common.http.HttpResponsePacket;
import org.tio.http.server.handler.IHttpRequestHandler;
import org.tio.server.intf.ServerAioHandler;

/**
 * 
 * @author tanyaowu 
 *
 */
public class HttpServerAioHandler implements ServerAioHandler<HttpSessionContext, HttpPacket, Object> {
	private static Logger log = LoggerFactory.getLogger(HttpServerAioHandler.class);

	//	private static Map<Command, ImBsHandlerIntf> handlerMap = new HashMap<>();
	//	static {
	//		handlerMap.put(Command.COMMAND_HANDSHAKE_REQ, new HandshakeReqHandler());
	//		handlerMap.put(Command.COMMAND_AUTH_REQ, new AuthReqHandler());
	//		handlerMap.put(Command.COMMAND_CHAT_REQ, new ChatReqHandler());
	//		handlerMap.put(Command.COMMAND_JOIN_GROUP_REQ, new JoinReqHandler());
	//		handlerMap.put(Command.COMMAND_HEARTBEAT_REQ, new HeartbeatReqHandler());
	//		handlerMap.put(Command.COMMAND_CLOSE_REQ, new CloseReqHandler());
	//
	//		handlerMap.put(Command.COMMAND_LOGIN_REQ, new LoginReqHandler());
	//		handlerMap.put(Command.COMMAND_CLIENT_PAGE_REQ, new ClientPageReqHandler());
	//
	//	}

	private HttpServerConfig httpServerConfig;
	
	private IHttpRequestHandler httpRequestHandler;

	/**
	 * 
	 *
	 * @author: tanyaowu
	 * 2016年11月18日 上午9:13:15
	 * 
	 */
	public HttpServerAioHandler(HttpServerConfig httpServerConfig, IHttpRequestHandler httpRequestHandler) {
		this.httpServerConfig = httpServerConfig;
		this.httpRequestHandler = httpRequestHandler;
	}

	/**
	 * @param args
	 *
	 * @author: tanyaowu
	 * 2016年11月18日 上午9:13:15
	 * 
	 */
	public static void main(String[] args) {
	}

	/** 
	 * @see org.tio.core.intf.AioHandler#handler(org.tio.core.intf.Packet)
	 * 
	 * @param packet
	 * @return
	 * @throws Exception 
	 * @author: tanyaowu
	 * 2016年11月18日 上午9:37:44
	 * 
	 */
	@Override
	public Object handler(HttpPacket packet, ChannelContext<HttpSessionContext, HttpPacket, Object> channelContext) throws Exception {
		//		Command command = packet.getCommand();
		//
		//
		//		ImBsHandlerIntf handler = handlerMap.get(command);
		//		if (handler != null) {
		//			Object obj = handler.handler(packet, channelContext);
		//			CommandStat.getCount(command).handled.incrementAndGet();
		//			return obj;
		//		} else {
		//			log.warn("命令码[{}]没有对应的处理类", command);
		//			CommandStat.getCount(command).handled.incrementAndGet();
		//			return null;
		//		}
		HttpRequestPacket httpRequestPacket = (HttpRequestPacket) packet;
		HttpResponsePacket httpResponsePacket = httpRequestHandler.handler(httpRequestPacket, httpRequestPacket.getRequestLine(), channelContext);
		Aio.send(channelContext, httpResponsePacket);
		return null;

	}

	/** 
	 * @see org.tio.core.intf.AioHandler#encode(org.tio.core.intf.Packet)
	 * 
	 * @param packet
	 * @return
	 * @author: tanyaowu
	 * 2016年11月18日 上午9:37:44
	 * 
	 */
	@Override
	public ByteBuffer encode(HttpPacket packet, GroupContext<HttpSessionContext, HttpPacket, Object> groupContext,
			ChannelContext<HttpSessionContext, HttpPacket, Object> channelContext) {
		HttpResponsePacket httpResponsePacket = (HttpResponsePacket) packet;
		ByteBuffer byteBuffer = HttpResponseEncoder.encode(httpResponsePacket, groupContext, channelContext);
		return byteBuffer;
	}

	/** 
	 * @see org.tio.core.intf.AioHandler#decode(java.nio.ByteBuffer)
	 * 
	 * @param buffer
	 * @return
	 * @throws AioDecodeException
	 * @author: tanyaowu
	 * 2016年11月18日 上午9:37:44
	 * 
	 */
	@Override
	public HttpRequestPacket decode(ByteBuffer buffer, ChannelContext<HttpSessionContext, HttpPacket, Object> channelContext) throws AioDecodeException {
		HttpRequestPacket httpRequestPacket = HttpRequestDecoder.decode(buffer, channelContext);
		return httpRequestPacket;
	}

	/**
	 * @return the httpServerConfig
	 */
	public HttpServerConfig getHttpServerConfig() {
		return httpServerConfig;
	}

	/**
	 * @param httpServerConfig the httpServerConfig to set
	 */
	public void setHttpServerConfig(HttpServerConfig httpServerConfig) {
		this.httpServerConfig = httpServerConfig;
	}

}
