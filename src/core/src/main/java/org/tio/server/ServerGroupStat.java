package org.tio.server;

import java.util.concurrent.atomic.AtomicLong;

import org.tio.core.stat.GroupStat;

/**
 * 
 * @author tanyaowu 
 * @创建时间 2016年12月3日 下午2:29:28
 *
 * @操作列表
 *  编号	| 操作时间	| 操作人员	 | 操作说明
 *  (1) | 2016年12月3日 | tanyaowu | 新建类
 *
 */
public class ServerGroupStat extends GroupStat
{

	/**
	 * 
	 *
	 * @author: tanyaowu
	 * @创建时间:　2016年12月3日 下午2:29:28
	 * 
	 */
	public ServerGroupStat()
	{}
	
	/**
	 * 接受了多少连接
	 */
	private AtomicLong accepted = new AtomicLong();
	
	/**
	 * @return the accepted
	 */
	public AtomicLong getAccepted()
	{
		return accepted;
	}
	/**
	 * @param accepted the accepted to set
	 */
	public void setAccepted(AtomicLong accepted)
	{
		this.accepted = accepted;
	}

	

}
