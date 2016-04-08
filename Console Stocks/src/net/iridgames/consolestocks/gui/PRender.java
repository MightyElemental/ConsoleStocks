package net.iridgames.consolestocks.gui;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import net.iridgames.consolestocks.ConsoleStocks;

public class PRender {
	
	
	public static final int SERVER_INFO = 0;
	public static final int CLIENT_INFO = 1;
	public static final int ACCOUNT_INFO = 2;
	public static final int CHAT_BOX = 3;
	public static final int STOCK_VALUE_INFO = 4;
	
	public static void renderPanel(int panelID, GameContainer gc, StateBasedGame sbg, Graphics g, int x, int y) {
		switch (panelID) {
			case CLIENT_INFO:
				try {
					renderClient(gc, sbg, g, x, y);
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
				break;
			case SERVER_INFO:
				try {
					renderServer(gc, sbg, g, x, y);
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
				break;
			case ACCOUNT_INFO:
				try {
					renderAccount(gc, sbg, g, x, y);
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
				break;
		}
	}
	
	private static void renderAccount(GameContainer gc, StateBasedGame sbg, Graphics g, int x, int y) throws UnknownHostException {
		if (ConsoleStocks.client == null || !ConsoleStocks.client.isRunning()) {
			g.drawString("Account information not found.", x + 5, y + 5);
			return;
		}
		
	}
	
	/** Used to render client information
	 * 
	 * @throws UnknownHostException */
	@SuppressWarnings( "unchecked" )
	private static void renderClient(GameContainer gc, StateBasedGame sbg, Graphics g, int x, int y) throws UnknownHostException {
		if (ConsoleStocks.client == null) {
			g.drawString("Client information not found.", x + 5, y + 5);
			return;
		}
		Object message = "";
		try {
			message = ((HashMap<String, Object>) ConsoleStocks.client.getLastRecievedObject()).get("ServerMessage");
		} catch (Exception e) {
			
		}
		String[] list = { "Client Information",
			"Connected IP: " + InetAddress.getByName(ConsoleStocks.client.getAddress()).getHostAddress() + ":"
				+ ConsoleStocks.client.getPort(),
			"User: " + ConsoleStocks.client.getName(), "ClientUID: " + ConsoleStocks.client.getUID(),
			"Message Last Recieved From Server: " + message };
		for (int i = 0; i < list.length; i++) {
			g.drawString(list[i], x + 5, y + 5 + (20 * i));
		}
	}
	
	/** Used to render server information
	 * 
	 * @throws UnknownHostException */
	private static void renderServer(GameContainer gc, StateBasedGame sbg, Graphics g, int x, int y) throws UnknownHostException {
		if (ConsoleStocks.client == null || !ConsoleStocks.client.isRunning()) {
			String[] list = { "Server information not found.", "", "---WARNING---", "NO CONNECTION" };
			for (int i = 0; i < list.length; i++) {
				g.drawString(list[i], x + 5, y + 5 + (20 * i));
			}
			return;
		}
		if (ConsoleStocks.client.serverInfo == null) {
			g.drawString("Server information not found.", x + 5, y + 5);
			return;
		}
		String[] list = { "Server Information", "Server Name: " + ConsoleStocks.client.serverInfo.get("ServerName"),
			"Number Of Users Online: " + ConsoleStocks.client.serverInfo.get("OnlineClients"),
			"Server Currency: " + ConsoleStocks.client.serverInfo.get("Currency").toString().toUpperCase() };
		for (int i = 0; i < list.length; i++) {
			g.drawString(list[i], x + 5, y + 5 + (20 * i));
		}
	}
	
}
