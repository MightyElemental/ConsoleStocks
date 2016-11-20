package net.iridgames.consolestocks.client.gui;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import net.iridgames.consolestocks.ConsoleStocks;
import net.iridgames.consolestocks.Helper;
import net.iridgames.consolestocks.common.Common;

public class PRender {
	
	
	public static final int SERVER_INFO = 0;
	public static final int CLIENT_INFO = 1;
	public static final int ACCOUNT_INFO = 2;
	public static final int CHAT_BOX = 3;
	public static final int STOCK_VALUE_INFO = 4;
	public static final int CONSOLE = 5;
	
	public static void renderPanel(int panelID, GameContainer gc, StateBasedGame sbg, Graphics g, int x, int y) {
		g.setColor(Color.white);
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
			case CONSOLE:
				renderConsole(gc, sbg, g, x, y);
				break;
			case CHAT_BOX:
				renderChatBox(gc, sbg, g, x, y);
				break;
		}
	}
	
	/** Used to render account information */
	private static void renderAccount(GameContainer gc, StateBasedGame sbg, Graphics g, int x, int y) throws UnknownHostException {
		drawTitle(gc, g, x, y, "Account Info");
		if (ConsoleStocks.client == null || !ConsoleStocks.client.isRunning()) {
			Helper.drawString(g, "alert{Account information not found}", x + 5, y + 5);
			return;
		}
		int i = 0;
		for (String key : ConsoleStocks.client.accountInfo.keySet()) {
			Object value = ConsoleStocks.client.accountInfo.get(key);
			boolean flag = false;
			if (key.equals("balance")) flag = true;
			key = key.replaceAll("(\\p{Ll})(\\p{Lu})", "$1 $2");
			StringBuilder s = new StringBuilder(key);
			s.replace(0, 1, (s.charAt(0) + "").toUpperCase());
			if (flag) {
				g.drawString(s.toString() + ": " + Common.getCurrencySymbol() + value, x + 5, y + 5 + (20 * i));
			} else {
				g.drawString(s.toString() + ": " + value, x + 5, y + 5 + (20 * i));
			}
			i++;
		}
	}
	
	/** Used to render client information
	 * 
	 * @throws UnknownHostException */
	@SuppressWarnings( "unchecked" )
	private static void renderClient(GameContainer gc, StateBasedGame sbg, Graphics g, int x, int y) throws UnknownHostException {
		drawTitle(gc, g, x, y, "Client Info");
		if (ConsoleStocks.client == null) {
			Helper.drawString(g, "alert{Client information not found}", x + 5, y + 5);
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
			Helper.drawString(g, list[i], x + 5, y + 5 + (20 * i));
		}
	}
	
	/** Used to render server information
	 * 
	 * @throws UnknownHostException */
	private static void renderServer(GameContainer gc, StateBasedGame sbg, Graphics g, int x, int y) throws UnknownHostException {
		drawTitle(gc, g, x, y, "Server Info");
		if (ConsoleStocks.client == null || !ConsoleStocks.client.isRunning()) {
			String[] list = { "alert{Server information not found}", "", "error{~~~WARNING~~~}", "NO CONNECTION" };
			for (int i = 0; i < list.length; i++) {
				Helper.drawString(g, list[i], x + 5, y + 5 + (20 * i));
			}
			return;
		}
		if (ConsoleStocks.client.serverInfo == null) {
			Helper.drawString(g, "alert{Server information not found.}", x + 5, y + 5);
			return;
		}
		String[] list = { "Server Information", "Server Name: " + ConsoleStocks.client.serverInfo.get("ServerName"),
			"Number Of Users Online: " + ConsoleStocks.client.serverInfo.get("OnlineClients"),
			"Server Currency: " + ConsoleStocks.client.serverInfo.get("Currency").toString().toUpperCase() };
		for (int i = 0; i < list.length; i++) {
			Helper.drawString(g, list[i], x + 5, y + 5 + (20 * i));
		}
	}
	
	private static void renderConsole(GameContainer gc, StateBasedGame sbg, Graphics g, int x, int y) {
		drawTitle(gc, g, x, y, "Console");
		ConsoleStocks.stateGame.console.renderConsole(gc, sbg, g, x, y);
	}
	
	private static void renderChatBox(GameContainer gc, StateBasedGame sbg, Graphics g, int x, int y) {
		drawTitle(gc, g, x, y, "Chat Panel");// TODO add client name to the panel
		// TODO add Map<String, ArrayList<String>> chats. // Client UID, messages (incoming and outgoing)
		// TODO add a display for the above information
	}
	
	private static void drawTitle(GameContainer gc, Graphics g, int x, int y, String title) {
		int h = g.getFont().getHeight(title);
		int w = g.getFont().getWidth(title);
		g.setColor(Color.gray);
		g.fillRoundRect(x + gc.getWidth() / 2 - w - 11, y + 2, w + 8, h + 2, 5, 5);
		g.setColor(Color.gray.brighter());
		g.drawRoundRect(x + gc.getWidth() / 2 - w - 11, y + 2, w + 8, h + 2, 5, 5);
		g.setColor(Color.white);
		g.drawString(title, x + gc.getWidth() / 2 - w - 7, y + 1);
	}
	
}
