package net.iridgames.consolestockserver.network;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.List;

import net.iridgames.consolestockserver.ServerListener;

public class Connection implements Runnable {

	private Socket	socket;
	private long	timeConnected;

	InputStream		is;
	OutputStream	os;

	List<ServerListener>	listeners;
	Thread					listenThread	= new Thread(this);

	public Connection(Socket socket, List<ServerListener> listeners) {
		if ( socket == null ) throw new NullPointerException("Socket cannot be null!");
		this.socket = socket;
		timeConnected = System.currentTimeMillis();
		try {
			is = socket.getInputStream();
			os = socket.getOutputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.listeners = listeners;
		listenThread.start();
	}

	/**
	 * Converts the message string into UTF-8 encoded bytes then sends it to the
	 * client.
	 * 
	 * @param message
	 *            the message that will be sent to the client
	 * @throws IOException
	 *             when bytes fail to be sent through socket
	 */
	public void send(String message) throws IOException {
		System.err.println("MESSAGE SENT");
		os.write(message.getBytes("UTF-8"));
	}

	/** @return the IP address in string form */
	public String getIPString() {
		return socket.getInetAddress().toString();
	}

	/**
	 * @return the time at which the client connected to the server in milliseconds
	 */
	public long getTimeConnected() {
		return timeConnected;
	}

	@Override
	public void run() {
		while (!socket.isClosed()) {
			byte[] bytes = new byte[256];
			try {
				is.read(bytes);

				for ( ServerListener l : listeners ) {
					l.received(this, new String(bytes).trim());
				}
			} catch (SocketException se) {
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
