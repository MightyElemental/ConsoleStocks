package net.iridgames.consolestocks;

import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.TrueTypeFont;

public class Helper {
	
	
	public static void drawString(Graphics g, String s, float x, float y) {// color{text}
		String[] words = s.split(" ");
		List<String> wordsAndColors = new ArrayList<String>();
		for (int i = 0; i < words.length; i++) {
			if (words[i].contains("{")) {
				wordsAndColors.add("<" + words[i].split("\\{")[0] + ">");
				if (words[i].contains("}")) {
					wordsAndColors.add(words[i].split("\\{")[1].replace("}", ""));
					
				} else {
					wordsAndColors.add(words[i].split("\\{")[1]);
					while (i < words.length && !words[i].contains("}")) {
						i++;
						if (i < words.length) {
							wordsAndColors.add(words[i].replace("}", ""));
						} else {
							break;
						}
					}
				}
				wordsAndColors.add("<default>");
			} else if (!words[i].contains("}")) {
				wordsAndColors.add(words[i]);
			}
			words[i] = words[i].replace("}", "");
			words[i] = words[i].replace("{", "");
		}
		//System.out.println(wordsAndColors);
		float x1 = x;
		for (int i = 0; i < wordsAndColors.size(); i++) {
			String word = wordsAndColors.get(i);
			if (word.contains("<") && word.contains(">")) {
				switch (word) {
					case "<error>":
						g.setColor(Color.red);
						break;
					case "<alert>":
						g.setColor(Color.yellow);
						break;
					case "<default>":
					default:
						g.setColor(Color.white);
						break;
				}
			} else {
				g.drawString(word, x1, y);
				x1 += g.getFont().getWidth(word + "  ");
			}
		}
		
	}
	
	public static float round(double value, int precision) {
		int scale = (int) Math.pow(10, precision);
		return (float) ((double) Math.round(value * scale) / scale);
	}
	
	public static double distance(Point p, Point q) {
		double dx = p.x - q.x; // horizontal difference
		double dy = p.y - q.y; // vertical difference
		double dist = Math.sqrt(dx * dx + dy * dy); // distance using Pythagoras
													// theorem
		return dist;
	}
	
	public static int arrayMin(int[] arr) {
		int i = 0;
		int minI = 0;
		int min = Integer.MAX_VALUE;
		if (arr == null) {
			return 0; // What if 0 is the minimum value? What do you want to do
						// in this case?
		} else {
			while (i < arr.length) {
				if (arr[i] < min && arr[i] != 0) {
					min = arr[i];
					minI = i;
				}
				i++;
			}
		}
		return minI; // min
	}
	
	public static int caltulateCenterX(int obj1X, int obj1Width, int obj2Width) {
		int temp = (obj1Width / 2) - (obj2Width / 2);
		return obj1X + temp;
	}
	
	public static int caltulateCenterY(int obj1Y, int obj1Height, int obj2Height) {
		int temp = (obj1Height / 2) - (obj2Height / 2);
		return obj1Y + temp;
	}
	
	public static int getTextWidthOLD(String text, Graphics2D g) {
		int totaltemp = 0;
		
		for (int c1 = 0; c1 < text.length(); c1++) {
			char ch = text.charAt(c1);
			totaltemp += g.getFontMetrics().charWidth(ch);
		}
		
		return totaltemp;
		
	}
	
	public static int getTextWidth(String text, TrueTypeFont ttf) {
		if (ttf == null) { return 0; }
		return ttf.getWidth(text);
	}
	
	/** Finds the amount of hours overlapping in a 24 hour period. */
	public static int findOverlappingInterval(int startTime1, int endTime1, int startTime2, int endTime2) {
		int overlappingTime = 0;
		
		// First time
		int time1Length = 0;
		if (endTime1 < startTime1) {
			time1Length = 24 - startTime1;
			time1Length += endTime1;
		}
		int[] time1;
		if (time1Length == 0) {
			time1 = new int[Math.abs(endTime1 - startTime1)];
			for (int i1 = startTime1; i1 < endTime1; i1++) {
				time1[i1 - startTime1] = i1;
			}
		} else {
			time1 = new int[time1Length];
			int count = 0;
			for (int i1 = 0; i1 < endTime1; i1++) {
				time1[count] = i1;
				count++;
			}
			for (int i1 = startTime1; i1 < 24; i1++) {
				time1[count] = i1;
				count++;
			}
		}
		
		// Second time
		int time2Length = 0;
		if (endTime2 < startTime2) {
			time2Length = 24 - startTime2;
			time2Length += endTime2;
		}
		int[] time2;
		if (time2Length == 0) {
			time2 = new int[Math.abs(endTime2 - startTime2)];
			for (int i2 = startTime2; i2 < endTime2; i2++) {
				time2[i2 - startTime2] = i2;
			}
		} else {
			time2 = new int[time2Length];
			int count = 0;
			for (int i2 = 0; i2 < endTime2; i2++) {
				time2[count] = i2;
				count++;
			}
			for (int i2 = startTime2; i2 < 24; i2++) {
				time2[count] = i2;
				count++;
			}
		}
		
		// Overlap calculator
		for (int i = 0; i < time1.length; i++) {
			for (int j = 0; j < time2.length; j++) {
				if (time1[i] == time2[j]) {
					overlappingTime++;
				}
			}
		}
		return overlappingTime;
	}
	
}
