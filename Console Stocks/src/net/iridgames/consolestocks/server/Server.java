package net.iridgames.consolestocks.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class Server
{
	private int		port;
	private boolean	running;

	private DatagramSocket serverSocket;

	private byte[]	receiveData;
	private byte[]	sendData;

	private Thread serverTick = new Thread()
	{
		public void run()
		{
						
			receiveData = new byte[1024];
			sendData = new byte[1024];

			while (running)
			{
				DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
				try
				{
					serverSocket.receive(receivePacket);
					String data = new String(receivePacket.getData()).trim();
					
					InetAddress IPAddress = receivePacket.getAddress();

					int port = receivePacket.getPort();

					String[] dataArray = data.split(" : ");
					
					StringBuilder sb = new StringBuilder();
					
					for (int i = 1; i < dataArray.length; i++)
					{
						sb.append(dataArray[i]);
					}
					
					sendData = (sb.toString()).toUpperCase().getBytes("UTF-8");
					
					DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
					
					
					
					serverSocket.send(sendPacket);
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
			try
			{
				this.join();
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	};

	public Server(int port)
	{
		this.port = port;
		this.running = true;
	}

	public int getPort()
	{
		return this.port;
	}

	public void setupServer()
	{
		try
		{
			serverSocket = new DatagramSocket(port);
		}
		catch (SocketException e1)
		{
			e1.printStackTrace();
		}

		serverTick.start();
	}
}
