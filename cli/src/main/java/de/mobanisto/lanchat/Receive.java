package de.mobanisto.lanchat;

public class Receive
{

	public static void main(String[] args)
	{
		Receiver receiver = new Receiver(5000);
		receiver.run();
	}

}
