package net.iridgames.consolestocks.common;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Common {
	
	
	public static final String SERVER_PROPERTIES = "server.properties";
	public static final String CLIENT_PROPERTIES = "client.properties";
	
	public static ConfigFile clientConfig = new ConfigFile(CLIENT_PROPERTIES) {
		
		
		@Override
		public void setDefaultSettings() {
			this.addDefault("User", "name!");
			this.addDefault("CursorFlashSpeed", "40");
			this.addDefault("DefaultServerIP", "127.0.0.1");
			this.addDefault("DefaultServerPort", "4040");
			this.addDefault("ShowPanelNumbers", "false");
		}
		
	};
	
	public static Map<String, String> serverSettings = new HashMap<String, String>();
	public static Map<String, String> clientSettings = new HashMap<String, String>();
	
	public static String[][] defaultServerVariables = { { "ServerName", "Console Stocks" }, { "Password", "" }, { "AdminIP", "" },
		{ "Currency", "pound" }, { "MinNumOfStocks", "50" }, { "MaxNumOfStocks", "200" } };
	
	public static String[][] defaultClientVariables = { { "User", "name!" }, { "CursorFlashSpeed", "40" }, { "DefaultServerIP", "" },
		{ "DefaultServerPort", "4040" }, { "ShowPanelNumbers", "false" } };
	
	private static boolean doesFileExist(String file) {
		File f = new File(file);
		return f.exists();
	}
	
	public static void createClientProperties() {
		if (doesFileExist(CLIENT_PROPERTIES)) { return; }
		System.out.println("Generating client properties file");
		try {
			FileWriter fileWriter = new FileWriter(CLIENT_PROPERTIES);
			BufferedWriter bw = new BufferedWriter(fileWriter);
			bw.write("//ClientProperties");
			bw.newLine();
			for (int i = 0; i < defaultClientVariables.length; i++) {
				bw.write(defaultClientVariables[i][0] + ":" + defaultClientVariables[i][1]);
				bw.newLine();
			}
			bw.close();
		} catch (Exception e) {
		}
	}
	
	public static void createServerProperties() {
		if (doesFileExist(SERVER_PROPERTIES)) { return; }
		System.out.println("Generating server properties file");
		try {
			FileWriter fileWriter = new FileWriter(SERVER_PROPERTIES);
			BufferedWriter bw = new BufferedWriter(fileWriter);
			bw.write("//ServerProperties");
			bw.newLine();
			for (int i = 0; i < defaultServerVariables.length; i++) {
				bw.write(defaultServerVariables[i][0] + ":" + defaultServerVariables[i][1]);
				bw.newLine();
			}
			bw.close();
		} catch (Exception e) {
		}
	}
	
	private static void verifyClientProperties() {
		if (!doesFileExist(CLIENT_PROPERTIES)) { return; }
		try {
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/YYYY HH:mm:ss");
			Calendar cal = Calendar.getInstance();
			String time = dateFormat.format(cal.getTime());
			
			FileWriter fileWriter = new FileWriter(CLIENT_PROPERTIES);
			BufferedWriter bw = new BufferedWriter(fileWriter);
			bw.write("//ClientProperties " + time);
			bw.newLine();
			for (int i = 0; i < defaultClientVariables.length; i++) {
				if (clientSettings.containsKey(defaultClientVariables[i][0].toUpperCase())) {
					bw.write(defaultClientVariables[i][0] + ":" + clientSettings.get(defaultClientVariables[i][0].toUpperCase()));
				} else {
					bw.write(defaultClientVariables[i][0] + ":" + defaultClientVariables[i][1]);
				}
				bw.newLine();
			}
			bw.close();
		} catch (Exception e) {
		}
	}
	
	public static void setClientVariable2(String key, String value) {
		if (!doesFileExist(CLIENT_PROPERTIES)) {
			createClientProperties();
		}
		try {
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/YYYY HH:mm:ss");
			Calendar cal = Calendar.getInstance();
			String time = dateFormat.format(cal.getTime());
			
			FileWriter fileWriter = new FileWriter(CLIENT_PROPERTIES);
			BufferedWriter bw = new BufferedWriter(fileWriter);
			bw.write("//ClientProperties " + time);
			bw.newLine();
			for (int i = 0; i < defaultClientVariables.length; i++) {
				if (defaultClientVariables[i][0].toUpperCase().equals(key.toUpperCase())) {// key == key
					bw.write(defaultClientVariables[i][0] + ":" + value);// write dKey + val
					clientSettings.put(defaultClientVariables[i][0].toUpperCase(), value);// write dKey + val
				} else if (clientSettings.containsKey(defaultClientVariables[i][0].toUpperCase())) {// cSet contains dKey
					// write dKey + cVal
					bw.write(defaultClientVariables[i][0] + ":" + clientSettings.get(defaultClientVariables[i][0].toUpperCase()));
				} else {
					bw.write(defaultClientVariables[i][0] + ":" + defaultClientVariables[i][1]); //write dKey + dVal
				}
				
				bw.newLine();
			}
			bw.close();
		} catch (Exception e) {
		}
	}
	
	private static void verifyServerProperties() {
		if (!doesFileExist(SERVER_PROPERTIES)) { return; }
		try {
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/YYYY HH:mm:ss");
			Calendar cal = Calendar.getInstance();
			String time = dateFormat.format(cal.getTime());
			
			FileWriter fileWriter = new FileWriter(SERVER_PROPERTIES);
			BufferedWriter bw = new BufferedWriter(fileWriter);
			bw.write("//ServerProperties " + time);
			bw.newLine();
			for (int i = 0; i < defaultServerVariables.length; i++) {
				if (serverSettings.containsKey(defaultServerVariables[i][0].toUpperCase())) {
					bw.write(defaultServerVariables[i][0] + ":" + serverSettings.get(defaultServerVariables[i][0].toUpperCase()));
				} else {
					bw.write(defaultServerVariables[i][0] + ":" + defaultServerVariables[i][1]);
				}
				bw.newLine();
			}
			bw.close();
		} catch (Exception e) {
		}
	}
	
	public static void loadClientProperties() {
		ArrayList<String> data = new ArrayList<String>();
		String line = null;
		try {
			FileReader fileReader = new FileReader(CLIENT_PROPERTIES);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			while ((line = bufferedReader.readLine()) != null) {
				data.add(line);
			}
			interClientSettings(data);
			bufferedReader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		verifyClientProperties();
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
		verifyServerProperties();
	}
	
	private static void interClientSettings(ArrayList<String> data) {
		for (String line : data) {
			if (line.startsWith("//")) {
				continue;
			}
			String[] setting = line.split(":");
			line = "";
			for (int i = 1; i < setting.length; i++) {
				line += setting[i];
			}
			clientSettings.put(setting[0].toUpperCase(), line);
		}
		for (String key : clientSettings.keySet()) {
			System.out.println(key + " " + clientSettings.get(key));
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
	
	/** Splits the lines and args up and compiles it into an ArrayList<ArrayList<String>> */
	public static ArrayList<ArrayList<String>> interpretCommandLine(String line) {
		ArrayList<ArrayList<String>> commands = new ArrayList<ArrayList<String>>();
		
		StringBuilder sb = new StringBuilder(line);
		while (sb.toString().startsWith(" ") || sb.toString().startsWith(";")) {
			sb.deleteCharAt(0);
		}
		while (sb.toString().endsWith(" ")) {
			sb.deleteCharAt(sb.length() - 1);
		}
		line = sb.toString();
		
		String[] coms = line.split(";");
		for (String tempCom : coms) {
			String[] commAndArgs = tempCom.split(" ");
			ArrayList<String> args = new ArrayList<String>();
			Collections.addAll(args, commAndArgs);
			args.remove(" ");
			args.remove("");
			commands.add(args);
		}
		return commands;
	}
	
	public static String getCurrencySymbol() {
		String name = Common.serverSettings.get("CURRENCY");
		if (name == null) {
			name = "nothing";
		}
		switch (name) {
			case "pound":
				return "\u00A3";
			case "dollar":
				return "$";
			case "yen":
				return "\u00A5";
			default:
				return "\u00A3";
		}
	}
	
	public static String getVerifyCode() {
		return "ConsoleStocks";
	}
	
}
