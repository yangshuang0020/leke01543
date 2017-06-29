package ab;

import java.nio.ByteBuffer;

import org.tio.core.Aio;
import org.tio.core.ChannelContext;
import org.tio.core.GroupContext;
import org.tio.core.exception.AioDecodeException;
import org.tio.core.intf.Packet;
import org.tio.server.intf.ServerAioHandler;

/**
 * @author tanyaowu 
 * 2017年6月29日 下午2:16:31
 */
public class AbTestServerAioHandler implements ServerAioHandler {
	@Override
	public Object handler(Packet packet, ChannelContext channelContext) throws Exception {
		Aio.send(channelContext, packet);
		return packet;
	}

	@Override
	public ByteBuffer encode(Packet packet, GroupContext groupContext, ChannelContext channelContext) {
		String retVal = "HTTP/1.1 200 OK\r\n" + "Server: Tio-HttpServer/0.0.1\r\n" + "Connection: keep-alive\r\n" + "Content-Length: 2\r\n"
				+ "Date: Thu, 05 Jan 2017 04:55:20 GMT\r\n" + "Content-Type: text/html\r\n" + "\r\n" + "OK\r\n\r\n";
		ByteBuffer byteBuffer = ByteBuffer.wrap(retVal.getBytes());
		byteBuffer.position(byteBuffer.limit());
		return byteBuffer;
	}

	@Override
	public Packet decode(ByteBuffer byteBuffer, ChannelContext channelContext) throws AioDecodeException {
		byteBuffer.position(byteBuffer.limit());
		Packet packet = new Packet();
		return packet;
	}
}
