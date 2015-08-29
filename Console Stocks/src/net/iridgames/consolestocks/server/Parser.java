package net.iridgames.consolestocks.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;

public class Parser
{
	private Server server;

	public Parser(Server server)
	{
		this.server = server;
	}

	public void parseMessage(String message, String sender, InetAddress ip, int port)
	{
		try
		{
			sendMessage(message + " sent by " + sender + " @ " + ip.getHostAddress(), ip, port);

			String[] msg = message.split(" ");
			switch (msg[0].toUpperCase())
			{
				case "PING":
					sendMessage("PONG", ip, port);
					break;
				default:
					sendMessage("INVALID COMMAND", ip, port);
					break;
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
			sendMessage("INTERNAL SERVER ERROR", ip, port);
		}
	}

	public void sendMessage(String message, InetAddress ip, int port)
	{
		try
		{
			server.sendData = (message.toString()).getBytes("UTF-8");
			DatagramPacket sendPacket = new DatagramPacket(server.sendData, server.sendData.length, ip, port);
			server.serverSocket.send(sendPacket);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
