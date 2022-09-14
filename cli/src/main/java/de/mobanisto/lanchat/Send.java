package de.mobanisto.lanchat;

import java.io.IOException;
import java.net.InetAddress;
import java.util.List;

public class Send
{

	public static void main(String[] args) throws IOException
	{
		Sender sender = new Sender(5000);
		List<InetAddress> addresses = sender.listAllBroadcastAddresses();
		for (InetAddress address : addresses) {
			sender.broadcast("LanChat host", address);
		}
	}

}
