package net.iridgames.consolestocks;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import net.iridgames.consolestocks.gui.ConsoleDrawInfo;

public class Helper {

	private static Graphics			g;
	private static GameContainer	gc;

	public static void setGraphics(Graphics graphics) {
		g = graphics;
	}

	public static void setGameContainer(GameContainer gamec) {
		gc = gamec;
	}

	public static int getHeightLines(String s) {
		s = s.replaceAll(" ", " <s> ");
		String[] ss = s.split(" ");
		int temp = 0;
		int lines = 1;
		if ( s.length() > 0 ) lines++;
		for ( int i = 0; i < ss.length; i++ ) {
			switch (ss[i]) {
			case "<s>":
				temp += Settings.spaceSize;
				break;
			default:
				if ( temp + g.getFont().getWidth(ss[i]) > gc.getWidth() / 2 - 10 ) {
					temp = 0;
					lines++;
				}
				temp += g.getFont().getWidth(ss[i]);
				break;
			}
		}
		return lines;
	}

	public static ConsoleDrawInfo drawConsoleInput(String s, int x, int y) {
		s = s.replaceAll(" ", " <s> ");
		String[] ss = s.split(" ");
		int firstX = x;
		int temp = 0;
		int lines = 0;
		for ( int i = 0; i < ss.length; i++ ) {
			switch (ss[i]) {
			case "<s>":
				x += Settings.spaceSize;
				temp += Settings.spaceSize;
				break;
			default:
				String tempSt = ss[i].replace("\u300C", "<").replace("\u300D", ">").replace("\u3014", "{")
						.replace("\u3015", "}");
				if ( temp + g.getFont().getWidth(tempSt) > gc.getWidth() / 2 - 10 ) {
					y += Settings.newLineSpace;
					x = firstX;
					temp = 0;
					lines++;
				}
				temp += g.getFont().getWidth(tempSt);
				g.drawString(tempSt, x, y);
				x += g.getFont().getWidth(ss[i]);
				break;
			}
		}
		return new ConsoleDrawInfo(x, y, lines);
	}

	public static int drawString(String s, int x, int y) {
		List<String> ss = formatString(s);
		return drawProcessedString(ss, x, y);
	}

	public static int drawProcessedString(List<String> ss, int x, int y) {
		int firstX = x;
		int temp = 0;
		int lines = 0;
		for ( int i = 0; i < ss.size(); i++ ) {
			switch (ss.get(i)) {
			case "<s>":
				x += Settings.spaceSize;
				temp += Settings.spaceSize;
				break;
			case "<alert>":
				g.setColor(Color.yellow);
				break;
			case "<error>":
				g.setColor(Color.red);
				break;
			case "<clear>":
				g.setColor(Color.white);
				break;
			default:
				String tempSt = ss.get(i).replace("\u300C", "<").replace("\u300D", ">").replace("\u3014", "{")
						.replace("\u3015", "}");
				if ( temp + g.getFont().getWidth(tempSt) > gc.getWidth() / 2 - 10 ) {
					y += Settings.newLineSpace;
					x = firstX;
					temp = 0;
					lines++;
				}
				temp += g.getFont().getWidth(tempSt);
				g.drawString(tempSt, x, y);
				x += g.getFont().getWidth(ss.get(i));
				break;
			}
		}
		return lines;
	}

	/**
	 * Takes a string, splits the string up and extracts color formatting. <br>
	 * E.G.<br>
	 * Input: error{hello there}.<br>
	 * Output: &lt;error>, hello, &lt;s>, there, &lt;s>, &lt;clear>, .
	 */
	private static List<String> formatString(String s) {
		// String s = "error{hello} what is going on error{here}? green{I have} error{no
		// clue}.";
		s = s.replaceAll("\\{", ">{");
		s = s.replaceAll("\\}", "}<clear>");
		String[] ss = s.split("\\{|\\}");
		List<String> l = new ArrayList<String>();
		for ( int i = 0; i < ss.length; i++ ) {
			ss[i] = ss[i].replaceAll(" ", " <s> ");
			String[] s1 = ss[i].split(" ");
			for ( int x = 0; x < s1.length; x++ ) {
				if ( !s1[x].matches("\\<\\w+\\>") && s1[x].matches("\\w+\\>") ) s1[x] = "<" + s1[x];
				if ( s1[x].matches("\\<\\w+\\>\\S+") ) {
					String[] s2 = s1[x].split("\\>");
					for ( int j = 0; j < s2.length; j++ ) {
						if ( s2[j].startsWith("<") ) s2[j] = s2[j] + ">";
						l.add(s2[j]);
					}
				} else {
					l.add(s1[x]);
				}
			}
		}
		return l;
	}

	public static int getTextWidth(String s) {
		List<String> ss = formatString(s);
		int width = 0;
		boolean flag = false;
		for ( int i = 0; i < ss.size(); i++ ) {
			switch (ss.get(i)) {
			case "<s>":
				width += Settings.spaceSize;
			case "<alert>":
			case "<error>":
			case "<clear>":
				flag = true;
				break;
			default:
				width += flag ? 0 : g.getFont().getWidth(ss.get(i));
				break;
			}
		}
		return width;
	}

	public static List<List<String>> formatLine(String s) {
		List<List<String>> list = new ArrayList<List<String>>();
		List<String> tempLineList = new ArrayList<String>();
		List<String> ss = formatString(s);
		int temp = 0;
		for ( int i = 0; i < ss.size(); i++ ) {

			int width = getTextWidth(ss.get(i));
			if ( temp + width > gc.getWidth() / 2 - 10 ) {
				temp = 0;
				list.add(tempLineList);
				tempLineList = new ArrayList<String>();
			}
			tempLineList.add(ss.get(i));
			temp += width;

		}
		if ( !tempLineList.isEmpty() ) list.add(tempLineList);
		System.out.println(list.size());
		return list;
	}

}
