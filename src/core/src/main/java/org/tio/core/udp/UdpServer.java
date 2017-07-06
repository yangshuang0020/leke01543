package org.tio.core.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicLong;

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
		final AtomicLong count = new AtomicLong();
		UdpHandler udpHandler = new UdpHandler() {
			@Override
			public void handler(UdpPacket udpPacket) {
				byte[] data = udpPacket.getData();
				Node remote = udpPacket.getRemote();
				long c = count.incrementAndGet();
				if (c % 100000 == 0) {
					String str = "【" + new String(data) + "】 from " + remote;
					log.error(str);
				}
				
			}};
		UdpServerConf udpServerConf = new UdpServerConf(3000, udpHandler);
		
		UdpServer udpServer = new UdpServer(udpServerConf);
		
		udpServer.start();
	}
}
