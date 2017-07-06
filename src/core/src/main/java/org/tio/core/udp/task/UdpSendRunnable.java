package org.tio.core.udp.task;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.udp.UdpClientConf;

/**
 * @author tanyaowu 
 * 2017年7月5日 下午5:54:13
 */
public class UdpSendRunnable implements Runnable {
	private static Logger log = LoggerFactory.getLogger(UdpSendRunnable.class);

	private LinkedBlockingQueue<DatagramPacket> queue;

	private UdpClientConf udpClientConf;

	private boolean isStopped = false;

	/**
	 * 
	 * @author: tanyaowu
	 */
	public UdpSendRunnable(LinkedBlockingQueue<DatagramPacket> queue, UdpClientConf udpClientConf) {
		this.queue = queue;
		this.udpClientConf = udpClientConf;
	}

	/**
	 * @param args
	 * @author: tanyaowu
	 */
	public static void main(String[] args) {

	}

	@Override
	public void run() {
		DatagramSocket datagramSocket = null;
		while (!isStopped) {
			try {
				DatagramPacket datagramPacket = queue.take();
				if (datagramSocket == null) {
					datagramSocket = new DatagramSocket();
					datagramSocket.setSoTimeout(udpClientConf.getTimeout());
				}
				datagramSocket.send(datagramPacket);

			} catch (Throwable e) {
				log.error(e.toString(), e);
			} finally {
				if (queue.size() == 0) {
					if (datagramSocket != null) {
						datagramSocket.close();
						datagramSocket = null;
					}
				}
			}
		}
	}

	public void stop() {
		isStopped = true;
	}
}
