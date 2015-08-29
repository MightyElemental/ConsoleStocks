package net.iridgames.consolestocks.gui;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import net.iridgames.consolestocks.ConsoleStocks;

public class PanelRenderers {

	public static void renderClient(GameContainer gc, StateBasedGame sbg, Graphics g, int x, int y) {
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
