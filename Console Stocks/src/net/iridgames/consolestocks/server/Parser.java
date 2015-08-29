package net.iridgames.consolestocks.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.Arrays;

public class Parser
{
	private Server server;
	private Stocks stocks;

	public Parser(Server server)
	{
		this.server = server;
		this.stocks = new Stocks();
	}

	public void parseMessage(String message, String sender, InetAddress ip, int port)
	{
		try
		{
			String[] msg = message.split(" ");
			
			System.out.println(msg.toString());
			switch (msg[0].toUpperCase())
			{
				case "GETSTOCKS":
					for (int i = 0; i < stocks.stockList.size(); i++)
					{
						sendMessage(stocks.stockList.get(i).getName(), ip, port);
					}
					break;
				case "GETSTOCK":
					sendMessage("" + stocks.getStock(msg[1]).getValue(), ip, port);
					break;
				case "PING":
					sendMessage("PONG!", ip, port);
					break;
				default:
					sendMessage("Invalid command.", ip, port);
					break;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			sendMessage("Internal Server Error.", ip, port);
		}
	}

	public void sendMessage(String message, InetAddress ip, int port)
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
}
