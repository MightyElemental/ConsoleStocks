package net.iridgames.consolestocks;

import java.io.IOException;
import java.net.BindException;
import java.net.ConnectException;
import java.util.Random;

import javax.swing.JOptionPane;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.openal.SoundStore;
import org.newdawn.slick.state.StateBasedGame;

import net.iridgames.consolestocks.client.Client;
import net.iridgames.consolestocks.client.LocalCommands;
import net.iridgames.consolestocks.client.gui.StateGame;
import net.iridgames.consolestocks.client.gui.StateMenu;
import net.iridgames.consolestocks.common.Common;
import net.iridgames.consolestocks.server.Commands;
import net.iridgames.consolestocks.server.Parser;
import net.iridgames.consolestocks.server.gui.ServerFrame;
import net.mightyelemental.network.BasicCommands;
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
	public static final String VERSION = "0.9.2 alpha";
	public static final String TITLE = GAME_NAME + " | v" + VERSION;
	public static final int WIDTH = 1600;
	public static Image NULL_IMAGE;
	
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
			setupCommon();
			if (!isServer) {
				setupClient();
			} else {
				setupServer();
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.toString(), "An error has occured", JOptionPane.WARNING_MESSAGE);
			e.printStackTrace();
		}
		
	}
	
	private static void setupCommon() {
		Commands.setupCommandList();
		LocalCommands.setupCommandList();
	}
	
	private static void setupServer() throws IOException {
		server = new TCPServer(port, false, 1024, Common.getVerifyCode()) {
			
			
			@Override
			public void onVerifyDenied(String uid) throws IOException {
				getTCPConnectionFromUID(uid).sendObject("ServerMessage",
					"error{Your client verification code does not match the server verification code!}");
			}
			
			@Override
			public void onVerified(String uid) throws IOException {
				getTCPConnectionFromUID(uid).sendObject("ServerMessage", "alert{Your client has been verified.}");
			}
		};
		try {
			server.setupServer();
		} catch (BindException e) {
			System.err.println("There is already a server running on that port!");
			System.err.println("Make sure you are not using the same port as any other server on your network.");
			System.exit(1);
		}
		Common.createServerProperties();
		Common.loadServerProperties();
		serverParser = new Parser(server);
		server.addListener(serverParser);
		server.initGUI(new ServerFrame(server, BasicCommands.getExternalIPAddress()));// Common.serverSettings.get("SERVERNAME")
		serverParser.serverThread.start();
		// server.getGUI().textField.setText("Your not getting my IP.");
	}
	
	private static void setupClient() throws IOException {
		Common.clientConfig.createFile();
		// Common.createClientProperties();
		Common.clientConfig.load();
		// Common.loadClientProperties();
		
		loadClientProperties();
		client = new Client(Common.clientConfig.getVal("USER"), address, port, 1024);
		client.addListener(client);
		try {
			client.setup();
		} catch (ConnectException e) {
			client.onConnectionRefused();
		} catch (IOException e) {
			System.err.println("Could not connect to server!");
			try {
				client.stopClient();
			} catch (InterruptedException | IOException e1) {
				e1.printStackTrace();
			}
			client = null;
		}
		AppGameContainer appGc;
		try {
			SoundStore.get().init();
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
	
	private static void loadClientProperties() throws NullPointerException, IOException {
		Common.clientConfig.verify();
		if (Common.clientConfig.getVal("DEFAULTSERVERIP").length() > 5) {
			address = Common.clientConfig.getVal("DEFAULTSERVERIP");
		}
		if (Common.clientConfig.getVal("DEFAULTSERVERPORT").length() > 1) {
			try {
				port = Integer.parseInt(Common.clientConfig.getVal("DEFAULTSERVERPORT"));
			} catch (Exception e) {
				System.err.println("'" + Common.clientConfig.getVal("DEFAULTSERVERPORT") + "' is not a valid port");
			}
		}
		try {
			stateGame.console.flashSpeed = Integer.parseInt(Common.clientConfig.getVal("CURSORFLASHSPEED"));
		} catch (Exception e) {
			System.err.println("'" + Common.clientConfig.getVal("CURSORFLASHSPEED") + "' is not a valid number");
		}
		// if (Common.clientSettings.get("DEFAULTSERVERIP").length() > 5) {
		// address = Common.clientSettings.get("DEFAULTSERVERIP");
		// }
		// if (Common.clientSettings.get("DEFAULTSERVERPORT").length() > 1) {
		// try {
		// port = Integer.parseInt(Common.clientSettings.get("DEFAULTSERVERPORT"));
		// } catch (Exception e) {
		// System.err.println("'" + Common.clientSettings.get("DEFAULTSERVERPORT") + "' is not a valid port");
		// }
		// }
		// try {
		// stateGame.console.flashSpeed = Integer.parseInt(Common.clientSettings.get("CURSORFLASHSPEED"));
		// } catch (Exception e) {
		// System.err.println("'" + Common.clientSettings.get("CURSORFLASHSPEED") + "' is not a valid number");
		// }
	}
	
	@Override
	public void initStatesList(GameContainer gc) throws SlickException {
		this.getState(STATE_MENU).init(gc, this);
		// this.getState(STATE_GAME).init(gc, this);
		this.enterState(STATE_GAME);
	}
	
}
