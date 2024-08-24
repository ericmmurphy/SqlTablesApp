package ericmm.maven;

import java.awt.Color;
import java.awt.Font;

/**
 * new Insets(3,8,3,8)
 * new Insets(3,23,3,23)
 * new Insets(0,4,0,4)
 * 
 * BorderFactory.createEmptyBorder(2, 0, 0, 0)
 * BorderFactory.createMatteBorder(1, 0, 0, 0, StyleFactory.getBorderColor())
 * BorderFactory.createEmptyBorder(5, 0, 5, 0)
 * BorderFactory.createEmptyBorder(5, 10, 5, 10)
 * BorderFactory.createEmptyBorder(2, 0, 1, 0)
 * BorderFactory.createMatteBorder(1, 0, 1, 0, StyleFactory.getBorderColor())
 * BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(StyleFactory.getForegroundColor()),BorderFactory.createEmptyBorder(2, 2, 2, 2))
 * 
 */

public class StyleFactory {
	public static Font getHeaderFont() {
		return new Font("Calibri", Font.BOLD, 24);
	}
	
	public static Font getSubHeaderFont() {
		return new Font("Calibri", Font.PLAIN, 22);
	}
	
	public static Color getBackgroundColorLight() {
		return new Color(0xf6f8fd);
	}
	
	public static Color getForegroundColor() {
		return new Color(0x1b1e29);
	}
	
	public static Color getForegroundColorLight() {
		return new Color(0x2e313b);
	}
	
	public static Color getBorderColorLight() {
		return new Color(0x677090);
	}
}