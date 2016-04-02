package net.iridgames.consolestocks.common;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Common {

	public static final String			SERVER_PROPERTIES	= "server.properties";

	public static Map<String, String>	serverSettings		= new HashMap<String, String>();

	private static boolean doesServerPropExist() {
		File f = new File(SERVER_PROPERTIES);
		return f.exists();
	}

	public static void createServerProperties() {
		if (doesServerPropExist()) { return; }
		try {
			FileWriter fileWriter = new FileWriter(SERVER_PROPERTIES);
			BufferedWriter bw = new BufferedWriter(fileWriter);
			bw.write("//Server Properties");
			bw.newLine();
			bw.write("ServerName:");
			bw.newLine();
			bw.write("Password:");
			bw.newLine();
			bw.write("AdminIP:");
			bw.newLine();
			bw.close();
		} catch (Exception e) {
		}
	}

	public static void loadServerProperties() {
		ArrayList<String> data = new ArrayList<String>();
		String line = null;
		try {
			FileReader fileReader = new FileReader(SERVER_PROPERTIES);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			while ((line = bufferedReader.readLine()) != null) {
				data.add(line);
			}
			interServerSettings(data);
			bufferedReader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void interServerSettings(ArrayList<String> data) {
		for (String line : data) {
			if (line.startsWith("//")) {
				continue;
			}
			String[] setting = line.split(":");
			line = "";
			for (int i = 1; i < setting.length; i++) {
				line += setting[i];
			}
			serverSettings.put(setting[0].toUpperCase(), line);
		}
		for (String key : serverSettings.keySet()) {
			System.out.println(key + " " + serverSettings.get(key));
		}
	}

}
