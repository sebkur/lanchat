package de.mobanisto.lanchat;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SendFile
{

	public static void main(String[] args)
			throws UnknownHostException, IOException
	{
		if (args.length != 2) {
			System.out.print("usage: send-file <ip> <file>");
			System.exit(1);
		}

		String argIp = args[0];
		String argInput = args[1];
		Path input = Paths.get(argInput);

		System.out.println("Connecting...");
		try (Socket socket = new Socket(argIp, 5001)) {
			System.out.println("Sending file...");
			OutputStream os = socket.getOutputStream();
			WritableByteChannel channel = Channels.newChannel(os);
			FileChannel inputChannel = FileChannel.open(input);
			long transferred = inputChannel.transferTo(0, Integer.MAX_VALUE,
					channel);
			System.out.println(String.format("Sent %d bytes", transferred));
		}
	}

}
