package net.iridgames.consolestocks.gui;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import net.iridgames.consolestocks.ConsoleStocks;

public class PRender {

	public static final int CLIENT_RENDER = 1;

	public static void renderPanel(int panelID, GameContainer gc, StateBasedGame sbg, Graphics g, int x, int y) {
		switch (panelID) {
			case CLIENT_RENDER:
				try {
					renderClient(gc, sbg, g, x, y);
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
				break;
		}
	}

	/** Used to render client information
	 * 
	 * @throws UnknownHostException */
	private static void renderClient(GameContainer gc, StateBasedGame sbg, Graphics g, int x, int y) throws UnknownHostException {
		if (ConsoleStocks.client == null) {
			g.drawString("Client information not found.", x + 5, y + 5);
			return;
		}
		String[] list = { "Client Information",
				"Connected IP: " + InetAddress.getByName(ConsoleStocks.client.getAddress()).getHostAddress() + ":"
						+ ConsoleStocks.client.getPort(),
				"User: " + ConsoleStocks.client.getName(),
				"Message Last Recieved From Server: " + ConsoleStocks.client.getLastRecievedMessage() };
		for (int i = 0; i < list.length; i++) {
			g.drawString(list[i], x + 5, y + 5 + (20 * i));
		}
	}
}
