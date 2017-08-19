package net.iridgames.consolestockserver;

import java.io.IOException;

import com.esotericsoftware.kryonet.Server;

public class CSServer implements Runnable {

	Thread					mainThread	= new Thread(this);
	ServerListener			sListen		= new ServerListener();
	public static Server	server;

	public CSServer() {
		mainThread.start();
		server = new Server();
		server.start();
		try {
			server.bind(4040);
		} catch (IOException e) {
			e.printStackTrace();
		}
		server.addListener(sListen);
	}

	public static void main(String[] args) {
		new CSServer();
	}

	@Override
	public void run() {

	}

}
