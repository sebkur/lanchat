package de.mobanisto.lanchat;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;

public class Sender
{

	private int port;
	private DatagramSocket socket = null;

	public Sender(int port)
	{
		this.port = port;
	}

	public void broadcast(String broadcastMessage, InetAddress address)
			throws IOException
	{
		socket = new DatagramSocket();
		socket.setBroadcast(true);

		byte[] buffer = broadcastMessage.getBytes(UTF_8);

		DatagramPacket packet = new DatagramPacket(buffer, buffer.length,
				address, port);
		socket.send(packet);
		socket.close();
	}

	public List<InetAddress> listAllBroadcastAddresses() throws SocketException
	{
		List<InetAddress> broadcastList = new ArrayList<>();
		Enumeration<NetworkInterface> interfaces = NetworkInterface
				.getNetworkInterfaces();
		while (interfaces.hasMoreElements()) {
			NetworkInterface networkInterface = interfaces.nextElement();

			if (networkInterface.isLoopback() || !networkInterface.isUp()) {
				continue;
			}

			networkInterface.getInterfaceAddresses().stream()
					.map(a -> a.getBroadcast()).filter(Objects::nonNull)
					.forEach(broadcastList::add);
		}
		return broadcastList;
	}

}
