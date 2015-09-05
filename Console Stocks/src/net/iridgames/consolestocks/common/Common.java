package net.iridgames.consolestocks.common;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class Common {

	public static final String SERVER_PROPERTIES = "server.properties";

	private static boolean doesServerPropExist() {
		File f = new File(SERVER_PROPERTIES);
		return f.exists();
	}

	public static void createServerProperties() {
		if (doesServerPropExist()) { return; }
		try {
			FileWriter fileWriter = new FileWriter(SERVER_PROPERTIES);
			BufferedWriter bw = new BufferedWriter(fileWriter);
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

}
