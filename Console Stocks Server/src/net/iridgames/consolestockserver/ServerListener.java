package net.iridgames.consolestockserver;

import java.io.IOException;

import net.iridgames.consolestockserver.network.Connection;

public class ServerListener {

	public void received(Connection connection, String object) {
		System.out.println(object);
		try {
			switch (object.toString().replaceFirst("COMMAND", "").toLowerCase()) {
			case "hello":
				connection.send("alert{Hello} error{client}!");
				break;
			case "info":
				connection.send("I would love to send you info but alert{there is nothing to give you} :(");
				break;
			case "getstocks":
				connection.send("ekuja");
				connection.send("oisaf");
				connection.send("wobvd");
				connection.send("awobd");
				break;
			default:
				connection.send("error{THAT IS NOT A COMMAND}");
				break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
