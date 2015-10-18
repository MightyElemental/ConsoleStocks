package net.iridgames.consolegame;

import java.util.ArrayList;
import java.util.List;

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

}
