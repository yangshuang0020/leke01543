package org.tio.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousSocketChannel;

import org.tio.core.ChannelContext;
import org.tio.core.GroupContext;
import org.tio.core.Node;
import org.tio.core.intf.Packet;

/**
 * 
 * @author tanyaowu 
 * @创建时间 2016年12月6日 下午12:17:30
 *
 * @操作列表
 *  编号	| 操作时间	| 操作人员	 | 操作说明
 *  (1) | 2016年12月6日 | tanyaowu | 新建类
 *
 */
public class ServerChannelContext<SessionContext, P extends Packet, R> extends ChannelContext<SessionContext, P, R>
{

	/**
	 * @param groupContext
	 * @param asynchronousSocketChannel
	 *
	 * @author: tanyaowu
	 * @创建时间:　2016年12月6日 下午12:17:59
	 * 
	 */
	public ServerChannelContext(GroupContext<SessionContext, P, R> groupContext, AsynchronousSocketChannel asynchronousSocketChannel)
	{
		super(groupContext, asynchronousSocketChannel);
	}

	/** 
	 * @see org.tio.core.ChannelContext#createClientNode(java.nio.channels.AsynchronousSocketChannel)
	 * 
	 * @param asynchronousSocketChannel
	 * @return
	 * @throws IOException 
	 * @重写人: tanyaowu
	 * @重写时间: 2016年12月6日 下午12:18:08
	 * 
	 */
	@Override
	public Node createClientNode(AsynchronousSocketChannel asynchronousSocketChannel) throws IOException
	{
		InetSocketAddress inetSocketAddress = (InetSocketAddress) asynchronousSocketChannel.getRemoteAddress();
		Node clientNode = new Node(inetSocketAddress.getHostString(), inetSocketAddress.getPort());
		return clientNode;
	}

}
