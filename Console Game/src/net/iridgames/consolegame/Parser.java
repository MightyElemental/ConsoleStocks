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

	public static void parseCommand(List<String> args, Server server, InetAddress ip, int port) {
		if (args.size() < 1) { return; }
		switch (args.get(0)) {
			case "getStockList()":
				server.sendMessage("No stocks avaliable!", ip, port);
				break;
			default:
				break;
		}
	}

}
