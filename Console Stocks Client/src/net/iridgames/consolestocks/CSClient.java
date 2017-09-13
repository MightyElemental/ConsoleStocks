package net.iridgames.consolestocks;

import java.io.IOException;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.openal.SoundStore;
import org.newdawn.slick.state.StateBasedGame;

import net.iridgames.consolestocks.gui.Interface;
import net.iridgames.consolestocks.network.Client;

public class CSClient extends StateBasedGame {

	ClientListener cListen = new ClientListener();

	public static Interface	userInterface;
	public static Client	client;

	public static boolean running = true;

	public static final int		WIDTH		= 1280;
	public static final String	GAME_NAME	= "Console Stocks";
	public static final String	VERSION		= "testing stage";
	public static final String	TITLE		= GAME_NAME + " | " + VERSION;

	public CSClient(String name) {
		super(name);
		userInterface = new Interface();
		this.addState(userInterface);
		client = new Client();
		client.start();
		try {
			client.connect(5000, "77.172.138.167", 4040);
		} catch (IOException e) {
			e.printStackTrace();
			userInterface.pConsole.addEntry("Failed to join server");
			System.err.println("Coult not connect to server");
		}
		client.addListener(cListen);
		client.send("Hello");
	}

	public static void main(String[] args) {
		AppGameContainer appGc;
		try {
			SoundStore.get().init();
			appGc = new AppGameContainer(new CSClient(TITLE));
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

	@Override
	public void initStatesList(GameContainer gc) throws SlickException {
		this.enterState(0);
	}

}
