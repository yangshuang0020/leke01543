package org.tio.core.intf;

import java.nio.ByteBuffer;

import org.tio.core.ChannelContext;
import org.tio.core.GroupContext;
import org.tio.core.exception.AioDecodeException;

/**
 * The Interface AioHandler.
 *
 * @param <Ext> the generic type
 * @param <P> the generic type
 * @param <R> the generic type
 */
public interface AioHandler<SessionContext, P extends Packet, R>
{

	/**
	 * 处理消息包
	 *
	 * @param packet the packet
	 * @return the r
	 * @author: tanyaowu
	 * @创建时间:　2016年11月15日 上午11:38:52
	 */
	R handler(P packet, ChannelContext<SessionContext, P, R> channelContext) throws Exception;

	/**
	 * 编码
	 *
	 * @param packet the packet
	 * @return the byte buffer
	 * @author: tanyaowu
	 * @创建时间:　2016年11月15日 上午11:38:52
	 */
	ByteBuffer encode(P packet, GroupContext<SessionContext, P, R> groupContext, ChannelContext<SessionContext, P, R> channelContext);

	/**
	 * 根据ByteBuffer解码成业务需要的Packet对象.
	 *
	 * @param buffer the buffer
	 * @return the t
	 * @throws AioDecodeException the aio decode exception
	 */
	P decode(ByteBuffer buffer, ChannelContext<SessionContext, P, R> channelContext) throws AioDecodeException;

}
