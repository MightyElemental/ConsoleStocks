package net.iridgames.consolegame.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import net.iridgames.consolegame.ConsoleGame;

public class Input {

	private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	public static String getInputText() {
		System.out.print(ConsoleGame.username + "> ");
		try {
			return br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "NO INPUT";
	}

}
