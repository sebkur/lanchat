package de.mobanisto.lanchat;

import jdk.vm.ci.code.site.Call;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Receiver
{

	public interface Callback {
		public void received(InetAddress source, String message);
	}

	private int port;
	private Callback callback;

	public Receiver(int port, Callback callback)
	{
		this.port = port;
		this.callback = callback;
	}

	public void run()
	{
		try (DatagramSocket serverSocket = new DatagramSocket(port)) {
			byte[] receiveData = new byte[65507];

			System.out.println(String.format("Listening on %s:%d",
					InetAddress.getLocalHost().getHostAddress(), port));
			DatagramPacket receivePacket = new DatagramPacket(receiveData,
					receiveData.length);

			while (true) {
				serverSocket.receive(receivePacket);
				String message = new String(receivePacket.getData(), 0,
						receivePacket.getLength(), UTF_8);
				InetAddress source = receivePacket.getAddress();
				callback.received(source, message);
			}
		} catch (IOException e) {
			System.out.println(e);
		}
	}

}
