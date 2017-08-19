package net.iridgames.consolestockserver;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

public class ServerListener extends Listener {

	public void received(Connection connection, Object object) {
		if ( object instanceof String ) {
			if ( object.toString().startsWith("COMMAND") ) {//TODO create a better command system
				switch (object.toString().replaceFirst("COMMAND", "").toLowerCase()) {
				case "hello":
					connection.sendTCP("alert{Hello} error{client}!");
					break;
				case "info":
					connection.sendTCP("I would love to send you info but alert{there is nothing to give you} :(");
					break;
				case "getstocks":
					connection.sendTCP("ekuja");
					connection.sendTCP("oisaf");
					connection.sendTCP("wobvd");
					connection.sendTCP("awobd");
					break;
				default:
					connection.sendTCP("error{THAT IS NOT A COMMAND}");
					break;
				}
			}
			System.out.println(object.toString());
		}
	}

}
