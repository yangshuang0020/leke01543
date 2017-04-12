package com.runyuan.scan.network.common.intf;

import com.runyuan.scan.network.common.Const;
import com.runyuan.scan.network.common.ScanPacket;
import com.runyuan.scan.network.common.ScanSessionContext;
import com.runyuan.scan.network.common.json.Json;
import com.runyuan.scan.network.packets.BaseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.ChannelContext;

/**
 * @author tanyaowu 
 * 2017年3月27日 下午9:56:16
 */
public abstract class AbsScanBsHandler<T extends BaseBody> implements ScanBsHandlerIntf
{
	private static Logger log = LoggerFactory.getLogger(AbsScanBsHandler.class);

	/**
	 * 
	 * @author: tanyaowu
	 */
	public AbsScanBsHandler()
	{
	}

	public abstract Class<T> bodyClass();

	public abstract Object handler(ScanPacket packet, T bsBody, ChannelContext<ScanSessionContext, ScanPacket, Object> channelContext) throws Exception;

	@Override
	public Object handler(ScanPacket packet, ChannelContext<ScanSessionContext, ScanPacket, Object> channelContext) throws Exception
	{
		String jsonStr = null;
		T bsBody = null;
		if (packet.getBody() != null)
		{
			jsonStr = new String(packet.getBody(), Const.CHARSET);
			bsBody = Json.toBean(jsonStr, bodyClass());
		}

		return handler(packet, bsBody, channelContext);
	}

}
