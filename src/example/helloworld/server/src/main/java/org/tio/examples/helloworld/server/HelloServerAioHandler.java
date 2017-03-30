package org.tio.examples.helloworld.server;

import org.tio.core.Aio;
import org.tio.core.ChannelContext;
import org.tio.examples.helloworld.common.HelloAbsAioHandler;
import org.tio.examples.helloworld.common.HelloPacket;
import org.tio.server.intf.ServerAioHandler;

/**
 * 
 * @author tanyaowu 
 * @创建时间 2016年11月18日 上午9:13:15
 *
 * @操作列表
 *  编号	| 操作时间	| 操作人员	 | 操作说明
 *  (1) | 2016年11月18日 | tanyaowu | 新建类
 *
 */
public class HelloServerAioHandler extends HelloAbsAioHandler implements ServerAioHandler<Object, HelloPacket, Object>
{
	/** 
	 * 处理消息
	 */
	@Override
	public Object handler(HelloPacket packet, ChannelContext<Object, HelloPacket, Object> channelContext) throws Exception
	{
		byte[] body = packet.getBody();
		if (body != null)
		{
			String str = new String(body, HelloPacket.CHARSET);
			System.out.println("收到消息：" + str);

			HelloPacket resppacket = new HelloPacket();
			resppacket.setBody(("收到了你的消息，你的消息是:" + str).getBytes(HelloPacket.CHARSET));
			Aio.send(channelContext, resppacket);
		}
		return null;
	}
}
