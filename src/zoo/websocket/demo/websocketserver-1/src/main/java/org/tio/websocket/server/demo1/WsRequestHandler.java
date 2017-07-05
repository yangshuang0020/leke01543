package org.tio.websocket.server.demo1;

import java.lang.reflect.Method;
import java.nio.ByteBuffer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.Aio;
import org.tio.core.ChannelContext;
import org.tio.http.common.http.HttpRequestPacket;
import org.tio.http.common.http.HttpResponsePacket;
import org.tio.websocket.common.Opcode;
import org.tio.websocket.common.WsPacket;
import org.tio.websocket.common.WsRequestPacket;
import org.tio.websocket.common.WsResponsePacket;
import org.tio.websocket.common.WsSessionContext;
import org.tio.websocket.server.WsServerConfig;
import org.tio.websocket.server.handler.IWsRequestHandler;

/**
 * @author tanyaowu 
 * 2017年6月28日 下午5:32:38
 */
public class WsRequestHandler implements IWsRequestHandler {
	private static Logger log = LoggerFactory.getLogger(WsRequestHandler.class);

	private WsServerConfig wsServerConfig = null;

	private Routes routes = null;

	/** 
	 * @param httpRequestPacket
	 * @param httpResponsePacket
	 * @param channelContext
	 * @return
	 * @throws Exception
	 * @author: tanyaowu
	 */
	@Override
	public HttpResponsePacket handshake(HttpRequestPacket httpRequestPacket, HttpResponsePacket httpResponsePacket,
			ChannelContext<WsSessionContext, WsPacket, Object> channelContext) throws Exception {
		return httpResponsePacket;
	}

	/**
	 * 
	 * @param websocketPacket
	 * @param text
	 * @param channelContext
	 * @return 可以是WsResponsePacket、String、null
	 * @author: tanyaowu
	 */
	@Override
	public Object onText(WsRequestPacket websocketPacket, String text, ChannelContext<WsSessionContext, WsPacket, Object> channelContext) throws Exception {

		if (true) {
			return "收到消息:" + text;
		}

		String messageType = "/test/json"; // TODO 这里通过协议得到路径

		Method method = routes.pathMethodMap.get(messageType);
		if (method != null) {
			//			String[] paramnames = methodParamnameMap.get(method);
			Object bean = routes.methodBeanMap.get(method);

			Object obj = method.invoke(bean, websocketPacket, text, wsServerConfig, channelContext);
			return obj;

			//			if (obj instanceof WsResponsePacket) {
			//				return (WsResponsePacket)obj;
			//			} else {
			//				return null;
			//			}
		} else {
			log.error("没找到应对的处理方法");
			return null;
		}
	}

	/**
	 * 
	 * @param websocketPacket
	 * @param bytes
	 * @param channelContext
	 * @return 可以是WsResponsePacket、byte[]、ByteBuffer、null
	 * @author: tanyaowu
	 */
	@Override
	public Object onBytes(WsRequestPacket websocketPacket, byte[] bytes, ChannelContext<WsSessionContext, WsPacket, Object> channelContext) throws Exception {
		if (true) {
			byte[] bs1 = "收到消息:".getBytes(wsServerConfig.getCharset());
			ByteBuffer buffer = ByteBuffer.allocate(bs1.length + bytes.length);
			buffer.put(bs1);
			buffer.put(bytes);
			return buffer;
		}
		String logstr = "业务不支持byte";
		log.error(logstr);
		Aio.remove(channelContext, logstr);
		return null;
	}

	/** 
	 * @param packet
	 * @param channelContext
	 * @return
	 * @throws Exception
	 * @author: tanyaowu
	 */
	@Override
	public WsResponsePacket handler(WsRequestPacket websocketPacket, byte[] bytes, Opcode opcode, ChannelContext<WsSessionContext, WsPacket, Object> channelContext)
			throws Exception {
		WsResponsePacket wsResponsePacket = null;
		if (opcode == Opcode.TEXT) {
			if (bytes == null || bytes.length == 0) {
				Aio.remove(channelContext, "错误的websocket包，body为空");
				return null;
			}
			String text = new String(bytes, wsServerConfig.getCharset());
			Object retObj = onText(websocketPacket, text, channelContext);
			if (retObj != null) {
				if (retObj instanceof WsResponsePacket) {
					return (WsResponsePacket) retObj;
				} else if (retObj instanceof String) {
					String xx = (String) retObj;
					wsResponsePacket = new WsResponsePacket();
					wsResponsePacket.setBody(xx.getBytes(wsServerConfig.getCharset()));
					wsResponsePacket.setWsOpcode(Opcode.TEXT);
					return wsResponsePacket;
				} else {
					log.error(this.getClass().getName() + "#onText()方法，只允许返回String或WsResponsePacket，但是程序返回了{}" + retObj.getClass().getName());
					return null;
				}

			} else {
				return null;
			}

		} else if (opcode == Opcode.BINARY) {
			if (bytes == null || bytes.length == 0) {
				Aio.remove(channelContext, "错误的websocket包，body为空");
				return null;
			}
			Object retObj = onBytes(websocketPacket, bytes, channelContext);
			if (retObj != null) {
				if (retObj instanceof WsResponsePacket) {
					return (WsResponsePacket) retObj;
				} else if (retObj instanceof byte[]) {
					wsResponsePacket = new WsResponsePacket();
					wsResponsePacket.setBody((byte[]) retObj);
					wsResponsePacket.setWsOpcode(Opcode.BINARY);
					return wsResponsePacket;
				} else if (retObj instanceof ByteBuffer) {
					wsResponsePacket = new WsResponsePacket();
					byte[] bs = ((ByteBuffer) retObj).array();
					wsResponsePacket.setBody(bs);
					wsResponsePacket.setWsOpcode(Opcode.BINARY);
					return wsResponsePacket;
				} else {
					log.error(this.getClass().getName() + "#onText()方法，只允许返回String或WsResponsePacket，但是程序返回了{}" + retObj.getClass().getName());
					return null;
				}
			} else {
				return null;
			}
		} else if (opcode == Opcode.PING || opcode == Opcode.PONG) {
			log.error("收到" + opcode);
			return null;
		} else if (opcode == Opcode.CLOSE) {
			Aio.remove(channelContext, "收到对方请求关闭的消息");
			return null;
		} else {
			Aio.remove(channelContext, "错误的websocket包，错误的Opcode");
			return null;
		}
	}

	/**
	 * 
	 * @author: tanyaowu
	 */
	public WsRequestHandler(WsServerConfig wsServerConfig, String[] scanPackages) {
		this.setWsServerConfig(wsServerConfig);
		this.routes = new Routes(scanPackages);
	}

	//	/**
	//	 * 
	//	 * @author: tanyaowu
	//	 */
	//	public HttpRequestHandler() {
	//	}

	/**
	 * @param args
	 * @author: tanyaowu
	 */
	public static void main(String[] args) {

	}

	/**
	 * @return the wsServerConfig
	 */
	public WsServerConfig getWsServerConfig() {
		return wsServerConfig;
	}

	/**
	 * @param wsServerConfig the wsServerConfig to set
	 */
	public void setWsServerConfig(WsServerConfig wsServerConfig) {
		this.wsServerConfig = wsServerConfig;
	}

}
