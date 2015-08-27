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

	private DatagramSocket clientSocket;

	private InetAddress IPAddress;

	private byte[]	receiveData;
	private byte[]	sendData;

	public Client(String name, String address, int port)
	{
		this.userName = name;
		this.address = address;
		this.port = port;
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

	public void sendMessage(String message)
	{
		String messageOut = this.userName + " : " + message;
		
		try
		{
			clientSocket = new DatagramSocket();
			IPAddress = InetAddress.getByName(getAddress());

			sendData = new byte[1024];
			receiveData = new byte[1024];
			
			sendData = messageOut.getBytes();
			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, this.port);
			try
			{
				clientSocket.send(sendPacket);
				DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
				clientSocket.receive(receivePacket);

				String receiveData = new String(receivePacket.getData()).trim();
				
				System.out.println(receiveData);

			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		catch (SocketException | UnknownHostException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}


	}
}
