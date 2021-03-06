package net.iridgames.consolestocks.common;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public abstract class ConfigFile {
	
	
	private Map<String, String> argMap = new HashMap<String, String>();
	
	private Map<String, String> defaultSettings = new HashMap<String, String>();
	
	public String fileName = "";
	
	public ConfigFile( String name ) {
		this.fileName = name;
		setDefaultSettings();
	}
	
	public abstract void setDefaultSettings();
	
	public void addDefault(String key, String val) {
		defaultSettings.put(key, val);
	}
	
	public String getVal(String key) {
		return argMap.get(key);
	}
	
	public void createFile() throws IOException {
		if (doesFileExist(fileName)) { return; }
		System.out.println("Generating " + fileName + " config file");
		FileWriter fileWriter = new FileWriter(fileName);
		BufferedWriter bw = new BufferedWriter(fileWriter);
		bw.write("//" + fileName + " ConfigFile");
		bw.newLine();
		for (String key : defaultSettings.keySet()) {
			bw.write(key + ":" + defaultSettings.get(key));
			bw.newLine();
		}
		bw.close();
	}
	
	public void load() throws IOException {
		ArrayList<String> data = new ArrayList<String>();
		String line = null;
		FileReader fileReader = new FileReader(fileName);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		while ((line = bufferedReader.readLine()) != null) {
			data.add(line);
		}
		interpret(data);
		bufferedReader.close();
		verify();
	}
	
	private void interpret(ArrayList<String> data) {
		for (String line : data) {
			if (line.startsWith("//")) {// skip comments
				continue;
			}
			String[] setting = line.split(":");
			line = "";
			for (int i = 1; i < setting.length; i++) {
				line += setting[i];
			}
			argMap.put(setting[0].toUpperCase(), line);
		}
		for (String key : argMap.keySet()) {
			System.out.println(key + " " + argMap.get(key));
		}
	}
	
	private static boolean doesFileExist(String file) {
		File f = new File(file);
		return f.exists();
	}
	
	/** Used to overwrite everything in the properties file to ensure there are no faulty settings */
	public void verify() throws IOException {
		if (!doesFileExist(fileName)) { return; }
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/YYYY HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		String time = dateFormat.format(cal.getTime());
		
		FileWriter fileWriter = new FileWriter(fileName);
		BufferedWriter bw = new BufferedWriter(fileWriter);
		bw.write("//" + fileName + " ConfigFile " + time);
		bw.newLine();
		for (String key : defaultSettings.keySet()) {
			if (argMap.containsKey(key.toUpperCase())) {
				bw.write(key + ":" + argMap.get(key.toUpperCase()));
			} else {
				bw.write(key + ":" + defaultSettings.get(key));
			}
			bw.newLine();
		}
		bw.close();
	}
	
	public void setVariable(String key, String value) throws IOException {
		if (!doesFileExist(fileName)) {
			createFile();
		}
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/YYYY HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		String time = dateFormat.format(cal.getTime());
		
		FileWriter fileWriter = new FileWriter(fileName);
		BufferedWriter bw = new BufferedWriter(fileWriter);
		bw.write("//" + fileName + " ConfigFile " + time);
		bw.newLine();
		for (String defaultKey : defaultSettings.keySet()) {
			if (defaultKey.toUpperCase().equals(key.toUpperCase())) {
				bw.write(defaultKey + ":" + value);
				argMap.put(defaultKey.toUpperCase(), value);
			} else if (argMap.containsKey(defaultKey.toUpperCase())) {
				bw.write(defaultKey + ":" + argMap.get(defaultKey.toUpperCase()));
			} else {
				bw.write(defaultKey + ":" + defaultSettings.get(defaultKey));
			}
			
			bw.newLine();
		}
		bw.close();
	}
	
}
