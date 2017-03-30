/**
 * **************************************************************************
 *
 * @说明: 
 * @项目名称: tio-examples-server
 *
 * @author: tanyaowu 
 * @创建时间: 2016年11月17日 下午5:59:24
 *
 * **************************************************************************
 */
package org.tio.examples.helloworld.client;

import org.tio.client.AioClient;
import org.tio.client.ClientChannelContext;
import org.tio.client.ClientGroupContext;
import org.tio.client.ReconnConf;
import org.tio.client.intf.ClientAioHandler;
import org.tio.client.intf.ClientAioListener;
import org.tio.core.Aio;
import org.tio.core.Node;
import org.tio.examples.helloworld.common.Const;
import org.tio.examples.helloworld.common.HelloPacket;

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
public class HelloClientStarter
{
	private static Node serverNode = null;
	private static AioClient<Object, HelloPacket, Object> aioClient;
	private static ClientGroupContext<Object, HelloPacket, Object> clientGroupContext = null;
	private static ClientAioHandler<Object, HelloPacket, Object> aioClientHandler = null;
	private static ClientAioListener<Object, HelloPacket, Object> aioListener = null;
	
	//用来自动连接的，不想自动连接请设为null
	private static ReconnConf<Object, HelloPacket, Object> reconnConf = new ReconnConf<Object, HelloPacket, Object>(5000L);

	public static void main(String[] args) throws Exception
	{
		String serverIp = "127.0.0.1";
		int serverPort = Const.PORT;
		serverNode = new Node(serverIp, serverPort);
		aioClientHandler = new HelloClientAioHandler();
		aioListener = null;

		clientGroupContext = new ClientGroupContext<>(aioClientHandler, aioListener, reconnConf);
		aioClient = new AioClient<>(clientGroupContext);

		ClientChannelContext<Object, HelloPacket, Object> clientChannelContext = aioClient.connect(serverNode);

		//以下内容不是启动的过程，而是属于发消息的过程
		HelloPacket packet = new HelloPacket();
		packet.setBody("hello world".getBytes(HelloPacket.CHARSET));
		Aio.send(clientChannelContext, packet);
	}
}
