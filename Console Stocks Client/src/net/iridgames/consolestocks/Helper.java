package net.iridgames.consolestocks;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public class Helper {

	private static Graphics			g;
	private static GameContainer	gc;

	public static void setGraphics(Graphics graphics) {
		g = graphics;
	}

	public static void setGameContainer(GameContainer gamec) {
		gc = gamec;
	}

	public static void drawString(String s, int x, int y) {
		String[] ss = s.split("\\S+\\{");
		for (int i = 0; i < ss.length; i++) {
			System.out.println(ss[i]);
			g.drawString(ss[i], x, y);
			x += g.getFont().getWidth(ss[i]);
		}
		formatString("asd");
	}

	/**Takes a string, splits the string up and extracts color formatting.
	 * <br>E.G.<br>
	 * Input: error{hello there}.<br>
	 * Output: &lt;error>, hello, &lt;s>, there, &lt;s>, &lt;clear>, .*/
	private static List<String> formatString(String s) {
		// String s = "error{hello} what is going on error{here}? green{I have} error{no
		// clue}.";
		s = s.replaceAll("\\{", ">{");
		s = s.replaceAll("\\}", "}<clear>");
		String[] ss = s.split("\\{|\\}");
		List<String> l = new ArrayList<String>();
		for (int i = 0; i < ss.length; i++) {
			ss[i] = ss[i].replaceAll(" ", " <s> ");
			String[] s1 = ss[i].split(" ");
			for (int x = 0; x < s1.length; x++) {
				if (!s1[x].matches("\\<\\w+\\>") && s1[x].matches("\\w+\\>")) s1[x] = "<" + s1[x];
				if (s1[x].matches("\\<\\w+\\>\\S+")) {
					String[] s2 = s1[x].split("\\>");
					for (int j = 0; j < s2.length; j++) {
						if (s2[j].startsWith("<")) s2[j] = s2[j] + ">";
						l.add(s2[j]);
					}
				} else {
					l.add(s1[x]);
				}
			}
		}
		return l;
	}

}
