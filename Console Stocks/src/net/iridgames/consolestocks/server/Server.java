package net.iridgames.consolestocks.server;

import java.net.DatagramSocket;
import java.net.SocketException;

public class Server
{
	private String	address;
	private int		port;

	private Thread serverTick = new Thread()
	{
		public void run()
		{
			System.out.println(port);
		}
	};

	public Server(String address, int port)
	{
		this.address = address;
		this.port = port;
	}

	public String getAddress()
	{
		return this.address;
	}

	public int getPort()
	{
		return this.port;
	}

	public void setupServer()
	{
		try
		{
			DatagramSocket ServerSocket = new DatagramSocket(this.port);

			byte[] receiveData = new byte[1024];
			byte[] sendData = new byte[1024];

			serverTick.start();
		}
		catch (SocketException e)
		{
		}
	}
}
