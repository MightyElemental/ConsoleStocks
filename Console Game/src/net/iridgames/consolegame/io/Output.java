package net.iridgames.consolegame.io;

public class Output {

	public static final String LogType = null;

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

	
	public static void consoleSay(String message, String f) {
		String[] temp = message.split("");
		System.out.print("[ " + f.toString() + " | Console ] ");
		for (int i = 0; i < temp.length; i++) {
			System.out.print(temp[i]);
			try {
				Thread.sleep(100);
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
