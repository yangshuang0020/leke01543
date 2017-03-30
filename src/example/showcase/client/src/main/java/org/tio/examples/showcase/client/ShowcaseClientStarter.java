package org.tio.examples.showcase.client;

import org.tio.client.AioClient;
import org.tio.client.ClientChannelContext;
import org.tio.client.ClientGroupContext;
import org.tio.client.ReconnConf;
import org.tio.client.intf.ClientAioHandler;
import org.tio.client.intf.ClientAioListener;
import org.tio.core.Aio;
import org.tio.core.Node;
import org.tio.examples.showcase.common.Const;
import org.tio.examples.showcase.common.ShowcasePacket;
import org.tio.examples.showcase.common.ShowcaseSessionContext;

/**
 * 
 * @author tanyaowu 
 * @创建时间 2016年11月17日 下午5:59:24
 *
 * @操作列表
 *  编号	| 操作时间	| 操作人员	 | 操作说明
 *  (1) | 2016年11月17日 | tanyaowu | 新建类
 *
 */
public class ShowcaseClientStarter
{
	private static Node serverNode = null;
	private static AioClient<ShowcaseSessionContext, ShowcasePacket, Object> aioClient;
	private static ClientGroupContext<ShowcaseSessionContext, ShowcasePacket, Object> clientGroupContext = null;
	private static ClientAioHandler<ShowcaseSessionContext, ShowcasePacket, Object> aioClientHandler = null;
	private static ClientAioListener<ShowcaseSessionContext, ShowcasePacket, Object> aioListener = new ShowcaseClientAioListener();
	
	//用来自动连接的，不想自动连接请设为null
	private static ReconnConf<ShowcaseSessionContext, ShowcasePacket, Object> reconnConf = new ReconnConf<ShowcaseSessionContext, ShowcasePacket, Object>(5000L);

	public static void main(String[] args) throws Exception
	{
		String serverIp = "127.0.0.1";
		int serverPort = Const.PORT;
		serverNode = new Node(serverIp, serverPort);
		aioClientHandler = new ShowcaseClientAioHandler();
		aioListener = null;

		clientGroupContext = new ClientGroupContext<>(aioClientHandler, aioListener, reconnConf);
		aioClient = new AioClient<>(clientGroupContext);

		ClientChannelContext<ShowcaseSessionContext, ShowcasePacket, Object> clientChannelContext = aioClient.connect(serverNode);

		//以下内容不是启动的过程，而是属于发消息的过程
		ShowcasePacket packet = new ShowcasePacket();
		packet.setBody("show case".getBytes(ShowcasePacket.CHARSET));
		Aio.send(clientChannelContext, packet);
	}
}
