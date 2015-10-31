package net.iridgames.consolegame;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import net.mightyelemental.network.Server;

public class Parser {

	public static List<String> getArgs(String command) {
		List<String> args = new ArrayList<String>();

		StringBuilder sb = new StringBuilder(command);
		while (sb.toString().startsWith(" ")) {
			sb.deleteCharAt(0);
		}
		while (sb.toString().endsWith(" ")) {
			sb.deleteCharAt(sb.length() - 1);
		}
		for (String temp : sb.toString().split(" ")) {
			args.add(temp);
		}

		return args;
	}

	public static void parseCommand(List<String> args, Server server, InetAddress ip, int port) throws InterruptedException {
		if (args.size() < 1) { return; }
		if (args.get(0).contains("JLB1F0")) { return; }
		switch (args.get(0)) {
			case "getStocks":
				server.sendMessage("No stocks avaliable!", ip, port);
				break;
			case "StealEvansAsiimov":
				server.sendMessage("Ooops! Could not find the Asiimov!", ip, port);
				break;
			case "sendMessage":
				// stuff
				break;
			case "sendBroadcast":
				StringBuilder sb = new StringBuilder();
				args.set(0, "");

				for (String arg : args) {
					sb.append(arg + " ");
				}
				// server.broadcastmessage(sb.toString());
				break;
			default:
				server.sendMessage("'" + args.get(0) + "' is not a valid command!", ip, port);
				break;
		}
	}

	public static void notEnoughArgs(Server server, InetAddress ip, int port) {
		server.sendMessage("There are not enough arguments!", ip, port);
	}

	public static void wrongArgs(Server server, InetAddress ip, int port) {
		server.sendMessage("Wrong argument!", ip, port);
	}

}
