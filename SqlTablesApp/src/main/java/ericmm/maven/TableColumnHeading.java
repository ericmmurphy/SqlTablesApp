package ericmm.maven;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class TableColumnHeading {
	Header header;
	String name;
	String type;
	JPanel panel;
	JLabel label;
	
	TableColumnHeading(Header header, String name, String type) {
		this.header = header;
		this.name = name;
		this.type = type;
	}
}
