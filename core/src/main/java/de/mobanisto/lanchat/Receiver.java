package de.mobanisto.lanchat;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Receiver
{

	private int port;

	public Receiver(int port)
	{
		this.port = port;
	}

	public void run()
	{
		try (DatagramSocket serverSocket = new DatagramSocket(port)) {
			byte[] receiveData = new byte[8];

			System.out.println(String.format("Listening on %s:%d",
					InetAddress.getLocalHost().getHostAddress(), port));
			DatagramPacket receivePacket = new DatagramPacket(receiveData,
					receiveData.length);

			while (true) {
				serverSocket.receive(receivePacket);
				String message = new String(receivePacket.getData(), 0,
						receivePacket.getLength(), UTF_8);
				InetAddress source = receivePacket.getAddress();
				System.out.println(
						String.format("received from %s: %s", source, message));
			}
		} catch (IOException e) {
			System.out.println(e);
		}
	}

}
