package net.iridgames.consolestocks;

/** @author MightyElemental & WolfgangTS */
public class ConsoleStocks {

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
