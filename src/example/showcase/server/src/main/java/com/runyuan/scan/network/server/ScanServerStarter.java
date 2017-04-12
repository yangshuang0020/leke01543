package com.runyuan.scan.network.server;


import com.runyuan.scan.network.common.Const;
import com.runyuan.scan.network.common.ScanPacket;
import com.runyuan.scan.network.common.ScanSessionContext;
import org.tio.server.AioServer;
import org.tio.server.ServerGroupContext;
import org.tio.server.intf.ServerAioHandler;
import org.tio.server.intf.ServerAioListener;

import java.io.IOException;

/**
 * 
 * @author tanyaowu 
 * 2017年3月27日 上午12:16:31
 */
public class ScanServerStarter
{
	static ServerAioHandler<ScanSessionContext, ScanPacket, Object> aioHandler = new ScanServerAioHandler();
	static ServerAioListener<ScanSessionContext, ScanPacket, Object> aioListener = new ScanServerAioListener();
	static ServerGroupContext<ScanSessionContext, ScanPacket, Object> serverGroupContext = new ServerGroupContext<>(aioHandler, aioListener);
	static AioServer<ScanSessionContext, ScanPacket, Object> aioServer = new AioServer<>(serverGroupContext); //可以为空
	
	static String serverIp = null;
	static int serverPort = Const.PORT;

	public static void main(String[] args) throws IOException
	{

		aioServer.start(serverIp, serverPort);
	}
}