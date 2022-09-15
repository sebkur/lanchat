package de.mobanisto.lanchat;

import java.io.IOException;
import java.net.InetAddress;
import java.util.List;

public class Send
{

	public static void main(String[] args) throws IOException
	{
		String message = "LanChat host";
		if (args.length != 0) {
			message = args[0];
		}

		Sender sender = new Sender(5000);
		List<InetAddress> addresses = sender.listAllBroadcastAddresses();
		for (InetAddress address : addresses) {
			sender.broadcast(message, address);
		}
	}

}
