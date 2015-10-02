package net.iridgames.consolestocks;

import java.util.Random;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import net.iridgames.consolestocks.client.Client;
import net.iridgames.consolestocks.common.Common;
import net.iridgames.consolestocks.gui.ServerGUI;
import net.iridgames.consolestocks.gui.StateGame;
import net.iridgames.consolestocks.gui.StateMenu;
import net.iridgames.consolestocks.server.Server;

/** @author MightyElemental & WolfgangTS */
public class ConsoleStocks extends StateBasedGame {

	public static Server	server;
	public static ServerGUI	serverGUI;

	public static boolean	showGUI		= true;
	public static String	address		= "localhost";
	public static int		port		= 4040;
	public static Client	client;
	public static boolean	isServer	= false;

	public static final int	STATE_MENU	= 0;
	public static final int	STATE_GAME	= 1;
	public static StateGame	stateGame	= new StateGame(STATE_GAME);

	public static Random rand = new Random();

	public static final String	GAME_NAME	= "Console Stocks";
	public static final String	VERSION		= "0.2.1";
	public static final String	TITLE		= GAME_NAME + " | v" + VERSION;
	public static final int		WIDTH		= 1600;

	public ConsoleStocks( String name ) {
		super(name);

		addState(new StateMenu(STATE_MENU));
		addState(stateGame);
	}

	public static void main(String[] settings) {
		try {
			for (int i = 0; i < settings.length; i++) {
				if (settings[i] != null) {
					switch (settings[i]) {
						case "--nogui":
							showGUI = false;
							break;
						case "--address":
							i++;
							address = settings[i];
							break;
						case "--port":
							i++;
							port = Integer.parseInt(settings[i]);
							break;
						case "--server":
							isServer = true;
							break;
					}
				}
			}

			if (!isServer) {
				setupClient();
			} else {
				setupServer();
			}
		} catch (Exception e) {
		}

	}

	private static void setupServer() {
		server = new Server(port);
		server.setupServer();
		serverGUI = new ServerGUI();
		Common.createServerProperties();
		Common.loadServerProperties();
	}

	private static void setupClient() {
		client = new Client("Name!", address, port);

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
