package net.iridgames.consolestocks.client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Client
{
	private String	userName;
	private String	address;
	private int		port;
	
	public boolean isRunning;

	private DatagramSocket clientSocket;

	private InetAddress IPAddress;

	private byte[]	receiveData;
	private byte[]	sendData;

	private Thread receiveThread = new Thread("ClientReceiveThread")
	{
		public void run()
		{
			isRunning = true;
			
			while(isRunning)
			{
				try
				{
					receiveData = new byte[1024];
					DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
					clientSocket.receive(receivePacket);
					String receiveData = new String(receivePacket.getData()).trim();
					
					System.out.println(receiveData.toString());
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

	public Client(String name, String address, int port)
	{
		this.userName = name;
		this.address = address;
		this.port = port;
		
		this.setup();
	}

	public String getName()
	{
		return this.userName;
	}

	public String getAddress()
	{
		return this.address;
	}

	public int getPort()
	{
		return this.getPort();
	}
	
	public void setup()
	{
		try
		{
			clientSocket = new DatagramSocket();
			IPAddress = InetAddress.getByName(getAddress());

			sendData = new byte[1024];
			receiveData = new byte[1024];
		}
		catch (SocketException | UnknownHostException e)
		{
			e.printStackTrace();
		}
		
		receiveThread.start();
	}

	public void sendMessage(String message)
	{
		sendData = null;
		String messageOut = this.userName + " : " + message;
		sendData = messageOut.getBytes();
		
		try
		{
			clientSocket.send(new DatagramPacket(sendData, sendData.length, this.IPAddress, this.port));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
