package org.tio.websocket.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.intf.TioUuid;

import com.xiaoleilu.hutool.lang.Snowflake;
import com.xiaoleilu.hutool.util.RandomUtil;

/**
 * @author tanyaowu 
 * 2017年6月5日 上午10:44:26
 */
public class WsTioUuid implements TioUuid {
	private static Logger log = LoggerFactory.getLogger(WsTioUuid.class);

//	private long workerId;
//	private long datacenterId;
//	
	private Snowflake snowflake;
	/**
	 * 
	 * @author: tanyaowu
	 */
	public WsTioUuid(long workerId, long datacenterId) {
		snowflake = new Snowflake(workerId, datacenterId);
	}
	
	public WsTioUuid() {
		snowflake = new Snowflake(RandomUtil.randomInt(), RandomUtil.randomInt());
	}

	/**
	 * @param args
	 * @author: tanyaowu
	 */
	public static void main(String[] args) {

	}

	/** 
	 * @return
	 * @author: tanyaowu
	 */
	@Override
	public String uuid() {
		return snowflake.nextId() + "";
	}
}
