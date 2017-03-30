package org.tio.core;

/**
 *  
 * @author tanyaowu 
 * @创建时间 2017年3月23日 上午8:27:50
 *
 * @操作列表
 *  编号	| 操作时间	| 操作人员	 | 操作说明
 *  (1) | 2017年3月23日 | tanyaowu | 新建类
 *
 */
public enum PacketHandlerMode
{
	/**
	 * 处理消息与解码在同一个线程中处理
	 */
	SINGLE_THREAD(1), 
	
	/**
	 * 把packet丢到一个队列中，让线程池去处理
	 */
	QUEUE(2);

	private final int value;

	public static PacketHandlerMode forNumber(int value)
	{
		switch (value)
		{
		case 1:
			return SINGLE_THREAD;
		case 2:
			return QUEUE;
		default:
			return null;
		}
	}

	private PacketHandlerMode(int value)
	{
		this.value = value;
	}

	/**
	 * @return the value
	 */
	public int getValue()
	{
		return value;
	}
}
