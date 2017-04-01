package org.tio.core.maintain;

import java.util.concurrent.locks.Lock;

import org.apache.commons.collections4.bidimap.DualHashBidiMap;
import org.tio.core.ChannelContext;
import org.tio.core.Node;
import org.tio.core.ObjWithLock;
import org.tio.core.intf.Packet;

/**
 * 
 * @author tanyaowu 
 * 2017年4月1日 上午9:35:20
 */
public class ClientNodes<SessionContext, P extends Packet, R>
{

	/** remoteAndChannelContext key: "ip:port" value: ChannelContext. */
	private ObjWithLock<DualHashBidiMap<String, ChannelContext<SessionContext, P, R>>> map = new ObjWithLock<DualHashBidiMap<String, ChannelContext<SessionContext, P, R>>>(
			new DualHashBidiMap<String, ChannelContext<SessionContext, P, R>>());

	/**
	 * Gets the key.
	 *
	 * @param <Ext> the generic type
	 * @param <P> the generic type
	 * @param <R> the generic type
	 * @param channelContext the channel context
	 * @return the key
	 */
	public static <SessionContext, P extends Packet, R> String getKey(ChannelContext<SessionContext, P, R> channelContext)
	{
		Node clientNode = channelContext.getClientNode();
		if (clientNode == null)
		{
			throw new RuntimeException("client node is null");
		}
		String key = getKey(clientNode.getIp(), clientNode.getPort());
		return key;
	}

	/**
	 * Gets the key.
	 *
	 * @param <Ext> the generic type
	 * @param <P> the generic type
	 * @param <R> the generic type
	 * @param ip the ip
	 * @param port the port
	 * @return the key
	 */
	public static String getKey(String ip, int port)
	{
		String key = ip + ":" + port;
		return key;
	}

	/**
	 * Removes映射
	 *
	 * @param <Ext> the generic type
	 * @param <P> the generic type
	 * @param <R> the generic type
	 * @param channelContext the channel context
	 */
	public void remove(ChannelContext<SessionContext, P, R> channelContext)
	{
		Lock lock = map.getLock().writeLock();
		DualHashBidiMap<String, ChannelContext<SessionContext, P, R>> m = map.getObj();
		try
		{
			lock.lock();
			m.removeValue(channelContext);
		} catch (Exception e)
		{
			throw e;
		} finally
		{
			lock.unlock();
		}
	}

	/**
	 * 添加映射.
	 *
	 * @param <Ext> the generic type
	 * @param <P> the generic type
	 * @param <R> the generic type
	 * @param channelContext the channel context
	 * @author: tanyaowu
	 */
	public void put(ChannelContext<SessionContext, P, R> channelContext)
	{
		String key = getKey(channelContext);
		Lock lock = map.getLock().writeLock();
		DualHashBidiMap<String, ChannelContext<SessionContext, P, R>> m = map.getObj();

		try
		{
			lock.lock();
			m.put(key, channelContext);
		} catch (Exception e)
		{
			throw e;
		} finally
		{
			lock.unlock();
		}
	}

	/**
	 * 
	 * @param ip
	 * @param port
	 * @return
	 *
	 * @author: tanyaowu
	 *
	 */
	public ChannelContext<SessionContext, P, R> find(String ip, int port)
	{
		String key = getKey(ip, port);
		return find(key);
	}

	public ChannelContext<SessionContext, P, R> find(String key)
	{
		Lock lock = map.getLock().readLock();
		DualHashBidiMap<String, ChannelContext<SessionContext, P, R>> m = map.getObj();

		try
		{
			lock.lock();
			return m.get(key);
		} catch (Exception e)
		{
			throw e;
		} finally
		{
			lock.unlock();
		}
	}
	/**
	 * @return the map
	 */
	public ObjWithLock<DualHashBidiMap<String, ChannelContext<SessionContext, P, R>>> getMap()
	{
		return map;
	}

}
