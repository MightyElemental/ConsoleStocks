package net.iridgames.consolegame;

import java.net.InetAddress;
import java.util.Random;

import net.iridgames.consolegame.io.Input;
import net.mightyelemental.network.Server;
import net.mightyelemental.network.client.Client;
import net.mightyelemental.network.listener.MessageListenerClient;
import net.mightyelemental.network.listener.MessageListenerServer;

public class ConsoleGame implements MessageListenerServer, MessageListenerClient {

	public static final String username = System.getProperty("user.name");

	public static Random random = new Random();

	public static Server	server;
	public static Client	client	= new Client(username, "localhost", 4040);
	String[]				args2;

	public ConsoleGame( String[] args ) {
		// if (SteamAPI.init()) {
		// System.out.println("did not init");
		// }
		args2 = args;
		if (args[0].equals("server")) {
			System.out.println("Server");
			server = new Server(4040);
			server.setupServer();
			server.addListener(this);
			server.initGUI("Server Stuffs");
		} else {
			System.out.println("Client");
			client.setup();
			client.addListener(this);
			for (int i = 0; i < 5; i++) {
				client.sendMessage(Input.getInputText());
			}
		}
		// System.out.println(Input.getInputText());
	}

	public static void main(String[] args) {
		new ConsoleGame(args);
	}

	@Override
	public void onMessageRecievedFromClient(String message, InetAddress ip) {
		System.out.println("(" + args2[0] + ") new message! " + message);
		for (String arg : Parser.getArgs(message)) {
			System.out.println(arg);
		}
	}

	@Override
	public void onNewClientAdded(InetAddress ip, int port, String uid) {
		System.out.println("(" + args2[0] + ") hello " + ip.getHostAddress() + ":" + port);
		server.broadcastmessage("(bc) hello new client! your new UID is " + uid);
		// server.sendMessage("(direct) hello new client! your new UID is " + uid, ip, port);
	}

	@Override
	public void onMessageRecievedFromServer(String message) {
		// System.out.println("(client) " + message);
	}

}
