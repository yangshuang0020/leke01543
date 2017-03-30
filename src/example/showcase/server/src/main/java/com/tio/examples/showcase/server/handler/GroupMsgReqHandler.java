package org.tio.examples.showcase.server.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.tio.core.ChannelContext;
import org.tio.examples.showcase.common.ShowcasePacket;
import org.tio.examples.showcase.common.ShowcaseSessionContext;
import org.tio.examples.showcase.common.intf.AbsShowcaseBsHandler;
import org.tio.examples.showcase.common.packets.GroupMsgReqBody;

/**
 * @author tanyaowu 
 * 2017年3月27日 下午9:51:28
 */
public class GroupMsgReqHandler extends AbsShowcaseBsHandler<GroupMsgReqBody>
{
	private static Logger log = LoggerFactory.getLogger(GroupMsgReqHandler.class);

	/**
	 * 
	 * @author: tanyaowu
	 */
	public GroupMsgReqHandler()
	{
	}

	/**
	 * @param args
	 * @author: tanyaowu
	 */
	public static void main(String[] args)
	{

	}
	/** 
	 * @return
	 * @author: tanyaowu
	 */
	@Override
	public Class<GroupMsgReqBody> bodyClass()
	{
		return GroupMsgReqBody.class;
	}

	/** 
	 * @param packet
	 * @param bsBody
	 * @param channelContext
	 * @return
	 * @throws Exception
	 * @author: tanyaowu
	 */
	@Override
	public Object handler(ShowcasePacket packet, GroupMsgReqBody bsBody, ChannelContext<ShowcaseSessionContext, ShowcasePacket, Object> channelContext) throws Exception
	{
		return null;
	}
}
