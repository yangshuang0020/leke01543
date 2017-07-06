package org.tio.core.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.Node;
import org.tio.core.udp.intf.UdpHandler;
import org.tio.core.udp.task.UdpHandlerRunnable;

/**
 * @author tanyaowu 
 * 2017年7月5日 下午2:47:16
 */
public class UdpServer {
	private static Logger log = LoggerFactory.getLogger(UdpServer.class);

	private LinkedBlockingQueue<UdpPacket> queue = new LinkedBlockingQueue<>();

	private DatagramSocket datagramSocket = null;

	private byte[] readBuf = null;

	private boolean isStopped = false;

	private UdpHandlerRunnable udpHandlerRunnable;

	/**
	 * 
	 * @author: tanyaowu
	 * @throws SocketException 
	 */
	public UdpServer(UdpServerConf udpServerConf) throws SocketException {
		this.udpServerConf = udpServerConf;
		datagramSocket = new DatagramSocket(this.udpServerConf.getServerNode().getPort());
		readBuf = new byte[this.udpServerConf.getReadBufferSize()];
		udpHandlerRunnable = new UdpHandlerRunnable(udpServerConf.getUdpHandler(), queue);
	}

	private UdpServerConf udpServerConf;

	public void start() {
		startListen();
		startHandler();
	}

	private void startListen() {
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				String startLog = "started tio udp server: " + udpServerConf.getServerNode();
				if (log.isInfoEnabled()) {
					log.info(startLog);
				} else {
					System.out.println(startLog);
				}
				
				
				while (!isStopped) {
					try {
						DatagramPacket datagramPacket = new DatagramPacket(readBuf, readBuf.length);
						datagramSocket.receive(datagramPacket);

						byte[] data = new byte[datagramPacket.getLength()];
						System.arraycopy(readBuf, 0, data, 0, datagramPacket.getLength());

						String remoteip = datagramPacket.getAddress().getHostAddress();
						int remoteport = datagramPacket.getPort();
						Node remote = new Node(remoteip, remoteport);
						UdpPacket udpPacket = new UdpPacket(data, remote);

						queue.put(udpPacket);
					} catch (Exception e) {
						log.error(e.toString(), e);
					}
				}
			}
		};

		Thread thread = new Thread(runnable, "tio-udp-server-listen");
		thread.setDaemon(false);
		thread.start();
	}

	private void startHandler() {
		Thread thread = new Thread(udpHandlerRunnable, "tio-udp-server-handler");
		thread.setDaemon(false);
		thread.start();
	}

	public void stop() {
		isStopped = true;
		datagramSocket.close();
		udpHandlerRunnable.stop();
	}

	/**
	 * @param args
	 * @author: tanyaowu
	 */
	public static void main(String[] args) throws IOException {
		UdpHandler udpHandler = new UdpHandler() {
			@Override
			public void handler(UdpPacket udpPacket) {
				byte[] data = udpPacket.getData();
				Node remote = udpPacket.getRemote();
				String str = "【" + new String(data) + "】 from " + remote;
				System.out.println(str);
			}};
		UdpServerConf udpServerConf = new UdpServerConf(3000, udpHandler);
		
		UdpServer udpServer = new UdpServer(udpServerConf);
		
		udpServer.start();

		//		String str_send = "Hello UDPclient";
		//		byte[] buf = new byte[1024];
		//		//服务端在3000端口监听接收到的数据  
		//		DatagramSocket ds = new DatagramSocket(3000);
		//		//接收从客户端发送过来的数据  
		//		DatagramPacket dp_receive = new DatagramPacket(buf, 1024);
		//		System.out.println("server is on，waiting for client to send data......");
		//		boolean f = true;
		//		while (f) {
		//			//服务器端接收来自客户端的数据  
		//			ds.receive(dp_receive);
		//			//			System.out.println("server received data from client：");
		//			byte[] dp_receivedData = new byte[dp_receive.getLength()];
		//			System.arraycopy(buf, 0, dp_receivedData, 0, dp_receive.getLength());
		//
		//			System.out.println(dp_receive.getOffset());
		//			String str_receive = new String(dp_receivedData, 0, dp_receive.getLength()) + " from " + dp_receive.getAddress().getHostAddress() + ":" + dp_receive.getPort();
		//			System.out.println(str_receive);
		//			//数据发动到客户端的3000端口  
		//			DatagramPacket dp_send = new DatagramPacket(str_send.getBytes(), str_send.length(), dp_receive.getAddress(), 9000);
		//			ds.send(dp_send);
		//			//由于dp_receive在接收了数据之后，其内部消息长度值会变为实际接收的消息的字节数，  
		//			//所以这里要将dp_receive的内部消息长度重新置为1024  
		//			dp_receive.setLength(1024);
		//		}
		//		ds.close();
	}
}
