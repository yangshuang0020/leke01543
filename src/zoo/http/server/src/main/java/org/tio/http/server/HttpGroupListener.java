package org.tio.http.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.ChannelContext;
import org.tio.core.intf.GroupListener;
import org.tio.http.common.HttpPacket;
import org.tio.http.common.HttpSessionContext;

/**
 * @author tanyaowu 
 * 2017年5月13日 下午10:38:36
 */
public class HttpGroupListener implements GroupListener<HttpSessionContext, HttpPacket, Object>{
	private static Logger log = LoggerFactory.getLogger(HttpGroupListener.class);

	/**
	 * 
	 * @author: tanyaowu
	 */
	public HttpGroupListener() {
	}

	/**
	 * @param args
	 * @author: tanyaowu
	 */
	public static void main(String[] args) {

	}

	/** 
	 * @param channelContext
	 * @param group
	 * @throws Exception
	 * @author: tanyaowu
	 */
	@Override
	public void onAfterBind(ChannelContext<HttpSessionContext, HttpPacket, Object> channelContext, String group) throws Exception {
	}

	/** 
	 * @param channelContext
	 * @param group
	 * @throws Exception
	 * @author: tanyaowu
	 */
	@Override
	public void onAfterUnbind(ChannelContext<HttpSessionContext, HttpPacket, Object> channelContext, String group) throws Exception {
//		ImSessionContext httpSessionContext = channelContext.getSessionContext();
//		ExitGroupNotifyRespBody exitGroupNotifyRespBody = ExitGroupNotifyRespBody.newBuilder().setGroup(group).setClient(httpSessionContext.getClient()).build();
//		WebsocketPacket respPacket2 = new WebsocketPacket(Command.COMMAND_EXIT_GROUP_NOTIFY_RESP, exitGroupNotifyRespBody.toByteArray());
//		Aio.sendToGroup(channelContext.getGroupContext(), group, respPacket2);
	
	}
}
