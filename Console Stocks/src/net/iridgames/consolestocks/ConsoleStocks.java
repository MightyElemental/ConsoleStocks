package net.iridgames.consolestocks;

import java.io.IOException;
import java.net.BindException;
import java.util.Random;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import net.iridgames.consolestocks.client.Client;
import net.iridgames.consolestocks.client.LocalCommands;
import net.iridgames.consolestocks.common.Common;
import net.iridgames.consolestocks.gui.StateGame;
import net.iridgames.consolestocks.gui.StateMenu;
import net.iridgames.consolestocks.server.Commands;
import net.iridgames.consolestocks.server.Parser;
import net.mightyelemental.network.TCPServer;

/** @author MightyElemental & WolfgangTS */
public class ConsoleStocks extends StateBasedGame {
	
	
	public static TCPServer server;
	public static Parser serverParser;
	
	public static boolean showGUI = true;
	public static String address = "127.0.0.1";
	public static int port = 4040;
	public static Client client;
	public static boolean isServer = false;
	
	public static final int STATE_MENU = 0;
	public static final int STATE_GAME = 1;
	public static StateGame stateGame = new StateGame(STATE_GAME);
	
	public static Random rand = new Random();
	
	public static final String GAME_NAME = "Console Stocks";
	public static final String VERSION = "0.5.0";
	public static final String TITLE = GAME_NAME + " | v" + VERSION;
	public static final int WIDTH = 1600;
	
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
			e.printStackTrace();
		}
		
	}
	
	private static void setupServer() throws IOException {
		server = new TCPServer(port, false, 1024);
		try {
			server.setupServer();
		} catch (BindException e) {
			System.err.println("There is already a server running on that port!");
			System.err.println("Make sure you are not using the same port as any other server on your network.");
			System.exit(1);
		}
		Commands.setupCommandList();
		Common.createServerProperties();
		Common.loadServerProperties();
		serverParser = new Parser(server);
		server.addListener(serverParser);
		server.initGUI(Common.serverSettings.get("SERVERNAME"));
	}
	
	private static void setupClient() {
		Common.createClientProperties();
		Common.loadClientProperties();
		loadClientProperties();
		client = new Client(Common.clientSettings.get("USER"), address, port, 1024);
		try {
			client.setup();
		} catch (IOException e1) {
			System.err.println("Could not connect to server!");
			e1.printStackTrace();
		}
		LocalCommands.setupCommandList();
		client.addListener(client);
		
		AppGameContainer appGc;
		try {
			appGc = new AppGameContainer(new ConsoleStocks(TITLE));
			appGc.setDisplayMode(WIDTH, (int) (WIDTH / 16.0 * 9.0), false);
			appGc.setTargetFrameRate(60);
			appGc.setShowFPS(false);
			appGc.setAlwaysRender(true);
			appGc.start();
		} catch (SlickException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	private static void loadClientProperties() {
		if (Common.clientSettings.get("DEFAULTSERVERIP").length() > 5) {
			address = Common.clientSettings.get("DEFAULTSERVERIP");
		}
		if (Common.clientSettings.get("DEFAULTSERVERPORT").length() > 1) {
			try {
				port = Integer.parseInt(Common.clientSettings.get("DEFAULTSERVERPORT"));
			} catch (Exception e) {
				System.err.println("'" + Common.clientSettings.get("DEFAULTSERVERPORT") + "' is not a valid port");
			}
		}
		try {
			stateGame.console.flashSpeed = Integer.parseInt(Common.clientSettings.get("CURSORFLASHSPEED"));
		} catch (Exception e) {
			System.err.println("'" + Common.clientSettings.get("CURSORFLASHSPEED") + "' is not a valid number");
		}
	}
	
	@Override
	public void initStatesList(GameContainer gc) throws SlickException {
		this.getState(STATE_MENU).init(gc, this);
		this.getState(STATE_GAME).init(gc, this);
		this.enterState(STATE_GAME);
	}
	
}
