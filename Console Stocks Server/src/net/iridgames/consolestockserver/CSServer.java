package net.iridgames.consolestockserver;

import java.io.IOException;

import net.iridgames.consolestockserver.network.Server;

public class CSServer {

	ServerListener			sListen	= new ServerListener();
	public static Server	server;

	public static boolean isRunning = true;

	public CSServer() {
		server = new Server();
		try {
			server.bind(4040);
		} catch (IOException e) {
			e.printStackTrace();
		}
		server.start();
		server.addListener(sListen);
	}

	public static void main(String[] args) {
		new CSServer();
	}

}
