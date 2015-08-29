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
		String[] msg = message.split(" ");
		String[] args = new String[msg.length - 1];
		String command = msg[0];

		for (int i = 1; i < msg.length; i++)
		{
			args[i] = msg[i];
		}

		switch (command.toUpperCase())
		{
			case "PING":
				sendMessage("PONG", ip, port);
				break;
			default:
				sendMessage("INVALID COMMAND", ip, port);
				break;
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
