package net.iridgames.consolestockserver.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.iridgames.consolestockserver.CSServer;
import net.iridgames.consolestockserver.ServerListener;

public class Server implements Runnable {

	private ServerSocket			socket;
	private Map<String, Connection>	connections	= new HashMap<String, Connection>();

	private List<ServerListener> listeners;

	Thread updateThread = new Thread(this);

	public Server() {
		listeners = new ArrayList<ServerListener>();
	}

	/**
	 * Initialises the socket object and assigns a port to it.
	 * 
	 * @param port
	 *            the port the socket will be bound to
	 * @throws IOException
	 *             if the socket cannot be bound
	 */
	public void bind(int port) throws IOException {
		socket = new ServerSocket(port);
	}

	/** Starts the update thread so the server can receive data. */
	public void start() {
		updateThread.start();
	}

	@Override
	public void run() {
		while (CSServer.isRunning) {
			try {
				Socket connectionSocket = socket.accept();
				Connection c = new Connection(connectionSocket, listeners);
				connections.put(c.getIPString(), c);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Registers a ServerListener object in the list of listeners.
	 * 
	 * @param sListen
	 *            the listener instance
	 */
	public void addListener(ServerListener sListen) {
		listeners.add(sListen);
	}

}
