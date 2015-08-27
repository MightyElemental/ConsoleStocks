package net.iridgames.consolestocks;

import net.iridgames.consolestocks.client.Client;
import net.iridgames.consolestocks.server.Server;

/** @author MightyElemental & WolfgangTS */
public class ConsoleStocks {

	public static Server server;

	public static boolean	showGUI		= true;
	public static int		port		= 4040;
	public static Client	client;
	public static boolean	isServer	= false;

	public ConsoleStocks() {
		if (showGUI) {
			
		}
	}

	public static void main(String[] settings) {
		try {
			for (int i = 0; i < settings.length; i++) {
				if (settings[i] != null) {
					switch (settings[i]) {
						case "--nogui":
							break;
						case "--port":
							i++;
							port = Integer.parseInt(settings[i]);
							break;
						case "--server":
							isServer = true;
							server = new Server(port);
							server.setupServer();
							break;
					}
				}
			}

			if (!isServer) {
				// do stuff
			}
		} catch (Exception e) {
		}
		new ConsoleStocks();
	}

}
