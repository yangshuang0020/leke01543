package com.runyuan.scan.network.common.intf;

import com.runyuan.scan.network.common.ScanPacket;
import com.runyuan.scan.network.common.ScanSessionContext;
import org.tio.core.ChannelContext;

/**
 * 业务处理器接口
 * @author tanyaowu 
 * 2017年3月27日 下午9:52:42
 */
public interface ScanBsHandlerIntf
{
	
	/**
	 * 
	 * @param packet
	 * @param channelContext
	 * @return
	 * @throws Exception
	 * @author: tanyaowu
	 */
	public Object handler(ScanPacket packet, ChannelContext<ScanSessionContext, ScanPacket, Object> channelContext)  throws Exception;

}
