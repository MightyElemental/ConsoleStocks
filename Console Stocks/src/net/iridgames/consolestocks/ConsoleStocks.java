package net.iridgames.consolestocks;

import net.iridgames.consolestocks.gui.GameFrame;

/** @author MightyElemental & WolfgangTS */
public class ConsoleStocks {

	public static boolean	showGUI	= true;
	public static GameFrame	gameFrame;

	public ConsoleStocks() {
		if (showGUI) {
			gameFrame = new GameFrame();
		}
	}

	public static void main(String[] settings) {
		try {
			for (int i = 0; i < settings.length; i++) {
				if (settings[i] != null) {
					switch (settings[i]) {
						case "--nogui":
							break;
						case "--server":
							break;
					}
				}
			}
		} catch (Exception e) {
		}
	}

}
