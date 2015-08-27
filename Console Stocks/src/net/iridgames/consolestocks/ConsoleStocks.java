package net.iridgames.consolestocks;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import net.iridgames.consolestocks.client.Client;
import net.iridgames.consolestocks.gui.StateGame;
import net.iridgames.consolestocks.gui.StateMenu;
import net.iridgames.consolestocks.server.Server;

/** @author MightyElemental & WolfgangTS */
public class ConsoleStocks extends StateBasedGame {

	public static Server server;

	public static boolean	showGUI		= true;
	public static int		port		= 4040;
	public static Client	client;
	public static boolean	isServer	= false;

	public static final int	STATE_MENU	= 0;
	public static final int	STATE_GAME	= 1;

	public static final String	GAME_NAME	= "That good game";
	public static final String	VERSION		= "0.0.0";
	public static final String	TITLE		= GAME_NAME + " | Version " + VERSION;
	public static final int		WIDTH		= 1600;

	public ConsoleStocks( String name ) {
		super(name);
		if (!isServer) {
			this.addState(new StateMenu(STATE_MENU));
			this.addState(new StateGame(STATE_GAME));
		}
	}

	public static void main(String[] settings) {
		try {
			for (int i = 0; i < settings.length; i++) {
				if (settings[i] != null) {
					switch (settings[i]) {
						case "--nogui":
							showGUI = false;
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

			if (!isServer && showGUI) {
				setupClient();
			}
		} catch (Exception e) {
		}

	}

	private static void setupClient() {
		AppGameContainer appGc;
		try {
			appGc = new AppGameContainer(new ConsoleStocks(TITLE));
			appGc.setDisplayMode(WIDTH, (int) (WIDTH / 16.0 * 9.0), false);
			appGc.setTargetFrameRate(60);
			appGc.setShowFPS(false);
			appGc.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initStatesList(GameContainer gc) throws SlickException {
		this.getState(STATE_MENU).init(gc, this);
		this.getState(STATE_GAME).init(gc, this);
		this.enterState(STATE_GAME);
	}

}
