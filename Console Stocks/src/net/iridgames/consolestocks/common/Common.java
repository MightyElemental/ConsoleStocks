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
	
	public static Map<String, String> serverSettings = new HashMap<String, String>();
	
	public static String[][] defaultVariables = { { "ServerName", "Console Stocks" }, { "Password", "" }, { "AdminIP", "" },
		{ "Currency", "pound" }, { "MinNumOfStocks", "50" }, { "MaxNumOfStocks", "200" } };
	
	private static boolean doesServerPropExist() {
		File f = new File(SERVER_PROPERTIES);
		return f.exists();
	}
	
	public static void createServerProperties() {
		if (doesServerPropExist()) { return; }
		try {
			FileWriter fileWriter = new FileWriter(SERVER_PROPERTIES);
			BufferedWriter bw = new BufferedWriter(fileWriter);
			bw.write("//ServerProperties");
			bw.newLine();
			for (int i = 0; i < defaultVariables.length; i++) {
				bw.write(defaultVariables[i][0] + ":" + defaultVariables[i][1]);
				bw.newLine();
			}
			bw.close();
		} catch (Exception e) {
		}
	}
	
	private static void verifyServerProperties() {
		if (!doesServerPropExist()) { return; }
		try {
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/YYYY HH:mm:ss");
			Calendar cal = Calendar.getInstance();
			String time = dateFormat.format(cal.getTime());
			
			FileWriter fileWriter = new FileWriter(SERVER_PROPERTIES);
			BufferedWriter bw = new BufferedWriter(fileWriter);
			bw.write("//ServerProperties Updated " + time);
			bw.newLine();
			for (int i = 0; i < defaultVariables.length; i++) {
				if (serverSettings.containsKey(defaultVariables[i][0].toUpperCase())) {
					bw.write(defaultVariables[i][0] + ":" + serverSettings.get(defaultVariables[i][0].toUpperCase()));
				} else {
					bw.write(defaultVariables[i][0] + ":" + defaultVariables[i][1]);
				}
				bw.newLine();
			}
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
		verifyServerProperties();
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
		while (sb.toString().startsWith(" ")) {
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
	
}
