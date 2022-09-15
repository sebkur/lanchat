package de.mobanisto.lanchat;

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;
import static java.nio.file.StandardOpenOption.WRITE;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ReceiveFile
{

	public static void main(String[] args) throws IOException
	{
		if (args.length != 1) {
			System.out.println("usage: receive-file <file>");
			System.exit(1);
		}

		String argOutput = args[0];
		Path output = Paths.get(argOutput);

		try (ServerSocket serverSocket = new ServerSocket(5001)) {
			System.out.println("Waiting for incoming file...");
			Socket socket = serverSocket.accept();
			System.out.println("Receiving file...");
			InputStream input = socket.getInputStream();
			try (FileChannel channel = FileChannel.open(output, CREATE,
					TRUNCATE_EXISTING, WRITE);
					ReadableByteChannel inputChannel = Channels
							.newChannel(input);) {
				long transferred = channel.transferFrom(inputChannel, 0,
						Integer.MAX_VALUE);
				System.out.println(
						String.format("Received %d bytes", transferred));
			}
		}
	}

}
