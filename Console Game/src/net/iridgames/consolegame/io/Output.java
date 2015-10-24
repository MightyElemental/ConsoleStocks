package net.iridgames.consolegame.io;

public class Output {

	public static void say(String message, String name) {
		String[] temp = message.split("");
		System.out.print("[ INFO | " + name + "] ");
		for (int i = 0; i < temp.length; i++) {
			System.out.print(temp[i]);
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("");
	}

	public static enum LogType {
			INFO,
			ERROR,
			WARNING
	}
	
	public static void consoleSay(String message, LogType f) {
		String[] temp = message.split("");
		System.out.print("[ " + f.toString() + " | Console] ");
		for (int i = 0; i < temp.length; i++) {
			System.out.print(temp[i]);
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("");
	}

	public static void newLine() {
		System.out.println("");
	}
}
