package com.runyuan.scan.network.common;

import org.tio.core.intf.Packet;

/**
 *
 * @author tanyaowu
 */
public class ScanPacket extends Packet
{
	public static final int HEADER_LENGHT = 4;//消息头的长度4
	public static final String CHARSET = "gb2312";

	public ScanPacket()
	{
		super();
	}

	/**
	 * @param type
	 * @param body
	 * @author: tanyaowu
	 */
	public ScanPacket(byte type, byte[] body)
	{
		super();
		this.type = type;
		this.body = body;
	}

	/**
	 * 消息类型，其值在org.tio.examples.showcase.common.Type中定义
	 */
	private byte type;

	private byte[] body;

	/**
	 * @return the body
	 */
	public byte[] getBody()
	{
		return body;
	}

	/**
	 * @param body the body to set
	 */
	public void setBody(byte[] body)
	{
		this.body = body;
	}

	/**
	 * @return the type
	 */
	public byte getType()
	{
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(byte type)
	{
		this.type = type;
	}

	public String logstr()
	{
		return "" + type;
	}
}
