package net.iridgames.consolegame.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Input {

	private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	public String getInputText() {
		System.out.print("User> ");
		try {
			return this.br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "NO INPUT";
	}
	
}
