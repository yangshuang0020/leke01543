package ab;

import java.io.IOException;

import org.tio.core.intf.Packet;
import org.tio.server.AioServer;
import org.tio.server.ServerGroupContext;

/**
 * @author tanyaowu 
 * 2017年6月29日 下午1:10:03
 */
public class TioAbTest {
	private class TioPacket extends Packet {

	}

	/**
	 * 测试脚本: ab -c 100 -n 2000 -k http://127.0.0.1:28080/test/
	 */
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws IOException {
		ServerGroupContext<Object, TioPacket, Object> serverGroupContext = new ServerGroupContext<Object, TioPacket, Object>(new AbTestServerAioHandler(),
				new AbTestServerAioListener());

		HttpUuid imTioUuid = new HttpUuid();
		serverGroupContext.setTioUuid(imTioUuid);

		//aioServer对象
		AioServer<Object, TioPacket, Object> aioServer = new AioServer<>(serverGroupContext);

		//有时候需要绑定ip，不需要则null
		String serverIp = "127.0.0.1";

		//监听的端口
		int serverPort = 28080;

		aioServer.start(serverIp, serverPort);

	}
}
