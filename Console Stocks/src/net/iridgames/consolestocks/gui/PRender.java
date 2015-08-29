package net.iridgames.consolestocks.gui;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import net.iridgames.consolestocks.ConsoleStocks;

public class PRender {
	
	public static final int CLIENT_RENDER = 1;

	public static void renderPanel(int panelID, GameContainer gc, StateBasedGame sbg, Graphics g, int x, int y) {
		switch (panelID) {
			case CLIENT_RENDER:
				renderClient(gc, sbg, g, x, y);
				break;
		}
	}

	/** Used to render client information */
	private static void renderClient(GameContainer gc, StateBasedGame sbg, Graphics g, int x, int y) {
		if (ConsoleStocks.client == null) {
			g.drawString("Client information not found.", x + 5, y + 5);
			return;
		}
		String[] list = { "Client Information", "Connected IP: " + ConsoleStocks.client.getAddress() + ":" + ConsoleStocks.client.getPort(),
				"User: " + ConsoleStocks.client.getName(),
				"Message Last Recieved From Server: " + ConsoleStocks.client.getLastRecievedMessage() };
		for (int i = 0; i < list.length; i++) {
			g.drawString(list[i], x + 5, y + 5 + (20 * i));
		}
	}
}
