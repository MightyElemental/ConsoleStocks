package net.iridgames.consolegame;

import java.net.InetAddress;
import java.util.Random;

import net.iridgames.consolegame.io.Input;
import net.iridgames.consolegame.io.Output;
import net.mightyelemental.network.TCPServer;
import net.mightyelemental.network.client.TCPClient;
import net.mightyelemental.network.listener.MessageListenerClient;
import net.mightyelemental.network.listener.MessageListenerServer;

public class ConsoleGame implements MessageListenerServer, MessageListenerClient {

	public static Random random = new Random();

	public static TCPServer	server;
	public static TCPClient	client	= new TCPClient("localhost", 4040);
	String[]				args2;

	public static Input		input	= new Input();
	public static Output	output	= new Output();

	public ConsoleGame( String[] args ) {

		// if (SteamAPI.init()) {
		// System.out.println("did not init");
		// }
		args2 = args;
		if (args.length > 0 && args[0].equals("--server")) {
			output.consoleSay("You are running a server.", "INFO");
			server = new TCPServer(4040);
			server.setupServer();
			server.addListener(this);
			server.initGUI("Server Stuffs");
		} else {
			output.consoleSay("You are running a client.", "INFO");
			client.setup();
			client.addListener(this);
			String input = Input.getInputText();
			while (!input.equals("exit()")) {
				client.sendMessage(input);
				input = Input.getInputText();
				//client.sendPingRequest();
				output.consoleSay("Your ping is " + client.getPingTime(), "INFO");
			}
			client.stopClient();
			System.exit(0);
		}
		// System.out.println(Input.getInputText());
	}

	public static void main(String[] args) {
		new ConsoleGame(args);
	}

	@Override
	public void onMessageRecievedFromClient(String message, InetAddress ip, int port) {
		// System.out.println("(" + args2[0] + ") new message! " + message);
		// for (String arg : Parser.getArgs(message)) {
		// System.out.println(arg);
		// }
		try {
			Parser.parseCommand(Parser.getArgs(message), server, ip, port);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// server.broadcastmessage("(bc) You sent '" + message + "'!");
	}

	@Override
	public void onNewClientAdded(InetAddress ip, int port, String uid) {
		System.out.println(ip.getHostAddress() + ":" + port + " has connected");
		// server.broadcastmessage("(bc) hello new client! your new UID is " +
		// uid);
		// server.sendMessage("(direct) hello new client! your new UID is " +
		// uid, ip, port);
	}

	@Override
	public void onMessageRecievedFromServer(String message) {
		output.consoleSay(message, "INFO");
	}

}
