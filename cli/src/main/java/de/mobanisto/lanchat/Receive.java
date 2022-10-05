package de.mobanisto.lanchat;

public class Receive
{

	public static void main(String[] args)
	{
		Receiver receiver = new Receiver(5000, (source, message) -> {
			System.out.println(String.format("received from %s: %s", source, message));
		});
		receiver.run();
	}

}
