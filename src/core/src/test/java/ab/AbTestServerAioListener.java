package ab;

import org.tio.core.ChannelContext;
import org.tio.core.intf.Packet;
import org.tio.server.intf.ServerAioListener;

/**
 * @author tanyaowu 
 * 2017年6月29日 下午2:17:57
 */
public class AbTestServerAioListener implements ServerAioListener{
	@Override
	public void onAfterConnected(ChannelContext channelContext, boolean b, boolean b1) throws Exception {

	}

	@Override
	public void onAfterSent(ChannelContext channelContext, Packet packet, boolean b) throws Exception {
//		Aio.close(channelContext, "close");
	}

	@Override
	public void onAfterReceived(ChannelContext channelContext, Packet packet, int i) throws Exception {

	}

	@Override
	public void onAfterClose(ChannelContext channelContext, Throwable throwable, String s, boolean b) throws Exception {

	}
}
