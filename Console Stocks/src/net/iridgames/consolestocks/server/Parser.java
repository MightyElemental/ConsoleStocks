package net.iridgames.consolestocks.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.ArrayList;

public class Parser
{
	private Server server;
	private Stocks stocks;
	private ArrayList<InetAddress> addressList = new ArrayList<InetAddress>();
	private int port;
	
	public Parser(Server server, int port)
	{
		this.server = server;
		this.stocks = new Stocks();
		this.port = port;
	}

	public void parseMessage(String message, String sender, InetAddress ip, int port)
	{
		if(!addressList.contains(ip))
		{
			addressList.add(ip);
		}
		
		try
		{
			String[] msg = message.split(" ");
			
			for (int i = 0; i < msg.length; i++)
			{
				System.out.println(msg[i] + ":):):):):):):");
			}
			
			switch (msg[0].toUpperCase())
			{
				case "GETSTOCKS":
					for (int i = 0; i < stocks.stockList.size(); i++)
					{
						sendMessage(stocks.stockList.get(i).getName(), ip);
					}
					break;
				case "GETSTOCK":
					System.out.println(msg[1]);
					sendMessage("" + stocks.getStock(msg[1]).getValue(), ip);
					break;
				case "PING":
					sendMessage("PONG!", ip);
					break;
				default:
					sendMessage("Invalid command.", ip);
					break;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			sendMessage("Internal Server Error.", ip);
		}
	}

	public void sendMessage(String message, InetAddress ip)
	{
		String sendMessage = message;
		
		try
		{
			server.sendData = (sendMessage.toString()).getBytes("UTF-8");
			DatagramPacket sendPacket = new DatagramPacket(server.sendData, server.sendData.length, ip, port);
			server.serverSocket.send(sendPacket);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public void broadCastmessage(String message)
	{
		for (int i = 0; i < addressList.size(); i++)
		{
			sendMessage(message, addressList.get(i));
		}
	}
}
