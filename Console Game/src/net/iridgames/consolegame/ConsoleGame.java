package net.iridgames.consolegame;

import java.util.Random;

import net.iridgames.consolegame.io.Input;
import net.iridgames.consolegame.io.Output;
import net.mightyelemental.network.Server;

public class ConsoleGame {

	public static final String username = System.getProperty("user.name");

	public static Random random = new Random();

	public static Server server = new Server(4040);

	public static void main(String[] args) {
		server.setupServer();
		// System.out.println(Input.getInputText());
		Output.say(Input.getInputText(), username);
	}

}
